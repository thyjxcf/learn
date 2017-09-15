package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.CustomRoleUser;

public interface CustomRoleUserService {

	public List<CustomRoleUser> getCustomRoleUserList(String... roleIds);
	
	public List<CustomRoleUser> getCustomRoleUserListByUserId(String userId);
	
	public Map<String,List<CustomRoleUser>> getCustomRoleUserMap(String unitId,String... roleIds);
}
