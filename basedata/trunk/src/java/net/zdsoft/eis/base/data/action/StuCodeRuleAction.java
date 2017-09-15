package net.zdsoft.eis.base.data.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.CodeRule;
import net.zdsoft.eis.base.common.entity.CodeRuleDetail;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.CodeRuleBuildService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.entity.CodeRuleSetAdminDto;
import net.zdsoft.eis.base.data.service.BaseCodeRuleService;
import net.zdsoft.eis.base.data.util.CodeRuleUtils;
import net.zdsoft.eis.frame.action.ModelBaseAction;
import net.zdsoft.keel.util.StringUtils;

/**
 * <p>
 * ZDSoft学籍系统(stusys)V3.5
 * </p>
 * <p>
 * 学号规则设置的action,包括列表,新增,修改,保存和删除
 * </p>
 * 
 * @author zhongh
 * @since 1.0
 * @version $Id: StuCodeRuleAction.java,v 1.7 2007/01/31 10:54:28 linqz Exp $
 */
public class StuCodeRuleAction extends ModelBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1574911288558978134L;

	private static String STU_CODE_RULE_ACTION_URL = "codeRuleSetAdmin-stuCodeRule.action";

	// 页面变量封装对象
	private CodeRuleSetAdminDto codeRuleDto = new CodeRuleSetAdminDto();

	private String codeType; // 供查询用

	private String sections;

	private boolean createCoderule = false;// 是否初始化毕业证号规则

	private boolean initCodeRule = false;

	public String getSections() {
		return sections;
	}

	public void setSections(String sections) {
		this.sections = sections;
	}

	public Object getModel() {
		return codeRuleDto;
	}

	// service
	private BaseCodeRuleService baseCodeRuleService;
	private CodeRuleBuildService codeRuleBuildService;
	private UnitService unitService;

	public boolean isInitCodeRule() {
		return initCodeRule;
	}

	public void setInitCodeRule(boolean initCodeRule) {
		this.initCodeRule = initCodeRule;
	}

	public void setBaseCodeRuleService(BaseCodeRuleService baseCodeRuleService) {
		this.baseCodeRuleService = baseCodeRuleService;
	}

	public void setCodeRuleBuildService(
			CodeRuleBuildService codeRuleBuildService) {
		this.codeRuleBuildService = codeRuleBuildService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public boolean isTopEdu() {
		Unit unit = unitService.getTopEdu();
		if (unit.getId().equals(this.getLoginInfo().getUnitID()))
			return true;
		return false;
	}

	// 学校主键GUID，直接从logInfo中取得
	protected String schGUID;

	// 学号规则列表
	public String execute() throws Exception {
		log.info("stuCodeRule action: execute()......");

		if (!initCodeRule) {
			schGUID = this.getLoginInfo().getUnitID();
			if (schGUID == null || "".equals(schGUID)) {
				this.addActionError("没有取到单位（学校）GUID编号！");
				return ERROR;
			}
		}

		// 检查是否存在一个学号规则
		if (codeType == null || codeType == "") {
			codeType = String.valueOf(CodeRuleUtils.CODETYPE_STUDENT_CODE);

		}
		CodeRule rule = null;
		if (sections == null)
			sections = "1";
		boolean topEdu = false;
		boolean isGlobal = false;
		if ("global".equals(CodeRuleUtils.codeTypeRangeMap.get(codeType))) {
			isGlobal = true;
			schGUID = unitService.getTopEdu().getId();
			if (initCodeRule) {
				topEdu = true;
			} else {
				if (schGUID.equals(getLoginInfo().getUnitID()))
					topEdu = true;
			}
		}
		String ruleid=null;
		if (initCodeRule) {
			schGUID = BaseConstant.ZERO_GUID;
			rule = baseCodeRuleService.getInitCodeRule(codeType);
			ruleid = rule.getId();
		} else {
			rule = codeRuleBuildService.getUnitCodeRule(schGUID, Integer
					.parseInt(codeType));
		}
		if (rule == null) {
			if ((isGlobal && topEdu) || !isGlobal) {
				this.promptMessageDto = baseCodeRuleService
						.createCodeRuleByType(schGUID, codeType);
				addActionMessage("已根据默认规则进行初始化！");
			}
			if (!topEdu && isGlobal) {
				addActionError("规则未添加，请联系顶级教育局！");
			} else if (!promptMessageDto.getOperateSuccess()) {
				promptMessageDto.addOperation(new String[] { "确定",
						STU_CODE_RULE_ACTION_URL + "?codeType=" + codeType });
				promptMessageDto.addHiddenText(new String[] { "initCodeRule",
						String.valueOf(initCodeRule) });
				return PROMPTMSG;
			}
		}
		
		CodeRuleSetAdminDto dto = baseCodeRuleService.getUnitCodeRuleList(schGUID, codeType,
				initCodeRule, ruleid);
		// DtoFactory.copyToDTOFromDTO(dto, codeRuleDto);
		CodeRuleSetAdminDto.dtoToDto(dto, codeRuleDto);

		return SUCCESS;
	}

	// 更改是否自动生成学号开关
	public String doUpdateAutomatism() {
		log.info("stuCodeRuleDoUpdateAutomatism action: doUpdateAutomatism()......");
		log.debug("stucoderuleid == " + codeRuleDto.getStucoderuleid());
		log.debug("automatism == " + codeRuleDto.getAutomatism());

		promptMessageDto = baseCodeRuleService.updateAutomatism(codeRuleDto
				.getStucoderuleid(), codeRuleDto.getAutomatism());

		return SUCCESS;
	}

	// 添加明细规则
	public String doAdd() {
		log.info("stuCodeRuleDoAdd action: doAdd()......");

		CodeRuleSetAdminDto dto = baseCodeRuleService.insertUnitCodeRuleList(
				codeRuleDto.getSchid(), codeRuleDto.getStucoderuleid());
		// DtoFactory.copyToDTOFromDTO(dto, codeRuleDto);
		CodeRuleSetAdminDto.dtoToDto(dto, codeRuleDto);
		return SUCCESS;
	}
	
	private String schId;
	private String stuCodeRuleId;
	
	public void setSchId(String schId) {
		this.schId = schId;
	}

	public void setStuCodeRuleId(String stuCodeRuleId) {
		this.stuCodeRuleId = stuCodeRuleId;
	}

	/**
	 * 判断是否已经达到最大位数
	 * 
	 * @return
	 */
	public String isOutOfBound() {
		//String schId1 = schId;
		List<CodeRuleDetail> list = codeRuleBuildService
				.getCodeRuleDetails(stuCodeRuleId);
		int MAX = 30;
		int length = 0;
		for (CodeRuleDetail rule : list) {
			if (rule.getDataType().equals("serialno")) {
				length += rule.getRuleNumber();
			} else if (rule.getDataType().equals("fixedvalue")) {
				length += StringUtils.getRealLength(rule.getConstant());
			} else {
				length++;
			}
		}
		if (MAX < length) {
			jsonError = "规则的最大长度为" + MAX + "位，不能再增加！";
		} 
		return SUCCESS;
	}

	// 编辑明细规则
	public String doEdit() {
		log.info("stuCodeRuleDoEdit action: doEdit()......");
		log.debug("rulelistid == " + codeRuleDto.getRulelistid());

		CodeRuleSetAdminDto dto = baseCodeRuleService
				.getUnitCodeRuleList(codeRuleDto.getRulelistid());
		// DtoFactory.copyToDTOFromDTO(dto, codeRuleDto);
		dto.setSchid(codeRuleDto.getSchid());
		dto.setRulelistid(codeRuleDto.getRulelistid());
		CodeRuleSetAdminDto.dtoToDto(dto, codeRuleDto);

		return SUCCESS;
	}

	// 保存规则明细
	public String doSave() {
		log.info("stuCodeRuleDoSave action: doSave()......");
		List<CodeRuleDetail> list = codeRuleBuildService
				.getCodeRuleDetails(codeRuleDto.getRuleid());
		if (codeRuleDto.getRulelistid() == null
				|| codeRuleDto.getRulelistid().equals("")) {
			// 新增
			CodeRuleDetail addRule = new CodeRuleDetail();
			addRule.setDataType(codeRuleDto.getDatatype());
			addRule.setRuleNumber(codeRuleDto.getRulenumber());
			addRule.setConstant(codeRuleDto.getConstant());
			list.add(addRule);
		}
		int MAX = 20;
		int length = 0;
		for (CodeRuleDetail rule : list) {
			if (rule.getId() != null
					&& rule.getId().equals(codeRuleDto.getRulelistid())) {
				rule.setDataType(codeRuleDto.getDatatype());
				rule.setRuleNumber(codeRuleDto.getRulenumber());
				rule.setConstant(codeRuleDto.getConstant());
			}
			if (rule.getDataType().equals("serialno")) {
				length += rule.getRuleNumber();
			} else if (rule.getDataType().equals("fixedvalue")) {
				length += rule.getConstant().length();
			} else {
				length++;
			}
		}

		if (MAX < length) {
//			promptMessageDto.setErrorMessage("规则的最大长度为" + MAX + "位，不能再增加！");
//			promptMessageDto.addOperation(new String[] { "返回",
//					STU_CODE_RULE_ACTION_URL + "?codeType=" + codeType });
//			promptMessageDto.addHiddenText(new String[] { "schid",
//					codeRuleDto.getSchid() });
//			promptMessageDto.addHiddenText(new String[] { "stucoderuleid",
//					codeRuleDto.getRuleid() });
//			promptMessageDto.addHiddenText(new String[] { "initCodeRule",
//					String.valueOf(initCodeRule) });
//			return PROMPTMSG;
			jsonError = "规则的最大长度为" + MAX + "位，不能再增加！";
		}
		CodeRuleDetail cdDetail = new CodeRuleDetail();
		CodeRuleSetAdminDto.dtoToEntity(codeRuleDto, cdDetail);

		String unitId = null;
		if (!initCodeRule) {
			unitId = this.getLoginInfo().getUnitID();
		}
		this.promptMessageDto = baseCodeRuleService.saveUnitCodeRuleList(
				cdDetail, codeType, unitId);

		if (promptMessageDto.getOperateSuccess()) {
			// 打开一个新的窗口时
			// promptMessageDto.addOperation(new String[]{"确定","javascript:
			// window.close();"});

			// 在原窗口中打开时
//			promptMessageDto.addOperation(new String[] { "返回",
//					STU_CODE_RULE_ACTION_URL });
//			if (codeRuleDto.getRulelistid() == null
//					|| codeRuleDto.getRulelistid().equals("")) {
//				// 新增
//				promptMessageDto.addOperation(new String[] { "增加下一个",
//						"codeRuleSetAdmin-stuCodeRuleDoAdd.action" });
//			}
//			promptMessageDto.addHiddenText(new String[] { "schid",
//					codeRuleDto.getSchid() });
//			promptMessageDto.addHiddenText(new String[] { "stucoderuleid",
//					codeRuleDto.getRuleid() });
//			promptMessageDto
//					.addHiddenText(new String[] { "codeType", codeType });
//			promptMessageDto.addHiddenText(new String[] { "initCodeRule",
//					String.valueOf(initCodeRule) });
//			return PROMPTMSG;
		} else {
//			return INPUT;
			jsonError = "出现问题保存失败";
		}
		return SUCCESS;
	}
	
	private String ruleListId;
	
	public void setRuleListId(String ruleListId) {
		this.ruleListId = ruleListId;
	}
	
	// 删除明细规则
	public String doDelete() {
		log.info("stuCodeRuleDoDelete action: doDelete()......");
		log.debug("rulelistid == " + ruleListId);
		try {
			promptMessageDto = baseCodeRuleService.deleteUnitCodeRuleList(
					ruleListId);
		} catch (Exception e) {
			jsonError = "删除失败,失败原因:"+e.getMessage();
		}

//		promptMessageDto.addOperation(new String[] { "确定",
//				STU_CODE_RULE_ACTION_URL + "?codeType=" + codeType });
//		promptMessageDto.addHiddenText(new String[] { "initCodeRule",
//				String.valueOf(initCodeRule) });
		return SUCCESS;
	}

	// 打开排序页面
	public String doSort() throws Exception {
		log.info("stuCodeRuleDoSort action: doSort()......");

		if (!initCodeRule) {
			schGUID = this.getLoginInfo().getUnitID();
			if (schGUID == null || "".equals(schGUID)) {
				this.addActionError("没有取到单位（学校）GUID编号！");
				return ERROR;
			}
		}

		if (codeType == null || codeType == "") {
			codeType = String.valueOf(CodeRuleUtils.CODETYPE_STUDENT_CODE);
		}
		String ruleid = null;
		if (initCodeRule) {
			CodeRule rule = baseCodeRuleService.getInitCodeRule(codeType);
			ruleid = rule.getId();
		}
		CodeRuleSetAdminDto dto = baseCodeRuleService.getUnitCodeRuleList(
				schGUID, codeType, initCodeRule, ruleid);
		List<CodeRuleDetail> tempRuleList = dto.getStuCodeRuleList();
		if (tempRuleList == null || tempRuleList.size() < 1) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("该规则还没有维护，不能使用调整位置功能，请先维护号码规则！");
			promptMessageDto
					.addOperation(new String[] {
							"确定",
							"codeRuleSetAdmin-stuCodeRule.action?codeType="
									+ codeType });
			promptMessageDto.addHiddenText(new String[] { "initCodeRule",
					String.valueOf(initCodeRule) });

			return PROMPTMSG;
		}

		// 若最后一行是流水号，则不参与位置调整
		CodeRuleDetail ruleListdto = (CodeRuleDetail) tempRuleList
				.get(tempRuleList.size() - 1);
		if (ruleListdto.getRulePosition() == 99) {
			tempRuleList.remove(tempRuleList.size() - 1);
		}

		if (tempRuleList.size() > 1) {
			dto.setStuCodeRuleList(tempRuleList);
			// DtoFactory.copyToDTOFromDTO(dto, codeRuleDto);
			CodeRuleSetAdminDto.dtoToDto(dto, codeRuleDto);
			return SUCCESS;
		} else {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("规则中可调序的行少于2行，不能使用调整位置功能！");
			promptMessageDto.addOperation(new String[] { "确定",
					STU_CODE_RULE_ACTION_URL + "?codeType=" + codeType });
			promptMessageDto.addHiddenText(new String[] { "initCodeRule",
					String.valueOf(initCodeRule) });

			return PROMPTMSG;
		}
	}
	
	private String ruleId;
	private String[] ruleDetailId;
	 
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public void setRuleDetailId(String[] ruleDetailId) {
		this.ruleDetailId = ruleDetailId;
	}

	// 保存新的号码位置
	public String saveSort() {
		try {
			baseCodeRuleService.updateRulePositionall(ruleId,ruleDetailId);
		} catch (Exception e) {
			jsonError = e.getMessage();
		}
		return SUCCESS;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public boolean isCreateCoderule() {
		return createCoderule;
	}

	public void setCreateCoderule(boolean createCoderule) {
		this.createCoderule = createCoderule;
	}

}
