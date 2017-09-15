/**
 * 
 */
package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.dao.SimpleModuleDao;
import net.zdsoft.eis.base.common.entity.SimpleModule;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;

/**
 * @author zhaosf
 * @since 1.0
 * @version $Id: ModuleDaoImpl.java, v 1.0 2007-11-9 下午05:08:13 zhaosf Exp $
 */

public class SimpleModuleDaoImpl extends BaseDao<SimpleModule> implements
		SimpleModuleDao {

	private static final String SQL_FIND_MODULE_BY_PARENTID = "SELECT * FROM sys_platform_model WHERE platform=? AND subsystem=? and parentid=? ORDER BY orderid";
	
	private static final String SQL_FIND_MODULE_BY_PLATFORM = "SELECT * FROM sys_platform_model WHERE platform=? and parentid=?";
	
	private static final String SQL_FIND_MODULE_BY_ID = "SELECT * FROM sys_platform_model WHERE id=?";

	private static final String SQL_FIND_SUBSYSTEM_BY_PLATFORM = "SELECT distinct subsystem FROM sys_platform_model WHERE platform=? and mark = 1";

	private static final String SQL_FIND_MODULE_BY_IDS = "SELECT * FROM sys_platform_model WHERE id IN";

	private static final String SQL_FIND_MODULE = "SELECT * FROM sys_platform_model ";
	
	private static final String SQL_DISABLE_MODULE_BY_SUBSYSTEM = "UPDATE sys_platform_model SET mark = -1 WHERE subsystem=? ";
	
	private static final String SQL_ENABLE_MODULE_BY_SUBSYSTEM = "UPDATE sys_platform_model SET mark = 1 WHERE subsystem=? and mark = -1 ";


	public SimpleModule setField(ResultSet rs) throws SQLException {
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.setId(rs.getLong("id"));
		simpleModule.setMid(rs.getString("mid"));
		simpleModule.setParentid(rs.getLong("parentid"));
		simpleModule.setOrderid(rs.getInt("orderid"));
		simpleModule.setName(rs.getString("name"));
		simpleModule.setType(rs.getString("type"));
		simpleModule.setPicture(rs.getString("picture"));
		simpleModule.setUrl(rs.getString("url"));
		simpleModule.setSubsystem(rs.getInt("subsystem"));
		simpleModule.setUsertype(rs.getString("usertype"));
		simpleModule.setUnitclass(rs.getInt("unitclass"));
		simpleModule.setIsassigned(rs.getInt("isassigned"));
		simpleModule.setDescription(rs.getString("description"));
		simpleModule.setLimit(rs.getString("limit"));
		simpleModule.setUselevel(rs.getInt("uselevel"));
		simpleModule.setActionenable(rs.getInt("actionenable"));
		simpleModule.setIsActive(rs.getInt("mark"));
		simpleModule.setCommon(rs.getBoolean("common"));
		simpleModule.setDataSubsystems(rs.getString("data_subsystems"));
		simpleModule.setPlatform(rs.getInt("platform"));
		simpleModule.setWin(rs.getString("win"));
		return simpleModule;
	}

	public SimpleModule getModule(Long id) {
		return query(SQL_FIND_MODULE_BY_ID, new Object[] { id },
				new SingleRow());
	}

	public List<SimpleModule> getModules(int platform, int subsystem,
			Long parentId) {
		if (subsystem == 0) {
			return query(SQL_FIND_MODULE_BY_PLATFORM, new Object[] { platform,
					parentId }, new MultiRow());
		} else {
			return query(SQL_FIND_MODULE_BY_PARENTID, new Object[] { platform,
					subsystem, parentId }, new MultiRow());
		}
	}

	public Set<Integer> getActiveSubsytems(int platform) {
		List<Integer> list = query(SQL_FIND_SUBSYSTEM_BY_PLATFORM,
				new Object[] { Integer.valueOf(platform) },
				new MultiRowMapper<Integer>() {

					@Override
					public Integer mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getInt("subsystem");
					}
				});

		Set<Integer> set = new HashSet<Integer>();
		set.addAll(list);
		return set;
	}

	public Map<Integer, SimpleModule> getModulesMap(Integer... intIds) {
		return queryForInSQL(SQL_FIND_MODULE_BY_IDS, null, intIds,
				new MapRowMapper<Integer, SimpleModule>() {

					public Integer mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getInt("id");
					}

					public SimpleModule mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return setField(rs);
					}
				});
	}

	public Map<Integer, SimpleModule> getModulesMap() {
		return queryForMap(SQL_FIND_MODULE, null,
				new MapRowMapper<Integer, SimpleModule>() {

					public Integer mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getInt("id");
					}

					public SimpleModule mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return setField(rs);
					}
				});
	}
	
	public void disableModules(int subSystemId) {
		update(SQL_DISABLE_MODULE_BY_SUBSYSTEM, new Object[] { new Integer(
				subSystemId) });
	}

	public void enableModules(int subSystemId) {
		update(SQL_ENABLE_MODULE_BY_SUBSYSTEM, new Object[] { new Integer(
				subSystemId) });
	}

}
