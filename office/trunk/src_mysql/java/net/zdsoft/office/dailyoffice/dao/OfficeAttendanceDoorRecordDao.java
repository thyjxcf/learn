package net.zdsoft.office.dailyoffice.dao;

import java.util.*;
import net.zdsoft.office.dailyoffice.entity.OfficeAttendanceDoorRecord;
import net.zdsoft.keel.util.Pagination;

/**
 * office_attendance_door_record 
 * @author 
 * 
 */
public interface OfficeAttendanceDoorRecordDao {
	/**
	 * 新增office_attendance_door_record
	 * @param officeAttendanceDoorRecord
	 * @return"
	 */
	public OfficeAttendanceDoorRecord save(OfficeAttendanceDoorRecord officeAttendanceDoorRecord);
	
	/**
	 * 根据ids数组删除office_attendance_door_record
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新office_attendance_door_record
	 * @param officeAttendanceDoorRecord
	 * @return
	 */
	public Integer update(OfficeAttendanceDoorRecord officeAttendanceDoorRecord);
	
	/**
	 * 根据id获取office_attendance_door_record;
	 * @param id);
	 * @return
	 */
	public OfficeAttendanceDoorRecord getOfficeAttendanceDoorRecordById(String id);
	
	/**
	 * 获取最大的版本号
	 * @return
	 */
	public int getMaxSyncVersion();
	/**
	 * 根据controllerID获取office_attendance_door_record
	 * @param controllerID
	 * @return
	 */
	public  List<OfficeAttendanceDoorRecord> getOfficeAttendanceDoorRecordByControllerID(String controllerID,String queryBeginDate,String queryEndTime,Pagination page);
}
