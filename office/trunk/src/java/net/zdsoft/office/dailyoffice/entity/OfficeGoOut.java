package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.office.officeFlow.dto.HisTask;
/**
 * office_go_out
 * @author 
 * 
 */
public class OfficeGoOut implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
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
	private Double hours;
	
	private String desHours;//审核
	private String type;//
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
	private String applyUserName;
	/**
	 * 
	 */
	private String outType;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Boolean isDeleted;
	/**
	 * 作废人Id
	 */
	private String invalidUser;
	private String invalidUserName;//作废人姓名
	//辅助字段
	private List<Attachment> attachments=new ArrayList<Attachment>();
	private List<HisTask> hisTaskList=new ArrayList<HisTask>();//流程意见
	private List<Flow> flowList;
	//
	private String taskId;
	private String taskName;
	private String uploadContentFileInput;
	
	private Integer outNum;
	private Double sumHours; 
	private Integer NumJob;
	private Integer NumSelf;
	private Double HoursJob;
	private Double HoursSelf;
	
	
	public Integer getNumJob() {
		return NumJob;
	}
	public void setNumJob(Integer numJob) {
		NumJob = numJob;
	}
	public Integer getNumSelf() {
		return NumSelf;
	}
	public void setNumSelf(Integer numSelf) {
		NumSelf = numSelf;
	}
	public Double getHoursJob() {
		return HoursJob;
	}
	public void setHoursJob(Double hoursJob) {
		HoursJob = hoursJob;
	}
	public Double getHoursSelf() {
		return HoursSelf;
	}
	public void setHoursSelf(Double hoursSelf) {
		HoursSelf = hoursSelf;
	}
	public String getOutType() {
		return outType;
	}
	public void setOutType(String outType) {
		this.outType = outType;
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
	
	public Double getHours() {
		return hours;
	}
	public void setHours(Double hours) {
		this.hours = hours;
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
	public Integer getOutNum() {
		return outNum;
	}
	public void setOutNum(Integer outNum) {
		this.outNum = outNum;
	}
	public Double getSumHours() {
		return sumHours;
	}
	public void setSumHours(Double sumHours) {
		this.sumHours = sumHours;
	}
	public String getDesHours() {
		return desHours;
	}
	public void setDesHours(String desHours) {
		this.desHours = desHours;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public List<Flow> getFlowList() {
		return flowList;
	}
	public void setFlowList(List<Flow> flowList) {
		this.flowList = flowList;
	}
	
}