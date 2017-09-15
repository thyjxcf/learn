package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.leadin.cache.CacheManager;

public interface CustomRoleService extends CacheManager {

	public void deleteCustomRoles(String unitId);

	public List<CustomRole> getCustomRoleList(String unitId);

	public List<CustomRole> getCustomRoleList(String unitId, String subsystem);
	
	/**
	 * 根据单位找用户角色（自动初始化各单位内用户角色）
	 * 
	 * @param unitIds
	 * @param roleCode 可为空
	 * @return key unitId
	 */
	public Map<String,List<CustomRole>> getCustomRoleListMap(String[] unitIds, String roleCode);

	public List<CustomRole> getCustomRoleWithUserList(String unitId,
			String subsystem);

	/**
	 * 根据用户和所在的子系统获取用户的角色
	 * 
	 * @param unitId
	 * @param userId
	 * @param subsystemId
	 * @return
	 */
	public List<CustomRole> getCustomRoleListByUserId(String unitId,
			String userId, String subsystemId);

	/**
	 * 根据单位和角色code获取角色
	 * 
	 * @param unitId
	 * @param roleCode
	 * @return
	 */
	public CustomRole getCustomRoleByRoleCode(final String unitId,
			final String roleCode);

	/**
	 * 根据id获取角色
	 * 
	 * @param id
	 * @return
	 */
	public CustomRole getCustomRoleById(final String id);

	/**
	 * 判断是否是自定义角色
	 * @param unitId
	 * @param userId
	 * @param subsystem
	 * @param roleCode
	 * @return
	 */
	public boolean isCustomRole(String unitId,String userId, String subsystem, String roleCode);

	/**
	 * 根据单位找用户角色
	 * @param unitIds
	 * @param roleCode
	 * @return key unitId
	 */
	public Map<String, CustomRole> getCustomRoleMap(String[] unitIds,
			String roleCode);
	
	/**
	 * 
	 * @param unitId
	 * @param roleCodes
	 * @return
	 */
	public Map<String,List<CustomRoleUser>> getCustomRoleWithUserList(String unitId, String[] roleCodes);


}
