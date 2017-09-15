package net.zdsoft.office.studentLeave.dao.impl;

import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.dao.OfficeStudentLeaveDao;
import net.zdsoft.office.studentLeave.entity.OfficeStudentLeave;
import net.zdsoft.office.util.Constants;
/**
 * office_student_leave
 * @author 
 * 
 */
public class OfficeStudentLeaveDaoImpl extends BaseDao<OfficeStudentLeave> implements OfficeStudentLeaveDao{

	@Override
	public OfficeStudentLeave setField(ResultSet rs) throws SQLException{
		OfficeStudentLeave officeStudentLeave = new OfficeStudentLeave();
		officeStudentLeave.setId(rs.getString("id"));
		officeStudentLeave.setStudentId(rs.getString("student_id"));
		officeStudentLeave.setStartTime(rs.getTimestamp("start_time"));
		officeStudentLeave.setEndTime(rs.getTimestamp("end_time"));
		officeStudentLeave.setDays(rs.getFloat("days"));
		officeStudentLeave.setLeaveTypeId(rs.getString("leave_type_id"));
		officeStudentLeave.setClassId(rs.getString("class_id"));
		officeStudentLeave.setAcadyear(rs.getString("acadyear"));
		officeStudentLeave.setSemester(rs.getInt("semester"));
		officeStudentLeave.setCreateUserId(rs.getString("create_user_id"));
		officeStudentLeave.setCreateTime(rs.getTimestamp("create_time"));
		officeStudentLeave.setRemark(rs.getString("remark"));
		officeStudentLeave.setAuditUserId(rs.getString("audit_user_id"));
		officeStudentLeave.setState(rs.getInt("state"));
		officeStudentLeave.setAuditTime(rs.getTimestamp("audit_time"));
		officeStudentLeave.setAuditRemark(rs.getString("audit_remark"));
		officeStudentLeave.setIsDeleted(rs.getInt("is_deleted"));
		officeStudentLeave.setUnitId(rs.getString("unit_id"));
		return officeStudentLeave;
	}

	@Override
	public OfficeStudentLeave save(OfficeStudentLeave officeStudentLeave){
		String sql = "insert into office_student_leave(id, student_id, start_time, end_time, days, leave_type_id, class_id, acadyear, semester, create_user_id, create_time, remark, audit_user_id, state, audit_time, audit_remark, is_deleted ,unit_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeStudentLeave.getId())){
			officeStudentLeave.setId(createId());
		}
		Object[] args = new Object[]{
			officeStudentLeave.getId(), officeStudentLeave.getStudentId(), 
			officeStudentLeave.getStartTime(), officeStudentLeave.getEndTime(), 
			officeStudentLeave.getDays(), officeStudentLeave.getLeaveTypeId(), 
			officeStudentLeave.getClassId(), officeStudentLeave.getAcadyear(), 
			officeStudentLeave.getSemester(), officeStudentLeave.getCreateUserId(), 
			officeStudentLeave.getCreateTime(), officeStudentLeave.getRemark(), 
			officeStudentLeave.getAuditUserId(), officeStudentLeave.getState(), 
			officeStudentLeave.getAuditTime(), officeStudentLeave.getAuditRemark(), 
			officeStudentLeave.getIsDeleted(),officeStudentLeave.getUnitId()
		};
		update(sql, args);
		return officeStudentLeave;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_student_leave where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeStudentLeave officeStudentLeave){
		String sql = "update office_student_leave set student_id = ?, start_time = ?, end_time = ?, days = ?, leave_type_id = ?, class_id = ?, acadyear = ?, semester = ?, create_user_id = ?, create_time = ?, remark = ?, audit_user_id = ?, state = ?, audit_time = ?, audit_remark = ?, is_deleted = ?, unit_id = ? where id = ?";
		Object[] args = new Object[]{
			officeStudentLeave.getStudentId(), officeStudentLeave.getStartTime(), 
			officeStudentLeave.getEndTime(), officeStudentLeave.getDays(), 
			officeStudentLeave.getLeaveTypeId(), officeStudentLeave.getClassId(), 
			officeStudentLeave.getAcadyear(), officeStudentLeave.getSemester(), 
			officeStudentLeave.getCreateUserId(), officeStudentLeave.getCreateTime(), 
			officeStudentLeave.getRemark(), officeStudentLeave.getAuditUserId(), 
			officeStudentLeave.getState(), officeStudentLeave.getAuditTime(), 
			officeStudentLeave.getAuditRemark(), officeStudentLeave.getIsDeleted(), 
			officeStudentLeave.getUnitId(),
			officeStudentLeave.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public void batchUpdateBackState(
			List<OfficeStudentLeave> officeStudentLeaves) {
		String sql = "update office_student_leave set back_state = ? where id = ?";
		List<Object[]> objs = new ArrayList<Object[]>();
		for(OfficeStudentLeave officeStudentLeave:officeStudentLeaves){
			Object[] args = new Object[]{
					officeStudentLeave.getBackState(),officeStudentLeave.getId()
			};
			objs.add(args);
		}
		batchUpdate(sql, objs, new int[]{Types.INTEGER,Types.CHAR});
	}

	@Override
	public OfficeStudentLeave getOfficeStudentLeaveById(String id){
		String sql = "select * from office_student_leave where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeStudentLeave> getOfficeStudentLeaveMapByIds(String[] ids){
		String sql = "select * from office_student_leave where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeStudentLeave> getOfficeStudentLeaveList(){
		String sql = "select * from office_student_leave where is_deleted = 0 and state = 3 and back_state = 0 and  to_char(end_time,'yyyy-MM-dd') <= to_char(sysdate-1,'yyyy-MM-dd')";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeStudentLeave> getOfficeStudentLeavePage(Pagination page){
		String sql = "select * from office_student_leave";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeStudentLeave> getOfficeStudentLeavePageByParams(
			Date startTime, Date endTime, int state,Pagination page,String unitId) {
		List<Object> argList=new ArrayList<Object>();
		StringBuilder sql=new StringBuilder();
		sql.append("select * from office_student_leave where is_deleted = 0 and unit_id = ? ");
		argList.add(unitId);
		if(startTime!=null){
			sql.append(" and start_time >= ?");
			argList.add(startTime);
		}
		if(endTime!=null){
			sql.append(" and start_time <= ?");
			argList.add(endTime);
		}
		if(Integer.valueOf(state)!=null&&Constants.LEAVE_APPLY_ALL!=Integer.valueOf(state)){
			sql.append(" and state = ?");
			argList.add(state);
		}
		sql.append(" order by state, start_time desc");
		return query(sql.toString(), argList.toArray(new Object[0]),new MultiRow(), page);
	}

	@Override
	public List<OfficeStudentLeave> getOfficeStudentLeavesByAuditParams(
			Date startTime,Date endTime,int state,Pagination page, String unitId) {
		List<Object> argList=new ArrayList<Object>();
		StringBuilder sql=new StringBuilder();
		sql.append("select * from office_student_leave where is_deleted = 0 and unit_id = ? ");
		argList.add(unitId);
		if(startTime!=null){
			sql.append(" and start_time >= ? ");
			argList.add(startTime);
		}
		if(endTime!=null){
			sql.append(" and start_time <= ? ");
			argList.add(endTime);
		}
		if(Constants.LEAVE_APPLY_ALL==Integer.valueOf(state)){
			sql.append(" and state >= 2 ");
		}
		if(Constants.LEAVE_APPLY_ALL!=Integer.valueOf(state)&&Integer.valueOf(state)!=null){
			sql.append(" and state = ? ");
			argList.add(state);
		}
		sql.append(" order by state, start_time desc");
		return query(sql.toString(),argList.toArray(new Object[0]), new MultiRow(), page);
	}


	@Override
	public List<OfficeStudentLeave> getOfficeStudentLeavesByCountParams(
			Date startTime, Date endTime, int state, Pagination page,
			String unitId,String[] leaveIds) {
		StringBuilder sql=new StringBuilder();
		List<Object> argList=new ArrayList<Object>();
		sql.append("select * from office_student_leave where is_deleted = 0 and unit_id = ? ");
		argList.add(unitId);
		if(startTime!=null){
			sql.append(" and start_time >= ?");
			argList.add(startTime);
		}
		if(endTime!=null){
			sql.append(" and start_time <= ?");
			argList.add(endTime);
		}
		if(Constants.LEAVE_APPLY_ALL==Integer.valueOf(state)){
			sql.append(" and state >= 3");
		}
		if(Integer.valueOf(state)!=null&&Constants.LEAVE_APPLY_ALL!=Integer.valueOf(state)){
			sql.append(" and state = ?");
			argList.add(state);
		}
		if(StringUtils.isNotBlank(leaveIds.toString())){
			sql.append(" and leave_type_id in");
			return queryForInSQL(sql.toString(), argList.toArray(new Object[]{}), leaveIds, new MultiRow(), " order by state,  start_time desc", page);
		}else{
			sql.append(" order by state,  start_time desc");
			return query(sql.toString(),argList.toArray(new Object[0]), new MultiRow(), page);
		}
	}

	@Override
	public Map<String, String> getSunMap(String unitId, Date startTime,
			Date endTime, String classId) {
		StringBuilder sql=new StringBuilder();
		List<Object> argList=new ArrayList<Object>();
		sql.append("select student_id,leave_type_id,sum(days) sumdays from office_student_leave where is_deleted = 0 and state = 3 and unit_id = ? ");
		argList.add(unitId);
		if(startTime!=null){
			sql.append(" and start_time >= ?");
			argList.add(startTime);
		}
		if(endTime!=null){
			sql.append(" and start_time <= ?");
			argList.add(endTime);
		}
		if(StringUtils.isNotBlank(classId)){
			sql.append(" and class_id = ?");
			argList.add(classId);
		}
		sql.append(" group by student_id, leave_type_id  ");
		return queryForMap(sql.toString(), argList.toArray(new Object[0]), new MapRowMapper<String, String>() {

			@Override
			public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("student_id")+"_"+rs.getString("leave_type_id");
			}

			@Override
			public String mapRowValue(ResultSet rs, int rowNum) throws SQLException {
				return rs.getDouble("sumdays")+"";
			}
			
		});
	}

	@Override
	public String[] getStuIds(String unitId, Date startTime, Date endTime,
			String classId,String[] leaveTypeIds) {
		StringBuffer sql = new StringBuffer();
		List<Object> objs = new ArrayList<Object>();
		sql.append("select distinct student_id from office_student_leave where is_deleted = 0 and state = 3 and unit_id = ? ");
		objs.add(unitId);
		if(startTime!=null){
			sql.append(" and start_time >= ?");
			objs.add(startTime);
		}
		if(endTime!=null){
			sql.append(" and start_time <= ?");
			objs.add(endTime);
		}
		if(StringUtils.isNotBlank(classId)){
			sql.append(" and class_id = ?");
			objs.add(classId);
		}
		if(StringUtils.isNotBlank(leaveTypeIds.toString())){
			sql.append(" and leave_type_id in ");
			List<String> stuIds=queryForInSQL(sql.toString(), objs.toArray(new Object[0]), leaveTypeIds, new MultiRowMapper<String>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("student_id");
				}
			});
			return stuIds.toArray(new String[0]);
		}else{
			List<String> stuIds =  query(sql.toString(), objs.toArray(new Object[0]), new MultiRowMapper<String>(){
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("student_id");
				}
			});
			return stuIds.toArray(new String[0]);
		}
	}


	@Override
	public List<OfficeStudentLeave> findStuIsLeaveBytime(Date date,
			String[] studentIds) {
		List<Object> argList=new ArrayList<Object>();
		StringBuilder sql=new StringBuilder();
		sql.append("select * from office_student_leave where is_deleted = 0 and state=3 ");
		if(date!=null){
			sql.append(" and  ? >= start_time ");
			argList.add(date);
			sql.append(" and  ? <= end_time ");
			argList.add(date);
		}
		
		sql.append(" and student_id in ");
		return queryForInSQL(sql.toString(), argList.toArray(new Object[0]), studentIds, new MultiRow());
	}

	
	@Override
	public List<OfficeStudentLeave> findStuIsLeaveBytime(Date start,Date end,
			String[] studentIds) {
		List<Object> argList=new ArrayList<Object>();
		StringBuilder sql=new StringBuilder();
		sql.append("select * from office_student_leave where is_deleted = 0 and state=3 ");
		if(start!=null && end!=null){
			sql.append(" and (");
			sql.append("  ( ? >= start_time ");
			argList.add(start);
			sql.append(" and  ? <= end_time ) ");
			argList.add(start);
			sql.append(" or ( ? >= start_time ");
			argList.add(end);
			sql.append(" and  ? <= end_time ) ");
			argList.add(end);
			sql.append("  )");
		}
		sql.append(" and student_id in ");
		return queryForInSQL(sql.toString(), argList.toArray(new Object[0]), studentIds, new MultiRow());
	}

	@Override
	public boolean isExistConflict(String unitId, String id,String studentId, Date startTime,
			Date endTime) {
		String sql="select count(1) from office_student_leave where unit_id = ? and ((start_time <= ? and end_time >= ?) or (start_time <= ? and end_time >= ?) or (start_time >= ? and end_time <= ?)) ";
		List<Object> argList=new ArrayList<Object>();
		argList.add(unitId);
		argList.add(startTime);
		argList.add(startTime);
		argList.add(endTime);
		argList.add(endTime);
		argList.add(startTime);
		argList.add(endTime);
		int i=0;
		if(StringUtils.isNotBlank(studentId)){
			sql+=" and student_id = ? ";
			argList.add(studentId);
		}
		if(StringUtils.isNotBlank(id)){
			sql+=" and id <> ? ";
			argList.add(id);
		}
		sql+=" and state != 4";
		i=queryForInt(sql, argList.toArray(new Object[]{}));
		if(i>0){
			return true;
		}
		return false;
	}
	
}
