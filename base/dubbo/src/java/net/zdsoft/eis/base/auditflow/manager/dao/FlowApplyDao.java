/* 
 * @(#)FlowApplyDao.java    Created on 2012-11-2
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.dao;

import java.util.List;

import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.keel.util.Pagination;

public interface FlowApplyDao {

	public void addFlowApply(FlowApply flowApply);

	public void deleteFlowApply(String... flowApplyIds);

	public void updateFlowApply(FlowApply flowApply);

	public void updateStatus(int status, String... applyIds);

	public FlowApply getFlowApply(String flowApplyId);

	/**
	 * 除审核通过外的流程中的申请
	 * @param businessId
	 * @return
	 */
	public FlowApply getFlowApplyInFlow(String businessId);
	
	public List<FlowApply> getFlowApplysByBusinessIds(String[] businessIds);
	
	public List<FlowApply> getFlowApplys(String[] flowApplyIds);

	public String[] getBusinessIds(String[] flowApplyIds);

	public List<FlowApply> getFlowApplys(String applyUnitId, int businessType, int status,
			String businessId, Pagination page);
			
	public List<FlowApply> getFlowApplys(String applyArrangeId, int businessType, int status, Pagination page);

}