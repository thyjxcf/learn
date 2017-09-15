package net.zdsoft.office.teacherAttendance.service;


import java.util.*;

import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckApply;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceSet;
/**
 * 考勤补卡申请表
 * @author 
 * 
 */
public interface OfficeAttendanceColckApplyService{

	/**
	 * 新增考勤补卡申请表
	 * @param officeAttendanceColckApply
	 * @return
	 */
	public OfficeAttendanceColckApply save(OfficeAttendanceColckApply officeAttendanceColckApply);

	/**
	 * 根据ids数组删除考勤补卡申请表数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新考勤补卡申请表
	 * @param officeAttendanceColckApply
	 * @return
	 */
	public Integer update(OfficeAttendanceColckApply officeAttendanceColckApply);
	
	/**
	 * 根据日期type查询数据
	 * @param userId
	 * @param attenceDate
	 * @param type
	 * @return
	 */
	public OfficeAttendanceColckApply getObjByDateType(String userId, String attenceDate, String type);

	/**
	 * 根据id获取考勤补卡申请表
	 * @param id
	 * @return
	 */
	public OfficeAttendanceColckApply getOfficeAttendanceColckApplyById(String id);
	/**
	 * 提交审核 启动流程 
	 * @param 
	 * @return
	 */
	public void startFlow(OfficeAttendanceColckApply officeAttendanceColckApply, String userId);
	
	/**
	 * 查询日期范围内的申请记录
	 * @param userId
	 * @param startDate
	 * @param endDate
	 */
	public List<OfficeAttendanceColckApply> getListByDate(String userId, String startDate, String endDate);
	
	/**
	 * 根据日期范围的申请记录map
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map<String, OfficeAttendanceColckApply> getMapByDate(String userId, Date startDate, Date endDate);
	
	/**
	 * 获取list
	 * @param userId unitId
	 * @param page
	 * @return
	 */
	public List<OfficeAttendanceColckApply> getApplyList(String userId, String unitId,int applyStatus,Pagination page);
	/**
	 * 未审核
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<OfficeAttendanceColckApply> toDoAudit(String userId, String unitId,Pagination page);
	/**
	 * 已审核 审核结束
	 * @param userId
	 * @param page
	 * @return
	 */
	public List<OfficeAttendanceColckApply> HaveDoAudit(String userId,String unitId, Pagination page);
	/**
	 * 审核是否通过
	 * @param pass
	 * @param taskHandlerSave
	 * @param id
	 */
	public void passFlow(boolean pass, TaskHandlerSave taskHandlerSave,String id);
	/**
	 * 获取对应的补卡信息
	 * @param
	 * @param unitId userIds
	 * @param 
	 */
	public Map<String,OfficeAttendanceColckApply> getOfficeAttendanceClockByUnitIdMap(String unitId,String[] userIds,String startTimeStr,String endTimeStr);
	/**
	 * userId 得到set制度
	 * @param
	 * @param userId
	 * @param 
	 */
	public OfficeAttendanceSet getSet(String userId);
	/**
	 * userIds 得到set Map制度
	 * @param
	 * @param userIds
	 * @param 
	 */
	public Map<String,OfficeAttendanceSet> getSetMapByUserIds(String[] userIds,String unitId);
	
}