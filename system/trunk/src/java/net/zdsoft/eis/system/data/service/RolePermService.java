package net.zdsoft.eis.system.data.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.system.data.entity.RolePerm;

/**
 * @author luxingmu
 * @version $Revision: 1.9 $, $Date: 2007/01/15 02:53:32 $
 */
public interface RolePermService {
    /**
     * 取权限信息
     * @param role
     * @return
     */
    public Role wrapRoleWithPerm(Role role);
    
    /**
     * 移除权限
     * @param roleId
     */
    public void removeRolePermsFromCacheByRoleId(String roleId);
    
    /**
     * 根据roleId获取相应的角色权限对应关系
     * 
     * @param roleId
     * @return
     */
    List<RolePerm> getCachedRolePerm(String roleId);

    /**
     * 获取登录用户权限角色信息(优先取缓存，没有重新组装)
     * 
     * @param roleid
     * @param unitClass
     * @return
     */
    public List<Role> getCacheRoleList(String[] roleid);

    /**
     * 生成管理员角色
     * 
     * @param unitId
     * @param unitClass
     * @param userType
     * @return
     */
    public List<Role> getAdminRole(String unitId, int unitClass, Integer userType);

    /**
     * 生成公共权限角色
     * 
     * @param unitClass
     * @return
     */
    public List<Role> getCommonRole(int unitClass);

	/**
	 * 获得角色是否有对应的权限
	 * @param roleIds
	 * @param moduleId
	 * @return
	 */
	public List<String> getRolePerm(String[] roleIds, String moduleId);

}
