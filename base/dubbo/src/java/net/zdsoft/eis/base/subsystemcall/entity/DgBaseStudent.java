package net.zdsoft.eis.base.subsystemcall.entity;

import java.util.Date;

import net.zdsoft.eis.base.common.entity.Student;

/**
 * 
 * @author weixh
 * @since 2015-6-2
 */
@SuppressWarnings("serial")
public class DgBaseStudent extends Student {
//	private String schId; // 学校id
//    private String classId; // 班级id
//    private int leavesign; // 离校在校标志
//    private String nowState; // 当前状态
//    private String enrollYear; // 新生入学学年
//    
//    private String stuName; // 姓名
//    private int sex; // 性别
//    private Date birthday; // 出生日期
//    private String homeplace; // 出生地
//    private String nativeplace; // 籍贯
//    private String nation; // 民族
//    private String country; // 国籍
//    private String identityCardType; // 身份证件类型 
//    private String identityCard; // 身份证号码
//    private int compatriots; // 港澳台侨
//    private String background; // 政治面貌
//    private String health; // 健康状况
//   
//    private String spellname; // 姓名拼音
//    private String oldname; // 曾用名
//    private String identityCardValid; // 身份证件有效期
//    private String registerplace; // 户口所在地
//    private int registertype; // 户口类别/性质
//	  private int urbanRegisterType; // 非农业户口性质
//    private String strong; // 特长
//    
//    private String stuCode; // 学号
//    private String unitiveCode; // 学籍号，东莞学号
//    private String classoOrderId; // 学生班内编号
//    private String pin;// 国家学籍号
//    private int toSchoolType; // 入学方式
//    private Date toSchoolDate; // 入学时间，入园时间
//    private int boarder; // 就读方式
//    private int sourceType; // 生源类别，学生来源
//    
//    private String nowAddress; // 现住址
//    private String homeAddress; // 家庭地址
//    private String linkAddress; // 联系地址，通信地址
//    private String linkPhone; // 联系电话
//    private String postalCode; // 邮政编码
//    private String email; // 电子邮件
//    private String homePage; // 主页地址
//    
//    private int singleton = 1; // 是否独生子女
//    private int preeduflag; // 是否受过学前教育
//    private int stayin; // 是否留守儿童
//    private String isMigration; // 是否随迁子女
//    private int isOrphan; // 是否孤儿
//    private int isMartyrChild; // 是否烈士或优抚子女
//    private int regularClass; // 随班就读
//    private String disabilityType; // 残疾类型
//    private int isGovernmentBear; // 是否由政府购买学位
//    private int isNeedAssistance; // 是否需要申请资助
//    private int isEnjoyAssistance; // 是否享受一补
//    
//    private String distance; // 上下学距离
//    private String trafficWay; // 上下学交通方式
//    private String isNeedBus; // 是否乘坐校车
//    
//    private String seatNumber; // 座号
    private Integer nowChildNum;//--现有子女数
    private Integer birthRank;//--出生排行
    private String studentClassify;//学生分类 微代码：XSFL
    private String isNewGrads;//是否应届生
    // extend
    private String className;// 班级名称
    private String gradeName;// 年级名称
    private String nativeplaceName;//籍贯名称
    private String homePlaceName;
    private String registerPlaceName;
    private String toSchoolDateStr;
    private Date identitycardValid1;
    private Date identitycardValid2;
    private String flowtype;//异动类型
    private String flowtypeName;
    private boolean isAutoUnitiveCode;
    private String schoolName;
    private String classAcadyear;//班级开班学年
    private boolean isTeacherEditable;//当前用户是否能编辑该学生
    
    private String enrolWay;//入读方式 DM-DGRDFS
    private String isDgregister;//近期是否有加入东莞户籍意愿DM-JRDGFJYY
    private String yearDgregister;//计划几年加入东莞户籍DM-JNLJRDGFJ
    private String dgregisterAim;//加入东莞户籍目的 DM-DGRHMD
    
    

	public Integer getNowChildNum() {
		return nowChildNum;
	}

	public void setNowChildNum(Integer nowChildNum) {
		this.nowChildNum = nowChildNum;
	}

	public Integer getBirthRank() {
		return birthRank;
	}

	public void setBirthRank(Integer birthRank) {
		this.birthRank = birthRank;
	}

	public boolean isAutoUnitiveCode() {
		return isAutoUnitiveCode;
	}

	public void setAutoUnitiveCode(boolean isAutoUnitiveCode) {
		this.isAutoUnitiveCode = isAutoUnitiveCode;
	}

	public Date getIdentitycardValid1() {
		return identitycardValid1;
	}

	public void setIdentitycardValid1(Date identitycardValid1) {
		this.identitycardValid1 = identitycardValid1;
	}

	public Date getIdentitycardValid2() {
		return identitycardValid2;
	}

	public void setIdentitycardValid2(Date identitycardValid2) {
		this.identitycardValid2 = identitycardValid2;
	}

	public String getToSchoolDateStr() {
		return toSchoolDateStr;
	}

	public void setToSchoolDateStr(String toSchoolDateStr) {
		this.toSchoolDateStr = toSchoolDateStr;
	}

	public String getRegisterPlaceName() {
		return registerPlaceName;
	}

	public void setRegisterPlaceName(String registerPlaceName) {
		this.registerPlaceName = registerPlaceName;
	}

	public String getHomePlaceName() {
		return homePlaceName;
	}

	public void setHomePlaceName(String homePlaceName) {
		this.homePlaceName = homePlaceName;
	}

	public String getNativeplaceName() {
		return nativeplaceName;
	}

	public void setNativeplaceName(String nativeplaceName) {
		this.nativeplaceName = nativeplaceName;
	}

	public String getClassName() {
		return className;
	}
	
    public void setClassName(String className) {
		this.className = className;
	}
	
    public String getGradeName() {
		return gradeName;
	}
	
    public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getFlowtype() {
		return flowtype;
	}

	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype;
	}

	public String getFlowtypeName() {
		return flowtypeName;
	}

	public void setFlowtypeName(String flowtypeName) {
		this.flowtypeName = flowtypeName;
	}

	/**
	 * 获取schoolName
	 * @return schoolName
	 */
	public String getSchoolName() {
	    return schoolName;
	}

	/**
	 * 设置schoolName
	 * @param schoolName schoolName
	 */
	public void setSchoolName(String schoolName) {
	    this.schoolName = schoolName;
	}

	/**
	 * 获取classAcadyear
	 * @return classAcadyear
	 */
	public String getClassAcadyear() {
	    return classAcadyear;
	}

	/**
	 * 设置classAcadyear
	 * @param classAcadyear classAcadyear
	 */
	public void setClassAcadyear(String classAcadyear) {
	    this.classAcadyear = classAcadyear;
	}

	/**
	 * 获取isTeacherEditable
	 * @return isTeacherEditable
	 */
	public boolean isTeacherEditable() {
	    return isTeacherEditable;
	}

	/**
	 * 设置isTeacherEditable
	 * @param isTeacherEditable isTeacherEditable
	 */
	public void setTeacherEditable(boolean isTeacherEditable) {
	    this.isTeacherEditable = isTeacherEditable;
	}

	public String getStudentClassify() {
		return studentClassify;
	}

	public void setStudentClassify(String studentClassify) {
		this.studentClassify = studentClassify;
	}

	public String getIsNewGrads() {
		return isNewGrads;
	}

	public void setIsNewGrads(String isNewGrads) {
		this.isNewGrads = isNewGrads;
	}

	public String getEnrolWay() {
		return enrolWay;
	}

	public void setEnrolWay(String enrolWay) {
		this.enrolWay = enrolWay;
	}

	public String getIsDgregister() {
		return isDgregister;
	}

	public void setIsDgregister(String isDgregister) {
		this.isDgregister = isDgregister;
	}

	public String getYearDgregister() {
		return yearDgregister;
	}

	public void setYearDgregister(String yearDgregister) {
		this.yearDgregister = yearDgregister;
	}

	public String getDgregisterAim() {
		return dgregisterAim;
	}

	public void setDgregisterAim(String dgregisterAim) {
		this.dgregisterAim = dgregisterAim;
	}

}
