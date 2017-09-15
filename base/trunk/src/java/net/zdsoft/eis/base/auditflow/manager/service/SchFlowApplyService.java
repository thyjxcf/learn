package net.zdsoft.eis.base.auditflow.manager.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.keel.util.Pagination;

public interface SchFlowApplyService {
	public void addFlowApply(FlowApply apply);

	public void deleteFlowApply(ApplyBusinessService<ApplyBusiness> applyBusinessService,
			String[] flowApplyIds);

	public void updateFlowApply(FlowApply flowApply);

	public FlowApply getFlowApply(ApplyBusinessService<ApplyBusiness> applyBusinessService,
			String flowApplyId);

	/**
	 * 提交申请
	 * 
	 * @param applyIds
	 */
	public void saveSubmitFlowApply(String... applyIds);

	/**
	 * 撤消申请
	 * 
	 * @param applyIds
	 */
	public void saveCancelFlowApply(String... applyIds);

	/**
	 * 查询是否有处于流程中的申请（未提交、审核中、审核未通过（可以再次提交））
	 * 
	 * @param businessId
	 * @return
	 */
	public FlowApply getFlowApplyInFlow(String businessId);
	
	/**
	 * 获取申请信息
	 * @param flowApplyId
	 * @return
	 */
	public FlowApply getFlowApply(String flowApplyId);

	/**
	 * 取申请列表
	 * 
	 * @param applyBusinessService
	 * @param applyArrangeId
	 * @param businessType
	 * @param status
	 * @param page
	 * 
	 * @return
	 */
	public List<FlowApply> getApplys(ApplyBusinessService<ApplyBusiness> applyBusinessService,
			String applyArrangeId, int businessType, int status,Pagination page,Map<String, String> paramMap);

	/**
	 * 取审核申请列表
	 * 
	 * @param applyBusinessService
	 * @param arrangeId
	 * @param roleId
	 * @param businessType
	 * @param status
	 * @param page
	 * @param paramMap TODO
	 * @return
	 */
	public List<FlowApply> getAuditApplys(ApplyBusinessService<ApplyBusiness> applyBusinessService,
			String arrangeId,String roleId, int businessType, int status, int operateType, String applyUnitId, Pagination page, Map<String, String> paramMap);

	/**
	 * 审核
	 * 
	 * @param step
	 * @param ids
	 */
	public void saveAudits(ApplyBusinessService<ApplyBusiness> applyBusinessService,
			FlowAudit step, String... auditIds);

	/**
	 * 审核流程显示步骤
	 * 
	 * @param applyId
	 * @return
	 */
	public List<FlowAudit> getShowSteps(String applyId);
	
	public List<FlowApply> getFlowApplysByBusinessIds(String[] businessIds);
	
	public Map<String,FlowApply> getFlowApplyMapByBusinessIds(String[] businessIds);
}
