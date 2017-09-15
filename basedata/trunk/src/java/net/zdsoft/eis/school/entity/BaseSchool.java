package net.zdsoft.eis.school.entity;

import java.util.Date;

import net.zdsoft.eis.base.common.entity.School;

/**
 * A class that represents a row in the 'base_school' table. This class may be
 * customized as it is never re-generated after being created.
 */
public class BaseSchool extends School {
	private static final long serialVersionUID = -6397155841727944668L;

	// ===================================dto====================
	private String synchroSchDistrict; // 是否同步学生的应服务学区标记，如�果
	// 值为"synchroSchDistrict"则同�步,否则不同�步
	private String educode;// 主管部门代码
	
	//add 2012-9-12
	private String builtupArea; //建筑面积
	private String greenArea;  //绿化面积
	private String sportsAreal; //运动场面积
	private String industry;  //行业
	private String legalPerson;// 法人
	private String remark;  //备注
	
	
	public String getBuiltupArea() {
		return builtupArea;
	}

	public void setBuiltupArea(String builtupArea) {
		this.builtupArea = builtupArea;
	}

	public String getGreenArea() {
		return greenArea;
	}

	public void setGreenArea(String greenArea) {
		this.greenArea = greenArea;
	}

	public String getSportsAreal() {
		return sportsAreal;
	}

	public void setSportsAreal(String sportsAreal) {
		this.sportsAreal = sportsAreal;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * Simple constructor of AbstractBasicSchoolinfo instances.
	 */
	public BaseSchool() {
	}


	public String getSynchroSchDistrict() {
		return synchroSchDistrict;
	}

	public void setSynchroSchDistrict(String synchroSchDistrict) {
		this.synchroSchDistrict = synchroSchDistrict;
	}

	public String getEducode() {
		return educode;
	}

	public void setEducode(String educode) {
		this.educode = educode;
	}

	public static void main(String[] args) {
		temptools.BaseCodeBuilder builder = new temptools.BaseCodeBuilder(
				"classpath:conf/spring/applicationContext.xml", "base_school",
				"BaseSchool");
		builder.printCode();
	}
}
