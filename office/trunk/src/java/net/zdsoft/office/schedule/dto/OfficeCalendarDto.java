package net.zdsoft.office.schedule.dto;

import java.util.Date;
import java.util.List;

import net.zdsoft.eis.frame.dto.BaseDto;
import net.zdsoft.office.schedule.entity.OfficeCalendar;

/**
 * 
 * @author weixh
 * @since 2015-10-12 下午2:10:08
 */
@SuppressWarnings("serial")
public class OfficeCalendarDto extends BaseDto {
	private String unitId;
	private Date calendarTime;// 日期
	private Date endTime;
	private String period;// 时段
	private int num;// 数量
	private String creator;// 创建人
	private String creatorType;// 类型，1个人，3局领导
	private String calendarId;
	private List<OfficeCalendar> cals;
	private String searchType;// 1周视图，2月视图，3日程
	private String rangeType;// 数据范围，1个人，2科室，3单位
	private String operate;// 上一周/月/天1，下一周/月/天2
	private boolean needContent;
	private boolean fullContent;
	private String deptId;
	private int weekNum;// 月周数
	private int dayNum;// 月天数
	private int firstWeek;// 月第一天是周几
	private String content;
	
	public Date getCalendarTime() {
		return calendarTime;
	}
	public void setCalendarTime(Date calendarTime) {
		this.calendarTime = calendarTime;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreatorType() {
		return creatorType;
	}
	public void setCreatorType(String creatorType) {
		this.creatorType = creatorType;
	}
	public String getCalendarId() {
		return calendarId;
	}
	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}
	public List<OfficeCalendar> getCals() {
		return cals;
	}
	public void setCals(List<OfficeCalendar> cals) {
		this.cals = cals;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getRangeType() {
		return rangeType;
	}
	public void setRangeType(String rangeType) {
		this.rangeType = rangeType;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public boolean isNeedContent() {
		return needContent;
	}
	public void setNeedContent(boolean needContent) {
		this.needContent = needContent;
	}
	public boolean isFullContent() {
		return fullContent;
	}
	public void setFullContent(boolean fullContent) {
		this.fullContent = fullContent;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public int getWeekNum() {
		return weekNum;
	}
	public void setWeekNum(int weekNum) {
		this.weekNum = weekNum;
	}
	public int getDayNum() {
		return dayNum;
	}
	public void setDayNum(int dayNum) {
		this.dayNum = dayNum;
	}
	public int getFirstWeek() {
		return firstWeek;
	}
	public void setFirstWeek(int firstWeek) {
		this.firstWeek = firstWeek;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
