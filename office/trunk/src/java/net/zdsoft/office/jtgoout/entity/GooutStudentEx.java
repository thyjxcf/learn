package net.zdsoft.office.jtgoout.entity;

import java.io.Serializable;
/**
 * goout_student_ex
 * @author 
 * 
 */
public class GooutStudentEx implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String jtgooutId;
	/**
	 * 
	 */
	private String organize;
	/**
	 * 
	 */
	private Integer activityNumber;
	/**
	 * 
	 */
	private String place;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private String vehicle;
	/**
	 * 
	 */
	private Boolean isDrivinglicence;
	private String drivinglicence;
	/**
	 * 
	 */
	private Boolean isOrganization;
	private String organization;
	/**
	 * 
	 */
	private String traveUnit;
	/**
	 * 
	 */
	private String traveLinkPerson;
	/**
	 * 
	 */
	private String traveLinkPhone;
	/**
	 * 
	 */
	private Boolean isInsurance;
	private String insurance;
	/**
	 * 
	 */
	private String activityLeaderId;
	private String activityLeaderPhone;
	private String activityLeaderName;
	/**
	 * 
	 */
	private String leadGroupId;
	private String leadGroupPhone;
	private String leadGroupName;
	/**
	 * 
	 */
	private String otherTeacherId;
	private String otherTeacherNames;
	/**
	 * 
	 */
	private Boolean activityIdeal;
	private String activity;
	/**
	 * 
	 */
	private Boolean saftIdeal;
	private String saft;

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
	public void setUnitId(String unitId){
		this.unitId = unitId;
	}
	/**
	 * 获取
	 */
	public String getUnitId(){
		return this.unitId;
	}
	/**
	 * 设置
	 */
	public void setJtgooutId(String jtgooutId){
		this.jtgooutId = jtgooutId;
	}
	/**
	 * 获取
	 */
	public String getJtgooutId(){
		return this.jtgooutId;
	}
	/**
	 * 设置
	 */
	public void setOrganize(String organize){
		this.organize = organize;
	}
	/**
	 * 获取
	 */
	public String getOrganize(){
		return this.organize;
	}
	/**
	 * 设置
	 */
	public void setActivityNumber(Integer activityNumber){
		this.activityNumber = activityNumber;
	}
	/**
	 * 获取
	 */
	public Integer getActivityNumber(){
		return this.activityNumber;
	}
	/**
	 * 设置
	 */
	public void setPlace(String place){
		this.place = place;
	}
	/**
	 * 获取
	 */
	public String getPlace(){
		return this.place;
	}
	/**
	 * 设置
	 */
	public void setContent(String content){
		this.content = content;
	}
	/**
	 * 获取
	 */
	public String getContent(){
		return this.content;
	}
	/**
	 * 设置
	 */
	public void setVehicle(String vehicle){
		this.vehicle = vehicle;
	}
	/**
	 * 获取
	 */
	public String getVehicle(){
		return this.vehicle;
	}
	/**
	 * 设置
	 */
	public void setIsDrivinglicence(Boolean isDrivinglicence){
		this.isDrivinglicence = isDrivinglicence;
	}
	/**
	 * 获取
	 */
	public Boolean getIsDrivinglicence(){
		return this.isDrivinglicence;
	}

	public void setIsOrganization(Boolean isOrganization) {
		this.isOrganization = isOrganization;
	}
	/**
	 * 获取
	 */
	public Boolean getIsOrganization(){
		return this.isOrganization;
	}
	/**
	 * 设置
	 */
	public void setTraveUnit(String traveUnit){
		this.traveUnit = traveUnit;
	}
	/**
	 * 获取
	 */
	public String getTraveUnit(){
		return this.traveUnit;
	}
	/**
	 * 设置
	 */
	public void setTraveLinkPerson(String traveLinkPerson){
		this.traveLinkPerson = traveLinkPerson;
	}
	/**
	 * 获取
	 */
	public String getTraveLinkPerson(){
		return this.traveLinkPerson;
	}
	/**
	 * 设置
	 */
	public void setTraveLinkPhone(String traveLinkPhone){
		this.traveLinkPhone = traveLinkPhone;
	}
	/**
	 * 获取
	 */
	public String getTraveLinkPhone(){
		return this.traveLinkPhone;
	}
	/**
	 * 设置
	 */
	public void setIsInsurance(Boolean isInsurance){
		this.isInsurance = isInsurance;
	}
	/**
	 * 获取
	 */
	public Boolean getIsInsurance(){
		return this.isInsurance;
	}
	/**
	 * 设置
	 */
	public void setActivityLeaderId(String activityLeaderId){
		this.activityLeaderId = activityLeaderId;
	}
	/**
	 * 获取
	 */
	public String getActivityLeaderId(){
		return this.activityLeaderId;
	}
	/**
	 * 设置
	 */
	public void setLeadGroupId(String leadGroupId){
		this.leadGroupId = leadGroupId;
	}
	/**
	 * 获取
	 */
	public String getLeadGroupId(){
		return this.leadGroupId;
	}
	/**
	 * 设置
	 */
	public void setOtherTeacherId(String otherTeacherId){
		this.otherTeacherId = otherTeacherId;
	}
	/**
	 * 获取
	 */
	public String getOtherTeacherId(){
		return this.otherTeacherId;
	}
	/**
	 * 设置
	 */
	
	public String getActivityLeaderName() {
		return activityLeaderName;
	}
	public Boolean getActivityIdeal() {
		return activityIdeal;
	}
	public void setActivityIdeal(Boolean activityIdeal) {
		this.activityIdeal = activityIdeal;
	}
	public Boolean getSaftIdeal() {
		return saftIdeal;
	}
	public void setSaftIdeal(Boolean saftIdeal) {
		this.saftIdeal = saftIdeal;
	}
	public void setActivityLeaderName(String activityLeaderName) {
		this.activityLeaderName = activityLeaderName;
	}
	public String getLeadGroupName() {
		return leadGroupName;
	}
	public void setLeadGroupName(String leadGroupName) {
		this.leadGroupName = leadGroupName;
	}
	public String getOtherTeacherNames() {
		return otherTeacherNames;
	}
	public void setOtherTeacherNames(String otherTeacherNames) {
		this.otherTeacherNames = otherTeacherNames;
	}
	public String getDrivinglicence() {
		return drivinglicence;
	}
	public void setDrivinglicence(String drivinglicence) {
		this.drivinglicence = drivinglicence;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public String getSaft() {
		return saft;
	}
	public void setSaft(String saft) {
		this.saft = saft;
	}
	public String getActivityLeaderPhone() {
		return activityLeaderPhone;
	}
	public void setActivityLeaderPhone(String activityLeaderPhone) {
		this.activityLeaderPhone = activityLeaderPhone;
	}
	public String getLeadGroupPhone() {
		return leadGroupPhone;
	}
	public void setLeadGroupPhone(String leadGroupPhone) {
		this.leadGroupPhone = leadGroupPhone;
	}
}