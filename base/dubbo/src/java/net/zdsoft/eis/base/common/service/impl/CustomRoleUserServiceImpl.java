package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.CustomRoleUserDao;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.base.common.service.UserService;

import org.apache.commons.collections.CollectionUtils;

public class CustomRoleUserServiceImpl implements CustomRoleUserService {

	private CustomRoleUserDao customRoleUserDao;

	private UserService userService;

	public void setCustomRoleUserDao(CustomRoleUserDao customRoleUserDao) {
		this.customRoleUserDao = customRoleUserDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public List<CustomRoleUser> getCustomRoleUserList(String... roleIds) {
		return customRoleUserDao.getCustomRoleUserList(roleIds);
	}

	@Override
	public Map<String, List<CustomRoleUser>> getCustomRoleUserMap(
			String unitId, String... roleIds) {
		List<CustomRoleUser> userList = getCustomRoleUserList(roleIds);
		Map<String, List<CustomRoleUser>> roleUserMap = new HashMap<String, List<CustomRoleUser>>();
		Map<String, User> userMap = userService.getUserMap(unitId);
		for (CustomRoleUser roleUser : userList) {
			List<CustomRoleUser> tempUserList = roleUserMap.get(roleUser
					.getRoleId());
			if (CollectionUtils.isEmpty(tempUserList)) {
				tempUserList = new ArrayList<CustomRoleUser>();
			}
			User user = userMap.get(roleUser.getUserId());
			if (user != null) {
				roleUser.setUserName(user.getRealname());
			}else{
				continue;
			}
			tempUserList.add(roleUser);
			roleUserMap.put(roleUser.getRoleId(), tempUserList);
		}
		return roleUserMap;
	}

	@Override
	public List<CustomRoleUser> getCustomRoleUserListByUserId(String userId) {
		return customRoleUserDao.getCustomRoleUserListByUserId(userId);
	}

}
