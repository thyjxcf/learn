package net.zdsoft.eis.base.auditflow.template.entity;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.auditflow.template.service.FlowTemplateService;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.UnitService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 业务组装器
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-12-11 下午05:30:12 $
 */
public class BusinessAssembler {
	private static final Logger log = LoggerFactory.getLogger(BusinessAssembler.class);

	private UnitService unitService;
	private FlowTemplateService templateService;
	
	private FlowInitiator initiator;
	private FlowType type;
	private Unit srcSchool;
	private Unit destSchool;
	private Unit organizeUnit;//组织单位

	private int stepType;

	public BusinessAssembler(FlowInitiator initiator, FlowType type, UnitService unitService) {
		this.initiator = initiator;
		this.type = type;
		this.unitService = unitService;
		// init involved schools
		srcSchool = unitService.getUnit(initiator.getApplyUnitId());
		if (null == srcSchool) {
			throw new IllegalArgumentException("school no exists in business");
		}
		if (FlowType.BUSINESS_MODE_MULTI_ONE_WAY == type.getBusinessMode()) {
			if (initiator.getCooperateUnitId().isEmpty()) {
				throw new IllegalArgumentException("need a cooperate school in business");
			}
			destSchool = unitService.getUnit(initiator.getCooperateUnitId());
			if (null == destSchool) {
				throw new IllegalArgumentException("school no exists in business");
			}
		}
		
		if (StringUtils.isNotEmpty(initiator.getOrganizeUnitId())) {
			organizeUnit = unitService.getUnit(initiator.getOrganizeUnitId());
		}
	}
	
	public BusinessAssembler(FlowInitiator initiator, FlowType type,
			UnitService unitService, FlowTemplateService templateService) {
		this.initiator = initiator;
		this.type = type;
		this.templateService = templateService;
		srcSchool = unitService.getUnit(initiator.getApplyUnitId());
		if (null == srcSchool) {
			throw new IllegalArgumentException("school no exists in business");
		}
	}

	/**
	 * 根据业务相关信息,返回一个与该业务匹配的业务流程模板对象.
	 * 
	 * @return 一个与该业务匹配的业务流程模板对象.
	 */
	public Flow getSuitedTemplate() {
		Flow template = new Flow();
		template.setType(type.getValue());
		template.setSection(initiator.getSection());
		template.setSourceRegionLevel(srcSchool.getRegionlevel() - 1);
		if (null != destSchool) {
			template.setTargetRegionLevel(destSchool.getRegionlevel() - 1);
		}
		
		List<Integer> defaultTypes = new ArrayList<Integer>();
		if (organizeUnit != null) {
			int regionLevel = organizeUnit.getRegionlevel();
			if (Unit.UNIT_CLASS_SCHOOL == organizeUnit.getUnitclass()) {
				regionLevel = Unit.UNIT_REGION_SCHOOL;
				
				// 组织者为学校时，来源也定为学校
				template.setSourceRegionLevel(Unit.UNIT_REGION_SCHOOL);
			}
			defaultTypes.add(Integer.valueOf(String.valueOf(Flow.DEFAULT_TYPE)
					+ String.valueOf(regionLevel)));
		}
		
		defaultTypes.add(Integer.valueOf(Flow.DEFAULT_TYPE));
		template.setDefaultTypes(defaultTypes);
		return template;
	}

	/**
	 * 根据业务相关信息,返回一个与该业务匹配的业务流程模板对象.(不涉及跨学校的流程，学校内部审核流程)
	 * 
	 * @return 一个与该业务匹配的业务流程模板对象.
	 */
	public Flow getSuitedTemplate4Sch() {
		Flow template = templateService.getFlow(type.getValue());
		this.stepType = template.getStepType();
		return template;
	}

	/**
	 * 通过业务流程步骤的模板,组装出真实的业务步骤信息.
	 * 
	 * @param step
	 *            需要组装的真实业务步骤对象
	 * @param templateStep
	 *            给定的业务流程步骤模板对象
	 */
	public FlowAudit assembleStepInfo(FlowStep templateStep) {
		log.info("开始设置当前步骤的操作单位--");
		Unit unit = FlowStep.IOTYPE_IN == templateStep.getIoType() ? destSchool : srcSchool;

		if (unit == null) {
			unit = srcSchool != null ? srcSchool : destSchool;
		}

		if (Unit.UNIT_CLASS_EDU == templateStep.getAuditUnitType()) {
			unit = unitService.getUpperUnit(unit.getId(),
					unit.getRegionlevel() - templateStep.getRegionLevel());
		}
		if (null == unit) {
			log.info("取不到当前步骤的操作单位");
			throw new IllegalStateException("unit define in step template no found ");
		}
		log.info("取得当前步骤的操作单位，本次步骤设置结束");

		FlowAudit audit = new FlowAudit();
		audit.setApplyId(initiator.getApplyId());
		audit.setApplyUnitId(initiator.getApplyUnitId());
		audit.setAuditOrder(templateStep.getStepValue());
		audit.setAuditUnitId(unit.getId());
		audit.setAuditUnitLevel(unit.getRegionlevel());
		audit.setBusinessType(type.getBusinessType());
		audit.setStatus(FlowAudit.STATUS_PREPARING);
		return audit;
	}
	/**
	 * 通过业务流程步骤的模板,组装出真实的业务步骤信息.
	 * 
	 * @param step
	 *            需要组装的真实业务步骤对象
	 * @param templateStep
	 *            给定的业务流程步骤模板对象
	 */
	public FlowAudit assembleStepInfo(FlowStep templateStep,String arrangeId,String roleId) {
		log.info("开始设置当前步骤的操作单位--");
		if (null == srcSchool) {
			log.info("取不到当前步骤的操作单位");
			throw new IllegalStateException(
					"unit define in step template no found ");
		}
		log.info("取得当前步骤的操作单位，本次步骤设置结束");

		FlowAudit audit = new FlowAudit();
		audit.setArrangeId(arrangeId);
		audit.setRoleId(roleId);
		audit.setApplyId(initiator.getApplyId());
		audit.setApplyUnitId(initiator.getApplyUnitId());
		audit.setAuditOrder(templateStep.getStepValue());
		audit.setAuditUnitId(srcSchool.getId());
		audit.setBusinessType(type.getBusinessType());
		audit.setStatus(FlowAudit.STATUS_PREPARING);
		return audit;
	}
	
	public static FlowAudit assembleStepInfo(FlowAudit templateStep,int stepValue) {
		FlowAudit audit = templateStep;
		audit.setAuditOrder(stepValue);
		audit.setStatus(FlowAudit.STATUS_CHECKING);
		audit.setAuditDate(null);
		audit.setAuditUnitName(null);
		audit.setAuditUserId(null);
		audit.setOpinion(null);
		return audit;
	}

	public int getStepType() {
		return this.stepType;
	}
}
