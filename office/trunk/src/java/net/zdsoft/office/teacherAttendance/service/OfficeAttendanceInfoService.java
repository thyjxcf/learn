package net.zdsoft.office.teacherAttendance.service;


import java.util.*;

import com.alibaba.fastjson.JSONObject;

import net.sf.json.JSONArray;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceInfo;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceStatisticsViewDto;
/**
 * 考勤打卡信息
 * @author 
 * 
 */
public interface OfficeAttendanceInfoService{

	/**
	 * 新增考勤打卡信息
	 * @param officeAttendanceInfo
	 * @return
	 */
	public OfficeAttendanceInfo save(OfficeAttendanceInfo officeAttendanceInfo);

	/**
	 * 根据ids数组删除考勤打卡信息数据
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
	 * 根据unitId 等查询条件获取考勤信息
	 * @param 
	 * @return
	 */
	public List<OfficeAttendanceInfo> getOfficeAttendanceInfoByCondition(String unitId,String userId,
			String searchName,String deptId,Date startTime,Date endTime,boolean isTeacherAttenceAdmin,Pagination page);
	
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
	 * 获取map，key为考勤日期yyyy-MM-dd
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String, List<OfficeAttendanceInfo>> getMapByStartAndEndTime(String userId, Date startTime, Date endTime);
	
	/**
	 * 统计请假、出差、外出等信息
	 * @param unitId
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<Date, JSONArray> getOtherInfo(String unitId, String userId, Date startTime, Date endTime);
	
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
	
	/**
	 * 统计一天
	 * @param unitId
	 * @param groupId
	 * @param deptId
	 * @param day
	 * @return
	 */
	public String getOneDayStatistic(String unitId,String groupId,String deptId,Date day);
	/**
	 * 统计多天
	 * @param unitId
	 * @param groupId
	 * @param deptId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<OfficeAttendanceStatisticsViewDto>  listStatistics(String unitId, String groupId,
			String deptId, Date startDate , Date endDate ,Pagination page);
	
	
	/**
	 *获取请假等其他信息 
	 * @param unitId
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public JSONArray getOtherCountInfo(String unitId, String userId, Date startTime, Date endTime);

}