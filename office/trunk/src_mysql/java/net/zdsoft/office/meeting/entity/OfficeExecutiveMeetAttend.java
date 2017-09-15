package net.zdsoft.office.meeting.entity;

import java.io.Serializable;
/**
 * office_executive_meet_attend
 * @author 
 * 
 */
public class OfficeExecutiveMeetAttend implements Serializable{
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
	 * 1：主办科室，2：列席科室, 3:提报领导
	 */
	private Integer type;
	/**
	 * 
	 */
	private String objectId;
	
	private String unitId;

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
	 * 设置1：主办科室，2：列席科室, 3:提报领导
	 */
	public void setType(Integer type){
		this.type = type;
	}
	/**
	 * 获取1：主办科室，2：列席科室, 3:提报领导
	 */
	public Integer getType(){
		return this.type;
	}
	/**
	 * 设置
	 */
	public void setObjectId(String objectId){
		this.objectId = objectId;
	}
	/**
	 * 获取
	 */
	public String getObjectId(){
		return this.objectId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
}