package net.zdsoft.office.schedule.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.schedule.dao.OfficeCalendarDao;
import net.zdsoft.office.schedule.entity.OfficeCalendar;

import org.apache.commons.lang3.StringUtils;
/**
 * 日程安排表
 * @author 
 * 
 */
public class OfficeCalendarDaoImpl extends BaseDao<OfficeCalendar> implements OfficeCalendarDao{

	@Override
	public OfficeCalendar setField(ResultSet rs) throws SQLException{
		OfficeCalendar officeCalendar = new OfficeCalendar();
		officeCalendar.setId(rs.getString("id"));
		officeCalendar.setOperator(rs.getString("operator"));
		officeCalendar.setUnitId(rs.getString("unit_id"));
		officeCalendar.setPlace(rs.getString("place"));
		officeCalendar.setContent(rs.getString("content"));
		officeCalendar.setRemark(rs.getString("remark"));
		officeCalendar.setDeleted(rs.getBoolean("is_deleted"));
		officeCalendar.setModifyTime(rs.getTimestamp("modify_time"));
		officeCalendar.setCalendarTime(rs.getTimestamp("calendar_time"));
		officeCalendar.setIsSmsAlarm(rs.getBoolean("is_sms_alarm"));
		officeCalendar.setSmsAlarmTime(rs.getTimestamp("sms_alarm_time"));
		officeCalendar.setVersion(rs.getString("version"));
		officeCalendar.setHalfDays(rs.getInt("half_days"));
		officeCalendar.setEndTime(rs.getTimestamp("end_time"));
		officeCalendar.setPeriod(rs.getInt("period"));
		officeCalendar.setCreatorType(rs.getInt("creator_type"));
		officeCalendar.setCreator(rs.getString("creator"));
		return officeCalendar;
	}

	@Override
	public OfficeCalendar save(OfficeCalendar officeCalendar){
		String sql = "insert into office_calendar(id, operator, unit_id, place, content, remark, is_deleted, modify_time, calendar_time, is_sms_alarm, sms_alarm_time, version, half_days, end_time, period, creator_type, creator) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeCalendar.getId())){
			officeCalendar.setId(createId());
		}
		Object[] args = new Object[]{
			officeCalendar.getId(), officeCalendar.getOperator(), 
			officeCalendar.getUnitId(), officeCalendar.getPlace(), 
			officeCalendar.getContent(), 
			officeCalendar.getRemark(), officeCalendar.getIsDeleted(), 
			officeCalendar.getModifyTime(), officeCalendar.getCalendarTime(), 
			officeCalendar.getIsSmsAlarm(), officeCalendar.getSmsAlarmTime(), 
			officeCalendar.getVersion(), officeCalendar.getHalfDays(), 
			officeCalendar.getEndTime(), officeCalendar.getPeriod(), 
			officeCalendar.getCreatorType(), officeCalendar.getCreator()
		};
		update(sql, args);
		return officeCalendar;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_calendar where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeCalendar officeCalendar){
		String sql = "update office_calendar set operator = ?, unit_id = ?, place = ?, content = ?, remark = ?, is_deleted = ?, modify_time = ?, calendar_time = ?, is_sms_alarm = ?, sms_alarm_time = ?, half_days = ?, end_time = ?, period = ?, creator_type = ?, creator = ? where id = ?";
		Object[] args = new Object[]{
			officeCalendar.getOperator(), officeCalendar.getUnitId(), 
			officeCalendar.getPlace(),  
			officeCalendar.getContent(), officeCalendar.getRemark(), 
			officeCalendar.getIsDeleted(), officeCalendar.getModifyTime(), 
			officeCalendar.getCalendarTime(), officeCalendar.getIsSmsAlarm(), 
			officeCalendar.getSmsAlarmTime(), 
			officeCalendar.getHalfDays(), officeCalendar.getEndTime(), 
			officeCalendar.getPeriod(), officeCalendar.getCreatorType(), 
			officeCalendar.getCreator(), officeCalendar.getId()
		};
		return update(sql, args);
	}
	
	public boolean checkExistsTimeCross(OfficeCalendar officeCalendar){
		StringBuffer sql = new StringBuffer("select count(1) from office_calendar where creator=? and creator_type=? and is_deleted=0");
//		
//		and ((CALENDAR_TIME<= to_date(?,'yyyy-MM-dd HH24:mi') 
//				and END_TIME>=to_date('2015-10-30 12:00','yyyy-MM-dd HH24:mi'))
//				or (CALENDAR_TIME<=to_date('2015-10-30 14:26','yyyy-MM-dd HH24:mi') 
//				and END_TIME >=to_date('2015-10-30 14:26','yyyy-MM-dd HH24:mi'))
//
//				or (CALENDAR_TIME >= to_date('2015-10-30 12:00','yyyy-MM-dd HH24:mi') 
//				and END_TIME <= to_date('2015-10-30 14:26','yyyy-MM-dd HH24:mi')))
		sql.append(" and ((CALENDAR_TIME<= str_to_date(?,'%Y-%m-%d %H:%i') and END_TIME>=str_to_date(?,'%Y-%m-%d %H:%i'))");
		sql.append(" or (CALENDAR_TIME<=str_to_date(?,'%Y-%m-%d %H:%i') and END_TIME >=str_to_date(?,'%Y-%m-%d %H:%i'))");
		sql.append(" or (CALENDAR_TIME >= str_to_date(?,'%Y-%m-%d %H:%i') and END_TIME <= str_to_date(?,'%Y-%m-%d %H:%i')))");
		List<Object> args = new ArrayList<Object>();
		args.add(officeCalendar.getCreator());
		args.add(officeCalendar.getCreatorType());
		String st = DateUtils.date2String(officeCalendar.getCalendarTime(), "yyyy-MM-dd HH:mm");
		String et = DateUtils.date2String(officeCalendar.getEndTime(), "yyyy-MM-dd HH:mm");
		args.add(st);
		args.add(st);
		args.add(et);
		args.add(et);
		args.add(st);
		args.add(et);
		if(StringUtils.isNotEmpty(officeCalendar.getId())){
			sql.append(" and id <> ?");
			args.add(officeCalendar.getId());
		}
		int count = queryForInt(sql.toString(), args.toArray());
		if(count > 0){
			return true;
		}
		return false;
	}

	@Override
	public OfficeCalendar getOfficeCalendarById(String id){
		String sql = "select * from office_calendar where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	public List<OfficeCalendar> getOfficeCalendarsBy(String unitId, String calendarTime, 
			String endTime, int type, String[] creator){
		String sql = "select * from office_calendar where unit_id=? and creator_type=? and is_deleted=0"
				+ " and ((to_char(CALENDAR_TIME,'yyyy-MM-dd')<= ? and to_char(END_TIME,'yyyy-MM-dd')>=?)"
				+ " or (to_char(CALENDAR_TIME,'yyyy-mm-dd')<=? and to_char(END_TIME,'yyyy-MM-dd') >=?)"
				+ " or (to_char(CALENDAR_TIME,'yyyy-MM-dd') >= ? and to_char(END_TIME, 'yyyy-MM-dd') <= ?))"
				+ " and creator IN";
		return queryForInSQL(sql, new Object[]{unitId, type, calendarTime, calendarTime,
				endTime, endTime, calendarTime, endTime}, creator, new MultiRow()," order by CALENDAR_TIME, period");
	}
	
	public List<OfficeCalendar> getOfficeCalendarsByDate(String unitId, String calendarTime, 
			int period, int type, String[] creator){
		String sql = "select * from office_calendar where unit_id=? and creator_type=? and is_deleted=0"
				+ " and to_char(CALENDAR_TIME,'yyyy-MM-dd') <= ? and to_char(END_TIME,'yyyy-MM-dd')>=?";
		if(period > 0){
			sql += " and (period=? or period=0) and creator IN";
			return queryForInSQL(sql, new Object[]{unitId, type, calendarTime, calendarTime, period}, 
					creator, new MultiRow()," order by CALENDAR_TIME, period");
		}
		sql += " and creator IN";
		return queryForInSQL(sql, new Object[]{unitId, type, calendarTime, calendarTime}, 
				creator, new MultiRow()," order by CALENDAR_TIME, period");
	}

	@Override
	public Map<String, OfficeCalendar> getOfficeCalendarMapByIds(String[] ids){
		String sql = "select * from office_calendar where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeCalendar> getOfficeCalendarList(){
		String sql = "select * from office_calendar";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeCalendar> getOfficeCalendarPage(Pagination page){
		String sql = "select * from office_calendar";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeCalendar> getOfficeCalendarByUnitIdList(String unitId){
		String sql = "select * from office_calendar where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeCalendar> getOfficeCalendarByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_calendar where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeCalendar> getOfficeCalendarListPage(String unitId,
			String startTime, String endTime, String[] userIds, Pagination page) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from office_calendar where unit_id = ? ");
		args.add(unitId);
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" and str_to_date(?, '%Y-%m-%d') <= str_to_date(date_format(calendar_time, '%Y-%m-%d'), '%Y-%m-%d')");
			args.add(startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and str_to_date(?, '%Y-%m-%d') >= str_to_date(date_format(calendar_time, '%Y-%m-%d'), '%Y-%m-%d')");
			args.add(endTime);
		}
		sql.append(" and creator in ");
		return queryForInSQL(sql.toString(), args.toArray(), userIds, new MultiRow(), " order by calendar_time desc ", page);
	}
	
	@Override
	public List<OfficeCalendar> getCalendarListByCondition(String unitId, String[] userIds, 
			String startTime, String endTime, Pagination page){
		List<Object> argsList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from office_calendar where unit_id = ? ");
		argsList.add(unitId);
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" and str_to_date(?, '%Y-%m-%d') >= str_to_date(date_format(calendar_time, '%Y-%m-%d'), '%Y-%m-%d')");
			argsList.add(startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" and str_to_date(?, '%Y-%m-%d') <= str_to_date(date_format(calendar_time, '%Y-%m-%d'), '%Y-%m-%d')");
			argsList.add(endTime);
		}
		sql.append(" and creator in");
		if(page != null)
			return queryForInSQL(sql.toString(), argsList.toArray(), 
				userIds, new MultiRow()," order by calendar_time desc", page);
		else
			return queryForInSQL(sql.toString(), argsList.toArray(), 
				userIds, new MultiRow()," order by calendar_time desc");
	}
}