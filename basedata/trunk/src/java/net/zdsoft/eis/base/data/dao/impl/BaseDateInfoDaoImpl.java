package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.DateInfo;
import net.zdsoft.eis.base.data.dao.BaseDateInfoDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.DateUtils;

public class BaseDateInfoDaoImpl extends BaseDao<DateInfo> implements BaseDateInfoDao {
    private static final String SQL_INSERT_BASEDATEINFO = "INSERT INTO sys_date_info(id,sch_id,info_date,"
            + "acadyear,semester,week,weekday,is_feast,feastname) " + "VALUES(?,?,?,?,?,?,?,?,?)";

    private static final String SQL_DELETE_BASEDATEINFO = "delete sys_date_info where sch_id=?"
            + " and acadyear=? and semester=?";
    //只需更新是否节假日，节假日名称字段
    private static final String SQL_UPDATE_BASEDATEINFO = "update sys_date_info set "
        + " is_feast=?, feastname=?,week=? where id = ?";
    private static final String SQL_UPDATE_BASEDATEINFO1 = "update sys_date_info set "
            + " month=? where id = ?";
    private static final String SQL_GETDATELIST_BASEDATEINFO = "select * from sys_date_info where sch_id=?"
            + " and acadyear=? and semester=? order by info_date";
    
    private static final String SQL_GETDATELIST_BASEDATEINFO_BY_UNITID = "select * from sys_date_info where sch_id=?  " +
    		" and info_date between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') order by info_date";

   
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
        dateInfo.setMonth(rs.getInt("month"));
        return dateInfo;
    }
    
    public List<DateInfo> getDateList(String unitId, Date startDate, Date endDate){
    	return query(SQL_GETDATELIST_BASEDATEINFO_BY_UNITID, new Object[]{unitId, DateUtils.date2String(startDate, "yyyy-MM-dd"), DateUtils.date2String(endDate, "yyyy-MM-dd")}, new MultiRow());
    }

    public List<DateInfo> getDateList(String schoolId, String acadyear, String semester) {
        return query(SQL_GETDATELIST_BASEDATEINFO, new String[] { schoolId, acadyear, semester },
                new MultiRow());
    }
    public void updateDateList(List<DateInfo> dateList) {
        List<Object[]> listOfArgs = new ArrayList<Object[]>();
        for (int i = 0; i < dateList.size(); i++) {
            DateInfo dateInfo = dateList.get(i);
            listOfArgs.add(new Object[] { dateInfo.getIsfeast(), dateInfo.getFeastname(),dateInfo.getWeek()+"", dateInfo.getId()});
        }
        int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.CHAR,Types.CHAR, Types.CHAR };
        batchUpdate(SQL_UPDATE_BASEDATEINFO, listOfArgs, argTypes);
    }
    public void updateDateList1(List<DateInfo> dateList) {
        List<Object[]> listOfArgs = new ArrayList<Object[]>();
        for (int i = 0; i < dateList.size(); i++) {
            DateInfo dateInfo = dateList.get(i);
            listOfArgs.add(new Object[] { dateInfo.getMonth(), dateInfo.getId()});
        }
        int[] argTypes = new int[] { Types.INTEGER, Types.CHAR};
        batchUpdate(SQL_UPDATE_BASEDATEINFO1, listOfArgs, argTypes);
    }
    public void deleteDateInfo(String schoolId, String acadyear, String semester) {
        update(SQL_DELETE_BASEDATEINFO, new Object[] { schoolId, acadyear, semester });
    }
    
    public void batchInsert(List<DateInfo> list){
    	List<Object[]> objs = new ArrayList<Object[]>();
    	for(DateInfo entity : list){
    		if(StringUtils.isBlank(entity.getId())){
    			entity.setId(getGUID());
    		}
    		objs.add(new Object[]{
    				entity.getId(), entity.getSchid(),
                    entity.getDate(), entity.getAcadyear(), entity.getSemester(), entity.getWeek(),
                    entity.getWeekday(), entity.getIsfeast(), entity.getFeastname()
    		});
    	}
    	
    	batchUpdate(SQL_INSERT_BASEDATEINFO, objs, new int[] {
                Types.CHAR, Types.CHAR, Types.DATE, Types.CHAR, Types.CHAR, Types.INTEGER,
                Types.CHAR, Types.CHAR, Types.VARCHAR });
    }

    public void insertDateInfo(String schoolId, String acadyear, String semester, Date beginDate,
            Date endDate, boolean isSun) {
        DateInfo entity;
        Calendar theDate = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();

        // 初始化日期
        theDate.setTime(beginDate);
        calendarEnd.setTime(endDate);
        calendarEnd.add(Calendar.DATE, 1);

        int iWeekday;
        int i = 0;
        int theWeek = 0;
        // 若循环日期大于结束日期
        while (theDate.before(calendarEnd)) {
            i++;
            entity = new DateInfo();
            // 第一天不管是星期几都为第一周
            if(isSun){
            	if (i == 1) {
                    theWeek = 1;
                } else if (theDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) { // 若为星期天，加一周
                    theWeek++;
                }
            }else{
            	if (i == 1) {
                    theWeek = 1;
                } else if (theDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) { // 若为星期一，加一周
                    theWeek++;
                }
            }
            entity.setSchid(schoolId); // 学校
            entity.setAcadyear(acadyear); // 学年
            entity.setSemester(semester); // 学期

            entity.setDate(theDate.getTime()); // 日期

            // 周次已第一学期开始
            entity.setWeek(theWeek); // 周次

            // 老外以星期日为一周的第一天
            iWeekday = theDate.get(Calendar.DAY_OF_WEEK);
            if (iWeekday == 1) {
                entity.setWeekday("7"); // 星期日
            } else {
                entity.setWeekday(Integer.toString(iWeekday - 1)); // 星期日
            }

            if (entity.getWeekday().equals("6") || entity.getWeekday().equals("7")) {
                entity.setIsfeast("Y"); // 是否节假日
            } else {
                entity.setIsfeast("N"); // 是否节假日
            }

            // entity.setFeastname(feastname); //节假日名称

            entity.setId(getGUID());
            update(SQL_INSERT_BASEDATEINFO, new Object[] { entity.getId(), entity.getSchid(),
                    entity.getDate(), entity.getAcadyear(), entity.getSemester(), entity.getWeek(),
                    entity.getWeekday(), entity.getIsfeast(), entity.getFeastname() }, new int[] {
                    Types.CHAR, Types.CHAR, Types.DATE, Types.CHAR, Types.CHAR, Types.INTEGER,
                    Types.CHAR, Types.CHAR, Types.VARCHAR });

            // 下一天
            theDate.add(Calendar.DATE, 1);
        }
        return;
    }

    public List<DateInfo> getDateInfoByWeek(String schoolId, String acadyear, String semester, int week){
    	String sql = "select * from sys_date_info"
    			+" where sch_id=? and acadyear=? and semester=? and week=? order by info_date";
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
    public DateInfo getDate(String schId, String acadyear, String semester,
    		Date date) {
    	String sql = "select * from sys_date_info"
    			+" where sch_id=? and acadyear=? and semester=? and to_char(info_date,'yyyy-mm-dd') = ?";
    	return query(sql, new Object[]{schId,acadyear,semester,DateUtils.date2StringByDay(date)}, new SingleRow());
    }

}
