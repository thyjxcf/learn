package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
import java.util.Date;

import net.zdsoft.eis.base.attachment.entity.Attachment;
/**
 * office_apply_number
 * @author 
 * 
 */
public class OfficeApplyNumber implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	private String unitId;
	private String type;
	private String purpose;
	private String applyUserId;
	private Date applyDate;
	private Date createTime;//创建时间
	private String content;
	private String cardNumber;
	private String courseId;
	private String labInfoId;//实验详情
	/**
	 * 	2：待审核
		3：通过
		4：未通过
	 */
	private Integer state;
	private String auditUserId;
	private Date auditTime;
	private String remark;
	private String meetingTheme;
	private String hostUserId;
	private String deptIds;
	private String feedback;//反馈信息
	
	private String meetingUserIds;//通知人员ID
	private String hostUserName;//主持人姓名
	private String deptNames;//主办部门名称
	private String meetingUserNames;//通知人员名称
	
	/**
	 * 保存使用
	 */
	private String[] applyRooms;//申请教室_节次/时间数组
	private String userName;//显示使用，申请人姓名
	private String auditUserName;//审核人
	
	private Attachment attachment;
	
	private String weekDay;//星期几
	
	/**
	 * 实验室用辅助字段
	 */
	private String labName;
	private String labMode;
	private String labSubject;
	private String labGrade;
	
	public static final String OFFICE_MEETING_ROOM_ATTACHMENT = "OFFICE_MEETING_ROOM_ATTACHMENT";

	public void setId(String id){
		this.id = id;
	}
	public String getId(){
		return this.id;
	}
	public void setUnitId(String unitId){
		this.unitId = unitId;
	}
	public String getUnitId(){
		return this.unitId;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getType(){
		return this.type;
	}
	public void setPurpose(String purpose){
		this.purpose = purpose;
	}
	public String getPurpose(){
		return this.purpose;
	}
	public void setApplyUserId(String applyUserId){
		this.applyUserId = applyUserId;
	}
	public String getApplyUserId(){
		return this.applyUserId;
	}
	
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setContent(String content){
		this.content = content;
	}
	public String getContent(){
		return this.content;
	}
	public void setCardNumber(String cardNumber){
		this.cardNumber = cardNumber;
	}
	public String getCardNumber(){
		return this.cardNumber;
	}
	/**
	 * 设置2：待审核
		3：通过
		4：未通过
	 */
	public void setState(Integer state){
		this.state = state;
	}
	/**
	 * 获取2：待审核
		3：通过
		4：未通过
	 */
	public Integer getState(){
		return this.state;
	}
	public void setAuditUserId(String auditUserId){
		this.auditUserId = auditUserId;
	}
	public String getAuditUserId(){
		return this.auditUserId;
	}
	public void setAuditTime(Date auditTime){
		this.auditTime = auditTime;
	}
	public Date getAuditTime(){
		return this.auditTime;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getRemark(){
		return this.remark;
	}
	public String[] getApplyRooms() {
		return applyRooms;
	}
	public void setApplyRooms(String[] applyRooms) {
		this.applyRooms = applyRooms;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMeetingTheme() {
		return meetingTheme;
	}
	public void setMeetingTheme(String meetingTheme) {
		this.meetingTheme = meetingTheme;
	}
	public String getHostUserId() {
		return hostUserId;
	}
	public void setHostUserId(String hostUserId) {
		this.hostUserId = hostUserId;
	}
	public String getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	public String getMeetingUserIds() {
		return meetingUserIds;
	}
	public void setMeetingUserIds(String meetingUserIds) {
		this.meetingUserIds = meetingUserIds;
	}
	public String getHostUserName() {
		return hostUserName;
	}
	public void setHostUserName(String hostUserName) {
		this.hostUserName = hostUserName;
	}
	public String getDeptNames() {
		return deptNames;
	}
	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
	public String getMeetingUserNames() {
		return meetingUserNames;
	}
	public void setMeetingUserNames(String meetingUserNames) {
		this.meetingUserNames = meetingUserNames;
	}
	public Attachment getAttachment() {
		return attachment;
	}
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	public String getAuditUserName() {
		return auditUserName;
	}
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	public String getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getLabInfoId() {
		return labInfoId;
	}
	public void setLabInfoId(String labInfoId) {
		this.labInfoId = labInfoId;
	}
	public String getLabName() {
		return labName;
	}
	public void setLabName(String labName) {
		this.labName = labName;
	}
	public String getLabMode() {
		return labMode;
	}
	public void setLabMode(String labMode) {
		this.labMode = labMode;
	}
	public String getLabSubject() {
		return labSubject;
	}
	public void setLabSubject(String labSubject) {
		this.labSubject = labSubject;
	}
	public String getLabGrade() {
		return labGrade;
	}
	public void setLabGrade(String labGrade) {
		this.labGrade = labGrade;
	}
}