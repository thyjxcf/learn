package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_work_arrange_content
 * @author 
 * 
 */
public class OfficeWorkArrangeContent implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String outlineId;
	/**
	 * 
	 */
	private String detailId;
	/**
	 * 
	 */
	private Date workDate;
	/**
	 * 
	 */
	private String content;
	private String arrangContent;//具体要求
	/**
	 * 
	 */
	private String deptIds;
	/**
	 * 
	 */
	private String place;
	/**
	 * 
	 */
	private String state;
	
	private String workStartTime;//时段开始时间
	private String workEndTime;//时段结束时间
	private String attendees;//参与人员
	
	private String deptNames;//部门名称

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
	public void setOutlineId(String outlineId){
		this.outlineId = outlineId;
	}
	/**
	 * 获取
	 */
	public String getOutlineId(){
		return this.outlineId;
	}
	/**
	 * 设置
	 */
	public void setDetailId(String detailId){
		this.detailId = detailId;
	}
	/**
	 * 获取
	 */
	public String getDetailId(){
		return this.detailId;
	}
	/**
	 * 设置
	 */
	public void setWorkDate(Date workDate){
		this.workDate = workDate;
	}
	/**
	 * 获取
	 */
	public Date getWorkDate(){
		return this.workDate;
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
	public void setState(String state){
		this.state = state;
	}
	/**
	 * 获取
	 */
	public String getState(){
		return this.state;
	}
	
	public String getDeptNames() {
		return deptNames;
	}
	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
	public String getAttendees() {
		return attendees;
	}
	public void setAttendees(String attendees) {
		this.attendees = attendees;
	}
	public String getWorkStartTime() {
		return workStartTime;
	}
	public void setWorkStartTime(String workStartTime) {
		this.workStartTime = workStartTime;
	}
	public String getWorkEndTime() {
		return workEndTime;
	}
	public void setWorkEndTime(String workEndTime) {
		this.workEndTime = workEndTime;
	}
	public String getArrangContent() {
		return arrangContent;
	}
	public void setArrangContent(String arrangContent) {
		this.arrangContent = arrangContent;
	}
	
}