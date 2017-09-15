package net.zdsoft.office.teacherLeave.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeaveNh;
/**
 * office_teacher_leave_nh
 * @author 
 * 
 */
public interface OfficeTeacherLeaveNhDao{

	/**
	 * 新增office_teacher_leave_nh
	 * @param officeTeacherLeaveNh
	 * @return
	 */
	public OfficeTeacherLeaveNh save(OfficeTeacherLeaveNh officeTeacherLeaveNh);

	/**
	 * 根据ids数组删除office_teacher_leave_nh
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
	 * 更新审核状态
	 * @param id
	 * @param state
	 */
	public void updateState(String id, Integer state);

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
	 * 根据unitId获取office_teacher_leave_nh列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_teacher_leave_nh获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhByUnitIdPage(String unitId, String state, Pagination page);
	
	/**
	 * 获取待审核数据
	 * @param flowIds
	 * @param page
	 * @return
	 */
	public List<OfficeTeacherLeaveNh> getOfficeTeacherLeaveNhListByFlowIds(String[] flowIds, Date startTime, Date endTime, Pagination page);

	/**
	 * 已审核
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<OfficeTeacherLeaveNh> HaveDoAudit(String userId, Date startTime, Date endTime, Pagination page);
	
	/**
	 * 根据参数获得查询列表
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param state
	 * @param leaveIds  现有维护的请假类型
	 * @return
	 */
	public List<OfficeTeacherLeaveNh> getteacherLeavListBySearParams(String unitId,Date startTime,Date endTime,String state,Pagination page,String[] leaveIds);
	
	/**
	 * 统计用的查询方法
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<OfficeTeacherLeaveNh> getSummaryList(String unitId,Date startTime,Date endTime);
	
	/**
	 * 获取userIds
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param leaveIds  现有请假类型
	 * @return
	 */
	public String[] getUserIds(String unitId,Date startTime,Date endTime,String[] leaveIds);
	
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