/* 
 * @(#)FlowApplyService.java    Created on 2012-11-2
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.keel.util.Pagination;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-2 下午05:23:42 $
 */
public interface FlowApplyService {

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
	 * 取申请列表
	 * 
	 * @param applyBusinessService
	 * @param applyUnitId
	 * @param businessType
	 * @param status
	 * @param businessId
	 * @param page
	 * 
	 * @return
	 */
	public List<FlowApply> getApplys(ApplyBusinessService<ApplyBusiness> applyBusinessService,
			String applyUnitId, int businessType, int status, String businessId, Pagination page,Map<String, String> paramMap);

	/**
	 * 取审核申请列表
	 * 
	 * @param applyBusinessService
	 * @param auditUnitId
	 * @param businessType
	 * @param status
	 * @param page
	 * @param paramMap TODO
	 * @return
	 */
	public List<FlowApply> getAuditApplys(ApplyBusinessService<ApplyBusiness> applyBusinessService,
			String auditUnitId, int businessType, int status, int operateType, String applyUnitId, Pagination page, Map<String, String> paramMap);

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

	/**
	 * 取业务列表，用于“修改和删除”时选择要操作的对象
	 * @param applyBusinessService
	 * @param businessType TODO
	 * @param ownerId
	 * @return
	 */
	public List<ApplyBusiness> getBusinesses(
			ApplyBusinessService<ApplyBusiness> applyBusinessService, int businessType, String ownerId);

}
