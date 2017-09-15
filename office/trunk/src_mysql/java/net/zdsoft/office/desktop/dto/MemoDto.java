package net.zdsoft.office.desktop.dto;

import net.zdsoft.office.desktop.entity.Memo;


public class MemoDto {
	private String type;
	private Memo memo;
//	private Schedule schedule;
//	private WorkPlanItem workPlanItem;
//	private CalendarDayInfo calendarDayInfo;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Memo getMemo() {
		return memo;
	}
	public void setMemo(Memo memo) {
		this.memo = memo;
	}
//	public Schedule getSchedule() {
//		return schedule;
//	}
//	public void setSchedule(Schedule schedule) {
//		this.schedule = schedule;
//	}
//	public WorkPlanItem getWorkPlanItem() {
//		return workPlanItem;
//	}
//	public void setWorkPlanItem(WorkPlanItem workPlanItem) {
//		this.workPlanItem = workPlanItem;
//	}
//	public CalendarDayInfo getCalendarDayInfo() {
//		return calendarDayInfo;
//	}
//	public void setCalendarDayInfo(CalendarDayInfo calendarDayInfo) {
//		this.calendarDayInfo = calendarDayInfo;
//	}
	
	
}
