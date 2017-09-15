package net.zdsoft.eis.system.data.dao;

import java.util.List;

import net.zdsoft.eis.system.data.entity.RolePerm;

/**
 * @author luxingmu
 * @version $Revision: 1.6 $, $Date: 2007/01/09 10:04:35 $
 */
public interface RolePermDao {

    /**
     * 批量增加
     * 
     * @param rolePerms
     */
    public void saveRolePerms(List<RolePerm> rolePerms);

    /**
     * 根据角色ID删除角色权限关系
     * 
     * @param roleIds
     */
    public void deleteRolePerm(String... roleIds);

    /**
     * 根据id获得 角色权限
     * 
     * @param rolePermId
     * @return
     */
    public RolePerm getRolePerm(String rolePermId);

    /**
     * 根据角色IDs获取角色权限关系
     * 
     * @param roleIds
     * @return
     */
    public List<RolePerm> getRolePerms(String... roleIds);
    /**
     * 根据Id删除
     * @param Ids
     */
    public void deleteRolePermsByIds(String... Ids);
    /**
     * 更新
     * @param rolePerms
     */
    public void updateRolePerms(List<RolePerm> rolePerms);

	/**
	 * 
	 * @param roleIds
	 * @param moduleId
	 * @return
	 */
	public List<String> getRolePerm(String[] roleIds, String moduleId);
}
