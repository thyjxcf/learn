package net.zdsoft.office.officeFlow.dao;

import java.util.*;

import net.zdsoft.office.officeFlow.entity.OfficeFlowStepInfo;
import net.zdsoft.keel.util.Pagination;
/**
 * office_flow_step_info
 * @author 
 * 
 */
public interface OfficeFlowStepInfoDao{

	/**
	 * 新增office_flow_step_info
	 * @param officeFlowStepInfo
	 * @return
	 */
	public OfficeFlowStepInfo save(OfficeFlowStepInfo officeFlowStepInfo);

	/**
	 * 根据ids数组删除office_flow_step_info
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_flow_step_info
	 * @param officeFlowStepInfo
	 * @return
	 */
	public Integer update(OfficeFlowStepInfo officeFlowStepInfo);

	/**
	 * 根据id获取office_flow_step_info
	 * @param id
	 * @return
	 */
	public OfficeFlowStepInfo getOfficeFlowStepInfoById(String id);

	/**
	 * 根据ids数组查询office_flow_step_infomap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeFlowStepInfo> getOfficeFlowStepInfoMapByIds(String[] ids);

	/**
	 * 获取office_flow_step_info列表
	 * @return
	 */
	public List<OfficeFlowStepInfo> getOfficeFlowStepInfoList();

	/**
	 * 分页获取office_flow_step_info列表
	 * @param page
	 * @return
	 */
	public List<OfficeFlowStepInfo> getOfficeFlowStepInfoPage(Pagination page);
	
	public void batchRemove(String flowId, String[] stepIds);
	
	public void batchInsert(List<OfficeFlowStepInfo> list);
	
	public List<OfficeFlowStepInfo> getStepInfo(String flowId, String stepId);
	
}