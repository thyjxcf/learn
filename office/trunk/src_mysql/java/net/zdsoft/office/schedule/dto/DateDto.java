package net.zdsoft.office.schedule.dto;

import java.util.Date;

/**
 * 日期
 * @author weixh
 * @since 2015-10-13 下午1:45:44
 */
public class DateDto {
	private Date calendarDate;
	private String dateStr;
	private int week;// 第几周
	private int dayOfWeek = -1;//周几
	private String dayOfWeekStr;// 周几str，星期一。。。
	private String strByDay;
	
	public Date getCalendarDate() {
		return calendarDate;
	}
	public void setCalendarDate(Date date) {
		this.calendarDate = date;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getDayOfWeekStr() {
		return dayOfWeekStr;
	}
	public void setDayOfWeekStr(String dayOfWeekStr) {
		this.dayOfWeekStr = dayOfWeekStr;
	}
	public String getStrByDay() {
		return strByDay;
	}
	public void setStrByDay(String strByDay) {
		this.strByDay = strByDay;
	}
	
}
