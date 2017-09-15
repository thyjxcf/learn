package net.zdsoft.office.teacherAttendance.entity;


import java.io.Serializable;
import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;
/**
 * 考勤打卡信息
 * @author 
 * 
 */
public class OfficeAttendanceInfo extends BaseEntity{
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
	 * 记录状态（自我打卡、审批、管理员调整等）
	 */
	private String logType;
	/**
	 * 打卡状态
	 */
	private String clockState;
	/**
	 * 打卡时间
	 */
	private Date clockTime;
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
	 * 当时的考勤时间（如9：00，18：00）
	 */
	private String attendanceTime;
	/**
	 * 备注
	 */
	private String remark;
	
	//辅助字段
	private String userName;
	private String deptName;
	private Date clockTimeAm;
	private Date clockTimePm;
	private String clockStateTotal;
	private String timeLength;
	private String infoIdAm;
	private String infoIdPm;
	private boolean isMySelf;//是否本人
	private boolean isHaveColckApplyAm;//上班时间  --是否有补卡申请待审核中
	private boolean isHaveColckApplyPm;
	private boolean isAuditNoPassAm;//上班时间  --是否补卡申请未通过   true代表未通过 false代表未审核
	private boolean isAuditNoPassPm;//上班时间  --是否补卡申请未通过   true代表未通过 false代表未审核
	private boolean isCanNoApplyAm;//上班打卡是否可以申请补卡
	private boolean isCanNoApplyPm;//下班打卡是否可以申请补卡
	
	public OfficeAttendanceColckLog entityToLog(OfficeAttendanceInfo info){
		OfficeAttendanceColckLog log = new OfficeAttendanceColckLog();
		log.setAttenceDate(info.getAttenceDate());
		log.setAttendanceTime(info.getAttendanceTime());
		log.setClockPlace(info.getClockPlace());
		log.setClockState(info.getClockState());
		log.setClockTime(info.getClockTime());
		log.setIsHoliday(info.getIsHoliday());
		log.setLatitude(info.getLatitude());
		log.setLogType(info.getLogType());
		log.setLongitude(info.getLongitude());
		log.setRemark(info.getRemark());
		log.setType(info.getType());
		log.setUnitId(info.getUnitId());
		log.setUserId(info.getUserId());
		return log;
	}
	
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
	
	public String getClockPlace() {
		return clockPlace;
	}
	public void setClockPlace(String clockPlace) {
		this.clockPlace = clockPlace;
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
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public Boolean getIsHoliday() {
		return isHoliday;
	}
	public void setIsHoliday(Boolean isHoliday) {
		this.isHoliday = isHoliday;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Date getClockTimeAm() {
		return clockTimeAm;
	}
	public void setClockTimeAm(Date clockTimeAm) {
		this.clockTimeAm = clockTimeAm;
	}
	public Date getClockTimePm() {
		return clockTimePm;
	}
	public void setClockTimePm(Date clockTimePm) {
		this.clockTimePm = clockTimePm;
	}
	public String getClockStateTotal() {
		return clockStateTotal;
	}
	public void setClockStateTotal(String clockStateTotal) {
		this.clockStateTotal = clockStateTotal;
	}
	public String getTimeLength() {
		return timeLength;
	}
	public void setTimeLength(String timeLength) {
		this.timeLength = timeLength;
	}
	public boolean isMySelf() {
		return isMySelf;
	}
	public void setMySelf(boolean isMySelf) {
		this.isMySelf = isMySelf;
	}
	public boolean isHaveColckApplyAm() {
		return isHaveColckApplyAm;
	}
	public void setHaveColckApplyAm(boolean isHaveColckApplyAm) {
		this.isHaveColckApplyAm = isHaveColckApplyAm;
	}
	public boolean isHaveColckApplyPm() {
		return isHaveColckApplyPm;
	}
	public void setHaveColckApplyPm(boolean isHaveColckApplyPm) {
		this.isHaveColckApplyPm = isHaveColckApplyPm;
	}

	public boolean isAuditNoPassAm() {
		return isAuditNoPassAm;
	}

	public void setAuditNoPassAm(boolean isAuditNoPassAm) {
		this.isAuditNoPassAm = isAuditNoPassAm;
	}

	public boolean isAuditNoPassPm() {
		return isAuditNoPassPm;
	}

	public void setAuditNoPassPm(boolean isAuditNoPassPm) {
		this.isAuditNoPassPm = isAuditNoPassPm;
	}

	public String getInfoIdAm() {
		return infoIdAm;
	}

	public void setInfoIdAm(String infoIdAm) {
		this.infoIdAm = infoIdAm;
	}

	public String getInfoIdPm() {
		return infoIdPm;
	}

	public void setInfoIdPm(String infoIdPm) {
		this.infoIdPm = infoIdPm;
	}

	public boolean isCanNoApplyAm() {
		return isCanNoApplyAm;
	}

	public void setCanNoApplyAm(boolean isCanNoApplyAm) {
		this.isCanNoApplyAm = isCanNoApplyAm;
	}

	public boolean isCanNoApplyPm() {
		return isCanNoApplyPm;
	}

	public void setCanNoApplyPm(boolean isCanNoApplyPm) {
		this.isCanNoApplyPm = isCanNoApplyPm;
	}

	
}