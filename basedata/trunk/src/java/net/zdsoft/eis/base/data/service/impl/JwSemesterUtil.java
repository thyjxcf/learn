package net.zdsoft.eis.base.data.service.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import net.zdsoft.eis.base.data.util.ReadSemesterCfgXml;
import net.zdsoft.keel.util.DateUtils;

/* 
 * 教育局端学年学期维护工具类，在spring中配置，id="jwSemUtil"
 * 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: JwSemesterUtil.java,v 1.3 2006/08/30 07:11:17 dongzk Exp $
 */
public class JwSemesterUtil {
	private GregorianCalendar gc;

	public JwSemesterUtil() {
		gc = new GregorianCalendar();
	}

	/**
	 * 得到上一学年
	 * @param acadyear 本学年 如：2005-2006
	 * @return String
	 */
	public String getPreAcadyear(String acadyear) {
		String endYear = acadyear.substring(0, 4);
		String startYear = String.valueOf(Integer.parseInt(endYear) - 1);
		String acadYear = startYear + "-" + endYear;

		return acadYear;
	}

	/**
	 * 得到下一学年
	 * @param acadyear 本学年 如：2005-2006
	 * @return String
	 */
	public String getNextAcadyear(String acadyear) {
		String startYear = acadyear.substring(5);
		String endYear = String.valueOf(Integer.parseInt(startYear) + 1);
		String acadYear = startYear + "-" + endYear;

		return acadYear;
	}

	/**
	 * 从配置文件中获得指定学年、学期的各项默认值
	 * @param acadyear 学年，格式为：2005-2006
	 * @param semester 学期，微代码表示，1第一学期，2第二学期
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public Map getValueFromXml(String acadyear, String semester) {
		Map map = ReadSemesterCfgXml.getXmlDefaultValue(semester);
		if (map == null || map.size() == 0) {
			return null;
		}

		//由学年得到学年份(2005-2006)
		String startYear = acadyear.substring(0, 4);
		String endYear = acadyear.substring(5);

		//转换Map中的日期字段为Date对象
		String workbegin, workend, semesterbegin, semesterend, registerdate;
		if ("2".equals(semester)) {
			workbegin = endYear + "-" + (String) map.get("workbegin");
			workend = endYear + "-" + (String) map.get("workend");
			semesterbegin = endYear + "-" + (String) map.get("semesterbegin");
			semesterend = endYear + "-" + (String) map.get("semesterend");
			registerdate = endYear + "-" + (String) map.get("registerdate");

		} else {
			workbegin = startYear + "-" + (String) map.get("workbegin");
			workend = endYear + "-" + (String) map.get("workend");
			semesterbegin = startYear + "-" + (String) map.get("semesterbegin");
			semesterend = endYear + "-" + (String) map.get("semesterend");
			registerdate = startYear + "-" + (String) map.get("registerdate");
		}
		map.put("workbegin", DateUtils.string2Date(workbegin));
		map.put("workend", DateUtils.string2Date(workend));
		map.put("semesterbegin", DateUtils.string2Date(semesterbegin));
		map.put("semesterend", DateUtils.string2Date(semesterend));
		map.put("registerdate", DateUtils.string2Date(registerdate));
		return map;
	}

	/**
	 * 由一个日期得到明年的同一天（是否润年不考虑在内）
	 * @param date
	 * @return
	 */
	public Date getNextYearDate(Date date) {
		if (date == null) {
			return null;
		}

		gc.setTime(date);
		int year = gc.get(GregorianCalendar.YEAR);
		int nextYear = year + 1;

		gc.set(GregorianCalendar.YEAR, nextYear);
		Date nextdate = gc.getTime();

		return nextdate;
	}

	/**
	 * 判断开始日期是否小于结束日期
	 * @param workbegin 工作开始日期
	 * @param workend 工作结束日期
	 * @return boolean true?符合逻辑:不符合逻辑
	 */
	public boolean isLogicDate(Date workbegin, Date workend) {
		long wBegin = workbegin.getTime();
		long wEnd = workend.getTime();

		if (wBegin > wEnd) {
			return false;
		}
		return true;
	}

}
