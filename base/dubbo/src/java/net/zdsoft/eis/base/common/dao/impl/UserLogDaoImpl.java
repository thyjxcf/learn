package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.dao.UserLogDao;
import net.zdsoft.eis.base.common.entity.UserLog;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;

public class UserLogDaoImpl extends BaseDao<UserLog> implements UserLogDao {

	private static final String SQL_INSERT_USERLOG = "INSERT INTO sys_log(id,subsystem,modid,"
			+ "userid,description,logtime,logtype,flag,ownername,unitid) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_BY_ID = "DELETE FROM sys_log WHERE id in ";

	private static final String SQL_DELETE_BY_LOGTIME = "DELETE FROM sys_log WHERE subsystem=? AND logtime<=?";

	private static final String SQL_FIND_CNT = "SELECT count(id) FROM sys_log";

	public UserLog setField(ResultSet rs) throws SQLException {
		UserLog userLog = new UserLog();
		userLog.setId(rs.getLong("id"));
		userLog.setSubSystem(rs.getInt("subsystem"));
		userLog.setModId(rs.getString("modid"));
		userLog.setUserId(rs.getString("userid"));
		userLog.setDescription(rs.getString("description"));
		userLog.setLogTime(rs.getTimestamp("logtime"));
		userLog.setLogType(rs.getInt("logtype"));
		userLog.setFlag(rs.getInt("flag"));
		userLog.setUserName(rs.getString("ownername"));
		userLog.setUnitid(rs.getString("unitid"));
		return userLog;
	}

	public void insertUserLog(UserLog userLog) {
		userLog.setId(getIncrementerKey());
		update(SQL_INSERT_USERLOG, new Object[] { userLog.getId(),
				userLog.getSubSystem(), userLog.getModId(),
				userLog.getUserId(), userLog.getDescription(),
				userLog.getLogTime(), userLog.getLogType(), userLog.getFlag(),
				userLog.getUserName(), userLog.getUnitid() }, new int[] {
				Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.CHAR,
				Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER, Types.INTEGER,
				Types.VARCHAR, Types.CHAR });
	}

	public void deleteUserLogs(String[] ids) {
		updateForInSQL(SQL_DELETE_BY_ID, null, ids);
	}

	public void deleteUserLogs(Integer subSystem, Date endTime) {
		update(SQL_DELETE_BY_LOGTIME, new Object[] { subSystem, endTime });
	}

	public List<UserLog> getUserLogs(Date startTime, Date endTime,
			Integer subSystem, String unitId, Pagination page) {
		Date startDate = startTime;
		Date endDate = endTime;
		Calendar ca = Calendar.getInstance();
		if (startTime != null) {
			startDate = startTime;
		}

		if (endTime != null) {
			ca.setTime(endTime);
			ca.add(Calendar.DATE, 1);
			endDate = ca.getTime();
		}

		List<UserLog> logs = null;
		Object[] parameters = null;
		StringBuffer sb = new StringBuffer(
				"SELECT * FROM sys_log WHERE logtime>=? and logtime<=? ");
		if (subSystem == null && unitId == null) {
			sb.append(" ORDER BY logtime asc");
			parameters = new Object[] { startDate, endDate };
		} else if (subSystem == null && unitId != null) {
			sb.append("and unitid like ? ");
			sb.append(" ORDER BY logtime asc");
			parameters = new Object[] { startDate, endDate, unitId };
		} else if (subSystem != null && unitId == null) {
			sb.append("and subsystem=? ");
			sb.append(" ORDER BY logtime asc");
			parameters = new Object[] { startDate, endDate, subSystem };
		} else if (subSystem != null && unitId != null) {
			sb.append("and subsystem=? and unitid like ?");
			sb.append(" ORDER BY logtime asc");
			parameters = new Object[] { startDate, endDate, subSystem, unitId };
		}
		if (null != parameters) {
			if (null == page) {
				logs = query(sb.toString(), parameters, new MultiRow());
			} else {
				logs = query(sb.toString(), parameters, new MultiRow(), page);
			}
		}
		return logs;

	}

	public Integer getLogCount() {
		return queryForInt(SQL_FIND_CNT);
	}

}
