/**
 * 
 */
package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.data.dao.BaseUserDao;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;

import org.apache.commons.lang.StringUtils;

/**
 * @author yanb
 * 
 */
public class BaseUserDaoImpl extends BaseDao<User> implements BaseUserDao {

	// ==========================维护=====================
	private static final String SQL_INSERT_USER = "INSERT INTO base_user(id,unit_id,sequence,"
			+ "account_id,owner_id,owner_type,username,real_name,password,user_state,"
			+ "user_type,email,region_code,display_order,"
			+ "creation_time,modify_time,is_deleted,charge_number,charge_number_type,dept_id,nick_name,order_status,event_source,sex,mobile_phone) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_USERS_BY_IDS = "UPDATE base_user SET is_deleted = 1,event_source =?,modify_time=? WHERE id IN ";

	private static final String SQL_UPDATE_USER = "UPDATE base_user SET unit_id=?,sequence=?,"
			+ "account_id=?,owner_id=?,owner_type=?,username=?,real_name=?,password=?,user_state=?,"
			+ "user_type=?,email=?,region_code=?,display_order=?,"
			+ "modify_time=?,is_deleted=?,charge_number=?,charge_number_type=?,dept_id=?,nick_name=?,order_status=?,event_source =? WHERE id=?";

	private static final String SQL_UPDATE_SEQUENCE_BY_ID = "UPDATE base_user SET sequence=?,event_source =?, modify_time=? WHERE id =? ";

	private static final String SQL_UPDATE_PASSWORD_BY_IDS = "UPDATE base_user SET password=?, modify_time=?,event_source =? WHERE id IN";

	private static final String SQL_UPDATE_PASSWORD_BY_ID = "UPDATE base_user SET password=?, modify_time=?,event_source =? WHERE id =? ";

	private static final String SQL_UPDATE_STATE_BY_IDS = "UPDATE base_user SET user_state=?,event_source =?, modify_time=? WHERE id IN";

	private static final String SQL_UPDATE_STATE_BY_OWNERIDS = "UPDATE base_user SET user_state=?,event_source =?,modify_time=? WHERE owner_id in";

	private static final String SQL_UPDATE_STATE_BY_UNITID_OWNERTYPE = "UPDATE base_user SET user_state=?,event_source =?,modify_time=? "
			+ "WHERE unit_id=? and owner_type = ? ";

	private static final String SQL_DELETE_USERS = "delete from base_user where id in";

	// ==========================数值查询=====================
	private static final String SQL_FIND_CNT_BY_EMAIL = "SELECT COUNT(username) FROM base_user WHERE owner_type = ? AND email=? AND is_deleted=0";

	private static final String SQL_FIND_USERNAME_CNT_BY_NAMES = "SELECT username,COUNT(username) as num FROM base_user "
			+ " WHERE is_deleted=0 AND username IN";

	private static final String SQL_FIND_USER_COUNT_BY_USERNAME = "SELECT COUNT(id) FROM base_user WHERE username=? AND is_deleted=0 ";

	private static final String SQL_FIND_USER_COUNT_BY_UNITID = "select count(id) from base_user where unit_id = ? and user_state = 1 "
			+ " and owner_type = ? and  user_type = ? and is_deleted = 0 ";
	// ==========================一般查询=====================
	private static final String SQL_FIND_ADMIN_USERS_BY_UNITIDS = "SELECT * FROM base_user WHERE "
			+ "(user_type=? OR user_type=?) AND owner_type=? AND unit_id IN";

	private static final String SQL_FIND_USERS_BY_ACCOUNT_NULL = "SELECT * From base_user where is_deleted = 0 AND account_id is null ";

	private static final String SQL_FIND_ADMINS_USERS = "SELECT * FROM base_user WHERE owner_type=? AND "
			+ "(user_type=? OR user_type=?) AND unit_id in ";

	private static final String SQL_FIND_USERS_BY_USERNAMES = "SELECT * FROM base_user WHERE is_deleted = 0 AND username in ";

	// ==========================模糊查询=====================
	private static final String SQL_SERRCH_USER_BY_NAME_INUNIT = "SELECT * FROM base_user WHERE unit_id=? "
			+ "AND owner_type = ? AND username LIKE ? AND is_deleted=0 ORDER BY display_order";

	private static final String SQL_SERRCH_USER_BY_NAME = "SELECT * FROM base_user WHERE owner_type = ? "
			+ "AND username LIKE ? AND is_deleted=0 ORDER BY display_order";

	private static final String SQL_SERRCH_USER_BY_NAME_REALNAME = "SELECT * FROM base_user WHERE unit_id=? "
			+ "AND owner_type = ? AND username like ? escape '\\' AND real_name like ? escape '\\' AND is_deleted=0";

	// ==========================与用户对应的学生、教师、家长信息=====================
	private static final String SQL_FIND_STUDENTS_BY_IDS = "SELECT * FROM base_student WHERE is_deleted = 0 AND id in ";

	private static final String SQL_UPDATE_PINYIN = "update base_user set pinyin_all = ? where id = ? ";

	private static final String SQL_UPDATE_MOBILE_PHONE = "update base_user set mobile_phone=?,modify_time=? where owner_id=? and is_deleted = 0";

	public User setField(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getString("id"));
		user.setUnitid(rs.getString("unit_id"));
		user.setSequence(rs.getLong("sequence"));
		user.setAccountId(rs.getString("account_id"));
		user.setTeacherid(rs.getString("owner_id"));
		user.setOwnerType(rs.getInt("owner_type"));
		user.setName(rs.getString("username"));
		user.setRealname(rs.getString("real_name"));
		user.setPassword(rs.getString("password"));
		user.setMark(rs.getInt("user_state"));
		user.setType(rs.getInt("user_type"));
		user.setEmail(rs.getString("email"));
		user.setRegion(rs.getString("region_code"));
		user.setOrderid(rs.getLong("display_order"));
		user.setCreationTime(rs.getTimestamp("creation_time"));
		user.setModifyTime(rs.getTimestamp("modify_time"));
		user.setChargeNumber(rs.getString("charge_number"));
		user.setChargeNumberType(rs.getInt("charge_number_type"));
		user.setDeptid(rs.getString("dept_id"));
		user.setNickName(rs.getString("nick_name"));
		user.setOrderStatus(rs.getInt("order_status"));
		return user;
	}

	// ==========================维护=====================
	public void insertUsers(User[] users) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (int i = 0; i < users.length; i++) {
			User user = users[i];
			if (StringUtils.isBlank(user.getId()))
				user.setId(getGUID());
			user.setCreationTime(new Date());
			user.setModifyTime(new Date());
			user.setIsdeleted(false);
			listOfArgs
					.add(new Object[] {
							user.getId(),
							user.getUnitid(),
							user.getSequence(),
							user.getAccountId(),
							user.getTeacherid(),
							user.getOwnerType(),
							user.getName(),
							user.getRealname(),
							user.findEncodePassword(),
							user.getMark(),
							user.getType(),
							user.getEmail(),
							user.getRegion(),
							user.getOrderid(),
							user.getCreationTime(),
							user.getModifyTime(),
							user.getIsdeleted() ? 1 : 0,
							user.getChargeNumber(),
							user.getChargeNumberType(),
							user.getDeptid(),
							user.getNickName(),
							user.getOrderStatus(),
							user.getEventSourceValue(),
							StringUtils.isNotBlank(user.getSex()) ? Integer
									.valueOf(user.getSex()) : 0,
							user.getMobilePhone() });
		}

		int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.BIGINT,
				Types.CHAR, Types.CHAR, Types.BIGINT, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.BIGINT, Types.BIGINT,
				Types.VARCHAR, Types.CHAR, Types.BIGINT, Types.TIMESTAMP,
				Types.TIMESTAMP, Types.INTEGER, Types.VARCHAR, Types.INTEGER,
				Types.CHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER,
				Types.INTEGER, Types.VARCHAR };
		batchUpdate(SQL_INSERT_USER, listOfArgs, argTypes);
	}

	public void deleteUsers(String[] ids, EventSourceType eventSource) {
		String sql = SQL_DELETE_USERS_BY_IDS;
		updateForInSQL(sql,
				new Object[] { eventSource.getValue(), new Date() }, ids);
	}

	public void deleteUsers(String[] ids) {
		String sql = SQL_DELETE_USERS;
		updateForInSQL(sql, new Object[] {}, ids);
	}

	public void updateUser(User user) {
		user.setModifyTime(new Date());
		user.setIsdeleted(false);
		update(SQL_UPDATE_USER,
				new Object[] { user.getUnitid(), user.getSequence(),
						user.getAccountId(), user.getTeacherid(),
						user.getOwnerType(), user.getName(),
						user.getRealname(), user.findEncodePassword(),
						user.getMark(), user.getType(), user.getEmail(),
						user.getRegion(), user.getOrderid(),
						user.getModifyTime(), user.getIsdeleted() ? 1 : 0,
						user.getChargeNumber(), user.getChargeNumberType(),
						user.getDeptid(), user.getNickName(),
						user.getOrderStatus(), user.getEventSourceValue(),
						user.getId() }, new int[] { Types.CHAR, Types.BIGINT,
						Types.CHAR, Types.CHAR, Types.BIGINT, Types.VARCHAR,
						Types.VARCHAR, Types.VARCHAR, Types.BIGINT,
						Types.BIGINT, Types.VARCHAR, Types.CHAR, Types.BIGINT,
						Types.TIMESTAMP, Types.INTEGER, Types.VARCHAR,
						Types.INTEGER, Types.CHAR, Types.VARCHAR,
						Types.INTEGER, Types.INTEGER, Types.CHAR });
	}

	public void updateUsers(User[] users) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (int i = 0; i < users.length; i++) {
			User user = users[i];
			user.setModifyTime(new Date());
			listOfArgs.add(new Object[] { user.getUnitid(), user.getSequence(),
					user.getAccountId(), user.getTeacherid(),
					user.getOwnerType(), user.getName(), user.getRealname(),
					user.findEncodePassword(), user.getMark(), user.getType(),
					user.getEmail(), user.getRegion(), user.getOrderid(),
					user.getModifyTime(), user.getIsdeleted() ? 1 : 0,
					user.getChargeNumber(), user.getChargeNumberType(),
					user.getDeptid(), user.getNickName(),
					user.getOrderStatus(), user.getEventSourceValue(),
					user.getId() });
		}

		int[] argTypes = new int[] { Types.CHAR, Types.BIGINT, Types.CHAR,
				Types.CHAR, Types.BIGINT, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.BIGINT, Types.BIGINT, Types.VARCHAR,
				Types.CHAR, Types.BIGINT, Types.TIMESTAMP, Types.INTEGER,
				Types.VARCHAR, Types.INTEGER, Types.CHAR, Types.VARCHAR,
				Types.INTEGER, Types.INTEGER, Types.CHAR };
		batchUpdate(SQL_UPDATE_USER, listOfArgs, argTypes);
	}

	public void insertUser(User user) {
		if (StringUtils.isBlank(user.getId()))
			user.setId(getGUID());
		user.setIsdeleted(false);
		user.setCreationTime(new Date());
		user.setModifyTime(new Date());
		user.setMark(1);

		update(SQL_INSERT_USER,
				new Object[] {
						user.getId(),
						user.getUnitid(),
						user.getSequence(),
						user.getAccountId(),
						user.getTeacherid(),
						user.getOwnerType(),
						user.getName(),
						user.getRealname(),
						user.findEncodePassword(),
						user.getMark(),
						user.getType(),
						user.getEmail(),
						user.getRegion(),
						user.getOrderid(),
						user.getCreationTime(),
						user.getModifyTime(),
						user.getIsdeleted() ? 1 : 0,
						user.getChargeNumber(),
						user.getChargeNumberType(),
						user.getDeptid(),
						user.getNickName(),
						user.getOrderStatus(),
						user.getEventSourceValue(),
						StringUtils.isNotBlank(user.getSex()) ? Integer
								.valueOf(user.getSex()) : 0,
						user.getMobilePhone() });
	}

	public void updateUserPassword(String[] ids, String passwordInit) {
		updateForInSQL(SQL_UPDATE_PASSWORD_BY_IDS, new Object[] { passwordInit,
				new Date(), EventSourceType.LOCAL.getValue() }, ids);
	}

	public void updateUserPassword(Map<String, String> map) {
		Set<String> userSet = map.keySet();
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (String userId : userSet) {
			String passwordInit = map.get(userId);
			listOfArgs.add(new Object[] { passwordInit, new Date(),
					EventSourceType.LOCAL.getValue(), userId });
		}

		int[] argTypes = new int[] { Types.VARCHAR, Types.TIMESTAMP,
				Types.INTEGER, Types.CHAR };
		batchUpdate(SQL_UPDATE_PASSWORD_BY_ID, listOfArgs, argTypes);
	}

	public void updateUserMark(String[] ids, int mark) {
		updateForInSQL(SQL_UPDATE_STATE_BY_IDS, new Object[] { mark,
				EventSourceType.LOCAL.getValue(), new Date() }, ids);
	}

	public void updateUserForPassport(User... users) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (int i = 0; i < users.length; i++) {
			User user = users[i];
			listOfArgs
					.add(new Object[] { user.getSequence(),
							EventSourceType.LOCAL.getValue(), new Date(),
							user.getId() });
		}

		int[] argTypes = new int[] { Types.BIGINT, Types.INTEGER,
				Types.TIMESTAMP, Types.CHAR };

		batchUpdate(SQL_UPDATE_SEQUENCE_BY_ID, listOfArgs, argTypes);
	}

	public void updateUserMarkByOwner(String[] ownerIds, int state) {
		updateForInSQL(SQL_UPDATE_STATE_BY_OWNERIDS, new Object[] { state,
				EventSourceType.LOCAL.getValue(), new Date() }, ownerIds);
	}

	public void updateUserLock(String unitId) {
		Object[] objs = new Object[] { User.USER_MARK_LOCK,
				EventSourceType.LOCAL.getValue(), new Date(), unitId,
				User.TEACHER_LOGIN };
		update(SQL_UPDATE_STATE_BY_UNITID_OWNERTYPE, objs);
	}

	// ===================与用户对应的学生、教师、家长信息=============

	public List<User> getStudentUsers(String... studentIds) {
		return queryForInSQL(SQL_FIND_STUDENTS_BY_IDS, null, studentIds,
				new MultiRowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						User user = new User();
						user.setId(rs.getString("id"));
						user.setMobilePhone(rs.getString("mobile_phone"));
						user.setBirthday(rs.getTimestamp("birthday"));
						user.setSex(rs.getString("sex"));
						user.setEmail(rs.getString("email"));
						user.setHomepage(rs.getString("homepage"));
						user.setInteName(rs.getString("student_name"));
						user.setUnitid(rs.getString("school_id"));
						user.setHomePhone(rs.getString("link_phone"));
						user.setOfficePhone(rs.getString("link_phone"));
						user.setExtraId(rs.getString("student_code"));
						return user;
					}
				});
	}

	// ==========================数值=====================
	public boolean isExistsEmail(String email) {
		int rtn = queryForInt(SQL_FIND_CNT_BY_EMAIL, new Object[] {
				User.TEACHER_LOGIN, email });
		if (rtn > 0)
			return true;
		else
			return false;
	}

	public int getUserNameCount(String loginName) {
		if (User.isUsernameNotCaseSensitive()) {
			if (null != loginName) {
				loginName = loginName.toLowerCase();
			}
		}
		return queryForInt(SQL_FIND_USER_COUNT_BY_USERNAME,
				new Object[] { loginName });
	}

	public int getUserCount(String unitId, int ownerType, int userType) {
		return queryForInt(SQL_FIND_USER_COUNT_BY_UNITID, new Object[] {
				unitId, ownerType, userType });
	}

	public Map<String, Integer> getUserNameCount(String[] userNames) {
		if (User.isUsernameNotCaseSensitive()) {
			for (int i = 0; i < userNames.length; i++) {
				String username = userNames[i];
				if (null != username) {
					userNames[i] = username.toLowerCase();
				}
			}
		}
		return queryForInSQL(SQL_FIND_USERNAME_CNT_BY_NAMES, null, userNames,
				new MapRowMapper<String, Integer>() {

					public String mapRowKey(ResultSet rs, int arg1)
							throws SQLException {
						String username = rs.getString("username");
						if (User.isUsernameNotCaseSensitive()) {
							if (null != username) {
								username = username.toLowerCase();
							}
						}
						return username;
					}

					public Integer mapRowValue(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getInt("num");
					}

				});
	}

	// ==========================列表=====================

	public List<User> getAdminUsers(String[] unitIds) {
		Object[] objs = new Object[] { User.TYPE_ADMIN, User.TYPE_TOPADMIN,
				User.TEACHER_LOGIN };
		return queryForInSQL(SQL_FIND_ADMIN_USERS_BY_UNITIDS, objs, unitIds,
				new MultiRow());
	}

	public List<User> getUsersByUserNames(String... userNames) {
		if (User.isUsernameNotCaseSensitive()) {
			for (int i = 0; i < userNames.length; i++) {
				String username = userNames[i];
				if (null != username) {
					userNames[i] = username.toLowerCase();
				}
			}
		}
		return queryForInSQL(SQL_FIND_USERS_BY_USERNAMES, new Object[] {},
				userNames, new MultiRow());
	}

	public List<User> getUsersFaintness(String userName) {
		if (userName == null) {
			return null;
		}
		if (User.isUsernameNotCaseSensitive()) {
			userName = userName.toLowerCase();
		}

		return query(SQL_SERRCH_USER_BY_NAME, new Object[] {
				User.TEACHER_LOGIN, "%" + userName + "%" }, new MultiRow());
	}

	public List<User> getUsersFaintness(String userName, String unitId) {
		if (userName == null) {
			return null;
		}
		if (User.isUsernameNotCaseSensitive()) {
			userName = userName.toLowerCase();
		}

		return query(SQL_SERRCH_USER_BY_NAME_INUNIT, new Object[] { unitId,
				User.TEACHER_LOGIN, "%" + userName + "%" }, new MultiRow());
	}

	public Map<String, User> getUserByName(String unitId, int ownerType,
			String userName) {

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM base_user WHERE 1=1 ");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.isNotEmpty(unitId)) {
			sql.append("  and unit_id = ?");
			args.add(unitId);
		}
		if (StringUtils.isNotEmpty(userName)) {
			sql.append(" and username  like ?");
			args.add("%" + userName + "%");
		}
		sql.append(" and owner_type = ? AND is_deleted=0 ORDER BY display_order ");
		args.add(ownerType);

		return queryForMap(sql.toString(), args.toArray(),
				new MapRowMapper<String, User>() {

					@Override
					public String mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						// TODO Auto-generated method stub
						return rs.getString("owner_id");
					}

					@Override
					public User mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						// TODO Auto-generated method stub
						
						return setField(rs);
					}

				});

	}

	public List<User> getUsersFaintness(String unitId, int ownerType,
			String userTypes, String userName, String realName, Pagination page) {
		String sql = SQL_SERRCH_USER_BY_NAME_REALNAME;
		if (userName != null) {
			if (User.isUsernameNotCaseSensitive()) {
				userName = userName.toLowerCase();
			}
		}
		if (userTypes != null) {
			sql += " AND user_type in (" + userTypes + ")";
		}
		Object[] objs = new Object[] { unitId, ownerType, "%" + userName + "%",
				"%" + realName + "%" };
		sql += " ORDER BY display_order";

		if (null == page) {
			return query(sql, objs, new MultiRow());
		} else {
			return query(sql, objs, new MultiRow(), page);
		}
	}

	public List<User> getUsersWithAccountNull() {
		return query(SQL_FIND_USERS_BY_ACCOUNT_NULL, new MultiRow());
	}

	public Map<String, User> getUnitAdmins(String[] unitids) {
		String sql = SQL_FIND_ADMINS_USERS + SQLUtils.toSQLInString(unitids);
		return queryForMap(sql, new Object[] { User.TEACHER_LOGIN,
				User.TYPE_ADMIN, User.TYPE_TOPADMIN }, new MapRow() {
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("unit_id");
			}
		});
	}

	@Override
	public void updateUsersPinYin(List<User> users) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (User user : users) {
			listOfArgs.add(new Object[] { user.getPinyinAll(), user.getId() });
		}
		int[] argTypes = new int[] { Types.VARCHAR, Types.CHAR };
		batchUpdate(SQL_UPDATE_PINYIN, listOfArgs, argTypes);
	}

	@Override
	public void updateUsersMobilePhoneByOwnerId(String familyMobPho,
			String ownerId) {
		update(SQL_UPDATE_MOBILE_PHONE, new Object[] { familyMobPho,
				new Date(), ownerId });
	}

	@Override
	public List<User> getUsersByUnitIds(String[] unitIds) {
		String sql = "SELECT * FROM base_user WHERE is_deleted=0 and unit_id in";
		return queryForInSQL(sql, null, unitIds, new MultiRow(),
				" order by unit_id");
	}
	
	@Override
	public void updateUserMobilePhone(String... ownerIds) {
		String sql="update base_user set mobile_phone='' where owner_type =3 and is_deleted = 0 AND owner_id IN ";
		this.updateForInSQL(sql, null, ownerIds);
	}

	public int getUserNameMaxCodeByClassCode(String unitId, String classCode){
		String sql = "SELECT max(replace(username,'"+classCode+"','')) as maxcode FROM base_user WHERE unit_id=? and  is_deleted = 0 and username like '"+classCode+"%'";
		return this.query(sql, unitId, new SingleRowMapper<Integer>(){
			public Integer mapRow(ResultSet rs) throws SQLException {
				String code = rs.getString("maxcode");
				return StringUtils.isBlank(code)?0:Integer.valueOf(code);
			}
		});
	}
}
