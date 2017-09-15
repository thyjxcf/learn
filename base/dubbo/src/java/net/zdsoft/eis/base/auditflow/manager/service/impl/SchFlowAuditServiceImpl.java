package net.zdsoft.eis.base.auditflow.manager.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.auditflow.manager.dao.FlowApplyDao;
import net.zdsoft.eis.base.auditflow.manager.dao.FlowAuditDao;
import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.auditflow.manager.service.ApplyBusinessService;
import net.zdsoft.eis.base.auditflow.manager.service.SchFlowAuditService;
import net.zdsoft.eis.base.auditflow.template.entity.BusinessAssembler;
import net.zdsoft.eis.base.auditflow.template.entity.Flow;
import net.zdsoft.eis.base.auditflow.template.entity.FlowInitiator;
import net.zdsoft.eis.base.auditflow.template.entity.FlowStep;
import net.zdsoft.eis.base.auditflow.template.entity.FlowType;
import net.zdsoft.eis.base.auditflow.template.service.FlowTemplateService;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.keel.util.Pagination;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class SchFlowAuditServiceImpl implements SchFlowAuditService{
	private FlowAuditDao flowAuditDao;
	private FlowApplyDao flowApplyDao;
	private UnitService unitService;
	private FlowTemplateService flowTemplateService;
	private CustomRoleService customRoleService;

	public void setFlowTemplateService(FlowTemplateService flowTemplateService) {
		this.flowTemplateService = flowTemplateService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setFlowAuditDao(FlowAuditDao flowAuditDao) {
		this.flowAuditDao = flowAuditDao;
	}

	public void setFlowApplyDao(FlowApplyDao flowApplyDao) {
		this.flowApplyDao = flowApplyDao;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void deleteFlowAudits(String... applyIds) {
		flowAuditDao.deleteFlowAudits(applyIds);
	}

	public FlowAudit getFlowAudit(String flowAuditId) {
		return flowAuditDao.getFlowAudit(flowAuditId);
	}

	public List<FlowAudit> getFlowAuditList(String[] ids) {
		return flowAuditDao.getFlowAuditList(ids);
	}

	public Map<String, List<FlowAudit>> getFlowAuditsMap(String[] applyIds,
			int state) {
		List<FlowAudit> auditList = flowAuditDao.getFlowAudits(applyIds, state);
		Map<String, List<FlowAudit>> auditMap = new HashMap<String, List<FlowAudit>>();
		if (CollectionUtils.isEmpty(auditList)) {
			return auditMap;
		}
		for (FlowAudit audit : auditList) {
			List<FlowAudit> tempList = auditMap.get(audit.getApplyId());
			if (CollectionUtils.isEmpty(tempList))
				tempList = new ArrayList<FlowAudit>();
			tempList.add(audit);
			auditMap.put(audit.getApplyId(), tempList);
		}
		return auditMap;
	}

	public List<FlowAudit> getFlowAudits(String arrangeId, String roleId,
			int businessType, int status, int operateType, String applyUnitId,
			Pagination page) {
		return flowAuditDao.getFlowAudits(arrangeId, roleId, businessType,
				status, operateType, applyUnitId, page);
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
			}
		}
		return audits;
	}

	public Map<String, List<FlowAudit>> getFlowAuditsMap(String[] applyIds) {
		List<FlowAudit> auditList = flowAuditDao
				.getFlowAuditListByAppIds(applyIds);
		Collections.sort(auditList, STEP_ORDER);
		Map<String, List<FlowAudit>> auditMap = new HashMap<String, List<FlowAudit>>();
		if (CollectionUtils.isEmpty(auditList)) {
			return auditMap;
		}
		for (FlowAudit audit : auditList) {
			List<FlowAudit> tempList = auditMap.get(audit.getApplyId());
			if (CollectionUtils.isEmpty(tempList))
				tempList = new ArrayList<FlowAudit>();
			tempList.add(audit);
			auditMap.put(audit.getApplyId(), tempList);
		}
		return auditMap;
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

	@Override
	public int saveCreateSteps(
			ApplyBusinessService<ApplyBusiness> applyBusinessService,
			FlowInitiator initiator, FlowType flowType) {
		BusinessAssembler assembler = new BusinessAssembler(initiator,
				flowType, unitService, flowTemplateService);
		List<FlowStep> tmpSteps = flowTemplateService.getSuitedSteps(assembler
				.getSuitedTemplate4Sch().getType());
		int stepType = assembler.getStepType();
		List<FlowAudit> audits = new ArrayList<FlowAudit>();
		for (FlowStep tmpStep : tmpSteps) {
			// applyBusinessService.getBusiness(id)
			String arrangeId = applyBusinessService.getArrangeId(
					initiator.getApplyUserId(), tmpStep.getArrangeMethod(),
					tmpStep.getArrangeParam(), initiator.getBusinessId());
			if (StringUtils.isBlank(arrangeId)) {
				throw new RuntimeException("生成步骤出错，arrangeId不能为空");
			}
			CustomRole role = customRoleService.getCustomRoleByRoleCode(
					initiator.getApplyUnitId(), tmpStep.getRoleCode());
			FlowAudit step = assembler.assembleStepInfo(tmpStep, arrangeId,
					role.getId());
			audits.add(step);
		}
		// 1、一次性全部生成步骤
		if (stepType == Flow.STEP_ONE_TIME) {
			flowAuditDao.addFlowAudits(audits);
			return audits.size();
		} else {
			// 2、只生成第一步骤(下一步骤由当前的步骤操作来确定)
			flowAuditDao.addFlowAudits(audits.subList(0, 1));
			return 1;
		}
	}

	private void saveCreateNextStep(
			ApplyBusinessService<ApplyBusiness> applyBusinessService,
			FlowAudit currentStep) {
		List<FlowStep> tmpSteps = flowTemplateService
				.getSuitedSteps(currentStep.getBusinessType());

		List<FlowAudit> nextStep = new ArrayList<FlowAudit>();
		// 只生成下一步骤(下一步骤由当前的步骤操作来确定)
		boolean started = false;
		for (FlowStep step : tmpSteps) {
			if (started) {
				FlowApply flowApply = flowApplyDao.getFlowApply(currentStep
						.getApplyId());
				String arrangeId = applyBusinessService.getArrangeId(
						currentStep.getAuditUserId(), step.getArrangeMethod(),
						step.getArrangeParam(), flowApply.getBusinessId());
				if (StringUtils.isBlank(arrangeId)) {
					throw new RuntimeException("生成步骤出错，arrangeId不能为空");
				}
				currentStep.setArrangeId(arrangeId);
				CustomRole role = customRoleService.getCustomRoleByRoleCode(
						currentStep.getApplyUnitId(), step.getRoleCode());
				currentStep.setRoleId(role.getId());
				nextStep.add(BusinessAssembler.assembleStepInfo(currentStep,
						step.getStepValue()));
				break;
			} else {
				started = (step.getStepValue() == currentStep.getAuditOrder());
			}
		}
		flowAuditDao.addFlowAudits(nextStep);
	}

	@Override
	public void saveStartFlow(String applyId) {
		FlowApply apply = flowApplyDao.getFlowApply(applyId);
		Flow flow = flowTemplateService.getFlow(apply.getBusinessType());
		List<FlowAudit> steps = flowAuditDao.getFlowAudits(applyId);
		if (steps.isEmpty()) {
			return;
		}
		Collections.sort(steps, STEP_ORDER);

		if (Flow.STEP_ONE_TIME == flow.getStepType()) {
			// set first step CHECKING
			steps.get(0).setStatus(FlowAudit.STATUS_CHECKING);
			// set others PREPARING
			for (int i = 1; i < steps.size(); i++) {
				steps.get(i).setStatus(FlowAudit.STATUS_PREPARING);
			}
			flowAuditDao.updateStatus(steps);
		} else {
			List<FlowAudit> firstStep = new ArrayList<FlowAudit>();
			Set<String> otherSteps = new HashSet<String>();
			int count = 0;
			for (FlowAudit step : steps) {
				if (count == 0) {
					step.setStatus(FlowAudit.STATUS_CHECKING);
					firstStep.add(step);
				} else {
					otherSteps.add(step.getId());
				}
				count++;
			}
			flowAuditDao.updateStatus(firstStep);
			flowAuditDao.deleteFlowAuditsByIds(otherSteps
					.toArray(new String[0]));
		}

	}

	@Override
	public void saveCancelFlow(String applyId) {
		FlowApply apply = flowApplyDao.getFlowApply(applyId);
		Flow flow = flowTemplateService.getFlow(apply.getBusinessType());
		List<FlowAudit> steps = flowAuditDao.getFlowAudits(applyId);
		if (steps.isEmpty()) {
			return;
		}
		Collections.sort(steps, STEP_ORDER);

		if (Flow.STEP_ONE_TIME == flow.getStepType()) {
			for (FlowAudit step : steps) {
				step.setStatus(FlowAudit.STATUS_PREPARING);
			}
			flowAuditDao.updateStatus(steps);
		} else {
			List<FlowAudit> firstStep = new ArrayList<FlowAudit>();
			Set<String> otherSteps = new HashSet<String>();
			int count = 0;
			for (FlowAudit step : steps) {
				if (count == 0) {
					step.setStatus(FlowAudit.STATUS_PREPARING);
					firstStep.add(step);
				} else {
					otherSteps.add(step.getId());
				}
				count++;
			}
			flowAuditDao.updateStatus(firstStep);
			flowAuditDao.deleteFlowAuditsByIds(otherSteps
					.toArray(new String[0]));
		}
	}

	@Override
	public int saveUpOneStep(
			ApplyBusinessService<ApplyBusiness> applyBusinessService,
			FlowAudit currentStep) {
		// 保存当前步骤的审核信息
		flowAuditDao.updateFlowAudit(currentStep);
		Flow flow = flowTemplateService.getFlow(currentStep.getBusinessType());
		int state = currentStep.getStatus();
		int stepType = flow.getStepType();
		// update related step state
		int order = currentStep.getAuditOrder();
		if (FlowAudit.STEP_FINAL == order) {
			return order;
		}
		if (Flow.STEP_ONE_TIME == stepType) {
			if (FlowAudit.STATUS_AUDIT_PASS == state
					|| FlowAudit.STATUS_PREPARING == state
					|| FlowAudit.STATUS_CHECKING == state) {
				List<FlowAudit> updateList = new ArrayList<FlowAudit>();
				List<FlowAudit> steps = flowAuditDao.getFlowAudits(currentStep
						.getApplyId());
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
		} else {
			if (FlowAudit.STATUS_AUDIT_PASS == state) {
				saveCreateNextStep(applyBusinessService, currentStep);
			}
		}
		return order;
	}
}
