package net.zdsoft.eis.base.common.entity;

// default package

import java.util.Date;

import net.zdsoft.eis.base.photo.PhotoEntity;
import net.zdsoft.eis.frame.entity.JsonDateSerialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Teacher extends PhotoEntity {
    private static final long serialVersionUID = -800857156179534639L;

    /**
     *  教职工代码
     */
    @JsonProperty(value="teacherCode")
    private String tchId;
    /**
     *  姓名
     */
    @JsonProperty(value="teacherName")
    private String name;
    /**
     *  性别
     */
    @JsonProperty(value="teacherGender")
    private String sex;
    /**
     *  在职标志
     */
    @JsonProperty(value="incumbency")
    private String eusing;
    /**
     *  身份证
     */
    @JsonProperty(value="identityCardNo")
    private String idcard;
    /**
     *  个人电话
     */
    @JsonProperty(value="mobilePhone")
    private String personTel;
    /**
     *  所属部门
     */
    @JsonProperty(value="deptId")
    private String deptid;
    /**
     *  所在单位（若是班主任则一定是某学校的GUID）
     */
    @JsonProperty(value="unitId")
    private String unitid;
    /**
     * 排序号
     */
    @JsonIgnore
    private Integer displayOrder;
    /**
     *  办公电话
     */
    @JsonProperty(value="officePhone")
    private String officeTel;
    /**
     *  Email
     */
    private String email;
    /**
     *  民族
     */
    private String nation;
    /**
     * 籍贯
     */
    @JsonProperty(value="nativePlace")
    private String perNative;
    /**
     * 生日
     */
    @JsonSerialize(using = JsonDateSerialize.class)  
    private Date birthday;
    /**
     * 政治面貌
     */
    private String polity;
    /**
     *  入党时间
     */
    @JsonIgnore
    private Date polityJoin;
    /**
     * 职称
     */
    private String title;
    /**
     *  职务
     */
    @JsonProperty(value="post")
    private String duty;
    
    /**
     * 专业技术职务新标准(DM-DC-ZYJSZW)
     */
    @JsonIgnore
    private String dutyNew;
    /**
     * 学历
     */
    @JsonProperty(value="education")
    private String stulive;
    /**
     *  备注
     */
    private String remark;
    /**
     * 联系电话
     */
    @JsonIgnore
    private String linkPhone;
    /**
     * 所属院系id
     */
    @JsonIgnore
    private String instituteId;
    /**
     * 一卡通
     */
    @JsonIgnore
    private String generalCard;
    
    //===========================BaseTeacher属性上提 by zhangkc 2015-05-06===========================
    /**
     * 曾用名
     */
    @JsonIgnore
    private String pname;
    /**
     * 毕业时间
     */
    @JsonIgnore
    private Date fstime;
    /**
     * 毕业学校
     */
    @JsonIgnore
    private String fsschool;
    /**
     * 专业 
     */
    private String major;
    /**
     * 参加工作时间
     */
    @JsonProperty("workDate")
    @JsonSerialize(using = JsonDateSerialize.class)  
    private Date workdate;
    /**
     * 户口类型
     */
    @JsonIgnore
    private Integer registertype;
    /**
     * 户口所在地    
     */
    @JsonIgnore
    private String register;
    
    @JsonIgnore
    private String cardNumber;
    /**
     * 主页地址
     */
    private String homepage;
    /**
     * 联系地址
     */
    private String linkAddress;
    /**
     * 邮编
     */
    @JsonIgnore
    private String postalcode;
    /**
     * 行政区划码
     */
    @JsonIgnore
    private String regionCode;
    @JsonIgnore
    private String chargeNumber;
    @JsonIgnore
    private int chargeNumberType;
    /**
     *港澳台侨
     */
    @JsonIgnore
    private String returnedChinese;
    /**
     *国籍
     */
    private String country;
    /**
     *编制类别
     */
    @JsonIgnore
    private String weaveType;
    /**
     *是否是双师型
     */
    @JsonIgnore
    private String multiTitle;
    /**
     *授课状态
     */
    @JsonIgnore
    private String teachStatus;
    /**
     *  参加教育工作年月
     */
    @JsonIgnore
    private Date workTeachDate; 
    /**
     * 原有学历（全日制）
     */
    @JsonIgnore
    private String oldAcademicQualification;
    /**
     * 专业技术职务
     */
    @JsonIgnore
    private String specTechnicalDuty;
    /**
     * 专业技术职务评审年月
     */
    @JsonIgnore
    private Date specTechnicalDutyDate;
    /**
     * 家庭地址
     */
    private String homeAddress;
    
    
	//=============================台账属性上提 by zhangkc 2015-05-06==============================
	/**
	 * 身份证件类型
	 */
    @JsonProperty(value="identityCardType")
	private String identityType;
	/**
	 * 是否在编
	 */
    @JsonIgnore
	private String inPreparation;
	/**
	 * 是否属于免费师范生
	 */
    @JsonIgnore
	private String freeNormal;
	/**
	 * 是否特岗教师
	 */
    @JsonIgnore
	private String specialTeacher;
	/**
	 * 最高学位
	 */
    @JsonIgnore
	private String highestDegree;
	/**
	 * 获得最高学位的院校或机构
	 */
    @JsonIgnore
	private String highestDegreeInstitutions;
	/**
	 * 最高学历
	 */
    @JsonIgnore
	private String highestDiploma;
	/**
	 * 获得最高学历的院校或机构
	 */
    @JsonIgnore
	private String highestDiplomaInstitutions;
	/**
	 * 签订合同情况
	 */
    @JsonIgnore
	private String laborContractSituation;
	/**
	 * 五险一金情况
	 */
    @JsonIgnore
	private String fiveInsurancePayments;
	/**
	 * 近一年专任教师接受培训情况
	 */
    @JsonIgnore
	private String oneYearTraining;
	/**
	 * 主要任课课程
	 */
    @JsonIgnore
	private String mainCourse;
	/**
	 * 是否接受过特殊教育专业培养培训
	 */
    @JsonIgnore
	private String specialEduTraining;
	/**
	 * 是否学前教育专业
	 */
    @JsonIgnore
	private String preschoolSpecialty;
	/**
	 * 任课状况
	 */
    @JsonIgnore
	private String courseSituation;
	/**
	 * 任课学科类别
	 */
    @JsonIgnore
	private String courseSubjectCategory;
	/**
	 * 是否双师
	 */
    @JsonIgnore
	private String isDoubleCertification;
	/**
	 * 教师资格证类型
	 */
	private String certificationType;
	/**
	 * 教师资格证号码
	 */
	private String certificationCode;
	/**
	 * 取得的其他职业资格证书
	 */
	@JsonIgnore
	private String otherCertification;
	/**
	 * 取得的其他职业资格证书等级
	 */
	@JsonIgnore
	private String otherCertificationLevel;
	/**
	 * 企业工作（实践）时长
	 */
	@JsonIgnore
	private String enterpriseWorkTime;
	/**
	 * 导师类别
	 */
	@JsonIgnore
	private String tutorType;
	/**
	 * 现主要从事学科领域
	 */
	@JsonIgnore
	private String subjectAreas;
	/**
	 * 专家类别
	 */
	@JsonIgnore
	private String expertType;
	/**
	 * 重要科研教学获奖情况
	 */
	@JsonIgnore
	private String researchAwards;
	/**
	 * 获得海外学位情况
	 */
	@JsonIgnore
	private String overseasDegree;
	/**
	 * 海外研修经历
	 */
	@JsonIgnore
	private String overseasTraining;
	/**
	 * 学缘结构
	 */
	@JsonIgnore
	private String academicStructure;
	/**
	 * 个人信息标识码
	 */
	@JsonIgnore
	private String infoPin;
	/**
	 * 附设班/主体校id
	 */
	@JsonIgnore
	private String subschoolId;
	/**
	 * 骨干教师等级
	 */
	@JsonIgnore
	private String backboneTeacherLevel;
	/**
	 * 是否拥有统计从业资格证书
	 */
	@JsonIgnore
	private String isHaveCountCertification;
	/**
	 * 统计从业资格证书编号
	 */
	@JsonIgnore
	private String countCertificationCode;
	/**
	 * 是否学前教育专业毕业
	 */
	@JsonIgnore
	private String isPreeEduGraduate;
	/**
	 * 非本校在编编制所在单位
	 */
	@JsonIgnore
	private String weaveUnitId;
	/**
	 * 岗位类型
	 */
	@JsonIgnore
	private String job;
	
	/**
	 * 岗位类型
	 */
	@JsonIgnore
	private String jobLevel;
	
	/**
	 * 岗位类型
	 */
	@JsonIgnore
	private String otherJobLevel;
	
	/**
	 * 岗位类型分组
	 */
	@JsonIgnore
	private String jobGroup;
	/**
	 * 普通话等级
	 */
	@JsonProperty(value="mandarinLevel")
	private String putonghuaGrade;
	/**
	 * 任课学段
	 */
	@JsonIgnore
	private String learningPeriod;
	/**
	 * 是否全日制师范类专业毕业
	 */
	@JsonIgnore
	private String normalGraduated;
	/**
	 * 从事教育工作时间
	 */
	@JsonIgnore
	private Date educationWorkDate;
	
	/**
	 * 是否聘任制教师
	 */
	@JsonIgnore
	private String isAppointmentTeacher;
	
	/**
	 * 是否授课
	 */
	@JsonIgnore
	private String isGiveLessons;
	
	/**
	 * 是否双师型教师
	 */
	@JsonIgnore
	private String isDoubleTeachers;
	
	/**
	 * 分科情况
	 */
	@JsonIgnore
	private String subjectBranch;
	/**
	 * 分科大类
	 */
	@JsonIgnore
	private String subjectBranchGroup;
	
	/**
	 * 不授课原因
	 */
	@JsonIgnore
	private String notGivelessonReason;
	
	/**
	 * 是否特殊教育专业毕业
	 */
	@JsonIgnore
	private String isSpecialEduGraduate;
	
	/**
	 * 年龄
	 */
	@JsonIgnore
	private int age;
	
	/**
	 * 是否离校 0否 1是
	 */
	@JsonIgnore
	private String isLeaveSchool;
	
	/**
	 * 当前状态
	 */
	@JsonIgnore
	private String nowState;
	
	//=============================2016-02-23==============================
	/**
	 * 档案号
	 */
	@JsonIgnore
	private String fileNumber;
	
	/**
	 * 是否隐藏电话号码：0显示，1隐藏
	 */
	@JsonIgnore
	private int hidePhone;
	
	
	//=============================2016-10-11==============================
	/**
	 * 手机短号
	 */
	private String mobileCornet;
	
    // ==========================================辅助字段===============
	@JsonIgnore
    private String deptName;// 部门名
	@JsonIgnore
    private String dutyName;// 职务名
	@JsonIgnore
    private boolean conflict = false;//是否冲突
	@JsonIgnore
	private String userId;
	@JsonIgnore
	private String unitName;
	@JsonIgnore
	private Integer ownerType;
	
    /** default constructor */
	
	/**新疆页面 字段**/
	/**
	 * 任职时间
	 */
	private Date dutyDate;
	/**
	 * 其他职务任职时间
	 */
	private Date dutyExDate;
	/**
	 * 其他职务
	 */
	private String dutyEx;
	/**
	 * 其他职务所在单位
	 */
	private String dutyExUnit;
	/**
	 * 职位级别
	 */
	private String dutyLevel;
    public Teacher() {
    }
    /**
     * 其他职业级别
     */
    private String otherDutyLevel;
    /**
     * 照片分类
     */
    public String getObjectType() {
        return "teacher";
    }
    
    /**
     * 图片文件宽度，0表示不限制
     */
    public int getPhotoWidth() {
        return 220;
    }

    /**
     * 图片文件高度，0表示不限制
     */
    public int getPhotoHeight() {
        return 220;
    }
    
    public String getTchId() {
        return tchId;
    }

    public void setTchId(String tchId) {
        this.tchId = tchId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEusing() {
        return this.eusing;
    }

    public void setEusing(String eusing) {
        this.eusing = eusing;
    }

    public String getIdcard() {
        return this.idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPersonTel() {
        return this.personTel;
    }

    public void setPersonTel(String personTel) {
        this.personTel = personTel;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getUnitid() {
        return this.unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOfficeTel() {
        return this.officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public String getPerNative() {
		return perNative;
	}

	public void setPerNative(String perNative) {
		this.perNative = perNative;
	}

	public String getPolity() {
		return polity;
	}

	public void setPolity(String polity) {
		this.polity = polity;
	}

	public Date getPolityJoin() {
		return polityJoin;
	}

	public void setPolityJoin(Date polityJoin) {
		this.polityJoin = polityJoin;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getStulive() {
		return stulive;
	}

	public void setStulive(String stulive) {
		this.stulive = stulive;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public boolean isConflict() {
		return conflict;
	}

	public void setConflict(boolean conflict) {
		this.conflict = conflict;
	}

	public String getWeaveUnitId() {
		return weaveUnitId;
	}

	public void setWeaveUnitId(String weaveUnitId) {
		this.weaveUnitId = weaveUnitId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(Integer ownerType) {
		this.ownerType = ownerType;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getGeneralCard() {
		return generalCard;
	}

	public void setGeneralCard(String generalCard) {
		this.generalCard = generalCard;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Date getFstime() {
		return fstime;
	}

	public void setFstime(Date fstime) {
		this.fstime = fstime;
	}

	public String getFsschool() {
		return fsschool;
	}

	public void setFsschool(String fsschool) {
		this.fsschool = fsschool;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Date getWorkdate() {
		return workdate;
	}

	public void setWorkdate(Date workdate) {
		this.workdate = workdate;
	}

	public Integer getRegistertype() {
		return registertype;
	}

	public void setRegistertype(Integer registertype) {
		this.registertype = registertype;
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getChargeNumber() {
		return chargeNumber;
	}

	public void setChargeNumber(String chargeNumber) {
		this.chargeNumber = chargeNumber;
	}

	public int getChargeNumberType() {
		return chargeNumberType;
	}

	public void setChargeNumberType(int chargeNumberType) {
		this.chargeNumberType = chargeNumberType;
	}

	public String getReturnedChinese() {
		return returnedChinese;
	}

	public void setReturnedChinese(String returnedChinese) {
		this.returnedChinese = returnedChinese;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getWeaveType() {
		return weaveType;
	}

	public void setWeaveType(String weaveType) {
		this.weaveType = weaveType;
	}

	public String getMultiTitle() {
		return multiTitle;
	}

	public void setMultiTitle(String multiTitle) {
		this.multiTitle = multiTitle;
	}

	public String getTeachStatus() {
		return teachStatus;
	}

	public void setTeachStatus(String teachStatus) {
		this.teachStatus = teachStatus;
	}

	public Date getWorkTeachDate() {
		return workTeachDate;
	}

	public void setWorkTeachDate(Date workTeachDate) {
		this.workTeachDate = workTeachDate;
	}

	public String getOldAcademicQualification() {
		return oldAcademicQualification;
	}

	public void setOldAcademicQualification(String oldAcademicQualification) {
		this.oldAcademicQualification = oldAcademicQualification;
	}

	public String getSpecTechnicalDuty() {
		return specTechnicalDuty;
	}

	public void setSpecTechnicalDuty(String specTechnicalDuty) {
		this.specTechnicalDuty = specTechnicalDuty;
	}

	public Date getSpecTechnicalDutyDate() {
		return specTechnicalDutyDate;
	}

	public void setSpecTechnicalDutyDate(Date specTechnicalDutyDate) {
		this.specTechnicalDutyDate = specTechnicalDutyDate;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getInPreparation() {
		return inPreparation;
	}

	public void setInPreparation(String inPreparation) {
		this.inPreparation = inPreparation;
	}

	public String getFreeNormal() {
		return freeNormal;
	}

	public void setFreeNormal(String freeNormal) {
		this.freeNormal = freeNormal;
	}

	public String getSpecialTeacher() {
		return specialTeacher;
	}

	public void setSpecialTeacher(String specialTeacher) {
		this.specialTeacher = specialTeacher;
	}

	public String getHighestDegree() {
		return highestDegree;
	}

	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}

	public String getHighestDegreeInstitutions() {
		return highestDegreeInstitutions;
	}

	public void setHighestDegreeInstitutions(String highestDegreeInstitutions) {
		this.highestDegreeInstitutions = highestDegreeInstitutions;
	}

	public String getHighestDiploma() {
		return highestDiploma;
	}

	public void setHighestDiploma(String highestDiploma) {
		this.highestDiploma = highestDiploma;
	}

	public String getHighestDiplomaInstitutions() {
		return highestDiplomaInstitutions;
	}

	public void setHighestDiplomaInstitutions(String highestDiplomaInstitutions) {
		this.highestDiplomaInstitutions = highestDiplomaInstitutions;
	}

	public String getLaborContractSituation() {
		return laborContractSituation;
	}

	public void setLaborContractSituation(String laborContractSituation) {
		this.laborContractSituation = laborContractSituation;
	}

	public String getFiveInsurancePayments() {
		return fiveInsurancePayments;
	}

	public void setFiveInsurancePayments(String fiveInsurancePayments) {
		this.fiveInsurancePayments = fiveInsurancePayments;
	}

	public String getOneYearTraining() {
		return oneYearTraining;
	}

	public void setOneYearTraining(String oneYearTraining) {
		this.oneYearTraining = oneYearTraining;
	}

	public String getMainCourse() {
		return mainCourse;
	}

	public void setMainCourse(String mainCourse) {
		this.mainCourse = mainCourse;
	}

	public String getSpecialEduTraining() {
		return specialEduTraining;
	}

	public void setSpecialEduTraining(String specialEduTraining) {
		this.specialEduTraining = specialEduTraining;
	}

	public String getPreschoolSpecialty() {
		return preschoolSpecialty;
	}

	public void setPreschoolSpecialty(String preschoolSpecialty) {
		this.preschoolSpecialty = preschoolSpecialty;
	}

	public String getCourseSituation() {
		return courseSituation;
	}

	public void setCourseSituation(String courseSituation) {
		this.courseSituation = courseSituation;
	}

	public String getCourseSubjectCategory() {
		return courseSubjectCategory;
	}

	public void setCourseSubjectCategory(String courseSubjectCategory) {
		this.courseSubjectCategory = courseSubjectCategory;
	}

	public String getIsDoubleCertification() {
		return isDoubleCertification;
	}

	public void setIsDoubleCertification(String isDoubleCertification) {
		this.isDoubleCertification = isDoubleCertification;
	}

	public String getCertificationType() {
		return certificationType;
	}

	public void setCertificationType(String certificationType) {
		this.certificationType = certificationType;
	}

	public String getCertificationCode() {
		return certificationCode;
	}

	public void setCertificationCode(String certificationCode) {
		this.certificationCode = certificationCode;
	}

	public String getOtherCertification() {
		return otherCertification;
	}

	public void setOtherCertification(String otherCertification) {
		this.otherCertification = otherCertification;
	}

	public String getOtherCertificationLevel() {
		return otherCertificationLevel;
	}

	public void setOtherCertificationLevel(String otherCertificationLevel) {
		this.otherCertificationLevel = otherCertificationLevel;
	}

	public String getEnterpriseWorkTime() {
		return enterpriseWorkTime;
	}

	public void setEnterpriseWorkTime(String enterpriseWorkTime) {
		this.enterpriseWorkTime = enterpriseWorkTime;
	}

	public String getTutorType() {
		return tutorType;
	}

	public void setTutorType(String tutorType) {
		this.tutorType = tutorType;
	}

	public String getSubjectAreas() {
		return subjectAreas;
	}

	public void setSubjectAreas(String subjectAreas) {
		this.subjectAreas = subjectAreas;
	}

	public String getExpertType() {
		return expertType;
	}

	public void setExpertType(String expertType) {
		this.expertType = expertType;
	}

	public String getResearchAwards() {
		return researchAwards;
	}

	public void setResearchAwards(String researchAwards) {
		this.researchAwards = researchAwards;
	}

	public String getOverseasDegree() {
		return overseasDegree;
	}

	public void setOverseasDegree(String overseasDegree) {
		this.overseasDegree = overseasDegree;
	}

	public String getOverseasTraining() {
		return overseasTraining;
	}

	public void setOverseasTraining(String overseasTraining) {
		this.overseasTraining = overseasTraining;
	}

	public String getAcademicStructure() {
		return academicStructure;
	}

	public void setAcademicStructure(String academicStructure) {
		this.academicStructure = academicStructure;
	}

	public String getInfoPin() {
		return infoPin;
	}

	public void setInfoPin(String infoPin) {
		this.infoPin = infoPin;
	}

	public String getSubschoolId() {
		return subschoolId;
	}

	public void setSubschoolId(String subschoolId) {
		this.subschoolId = subschoolId;
	}

	public String getBackboneTeacherLevel() {
		return backboneTeacherLevel;
	}

	public void setBackboneTeacherLevel(String backboneTeacherLevel) {
		this.backboneTeacherLevel = backboneTeacherLevel;
	}

	public String getIsHaveCountCertification() {
		return isHaveCountCertification;
	}

	public void setIsHaveCountCertification(String isHaveCountCertification) {
		this.isHaveCountCertification = isHaveCountCertification;
	}

	public String getCountCertificationCode() {
		return countCertificationCode;
	}

	public void setCountCertificationCode(String countCertificationCode) {
		this.countCertificationCode = countCertificationCode;
	}

	public String getIsPreeEduGraduate() {
		return isPreeEduGraduate;
	}

	public void setIsPreeEduGraduate(String isPreeEduGraduate) {
		this.isPreeEduGraduate = isPreeEduGraduate;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getPutonghuaGrade() {
		return putonghuaGrade;
	}

	public void setPutonghuaGrade(String putonghuaGrade) {
		this.putonghuaGrade = putonghuaGrade;
	}

	public String getLearningPeriod() {
		return learningPeriod;
	}

	public void setLearningPeriod(String learningPeriod) {
		this.learningPeriod = learningPeriod;
	}

	public String getNormalGraduated() {
		return normalGraduated;
	}

	public void setNormalGraduated(String normalGraduated) {
		this.normalGraduated = normalGraduated;
	}

	public Date getEducationWorkDate() {
		return educationWorkDate;
	}

	public void setEducationWorkDate(Date educationWorkDate) {
		this.educationWorkDate = educationWorkDate;
	}

	public String getIsAppointmentTeacher() {
		return isAppointmentTeacher;
	}

	public void setIsAppointmentTeacher(String isAppointmentTeacher) {
		this.isAppointmentTeacher = isAppointmentTeacher;
	}

	public String getIsGiveLessons() {
		return isGiveLessons;
	}

	public void setIsGiveLessons(String isGiveLessons) {
		this.isGiveLessons = isGiveLessons;
	}

	public String getIsDoubleTeachers() {
		return isDoubleTeachers;
	}

	public void setIsDoubleTeachers(String isDoubleTeachers) {
		this.isDoubleTeachers = isDoubleTeachers;
	}

	public String getSubjectBranch() {
		return subjectBranch;
	}

	public void setSubjectBranch(String subjectBranch) {
		this.subjectBranch = subjectBranch;
	}

	public String getSubjectBranchGroup() {
		return subjectBranchGroup;
	}

	public void setSubjectBranchGroup(String subjectBranchGroup) {
		this.subjectBranchGroup = subjectBranchGroup;
	}

	public String getNotGivelessonReason() {
		return notGivelessonReason;
	}

	public void setNotGivelessonReason(String notGivelessonReason) {
		this.notGivelessonReason = notGivelessonReason;
	}

	public String getIsSpecialEduGraduate() {
		return isSpecialEduGraduate;
	}

	public void setIsSpecialEduGraduate(String isSpecialEduGraduate) {
		this.isSpecialEduGraduate = isSpecialEduGraduate;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDutyNew() {
		return dutyNew;
	}

	public void setDutyNew(String dutyNew) {
		this.dutyNew = dutyNew;
	}

	public String getIsLeaveSchool() {
		return isLeaveSchool;
	}

	public void setIsLeaveSchool(String isLeaveSchool) {
		this.isLeaveSchool = isLeaveSchool;
	}

	public String getNowState() {
		return nowState;
	}

	public void setNowState(String nowState) {
		this.nowState = nowState;
	}

	public String getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	public String getOtherJobLevel() {
		return otherJobLevel;
	}

	public void setOtherJobLevel(String otherJobLevel) {
		this.otherJobLevel = otherJobLevel;
	}

	public int getHidePhone() {
		return hidePhone;
	}

	public void setHidePhone(int hidePhone) {
		this.hidePhone = hidePhone;
	}

	public String getMobileCornet() {
		return mobileCornet;
	}

	public void setMobileCornet(String mobileCornet) {
		this.mobileCornet = mobileCornet;
	}

	public Date getDutyDate() {
		return dutyDate;
	}

	public void setDutyDate(Date dutyDate) {
		this.dutyDate = dutyDate;
	}

	public Date getDutyExDate() {
		return dutyExDate;
	}

	public void setDutyExDate(Date dutyExDate) {
		this.dutyExDate = dutyExDate;
	}

	public String getDutyEx() {
		return dutyEx;
	}

	public void setDutyEx(String dutyEx) {
		this.dutyEx = dutyEx;
	}

	public String getDutyExUnit() {
		return dutyExUnit;
	}

	public void setDutyExUnit(String dutyExUnit) {
		this.dutyExUnit = dutyExUnit;
	}

	public String getDutyLevel() {
		return dutyLevel;
	}

	public void setDutyLevel(String dutyLevel) {
		this.dutyLevel = dutyLevel;
	}

	public String getOtherDutyLevel() {
		return otherDutyLevel;
	}

	public void setOtherDutyLevel(String otherDutyLevel) {
		this.otherDutyLevel = otherDutyLevel;
	}
	
}
