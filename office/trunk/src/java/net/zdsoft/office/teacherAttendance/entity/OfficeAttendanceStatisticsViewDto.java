package net.zdsoft.office.teacherAttendance.entity;

import java.util.Date;

/** 
 * @author  lufeng 
 * @version 创建时间：2017-4-26 下午02:41:35 
 * 类说明 
 */
public class OfficeAttendanceStatisticsViewDto {
	/**
	 * 日期
	 */
	private Date day;
	/**
	 * 考勤人数
	 */
	private String attendanceNum;
	/**
	 * 正常考勤人数
	 */
	private String customAttendanceNum;
	/**
	 * 外勤人数
	 */
	private String outWorkNum;
	/**
	 * 迟到人数
	 */
	private String laterNum;
	/**
	 * 早退人数
	 */
	private String leaveEarlyNum;
	/**
	 * 缺卡人数
	 */
	private String missCardNum;
	/**
	 * 请假人数
	 */
	private String leaveNum;
	/**
	 * 旷工人数
	 */
	private String notWorkNum;
	/**
	 * 出差人数
	 */
	private String businessNum;
	/**
	 * 外出人数
	 */
	private String goOutNum;
	/**
	 * 集体外出人数
	 */
	private String jtGoOutNum;
	
	private String dateStr;
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public String getAttendanceNum() {
		return attendanceNum;
	}
	public void setAttendanceNum(String attendanceNum) {
		this.attendanceNum = attendanceNum;
	}
	public String getCustomAttendanceNum() {
		return customAttendanceNum;
	}
	public void setCustomAttendanceNum(String customAttendanceNum) {
		this.customAttendanceNum = customAttendanceNum;
	}
	public String getOutWorkNum() {
		return outWorkNum;
	}
	public void setOutWorkNum(String outWorkNum) {
		this.outWorkNum = outWorkNum;
	}
	public String getLaterNum() {
		return laterNum;
	}
	public void setLaterNum(String laterNum) {
		this.laterNum = laterNum;
	}
	public String getLeaveEarlyNum() {
		return leaveEarlyNum;
	}
	public void setLeaveEarlyNum(String leaveEarlyNum) {
		this.leaveEarlyNum = leaveEarlyNum;
	}
	public String getMissCardNum() {
		return missCardNum;
	}
	public void setMissCardNum(String missCardNum) {
		this.missCardNum = missCardNum;
	}
	public String getLeaveNum() {
		return leaveNum;
	}
	public void setLeaveNum(String leaveNum) {
		this.leaveNum = leaveNum;
	}
	public String getNotWorkNum() {
		return notWorkNum;
	}
	public void setNotWorkNum(String notWorkNum) {
		this.notWorkNum = notWorkNum;
	}
	public String getBusinessNum() {
		return businessNum;
	}
	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}
	public String getGoOutNum() {
		return goOutNum;
	}
	public void setGoOutNum(String goOutNum) {
		this.goOutNum = goOutNum;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public String getJtGoOutNum() {
		return jtGoOutNum;
	}
	public void setJtGoOutNum(String jtGoOutNum) {
		this.jtGoOutNum = jtGoOutNum;
	}
	
	
	
	
}
