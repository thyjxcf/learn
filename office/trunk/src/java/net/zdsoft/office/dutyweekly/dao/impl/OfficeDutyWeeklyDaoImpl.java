package net.zdsoft.office.dutyweekly.dao.impl;

import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.dutyweekly.entity.OfficeDutyWeekly;
import net.zdsoft.office.dutyweekly.dao.OfficeDutyWeeklyDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_weekly
 * @author 
 * 
 */
public class OfficeDutyWeeklyDaoImpl extends BaseDao<OfficeDutyWeekly> implements OfficeDutyWeeklyDao{

	@Override
	public OfficeDutyWeekly setField(ResultSet rs) throws SQLException{
		OfficeDutyWeekly officeDutyWeekly = new OfficeDutyWeekly();
		officeDutyWeekly.setId(rs.getString("id"));
		officeDutyWeekly.setUnitId(rs.getString("unit_id"));
		officeDutyWeekly.setCreateUserId(rs.getString("create_user_id"));
		officeDutyWeekly.setCreateTime(rs.getTimestamp("create_time"));
		officeDutyWeekly.setWeek(rs.getInt("week"));
		officeDutyWeekly.setState(rs.getInt("state"));
		officeDutyWeekly.setWeekStartTime(rs.getTimestamp("week_start_time"));
		officeDutyWeekly.setWeekEndTime(rs.getTimestamp("week_end_time"));
		officeDutyWeekly.setDutyGrade(rs.getString("duty_grade"));
		officeDutyWeekly.setDutyClass(rs.getString("duty_class"));
		officeDutyWeekly.setDutyTeacher(rs.getString("duty_teacher"));
		officeDutyWeekly.setYear(rs.getString("acadyear"));
		officeDutyWeekly.setSemester(rs.getInt("semester"));
		return officeDutyWeekly;
	}

	@Override
	public OfficeDutyWeekly save(OfficeDutyWeekly officeDutyWeekly){
		String sql = "insert into office_duty_weekly(id, unit_id, create_user_id, create_time, week,state, week_start_time, week_end_time,duty_grade, duty_class, duty_teacher,acadyear,semester) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeDutyWeekly.getId())){
			officeDutyWeekly.setId(createId());
		}
		Object[] args = new Object[]{
			officeDutyWeekly.getId(), officeDutyWeekly.getUnitId(), 
			officeDutyWeekly.getCreateUserId(), officeDutyWeekly.getCreateTime(), 
			officeDutyWeekly.getWeek(),officeDutyWeekly.getState(), officeDutyWeekly.getWeekStartTime(), 
			officeDutyWeekly.getWeekEndTime(),officeDutyWeekly.getDutyGrade(), officeDutyWeekly.getDutyClass(), 
			officeDutyWeekly.getDutyTeacher(),officeDutyWeekly.getYear(),officeDutyWeekly.getSemester()
		};
		update(sql, args);
		return officeDutyWeekly;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_duty_weekly where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeDutyWeekly officeDutyWeekly){
		String sql = "update office_duty_weekly set unit_id = ?, create_user_id = ?, create_time = ?, week = ?,state=?, week_start_time = ?, week_end_time = ?,duty_grade=?, duty_class = ?, duty_teacher = ?,acadyear=?,semester=? where id = ?";
		Object[] args = new Object[]{
			officeDutyWeekly.getUnitId(), officeDutyWeekly.getCreateUserId(), 
			officeDutyWeekly.getCreateTime(), officeDutyWeekly.getWeek(),officeDutyWeekly.getState(), 
			officeDutyWeekly.getWeekStartTime(), officeDutyWeekly.getWeekEndTime(), officeDutyWeekly.getDutyGrade(),
			officeDutyWeekly.getDutyClass(), officeDutyWeekly.getDutyTeacher(),officeDutyWeekly.getYear(),officeDutyWeekly.getSemester(), 
			officeDutyWeekly.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeDutyWeekly getOfficeDutyWeeklyById(String id){
		String sql = "select * from office_duty_weekly where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeDutyWeekly> getOfficeDutyWeeklyMapByIds(String[] ids){
		String sql = "select * from office_duty_weekly where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeDutyWeekly> getOfficeDutyWeeklyList(){
		String sql = "select * from office_duty_weekly";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeDutyWeekly> getOfficeDutyWeeklyPage(Pagination page){
		String sql = "select * from office_duty_weekly";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeDutyWeekly> getOfficeDutyWeeklyByUnitIdList(String unitId){
		String sql = "select * from office_duty_weekly where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeDutyWeekly> getOfficeDutyWeeklyByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_duty_weekly where unit_id = ? order by create_time";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeDutyWeekly> getOfficeDutyWeeklyByUnitIdPagess(
			String unitId, String year, String semester, Pagination page) {
		String sql = "select * from office_duty_weekly where unit_id = ? and acadyear=? and semester=? order by create_time";
		return query(sql, new Object[]{unitId,year,semester }, new MultiRow(), page);
	}

	@Override
	public boolean isExistConflicts(String unitId,String year,String semester, String id, Date beginTime,
			Date endTime) {
		String sql = "select count(1) from office_duty_weekly where unit_id=? and acadyear=? and semester=? and ((week_start_time <= ? and week_end_time >= ?) or (week_start_time <= ? and week_end_time >= ?) or (week_start_time >= ? and week_end_time <= ?))";
		
		String sql1 = "select count(1) from office_duty_weekly where unit_id=? and acadyear=? and semester=? and  id != ? and ((week_start_time <= ? and week_end_time >= ?) or (week_start_time <= ? and week_end_time >= ?) or (week_start_time >= ? and week_end_time <= ?))";
		int i = 0;
		if(StringUtils.isNotBlank(id)){
			i = queryForInt(sql1, new Object[] {unitId,year,semester, id, beginTime, beginTime, endTime, endTime, beginTime, endTime});
		}else{
			i = queryForInt(sql, new Object[] {unitId,year,semester, beginTime, beginTime, endTime, endTime, beginTime, endTime});
		}
		if(i > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean isExistConflictss(String unitId,String year,String semester, String id, Integer week) {
		String sql = "select count(1) from office_duty_weekly where unit_id=? and acadyear=? and semester=? and week=?";
		
		String sql1 = "select count(1) from office_duty_weekly where unit_id=? and acadyear=? and semester=? and  id != ? and week =?";
		int i = 0;
		if(StringUtils.isNotBlank(id)){
			i = queryForInt(sql1, new Object[] {unitId,year,semester, id, week});
		}else{
			i = queryForInt(sql, new Object[] {unitId,year,semester, week});
		}
		if(i > 0){
			return true;
		}
		return false;
	}

	@Override
	public OfficeDutyWeekly getOfficeDutyWeeklyByUnitIdAndDate(String unitId,
			Date dutyDate) {
		String sql = "select * from office_duty_weekly where unit_id=? and week_start_time <= ? and week_end_time >= ?";
		return query(sql, new Object[]{unitId,dutyDate,dutyDate}, new SingleRow());
	}

	@Override
	public OfficeDutyWeekly getOfficeDutyWeeklyByUnitIdAnds(String unitId,
			String year, String semester, String week) {
		String sql="select * from office_duty_weekly where unit_id=? and acadyear=? and semester=? and week=?";
		return query(sql, new Object[]{unitId,year,semester,week}, new SingleRow());
	}

	@Override
	public Map<String, OfficeDutyWeekly> getOfficeDMap(String[] weeklyIds) {
		String sql = "select * from office_duty_weekly where id in";
		return queryForInSQL(sql,null, weeklyIds, new MapRowMapper<String, OfficeDutyWeekly>() {

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("id");
			}

			@Override
			public OfficeDutyWeekly mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public String findMaxWeek(String unitId, String year, String semester) {
		String sql="SELECT MAX(week) FROM office_duty_weekly WHERE unit_id=? and acadyear=? and semester=?";
		return queryForString(sql,new Object[]{unitId,year,semester});
	}

	@Override
	public OfficeDutyWeekly getFindMaxWeek(String unitId, String year,
			String semester) {
		String sql="select * from office_duty_weekly where unit_id=? and acadyear=? and semester=? and week=(SELECT MAX(week) FROM office_duty_weekly WHERE unit_id=? and acadyear=? and semester=?)";
		return query(sql, new Object[]{unitId,year,semester,unitId,year,semester}, new SingleRow());
	}
	
}
