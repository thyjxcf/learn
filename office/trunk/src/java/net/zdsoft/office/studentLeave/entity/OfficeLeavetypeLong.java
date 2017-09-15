package net.zdsoft.office.studentLeave.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.office.officeFlow.dto.HisTask;
/**
 * office_leavetype_long
 * @author 
 * 
 */
public class OfficeLeavetypeLong implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String leaveId;
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
	private float days;
	/**
	 * 
	 */
	private String hasBed;
	/**
	 * 
	 */
	private String address;
	/**
	 * 
	 */
	private String mateName;
	/**
	 * 
	 */
	private String mateGx;
	/**
	 * 
	 */
	private Date creationTime;
	private String classId;
	private String className;
	private String studentId;
	private String studentName;
	private String unitId;
	
	private String state;
	private String flowId;
	private String applyUserId;
	private String applyUserName;
	private List<HisTask> hisTaskList=new ArrayList<HisTask>();//流程意见
	
	private String taskId;
	private String taskName;
	private Date createTime;
	
	public Date getCreateTime() {
		if(createTime == null){
			return new Date();
		}
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
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
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
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
	public void setLeaveId(String leaveId){
		this.leaveId = leaveId;
	}
	/**
	 * 获取
	 */
	public String getLeaveId(){
		return this.leaveId;
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
	public void setDays(float days){
		this.days = days;
	}
	/**
	 * 获取
	 */
	public float getDays(){
		return this.days;
	}
	/**
	 * 设置
	 */
	public void setHasBed(String hasBed){
		this.hasBed = hasBed;
	}
	/**
	 * 获取
	 */
	public String getHasBed(){
		return this.hasBed;
	}
	/**
	 * 设置
	 */
	public void setAddress(String address){
		this.address = address;
	}
	/**
	 * 获取
	 */
	public String getAddress(){
		return this.address;
	}
	/**
	 * 设置
	 */
	public void setMateName(String mateName){
		this.mateName = mateName;
	}
	/**
	 * 获取
	 */
	public String getMateName(){
		return this.mateName;
	}
	/**
	 * 设置
	 */
	public void setMateGx(String mateGx){
		this.mateGx = mateGx;
	}
	/**
	 * 获取
	 */
	public String getMateGx(){
		return this.mateGx;
	}
	/**
	 * 设置
	 */
	public void setCreationTime(Date creationTime){
		this.creationTime = creationTime;
	}
	/**
	 * 获取
	 */
	public Date getCreationTime(){
		return this.creationTime;
	}
}