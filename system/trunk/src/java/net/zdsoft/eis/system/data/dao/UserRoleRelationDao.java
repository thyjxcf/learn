package net.zdsoft.eis.system.data.dao;

import java.util.List;

import net.zdsoft.eis.system.data.entity.UserRoleRelation;

/**
 * @author luxingmu
 * @version $Revision: 1.6 $, $Date: 2007/01/09 10:04:35 $
 */
public interface UserRoleRelationDao {

    /**
     * 根据角色ID删除用户角色关系
     * 
     * @param roleIds
     * @param userIds
     * 
     */
    public void deleteUserRole(String roleIds, String[] userIds);

    /**
     * 根据用户ID删除用户角色关系
     * 
     * @param userIds
     */
    public void deleteUserRole(String[] userIds);
    /**
     * 设置默认角色
     * 
     * @param userId
     * @param roleId
     */
    public void saveDefaultUserRole(String userId, String roleId);

    /**
     * 批量增加
     * 
     * @param userRoles
     */
    public void saveUserRoles(List<UserRoleRelation> userRoles);

    /**
     * 查找用户的默认角色
     * 
     * @param userId
     * @return
     */
    public String getDefaultRoleId(String userId);

    /**
     * 根据角色ID获取用户权限关系
     * 
     * @param roleId
     * @return
     */
    public List<UserRoleRelation> getUserRolesByRoleId(String roleId);
    
    /**
     * 根据角色ID和角色所属的部门ID获取用户权限关系
     * 
     * @param roleId
     * @return
     */
    public List<UserRoleRelation> getUserRolesByRoleIdAndDeptId(String roleId, String deptId);
    
    /**
     * 根据角色ID获取用户权限关系
     * 
     * @param roleId
     * @return
     */
    public List<UserRoleRelation> getUserRolesByRoleIds(String[] roleIds);

    /**
     * 根据useid获取所有的权限关系（忽略锁定角色的权限关系）
     * 
     * @param userid
     * @return
     */
    public List<UserRoleRelation> getUserRolesByUserIds(String[] userIds);
    /**
     * 根据userId获取所有权限关系
     * @param userId
     * @return
     */
    public List<UserRoleRelation> getUserRolesByUserId(String userId);
    /**
     * 根据Id删除权限关系
     * @param ids
     */
    public void deleteUserRoles(String[] ids);
    /**
     * 获取所有角色，包括锁定状态的
     * @param userIds
     * @return
     */
    public List<UserRoleRelation> getAllUserRoleRelaction(String[] userIds);
}
