package net.zdsoft.eis.base.subsystemcall.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * STUDENT_INFORMAL_FAMILY_TEMP
 * @author 
 * 
 */
public class StudentInformalFamilyTemp implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String studentId;
	/**
	 * 
	 */
	private String schoolId;
	/**
	 * 
	 */
	private String relation;
	/**
	 * 
	 */
	private String realName;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private String nation;
	/**
	 * 
	 */
	private String company;
	/**
	 * 
	 */
	private String linkAddress;
	/**
	 * 
	 */
	private String regionCode;
	/**
	 * 
	 */
	private String mobilePhone;
	/**
	 * 
	 */
	private int isGuardian;
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
	private String duty;
	/**
	 * 
	 */
	private String jobId;
	
	private Date creationTime; 
	
	private Date modifyTime;
	
	private String isResidence;//是否办理过居住证 
    
	private String isBuySocsec;//是否购买社保
	
	//辅助
	private String regionPlaceName;//户口所在地
	
	private List<StusysTimeFrame> stFramelist;
	
	private String residenceValids;//手机东莞居住证端时段
	private String socsecValids;//手机东莞购买社保端时段
	
	public String getResidenceValids() {
		return residenceValids;
	}
	public void setResidenceValids(String residenceValids) {
		this.residenceValids = residenceValids;
	}
	public String getSocsecValids() {
		return socsecValids;
	}
	public void setSocsecValids(String socsecValids) {
		this.socsecValids = socsecValids;
	}

	/**
	 * 设置
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 * 获取
	 */
	public String getId(){
		return this.id;
	}
	/**
	 * 设置
	 */
	public void setStudentId(String studentId){
		this.studentId = studentId;
	}
	/**
	 * 获取
	 */
	public String getStudentId(){
		return this.studentId;
	}
	/**
	 * 设置
	 */
	public void setSchoolId(String schoolId){
		this.schoolId = schoolId;
	}
	/**
	 * 获取
	 */
	public String getSchoolId(){
		return this.schoolId;
	}
	/**
	 * 设置
	 */
	public void setRelation(String relation){
		this.relation = relation;
	}
	/**
	 * 获取
	 */
	public String getRelation(){
		return this.relation;
	}
	/**
	 * 设置
	 */
	public void setRealName(String realName){
		this.realName = realName;
	}
	/**
	 * 获取
	 */
	public String getRealName(){
		return this.realName;
	}
	/**
	 * 设置
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/**
	 * 获取
	 */
	public String getRemark(){
		return this.remark;
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
	public void setCompany(String company){
		this.company = company;
	}
	/**
	 * 获取
	 */
	public String getCompany(){
		return this.company;
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
	public void setRegionCode(String regionCode){
		this.regionCode = regionCode;
	}
	/**
	 * 获取
	 */
	public String getRegionCode(){
		return this.regionCode;
	}
	/**
	 * 设置
	 */
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	/**
	 * 获取
	 */
	public String getMobilePhone(){
		return this.mobilePhone;
	}
	/**
	 * 设置
	 */
	public void setIsGuardian(int isGuardian){
		this.isGuardian = isGuardian;
	}
	/**
	 * 获取
	 */
	public int getIsGuardian(){
		return this.isGuardian;
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
	public void setDuty(String duty){
		this.duty = duty;
	}
	/**
	 * 获取
	 */
	public String getDuty(){
		return this.duty;
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
	 * 获取regionPlaceName
	 * @return regionPlaceName
	 */
	public String getRegionPlaceName() {
	    return regionPlaceName;
	}
	/**
	 * 设置regionPlaceName
	 * @param regionPlaceName regionPlaceName
	 */
	public void setRegionPlaceName(String regionPlaceName) {
	    this.regionPlaceName = regionPlaceName;
	}
	
	
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
	public String getIsResidence() {
		return isResidence;
	}
	public void setIsResidence(String isResidence) {
		this.isResidence = isResidence;
	}
	public String getIsBuySocsec() {
		return isBuySocsec;
	}
	public void setIsBuySocsec(String isBuySocsec) {
		this.isBuySocsec = isBuySocsec;
	}
	
	
	public List<StusysTimeFrame> getStFramelist() {
		return stFramelist;
	}
	public void setStFramelist(List<StusysTimeFrame> stFramelist) {
		this.stFramelist = stFramelist;
	}
	/**
	 * 拷贝<tt>StudentInformalFamilyTemp</tt>到<tt>DgFamily</tt>
	 * 
	 * @param stuTemp
	 * 
	 * @param dgstu
	 */
	public DgFamily toDgFamily(){
		DgFamily family=new DgFamily();
		family.setId(this.getId());
		family.setStudentId(this.getStudentId());
		family.setSchoolId(this.getSchoolId());
		family.setRelation(this.getRelation());
		family.setName(this.getRealName());
		family.setCompany(this.getCompany());
		family.setDuty(this.getDuty());
		family.setIdentityCard(this.getIdentityCard());
		family.setNation(this.getNation());
		family.setGuardian(this.getIsGuardian()==0?false:true);
		family.setLinkAddress(this.getLinkAddress());
		family.setMobilePhone(this.getMobilePhone());
		family.setRegionCode(this.getRegionCode());
		family.setCreationTime(new Date());
		family.setModifyTime(new Date());
		family.setIsdeleted(false);
		family.setChargeNumber(this.getMobilePhone());
		family.setLeaveSchool(0);
		family.setIdentitycardType(this.getIdentitycardType());
		family.setRelationRemark(this.getRemark());//关系说明
		family.setRegisterPlace(this.getRegionCode());
		
		family.setIsResidence(this.getIsResidence());
		family.setIsbuySocsec(this.getIsBuySocsec());
		return family;
	}
}