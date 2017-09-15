package net.zdsoft.eis.system.data.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;

public interface BaseCustomRoleUserService extends CustomRoleUserService{
	public void saveCustomRoleUsers(String roleId, String... userIds);
	
	/**
	 * 现根据roleIds删除，再批量插入数据
	 * @param roleIds 可为空
	 * @param cruList
	 */
	public void saveCustomRoleUsers(String[] roleIds, List<CustomRoleUser> cruList);
	
	public void addCustomRoleUsers(List<CustomRoleUser> roleUserList);

	public void deleteCustomRoleUsers(String... ids);

	public void deleteCustomRoleUsers(String roleId);
	
	public void deleteCustomRoleUsersByRoleIds(String[] roleIds);
}
