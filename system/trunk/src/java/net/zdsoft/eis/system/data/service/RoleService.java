package net.zdsoft.eis.system.data.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Role;

/**
 * @author luxingmu
 * @version $Revision: 1.9 $, $Date: 2007/01/12 06:42:00 $
 */
public interface RoleService {    
    // ===================维护====================
    /**
     * 角色保存
     * 
     * @param role
     */
    public void saveRole(Role role);

    /**
     * 更新角色的状态 true 激活 false 锁定
     * 
     * @param ids
     * @param activateStatus
     */
    public void updateRoles(String ids, boolean activateStatus);

    /**
     * 删除角色
     * 
     * @param roleIds
     */
    public void deleteRoles(String[] roleIds);

    /**
     * 初始化单位默认角色
     * 
     * @param unitId
     * @param unitClass
     */
    public void initUnitRoles(String unitId, int unitClass);

    // ===================查询单个对象====================
    /**
     * 根据id得到一个role
     * 
     * @param id
     * @return
     */
    public Role getCachedPermRole(String id);

    /**
     * 根据id得到一个role，包括其权限
     * 
     * @param roleId
     * @param unitClass
     * @return
     */
    public Role getRoleFullInfo(String roleId, int unitClass);

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
     * 根据useid获取该用户关联的角色
     * 
     * @param userid
     * @return
     */
    public List<Role> getRoleListByUserId(String userId);

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
