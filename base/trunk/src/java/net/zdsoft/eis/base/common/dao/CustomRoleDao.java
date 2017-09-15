package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.CustomRole;

public interface CustomRoleDao {

	public void addCustomRoles(List<CustomRole> roleList);

	public List<CustomRole> getCustomRoleList(String unitId);
	
	public List<CustomRole> getCustomRoleList(String[] unitIds);
	
	public void deleteCustomRoles(String unitId);

	public CustomRole getCustomRoleByCode(String unitId, String roleCode);
	
	public CustomRole getCustomRoleById(String id);

	public List<CustomRole> getCustomRoleList(String[] unitIds,
			String roleCode);

}
