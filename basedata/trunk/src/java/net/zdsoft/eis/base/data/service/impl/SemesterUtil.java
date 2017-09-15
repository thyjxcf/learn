package net.zdsoft.eis.base.data.service.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.common.entity.SchoolSemester;
import net.zdsoft.eis.base.data.service.BaseSchoolSemesterService;
import net.zdsoft.eis.base.data.util.ReadSemesterCfgXml;
import net.zdsoft.keel.util.DateUtils;

/* 
 * 学年学期工具类，在spring配置文件中配置，id="semesterUtil"
 * 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: SemesterUtil.java,v 1.13 2006/11/06 10:19:02 dongzk Exp $
 */
public class SemesterUtil {
    Logger log = LoggerFactory.getLogger(SemesterUtil.class);

    private BaseSchoolSemesterService baseSchoolSemesterService;
    private GregorianCalendar gc;

    public SemesterUtil() {
        gc = new GregorianCalendar();
    }

    public void setBaseSchoolSemesterService(BaseSchoolSemesterService baseSchoolSemesterService) {
        this.baseSchoolSemesterService = baseSchoolSemesterService;
    }

    /**
     * 得到指定学校要添加的下一下学年学期DTO
     * 
     * @param schid 学校ID
     * @return BasicSemesterDto
     */
    @SuppressWarnings("unchecked")
    public SchoolSemester getNextSemester(String schid) {
        SchoolSemester dto = new SchoolSemester();

        SchoolSemester semester = baseSchoolSemesterService.getMaxSemester(schid);
        // 判断数据库中是否已有本校的学年学期记录，若没有则默认生成一个
        if (null == semester) {
            gc.setTime(new Date());
            int endYear = gc.get(GregorianCalendar.YEAR);
            int startYear = endYear - 1;
            String acadYear = String.valueOf(startYear) + "-" + String.valueOf(endYear);
            Map map = getValueFromXml(acadYear, "2");
            Date beginDate = (Date) map.get("semesterbegin");
            Date endDate = (Date) map.get("semesterend");
            Date date = new Date();
            // 如果d1 >= 0 and d2 <= 0 表示在这个学期内
            int d1 = net.zdsoft.keel.util.DateUtils.compareIgnoreSecond(date, beginDate);
            int d2 = net.zdsoft.keel.util.DateUtils.compareIgnoreSecond(endDate, date);
            if (d1 >= 0 && d2 >= 0) {
                dto.setSemester("2"); // 学期微代码表示:1第一学期，2第二学期
            } else {
                dto.setSemester("1"); // 学期微代码表示:1第一学期，2第二学期
            }
            dto.setAcadyear(acadYear); // 学年格式为:2005-2006

        } else {
            if ("1".equals(semester.getSemester())) {
                dto.setAcadyear(semester.getAcadyear());
                dto.setSemester("2");
            } else {
                String acadYear2 = this.getNextAcadyear(semester.getAcadyear());

                dto.setAcadyear(acadYear2);
                dto.setSemester("1");
            }
        }

        return dto;
    }

    /**
     * 得到上一学年
     * 
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
     * 
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
     * 根据入学学年和学制得到毕业学年（格式为：2006-2007）
     * 
     * @param acadyear 入学学年 2004-2005
     * @param schoolinglen 学制 3
     * @return String
     */
    public String getGraduateAcadyear(String acadyear, int schoolinglen) {
        if (acadyear == null || "".equals(acadyear) || schoolinglen < 1)
            return null;

        String tempStartYear = acadyear.substring(0, 4);
        String tempEndYear = acadyear.substring(5);
        String startYear = String.valueOf(Integer.parseInt(tempStartYear) + (schoolinglen - 1));
        String endYear = String.valueOf(Integer.parseInt(tempEndYear) + (schoolinglen - 1));

        return startYear + "-" + endYear;
    }

    /**
     * 由一个日期得到明年的同一天（是否润年不考虑在内）
     * 
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
     * 
     * @param workbegin 工作开始日期
     * @param workend 工作结束日期
     * @param semesterbegin 学期开始日期
     * @param semesterend 学期结束日期
     * @return boolean true?符合逻辑:不符合逻辑
     */
    public boolean isLogicDate(Date workbegin, Date workend, Date semesterbegin, Date semesterend) {
        long wBegin = workbegin.getTime();
        long wEnd = workend.getTime();
        long sBegin = semesterbegin.getTime();
        long sEnd = semesterend.getTime();

        if ((wBegin > wEnd) || (sBegin > sEnd)) {
            return false;
        }
        return true;
    }

    /**
     * 从配置文件中获得指定学年、学期的各项默认值
     * 
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

        // 由学年得到学年份(2005-2006)
        String startYear = acadyear.substring(0, 4);
        String endYear = acadyear.substring(5);

        // 转换Map中的日期字段为Date对象
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

    // 测试
    public static void main(String[] srgs) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        int endYear = gc.get(GregorianCalendar.YEAR);
        int startYear = endYear - 1;
        String acadYear = String.valueOf(startYear) + "-" + String.valueOf(endYear);

        System.out.println(acadYear);

        String start = acadYear.substring(5);
        String end = String.valueOf(Integer.parseInt(start) + 1);
        System.out.println(start);
        System.out.println(end);

        gc.set(GregorianCalendar.YEAR, startYear);
        Date date = gc.getTime();
        System.out.println(date);

    }

}
