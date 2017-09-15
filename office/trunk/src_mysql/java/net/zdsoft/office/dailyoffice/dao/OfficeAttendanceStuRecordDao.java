package net.zdsoft.office.dailyoffice.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.office.dailyoffice.entity.OfficeAttendanceStuRecord;

/**
 * office_attendance_stu_record 
 * @author 
 * 
 */
public interface OfficeAttendanceStuRecordDao {
	/**
	 * 新增office_attendance_stu_record
	 * @param officeAttendanceStuRecord
	 * @return"
	 */
	public OfficeAttendanceStuRecord save(OfficeAttendanceStuRecord officeAttendanceStuRecord);
	
	/**
	 * 根据ids数组删除office_attendance_stu_record
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新office_attendance_stu_record
	 * @param officeAttendanceStuRecord
	 * @return
	 */
	public Integer update(OfficeAttendanceStuRecord officeAttendanceStuRecord);
	
	/**
	 * 根据id获取office_attendance_stu_record;
	 * @param id);
	 * @return
	 */
	public OfficeAttendanceStuRecord getOfficeAttendanceStuRecordById(String id);
	
	/**
	 * 获取学生考勤记录表
	 * @param isDk
	 * @param isRemind
	 * @return
	 */
	public List<OfficeAttendanceStuRecord> getOfficeAttendanceStuRecord(String isDk, String isRemind);
	
	/**
	 * 批量更新是否发短信提醒的标志
	 * @param ids
	 * @param isRemind
	 */
	public void batchUpdateIsRemind(String[] ids, String isRemind); 
	
	/**
	 * 统计课程学生人数
	 * @param courseId
	 * @param dateStr
	 * @param startPeriod
	 * @param endPeriod
	 * @param isDk
	 * @return
	 */
	public int getCount(String courseId, String dateStr, int startPeriod, int endPeriod, String isDk);
	
	/**
	 * 获取课程的列表
	 * @param courseId
	 * @param dateStr
	 * @param startPeriod
	 * @param endPeriod
	 * @param isDk
	 * @return
	 */
	public List<OfficeAttendanceStuRecord> getList(String courseId, String dateStr, int startPeriod, int endPeriod, String isDk);
	
	/**
	 * 获取学生课程考勤次数map
	 * @param courseId
	 * @return
	 */
	public Map<String, Integer> getStuAttendanceCountMap(String courseId);
	
	/**
	 * 获取指定学生出席课程次数map
	 * @param stuCodes
	 * @return
	 */
	public Map<String, Integer> getStuAttendanceCountMap(String[] stuCodes);
	
	/**
	 * 获取最大的版本号
	 * @return
	 */
	public int getMaxSyncVersion();
}
