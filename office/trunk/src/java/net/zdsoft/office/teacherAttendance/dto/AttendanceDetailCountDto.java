package net.zdsoft.office.teacherAttendance.dto;

/**
 * 天数统计类
 * @author Administrator
 *
 */
public class AttendanceDetailCountDto {
	
	private String type;//1-8 分别对应 "出勤天数","正常打卡","外勤打卡","早退","缺卡","迟到","旷工","休息"
	private String name;//"出勤天数","正常打卡","外勤打卡","早退","缺卡","迟到","旷工","休息"
	private int num;//天数，如2，次
	private String numStr;//天数，如2天，3次
	private double percentage;//两位小数 所占百分比
	
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumStr() {
		return numStr;
	}
	public void setNumStr(String numStr) {
		this.numStr = numStr;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
}	
