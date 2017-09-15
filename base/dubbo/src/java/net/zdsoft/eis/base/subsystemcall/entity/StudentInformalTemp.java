package net.zdsoft.eis.base.subsystemcall.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.photo.PhotoEntity;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
/**
 * STUDENT_INFORMAL_TEMP
 * @author 
 * 
 */
public class StudentInformalTemp extends PhotoEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	private String schid;
	/**
	 * 
	 */
	private String classid;
	/**
	 * 
	 */
	private String gradeId;
	/**
	 * 
	 */
	private String stuname;
	/**
	 * 
	 */
	private String oldname;
	/**
	 * 
	 */
	private Integer sex;
	/**
	 * 
	 */
	private Date birthday;
	/**
	 * 
	 */
	private String homeplace;
	/**
	 * 
	 */
	private String nativePlace;
	/**
	 * 
	 */
	private String nation;
	/**
	 * 
	 */
	private String country;
	/**
	 * 
	 */
	private String spellname;
	/**
	 * 
	 */
	private String identitycardValid;
	/**
	 * 
	 */
	private String stucode;
	/**
	 * 
	 */
	private String classInnerCode;
	/**
	 * 
	 */
	private String identitycardType;
	/**
	 * 
	 */
	private String identityCard;
	/**
	 * 
	 */
	private Integer compatriots;
	/**
	 * 
	 */
	private String background;
	/**
	 * 
	 */
	private String health;
//	/**
//	 * 
//	 */
//	private String dirId;
//	/**
//	 * 
//	 */
//	private String filePath;
	/**
	 * 全国学籍号
	 */
	private String unitiveCode;
	/**
	 * 
	 */
	private String registerPlace;
	/**
	 * 
	 */
	private String nativeType;
	/**
	 * 
	 */
	private String urbanRegisterType;
	/**
	 * 
	 */
	private String strong;
	/**
	 * 
	 */
	private Integer freshmanType;
	/**
	 * 
	 */
	private String toSchoolYm;
	/**
	 * 
	 */
	private Date toSchoolDate;
	/**
	 * 
	 */
	private String toschooltype;
	/**
	 * 
	 */
	private Integer studyMode;
	/**
	 * 
	 */
	private String source;
	/**
	 * 
	 */
	private String nowaddress;
	/**
	 * 
	 */
	private String linkAddress;
	/**
	 * 
	 */
	private String homeAddress;
	/**
	 * 
	 */
	private String linkPhone;
	/**
	 * 
	 */
	private String postalcode;
	/**
	 * 
	 */
	private String email;
	/**
	 * 
	 */
	private String homepage;
	/**
	 * 
	 */
	private int isSingleton;
	/**
	 * 
	 */
	private int isPreedu;
	/**
	 * 
	 */
	private Integer stayin;
	/**
	 * 
	 */
	private String isMigration;
	/**
	 * 
	 */
	private int isOrphan;
	/**
	 * 
	 */
	private int isMartyrChild;
	/**
	 * 
	 */
	private String regularClass;
	/**
	 * 
	 */
	private String disabilityType;
	/**
	 * 
	 */
	private int isGovernmentBear;
	/**
	 * 
	 */
	private int isNeedAssistance;
	/**
	 * 
	 */
	private int isEnjoyAssistance;
	/**
	 * 
	 */
	private String distance;
	/**
	 * 
	 */
	private String trafficWay;
	/**
	 * 
	 */
	private int isNeedBus;
	/**
	 * 
	 */
	private String addType;
	/**
	 * 
	 */
	private String auditStatus;
	/**
	 * 
	 */
	private String jobId;
	/**
	 * 
	 */
	private String resultInfo;
	/**
	 * 
	 */
	private Date auditSchDate;
	/**
	 * 
	 */
	private Date auditEduDate;

	/**
	 * 
	 */
	private String businessType;
	
	private String mobilePhone;
	/**
	 * 入读方式
	 */
	private String enrolWay;
	/**
	 * 近期是否有加入东莞户籍意愿
	 */
	private String isDgregister;  
	/**
	 * 计划几年加入东莞户籍
	 */
	private String yearDgregister; 
	/**
	 * 加入东莞户籍目的
	 */
	private String dgregisterAim  ; 
	
	/**
	 * 幼儿园增加字段
	 */
	private String bloodType;//血型
	private Integer nowChildNum;//--现有子女数
    private Integer birthRank;//--出生排行
    
	// 年级名称
	private String gradeName;
	
	private String nativeplaceName;
	
	private String registerPlaceName;
	
	private String homePlaceName;
	
	private Date identitycardValid1;
	
	private Date identitycardValid2;
	
	private String className;
	
	private String schoolName;
	
	private List<StudentInformalFamilyTemp> familyTempList;
	
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	/**
	 * 设置
	 */
	public void setSchid(String schid){
		this.schid = schid;
	}
	/**
	 * 获取
	 */
	public String getSchid(){
		return this.schid;
	}
	/**
	 * 设置
	 */
	public void setClassid(String classid){
		this.classid = classid;
	}
	/**
	 * 获取
	 */
	public String getClassid(){
		return this.classid;
	}
	/**
	 * 设置
	 */
	public void setGradeId(String gradeId){
		this.gradeId = gradeId;
	}
	/**
	 * 获取
	 */
	public String getGradeId(){
		return this.gradeId;
	}
	/**
	 * 设置
	 */
	public void setStuname(String stuname){
		this.stuname = stuname;
	}
	/**
	 * 获取
	 */
	public String getStuname(){
		return this.stuname;
	}
	/**
	 * 设置
	 */
	public void setOldname(String oldname){
		this.oldname = oldname;
	}
	/**
	 * 获取
	 */
	public String getOldname(){
		return this.oldname;
	}
	/**
	 * 设置
	 */
	public void setSex(Integer sex){
		this.sex = sex;
	}
	/**
	 * 获取
	 */
	public Integer getSex(){
		return this.sex;
	}
	/**
	 * 设置
	 */
	public void setBirthday(Date birthday){
		this.birthday = birthday;
	}
	/**
	 * 获取
	 */
	public Date getBirthday(){
		return this.birthday;
	}
	/**
	 * 设置
	 */
	public void setHomeplace(String homeplace){
		this.homeplace = homeplace;
	}
	/**
	 * 获取
	 */
	public String getHomeplace(){
		return this.homeplace;
	}
	/**
	 * 设置
	 */
	public void setNativePlace(String nativePlace){
		this.nativePlace = nativePlace;
	}
	/**
	 * 获取
	 */
	public String getNativePlace(){
		return this.nativePlace;
	}
	/**
	 * 设置
	 */
	public void setNation(String nation){
		this.nation = nation;
	}
	/**
	 * 获取
	 */
	public String getNation(){
		return this.nation;
	}
	/**
	 * 设置
	 */
	public void setCountry(String country){
		this.country = country;
	}
	/**
	 * 获取
	 */
	public String getCountry(){
		return this.country;
	}
	/**
	 * 设置
	 */
	public void setSpellname(String spellname){
		this.spellname = spellname;
	}
	/**
	 * 获取
	 */
	public String getSpellname(){
		return this.spellname;
	}
	/**
	 * 设置
	 */
	public void setIdentitycardValid(String identitycardValid){
		this.identitycardValid = identitycardValid;
	}
	/**
	 * 获取
	 */
	public String getIdentitycardValid(){
		return this.identitycardValid;
	}
	/**
	 * 设置
	 */
	public void setStucode(String stucode){
		this.stucode = stucode;
	}
	/**
	 * 获取
	 */
	public String getStucode(){
		return this.stucode;
	}
	/**
	 * 设置
	 */
	public void setClassInnerCode(String classInnerCode){
		this.classInnerCode = classInnerCode;
	}
	/**
	 * 获取
	 */
	public String getClassInnerCode(){
		return this.classInnerCode;
	}
	/**
	 * 设置
	 */
	public void setIdentitycardType(String identitycardType){
		this.identitycardType = identitycardType;
	}
	/**
	 * 获取
	 */
	public String getIdentitycardType(){
		return this.identitycardType;
	}
	/**
	 * 设置
	 */
	public void setIdentityCard(String identityCard){
		this.identityCard = identityCard;
	}
	/**
	 * 获取
	 */
	public String getIdentityCard(){
		return this.identityCard;
	}
	/**
	 * 设置
	 */
	public void setCompatriots(Integer compatriots){
		this.compatriots = compatriots;
	}
	/**
	 * 获取
	 */
	public Integer getCompatriots(){
		return this.compatriots;
	}
	/**
	 * 设置
	 */
	public void setBackground(String background){
		this.background = background;
	}
	/**
	 * 获取
	 */
	public String getBackground(){
		return this.background;
	}
	/**
	 * 设置
	 */
	public void setHealth(String health){
		this.health = health;
	}
	/**
	 * 获取
	 */
	public String getHealth(){
		return this.health;
	}
//	/**
//	 * 设置
//	 */
//	public void setDirId(String dirId){
//		this.dirId = dirId;
//	}
//	/**
//	 * 获取
//	 */
//	public String getDirId(){
//		return this.dirId;
//	}
//	/**
//	 * 设置
//	 */
//	public void setFilePath(String filePath){
//		this.filePath = filePath;
//	}
//	/**
//	 * 获取
//	 */
//	public String getFilePath(){
//		return this.filePath;
//	}
	/**
	 * 设置
	 */
	public void setUnitiveCode(String unitiveCode){
		this.unitiveCode = unitiveCode;
	}
	/**
	 * 获取
	 */
	public String getUnitiveCode(){
		return this.unitiveCode;
	}
	/**
	 * 设置
	 */
	public void setRegisterPlace(String registerPlace){
		this.registerPlace = registerPlace;
	}
	/**
	 * 获取
	 */
	public String getRegisterPlace(){
		return this.registerPlace;
	}
	/**
	 * 设置
	 */
	public void setNativeType(String nativeType){
		this.nativeType = nativeType;
	}
	/**
	 * 获取
	 */
	public String getNativeType(){
		return this.nativeType;
	}
	/**
	 * 设置
	 */
	public void setUrbanRegisterType(String urbanRegisterType){
		this.urbanRegisterType = urbanRegisterType;
	}
	/**
	 * 获取
	 */
	public String getUrbanRegisterType(){
		return this.urbanRegisterType;
	}
	/**
	 * 设置
	 */
	public void setStrong(String strong){
		this.strong = strong;
	}
	/**
	 * 获取
	 */
	public String getStrong(){
		return this.strong;
	}
	/**
	 * 设置
	 */
	public void setFreshmanType(Integer freshmanType){
		this.freshmanType = freshmanType;
	}
	/**
	 * 获取
	 */
	public Integer getFreshmanType(){
		return this.freshmanType;
	}
	/**
	 * 设置
	 */
	public void setToSchoolYm(String toSchoolYm){
		this.toSchoolYm = toSchoolYm;
	}
	/**
	 * 获取
	 */
	public String getToSchoolYm(){
		return this.toSchoolYm;
	}
	/**
	 * 设置
	 */
	public void setToSchoolDate(Date toSchoolDate){
		this.toSchoolDate = toSchoolDate;
	}
	/**
	 * 获取
	 */
	public Date getToSchoolDate(){
		return this.toSchoolDate;
	}
	/**
	 * 设置
	 */
	public void setToschooltype(String toschooltype){
		this.toschooltype = toschooltype;
	}
	/**
	 * 获取
	 */
	public String getToschooltype(){
		return this.toschooltype;
	}
	/**
	 * 设置
	 */
	public void setStudyMode(Integer studyMode){
		this.studyMode = studyMode;
	}
	/**
	 * 获取
	 */
	public Integer getStudyMode(){
		return this.studyMode;
	}
	/**
	 * 设置
	 */
	public void setSource(String source){
		this.source = source;
	}
	/**
	 * 获取
	 */
	public String getSource(){
		return this.source;
	}
	/**
	 * 设置
	 */
	public void setNowaddress(String nowaddress){
		this.nowaddress = nowaddress;
	}
	/**
	 * 获取
	 */
	public String getNowaddress(){
		return this.nowaddress;
	}
	/**
	 * 设置
	 */
	public void setLinkAddress(String linkAddress){
		this.linkAddress = linkAddress;
	}
	/**
	 * 获取
	 */
	public String getLinkAddress(){
		return this.linkAddress;
	}
	/**
	 * 设置
	 */
	public void setHomeAddress(String homeAddress){
		this.homeAddress = homeAddress;
	}
	/**
	 * 获取
	 */
	public String getHomeAddress(){
		return this.homeAddress;
	}
	/**
	 * 设置
	 */
	public void setLinkPhone(String linkPhone){
		this.linkPhone = linkPhone;
	}
	/**
	 * 获取
	 */
	public String getLinkPhone(){
		return this.linkPhone;
	}
	/**
	 * 设置
	 */
	public void setPostalcode(String postalcode){
		this.postalcode = postalcode;
	}
	/**
	 * 获取
	 */
	public String getPostalcode(){
		return this.postalcode;
	}
	/**
	 * 设置
	 */
	public void setEmail(String email){
		this.email = email;
	}
	/**
	 * 获取
	 */
	public String getEmail(){
		return this.email;
	}
	/**
	 * 设置
	 */
	public void setHomepage(String homepage){
		this.homepage = homepage;
	}
	/**
	 * 获取
	 */
	public String getHomepage(){
		return this.homepage;
	}
	/**
	 * 设置
	 */
	public void setIsSingleton(int isSingleton){
		this.isSingleton = isSingleton;
	}
	/**
	 * 获取
	 */
	public int getIsSingleton(){
		return this.isSingleton;
	}
	/**
	 * 设置
	 */
	public void setIsPreedu(int isPreedu){
		this.isPreedu = isPreedu;
	}
	/**
	 * 获取
	 */
	public int getIsPreedu(){
		return this.isPreedu;
	}
	/**
	 * 设置
	 */
	public void setStayin(Integer stayin){
		this.stayin = stayin;
	}
	/**
	 * 获取
	 */
	public Integer getStayin(){
		return this.stayin;
	}
	/**
	 * 设置
	 */
	/*public void setIsMigration(int isMigration){
		this.isMigration = isMigration;
	}*/
	/**
	 * 获取
	 */
	/*public int getIsMigration(){
		return this.isMigration;
	}*/
	
	public String getIsMigration() {
		return isMigration;
	}
	public void setIsMigration(String isMigration) {
		this.isMigration = isMigration;
	}
	
	/**
	 * 设置
	 */
	public void setIsOrphan(int isOrphan){
		this.isOrphan = isOrphan;
	}
	/**
	 * 获取
	 */
	public int getIsOrphan(){
		return this.isOrphan;
	}
	/**
	 * 设置
	 */
	public void setIsMartyrChild(int isMartyrChild){
		this.isMartyrChild = isMartyrChild;
	}
	/**
	 * 获取
	 */
	public int getIsMartyrChild(){
		return this.isMartyrChild;
	}
	/**
	 * 设置
	 */
	public void setRegularClass(String regularClass){
		this.regularClass = regularClass;
	}
	/**
	 * 获取
	 */
	public String getRegularClass(){
		return this.regularClass;
	}
	/**
	 * 设置
	 */
	public void setDisabilityType(String disabilityType){
		this.disabilityType = disabilityType;
	}
	/**
	 * 获取
	 */
	public String getDisabilityType(){
		return this.disabilityType;
	}
	/**
	 * 设置
	 */
	public void setIsGovernmentBear(int isGovernmentBear){
		this.isGovernmentBear = isGovernmentBear;
	}
	/**
	 * 获取
	 */
	public int getIsGovernmentBear(){
		return this.isGovernmentBear;
	}
	/**
	 * 设置
	 */
	public void setIsNeedAssistance(int isNeedAssistance){
		this.isNeedAssistance = isNeedAssistance;
	}
	/**
	 * 获取
	 */
	public int getIsNeedAssistance(){
		return this.isNeedAssistance;
	}
	/**
	 * 设置
	 */
	public void setIsEnjoyAssistance(int isEnjoyAssistance){
		this.isEnjoyAssistance = isEnjoyAssistance;
	}
	/**
	 * 获取
	 */
	public int getIsEnjoyAssistance(){
		return this.isEnjoyAssistance;
	}
	/**
	 * 设置
	 */
	public void setDistance(String distance){
		this.distance = distance;
	}
	/**
	 * 获取
	 */
	public String getDistance(){
		return this.distance;
	}
	/**
	 * 设置
	 */
	public void setTrafficWay(String trafficWay){
		this.trafficWay = trafficWay;
	}
	/**
	 * 获取
	 */
	public String getTrafficWay(){
		return this.trafficWay;
	}
	/**
	 * 设置
	 */
	public void setIsNeedBus(int isNeedBus){
		this.isNeedBus = isNeedBus;
	}
	/**
	 * 获取
	 */
	public int getIsNeedBus(){
		return this.isNeedBus;
	}
	/**
	 * 设置
	 */
	public void setAddType(String addType){
		this.addType = addType;
	}
	/**
	 * 获取
	 */
	public String getAddType(){
		return this.addType;
	}
	/**
	 * 设置
	 */
	public void setAuditStatus(String auditStatus){
		this.auditStatus = auditStatus;
	}
	/**
	 * 获取
	 */
	public String getAuditStatus(){
		return this.auditStatus;
	}
	/**
	 * 设置
	 */
	public void setJobId(String jobId){
		this.jobId = jobId;
	}
	/**
	 * 获取
	 */
	public String getJobId(){
		return this.jobId;
	}
	/**
	 * 设置
	 */
	public void setResultInfo(String resultInfo){
		this.resultInfo = resultInfo;
	}
	/**
	 * 获取
	 */
	public String getResultInfo(){
		return this.resultInfo;
	}
	/**
	 * 设置
	 */
	public void setAuditSchDate(Date auditSchDate){
		this.auditSchDate = auditSchDate;
	}
	/**
	 * 获取
	 */
	public Date getAuditSchDate(){
		return this.auditSchDate;
	}
	/**
	 * 设置
	 */
	public void setAuditEduDate(Date auditEduDate){
		this.auditEduDate = auditEduDate;
	}
	/**
	 * 获取
	 */
	public Date getAuditEduDate(){
		return this.auditEduDate;
	}
	
	public String getBusinessType() {
		return businessType;
	}
	
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	/**
	 * 获取gradeName
	 * @return gradeName
	 */
	public String getGradeName() {
	    return gradeName;
	}
	/**
	 * 设置gradeName
	 * @param gradeName gradeName
	 */
	public void setGradeName(String gradeName) {
	    this.gradeName = gradeName;
	}
	/**
	 * 获取nativeplaceName
	 * @return nativeplaceName
	 */
	public String getNativeplaceName() {
	    return nativeplaceName;
	}
	/**
	 * 设置nativeplaceName
	 * @param nativeplaceName nativeplaceName
	 */
	public void setNativeplaceName(String nativeplaceName) {
	    this.nativeplaceName = nativeplaceName;
	}
	/**
	 * 获取registerPlaceName
	 * @return registerPlaceName
	 */
	public String getRegisterPlaceName() {
	    return registerPlaceName;
	}
	/**
	 * 设置registerPlaceName
	 * @param registerPlaceName registerPlaceName
	 */
	public void setRegisterPlaceName(String registerPlaceName) {
	    this.registerPlaceName = registerPlaceName;
	}
	/**
	 * 获取homePlaceName
	 * @return homePlaceName
	 */
	public String getHomePlaceName() {
	    return homePlaceName;
	}
	/**
	 * 设置homePlaceName
	 * @param homePlaceName homePlaceName
	 */
	public void setHomePlaceName(String homePlaceName) {
	    this.homePlaceName = homePlaceName;
	}
	/**
	 * 获取identitycardValid1
	 * @return identitycardValid1
	 */
	public Date getIdentitycardValid1() {
	    return identitycardValid1;
	}
	/**
	 * 设置identitycardValid1
	 * @param identitycardValid1 identitycardValid1
	 */
	public void setIdentitycardValid1(Date identitycardValid1) {
	    this.identitycardValid1 = identitycardValid1;
	}
	/**
	 * 获取identitycardValid2
	 * @return identitycardValid2
	 */
	public Date getIdentitycardValid2() {
	    return identitycardValid2;
	}
	/**
	 * 设置identitycardValid2
	 * @param identitycardValid2 identitycardValid2
	 */
	public void setIdentitycardValid2(Date identitycardValid2) {
	    this.identitycardValid2 = identitycardValid2;
	}
	
	@Override
	public String getObjectType() {
		return "student_temp";
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * 获取familyTempList
	 * @return familyTempList
	 */
	public List<StudentInformalFamilyTemp> getFamilyTempList() {
	    return familyTempList;
	}
	/**
	 * 设置familyTempList
	 * @param familyTempList familyTempList
	 */
	public void setFamilyTempList(List<StudentInformalFamilyTemp> familyTempList) {
	    this.familyTempList = familyTempList;
	}
	
	
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getBloodType() {
		return bloodType;
	}
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
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
	public DgBaseStudent toDgBaseStudent(DgBaseStudent dgstu){
		if(dgstu==null)
			dgstu=new DgBaseStudent();
		
		dgstu.setId(this.getId());
		dgstu.setSchid(this.getSchid());
		if(StringUtils.isBlank(dgstu.getClassid())){
			if(StringUtils.isBlank(this.getClassid())||BaseConstant.ZERO_GUID.equals(this.getClassid())){
				dgstu.setClassid(BaseConstant.ZERO_GUID);
				dgstu.setIsFreshman(this.getFreshmanType());
			} else {//如果classid 已存在说明则不需要分班
				dgstu.setClassid(this.getClassid());
				dgstu.setIsFreshman(9);
			}
		}else{//外网做了特殊标志-1处理所以这里需要调下
			if(dgstu.getIsFreshman()==-1){
				dgstu.setIsFreshman(9);
			}
		}
		dgstu.setStuname(this.getStuname());
		dgstu.setOldName(this.getOldname());
		dgstu.setSex(this.getSex());
		dgstu.setBirthday(this.getBirthday());
		dgstu.setHomePlace(this.getHomeplace());
		dgstu.setIdentitycard(this.getIdentityCard());
		dgstu.setSex(this.getSex());
		dgstu.setBirthday(this.getBirthday());
		if(dgstu.getLeavesign()==null){
			dgstu.setLeavesign(0);
		}
		if(StringUtils.isBlank(dgstu.getEnrollYear())){
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			dgstu.setEnrollYear(YearsToAcadyear(format.format(this.getToSchoolDate())));
		}
		dgstu.setIsSingleton(this.getIsSingleton());
		dgstu.setStayin(this.getStayin());
		dgstu.setStudyMode(this.getStudyMode());
		dgstu.setNativePlace(this.getNativePlace());
		dgstu.setHomepage(this.getHomepage());
		dgstu.setEmail(this.getEmail());
		dgstu.setLinkPhone(this.getLinkPhone());
		dgstu.setMobilePhone(this.getMobilePhone());
		dgstu.setLinkAddress(this.getLinkAddress());
		dgstu.setPostalcode(this.getPostalcode());
		dgstu.setCreationTime(this.getCreationTime());
		dgstu.setModifyTime(this.getModifyTime());
		dgstu.setIsdeleted(this.getIsdeleted());
		dgstu.setToschooltype(this.getToschooltype());
		dgstu.setToSchoolDate(this.getToSchoolDate());
		dgstu.setIsPreedu(String.valueOf(this.getIsPreedu()));
		dgstu.setStrong(this.getStrong());
		dgstu.setDirId(this.getDirId());
		dgstu.setFilePath(this.getFilePath());
		dgstu.setIdentitycardType(this.getIdentitycardType());
		dgstu.setSpellName(this.getSpellname());
		dgstu.setHealth(this.getHealth());
		dgstu.setCountry(this.getCountry());
		dgstu.setNation(this.getNation());
		dgstu.setBackground(this.getBackground());
		dgstu.setHomeAddress(this.getHomeAddress());
		dgstu.setRegisterPlace(this.getRegisterPlace());
		dgstu.setDisabilityType(this.getDisabilityType());
		dgstu.setRegularClass(this.getRegularClass());
		dgstu.setDistance(this.getDistance());
		dgstu.setTrafficWay(this.getTrafficWay());
		dgstu.setIsNeedBus(this.getIsNeedBus());
		dgstu.setIsGovernmentBear(this.getIsGovernmentBear());
		dgstu.setIsNeedAssistance(this.getIsNeedAssistance());
		dgstu.setIsOrphan(this.getIsOrphan());
		dgstu.setIsEnjoyAssistance(this.getIsEnjoyAssistance());
		dgstu.setIsMartyrChild(this.getIsMartyrChild());
		dgstu.setUrbanRegisterType(this.getUrbanRegisterType());
		dgstu.setNowaddress(this.getNowaddress());
		dgstu.setSource(this.getSource());
		dgstu.setIdentitycardValid(this.getIdentitycardValid());
		dgstu.setCompatriots(this.getCompatriots());
		dgstu.setCreationTime(new Date());
		dgstu.setModifyTime(new Date());
		//dgstu.setStucode();
		//dgstu.setUnitivecode(stuTemp.getUnitivecode());
		//dgstu.setSeatNumber(stuTemp.getSeatNumber());
		//dgstu.setSourcePlace(stuTemp.getSourcePlace());//来源地区
		//dgstu.setIsSpecialid(stuTemp.getIsSpecialid());// 是否特殊身份证
		//dgstu.setStudentType(stuTemp.get);//学生类别
		
		dgstu.setBloodType(this.getBloodType());// 血型
		dgstu.setBirthRank(this.getBirthRank());//出生排行
		dgstu.setNowChildNum(this.getNowChildNum());//现有子女数
		
		//dgstu.setReligion(stuTemp.getReligion());// 宗教信息
		dgstu.setRegisterType(NumberUtils.toInt(this.getNativeType()));//// 户口类别
		dgstu.setIsMigration(this.getIsMigration());
		dgstu.setClassorderid(this.getClassInnerCode());//班内编码
		//dgstu.setStudentRecruitment(Integer.valueOf(this.getSource()));//学生来源 用于异动参数控制
		dgstu.setPin(this.getUnitiveCode());
		if(StringUtils.isBlank(dgstu.getNowState()))
			dgstu.setNowState("40");
		
		dgstu.setHomePlace(this.getHomeplace());// 出生地
		//dgstu.setRegionCode(stuTemp.get());
		
		dgstu.setEnrolWay(this.getEnrolWay());//入读方式
		dgstu.setIsDgregister(this.getIsDgregister());//近期是否有加入东莞户籍意愿
		dgstu.setYearDgregister(this.getYearDgregister());//计划几年加入东莞户籍
		dgstu.setDgregisterAim(this.getDgregisterAim());//加入东莞户籍目的
		
		return dgstu;
	} 
	/**入学年月转化为学期*/
	public static String YearsToAcadyear(String years){
		if(StringUtils.isBlank(years)){
			return null;
		}
		String acadyear=years.substring(0, 4)+"-"+(Integer.valueOf(years.substring(0, 4))+1);
		return acadyear;
	}

}