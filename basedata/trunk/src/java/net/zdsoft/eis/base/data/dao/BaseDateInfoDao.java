package net.zdsoft.eis.base.data.dao;

import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.entity.DateInfo;

public interface BaseDateInfoDao {
    /**
     * 根据学校，学年，学期删除日期数据
     * 
     * @param schoolId 学校id
     * @param acadyear 学年
     * @param semester 学期
     * @return
     */
    public void deleteDateInfo(String schoolId, String acadyear, String semester);
    
    
    /**
     * 批量新增数据
     * @param list
     */
    public void batchInsert(List<DateInfo> list);

    /**
     * 根据学校，学年，学期插入日期数据
     * 
     * @param schoolId 学校id
     * @param acadyear 学年
     * @param semester 学期
     * @param isSun TODO
     * @return
     */
    public void insertDateInfo(String schoolId, String acadyear, String semester, Date beginDate,
            Date endDate, boolean isSun);
    
    /**
     * 根据单位id查询日期数据
     * @param startDate TODO
     * @param endDate TODO
     * @param schoolId 学校id
     * @return
     */
    public List<DateInfo> getDateList(String unitId, Date startDate, Date endDate) ;

    /**
     * 根据学校，学年，学期查询日期数据
     * 
     * @param schoolId 学校id
     * @param acadyear 学年
     * @param semester 学期
     * @return
     */
    public List<DateInfo> getDateList(String schoolId, String acadyear, String semester) ;

    /**
     * 批量更新节假日信息(只更新 是否节假日,节假日名称), 其他字段不变
     */
    public void updateDateList(List<DateInfo> dateList) ;

	public void updateDateList1(List<DateInfo> dateList);
    
    /**
     * 根据学校，学年，月份 学期查询日期数据
     * 
     * @param schoolId 学校id
     * @param acadyear 学年
     * @param semester 学期
     * @return
     */
    public List<DateInfo> getDateListByMon(String schoolId, String acadyear, String semester, int month);
    
    /**
     * 月份下周次数
     * @param schoolId
     * @param acadyear
     * @param semester
     * @param month
     * @return
     */
    public int getWeeksByMon(String schoolId, String acadyear, String semester, int month);
    
    /**
     * 取某周次的日期信息
     * @param schoolId
     * @param acadyear
     * @param semester
     * @param week
     * @return
     */
    public List<DateInfo> getDateInfoByWeek(String schoolId, String acadyear, String semester, int week);
    
    /**
     * 取得日期之后的第一个工作日
     * @param schId
     * @param acadyear
     * @param semester
     * @param date
     * @return
     */
    public DateInfo getNextDate(String schId, String acadyear, String semester, Date date);

	public DateInfo getDate(String schId, String acadyear, String semester,
			Date date);
}
