package net.zdsoft.office.studentLeave.dao;

import java.util.*;

import net.zdsoft.office.studentLeave.entity.OfficeHwstudentLeave;
import net.zdsoft.keel.util.Pagination;
/**
 * office_hwstudent_leave
 * @author 
 * 
 */
public interface OfficeHwstudentLeaveDao{

	/**
	 * 新增office_hwstudent_leave
	 * @param officeHwstudentLeave
	 * @return
	 */
	public OfficeHwstudentLeave save(OfficeHwstudentLeave officeHwstudentLeave);

	/**
	 * 根据ids数组删除office_hwstudent_leave
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_hwstudent_leave
	 * @param officeHwstudentLeave
	 * @return
	 */
	public Integer update(OfficeHwstudentLeave officeHwstudentLeave);

	/**
	 * 根据id获取office_hwstudent_leave
	 * @param id
	 * @return
	 */
	public OfficeHwstudentLeave getOfficeHwstudentLeaveById(String id);

	/**
	 * 根据ids数组查询office_hwstudent_leavemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeHwstudentLeave> getOfficeHwstudentLeaveMapByIds(String[] ids);

	/**
	 * 获取office_hwstudent_leave列表
	 * @return
	 */
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeaveList();

	/**
	 * 分页获取office_hwstudent_leave列表
	 * @param page
	 * @return
	 */
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeavePage(Pagination page);

	/**
	 * 根据unitId获取office_hwstudent_leave列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeaveByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_hwstudent_leave获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeaveByUnitIdPage(String unitId, Pagination page);

	public List<OfficeHwstudentLeave> getOfficeHwstudentLeaveByUnitIdPage(
			String unitId, String stuId, String type, Pagination page);

	public List<OfficeHwstudentLeave> getAuditedList(String userId,
			String[] state, String type, Pagination page);

	public Map<String, OfficeHwstudentLeave> findByFlowIds(String type,
			String[] flowIds);

	public List<OfficeHwstudentLeave> findByUnitIdAndType(String unitId,
			String classId, String studentId, String leaveType);

	public List<OfficeHwstudentLeave> getLeavesByIdsAndState(String stuId,
			String[] leaveIds, String[] states);

	public List<OfficeHwstudentLeave> findByUnitIdAndSubmit(String unitId,
			String studentId);
}