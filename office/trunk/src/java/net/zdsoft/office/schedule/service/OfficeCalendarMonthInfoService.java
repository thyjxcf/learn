package net.zdsoft.office.schedule.service;

import java.util.*;

import net.zdsoft.office.schedule.entity.OfficeCalendarMonthInfo;
import net.zdsoft.keel.util.Pagination;
/**
 * office_calendar_month_info
 * @author 
 * 
 */
public interface OfficeCalendarMonthInfoService{

	/**
	 * 新增office_calendar_month_info
	 * @param officeCalendarMonthInfo
	 * @return
	 */
	public OfficeCalendarMonthInfo save(OfficeCalendarMonthInfo officeCalendarMonthInfo);

	/**
	 * 根据ids数组删除office_calendar_month_info数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_calendar_month_info
	 * @param officeCalendarMonthInfo
	 * @return
	 */
	public Integer update(OfficeCalendarMonthInfo officeCalendarMonthInfo);

	/**
	 * 根据id获取office_calendar_month_info
	 * @param id
	 * @return
	 */
	public OfficeCalendarMonthInfo getOfficeCalendarMonthInfoById(String id);

	/**
	 * 根据ids数组查询office_calendar_month_infomap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoMapByIds(String[] ids);

	/**
	 * 获取office_calendar_month_info列表
	 * @return
	 */
	public List<OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoList();

	/**
	 * 分页获取office_calendar_month_info列表
	 * @param page
	 * @return
	 */
	public List<OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoPage(Pagination page);

	/**
	 * 根据UnitId获取office_calendar_month_info列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_calendar_month_info
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoByUnitIdPage(String unitId, Pagination page);

	public Map<String, OfficeCalendarMonthInfo> getCalendarMonthInfoMapByUnitId(
			String unitId);
}