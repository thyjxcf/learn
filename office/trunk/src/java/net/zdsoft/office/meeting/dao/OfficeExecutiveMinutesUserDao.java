package net.zdsoft.office.meeting.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMinutesUser;
/**
 * office_executive_minutes_user
 * @author 
 * 
 */
public interface OfficeExecutiveMinutesUserDao{

	/**
	 * 新增office_executive_minutes_user
	 * @param officeExecutiveMinutesUser
	 * @return
	 */
	public OfficeExecutiveMinutesUser save(OfficeExecutiveMinutesUser officeExecutiveMinutesUser);

	public void deleteByUnitId(String unitId);
	public void batchSave(List<OfficeExecutiveMinutesUser> list);
	
	/**
	 * 根据ids数组删除office_executive_minutes_user
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
	 * 根据unitId获取office_executive_minutes_user列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeExecutiveMinutesUser> getOfficeExecutiveMinutesUserByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_executive_minutes_user获取
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