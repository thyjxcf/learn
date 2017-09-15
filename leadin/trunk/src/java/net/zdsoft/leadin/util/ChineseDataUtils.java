package net.zdsoft.leadin.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;

public class ChineseDataUtils {
	final static long[] lunarInfo = new long[] { 0x04bd8, 0x04ae0, 0x0a570,
			0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
			0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0,
			0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50,
			0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566,
			0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0,
			0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4,
			0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550,
			0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950,
			0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260,
			0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0,
			0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
			0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40,
			0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3,
			0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960,
			0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0,
			0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9,
			0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0,
			0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65,
			0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0,
			0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2,
			0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0 };

	/**
	 * 根据传入的年份算出这个年份的总天数
	 * @param y
	 * @return
	 */
	final public static int lYearDays(int y)// ====== 传回农历 y年的总天数
	{
		int i, sum = 348;
		for (i = 0x8000; i > 0x8; i >>= 1) {
			if ((lunarInfo[y - 1900] & i) != 0)
				sum += 1;
		}
		return (sum + leapDays(y));
	}

	final private static int leapDays(int y)// ====== 传回农历 y年闰月的天数
	{
		if (leapMonth(y) != 0) {
			if ((lunarInfo[y - 1900] & 0x10000) != 0)
				return 30;
			else
				return 29;
		} else
			return 0;
	}

	final private static int leapMonth(int y)// ====== 传回农历 y年闰哪个月 1-12 , 没闰传回 0
	{
		return (int) (lunarInfo[y - 1900] & 0xf);
	}

	final private static int monthDays(int y, int m)// ====== 传回农历 y年m月的总天数
	{
		if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
			return 29;
		else
			return 30;
	}

	/**
	 * 根据出入的年份，返回生肖
	 * @param y
	 * @return
	 */
	final public static String AnimalsYear(int y)// ====== 传回农历 y年的生肖
	{
		final String[] Animals = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇",
				"马", "羊", "猴", "鸡", "狗", "猪" };
		return Animals[(y - 4) % 12];
	}

	final private static String cyclicalm(int num)// ====== 传入月日的offset 传回干支,
	// 0=甲子
	{
		final String[] Gan = new String[] { "甲", "乙", "丙", "丁", "戊", "己", "庚",
				"辛", "壬", "癸" };
		final String[] Zhi = new String[] { "子", "丑", "寅", "卯", "辰", "巳", "午",
				"未", "申", "酉", "戌", "亥" };
		return (Gan[num % 10] + Zhi[num % 12]);
	}

	/**
	 * 传入年，返回干支，如2008，返回戊子
	 * @param y
	 * @return
	 */
	final public static String cyclical(int y)// ====== 传入 offset 传回干支, 0=甲子
	{
		int num = y - 1900 + 36;
		return (cyclicalm(num));
		
	}

	/**
	 * 根据出入的年月日返回日期
	 * @param year 年
	 * @param month 月 0-11
	 * @param day 日 1-31
	 * @return
	 */
	final private static Date getDateByYMD(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		return c.getTime();
	}

	final public long[] Lunar(int y, int m)// 传出农历.year0 .month1 .day2
	// .yearCyl3 .monCyl4
	// .dayCyl5 .isLeap6
	{
		final int[] year20 = new int[] { 1, 4, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1 };
		final int[] year19 = new int[] { 0, 3, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0 };
		final int[] year2000 = new int[] { 0, 3, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1 };
		long[] nongDate = new long[7];
		int i = 0, temp = 0, leap = 0;
		Date baseDate = getDateByYMD(1900, 0, 31);
		Date objDate = getDateByYMD(y, m - 1, 1);
		long offset = (objDate.getTime() - baseDate.getTime()) / 86400000L;
		if (y < 2000)
			offset += year19[m - 1];
		if (y > 2000)
			offset += year20[m - 1];
		if (y == 2000)
			offset += year2000[m - 1];
		nongDate[5] = offset + 40;
		nongDate[4] = 14;
		for (i = 1900; i < 2050 && offset > 0; i++) {
			temp = lYearDays(i);
			offset -= temp;
			nongDate[4] += 12;
		}
		if (offset < 0) {
			offset += temp;
			i--;
			nongDate[4] -= 12;
		}
		nongDate[0] = i;
		nongDate[3] = i - 1864;
		leap = leapMonth(i); // 闰哪个月
		nongDate[6] = 0;
		for (i = 1; i < 13 && offset > 0; i++) {
			// 闰月
			if (leap > 0 && i == (leap + 1) && nongDate[6] == 0) {
				--i;
				nongDate[6] = 1;
				temp = leapDays((int) nongDate[0]);
			} else {
				temp = monthDays((int) nongDate[0], i);
			}
			// 解除闰月
			if (nongDate[6] == 1 && i == (leap + 1))
				nongDate[6] = 0;
			offset -= temp;
			if (nongDate[6] == 0)
				nongDate[4]++;
		}
		if (offset == 0 && leap > 0 && i == leap + 1) {
			if (nongDate[6] == 1) {
				nongDate[6] = 0;
			} else {
				nongDate[6] = 1;
				--i;
				--nongDate[4];
			}
		}
		if (offset < 0) {
			offset += temp;
			--i;
			--nongDate[4];
		}
		nongDate[1] = i;
		nongDate[2] = offset + 1;
		return nongDate;
	}

	/**
	 * 出入日期转化
	 * @param y 年
	 * @param m 月，1-12
	 * @param d 日，1-31
	 * @return year0 .month1 .day2 .yearCyl3 .monCyl4 .dayCyl5 .isLeap6
	 */
	final public static long[] calElement(int y, int m, int d)
	{
		long[] nongDate = new long[7];
		int i = 0, temp = 0, leap = 0;
		// Date baseDate = new Date(0,0,31);
		Date baseDate = getDateByYMD(1900, 0, 31);
		Date objDate = getDateByYMD(y, m - 1, d);
		long offset = (objDate.getTime() - baseDate.getTime()) / 86400000L;
		nongDate[5] = offset + 40;
		nongDate[4] = 14;
		for (i = 1900; i < 2050 && offset > 0; i++) {
			temp = lYearDays(i);
			offset -= temp;
			nongDate[4] += 12;
		}
		if (offset < 0) {
			offset += temp;
			i--;
			nongDate[4] -= 12;
		}
		nongDate[0] = i;
		nongDate[3] = i - 1864;
		leap = leapMonth(i); // 闰哪个月
		nongDate[6] = 0;
		for (i = 1; i < 13 && offset > 0; i++) {
			// 闰月
			if (leap > 0 && i == (leap + 1) && nongDate[6] == 0) {
				--i;
				nongDate[6] = 1;
				temp = leapDays((int) nongDate[0]);
			} else {
				temp = monthDays((int) nongDate[0], i);
			}
			// 解除闰月
			if (nongDate[6] == 1 && i == (leap + 1))
				nongDate[6] = 0;
			offset -= temp;
			if (nongDate[6] == 0)
				nongDate[4]++;
		}
		if (offset == 0 && leap > 0 && i == leap + 1) {
			if (nongDate[6] == 1) {
				nongDate[6] = 0;
			} else {
				nongDate[6] = 1;
				--i;
				--nongDate[4];
			}
		}
		if (offset < 0) {
			offset += temp;
			--i;
			--nongDate[4];
		}
		nongDate[1] = i;
		nongDate[2] = offset + 1;
		return nongDate;
	}
	
	/**
	 * 根据传入的月份，转为中文，1-12
	 * @param month
	 * @return
	 */
	public static String getChinaMonth(int month){
		String n = ""; 
		switch (month) {
		case 1:
			n = "一";
			break;
		case 2:
			n = "二";
			break;
		case 3:
			n = "三";
			break;
		case 4:
			n = "四";
			break;
		case 5:
			n = "五";
			break;
		case 6:
			n = "六";
			break;
		case 7:
			n = "七";
			break;
		case 8:
			n = "八";
			break;
		case 9:
			n = "九";
			break;
		case 10:
			n = "十";
			break;
		case 11:
			n = "十一";
			break;
		case 12:
			n = "十二";
			break;
		}
		return n;
	}
	
	/**
	 * 根据传入的星期，返回中文星期， 1-7，星期天为第一天
	 * @param day
	 * @return
	 */
	public static String getChinaWeek(int day){
		String week = "";
		switch (day) {
		case 1:
			week = "日";
			break;
		case 2:
			week = "一";
			break;
		case 3:
			week = "二";
			break;
		case 4:
			week = "三";
			break;
		case 5:
			week = "四";
			break;
		case 6:
			week = "五";
			break;
		case 7:
			week = "六";
			break;
		}
		return week;	
	}

	/**
	 * 将数字转为中文，如11变为十一，1变为初一
	 * @param day
	 * @return
	 */
	public static String getChinaDay(int day) {
		String a = "";
		if (day == 10)
			return "初十";
		int two = (int) ((day) / 10);
		if (two == 0)
			a = "初";
		if (two == 1)
			a = "十";
		if (two == 2)
			a = "廿";
		if (two == 3)
			a = "卅";
		int one = (int) (day % 10);
		switch (one) {
		case 1:
			a += "一";
			break;
		case 2:
			a += "二";
			break;
		case 3:
			a += "三";
			break;
		case 4:
			a += "四";
			break;
		case 5:
			a += "五";
			break;
		case 6:
			a += "六";
			break;
		case 7:
			a += "七";
			break;
		case 8:
			a += "八";
			break;
		case 9:
			a += "九";
			break;
		}
		return a;
	}
	
	/**
	 * 取得公历信息
	 * @param c
	 * @return 2008年7月9日 周三
	 */
	public static String getYearAndWeek(Calendar c){
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONDAY) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String week = getChinaWeek(c.get(Calendar.DAY_OF_WEEK));		
		return year + "年" + month + "月" + day + "日" + " 周" + week;
	}
	
	/**
	 * 取得农历信息
	 * @param c
	 * @return 戊子(鼠)年 六月初七
	 */
	public static String getChinaDate(Calendar c){
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONDAY) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		long[] l = calElement(year, month, day);		
		return cyclical(year) + "(" + AnimalsYear(year) + ")年" + " " + getChinaMonth((int)l[1]) + "月" + getChinaDay((int) (l[2]));
	}

	// 传出y年m月d日对应的农历.year0 .month1 .day2 .yearCyl3 .monCyl4 .dayCyl5 .isLeap6
	public static void main(String[] args) {
		Calendar cld = Calendar.getInstance();
		cld.set(2009, 8, 19);
		int year = cld.get(Calendar.YEAR);
		int month = cld.get(Calendar.MONTH) + 1;
		int day = cld.get(Calendar.DAY_OF_MONTH);
		String week = "";
		long[] l = calElement(year, month, day);
		
		week = getChinaWeek(cld.get(Calendar.DAY_OF_WEEK));
		String n = getChinaMonth((int)l[1]);
		
		try {
			String a = "北京时间： " + year + "年" + month + "月" + day
					+ "日           星期" + week + "　农历" + n + "月"
					+ getChinaDay((int) (l[2]));
			String b = "北京时间： " + year + "年" + month + "月" + day + "日　星期"
					+ week + "　农历" + n + "月" + getChinaDay((int) (l[2]));
			BufferedWriter outout = new BufferedWriter(new FileWriter(
					"rili.html", false));
			outout.write(a);
			System.out.println(a);
			System.out.println(b);
			outout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
