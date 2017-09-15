package net.zdsoft.eis.system.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.system.data.dao.ExternalAppDao;
import net.zdsoft.eis.system.data.entity.ExternalApp;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;

public class ExternalAppDaoImpl extends BaseDao<ExternalApp> implements
		ExternalAppDao {

	private static final String SQL_INSERT_EXTERNAL_APP = "INSERT INTO sys_external_app(id,unit_id,type,"
			+ "app_name,app_url,order_no,dir_id,file_path,temp) "
			+ "VALUES(?,?,?,?,?,?,?,?,?)";

	private static final String SQL_UPDATE_EXTERNAL_APP = "UPDATE sys_external_app SET unit_id=?,type=?,"
			+ "app_name=?,app_url=?,order_no=?,dir_id=?,file_path=?,temp=? WHERE id=?";

	private static final String SQL_DELETE_EXTERNAL_APP_BY_IDS = "DELETE sys_external_app WHERE id IN";
	
	private static final String SQL_UPDATE_EXTERNAL_APP_NOTEMP_BY_IDS = "UPDATE sys_external_app SET temp=0 WHERE id IN";

	private static final String SQL_FIND_EXTERNAL_APP_BY_UNIT_ID = "SELECT * FROM sys_external_app WHERE unit_id=? and type =? and temp=? order by order_no";

	@Override
	public ExternalApp setField(ResultSet rs) throws SQLException {
		ExternalApp app = new ExternalApp();
		app.setId(rs.getString("id"));
		app.setUnitId(rs.getString("unit_id"));
		app.setType(rs.getInt("type"));
		app.setAppName(rs.getString("app_name"));
		app.setAppUrl(rs.getString("app_url"));
		app.setOrderNo(rs.getInt("order_no"));
		app.setDirId(rs.getString("dir_id"));
		app.setFilePath(rs.getString("file_path"));
		app.setTemp(rs.getBoolean("temp"));
		return app;
	}

	public void addExternalApp(ExternalApp app) {
		app.setId(getGUID());
		update(SQL_INSERT_EXTERNAL_APP,
				new Object[] { app.getId(), app.getUnitId(), app.getType(),
						app.getAppName(), app.getAppUrl(), app.getOrderNo(),
						app.getDirId(), app.getFilePath(), app.getTemp()?1:0 },
				new int[] { Types.CHAR, Types.CHAR, Types.INTEGER,
						Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
						Types.CHAR, Types.VARCHAR, Types.INTEGER });
	}

	public void updateExternalApp(ExternalApp app) {
		update(SQL_UPDATE_EXTERNAL_APP,
				new Object[] { app.getUnitId(), app.getType(),
						app.getAppName(), app.getAppUrl(), app.getOrderNo(),
						app.getDirId(), app.getFilePath(), app.getTemp()?1:0, app.getId() },
				new int[] { Types.CHAR, Types.INTEGER, Types.VARCHAR,
						Types.VARCHAR, Types.INTEGER, Types.CHAR,
						Types.VARCHAR, Types.INTEGER, Types.CHAR });
	}

	public void deleteExternalApp(String... ids) {
		updateForInSQL(SQL_DELETE_EXTERNAL_APP_BY_IDS, null, ids);
	}
	
	public void updateExternalAppNotTemp(String... ids){
		updateForInSQL(SQL_UPDATE_EXTERNAL_APP_NOTEMP_BY_IDS, null, ids);
	}

	public List<ExternalApp> getExternalAppListByUnitId(String unitId,
			int type, int num, boolean temp) {
		if (num == 0)
			return query(SQL_FIND_EXTERNAL_APP_BY_UNIT_ID, new Object[] {
					unitId, type, temp?1:0 }, new MultiRow());
		else
			return queryForTop(SQL_FIND_EXTERNAL_APP_BY_UNIT_ID, new Object[] {
					unitId, type, temp?1:0 }, new int[] { Types.CHAR, Types.INTEGER },
					new MultiRow(), num);
	}

	public List<ExternalApp> getExternalAppListByUnionId(String unionId,
			int type, int num, boolean temp) {
		String sql = "SELECT s.*,b.unit_name FROM sys_external_app s,base_unit b WHERE s.unit_id=b.id and b.union_code like ? and s.type =? and s.temp=? order by b.unit_name";
		if (num == 0)
			return query(sql, new Object[] { unionId + "%", type, temp?1:0 },
					new MultiRowMapper<ExternalApp>() {

						@Override
						public ExternalApp mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							ExternalApp app = new ExternalApp();
							app.setId(rs.getString("id"));
							app.setUnitId(rs.getString("unit_id"));
							app.setType(rs.getInt("type"));
							app.setAppName(rs.getString("app_name"));
							app.setAppUrl(rs.getString("app_url"));
							app.setOrderNo(rs.getInt("order_no"));
							app.setDirId(rs.getString("dir_id"));
							app.setFilePath(rs.getString("file_path"));
							app.setUnitName(rs.getString("unit_name"));
							return app;
						}
					});
		else
			return queryForTop(sql, new Object[] { unionId + "%", type, temp?1:0 },
					new int[] { Types.CHAR, Types.INTEGER, Types.INTEGER },
					new MultiRowMapper<ExternalApp>() {

						@Override
						public ExternalApp mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							ExternalApp app = new ExternalApp();
							app.setId(rs.getString("id"));
							app.setUnitId(rs.getString("unit_id"));
							app.setType(rs.getInt("type"));
							app.setAppName(rs.getString("app_name"));
							app.setAppUrl(rs.getString("app_url"));
							app.setOrderNo(rs.getInt("order_no"));
							app.setDirId(rs.getString("dir_id"));
							app.setFilePath(rs.getString("file_path"));
							app.setUnitName(rs.getString("unit_name"));
							return app;
						}
					}, num);
	}

	public List<ExternalApp> getExternalAppListByCondition(String unionId,
			int type, String unitName, boolean temp, Pagination page) {
		if (StringUtils.isBlank(unitName))
			unitName = "";
		String sql = "SELECT s.*,b.unit_name FROM sys_external_app s,base_unit b WHERE s.unit_id=b.id and b.union_code like ? and b.unit_name like ? and s.type =? and s.temp=? order by b.unit_name";
		return query(sql, new Object[] { unionId+"%", "%" + unitName + "%", type, temp?1:0 },
				new MultiRowMapper<ExternalApp>() {

					@Override
					public ExternalApp mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						ExternalApp app = new ExternalApp();
						app.setId(rs.getString("id"));
						app.setUnitId(rs.getString("unit_id"));
						app.setType(rs.getInt("type"));
						app.setAppName(rs.getString("app_name"));
						app.setAppUrl(rs.getString("app_url"));
						app.setOrderNo(rs.getInt("order_no"));
						app.setDirId(rs.getString("dir_id"));
						app.setFilePath(rs.getString("file_path"));
						app.setUnitName(rs.getString("unit_name"));
						return app;
					}
				}, page);
	}

}
