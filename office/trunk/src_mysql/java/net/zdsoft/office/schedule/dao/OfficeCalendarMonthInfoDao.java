package net.zdsoft.office.schedule.dao;

import java.util.*;

import net.zdsoft.office.schedule.entity.OfficeCalendarMonthInfo;
import net.zdsoft.keel.util.Pagination;
/**
 * office_calendar_month_info
 * @author 
 * 
 */
public interface OfficeCalendarMonthInfoDao{

	/**
	 * 新增office_calendar_month_info
	 * @param officeCalendarMonthInfo
	 * @return
	 */
	public OfficeCalendarMonthInfo save(OfficeCalendarMonthInfo officeCalendarMonthInfo);

	/**
	 * 根据ids数组删除office_calendar_month_info
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
	 * 根据unitId获取office_calendar_month_info列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_calendar_month_info获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoByUnitIdPage(String unitId, Pagination page);

	public Map<String, OfficeCalendarMonthInfo> getCalendarMonthInfoMapByUnitId(
			String unitId);
}