/* 
 * @author zhangza
 * @since 1.0
 * @version $Id$
 */
package net.zdsoft.eis.system.data.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Role;

public interface RoleDao {
    // ===================维护====================
    /**
     * 增加
     * 
     * @param role
     */
    public void insertRole(Role role);

    /**
     * 更新
     * 
     * @param role
     */
    public void updateRole(Role role);

    /**
     * 根据ID删除角色
     * 
     * @param roleIds
     */
    public void deleteRole(String[] roleIds);

    /**
     * 更新角色的状态 true 激活 false 锁定
     * 
     * @param roleIds
     * @param activateStatus
     */
    public void updateActive(String roleIds, boolean activateStatus);

    /**
     * 批量增加
     * 
     * @param roles
     */
    public void saveRoles(List<Role> roles);

    // ===================查询单个对象====================
    /**
     * 根据id获得角色
     * 
     * @param roleId
     * @return
     */
    public Role getRole(String roleId);

    /**
     * 根据单位和角色标识得到一个role
     * 
     * @param unitId
     * @param identifier
     * @return
     */
    public Role getRole(String unitId, String identifier);

    /**
     * 根据单位和角色名称得到一个role
     * 
     * @param unitId
     * @param roleName
     * @return
     */
    public Role getRoleByName(String unitId, String roleName);

    // ===================查询多个对象====================
    /**
     * 根据ids获得
     * 
     * @param roleIds
     * @return
     */
    public List<Role> getRoles(String[] roleIds);

    /**
     * 根据useid获取该用户关联的角色
     * 
     * @param userid
     * @return
     */
    public List<Role> getRolesByUserId(String userId);

    // ===================按unitId查询多个对象====================
    /**
     * 取得一个单位的role列表
     * 
     * @param unitId
     * @return
     */
    public List<Role> getRoles(String unitId);

    /**
     * 根据单位unitId 获取不属于该角色类型的角色数据
     * 
     * @param unitId
     * @param notInRoleType
     * @return
     */
    public List<Role> getRolesByNotThisType(String unitId, int notInRoleType);

}
