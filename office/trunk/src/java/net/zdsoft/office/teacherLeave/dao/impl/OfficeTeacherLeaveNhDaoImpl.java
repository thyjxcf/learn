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
import net.zdsoft.office.teacherLeave.dao.OfficeTeacherLeaveNhDao;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeaveNh;
import net.zdsoft.office.util.Constants;

import org.apache.commons.lang.StringUtils;
/**
 * office_teacher_leave_nh
 * @author 
 * 
 */
public class OfficeTeacherLeaveNhDaoImpl extends BaseDao<OfficeTeacherLeaveNh> implements OfficeTeacherLeaveNhDao{

	@Override
	public OfficeTeacherLeaveNh setField(ResultSet rs) throws SQLException{
		OfficeTeacherLeaveNh officeTeacherLeaveNh = new OfficeTeacherLeaveNh();
		officeTeacherLeaveNh.setId(rs.getString("id"));
		officeTeacherLeaveNh.setUnitId(rs.getString("unit_id"));
		officeTeacherLeaveNh.setApplyUserId(rs.getString("apply_user_id"));
		officeTeacherLeaveNh.setBeginTime(rs.getTimestamp("begin_time"));
		officeTeacherLeaveNh.setEndTime(rs.getTimestamp("end_time"));
		officeTeacherLeaveNh.setDays(rs.getFloat("days"));
		officeTeacherLeaveNh.setLeaveTypeId(rs.getString("leave_type_id"));
		officeTeacherLeaveNh.setMorningChange(rs.getString("morning_change"));
		officeTeacherLeaveNh.setNightChange(rs.getString("night_change"));
		officeTeacherLeaveNh.setWeekChange(rs.getString("week_change"));
		officeTeacherLeaveNh.setActChargeTeacher(rs.getString("act_charge_teacher"));
		officeTeacherLeaveNh.setRemark(rs.getString("remark"));
		officeTeacherLeaveNh.setState(rs.getInt("state"));
		officeTeacherLeaveNh.setIsDeleted(rs.getBoolean("is_deleted"));
		officeTeacherLeaveNh.setCreateUserId(rs.getString("create_user_id"));
		officeTeacherLeaveNh.setCreateTime(rs.getTimestamp("create_time"));
		officeTeacherLeaveNh.setFlowId(rs.getString("flow_id"));
		return officeTeacherLeaveNh;
	}

	@Override
	public OfficeTeacherLeaveNh save(OfficeTeacherLeaveNh officeTeacherLeaveNh){
		String sql = "insert into office_teacher_leave_nh(id, unit_id, apply_user_id, begin_time, end_time, days, leave_type_id, morning_change, night_change, week_change, act_charge_teacher, remark, state, is_deleted, create_user_id, create_time, flow_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeTeacherLeaveNh.getId())){
			officeTeacherLeaveNh.setId(createId());
		}
		Object[] args = new Object[]{
			officeTeacherLeaveNh.getId(), officeTeacherLeaveNh.getUnitId(), 
			officeTeacherLeaveNh.getApplyUserId(), officeTeacherLeaveNh.getBeginTime(), 
			officeTeacherLeaveNh.getEndTime(), officeTeacherLeaveNh.getDays(), 
			officeTeacherLeaveNh.getLeaveTypeId(), officeTeacherLeaveNh.getMorningChange(), 
			officeTeacherLeaveNh.getNightChange(), officeTeacherLeaveNh.getWeekChange(), 
			officeTeacherLeaveNh.getActChargeTeacher(), officeTeacherLeaveNh.getRemark(), 
			officeTeacherLeaveNh.getState(), officeTeacherLeaveNh.getIsDeleted(), 
			officeTeacherLeaveNh.getCreateUserId(), officeTeacherLeaveNh.getCreateTime(), 
			officeTeacherLeaveNh.getFlowId()
		};
		update(sql, args);
		return officeTeacherLeaveNh;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_teacher_leave_nh where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeTeacherLeaveNh officeTeacherLeaveNh){
		String sql = "update office_teacher_leave_nh set apply_user_id = ?, begin_time = ?, end_time = ?, days = ?, leave_type_id = ?, morning_change = ?, night_change = ?, week_change = ?, act_charge_teacher = ?, remark = ?, state = ?, flow_id = ? where id = ?";
		Object[] args = new Object[]{
			officeTeacherLeaveNh.getApplyUserId(), 
			officeTeacherLeaveNh.getBeginTime(), officeTeacherLeaveNh.getEndTime(), 
			officeTeacherLeaveNh.getDays(), officeTeacherLeaveNh.getLeaveTypeId(), 
			officeTeacherLeaveNh.getMorningChange(), officeTeacherLeaveNh.getNightChange(), 
			officeTeacherLeaveNh.getWeekChange(), officeTeacherLeaveNh.getActChargeTeacher(), 
			officeTeacherLeaveNh.getRemark(), officeTeacherLeaveNh.getState(), 
			officeTeacherLeaveNh.getFlowId(), officeTeacherLeaveNh.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public void updateState(String id, Integer state) {
		String sql = " update office_teacher_leave_nh set state = ? where id = ? ";
		update(sql, new Object[]{state, id});
	}

	@Override
	public OfficeTeacherLeaveNh getOfficeTeacherLeaveNhById(String id){
		String sql = "select * from office_teacher_leave_nh where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhMapByIds(String[] ids){
		String sql = "select * from office_teacher_leave_nh where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhList(){
		String sql = "select * from office_teacher_leave_nh";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhPage(String userId, Date beginTime, Date endTime, String state, Pagination page){
		StringBuffer sql = new StringBuffer("select * from office_teacher_leave_nh where is_deleted = 0 and create_user_id = ? ");
		List<Object> objs = new ArrayList<Object>();
		objs.add(userId);
		if(StringUtils.isNotBlank(state)){
			sql.append(" and state = ?"); 
			objs.add(state);
		}
		if(beginTime!=null){
			sql.append(" and to_date(to_char(begin_time,'yyyy-MM-dd'),'yyyy-MM-dd') >= ? ");
			objs.add(beginTime);
		}
		if(endTime!=null){
			sql.append(" and to_date(to_char(begin_time,'yyyy-MM-dd'),'yyyy-MM-dd') <= ? ");
			objs.add(endTime);
		}
		sql.append(" order by state, begin_time desc ");
		return query(sql.toString(), objs.toArray(new Object[0]), new MultiRow(), page);
	}

	@Override
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhByUnitIdList(String unitId){
		String sql = "select * from office_teacher_leave_nh where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhByUnitIdPage(String unitId, String state, Pagination page){
		String sql = "select * from office_teacher_leave_nh where is_deleted = 0 and unit_id = ? ";
		List<Object> objs = new ArrayList<Object>();
		objs.add(unitId);
		if(StringUtils.isNotBlank(state)){
			sql += " and state = ? order by state, begin_time desc "; 
			objs.add(state);
		}else{
			sql += " and state >= 2 order by state, begin_time desc ";
		}
		return query(sql, objs.toArray(new Object[0]), new MultiRow(), page);
	}
	
	@Override
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhListByFlowIds(
			String[] flowIds, Date beginTime, Date endTime, Pagination page) {
		StringBuffer sql = new StringBuffer("select * from office_teacher_leave_nh where is_deleted = 0 ");
		List<Object> obj = new ArrayList<Object>();
		if(beginTime!=null){
			sql.append(" and to_date(to_char(begin_time,'yyyy-MM-dd'),'yyyy-MM-dd') >= ? ");
			obj.add(beginTime);
		}
		if(endTime!=null){
			sql.append(" and to_date(to_char(begin_time,'yyyy-MM-dd'),'yyyy-MM-dd') <= ? ");
			obj.add(endTime);
		}
		sql.append(" and flow_id in ");
		return queryForInSQL(sql.toString(), obj.toArray(new Object[0]), flowIds, new MultiRow(), " order by begin_time desc ", page);
	}
	
	@Override
	public List<OfficeTeacherLeaveNh> HaveDoAudit(String userId, Date beginTime, Date endTime, Pagination page) {
		page.setUseCursor(true);
		List<Object> obj = new ArrayList<Object>();
		String findSql = "select distinct leave.* from office_teacher_leave_nh leave,jbpm_hi_task task where leave.state >=3 and leave.flow_id = task.proc_inst_id ";
		StringBuffer sb = new StringBuffer();
		sb.append(findSql);
		sb.append(" and task.ASSIGNEE_ID =?");
		obj.add(userId);
		if(beginTime!=null){
			sb.append(" and to_date(to_char(leave.begin_time,'yyyy-MM-dd'),'yyyy-MM-dd') >= ? ");
			obj.add(beginTime);
		}
		if(endTime!=null){
			sb.append(" and to_date(to_char(leave.begin_time,'yyyy-MM-dd'),'yyyy-MM-dd') <= ? ");
			obj.add(endTime);
		}
		sb.append(" order by state, begin_time desc");
		return query(sb.toString(),obj.toArray(new Object[0]), new MultiRow(),page);
	}

	@Override
	public List<OfficeTeacherLeaveNh> getteacherLeavListBySearParams(
			String unitId, Date beginTime, Date endTime, String state,Pagination page,String[] leaveIds) {
		StringBuilder sql=new StringBuilder();
		sql.append("select * from office_teacher_leave_nh where is_deleted = 0 ");
		List<Object> argList=new ArrayList<Object>();
		if(StringUtils.isNotBlank(unitId)){
			sql.append(" and unit_id = ? ");
			argList.add(unitId);
		}
		if(beginTime!=null){
			sql.append(" and to_date(to_char(begin_time,'yyyy-MM-dd'),'yyyy-MM-dd') >= ? ");
			argList.add(beginTime);
		}
		if(endTime!=null){
			sql.append(" and to_date(to_char(begin_time,'yyyy-MM-dd'),'yyyy-MM-dd') <= ? ");
			argList.add(endTime);
		}
		if(state!=null&&Constants.LEAVE_APPLY_ALL==Integer.valueOf(state)){
			sql.append(" and state >= 3 ");
		}
		if(state!=null&&Constants.LEAVE_APPLY_ALL!=Integer.valueOf(state)){
			sql.append(" and state = ? ");
			argList.add(state);
		}
		if(StringUtils.isNotBlank(leaveIds.toString())){
			sql.append(" and leave_type_id in");
			return queryForInSQL(sql.toString(),argList.toArray(new Object[]{}), leaveIds, new MultiRow(), " order by state,begin_time desc",page);
		}else{
			sql.append(" order by state,begin_time desc");
			return query(sql.toString(), argList.toArray(new Object[0]), new MultiRow(), page);
		}
	}

	@Override
	public List<OfficeTeacherLeaveNh> getSummaryList(String unitId,
			Date beginTime, Date endTime) {
		StringBuilder sql=new StringBuilder();
		List<Object> argList=new ArrayList<Object>();
		sql.append("select * from office_teacher_leave_nh where is_deleted = 0 and state = 3 ");
		if(StringUtils.isNotBlank(unitId)){
			sql.append(" and unit_id = ? ");
			argList.add(unitId);
		}
		if(beginTime!=null){
			sql.append(" and begin_time >= ? ");
			argList.add(beginTime);
		}
		if(endTime!=null){
			sql.append(" and begin_time <= ? ");
			argList.add(beginTime);
		}
		sql.append(" order by begin_time desc");
		return query(sql.toString(), argList.toArray(new Object[]{}),new MultiRow());
	}

	@Override
	public String[] getUserIds(String unitId, Date beginTime, Date endTime,String[] leaveIds) {
		StringBuilder sql=new StringBuilder();
		sql.append("select distinct apply_user_id from office_teacher_leave_nh where is_deleted = 0 and state=3 and unit_id = ? ");
		List<Object> argList=new ArrayList<Object>();
		argList.add(unitId);
		if(beginTime!=null){
			sql.append(" and begin_time >= ?");
			argList.add(beginTime);
		}
		if(endTime!=null){
			sql.append(" and begin_time <= ?");
			argList.add(endTime);
		}
		if(StringUtils.isNotBlank(leaveIds.toString())){
			sql.append(" and leave_type_id in ");
			List<String> userIdsList=queryForInSQL(sql.toString(),argList.toArray(new Object[]{}), leaveIds, new MultiRowMapper<String>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("APPLY_USER_ID");
				}
			});
			return userIdsList.toArray(new String[]{});
		}else{
			List<String> userIdsList=query(sql.toString(), argList.toArray(new Object[]{}), new MultiRowMapper<String>() {
				
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("APPLY_USER_ID");
				}
			});
			return userIdsList.toArray(new String[]{});
		}
	}

	@Override
	public Map<String, String> getSumMap(String unitId, Date beginTime,
			Date endTime) {
		StringBuilder sql=new StringBuilder();
		List<Object> argList=new ArrayList<Object>();
		sql.append("select apply_user_id,leave_type_id,sum(days) sumdays from office_teacher_leave_nh where is_deleted = 0 and state = 3 and unit_id = ? ");
		argList.add(unitId);
		if(beginTime!=null){
			sql.append(" and begin_time >= ? ");
			argList.add(beginTime);
		}
		if(endTime!=null){
			sql.append(" and begin_time <= ? ");
			argList.add(endTime);
		}
		sql.append(" group by apply_user_id, leave_type_id  ");
		return queryForMap(sql.toString(), argList.toArray(new Object[]{}), new MapRowMapper<String, String>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("apply_user_id")+"_"+rs.getString("leave_type_id");
			}

			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getDouble("sumdays")+"";
			}
			
		});
	}
	
	@Override
	public boolean isExistConflict(String id, String applyUserId, Date beginTime,
			Date endTime) {
		String sql = "select count(1) from office_teacher_leave_nh where state !=4 and apply_user_id = ? and ((begin_time <= ? and end_time >= ?) or (begin_time <= ? and end_time >= ?) or (begin_time >= ? and end_time <= ?))";
		
		String sql1 = "select count(1) from office_teacher_leave_nh where state !=4 and apply_user_id = ? and id != ? and ((begin_time <= ? and end_time >= ?) or (begin_time <= ? and end_time >= ?) or (begin_time >= ? and end_time <= ?))";
		int i = 0;
		if(StringUtils.isNotBlank(id)){
			i = queryForInt(sql1, new Object[] {applyUserId, id, beginTime, beginTime, endTime, endTime, beginTime, endTime});
		}else{
			i = queryForInt(sql, new Object[] {applyUserId, beginTime, beginTime, endTime, endTime, beginTime, endTime});
		}
		if(i > 0){
			return true;
		}
		return false;
	}
}