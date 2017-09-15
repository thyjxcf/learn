package net.zdsoft.office.teacherAttendance.dao;


import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckLog;
/**
 * 考勤打卡流水记录表
 * @author 
 * 
 */
public interface OfficeAttendanceColckLogDao{

	/**
	 * 新增考勤打卡流水记录表
	 * @param officeAttendanceColckLog
	 * @return
	 */
	public OfficeAttendanceColckLog save(OfficeAttendanceColckLog officeAttendanceColckLog);

	/**
	 * 根据ids数组删除考勤打卡流水记录表
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新考勤打卡流水记录表
	 * @param officeAttendanceColckLog
	 * @return
	 */
	public Integer update(OfficeAttendanceColckLog officeAttendanceColckLog);

	/**
	 * 根据id获取考勤打卡流水记录表
	 * @param id
	 * @return
	 */
	public OfficeAttendanceColckLog getOfficeAttendanceColckLogById(String id);
	/**
	 * 查询该天 下的 迟到 早退 情况 
	 * @param state
	 * @param date
	 * @return
	 */
	public List<OfficeAttendanceColckLog> listOfficeAttendanceColckLogByDateAndState(String unitId,Date startDate,Date endDate,String state, Date date);
	/**
	 * 取数据 外勤 和正常打卡
	 * @param state
	 * @param date
	 * @param unitId
	 * @return
	 */
	public Map<String,String>  getOfficeAttendanceColckLogByDateAndState(String state, Date date  ,String unitId,Date startDate,Date endDate);
	/**
	 * 取 缺卡 map
	 * @param date
	 * @param unitId
	 * @param attendanceState
	 * @return
	 */
	public Map<String,String> getMissingCard(Date date ,String unitId ,String attendanceState,Date startDate,Date endDate);

}