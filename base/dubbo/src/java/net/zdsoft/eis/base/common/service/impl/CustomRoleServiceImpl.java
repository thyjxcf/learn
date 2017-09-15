package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.common.dao.CustomRoleDao;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class CustomRoleServiceImpl extends DefaultCacheManager implements
		CustomRoleService {

	private CustomRoleDao customRoleDao;

	private CustomRoleUserService customRoleUserService;
	
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setCustomRoleDao(CustomRoleDao customRoleDao) {
		this.customRoleDao = customRoleDao;
	}

	public void setCustomRoleUserService(
			CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

	public void deleteCustomRoles(String unitId) {
		customRoleDao.deleteCustomRoles(unitId);
	}
	
	public Map<String,List<CustomRole>> getCustomRoleListMap(String[] unitIds, String roleCode){
		Map<String,List<CustomRole>> cusMap=new HashMap<String, List<CustomRole>>();
		List<CustomRole> templateList = new ArrayList<CustomRole>();
		if(StringUtils.isNotBlank(roleCode)){
			CustomRole customRoleByCode = customRoleDao.getCustomRoleByCode(BaseConstant.ZERO_GUID, roleCode);
			if(customRoleByCode!=null){
				templateList.add(customRoleByCode);
			}
		}else{
			templateList=customRoleDao.getCustomRoleList(BaseConstant.ZERO_GUID);
		}
		List<CustomRole> list = new ArrayList<CustomRole>();
		if(StringUtils.isNotBlank(roleCode)){
			list=customRoleDao.getCustomRoleList(unitIds,roleCode);
		}else{
			list=customRoleDao.getCustomRoleList(unitIds);
		}
		for (CustomRole item : list) {
			List<CustomRole> list2 = cusMap.get(item.getUnitId());
			if(list2==null){
				list2=new ArrayList<CustomRole>();
				cusMap.put(item.getUnitId(), list2);
			}
			list2.add(item);
		}
		CustomRole linCus = null;
		List<CustomRole> addCusList=new ArrayList<CustomRole>();
		for(String unitId:unitIds){
			List<CustomRole> cusList = cusMap.get(unitId);

			if (CollectionUtils.isEmpty(cusList)) {
				for (CustomRole role : templateList) {
					try {
						linCus = (CustomRole) BeanUtils.cloneBean(role);
						linCus.setUnitId(unitId);
						addCusList.add(linCus);
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				Map<String, CustomRole> roleMap = new HashMap<String, CustomRole>();
				for (CustomRole role : cusList) {
					roleMap.put(role.getRoleCode(), role);
				}
				for (CustomRole role : templateList) {
					if (!roleMap.containsKey(role.getRoleCode())) {
						try {
							linCus = (CustomRole) BeanUtils.cloneBean(role);
							linCus.setUnitId(unitId);
							addCusList.add(linCus);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		if(addCusList.size()>0){
			customRoleDao.addCustomRoles(addCusList);
		}
		
		Map<String,List<CustomRole>> finCusMap=new HashMap<String, List<CustomRole>>();
		List<CustomRole> finCusList = new ArrayList<CustomRole>();
		if(StringUtils.isNotBlank(roleCode)){
			finCusList=customRoleDao.getCustomRoleList(unitIds,roleCode);
		}else{
			finCusList=customRoleDao.getCustomRoleList(unitIds);
		}
		for (CustomRole item : finCusList) {
			List<CustomRole> list2 = finCusMap.get(item.getUnitId());
			if(list2==null){
				list2=new ArrayList<CustomRole>();
				finCusMap.put(item.getUnitId(), list2);
			}
			list2.add(item);
		}
		
		return finCusMap;
	}
	
	public synchronized List<CustomRole> getCustomRoleList(String unitId) {
		List<CustomRole> list = customRoleDao.getCustomRoleList(unitId);
		List<CustomRole> templateList = customRoleDao
				.getCustomRoleList(BaseConstant.ZERO_GUID);

		if (CollectionUtils.isEmpty(list)) {
			for (CustomRole role : templateList) {
				role.setUnitId(unitId);
			}
			customRoleDao.addCustomRoles(templateList);
			list = customRoleDao.getCustomRoleList(unitId);
		} else {
			boolean isChanged = false;
			Map<String, CustomRole> roleMap = new HashMap<String, CustomRole>();
			for (CustomRole role : list) {
				roleMap.put(role.getRoleCode(), role);
			}
			List<CustomRole> addList = new ArrayList<CustomRole>();
			for (CustomRole role : templateList) {
				if (!roleMap.containsKey(role.getRoleCode())) {
					isChanged = true;
					role.setUnitId(unitId);
					addList.add(role);
				}
			}
			if (isChanged) {
				customRoleDao.addCustomRoles(addList);
				list = customRoleDao.getCustomRoleList(unitId);
			}
		}
		return list;
	}

	public List<CustomRole> getCustomRoleList(final String unitId,
			final String subsystem) {
		return getEntitiesFromCache(new CacheEntitiesParam<CustomRole>() {
			public String fetchKey() {
				return BaseCacheConstants.EIS_CUSTOM_ROLE_LIST + unitId
						+ subsystem;
			}

			public List<CustomRole> fetchObjects() {
				List<CustomRole> roleList = getCustomRoleList(unitId);
				Map<String, List<CustomRole>> roleMap = new HashMap<String, List<CustomRole>>();
				for (CustomRole role : roleList) {
					String[] subsystems = role.getSubsystems().split(",");
					for (int i = 0; i < subsystems.length; i++) {
						List<CustomRole> tempRoleList = roleMap
								.get(subsystems[i]);
						if (CollectionUtils.isEmpty(tempRoleList))
							tempRoleList = new ArrayList<CustomRole>();
						tempRoleList.add(role);
						roleMap.put(subsystems[i], tempRoleList);
					}
				}
				return roleMap.get(subsystem);
			}
		});
	}

	@Override
	public List<CustomRole> getCustomRoleWithUserList(String unitId,
			String subsystem) {
		List<CustomRole> roleList = getCustomRoleList(unitId, subsystem);
		Set<String> roleIds = new HashSet<String>();
		if(CollectionUtils.isEmpty(roleList))
		{
			return new ArrayList<CustomRole>();
		}
		
		for (CustomRole role : roleList) {
			roleIds.add(role.getId());
		}
		Map<String, List<CustomRoleUser>> roleUserMap = customRoleUserService
				.getCustomRoleUserMap(unitId, roleIds.toArray(new String[0]));
		for (CustomRole role : roleList) {
			if (roleUserMap.containsKey(role.getId())) {
				List<CustomRoleUser> roleUserList = roleUserMap.get(role
						.getId());
				String userNames = "";
				String userIds = "";
				for (CustomRoleUser roleUser : roleUserList) {
					if (StringUtils.isNotBlank(roleUser.getUserName())) {
						userNames += roleUser.getUserName() + ",";
						userIds += roleUser.getUserId() + ",";
					}
				}
				if (StringUtils.isNotBlank(userNames)) {
					userNames = userNames.substring(0, userNames.length() - 1);
					userIds = userIds.substring(0, userIds.length() - 1);
				}
				role.setUserNames(userNames);
				role.setUserIds(userIds);
			}
		}
		return roleList;
	}

	@Override
	public List<CustomRole> getCustomRoleListByUserId(String unitId,
			String userId, String subsystemId) {
		List<CustomRoleUser> roleUserList = customRoleUserService
				.getCustomRoleUserListByUserId(userId);
		List<CustomRole> roleList = getCustomRoleWithUserList(unitId,
				subsystemId);
		List<CustomRole> resultList = new ArrayList<CustomRole>();
		Set<String> roleIds = new HashSet<String>();
		for (CustomRoleUser roleUser : roleUserList) {
			roleIds.add(roleUser.getRoleId());
		}
		for (CustomRole role : roleList) {
			if (roleIds.contains(role.getId())) {
				resultList.add(role);
			}
		}
		return resultList;
	}

	@Override
	public CustomRole getCustomRoleByRoleCode(final String unitId,
			final String roleCode) {
		return getEntityFromCache(new CacheEntityParam<CustomRole>() {
			public String fetchKey() {
				return BaseCacheConstants.EIS_MCODEDETAIL_LIST_MCODEIDTHISID
						+ unitId + "_" + roleCode;
			}

			public CustomRole fetchObject() {
				return customRoleDao.getCustomRoleByCode(unitId, roleCode);
			}
		});
	}

	@Override
	public CustomRole getCustomRoleById(final String id) {
		return getEntityFromCache(new CacheEntityParam<CustomRole>() {
			public String fetchKey() {
				return BaseCacheConstants.EIS_MCODEDETAIL_LIST_MCODEIDTHISID
						+ id;
			}

			public CustomRole fetchObject() {
				return customRoleDao.getCustomRoleById(id);
			}
		});
	}

	@Override
	public boolean isCustomRole(String unitId, String userId, String subsystem,
			String roleCode) {
		List<CustomRole> customRoles = getCustomRoleListByUserId(unitId,
				userId, subsystem);
		for (CustomRole role : customRoles) {
			if (roleCode.equals(role.getRoleCode())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Map<String, CustomRole> getCustomRoleMap(String[] unitIds,
			String roleCode) {
		List<CustomRole> crList = customRoleDao.getCustomRoleList(unitIds,
				roleCode);
		Map<String, CustomRole> crMap = new HashMap<String, CustomRole>();
		Set<String> roleIds = new HashSet<String>();
		for (CustomRole item : crList) {
			roleIds.add(item.getId());
		}
		List<CustomRoleUser> customRoleUserList = customRoleUserService
				.getCustomRoleUserList(roleIds.toArray(new String[0]));
		Map<String, List<CustomRoleUser>> cruMap = new HashMap<String, List<CustomRoleUser>>();// key
																								// roleId
		Set<String> userIds=new HashSet<String>();
		for (CustomRoleUser item : customRoleUserList) {
			List<CustomRoleUser> list = cruMap.get(item.getRoleId());
			if (list == null) {
				list = new ArrayList<CustomRoleUser>();
				cruMap.put(item.getRoleId(), list);
			}
			list.add(item);
			userIds.add(item.getUserId());
		}
		Map<String, User> usersMap = userService.getUsersMap(userIds.toArray(new String[0]));
		for (CustomRole item : crList) {
			List<CustomRoleUser> list = cruMap.get(item.getId());
			StringBuffer sbf = new StringBuffer();
			StringBuffer sbfUserNames = new StringBuffer();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					CustomRoleUser cru = list.get(i);
					if (i == list.size() - 1) {
						sbf.append(cru.getUserId());
					} else {
						sbf.append(cru.getUserId() + ",");
					}
					User user = usersMap.get(cru.getUserId());
					if(user!=null){
						if (i == list.size() - 1) {
							sbfUserNames.append(user.getRealname());
						} else {
							sbfUserNames.append(user.getRealname()+",");
						}
					}
				}
			}
			item.setUserIds(sbf.toString());
			item.setUserNames(sbfUserNames.toString());
		}
		for (CustomRole item : crList) {
			crMap.put(item.getUnitId(), item);
		}
		return crMap;
	}

	public Map<String, List<CustomRoleUser>> getCustomRoleWithUserList(
			String unitId, String[] roleCodes) {
		List<CustomRole> roleList = new ArrayList<CustomRole>();
		Set<String> roleIds = new HashSet<String>();
		for (String roleCode : roleCodes) {
			CustomRole role = getCustomRoleByRoleCode(unitId, roleCode);
			roleIds.add(role.getId());
			roleList.add(role);
		}

		Map<String, List<CustomRoleUser>> roleUserMap = customRoleUserService
				.getCustomRoleUserMap(unitId, roleIds.toArray(new String[0]));

		Map<String, List<CustomRoleUser>> returnRoleUserMap = new HashMap<String, List<CustomRoleUser>>();
		for (CustomRole role : roleList) {
			List<CustomRoleUser> customRoleUsers = roleUserMap
					.get(role.getId());
			if (customRoleUsers == null)
				continue;
			returnRoleUserMap.put(role.getRoleCode(), customRoleUsers);
		}
		return returnRoleUserMap;
	}
}
