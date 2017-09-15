package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.office.officeFlow.dto.HisTask;
/**
 * office_jtgo_out
 * @author 
 * 
 */
public class OfficeJtgoOut implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String unitId;
	private String unitName;
	/**
	 * 
	 */
	private String days;
	/**
	 * 
	 */
	private String tripPerson;
	/**
	 * 
	 */
	private String tripReason;
	/**
	 * 
	 */
	private String state;
	/**
	 * 
	 */
	private String outType;
	/**
	 * 
	 */
	private String flowId;
	/**
	 * 
	 */
	private String applyUserId;
	private String applyUserName;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Boolean isDeleted;
	
	private List<Attachment> attachments=new ArrayList<Attachment>();
	private List<HisTask> hisTaskList=new ArrayList<HisTask>();//流程意见
	private String uploadContentFileInput;
	
	private String invalidUser;
	private String invalidUserName;//作废人姓名
	
	private String userName;
	private String taskName;

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
	public void setDays(String days){
		this.days = days;
	}
	/**
	 * 获取
	 */
	public String getDays(){
		return this.days;
	}
	/**
	 * 设置
	 */
	public void setTripPerson(String tripPerson){
		this.tripPerson = tripPerson;
	}
	/**
	 * 获取
	 */
	public String getTripPerson(){
		return this.tripPerson;
	}
	/**
	 * 设置
	 */
	public void setTripReason(String tripReason){
		this.tripReason = tripReason;
	}
	/**
	 * 获取
	 */
	public String getTripReason(){
		return this.tripReason;
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
	public void setOutType(String outType){
		this.outType = outType;
	}
	/**
	 * 获取
	 */
	public String getOutType(){
		return this.outType;
	}
	/**
	 * 设置
	 */
	public void setFlowId(String flowId){
		this.flowId = flowId;
	}
	/**
	 * 获取
	 */
	public String getFlowId(){
		return this.flowId;
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
	public void setIsDeleted(Boolean isDeleted){
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取
	 */
	public Boolean getIsDeleted(){
		return this.isDeleted;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public List<HisTask> getHisTaskList() {
		return hisTaskList;
	}
	public void setHisTaskList(List<HisTask> hisTaskList) {
		this.hisTaskList = hisTaskList;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public String getUploadContentFileInput() {
		return uploadContentFileInput;
	}
	public void setUploadContentFileInput(String uploadContentFileInput) {
		this.uploadContentFileInput = uploadContentFileInput;
	}
	public String getInvalidUser() {
		return invalidUser;
	}
	public void setInvalidUser(String invalidUser) {
		this.invalidUser = invalidUser;
	}
	public String getInvalidUserName() {
		return invalidUserName;
	}
	public void setInvalidUserName(String invalidUserName) {
		this.invalidUserName = invalidUserName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}