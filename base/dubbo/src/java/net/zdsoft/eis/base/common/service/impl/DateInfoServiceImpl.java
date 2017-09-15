/* 
 * @(#)DateInfoServiceImpl.java    Created on May 31, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.DateInfoDao;
import net.zdsoft.eis.base.common.entity.DateInfo;
import net.zdsoft.eis.base.common.service.DateInfoService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 31, 2011 1:34:49 PM $
 */
public class DateInfoServiceImpl implements DateInfoService {
	private DateInfoDao dateInfoDao;

	public void setDateInfoDao(DateInfoDao dateInfoDao) {
		this.dateInfoDao = dateInfoDao;
	}

	public int getTotalWeekNum(String unitId, String acadyear, String semester) {
		return dateInfoDao.getTotalWeekNum(unitId, acadyear, semester);
	}

	public DateInfo getDateInfo(String dateInfoId) {
		return dateInfoDao.getDateInfo(dateInfoId);
	}

	public DateInfo getDateInfo(String unitId, Date infoDate) {
		return dateInfoDao.getDateInfo(unitId, infoDate);
	}

	public DateInfo getCurrentDateInfo(String unitId) {
		return dateInfoDao.getDateInfo(unitId, new Date());
	}

	public List<DateInfo> getDateInfos(String unitId) {
		return dateInfoDao.getDateInfos(unitId);
	}

	public List<DateInfo> getDateInfos(String unitId, String beginDate,
			String endDate) {
		return dateInfoDao.getDateInfos(unitId, beginDate, endDate);
	}

	public Map<String, DateInfo> getDateInfoMap(String[] dateInfoIds) {
		return dateInfoDao.getDateInfoMap(dateInfoIds);
	}

	public Map<String, DateInfo> getDateInfoMap(String unitId, String acadyear,
			String semester) {
		return dateInfoDao.getDateInfoMap(unitId, acadyear, semester);
	}
	
	public List<DateInfo> getDateInfoByWeek(String schoolId, String acadyear, String semester, int week){
		return dateInfoDao.getDateInfoByWeek(schoolId, acadyear, semester, week);
	}

    @Override
    public DateInfo getNextDate(String schId, String acadyear, String semester, Date date) {
       return dateInfoDao.getNextDate(schId, acadyear, semester, date);
    }

	@Override
	public DateInfo getDateByWeekAndWeekday(String schId, String acadyear,
			String semester, int week, String weekday) {
		return dateInfoDao.getDateByWeekAndWeekday(schId, acadyear, semester, week, weekday);
	}

}
