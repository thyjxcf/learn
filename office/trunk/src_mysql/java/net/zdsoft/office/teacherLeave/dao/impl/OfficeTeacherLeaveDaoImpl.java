package net.zdsoft.office.teacherLeave.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherLeave.dao.OfficeTeacherLeaveDao;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.util.Constants;

import org.apache.commons.lang.StringUtils;

/**
 * office_teacher_leave 
 * @author 
 * 
 */
public class OfficeTeacherLeaveDaoImpl extends BaseDao<OfficeTeacherLeave> implements OfficeTeacherLeaveDao{
	
	private static final String FIND_SQL = "select * from office_teacher_leave where unit_id=? and create_user_id=? and is_deleted = 0 ";
	private static final String SQL_SUM = "select apply_user_id, leave_type, sum(days) sumdays from office_teacher_leave where unit_id = ? and apply_status = 3";
	@Override
	public OfficeTeacherLeave setField(ResultSet rs) throws SQLException{
		OfficeTeacherLeave officeTeacherLeave = new OfficeTeacherLeave();
		officeTeacherLeave.setId(rs.getString("id"));
		officeTeacherLeave.setUnitId(rs.getString("unit_id"));
		officeTeacherLeave.setApplyUserId(rs.getString("apply_user_id"));
		officeTeacherLeave.setLeaveBeignTime(rs.getTimestamp("leave_beign_time"));
		officeTeacherLeave.setLeaveEndTime(rs.getTimestamp("leave_end_time"));
		officeTeacherLeave.setDeptId(rs.getString("dept_id"));
		officeTeacherLeave.setDeleted(rs.getBoolean("is_deleted"));
		officeTeacherLeave.setDays(rs.getFloat("days"));
		officeTeacherLeave.setLeaveType(rs.getString("leave_type"));
		officeTeacherLeave.setLeaveReason(rs.getString("leave_reason"));
		officeTeacherLeave.setReplacingTeacher(rs.getString("replacing_teacher"));
		officeTeacherLeave.setReplacingClass(rs.getString("replacing_class"));
		officeTeacherLeave.setApplyStatus(rs.getInt("apply_status"));
		officeTeacherLeave.setFlowId(rs.getString("flow_id"));
		officeTeacherLeave.setCreateUserId(rs.getString("create_user_id"));
		officeTeacherLeave.setInvalidUser(rs.getString("invalid_user"));
		return officeTeacherLeave;
	}
	
	@Override
	public OfficeTeacherLeave save(OfficeTeacherLeave officeTeacherLeave){
		String sql = "insert into office_teacher_leave(id, unit_id, apply_user_id, leave_beign_time, leave_end_time, dept_id, is_deleted, days, leave_type, leave_reason, replacing_teacher, replacing_class, apply_status,flow_id,create_user_id,invalid_user) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(officeTeacherLeave.getId())){
			officeTeacherLeave.setId(createId());
		}
		Object[] args = new Object[]{
			officeTeacherLeave.getId(), officeTeacherLeave.getUnitId(), 
			officeTeacherLeave.getApplyUserId(), officeTeacherLeave.getLeaveBeignTime(), 
			officeTeacherLeave.getLeaveEndTime(), officeTeacherLeave.getDeptId(), 
			officeTeacherLeave.isDeleted(), officeTeacherLeave.getDays(), 
			officeTeacherLeave.getLeaveType(), officeTeacherLeave.getLeaveReason(), 
			officeTeacherLeave.getReplacingTeacher(), officeTeacherLeave.getReplacingClass(), 
			officeTeacherLeave.getApplyStatus(),officeTeacherLeave.getFlowId(),
			officeTeacherLeave.getCreateUserId(),officeTeacherLeave.getInvalidUser()
		};
		update(sql, args);
		return officeTeacherLeave;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_teacher_leave where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(OfficeTeacherLeave officeTeacherLeave){
		String sql = "update office_teacher_leave set unit_id = ?, apply_user_id = ?, leave_beign_time = ?, leave_end_time = ?, dept_id = ?, is_deleted = ?, days = ?, leave_type = ?, leave_reason = ?, replacing_teacher = ?, replacing_class = ?, apply_status = ?,flow_id=?,invalid_user=? where id = ?";
		Object[] args = new Object[]{
			officeTeacherLeave.getUnitId(), officeTeacherLeave.getApplyUserId(), 
			officeTeacherLeave.getLeaveBeignTime(), officeTeacherLeave.getLeaveEndTime(), 
			officeTeacherLeave.getDeptId(), officeTeacherLeave.isDeleted(), 
			officeTeacherLeave.getDays(), officeTeacherLeave.getLeaveType(), 
			officeTeacherLeave.getLeaveReason(), officeTeacherLeave.getReplacingTeacher(), 
			officeTeacherLeave.getReplacingClass(), officeTeacherLeave.getApplyStatus(), officeTeacherLeave.getFlowId(),
			officeTeacherLeave.getInvalidUser(),officeTeacherLeave.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeTeacherLeave getOfficeTeacherLeaveById(String id){
		String sql = "select * from office_teacher_leave where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeTeacherLeave> getOfficeTeacherLeaveMapByIds(String[] ids){
		String sql = "select * from office_teacher_leave where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeTeacherLeave> getOfficeTeacherLeaveList(){
		String sql = "select * from office_teacher_leave";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeTeacherLeave> getOfficeTeacherLeavePage(Pagination page){
		String sql = "select * from office_teacher_leave";
		return query(sql, new MultiRow(), page);
	}
	

	@Override
	public List<OfficeTeacherLeave> getOfficeTeacherLeaveByUnitIdList(String unitId){
		String sql = "select * from office_teacher_leave where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeTeacherLeave> getOfficeTeacherLeaveByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_teacher_leave where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeTeacherLeave> getQueryList(String unitId,
			String userId, String userName, String deptId,
			Date startTime, Date endTime, Pagination page) {
		StringBuffer sql=new StringBuffer("select otl.*,bu.real_name real_name from office_teacher_leave otl,base_user bu where otl.apply_user_id=bu.id and otl.apply_status = 3 and otl.is_deleted=0 and otl.unit_id = ?");
		List<Object>list=new ArrayList<Object>();
		list.add(unitId);
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and bu.id=?");
			list.add(userId);
		}
		if(StringUtils.isNotBlank(userName)){
			sql.append(" and bu.real_name like ?");
			list.add("%"+userName+"%");
		}
		if(StringUtils.isNotBlank(deptId)){
			sql.append(" and otl.dept_id=?");
			list.add(deptId);
		}
		if(startTime!=null){
			sql.append(" and otl.leave_beign_time>=?");
			list.add(startTime);
		}
		if(endTime!=null){
			sql.append(" and otl.leave_beign_time<=?");
			list.add(endTime);
		}
		if(page==null){
			return query(sql.toString(), list.toArray(new Object[0]), new MultiRowMapper<OfficeTeacherLeave>(){

				@Override
				public OfficeTeacherLeave mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeTeacherLeave otl=setField(rs);
					otl.setUserName(rs.getString("real_name"));
					return otl;
				}
				
			});
		}else{
			return query(sql.toString(), list.toArray(new Object[0]), new MultiRowMapper<OfficeTeacherLeave>(){

				@Override
				public OfficeTeacherLeave mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeTeacherLeave otl=setField(rs);
					otl.setUserName(rs.getString("real_name"));
					return otl;
				}
				
			},page);
		}
	}

	@Override
	public List<OfficeTeacherLeave> getApplyList(String userId, String unitId,
			int applyStatus, Pagination page) {
		StringBuffer sb = new StringBuffer(FIND_SQL);
		if (applyStatus!=Constants.LEAVE_APPLY_ALL) {
			sb.append(" and apply_status="+applyStatus);
		}else{
			sb.append(" and apply_status!="+Constants.APPLY_STATE_INVALID);
		}
		sb.append(" order by apply_status, leave_beign_time desc ");
		return query(sb.toString(), new String[]{unitId,userId}, new MultiRow(),page);
	}

	@Override
	public Map<String, OfficeTeacherLeave> getOfficeTeacherLeaveMapByFlowIds(
			String[] flowIds) {
		String sql = "select * from office_teacher_leave where flow_id in";
		return queryForInSQL(sql, null, flowIds, new MapRowMapper<String, OfficeTeacherLeave>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("flow_id");
			}

			@Override
			public OfficeTeacherLeave mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public List<OfficeTeacherLeave> HaveDoAudit(String userId,boolean invalid, Pagination page) {
		page.setUseCursor(true);
		List<Object> obj = new ArrayList<Object>();
		String findSql = "select distinct leave.* from office_teacher_leave leave,jbpm_hi_task task where  leave.flow_id = task.proc_inst_id ";
		StringBuffer sb = new StringBuffer();
		sb.append(findSql);
		if(invalid){
			sb.append(" and leave.apply_status = 8");
		}else{
			sb.append(" and leave.apply_status >=3 and leave.apply_status != 8");
		}
		sb.append(" and task.ASSIGNEE_ID =?");
		obj.add(userId);
		sb.append(" order by  leave_beign_time desc,apply_status");
		return query(sb.toString(),obj.toArray(), new MultiRow(),page);
	}

	@Override
	public List<OfficeTeacherLeave> getOfficeTeacherLeaveByIds(
			String[] ids) {
		String sql = "select * from office_teacher_leave where id in";
		return queryForInSQL(sql, null, ids, new MultiRow());
	}

	public Map<String, String> getSumMap(String unitId, Date startTime, Date endTime, String deptId){
		StringBuffer sql = new StringBuffer();
		sql.append(SQL_SUM);
		List<Object> objs = new ArrayList<Object>();
		objs.add(unitId);
		if(startTime!=null){
			sql.append(" and leave_beign_time >= ?");
			objs.add(startTime);
		}
		if(endTime!=null){
			sql.append(" and leave_beign_time <= ?");
			objs.add(endTime);
		}
		if(StringUtils.isNotBlank(deptId)){
			sql.append(" and dept_id = ?");
			objs.add(deptId);
		}
		sql.append(" group by apply_user_id, leave_type ");
		return queryForMap(sql.toString(), objs.toArray(new Object[0]), new MapRowMapper<String, String>() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("apply_user_id")+"_"+rs.getString("leave_type");
			}
			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getDouble("sumdays")+"";
			}
		});
	}
	
	@Override
	public String[] getApplyUserIds(String unitId, Date startTime,
			Date endTime, String deptId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct apply_user_id from office_teacher_leave where apply_status = 3 and unit_id = ? ");
		List<Object> objs = new ArrayList<Object>();
		objs.add(unitId);
		if(startTime!=null){
			sql.append(" and leave_beign_time >= ?");
			objs.add(startTime);
		}
		if(endTime!=null){
			sql.append(" and leave_beign_time <= ?");
			objs.add(endTime);
		}
		if(StringUtils.isNotBlank(deptId)){
			sql.append(" and dept_id = ?");
			objs.add(deptId);
		}
		List<String> userIds =  query(sql.toString(), objs.toArray(new Object[0]), new MultiRowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("apply_user_id");
			}
		});
		return userIds.toArray(new String[0]);
	}
}