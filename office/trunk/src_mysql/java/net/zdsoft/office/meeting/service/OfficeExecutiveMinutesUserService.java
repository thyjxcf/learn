package net.zdsoft.office.meeting.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMinutesUser;
/**
 * office_executive_minutes_user
 * @author 
 * 
 */
public interface OfficeExecutiveMinutesUserService{

	/**
	 * 新增office_executive_minutes_user
	 * @param officeExecutiveMinutesUser
	 * @return
	 */
	public OfficeExecutiveMinutesUser save(OfficeExecutiveMinutesUser officeExecutiveMinutesUser);

	/**
	 * 批量保存纪要人员
	 * @param unitId
	 * @param userIds
	 */
	public void batchUpdate(String unitId, String userIds);
	
	/**
	 * 根据ids数组删除office_executive_minutes_user数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_executive_minutes_user
	 * @param officeExecutiveMinutesUser
	 * @return
	 */
	public Integer update(OfficeExecutiveMinutesUser officeExecutiveMinutesUser);

	/**
	 * 根据id获取office_executive_minutes_user
	 * @param id
	 * @return
	 */
	public OfficeExecutiveMinutesUser getOfficeExecutiveMinutesUserById(String id);

	/**
	 * 根据ids数组查询office_executive_minutes_usermap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserMapByIds(String[] ids);

	/**
	 * 获取office_executive_minutes_user列表
	 * @return
	 */
	public List<OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserList();

	/**
	 * 分页获取office_executive_minutes_user列表
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserPage(Pagination page);

	/**
	 * 根据UnitId获取office_executive_minutes_user列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_executive_minutes_user
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserByUnitIdPage(String unitId, Pagination page);

	/**
	 * 是否会议纪要人员
	 * @param unitId
	 * @param userId
	 * @return
	 */
	public boolean isMinutesUser(String unitId, String userId);
}