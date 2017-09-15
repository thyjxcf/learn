package net.zdsoft.eis.base.data.dto;

public class DateInfoDto {
	
	private int month;
	private int beginWeek;
	private int endWeek;
	private int order;
	
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getBeginWeek() {
		return beginWeek;
	}
	public void setBeginWeek(int beginWeek) {
		this.beginWeek = beginWeek;
	}
	public int getEndWeek() {
		return endWeek;
	}
	public void setEndWeek(int endWeek) {
		this.endWeek = endWeek;
	}
}
