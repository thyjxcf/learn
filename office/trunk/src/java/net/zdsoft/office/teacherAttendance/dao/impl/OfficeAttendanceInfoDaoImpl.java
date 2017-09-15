package net.zdsoft.office.teacherAttendance.dao.impl;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.ArrayUtils;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.constant.AttendanceConstants;
import net.zdsoft.office.teacherAttendance.dao.OfficeAttendanceInfoDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckLog;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceInfo;
/**
 * 考勤打卡信息
 * @author 
 * 
 */
public class OfficeAttendanceInfoDaoImpl extends BaseDao<OfficeAttendanceInfo> implements OfficeAttendanceInfoDao{

	@Override
	public OfficeAttendanceInfo setField(ResultSet rs) throws SQLException{
		OfficeAttendanceInfo officeAttendanceInfo = new OfficeAttendanceInfo();
		officeAttendanceInfo.setId(rs.getString("id"));
		officeAttendanceInfo.setUnitId(rs.getString("unit_id"));
		officeAttendanceInfo.setUserId(rs.getString("user_id"));
		officeAttendanceInfo.setAttenceDate(rs.getDate("attence_date"));
		officeAttendanceInfo.setIsHoliday(rs.getBoolean("is_holiday"));
		officeAttendanceInfo.setType(rs.getString("type"));
		officeAttendanceInfo.setLogType(rs.getString("log_type"));
		officeAttendanceInfo.setClockState(rs.getString("clock_state"));
		officeAttendanceInfo.setClockTime(rs.getTimestamp("clock_time"));
		officeAttendanceInfo.setAttendanceTime(rs.getString("attendance_time"));
		officeAttendanceInfo.setClockPlace(rs.getString("clock_place"));
		officeAttendanceInfo.setLatitude(rs.getString("latitude"));
		officeAttendanceInfo.setLongitude(rs.getString("longitude"));
		officeAttendanceInfo.setRemark(rs.getString("remark"));
		officeAttendanceInfo.setCreationTime(rs.getTimestamp("create_time"));
		officeAttendanceInfo.setModifyTime(rs.getTimestamp("modify_time"));
		return officeAttendanceInfo;
	}

	@Override
	public OfficeAttendanceInfo save(OfficeAttendanceInfo officeAttendanceInfo){
		String sql = "insert into office_attendance_info(id, unit_id, user_id, attence_date, is_holiday, type, log_type, clock_state, clock_time,attendance_time, clock_place, latitude, longitude, remark, create_time, modify_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeAttendanceInfo.getId())){
			officeAttendanceInfo.setId(createId());
		}
		if(officeAttendanceInfo.getCreationTime()==null){
			officeAttendanceInfo.setCreationTime(new Date());
		}
		if(officeAttendanceInfo.getAttenceDate()!=null){
			officeAttendanceInfo.setAttenceDate(DateUtils.string2Date(DateUtils.date2String(officeAttendanceInfo.getAttenceDate()), "yyyy-MM-dd"));
		}
		Object[] args = new Object[]{
			officeAttendanceInfo.getId(), officeAttendanceInfo.getUnitId(), 
			officeAttendanceInfo.getUserId(), officeAttendanceInfo.getAttenceDate(), officeAttendanceInfo.getIsHoliday(),
			officeAttendanceInfo.getType(), officeAttendanceInfo.getLogType(), officeAttendanceInfo.getClockState(), 
			officeAttendanceInfo.getClockTime(), officeAttendanceInfo.getAttendanceTime(), officeAttendanceInfo.getClockPlace(), 
			officeAttendanceInfo.getLatitude(), officeAttendanceInfo.getLongitude(), officeAttendanceInfo.getRemark(),
			officeAttendanceInfo.getCreationTime(), officeAttendanceInfo.getModifyTime()
		};
		update(sql, args, new int[]{
				Types.CHAR,Types.CHAR,
				Types.CHAR,Types.DATE,Types.INTEGER,
				Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
				Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
				Types.TIMESTAMP,Types.TIMESTAMP
		});
		return officeAttendanceInfo;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_attendance_info where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeAttendanceInfo officeAttendanceInfo){
		String sql = "update office_attendance_info set unit_id = ?, user_id = ?, attence_date = ?, is_holiday = ?, type = ?, log_type = ?, clock_state = ?, clock_time = ?, attendance_time = ?, clock_place = ?, latitude = ?, longitude = ?, remark = ?, create_time = ?, modify_time = ? where id = ?";
		if(officeAttendanceInfo.getAttenceDate()!=null){
			officeAttendanceInfo.setAttenceDate(DateUtils.string2Date(DateUtils.date2String(officeAttendanceInfo.getAttenceDate()), "yyyy-MM-dd"));
		}
		Object[] args = new Object[]{
			officeAttendanceInfo.getUnitId(), officeAttendanceInfo.getUserId(), 
			officeAttendanceInfo.getAttenceDate(), officeAttendanceInfo.getIsHoliday(), 
			officeAttendanceInfo.getType(), officeAttendanceInfo.getLogType(),
			officeAttendanceInfo.getClockState(), officeAttendanceInfo.getClockTime(), officeAttendanceInfo.getAttendanceTime(),
			officeAttendanceInfo.getClockPlace(), officeAttendanceInfo.getLatitude(), 
			officeAttendanceInfo.getLongitude(), officeAttendanceInfo.getRemark(), officeAttendanceInfo.getCreationTime(), 
			officeAttendanceInfo.getModifyTime(), officeAttendanceInfo.getId()
		};
		return update(sql, args,new int[]{
				Types.CHAR,Types.CHAR,
				Types.DATE,Types.INTEGER,
				Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,
				Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP,
				Types.TIMESTAMP,Types.CHAR
		});
	}

	@Override
	public OfficeAttendanceInfo getOfficeAttendanceInfoById(String id){
		String sql = "select * from office_attendance_info where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	@Override
	public Map<String,OfficeAttendanceInfo> getOfficeAttendanceInfoByUnitIdMap(String unitId,String[] userIds,String startTimeStr,String endTimeStr){
		String sql="select * from office_attendance_info where unit_id=? and attence_date between to_date(?,'yyyy-MM-dd') and to_date(?,'yyyy-MM-dd')";
		if(userIds!=null && userIds.length>0){
			sql+=" and user_id in";
			return queryForInSQL(sql, new Object[]{unitId,startTimeStr,endTimeStr},userIds, new MapRowMapper<String,OfficeAttendanceInfo>(){
				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("user_id")+"_"+DateUtils.date2String(rs.getTimestamp("attence_date"),"yyyy-MM-dd")+"_"+rs.getString("type");
				}
				@Override
				public OfficeAttendanceInfo mapRowValue(ResultSet rs, int rowNum)
						throws SQLException {
					return setField(rs);
				}
				
			});
		}else{
			return queryForMap(sql, new Object[]{unitId,startTimeStr,endTimeStr} , new MapRowMapper<String,OfficeAttendanceInfo>(){
				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("user_id")+"_"+DateUtils.date2String(rs.getTimestamp("attence_date"))+"_"+rs.getString("type");
				}

				@Override
				public OfficeAttendanceInfo mapRowValue(ResultSet rs, int rowNum)
						throws SQLException {
					return setField(rs);
				}
			});
		}
	}
	
	@Override
	public List<OfficeAttendanceInfo> getListByUserIdDate(String userId, Date date){
		String sql = "select * from office_attendance_info where user_id = ? and to_char(attence_date,'yyyy-mm-dd') = ? order by type";
		return query(sql, new Object[]{userId, DateUtils.date2String(date, "yyyy-MM-dd")}, new MultiRow());
	}
	
	@Override
	public OfficeAttendanceInfo getItemByAdateType(String userId, Date attenceTime, String type){
		String sql = "select * from office_attendance_info where user_id = ? and to_char(attence_date,'yyyy-mm-dd') = ? and type = ?";
		return query(sql, new Object[]{userId, DateUtils.date2String(attenceTime, "yyyy-MM-dd"), type}, new SingleRow());
	}
	
	@Override
	public List<OfficeAttendanceInfo> getListByStartAndEndTime(String userId, Date startTime, Date endTime){
		String sql = "select * from office_attendance_info where user_id = ? and to_char(attence_date,'yyyy-mm-dd') >= ? and to_char(attence_date,'yyyy-mm-dd') <= ? order by attence_date, type";
		return query(sql, new Object[]{userId, DateUtils.date2String(startTime, "yyyy-MM-dd"), DateUtils.date2String(endTime, "yyyy-MM-dd")}, new MultiRow());
	}
	
	public List<OfficeAttendanceInfo> listOfficeAttendanceInfoByDateAndState(String unitId,Date startDate,Date endDate,
			String state, Date date) {
		
		StringBuffer sb = new StringBuffer("select * from office_attendance_info  where  unit_id =?   ");
		List<Object> argOb = new ArrayList<Object>();
		argOb.add(unitId);
		if(StringUtils.isNotEmpty(state)){
			sb.append(" and clock_state = ?  ");
			argOb.add(state);
		}
		if(date != null){
			sb.append(" and attence_date = ? ");
			argOb.add(date);
		}else{
			if(startDate != null){
				sb.append(" and attence_date >= ? ");
				argOb.add(startDate);
			}
			if(endDate != null){
				sb.append(" and attence_date <= ? ");
				argOb.add(endDate);
			}
		}
		
		return query(sb.toString(), argOb.toArray(), new MultiRow());
	}
	
	public Map<String,String>  getOfficeAttendanceInfoByDateAndState(String state, Date date  ,String unitId ,Date startDate,Date endDate){
		StringBuffer sb = new StringBuffer("select user_id,attence_date, count(id) as sum from office_attendance_info where  unit_id =?  ");
		List<Object> args  = new ArrayList<Object>();
		args.add(unitId);
		if(StringUtils.isNotEmpty(state)){
			sb.append(" and clock_state = ?  ");
			args.add(state);
		}
		if(date != null){
			sb.append(" and attence_date = ? ");
			args.add(date);
		}else{
			if(startDate != null){
				sb.append(" and attence_date >= ? ");
				args.add(startDate);
			}
			if(endDate != null){
				sb.append(" and attence_date <= ? ");
				args.add(endDate);
			}
		}
		//统计打外勤卡 
		if(AttendanceConstants.ATTENDANCE_CLOCK_STATE_3.equals(state)){
			sb.append("  group by user_id ,attence_date having count(*) <=2 ");
		//统计正常打卡
		}else if(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT.equals(state)){
			sb.append("  group by user_id ,attence_date  having count(*)=2 ");
		
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
		StringBuffer sb = new StringBuffer("select user_id ,attence_date,  count(id) as sum from office_attendance_info   where   unit_id = ?  ");
		List<Object> args  = new ArrayList<Object>();
		args.add(unitId);

		if(date != null){
			sb.append(" and attence_date = ? ");
			args.add(date);
		}else{
			if(startDate != null){
				sb.append(" and attence_date >= ? ");
				args.add(startDate);
			}
			if(endDate != null){
				sb.append(" and attence_date <= ? ");
				args.add(endDate);
			}
		}
		//统计缺卡信息 
		if(AttendanceConstants.ATTENDANCE_CLOCK_STATE_98.equals(attendanceState)){
			sb.append("group by user_id ,attence_date having  count(id) = 1 ");
			//统计至少有一次打卡
		}else{
			sb.append(" group by user_id ,attence_date having count(id)>=1 ");
		}
		final SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		
		Map<String,String> map;
        if(date != null){
			
			map = queryForMap(sb.toString(),args.toArray(),new MapRowMapper<String,String>(){
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
			map = queryForMap(sb.toString(),args.toArray(),new MapRowMapper<String,String>(){
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
        if( map == null){
        	map = new HashMap<String ,String>();
        }
        
        return map;
	}
}