package net.zdsoft.office.teacherAttendance.dao;

import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckApply;
/**
 * 考勤补卡申请表
 * @author 
 * 
 */
public interface OfficeAttendanceColckApplyDao{

	/**
	 * 新增考勤补卡申请表
	 * @param officeAttendanceColckApply
	 * @return
	 */
	public OfficeAttendanceColckApply save(OfficeAttendanceColckApply officeAttendanceColckApply);

	/**
	 * 根据ids数组删除考勤补卡申请表
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
	 * 查询日期范围内的申请记录
	 * @param userId
	 * @param startDate
	 * @param endDate
	 */
	public List<OfficeAttendanceColckApply> getListByDate(String userId, String startDate, String endDate);
	
	/**
	 * 获取list
	 * @param userId unitId
	 * @return
	 */
	public List<OfficeAttendanceColckApply> getApplyList(String userId, String unitId,int appplyStatus,Pagination page);
	/**	
	 * 
	 * @param 
	 * @return
	 */
	public Map<String, OfficeAttendanceColckApply> getAttendanceColckApplyMapByFlowIds(String[] array);
	/**	
	 * 已审核 审核结束
	 * @param 
	 * @return
	 */
	public List<OfficeAttendanceColckApply> HaveDoAudit(String userId, Pagination page);
	/**	
	 * 获取补卡信息map
	 * @param 
	 * @return
	 */
	public Map<String,OfficeAttendanceColckApply> getOfficeAttendanceClockByUnitIdMap(String unitId,String[] userIds,String startTimeStr,String endTimeStr);
}