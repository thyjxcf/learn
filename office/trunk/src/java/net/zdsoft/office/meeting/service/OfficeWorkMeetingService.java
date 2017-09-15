package net.zdsoft.office.meeting.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.entity.MeetingsInfoCondition;
import net.zdsoft.office.meeting.entity.OfficeWorkMeeting;
/**
 * office_work_meeting
 * @author 
 * 
 */
public interface OfficeWorkMeetingService{

	/**
	 * 新增office_work_meeting
	 * @param officeWorkMeeting
	 * @return
	 */
	public OfficeWorkMeeting save(OfficeWorkMeeting officeWorkMeeting);

	/**
	 * 根据ids数组删除office_work_meeting数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_work_meeting
	 * @param officeWorkMeeting
	 * @return
	 */
	public Integer update(OfficeWorkMeeting officeWorkMeeting);

	/**
	 * 根据id获取office_work_meeting
	 * @param id
	 * @return
	 */
	public OfficeWorkMeeting getOfficeWorkMeetingById(String id);

	/**
	 * 根据ids数组查询office_work_meetingmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeWorkMeeting> getOfficeWorkMeetingMapByIds(String[] ids);

	/**
	 * 获取office_work_meeting列表
	 * @return
	 */
	public List<OfficeWorkMeeting> getOfficeWorkMeetingList();

	/**
	 * 分页获取office_work_meeting列表
	 * @param page
	 * @return
	 */
	public List<OfficeWorkMeeting> getOfficeWorkMeetingPage(Pagination page);

	/**
	 * 根据UnitId获取office_work_meeting列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeWorkMeeting> getOfficeWorkMeetingByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_work_meeting
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeWorkMeeting> getOfficeWorkMeetingByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据会议名称和日期查询列表
	 * @param unitId
	 * @param meetName
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	public List<OfficeWorkMeeting> getOfficeWorkMeetingListBySearchParams(String unitId,String meetName,Date startTime,Date endTime,Pagination page);
	
	/**
	 * 根据会议名称查询纪要列表
	 * @param unitId
	 * @param userId
	 * @param meetName
	 * @param page
	 * @return
	 */
	public List<OfficeWorkMeeting> getOfficeWorkMeetingManageListByParams(String unitId,String userId,String meetName,Pagination page);
	
	public List<OfficeWorkMeeting> getOfficeWorkMeetingByConditionList(
			MeetingsInfoCondition mic, Pagination page);

	public List<OfficeWorkMeeting> getMeetingInfoPage(
			MeetingsInfoCondition mic, Pagination page);

	public void submitMeeting(String meetingId);

	public void publishMeeting(String meetingId);

	public void updateAudit(OfficeWorkMeeting meeting);

	public OfficeWorkMeeting getMeetingsAttendInfoById(String meetingId);
}