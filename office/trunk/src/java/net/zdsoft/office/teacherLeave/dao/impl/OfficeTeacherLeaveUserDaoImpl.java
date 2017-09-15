package net.zdsoft.office.teacherLeave.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherLeave.dao.OfficeTeacherLeaveUserDao;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeaveUser;

import org.apache.commons.lang.StringUtils;
/**
 * office_teacher_leave_user
 * @author 
 * 
 */
public class OfficeTeacherLeaveUserDaoImpl extends BaseDao<OfficeTeacherLeaveUser> implements OfficeTeacherLeaveUserDao{

	private static final String SQL_INSERT = "insert into office_teacher_leave_user(id, leave_id, user_id) values(?,?,?)";
	
	@Override
	public OfficeTeacherLeaveUser setField(ResultSet rs) throws SQLException{
		OfficeTeacherLeaveUser officeTeacherLeaveUser = new OfficeTeacherLeaveUser();
		officeTeacherLeaveUser.setId(rs.getString("id"));
		officeTeacherLeaveUser.setLeaveId(rs.getString("leave_id"));
		officeTeacherLeaveUser.setUserId(rs.getString("user_id"));
		return officeTeacherLeaveUser;
	}

	@Override
	public OfficeTeacherLeaveUser save(OfficeTeacherLeaveUser officeTeacherLeaveUser){
		if (StringUtils.isBlank(officeTeacherLeaveUser.getId())){
			officeTeacherLeaveUser.setId(createId());
		}
		Object[] args = new Object[]{
			officeTeacherLeaveUser.getId(), officeTeacherLeaveUser.getLeaveId(), 
			officeTeacherLeaveUser.getUserId()
		};
		update(SQL_INSERT, args);
		return officeTeacherLeaveUser;
	}
	
	@Override
	public void batchSave(List<OfficeTeacherLeaveUser> officeTeacherLeaveUsers) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
	    for (int i = 0; i < officeTeacherLeaveUsers.size(); i++) {
	    	OfficeTeacherLeaveUser officeTeacherLeaveUser = officeTeacherLeaveUsers.get(i);
	    	if (StringUtils.isBlank(officeTeacherLeaveUser.getId())){
	    		officeTeacherLeaveUser.setId(createId());
			}
	        listOfArgs.add(new Object[] {
	        	officeTeacherLeaveUser.getId(), officeTeacherLeaveUser.getLeaveId(), 
	        	officeTeacherLeaveUser.getUserId()
	        });
	    }
	    int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.CHAR };
	    batchUpdate(SQL_INSERT, listOfArgs, argTypes);
	}

	@Override
	public Integer deleteByLeaveIds(String[] ids){
		String sql = "delete from office_teacher_leave_user where leave_id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public void deleteByLeaveId(String leaveId) {
		String sql = "delete from office_teacher_leave_user where leave_id = ? ";
		update(sql, leaveId);
	}

	@Override
	public Integer update(OfficeTeacherLeaveUser officeTeacherLeaveUser){
		String sql = "update office_teacher_leave_user set leave_id = ?, user_id = ? where id = ?";
		Object[] args = new Object[]{
			officeTeacherLeaveUser.getLeaveId(), officeTeacherLeaveUser.getUserId(), 
			officeTeacherLeaveUser.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeTeacherLeaveUser getOfficeTeacherLeaveUserById(String id){
		String sql = "select * from office_teacher_leave_user where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeTeacherLeaveUser> getOfficeTeacherLeaveUserMapByIds(String[] ids){
		String sql = "select * from office_teacher_leave_user where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeTeacherLeaveUser> getOfficeTeacherLeaveUserList(String leaveId){
		String sql = "select * from office_teacher_leave_user where leave_id = ? ";
		return query(sql, new Object[]{leaveId} ,new MultiRow());
	}

	@Override
	public List<OfficeTeacherLeaveUser> getOfficeTeacherLeaveUserPage(Pagination page){
		String sql = "select * from office_teacher_leave_user";
		return query(sql, new MultiRow(), page);
	}
}