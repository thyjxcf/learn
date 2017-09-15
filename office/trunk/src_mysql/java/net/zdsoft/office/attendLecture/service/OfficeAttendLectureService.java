package net.zdsoft.office.attendLecture.service;

import java.util.*;

import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.attendLecture.entity.OfficeAttendLecture;
/**
 * office_attend_lecture(听课信息表)
 * @author 
 * 
 */
public interface OfficeAttendLectureService{

	/**
	 * 新增office_attend_lecture(听课信息表)
	 * @param officeAttendLecture
	 * @return
	 */
	public OfficeAttendLecture save(OfficeAttendLecture officeAttendLecture);

	/**
	 * 根据ids数组删除office_attend_lecture(听课信息表)数据
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

	/**
	 * 获取列表
	 * @param unitId
	 * @param userId
	 * @param state
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param unitId
	 * @return
	 */
	public List<OfficeAttendLecture> getOfficeAttendLectureList(String unitId, String userId, String state,
			Date startTime, Date endTime, Pagination page);

	public void startFlow(OfficeAttendLecture officeAttendLecture, String userId);
	
	/**
	 * 获取审核列表
	 * @param unitId
	 * @param userId
	 * @param searchType
	 * @param startTime
	 * @param endTime
	 * @param applyUserName
	 * @param page
	 * @return
	 */
	public List<OfficeAttendLecture> getOfficeAttendLectureAuditList(String unitId, String userId, String searchType,
			Date startTime, Date endTime, String applyUserName, Pagination page);

	public void doAudit(boolean pass, TaskHandlerSave taskHandlerSave, String id);
	
	public List<OfficeAttendLecture> getOfficeCountList(String unitId,String deptId,Date startTime,Date endTime,String applyUserName);
	
	public List<OfficeAttendLecture> getOfficeCountInfo(String unitId,Date startTime,Date endTime,String deptId,String applyUserName,Pagination page);
}