/* 
 * @(#)DateInfoService.java    Created on May 31, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.DateInfo;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 31, 2011 1:34:29 PM $
 */
public interface DateInfoService {

    /**
     * 根据单位id取总周次
     * 
     * @param unitId
     * @param acadyear 学年
     * @param semester 学期
     * @return
     */
    public int getTotalWeekNum(String unitId, String acadyear, String semester);

    /**
     * 根据id取日期信息
     * 
     * @param dateInfoId
     * @return
     */
    public DateInfo getDateInfo(String dateInfoId);

    /**
     * 根据学校id和日期取日期信息
     * @param unitId
     * @param infoDate
     * @return
     */
    public DateInfo getDateInfo(String unitId, Date infoDate);
    
    /**
     * 根据学校id取当前日期信息
     * @param unitId
     * @param infoDate
     * @return
     */
    public DateInfo getCurrentDateInfo(String unitId);
    
    /**
     * 根据单位id取日期信息
     * 
     * @param unitId
     * @return
     */
    public List<DateInfo> getDateInfos(String unitId);
    
    /**
     * 根据开始时间和结束时间获取日期信息
     * @param unitId
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<DateInfo> getDateInfos(String unitId, String beginDate,
			String endDate);

    /**
     * 取日期Map
     * 
     * @param dateInfoIds
     * @return
     */
    public Map<String, DateInfo> getDateInfoMap(String[] dateInfoIds);
    
    /**
     * key:周次+第几天
     * @param unitId
     * @param acadyear
     * @param semester
     * @return
     */
    public Map<String, DateInfo> getDateInfoMap(String unitId,String acadyear,String semester);
   
    /**
     * 取某周次的日期信息
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
    
    /**
     * 根据周次和星期取日期
     * @param schId
     * @param acadyear
     * @param semester
     * @param week
     * @param weekday
     * @return
     */
    public DateInfo getDateByWeekAndWeekday(String schId, String acadyear, String semester, int week, String weekday);

    
}
