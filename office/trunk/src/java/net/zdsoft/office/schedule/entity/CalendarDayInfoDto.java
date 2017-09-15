package net.zdsoft.office.schedule.entity;

import java.util.Date;

public class CalendarDayInfoDto {
	private String id;
	private Date restDate;	//时间
	private String content;	//内容
	private String unitId;	//单位id
	
	private String calendarStr;	//阳历时间
	private String lunarStr;	//农历时间
	
	private int week;//星期
	private int weekForYear;//第几周
	
	private int day;
	private int month;
	
	private int rowspan;
	
	public int getRowspan() {
		return rowspan;
	}
	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
	public int getDay() {
		return day;
	}
	public int getMonth() {
		return month;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getRestDate() {
		return restDate;
	}
	public void setRestDate(Date restDate) {
		this.restDate = restDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getCalendarStr() {
		return calendarStr;
	}
	public void setCalendarStr(String calendarStr) {
		this.calendarStr = calendarStr;
	}
	public String getLunarStr() {
		return lunarStr;
	}
	public void setLunarStr(String lunarStr) {
		this.lunarStr = lunarStr;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getWeekForYear() {
		return weekForYear;
	}
	public void setWeekForYear(int weekForYear) {
		this.weekForYear = weekForYear;
	}
	
}
