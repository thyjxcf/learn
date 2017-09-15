package net.zdsoft.office.meeting.dao;

import java.util.*;

import net.zdsoft.office.meeting.entity.OfficeWorkMeetingAttend;
import net.zdsoft.keel.util.Pagination;
/**
 * office_work_meeting_attend
 * @author 
 * 
 */
public interface OfficeWorkMeetingAttendDao{

	/**
	 * 新增office_work_meeting_attend
	 * @param officeWorkMeetingAttend
	 * @return
	 */
	public OfficeWorkMeetingAttend save(OfficeWorkMeetingAttend officeWorkMeetingAttend);

	/**
	 * 根据ids数组删除office_work_meeting_attend
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_work_meeting_attend
	 * @param officeWorkMeetingAttend
	 * @return
	 */
	public Integer update(OfficeWorkMeetingAttend officeWorkMeetingAttend);

	/**
	 * 根据id获取office_work_meeting_attend
	 * @param id
	 * @return
	 */
	public OfficeWorkMeetingAttend getOfficeWorkMeetingAttendById(String id);

	/**
	 * 根据ids数组查询office_work_meeting_attendmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeWorkMeetingAttend> getOfficeWorkMeetingAttendMapByIds(String[] ids);

	/**
	 * 获取office_work_meeting_attend列表
	 * @return
	 */
	public List<OfficeWorkMeetingAttend> getOfficeWorkMeetingAttendList();

	/**
	 * 分页获取office_work_meeting_attend列表
	 * @param page
	 * @return
	 */
	public List<OfficeWorkMeetingAttend> getOfficeWorkMeetingAttendPage(Pagination page);

	/**
	 * 根据meetingIds获取对象
	 * @param ids
	 * @return
	 */
	public List<OfficeWorkMeetingAttend> getOfficeWorkMeetingAttendByMeetingIds(String[] ids);
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeWorkMeetingAttend> getattendMap(String[] ids);

	public List<String> getMeetingsIds(String userId, String deptId,
			String[] types);

	public List<OfficeWorkMeetingAttend> getOfficeWorkMeetingAttendList(
			String[] meetingIds);

	public void batchSave(List<OfficeWorkMeetingAttend> meetingsAttendsList);

	public void deleteByMeetingId(String meetingId, int type);

	public List<OfficeWorkMeetingAttend> getMeetingsAttendsListByMeetingIds(
			String[] meetingIds);

}