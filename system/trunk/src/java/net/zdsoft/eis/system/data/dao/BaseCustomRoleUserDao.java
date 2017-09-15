package net.zdsoft.eis.system.data.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.CustomRoleUser;

public interface BaseCustomRoleUserDao {
	
	public void addCustomRoleUsers(List<CustomRoleUser> roleUserList);

	public void deleteCustomRoleUsers(String... ids);

	public void deleteCustomRoleUsers(String roleId);

	public void deleteCustomRoleUsersByRoleIds(String[] roleIds);
}
