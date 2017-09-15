package net.zdsoft.eis.base.common.entity;

import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;

public class Family extends BaseEntity {

    private static final long serialVersionUID = -8397337031361494516L;
    private String relation; // 关系码(Mcode)
    private String name; // 姓名
    private String company; // 单位名称
    private String duty; // 职务
    private String linkPhone; // 联系电话
    private String workCode; // 职业码(Mcode)
    private String professionCode; // 专业技术职务码(Mcode)
    private String dutyLevel; // 职务级别码(Mcode)
    private String politicalStatus; // 政治面貌码(Mcode)
    private String maritalStatus; // 婚姻状况码(Mcode)
    private String emigrationPlace; // 侨居地码(Mcode)
    private Date birthday; // 出生日期
    private String culture; // 文化程度(Mcode)
    private String studentId; // 学生信息
    private String schoolId; // 学校Id
    private String identityCard; // 身份证号
    private String nation; // 民族
    private boolean isGuardian; // 是否是监护人
    private String sex; // 性别
    private String remark; // 备注
    private String postalcode; // 邮编
    private String linkAddress; // 联系地址
    private String email; // 电子邮箱
    private String mobilePhone; // 手机号码
    private String officeTel; // 办公电话
    private String homepage; // 个人主页
    private String chargeNumber; // 扣费号码
    private int chargeNumberType;
    private String regionCode;
    private int leaveSchool;// 在校离校标志
    private String relationRemark;
    private String registerPlace;
    private String registerPlaceName;
    
    //add by zhaojt 2014-10-20
    private String receiveInfomation; //是否接收短信
    private String smartMobilePhone; //是否智能机
    
    //中策全国模板字段维护 add by like 2014-12-3
    private String identitycardType;//成员身份证件类型
	private String health;//成员健康状况
	
	//东莞
	private String isResidence;//是否办理过居住证
	private String isbuySocsec;//是否购买社保

    // ==================================dto==========================
    private String[] checkid; // 选择框名

    public String getRelation() {
        return relation;
    }

    public String getRelationRemark() {
		return relationRemark;
	}

	public void setRelationRemark(String relationRemark) {
		this.relationRemark = relationRemark;
	}

	public String getRegisterPlace() {
		return registerPlace;
	}

	public void setRegisterPlace(String registerPlace) {
		this.registerPlace = registerPlace;
	}

	public String getRegisterPlaceName() {
		return registerPlaceName;
	}

	public void setRegisterPlaceName(String registerPlaceName) {
		this.registerPlaceName = registerPlaceName;
	}

	public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    public String getProfessionCode() {
        return professionCode;
    }

    public void setProfessionCode(String professionCode) {
        this.professionCode = professionCode;
    }

    public String getDutyLevel() {
        return dutyLevel;
    }

    public void setDutyLevel(String dutyLevel) {
        this.dutyLevel = dutyLevel;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEmigrationPlace() {
        return emigrationPlace;
    }

    public void setEmigrationPlace(String emigrationPlace) {
        this.emigrationPlace = emigrationPlace;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public boolean isGuardian() {
        return isGuardian;
    }

    public void setGuardian(boolean isGuardian) {
        this.isGuardian = isGuardian;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getChargeNumber() {
        return chargeNumber;
    }

    public void setChargeNumber(String chargeNumber) {
        this.chargeNumber = chargeNumber;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public int getChargeNumberType() {
        return chargeNumberType;
    }

    public void setChargeNumberType(int chargeNumberType) {
        this.chargeNumberType = chargeNumberType;
    }

    public int getLeaveSchool() {
        return leaveSchool;
    }

    public void setLeaveSchool(int leaveSchool) {
        this.leaveSchool = leaveSchool;
    }

    public String[] getCheckid() {
        return checkid;
    }

    public void setCheckid(String[] checkid) {
        this.checkid = checkid;
    }

	public String getReceiveInfomation() {
		return receiveInfomation;
	}

	public void setReceiveInfomation(String receiveInfomation) {
		this.receiveInfomation = receiveInfomation;
	}

	public String getSmartMobilePhone() {
		return smartMobilePhone;
	}

	public void setSmartMobilePhone(String smartMobilePhone) {
		this.smartMobilePhone = smartMobilePhone;
	}

	public String getIdentitycardType() {
		return identitycardType;
	}

	public void setIdentitycardType(String identitycardType) {
		this.identitycardType = identitycardType;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getIsResidence() {
		return isResidence;
	}

	public void setIsResidence(String isResidence) {
		this.isResidence = isResidence;
	}

	public String getIsbuySocsec() {
		return isbuySocsec;
	}

	public void setIsbuySocsec(String isbuySocsec) {
		this.isbuySocsec = isbuySocsec;
	}

}
