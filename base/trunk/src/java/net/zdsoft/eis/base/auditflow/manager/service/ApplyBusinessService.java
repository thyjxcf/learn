/* 
 * @(#)FlowBusinessService.java    Created on 2012-11-2
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.form.Field;
import net.zdsoft.keel.util.Pagination;

/**
 * 申请变更的业务对象
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-2 下午05:46:22 $
 */
public interface ApplyBusinessService<E extends ApplyBusiness> {
	public E getEntity();
	/**
	 * 主要是奖惩信息删除附件使用
	 * @param id
	 * @return
	 */
	public void updateFile(String id);

	/**
	 * 转换数据
	 * 
	 * @param fieldMap
	 */
	public void fillFieldModification(Map<String, Field> fieldMap);

	public String getMacroPage();

	public BusinessOwnerService getBusinessOwnerService();
	
	public String getArrangeId(String userId, String method, String param,
			String businessId);

	// -----------------------临时申请表：对应applyId-----------------------------
	public void addApplyBusiness(E e);

	public void deleteApplyBusiness(String... ids);

	public void updateApplyBusiness(E e);

	public E getApplyBusiness(String id);

	public Map<String, E> getApplyBusinessMap(String[] ids);

	// -----------------------审核后进行更新数据：对应businessId------------------
	/**
	 * 处理业务
	 */
	public void saveDisposeBusiness(E e, int operateType);

	public E getBusiness(String id);
	
	/**
	 * 得到业务列表
	 * @param ownerId 所属对象
	 * @return
	 */
	public List<E> getBusinesses(String ownerId);
	/**
	 * 不同业务有特殊查询参数，需覆盖此方法，返回自己的查询结果
	 * @param applyUnitId
	 * @param businessType
	 * @param status
	 * @param businessId
	 * @param page
	 * @param paramMap 不同业务的查询参数map
	 * @return
	 */
	public List<FlowApply> getFlowApplys(String applyUnitId, int businessType, int status,
			String businessId, Pagination page,Map<String,String> paramMap);
	/**
	 * 不同业务有特殊查询参数，需覆盖此方法，返回自己的查询结果
	 * @param applyUnitId
	 * @param businessType
	 * @param status
	 * @param businessId
	 * @param page
	 * @param paramMap 不同业务的查询参数map
	 * @return
	 */
	public List<FlowAudit> getFlowAudits(String auditUnitId, int businessType, int status, int operateType,
			String applyUnitId, Pagination page,Map<String, String> paramMap);
	/**
	 * 不同业务有特殊的成员类型 比如有些成员是选择某某列表 需要从数据库修改的。
	 * @param fields
	 */
	public void operateField(List<Field> fields);
	
	/**
	 * 处理业务
	 */
	public void saveDisposeBusiness(FlowApply apply);

	/**
	 * 不同业务有特殊查询参数，需覆盖此方法，返回自己的查询结果
	 * 
	 * @param applyArrangeId
	 * @param businessType
	 * @param status
	 * @param page
	 * @param paramMap
	 *            不同业务的查询参数map
	 * @return
	 */
	public List<FlowApply> getFlowApplys(String applyArrangeId,
			int businessType, int status, Pagination page,
			Map<String, String> paramMap);

	/**
	 * 不同业务有特殊查询参数，需覆盖此方法，返回自己的查询结果
	 * 
	 * @param arrangeId
	 * @param roleId
	 * @param businessType
	 * @param status
	 * @param businessId
	 * @param page
	 * @param paramMap
	 *            不同业务的查询参数map
	 * @return
	 */
	public List<FlowAudit> getFlowAudits(String arrangeId, String roleId,
			int businessType, int status, int operateType, String applyUnitId,
			Pagination page, Map<String, String> paramMap);

}
