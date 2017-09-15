package net.zdsoft.eis.base.data.dto;

import java.util.Date;

public class UserTempNameDto {
	
	private String studentId;
	private String stuCode;
	private Integer type;//1--学生 3--家长
	private String identitycard;
	private String familyType;//微代码DM-CGX
	private String mobile;//手机号
	private String realName;
	/**
	 * 学校编号
	 */
	private String etohSchoolId;
	/**
	 * 用户名前缀
	 */
	private String userNamePrefix;
	/**
	 * 学籍号
	 */
	private String unitiveCode;
	/**
	 * 班级编号
	 */
	private String clsCode;
	/**
	 * 两位流水号
	 */
	private String maxcode;//用户名流水号最大值
	/**
	 * 父亲手机号
	 */
	private String fMobile;
	/**
	 * 姓名拼音
	 */
	private String spellName;
	/**
	 * 临时用户名
	 */
	private String tempUserName;
	/**
	 * 姓名全拼，名字取首字母
	 */
	private String firstSpell;
	/**
	 * 临时身份证号
	 */
	private String tempIdCard;
	//规则6 regionCode, birth, sex 生成临时身份证号 
	private String regionCode;
	private Date birth;//生日
	private Integer sex;//性别

	public String getTempIdCard() {
		return tempIdCard;
	}
	public void setTempIdCard(String tempIdCard) {
		this.tempIdCard = tempIdCard;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFamilyType() {
		return familyType;
	}
	public void setFamilyType(String familyType) {
		this.familyType = familyType;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStuCode() {
		return stuCode;
	}
	public void setStuCode(String stuCode) {
		this.stuCode = stuCode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getIdentitycard() {
		return identitycard;
	}
	public void setIdentitycard(String identitycard) {
		this.identitycard = identitycard;
	}
	public String getEtohSchoolId() {
		return etohSchoolId;
	}
	public void setEtohSchoolId(String etohSchoolId) {
		this.etohSchoolId = etohSchoolId;
	}
	public String getUserNamePrefix() {
		return userNamePrefix;
	}
	public void setUserNamePrefix(String userNamePrefix) {
		this.userNamePrefix = userNamePrefix;
	}
	public String getUnitiveCode() {
		return unitiveCode;
	}
	public void setUnitiveCode(String unitiveCode) {
		this.unitiveCode = unitiveCode;
	}
	public String getClsCode() {
		return clsCode;
	}
	public void setClsCode(String clsCode) {
		this.clsCode = clsCode;
	}

	
	public String getfMobile() {
		return fMobile;
	}
	public void setfMobile(String fMobile) {
		this.fMobile = fMobile;
	}
	public String getSpellName() {
		return spellName;
	}
	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}
	public String getTempUserName() {
		return tempUserName;
	}
	public void setTempUserName(String tempUserName) {
		this.tempUserName = tempUserName;
	}
	public String getMaxcode() {
		return maxcode;
	}
	public void setMaxcode(String maxcode) {
		this.maxcode = maxcode;
	}
	public String getFirstSpell() {
		return firstSpell;
	}
	public void setFirstSpell(String firstSpell) {
		this.firstSpell = firstSpell;
	}

	
	
}
