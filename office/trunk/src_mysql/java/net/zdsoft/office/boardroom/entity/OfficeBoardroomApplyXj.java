package net.zdsoft.office.boardroom.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_boardroom_apply_xj
 * @author 
 * 
 */
public class OfficeBoardroomApplyXj implements Serializable{
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
	private String applyDeptId;
	/**
	 * 
	 */
	private Date applyDate;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private String state;
	/**
	 * 
	 */
	private String auditUserId;
	/**
	 * 
	 */
	private Date auditDate;
	/**
	 * 
	 */
	private String auditOpinion;
	/**
	 * 
	 */
	private Date createTime;
	
	private String roomId;
	private String roomName;
	private String deptName;
	private String applyUserName;
	private String auditUserName;
	
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
	public void setApplyDeptId(String applyDeptId){
		this.applyDeptId = applyDeptId;
	}
	/**
	 * 获取
	 */
	public String getApplyDeptId(){
		return this.applyDeptId;
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
	public void setContent(String content){
		this.content = content;
	}
	/**
	 * 获取
	 */
	public String getContent(){
		return this.content;
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
	public void setAuditUserId(String auditUserId){
		this.auditUserId = auditUserId;
	}
	/**
	 * 获取
	 */
	public String getAuditUserId(){
		return this.auditUserId;
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
	public void setAuditOpinion(String auditOpinion){
		this.auditOpinion = auditOpinion;
	}
	/**
	 * 获取
	 */
	public String getAuditOpinion(){
		return this.auditOpinion;
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
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public String getAuditUserName() {
		return auditUserName;
	}
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	
}