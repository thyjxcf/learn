package net.zdsoft.office.officeFlow.service;

import java.util.*;
import net.zdsoft.office.officeFlow.entity.OfficeFlowStepInfo;
import net.zdsoft.keel.util.Pagination;
/**
 * office_flow_step_info
 * @author 
 * 
 */
public interface OfficeFlowStepInfoService{

	/**
	 * 新增office_flow_step_info
	 * @param officeFlowStepInfo
	 * @return
	 */
	public OfficeFlowStepInfo save(OfficeFlowStepInfo officeFlowStepInfo);

	/**
	 * 根据ids数组删除office_flow_step_info数据
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
	
	public void batchUpdateInfo(String flowId, String flowStepInfo);
	
	public void batchRemove(String flowId, String[] stepIds);
	
	public void batchInsert(List<OfficeFlowStepInfo> list);
	
	public OfficeFlowStepInfo getStepInfo(String flowId, String stepId);
	
	/**
	 * 在流程启动时，将流程定义的步骤信息关联到业务流转流程
	 * @param oldFlowId
	 * @param newFlowId
	 */
	public void batchUpdateByFlowId(String oldFlowId, String newFlowId);
	
	public List<OfficeFlowStepInfo> getStepInfosByFlowIds(String flowId);
}