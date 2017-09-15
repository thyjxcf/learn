package net.zdsoft.office.schedule.service;

import java.util.*;
import net.zdsoft.office.schedule.entity.OfficeCalendarAuth;
import net.zdsoft.keel.util.Pagination;
/**
 * office_calendar_auth
 * @author 
 * 
 */
public interface OfficeCalendarAuthService{

	/**
	 * 新增office_calendar_auth
	 * @param officeCalendarAuth
	 * @return
	 */
	public OfficeCalendarAuth save(OfficeCalendarAuth officeCalendarAuth);

	/**
	 * 根据ids数组删除office_calendar_auth数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_calendar_auth
	 * @param officeCalendarAuth
	 * @return
	 */
	public Integer update(OfficeCalendarAuth officeCalendarAuth);
	
	/**
	 * 判断是否有权限
	 * @param unitId
	 * @param type
	 * @param userId
	 * @return
	 */
	public boolean checkHasAuth(String unitId, String type, String userId);

	/**
	 * 根据id获取office_calendar_auth
	 * @param id
	 * @return
	 */
	public OfficeCalendarAuth getOfficeCalendarAuthById(String id);

	/**
	 * 根据ids数组查询office_calendar_authmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeCalendarAuth> getOfficeCalendarAuthMapByIds(String[] ids);

	/**
	 * 获取office_calendar_auth列表
	 * @return
	 */
	public List<OfficeCalendarAuth> getOfficeCalendarAuthList();

	/**
	 * 分页获取office_calendar_auth列表
	 * @param page
	 * @return
	 */
	public List<OfficeCalendarAuth> getOfficeCalendarAuthPage(Pagination page);

	/**
	 * 根据UnitId获取office_calendar_auth列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeCalendarAuth> getOfficeCalendarAuthByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_calendar_auth
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeCalendarAuth> getOfficeCalendarAuthByUnitIdPage(String unitId, Pagination page);

	public OfficeCalendarAuth getOfficeCalendarAuthByUnitIdList(String unitId,
			String objectId, String authType);
}