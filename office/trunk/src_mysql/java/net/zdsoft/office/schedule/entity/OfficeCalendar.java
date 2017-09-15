package net.zdsoft.office.schedule.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.attachment.entity.Attachment;
/**
 * 日程安排表
 * @author 
 * 
 */
public class OfficeCalendar implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String operator;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String place;
	/**
	 * 
	 */
	private String attendeeName;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private boolean isDeleted;
	/**
	 * 
	 */
	private Date modifyTime;
	/**
	 * 
	 */
	private Date calendarTime;
	/**
	 * 
	 */
	private boolean isSmsAlarm;
	/**
	 * 
	 */
	private Date smsAlarmTime;
	/**
	 * 
	 */
	private String version;
	/**
	 * 全天
	 */
	private int halfDays;
	/**
	 * 
	 */
	private Date endTime;
	/**
	 * 
	 */
	private int period;
	/**
	 * 
	 */
	private int creatorType;
	/**
	 * 
	 */
	private String creator;
	
	private boolean isAllDayEvent;//是否是全天事件
	
	private String creatorName;
	
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
	public void setOperator(String operator){
		this.operator = operator;
	}
	/**
	 * 获取
	 */
	public String getOperator(){
		return this.operator;
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
	public void setAttendeeName(String attendeeName){
		this.attendeeName = attendeeName;
	}
	/**
	 * 获取
	 */
	public String getAttendeeName(){
		return this.attendeeName;
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
	public void setDeleted(boolean isDeleted){
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取
	 */
	public boolean getIsDeleted(){
		return this.isDeleted;
	}
	/**
	 * 设置
	 */
	public void setModifyTime(Date modifyTime){
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取
	 */
	public Date getModifyTime(){
		return this.modifyTime;
	}
	/**
	 * 设置
	 */
	public void setCalendarTime(Date calendarTime){
		this.calendarTime = calendarTime;
	}
	/**
	 * 获取
	 */
	public Date getCalendarTime(){
		return this.calendarTime;
	}
	/**
	 * 设置
	 */
	public void setIsSmsAlarm(boolean isSmsAlarm){
		this.isSmsAlarm = isSmsAlarm;
	}
	/**
	 * 获取
	 */
	public boolean getIsSmsAlarm(){
		return this.isSmsAlarm;
	}
	/**
	 * 设置
	 */
	public void setSmsAlarmTime(Date smsAlarmTime){
		this.smsAlarmTime = smsAlarmTime;
	}
	/**
	 * 获取
	 */
	public Date getSmsAlarmTime(){
		return this.smsAlarmTime;
	}
	/**
	 * 设置
	 */
	public void setVersion(String version){
		this.version = version;
	}
	/**
	 * 获取
	 */
	public String getVersion(){
		return this.version;
	}
	/**
	 * 设置
	 */
	public void setHalfDays(int halfDays){
		this.halfDays = halfDays;
	}
	/**
	 * 获取
	 */
	public int getHalfDays(){
		return this.halfDays;
	}
	/**
	 * 设置
	 */
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	/**
	 * 获取
	 */
	public Date getEndTime(){
		return this.endTime;
	}
	/**
	 * 设置
	 */
	public void setPeriod(int period){
		this.period = period;
	}
	/**
	 * 获取
	 */
	public int getPeriod(){
		return this.period;
	}
	/**
	 * 设置
	 */
	public void setCreatorType(int creatorType){
		this.creatorType = creatorType;
	}
	/**
	 * 获取
	 */
	public int getCreatorType(){
		return this.creatorType;
	}
	/**
	 * 设置
	 */
	public void setCreator(String creator){
		this.creator = creator;
	}
	/**
	 * 获取
	 */
	public String getCreator(){
		return this.creator;
	}
	public boolean isAllDayEvent() {
		return isAllDayEvent;
	}
	public void setAllDayEvent(boolean isAllDayEvent) {
		this.isAllDayEvent = isAllDayEvent;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
}