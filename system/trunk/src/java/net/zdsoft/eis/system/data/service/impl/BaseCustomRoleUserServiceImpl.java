package net.zdsoft.eis.system.data.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.service.impl.CustomRoleUserServiceImpl;
import net.zdsoft.eis.system.data.dao.BaseCustomRoleUserDao;
import net.zdsoft.eis.system.data.service.BaseCustomRoleUserService;

import org.apache.commons.collections.CollectionUtils;

public class BaseCustomRoleUserServiceImpl extends CustomRoleUserServiceImpl
		implements BaseCustomRoleUserService {

	private BaseCustomRoleUserDao baseCustomRoleUserDao;


	public void setBaseCustomRoleUserDao(BaseCustomRoleUserDao baseCustomRoleUserDao) {
		this.baseCustomRoleUserDao = baseCustomRoleUserDao;
	}

	@Override
	public void addCustomRoleUsers(List<CustomRoleUser> roleUserList) {
		baseCustomRoleUserDao.addCustomRoleUsers(roleUserList);
	}

	@Override
	public void deleteCustomRoleUsers(String... ids) {
		baseCustomRoleUserDao.deleteCustomRoleUsers(ids);
	}

	@Override
	public void deleteCustomRoleUsers(String roleId) {
		baseCustomRoleUserDao.deleteCustomRoleUsers(roleId);
	}

	@Override
	public void saveCustomRoleUsers(String roleId, String... userIds) {
		if (userIds != null) {
			List<String> userIdList = Arrays.asList(userIds);
			List<String> deleteIds = new ArrayList<String>();
			List<CustomRoleUser> list = getCustomRoleUserList(roleId);
			List<String> oldUserIds = new ArrayList<String>();
			for (int j = 0; j < list.size(); j++) {
				if (!userIdList.contains(list.get(j).getUserId())) {
					deleteIds.add(list.get(j).getId());
				} else {
					oldUserIds.add(list.get(j).getUserId());
				}
			}
			if (CollectionUtils.isNotEmpty(deleteIds)) {
				deleteCustomRoleUsers(deleteIds.toArray(new String[0]));
			}

			List<CustomRoleUser> resultList = new LinkedList<CustomRoleUser>();
			for (int j = 0; j < userIds.length; j++) {
				if (oldUserIds.contains(userIds[j]))
					continue;
				CustomRoleUser roleUser = new CustomRoleUser();
				roleUser.setUserId(userIds[j]);
				roleUser.setRoleId(roleId);
				resultList.add(roleUser);
			}
			if (!resultList.isEmpty()) {
				addCustomRoleUsers(resultList);
			}
		} else {
			deleteCustomRoleUsers(roleId);
		}
	}

	@Override
	public void deleteCustomRoleUsersByRoleIds(String[] roleIds) {
		baseCustomRoleUserDao.deleteCustomRoleUsersByRoleIds(roleIds);
	}

	@Override
	public void saveCustomRoleUsers(String[] roleIds, List<CustomRoleUser> cruList) {
		if(roleIds!=null && roleIds.length>0){
			deleteCustomRoleUsersByRoleIds(roleIds);
		}
		addCustomRoleUsers(cruList);
	}
}
