package net.zdsoft.eis.system.data.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.system.data.entity.UserRoleRelation;

/**
 * @author luxingmu
 * @version $Revision: 1.8 $, $Date: 2006/12/22 07:05:05 $
 */
public interface UserRoleRelationService {

    /**
     * 根据角色ID删除用户角色关系
     * 
     * @param roleIds
     */
    public void deleteUserRole(String roleIds);

    /**
     * 根据用户ID删除用户角色关系
     * 
     * @param userIds
     */
    public void deleteUserRole(String[] userIds);

    /**
     * 从角色保存用户角色关系（需删除这些角色原先的用户角色关系）
     * 
     * @param userIds
     * @param roleIds
     * @param unitId 单位id
	 * @param deptId 部门id
     */
    public void saveUserRoles(String[] userIds, String[] roleIds, String unitId, String deptId);
    
    public void saveUserRoles(String[] userIds, String[] roleIds, String unitId);

    /**
     * 从用户保存用户角色关系（需删除这些用户原先的用户角色关系）
     * 
     * @param userIds
     * @param roleIds
     * @param unitId TODO
     */
    public void saveUserRolesFromUser(String[] userIds, String[] roleIds, String unitId);

    /**
     * 设置默认角色
     * 
     * @param userId
     * @param roleId
     */
    public void saveDefaultUserRole(String userId, String roleId);
	/**
	 * 在对用户进行角色授权和角色权限调整时，将用户和子系统的权限写入base_server_authorize
	 */
    public void reInsertServerAuthorize(String[] userIds, Map<String, String> roleSystemMap ,
			String[] noUserIds) ;
		
    /**
     * 根据角色ID获取用户权限关系
     * 
     * @param roleId
     * @return
     */
    public List<UserRoleRelation> getUserRoles(String roleId);

    /**
     * 根据useid获取所有的权限关系（忽略锁定角色的权限关系）
     * 
     * @param userId
     * @return
     */
    public List<UserRoleRelation> getUserRoles(String[] userId);

    /**
     * 查找用户的默认角色
     * 
     * @param userId
     * @return
     */
    public String getDefaultRoleId(String userId);

    /**
     * 判断用户是否拥有此模块MID的操作权限
     * 
     * @param userId
     * @param moduleMid
     * @return
     * @deprecated
     */
    public boolean checkUserModule(String userId, String moduleMid);

    /**
     * 判断某用户是否拥有某模块的某个操作的权限
     * 
     * @param userId
     * @param moduleId
     * @param oper
     * @return
     */
    public boolean checkUserModuleAndOperation(String userId, String moduleId, String oper);

    /**
     * 获取所有用户角色，包括锁定状态的角色
     * @param userIds
     * @return
     */
    public List<UserRoleRelation> getAllUserRoleRelaction(String[] userIds);

	/**
	 * @param userIds
	 * @param moduleId
	 * @return
	 */
	public Set<String> checkUserModule(String[] userIds, String moduleId);
}
