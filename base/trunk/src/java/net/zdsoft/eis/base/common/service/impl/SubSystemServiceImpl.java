package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.common.dao.SubSystemDao;
import net.zdsoft.eis.base.common.entity.AppRegistry;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;
import net.zdsoft.keel.util.Pagination;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class SubSystemServiceImpl extends DefaultCacheManager implements
		SubSystemService {

	private SubSystemDao subSystemDao;

	public void setSubSystemDao(SubSystemDao subSystemDao) {
		this.subSystemDao = subSystemDao;
	}

	// ========================以上为set================

	// -------------------缓存信息 begin------------------------

	public void saveSubsystem(SubSystem subsystem) {
		if (null != subsystem.getId() && !"".equals(subsystem.getId())) {
			subSystemDao.updateSubSystem(subsystem);
		} else {
			subSystemDao.insertSubSystem(subsystem);
		}
		clearCache();
	}

	private SubSystem getSingleSubSystem(final Integer subsystemIntId) {
		SubSystem subsystem = null;
		List<SubSystem> list = subSystemDao.getSubSystems(subsystemIntId);
		if (CollectionUtils.isNotEmpty(list)) {
			subsystem = list.get(0);
		}
		return subsystem;
	}

	/**
	 * 根据子系统的数字型id，检索子系统信息
	 * 
	 * @param subsystemIntId
	 *            子系统的数字型id
	 * @return 子系统信息
	 */
	public SubSystem getSubSystem(final Integer subsystemIntId) {
		return getEntityFromCache(new CacheEntityParam<SubSystem>() {

			public SubSystem fetchObject() {
				return getSingleSubSystem(subsystemIntId);
			}

			public String fetchKey() {
				return BaseCacheConstants.EIS_SYSTEM_CODE + subsystemIntId;
			}
		});
	}

	/**
	 * 根据子系统的code，取出子系统信息
	 * 
	 * @param subsystemCode
	 * @return 子系统信息
	 */
	public SubSystem getSubSystemByCode(final String subsystemCode) {
		return getEntityFromCache(new CacheEntityParam<SubSystem>() {

			public SubSystem fetchObject() {
				return subSystemDao.getSubSystemByCode(subsystemCode);
			}

			public String fetchKey() {
				return BaseCacheConstants.EIS_SYSTEM_CODE + subsystemCode;
			}
		});
	}

	/**
	 * 取出子系统列表
	 * 
	 * @return 所有的子系统列表
	 */
	public List<SubSystem> getSubSystems() {
		List<SubSystem> subsystemList = getEntitiesFromCache(new CacheEntitiesParam<SubSystem>() {

			public List<SubSystem> fetchObjects() {
				return subSystemDao.getSubSystems();
			}

			public String fetchKey() {
				return BaseCacheConstants.EIS_SYSTEM_LIST;
			}

		});

		List<SubSystem> resultList = new ArrayList<SubSystem>();
		String type = System.getProperty("eis.subsystem.type");
		if (StringUtils.isBlank(type)) {
			return subsystemList;
		} else {
			for (SubSystem subsystem : subsystemList) {
				if (Integer.valueOf(type).intValue() == subsystem
						.getInitUnitOpen()) {
					resultList.add(subsystem);
				}
			}
			return resultList;
		}
	}

	/**
	 * 取出子系统列表(不走缓存)
	 * 
	 * @return 所有的子系统列表
	 */
	public List<SubSystem> getSubSystemsWithoutCache() {
		List<SubSystem> subsystemList = subSystemDao.getSubSystems();
		List<SubSystem> resultList = new ArrayList<SubSystem>();
		String type = System.getProperty("eis.subsystem.type");
		if (StringUtils.isBlank(type)) {
			return subsystemList;
		} else {
			for (SubSystem subsystem : subsystemList) {
				if (Integer.valueOf(type).intValue() == subsystem
						.getInitUnitOpen()) {
					resultList.add(subsystem);
				}
			}
			return resultList;
		}
	}

	// -------------------缓存信息 end--------------------------

	public boolean isRepeatSubSystemName(String name) {
		return subSystemDao.isRepeatSubSystemName(name);
	}

	public Map<Integer, SubSystem> getCacheSubSystemMap() {
		Map<Integer, SubSystem> subSystemMap = new HashMap<Integer, SubSystem>();
		List<SubSystem> subSystems = getSubSystems();
		for (Iterator<SubSystem> iter = subSystems.iterator(); iter.hasNext();) {
			SubSystem subSystem = (SubSystem) iter.next();
			subSystemMap.put(subSystem.getId().intValue(), subSystem);
		}
		return subSystemMap;
	}

	public List<SubSystem> getSubSystems(Integer[] ids) {
		List<SubSystem> subsystemList = subSystemDao.getSubSystems(ids);

		List<SubSystem> resultList = new ArrayList<SubSystem>();
		String type = System.getProperty("eis.subsystem.type");
		if (StringUtils.isBlank(type)) {
			return subsystemList;
		} else {
			for (SubSystem subsystem : subsystemList) {
				if (Integer.valueOf(type).intValue() == subsystem
						.getInitUnitOpen()) {
					resultList.add(subsystem);
				}
			}
			return resultList;
		}
	}

	public List<SubSystem> getSpecialSubSystems(Set<Integer> ids) {
		List<SubSystem> subsystemList = getSubSystems();
		List<SubSystem> returnList = new ArrayList<SubSystem>();
		for (SubSystem subSystem : subsystemList) {
			if (ids.contains(subSystem.getId().intValue())
					&& subSystem.getCanDistributed() == 0) {
				returnList.add(subSystem);
			}
		}
		return returnList;
	}

	public List<AppRegistry> getUserSystemList(String contextPath,
			int userOwnerType, Integer[] ids, boolean showAll) {
		List<AppRegistry> subSystemList = new ArrayList<AppRegistry>();// 子系统
		List<SubSystem> subList = new ArrayList<SubSystem>();// 子系统
		if (showAll) {
			subList = getSubSystems();
		} else {
			if (ids != null && ids.length > 0) {
				subList = this.getSubSystems(ids);
			}
		}
		for (SubSystem subsystem : subList) {
			AppRegistry dto = new AppRegistry();
			dto.setAppSystemType(SubSystem.APP_SYSTEM_TYPE_LOCAL);
			dto.setId(String.valueOf(subsystem.getId()));
			dto.setAppcode(subsystem.getCode());
			dto.setAppname(subsystem.getName());
			dto.setXurl(contextPath + subsystem.getIndexUrl());
			dto.setImage(subsystem.getImage());
			dto.setDisplayorder(subsystem.getOrderid());
			subSystemList.add(dto);
		}
		switch (userOwnerType) {
		case User.TEACHER_LOGIN:
			break;
		case User.STUDENT_LOGIN:
			for (AppRegistry app : subSystemList) {
				app.setXurl(contextPath
						+ "/stuplatform/login/index.action?appId="
						+ String.valueOf(app.getId()) + "&platform="
						+ String.valueOf(BaseConstant.PLATFORM_STUPLATFORM));
			}
			break;
		case User.FAMILY_LOGIN:
			for (AppRegistry app : subSystemList) {
				app.setXurl(contextPath
						+ "/stuplatform/login/index.action?appId="
						+ String.valueOf(app.getId()) + "&platform="
						+ String.valueOf(BaseConstant.PLATFORM_FAMPLATFORM));
			}
			break;
		case User.OTHER_LOGIN:

			break;
		default:
			break;
		}

		Collections.sort(subSystemList, new Comparator<AppRegistry>() {

			@Override
			public int compare(AppRegistry o1, AppRegistry o2) {
				int order1 = o1.getDisplayorder() == null ? 0 : o1
						.getDisplayorder().intValue();
				int order2 = o2.getDisplayorder() == null ? 0 : o2
						.getDisplayorder().intValue();
				return order1 - order2;
			}
		});

		return subSystemList;
	}

	public List<SubSystem> getThirdPartSubSystems(final String source) {

		List<SubSystem> subsystemList = getEntitiesFromCache(new CacheEntitiesParam<SubSystem>() {

			public List<SubSystem> fetchObjects() {
				return subSystemDao.getThirdPartAppSubSystems(source);
			}

			public String fetchKey() {
				return BaseCacheConstants.EIS_THIRD_PART_SYSTEM_LIST + source;
			}
		});

		List<SubSystem> resultList = new ArrayList<SubSystem>();
		String type = System.getProperty("eis.subsystem.type");
		if (StringUtils.isBlank(type)) {
			return subsystemList;
		} else {
			for (SubSystem subsystem : subsystemList) {
				if (Integer.valueOf(type).intValue() == subsystem
						.getInitUnitOpen()) {
					resultList.add(subsystem);
				}
			}
			return resultList;
		}

	}

	public List<SubSystem> getSubSystemsByConditions(String searchName,
			String searchCode, String searchSortType, String searchSource,
			Pagination page) {
		return subSystemDao.getSubSystemsByConditions(searchName, searchCode,
				searchSortType, searchSource, page);
	}

	public void deleteSubSystem(Integer subSystemId) {
		subSystemDao.deleteSubSystem(subSystemId);
		this.clearSubSystemCache(subSystemId.toString());
	}

	public void insertSubSystem(SubSystem subsystem) {
		subSystemDao.insertSubSystem(subsystem);
		this.clearSubSystemCache(subsystem.getId().toString());
	}

	public void updateSubSystem(SubSystem subsystem) {
		subSystemDao.updateSubSystem(subsystem);
		this.clearSubSystemCache(subsystem.getId().toString());
	}

	public void clearSubSystemCache(String subsystemId) {
		removeFromCache(BaseCacheConstants.EIS_SYSTEM_CODE + subsystemId);
		removeFromCache(BaseCacheConstants.EIS_SYSTEM_LIST);
		removeFromCache(BaseCacheConstants.EIS_THIRD_PART_SYSTEM_LIST);
		removeFromCache(BaseCacheConstants.EIS_THIRD_PART_SYSTEM_LIST
				+ SubSystem.SOURCE_LOCAL);
		removeFromCache(BaseCacheConstants.EIS_THIRD_PART_SYSTEM_LIST
				+ SubSystem.SOURCE_THIRD_PART_NORMAL);
	}

	public int countMaxId() {
		return subSystemDao.countMaxId();
	}
}
