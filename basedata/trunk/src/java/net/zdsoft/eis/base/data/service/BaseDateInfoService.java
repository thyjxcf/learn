package net.zdsoft.eis.base.data.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.DateInfo;
import java.util.Date;

public interface BaseDateInfoService {

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
     * 根据学校，学年，学期更新日期数据
     * 
     * @param schoolId 学校id
     * @param acadyear 学年
     * @param semester 学期
     * @param isSun TODO
     * @return
     */
    public void saveDateInfo(String schoolId, String acadyear, String semester, Date beginDate,
            Date endDate, boolean isSun);

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
     * 根据单位id查询日期数据（教育局单位使用）
     * 没有数据则按自然年初始化日期数据
     * @param schoolId 学校id
     * @return
     */
    public List<DateInfo> getEduDateList(String unitId) ;
    
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
     * 批量更新节假日信息(只更新 是否节假日,节假日名称), 其他字段不变
     */
    public void updateDateList(List<DateInfo> dateList) ;

	public void updateDateList1(List<DateInfo> dateInfoList);
   
	/**
	 * 取得日期之后的第一个工作日
	 * @param schId
	 * @param acadyear
	 * @param semester
	 * @param date
	 * @return
	 */
	public DateInfo getNextDate(String schId, String acadyear, String semester, Date date);
	/**
	 * 获得当前日期信息
	 */
	public DateInfo getDate(String schId, String acadyear, String semester, Date date);
}
