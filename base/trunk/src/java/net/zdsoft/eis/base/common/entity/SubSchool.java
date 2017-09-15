package net.zdsoft.eis.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class SubSchool extends BaseEntity {
    private static final long serialVersionUID = -8765613547430315962L;

    private String name; // 校区名称
    private String address; // 校区地址
    private String schid; // 所属学校ID

    /**
	 * 校区学校类型（主校区就是学校的学校类型，附设校区就是附设班学校类型）
	 */
	private String subschoolType;
	/**
	 * 是否乡镇中心幼儿园
	 */
	private boolean isTownCenterKindergarten;
	/**
	 * 是否乡镇中心小学
	 */
	private boolean isTownCenterPrimarySchool;
	/**
	 * 是否附属学校（园）
	 */
	private boolean isAttachedSchool;
	/**
	 * 附属于高校(机构)名称
	 */
	private String attachedSchoolName;
	/**
	 * 是否体育运动场（馆）面积是否达标
	 */
	private boolean isStadiumQualified;
	/**
	 * 是否体育器械配备是否达标
	 */
	private boolean isSportsEquipQualified;
	/**
	 * 是否音乐器材配备是否达标
	 */
	private boolean isMusicalEquipQualified;
	/**
	 * 是否美术器材配备是否达标
	 */
	private boolean isArtEquipQualified;
	/**
	 * 是否数学自然实验仪器是否达标（小学）
	 */
	private boolean isMathNatureEquipQualified;
	/**
	 * 是否理科实验仪器是否达标（中学）
	 */
	private boolean isExperimentEquipQualified;
	/**
	 * 是否建立校园网
	 */
	private String existsCampusNetwork;
	/**
	 * 是否接入互联网
	 */
	private String internetAccess;
	/**
	 * 接入互联网方式
	 */
	private String internetAccessWay;
	/**
	 * 接入互联网出口带宽
	 */
	private double internetBandwidth;
	/**
	 * 校医院（卫生室）
	 */
	private String hasSchoolHospital;
	/**
	 * 有无专职校医
	 */
	private String hasFulltimeSchoolDoctor;
	/**
	 * 有无专职保健人员
	 */
	private String hasFulltimeHealthStuff;
	/**
	 * 有无少数民族双语教学班
	 */
	private String hasMinorityBilingualClass;
	/**
	 * 双语教学的少数民族语言
	 */
	private String minorityBilingualLanguage;
	/**
	 * 上学年体检学生数
	 */
	private int examinationNum;
	/**
	 * 上学年参加国家学生体质健康标准测试人数
	 */
	private int physExaminationNum;
	/**
	 * 上学年参加国家学生体质健康标准测试人数优秀人数
	 */
	private int physExaminationExcellentNum;
	/**
	 * 上学年参加国家学生体质健康标准测试人数良好人数
	 */
	private int physExaminationGoodNum;
	/**
	 * 上学年参加国家学生体质健康标准测试人数及格人数
	 */
	private int physExaminationPassNum;
	/**
	 * 上学年参加国家学生体质健康标准测试人数不及格人数
	 */
	private int physExaminationFailNum;
	/**
	 * 县级及以上骨干教师（小学）人数
	 */
	private int primaryBackboneTeacherNum;
	/**
	 * 县级及以上骨干教师（初中）人数
	 */
	private int juniorBackboneTeacherNum;
	/**
	 * 县级及以上骨干教师（高中）人数
	 */
	private int seniorBackboneTeacherNum;
	/**
	 * 安全保卫人员人数
	 */
	private int securityStaffNum;
	/**
	 * 心理健康教育教师人数
	 */
	private int psychologyTeaNum;
	/**
	 * 专职心理健康教育教师人数
	 */
	private int fulltimePsychologyTeaNum;
	/**
	 * 学校供水方式
	 */
	private String waterSupplyType;
	/**
	 * 学校厕所情况
	 */
	private String toiletSituation;
	/** 所含学段 */
	private String sections;

	/** 政府购买学位数 */
	private Long governmentBuyDegreeNumber;
	
	/**
	 * 小学学制
	 */
	private int gradeYear;
	/**
	 * 小学入学年龄
	 */
	private int gradeAge;
	/**
	 * 初中学制
	 */
	private int juniorYear;
	/**
	 * 初中入年龄
	 */
	private int juniorAge;
	
	//基础数据中心-中职类型学校添加
	/** 是否国家级重点中职校 */
	private boolean isNationalKeySchool;
	/** 是否省部级重点中职校 */
	private boolean isProvincialKeySchool;
	/** 是否国家示范性中职校 */
	private boolean isNationalExemplarySchool;
	/** 是否省部级示范性中职校 */
	private boolean isProvincialExemplarySchool;
	/** 专业实习场（所） */
	private int practicePlaceNum;
	/** 应届毕业生就业人数 */
	private int graduatesEmploymentNum;
	/** 应届毕业生升学人数 */
	private int graduatesToHigherSchoolNum;
	/** 定期公开出版的专业刊物数 */
	private int specialtyMagazineNum;
	/** 在校生中住宿生数 */
	private int boardersNum;
	/** 历史沿革 */
	private String historicalEvolution;
	/** 院（系）设置 */
	private String instituteSetting;
	/** 专业设置 */
	private String specialitySetting;
	/** 实验室设置 */
	private String labSetting;
	/** 研究中心设置 */
	private String researchCenterSetting;
	/** 定期出版的专业刊物 */
	private String specialtyMagazine;
	/** 奖学金数目 */
	private int scholarshipNum;
	/** 奖学金总金额 */
	private int scholarshipSumMoney;
	/** 奖学金最高金额 */
	private int scholarshipMaxMoney;
	/** 奖学金最低金额 */
	private int scholarshipMinMoney;
	/** 主要校办产业 */
	private String mainSchoolRunIndustry;
	/** 数据核查结果说明及建议 */
    private String explanation;
    /** 学生类型*/
    private String studentCategory;
    public SubSchool() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchid() {
        return schid;
    }

    public void setSchid(String schid) {
        this.schid = schid;
    }

	public String getSubschoolType() {
		return subschoolType;
	}

	public void setSubschoolType(String subschoolType) {
		this.subschoolType = subschoolType;
	}

	public boolean isTownCenterKindergarten() {
		return isTownCenterKindergarten;
	}

	public void setTownCenterKindergarten(boolean isTownCenterKindergarten) {
		this.isTownCenterKindergarten = isTownCenterKindergarten;
	}

	public boolean isTownCenterPrimarySchool() {
		return isTownCenterPrimarySchool;
	}

	public void setTownCenterPrimarySchool(boolean isTownCenterPrimarySchool) {
		this.isTownCenterPrimarySchool = isTownCenterPrimarySchool;
	}

	public boolean isAttachedSchool() {
		return isAttachedSchool;
	}

	public void setAttachedSchool(boolean isAttachedSchool) {
		this.isAttachedSchool = isAttachedSchool;
	}

	public String getAttachedSchoolName() {
		return attachedSchoolName;
	}

	public void setAttachedSchoolName(String attachedSchoolName) {
		this.attachedSchoolName = attachedSchoolName;
	}

	public boolean isStadiumQualified() {
		return isStadiumQualified;
	}

	public void setStadiumQualified(boolean isStadiumQualified) {
		this.isStadiumQualified = isStadiumQualified;
	}

	public boolean isSportsEquipQualified() {
		return isSportsEquipQualified;
	}

	public void setSportsEquipQualified(boolean isSportsEquipQualified) {
		this.isSportsEquipQualified = isSportsEquipQualified;
	}

	public boolean isMusicalEquipQualified() {
		return isMusicalEquipQualified;
	}

	public void setMusicalEquipQualified(boolean isMusicalEquipQualified) {
		this.isMusicalEquipQualified = isMusicalEquipQualified;
	}

	public boolean isArtEquipQualified() {
		return isArtEquipQualified;
	}

	public void setArtEquipQualified(boolean isArtEquipQualified) {
		this.isArtEquipQualified = isArtEquipQualified;
	}

	public boolean isMathNatureEquipQualified() {
		return isMathNatureEquipQualified;
	}

	public void setMathNatureEquipQualified(boolean isMathNatureEquipQualified) {
		this.isMathNatureEquipQualified = isMathNatureEquipQualified;
	}

	public boolean isExperimentEquipQualified() {
		return isExperimentEquipQualified;
	}

	public void setExperimentEquipQualified(boolean isExperimentEquipQualified) {
		this.isExperimentEquipQualified = isExperimentEquipQualified;
	}

	public String getExistsCampusNetwork() {
		return existsCampusNetwork;
	}

	public void setExistsCampusNetwork(String existsCampusNetwork) {
		this.existsCampusNetwork = existsCampusNetwork;
	}

	public String getInternetAccess() {
		return internetAccess;
	}

	public void setInternetAccess(String internetAccess) {
		this.internetAccess = internetAccess;
	}

	public String getInternetAccessWay() {
		return internetAccessWay;
	}

	public void setInternetAccessWay(String internetAccessWay) {
		this.internetAccessWay = internetAccessWay;
	}

	public double getInternetBandwidth() {
		return internetBandwidth;
	}

	public void setInternetBandwidth(double internetBandwidth) {
		this.internetBandwidth = internetBandwidth;
	}

	public String getHasSchoolHospital() {
		return hasSchoolHospital;
	}

	public void setHasSchoolHospital(String hasSchoolHospital) {
		this.hasSchoolHospital = hasSchoolHospital;
	}

	public String getHasFulltimeSchoolDoctor() {
		return hasFulltimeSchoolDoctor;
	}

	public void setHasFulltimeSchoolDoctor(String hasFulltimeSchoolDoctor) {
		this.hasFulltimeSchoolDoctor = hasFulltimeSchoolDoctor;
	}

	public String getHasFulltimeHealthStuff() {
		return hasFulltimeHealthStuff;
	}

	public void setHasFulltimeHealthStuff(String hasFulltimeHealthStuff) {
		this.hasFulltimeHealthStuff = hasFulltimeHealthStuff;
	}

	public String getHasMinorityBilingualClass() {
		return hasMinorityBilingualClass;
	}

	public void setHasMinorityBilingualClass(String hasMinorityBilingualClass) {
		this.hasMinorityBilingualClass = hasMinorityBilingualClass;
	}

	public String getMinorityBilingualLanguage() {
		return minorityBilingualLanguage;
	}

	public void setMinorityBilingualLanguage(String minorityBilingualLanguage) {
		this.minorityBilingualLanguage = minorityBilingualLanguage;
	}

	public int getExaminationNum() {
		return examinationNum;
	}

	public void setExaminationNum(int examinationNum) {
		this.examinationNum = examinationNum;
	}

	public int getPhysExaminationNum() {
		return physExaminationNum;
	}

	public void setPhysExaminationNum(int physExaminationNum) {
		this.physExaminationNum = physExaminationNum;
	}

	public int getPhysExaminationExcellentNum() {
		return physExaminationExcellentNum;
	}

	public void setPhysExaminationExcellentNum(int physExaminationExcellentNum) {
		this.physExaminationExcellentNum = physExaminationExcellentNum;
	}

	public int getPhysExaminationGoodNum() {
		return physExaminationGoodNum;
	}

	public void setPhysExaminationGoodNum(int physExaminationGoodNum) {
		this.physExaminationGoodNum = physExaminationGoodNum;
	}

	public int getPhysExaminationPassNum() {
		return physExaminationPassNum;
	}

	public void setPhysExaminationPassNum(int physExaminationPassNum) {
		this.physExaminationPassNum = physExaminationPassNum;
	}

	public int getPhysExaminationFailNum() {
		return physExaminationFailNum;
	}

	public void setPhysExaminationFailNum(int physExaminationFailNum) {
		this.physExaminationFailNum = physExaminationFailNum;
	}

	public int getPrimaryBackboneTeacherNum() {
		return primaryBackboneTeacherNum;
	}

	public void setPrimaryBackboneTeacherNum(int primaryBackboneTeacherNum) {
		this.primaryBackboneTeacherNum = primaryBackboneTeacherNum;
	}

	public int getJuniorBackboneTeacherNum() {
		return juniorBackboneTeacherNum;
	}

	public void setJuniorBackboneTeacherNum(int juniorBackboneTeacherNum) {
		this.juniorBackboneTeacherNum = juniorBackboneTeacherNum;
	}

	public int getSeniorBackboneTeacherNum() {
		return seniorBackboneTeacherNum;
	}

	public void setSeniorBackboneTeacherNum(int seniorBackboneTeacherNum) {
		this.seniorBackboneTeacherNum = seniorBackboneTeacherNum;
	}

	public int getSecurityStaffNum() {
		return securityStaffNum;
	}

	public void setSecurityStaffNum(int securityStaffNum) {
		this.securityStaffNum = securityStaffNum;
	}

	public int getPsychologyTeaNum() {
		return psychologyTeaNum;
	}

	public void setPsychologyTeaNum(int psychologyTeaNum) {
		this.psychologyTeaNum = psychologyTeaNum;
	}

	public int getFulltimePsychologyTeaNum() {
		return fulltimePsychologyTeaNum;
	}

	public void setFulltimePsychologyTeaNum(int fulltimePsychologyTeaNum) {
		this.fulltimePsychologyTeaNum = fulltimePsychologyTeaNum;
	}

	public String getWaterSupplyType() {
		return waterSupplyType;
	}

	public void setWaterSupplyType(String waterSupplyType) {
		this.waterSupplyType = waterSupplyType;
	}

	public String getToiletSituation() {
		return toiletSituation;
	}

	public void setToiletSituation(String toiletSituation) {
		this.toiletSituation = toiletSituation;
	}

	public String getSections() {
		return sections;
	}

	public void setSections(String sections) {
		this.sections = sections;
	}

	public Long getGovernmentBuyDegreeNumber() {
		return governmentBuyDegreeNumber;
	}

	public void setGovernmentBuyDegreeNumber(Long governmentBuyDegreeNumber) {
		this.governmentBuyDegreeNumber = governmentBuyDegreeNumber;
	}

	public int getGradeYear() {
		return gradeYear;
	}

	public void setGradeYear(int gradeYear) {
		this.gradeYear = gradeYear;
	}

	public int getGradeAge() {
		return gradeAge;
	}

	public void setGradeAge(int gradeAge) {
		this.gradeAge = gradeAge;
	}

	public int getJuniorYear() {
		return juniorYear;
	}

	public void setJuniorYear(int juniorYear) {
		this.juniorYear = juniorYear;
	}

	public int getJuniorAge() {
		return juniorAge;
	}

	public void setJuniorAge(int juniorAge) {
		this.juniorAge = juniorAge;
	}

	public boolean isNationalKeySchool() {
		return isNationalKeySchool;
	}

	public void setNationalKeySchool(boolean isNationalKeySchool) {
		this.isNationalKeySchool = isNationalKeySchool;
	}

	public boolean isProvincialKeySchool() {
		return isProvincialKeySchool;
	}

	public void setProvincialKeySchool(boolean isProvincialKeySchool) {
		this.isProvincialKeySchool = isProvincialKeySchool;
	}

	public boolean isNationalExemplarySchool() {
		return isNationalExemplarySchool;
	}

	public void setNationalExemplarySchool(boolean isNationalExemplarySchool) {
		this.isNationalExemplarySchool = isNationalExemplarySchool;
	}

	public boolean isProvincialExemplarySchool() {
		return isProvincialExemplarySchool;
	}

	public void setProvincialExemplarySchool(boolean isProvincialExemplarySchool) {
		this.isProvincialExemplarySchool = isProvincialExemplarySchool;
	}

	public int getPracticePlaceNum() {
		return practicePlaceNum;
	}

	public void setPracticePlaceNum(int practicePlaceNum) {
		this.practicePlaceNum = practicePlaceNum;
	}

	public int getGraduatesEmploymentNum() {
		return graduatesEmploymentNum;
	}

	public void setGraduatesEmploymentNum(int graduatesEmploymentNum) {
		this.graduatesEmploymentNum = graduatesEmploymentNum;
	}

	public int getGraduatesToHigherSchoolNum() {
		return graduatesToHigherSchoolNum;
	}

	public void setGraduatesToHigherSchoolNum(int graduatesToHigherSchoolNum) {
		this.graduatesToHigherSchoolNum = graduatesToHigherSchoolNum;
	}

	public int getSpecialtyMagazineNum() {
		return specialtyMagazineNum;
	}

	public void setSpecialtyMagazineNum(int specialtyMagazineNum) {
		this.specialtyMagazineNum = specialtyMagazineNum;
	}

	public int getBoardersNum() {
		return boardersNum;
	}

	public void setBoardersNum(int boardersNum) {
		this.boardersNum = boardersNum;
	}

	public String getHistoricalEvolution() {
		return historicalEvolution;
	}

	public void setHistoricalEvolution(String historicalEvolution) {
		this.historicalEvolution = historicalEvolution;
	}

	public String getInstituteSetting() {
		return instituteSetting;
	}

	public void setInstituteSetting(String instituteSetting) {
		this.instituteSetting = instituteSetting;
	}

	public String getSpecialitySetting() {
		return specialitySetting;
	}

	public void setSpecialitySetting(String specialitySetting) {
		this.specialitySetting = specialitySetting;
	}

	public String getLabSetting() {
		return labSetting;
	}

	public void setLabSetting(String labSetting) {
		this.labSetting = labSetting;
	}

	public String getResearchCenterSetting() {
		return researchCenterSetting;
	}

	public void setResearchCenterSetting(String researchCenterSetting) {
		this.researchCenterSetting = researchCenterSetting;
	}

	public String getSpecialtyMagazine() {
		return specialtyMagazine;
	}

	public void setSpecialtyMagazine(String specialtyMagazine) {
		this.specialtyMagazine = specialtyMagazine;
	}

	public int getScholarshipNum() {
		return scholarshipNum;
	}

	public void setScholarshipNum(int scholarshipNum) {
		this.scholarshipNum = scholarshipNum;
	}

	public int getScholarshipSumMoney() {
		return scholarshipSumMoney;
	}

	public void setScholarshipSumMoney(int scholarshipSumMoney) {
		this.scholarshipSumMoney = scholarshipSumMoney;
	}

	public int getScholarshipMaxMoney() {
		return scholarshipMaxMoney;
	}

	public void setScholarshipMaxMoney(int scholarshipMaxMoney) {
		this.scholarshipMaxMoney = scholarshipMaxMoney;
	}

	public int getScholarshipMinMoney() {
		return scholarshipMinMoney;
	}

	public void setScholarshipMinMoney(int scholarshipMinMoney) {
		this.scholarshipMinMoney = scholarshipMinMoney;
	}

	public String getMainSchoolRunIndustry() {
		return mainSchoolRunIndustry;
	}

	public void setMainSchoolRunIndustry(String mainSchoolRunIndustry) {
		this.mainSchoolRunIndustry = mainSchoolRunIndustry;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getStudentCategory() {
		return studentCategory;
	}

	public void setStudentCategory(String studentCategory) {
		this.studentCategory = studentCategory;
	}
}
