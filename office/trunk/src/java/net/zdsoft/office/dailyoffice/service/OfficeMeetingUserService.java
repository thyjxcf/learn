package net.zdsoft.office.dailyoffice.service;

import java.util.*;
import net.zdsoft.office.dailyoffice.entity.OfficeMeetingUser;
import net.zdsoft.keel.util.Pagination;
/**
 * office_meeting_user
 * @author 
 * 
 */
public interface OfficeMeetingUserService{

	/**
	 * 新增office_meeting_user
	 * @param officeMeetingUser
	 * @return
	 */
	public OfficeMeetingUser save(OfficeMeetingUser officeMeetingUser);

	/**
	 * 通过申请ID、通知人员ID批量新增office_meeting_user
	 * @param applyId
	 * @param userIds
	 */
	public void batchsave(String applyId, String[] userIds);
	
	/**
	 * 根据ids数组删除office_meeting_user数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_meeting_user
	 * @param officeMeetingUser
	 * @return
	 */
	public Integer update(OfficeMeetingUser officeMeetingUser);

	/**
	 * 根据id获取office_meeting_user
	 * @param id
	 * @return
	 */
	public OfficeMeetingUser getOfficeMeetingUserById(String id);

	/**
	 * 根据ids数组查询office_meeting_usermap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeMeetingUser> getOfficeMeetingUserMapByIds(String[] ids);

	/**
	 * 获取office_meeting_user列表
	 * @return
	 */
	public List<OfficeMeetingUser> getOfficeMeetingUserList();
	
	/**
	 * 按会议申请ID获取office_meeting_user列表
	 * @param applyId
	 * @return
	 */
	public List<OfficeMeetingUser> getOfficeMeetingUserListByApplyId(String applyId);

	/**
	 * 分页获取office_meeting_user列表
	 * @param page
	 * @return
	 */
	public List<OfficeMeetingUser> getOfficeMeetingUserPage(Pagination page);
}