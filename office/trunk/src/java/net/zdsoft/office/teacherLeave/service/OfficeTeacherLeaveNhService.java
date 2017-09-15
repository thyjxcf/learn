package net.zdsoft.office.teacherLeave.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeaveNh;
/**
 * office_teacher_leave_nh
 * @author 
 * 
 */
public interface OfficeTeacherLeaveNhService{

	/**
	 * 新增office_teacher_leave_nh
	 * @param officeTeacherLeaveNh
	 * @return
	 */
	public OfficeTeacherLeaveNh save(OfficeTeacherLeaveNh officeTeacherLeaveNh);

	/**
	 * 根据ids数组删除office_teacher_leave_nh数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_teacher_leave_nh
	 * @param officeTeacherLeaveNh
	 * @return
	 */
	public Integer update(OfficeTeacherLeaveNh officeTeacherLeaveNh);
	
	/**
	 * 开始请假流程
	 * @param officeTeacherLeaveNh
	 * @param userId
	 */
	public void startFlow(OfficeTeacherLeaveNh officeTeacherLeaveNh, String userId);

	/**
	 * 根据id获取office_teacher_leave_nh
	 * @param id
	 * @return
	 */
	public OfficeTeacherLeaveNh getOfficeTeacherLeaveNhById(String id);

	/**
	 * 根据ids数组查询office_teacher_leave_nhmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhMapByIds(String[] ids);

	/**
	 * 获取office_teacher_leave_nh列表
	 * @return
	 */
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhList();

	/**
	 * 分页获取office_teacher_leave_nh列表
	 * @param page
	 * @return
	 */
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhPage(String userId, Date startTime, Date endTime, String state, Pagination page);

	/**
	 * 根据UnitId获取office_teacher_leave_nh列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_teacher_leave_nh
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhByUnitIdPage(String unitId, String state, Pagination page);

	/**
	 * 未审核
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<OfficeTeacherLeaveNh> toDoAudit(String userId, Date startTime, Date endTime, Pagination page);
	/**
	 * 已审核
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<OfficeTeacherLeaveNh> HaveDoAudit(String userId, Date startTime, Date endTime, Pagination page);
	
	/**
	 * 审核
	 * @param pass
	 * @param taskHandlerSave
	 * @param id
	 * @param isSchool 学校教师请假要发短信告知班主任
	 */
	public void saveAuditFlow(boolean pass, TaskHandlerSave taskHandlerSave,
			String id, boolean isSchool);
	
	/**
	 * 根据参数获取查询列表
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param state
	 * @return
	 */
	public List<OfficeTeacherLeaveNh> getteacherLeaveListBySearchParams(String unitId,Date startTime,Date endTime,String state,Pagination page);
	
	/**
	 * 统计用到的查询
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<OfficeTeacherLeaveNh> getSummarylist(String unitId,Date startTime,Date endTime);
	
	/**
	 * 获取该单位下某时间段的useId
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param leaveIds 现有请假类型
	 * @return
	 */
	public String[] getUserIdsByUnitId(String unitId,Date startTime,Date endTime,String[] leaveIds);
	
	/**
	 * 
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String, String> getSumMap(String unitId,Date startTime,Date endTime);
	
	/**
	 * 判断是否存在冲突
	 * @param applyUserId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public boolean isExistConflict(String id, String applyUserId, Date beginTime, Date endTime);
}