package net.zdsoft.office.schedule.util;

/**
 * 日程安排常量
 * 
 * @author weixh
 * @since 2015-10-10 上午10:24:43
 */
public class ScheduleConstant {
	/** 视图-周视图 */
	public static final String SEARCH_TYPE_WEEK = "1";
	/** 视图-月视图 */
	public static final String SEARCH_TYPE_MONTH = "2";
	/** 视图-日程视图 */
	public static final String SEARCH_TYPE_DATE = "3";
	
	/** 时间段-全天 */
	public static final int PERIOD_ALLDAY = 0;
	/** 时间段-上午 */
	public static final int PERIOD_AM = 1;
	/** 时间段-中午 */
	public static final int PERIOD_NOON = 2;
	/** 时间段-下午 */
	public static final int PERIOD_PM = 3;
	/** 时间段-晚上 */
	public static final int PERIOD_NIGHT = 4;
	
	/** 日程类型-个人 */
	public static final int CALENDAR_CREATOR_TYPE_SELF = 1;
	/** 日程类型-科室 */
	public static final int CALENDAR_CREATOR_TYPE_DEPT = 2;
	/** 日程类型-局领导 */
	public static final int CALENDAR_CREATOR_TYPE_LEADER = 3;
	
	/** 日程查看范围-个人 */
	public static final String CALENDAR_RANGE_TYPE_SELF = "1";
	/** 日程查看范围-科室 */
	public static final String CALENDAR_RANGE_TYPE_DEPT = "2";
	/** 日程查看范围-单位 */
	public static final String CALENDAR_RANGE_TYPE_UNIT = "3";

	/** 权限类型-科室 */
	public static final String CALENDAR_AUTH_TYPE_DEPT = "1";
	/** 权限类型-局领导 */
	public static final String CALENDAR_AUTH_TYPE_LEADER = "2";

	
	/** 日程保存  version*/
	public static final String WORKOUTLINE_VERSION = "00000000000000000000000000000000";
	/** 是否发送短信  0不提醒  1提醒*/
	public static final int WORKOUTLINE_ISSMSALARM=1;
	public static final int WORKOUTLINE_NOTSMSALARM=0;

	/**
	 * 根据日期周几值获取汉字
	 * @param dayOfWeek
	 * @return
	 */
	public static String getWeekName(int dayOfWeek){
		switch(dayOfWeek){
		case 1 : return "星期日";
		case 2 : return "星期一";
		case 3 : return "星期二";
		case 4 : return "星期三";
		case 5 : return "星期四";
		case 6 : return "星期五";
		case 7 : return "星期六";
		}
		return "";
	}

}
