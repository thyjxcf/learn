/* 
 * @(#)AbstractApplyBusinessServiceImpl.java    Created on 2012-12-7
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.auditflow.manager.dao.FlowApplyDao;
import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.auditflow.manager.service.ApplyBusinessService;
import net.zdsoft.eis.base.auditflow.manager.service.BusinessAdd;
import net.zdsoft.eis.base.auditflow.manager.service.BusinessDelete;
import net.zdsoft.eis.base.auditflow.manager.service.BusinessDispose;
import net.zdsoft.eis.base.auditflow.manager.service.BusinessOwnerService;
import net.zdsoft.eis.base.auditflow.manager.service.BusinessUpdate;
import net.zdsoft.eis.base.auditflow.manager.service.FlowAuditService;
import net.zdsoft.eis.base.auditflow.manager.service.SchFlowApplyService;
import net.zdsoft.eis.base.auditflow.manager.service.SchFlowAuditService;
import net.zdsoft.eis.base.form.Field;
import net.zdsoft.keel.util.Pagination;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-12-7 上午10:44:01 $
 */
public abstract class AbstractApplyBusinessServiceImpl<E extends ApplyBusiness> implements
		ApplyBusinessService<E> {
	protected FlowApplyDao flowApplyDao;
	protected FlowAuditService flowAuditService;
	protected SchFlowAuditService schFlowAuditService;
	protected SchFlowApplyService schFlowApplyService;
	/**
	 * 业务有特殊处理时，须覆盖此方法
	 */
	@Override
	public void fillFieldModification(Map<String, Field> fieldMap) {

	}
	
	@Override
	public void updateFile(String id) {
		
	}
	
	@Override
	/**
	 * 这是申请编辑页面的转换
	 */
	public void operateField(List<Field> fields) {
	
	}
	/**
	 * 页面有特殊处理时，须覆盖此方法
	 */
	@Override
	public String getMacroPage() {
		return null;
	}
	
	@Override
	public String getArrangeId(String userId, String method, String param,String businessId) {
		return null;
	}

	@Override
	public BusinessOwnerService getBusinessOwnerService() {
		return null;
	}

	@Override
	public List<E> getBusinesses(String ownerId) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public void saveDisposeBusiness(E e, int operateType) {
		switch (operateType) {
		case FlowApply.OPERATE_TYPE_NO:
			if (this instanceof BusinessAdd) {
				BusinessDispose<E> service = (BusinessDispose<E>) this;
				service.saveDisposeBusiness(e);
			} else {
				System.out.println("请实现BusinessDispose接口");
			}
			break;

		case FlowApply.OPERATE_TYPE_ADD:
			if (this instanceof BusinessAdd) {
				BusinessAdd<E> service = (BusinessAdd<E>) this;
				service.addBusiness(e);
			} else {
				System.out.println("请实现BusinessAdd接口");
			}

			break;

		case FlowApply.OPERATE_TYPE_MODIFY:
			if (this instanceof BusinessDelete) {
				BusinessUpdate<E> service = (BusinessUpdate<E>) this;
				service.updateBusiness(e);
			} else {
				System.out.println("请实现BusinessUpdate接口");
			}

			break;

		case FlowApply.OPERATE_TYPE_DELETE:
			if (this instanceof BusinessDelete) {
				BusinessDelete service = (BusinessDelete) this;
				service.deleteBusiness(e.getBusinessId());
			} else {
				System.out.println("请实现BusinessDelete接口");
			}
			break;

		default:
			break;
		}
	}

	public void saveDisposeBusiness(FlowApply apply) {

	}

	@Override
	public E getEntity() {
		return null;
	}

	@Override
	public void addApplyBusiness(E e) {
		
	}

	@Override
	public void deleteApplyBusiness(String... ids) {
		
	}

	@Override
	public void updateApplyBusiness(E e) {
		
	}

	@Override
	public E getApplyBusiness(String id) {
		return null;
	}

	@Override
	public Map<String, E> getApplyBusinessMap(String[] ids) {
		return null;
	}

	@Override
	public E getBusiness(String id) {
		return null;
	}

	/**
	 * 业务有特殊处理时，须覆盖此方法
	 */
	@Override
	public List<FlowApply> getFlowApplys(String applyUnitId, int businessType,
			int status, String businessId, Pagination page,
			Map<String, String> paramMap) {
		List<FlowApply> applys = flowApplyDao.getFlowApplys(applyUnitId, businessType, status,
				businessId, page);
		return applys;
	}
	
		/**
	 * 业务有特殊处理时，须覆盖此方法
	 */
	@Override
	public List<FlowApply> getFlowApplys(String applyArrangeId,
			int businessType, int status, Pagination page,
			Map<String, String> paramMap) {
		List<FlowApply> applys = flowApplyDao.getFlowApplys(applyArrangeId,
				businessType, status, page);
		return applys;
	}
	@Override
	public List<FlowAudit> getFlowAudits(String auditUnitId, int businessType,
			int status, int operateType, String applyUnitId, Pagination page,
			Map<String, String> paramMap) {
		List<FlowAudit> audits = flowAuditService.getFlowAudits(auditUnitId, businessType, status, 
				operateType, applyUnitId, page);
		return audits;
	}
	
	@Override
	public List<FlowAudit> getFlowAudits(String arrangeId, String roleId,
			int businessType, int status, int operateType, String applyUnitId,
			Pagination page, Map<String, String> paramMap) {
		List<FlowAudit> audits = schFlowAuditService.getFlowAudits(arrangeId,
				roleId, businessType, status, operateType, applyUnitId, page);
		return audits;
	}

	public void setFlowAuditService(FlowAuditService flowAuditService) {
		this.flowAuditService = flowAuditService;
	}

	public void setFlowApplyDao(FlowApplyDao flowApplyDao) {
		this.flowApplyDao = flowApplyDao;
	}
	
	public void setSchFlowAuditService(SchFlowAuditService schFlowAuditService) {
		this.schFlowAuditService = schFlowAuditService;
	}

	public void setSchFlowApplyService(SchFlowApplyService schFlowApplyService) {
		this.schFlowApplyService = schFlowApplyService;
	}
}
