package net.zdsoft.office.msgcenter.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_sms_info
 * @author 
 * 
 */
public class OfficeSmsInfo implements Serializable{
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
	private String batchId;
	/**
	 * 
	 */
	private String sendUserId;
	/**
	 * 
	 */
	private String msg;
	/**
	 * 
	 */
	private String sendTime;
	/**
	 * 
	 */
	private String feedbackDescription;
	/**
	 * 
	 */
	private Date createTime;
	
	private String sendTimeStr;
	private String successPhone;
	private String failedPhone;
	private String noPhone;

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
	public void setBatchId(String batchId){
		this.batchId = batchId;
	}
	/**
	 * 获取
	 */
	public String getBatchId(){
		return this.batchId;
	}
	/**
	 * 设置
	 */
	public void setSendUserId(String sendUserId){
		this.sendUserId = sendUserId;
	}
	/**
	 * 获取
	 */
	public String getSendUserId(){
		return this.sendUserId;
	}
	/**
	 * 设置
	 */
	public void setMsg(String msg){
		this.msg = msg;
	}
	/**
	 * 获取
	 */
	public String getMsg(){
		return this.msg;
	}
	/**
	 * 设置
	 */
	public void setSendTime(String sendTime){
		this.sendTime = sendTime;
	}
	/**
	 * 获取
	 */
	public String getSendTime(){
		return this.sendTime;
	}
	/**
	 * 设置
	 */
	public void setFeedbackDescription(String feedbackDescription){
		this.feedbackDescription = feedbackDescription;
	}
	/**
	 * 获取
	 */
	public String getFeedbackDescription(){
		return this.feedbackDescription;
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
	public String getSendTimeStr() {
		return sendTimeStr;
	}
	public void setSendTimeStr(String sendTimeStr) {
		this.sendTimeStr = sendTimeStr;
	}
	public String getSuccessPhone() {
		return successPhone;
	}
	public void setSuccessPhone(String successPhone) {
		this.successPhone = successPhone;
	}
	public String getFailedPhone() {
		return failedPhone;
	}
	public void setFailedPhone(String failedPhone) {
		this.failedPhone = failedPhone;
	}
	public String getNoPhone() {
		return noPhone;
	}
	public void setNoPhone(String noPhone) {
		this.noPhone = noPhone;
	}
}