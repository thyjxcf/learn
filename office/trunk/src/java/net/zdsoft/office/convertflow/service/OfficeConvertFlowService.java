package net.zdsoft.office.convertflow.service;

import java.util.*;

import net.zdsoft.jbpm.core.entity.TaskHandlerResult;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.convertflow.dto.OfficeConvertDto;
import net.zdsoft.office.convertflow.entity.OfficeConvertFlow;

/**
 * office_convert_flow
 * @author 
 * 
 */
public interface OfficeConvertFlowService{
	
	/**
	 * 删除流程时  要同步删除手机端审核信息
	 * @param businessId
	 */
	void deleteFlow(String businessId);
	
	/**
	 * 启动流程时调用（审核数据同步 手机端微办公调用）
	 * @param ent
	 */
	public void startFlow(Object obj, Integer type);
	
	/**
	 * 流转中处理时调用（审核数据同步 手机端微办公调用）
	 * @param businessId
	 * @param flowId TODO
	 * @param curUserId TODO
	 * @param taskId
	 * @param result
	 * @param isPass
	 */
	public void completeTask(String businessId, String flowId, String curUserId, String taskId, TaskHandlerResult result, boolean isPass);
	
	/**
	 * 普通审核完成时调用--如物品管理只有一步审核流程
	 * @param isPass
	 * @param businessId
	 */
	public void completeAudit(boolean isPass, String businessId);
	
	
	/**
	 * 根据参数或者审核list数据(待审核、已审核)
	 * @param unitId
	 * @param userId 审核人或者其他审核参数
	 * @param dataType 待审核、已审核状态 逻辑参数
	 * @param type 业务类型 物品、请假等
	 * @param searchStr TODO
	 * @param page
	 * @return
	 */
	public List<OfficeConvertDto> getAuditList(String unitId, String userId, String dataType, Integer type, String searchStr, Pagination page);
	
	/**
	 * 根据参数获取申请list数据(我发起的)
	 * @param applyUserId 申请人id
	 * @param type 业务类型 物品、请假等
	 * @param page
	 * @return
	 */
	public List<OfficeConvertDto> getApplyList(String applyUserId, Integer type, Pagination page);

	/**
	 * 通过businessId删除
	 * @param businessId
	 * @return
	 * 2016年9月11日
	 */
	public int deleteByBusinessId(String businessId);
	/**
	 * 通过businessId获取对象
	 * @param businessId
	 * @return
	 */
	public OfficeConvertFlow getObjByBusinessId(String businessId);
	/**
	 * 更新office_convert_flow
	 * @param officeConvertFlow
	 * @return
	 */
	public void update(Integer status, String businessId);
	
	public OfficeConvertFlow getOfficeConvertFlowById(String id);

}