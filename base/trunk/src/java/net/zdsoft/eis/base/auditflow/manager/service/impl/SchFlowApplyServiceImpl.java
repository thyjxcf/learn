package net.zdsoft.eis.base.auditflow.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.auditflow.manager.FlowException;
import net.zdsoft.eis.base.auditflow.manager.dao.FlowApplyDao;
import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.auditflow.manager.service.ApplyBusinessService;
import net.zdsoft.eis.base.auditflow.manager.service.SchFlowApplyService;
import net.zdsoft.eis.base.auditflow.manager.service.SchFlowAuditService;
import net.zdsoft.eis.base.auditflow.template.entity.FlowInitiator;
import net.zdsoft.eis.base.auditflow.template.entity.FlowType;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.frame.client.BaseDao.SingleRow;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keel.util.UUIDUtils;

import org.apache.commons.lang.StringUtils;

public class SchFlowApplyServiceImpl implements SchFlowApplyService{
	private FlowApplyDao flowApplyDao;
	private SchFlowAuditService schFlowAuditService;
	private UnitService unitService;
	private CustomRoleService customRoleService;

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setFlowApplyDao(FlowApplyDao flowApplyDao) {
		this.flowApplyDao = flowApplyDao;
	}

	public void setSchFlowAuditService(SchFlowAuditService schFlowAuditService) {
		this.schFlowAuditService = schFlowAuditService;
	}
	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}
	
	public FlowApply getFlowApply(String flowApplyId) {
		return flowApplyDao.getFlowApply(flowApplyId);
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
			// business.setId(applyId);
		}

		// 业务id
		String businessId = apply.getBusinessId();
		if (StringUtils.isEmpty(businessId)) {// 新增业务类型
			apply.setBusinessId(UUIDUtils.newId());
		}

		flowApplyDao.addFlowApply(apply);

		FlowInitiator initiator = new FlowInitiator(applyId,
				apply.getApplyUnitId(), apply.getApplyUserId(),
				apply.getBusinessId());
		FlowType type = new FlowType(apply.getBusinessType(),
				business.getFlowTypeValue());
		int steps = 0;
		steps = schFlowAuditService.saveCreateSteps(
				apply.getApplyBusinessService(), initiator, type);
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
			schFlowAuditService.saveStartFlow(applyIds[i]);
		}
	}

	public void saveCancelFlowApply(String... applyIds) {

		// 修改apply表的状态到未提交状态
		flowApplyDao.updateStatus(FlowApply.STATUS_PREPARING, applyIds);
		// 撤消审核流程
		for (int i = 0; i < applyIds.length; i++) {
			schFlowAuditService.saveCancelFlow(applyIds[i]);
		}
	}

	public void deleteFlowApply(
			ApplyBusinessService<ApplyBusiness> applyBusinessService,
			String[] flowApplyIds) {
		flowApplyDao.deleteFlowApply(flowApplyIds);
		schFlowAuditService.deleteFlowAudits(flowApplyIds);
	}

	public void updateFlowApply(FlowApply flowApply) {
		// TODO
		// 如果不需要走审核流程，则直接保存
		// flowApply.getApplyBusinessService().updateBusiness(flowApply.getBusiness());

		// 否则，走审核流程，保存临时表信息、申请信息
		flowApplyDao.updateFlowApply(flowApply);
	}

	public FlowApply getFlowApply(
			ApplyBusinessService<ApplyBusiness> applyBusinessService,
			String flowApplyId) {
		// TODO 审核通过时，通过备份表取申请、流程等数据

		FlowApply apply = flowApplyDao.getFlowApply(flowApplyId);
		apply.getBusinessId();
		// 申请对象
		ApplyBusiness business = applyBusinessService.getApplyBusiness(apply
				.getId());
		apply.setBusiness(business);

		List<FlowAudit> audits = schFlowAuditService.getFlowAudits(apply.getId());
		apply.setAudits(audits);

		apply.setApplyBusinessService(applyBusinessService);
		return apply;
	}

	public FlowApply getFlowApplyInFlow(String businessId) {
		return flowApplyDao.getFlowApplyInFlow(businessId);
	}

	public List<FlowApply> getApplys(
			ApplyBusinessService<ApplyBusiness> applyBusinessService,
			String applyUnitId, int businessType, int status, Pagination page,
			Map<String, String> paramMap) {
		List<FlowApply> applys = applyBusinessService.getFlowApplys(
				applyUnitId, businessType, status, page, paramMap);
		return applys;
	}

	public List<FlowApply> getAuditApplys(
			ApplyBusinessService<ApplyBusiness> applyBusinessService,
			String arrangeId, String roleId, int businessType, int status,
			int operateType, String applyUnitId, Pagination page,
			Map<String, String> paramMap) {
		List<FlowApply> applys = new ArrayList<FlowApply>();

		List<FlowAudit> audits = applyBusinessService.getFlowAudits(arrangeId,
				roleId, businessType, status, operateType, applyUnitId, page,
				paramMap);
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
		Map<String, Unit> unitMap = unitService.getUnitMap(unitIds
				.toArray(new String[0]));

		for (FlowApply apply : applys) {
			List<FlowAudit> list = new ArrayList<FlowAudit>();
			list.add(auditMap.get(apply.getId()));
			apply.setAudits(list);

			Unit unit = unitMap.get(apply.getApplyUnitId());
			if (unit != null) {
				apply.setApplyUnitName(unit.getName());
			}
		}
		return applys;
	}

	public void saveAudits(
			ApplyBusinessService<ApplyBusiness> applyBusinessService,
			FlowAudit audit, String... auditIds) {
		for (String id : auditIds) {
			audit.setId(id);
			saveAudit(applyBusinessService, audit);
		}
	}

	private void saveAudit(
			ApplyBusinessService<ApplyBusiness> applyBusinessService,
			FlowAudit audit) {

		FlowAudit originalStep = schFlowAuditService.getFlowAudit(audit.getId());
		if (null == originalStep
				|| FlowAudit.STATUS_CHECKING != originalStep.getStatus()) {
			throw new FlowException("申请的状态已经改变，或已经被删除");
		} else {
			int step = originalStep.getAuditOrder();

			audit.setApplyId(originalStep.getApplyId());
			audit.setApplyUnitId(originalStep.getApplyUnitId());
			audit.setAuditOrder(originalStep.getAuditOrder());
			audit.setBusinessType(originalStep.getBusinessType());
			audit.setAuditUnitId(originalStep.getAuditUnitId());

			// 更新审核步骤状态
			schFlowAuditService.saveUpOneStep(applyBusinessService, audit);

			// 审核不通过的情况，更新业务申请状态为审核不通过
			if (FlowAudit.STATUS_AUDIT_REJECT == audit.getStatus()) {
				flowApplyDao.updateStatus(FlowApply.STATUS_AUDIT_REJECT,
						audit.getApplyId());
				return;
			}

			// 如果是审核流程的最后一步，做学生相关操作和流程备份操作
			if (FlowAudit.STEP_FINAL == step
					|| FlowApply.STATUS_AUDIT_FINISH == audit.getStatus()) {
				finalAudit(applyBusinessService, audit);
			}
		}
	}

	/**
	 * 最终一步审核
	 */
	private void finalAudit(
			ApplyBusinessService<ApplyBusiness> applyBusinessService,
			FlowAudit audit) {
		// 更新异动申请流程为审核通过状态
		flowApplyDao.updateStatus(FlowApply.STATUS_AUDIT_PASS,
				audit.getApplyId());
		FlowApply apply = flowApplyDao.getFlowApply(audit.getApplyId());

		// ApplyBusiness business =
		// applyBusinessService.getApplyBusiness(apply.getId());
		// business.setId(business.getBusinessId());// 真正的业务对象的id=临时表的businessId
		// 增删改：直接调用对应的方法进行处理，否则交由业务自身进行处理
		applyBusinessService.saveDisposeBusiness(apply);

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
		applyAudit.setRoleName(apply.getApplyArrangeName() + "申请");
		applyAudit.setAuditUsername(apply.getApplyUnitName());
		applyAudit.setAuditOrder(0);
		applyAudit.setAuditUnitName(unit.getName());
		applyAudit.setStatus(FlowAudit.STATUS_AUDIT_PASS);

		// 审核信息
		List<FlowAudit> steps = schFlowAuditService.getFlowAudits(applyId);
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
			step.setRoleName(getRoleName(step.getRoleId()) + "审核");
		}

		steps.add(0, applyAudit);
		return steps;
	}

	private String getRoleName(String roleId) {
		CustomRole role = customRoleService.getCustomRoleById(roleId);
		if (role != null) {
			return role.getRoleName();
		}
		return "未知";
	}

	public List<FlowApply> getFlowApplysByBusinessIds(String[] businessIds) {
		return flowApplyDao.getFlowApplysByBusinessIds(businessIds);
	}

	public Map<String, FlowApply> getFlowApplyMapByBusinessIds(
			String[] businessIds) {
		List<FlowApply> applyList = getFlowApplysByBusinessIds(businessIds);
		Map<String, FlowApply> applyMap = new HashMap<String, FlowApply>();
		for (FlowApply apply : applyList) {
			applyMap.put(apply.getBusinessId(), apply);
		}
		return applyMap;
	}
}
