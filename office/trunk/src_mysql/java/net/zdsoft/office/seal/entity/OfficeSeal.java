package net.zdsoft.office.seal.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_seal
 * @author 
 * 
 */
public class OfficeSeal implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String acadyear;
	/**
	 * 
	 */
	private Integer semester;
	/**
	 * 
	 */
	private String createUserId;
	private String createUserName;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String applyOpinion;
	/**
	 * 
	 */
	private String auditOpinion;
	/**
	 * 
	 */
	private String auditUserId;
	private String auditUserName;
	/**
	 * 
	 */
	private String sealType;
	private String sealName;
	/**
	 * 
	 */
	private String state;
	/**
	 * 
	 */
	private String manageUserId;
	private String manageUserName;
	/**
	 * 
	 */
	private String useSeal;
	private String deptId;
	private String deptName;
	private String mark;
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
	public void setAcadyear(String acadyear){
		this.acadyear = acadyear;
	}
	/**
	 * 获取
	 */
	public String getAcadyear(){
		return this.acadyear;
	}
	/**
	 * 设置
	 */
	public void setSemester(Integer semester){
		this.semester = semester;
	}
	/**
	 * 获取
	 */
	public Integer getSemester(){
		return this.semester;
	}
	/**
	 * 设置
	 */
	public void setCreateUserId(String createUserId){
		this.createUserId = createUserId;
	}
	/**
	 * 获取
	 */
	public String getCreateUserId(){
		return this.createUserId;
	}
	/**
	 * 设置
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 * 获取
	 */
	public Date getCreateTime(){
		return this.createTime;
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
	public void setApplyOpinion(String applyOpinion){
		this.applyOpinion = applyOpinion;
	}
	/**
	 * 获取
	 */
	public String getApplyOpinion(){
		return this.applyOpinion;
	}
	/**
	 * 设置
	 */
	public void setAuditOpinion(String auditOpinion){
		this.auditOpinion = auditOpinion;
	}
	/**
	 * 获取
	 */
	public String getAuditOpinion(){
		return this.auditOpinion;
	}
	/**
	 * 设置
	 */
	public void setAuditUserId(String auditUserId){
		this.auditUserId = auditUserId;
	}
	/**
	 * 获取
	 */
	public String getAuditUserId(){
		return this.auditUserId;
	}
	/**
	 * 设置
	 */
	public void setSealType(String sealType){
		this.sealType = sealType;
	}
	/**
	 * 获取
	 */
	public String getSealType(){
		return this.sealType;
	}
	/**
	 * 设置
	 */
	public void setState(String state){
		this.state = state;
	}
	/**
	 * 获取
	 */
	public String getState(){
		return this.state;
	}
	/**
	 * 设置
	 */
	public void setManageUserId(String manageUserId){
		this.manageUserId = manageUserId;
	}
	/**
	 * 获取
	 */
	public String getManageUserId(){
		return this.manageUserId;
	}
	/**
	 * 设置
	 */
	public void setUseSeal(String useSeal){
		this.useSeal = useSeal;
	}
	/**
	 * 获取
	 */
	public String getUseSeal(){
		return this.useSeal;
	}
	public String getAuditUserName() {
		return auditUserName;
	}
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	public String getManageUserName() {
		return manageUserName;
	}
	public void setManageUserName(String manageUserName) {
		this.manageUserName = manageUserName;
	}
	public String getSealName() {
		return sealName;
	}
	public void setSealName(String sealName) {
		this.sealName = sealName;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
}