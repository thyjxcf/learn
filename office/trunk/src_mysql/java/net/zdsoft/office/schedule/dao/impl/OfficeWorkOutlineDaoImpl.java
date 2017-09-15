package net.zdsoft.office.schedule.dao.impl;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.schedule.dao.OfficeWorkOutlineDao;
import net.zdsoft.office.schedule.entity.OfficeWorkOutline;

import org.apache.commons.lang.StringUtils;
/**
 * office_work_outline
 * @author 
 * 
 */
public class OfficeWorkOutlineDaoImpl extends BaseDao<OfficeWorkOutline> implements OfficeWorkOutlineDao{

	@Override
	public OfficeWorkOutline setField(ResultSet rs) throws SQLException{
		OfficeWorkOutline officeWorkOutline = new OfficeWorkOutline();
		officeWorkOutline.setId(rs.getString("id"));
		officeWorkOutline.setOperator(rs.getString("operator"));
		officeWorkOutline.setUnitId(rs.getString("unit_id"));
		officeWorkOutline.setPlace(rs.getString("place"));
		officeWorkOutline.setContent(rs.getString("content"));
		officeWorkOutline.setRemark(rs.getString("remark"));
		officeWorkOutline.setIsDeleted(rs.getInt("is_deleted"));
		officeWorkOutline.setModifyTime(rs.getTimestamp("modify_time"));
		officeWorkOutline.setCalendarTime(rs.getTimestamp("calendar_time"));
		officeWorkOutline.setIsSmsAlarm(rs.getInt("is_sms_alarm"));
		officeWorkOutline.setSmsAlarmTime(rs.getTimestamp("sms_alarm_time"));
		officeWorkOutline.setVersion(rs.getString("version"));
		officeWorkOutline.setHalfDays(rs.getInt("half_days"));
		officeWorkOutline.setEndTime(rs.getTimestamp("end_time"));
		officeWorkOutline.setPeriod(rs.getInt("period"));
		officeWorkOutline.setCreateDept(rs.getString("create_dept"));
		officeWorkOutline.setState(rs.getString("state"));
		officeWorkOutline.setAuditRemark(rs.getString("audit_remark"));
		return officeWorkOutline;
	}

	@Override
	public OfficeWorkOutline save(OfficeWorkOutline officeWorkOutline){
		String sql = "insert into office_work_outline(id, operator, unit_id, place, content, remark, is_deleted, modify_time, calendar_time, is_sms_alarm, sms_alarm_time, version, half_days, end_time, period, create_dept, state, audit_remark) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeWorkOutline.getId())){
			officeWorkOutline.setId(createId());
		}
		Object[] args = new Object[]{
			officeWorkOutline.getId(), officeWorkOutline.getOperator(), 
			officeWorkOutline.getUnitId(), officeWorkOutline.getPlace(), 
			officeWorkOutline.getContent(), officeWorkOutline.getRemark(), 
			officeWorkOutline.getIsDeleted(), officeWorkOutline.getModifyTime(), 
			officeWorkOutline.getCalendarTime(), officeWorkOutline.getIsSmsAlarm(), 
			officeWorkOutline.getSmsAlarmTime(), officeWorkOutline.getVersion(), 
			officeWorkOutline.getHalfDays(), officeWorkOutline.getEndTime(), 
			officeWorkOutline.getPeriod(), officeWorkOutline.getCreateDept(), 
			officeWorkOutline.getState(), officeWorkOutline.getAuditRemark()
		};
		update(sql, args);
		return officeWorkOutline;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "update office_work_outline set is_deleted = 1 where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeWorkOutline officeWorkOutline){
		String sql = "update office_work_outline set operator = ?, unit_id = ?, place = ?, content = ?, remark = ?, is_deleted = ?, modify_time = ?, calendar_time = ?, is_sms_alarm = ?, sms_alarm_time = ?, version = ?, half_days = ?, end_time = ?, period = ?, create_dept = ?, state = ?, audit_remark = ? where id = ?";
		Object[] args = new Object[]{
			officeWorkOutline.getOperator(), officeWorkOutline.getUnitId(), 
			officeWorkOutline.getPlace(),  officeWorkOutline.getContent(), 
			officeWorkOutline.getRemark(), officeWorkOutline.getIsDeleted(), 
			officeWorkOutline.getModifyTime(), officeWorkOutline.getCalendarTime(), 
			officeWorkOutline.getIsSmsAlarm(), officeWorkOutline.getSmsAlarmTime(), 
			officeWorkOutline.getVersion(), officeWorkOutline.getHalfDays(), 
			officeWorkOutline.getEndTime(), officeWorkOutline.getPeriod(), 
			officeWorkOutline.getCreateDept(), officeWorkOutline.getState(), 
			officeWorkOutline.getAuditRemark(), officeWorkOutline.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeWorkOutline getOfficeWorkOutlineById(String id){
		String sql = "select * from office_work_outline where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeWorkOutline> getOfficeWorkOutlineMapByIds(String[] ids){
		String sql = "select * from office_work_outline where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeWorkOutline> getOfficeWorkOutlineList(){
		String sql = "select * from office_work_outline";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeWorkOutline> getOfficeWorkOutlinePage(Pagination page){
		String sql = "select * from office_work_outline";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeWorkOutline> getOfficeWorkOutlineByUnitIdList(String unitId){
		String sql = "select * from office_work_outline where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeWorkOutline> getOfficeWorkOutlineByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_work_outline where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeWorkOutline> getOfficeWorkOutlineBySearchParams(
			String unitId, String deptId, Date startTime, Date endTime) {
		String sql="select * from office_work_outline where unit_id = ? and is_deleted = 0 ";
		List<Object> argList=new ArrayList<Object>();
		argList.add(unitId);
		if(StringUtils.isNotBlank(deptId)){
			sql+=" and create_dept = ? ";
			argList.add(deptId);
		}
		if(startTime!=null&&endTime!=null){
			sql+=" and ((to_char(CALENDAR_TIME,'yyyy-MM-dd')<= to_char(?,'yyyy-MM-dd') and to_char(END_TIME,'yyyy-MM-dd')>= to_char(?,'yyyy-MM-dd'))"
					+ " or (to_char(CALENDAR_TIME,'yyyy-mm-dd')<= to_char(?,'yyyy-MM-dd') and to_char(END_TIME,'yyyy-MM-dd') >= to_char(?,'yyyy-MM-dd'))"
					+ " or (to_char(CALENDAR_TIME,'yyyy-MM-dd') >= to_char(?,'yyyy-MM-dd') and to_char(END_TIME, 'yyyy-MM-dd') <= to_char(?,'yyyy-MM-dd')))";
			argList.add(startTime);
			argList.add(startTime);
			argList.add(endTime);
			argList.add(endTime);
			argList.add(startTime);
			argList.add(endTime);
		}
		
		return query(sql,argList.toArray(new Object[]{}),new MultiRow());
	}

	@Override
	public boolean isExistConflict(String unitId, String deptId,String id,
			Date startTime, Date endTime) {
		String sql="select count(1) from office_work_outline where  unit_id = ? and is_deleted = 0 and ((calendar_time <= ? and end_time >= ?) or (calendar_time <= ? and end_time >= ?) or(calendar_time >= ? and end_time <= ?))";
		List<Object> argList=new ArrayList<Object>();
		argList.add(unitId);
		argList.add(startTime);
		argList.add(startTime);
		argList.add(endTime);
		argList.add(endTime);
		argList.add(startTime);
		argList.add(endTime);
		if(StringUtils.isNotBlank(deptId)){
			sql+=" and create_dept = ? ";
			argList.add(deptId);
		}
		if(StringUtils.isNotBlank(id)){
			sql+=" and id <> ? ";
			argList.add(id);
		}
		int i=0;
		i=queryForInt(sql, argList.toArray(new Object[0]));
		if(i>0){
			return true;
		}
		return false;
	}

	@Override
	public List<OfficeWorkOutline> getOfficeWorkOutlineByDay(String unitId,
			String deptId, Date date) {
		String sql="select * from office_work_outline where unit_id = ? and is_deleted = 0 ";
		List<Object> argList=new ArrayList<Object>();
		argList.add(unitId);
		if(StringUtils.isNotBlank(deptId)){
			sql+=" and create_dept = ? ";
			argList.add(deptId);
		}
		if(date!=null){
			sql+=" and (to_char(calendar_time,'yyyy-MM-dd') <= to_char(?,'yyyy-MM-dd') and to_char(end_time,'yyyy-MM-dd') >= to_char(?,'yyyy-MM-dd'))";
			argList.add(date);
			argList.add(date);
		}
		sql+=" order by calendar_time desc ";
		return query(sql, argList.toArray(new Object[]{}), new MultiRow());
	}

	@Override
	public List<OfficeWorkOutline> getOfficeWorkOutlineByPeriodOfDay(
			String unitId, String deptId, Date date, int period) {
		String sql=" select * from office_work_outline where unit_id = ? and is_deleted = 0 ";
		List<Object> argList=new ArrayList<Object>();
		argList.add(unitId);
		if(StringUtils.isNotBlank(deptId)){
			sql+=" and create_dept = ? ";
			argList.add(deptId);
		}
		if(date!=null){
			sql+=" and (to_char(calendar_time,'yyyy-MM-dd') <= to_char(?,'yyyy-MM-dd') and to_char(end_time,'yyyy-MM-dd') >= to_char(?,'yyyy-MM-dd'))  ";
			argList.add(date);
			argList.add(date);
		}
		if(Integer.valueOf(period)!=null){
			sql+=" and period = ? ";
			argList.add(period);
		}
		sql+=" order by calendar_time desc";
		return query(sql, argList.toArray(new Object[]{}), new MultiRow());
	}

	@Override
	public List<OfficeWorkOutline> getOfficeWorklinesByMonth(String unitId,
			String deptId,Date firstDay,Date lastDay) {
		String sql="select * from office_work_outline where unit_id = ? and is_deleted = 0 ";
		List<Object> argList=new ArrayList<Object>();
		argList.add(unitId);
		if(StringUtils.isNotBlank(deptId)){
			sql+=" and create_dept = ? ";
			argList.add(deptId);
		}
		if(firstDay!=null&&lastDay!=null){
			sql+=" and ((to_char(CALENDAR_TIME,'yyyy-MM-dd')<= to_char(?,'yyyy-MM-dd') and to_char(END_TIME,'yyyy-MM-dd')>= to_char(?,'yyyy-MM-dd'))"
				+ " or (to_char(CALENDAR_TIME,'yyyy-mm-dd')<= to_char(?,'yyyy-MM-dd') and to_char(END_TIME,'yyyy-MM-dd') >= to_char(?,'yyyy-MM-dd'))"
				+ " or (to_char(CALENDAR_TIME,'yyyy-MM-dd') >= to_char(?,'yyyy-MM-dd') and to_char(END_TIME, 'yyyy-MM-dd') <= to_char(?,'yyyy-MM-dd')))";
			argList.add(firstDay);
			argList.add(firstDay);
			argList.add(lastDay);
			argList.add(lastDay);
			argList.add(firstDay);
			argList.add(lastDay);
		}
		sql+=" order by calendar_time,period";
		
		return query(sql, argList.toArray(new Object[]{}),new MultiRow());
	}

	@Override
	public List<OfficeWorkOutline> getOfficeWorklineBy(String unitId,
			String deptId, String srt, String end) {
		String sql="select * from office_work_outline where unit_id = ? and is_deleted = 0 "
		+ " and ((to_char(CALENDAR_TIME,'yyyy-MM-dd')<= ? and to_char(END_TIME,'yyyy-MM-dd')>=?)"
		+ " or (to_char(CALENDAR_TIME,'yyyy-mm-dd')<=? and to_char(END_TIME,'yyyy-MM-dd') >=?)"
		+ " or (to_char(CALENDAR_TIME,'yyyy-MM-dd') >= ? and to_char(END_TIME, 'yyyy-MM-dd') <= ?))";
		sql+="order by CALENDAR_TIME, period";
		return query(sql, new Object[]{unitId,srt,srt,end,end,srt,end},new MultiRow());
	}
}
