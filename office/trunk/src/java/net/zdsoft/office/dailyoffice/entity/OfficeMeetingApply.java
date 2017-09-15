package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_meeting_apply
 * @author 
 * 
 */
public class OfficeMeetingApply implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 状态： 审核未通过(初始状态)
	 */
	public static final int STATUS_APPLY_REJECT = 0;

	/**
	 * 状态：审核通过
	 */
	public static final int STATUS_APPLY_PASS = 1;
	
	/**
	 * 附件表：sys_attachment 
	 * server：AttachmentService
	 * 会议管理上传附件的类型objectType  ObjectId=(OfficeMeetingApply.id)
	 */
	public static final String OFFICE_MEETING_ATTACHMENT = "OFFICE_MEETING_ATTACHMENT";
	
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
	private String meetingTheme;
	/**
	 * 
	 */
	private String hostUserId;
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
	private String meetingPlace;
	/**
	 * 
	 */
	private String meetingRoom;
	/**
	 * 
	 */
	private String deptIds;
	/**
	 * 
	 */
	private String participants;
	/**
	 * 
	 */
	private String meetingContent;
	/**
	 * 
	 */
	private Integer fee;
	/**
	 * 
	 */
	private Integer state;
	/**
	 * 
	 */
	private Boolean isSendNotice;
	/**
	 * 
	 */
	private Boolean isSendSms;
	/**
	 * 
	 */
	private Date applyDate;
	/**
	 * 
	 */
	private Date auditDate;
	/**
	 * 
	 */
	private Date creationTime;

	//辅助字段
	private String opinion;//审核意见   
	private String auditState;//审核状态
	private String timeStr;
	private String applyUserName;//申请人姓名
	private String hostUserName;//主持人姓名
	private String teachPlaceName;//会议室名称
	private String deptNames;//主办部门名称
	private String meetingUserIds;//通知人员ID
	private String meetingUserNames;//通知人员名称
	private String fileName;//附件文件名称
	private String fileUrl;//附件下载地址
	
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
	public void setMeetingTheme(String meetingTheme){
		this.meetingTheme = meetingTheme;
	}
	/**
	 * 获取
	 */
	public String getMeetingTheme(){
		return this.meetingTheme;
	}
	/**
	 * 设置
	 */
	public void setHostUserId(String hostUserId){
		this.hostUserId = hostUserId;
	}
	/**
	 * 获取
	 */
	public String getHostUserId(){
		return this.hostUserId;
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
	public void setMeetingPlace(String meetingPlace){
		this.meetingPlace = meetingPlace;
	}
	/**
	 * 获取
	 */
	public String getMeetingPlace(){
		return this.meetingPlace;
	}
	/**
	 * 设置
	 */
	public void setMeetingRoom(String meetingRoom){
		this.meetingRoom = meetingRoom;
	}
	/**
	 * 获取
	 */
	public String getMeetingRoom(){
		return this.meetingRoom;
	}
	/**
	 * 设置
	 */
	public void setDeptIds(String deptIds){
		this.deptIds = deptIds;
	}
	/**
	 * 获取
	 */
	public String getDeptIds(){
		return this.deptIds;
	}
	/**
	 * 设置
	 */
	public void setParticipants(String participants){
		this.participants = participants;
	}
	/**
	 * 获取
	 */
	public String getParticipants(){
		return this.participants;
	}
	/**
	 * 设置
	 */
	public void setMeetingContent(String meetingContent){
		this.meetingContent = meetingContent;
	}
	/**
	 * 获取
	 */
	public String getMeetingContent(){
		return this.meetingContent;
	}
	/**
	 * 设置
	 */
	public void setFee(Integer fee){
		this.fee = fee;
	}
	/**
	 * 获取
	 */
	public Integer getFee(){
		return this.fee;
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
	public void setIsSendNotice(Boolean isSendNotice){
		this.isSendNotice = isSendNotice;
	}
	/**
	 * 获取
	 */
	public Boolean getIsSendNotice(){
		return this.isSendNotice;
	}
	/**
	 * 设置
	 */
	public void setIsSendSms(Boolean isSendSms){
		this.isSendSms = isSendSms;
	}
	/**
	 * 获取
	 */
	public Boolean getIsSendSms(){
		return this.isSendSms;
	}
	/**
	 * 设置
	 */
	public void setApplyDate(Date applyDate){
		this.applyDate = applyDate;
	}
	/**
	 * 获取
	 */
	public Date getApplyDate(){
		return this.applyDate;
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
	
	public String getOpinion() {
		return opinion;
	}
	
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	public String getAuditState() {
		return auditState;
	}
	
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	
	public String getTimeStr() {
		return timeStr;
	}
	
	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public String getHostUserName() {
		return hostUserName;
	}
	public void setHostUserName(String hostUserName) {
		this.hostUserName = hostUserName;
	}
	public String getTeachPlaceName() {
		return teachPlaceName;
	}
	public void setTeachPlaceName(String teachPlaceName) {
		this.teachPlaceName = teachPlaceName;
	}
	public String getDeptNames() {
		return deptNames;
	}
	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
	public String getMeetingUserIds() {
		return meetingUserIds;
	}
	public void setMeetingUserIds(String meetingUserIds) {
		this.meetingUserIds = meetingUserIds;
	}
	public String getMeetingUserNames() {
		return meetingUserNames;
	}
	public void setMeetingUserNames(String meetingUserNames) {
		this.meetingUserNames = meetingUserNames;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
}