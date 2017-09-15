package net.zdsoft.office.teacherAttendance.dao.impl;


import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.dao.OfficeAttendanceExcludeUserDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceExcludeUser;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroupUser;
/**
 * 不参与考勤统计人员信息
 * @author 
 * 
 */
public class OfficeAttendanceExcludeUserDaoImpl extends BaseDao<OfficeAttendanceExcludeUser> implements OfficeAttendanceExcludeUserDao{

	@Override
	public OfficeAttendanceExcludeUser setField(ResultSet rs) throws SQLException{
		OfficeAttendanceExcludeUser officeAttendanceExcludeUser = new OfficeAttendanceExcludeUser();
		officeAttendanceExcludeUser.setId(rs.getString("id"));
		officeAttendanceExcludeUser.setUnitId(rs.getString("unit_id"));
		officeAttendanceExcludeUser.setUserId(rs.getString("user_id"));
		return officeAttendanceExcludeUser;
	}

	@Override
	public OfficeAttendanceExcludeUser save(OfficeAttendanceExcludeUser officeAttendanceExcludeUser){
		String sql = "insert into office_attendance_exclude_user(id, unit_id, user_id) values(?,?,?)";
		if (StringUtils.isBlank(officeAttendanceExcludeUser.getId())){
			officeAttendanceExcludeUser.setId(createId());
		}
		Object[] args = new Object[]{
			officeAttendanceExcludeUser.getId(), officeAttendanceExcludeUser.getUnitId(), 
			officeAttendanceExcludeUser.getUserId()
		};
		update(sql, args);
		return officeAttendanceExcludeUser;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_attendance_exclude_user where id in";
		return updateForInSQL(sql, null, ids);
	}
	@Override
	public Integer deleteByUserId(String[] ids) {
		// TODO Auto-generated method stub
		String sql = "delete from office_attendance_exclude_user where user_id in";
		return updateForInSQL(sql, null, ids);
	}
	@Override
	public Integer update(OfficeAttendanceExcludeUser officeAttendanceExcludeUser){
		String sql = "update office_attendance_exclude_user set unit_id = ?, user_id = ? where id = ?";
		Object[] args = new Object[]{
			officeAttendanceExcludeUser.getUnitId(), officeAttendanceExcludeUser.getUserId(), 
			officeAttendanceExcludeUser.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeAttendanceExcludeUser getOfficeAttendanceExcludeUserById(String id){
		String sql = "select * from office_attendance_exclude_user where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public List<OfficeAttendanceExcludeUser> getOfficeAttendanceExcludeUserByUnitId(
			String unitId) {
		// TODO Auto-generated method stub
		String sql = "select * from office_attendance_exclude_user where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public void batchSave(List<OfficeAttendanceExcludeUser> users) {
		String sql = "insert into office_attendance_exclude_user(id, unit_id, user_id) values(?,?,?)";
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for(OfficeAttendanceExcludeUser user:users){
			if(StringUtils.isEmpty(user.getId())){
				user.setId(createId());
			}
			Object[] args = new Object[]{
					user.getId(),user.getUnitId(),user.getUserId()
			};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[]{Types.CHAR, Types.CHAR,Types.CHAR};
		batchUpdate(sql,listOfArgs,argTypes);
	}

	@Override
	public Integer deleteByUnitId(String unitId) {
		// TODO Auto-generated method stub
		String sql = "delete from office_attendance_exclude_user where unit_id = ? ";
		return update(sql, new Object[]{unitId});
	}

	
}