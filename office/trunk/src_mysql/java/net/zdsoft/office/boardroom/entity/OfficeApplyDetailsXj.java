package net.zdsoft.office.boardroom.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_apply_details_xj
 * @author 
 * 
 */
public class OfficeApplyDetailsXj implements Serializable{
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
	private String roomId;
	/**
	 * 
	 */
	private String applyPeriod;
	/**
	 * 
	 */
	private Date applyDate;
	/**
	 * 
	 */
	private String userId;
	/**
	 * 
	 */
	private String state;

	//辅助字段
	private String userName;
	private String[] applyTimes;
	private String deptId;
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
	public void setRoomId(String roomId){
		this.roomId = roomId;
	}
	/**
	 * 获取
	 */
	public String getRoomId(){
		return this.roomId;
	}
	/**
	 * 设置
	 */
	public void setApplyPeriod(String applyPeriod){
		this.applyPeriod = applyPeriod;
	}
	/**
	 * 获取
	 */
	public String getApplyPeriod(){
		return this.applyPeriod;
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
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 * 获取
	 */
	public String getUserId(){
		return this.userId;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String[] getApplyTimes() {
		return applyTimes;
	}
	public void setApplyTimes(String[] applyTimes) {
		this.applyTimes = applyTimes;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
}