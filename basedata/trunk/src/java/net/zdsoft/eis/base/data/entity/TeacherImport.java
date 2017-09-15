package net.zdsoft.eis.base.data.entity;

import java.util.Date;

import net.zdsoft.eis.base.common.entity.User;

/**
 * 
 * @author hexq
 * @version $Revision: 1.0 $, $Date: Oct 19, 2009 13:46:37 PM $
 */
public class TeacherImport {
	
	private String unitid;				//所属单位
	private String teachercode;			//职工编号
	private String teachername;			//职工姓名
	private String oldname;				//曾用名
	private String sex;					//性别
	private String birthday;			//出生日期
	private String identitycard;		//身份证号码
	private String deptid;				//所在部门
	private String username;			//用户名
	private String password;			//登录密码
	
	private String incumbencysign;		//在职标记
	private String workdate;			//参加工作时间
	private String tribe;               //民族
	private String duty;				//职称
	private String title;				//职务
	private String mobilephone;			//手机号码
	private String officetel;			//办公电话
	private String nativeplace;			//籍贯
	private String polity;				//政治面貌
	private String polityjoin;			//加入时间
	
	private String academicqualification;//学历
	private String major;				//所学专业
	private String graduateschool;		//毕业院校
	private String graduatetime;		//毕业时间
	private String registertype;		//户口类别
	private String registerplace;		//户口所在地
	private String linkaddress;			//联系地址
	private String homepage;			//主页地址
	private String email;				//电子邮件
	private String remark;				//备注
	
	private String chargenumber;		//扣费号码
	
	
//	隐含字段
	private String id;					//职工id			
	private String regioncode;			//单位regioncode

    private String nation;  //民族
	private String returnedChinese; //港澳台侨
	private String country; //国籍
	private String weaveType; //编制类别
	private String multiTitle; //是否是双师型
	private String teachStatus; //授课状态
	
	private String titleCode;				//职称编号
	private String titleDate;				//职称批准时间
	private String titleDept;				//职称批准部门
	private String specJobLevel;			//专业技术岗位级别 
	private String manageJobLevel;			//管理岗位级别
	
	private String appDate;					//在职人员聘任时间
	private String adminPost;				//行政职务
	private String appYear;					//在职人员聘任年限
	private String adminPostLevel;			//行政职级
    private String teachSubject;			//任教学科
    
    private String degree;					//学位
    private String firstStulive;			//第一学历
    private String highestStulive;			//最高学历
    private String highestStuliveSch;		//最高学历毕业院校
    private String highestStuliveMajor;		//最高学历所学专业
    
    private String highestStuliveDate;		//最高学历毕业时间
    private String teacherLevel;			//教师资格证级别
    private String certificateName;			//职业资格证名称
    private String approvalDept;			//批准部门
    private String certificateLevel;		//职业资格证级别
    
    private String approvalDate;			//批准时间
    private String certificateApprovalDept;	//资格证批准部门
    private String certificateApprovalDate;	//资格证批准时间
    private String contractStartDate;		//合同开始时间
    private String contractEndDate;			//合同终止时间
    
    private String firstContractStart;		//第一次合同起
    private String firstContractEnd;		//第一次合同终
    private String secondContractStart;		//第二次合同起
    private String secondContractEnd;		//第二次合同终
    private String thirdContractStart;		//第三次合同起
    
    private String thirdContractEnd;		//第三次合同终
    private String fourthContractStart;		//第四次合同起
    private String fourthContractEnd;		//第四次合同终
    private String QQ;						//QQ
    
    
    //2014-12-30 add by like 镇海需求
    private String workTeachDate; // 参加教育工作年月
    private String oldAcademicQualification;//原有学历（全日制）
    private String specTechnicalDuty;//专业技术职务
    private String specTechnicalDutyDate;//专业技术职务评审年月
    private String homeAddress;//家庭地址
    private String generalCard;//一卡通
    private String linkPhone;//联系方式
	
    //2017-08-08 add 
    private String teachGroupName; //教研组
    
	public String getLinkPhone() {
		return linkPhone;
	}


	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}


	public String getWorkTeachDate() {
		return workTeachDate;
	}


	public void setWorkTeachDate(String workTeachDate) {
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


	public String getSpecTechnicalDutyDate() {
		return specTechnicalDutyDate;
	}


	public void setSpecTechnicalDutyDate(String specTechnicalDutyDate) {
		this.specTechnicalDutyDate = specTechnicalDutyDate;
	}


	public String getHomeAddress() {
		return homeAddress;
	}


	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}


	public String getGeneralCard() {
		return generalCard;
	}


	public void setGeneralCard(String generalCard) {
		this.generalCard = generalCard;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getAcademicqualification() {
		return academicqualification;
	}


	public void setAcademicqualification(String academicqualification) {
		this.academicqualification = academicqualification;
	}


	public String getBirthday() {
		return birthday;
	}


	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public String getChargenumber() {
		return chargenumber;
	}


	public void setChargenumber(String chargenumber) {
		this.chargenumber = chargenumber;
	}


	public String getDeptid() {
		return deptid;
	}


	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}


	public String getDuty() {
		return duty;
	}


	public void setDuty(String duty) {
		this.duty = duty;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getGraduateschool() {
		return graduateschool;
	}


	public void setGraduateschool(String graduateschool) {
		this.graduateschool = graduateschool;
	}


	public String getGraduatetime() {
		return graduatetime;
	}


	public void setGraduatetime(String graduatetime) {
		this.graduatetime = graduatetime;
	}


	public String getHomepage() {
		return homepage;
	}


	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}


	public String getIdentitycard() {
		return identitycard;
	}


	public void setIdentitycard(String identitycard) {
		this.identitycard = identitycard;
	}


	public String getIncumbencysign() {
		return incumbencysign;
	}


	public void setIncumbencysign(String incumbencysign) {
		this.incumbencysign = incumbencysign;
	}


	public String getLinkaddress() {
		return linkaddress;
	}


	public void setLinkaddress(String linkaddress) {
		this.linkaddress = linkaddress;
	}


	public String getMajor() {
		return major;
	}


	public void setMajor(String major) {
		this.major = major;
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


	public String getOfficetel() {
		return officetel;
	}


	public void setOfficetel(String officetel) {
		this.officetel = officetel;
	}


	public String getOldname() {
		return oldname;
	}


	public void setOldname(String oldname) {
		this.oldname = oldname;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPolity() {
		return polity;
	}


	public void setPolity(String polity) {
		this.polity = polity;
	}


	public String getPolityjoin() {
		return polityjoin;
	}


	public void setPolityjoin(String polityjoin) {
		this.polityjoin = polityjoin;
	}


	public String getRegioncode() {
		return regioncode;
	}


	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
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


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getTeachercode() {
		return teachercode;
	}


	public void setTeachercode(String teachercode) {
		this.teachercode = teachercode;
	}


	public String getTeachername() {
		return teachername;
	}


	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getUnitid() {
		return unitid;
	}


	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}


	public String getUsername() {
	    if (User.isUsernameNotCaseSensitive()) {
            if (null != this.username) {
                this.username = this.username.toLowerCase();
            }
        }
	    
		return username;
	}


	public void setUsername(String username) {
	    if (User.isUsernameNotCaseSensitive()) {
            if (null != username) {
                username = username.toLowerCase();
            }
        }
		this.username = username;
	}


	public String getWorkdate() {
		return workdate;
	}


	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}


	public String getTribe() {
		return tribe;
	}


	public void setTribe(String tribe) {
		this.tribe = tribe;
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


	public String getTitleCode() {
		return titleCode;
	}


	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}


	public String getTitleDate() {
		return titleDate;
	}


	public void setTitleDate(String titleDate) {
		this.titleDate = titleDate;
	}


	public String getTitleDept() {
		return titleDept;
	}


	public void setTitleDept(String titleDept) {
		this.titleDept = titleDept;
	}


	public String getSpecJobLevel() {
		return specJobLevel;
	}


	public void setSpecJobLevel(String specJobLevel) {
		this.specJobLevel = specJobLevel;
	}


	public String getManageJobLevel() {
		return manageJobLevel;
	}


	public void setManageJobLevel(String manageJobLevel) {
		this.manageJobLevel = manageJobLevel;
	}


	public String getAppDate() {
		return appDate;
	}


	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}


	public String getAdminPost() {
		return adminPost;
	}


	public void setAdminPost(String adminPost) {
		this.adminPost = adminPost;
	}


	public String getAppYear() {
		return appYear;
	}


	public void setAppYear(String appYear) {
		this.appYear = appYear;
	}


	public String getAdminPostLevel() {
		return adminPostLevel;
	}


	public void setAdminPostLevel(String adminPostLevel) {
		this.adminPostLevel = adminPostLevel;
	}


	public String getTeachSubject() {
		return teachSubject;
	}


	public void setTeachSubject(String teachSubject) {
		this.teachSubject = teachSubject;
	}


	public String getDegree() {
		return degree;
	}


	public void setDegree(String degree) {
		this.degree = degree;
	}


	public String getFirstStulive() {
		return firstStulive;
	}


	public void setFirstStulive(String firstStulive) {
		this.firstStulive = firstStulive;
	}


	public String getHighestStulive() {
		return highestStulive;
	}


	public void setHighestStulive(String highestStulive) {
		this.highestStulive = highestStulive;
	}


	public String getHighestStuliveSch() {
		return highestStuliveSch;
	}


	public void setHighestStuliveSch(String highestStuliveSch) {
		this.highestStuliveSch = highestStuliveSch;
	}


	public String getHighestStuliveMajor() {
		return highestStuliveMajor;
	}


	public void setHighestStuliveMajor(String highestStuliveMajor) {
		this.highestStuliveMajor = highestStuliveMajor;
	}


	public String getHighestStuliveDate() {
		return highestStuliveDate;
	}


	public void setHighestStuliveDate(String highestStuliveDate) {
		this.highestStuliveDate = highestStuliveDate;
	}


	public String getTeacherLevel() {
		return teacherLevel;
	}


	public void setTeacherLevel(String teacherLevel) {
		this.teacherLevel = teacherLevel;
	}


	public String getCertificateName() {
		return certificateName;
	}


	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}


	public String getApprovalDept() {
		return approvalDept;
	}


	public void setApprovalDept(String approvalDept) {
		this.approvalDept = approvalDept;
	}


	public String getCertificateLevel() {
		return certificateLevel;
	}


	public void setCertificateLevel(String certificateLevel) {
		this.certificateLevel = certificateLevel;
	}


	public String getApprovalDate() {
		return approvalDate;
	}


	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}


	public String getCertificateApprovalDept() {
		return certificateApprovalDept;
	}


	public void setCertificateApprovalDept(String certificateApprovalDept) {
		this.certificateApprovalDept = certificateApprovalDept;
	}


	public String getCertificateApprovalDate() {
		return certificateApprovalDate;
	}


	public void setCertificateApprovalDate(String certificateApprovalDate) {
		this.certificateApprovalDate = certificateApprovalDate;
	}


	public String getContractStartDate() {
		return contractStartDate;
	}


	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}


	public String getContractEndDate() {
		return contractEndDate;
	}


	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}


	public String getFirstContractStart() {
		return firstContractStart;
	}


	public void setFirstContractStart(String firstContractStart) {
		this.firstContractStart = firstContractStart;
	}


	public String getFirstContractEnd() {
		return firstContractEnd;
	}


	public void setFirstContractEnd(String firstContractEnd) {
		this.firstContractEnd = firstContractEnd;
	}


	public String getSecondContractStart() {
		return secondContractStart;
	}


	public void setSecondContractStart(String secondContractStart) {
		this.secondContractStart = secondContractStart;
	}


	public String getSecondContractEnd() {
		return secondContractEnd;
	}


	public void setSecondContractEnd(String secondContractEnd) {
		this.secondContractEnd = secondContractEnd;
	}


	public String getThirdContractStart() {
		return thirdContractStart;
	}


	public void setThirdContractStart(String thirdContractStart) {
		this.thirdContractStart = thirdContractStart;
	}


	public String getThirdContractEnd() {
		return thirdContractEnd;
	}


	public void setThirdContractEnd(String thirdContractEnd) {
		this.thirdContractEnd = thirdContractEnd;
	}


	public String getFourthContractStart() {
		return fourthContractStart;
	}


	public void setFourthContractStart(String fourthContractStart) {
		this.fourthContractStart = fourthContractStart;
	}


	public String getFourthContractEnd() {
		return fourthContractEnd;
	}


	public void setFourthContractEnd(String fourthContractEnd) {
		this.fourthContractEnd = fourthContractEnd;
	}


	public String getQQ() {
		return QQ;
	}


	public void setQQ(String qQ) {
		QQ = qQ;
	}


	public String getTeachGroupName() {
		return teachGroupName;
	}


	public void setTeachGroupName(String teachGroupName) {
		this.teachGroupName = teachGroupName;
	}
	
	
}
