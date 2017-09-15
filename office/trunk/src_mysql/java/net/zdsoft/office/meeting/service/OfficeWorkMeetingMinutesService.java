package net.zdsoft.office.meeting.service;

import java.util.*;

import net.zdsoft.office.meeting.entity.OfficeWorkMeetingMinutes;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
/**
 * office_work_meeting_minutes
 * @author 
 * 
 */
public interface OfficeWorkMeetingMinutesService{

	/**
	 * 新增office_work_meeting_minutes
	 * @param officeWorkMeetingMinutes
	 * @return
	 */
	public void save(OfficeWorkMeetingMinutes officeWorkMeetingMinutes,List<UploadFile> uploadFileList);
	
	public OfficeWorkMeetingMinutes save(OfficeWorkMeetingMinutes officeworkmeetingminutes);
	
	/**
	 * 根据ids数组删除office_work_meeting_minutes数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_work_meeting_minutes
	 * @param officeWorkMeetingMinutes
	 * @return
	 */
	public Integer update(OfficeWorkMeetingMinutes officeWorkMeetingMinutes,List<UploadFile> uploadFileList,String[] removeAttachment);
	
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
	 * 根据meetId查询记录
	 * @param meetingId
	 * @return
	 */
	public OfficeWorkMeetingMinutes getOfficeWorkMinutesByMeetId(String meetingId);

	public Map<String, OfficeWorkMeetingMinutes> getOfficeWorkMeetingMinutesMap();
}