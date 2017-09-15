package net.zdsoft.office.meeting.dao;

import java.util.*;

import net.zdsoft.office.meeting.entity.OfficeWorkMeetingConfirm;
import net.zdsoft.keel.util.Pagination;
/**
 * office_work_meeting_confirm
 * @author 
 * 
 */
public interface OfficeWorkMeetingConfirmDao{

	/**
	 * 新增office_work_meeting_confirm
	 * @param officeWorkMeetingConfirm
	 * @return
	 */
	public OfficeWorkMeetingConfirm save(OfficeWorkMeetingConfirm officeWorkMeetingConfirm);

	/**
	 * 根据ids数组删除office_work_meeting_confirm
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_work_meeting_confirm
	 * @param officeWorkMeetingConfirm
	 * @return
	 */
	public Integer update(OfficeWorkMeetingConfirm officeWorkMeetingConfirm);

	/**
	 * 根据id获取office_work_meeting_confirm
	 * @param id
	 * @return
	 */
	public OfficeWorkMeetingConfirm getOfficeWorkMeetingConfirmById(String id);

	/**
	 * 根据ids数组查询office_work_meeting_confirmmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeWorkMeetingConfirm> getOfficeWorkMeetingConfirmMapByIds(String[] ids);

	/**
	 * 获取office_work_meeting_confirm列表
	 * @return
	 */
	public List<OfficeWorkMeetingConfirm> getOfficeWorkMeetingConfirmList();

	/**
	 * 分页获取office_work_meeting_confirm列表
	 * @param page
	 * @return
	 */
	public List<OfficeWorkMeetingConfirm> getOfficeWorkMeetingConfirmPage(Pagination page);

	public List<String> getMeetingsIdByUserId(String userId);

	public Map<String, String> getTransferMapByMeetingsId(String meetingId);

	public Map<String, OfficeWorkMeetingConfirm> getMeetingConfirmMapByMeetingId(
			String meetingId);

	public boolean isTransfered(String meetingId, String transferUserId);

	public boolean isExist(String meetingId, String attendUserId);

	public Map<String, OfficeWorkMeetingConfirm> getMeetingsConfirmMapByMeetingsId(
			String meetingId);

	public String getConfirmId(String meetingId, String attendUserId);
}