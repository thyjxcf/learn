package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.data.dao.CountOnlineTimeDao;
import net.zdsoft.eis.base.data.entity.CountOnlineTime;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;

import org.apache.commons.lang.StringUtils;

public class CountOnlineTimeDaoImpl extends BaseDao<CountOnlineTime> implements CountOnlineTimeDao{
	
	private static final String SQL_COUNT_ONLINETIME_INSERT="insert into sys_onlinetime(id, user_id, session_id, login_time, logout_time, online_time,unit_id) values(?,?,?,?,?,?,?)";
	private static final String SQL_COUNT_ONLINETIME_UPDATE="update sys_onlinetime set user_id = ?, session_id = ?, login_time = ?, logout_time = ?, online_time = ?,unit_id=? where id = ?";
	@Override
	public CountOnlineTime setField(ResultSet rs) throws SQLException {
		CountOnlineTime countOnlineTime = new CountOnlineTime();
		countOnlineTime.setId(rs.getString("id"));
		countOnlineTime.setUserId(rs.getString("user_id"));
		countOnlineTime.setSessionId(rs.getString("session_id"));
		countOnlineTime.setLoginTime(rs.getTimestamp("login_time"));
		countOnlineTime.setLogoutTime(rs.getTimestamp("logout_time"));
		countOnlineTime.setOnlineTime(rs.getInt("online_time"));
		countOnlineTime.setUnitId(rs.getString("unit_id"));
		return countOnlineTime;
	}

	@Override
	public void save(CountOnlineTime countOnlineTime) {
		if (StringUtils.isBlank(countOnlineTime.getId())){
			countOnlineTime.setId(createId());
		}
		Object[] args = new Object[]{
		    countOnlineTime.getId(), countOnlineTime.getUserId(),
		    countOnlineTime.getSessionId(), countOnlineTime.getLoginTime(), 
		    countOnlineTime.getLogoutTime(), countOnlineTime.getOnlineTime(),
		    countOnlineTime.getUnitId()
		};
		update(SQL_COUNT_ONLINETIME_INSERT, args);
	}

	@Override
	public void update(CountOnlineTime countOnlineTime) {
		Object[] args = new Object[]{
			    countOnlineTime.getUserId(), countOnlineTime.getSessionId(),
			    countOnlineTime.getLoginTime(), countOnlineTime.getLogoutTime(),
			    countOnlineTime.getOnlineTime(),countOnlineTime.getUnitId(),
			    countOnlineTime.getId()
			};
	    update(SQL_COUNT_ONLINETIME_UPDATE, args);
	}

	@Override
	public CountOnlineTime getCountOnlineTimeBySessId(String sessionId) {
		String sql="select * from sys_onlinetime where session_id=?";
		return query(sql, sessionId, new SingleRow());
	}

	@Override
	public List<CountOnlineTime> getCountOnlineTimeList(String beginTime,String endTime,String[] unitIds) {
		StringBuffer sql = new StringBuffer("select distinct user_id from sys_onlinetime where ((login_time >= to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss') and login_time<=to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') and" +
				" logout_time <= to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss')) or (login_time>=to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss')" +
				" and login_time<=to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') and logout_time>=to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss')" +
				" and logout_time<=to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss')) or (login_time>=to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss')" +
				" and login_time<=to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') and logout_time>=to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss'))" +
				" or (login_time<=to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss') and logout_time>=to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss'))" +
						" or (login_time>=to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss') and login_time<=to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') and logout_time is null))");
		//StringBuffer sql = new StringBuffer("select distinct user_id from count_onlinetime where ("+beginTime+"<=login_time && (login_time<="+endTime+"<=logout_time))");
    	/*if(StringUtils.isNotBlank(subjectName)){
    		sql.append(" and subject_name like '%"+subjectName+"%'");
    	}*/
    	sql.append(" and unit_id in");
        return queryForInSQL(sql.toString(), new String[] {},unitIds, new MultiRowMapper<CountOnlineTime>(){

			@Override
			public CountOnlineTime mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CountOnlineTime countOnlineTime = new CountOnlineTime();
				countOnlineTime.setUserId(rs.getString("user_id"));
				//countOnlineTime.setOnlineTime(rs.getInt("online_time"));
				return countOnlineTime;
			}
        	
        });
	}

	@Override
	public List<CountOnlineTime> getCountOnlineTimeListByTime(String beginTime,
			String endTime,String[] unitIds) {
		StringBuffer sql = new StringBuffer("select * from sys_onlinetime where ((login_time >= to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss') and login_time<=to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') and" +
				" logout_time <= to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss')) or (login_time>=to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss')" +
				" and login_time<=to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') and logout_time>=to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss')" +
				" and logout_time<=to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss')) or (login_time>=to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss')" +
				" and login_time<=to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') and logout_time>=to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss'))" +
				" or (login_time<=to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss') and logout_time>=to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss'))" +
						" or (login_time>=to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss') and login_time<=to_date('"+endTime+"','yyyy-mm-dd hh24:mi:ss') and logout_time is null))");
		sql.append(" and unit_id in");
		return queryForInSQL(sql.toString(), new String[] {},unitIds,new MultiRow());
	}

	@Override
	public List<CountOnlineTime> getCountOnlineTimeListByLastlogin() {
		StringBuffer sql = new StringBuffer("select sys_onlinetime.* from sys_onlinetime,(select user_id,max(login_time) login_time from sys_onlinetime group by user_id) x where sys_onlinetime.login_time=x.login_time and sys_onlinetime.user_id=x.user_id");
		return query(sql.toString(), new String[] {},new MultiRow());
	}

	@Override
	public void deleteByNullLogoutTime() {
		String sql = "delete from sys_onlinetime where logout_time is null";
		update(sql, new Object[]{});
	}

	
}
