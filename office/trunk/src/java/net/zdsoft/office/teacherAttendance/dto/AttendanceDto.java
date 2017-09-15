package net.zdsoft.office.teacherAttendance.dto;

import java.util.Date;

public class AttendanceDto {
	
	/**
	 * 考勤日期
	 */
	private Date attenceDate;
	
	/**
	 * 星期几
	 */
	private String weekdayStr;
	
	/**
	 * 打卡时段类型（上班、下班）
	 */
	private String type;
	
	/**
	 * 打卡时间
	 */
	private String timeStr;

	public Date getAttenceDate() {
		return attenceDate;
	}

	public void setAttenceDate(Date attenceDate) {
		this.attenceDate = attenceDate;
	}

	public String getWeekdayStr() {
		return weekdayStr;
	}

	public void setWeekdayStr(String weekdayStr) {
		this.weekdayStr = weekdayStr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
}
