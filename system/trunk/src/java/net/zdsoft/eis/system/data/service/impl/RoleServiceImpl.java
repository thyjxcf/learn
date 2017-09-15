package net.zdsoft.eis.system.data.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;
import net.zdsoft.eis.system.constant.SystemConstant;
import net.zdsoft.eis.system.data.dao.RoleDao;
import net.zdsoft.eis.system.data.dao.RolePermDao;
import net.zdsoft.eis.system.data.dao.UserRoleRelationDao;
import net.zdsoft.eis.system.data.entity.RolePerm;
import net.zdsoft.eis.system.data.entity.UserRoleRelation;
import net.zdsoft.eis.system.data.service.RolePermService;
import net.zdsoft.eis.system.data.service.RoleService;
import net.zdsoft.eis.system.data.service.UserRoleRelationService;
import net.zdsoft.eis.system.frame.service.ModelOperatorService;
import net.zdsoft.keelcnet.entity.DtoAssembler;
import net.zdsoft.leadin.util.AssembleTool;
import net.zdsoft.leadin.util.SQLUtils;

/**
 * @author luxingmu
 * @version $Revision: 1.27 $, $Date: 2007/01/12 06:42:00 $
 */
public class RoleServiceImpl extends DefaultCacheManager implements RoleService {
	private RoleDao roleDao;
	private RolePermDao rolePermDao;
	private ModelOperatorService modelOperatorService;
	private ModuleService moduleService;
	private UserRoleRelationService userRoleRelationService;
	private RolePermService rolePermService;
	private UserRoleRelationDao userRoleRelationDao;

	public void setUserRoleRelationDao(UserRoleRelationDao userRoleRelationDao) {
		this.userRoleRelationDao = userRoleRelationDao;
	}

	public void setRolePermService(RolePermService rolePermService) {
		this.rolePermService = rolePermService;
	}

	public void setRolePermDao(RolePermDao rolePermDao) {
		this.rolePermDao = rolePermDao;
	}

	public void setUserRoleRelationService(
			UserRoleRelationService userRoleRelationService) {
		this.userRoleRelationService = userRoleRelationService;
	}

	public void setModelOperatorService(
			ModelOperatorService modelOperatorService) {
		this.modelOperatorService = modelOperatorService;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	// -------------------缓存信息 begin------------------------    
	public Role getCachedPermRole(final String intId) {
		return getEntityFromCache(new CacheEntityParam<Role>() {

			public Role fetchObject() {
				Role role = roleDao.getRole(intId);
				role = rolePermService.wrapRoleWithPerm(role);
				return role;
			}

			public String fetchKey() {
				return fetchCacheEntityKey() + intId;
			}
		});
	}

	/**
	 * 根据角色ID清除相应的缓存
	 * 
	 * @param roleId
	 */
	private void removeRolePermFromCache(String[] roleId) {
		for (int i = 0; i < roleId.length; i++) {
			rolePermService.removeRolePermsFromCacheByRoleId(roleId[i]);
			removeEntityFromCache(String.valueOf(roleId[i]));
		}
	}

	// -------------------缓存信息 end--------------------------

	public void initUnitRoles(String unitId, int unitClass) {
		// 取出默认初始角色
		List<Role> defaultRoleList = roleDao
				.getRoles(Unit.UNIT_CLASS_EDU == unitClass ? SystemConstant.DEFAULT_EDU_GUID
						: SystemConstant.DEFAULT_SCHOOL_GUID);
		List<Role> wantToSaveRoleList = new ArrayList<Role>();
		List<RolePerm> wantToSaveRolePermList = new ArrayList<RolePerm>();
		List<String> defaultRoleIds = new ArrayList<String>();
		Map<String, Role> relationMap = new HashMap<String, Role>();
		// 封装
		for (Iterator<Role> iter = defaultRoleList.iterator(); iter.hasNext();) {
			Role defaultRole = (Role) iter.next();
			defaultRoleIds.add(defaultRole.getId());
			Role newUnitRole = new Role();
			DtoAssembler.toEntity(defaultRole, newUnitRole);
			newUnitRole.setId(null);
			newUnitRole.setUnitid(unitId);
			wantToSaveRoleList.add(newUnitRole);
			relationMap.put(defaultRole.getId(), newUnitRole);
		}
		if (!wantToSaveRoleList.isEmpty()) {
			roleDao.saveRoles(wantToSaveRoleList);
			// 默认系统默认角色权限
			List<RolePerm> defaultRolePermList = rolePermDao
					.getRolePerms(defaultRoleIds.toArray(new String[0]));
			for (Iterator<RolePerm> iter = defaultRolePermList.iterator(); iter
					.hasNext();) {
				RolePerm defaultRolePerm = (RolePerm) iter.next();
				RolePerm newUnitRolePerm = new RolePerm();
				DtoAssembler.toEntity(defaultRolePerm,
						newUnitRolePerm);
				newUnitRolePerm.setRoleid(relationMap.get(
						defaultRolePerm.getRoleid()).getId());
				wantToSaveRolePermList.add(newUnitRolePerm);
			}
			if (!wantToSaveRolePermList.isEmpty()) {
				rolePermDao.saveRolePerms(wantToSaveRolePermList);
			}
		}
	}

	public void saveRole(Role role) {
		boolean isNewRecord = false;
		Set<String> selectedExtraSubSystemSet = new HashSet<String>();
		selectedExtraSubSystemSet.addAll(role.getExtraSubSystemSet());
		if (role.getId() != null && !role.getId().equals("") ) {
			role.getExtraSubSystemSet().clear();
		} else {
			isNewRecord = true;
		}
		
		Set<Long> selectedModSet = role.getSelectedModSet();
		Set<Long> selectedOperSet = role.getSelectedOperSet();
		
		Set<Integer> subSystemIdSet = moduleService.getSubSystemSet(
				selectedModSet, role.getUnitClass().intValue());
		role.setSubsystem(AssembleTool.getAssembledString(subSystemIdSet
				.toArray(), ","));
		
		if (!isNewRecord) {
			roleDao.updateRole(role);
		} else {
			roleDao.insertRole(role);
		}
		List<RolePerm> rolePermList = new LinkedList<RolePerm>();
		// 组装MODID 对应的 operation字符串
		Map<Long, Long> fromOperToModMap = modelOperatorService
				.getModelOperatorMap();
		Map<Long, StringBuilder> tempOperStrMap = new HashMap<Long, StringBuilder>();
		if (!selectedOperSet.isEmpty()) {
			for (Long operId : selectedOperSet) {
				Long modId = fromOperToModMap.get(operId);
				StringBuilder stringBuilder = tempOperStrMap.get(modId);
				if (stringBuilder != null) {
					if (stringBuilder.length() != 0) {
						stringBuilder.append("," + operId.intValue());
					}
				} else {
					stringBuilder = new StringBuilder();
					stringBuilder.append(operId.intValue());
					tempOperStrMap.put(modId, stringBuilder);
				}
			}
		}
		String roleId = role.getId();
		List<RolePerm> oldRolePermList = rolePermDao.getRolePerms(new String[] {roleId});
		List<String> deleteIds = new ArrayList<String>(); 
		List<RolePerm> updateRolePerm = new ArrayList<RolePerm>();
		if(CollectionUtils.isNotEmpty(oldRolePermList)){
			for(RolePerm rolePerm: oldRolePermList){
				if(selectedModSet.contains(rolePerm.getModuleid())){
					selectedModSet.remove(rolePerm.getModuleid());
					StringBuilder operId = tempOperStrMap.get(rolePerm.getModuleid());
					if(operId == null){
						rolePerm.setOperids("");
					} else if(!StringUtils.equals(operId.toString(), rolePerm.getOperids())){
						rolePerm.setOperids(operId.toString());
					}
					updateRolePerm.add(rolePerm);
				}else{
					deleteIds.add(rolePerm.getId());
				}
			}
		}
		
		// 保存角色权限关系数据
		for (Long moduleId : selectedModSet) {
			RolePerm rolePerm = new RolePerm();
			rolePerm.setRoleid(roleId);
			rolePerm.setModuleid(moduleId);
			StringBuilder operSb = tempOperStrMap.get(moduleId);
			if (operSb != null) {
				rolePerm.setOperids(operSb.toString());
			}
			rolePermList.add(rolePerm);
		}
		if(!updateRolePerm.isEmpty()){
			rolePermDao.updateRolePerms(updateRolePerm);
		}
		if(!deleteIds.isEmpty()){
			rolePermDao.deleteRolePermsByIds(deleteIds.toArray(new String[0]));
		}
		if (!rolePermList.isEmpty()) {
			rolePermDao.saveRolePerms(rolePermList);
		}

		// 保存角色附属子系统权限关系数据,附属子系统部分去掉

		if (!isNewRecord) {
			removeRolePermFromCache(new String[] { role.getId() });
			
			//在对用户进行角色授权和角色权限调整时，将用户和子系统的权限写入base_server_authorize
			Map<String, String> roleSystemMap=new HashMap<String, String>();
			String[] userIds =getRoleSystemMapAndUserids(new String[]{roleId}, roleSystemMap);
			userRoleRelationService.reInsertServerAuthorize(userIds, roleSystemMap, null);
		}
	}

	public void updateRoles(String ids, boolean activateStatus) {
//		Set<Long> roidsSet=new HashSet<Long>();
//		if(StringUtils.isNotBlank(ids)){
//			String[] roleIdsStr=ids.split(","); 
//			for (String roid : roleIdsStr) {
//				roidsSet.add(Long.parseLong(roid));
//			}
//		}
		String[] roleIdsStr = ids.split(",");
		roleDao.updateActive(SQLUtils.toSQLInString(roleIdsStr), activateStatus);
		Map<String, String> roleSystemMap=new HashMap<String, String>();
		String[] userIds =getRoleSystemMapAndUserids(roleIdsStr, roleSystemMap);
		if (!activateStatus) {
			String[] idsStringArray = ids.split(",");
//			Long[] idArray = new Long[idsStringArray.length];
//			for (int i = 0; i < idArray.length; i++) {
//				idArray[i] = new Long(idsStringArray[i]);
//			}
			removeRolePermFromCache(idsStringArray);
		}
		userRoleRelationService.reInsertServerAuthorize(userIds, roleSystemMap, null);
	}

	public void deleteRoles(String[] roleIds) {
		String idsString = SQLUtils.toSQLInString(roleIds);
		Map<String, String> roleSystemMap=new HashMap<String, String>();
		String[] userIds =getRoleSystemMapAndUserids(roleIds, roleSystemMap);
		roleDao.deleteRole(roleIds);
		rolePermDao.deleteRolePerm(roleIds);
		userRoleRelationService.deleteUserRole(idsString);
		userRoleRelationService.reInsertServerAuthorize(userIds, roleSystemMap, null);
		removeRolePermFromCache(roleIds);
	}
	private String[] getRoleSystemMapAndUserids (String[] roleIds,Map<String, String> roleSystemMap){
		List<UserRoleRelation> userRoleList1=userRoleRelationDao.getUserRolesByRoleIds(roleIds);
		Set<String> useridsSet=new HashSet<String>();
		for (UserRoleRelation userRoleRelation2 : userRoleList1) {
			useridsSet.add(userRoleRelation2.getUserid());
		}
		
		String[] userIds=useridsSet.toArray(new String[]{});
		//根据用户找出相应的所有角色
		List<UserRoleRelation> userRoleList2=userRoleRelationDao.getUserRolesByUserIds(userIds);
		Set<String> roleidSet=new HashSet<String>();
		for (UserRoleRelation userRoleRelation : userRoleList2) {
			roleidSet.add(userRoleRelation.getRoleid());
		}
		List<Role> roleList=roleDao.getRoles(roleidSet.toArray(new String[]{}));
		// 得到所有角色对应的子系统关系
		for (Role role2 : roleList) {
			if(role2.getIsactive())
			   roleSystemMap.put(role2.getId(), role2.getSubsystem());
		}
		return userIds;
	}
	public Role getRole(String unitId, String identifier) {
		return roleDao.getRole(unitId, identifier);
	}

	public Role getRoleByName(String unitId, String roleName) {
		return roleDao.getRoleByName(unitId, roleName);
	}

	public Role getRoleFullInfo(String roleId, int unitClass) {
		Role role = roleDao.getRole(roleId);
		List<RolePerm> tempList = rolePermService.getCachedRolePerm(roleId);
		Set<Long> selectedModSet = new HashSet<Long>();
		Set<Long> selectedOperSet = new HashSet<Long>();
		Set<String> selectedExtraSubSystemSet = new HashSet<String>();
		for (RolePerm rolePerm : tempList) {
			selectedModSet.add(rolePerm.getModuleid());
			String opers = rolePerm.getOperids();
			if (!StringUtils.isEmpty(opers)) {
				String[] operArray = opers.split(",");
				for (int i = 0; i < operArray.length; i++) {
					selectedOperSet.add(new Long(operArray[i]));
				}
			}
		}
		// 获取附属子系统权限关系，此部分去掉

		role.setSelectedModSet(selectedModSet);
		role.setSelectedOperSet(selectedOperSet);
		role.setExtraSubSystemSet(selectedExtraSubSystemSet);
		return role;
	}

	public List<Role> getRoleListByUserId(String userId) {
		return roleDao.getRolesByUserId(userId);
	}

	public List<Role> getRoles(String unitId) {
		return wrapRoles(roleDao.getRoles(unitId));
	}

	public List<Role> getRolesByNotThisType(String unitId, int notInRoleType) {
		return wrapRoles(roleDao.getRolesByNotThisType(unitId, notInRoleType));
	}

	private List<Role> wrapRoles(List<Role> dataList) {
		for (Role role : dataList) {
			switch (role.getRoletype()) {
			case Role.ROLE_TYPE_COMMON:
				role.setRoletypeString("普通");
				break;
			case Role.ROLE_TYPE_OPER:
				role.setRoletypeString("业务");
				break;
			case Role.ROLE_TYPE_SUBADMIN:
				role.setRoletypeString("管理");
				break;
			case Role.ROLE_TYPE_UNITADMIN:
				role.setRoletypeString("管理");
				break;
			default:
				break;
			}
			if (role.getIsactive()) {
				role.setIsactiveString("是");
			} else {
				role.setIsactiveString("否");
			}
			if (Role.ROLE_TYPE_COMMON == role.getRoletype()) {
				role.setRoletypeString("普通");
			}
		}
		return dataList;
	}

}
