/**
 * 
 */
package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.common.dao.SimpleModuleDao;
import net.zdsoft.eis.base.common.entity.BasicModule;
import net.zdsoft.eis.base.common.entity.SimpleModule;
import net.zdsoft.eis.base.common.service.SimpleModuleService;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;

/**
 * @author zhaosf
 * @since 1.0
 * @version $Id: ModuleServiceImpl.java, v 1.0 2007-11-9 下午05:47:27 zhaosf Exp $
 */

public class SimpleModuleServiceImpl extends DefaultCacheManager implements
		SimpleModuleService {
	private SimpleModuleDao simpleModuleDao;

	public void setSimpleModuleDao(SimpleModuleDao simpleModuleDao) {
		this.simpleModuleDao = simpleModuleDao;
	}

	// -------------------缓存信息 begin------------------------
	public List<SimpleModule> getModules(final int platform,
			final int subsystem, final Long parentId) {
		return getEntitiesFromCache(new CacheEntitiesParam<SimpleModule>() {

			public List<SimpleModule> fetchObjects() {
				return simpleModuleDao
						.getModules(platform, subsystem, parentId);
			}

			public String fetchKey() {
				if (subsystem == 0)
					return BaseCacheConstants.EIS_SIMPLE_MODULE_LIST_APPID_PARENTID
							+ platform + parentId;
				else
					return BaseCacheConstants.EIS_SIMPLE_MODULE_LIST_APPID_PARENTID
							+ platform + subsystem + parentId;
			}
		});
	}

	public Map<Integer, SimpleModule> getModulesMap() {
		return getObjectMapFromCache(new CacheObjectMapParam<Integer, SimpleModule>() {

			public Map<Integer, SimpleModule> fetchObjects() {
				return simpleModuleDao.getModulesMap();
			}

			public String fetchKey() {
				return BaseCacheConstants.EIS_SIMPLE_MODULE_MAP;
			}
		});
	}

	// -------------------缓存信息 end------------------------

	public List<BasicModule> getBasicModules(int platform, int subsystem,
			int unitClass, Long parentId) {
		return convertBasicModules(getModules(platform, subsystem, parentId));
	}

	public List<BasicModule> getParentBasicModules(Long moduleId,
			boolean hasSubsystem) {
		return convertBasicModules(getParentModules(moduleId));
	}

	public Set<Integer> getActiveSubsytems() {
		return Collections.emptySet();
	}

	public Set<Integer> getActiveSubsytems(int platform) {
		return simpleModuleDao.getActiveSubsytems(platform);
	}

	/**
	 * 转化
	 * 
	 * @param modules
	 * @return
	 */
	private List<BasicModule> convertBasicModules(List<SimpleModule> modules) {
		List<BasicModule> list = new ArrayList<BasicModule>();
		for (SimpleModule simpleModule : modules) {
			list.add((BasicModule) simpleModule);
		}
		return list;
	}

	public List<SimpleModule> getParentModules(Long moduleId) {
		List<SimpleModule> rtnList = new ArrayList<SimpleModule>();
		SimpleModule simpleModule = simpleModuleDao.getModule(moduleId);

		if (null == simpleModule)
			return rtnList;

		rtnList.add(simpleModule);
		while (simpleModule.getParentid() != -1) {
			simpleModule = simpleModuleDao
					.getModule(simpleModule.getParentid());
			rtnList.add(simpleModule);
		}
		return rtnList;
	}

	public SimpleModule getModule(long id) {
		return simpleModuleDao.getModule(id);
	}

	public Map<Integer, SimpleModule> getModulesMap(Integer... intIds) {
		return simpleModuleDao.getModulesMap(intIds);
	}
	

	public void disableModules(int subSystemId) {
		simpleModuleDao.disableModules(subSystemId);
        clearCache();
    }

	 public void enableModules(int subSystemId) {
		 simpleModuleDao.enableModules(subSystemId);
		 clearCache();
	 }

}
