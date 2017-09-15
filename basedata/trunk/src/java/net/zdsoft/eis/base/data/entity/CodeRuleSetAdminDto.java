package net.zdsoft.eis.base.data.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.CodeRule;
import net.zdsoft.eis.base.common.entity.CodeRuleDetail;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: CodeRuleSetAdminDto.java,v 1.2 2007/01/09 10:03:34 jiangl Exp $
 */
public class CodeRuleSetAdminDto implements Serializable {
	private static final long serialVersionUID = 3850272946898497080L;

	// 学号、学籍号相关属性
	private String stucoderuleid; // 学号规则id
	private String schid; // 学校ID
	private String automatism; // 是否自动生成

	private String rulelistid; // 学号明细规则ID
	private String datatype; // 数据类型
	private int ruleposition; // 号码位置
	private int rulenumber; // 取值位
	private String constant; // 固定值
	private String remark; // 备注

	private String ruleid; // 学籍号ID
	private String section; // 学段
	private boolean inSerialNumber;// 是否计入流水号
	private int type;

	// 页面中其它显示属性
	private String automatismHtml; // 是否自动生成的select html
	private List<CodeRuleDetail> stuCodeRuleList; // 页面显示的学号明细规则列表
	private List<CodeRuleDetail> unitiveCodeRuleList; // 页面显示的学籍号规则列表
	private String existSchool; // 学校信息是否添加（true：添加，false：未添加）
	private String singleDeploy; // 是否单独布暑（true：单独，false：多级）
	private String datatypeHtml; // 数据类型的select html
	private Map<String, String> datatypeLengthMap; // 数据类型的长度Map

	// add by jiangf 2008-6-3
	private String showMeetExamSystem;// 会考证号规则是否显示 如果启动会考系统，允许， 反之不允许

	public String getShowMeetExamSystem() {
		return showMeetExamSystem;
	}

	public boolean isInSerialNumber() {
		return inSerialNumber;
	}

	public void setInSerialNumber(boolean inSerialNumber) {
		this.inSerialNumber = inSerialNumber;
	}

	public void setShowMeetExamSystem(String showMeetExamSystem) {
		this.showMeetExamSystem = showMeetExamSystem;
	}

	public CodeRuleSetAdminDto() {
	}

	public String getAutomatism() {
		return automatism;
	}

	public void setAutomatism(String automatism) {
		this.automatism = automatism;
	}

	public String getConstant() {
		return constant;
	}

	public void setConstant(String constant) {
		this.constant = constant;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRuleid() {
		return ruleid;
	}

	public void setRuleid(String ruleid) {
		this.ruleid = ruleid;
	}

	public String getRulelistid() {
		return rulelistid;
	}

	public void setRulelistid(String rulelistid) {
		this.rulelistid = rulelistid;
	}

	public int getRulenumber() {
		return rulenumber;
	}

	public void setRulenumber(int rulenumber) {
		this.rulenumber = rulenumber;
	}

	public int getRuleposition() {
		return ruleposition;
	}

	public void setRuleposition(int ruleposition) {
		this.ruleposition = ruleposition;
	}

	public String getSchid() {
		return schid;
	}

	public void setSchid(String schid) {
		this.schid = schid;
	}

	public String getStucoderuleid() {
		return stucoderuleid;
	}

	public void setStucoderuleid(String stucoderuleid) {
		this.stucoderuleid = stucoderuleid;
	}

	public String getAutomatismHtml() {
		return automatismHtml;
	}

	public void setAutomatismHtml(String automatismHtml) {
		this.automatismHtml = automatismHtml;
	}

	public List<CodeRuleDetail> getStuCodeRuleList() {
		return stuCodeRuleList;
	}

	public void setStuCodeRuleList(List<CodeRuleDetail> stuCodeRuleList) {
		this.stuCodeRuleList = stuCodeRuleList;
	}

	public String getExistSchool() {
		return existSchool;
	}

	public void setExistSchool(String existSchool) {
		this.existSchool = existSchool;
	}

	public String getSingleDeploy() {
		return singleDeploy;
	}

	public void setSingleDeploy(String singleDeploy) {
		this.singleDeploy = singleDeploy;
	}

	public String getDatatypeHtml() {
		return datatypeHtml;
	}

	public void setDatatypeHtml(String datatypeHtml) {
		this.datatypeHtml = datatypeHtml;
	}

	public Map<String, String> getDatatypeLengthMap() {
		return datatypeLengthMap;
	}

	public void setDatatypeLengthMap(Map<String, String> datatypeLengthMap) {
		this.datatypeLengthMap = datatypeLengthMap;
	}

	public List<CodeRuleDetail> getUnitiveCodeRuleList() {
		return unitiveCodeRuleList;
	}

	public void setUnitiveCodeRuleList(List<CodeRuleDetail> unitiveCodeRuleList) {
		this.unitiveCodeRuleList = unitiveCodeRuleList;
	}

	public static void dtoToDto(CodeRuleSetAdminDto srcDto,
			CodeRuleSetAdminDto destDto) {
		if (srcDto == null)
			return;

		if (destDto == null)
			destDto = new CodeRuleSetAdminDto();

		destDto.setStucoderuleid(srcDto.getStucoderuleid());
		destDto.setSchid(srcDto.getSchid());
		destDto.setAutomatism(srcDto.getAutomatism());
		destDto.setRulelistid(srcDto.getRulelistid());
		destDto.setDatatype(srcDto.getDatatype());
		destDto.setRuleposition(srcDto.getRuleposition());
		destDto.setRulenumber(srcDto.getRulenumber());
		destDto.setConstant(srcDto.getConstant());
		destDto.setRemark(srcDto.getRemark());
		destDto.setRuleid(srcDto.getRuleid());
		destDto.setAutomatismHtml(srcDto.getAutomatismHtml());
		destDto.setStuCodeRuleList(srcDto.getStuCodeRuleList());
		destDto.setUnitiveCodeRuleList(srcDto.getUnitiveCodeRuleList());
		destDto.setExistSchool(srcDto.getExistSchool());
		destDto.setSingleDeploy(srcDto.getSingleDeploy());
		destDto.setDatatypeHtml(srcDto.getDatatypeHtml());
		destDto.setDatatypeLengthMap(srcDto.getDatatypeLengthMap());
		destDto.setSection(srcDto.getSection());
		destDto.setType(srcDto.getType());
		destDto.setInSerialNumber(srcDto.isInSerialNumber());
	}

	public static void dtoToEntity(CodeRuleSetAdminDto srcDto,
			CodeRuleDetail entity) {
		if (srcDto == null)
			return;

		if (entity == null)
			entity = new CodeRuleDetail();

		entity.setId(srcDto.getRulelistid());
		entity.setDataType(srcDto.getDatatype());
		entity.setRulePosition(srcDto.getRuleposition());
		entity.setRuleNumber(srcDto.getRulenumber());
		entity.setConstant(srcDto.getConstant());
		entity.setRemark(srcDto.getRemark());
		entity.setRuleId(srcDto.getRuleid());
		entity.setInSerialNumber(srcDto.isInSerialNumber());
	}

	public static void detailEntityToEntity(CodeRuleDetail entity1,
			CodeRuleDetail entity2) {
		if (entity1 == null)
			return;

		if (entity2 == null)
			entity2 = new CodeRuleDetail();

		entity2.setId(entity1.getId());
		entity2.setDataType(entity1.getDataType());
		entity2.setRulePosition(entity1.getRulePosition());
		entity2.setRuleNumber(entity1.getRuleNumber());
		entity2.setConstant(entity1.getConstant());
		entity2.setRemark(entity1.getRemark());
		entity2.setRuleId(entity1.getRuleId());
		entity2.setInSerialNumber(entity1.isInSerialNumber());
	}

	public static void ruleEntityToEntity(CodeRule entity1, CodeRule entity2) {
		if (entity1 == null)
			return;

		if (entity2 == null)
			entity2 = new CodeRule();
		entity2.setUnitId(entity1.getUnitId());
		entity2.setAutomatism(entity1.getAutomatism());
		entity2.setCodeType(entity1.getCodeType());
		entity2.setSection(entity1.getSection());
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
