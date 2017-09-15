/* 
 * @(#)FlowAuditServiceImpl.java    Created on 2012-11-2
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.auditflow.manager.dao.FlowAuditDao;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.auditflow.manager.service.FlowAuditService;
import net.zdsoft.eis.base.auditflow.template.entity.BusinessAssembler;
import net.zdsoft.eis.base.auditflow.template.entity.FlowInitiator;
import net.zdsoft.eis.base.auditflow.template.entity.FlowStep;
import net.zdsoft.eis.base.auditflow.template.entity.FlowType;
import net.zdsoft.eis.base.auditflow.template.service.FlowTemplateService;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.keel.util.Pagination;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-2 下午05:27:34 $
 */
public class FlowAuditServiceImpl implements FlowAuditService {
	private FlowAuditDao flowAuditDao;
	private UnitService unitService;
	private FlowTemplateService flowTemplateService;

	public void setFlowTemplateService(FlowTemplateService flowTemplateService) {
		this.flowTemplateService = flowTemplateService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setFlowAuditDao(FlowAuditDao flowAuditDao) {
		this.flowAuditDao = flowAuditDao;
	}

	public void deleteFlowAudits(String... applyIds) {
		flowAuditDao.deleteFlowAudits(applyIds);
	}

	public FlowAudit getFlowAudit(String flowAuditId) {
		return flowAuditDao.getFlowAudit(flowAuditId);
	}

	public List<FlowAudit> getFlowAudits(String auditUnitId, int businessType, int status,
			int operateType, String applyUnitId, Pagination page) {
		return flowAuditDao.getFlowAudits(auditUnitId, businessType, status, operateType,
				applyUnitId, page);
	}

	public List<FlowAudit> getFlowAudits(String applyId) {
		List<FlowAudit> audits = flowAuditDao.getFlowAudits(applyId);
		Collections.sort(audits, STEP_ORDER);

		String[] unitIds = new String[audits.size()];
		for (int i = 0; i < audits.size(); i++) {
			FlowAudit audit = audits.get(i);
			unitIds[i] = audit.getAuditUnitId();
		}
		Map<String, Unit> unitMap = unitService.getUnitMap(unitIds);

		for (FlowAudit audit : audits) {
			Unit unit = unitMap.get(audit.getAuditUnitId());
			if (unit != null) {
				audit.setAuditUnitName(unit.getName());
				audit.setAuditUnitClass(unit.getUnitclass());
			}
		}
		return audits;
	}

	/**
	 * 业务步骤默认排序比较器<br>
	 * 按照 0,1,2...,-1 的顺序排列
	 */
	private static final Comparator<FlowAudit> STEP_ORDER = new Comparator<FlowAudit>() {
		// 对步骤排序,按照 0,1,2...,-1 的顺序排列
		public int compare(FlowAudit step1, FlowAudit step2) {
			int value1 = step1.getAuditOrder();
			int value2 = step2.getAuditOrder();
			if (FlowAudit.STEP_FINAL == value1) {
				value1 = Integer.MAX_VALUE;
			}
			if (FlowAudit.STEP_FINAL == value2) {
				value2 = Integer.MAX_VALUE;
			}
			return value1 - value2;
		}
	};

	/**
	 * 业务模板步骤排序比较器<br>
	 * 按照 0,1,2...,-1 的顺序排列
	 */
	private static final Comparator<FlowStep> BUSINESS_TEMPLATE_STEP_ORDER = new Comparator<FlowStep>() {
		// 对步骤排序,按照 0,1,2...,-1 的顺序排列
		public int compare(FlowStep step1, FlowStep step2) {
			int value1 = step1.getStepValue();
			int value2 = step2.getStepValue();
			if (FlowAudit.STEP_FINAL == value1) {
				value1 = Integer.MAX_VALUE;
			}
			if (FlowAudit.STEP_FINAL == value2) {
				value2 = Integer.MAX_VALUE;
			}
			return value1 - value2;
		}
	};

	@Override
	public int saveCreateSteps(FlowInitiator initiator, FlowType flowType) {
		BusinessAssembler assembler = new BusinessAssembler(initiator, flowType, unitService);
		List<FlowStep> tmpSteps = flowTemplateService.getSteps(assembler.getSuitedTemplate());
		Collections.sort(tmpSteps, BUSINESS_TEMPLATE_STEP_ORDER);

		// avoiding same operate-unit in two consecutive steps
		FlowAudit preStep = new FlowAudit();
		int repeatNum = 0;
		List<FlowAudit> list = new ArrayList<FlowAudit>();
		for (FlowStep tmpStep : tmpSteps) {
			FlowAudit step = assembler.assembleStepInfo(tmpStep);
			if (step.getAuditUnitId().equals(preStep.getAuditUnitId())) {
				repeatNum++;
			} else {
				step.setAuditOrder(step.getAuditOrder() - repeatNum);
				list.add(step);
				preStep = step;
			}
		}

		List<FlowAudit> audits = new ArrayList<FlowAudit>();
		Set<String> unitIdSet = new HashSet<String>();
		for (FlowAudit step : list) {
			String unitId = step.getAuditUnitId();
			Unit u = unitService.getUnit(unitId);
			if (u == null)
				continue;
			if (u.getUnitclass() == Unit.UNIT_CLASS_EDU) {
				if (unitIdSet.contains(step.getAuditUnitId())) {
					for (int k = audits.size() - 1; k >= 0; k--) {
						if (step.getAuditUnitId().equals(audits.get(k).getAuditUnitId())) {
							audits.remove(k);
						}
					}
				}
				unitIdSet.add(step.getAuditUnitId());
				audits.add(step);
			} else {
				audits.add(step);
			}
		}

		flowAuditDao.addFlowAudits(audits);
		// fix final step's order
		preStep.setAuditOrder(FlowAudit.STEP_FINAL);

		return audits.size();
	}

	@Override
	public void saveStartFlow(String applyId) {
		List<FlowAudit> steps = flowAuditDao.getFlowAudits(applyId);
		if (steps.isEmpty()) {
			return;
		}
		Collections.sort(steps, STEP_ORDER);
		// set first step CHECKING
		steps.get(0).setStatus(FlowAudit.STATUS_CHECKING);
		// set others PREPARING
		for (int i = 1; i < steps.size(); i++) {
			steps.get(i).setStatus(FlowAudit.STATUS_PREPARING);
		}
		flowAuditDao.updateStatus(steps);
	}

	@Override
	public void saveCancelFlow(String applyId) {
		List<FlowAudit> steps = flowAuditDao.getFlowAudits(applyId);
		if (steps.isEmpty()) {
			return;
		}
		for (FlowAudit step : steps) {
			step.setStatus(FlowAudit.STATUS_PREPARING);
		}
		flowAuditDao.updateStatus(steps);
	}

	@Override
	public int saveUpOneStep(FlowAudit currentStep) {
		// 保存当前步骤的审核信息
		flowAuditDao.updateFlowAudit(currentStep);

		int state = currentStep.getStatus();

		// update related step state
		int order = currentStep.getAuditOrder();
		if (FlowAudit.STEP_FINAL == order) {
			return order;
		}

		if (FlowAudit.STATUS_AUDIT_PASS == state || FlowAudit.STATUS_PREPARING == state
				|| FlowAudit.STATUS_CHECKING == state) {
			List<FlowAudit> updateList = new ArrayList<FlowAudit>();
			List<FlowAudit> steps = flowAuditDao.getFlowAudits(currentStep.getApplyId());
			Collections.sort(steps, STEP_ORDER);
			boolean started = false;
			for (FlowAudit sp : steps) {
				if (started) {
					if (FlowAudit.STATUS_AUDIT_PASS == state) {
						sp.setStatus(FlowAudit.STATUS_CHECKING);
						updateList.add(sp);
						break;
					} else {
						sp.setStatus(FlowAudit.STATUS_PREPARING);
						updateList.add(sp);
					}
				} else {
					started = (sp.getAuditOrder() == order);
				}
			}
			flowAuditDao.updateStatus(updateList);
		}

		return order;
	}

	/**
	 * 获取申请单位信息
	 * 
	 * @param auditUnitId
	 * @param businessType
	 * @param status
	 * @param operateType
	 * @return
	 */
	public List<Unit> getApplyUnits(String auditUnitId, int businessType, int status,
			int operateType) {
		Map<String, Unit> unitMap = flowAuditDao.getApplyUnits(auditUnitId, businessType, status,
				operateType);
		if (unitMap.isEmpty()) {
			return null;
		}
		List<Unit> list = new ArrayList<Unit>();
		list.addAll(unitMap.values());
		return list;
	}
}
