package net.zdsoft.office.teacherAttendance.dao.impl;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.aspectj.apache.bcel.generic.Type;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.constant.AttendanceConstants;
import net.zdsoft.office.teacherAttendance.dao.OfficeAttendanceColckLogDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckLog;
/**
 * 考勤打卡流水记录表
 * @author 
 * 
 */
public class OfficeAttendanceColckLogDaoImpl extends BaseDao<OfficeAttendanceColckLog> implements OfficeAttendanceColckLogDao{

	@Override
	public OfficeAttendanceColckLog setField(ResultSet rs) throws SQLException{
		OfficeAttendanceColckLog officeAttendanceColckLog = new OfficeAttendanceColckLog();
		officeAttendanceColckLog.setId(rs.getString("id"));
		officeAttendanceColckLog.setUnitId(rs.getString("unit_id"));
		officeAttendanceColckLog.setUserId(rs.getString("user_id"));
		officeAttendanceColckLog.setAttenceDate(rs.getDate("attence_date"));
		officeAttendanceColckLog.setIsHoliday(rs.getBoolean("is_holiday"));
		officeAttendanceColckLog.setType(rs.getString("type"));
		officeAttendanceColckLog.setLogType(rs.getString("log_type"));
		officeAttendanceColckLog.setClockState(rs.getString("clock_state"));
		officeAttendanceColckLog.setClockTime(rs.getTimestamp("clock_time"));
		officeAttendanceColckLog.setClockTime(rs.getTimestamp("clock_time"));
		officeAttendanceColckLog.setAttendanceTime(rs.getString("attendance_time"));
		officeAttendanceColckLog.setClockPlace(rs.getString("clock_place"));
		officeAttendanceColckLog.setLatitude(rs.getString("latitude"));
		officeAttendanceColckLog.setLongitude(rs.getString("longitude"));
		officeAttendanceColckLog.setRemark(rs.getString("remark"));
		officeAttendanceColckLog.setCreationTime(rs.getTimestamp("create_time"));
		return officeAttendanceColckLog;
	}

	@Override
	public OfficeAttendanceColckLog save(OfficeAttendanceColckLog officeAttendanceColckLog){
		String sql = "insert into office_attendance_colck_log(id, unit_id, user_id, attence_date,is_holiday, type, log_type, clock_state, clock_time,attendance_time, clock_place, latitude, longitude, remark, create_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeAttendanceColckLog.getId())){
			officeAttendanceColckLog.setId(createId());
		}
		if(officeAttendanceColckLog.getCreationTime()==null){
			officeAttendanceColckLog.setCreationTime(new Date());
		}
		if(officeAttendanceColckLog.getAttenceDate()!=null){
			officeAttendanceColckLog.setAttenceDate(DateUtils.string2Date(DateUtils.date2String(officeAttendanceColckLog.getAttenceDate()), "yyyy-MM-dd"));
		}
		Object[] args = new Object[]{
			officeAttendanceColckLog.getId(), officeAttendanceColckLog.getUnitId(), 
			officeAttendanceColckLog.getUserId(), officeAttendanceColckLog.getAttenceDate(), officeAttendanceColckLog.getIsHoliday(),
			officeAttendanceColckLog.getType(), officeAttendanceColckLog.getLogType(), 
			officeAttendanceColckLog.getClockState(), officeAttendanceColckLog.getClockTime(), officeAttendanceColckLog.getAttendanceTime(),
			officeAttendanceColckLog.getClockPlace(), officeAttendanceColckLog.getLatitude(), 
			officeAttendanceColckLog.getLongitude(), officeAttendanceColckLog.getRemark(), officeAttendanceColckLog.getCreationTime()
		};
		update(sql, args, new int[]{
				Types.CHAR,Types.CHAR,
				Types.CHAR, Types.DATE,Types.INTEGER,
				Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,
				Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP
		});
		return officeAttendanceColckLog;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_attendance_colck_log where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeAttendanceColckLog officeAttendanceColckLog){
		String sql = "update office_attendance_colck_log set unit_id = ?, user_id = ?, attence_date = ?,is_holiday=?, type = ?, log_type = ?, clock_state = ?, clock_time = ?, attendance_time=?, clock_place = ?, latitude = ?, longitude = ?, remark=?, create_time = ? where id = ?";
		if(officeAttendanceColckLog.getAttenceDate()!=null){
			officeAttendanceColckLog.setAttenceDate(DateUtils.string2Date(DateUtils.date2String(officeAttendanceColckLog.getAttenceDate()), "yyyy-MM-dd"));
		}
		Object[] args = new Object[]{
			officeAttendanceColckLog.getUnitId(), officeAttendanceColckLog.getUserId(), 
			officeAttendanceColckLog.getAttenceDate(), officeAttendanceColckLog.getIsHoliday(), officeAttendanceColckLog.getType(), 
			officeAttendanceColckLog.getLogType(), officeAttendanceColckLog.getClockState(), 
			officeAttendanceColckLog.getClockTime(), officeAttendanceColckLog.getAttendanceTime(), officeAttendanceColckLog.getClockPlace(), 
			officeAttendanceColckLog.getLatitude(), officeAttendanceColckLog.getLongitude(), officeAttendanceColckLog.getRemark(),
			officeAttendanceColckLog.getCreationTime(), officeAttendanceColckLog.getId()
		};
		return update(sql, args, new int[]{
				Types.CHAR,Types.CHAR, 
				Types.DATE,Types.INTEGER,Types.VARCHAR,
				Types.VARCHAR,Types.VARCHAR,
				Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
				Types.TIMESTAMP,Types.CHAR
		});
	}

	@Override
	public OfficeAttendanceColckLog getOfficeAttendanceColckLogById(String id){
		String sql = "select * from office_attendance_colck_log where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public List<OfficeAttendanceColckLog> listOfficeAttendanceColckLogByDateAndState(String unitId,Date startDate,Date endDate,
			String state, Date date) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer("select l.* from office_attendance_colck_log l where  l.unit_id =?   ");
		List<Object> argOb = new ArrayList<Object>();
		argOb.add(unitId);
		if(StringUtils.isNotEmpty(state)){
			sb.append(" and l.clock_state = ?  ");
			argOb.add(state);
		}
		if(date != null){
			sb.append(" and l.attence_date = ? ");
			argOb.add(date);
		}else{
			if(startDate != null){
				sb.append(" and l.attence_date >= ? ");
				argOb.add(startDate);
			}
			if(endDate != null){
				sb.append(" and l.attence_date <= ? ");
				argOb.add(endDate);
			}
		}
		
		return query(sb.toString(), argOb.toArray(), new MultiRow());
	}
	public Map<String,String>  getOfficeAttendanceColckLogByDateAndState(String state, Date date  ,String unitId ,Date startDate,Date endDate){
		StringBuffer sb = new StringBuffer("select l.user_id,l.attence_date, count(l.id) as sum from office_attendance_colck_log l where  l.unit_id =?  ");
		List<Object> args  = new ArrayList<Object>();
		args.add(unitId);
		if(StringUtils.isNotEmpty(state)){
			sb.append(" and l.clock_state = ?  ");
			args.add(state);
		}
		if(date != null){
			sb.append(" and l.attence_date = ? ");
			args.add(date);
		}else{
			if(startDate != null){
				sb.append(" and l.attence_date >= ? ");
				args.add(startDate);
			}
			if(endDate != null){
				sb.append(" and l.attence_date <= ? ");
				args.add(endDate);
			}
		}
		//统计打外勤卡 
		if(AttendanceConstants.ATTENDANCE_CLOCK_STATE_3.equals(state)){
			sb.append("  group by l.user_id ,l.attence_date having count(*) <=2 ");
		//统计正常打卡
		}else if(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT.equals(state)){
			sb.append("  group by l.user_id ,l.attence_date  having count(*)=2 ");
		
		}
		final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		if(date != null){
			
			return queryForMap(sb.toString(),args.toArray(),new MapRowMapper<String,String>(){
				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
				throws SQLException {
					// TODO Auto-generated method stub
					return rs.getString("user_id");
				}
				
				@Override
				public String mapRowValue(ResultSet rs, int rowNum)
				throws SQLException {
					// TODO Auto-generated method stub
					return String.valueOf(rs.getInt("sum"));
				}
			});
		}else{
			return queryForMap(sb.toString(),args.toArray(),new MapRowMapper<String,String>(){
				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
				throws SQLException {
					// TODO Auto-generated method stub
					String d = formate.format(rs.getDate("attence_date"));
					return rs.getString("user_id") + "_" + d;
				}
				
				@Override
				public String mapRowValue(ResultSet rs, int rowNum)
				throws SQLException {
					// TODO Auto-generated method stub
					return String.valueOf(rs.getInt("sum"));
				}
			});
		}
	}
	public Map<String,String> getMissingCard(Date date ,String unitId ,String attendanceState ,Date startDate,Date endDate){
		StringBuffer sb = new StringBuffer("select l.user_id ,l.attence_date,  count(l.id) as sum from office_attendance_colck_log l   where   l.unit_id = ?  ");
		List<Object> args  = new ArrayList<Object>();
		args.add(unitId);

		if(date != null){
			sb.append(" and l.attence_date = ? ");
			args.add(date);
		}else{
			if(startDate != null){
				sb.append(" and l.attence_date >= ? ");
				args.add(startDate);
			}
			if(endDate != null){
				sb.append(" and l.attence_date >= ? ");
				args.add(endDate);
			}
		}
		//统计缺卡信息 
		if(AttendanceConstants.ATTENDANCE_CLOCK_STATE_98.equals(attendanceState)){
			sb.append("group by l.user_id ,l.attence_date having  count(l.id) = 1 ");
			//统计至少有一次打卡
		}else{
			sb.append(" group by l.user_id ,l.attence_date having count(l.id)>=1 ");
		}
		final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        if(date != null){
			
			return queryForMap(sb.toString(),args.toArray(),new MapRowMapper<String,String>(){
				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
				throws SQLException {
					// TODO Auto-generated method stub
					return rs.getString("user_id");
				}
				
				@Override
				public String mapRowValue(ResultSet rs, int rowNum)
				throws SQLException {
					// TODO Auto-generated method stub
					return String.valueOf(rs.getInt("sum"));
				}
			});
		}else{
			return queryForMap(sb.toString(),args.toArray(),new MapRowMapper<String,String>(){
				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
				throws SQLException {
					// TODO Auto-generated method stub
					String d = formate.format(rs.getDate("attence_date"));
					return rs.getString("user_id") + "_" + d;
				}
				
				@Override
				public String mapRowValue(ResultSet rs, int rowNum)
				throws SQLException {
					// TODO Auto-generated method stub
					return String.valueOf(rs.getInt("sum"));
				}
			});
		}
	}


			
}