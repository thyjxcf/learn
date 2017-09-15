package net.zdsoft.office.meeting.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.entity.OfficeWorkMeetingAttend;
/**
 * office_work_meeting_attend
 * @author 
 * 
 */
public interface OfficeWorkMeetingAttendService{

	/**
	 * 新增office_work_meeting_attend
	 * @param officeWorkMeetingAttend
	 * @return
	 */
	public OfficeWorkMeetingAttend save(OfficeWorkMeetingAttend officeWorkMeetingAttend);

	/**
	 * 根据ids数组删除office_work_meeting_attend数据
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
	 * 根据meeting_id获取对象
	 * @param ids
	 * @return
	 */
	public List<OfficeWorkMeetingAttend> getOfficeWorkMeetingAttendListByMeetingIds(String[] ids);
	
	/**
	 * 根据meeting_id获取map
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeWorkMeetingAttend> getattendMapByMeetingId(String[] ids);

	public List<String> getMeetingsIds(String userId, String deptId,
			String[] types);

	public Map<String, List<OfficeWorkMeetingAttend>> getOfficeWorkMeetingAttendMap(
			String[] array);

	public void batchSave(List<OfficeWorkMeetingAttend> meetingsAttendsList);

	public void deleteByMeetingId(String id, int type);

	public List<OfficeWorkMeetingAttend> getMeetingsAttendsListByMeetingIds(
			String[] meetingIds);

}