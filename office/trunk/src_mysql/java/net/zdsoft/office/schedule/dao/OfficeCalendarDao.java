package net.zdsoft.office.schedule.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.schedule.entity.OfficeCalendar;
/**
 * 日程安排表
 * @author 
 * 
 */
public interface OfficeCalendarDao{

	/**
	 * 新增日程安排表
	 * @param officeCalendar
	 * @return
	 */
	public OfficeCalendar save(OfficeCalendar officeCalendar);

	/**
	 * 根据ids数组删除日程安排表
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新日程安排表
	 * @param officeCalendar
	 * @return
	 */
	public Integer update(OfficeCalendar officeCalendar);
	
	/**
	 * 检查是否日期交叉
	 * @return
	 */
	public boolean checkExistsTimeCross(OfficeCalendar officeCalendar);

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

	/**
	 * 获取日程安排表列表
	 * @return
	 */
	public List<OfficeCalendar> getOfficeCalendarList();
	
	public List<OfficeCalendar> getOfficeCalendarsBy(String unitId, String calendarTime, String endTime, int type, String[] creator);
	
	public List<OfficeCalendar> getOfficeCalendarsByDate(String unitId, String calendarTime, int period, int type, String[] creator);

	/**
	 * 分页获取日程安排表列表
	 * @param page
	 * @return
	 */
	public List<OfficeCalendar> getOfficeCalendarPage(Pagination page);

	/**
	 * 根据unitId获取日程安排表列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeCalendar> getOfficeCalendarByUnitIdList(String unitId);

	/**
	 * 根据unitId分页日程安排表获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeCalendar> getOfficeCalendarByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 获取科室负责人日志
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param userIds
	 * @param page
	 * @return
	 */
	public List<OfficeCalendar> getOfficeCalendarListPage(String unitId, String startTime, String endTime, String[] userIds, Pagination page);
	
	public List<OfficeCalendar> getCalendarListByCondition(String unitId, String[] userIds, String startTime, String endTime, Pagination page);
}