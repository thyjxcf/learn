package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.UserDao;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;

import org.apache.commons.lang.StringUtils;

public class UserDaoImpl extends BaseDao<User> implements UserDao {

	// ========================= 返回单个对象的查询=====================================
	private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM base_user WHERE id=?";
	
	private static final String SQL_FIND_USER_INCLUDE_DEL_BY_ID = "SELECT * FROM base_user WHERE id=? ";

    private static final String SQL_FIND_USER_BY_USERNAME = "SELECT * FROM base_user WHERE username=? AND is_deleted=0";
    
    private static final String SQL_FIND_USER_BY_MOBILEPHONE = "SELECT * FROM base_user WHERE mobile_phone=? AND owner_type=? AND is_deleted=0";
    
    private static final String SQL_FIND_USER_BY_REALNAME_MOBILEPHONE = "SELECT * FROM base_user WHERE real_name=? AND mobile_phone=? AND owner_type=? AND is_deleted=0";

	private static final String SQL_FIND_ADMIN_USER = "SELECT * FROM base_user WHERE unit_id=? AND "
			+ "owner_type = ? AND is_deleted=0 AND (user_type=? OR user_type=?) ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	private static final String SQL_FIND_TOP_USER = "SELECT * From base_user where user_type = 0 AND is_deleted = 0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";
	//zengzt20160412 却掉AND is_deleted = 0删除的可以修改密码
    private static final String SQL_FIND_USERS_BY_ACCOUNTID = "SELECT * From base_user where account_id=? ";

    // ========================= 返回数值=====================================
    private static final String SQL_FIND_COMMON_USER_NUM_BY_UNITID = "SELECT COUNT(id) FROM base_user WHERE "
            + "unit_id=? AND owner_type = ? AND owner_id is null AND user_type=? AND is_deleted=0 ORDER BY display_order ";

    private static final String SQL_USERNUM_BY_USERNAME = "SELECT COUNT(id) FROM base_user WHERE username=? AND is_deleted=0";

    private static final String SQL_FIND_MAX_ORDERID = "SELECT max(display_order) FROM base_user WHERE unit_id=? and owner_type = ? AND is_deleted=0";

    private static final String SQL_FIND_USER_COUNT_BY_TEACHERIDS = "SELECT COUNT(*) as num FROM base_user WHERE is_deleted=0 AND owner_id IN ";

    // ========================= 返回多个对象的查询=====================================
    // 包括删除的用户
    private static final String SQL_FIND_USERS_INCLUDE_DEL_BY_IDS = "SELECT * FROM base_user WHERE id IN ";
    // 包括删除的用户
    private static final String SQL_FIND_USERS_INCLUDE_DEL_BY_UNITID = "SELECT * FROM base_user WHERE unit_id = ? ";

    private static final String SQL_FIND_USERS_BY_IDS = "SELECT * FROM base_user WHERE is_deleted = 0 AND id IN";

    private static final String SQL_FIND_USERS_BY_NAMES = "SELECT * FROM base_user WHERE is_deleted=0 AND username IN";

    private static final String SQL_FIND_USERS_BY_OWNERIDS = "SELECT * From base_user where is_deleted = 0 AND owner_type=? AND owner_id IN ";

	private static final String SQL_FIND_USERS_BY_USERTYPE_OWNERIDS = "SELECT * From base_user where is_deleted = 0 AND owner_type=? AND user_type=? AND owner_id IN ";

	private static final String SQL_FIND_USERS_BY_UNITID_TYPE = "SELECT * FROM base_user WHERE unit_id = ? "
			+ "AND user_type = ? AND owner_type = ? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	private static final String SQL_FIND_USERS_BY_OWNERID = "SELECT * FROM base_user WHERE owner_id=? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	private static final String SQL_FIND_USERS_BY_UNITID = "SELECT * FROM base_user WHERE unit_id=? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	private static final String SQL_FIND_USERS_BY_UNITID_OWNTYPE = "SELECT * FROM base_user "
			+ "WHERE unit_id=? AND owner_type = ? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

    private static final String SQL_FIND_USERS_BY_UNITID_OWNERTYPE_USERTYPE = "SELECT * FROM base_user "
            + "WHERE unit_id=? AND owner_type = ? AND user_type=2 AND is_deleted=0 AND user_state in";

    private static final String SQL_FIND_ADMINUSERS_BY_UNITIDS = "SELECT * FROM base_user "
            + "WHERE owner_type = ? AND (user_type=? OR user_type=?) AND is_deleted=0 AND unit_id IN";
    
    private static final String SQL_SERRCH_USER_BY_NAME_INUNIT = "SELECT * FROM base_user WHERE unit_id=? "
        + "AND owner_type = ? AND real_name LIKE ? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";
	
	private static final String SQL_FIND_USERS_BY_UNITID_REALNAME = "SELECT * FROM base_user WHERE unit_id = ? "
			+ " AND owner_type = ? AND real_name like ? AND is_deleted=0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	private static final String SQL_FIND_LIST_BY_UNITID_PAGE = "select * from base_user where unit_id = ? and real_name like ? and owner_type = ?  ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";
	
	private static final String SQL_FIND_LIST_BY_REALNAME_PAGE = "select * from base_user where is_deleted=0 and real_name like ? and owner_type = 2 ";
	
	private static final String SQL_FIND_LIST_WITHOUT_PINYIN = "select * from base_user where  owner_type = 2 and (pinyin_all is null or to_char(modify_time,'yyyy-MM-dd') >= to_char(sysdate-40,'yyyy-MM-dd')) ";
	
	private static final String SQL_FIND_LIST_BY_PINYIN = "select * from base_user where is_deleted = 0 and owner_type = 2 and unit_id = ? and instr(pinyin_all, ?) > 0 ";
	
	private static final String SQL_FIND_LIST_BY_PINYIN_WITHOUT_UNITID = "select * from base_user where is_deleted = 0 and owner_type = 2 and instr(pinyin_all, ?) > 0 ";
	
	private static final String SQL_FIND_USERS_BY_UNITIDS = "SELECT * FROM base_user WHERE is_deleted = 0 AND unit_id IN";
	
	private static final String SQL_FIND_USERS_BY_DEPTIDS = "SELECT * FROM base_user WHERE is_deleted = 0 AND dept_id IN";
	
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
		user.setIsdeleted(rs.getBoolean("is_deleted"));
		user.setChargeNumber(rs.getString("charge_number"));
		user.setChargeNumberType(rs.getInt("charge_number_type"));
		user.setDeptid(rs.getString("dept_id"));
		user.setNickName(rs.getString("nick_name"));
		user.setOrderStatus(rs.getInt("order_status"));
		user.setMobilePhone(rs.getString("mobile_phone"));
		user.setPinyinAll(rs.getString("pinyin_all"));
		user.setSex(rs.getString("sex"));
		return user;
	}

	public User getUser(String userId) {
		return query(SQL_FIND_USER_BY_ID, userId, new SingleRow());
	}
	
	public User getUserWithDel(String userId) {
		return query(SQL_FIND_USER_INCLUDE_DEL_BY_ID, userId, new SingleRow());
	}
	
	
	public User getUserByUserName(String loginName) {
		if (User.isUsernameNotCaseSensitive()) {
			if (null != loginName) {
				loginName = loginName.toLowerCase();
			}
		}
		return query(SQL_FIND_USER_BY_USERNAME, new String[] { loginName },
				new SingleRow());
	}
	
	
	public List<User> getUserByMobilePhone(String mobilePhone, int ownerType) {
		if(ownerType==-1){
			return query("SELECT * FROM base_user WHERE mobile_phone=? AND is_deleted=0",mobilePhone,new MultiRow());
		}
		return query(SQL_FIND_USER_BY_MOBILEPHONE, new Object[] { mobilePhone, ownerType },
				new MultiRow());
	}

    public User getUnitAdmin(String unitId) {
        return query(SQL_FIND_ADMIN_USER, new Object[] { unitId, User.TEACHER_LOGIN,
                User.TYPE_ADMIN, User.TYPE_TOPADMIN }, new SingleRow());
    }

    public User getUserByAccountId(String accountId) {
        return query(SQL_FIND_USERS_BY_ACCOUNTID, new Object[] { accountId }, new SingleRow());
    }

    public User getTopUser() {
        return query(SQL_FIND_TOP_USER, new SingleRow());
    }

    public Integer getUserNameCount(String userName) {
        if (User.isUsernameNotCaseSensitive()) {
            if (null != userName) {
                userName = userName.toLowerCase();
            }
        }
        int num = queryForInt(SQL_USERNUM_BY_USERNAME, new Object[] { userName });
        if (num < 1) {
            return 0;
        } else {
            return num;
        }
    }

    public Long getAvailableOrder(String unitId) {
    	Long orderidNum = queryForLong(SQL_FIND_MAX_ORDERID, new Object[] { unitId,
                User.TEACHER_LOGIN });
        if (orderidNum < 1) {
            return 1L;
        } else {
            return orderidNum + 1;
        }
    }

    public Integer getCommonUserCount(String unitId) {
        int num = queryForInt(SQL_FIND_COMMON_USER_NUM_BY_UNITID, new Object[] { unitId,
                User.TEACHER_LOGIN, User.TYPE_NORMAL });
        if (num < 1) {
            return 0;
        } else {
            return num;
        }
    }

    public Integer[] getUserCount(String[] teacherIds) {
        String postfix = " GROUP BY owner_id";
        List<Integer> list = queryForInSQL(SQL_FIND_USER_COUNT_BY_TEACHERIDS, null, teacherIds,
                new MultiRowMapper<Integer>() {

                    public Integer mapRow(ResultSet rs, int i) throws SQLException {
                        return rs.getInt("num");
                    }

                }, postfix);

        Iterator<Integer> it = list.iterator();
        if (it == null) {
            return null;
        }
        Integer[] inte = new Integer[teacherIds.length];
        Object obj;
        int i = 0;
        if (!it.hasNext()) {
            for (int j = 0; j < inte.length; j++) {
                inte[j] = 0;
            }
        }
        while (it.hasNext()) {
            obj = it.next();
            if (obj == null) {
                inte[i] = 0;
            } else {
                inte[i] = (Integer) obj;
            }
            i++;
        }
        return inte;
    }

   public List<User> getUsers(String unitId, int ownerType) {
		return query(SQL_FIND_USERS_BY_UNITID_OWNTYPE, new Object[] { unitId,
				ownerType }, new MultiRowMapper<User>(){

					@Override
					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						User user = setField(rs);
						user.setOfficeTel(rs.getString("office_tel"));
						user.setMobilePhone(rs.getString("mobile_phone"));
						return user;
					}
			
		});
	}
    
    public List<User> getUsers(String unitId, int ownerType,Pagination page) {
        return query(SQL_FIND_USERS_BY_UNITID_OWNTYPE, new Object[] { unitId, ownerType },
                new MultiRow(),page);
    }

	public List<User> getUsers(String unitId, String marks[]) {
		return queryForInSQL(
				SQL_FIND_USERS_BY_UNITID_OWNERTYPE_USERTYPE,
				new Object[] { unitId, User.TEACHER_LOGIN },
				marks,
				new MultiRow(),
				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
	}

    public List<User> getUsersWithDel(String[] userIds) {
        return queryForInSQL(SQL_FIND_USERS_INCLUDE_DEL_BY_IDS, null, userIds, new MultiRow(),
                " ORDER BY display_order");
    }
    
    @Override
    public List<User> getUsersWithDel(String unitId) {
    	return query(SQL_FIND_USERS_INCLUDE_DEL_BY_UNITID, new Object[]{unitId}, new MultiRow());
    }

    public List<User> getUsersByOwner(String teacherId) {
        return query(SQL_FIND_USERS_BY_OWNERID, new Object[] { teacherId }, new MultiRow());
    }

    public List<User> getUsersByUnitId(String unitId) {
        return query(SQL_FIND_USERS_BY_UNITID, new Object[] { unitId }, new MultiRow());
    }

	public List<User> getUsers(String[] userIds) {
		return queryForInSQL(
				SQL_FIND_USERS_BY_IDS,
				null,
				userIds,
				new MultiRow(),
				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
	}

	public List<User> getUsersByOwner(int ownerType, String[] ownerIds) {
		return queryForInSQL(
				SQL_FIND_USERS_BY_OWNERIDS,
				new Object[] { Integer.valueOf(ownerType) },
				ownerIds,
				new MultiRow(),
				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
	}
	
	public List<User> getUsersByOwner(int ownerType, String[] ownerIds, Pagination page) {
		if(page!=null){
		return queryForInSQL(
				SQL_FIND_USERS_BY_OWNERIDS,
				new Object[] { Integer.valueOf(ownerType) },
				ownerIds,
				new MultiRow(),
				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))", page);
		}else{
			return queryForInSQL(
					SQL_FIND_USERS_BY_OWNERIDS,
					new Object[] { Integer.valueOf(ownerType) },
					ownerIds,
					new MultiRow(),
					" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
		}
	}
	
	@Override
	public List<User> getUsersByOwner(int ownerType, String[] ownerIds,
			String[] marks) {
		//"SELECT * From base_user where is_deleted = 0 AND owner_type=? AND owner_id IN ";
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * From base_user where is_deleted = 0 AND owner_type=? AND  user_state in (");
		if(marks!=null&&marks.length>0){
			for (int i = 0; i < marks.length; i++) {
				sb.append(marks[i]);
				if(i!=marks.length-1){
					sb.append(",");
				}else{
					sb.append(") AND owner_id IN ");
				}
			}
		}
		return queryForInSQL(
				sb.toString(),
				new Object[] { Integer.valueOf(ownerType) },
				ownerIds,
				new MultiRow(),
				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
	}
    
    public List<User> getUsersByOwnerWithoutAdmin(int ownerType,
			String[] ownerIds) {
		return queryForInSQL(
				SQL_FIND_USERS_BY_USERTYPE_OWNERIDS,
				new Object[] { Integer.valueOf(ownerType), User.TYPE_NORMAL },
				ownerIds,
				new MultiRow(),
				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
	}
    
    public List<User> getUsersByUnitAndType(String unitId, int type) {
        return query(SQL_FIND_USERS_BY_UNITID_TYPE, new Object[] { unitId, Integer.valueOf(type),
                User.TEACHER_LOGIN }, new MultiRow());
    }
	
	public List<User> getUsersByFaintness(String unitId, String realName) {
		return query(SQL_FIND_USERS_BY_UNITID_REALNAME, new Object[] { unitId,
				User.TEACHER_LOGIN, "%" + realName + "%" }, new MultiRow());
	}

	public Map<String, User> getUserMap(String[] userIds) {
		return queryForInSQL(
				SQL_FIND_USERS_BY_IDS,
				null,
				userIds,
				new MapRow(),
				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
	}

    public Map<String, User> getUserMap(String unitId, int ownerType) {
        return queryForMap(SQL_FIND_USERS_BY_UNITID_OWNTYPE, new Object[] { unitId, ownerType },
                new MapRow());
    }
    
    @Override
    public Map<String, User> getTeacherUserMap(String unitId, int ownerType) {
    	return queryForMap(SQL_FIND_USERS_BY_UNITID_OWNTYPE, new Object[] { unitId, ownerType },
                new MapRowMapper<String, User>() {
    				@Override
    				public String mapRowKey(ResultSet rs, int rowNum)
    						throws SQLException {
    					return rs.getString("owner_id");
    				}
    				@Override
    				public User mapRowValue(ResultSet rs, int rowNum)
    						throws SQLException {
    					return setField(rs);
    				}
				});
    }

    public Map<String, User> getAdminUserMap(String[] unitIds) {
        Object[] objs = new Object[] { User.TEACHER_LOGIN, User.USER_TYPE_SUBADMIN,
                User.USER_TYPE_TOPADMIN };
        return queryForInSQL(SQL_FIND_ADMINUSERS_BY_UNITIDS, objs, unitIds,
                new MapRowMapper<String, User>() {
                    public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString("unit_id");
                    }

					public User mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return setField(rs);
					}
				},
				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
	}

    public Map<String, User> getUsersMapByName(String[] names) {
        if (User.isUsernameNotCaseSensitive()) {
            for (int i = 0; i < names.length; i++) {
                String username = names[i];
                if (null != username) {
                    names[i] = username.toLowerCase();
                }
            }
        }
        return queryForInSQL(SQL_FIND_USERS_BY_NAMES, null, names,
                new MapRowMapper<String, User>() {
                    public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
                        String username = rs.getString("username");
                        if (User.isUsernameNotCaseSensitive()) {
                            if(null != username){
                                username = username.toLowerCase();
                            }
                        }
                        return username;
                    }

					public User mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return setField(rs);
					}
				},
				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
	}

    public Map<String, User> getUserMapByOwner(int ownerType,String[] ownerIds) {
        return queryForInSQL(SQL_FIND_USERS_BY_OWNERIDS, new Object[]{Integer.valueOf(ownerType)}, ownerIds,
                new MapRowMapper<String, User>() {
                    public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString("owner_id");
                    }

                    	public User mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return setField(rs);
					}
				},
				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
    }
    
    public List<User> getUsersFaintness(String realName, String unitId) {
        if (realName == null) {
            return null;
        }
        if (User.isUsernameNotCaseSensitive()) {
        	realName = realName.toLowerCase();
        }

        return query(SQL_SERRCH_USER_BY_NAME_INUNIT, new Object[] { unitId, User.TEACHER_LOGIN,
                "%" + realName + "%" }, new MultiRow());
    }
	
	public List<User> getUserListByUnitId(String unitId, String realName, int ownerType, Pagination page){
		String userName = "";
		if(StringUtils.isBlank(realName)){
			userName = "%";
		}else{
			userName = "%"+realName+"%";
		}
		if(page == null){
			return query(SQL_FIND_LIST_BY_UNITID_PAGE, new Object[]{unitId, userName, ownerType}, new MultiRow());
		}
		return query(SQL_FIND_LIST_BY_UNITID_PAGE, new Object[]{unitId, userName, ownerType}, new MultiRow(), page);
	}
	
	@Override
	public List<User> getUsersBySearchName(String searchName, Pagination page) {
		searchName = "%"+searchName+"%";
		if(page == null){
			return query(SQL_FIND_LIST_BY_REALNAME_PAGE, new Object[]{searchName}, new MultiRow());
		}
		return query(SQL_FIND_LIST_BY_REALNAME_PAGE, new Object[]{searchName}, new MultiRow(), page);
	}
	
	@Override
	public List<User> getUsersWithOutPinYin() {
		return query(SQL_FIND_LIST_WITHOUT_PINYIN, new MultiRow());
	}
	
	@Override
	public List<User> getUsersBySearchName(String unitId, String searchName, Pagination page) {
		if(StringUtils.isNotBlank(unitId)){
			if(page!=null){
				return query(SQL_FIND_LIST_BY_PINYIN, new Object[]{unitId, searchName}, new MultiRow(), page);
			}else{
				return query(SQL_FIND_LIST_BY_PINYIN, new Object[]{unitId, searchName}, new MultiRow());
			}
		}else{
			if(page!=null){
				return query(SQL_FIND_LIST_BY_PINYIN_WITHOUT_UNITID, new Object[]{searchName}, new MultiRow(), page);
			}else{
				return query(SQL_FIND_LIST_BY_PINYIN_WITHOUT_UNITID, new Object[]{searchName}, new MultiRow());
			}
		}
	}
	
	@Override
	public List<User> getUsersByRealNameMobile(String realName, String mobilePhone, int ownerType) {
		return query(SQL_FIND_USER_BY_REALNAME_MOBILEPHONE, new Object[] { realName, mobilePhone, ownerType },
				new MultiRow());
	}

	@Override
	public List<User> getUsersByUnitIdAndName(String realName, String unitId) {
		String sql = "select * from base_user where is_deleted = 0 and owner_type = 2 and real_name = ? and unit_id = ?";
		return query(sql, new Object[] { realName, unitId },new MultiRow());
	}
	
	@Override
	public List<User> getUsersByUnitIds(String[] unitIds){
		return queryForInSQL(
			SQL_FIND_USERS_BY_UNITIDS,
			null,
			unitIds,
			new MultiRow(),
			" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
	}
	
	@Override
	public List<User> getUsersByDeptIds(String[] deptIds){
		return queryForInSQL(
			SQL_FIND_USERS_BY_DEPTIDS,
			null,
			deptIds,
			new MultiRow(),
			" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
	}
	
	@Override
	public Map<String, List<User>> getUserMapByUnitIds(String[] unitIds) {
		List<User> list = queryForInSQL(SQL_FIND_USERS_BY_UNITIDS, null, unitIds, 
				new MultiRowMapper<User>(){
			@Override
			public User mapRow(ResultSet rs, int arg1)
					throws SQLException {
				return setField(rs);
			}
		});
		Map<String, List<User>> map = new HashMap<String, List<User>>();
		for(User item : list){
			String key = item.getUnitid();
			List<User> l = map.get(key);
			if(l == null){
				l = new ArrayList<User>();
				map.put(key, l);
			}
			l.add(item);
		}
		return map;	
	}
}
