/**
 * 
 */
package net.zdsoft.leadin.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import net.zdsoft.keel.dao.BasicDAO;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.leadin.common.dao.SchedulerTokenDao;
import net.zdsoft.leadin.common.entity.SchedulerToken;

/**
 * @author zhaosf
 * 
 */
public class SchedulerTokenDaoImpl extends BasicDAO implements
		SchedulerTokenDao {
	private static Integer STATUS_IDLE = Integer.valueOf(0);
	private static Integer STATUS_BUSY = Integer.valueOf(1);

	private static final String SQL_FIND_TOKEN = "select * from sys_scheduler_token where code = ?";

	private static final String SQL_UPDATE_TOKEN = "UPDATE sys_scheduler_token set status = ?,modify_time=? where code = ? AND status = ?";
	private static final String SQL_UPDATE_TOKEN_FORCE = "UPDATE sys_scheduler_token set status = ?,modify_time=? where ROUND(TO_NUMBER(sysdate - modify_time) * 24 * 60) >= reset_second AND status = ?";

	@Override
	public SchedulerToken getSchedulerToken(String tokenCode) {
		return query(SQL_FIND_TOKEN, new Object[] { tokenCode },
				new SingleRowMapper<SchedulerToken>() {

					@Override
					public SchedulerToken mapRow(ResultSet rs)
							throws SQLException {
						SchedulerToken token = new SchedulerToken();
						token.setId(rs.getString("id"));
						token.setCode(rs.getString("code"));
						token.setName(rs.getString("name"));
						token.setStatus(rs.getInt("status"));
						token.setResetSecond(rs.getInt("reset_second"));
						token.setRemark(rs.getString("remark"));
						token.setModifyTime(rs.getTime("modify_time"));
						return token;
					}
				});
	}

	@Override
	public boolean lockToken(String tokenCode) {
		int rtn = update(SQL_UPDATE_TOKEN, new Object[] { STATUS_BUSY,
				new Date(), tokenCode, STATUS_IDLE });
		if (rtn == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void unlockToken(String tokenCode) {
		update(SQL_UPDATE_TOKEN, new Object[] { STATUS_IDLE, new Date(),
				tokenCode, STATUS_BUSY });
	}

	@Override
	public void unlockForceToken() {
		update(SQL_UPDATE_TOKEN_FORCE, new Object[] { STATUS_IDLE, new Date(),
				STATUS_BUSY });
	}
}
