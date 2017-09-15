/* 
 * @(#)FlowApplyServiceImpl.java    Created on 2012-11-2
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.auditflow.manager.FlowException;
import net.zdsoft.eis.base.auditflow.manager.dao.FlowApplyDao;
import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.entity.BusinessOwner;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.auditflow.manager.service.ApplyBusinessService;
import net.zdsoft.eis.base.auditflow.manager.service.BusinessOwnerService;
import net.zdsoft.eis.base.auditflow.manager.service.FlowApplyService;
import net.zdsoft.eis.base.auditflow.manager.service.FlowAuditService;
import net.zdsoft.eis.base.auditflow.template.entity.FlowInitiator;
import net.zdsoft.eis.base.auditflow.template.entity.FlowType;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.form.Field;
import net.zdsoft.eis.base.form.FieldService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.UUIDUtils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-2 下午05:24:25 $
 */
public class FlowApplyServiceImpl implements FlowApplyService {
	private FlowApplyDao flowApplyDao;
	private FlowAuditService flowAuditService;
	private McodedetailService mcodedetailService;
	private UnitService unitService;
	private FieldService fieldService;

	public void setFieldService(FieldService fieldService) {
		this.fieldService = fieldService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	public void setFlowAuditService(FlowAuditService flowAuditService) {
		this.flowAuditService = flowAuditService;
	}

	public void setFlowApplyDao(FlowApplyDao flowApplyDao) {
		this.flowApplyDao = flowApplyDao;
	}

	private Map<String, Map<String, String>> getMcodeMap(ApplyBusiness dest, List<Field> fields) {

		// 微代码
		List<String> mcodeIds = new ArrayList<String>();
		for (Field field : fields) {
			String mcode = field.getMcode();
			if (null != mcode) {
				mcodeIds.add(mcode);
			}
			
			if (field.getChildField() != null){
				mcode = field.getChildField().getMcode();
				if (null != mcode) {
					mcodeIds.add(mcode);
				}
			}
		}
		Map<String, Map<String, String>> mcodeMap = mcodedetailService.getContent2Map(mcodeIds
				.toArray(new String[0]));
		return mcodeMap;
	}

	private void fillEntityValues(List<FlowApply> applys) {
		if (applys.size() == 0)
			return;

		List<Field> fields = fieldService.getFiledList(applys.get(0).getBusinessType());
		Map<String, Map<String, String>> mcodeMap = getMcodeMap(applys.get(0).getBusiness(), fields);
		for (FlowApply apply : applys) {
			fillFieldValues(apply, fields, mcodeMap, false);
		}
	}

	private void fillFieldValue(Field field, ApplyBusiness dest,
			Map<String, Map<String, String>> mcodeMap, boolean old) {
		String name = field.getName();
		Object d = null;
		try {
			d = PropertyUtils.getSimpleProperty(dest, name);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String value = "";
		if (d != null) {
			if ("date".equals(field.getType().toLowerCase())){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				d = format.format(d);
			}
			value = String.valueOf(d).trim();
		}

		String wappedValue = null;

		// 转换
		String mcode = field.getMcode();
		if (null == mcode) {
			wappedValue = value;
		} else {
			wappedValue = mcodeMap.get(mcode).get(value);
		}
		
		if ("file".equals(field.getType().toLowerCase())){
			value = dest.getFilePath();
			wappedValue = dest.getFileName();
		}
		if (StringUtils.isBlank(wappedValue)){
			wappedValue = "";
		}
		
		if (old) {
			field.setOldValue(value);
			field.setWrappedOldValue(wappedValue);
		} else {
			field.setValue(value);
			field.setWrappedValue(wappedValue);
		}
	}

	/**
	 * 修改操作：整理出修改内容
	 * 
	 * @param apply
	 * @param fields
	 * @param fillOld
	 *            是否填充原值
	 */
	private void fillFieldValues(FlowApply apply, List<Field> fields,
			Map<String, Map<String, String>> mcodeMap, boolean fillOld) {
		fillFieldValues(apply.getApplyBusinessService(), apply.getBusinessType(),
				apply.getOperateType(), apply.getBusiness(), fields, mcodeMap, fillOld);
	}

	/**
	 * 修改操作：整理出修改内容
	 * @param businessType
	 * @param fields
	 * @param fillOld
	 *            是否填充原值
	 * @param apply
	 */
	private void fillFieldValues(ApplyBusinessService<ApplyBusiness> applyBusinessService,
			int businessType, int operateType, ApplyBusiness dest,
			List<Field> fields, Map<String, Map<String, String>> mcodeMap, boolean fillOld) {
		if (dest == null)
			return;

		if(null == fields){
			fields = fieldService.getFiledList(businessType,applyBusinessService);
		}
		
		if (null == mcodeMap) {
			mcodeMap = getMcodeMap(dest, fields);
		}	
		
		ApplyBusiness source = null;
		if (fillOld && FlowApply.OPERATE_TYPE_MODIFY == operateType) {
			source = applyBusinessService.getBusiness(dest.getBusinessId());
		}
		
		Map<String, Field> fieldMap = new HashMap<String, Field>();

		// 取字段值
		for (Field f : fields) {
			Field field = f.clone();
			fillFieldValue(field, dest, mcodeMap, false);

			if (fillOld && FlowApply.OPERATE_TYPE_MODIFY == operateType) {
				fillFieldValue(field, source, mcodeMap, true);
			}

			fieldMap.put(field.getName(), field);
			if (field.getChildField() != null){
				Field child = field.getChildField().clone();
				fillFieldValue(child, dest, mcodeMap, false);

				if (fillOld && FlowApply.OPERATE_TYPE_MODIFY == operateType) {
					fillFieldValue(child, source, mcodeMap, true);
				}
				field.setChildField(child);
			}
		}
		// 自定义转换
		applyBusinessService.fillFieldModification(fieldMap);

		// 排序
		List<Field> modifications = new ArrayList<Field>(fieldMap.values());
		Collections.sort(modifications, new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				return o1.getOrder() - o2.getOrder();
			}
		});
		// 填充修改内容
		dest.setFieldValues(modifications);

	}

	public void addFlowApply(FlowApply apply) {
		// TODO
		// 如果不需要走审核流程，则直接保存
		// flowApply.getApplyBusinessService().addBusiness(flowApply.getBusiness());

		// 否则，走审核流程，保存临时表信息、申请信息，生成审核流程

		// 申请业务
		ApplyBusiness business = apply.getBusiness();

		// 申请id
		String applyId = apply.getId();
		if (StringUtils.isEmpty(applyId)) {// 新增时为空
			applyId = UUIDUtils.newId();
			apply.setId(applyId);
			business.setId(applyId);
		}

		// 业务id
		String businessId = apply.getBusinessId();
		if (StringUtils.isEmpty(businessId)) {// 新增业务类型
			apply.setBusinessId(UUIDUtils.newId());
		}

		flowApplyDao.addFlowApply(apply);
		apply.getApplyBusinessService().addApplyBusiness(business);

		FlowInitiator initiator = new FlowInitiator(applyId, apply.getApplyUnitId(),
				business.getOrganizeUnitId());
		FlowType type = new FlowType(apply.getBusinessType(),business.getFlowTypeValue());
		int steps = 0;
		steps = flowAuditService.saveCreateSteps(initiator, type);
		if (steps == 0) {
			throw new FlowException("系统不支持该类型的流程，可能该类型申请没有定义审核流程。");
		}

		// 直接提交申请
		if (FlowApply.STATUS_IN_AUDIT == apply.getStatus()) {
			saveSubmitFlowApply(applyId);
		}
	}

	public void saveSubmitFlowApply(String... applyIds) {
		// 修改apply表的状态
		flowApplyDao.updateStatus(FlowApply.STATUS_IN_AUDIT, applyIds);

		// 启动审核流程
		for (int i = 0; i < applyIds.length; i++) {
			flowAuditService.saveStartFlow(applyIds[i]);
		}
	}

	public void saveCancelFlowApply(String... applyIds) {

		// 修改apply表的状态到未提交状态
		flowApplyDao.updateStatus(FlowApply.STATUS_PREPARING, applyIds);

		// 撤消审核流程
		for (int i = 0; i < applyIds.length; i++) {
			flowAuditService.saveCancelFlow(applyIds[i]);
		}
	}

	public void deleteFlowApply(ApplyBusinessService<ApplyBusiness> applyBusinessService,
			String[] flowApplyIds) {
		applyBusinessService.deleteApplyBusiness(flowApplyIds);

		flowApplyDao.deleteFlowApply(flowApplyIds);

		flowAuditService.deleteFlowAudits(flowApplyIds);
	}

	public void updateFlowApply(FlowApply flowApply) {
		// TODO
		// 如果不需要走审核流程，则直接保存
		// flowApply.getApplyBusinessService().updateBusiness(flowApply.getBusiness());

		flowApply.getApplyBusinessService().updateApplyBusiness(flowApply.getBusiness());

		// 否则，走审核流程，保存临时表信息、申请信息
		flowApplyDao.updateFlowApply(flowApply);
	}

	public FlowApply getFlowApply(ApplyBusinessService<ApplyBusiness> applyBusinessService,
			String flowApplyId) {
		// TODO 审核通过时，通过备份表取申请、流程等数据

		FlowApply apply = flowApplyDao.getFlowApply(flowApplyId);
		apply.getBusinessId();
		// 申请对象
		ApplyBusiness business = applyBusinessService.getApplyBusiness(apply.getId());
		apply.setBusiness(business);

		// 申请主对象
		if (!(business.isOwner())) {
			BusinessOwnerService businessOwnerService = applyBusinessService
					.getBusinessOwnerService();
			BusinessOwner owner = businessOwnerService.getOwner(business.getOwnerId());
			apply.setOwner(owner);
		}

		List<FlowAudit> audits = flowAuditService.getFlowAudits(apply.getId());
		apply.setAudits(audits);

		apply.setApplyBusinessService(applyBusinessService);
		fillFieldValues(apply, null, null, true);
		return apply;
	}

	public FlowApply getFlowApplyInFlow(String businessId) {
		return flowApplyDao.getFlowApplyInFlow(businessId);
	}

	/**
	 * 填充申请信息
	 * 
	 * @param applyBusinessService
	 * @param applys
	 */
	private void fillApplys(ApplyBusinessService<ApplyBusiness> applyBusinessService,
			List<FlowApply> applys) {

		if (applys.size() == 0)
			return;

		// 取业务对象
		String[] applyIds = new String[applys.size()];
		for (int i = 0; i < applys.size(); i++) {
			applyIds[i] = applys.get(i).getId();
		}
		Map<String, ApplyBusiness> businessMap = applyBusinessService.getApplyBusinessMap(applyIds);

		// 取业务主对象
		Set<String> ownerIds = new HashSet<String>();
		Collection<ApplyBusiness> businesses = businessMap.values();
		for (ApplyBusiness b : businesses) {
			ownerIds.add(b.getOwnerId());
		}
		BusinessOwnerService businessOwnerService = applyBusinessService.getBusinessOwnerService();
		Map<String, BusinessOwner> ownerMap = businessOwnerService.getOwnerMap(ownerIds
				.toArray(new String[0]));

		// 填充业务对象
		for (FlowApply apply : applys) {
			ApplyBusiness business = businessMap.get(apply.getId());
			apply.setBusiness(business);

			BusinessOwner owner = ownerMap.get(business.getOwnerId());
			apply.setOwner(owner);
			apply.setApplyBusinessService(applyBusinessService);
		}

		fillEntityValues(applys);

	}

	public List<FlowApply> getApplys(ApplyBusinessService<ApplyBusiness> applyBusinessService,
			String applyUnitId, int businessType, int status, String businessId, Pagination page,Map<String, String> paramMap) {
		List<FlowApply> applys = applyBusinessService.getFlowApplys(applyUnitId, businessType, status, businessId, page, paramMap);
		fillApplys(applyBusinessService, applys);

		return applys;
	}

	public List<FlowApply> getAuditApplys(ApplyBusinessService<ApplyBusiness> applyBusinessService,
			String auditUnitId, int businessType, int status, int operateType, String applyUnitId, Pagination page, Map<String, String> paramMap) {
		List<FlowApply> applys = new ArrayList<FlowApply>();

		List<FlowAudit> audits = applyBusinessService.getFlowAudits(auditUnitId, businessType, status, operateType, applyUnitId, page, paramMap);
		if (audits.size() == 0) {
			return applys;
		}

		// 取申请信息
		String[] applyIds = new String[audits.size()];
		Map<String, FlowAudit> auditMap = new HashMap<String, FlowAudit>();
		for (int i = 0; i < audits.size(); i++) {
			FlowAudit audit = audits.get(i);
			applyIds[i] = audit.getApplyId();
			auditMap.put(audit.getApplyId(), audit);
		}
		applys = flowApplyDao.getFlowApplys(applyIds);

		// 单位信息
		Set<String> unitIds = new HashSet<String>();
		for (FlowApply apply : applys) {
			unitIds.add(apply.getApplyUnitId());
		}
		Map<String, Unit> unitMap = unitService.getUnitMap(unitIds.toArray(new String[0]));

		for (FlowApply apply : applys) {
			List<FlowAudit> list = new ArrayList<FlowAudit>();
			list.add(auditMap.get(apply.getId()));
			apply.setAudits(list);

			Unit unit = unitMap.get(apply.getApplyUnitId());
			if (unit != null) {
				apply.setApplyUnitName(unit.getName());
			}
		}

		fillApplys(applyBusinessService, applys);

		return applys;
	}

	public void saveAudits(ApplyBusinessService<ApplyBusiness> applyBusinessService,
			FlowAudit audit, String... auditIds) {
		for (String id : auditIds) {
			audit.setId(id);
			saveAudit(applyBusinessService, audit);
		}
	}

	private void saveAudit(ApplyBusinessService<ApplyBusiness> applyBusinessService, FlowAudit audit) {

		FlowAudit originalStep = flowAuditService.getFlowAudit(audit.getId());
		if (null == originalStep || FlowAudit.STATUS_CHECKING != originalStep.getStatus()) {
			throw new FlowException("申请的状态已经改变，或已经被删除");
		} else {
			int step = originalStep.getAuditOrder();

			audit.setApplyId(originalStep.getApplyId());
			audit.setApplyUnitId(originalStep.getApplyUnitId());
			audit.setAuditOrder(originalStep.getAuditOrder());
			audit.setBusinessType(originalStep.getBusinessType());
			audit.setAuditUnitId(originalStep.getAuditUnitId());
			audit.setAuditUnitLevel(originalStep.getAuditUnitLevel());

			// 更新审核步骤状态
			flowAuditService.saveUpOneStep(audit);

			// 审核不通过的情况，更新业务申请状态为审核不通过
			if (FlowAudit.STATUS_AUDIT_REJECT == audit.getStatus()) {
				flowApplyDao.updateStatus(FlowApply.STATUS_AUDIT_REJECT, audit.getApplyId());
				return;
			}

			// 如果是审核流程的最后一步，做学生相关操作和流程备份操作
			if (FlowAudit.STEP_FINAL == step) {
				finalAudit(applyBusinessService, audit);
			}
		}
	}

	/**
	 * 最终一步审核
	 */
	private void finalAudit(ApplyBusinessService<ApplyBusiness> applyBusinessService,
			FlowAudit audit) {
		// 更新异动申请流程为审核通过状态
		flowApplyDao.updateStatus(FlowApply.STATUS_AUDIT_PASS, audit.getApplyId());

		FlowApply apply = flowApplyDao.getFlowApply(audit.getApplyId());

		ApplyBusiness business = applyBusinessService.getApplyBusiness(apply.getId());
		business.setId(business.getBusinessId());// 真正的业务对象的id=临时表的businessId

		// 增删改：直接调用对应的方法进行处理，否则交由业务自身进行处理
		int operateType = apply.getOperateType();

		applyBusinessService.saveDisposeBusiness(business,operateType);
		

		// 全部保留历史轨迹，定时删除
		// // 删除临时表
		// applyBusinessService.deleteApplyBusiness(apply.getId());
		//
		// // 删除申请表
		// flowApplyDao.deleteFlowApply(apply.getId());
		//
		// // 删除审核结果表
		// flowAuditService.deleteFlowAudits(apply.getId());

	}

	public List<FlowAudit> getShowSteps(String applyId) {
		FlowApply apply = flowApplyDao.getFlowApply(applyId);

		// 申请可能被撤销或删除
		if (apply == null || FlowApply.STATUS_PREPARING == apply.getStatus()) {
			return null;
		}

		// 申请单位信息
		Unit unit = unitService.getUnit(apply.getApplyUnitId());
		FlowAudit applyAudit = new FlowAudit();
		applyAudit.setAuditUnitLevelName(getUnitLevelName(unit.getUnitclass(),
				unit.getRegionlevel())
				+ "申请");
		applyAudit.setAuditOrder(0);
		applyAudit.setAuditUnitName(unit.getName());
		applyAudit.setStatus(FlowAudit.STATUS_AUDIT_PASS);

		// 审核信息
		List<FlowAudit> steps = flowAuditService.getFlowAudits(applyId);
		int steporder = 0;
		for (FlowAudit step : steps) {
			if (FlowAudit.STATUS_AUDIT_REJECT == step.getStatus()) {
				steporder = -1;
			} else if (steporder <= -1) {
				steporder--;
			} else {
				steporder++;
			}
			step.setAuditOrder(steporder);
			step.setAuditUnitLevelName(getUnitLevelName(step.getAuditUnitClass(),
					step.getAuditUnitLevel())
					+ "审核");
		}

		steps.add(0, applyAudit);
		return steps;
	}

	public static String getUnitLevelName(int unitClass, int level) {
		if (unitClass == Unit.UNIT_CLASS_SCHOOL) {
			return "学校";
		}
		if (Unit.UNIT_REGION_COUNTRY == level) {
			return "国家";
		} else if (Unit.UNIT_REGION_PROVINCE == level) {
			return "省教育厅";
		} else if (Unit.UNIT_REGION_CITY == level) {
			return "市教育局";
		} else if (Unit.UNIT_REGION_COUNTY == level) {
			return "区教育局";
		} else if (Unit.UNIT_REGION_LEVEL == level) {
			return "乡镇教育局";
		}
		return null;
	}

	@Override
	public List<ApplyBusiness> getBusinesses(
			ApplyBusinessService<ApplyBusiness> applyBusinessService, int businessType, String ownerId) {
		List<ApplyBusiness> list = applyBusinessService.getBusinesses(ownerId);

		if (list.size() == 0)
			return list;

		List<Field> fields = fieldService.getFiledList(businessType);
		Map<String, Map<String, String>> mcodeMap = getMcodeMap(list.get(0), fields);
		for (ApplyBusiness business : list) {
			fillFieldValues(applyBusinessService, businessType, FlowApply.OPERATE_TYPE_NO, business,
					fields, mcodeMap, false);
		}
		return list;
	}

}
