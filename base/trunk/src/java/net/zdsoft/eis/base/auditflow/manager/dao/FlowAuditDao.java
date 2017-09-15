/* 
 * @(#)FlowAuditDao.java    Created on 2012-11-2
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.keel.util.Pagination;

public interface FlowAuditDao {

	public void addFlowAudits(List<FlowAudit> flowAudits);

	public void deleteFlowAudits(String... applyIds);
	
	public void deleteFlowAuditsByIds(String... ids);

	/**
	 * 更新审核状态，并将意见、审核人等清空
	 * @param audits
	 */
	public void updateStatus(List<FlowAudit> audits);
	
	/**
	 * 更新审核信息：审核意见等
	 * @param audit
	 */
	public void updateFlowAudit(FlowAudit audit);
	
	public FlowAudit getFlowAudit(String flowAuditId);
	public List<FlowAudit> getFlowAuditList(String[] ids);
	
	public List<FlowAudit> getFlowAuditListByAppIds(String[] applyIds);
	
	public List<FlowAudit> getFlowAudits(String[] applyIds,int state);

	public List<FlowAudit> getFlowAudits(String arrangeId, String roleId,
			int businessType, int status, int operateType, String applyUnitId,
			Pagination page);

	public List<FlowAudit> getFlowAudits(String auditUnitId, int businessType, int status, int operateType,
			String applyUnitId, Pagination page);

	/**
	 * 根据申请id获得审核结果
	 * 
	 * @param applyId
	 * @return
	 */
	public List<FlowAudit> getFlowAudits(String applyId);

	/**
	 * 获取申请单位信息
	 * @param auditUnitId
	 * @param businessType
	 * @param status
	 * @param operateType
	 * @return
	 */
	public Map<String, Unit> getApplyUnits(String auditUnitId, int businessType, int status, 
			int operateType);
}