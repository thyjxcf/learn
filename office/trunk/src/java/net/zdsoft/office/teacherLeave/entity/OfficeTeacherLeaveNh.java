package net.zdsoft.office.teacherLeave.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.office.officeFlow.dto.HisTask;

/**
 * office_teacher_leave_nh
 * @author 
 * 
 */
public class OfficeTeacherLeaveNh implements Serializable{
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
	private Date beginTime;
	/**
	 * 
	 */
	private Date endTime;
	/**
	 * 
	 */
	private Float days;
	
	private String leaveTypeId;//请假类型
	/**
	 * 
	 */
	private String morningChange;
	/**
	 * 
	 */
	private String nightChange;
	/**
	 * 
	 */
	private String weekChange;
	/**
	 * 
	 */
	private String actChargeTeacher;
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
	private Boolean isDeleted;
	/**
	 * 
	 */
	private String createUserId;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String flowId;
	
	private String userName;//用户姓名
	private String flowType;//流程类型，分班主任流程及非班主任流程
	private String leaveTypeName;//请假类型名称
	private String taskId;
	private String taskName;
	private String deptName;//部门
	
	private List<HisTask> hisTaskList=new ArrayList<HisTask>();//流程意见

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
	public void setDays(Float days){
		this.days = days;
	}
	/**
	 * 获取
	 */
	public Float getDays(){
		return this.days;
	}
	public String getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(String leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	/**
	 * 设置
	 */
	public void setMorningChange(String morningChange){
		this.morningChange = morningChange;
	}
	/**
	 * 获取
	 */
	public String getMorningChange(){
		return this.morningChange;
	}
	/**
	 * 设置
	 */
	public void setNightChange(String nightChange){
		this.nightChange = nightChange;
	}
	/**
	 * 获取
	 */
	public String getNightChange(){
		return this.nightChange;
	}
	/**
	 * 设置
	 */
	public void setWeekChange(String weekChange){
		this.weekChange = weekChange;
	}
	/**
	 * 获取
	 */
	public String getWeekChange(){
		return this.weekChange;
	}
	/**
	 * 设置
	 */
	public void setActChargeTeacher(String actChargeTeacher){
		this.actChargeTeacher = actChargeTeacher;
	}
	/**
	 * 获取
	 */
	public String getActChargeTeacher(){
		return this.actChargeTeacher;
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
	public void setIsDeleted(Boolean isDeleted){
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取
	 */
	public Boolean getIsDeleted(){
		return this.isDeleted;
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
	public void setFlowId(String flowId){
		this.flowId = flowId;
	}
	/**
	 * 获取
	 */
	public String getFlowId(){
		return this.flowId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	public String getLeaveTypeName() {
		return leaveTypeName;
	}
	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
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
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}