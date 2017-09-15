package net.zdsoft.office.teacherAttendance.dao;


import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceInfo;
/**
 * 考勤打卡信息
 * @author 
 * 
 */
public interface OfficeAttendanceInfoDao{

	/**
	 * 新增考勤打卡信息
	 * @param officeAttendanceInfo
	 * @return
	 */
	public OfficeAttendanceInfo save(OfficeAttendanceInfo officeAttendanceInfo);

	/**
	 * 根据ids数组删除考勤打卡信息
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新考勤打卡信息
	 * @param officeAttendanceInfo
	 * @return
	 */
	public Integer update(OfficeAttendanceInfo officeAttendanceInfo);

	/**
	 * 根据id获取考勤打卡信息
	 * @param id
	 * @return
	 */
	public OfficeAttendanceInfo getOfficeAttendanceInfoById(String id);
	/**
	 * 根据unitId获取考勤打卡信息Map
	 * @param unitId
	 * @return
	 */
	public Map<String,OfficeAttendanceInfo> getOfficeAttendanceInfoByUnitIdMap(String unitId,String[] userIds,String startTimeStr,String endTimeStr);
	
	/**
	 * 根据userId，考勤日期获取list
	 * @param userId
	 * @param date
	 * @return
	 */
	public List<OfficeAttendanceInfo> getListByUserIdDate(String userId, Date date);
	
	/**
	 * 根据考勤日期，type获取对象（非休息日）
	 * @param userId TODO
	 * @param attenceTime
	 * @param type
	 * @return
	 */
	public OfficeAttendanceInfo getItemByAdateType(String userId, Date attenceTime, String type);
	
	/**
	 * 根据开始日期结束日期获取list
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<OfficeAttendanceInfo> getListByStartAndEndTime(String userId, Date startTime, Date endTime);
	/**
	 * 查询该天 下的 迟到 早退 情况 
	 * @param state
	 * @param date
	 * @return
	 */
	public List<OfficeAttendanceInfo> listOfficeAttendanceInfoByDateAndState(String unitId,Date startDate,Date endDate,String state, Date date);
	/**
	 * 取数据 外勤 和正常打卡
	 * @param state
	 * @param date
	 * @param unitId
	 * @return
	 */
	public Map<String,String>  getOfficeAttendanceInfoByDateAndState(String state, Date date  ,String unitId,Date startDate,Date endDate);
	/**
	 * 取 缺卡 map
	 * @param date
	 * @param unitId
	 * @param attendanceState
	 * @return
	 */
	public Map<String,String> getMissingCard(Date date ,String unitId ,String attendanceState,Date startDate,Date endDate);

}