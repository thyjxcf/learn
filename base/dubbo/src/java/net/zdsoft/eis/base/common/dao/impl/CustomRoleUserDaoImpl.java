package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.common.dao.CustomRoleUserDao;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.frame.client.BaseDao;

public class CustomRoleUserDaoImpl extends BaseDao<CustomRoleUser> implements CustomRoleUserDao{

	private static final String SQL_FIND_CUSTOM_ROLE_USER_BY_ROLEIDS ="select * from sys_custom_role_user where role_id IN";
	
	private static final String SQL_FIND_CUSTOM_ROLE_USER_BY_USERID ="select * from sys_custom_role_user where user_id =?";
	
	@Override
	public CustomRoleUser setField(ResultSet rs) throws SQLException {
		CustomRoleUser user=new CustomRoleUser();
		user.setId(rs.getString("id"));
		user.setRoleId(rs.getString("role_id"));
		user.setUserId(rs.getString("user_id"));
		return user;
	}

	@Override
	public List<CustomRoleUser> getCustomRoleUserList(String... roleIds) {
		return queryForInSQL(SQL_FIND_CUSTOM_ROLE_USER_BY_ROLEIDS, new Object[]{}, roleIds,new MultiRow());
	}
	
	@Override
	public List<CustomRoleUser> getCustomRoleUserListByUserId(String userId) {
		return query(SQL_FIND_CUSTOM_ROLE_USER_BY_USERID, new Object[]{userId},new MultiRow());
	}
	
	
}
