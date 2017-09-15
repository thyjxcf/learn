package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.ModuleDao;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;

public class ModuleDaoImpl extends BaseDao<Module> implements ModuleDao {
	private static final String SQL_ENABLE_MODULE = "UPDATE sys_model SET mark = 1 ";
	private static final String SQL_DISABLE_MODULE_BY_SUBSYSTEM = "UPDATE sys_model SET mark = -1 WHERE subsystem=? ";
	private static final String SQL_ENABLE_MODULE_BY_SUBSYSTEM = "UPDATE sys_model SET mark = 1 WHERE subsystem=? and mark = -1 ";

	private static final String SQL_FIND_MODULE_BY_ID = "SELECT * FROM sys_model WHERE id=?";
	private static final String SQL_FIND_MODULE_BY_IDS = "SELECT * FROM sys_model WHERE mark=1 and id IN";
	private static final String SQL_FIND_MODULE_BY_MODELID = "SELECT * FROM sys_model WHERE model_id=?";
	private static final String SQL_FIND_MODULE_BY_MID = "SELECT * FROM sys_model WHERE mid=?";
	private static final String SQL_FIND_MODULE_BY_MID_UNITCLASS = "SELECT * FROM sys_model WHERE mid=? AND unitclass=?";
	private static final String SQL_FIND_MODULE_BY_URL = "SELECT * FROM sys_model WHERE unitclass=? AND substr(url,0,instr(url||'?','?') -1)=?";

	private static final String SQL_FIND_MODULE_BY_MIDS_UNITCLASS = "SELECT * FROM sys_model WHERE unitclass=? AND mid IN";

	private static final String SQL_FIND_MODULE_ID_ALL = "SELECT id FROM sys_model order by orderid";
	private static final String SQL_FIND_MODULE_ALL = "SELECT * FROM sys_model order by orderid";

	private static final String SQL_FIND_MODULE = "SELECT * FROM sys_model WHERE mark=1 order by orderid";

	private static final String SQL_FIND_MODULE_BY_SUBSYSTEM = "SELECT * FROM sys_model "
			+ "WHERE mark=1 AND subsystem=? order by orderid";
	private static final String SQL_FIND_MODULE_BY_SUBSYS_UNITCLASS = "SELECT * FROM sys_model "
			+ "WHERE mark=1 AND subsystem=? AND unitclass=? order by orderid";
	private static final String SQL_FIND_MODULE_BY_SUBSYS_UNITCLASS_PARENTID = "SELECT * FROM sys_model "
			+ "WHERE mark=1 AND subsystem=? AND unitclass=? AND parentid=? order by orderid";
	private static final String SQL_FIND_MODULE_BY_SUBSYS_UNITCLASS_PARENTID_UNITTYPE = "SELECT * FROM sys_model "
			+ "WHERE mark=1 AND subsystem=? AND unitclass=? AND parentid=? AND usertype like ? order by orderid";
	
	private static final String SQL_FIND_MODULE_BY_SUBSYS_UNITCLASS_PARENTID_UNITTYPE_FOR_PC = "SELECT * FROM sys_model "
		+ "WHERE mark=1 AND subsystem=? AND unitclass=? AND (parm is null or parm ='') AND parentid=? AND usertype like ? order by orderid";
	
	private static final String SQL_FIND_MODULE_BY_SUBSYS_UNITCLASS_PARENTID_UNITTYPE_FOR_MOBILE = "SELECT * FROM sys_model "
		+ "WHERE mark=1 AND unitclass=? AND parentid=? AND usertype like ? AND parm=? order by orderid";

	private static final String SQL_FIND_MODULE_BY_UNITCLASS = "SELECT * FROM sys_model "
			+ "WHERE mark=1 AND unitclass=? order by orderid";
	private static final String SQL_FIND_MODULE_BY_UNITCLASS_USERTYPE = "SELECT * FROM sys_model "
			+ "WHERE mark=1 AND unitclass=? AND usertype like ? order by orderid";
	private static final String SQL_FIND_MODULE_BY_UNITCLASS_PARENTID_UNITTYPE = "SELECT * FROM sys_model "
			+ "WHERE mark=1 AND unitclass=? AND parentid=? AND usertype like ? order by orderid";
	
	private static final String SQL_FIND_MODULE_BY_UNITCLASS_PARENTID_UNITTYPE_FOR_PC = "SELECT * FROM sys_model "
		+ "WHERE mark=1 AND unitclass=? AND parentid=? AND (parm is null or parm ='') AND usertype like ? order by orderid";
	
	private static final String SQL_FIND_MODULE_BY_UNITCLASS_PARENTID_UNITTYPE_FOR_MOBILE = "SELECT * FROM sys_model "
		+ "WHERE mark=1 AND unitclass=? AND parentid=? AND usertype like ? AND  parm =? order by orderid";

	private static final String SQL_FIND_SUBSYSTEM_BY_UNITCLASS_USERTYPE = "SELECT distinct subsystem FROM sys_model "
			+ "WHERE mark=1 AND unitclass=? AND usertype like ? ";

	public Module setField(ResultSet rs) throws SQLException {
		Module module = new Module();
		module.setId(rs.getLong("id"));
		module.setMid(rs.getString("mid"));
		module.setParentid(rs.getLong("parentid"));
		module.setOrderid(rs.getInt("orderid"));
		module.setName(rs.getString("name"));
		module.setType(rs.getString("type"));
		module.setPicture(rs.getString("picture"));
		module.setUrl(rs.getString("url"));
		module.setWidth(rs.getInt("width"));
		module.setHeight(rs.getInt("height"));
		module.setSubsystem(rs.getInt("subsystem"));
		module.setUsertype(rs.getString("usertype"));
		module.setUnitclass(rs.getInt("unitclass"));
		module.setIsassigned(rs.getInt("isassigned"));
		module.setDescription(rs.getString("description"));
		module.setWin(rs.getString("win"));
		module.setPbcommon(rs.getString("pbcommon"));
		module.setLimit(rs.getString("limit"));
		module.setVersion(rs.getString("version"));
		module.setFilelist(rs.getString("filelist"));
		module.setReldir(rs.getString("reldir"));
		module.setMainfile(rs.getString("mainfile"));
		module.setParm(rs.getString("parm"));
		module.setUselevel(rs.getInt("uselevel"));
		module.setActionenable(rs.getInt("actionenable"));
		module.setIsActive(rs.getInt("mark"));
		module.setCommon(rs.getBoolean("common"));
		module.setModelId(rs.getString("model_id"));
		module.setDataSubsystems(rs.getString("data_subsystems"));
		return module;
	}

	public void enableModules() {
		update(SQL_ENABLE_MODULE);
	}

	public void disableModules(int subSystemId) {
		update(SQL_DISABLE_MODULE_BY_SUBSYSTEM, new Object[] { new Integer(
				subSystemId) });
	}

	public void enableModules(int subSystemId) {
		update(SQL_ENABLE_MODULE_BY_SUBSYSTEM, new Object[] { new Integer(
				subSystemId) });
	}

	public Module getModule(int intId) {
		return (Module) query(SQL_FIND_MODULE_BY_ID, new Object[] { intId },
				new SingleRow());
	}

	public List<Module> getModules(Integer... intIds) {
		return queryForInSQL(SQL_FIND_MODULE_BY_IDS, null, intIds,
				new MultiRow());
	}

	public Map<Integer, Module> getModulesMap(Integer... intIds) {
		return queryForInSQL(SQL_FIND_MODULE_BY_IDS, null, intIds,
				new MapRowMapper<Integer, Module>() {

					public Integer mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getInt("id");
					}

					public Module mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return setField(rs);
					}
				});
	}

	public Module getModuleByModuleId(String moduleId) {
		List<Module> modules = query(SQL_FIND_MODULE_BY_MODELID, moduleId,
				new MultiRow());
		Module module = null;
		if (!modules.isEmpty()) {
			module = (Module) modules.get(0);
		}
		return module;
	}

	public Module getModuleForPB(String modId) {
		List<Module> modules = query(SQL_FIND_MODULE_BY_MID, modId,
				new MultiRow());
		Module module = null;
		if (!modules.isEmpty()) {
			module = (Module) modules.get(0);
		}
		return module;
	}

	public Module getModule(String modId, Integer unitClass) {
		List<Module> modules = query(SQL_FIND_MODULE_BY_MID_UNITCLASS,
				new Object[] { modId, unitClass }, new MultiRow());
		Module module = null;
		if (!modules.isEmpty()) {
			module = (Module) modules.get(0);
		}
		return module;
	}

	public List<Module> getModulesByUrl(int unitClass, String url) {
		return query(SQL_FIND_MODULE_BY_URL, new Object[] {
				new Integer(unitClass), url }, new MultiRow());
	}

	public List<Module> getModules(String[] modIds, Integer unitClass) {
		return queryForInSQL(SQL_FIND_MODULE_BY_MIDS_UNITCLASS,
				new Object[] { unitClass }, modIds, new MultiRow());
	}

	public List<Module> getModules(int subsystemId, int unitClass) {
		return query(SQL_FIND_MODULE_BY_SUBSYS_UNITCLASS, new Object[] {
				new Integer(subsystemId), new Integer(unitClass) },
				new MultiRow());
	}

	public List<Module> getModules(int subsystemId, int unitClass, Long parentId) {
		return query(SQL_FIND_MODULE_BY_SUBSYS_UNITCLASS_PARENTID,
				new Object[] { new Integer(subsystemId),
						new Integer(unitClass), new Long(parentId) },
				new MultiRow());
	}

	public List<Module> getModules(int subsystemId, int unitClass,
			Long parentId, int unitType) {
		String strunitType = "%" + String.valueOf(unitType) + ",%";
		String sql = "";
		Object[] objs;
		if (parentId <= -1) {
			sql = SQL_FIND_MODULE_BY_SUBSYS_UNITCLASS_PARENTID_UNITTYPE;
			objs = new Object[] { new Integer(subsystemId),
					new Integer(unitClass), new Long(parentId), strunitType };
		} else {
			sql = SQL_FIND_MODULE_BY_UNITCLASS_PARENTID_UNITTYPE;
			objs = new Object[] { new Integer(unitClass), new Long(parentId),
					strunitType };
		}

		List<Module> modules = query(sql, objs, new MultiRow());
		return modules;
	}
	
	public List<Module> getModulesForPc(int subsystemId, int unitClass,
			Long parentId, int unitType) {
		String strunitType = "%" + String.valueOf(unitType) + ",%";
		String sql = "";
		Object[] objs;
		if (parentId <= -1) {
			sql = SQL_FIND_MODULE_BY_SUBSYS_UNITCLASS_PARENTID_UNITTYPE_FOR_PC;
			objs = new Object[] { new Integer(subsystemId),
					new Integer(unitClass), new Long(parentId), strunitType };
		} else {
			sql = SQL_FIND_MODULE_BY_UNITCLASS_PARENTID_UNITTYPE_FOR_PC;
			objs = new Object[] { new Integer(unitClass), new Long(parentId),
					strunitType };
		}

		List<Module> modules = query(sql, objs, new MultiRow());
		return modules;
	}
	
	public List<Module> getModulesForMobile(Long parentId, int unitClass,
			int unitType, String parm) {
		String strunitType = "%" + String.valueOf(unitType) + ",%";
		String sql = "";
		Object[] objs;
		if (parentId <= -1) {
			sql = SQL_FIND_MODULE_BY_SUBSYS_UNITCLASS_PARENTID_UNITTYPE_FOR_MOBILE;
			objs = new Object[] {new Integer(unitClass), new Long(parentId), strunitType, parm};
		} else {
			sql = SQL_FIND_MODULE_BY_UNITCLASS_PARENTID_UNITTYPE_FOR_MOBILE;
			objs = new Object[] { new Integer(unitClass), new Long(parentId),
					strunitType, parm};
		}

		List<Module> modules = query(sql, objs, new MultiRow());
		return modules;
	}

	public List<Integer> getAllModuleIds() {
		return query(SQL_FIND_MODULE_ID_ALL, new MultiRowMapper<Integer>() {
			public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getInt("id");
			}
		});
	}

	public Map<Integer, Module> getAllModuleMap() {
		return queryForMap(SQL_FIND_MODULE_ALL,
				new MapRowMapper<Integer, Module>() {

					public Integer mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getInt("id");
					}

					public Module mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return setField(rs);
					}
				});
	}

	public List<Module> getEnabledModules() {
		return query(SQL_FIND_MODULE, new MultiRow());
	}

	public List<Module> getEnabledModules(long subsystemId) {
		return (List<Module>) query(SQL_FIND_MODULE_BY_SUBSYSTEM,
				new Object[] { subsystemId }, new MultiRow());
	}

	public List<Module> getEnabledModulesByUnitClass(int unitClass) {
		return query(SQL_FIND_MODULE_BY_UNITCLASS, new Object[] { new Integer(
				unitClass) }, new MultiRow());
	}

	public List<Module> getEnabledModules(int unitClass, int unitType) {
		String strunitType = "%" + String.valueOf(unitType) + ",%";
		return query(SQL_FIND_MODULE_BY_UNITCLASS_USERTYPE, new Object[] {
				new Integer(unitClass), strunitType }, new MultiRow());
	}

	public List<Integer> getEnabledSubsytems(int unitClass, int unitType) {
		String strunitType = "%" + String.valueOf(unitType) + ",%";
		List<Integer> list = query(SQL_FIND_SUBSYSTEM_BY_UNITCLASS_USERTYPE,
				new Object[] { new Integer(unitClass), strunitType },
				new MultiRowMapper<Integer>() {

					public Integer mapRow(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getInt("subsystem");
					}

				});
		return list;
	}

}
