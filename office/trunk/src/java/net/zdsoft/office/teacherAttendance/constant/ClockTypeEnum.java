package net.zdsoft.office.teacherAttendance.constant;

public enum ClockTypeEnum {
	
	CLOCK_TYPE_0(AttendanceConstants.ATTENDANCE_CLOCK_STATE_DEFAULT, "正常"),
	CLOCK_TYPE_1(AttendanceConstants.ATTENDANCE_CLOCK_STATE_1, "迟到"),
	CLOCK_TYPE_2(AttendanceConstants.ATTENDANCE_CLOCK_STATE_2, "早退"),
	CLOCK_TYPE_3(AttendanceConstants.ATTENDANCE_CLOCK_STATE_3, "外勤"),
	CLOCK_TYPE_98(AttendanceConstants.ATTENDANCE_CLOCK_STATE_98, "缺卡"),
	CLOCK_TYPE_99(AttendanceConstants.ATTENDANCE_CLOCK_STATE_99, "旷工"),
	;
	
	private String code;
	private String name;
	
	ClockTypeEnum(String code, String name){
		this.code = code;
		this.name = name;
	}
	
	/**
	 * 获取对应的名称
	 * @param code
	 * @return
	 */
	public static String getName(String code){
		for(ClockTypeEnum e : values()){
			if(e.getCode().equals(code)){
				return e.getName();
			}
		}
		return "";
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
