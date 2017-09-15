package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.CustomRoleUser;

public interface CustomRoleUserDao {

	public List<CustomRoleUser> getCustomRoleUserList(String... roleIds);

	public List<CustomRoleUser> getCustomRoleUserListByUserId(String userId);
}
