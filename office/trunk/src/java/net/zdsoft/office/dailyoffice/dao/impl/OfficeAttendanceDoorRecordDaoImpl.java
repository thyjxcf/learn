package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeAttendanceDoorRecordDao;
import net.zdsoft.office.dailyoffice.entity.OfficeAttendanceDoorRecord;


/**
 * office_attendance_door_record 
 * @author 
 * 
 */
public class OfficeAttendanceDoorRecordDaoImpl extends BaseDao<OfficeAttendanceDoorRecord> implements OfficeAttendanceDoorRecordDao{
	@Override
	public OfficeAttendanceDoorRecord setField(ResultSet rs) throws SQLException{
		OfficeAttendanceDoorRecord officeAttendanceDoorRecord = new OfficeAttendanceDoorRecord();
		officeAttendanceDoorRecord.setId(rs.getString("id"));
		officeAttendanceDoorRecord.setTeacherCode(rs.getString("teacher_code"));
		officeAttendanceDoorRecord.setDataDate(rs.getString("data_date"));
		officeAttendanceDoorRecord.setDataTime(rs.getString("data_time"));
		officeAttendanceDoorRecord.setControllerId(rs.getString("controller_id"));
		officeAttendanceDoorRecord.setDataDoorNo(rs.getInt("data_door_no"));
		officeAttendanceDoorRecord.setReaderId(rs.getInt("reader_id"));
		officeAttendanceDoorRecord.setSyncDatetime(rs.getTimestamp("sync_datetime"));
		officeAttendanceDoorRecord.setSyncVersion(rs.getInt("sync_version"));
		return officeAttendanceDoorRecord;
	}
	
	public OfficeAttendanceDoorRecord save(OfficeAttendanceDoorRecord officeAttendanceDoorRecord){
		String sql = "insert into office_attendance_door_record(id, teacher_code, data_date, data_time, controller_id, data_door_no, reader_id, sync_datetime, sync_version) values(?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(officeAttendanceDoorRecord.getId())){
			officeAttendanceDoorRecord.setId(createId());
		}
		Object[] args = new Object[]{
			officeAttendanceDoorRecord.getId(), officeAttendanceDoorRecord.getTeacherCode(), 
			officeAttendanceDoorRecord.getDataDate(), officeAttendanceDoorRecord.getDataTime(), 
			officeAttendanceDoorRecord.getControllerId(), officeAttendanceDoorRecord.getDataDoorNo(), 
			officeAttendanceDoorRecord.getReaderId(), officeAttendanceDoorRecord.getSyncDatetime(), 
			officeAttendanceDoorRecord.getSyncVersion()
		};
		update(sql, args);
		return officeAttendanceDoorRecord;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_attendance_door_record where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(OfficeAttendanceDoorRecord officeAttendanceDoorRecord){
		String sql = "update office_attendance_door_record set teacher_code = ?, data_date = ?, data_time = ?, controller_id = ?, data_door_no = ?, reader_id = ?, sync_datetime = ?, sync_version = ? where id = ?";
		Object[] args = new Object[]{
			officeAttendanceDoorRecord.getTeacherCode(), officeAttendanceDoorRecord.getDataDate(), 
			officeAttendanceDoorRecord.getDataTime(), officeAttendanceDoorRecord.getControllerId(), 
			officeAttendanceDoorRecord.getDataDoorNo(), officeAttendanceDoorRecord.getReaderId(), 
			officeAttendanceDoorRecord.getSyncDatetime(), officeAttendanceDoorRecord.getSyncVersion(), 
			officeAttendanceDoorRecord.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeAttendanceDoorRecord getOfficeAttendanceDoorRecordById(String id){
		String sql = "select * from office_attendance_door_record where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	@Override
	public int getMaxSyncVersion() {
		String sql = "select max(sync_version) from office_attendance_door_record";
		return queryForInt(sql);
	}

	@Override
	public List<OfficeAttendanceDoorRecord> getOfficeAttendanceDoorRecordByControllerID(
			String controllerID,String queryBeginDate,String queryEndTime,Pagination page) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql =new StringBuffer("select * from office_attendance_door_record where 1=1");
		if(StringUtils.isNotBlank(controllerID)){
			sql.append("and controller_ID = ?");
			args.add(controllerID);
		}
		if(StringUtils.isNotBlank(queryBeginDate)){
			sql.append(" and to_date(?, 'yyyyMMdd') <= to_date(data_date, 'yyyyMMdd')");
			args.add(queryBeginDate);
		}
		if(StringUtils.isNotBlank(queryEndTime)){
			sql.append(" and to_date(?, 'yyyyMMdd') >= to_date(data_date, 'yyyyMMdd')");
			args.add(queryEndTime);
		}
	/*	if(StringUtils.isNotBlank(queryBeginDate)&&StringUtils.isNotBlank(queryEndTime)){
			sql.append(" and  to_date(data_date, 'yyyyMMdd') between to_date(?, 'yyyyMMdd') and  to_date(?, 'yyyyMMdd')");
			args.add(queryBeginDate);
			args.add(queryEndTime);
		}*/
		if(page != null){
			return query(sql.toString(),args.toArray(), new MultiRow(), page);
		}
		else{
			return query(sql.toString(),args.toArray(), new MultiRow());
		}
	}
}