package net.zdsoft.office.survey.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_survey_apply
 * @author 
 * 
 */
public class OfficeSurveyApply implements Serializable{
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
	private String applyUserId;
	/**
	 * 
	 */
	private String applyUserName;
	/**
	 * 
	 */
	private String surveyName;
	/**
	 * 
	 */
	private String place;
	/**
	 * 
	 */
	private Integer amount;
	/**
	 * 
	 */
	private Date startTime;
	/**
	 * 
	 */
	private Date endTime;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private Integer state;
	/**
	 * 
	 */
	private Date applyDate;
	/**
	 * 
	 */
	private Date auditDate;

	//辅助字段
	private String applyDeptName;
	private String opinion;//审核意见
	
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
	public void setApplyUserId(String applyUserId){
		this.applyUserId = applyUserId;
	}
	/**
	 * 获取
	 */
	public String getApplyUserId(){
		return this.applyUserId;
	}
	/**
	 * 设置
	 */
	public void setApplyUserName(String applyUserName){
		this.applyUserName = applyUserName;
	}
	/**
	 * 获取
	 */
	public String getApplyUserName(){
		return this.applyUserName;
	}
	/**
	 * 设置
	 */
	public void setSurveyName(String surveyName){
		this.surveyName = surveyName;
	}
	/**
	 * 获取
	 */
	public String getSurveyName(){
		return this.surveyName;
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
	public void setAmount(Integer amount){
		this.amount = amount;
	}
	/**
	 * 获取
	 */
	public Integer getAmount(){
		return this.amount;
	}
	/**
	 * 设置
	 */
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	/**
	 * 获取
	 */
	public Date getStartTime(){
		return this.startTime;
	}
	/**
	 * 设置
	 */
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	/**
	 * 获取
	 */
	public Date getEndTime(){
		return this.endTime;
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
	public void setState(Integer state){
		this.state = state;
	}
	/**
	 * 获取
	 */
	public Integer getState(){
		return this.state;
	}
	/**
	 * 设置
	 */
	public void setApplyDate(Date applyDate){
		this.applyDate = applyDate;
	}
	/**
	 * 获取
	 */
	public Date getApplyDate(){
		return this.applyDate;
	}
	/**
	 * 设置
	 */
	public void setAuditDate(Date auditDate){
		this.auditDate = auditDate;
	}
	/**
	 * 获取
	 */
	public Date getAuditDate(){
		return this.auditDate;
	}
	public String getApplyDeptName() {
		return applyDeptName;
	}
	public void setApplyDeptName(String applyDeptName) {
		this.applyDeptName = applyDeptName;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
}