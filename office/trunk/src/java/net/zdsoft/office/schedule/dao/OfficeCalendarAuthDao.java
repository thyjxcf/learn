package net.zdsoft.office.schedule.dao;

import java.util.*;

import net.zdsoft.office.schedule.entity.OfficeCalendarAuth;
import net.zdsoft.keel.util.Pagination;
/**
 * office_calendar_auth
 * @author 
 * 
 */
public interface OfficeCalendarAuthDao{

	/**
	 * 新增office_calendar_auth
	 * @param officeCalendarAuth
	 * @return
	 */
	public OfficeCalendarAuth save(OfficeCalendarAuth officeCalendarAuth);

	/**
	 * 根据ids数组删除office_calendar_auth
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
	 * 根据unitId获取office_calendar_auth列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeCalendarAuth> getOfficeCalendarAuthByUnitIdList(String unitId);
	
	/**
	 * 判断是否有权限
	 * @param unitId
	 * @param type
	 * @param objectId
	 * @return
	 */
	public boolean checkHasAuth(String unitId, String type, String objectId, String userId);

	/**
	 * 根据unitId分页office_calendar_auth获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeCalendarAuth> getOfficeCalendarAuthByUnitIdPage(String unitId, Pagination page);

	public Map<String, OfficeCalendarAuth> getOfficeAuthMap(String unitId);

	public OfficeCalendarAuth getOfficeCalendarAuthByUnitIdPage(String unitId,
			String objectId, String authType);
}