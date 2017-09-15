package net.zdsoft.office.teacherAttendance.constant;

import java.util.Calendar;

public enum WeekdayEnum {
	
	SUNDAY(Calendar.SUNDAY, "星期日"),
	MONDAY(Calendar.MONDAY, "星期一"),
	TUESDAY(Calendar.TUESDAY, "星期二"),
	WEDNESDAY(Calendar.WEDNESDAY, "星期三"),
	THURSDAY(Calendar.THURSDAY, "星期四"),
	FRIDAY(Calendar.FRIDAY, "星期五"),
	SATURDAY(Calendar.SATURDAY, "星期六"),
	;
	
	private int code;
	private String name;
	
	WeekdayEnum(int code, String name){
		this.code = code;
		this.name = name;
	}
	
	/**
	 * 获取对应的名称
	 * @param code
	 * @return
	 */
	public static String getName(int code){
		for(WeekdayEnum item : values()){
			if(item.getCode() ==  code){
				return item.getName();
			}
		}
		return "";
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
