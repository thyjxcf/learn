package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.UserSetDao;
import net.zdsoft.eis.base.common.entity.UserSet;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;

import org.apache.commons.lang.StringUtils;

public class UserSetDaoImpl extends BaseDao<UserSet> implements UserSetDao {

	public static final String SQL_FIND_USER_SET_BY_USER_ID = "select * from base_user_set where user_id=?";
	public static final String SQL_FIND_USER_SET_BY_USER_IDS = "select * from base_user_set where user_id in ";
	public static final String SQL_UPDATE_USER_SET_FILE = "update base_user_set set show_file_path = ?, show_dir_id = ? where id = ?";
	public static final String SQL_UPDATE_USER_SKIN_BG = "update base_user_set set layout = ?, theme = ?, background_img = ?, background_color = ?,skin=? where id=?";
	public static final String SQL_INSERT_USER_SET_INFO = "insert into base_user_set(id, user_id, desk_app, skin, app_closed, show_file_path, show_dir_id, layout, theme, background_img, background_color) values(?,?,?,?,?,?,?,?,?,?,?)";
	
	@Override
	public UserSet getUserSetByUserId(String userId) {
		return query(SQL_FIND_USER_SET_BY_USER_ID, new Object[] { userId },
				new SingleRow());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String,UserSet> getUserSetMapByUserIds(String[] userIds) {
		return queryForInSQL(SQL_FIND_USER_SET_BY_USER_IDS, null, userIds, new MapRowMapper() {
			@Override
			public Object mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("user_id");
			}
			@Override
			public Object mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public UserSet setField(ResultSet rs) throws SQLException {
		UserSet userSet = new UserSet();
		userSet.setId(rs.getString("id"));
		userSet.setUserId(rs.getString("user_id"));
		userSet.setDeskApp(rs.getString("desk_app"));
		userSet.setSkin(rs.getString("skin"));
		userSet.setAppClosed(rs.getString("app_closed"));
//		userSet.setDefaultDirId(rs.getString("default_dir_id"));
//		userSet.setDefaultFilePath(rs.getString("default_file_path"));
		userSet.setDirId(rs.getString("show_dir_id"));
		userSet.setFilePath(rs.getString("show_file_path"));
		userSet.setLayout(rs.getString("layout"));
		userSet.setTheme(rs.getString("theme"));
		userSet.setBackgroundImg(rs.getString("background_img"));
		userSet.setBackgroundColor(rs.getString("background_color"));
		return userSet;
	}
	
	public UserSet save(UserSet userSet){
		if (StringUtils.isBlank(userSet.getId())){
			userSet.setId(createId());
		}
		Object[] args = new Object[]{
				userSet.getId(), userSet.getUserId(), 
			userSet.getDeskApp(), userSet.getSkin(), 
			userSet.getAppClosed(), userSet.getFilePath(), 
			userSet.getDirId(), userSet.getLayout(),
			userSet.getTheme(), userSet.getBackgroundImg(), 
			userSet.getBackgroundColor(), 
		};
		update(SQL_INSERT_USER_SET_INFO, args);
		return userSet;
	}
	
	public void updateUserSetFile(UserSet uset){
		update(SQL_UPDATE_USER_SET_FILE, new Object[]{uset.getFilePath(), uset.getDirId(), uset.getId()});
	}
	
	public void updateUserSkin(UserSet uset){
		update(SQL_UPDATE_USER_SKIN_BG, new Object[]{uset.getLayout(), 
				uset.getTheme(), uset.getBackgroundImg(), 
				uset.getBackgroundColor(), uset.getSkin(), uset.getId()});
	}

}
