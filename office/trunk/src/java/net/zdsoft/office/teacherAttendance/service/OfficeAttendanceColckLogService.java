package net.zdsoft.office.teacherAttendance.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckLog;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceInfo;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceStatisticsViewDto;
/**
 * 考勤打卡流水记录表
 * @author 
 * 
 */
public interface OfficeAttendanceColckLogService{

	/**
	 * 新增考勤打卡流水记录表
	 * @param officeAttendanceColckLog
	 * @return
	 */
	public OfficeAttendanceColckLog save(OfficeAttendanceColckLog officeAttendanceColckLog);

	/**
	 * 根据ids数组删除考勤打卡流水记录表数据
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
	 * 根据考勤信息添加流水表
	 * @param info
	 * @return
	 */
	public OfficeAttendanceColckLog saveLogByInfo(OfficeAttendanceInfo officeAttendanceInfo);
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
	/**
	 * 查询迟到早退 list 数据
	 * @param unitId
	 * @param state
	 * @param date
	 * @return
	 */
	public List<OfficeAttendanceColckLog> listOfficeAttendanceColckLogByDateAndState(String unitId,Date startDate,Date endDate,
			String state, Date date);

}