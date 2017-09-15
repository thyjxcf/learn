package net.zdsoft.office.teacherLeave.dao;

import java.util.*;

import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.keel.util.Pagination;

/**
 * office_teacher_leave 
 * @author 
 * 
 */
public interface OfficeTeacherLeaveDao {
	/**
	 * 新增office_teacher_leave
	 * @param officeTeacherLeave
	 * @return"
	 */
	public OfficeTeacherLeave save(OfficeTeacherLeave officeTeacherLeave);
	
	/**
	 * 根据ids数组删除office_teacher_leave
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	/**
	 * 更新office_teacher_leave
	 * @param officeTeacherLeave
	 * @return
	 */
	public Integer update(OfficeTeacherLeave officeTeacherLeave);
	
	/**
	 * 根据id获取office_teacher_leave;
	 * @param id);
	 * @return
	 */
	public OfficeTeacherLeave getOfficeTeacherLeaveById(String id);
		
	/**
	 * 根据ids数组查询office_teacher_leavemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeTeacherLeave> getOfficeTeacherLeaveMapByIds(String[] ids);
	
	/**
	 * 根据ids数组查询office_teacher_leavemap
	 * @param ids
	 * @return
	 */
	public List<OfficeTeacherLeave> getOfficeTeacherLeaveByIds(String[] ids);
	
	/**
	 * 获取office_teacher_leave列表
	 * @return
	 */
	public List<OfficeTeacherLeave> getOfficeTeacherLeaveList();
		
	/**
	 * 分页获取office_teacher_leave列表
	 * @param page
	 * @return
	 */
	public List<OfficeTeacherLeave> getOfficeTeacherLeavePage(Pagination page);
	

	/**
	 * 根据unitId获取office_teacher_leave列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeTeacherLeave> getOfficeTeacherLeaveByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_teacher_leave获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeTeacherLeave> getOfficeTeacherLeaveByUnitIdPage(String unitId, Pagination page);

	/**
	 * 请假查询
	 * @param unitId
	 * @param userId
	 * @param userName
	 * @param deptId
	 * @param startTime
	 * @param endTime
	 * @param status TODO
	 * @param page TODO
	 * @return
	 */
	public List<OfficeTeacherLeave> getQueryList(String unitId,
			String userId, String userName, String deptId,
			Date startTime, Date endTime, Pagination page);

	public List<OfficeTeacherLeave> getApplyList(String userId, String unitId,
			int applyStatus, Pagination page);

	public Map<String, OfficeTeacherLeave> getOfficeTeacherLeaveMapByFlowIds(
			String[] array);

	public List<OfficeTeacherLeave> HaveDoAudit(String userId,boolean invalid,Pagination page);
	
	/**
	 * 获取统计信息
	 * @return
	 */
	public Map<String, String> getSumMap(String unitId, Date startTime, Date endTime, String deptId);
	
	/**
	 * 相应时间段内的有请假信息的用户
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param deptId
	 * @return
	 */
	public String[] getApplyUserIds(String unitId, Date startTime, Date endTime, String deptId);
}
