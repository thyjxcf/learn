package net.zdsoft.eis.system.data.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.ServerAuthorize;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.subsystemcall.service.BaseDataSubsystemService;
import net.zdsoft.eis.system.data.dao.RoleDao;
import net.zdsoft.eis.system.data.dao.UserRoleRelationDao;
import net.zdsoft.eis.system.data.entity.UserRoleRelation;
import net.zdsoft.eis.system.data.service.RolePermService;
import net.zdsoft.eis.system.data.service.UserRoleRelationService;
import net.zdsoft.leadin.util.SQLUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author luxingmu
 * @version $Revision: 1.13 $, $Date: 2007/01/29 02:35:43 $
 */
public class UserRoleRelationServiceImpl implements UserRoleRelationService {
	private static final Logger log = LoggerFactory
			.getLogger(UserRoleRelationServiceImpl.class);
	private UserRoleRelationDao userRoleRelationDao;
	private RolePermService rolePermService;
	private BaseDataSubsystemService baseDataSubsystemService;
	private SubSystemService subSystemService;
	private RoleDao roleDao;
	private ServerService serverService;

	public void setUserRoleRelationDao(UserRoleRelationDao userRoleRelationDao) {
		this.userRoleRelationDao = userRoleRelationDao;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public void setSubSystemService(SubSystemService subSystemService) {
		this.subSystemService = subSystemService;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setBaseDataSubsystemService(
			BaseDataSubsystemService baseDataSubsystemService) {
		this.baseDataSubsystemService = baseDataSubsystemService;
	}

	public void setRolePermService(RolePermService rolePermService) {
		this.rolePermService = rolePermService;
	}

	public void deleteUserRole(String roleIds) {
		userRoleRelationDao.deleteUserRole(roleIds, null);
	}

	public void deleteUserRole(String[] userIds) {
		userRoleRelationDao.deleteUserRole(userIds);
	}

	public void saveUserRoles(String[] userIds, String[] roleIds,
			String unitId, String deptId) {
		if (userIds != null) {
			Map<String, List<UserRoleRelation>> userRoleMap = new HashMap<String, List<UserRoleRelation>>();
			Map<String, List<String>> oldMap = new HashMap<String, List<String>>();
			List<String> userIdList = Arrays.asList(userIds);
			List<String> deleteIds = new ArrayList<String>();
			for (int i = 0; i < roleIds.length; i++) {
				List<UserRoleRelation> list = new ArrayList<UserRoleRelation>();
				if (deptId.equals(BaseConstant.ZERO_GUID) || StringUtils.isBlank(deptId)) {
					list = userRoleRelationDao.getUserRolesByRoleId(roleIds[i]);
				} else {
					list = userRoleRelationDao.getUserRolesByRoleIdAndDeptId(
							roleIds[i], deptId);
				}
				List<String> oldUserIds = new ArrayList<String>();
				userRoleMap.put(roleIds[i], list);
				for (int j = 0; j < list.size(); j++) {
					if (!userIdList.contains(list.get(j).getUserid())) {
						deleteIds.add(list.get(j).getId());
					} else {
						oldUserIds.add(list.get(j).getUserid());
					}
				}
				oldMap.put(roleIds[i], oldUserIds);
			}
			if (CollectionUtils.isNotEmpty(deleteIds)) {
				userRoleRelationDao.deleteUserRoles(deleteIds
						.toArray(new String[0]));
			}
			List<UserRoleRelation> resultList = new LinkedList<UserRoleRelation>();
			for (int i = 0; i < roleIds.length; i++) {
				String roleId = roleIds[i];
				List<String> noAddUserIds = oldMap.get(roleId);
				for (int j = 0; j < userIds.length; j++) {
					if (noAddUserIds.contains(userIds[j]))
						continue;
					UserRoleRelation userRoleRelation = new UserRoleRelation();
					userRoleRelation.setUserid(userIds[j]);
					userRoleRelation.setRoleid(roleId);
					userRoleRelation.setFlag(new Integer(0));
					resultList.add(userRoleRelation);
				}
			}
			if (!resultList.isEmpty()) {
				userRoleRelationDao.saveUserRoles(resultList);
			}
			reInsertServerAuth(userIds, roleIds, null, unitId);
		} else {
			List<String> deleteUserIds = new ArrayList<String>();
			for (int i = 0; i < roleIds.length; i++) {
				List<UserRoleRelation> list = new ArrayList<UserRoleRelation>();
				if (deptId.equals(BaseConstant.ZERO_GUID)) {
					list = userRoleRelationDao.getUserRolesByRoleId(roleIds[i]);
				} else {
					list = userRoleRelationDao.getUserRolesByRoleIdAndDeptId(
							roleIds[i], deptId);
				}
				for (int j = 0; j < list.size(); j++) {
					deleteUserIds.add(list.get(j).getUserid());
				}
			}
			userRoleRelationDao.deleteUserRole(SQLUtils.toSQLInString(roleIds),
					deleteUserIds.toArray(new String[deleteUserIds.size()]));
		}
	}

	public void saveUserRoles(String[] userIds, String[] roleIds, String unitId) {
		if (userIds != null) {
			Map<String, List<UserRoleRelation>> userRoleMap = new HashMap<String, List<UserRoleRelation>>();
			Map<String, List<String>> oldMap = new HashMap<String, List<String>>();
			List<String> userIdList = Arrays.asList(userIds);
			List<String> deleteIds = new ArrayList<String>();
			for (int i = 0; i < roleIds.length; i++) {
				List<UserRoleRelation> list = userRoleRelationDao
						.getUserRolesByRoleId(roleIds[i]);
				List<String> oldUserIds = new ArrayList<String>();
				userRoleMap.put(roleIds[i], list);
				for (int j = 0; j < list.size(); j++) {
					if (!userIdList.contains(list.get(j).getUserid())) {
						// 只有单个角色的时候才允许删除 特殊处理
						if (roleIds.length == 1)
							deleteIds.add(list.get(j).getId());
					} else {
						oldUserIds.add(list.get(j).getUserid());
					}
				}
				oldMap.put(roleIds[i], oldUserIds);
			}
			if (CollectionUtils.isNotEmpty(deleteIds)) {
				userRoleRelationDao.deleteUserRoles(deleteIds
						.toArray(new String[0]));
			}
			List<UserRoleRelation> resultList = new LinkedList<UserRoleRelation>();
			for (int i = 0; i < roleIds.length; i++) {
				String roleId = roleIds[i];
				List<String> noAddUserIds = oldMap.get(roleId);
				for (int j = 0; j < userIds.length; j++) {
					if (noAddUserIds.contains(userIds[j]))
						continue;
					UserRoleRelation userRoleRelation = new UserRoleRelation();
					userRoleRelation.setUserid(userIds[j]);
					userRoleRelation.setRoleid(roleId);
					userRoleRelation.setFlag(new Integer(0));
					resultList.add(userRoleRelation);
				}
			}
			if (!resultList.isEmpty()) {
				userRoleRelationDao.saveUserRoles(resultList);
			}
			reInsertServerAuth(userIds, roleIds, null, unitId);
		} else {
			userRoleRelationDao.deleteUserRole(SQLUtils.toSQLInString(roleIds),
					null);
		}
	}

	public void saveUserRolesFromUser(String[] userIds, String[] roleIds,
			String unitId) {
		if (roleIds != null) {
			Map<String, List<UserRoleRelation>> userRoleMap = new HashMap<String, List<UserRoleRelation>>();
			Map<String, List<String>> oldMap = new HashMap<String, List<String>>();
			List<String> roleIdList = Arrays.asList(roleIds);
			List<String> deleteIds = new ArrayList<String>();
			for (int i = 0; i < userIds.length; i++) {
				List<UserRoleRelation> list = userRoleRelationDao
						.getUserRolesByUserId(userIds[i]);
				List<String> oldRoleIds = new ArrayList<String>();
				userRoleMap.put(userIds[i], list);
				for (int j = 0; j < list.size(); j++) {
					if (!roleIdList.contains(list.get(j).getRoleid())) {
						deleteIds.add(list.get(j).getId());
					} else {
						oldRoleIds.add(list.get(j).getRoleid());

					}
				}
				oldMap.put(userIds[i], oldRoleIds);
			}
			if (CollectionUtils.isNotEmpty(deleteIds)) {
				userRoleRelationDao.deleteUserRoles(deleteIds
						.toArray(new String[0]));
			}
			List<UserRoleRelation> resultList = new LinkedList<UserRoleRelation>();
			for (int i = 0; i < userIds.length; i++) {
				String userid = userIds[i];
				List<String> noAddRoleIds = oldMap.get(userid);
				for (int j = 0; j < roleIds.length; j++) {
					if (noAddRoleIds.contains(roleIds[j]))
						continue;
					UserRoleRelation userRoleRelation = new UserRoleRelation();
					userRoleRelation.setUserid(userid);
					userRoleRelation.setRoleid(roleIds[j]);
					userRoleRelation.setFlag(new Integer(0));
					resultList.add(userRoleRelation);
				}
			}

			if (!resultList.isEmpty()) {
				userRoleRelationDao.saveUserRoles(resultList);
			}

			reInsertServerAuth(userIds, roleIds, null, unitId);
		} else {
			userRoleRelationDao.deleteUserRole(userIds);
		}
	}

	private void reInsertServerAuth(String[] userIds, String[] roleIds,
			String[] noUserIds, String unitId) {

		// 某单位内所有角色对应的子系统关系
		Map<String, String> roleSystemMap = new HashMap<String, String>();
		List<Role> roleList = roleDao.getRoles(unitId);
		for (Role role2 : roleList) {
			if (role2.getIsactive())// 是否激活
				roleSystemMap.put(role2.getId(), role2.getSubsystem());
		}
		reInsertServerAuthorize(userIds, roleSystemMap, noUserIds);
	}

	public void reInsertServerAuthorize(String[] userIds,
			Map<String, String> roleSystemMap, String[] noUserIds) {
		// 在对用户进行角色授权和角色权限调整时，将用户和子系统的权限写入base_server_authorize

		String[] deleteUserids = userIds;
		if (noUserIds != null) {
			int userLength = 0;
			if (userIds != null)
				userLength = userIds.length;
			deleteUserids = new String[userLength + noUserIds.length];
			int i = 0;
			for (String nouser : noUserIds) {
				deleteUserids[i++] = nouser;
			}
			if (userIds != null) {
				for (String user : userIds) {
					deleteUserids[i++] = user;
				}
			}

		}
		// 删除涉及用户的base_server_authorize表记录
		baseDataSubsystemService.deleteServerAuthorizes(deleteUserids,
				Server.SERVER_INNER);

		Map<Integer, SubSystem> subsystemMap = subSystemService
				.getCacheSubSystemMap();
		Map<String, Long> serverCodeIdMap = serverService
				.getServerMapByServerCode();
		Set<String> processSet = new HashSet<String>();

		List<ServerAuthorize> authInsertList = new ArrayList<ServerAuthorize>();
		// 重新取出所有涉及用户对应的角色关系 并组织成多条用户-子系统 记录 插入base_server_authorize
		List<UserRoleRelation> userRoleList = userRoleRelationDao
				.getUserRolesByUserIds(deleteUserids);
		for (UserRoleRelation userRoleRelation : userRoleList) {
			String roleSubsystems = roleSystemMap.get(userRoleRelation
					.getRoleid());
			if (roleSubsystems == null) {
				log.error("根据角色id找不到角色对应的子系统权限");
				continue;
			}
			String[] subsystemGroup = roleSubsystems.split(",");
			for (String subsys : subsystemGroup) {
				if (subsystemMap.get(Integer.parseInt(subsys)) == null) {
					log.error("找不到对应的子系统(自动跳过):" + subsys);
					continue;
				}
				if (serverCodeIdMap.get(subsystemMap.get(
						Integer.parseInt(subsys)).getCode()) == null) {
					log.info("mq找不到对应的子系统(自动跳过):" + subsys);
					continue;
				}
				String userid = userRoleRelation.getUserid();
				Long serverid = serverCodeIdMap.get(subsystemMap.get(
						Integer.parseInt(subsys)).getCode());

				if (!processSet.contains(userid + serverid))
					processSet.add(userid + serverid);
				else
					continue;
				ServerAuthorize auth = new ServerAuthorize();
				auth.setServerId(serverid);
				auth.setUserId(userid);
				authInsertList.add(auth);
			}
		}

		if (!authInsertList.isEmpty()) {
			baseDataSubsystemService.addServerAuthorizes(authInsertList);
		}
	}

	public void saveDefaultUserRole(String userId, String roleId) {
		userRoleRelationDao.saveDefaultUserRole(userId, roleId);
	}

	public List<UserRoleRelation> getUserRoles(String roleId) {
		return userRoleRelationDao.getUserRolesByRoleId(roleId);
	}

	public List<UserRoleRelation> getUserRoles(String[] userId) {
		return userRoleRelationDao.getUserRolesByUserIds(userId);
	}

	public String getDefaultRoleId(String userId) {
		return userRoleRelationDao.getDefaultRoleId(userId);
	}

	/**
	 * @deprecated
	 */
	public boolean checkUserModule(String userId, String moduleMid) {
		List<UserRoleRelation> relaList = this
				.getUserRoles(new String[] { userId.toString() });

		boolean rtn = false;
		if (!relaList.isEmpty()) {
			String[] roleIds = new String[relaList.size()];
			for (int i = 0; i < roleIds.length; i++) {
				roleIds[i] = relaList.get(i).getRoleid();
			}
			List<Role> roleList = rolePermService.getCacheRoleList(roleIds);

			for (Role role : roleList) {
				Set<Module> modSet = role.getModSet();
				Set<String> allModSet = new HashSet<String>();
				for (Module m : modSet) {
					allModSet.add(m.getMid());
				}
				if (allModSet.contains(moduleMid)) {
					rtn = true;
					break;
				}
			}
		}

		return rtn;
	}

	public boolean checkUserModuleAndOperation(String userId, String moduleId,
			String oper) {
		List<UserRoleRelation> relaList = this
				.getUserRoles(new String[] { userId.toString() });

		boolean rtn = false;
		if (!relaList.isEmpty()) {
			String[] roleIds = new String[relaList.size()];
			for (int i = 0; i < roleIds.length; i++) {
				roleIds[i] = relaList.get(i).getRoleid();
			}
			List<Role> roleList = rolePermService.getCacheRoleList(roleIds);

			for (Role role : roleList) {
				Set<Module> modSet = role.getModSet();
				Set<String> allModSet = new HashSet<String>();
				for (Module m : modSet) {
					allModSet.add(m.getMid());
				}
				if (allModSet.contains(moduleId) && role.getOperSet() != null
						&& role.getOperSet().contains(oper)) {
					rtn = true;
					break;
				}
			}
		}
		return rtn;
	}

	@Override
	public List<UserRoleRelation> getAllUserRoleRelaction(String[] userIds) {
		return userRoleRelationDao.getAllUserRoleRelaction(userIds);
	}

	/*
	 * (non-Javadoc) 判断一下用户有没有这个权限,把有该权限的用户id返回
	 * 
	 * @see
	 * net.zdsoft.eis.system.data.service.UserRoleRelationService#checkUserModule
	 * (java.lang.String[], java.lang.String)
	 */
	@Override
	public Set<String> checkUserModule(String[] userIds, String moduleId) {
		Set<String> returnUserIds = new HashSet<String>();
		List<UserRoleRelation> relaList = this.getUserRoles(userIds);
		Map<String, List<String>> userRoleMap = new HashMap<String, List<String>>();
		Set<String> roleSet = new HashSet<String>();
		if (relaList != null) {
			for (UserRoleRelation rela : relaList) {
				roleSet.add(rela.getRoleid());
				List<String> templist = userRoleMap.get(rela.getRoleid());
				if (templist != null) {
					templist.add(rela.getUserid());
					userRoleMap.put(rela.getRoleid(), templist);
				} else {
					templist = new ArrayList<String>();
					templist.add(rela.getUserid());
					userRoleMap.put(rela.getRoleid(), templist);
				}
			}
		}
		if (!roleSet.isEmpty()) {
			// Map<String,Integer>rolePermMap =
			// rolePermService.getRolePerm(roleSet.toArray(new
			// String[0]),moduleId);
			List<String> rolePermIds = rolePermService.getRolePerm(
					roleSet.toArray(new String[0]), moduleId);
			if (rolePermIds != null && rolePermIds.size() > 0
					&& userRoleMap != null) {
				for (String roleId : rolePermIds) {
					List<String> templist = userRoleMap.get(roleId);
					if (templist != null) {
						returnUserIds.addAll(templist);
					}
				}
			}
		}
		return returnUserIds;
	}
}
