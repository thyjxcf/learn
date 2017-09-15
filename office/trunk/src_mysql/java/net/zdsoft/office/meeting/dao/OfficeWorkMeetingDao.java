package net.zdsoft.office.meeting.dao;

import java.util.*;

import net.zdsoft.office.meeting.entity.MeetingsInfoCondition;
import net.zdsoft.office.meeting.entity.OfficeWorkMeeting;
import net.zdsoft.keel.util.Pagination;
/**
 * office_work_meeting
 * @author 
 * 
 */
public interface OfficeWorkMeetingDao{

	/**
	 * 新增office_work_meeting
	 * @param officeWorkMeeting
	 * @return
	 */
	public OfficeWorkMeeting save(OfficeWorkMeeting officeWorkMeeting);

	/**
	 * 根据ids数组删除office_work_meeting
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
	 * 根据unitId获取office_work_meeting列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeWorkMeeting> getOfficeWorkMeetingByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_work_meeting获取
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
	 * 根据会议名称查询会议纪要列表
	 * @param unitId
	 * @param userId
	 * @param meetName
	 * @param page
	 * @return
	 */
	public List<OfficeWorkMeeting> getOfficeWorkMeetingManageListByParams(String unitId,String userId,String meetName,Pagination page);
	
	
	public List<OfficeWorkMeeting> getMeetingsInfoList(String unitId,
			String isEnd, String meetingName, Date startTime, Date endTime,
			String[] array, Pagination page);


	public List<OfficeWorkMeeting> getMeetingInfoPage(
			MeetingsInfoCondition mic, Pagination page);

	public void updateEdit(OfficeWorkMeeting meeting);

	public void submitMeeting(String id);

	public void publishMeeting(String id);

	public void updateAudit(OfficeWorkMeeting meeting);

}