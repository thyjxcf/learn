package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.dao.AppRegistryDao;
import net.zdsoft.eis.base.common.entity.AppRegistry;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;

/* 
 * <p>ZDSoft电子政务系统V3.6</p>
 * @author zhaosf
 * @since 1.0
 * @version $Id: AppRegistryDaoImpl.java,v 1.12 2007/01/17 05:31:43 zhaosf Exp $
 */
public class AppRegistryDaoImpl extends BaseDao<AppRegistry> implements AppRegistryDao {
	private static final String SQL_INSERT_APPREGISTRY = "INSERT INTO fpf_appregistry(appid,appcode,appname,"
			+ "sysname,sysid,unitid,isusing,image,displayorder,description,"
			+ "url,xurl,sharedataurl,testurl,type,parameters,checkuserurl,"
			+ "issync,databasepass,isencoded,underlingunituse,appintid,islogin,verify_key) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_APPREGISTRY_BY_IDS = "DELETE fpf_appregistry WHERE appid IN ";

	private static final String SQL_UPDATE_DISPLAYORDER_BY_APPID = "UPDATE fpf_appregistry SET displayorder = ? WHERE appid = ? ";

	private static final String SQL_UPDATE_APPREGISTRY = "UPDATE fpf_appregistry SET appid=?,appcode=?,"
			+ "appname=?,sysname=?,sysid=?,unitid=?,isusing=?,image=?,displayorder=?,"
			+ "description=?,url=?,xurl=?,sharedataurl=?,testurl=?,type=?,parameters=?,"
			+ "checkuserurl=?,issync=?,databasepass=?,isencoded=?,underlingunituse=?,appintid=?,islogin=?,verify_key=? WHERE appid=?";

	private static final String SQL_FIND_APPREGISTRY_BY_ID = "SELECT * FROM fpf_appregistry WHERE appid=?";

	private static final String SQL_FIND_APPREGISTRY_BY_IDS = "SELECT * FROM fpf_appregistry WHERE appid IN";

	private static final String SQL_FIND_APPREGISTRY_BY_UNITID_TYPE_SYSID = "SELECT * FROM fpf_appregistry "
			+ "WHERE unitid=? AND type=? AND sysid=? AND isusing='1'";

	private static final String SQL_FIND_APPREGISTRY_BY_UNITID = "SELECT * FROM fpf_appregistry WHERE unitid=? AND isusing='1'";

	private static final String SQL_FIND_APPREGISTRYS_BY_APPIDS = "SELECT displayorder,appid FROM fpf_appregistry WHERE appid in ";

	private static final String SQL_FIND_APPREGISTRYS_BY_UNITIDS_UNDERLINGUNITUSE = "SELECT * FROM fpf_appregistry"
			+ " WHERE underlingunituse like ? AND unitid in ";

	private static final String SQL_FIND_APPREGISTRYS_BY_APPCODE = "SELECT * FROM fpf_appregistry WHERE appcode=?";

	private static final String SQL_FIND_APPREGISTRYS_BY_UNITID_APPNAME = "SELECT * FROM fpf_appregistry WHERE unitid=? AND appname=?";

	private static final String SQL_FIND_MAX_DISPLAYORDER_BY_UNITID = "SELECT MAX(displayorder) FROM fpf_appregistry WHERE unitid=? ";

	@Override
	public AppRegistry setField(ResultSet rs) throws SQLException {
		AppRegistry appRegistry = new AppRegistry();
		appRegistry.setId(rs.getString("appid"));
		appRegistry.setAppcode(rs.getString("appcode"));
		appRegistry.setAppname(rs.getString("appname"));
		appRegistry.setSysname(rs.getString("sysname"));
		appRegistry.setSysid(rs.getString("sysid"));
		appRegistry.setUnitid(rs.getString("unitid"));
		appRegistry.setIsusing(rs.getString("isusing"));
		appRegistry.setImage(rs.getString("image"));
		appRegistry.setDisplayorder(rs.getInt("displayorder"));
		appRegistry.setDescription(rs.getString("description"));
		appRegistry.setUrl(rs.getString("url"));
		appRegistry.setXurl(rs.getString("xurl"));
		appRegistry.setSharedataurl(rs.getString("sharedataurl"));
		appRegistry.setTesturl(rs.getString("testurl"));
		appRegistry.setType(rs.getString("type"));
		appRegistry.setParameters(rs.getString("parameters"));
		appRegistry.setCheckuserurl(rs.getString("checkuserurl"));
		appRegistry.setIssync(rs.getString("issync"));
		appRegistry.setDatabasepass(rs.getString("databasepass"));
		appRegistry.setIsencoded(rs.getString("isencoded"));
		appRegistry.setUnderlingUnitUse(rs.getString("underlingunituse"));
		appRegistry.setAppintid(rs.getLong("appintid"));
		appRegistry.setIslogin(rs.getString("islogin"));
		appRegistry.setVerifyKey(rs.getString("verify_key"));
		return appRegistry;
	}

	public void insertAppRegistry(AppRegistry appRegistry) {
		appRegistry.setId(getGUID());
		appRegistry.setAppintid(getIncrementerKey());
		update(SQL_INSERT_APPREGISTRY, new Object[] { appRegistry.getId(),
				appRegistry.getAppcode(), appRegistry.getAppname(),
				appRegistry.getSysname(), appRegistry.getSysid(),
				appRegistry.getUnitid(), appRegistry.getIsusing(),
				appRegistry.getImage(), appRegistry.getDisplayorder(),
				appRegistry.getDescription(), appRegistry.getUrl(),
				appRegistry.getXurl(), appRegistry.getSharedataurl(),
				appRegistry.getTesturl(), appRegistry.getType(),
				appRegistry.getParameters(), appRegistry.getCheckuserurl(),
				appRegistry.getIssync(), appRegistry.getDatabasepass(),
				appRegistry.getIsencoded(), appRegistry.getUnderlingUnitUse(),
				appRegistry.getAppintid(), appRegistry.getIslogin(),
				appRegistry.getVerifyKey() }, new int[] { Types.CHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.CHAR, Types.CHAR, Types.VARCHAR, Types.INTEGER,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.CHAR, Types.VARCHAR, Types.VARCHAR,
				Types.CHAR, Types.VARCHAR, Types.CHAR, Types.CHAR,
				Types.INTEGER, Types.CHAR, Types.VARCHAR });
	}

	public void deleteAppRegistry(String[] appRegistryIds) {
		updateForInSQL(SQL_DELETE_APPREGISTRY_BY_IDS, null, appRegistryIds);
	}

	public void updateAppRegistry(AppRegistry appRegistry) {
		update(SQL_UPDATE_APPREGISTRY, new Object[] { appRegistry.getId(),
				appRegistry.getAppcode(), appRegistry.getAppname(),
				appRegistry.getSysname(), appRegistry.getSysid(),
				appRegistry.getUnitid(), appRegistry.getIsusing(),
				appRegistry.getImage(), appRegistry.getDisplayorder(),
				appRegistry.getDescription(), appRegistry.getUrl(),
				appRegistry.getXurl(), appRegistry.getSharedataurl(),
				appRegistry.getTesturl(), appRegistry.getType(),
				appRegistry.getParameters(), appRegistry.getCheckuserurl(),
				appRegistry.getIssync(), appRegistry.getDatabasepass(),
				appRegistry.getIsencoded(), appRegistry.getUnderlingUnitUse(),
				appRegistry.getAppintid(), appRegistry.getIslogin(),
				appRegistry.getVerifyKey(), appRegistry.getId() }, new int[] {
				Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.CHAR, Types.CHAR, Types.VARCHAR,
				Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.VARCHAR,
				Types.VARCHAR, Types.CHAR, Types.VARCHAR, Types.CHAR,
				Types.CHAR, Types.INTEGER, Types.CHAR, Types.VARCHAR,
				Types.CHAR });
	}

	public void saveOrUpdate(AppRegistry appRegistry) {
		if (null != appRegistry.getId() && !"".equals(appRegistry.getId())) {
			updateAppRegistry(appRegistry);
		} else {
			insertAppRegistry(appRegistry);
		}
	}

	public void updateDisplayOrder(List<Object[]> idsList) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (int i = 0; i < idsList.size(); i++) {
			Object[] rs = idsList.get(i);
			listOfArgs.add(new Object[] { rs[0], rs[1] });
		}
		int[] argTypes = { Types.INTEGER, Types.CHAR };
		batchUpdate(SQL_UPDATE_DISPLAYORDER_BY_APPID, listOfArgs, argTypes);
	}

	public AppRegistry getAppRegistryById(String appId) {
		return (AppRegistry) query(SQL_FIND_APPREGISTRY_BY_ID, appId,
				new SingleRow());
	}

	public List<AppRegistry> getAppRegistrys(String[] appRegistryIds) {
		return queryForInSQL(SQL_FIND_APPREGISTRY_BY_IDS, null, appRegistryIds,
				new MultiRow());
	}

	public List<AppRegistry> getAppRegistries(String unitId, String type,
			String sysId) {
		return query(SQL_FIND_APPREGISTRY_BY_UNITID_TYPE_SYSID, new String[] {
				unitId, type, sysId }, new MultiRow());
	}

	public List<AppRegistry> getAppRegistriesUsing(String unitid) {
		return query(SQL_FIND_APPREGISTRY_BY_UNITID, new String[] { unitid },
				new MultiRow());
	}

	public List<AppRegistry> getAppRegistries(String unitid) {
		String sql = SQL_FIND_APPREGISTRY_BY_UNITID + " ORDER BY displayorder";
		return query(sql, new String[] { unitid }, new MultiRow());
	}

	public List<Object[]> getDisplayOrder(String[] ids, boolean asc) {
		String desc = "";
		if (!asc) {
			desc = "DESC";
		}
		String postfix = " ORDER BY displayorder " + desc;
		List<Object[]> list = queryForInSQL(SQL_FIND_APPREGISTRYS_BY_APPIDS,
				null, ids, new MultiRowMapper<Object[]>() {

					public Object[] mapRow(ResultSet rs, int arg1)
							throws SQLException {
						Object[] objs = new Object[2];
						objs[0] = rs.getInt("displayorder");
						objs[1] = rs.getString("appid");
						return objs;
					}

				}, postfix);
		return list;
	}

	public List<AppRegistry> getAppRegistriesByUnitidsAndUse(String[] unitids,
			String underlingUnitUse) {
		List<AppRegistry> list = queryForInSQL(
				SQL_FIND_APPREGISTRYS_BY_UNITIDS_UNDERLINGUNITUSE,
				new Object[] { underlingUnitUse }, unitids, new MultiRow());
		return list;
	}

	public boolean isRepeatAppCode(String appid, String appcode) {
		String sql = SQL_FIND_APPREGISTRYS_BY_APPCODE;
		Object[] objs = null;
		if (null == appid || "".equals(appid)) {
			objs = new Object[] { appcode };
		} else {
			sql += " AND appid <> ? ";
			objs = new Object[] { appcode, appid };
		}

		List<AppRegistry> list = query(sql, objs, new MultiRow());
		if (null == list || list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isRepeatAppName(String appid, String appname, String unitid) {
		String sql = SQL_FIND_APPREGISTRYS_BY_UNITID_APPNAME;
		Object[] objs = null;
		if (null == appid || "".equals(appid)) {
			objs = new Object[] { unitid, appname };
		} else {
			sql += " AND appid <> ? ";
			objs = new Object[] { unitid, appname, appid };
		}

		List<AppRegistry> list = query(sql, objs, new MultiRow());
		if (null == list || list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public int getMaxDisplayOrder(String unitid) {
		return queryForInt(SQL_FIND_MAX_DISPLAYORDER_BY_UNITID,
				new Object[] { unitid });
	}

}
