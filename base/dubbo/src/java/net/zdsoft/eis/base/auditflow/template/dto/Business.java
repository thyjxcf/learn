package net.zdsoft.eis.base.auditflow.template.dto;

import java.util.Comparator;
import java.util.Date;

import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.auditflow.template.entity.AbstractBusinessStep;
import net.zdsoft.eis.base.auditflow.template.entity.BusinessFlowTemplate;
import net.zdsoft.eis.base.auditflow.template.entity.FlowStep;
import net.zdsoft.eis.base.auditflow.template.entity.FlowType;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.UnitService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 该类表示一个业务.<br>
 * 包含了业务类型(<code>BusinessType</code>)以及业务信息(<code>BusinessInfo</code>).
 * 
 * @author yaox
 * @version 1.0
 */
public class Business {
	private static final Logger log = LoggerFactory.getLogger(Business.class);

	private BusinessInfo info;

	private BusinessType type;

	private UnitService unitService;

	private Unit srcSchool;

	private Unit destSchool;

	public Business(BusinessInfo info, BusinessType type,
			UnitService unitService) {
		this.info = info;
		this.type = type;
		this.unitService = unitService;
		// init involved schools
		srcSchool = unitService.getUnit(info.getApplyUnitId());
		if (null == srcSchool) {
			throw new IllegalArgumentException("school no exists in business");
		}
		if (FlowType.BUSINESS_MODE_MULTI_ONE_WAY == type
				.getBusinessMode()) {
			if (info.getCooperateUnitIds().isEmpty()) {
				throw new IllegalArgumentException(
						"need a cooperate school in business");
			}
			destSchool = unitService.getUnit(info.getCooperateUnitIds().get(
					0));
			if (null == destSchool) {
				throw new IllegalArgumentException(
						"school no exists in business");
			}
		}
	}

	/**
	 * 根据业务相关信息,返回一个与该业务匹配的业务流程模板对象.
	 * 
	 * @return 一个与该业务匹配的业务流程模板对象.
	 */
	public BusinessFlowTemplate getSuitedTemplate() {
		BusinessFlowTemplate template = new BusinessFlowTemplate();
		template.setBusinessType(type.value());
		template.setSection(info.getSection());
		template.setSourceRegionLevel(srcSchool.getRegionlevel() - 1);
		if (null != destSchool) {
			template.setTargetRegionLevel(destSchool.getRegionlevel() - 1);
		}
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
	public void assembleStepInfo(AbstractBusinessStep step,
			FlowStep templateStep) {
		log.info("开始设置当前步骤的操作单位--");
		Unit unit;
		unit = FlowStep.IOTYPE_IN == templateStep.getIoType() ? 
				destSchool : srcSchool;
		
		if (unit == null){
			unit = srcSchool != null ? srcSchool : destSchool;
		}
		
		if (Unit.UNIT_CLASS_EDU == templateStep.getAuditUnitType()) {
			unit = unitService.getUpperUnit(unit.getId(), unit.getRegionlevel()
					- templateStep.getRegionLevel());
		}
		if (null == unit) {
			log.info("取不到当前步骤的操作单位");
			throw new IllegalStateException(
					"unit define in step template no found ");
		}
		log.info("取得当前步骤的操作单位，本次步骤设置结束");
		step.setOperateUnit(unit.getId());
		step.setApplyId(info.getApplyId());
		step.setApplyUnitId(info.getApplyUnitId());
		step.setBusinessType(type.getBusinessType());
		step.setAuditOrder(templateStep.getStepValue());
		step.setAuditUnitLevel(templateStep.getRegionLevel());
		step.setState(FlowAudit.STATUS_PREPARING);
		step.setAuditDate(new Date());
	}
	
	/**
	 * 业务步骤默认排序比较器<br>
	 * 按照 0,1,2...,-1 的顺序排列
	 */
	public static final Comparator<AbstractBusinessStep> BUSINESS_STEP_ORDER = new Comparator<AbstractBusinessStep>() {
		// 对步骤排序,按照 0,1,2...,-1 的顺序排列
		public int compare(AbstractBusinessStep step1,
				AbstractBusinessStep step2) {
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
	public static final Comparator<FlowStep> BUSINESS_TEMPLATE_STEP_ORDER = new Comparator<FlowStep>() {
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
	
}
