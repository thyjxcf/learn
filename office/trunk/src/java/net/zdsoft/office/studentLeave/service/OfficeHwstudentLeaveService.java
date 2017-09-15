package net.zdsoft.office.studentLeave.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.jbpm.core.entity.TaskHandlerSave;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.entity.OfficeHwstudentLeave;
/**
 * office_hwstudent_leave
 * @author 
 * 
 */
public interface OfficeHwstudentLeaveService{

	/**
	 * 新增office_hwstudent_leave
	 * @param officeHwstudentLeave
	 * @return
	 */
	public OfficeHwstudentLeave save(OfficeHwstudentLeave officeHwstudentLeave);

	/**
	 * 根据ids数组删除office_hwstudent_leave数据
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
	 * 根据UnitId获取office_hwstudent_leave列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeaveByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_hwstudent_leave
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeHwstudentLeave> getOfficeHwstudentLeaveByUnitIdPage(String unitId, Pagination page);

	public List<OfficeHwstudentLeave> getOfficeHwstudentLeaveByUnitIdPage(
			String unitId, String stuId, String type, Pagination page);

	public void deleteById(String leaveId);

	public List<OfficeHwstudentLeave> getAuditedList(String userId,
			String[] state, String type, Pagination page);

	public Map<String, OfficeHwstudentLeave> findByFlowIds(String type, String[] flowIds);
	
	/**
	 * 审核
	 * @param pass
	 * @param taskHandlerSave
	 * @param id
	 */
	public void doAudit(boolean pass, TaskHandlerSave taskHandlerSave, String id, String currentStepId);

	public List<OfficeHwstudentLeave> findByUnitIdAndType(String unitId,
			String classId, String studentId, String leaveType);

	public List<OfficeHwstudentLeave> getLeavesByIdsAndState(String stuId,
			String[] leaveIds, String[] states);

	public List<OfficeHwstudentLeave> findByUnitIdAndSubmit(String unitId,
			String studentId);


}