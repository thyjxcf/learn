package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_meeting_audit
 * @author 
 * 
 */
public class OfficeMeetingAudit implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 状态： 未提交(初始状态)
	 */
	public static final String STATUS_AUDIT_PREPARING = "0";

	/**
	 * 状态：待审核
	 */
	public static final String STATUS_AUDIT_CHECKING = "1";

	/**
	 * 状态： 审核通过
	 */
	public static final String STATUS_AUDIT_PASS = "2";

	/**
	 * 状态： 审核未通过
	 */
	public static final String STATUS_AUDIT_REJECT = "3";

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
	private String state;
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