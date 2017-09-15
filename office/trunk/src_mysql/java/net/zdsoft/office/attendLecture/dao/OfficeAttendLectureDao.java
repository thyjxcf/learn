package net.zdsoft.office.attendLecture.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.attendLecture.entity.OfficeAttendLecture;
/**
 * office_attend_lecture(听课信息表)
 * @author 
 * 
 */
public interface OfficeAttendLectureDao{

	/**
	 * 新增office_attend_lecture(听课信息表)
	 * @param officeAttendLecture
	 * @return
	 */
	public OfficeAttendLecture save(OfficeAttendLecture officeAttendLecture);

	/**
	 * 根据ids数组删除office_attend_lecture(听课信息表)
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_attend_lecture(听课信息表)
	 * @param officeAttendLecture
	 * @return
	 */
	public Integer update(OfficeAttendLecture officeAttendLecture);

	/**
	 * 根据id获取office_attend_lecture(听课信息表)
	 * @param id
	 * @return
	 */
	public OfficeAttendLecture getOfficeAttendLectureById(String id);

	public List<OfficeAttendLecture> getOfficeAttendLectureList(String unitId, String userId, String state,
			Date startTime, Date endTime, Pagination page);

	public Map<String, OfficeAttendLecture> getAttendLectureMap(String[] flowIds,String unitId,Date startTime,
			Date endTime, String applyUserName);

	public List<OfficeAttendLecture> getAuditedList(String userId, String[] state,String unitId,Date startTime,
			Date endTime, String applyUserName, Pagination page);
	
	public List<OfficeAttendLecture> getOfficeCountList(String unitId,
			String[] userIds, Date startTime, Date endTime, String applyUserName);
	public List<OfficeAttendLecture> getOfficeCountInfo(String unitId,Date startTime,Date endTime,
			String[] userIds,String applyUserName,Pagination page);
}