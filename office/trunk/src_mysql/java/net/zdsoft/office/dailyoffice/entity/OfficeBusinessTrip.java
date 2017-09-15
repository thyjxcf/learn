package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.office.teacherLeave.dto.HisTask;
/**
 * office_business_trip
 * @author 
 * 
 */
public class OfficeBusinessTrip implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String place;
	/**
	 * 
	 */
	private Date beginTime;
	/**
	 * 
	 */
	private Date endTime;
	/**
	 * 
	 */
	private double days;
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
	private String flowId;
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
	private Date createTime;
	/**
	 * 
	 */
	private Boolean isDeleted;
	
	/**
	 * 辅助字段
	 */
	private List<Attachment> attachments=new ArrayList<Attachment>();
	private List<HisTask> hisTaskList=new ArrayList<HisTask>();//流程意见
	//
	private String taskId;
	private String taskName;
	private String userName;
	private String uploadContentFileInput;
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
	public void setBeginTime(Date beginTime){
		this.beginTime = beginTime;
	}
	/**
	 * 获取
	 */
	public Date getBeginTime(){
		return this.beginTime;
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

	public double getDays() {
		return days;
	}
	public void setDays(double days) {
		this.days = days;
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
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUploadContentFileInput() {
		return uploadContentFileInput;
	}
	public void setUploadContentFileInput(String uploadContentFileInput) {
		this.uploadContentFileInput = uploadContentFileInput;
	}
}