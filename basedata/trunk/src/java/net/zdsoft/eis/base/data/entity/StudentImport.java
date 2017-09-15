package net.zdsoft.eis.base.data.entity;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * @author hexq
 * @version $Revision: 1.0 $, $Date: 2009-10-9 下午04:56:31 $
 */
public class StudentImport {

	private String username;			//用户名
	private String password;			//登录密码
	
	//学生基本字段
    private String stuname; 			// 姓名
    private String sex; 				// 性别
    private String birthday; 			// 出生日期
    private String source; 			// 学生来源
    private String identitycardType;//身份证件类型
    private String identitycard; 		// 身份证号码
    private String unitivecode;		    // 学籍号	
    private String stucode; 			// 学号
    private String classid; 			// 班级id
    private String boarder; 			// 就读方式
    private String registerinfo; 		// 户籍信息
    private String cardnumber;			// 点到卡号
    private String stateCode;            //国家学籍号
    
    private String islocalsource; 		// 是否本地生源
    private String flowingpeople;		// 是否流动人口
    private String background; 			// 政治面貌
    private String nation; 				// 民族
    private String oldname;				// 曾用名
    private String spellname; 			// 姓名拼音
    private String bloodtype;			// 血型
    private String religion; 			// 宗教信仰
    private String homeaddress; 		// 家庭地址
    private String registerplace; 		// 户口所在地
    
    private String registertype; 		// 户口类别
    private String linkphone; 			// 联系电话
    private String postalcode;			// 邮政编码
    private String email; 				// 电子邮件
    private String toschooltype;		// 入学方式
    private String toschooldate; 		// 入学年月
    private String oldschool;			// 毕业学校名称
    private String studenttype;			// 学生类别
    private String nativeplace; 		// 籍贯
    private String health; 				// 健康状况
    
    private String country; 			// 国别
    private String sourceplace; 		// 来源地区
    private String preeduflag;		    // 是否接受学前教育
    private String mobilephone; 		// 手机
    private String computerlevel; 		// 计算机等级
    private String familyorigin;	    // 家庭出身
    private String strong;				// 特长
    private String remark; 				// 备注
    private String examcode;		    // 考试证号
    
    //2009-12-21 新增字段
    private String linkaddress;			// 联系地址
    private String homeplace;			// 出生地
    private String economystate;		// 经济状况
    private String homepage;			// 个人主页
    private String returnedchinese;		// 港澳台侨
    private String polityintroducer;	// 入团介绍人		
    private String politydate;			// 入团时间
    private String englishlevel;		// 英语等级
    private String singleton;			// 独生子女
    private String stayin;				// 留守儿童
    private String boorish;				// 农民工子女
    
    private String classorderid;//班内编号
    
    //考试分数信息
    private String subj1;				// 科目1
    private String subj2;				// 科目2
    private String subj3;				// 科目3
    private String subj4;				// 科目4
    private String subj5;				// 科目5
    private String subj6;				// 科目6
    private String subj7;				// 科目7
    private String subj8;				// 科目8
    private String subj9;				// 科目9
    
    
    
    
    //学生数据导入新增字段
    private String zkcode;				// 中考证号
    private String zkresult;			// 中考成绩
    private Integer isSpecialID;		// 是否特殊身份证

    //陇南用
    private String multilingual;//外语语种
    private String gradType;//毕业类别
    private String examineeType;//考生类别
    private String specialtyType;//专业类别
    private String specialExaminee;//是否特殊考生
    private String specialExamineeType;//特殊考生类别
    private String joinProvince;//是否参加省统
    private String joinProvinceType;//省统类别
    private String answerLanguage;//采用何种语言答卷
    private String chineseLanguage;//汉语文
    private String rewardOrPunishment;//奖励或处分
    /** 是否随迁子女 */ 
 	private String isMigration;
    
    
    /**
     * 隐藏字段
     */
    private String stuid;		// 学生id
    private String schid; 		// 学校id
    private String enrollyear; 	// 入学学年
    private String section; 	// 学段(对未入学学生而言，是非隐藏字段)
    private String regioncode;	// 地区码
    private String oldschcode;	// 毕业学校代码
    private String schcode;		// 学校代码
    private String schname;		// 学校名称
    
    //教育局审核学校导入专用
    private String allocid;		// 招生分派id
    private String scoreid;		// 招生成绩id
    private Integer infotype;	// 同recruit_allocate表中的informal_type
    private Integer state;		// 同recruit_allocate表中的state
    
    //下面的四个字段用在student_normalflow
    private String operator;	//操作人
    private String operateunit; //操作人单位
    private String flowtype;	//流动类型
    private String acadyear;
    private String semester;
    
    //学籍状态
    private Integer leavesign;	//注意：上面的flowtype和now_state同义
    
	private String allocId;
	private String scoreId;	
	private String flowType; //异动中使用

	private String businessType;
	
	// 2014-6-30 weixh
	private String realNameF; 			//父亲姓名
    private String mobilePhoneF;		//父亲手机号
    private String relationF;
    private String isGuardianF;
    private String realNameM;			//母亲姓名
    private String mobilePhoneM;		//母亲手机号
    private String relationM;
    private String isGuardianM;
    private String realNameG;			//监护人姓名
    private String mobilePhoneG;		//监护人手机号
    
    
    
	public String getMultilingual() {
		return multilingual;
	}
	public void setMultilingual(String multilingual) {
		this.multilingual = multilingual;
	}
	public String getGradType() {
		return gradType;
	}
	public void setGradType(String gradType) {
		this.gradType = gradType;
	}
	public String getExamineeType() {
		return examineeType;
	}
	public void setExamineeType(String examineeType) {
		this.examineeType = examineeType;
	}
	public String getSpecialtyType() {
		return specialtyType;
	}
	public void setSpecialtyType(String specialtyType) {
		this.specialtyType = specialtyType;
	}
	public String getSpecialExaminee() {
		return specialExaminee;
	}
	public void setSpecialExaminee(String specialExaminee) {
		this.specialExaminee = specialExaminee;
	}
	public String getSpecialExamineeType() {
		return specialExamineeType;
	}
	public void setSpecialExamineeType(String specialExamineeType) {
		this.specialExamineeType = specialExamineeType;
	}
	public String getJoinProvince() {
		return joinProvince;
	}
	public void setJoinProvince(String joinProvince) {
		this.joinProvince = joinProvince;
	}
	public String getJoinProvinceType() {
		return joinProvinceType;
	}
	public void setJoinProvinceType(String joinProvinceType) {
		this.joinProvinceType = joinProvinceType;
	}
	public String getAnswerLanguage() {
		return answerLanguage;
	}
	public void setAnswerLanguage(String answerLanguage) {
		this.answerLanguage = answerLanguage;
	}
	public String getChineseLanguage() {
		return chineseLanguage;
	}
	public void setChineseLanguage(String chineseLanguage) {
		this.chineseLanguage = chineseLanguage;
	}
	public String getRewardOrPunishment() {
		return rewardOrPunishment;
	}
	public void setRewardOrPunishment(String rewardOrPunishment) {
		this.rewardOrPunishment = rewardOrPunishment;
	}
	public String getIsMigration() {
		return isMigration;
	}
	public void setIsMigration(String isMigration) {
		this.isMigration = isMigration;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getAcadyear() {
		return acadyear;
	}
	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}
	public Integer getLeavesign() {
		return leavesign;
	}
	public void setLeavesign(Integer leavesign) {
		this.leavesign = leavesign;
	}
	public String getAllocid() {
		return allocid;
	}
	public void setAllocid(String allocid) {
		this.allocid = allocid;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getBloodtype() {
		return bloodtype;
	}
	public void setBloodtype(String bloodtype) {
		this.bloodtype = bloodtype;
	}
	public String getBoarder() {
		return boarder;
	}
	public void setBoarder(String boarder) {
		this.boarder = boarder;
	}
	public String getBoorish() {
		return boorish;
	}
	public void setBoorish(String boorish) {
		this.boorish = boorish;
	}
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getComputerlevel() {
		return computerlevel;
	}
	public void setComputerlevel(String computerlevel) {
		this.computerlevel = computerlevel;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEconomystate() {
		return economystate;
	}
	public void setEconomystate(String economystate) {
		this.economystate = economystate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEnglishlevel() {
		return englishlevel;
	}
	public void setEnglishlevel(String englishlevel) {
		this.englishlevel = englishlevel;
	}
	public String getEnrollyear() {
		return enrollyear;
	}
	public void setEnrollyear(String enrollyear) {
		this.enrollyear = enrollyear;
	}
	public String getExamcode() {
		return examcode;
	}
	public void setExamcode(String examcode) {
		this.examcode = examcode;
	}
	public String getFamilyorigin() {
		return familyorigin;
	}
	public void setFamilyorigin(String familyorigin) {
		this.familyorigin = familyorigin;
	}
	public String getFlowingpeople() {
		return flowingpeople;
	}
	public void setFlowingpeople(String flowingpeople) {
		this.flowingpeople = flowingpeople;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public String getHomeaddress() {
		return homeaddress;
	}
	public void setHomeaddress(String homeaddress) {
		this.homeaddress = homeaddress;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getHomeplace() {
		return homeplace;
	}
	public void setHomeplace(String homeplace) {
		this.homeplace = homeplace;
	}
	public String getIdentitycard() {
		return StringUtils.trim(StringUtils.upperCase(identitycard));
	}
	public void setIdentitycard(String identitycard) {
		this.identitycard = identitycard;
	}
	
	public Integer getInfotype() {
		return infotype;
	}
	public void setInfotype(Integer infotype) {
		this.infotype = infotype;
	}
	public String getIslocalsource() {
		return islocalsource;
	}
	public void setIslocalsource(String islocalsource) {
		this.islocalsource = islocalsource;
	}
	public String getLinkaddress() {
		return linkaddress;
	}
	public void setLinkaddress(String linkaddress) {
		this.linkaddress = linkaddress;
	}
	public String getLinkphone() {
		return linkphone;
	}
	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getNativeplace() {
		return nativeplace;
	}
	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}
	public String getOldname() {
		return oldname;
	}
	public void setOldname(String oldname) {
		this.oldname = oldname;
	}
	public String getOldschcode() {
		return oldschcode;
	}
	public void setOldschcode(String oldschcode) {
		this.oldschcode = oldschcode;
	}
	public String getOldschool() {
		return oldschool;
	}
	public void setOldschool(String oldschool) {
		this.oldschool = oldschool;
	}
	public String getOperateunit() {
		return operateunit;
	}
	public void setOperateunit(String operateunit) {
		this.operateunit = operateunit;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getPolitydate() {
		return politydate;
	}
	public void setPolitydate(String politydate) {
		this.politydate = politydate;
	}
	public String getPolityintroducer() {
		return polityintroducer;
	}
	public void setPolityintroducer(String polityintroducer) {
		this.polityintroducer = polityintroducer;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getPreeduflag() {
		return preeduflag;
	}
	public void setPreeduflag(String preeduflag) {
		this.preeduflag = preeduflag;
	}
	public String getRegioncode() {
		return regioncode;
	}
	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}
	public String getRegisterinfo() {
		return registerinfo;
	}
	public void setRegisterinfo(String registerinfo) {
		this.registerinfo = registerinfo;
	}
	public String getRegisterplace() {
		return registerplace;
	}
	public void setRegisterplace(String registerplace) {
		this.registerplace = registerplace;
	}
	public String getRegistertype() {
		return registertype;
	}
	public void setRegistertype(String registertype) {
		this.registertype = registertype;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReturnedchinese() {
		return returnedchinese;
	}
	public void setReturnedchinese(String returnedchinese) {
		this.returnedchinese = returnedchinese;
	}
	public String getSchcode() {
		return schcode;
	}
	public void setSchcode(String schcode) {
		this.schcode = schcode;
	}
	public String getSchid() {
		return schid;
	}
	public void setSchid(String schid) {
		this.schid = schid;
	}
	public String getSchname() {
		return schname;
	}
	public void setSchname(String schname) {
		this.schname = schname;
	}
	public String getScoreid() {
		return scoreid;
	}
	public void setScoreid(String scoreid) {
		this.scoreid = scoreid;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSingleton() {
		return singleton;
	}
	public void setSingleton(String singleton) {
		this.singleton = singleton;
	}
	public String getSourceplace() {
		return sourceplace;
	}
	public void setSourceplace(String sourceplace) {
		this.sourceplace = sourceplace;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSpellname() {
		return spellname;
	}
	public void setSpellname(String spellname) {
		this.spellname = spellname;
	}
	public String getStayin() {
		return stayin;
	}
	public void setStayin(String stayin) {
		this.stayin = stayin;
	}
	public String getStrong() {
		return strong;
	}
	public void setStrong(String strong) {
		this.strong = strong;
	}
	public String getStucode() {
		return stucode;
	}
	public void setStucode(String stucode) {
		this.stucode = stucode;
	}
	public String getStudenttype() {
		return studenttype;
	}
	public void setStudenttype(String studenttype) {
		this.studenttype = studenttype;
	}
	public String getStuid() {
		return stuid;
	}
	public void setStuid(String stuid) {
		this.stuid = stuid;
	}
	public String getStuname() {
		return stuname;
	}
	public void setStuname(String stuname) {
		this.stuname = stuname;
	}
	public String getSubj1() {
		return subj1;
	}
	public void setSubj1(String subj1) {
		this.subj1 = subj1;
	}
	public String getSubj2() {
		return subj2;
	}
	public void setSubj2(String subj2) {
		this.subj2 = subj2;
	}
	public String getSubj3() {
		return subj3;
	}
	public void setSubj3(String subj3) {
		this.subj3 = subj3;
	}
	public String getSubj4() {
		return subj4;
	}
	public void setSubj4(String subj4) {
		this.subj4 = subj4;
	}
	public String getSubj5() {
		return subj5;
	}
	public void setSubj5(String subj5) {
		this.subj5 = subj5;
	}
	public String getSubj6() {
		return subj6;
	}
	public void setSubj6(String subj6) {
		this.subj6 = subj6;
	}
	public String getSubj7() {
		return subj7;
	}
	public void setSubj7(String subj7) {
		this.subj7 = subj7;
	}
	public String getSubj8() {
		return subj8;
	}
	public void setSubj8(String subj8) {
		this.subj8 = subj8;
	}
	public String getSubj9() {
		return subj9;
	}
	public void setSubj9(String subj9) {
		this.subj9 = subj9;
	}
	public String getToschooldate() {
		return toschooldate;
	}
	public void setToschooldate(String toschooldate) {
		this.toschooldate = toschooldate;
	}
	public String getToschooltype() {
		return toschooltype;
	}
	public void setToschooltype(String toschooltype) {
		this.toschooltype = toschooltype;
	}
	public String getUnitivecode() {
		return unitivecode;
	}
	public void setUnitivecode(String unitivecode) {
		this.unitivecode = unitivecode;
	}
	public String getZkcode() {
		return zkcode;
	}
	public void setZkcode(String zkcode) {
		this.zkcode = zkcode;
	}
	public String getZkresult() {
		return zkresult;
	}
	public void setZkresult(String zkresult) {
		this.zkresult = zkresult;
	}
	public String getFlowtype() {
		return flowtype;
	}
	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public Integer getIsSpecialID() {
		return isSpecialID;
	}
	public void setIsSpecialID(Integer isSpecialID) {
		this.isSpecialID = isSpecialID;
	}
	public String getAllocId() {
		return allocId;
	}
	public void setAllocId(String allocId) {
		this.allocId = allocId;
	}
	public String getScoreId() {
		return scoreId;
	}
	public void setScoreId(String scoreId) {
		this.scoreId = scoreId;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getRealNameF() {
		return realNameF;
	}
	public void setRealNameF(String realNameF) {
		this.realNameF = realNameF;
	}
	public String getMobilePhoneF() {
		return mobilePhoneF;
	}
	public void setMobilePhoneF(String mobilePhoneF) {
		this.mobilePhoneF = mobilePhoneF;
	}
	public String getRelationF() {
		return relationF;
	}
	public void setRelationF(String relationF) {
		this.relationF = relationF;
	}
	public String getIsGuardianF() {
		return isGuardianF;
	}
	public void setIsGuardianF(String isGuardianF) {
		this.isGuardianF = isGuardianF;
	}
	public String getRealNameM() {
		return realNameM;
	}
	public void setRealNameM(String realNameM) {
		this.realNameM = realNameM;
	}
	public String getMobilePhoneM() {
		return mobilePhoneM;
	}
	public void setMobilePhoneM(String mobilePhoneM) {
		this.mobilePhoneM = mobilePhoneM;
	}
	public String getRelationM() {
		return relationM;
	}
	public void setRelationM(String relationM) {
		this.relationM = relationM;
	}
	public String getIsGuardianM() {
		return isGuardianM;
	}
	public void setIsGuardianM(String isGuardianM) {
		this.isGuardianM = isGuardianM;
	}
	public String getRealNameG() {
		return realNameG;
	}
	public void setRealNameG(String realNameG) {
		this.realNameG = realNameG;
	}
	public String getMobilePhoneG() {
		return mobilePhoneG;
	}
	public void setMobilePhoneG(String mobilePhoneG) {
		this.mobilePhoneG = mobilePhoneG;
	}
	public String getClassorderid() {
		return classorderid;
	}
	public void setClassorderid(String classorderid) {
		this.classorderid = classorderid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIdentitycardType() {
		return identitycardType;
	}
	public void setIdentitycardType(String identitycardType) {
		this.identitycardType = identitycardType;
	}
	
}