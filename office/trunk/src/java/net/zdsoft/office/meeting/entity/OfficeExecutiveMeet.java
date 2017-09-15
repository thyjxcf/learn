package net.zdsoft.office.meeting.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * office_executive_meet
 * @author 
 * 
 */
public class OfficeExecutiveMeet implements Serializable{
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
	private Date meetDate;
	/**
	 * 
	 */
	private String place;
	/**
	 * 0：未发布，1：已发布
	 */
	private Integer state;
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
	private String unitId;
	
	private boolean start;//会议是否已经开始，开始之后可以维护议题纪要及查看
	private boolean hasMinutes;//是否包含纪要
	
	private List<OfficeExecutiveIssue> issues;//会议包含的议题
	private String attendDeptNames;//参会科室

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
	public void setMeetDate(Date meetDate){
		this.meetDate = meetDate;
	}
	/**
	 * 获取
	 */
	public Date getMeetDate(){
		return this.meetDate;
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
	 * 设置0：未发布，1：已发布
	 */
	public void setState(Integer state){
		this.state = state;
	}
	/**
	 * 获取0：未发布，1：已发布
	 */
	public Integer getState(){
		return this.state;
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
	public void setUnitId(String unitId){
		this.unitId = unitId;
	}
	/**
	 * 获取
	 */
	public String getUnitId(){
		return this.unitId;
	}
	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
	public boolean isHasMinutes() {
		return hasMinutes;
	}
	public void setHasMinutes(boolean hasMinutes) {
		this.hasMinutes = hasMinutes;
	}
	public List<OfficeExecutiveIssue> getIssues() {
		return issues;
	}
	public void setIssues(List<OfficeExecutiveIssue> issues) {
		this.issues = issues;
	}
	public String getAttendDeptNames() {
		return attendDeptNames;
	}
	public void setAttendDeptNames(String attendDeptNames) {
		this.attendDeptNames = attendDeptNames;
	}
	
}