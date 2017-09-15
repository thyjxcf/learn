package net.zdsoft.office.schedule.dao;

import java.util.*;

import net.zdsoft.office.schedule.entity.OfficeCalendarDayInfo;
import net.zdsoft.keel.util.Pagination;
/**
 * office_calendar_day_info
 * @author 
 * 
 */
public interface OfficeCalendarDayInfoDao{

	/**
	 * 新增office_calendar_day_info
	 * @param officeCalendarDayInfo
	 * @return
	 */
	public OfficeCalendarDayInfo save(OfficeCalendarDayInfo officeCalendarDayInfo);

	/**
	 * 根据ids数组删除office_calendar_day_info
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_calendar_day_info
	 * @param officeCalendarDayInfo
	 * @return
	 */
	public Integer update(OfficeCalendarDayInfo officeCalendarDayInfo);

	/**
	 * 根据id获取office_calendar_day_info
	 * @param id
	 * @return
	 */
	public OfficeCalendarDayInfo getOfficeCalendarDayInfoById(String id);

	/**
	 * 根据ids数组查询office_calendar_day_infomap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeCalendarDayInfo> getOfficeCalendarDayInfoMapByIds(String[] ids);

	/**
	 * 获取office_calendar_day_info列表
	 * @return
	 */
	public List<OfficeCalendarDayInfo> getOfficeCalendarDayInfoList();

	/**
	 * 分页获取office_calendar_day_info列表
	 * @param page
	 * @return
	 */
	public List<OfficeCalendarDayInfo> getOfficeCalendarDayInfoPage(Pagination page);

	/**
	 * 根据unitId获取office_calendar_day_info列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeCalendarDayInfo> getOfficeCalendarDayInfoByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_calendar_day_info获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeCalendarDayInfo> getOfficeCalendarDayInfoByUnitIdPage(String unitId, Pagination page);

	public Map<String, OfficeCalendarDayInfo> getCalendarDayInfoMapByUnitId(
			Date beginDate, Date endDate, String unitId);
}