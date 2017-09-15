package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.office.dailyoffice.dao.OfficeAttendanceStuRecordDao;
import net.zdsoft.office.dailyoffice.entity.OfficeAttendanceStuRecord;

import org.apache.commons.lang3.StringUtils;

/**
 * office_attendance_stu_record 
 * @author 
 * 
 */
public class OfficeAttendanceStuRecordDaoImpl extends BaseDao<OfficeAttendanceStuRecord> implements OfficeAttendanceStuRecordDao{
	@Override
	public OfficeAttendanceStuRecord setField(ResultSet rs) throws SQLException{
		OfficeAttendanceStuRecord officeAttendanceStuRecord = new OfficeAttendanceStuRecord();
		officeAttendanceStuRecord.setId(rs.getString("id"));
		officeAttendanceStuRecord.setStudentCode(rs.getString("student_code"));
		officeAttendanceStuRecord.setStudentName(rs.getString("student_name"));
		officeAttendanceStuRecord.setCourseId(rs.getString("course_id"));
		officeAttendanceStuRecord.setDatestr(rs.getString("datestr"));
		officeAttendanceStuRecord.setTimestr(rs.getString("timestr"));
		officeAttendanceStuRecord.setStartPeriod(rs.getInt("start_period"));
		officeAttendanceStuRecord.setIsDk(rs.getString("is_dk"));
		officeAttendanceStuRecord.setRoomId(rs.getString("room_id"));
		officeAttendanceStuRecord.setControllerId(rs.getString("controller_id"));
		officeAttendanceStuRecord.setEndPeriod(rs.getInt("end_period"));
		officeAttendanceStuRecord.setIsRemind(rs.getString("is_remind"));
		officeAttendanceStuRecord.setSyncDatetime(rs.getDate("sync_datetime"));
		officeAttendanceStuRecord.setSyncVersion(rs.getInt("sync_version"));
		return officeAttendanceStuRecord;
	}
	
	public OfficeAttendanceStuRecord save(OfficeAttendanceStuRecord officeAttendanceStuRecord){
		String sql = "insert into office_attendance_stu_record(id, student_code, student_name, course_id, datestr, timestr, start_period, is_dk, room_id, controller_id, end_period, is_remind, sync_datetime, sync_version) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(officeAttendanceStuRecord.getId())){
			officeAttendanceStuRecord.setId(createId());
		}
		Object[] args = new Object[]{
			officeAttendanceStuRecord.getId(), officeAttendanceStuRecord.getStudentCode(), 
			officeAttendanceStuRecord.getStudentName(), officeAttendanceStuRecord.getCourseId(), 
			officeAttendanceStuRecord.getDatestr(), officeAttendanceStuRecord.getTimestr(), 
			officeAttendanceStuRecord.getStartPeriod(), officeAttendanceStuRecord.getIsDk(), 
			officeAttendanceStuRecord.getRoomId(), officeAttendanceStuRecord.getControllerId(), 
			officeAttendanceStuRecord.getEndPeriod(), officeAttendanceStuRecord.getIsRemind(), 
			officeAttendanceStuRecord.getSyncDatetime(), officeAttendanceStuRecord.getSyncVersion()
		};
		update(sql, args);
		return officeAttendanceStuRecord;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_attendance_stu_record where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(OfficeAttendanceStuRecord officeAttendanceStuRecord){
		String sql = "update office_attendance_stu_record set student_code = ?, student_name = ?, course_id = ?, datestr = ?, timestr = ?, start_period = ?, is_dk = ?, room_id = ?, controller_id = ?, end_period = ?, is_remind = ?, sync_datetime = ?, sync_version = ? where id = ?";
		Object[] args = new Object[]{
			officeAttendanceStuRecord.getStudentCode(), officeAttendanceStuRecord.getStudentName(), 
			officeAttendanceStuRecord.getCourseId(), officeAttendanceStuRecord.getDatestr(), 
			officeAttendanceStuRecord.getTimestr(), officeAttendanceStuRecord.getStartPeriod(), 
			officeAttendanceStuRecord.getIsDk(), officeAttendanceStuRecord.getRoomId(), 
			officeAttendanceStuRecord.getControllerId(), officeAttendanceStuRecord.getEndPeriod(), 
			officeAttendanceStuRecord.getIsRemind(), officeAttendanceStuRecord.getSyncDatetime(), 
			officeAttendanceStuRecord.getSyncVersion(), officeAttendanceStuRecord.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeAttendanceStuRecord getOfficeAttendanceStuRecordById(String id){
		String sql = "select * from office_attendance_stu_record where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	@Override
	public List<OfficeAttendanceStuRecord> getOfficeAttendanceStuRecord(
			String isDk, String isRemind) {
		String sql = "select * from office_attendance_stu_record where is_dk = ? and is_remind = ?";
		return query(sql, new Object[] {isDk, isRemind}, new MultiRow());
	}
	
	@Override
	public void batchUpdateIsRemind(String[] ids, String isRemind) {
		String sql = "update office_attendance_stu_record set is_remind = ? where id in";
		updateForInSQL(sql, new Object[] {isRemind}, ids);
	}
	
	@Override
	public int getCount(String courseId, String dateStr, int startPeriod,
			int endPeriod, String isDk) {
		String sql = "select count(distinct(student_code)) from office_attendance_stu_record where course_id = ? "
				+ "and dateStr = ? and start_period = ? and end_period = ?";
		if(StringUtils.isNotBlank(isDk)) {
			sql += " and is_dk ='" + isDk +"'";
		}
		return queryForInt(sql, new Object[] {courseId, dateStr, startPeriod, endPeriod});
	}
	
	@Override
	public List<OfficeAttendanceStuRecord> getList(String courseId,
			String dateStr, int startPeriod, int endPeriod, String isDk) {
		String sql = "select * from office_attendance_stu_record where course_id = ? "
				+ "and dateStr = ? and start_period = ? and end_period = ?";
		if(StringUtils.isNotBlank(isDk)) {
			sql += " and is_dk ='" + isDk +"'";
		}
		return query(sql, new Object[] {courseId, dateStr, startPeriod, endPeriod}, new MultiRow());
	}
	
	@Override
	public Map<String, Integer> getStuAttendanceCountMap(String courseId) {
		String sql = "select count(1) as num, b.student_code from (select a.student_code, a.datestr, a.end_period from office_attendance_stu_record a where a.course_id = ? and a.is_dk = '1' group by a.student_code, a.datestr, a.end_period) b group by b.student_code";
		return queryForMap(sql, new Object[] {courseId}, new MapRowMapper<String, Integer>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("student_code");
			}

			@Override
			public Integer mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getInt("num");
			}});
	}
	
	@Override
	public Map<String, Integer> getStuAttendanceCountMap(String[] stuCodes) {
		StringBuffer stuCodeSql = new StringBuffer();
		String stuCodeSqlStr = "('')";
		if(stuCodes != null && stuCodes.length > 0) {
			stuCodeSql.append("(");
			for(String stuCode : stuCodes) {
				stuCodeSql.append("'"+stuCode+"',");
			}
			stuCodeSqlStr = stuCodeSql.substring(0, stuCodeSql.length()-1);
			stuCodeSqlStr += ")";
		}
//		String sql = "select count(1) as num, student_code, course_id from office_attendance_stu_record where is_dk = '1' and student_code in";
//		return queryForInSQL(sql, new Object[] {}, stuCodes, new MapRowMapper<String, Integer>(){
//
//			@Override
//			public String mapRowKey(ResultSet rs, int rowNum)
//					throws SQLException {
//				return rs.getString("student_code")+"-"+rs.getString("course_id");
//			}
//
//			@Override
//			public Integer mapRowValue(ResultSet rs, int rowNum)
//					throws SQLException {
//				return rs.getInt("num");
//			}}, " group by student_code, course_id");
		
		String sql = "select count(1) as num, b.student_code, b.course_id from (select a.student_code, a.course_id from office_attendance_stu_record a where a.is_dk = '1' and a.student_code in " + stuCodeSqlStr +" group by a.student_code, a.course_id, a.datestr, a.end_period) b group by b.student_code, b.course_id";
		return queryForMap(sql, new MapRowMapper<String, Integer>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("student_code")+"-"+rs.getString("course_id");
			}

			@Override
			public Integer mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getInt("num");
			}});
	}
	
	@Override
	public int getMaxSyncVersion() {
		String sql = "select max(sync_version) from office_attendance_stu_record";
		return queryForInt(sql);
	}
}