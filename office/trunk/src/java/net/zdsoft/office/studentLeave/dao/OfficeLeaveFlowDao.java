package net.zdsoft.office.studentLeave.dao;

import java.util.*;

import net.zdsoft.office.studentLeave.entity.OfficeLeaveFlow;
import net.zdsoft.keel.util.Pagination;
/**
 * office_leave_flow
 * @author 
 * 
 */
public interface OfficeLeaveFlowDao{

	/**
	 * 新增office_leave_flow
	 * @param officeLeaveFlow
	 * @return
	 */
	public OfficeLeaveFlow save(OfficeLeaveFlow officeLeaveFlow);
	
	public void batchSave(List<OfficeLeaveFlow> officeLeaveFlows);

	/**
	 * 根据ids数组删除office_leave_flow
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_leave_flow
	 * @param officeLeaveFlow
	 * @return
	 */
	public Integer update(OfficeLeaveFlow officeLeaveFlow);

	/**
	 * 根据id获取office_leave_flow
	 * @param id
	 * @return
	 */
	public OfficeLeaveFlow getOfficeLeaveFlowById(String id);

	/**
	 * 根据ids数组查询office_leave_flowmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeLeaveFlow> getOfficeLeaveFlowMapByIds(String[] ids);

	/**
	 * 获取office_leave_flow列表
	 * @return
	 */
	public List<OfficeLeaveFlow> getOfficeLeaveFlowList();

	/**
	 * 分页获取office_leave_flow列表
	 * @param page
	 * @return
	 */
	public List<OfficeLeaveFlow> getOfficeLeaveFlowPage(Pagination page);
	
	public List<OfficeLeaveFlow> getOfficeLeaveFlows(String unitId,String type);
	
	public void deleteOfficeLeaveFlowByType(String unitId,String gradeId,String type);

	public List<OfficeLeaveFlow> getFlowsByGradeIdAndType(String unitId,
			String gradeId, String type);
}