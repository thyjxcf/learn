package net.zdsoft.office.teacherAttendance.dao.impl;


import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.dao.OfficeAttendanceGroupUserDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroupUser;
/**
 * 考勤组成员
 * @author 
 * 
 */
public class OfficeAttendanceGroupUserDaoImpl extends BaseDao<OfficeAttendanceGroupUser> implements OfficeAttendanceGroupUserDao{

	@Override
	public OfficeAttendanceGroupUser setField(ResultSet rs) throws SQLException{
		OfficeAttendanceGroupUser officeAttendanceGroupUser = new OfficeAttendanceGroupUser();
		officeAttendanceGroupUser.setId(rs.getString("id"));
		officeAttendanceGroupUser.setGroupId(rs.getString("group_id"));
		officeAttendanceGroupUser.setUserId(rs.getString("user_id"));
		return officeAttendanceGroupUser;
	}

	@Override
	public OfficeAttendanceGroupUser save(OfficeAttendanceGroupUser officeAttendanceGroupUser){
		String sql = "insert into office_attendance_group_user(id, group_id, user_id) values(?,?,?)";
		if (StringUtils.isBlank(officeAttendanceGroupUser.getId())){
			officeAttendanceGroupUser.setId(createId());
		}
		Object[] args = new Object[]{
			officeAttendanceGroupUser.getId(), officeAttendanceGroupUser.getGroupId(), 
			officeAttendanceGroupUser.getUserId()
		};
		update(sql, args);
		return officeAttendanceGroupUser;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_attendance_group_user where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeAttendanceGroupUser officeAttendanceGroupUser){
		String sql = "update office_attendance_group_user set group_id = ?, user_id = ? where id = ?";
		Object[] args = new Object[]{
			officeAttendanceGroupUser.getGroupId(), officeAttendanceGroupUser.getUserId(), 
			officeAttendanceGroupUser.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeAttendanceGroupUser getOfficeAttendanceGroupUserById(String id){
		String sql = "select * from office_attendance_group_user where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	@Override
	public OfficeAttendanceGroupUser getItemByUserId(String userId){
		String sql = "select * from office_attendance_group_user where user_id = ?";
		return query(sql, new Object[]{userId}, new SingleRow());
	}

	@Override
	public void batchSave(List<OfficeAttendanceGroupUser> groupUsers) {
		// TODO Auto-generated method stub
		String sql = "insert into office_attendance_group_user(id, group_id, user_id) values(?,?,?)";
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for(OfficeAttendanceGroupUser user:groupUsers){
			if(StringUtils.isEmpty(user.getId())){
				user.setId(createId());
			}
			Object[] args = new Object[]{
					user.getId(),user.getGroupId(),user.getUserId()
			};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[]{Types.CHAR, Types.CHAR,Types.CHAR};
		batchUpdate(sql,listOfArgs,argTypes);
	}

	@Override
	public Integer deleteByGroupId(String groupId) {
		// TODO Auto-generated method stub
		String sql = "delete from office_attendance_group_user where group_id = ?";
		return update(sql, groupId);
	}
	@Override
	public Map<String,String> getGroupUser(String[] userIds){
		String sql="select * from office_attendance_group_user where user_id in ";
		return queryForInSQL(sql, null, userIds,new MapRowMapper<String,String>() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("user_id");
			}

			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("group_id");
			}
		});
	}
	@Override
	public List<OfficeAttendanceGroupUser> getOfficeAttendanceGroupUser() {
		// TODO Auto-generated method stub
		String sql = "select * from office_attendance_group_user ";
		return query(sql, new MultiRow());
	}
	
	public Integer deleteByGroupIdAndUserId(String GroupId,String UserId){
		String sql = "delete from office_attendance_group_user where group_id= ? and user_id = ?";
		return update(sql,new Object[]{GroupId,UserId});
	}



}