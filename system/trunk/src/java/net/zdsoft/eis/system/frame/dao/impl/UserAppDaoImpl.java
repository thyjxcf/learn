package net.zdsoft.eis.system.frame.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.system.frame.dao.UserAppDao;
import net.zdsoft.eis.system.frame.entity.UserApp;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;

public class UserAppDaoImpl extends BaseDao<UserApp> implements UserAppDao {

	private static final String SQL_INSERT_USERAPP = "insert into sys_user_app(id,user_id,module_id,subsystem,order_no,creation_time) values(?,?,?,?,?,?)";
	private static final String SQL_FIND_BY_USERID = "select * from sys_user_app where user_id=?";
	private static final String SQL_FIND_MODULEID_BY_USERID = "select module_id from sys_user_app where user_id=?";
	private static final String SQL_DELETE_BY_ID = "delete from sys_user_app where id=?";
	private static final String SQL_DELETE_BY_USER_ID = "delete from sys_user_app where user_id=?";
	
	@Override
	public UserApp setField(ResultSet rs) throws SQLException {
		UserApp userApp = new UserApp();
		userApp.setId(rs.getString("id"));
		userApp.setUserId(rs.getString("user_id"));
		userApp.setModuleId(rs.getInt("module_id"));
		userApp.setSubsystem(rs.getInt("subsystem"));
		userApp.setOrderNo(rs.getInt("order_no"));
		userApp.setCreationTime(rs.getTimestamp("creation_time"));
		return userApp;
	}

	@Override
	public void addUserApp(UserApp userApp) {
		userApp.setId(getGUID());
		userApp.setCreationTime(new Date());
		update(SQL_INSERT_USERAPP,
				new Object[] { userApp.getId(), userApp.getUserId(),
						userApp.getModuleId(), userApp.getSubsystem(),
						userApp.getOrderNo(), userApp.getCreationTime() },
				new int[] { Types.CHAR, Types.CHAR, Types.INTEGER,
						Types.INTEGER, Types.INTEGER, Types.DATE });
	}
	
	@Override
	public void addUserApps(List<UserApp> appList) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (UserApp userApp : appList) {
			userApp.setId(getGUID());
			userApp.setCreationTime(new Date());
			listOfArgs.add(new Object[] { userApp.getId(), userApp.getUserId(),
					userApp.getModuleId(), userApp.getSubsystem(),
					userApp.getOrderNo(), userApp.getCreationTime() });
		}
		batchUpdate(SQL_INSERT_USERAPP, listOfArgs, new int[] { Types.CHAR, Types.CHAR, Types.INTEGER,
				Types.INTEGER, Types.INTEGER, Types.DATE });
	}

	@Override
	public List<UserApp> getUserAppListByUserId(String userId, Integer subsystem) {
		String sql = SQL_FIND_BY_USERID;
		Object[] argsObj = new Object[] { userId };
		if (subsystem != 0) {
			sql += " and subsystem=?";
			argsObj = new Object[] { userId, subsystem };
		}
		sql += " order by order_no";
		return query(sql, argsObj, new MultiRow());
	}
	
	public List<String> getUserAppModuleIdsByUserId(String userId, Integer subsystem){
		String sql = SQL_FIND_MODULEID_BY_USERID;
		Object[] argsObj = new Object[] { userId };
		if (subsystem != 0) {
			sql += " and subsystem=?";
			argsObj = new Object[] { userId, subsystem };
		}
		sql += " order by order_no";
		return query(sql, argsObj, new MultiRowMapper<String>(){

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("module_id");
			}
		});
	}
	
	@Override
	public Map<Integer,UserApp> getserAppMapByUserId(String userId, Integer subsystem) {
		String sql = SQL_FIND_BY_USERID;
		Object[] argsObj = new Object[] { userId };
		if (subsystem != 0) {
			sql += " and subsystem=?";
			argsObj = new Object[] { userId, subsystem };
		}
		sql += " order by order_no";
		return queryForMap(sql, argsObj,new MapRowMapper<Integer, UserApp>() {

					public Integer mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getInt("module_id");
					}

					public UserApp mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return setField(rs);
					}
				});
	}

	@Override
	public void deleteUserApp(String id) {
		update(SQL_DELETE_BY_ID, id);
	}
	
	@Override
	public void deleteUserAppByUserId(String userId) {
		update(SQL_DELETE_BY_USER_ID, userId);
	}
	

}
