package net.zdsoft.office.schedule.entity;

import java.io.Serializable;
/**
 * office_calendar_auth
 * @author 
 * 
 */
public class OfficeCalendarAuth implements Serializable{
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
	 * 1部门科室，2局领导
	 */
	private String authType;
	/**
	 * 
	 */
	private String objectId;
	/**
	 * 
	 */
	private String leaderId;
	
	private String leaderName;
	private String objectName;
	
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
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
	 * 设置1部门科室，2局领导
	 */
	public void setAuthType(String authType){
		this.authType = authType;
	}
	/**
	 * 获取1部门科室，2局领导
	 */
	public String getAuthType(){
		return this.authType;
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
	/**
	 * 设置
	 */
	public void setLeaderId(String leaderId){
		this.leaderId = leaderId;
	}
	/**
	 * 获取
	 */
	public String getLeaderId(){
		return this.leaderId;
	}
}