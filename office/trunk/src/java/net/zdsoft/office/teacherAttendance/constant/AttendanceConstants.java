package net.zdsoft.office.teacherAttendance.constant;

public class AttendanceConstants {
	
	/**
	 * 教师考勤管理员
	 */
	public static final String TEACHER_ATTENCE_ADMIN = "teacherAttence_admin";
	
	/**上班时段类型--上班*/
	public static final String ATTENDANCE_TYPE_AM = "1";
	/**上班时段类型--下班*/
	public static final String ATTENDANCE_TYPE_PM = "2";
	
	/**打卡状态--正常打卡*/
	public static final String ATTENDANCE_CLOCK_STATE_DEFAULT = "0";
	/**打卡状态--迟到*/
	public static final String ATTENDANCE_CLOCK_STATE_1 = "1";
	/**打卡状态--早退*/
	public static final String ATTENDANCE_CLOCK_STATE_2 = "2";
	/**打卡状态--外勤*/
	public static final String ATTENDANCE_CLOCK_STATE_3 = "3";
	
	/**考勤状态--旷工*/
	public static final String ATTENDANCE_CLOCK_STATE_99 = "99";
	/**考勤状态--缺卡*/
	public static final String ATTENDANCE_CLOCK_STATE_98 = "98";
	
	/**记录类型--自我打卡*/
	public static final String ATTENDANCE_LOG_TYPE_DEFAULT = "0";
	/**记录类型--审批*/
	public static final String ATTENDANCE_LOG_TYPE_1 = "1";
	/**记录类型--管理员调整*/
	public static final String ATTENDANCE_LOG_TYPE_2 = "2";
	
	/**
	 * "出勤天数"
	 */
	public static final String ATTENDANCE_COUNT_TYPE_1 = "1";
	/**
	 * "正常打卡"
	 */
	public static final String ATTENDANCE_COUNT_TYPE_2 = "2";
	/**
	 * "外勤打卡"
	 */
	public static final String ATTENDANCE_COUNT_TYPE_3 = "3";
	/**
	 * "早退"
	 */
	public static final String ATTENDANCE_COUNT_TYPE_4 = "4";
	/**
	 * "缺卡"
	 */
	public static final String ATTENDANCE_COUNT_TYPE_5 = "5";
	/**
	 * "迟到"
	 */
	public static final String ATTENDANCE_COUNT_TYPE_6 = "6";
	/**
	 * "旷工",
	 */
	public static final String ATTENDANCE_COUNT_TYPE_7 = "7";
	/**
	 * "休息"
	 */
	public static final String ATTENDANCE_COUNT_TYPE_8 = "8";
	
}
