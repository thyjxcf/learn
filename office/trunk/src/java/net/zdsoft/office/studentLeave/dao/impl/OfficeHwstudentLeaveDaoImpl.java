package net.zdsoft.office.studentLeave.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
import net.zdsoft.office.studentLeave.dao.OfficeHwstudentLeaveDao;
import net.zdsoft.office.studentLeave.entity.OfficeHwstudentLeave;

import org.apache.commons.lang3.StringUtils;
/**
 * office_hwstudent_leave
 * @author 
 * 
 */
public class OfficeHwstudentLeaveDaoImpl extends BaseDao<OfficeHwstudentLeave> implements OfficeHwstudentLeaveDao{

	@Override
	public OfficeHwstudentLeave setField(ResultSet rs) throws SQLException{
		OfficeHwstudentLeave officeHwstudentLeave = new OfficeHwstudentLeave();
		officeHwstudentLeave.setId(rs.getString("id"));
		officeHwstudentLeave.setUnitId(rs.getString("unit_id"));
		officeHwstudentLeave.setApplyUserId(rs.getString("apply_user_id"));
		officeHwstudentLeave.setApplyDate(rs.getTimestamp("apply_date"));
		officeHwstudentLeave.setType(rs.getString("type"));
		officeHwstudentLeave.setState(rs.getString("state"));
		officeHwstudentLeave.setFlowId(rs.getString("flow_id"));
		officeHwstudentLeave.setIsDeleted(rs.getBoolean("is_deleted"));
		officeHwstudentLeave.setCreationTime(rs.getTimestamp("creation_time"));
		officeHwstudentLeave.setModifyTime(rs.getTimestamp("modify_time"));
		officeHwstudentLeave.setClassId(rs.getString("class_id"));
		officeHwstudentLeave.setStudentId(rs.getString("student_id"));
		return officeHwstudentLeave;
	}

	@Override
	public OfficeHwstudentLeave save(OfficeHwstudentLeave officeHwstudentLeave){
		String sql = "insert into office_hwstudent_leave(id, unit_id, apply_user_id, apply_date, type, state, flow_id, is_deleted, creation_time, modify_time, class_id, student_id) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeHwstudentLeave.getId())){
			officeHwstudentLeave.setId(createId());
		}
		Object[] args = new Object[]{
			officeHwstudentLeave.getId(), officeHwstudentLeave.getUnitId(), 
			officeHwstudentLeave.getApplyUserId(), officeHwstudentLeave.getApplyDate(), 
			officeHwstudentLeave.getType(), officeHwstudentLeave.getState(), 
			officeHwstudentLeave.getFlowId(), officeHwstudentLeave.getIsDeleted(), 
			officeHwstudentLeave.getCreationTime(), officeHwstudentLeave.getModifyTime(), 
			officeHwstudentLeave.getClassId(), officeHwstudentLeave.getStudentId()
		};
		update(sql, args);
		return officeHwstudentLeave;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_hwstudent_leave where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeHwstudentLeave officeHwstudentLeave){
		String sql = "update office_hwstudent_leave set unit_id = ?, apply_user_id = ?, apply_date = ?, type = ?, state = ?, flow_id = ?, is_deleted = ?, creation_time = ?, modify_time = ?, class_id = ?, student_id = ? where id = ?";
		Object[] args = new Object[]{
			officeHwstudentLeave.getUnitId(), officeHwstudentLeave.getApplyUserId(), 
			officeHwstudentLeave.getApplyDate(), officeHwstudentLeave.getType(), 
			officeHwstudentLeave.getState(), officeHwstudentLeave.getFlowId(), 
			officeHwstudentLeave.getIsDeleted(), officeHwstudentLeave.getCreationTime(), 
			officeHwstudentLeave.getModifyTime(), officeHwstudentLeave.getClassId(), 
			officeHwstudentLeave.getStudentId(), officeHwstudentLeave.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeHwstudentLeave getOfficeHwstudentLeaveById(String id){
		String sql = "select * from office_hwstudent_leave where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeHwstudentLeave> getOfficeHwstudentLeaveMapByIds(String[] ids){
		String sql = "select * from office_hwstudent_leave where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeaveList(){
		String sql = "select * from office_hwstudent_leave";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeavePage(Pagination page){
		String sql = "select * from office_hwstudent_leave";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeaveByUnitIdList(String unitId){
		String sql = "select * from office_hwstudent_leave where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}
	@Override
	public List<OfficeHwstudentLeave> findByUnitIdAndSubmit(String unitId,
			String studentId) {
		String sql = "select * from office_hwstudent_leave where unit_id = ? and student_id = ? and state <> '1' ";
		return query(sql, new Object[]{unitId,studentId }, new MultiRow());
	}
	
	@Override
	public List<OfficeHwstudentLeave> findByUnitIdAndType(String unitId,
			String classId, String studentId, String leaveType) {
		String sql = "select * from office_hwstudent_leave where unit_id = ? and state = '3' ";
		List<Object> obj = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		obj.add(unitId);
		if(StringUtils.isNotBlank(leaveType)){
			sb.append("and type = ? ");
			obj.add(leaveType);
		}
		if(StringUtils.isNotBlank(classId)){
			sb.append(" and class_id = ? ");
			obj.add(classId);
		}
		if(StringUtils.isNotBlank(studentId)){
			sb.append(" and student_id = ? ");
			obj.add(studentId);
		}
		return query(sb.toString(), obj.toArray(), new MultiRow());
	}
	@Override
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeaveByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_hwstudent_leave where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeaveByUnitIdPage(
			String unitId, String stuId, String type, Pagination page) {
		String sql = "select * from office_hwstudent_leave where unit_id = ? and student_id = ? and type = ?";
		return query(sql, new Object[]{unitId,stuId,type }, new MultiRow(), page);
	}
	
	@Override
	public Map<String, OfficeHwstudentLeave> findByFlowIds(String type,
			String[] flowIds) {
		String sql = "select * from office_hwstudent_leave where is_deleted=0 and flow_id in";
			return queryForInSQL(sql, null, flowIds, new MapRowMapper<String, OfficeHwstudentLeave>(){

				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("flow_id");
				}

				@Override
				public OfficeHwstudentLeave mapRowValue(ResultSet rs, int rowNum)
						throws SQLException {
					return setField(rs);
				}
			});
	}
	
	@Override
	public List<OfficeHwstudentLeave> getAuditedList(String userId,
			String[] state, String type, Pagination page) {
		page.setUseCursor(true);
		List<Object> obj = new ArrayList<Object>();
		String sql = "select distinct leave.* from office_hwstudent_leave leave,jbpm_hi_task task where leave.is_deleted=0 and leave.flow_id = task.proc_inst_id ";
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		sb.append(" and task.ASSIGNEE_ID =?");
		obj.add(userId);
		sb.append(" and leave.type = ?");
		obj.add(type);
		if(state != null){
			sb.append(" and leave.state in").append(
					SQLUtils.toSQLInString(state));
		}
		sb.append(" order by leave.state desc");
		return query(sb.toString(), obj.toArray(), new MultiRow(), page);
	}
	
	@Override
	public List<OfficeHwstudentLeave> getLeavesByIdsAndState(String stuId,
			String[] leaveIds, String[] states) {
		String sql = "select * from office_hwstudent_leave where student_id = ? and id in "
				+SQLUtils.toSQLInString(leaveIds)+" and state in" + SQLUtils.toSQLInString(states);
		return query(sql, new Object[]{stuId},new MultiRow());
	}
}

	