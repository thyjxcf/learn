package net.zdsoft.office.teacherAttendance.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.office.officeFlow.dto.HisTask;

/**
 * 考勤补卡申请表
 * @author 
 * 
 */
public class OfficeAttendanceColckApply extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String flowId;
	/**
	 * 
	 */
	private String applyUserId;
	/**
	 * 申请状态
	 */
	private Integer applyStatus;
	/**
	 * 考勤日期
	 */
	private Date attenceDate;
	/**
	 * 是否节假日(打卡当日是否是节假日)
	 */
//	private Boolean isHoliday;
	/**
	 * 打卡时段类型
	 */
	private String type;
	/**
	 * 补卡时间
	 */
//	private Date clockTime;
	/**
	 * 补卡原因
	 */
	private String reason;
	
	
	//辅助字段
	private String applyStatusName;//审批状态
	private String userName;
	private String deptName;
	private String typeWeekTime;//星期几+上班或下班时间
	private String auditTextComment;
	
	private String taskId;
	private String taskName;
	private List<HisTask> hisTaskList=new ArrayList<HisTask>();//流程意见

	
//	public Boolean getIsHoliday() {
//		return isHoliday;
//	}
//	public void setIsHoliday(Boolean isHoliday) {
//		this.isHoliday = isHoliday;
//	}
//	public Date getClockTime() {
//		return clockTime;
//	}
//	public void setClockTime(Date clockTime) {
//		this.clockTime = clockTime;
//	}
	public String getApplyStatusName() {
		return applyStatusName;
	}
	public void setApplyStatusName(String applyStatusName) {
		this.applyStatusName = applyStatusName;
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
	public void setApplyStatus(Integer applyStatus){
		this.applyStatus = applyStatus;
	}
	/**
	 * 获取
	 */
	public Integer getApplyStatus(){
		return this.applyStatus;
	}
	/**
	 * 设置
	 */
	public void setAttenceDate(Date attenceDate){
		this.attenceDate = attenceDate;
	}
	/**
	 * 获取
	 */
	public Date getAttenceDate(){
		return this.attenceDate;
	}
	/**
	 * 设置
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 * 获取
	 */
	public String getType(){
		return this.type;
	}
	/**
	 * 设置
	 */
	public void setReason(String reason){
		this.reason = reason;
	}
	/**
	 * 获取
	 */
	public String getReason(){
		return this.reason;
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
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public List<HisTask> getHisTaskList() {
		return hisTaskList;
	}
	public void setHisTaskList(List<HisTask> hisTaskList) {
		this.hisTaskList = hisTaskList;
	}
	public String getTypeWeekTime() {
		return typeWeekTime;
	}
	public void setTypeWeekTime(String typeWeekTime) {
		this.typeWeekTime = typeWeekTime;
	}
	public String getAuditTextComment() {
		return auditTextComment;
	}
	public void setAuditTextComment(String auditTextComment) {
		this.auditTextComment = auditTextComment;
	}
	
}