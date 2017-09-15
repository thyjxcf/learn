package net.zdsoft.office.meeting.dao;

import java.util.*;
import net.zdsoft.office.meeting.entity.OfficeWorkMeetingMinutes;
import net.zdsoft.keel.util.Pagination;
/**
 * office_work_meeting_minutes
 * @author 
 * 
 */
public interface OfficeWorkMeetingMinutesDao{

	/**
	 * 新增office_work_meeting_minutes
	 * @param officeWorkMeetingMinutes
	 * @return
	 */
	public OfficeWorkMeetingMinutes save(OfficeWorkMeetingMinutes officeWorkMeetingMinutes);

	/**
	 * 根据ids数组删除office_work_meeting_minutes
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_work_meeting_minutes
	 * @param officeWorkMeetingMinutes
	 * @return
	 */
	public Integer update(OfficeWorkMeetingMinutes officeWorkMeetingMinutes);

	/**
	 * 根据id获取office_work_meeting_minutes
	 * @param id
	 * @return
	 */
	public OfficeWorkMeetingMinutes getOfficeWorkMeetingMinutesById(String id);

	/**
	 * 根据ids数组查询office_work_meeting_minutesmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeWorkMeetingMinutes> getOfficeWorkMeetingMinutesMapByIds(String[] ids);

	/**
	 * 获取office_work_meeting_minutes列表
	 * @return
	 */
	public List<OfficeWorkMeetingMinutes> getOfficeWorkMeetingMinutesList();

	/**
	 * 分页获取office_work_meeting_minutes列表
	 * @param page
	 * @return
	 */
	public List<OfficeWorkMeetingMinutes> getOfficeWorkMeetingMinutesPage(Pagination page);
	
	/**
	 * 根据meetingId获得记录
	 * @param meetId
	 * @return
	 */
	public OfficeWorkMeetingMinutes getOfficeWorkMeetingsByMeetId(String meetId);
}