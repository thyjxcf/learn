package net.zdsoft.office.meeting.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_executive_meet_minutes
 * @author 
 * 
 */
public class OfficeExecutiveMeetMinutes implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String meetingId;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private String deptIds;
	/**
	 * 
	 */
	private Date createTime;
	
	private String unitId;
	
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
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 * 获取
	 */
	public Date getCreateTime(){
		return this.createTime;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getDeptNames() {
		return deptNames;
	}
	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
	
}