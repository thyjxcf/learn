package net.zdsoft.office.schedule.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.schedule.dto.DateDto;
import net.zdsoft.office.schedule.dto.OfficeCalendarDto;
import net.zdsoft.office.schedule.entity.OfficeCalendar;
/**
 * 日程安排表
 * @author 
 * 
 */
public interface OfficeCalendarService{

	/**
	 * 新增日程安排表
	 * @param officeCalendar
	 * @return
	 */
	public PromptMessageDto saveOrUpdate(OfficeCalendar officeCalendar);

	/**
	 * 根据ids数组删除日程安排表数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 根据id获取日程安排表
	 * @param id
	 * @return
	 */
	public OfficeCalendar getOfficeCalendarById(String id);

	/**
	 * 根据ids数组查询日程安排表map
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeCalendar> getOfficeCalendarMapByIds(String[] ids);
	
	public Map<String, OfficeCalendarDto> getCalendarMapBy(OfficeCalendarDto oc, List<DateDto> dates);

	/**
	 * 获取日程安排表列表
	 * @return
	 */
	public List<OfficeCalendar> getOfficeCalendarList();
	
	/**
	 * 获得日程详情
	 * @param oc
	 * @return
	 */
	public List<OfficeCalendar> getOfficeCalendarDetails(OfficeCalendarDto oc);

	/**
	 * 分页获取日程安排表列表
	 * @param page
	 * @return
	 */
	public List<OfficeCalendar> getOfficeCalendarPage(Pagination page);

	/**
	 * 根据UnitId获取日程安排表列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeCalendar> getOfficeCalendarByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取日程安排表
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeCalendar> getOfficeCalendarByUnitIdPage(String unitId, Pagination page);

	/**
	 * 获取科室负责人日志
	 * @param userId
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<OfficeCalendar> getOfficeCalendarListPage(String userId, String unitId, String startTime, String endTime,  Pagination page);
	
	/**
	 * 根据条件获取list
	 * @param unitId
	 * @param deptId
	 * @param searchName
	 * @param searchStartTime
	 * @param searchEndTime
	 * @param page
	 * @return
	 */
	public List<OfficeCalendar> getCalendarListByCondition(String unitId, String deptId, 
			String searchName, String startTime, String endTime, Pagination page);
	
}