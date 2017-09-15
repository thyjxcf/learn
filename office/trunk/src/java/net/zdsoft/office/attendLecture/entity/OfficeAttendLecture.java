package net.zdsoft.office.attendLecture.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.office.officeFlow.dto.HisTask;

/**
 * office_attend_lecture(听课信息表)
 * @author 
 * 
 */
public class OfficeAttendLecture extends BaseEntity{

	/**
	 * 
	 */
	private String unitId;
	
	private Integer type;
	/**
	 * 
	 */
	private String flowId;
	/**
	 * 
	 */
	private String applyUserId;
	/**
	 * 
	 */
	private String state;
	/**
	 * 
	 */
	private Date attendDate;
	/**
	 * 
	 */
	private String attendPeriod;
	/**
	 * 
	 */
	private String attendPeriodNum;
	/**
	 * 
	 */
	private String gradeId;
	/**
	 * 
	 */
	private String classId;
	/**
	 * 
	 */
	private String subjectName;
	/**
	 * 
	 */
	private String teacherName;
	/**
	 * 
	 */
	private String projectName;
	/**
	 * 
	 */
	private String projectContent;
	/**
	 * 
	 */
	private String projectOpinion;
	/**
	 * 
	 */
	private Date createTime;

	//辅助
	private String className;
	private List<HisTask> hisTaskList=new ArrayList<HisTask>();//流程意见
	private String applyUserName;
	private String taskId;
	private String taskName;
	private String deptName;
	private String deptId;
	private Integer teacherNum;
	private Integer lectureNum;
	
	private Integer schoolInNum;
	private Integer schoolOutNum;
	
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
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
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
	public void setAttendDate(Date attendDate){
		this.attendDate = attendDate;
	}
	/**
	 * 获取
	 */
	public Date getAttendDate(){
		return this.attendDate;
	}
	/**
	 * 设置
	 */
	public void setAttendPeriod(String attendPeriod){
		this.attendPeriod = attendPeriod;
	}
	/**
	 * 获取
	 */
	public String getAttendPeriod(){
		return this.attendPeriod;
	}
	
	public String getAttendPeriodNum() {
		return attendPeriodNum;
	}
	public void setAttendPeriodNum(String attendPeriodNum) {
		this.attendPeriodNum = attendPeriodNum;
	}
	/**
	 * 设置
	 */
	public void setGradeId(String gradeId){
		this.gradeId = gradeId;
	}
	/**
	 * 获取
	 */
	public String getGradeId(){
		return this.gradeId;
	}
	/**
	 * 设置
	 */
	public void setClassId(String classId){
		this.classId = classId;
	}
	/**
	 * 获取
	 */
	public String getClassId(){
		return this.classId;
	}
	/**
	 * 设置
	 */
	public void setSubjectName(String subjectName){
		this.subjectName = subjectName;
	}
	/**
	 * 获取
	 */
	public String getSubjectName(){
		return this.subjectName;
	}
	/**
	 * 设置
	 */
	public void setTeacherName(String teacherName){
		this.teacherName = teacherName;
	}
	/**
	 * 获取
	 */
	public String getTeacherName(){
		return this.teacherName;
	}
	/**
	 * 设置
	 */
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}
	/**
	 * 获取
	 */
	public String getProjectName(){
		return this.projectName;
	}
	/**
	 * 设置
	 */
	public void setProjectContent(String projectContent){
		this.projectContent = projectContent;
	}
	/**
	 * 获取
	 */
	public String getProjectContent(){
		return this.projectContent;
	}
	/**
	 * 设置
	 */
	public void setProjectOpinion(String projectOpinion){
		this.projectOpinion = projectOpinion;
	}
	/**
	 * 获取
	 */
	public String getProjectOpinion(){
		return this.projectOpinion;
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
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Integer getTeacherNum() {
		return teacherNum;
	}
	public void setTeacherNum(Integer teacherNum) {
		this.teacherNum = teacherNum;
	}
	public Integer getLectureNum() {
		return lectureNum;
	}
	public void setLectureNum(Integer lectureNum) {
		this.lectureNum = lectureNum;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSchoolInNum() {
		return schoolInNum;
	}
	public void setSchoolInNum(Integer schoolInNum) {
		this.schoolInNum = schoolInNum;
	}
	public Integer getSchoolOutNum() {
		return schoolOutNum;
	}
	public void setSchoolOutNum(Integer schoolOutNum) {
		this.schoolOutNum = schoolOutNum;
	}
	
}