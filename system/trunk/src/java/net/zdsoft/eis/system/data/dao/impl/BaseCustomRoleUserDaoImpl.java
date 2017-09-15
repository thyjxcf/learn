package net.zdsoft.eis.system.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.system.data.dao.BaseCustomRoleUserDao;

import org.apache.commons.lang.StringUtils;

public class BaseCustomRoleUserDaoImpl extends BaseDao<CustomRoleUser>
		implements BaseCustomRoleUserDao {

	public static final String SQL_INSERT_CUSTOM_ROLE_USER = "insert into sys_custom_role_user(id,role_id,user_id) values(?,?,?)";

	public static final String SQL_UPDATE_CUSTOM_ROLE_USER = "update sys_custom_role_user set role_id=?, user_id=? where id=?";

	public static final String SQL_DELETE_CUSTOM_ROLE_USER = "delete from sys_custom_role_user where id IN";

	public static final String SQL_DELETE_CUSTOM_ROLE_USER_BY_ROLEID = "delete from sys_custom_role_user where role_id =?";

	public static final String SQL_DELETE_CUSTOM_ROLE_USER_ROLEID = "delete from sys_custom_role_user where role_id IN";

	@Override
	public CustomRoleUser setField(ResultSet rs) throws SQLException {
		CustomRoleUser user = new CustomRoleUser();
		user.setId(rs.getString("id"));
		user.setRoleId(rs.getString("role_id"));
		user.setUserId(rs.getString("user_id"));
		return user;
	}

	@Override
	public void addCustomRoleUsers(List<CustomRoleUser> roleUserList) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (int i = 0; i < roleUserList.size(); i++) {
			CustomRoleUser u = roleUserList.get(i);
			if (StringUtils.isBlank(u.getId()))
				u.setId(getGUID());

			Object[] objs = new Object[] { u.getId(), u.getRoleId(),
					u.getUserId() };
			listOfArgs.add(objs);
		}
		int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.CHAR };
		batchUpdate(SQL_INSERT_CUSTOM_ROLE_USER, listOfArgs, argTypes);
	}

	@Override
	public void deleteCustomRoleUsers(String... ids) {
		updateForInSQL(SQL_DELETE_CUSTOM_ROLE_USER, new Object[] {}, ids);
	}

	@Override
	public void deleteCustomRoleUsers(String roleId) {
		update(SQL_DELETE_CUSTOM_ROLE_USER_BY_ROLEID, roleId);
	}

	@Override
	public void deleteCustomRoleUsersByRoleIds(String[] roleIds) {
		updateForInSQL(SQL_DELETE_CUSTOM_ROLE_USER_ROLEID, null, roleIds);
	}

}
