/* 
 * @(#)DateInfoDaoImpl.java    Created on May 31, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.DateInfoDao;
import net.zdsoft.eis.base.common.entity.DateInfo;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.DateUtils;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 31, 2011 11:40:48 AM $
 */
public class DateInfoDaoImpl extends BaseDao<DateInfo> implements DateInfoDao {
	private static final String SQL_FIND_TOTAL_WEEKNUM_BY_SCHOOLID_ACADYEAR_SEMESTER = "SELECT max(week) FROM sys_date_info "
			+ "WHERE sch_id=? AND acadyear=? AND semester=?";

	private static final String SQL_FIND_DATEINFO_BY_ID = "SELECT * FROM sys_date_info WHERE id=?";

	private static final String SQL_FIND_DATEINFOS_BY_IDS = "SELECT * FROM sys_date_info WHERE id IN";

	private static final String SQL_FIND_DATEINFOS = "SELECT * FROM sys_date_info WHERE sch_id=?";

	private static final String SQL_FIND_DATEINFOS_BY_ACADYEAR_SEMESTER = "SELECT * FROM sys_date_info WHERE sch_id=? AND acadyear=? AND semester=?";

	private static final String SQL_FIND_DATEINFOS_BY_SCHID_DATE = "SELECT * FROM sys_date_info WHERE sch_id=? AND info_date=?";

	private static final String SQL_FIND_DATEINFOS_BY_DATE = "select * from sys_date_info where sch_id=? AND info_date between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd')";

	@Override
	public DateInfo setField(ResultSet rs) throws SQLException {
		DateInfo dateInfo = new DateInfo();
		dateInfo.setId(rs.getString("id"));
		dateInfo.setSchid(rs.getString("sch_id"));
		dateInfo.setDate(rs.getTimestamp("info_date"));
		dateInfo.setAcadyear(rs.getString("acadyear"));
		dateInfo.setSemester(rs.getString("semester"));
		dateInfo.setWeek(rs.getInt("week"));
		dateInfo.setWeekday(rs.getString("weekday"));
		dateInfo.setIsfeast(rs.getString("is_feast"));
		dateInfo.setFeastname(rs.getString("feastname"));
		return dateInfo;
	}

	public int getTotalWeekNum(String unitId, String acadyear, String semester) {
		return queryForInt(
				SQL_FIND_TOTAL_WEEKNUM_BY_SCHOOLID_ACADYEAR_SEMESTER,
				new Object[] { unitId, acadyear, semester });
	}

	public DateInfo getDateInfo(String dateInfoId) {
		return query(SQL_FIND_DATEINFO_BY_ID, dateInfoId, new SingleRow());
	}

	public DateInfo getDateInfo(String unitId, Date infoDate) {
		Date day = DateUtils.string2Date(DateUtils.date2StringByDay(infoDate));
		return query(SQL_FIND_DATEINFOS_BY_SCHID_DATE, new Object[] { unitId,
				day }, new SingleRow());
	}

	public List<DateInfo> getDateInfos(String unitId) {
		return query(SQL_FIND_DATEINFOS, unitId, new MultiRow());
	}

	public List<DateInfo> getDateInfos(String unitId, String beginDate,
			String endDate) {
		return query(SQL_FIND_DATEINFOS_BY_DATE, new Object[] { unitId,
				beginDate, endDate }, new MultiRow());
	}

	public Map<String, DateInfo> getDateInfoMap(String[] dateInfoIds) {
		return queryForInSQL(SQL_FIND_DATEINFOS_BY_IDS, null, dateInfoIds,
				new MapRow());
	}

	public Map<String, DateInfo> getDateInfoMap(String unitId, String acadyear,
			String semester) {
		return queryForMap(SQL_FIND_DATEINFOS_BY_ACADYEAR_SEMESTER,
				new Object[] { unitId, acadyear, semester },
				new MapRowMapper<String, DateInfo>() {

					@Override
					public String mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getInt("week") + rs.getString("weekday");
					}

					@Override
					public DateInfo mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return setField(rs);
					}
				});
	}
	
	public List<DateInfo> getDateInfoByWeek(String schoolId, String acadyear, String semester, int week){
    	String sql = "select * from sys_date_info"
    			+" where sch_id=? and acadyear=? and semester=? and week=? order by weekday";
    	return query(sql, new Object[]{schoolId,acadyear,semester,week}, new MultiRow());
    }
    
    public List<DateInfo> getDateListByMon(String schoolId, String acadyear, String semester, int month){
    	String sql = "select * from sys_date_info"
    			+" where sch_id=? and acadyear=? and semester=? and month=? order by week,weekday";
    	return query(sql, new Object[]{schoolId,acadyear,semester,month}, new MultiRow());
    }
    
    public int getWeeksByMon(String schoolId, String acadyear, String semester, int month){
    	String sql = "select count(distinct week) from sys_date_info"
    			+" where sch_id=? and acadyear=? and semester=? and month=?";
    	return queryForInt(sql, new Object[]{schoolId,acadyear,semester,month});
    }

    @Override
    public DateInfo getNextDate(String schId, String acadyear, String semester, Date date){
        String sql = "select * from sys_date_info"
                +" where sch_id=? and acadyear=? and semester=? and is_feast='N' and to_date('"+DateUtils.date2StringByDay(date)+" 23:59:59"+"','yyyy-mm-dd hh24:mi:ss')<info_date order by info_date";
        List<DateInfo> ds;
        try {
            ds = queryForTop(sql, new Object[]{schId,acadyear,semester}, 
                    new int[]{Types.CHAR, Types.CHAR, Types.CHAR}, new MultiRow(), 1);
            if(CollectionUtils.isNotEmpty(ds)){
                return ds.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

	@Override
	public DateInfo getDateByWeekAndWeekday(String schId, String acadyear,
			String semester, int week, String weekday) {
		String sql = "select * from sys_date_info where sch_id=? and acadyear=? and semester=? and week=? and weekday=?";
		return query(sql,new Object[]{schId,acadyear,semester,week,weekday},new SingleRow());
	}
    
    
}
