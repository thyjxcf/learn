package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import net.zdsoft.eis.base.common.dao.LoginLogDao;
import net.zdsoft.eis.base.common.entity.LoginLog;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.SingleRowMapper;

public class LoginLogDaoImpl extends BaseDao<LoginLog> implements LoginLogDao {
	private static final String SQL_INSERT_LOGINLOG = "INSERT INTO log_user_login(account_id,user_type,region_code,"
			+ "unit_id,remote_ip,server_type_id,server_id,login_time,creation_time) "
			+ "VALUES(?,?,?,?,?,?,?,?,?)";

	private static final String SQL_FIND_LAST_LOGIN_TIME_BY_ACCOUNT_ID = "select login_time as login_time from (select rownum rr, login_time as login_time from (select login_time from log_user_login where account_id =? order by login_time desc ) lul ) where rr=2";

    public LoginLog setField(ResultSet rs) throws SQLException {
        LoginLog loginLog = new LoginLog();
        loginLog.setAccountId(rs.getString("account_id"));
        loginLog.setUserType(rs.getInt("user_type"));
        loginLog.setRegionCode(rs.getString("region_code"));
        loginLog.setUnitId(rs.getString("unit_id"));
        loginLog.setRemoteIp(rs.getString("remote_ip"));
        loginLog.setServerTypeId(rs.getInt("server_type_id"));
        loginLog.setServerId(rs.getInt("server_id"));
        loginLog.setLoginTime(rs.getTimestamp("login_time"));
        loginLog.setCreationTime(rs.getTimestamp("creation_time"));
        return loginLog;
    }

    public void insertLoginLog(LoginLog loginLog) {
        loginLog.setCreationTime(new Date());
        update(SQL_INSERT_LOGINLOG, new Object[] { loginLog.getAccountId(), loginLog.getUserType(),
                loginLog.getRegionCode(), loginLog.getUnitId(), loginLog.getRemoteIp(),
                loginLog.getServerTypeId(), loginLog.getServerId(), loginLog.getLoginTime(),
                loginLog.getCreationTime() }, new int[] { Types.CHAR, Types.INTEGER, Types.CHAR,
                Types.CHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP });
    }

	public Date getUserLastLoginTime(String accountId) {
		return query(SQL_FIND_LAST_LOGIN_TIME_BY_ACCOUNT_ID,
				new Object[] { accountId }, new SingleRowMapper<Date>() {
					@Override
					public Date mapRow(ResultSet rs) throws SQLException {
						return rs.getTimestamp("login_time");
					}
				});
	}
	
	public boolean isExistsTable(int year){
		String sql = "select count(1) from all_tables where TABLE_NAME = 'LOG_USER_LOGIN_"+year+"'";
		int i = queryForInt(sql);
		if(i > 0){
			return true;
		}
		return false;
	}
	
	public void createTable(int year){
		String sql = "create table log_user_login_"+year+" as select * from log_user_login where 0 = 1";
		update(sql);
	}
	
	@Override
	public void insertIntoHistoryTable(int year) {
		String sql = "insert into log_user_login_"+year+" select * from log_user_login where login_time < add_months(sysdate,-1)";
		update(sql);
	}
	
	@Override
	public void deleteOneMonthAgoData() {
		String sql = "delete from log_user_login where login_time < add_months(sysdate,-1)";
		update(sql);
	}

}
