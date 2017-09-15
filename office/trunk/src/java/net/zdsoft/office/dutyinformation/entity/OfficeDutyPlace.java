package net.zdsoft.office.dutyinformation.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_duty_place
 * @author 
 * 
 */
public class OfficeDutyPlace implements Serializable{
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
	private String dutyApplyId;
	/**
	 * 
	 */
	private String patrolPlaceId;
	private String placeName;
	/**
	 * 
	 */
	private Date dutyTime;
	/**
	 * 
	 */
	private String dutyUserId;
	/**
	 * 
	 */
	private String patrolContent;
	/**
	 * 
	 */
	private String patrolTime;
	/**
	 * 
	 */
	private Date createTime;
	
	private boolean idExit;

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
	public void setDutyApplyId(String dutyApplyId){
		this.dutyApplyId = dutyApplyId;
	}
	/**
	 * 获取
	 */
	public String getDutyApplyId(){
		return this.dutyApplyId;
	}
	/**
	 * 设置
	 */
	public void setPatrolPlaceId(String patrolPlaceId){
		this.patrolPlaceId = patrolPlaceId;
	}
	/**
	 * 获取
	 */
	public String getPatrolPlaceId(){
		return this.patrolPlaceId;
	}
	/**
	 * 设置
	 */
	public void setDutyTime(Date dutyTime){
		this.dutyTime = dutyTime;
	}
	/**
	 * 获取
	 */
	public Date getDutyTime(){
		return this.dutyTime;
	}
	/**
	 * 设置
	 */
	public void setDutyUserId(String dutyUserId){
		this.dutyUserId = dutyUserId;
	}
	/**
	 * 获取
	 */
	public String getDutyUserId(){
		return this.dutyUserId;
	}
	/**
	 * 设置
	 */
	public void setPatrolContent(String patrolContent){
		this.patrolContent = patrolContent;
	}
	/**
	 * 获取
	 */
	public String getPatrolContent(){
		return this.patrolContent;
	}
	/**
	 * 设置
	 */
	public void setPatrolTime(String patrolTime){
		this.patrolTime = patrolTime;
	}
	/**
	 * 获取
	 */
	public String getPatrolTime(){
		return this.patrolTime;
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
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public boolean isIdExit() {
		return idExit;
	}
	public void setIdExit(boolean idExit) {
		this.idExit = idExit;
	}
	
}