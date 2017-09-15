package net.zdsoft.office.teacherAttendance.entity;

import java.io.Serializable;
import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;
/**
 * 考勤打卡流水记录表
 * @author 
 * 
 */
public class OfficeAttendanceColckLog extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String userId;
	/**
	 * 考勤日期
	 */
	private Date attenceDate;
	/**
	 * 是否节假日(打卡当日是否是节假日)
	 */
	private Boolean isHoliday;
	/**
	 * 打卡时段类型（上班、下班）
	 */
	private String type;
	/**
	 * 记录类型（自我打卡、管理员调整、审批）
	 */
	private String logType;
	/**
	 * 打卡状态(正常、迟到、...、)
	 */
	private String clockState;
	/**
	 * 打卡时间
	 */
	private Date clockTime;
	/**
	 * 当时考勤时间
	 */
	private String attendanceTime;
	/**
	 * 打卡地点
	 */
	private String clockPlace;
	/**
	 * 纬度
	 */
	private String latitude;
	/**
	 * 经度
	 */
	private String longitude;
	
	/**
	 * 备注
	 */
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAttendanceTime() {
		return attendanceTime;
	}
	public void setAttendanceTime(String attendanceTime) {
		this.attendanceTime = attendanceTime;
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
	public void setAttenceDate(Date attenceDate){
		this.attenceDate = attenceDate;
	}
	/**
	 * 获取
	 */
	public Date getAttenceDate(){
		return this.attenceDate;
	}
	/**
	 * 设置
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 * 获取
	 */
	public String getType(){
		return this.type;
	}
	/**
	 * 设置
	 */
	public void setLogType(String logType){
		this.logType = logType;
	}
	/**
	 * 获取
	 */
	public String getLogType(){
		return this.logType;
	}
	/**
	 * 设置
	 */
	public void setClockState(String clockState){
		this.clockState = clockState;
	}
	/**
	 * 获取
	 */
	public String getClockState(){
		return this.clockState;
	}
	/**
	 * 设置
	 */
	public void setClockTime(Date clockTime){
		this.clockTime = clockTime;
	}
	/**
	 * 获取
	 */
	public Date getClockTime(){
		return this.clockTime;
	}
	/**
	 * 设置
	 */
	public void setClockPlace(String clockPlace){
		this.clockPlace = clockPlace;
	}
	/**
	 * 获取
	 */
	public String getClockPlace(){
		return this.clockPlace;
	}
	/**
	 * 设置
	 */
	public void setLatitude(String latitude){
		this.latitude = latitude;
	}
	/**
	 * 获取
	 */
	public String getLatitude(){
		return this.latitude;
	}
	/**
	 * 设置
	 */
	public void setLongitude(String longitude){
		this.longitude = longitude;
	}
	/**
	 * 获取
	 */
	public String getLongitude(){
		return this.longitude;
	}
	public Boolean getIsHoliday() {
		return isHoliday;
	}
	public void setIsHoliday(Boolean isHoliday) {
		this.isHoliday = isHoliday;
	}
	
}