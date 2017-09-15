package net.zdsoft.office.meeting.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.attachment.entity.Attachment;
/**
 * office_executive_issue
 * @author 
 * 
 */
public class OfficeExecutiveIssue implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private String meetingId;
	/**
	 * 1：未提报，2：待审批，3：通过，4：未通过
	 */
	private Integer state;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String deptId;
	/**
	 * 
	 */
	private String createUserId;
	/**
	 * 
	 */
	private Date createTime;
	
	private Integer serialNumber;
	
	private String auditRemark;

	//辅助字段
	private String hostDeptId;//主办科室
	private String attendDeptId;//列席科室
	private String leaderId;//提报领导
	private String opinionDeptId;//意见征集科室
	private String hostDeptNameStr;//主办科室
	private String attendDeptNameStr;//列席科室
	private String leaderNameStr;//提报领导
	private String opinionDeptNameStr;//意见征集科室
	private boolean createUser;//是否是提报人
	private Integer reviseOpinionType;//0-没有填写意见权限;1-填写意见;2-修改意见
	private String meetingName;
	
	private List<Attachment> attachments;
	private String[] removeAttachment;//已删除附件的id
	
	private boolean canManageOpinion;
	
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
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 获取
	 */
	public String getName(){
		return this.name;
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
	public void setMeetingId(String meetingId){
		this.meetingId = meetingId;
	}
	/**
	 * 获取
	 */
	public String getMeetingId(){
		return this.meetingId;
	}
	/**
	 * 设置1：未提报，2：待审批，3：通过，4：未通过
	 */
	public void setState(Integer state){
		this.state = state;
	}
	/**
	 * 获取1：未提报，2：待审批，3：通过，4：未通过
	 */
	public Integer getState(){
		return this.state;
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
	public void setDeptId(String deptId){
		this.deptId = deptId;
	}
	/**
	 * 获取
	 */
	public String getDeptId(){
		return this.deptId;
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
	public String getLeaderNameStr() {
		return leaderNameStr;
	}
	public void setLeaderNameStr(String leaderNameStr) {
		this.leaderNameStr = leaderNameStr;
	}
	public String getHostDeptNameStr() {
		return hostDeptNameStr;
	}
	public void setHostDeptNameStr(String hostDeptNameStr) {
		this.hostDeptNameStr = hostDeptNameStr;
	}
	public String getAttendDeptNameStr() {
		return attendDeptNameStr;
	}
	public void setAttendDeptNameStr(String attendDeptNameStr) {
		this.attendDeptNameStr = attendDeptNameStr;
	}
	public String getOpinionDeptNameStr() {
		return opinionDeptNameStr;
	}
	public void setOpinionDeptNameStr(String opinionDeptNameStr) {
		this.opinionDeptNameStr = opinionDeptNameStr;
	}
	public Integer getReviseOpinionType() {
		return reviseOpinionType;
	}
	public void setReviseOpinionType(Integer reviseOpinionType) {
		this.reviseOpinionType = reviseOpinionType;
	}
	public String getHostDeptId() {
		return hostDeptId;
	}
	public void setHostDeptId(String hostDeptId) {
		this.hostDeptId = hostDeptId;
	}
	public String getAttendDeptId() {
		return attendDeptId;
	}
	public void setAttendDeptId(String attendDeptId) {
		this.attendDeptId = attendDeptId;
	}
	public String getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	public String getOpinionDeptId() {
		return opinionDeptId;
	}
	public void setOpinionDeptId(String opinionDeptId) {
		this.opinionDeptId = opinionDeptId;
	}
	public boolean isCreateUser() {
		return createUser;
	}
	public void setCreateUser(boolean createUser) {
		this.createUser = createUser;
	}
	public String getMeetingName() {
		return meetingName;
	}
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
	public Integer getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getAuditRemark() {
		return auditRemark;
	}
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public String[] getRemoveAttachment() {
		return removeAttachment;
	}
	public void setRemoveAttachment(String[] removeAttachment) {
		this.removeAttachment = removeAttachment;
	}
	public boolean isCanManageOpinion() {
		return canManageOpinion;
	}
	public void setCanManageOpinion(boolean canManageOpinion) {
		this.canManageOpinion = canManageOpinion;
	}
}