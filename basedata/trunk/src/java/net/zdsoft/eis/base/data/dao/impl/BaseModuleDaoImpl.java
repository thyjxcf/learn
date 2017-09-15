package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.data.dao.BaseModuleDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;

import org.apache.commons.lang.StringUtils;

public class BaseModuleDaoImpl extends BaseDao<Module> implements BaseModuleDao {
    private static final String SQL_DIABLE_MODULE = "UPDATE sys_model SET mark = 0 where id in ";

    private static final String SQL_DIABLE_MODULE_NOT = "UPDATE sys_model SET mark = 0 where id NOT in ";
    
    private static final String SQL_FIND_PARENT_MODULES = "SELECT id,name FROM sys_model where parentid=-1 ";

    private static final String SQL_UPDATE_MODULE = "UPDATE sys_model set name=?,subsystem=?,parentid=?,"
            + "unitclass=?,limit=?,orderid=?, mark=? where id=?";

    private static final String SQL_FIND_ALL_MODULE_BY_PAGE = "SELECT * FROM sys_model where 1=1 ";

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

    public List<Module> takeModulesByPage(String unitClass, String subSystemId, Pagination page) {
        StringBuffer sql = new StringBuffer(SQL_FIND_ALL_MODULE_BY_PAGE);
        if (StringUtils.isNotBlank(unitClass)) {
            sql.append(" and unitclass=" + unitClass);
        }
        if (StringUtils.isNotBlank(subSystemId)) {
            sql.append(" and subsystem=" + subSystemId);
        }
        sql.append(" order by subsystem,orderid");
        List<Module> modList = query(sql.toString(), new MultiRow(), page);
        return null == modList ? new ArrayList<Module>() : modList;
    }

    public void updateCloseMark(Long[] ids) {
        this.updateForInSQL(SQL_DIABLE_MODULE, new Object[] {}, ids);
    }

    public void updateCloseMarkNotIn(Long[] ids){
        this.updateForInSQL(SQL_DIABLE_MODULE_NOT, new Object[] {}, ids);
    }
    
    public List<Module> takeParentModules(String subSystemId, String unitClass) {
        StringBuffer sql = new StringBuffer(SQL_FIND_PARENT_MODULES);
        if (StringUtils.isNotBlank(subSystemId)) {
            sql.append(" and subsystem=" + subSystemId);
        }
        if (StringUtils.isNotBlank(unitClass)) {
            sql.append(" and unitclass=" + unitClass);
        }

        List<Module> modList = query(sql.toString(), new MultiRowMapper<Module>() {
            @Override
            public Module mapRow(ResultSet rs, int i) throws SQLException {
                Module module = new Module();
                module.setId(rs.getLong("id"));
                module.setName(rs.getString("name"));
                return module;
            }

        });
        return null == modList ? new ArrayList<Module>() : modList;
    }

    public void updateModule(Module m) {
        update(SQL_UPDATE_MODULE, new Object[] { m.getName(), m.getSubsystem(), m.getParentid(),
                m.getUnitclass(), m.getLimit(), m.getOrderid(), m.getIsActive(), m.getId() });
    }
}
