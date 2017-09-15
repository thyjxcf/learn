package net.zdsoft.office.survey.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_survey_audit
 * @author 
 * 
 */
public class OfficeSurveyAudit implements Serializable{
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
	private String applyId;
	/**
	 * 
	 */
	private Integer state;
	/**
	 * 
	 */
	private String auditUserId;
	/**
	 * 
	 */
	private String opinion;
	/**
	 * 
	 */
	private Date auditDate;

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
	public void setApplyId(String applyId){
		this.applyId = applyId;
	}
	/**
	 * 获取
	 */
	public String getApplyId(){
		return this.applyId;
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
	public void setOpinion(String opinion){
		this.opinion = opinion;
	}
	/**
	 * 获取
	 */
	public String getOpinion(){
		return this.opinion;
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
}