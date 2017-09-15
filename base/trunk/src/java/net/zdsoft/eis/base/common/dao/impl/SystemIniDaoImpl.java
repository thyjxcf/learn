package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.common.dao.SystemIniDao;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.frame.client.BaseDao;

public class SystemIniDaoImpl extends BaseDao<SystemIni> implements SystemIniDao {
    private static final String SQL_FIND_SYSTEMINI_BY_ID = "SELECT * FROM sys_option WHERE id=?";

    private static final String SQL_FIND_SYSTEMINI_BY_INIID = "SELECT * FROM sys_option WHERE iniid=?";

    private static final String SQL_FIND_SYSTEMINI_BY_VISIBLE = "SELECT * FROM sys_option WHERE viewable=? ";
    
    private static final String SQL_UPDATE_NOWVALUE = "UPDATE sys_option SET nowvalue=? WHERE iniid=?";

    public SystemIni setField(ResultSet rs) throws SQLException {
        SystemIni systemIni = new SystemIni();
        systemIni.setId(rs.getString("id"));
        systemIni.setIniid(rs.getString("iniid"));
        systemIni.setName(rs.getString("name"));
        systemIni.setDefaultValue(rs.getString("dvalue"));
        systemIni.setDescription(rs.getString("description"));
        systemIni.setNowValue(rs.getString("nowvalue"));
        systemIni.setIsVisible(rs.getInt("viewable"));
        systemIni.setValidateJS(rs.getString("validatejs"));
        systemIni.setOrderId(rs.getInt("orderid"));
        systemIni.setSubSystemId(rs.getString("subsystemid"));
        systemIni.setCoercive(rs.getInt("coercive"));
        return systemIni;
    }

    public SystemIni getSystemIni(Long id) {
        return query(SQL_FIND_SYSTEMINI_BY_ID, String.valueOf(id), new SingleRow());
    }

    public SystemIni getSystemIni(String iniId) {
        return query(SQL_FIND_SYSTEMINI_BY_INIID, iniId, new SingleRow());
    }

    public List<SystemIni> getSystemInis(Integer visible) {
        return query(SQL_FIND_SYSTEMINI_BY_VISIBLE, String.valueOf(visible), new MultiRow());
    }
    
    public void updateNowValue(String iniId, String nowValue) {
        update(SQL_UPDATE_NOWVALUE, new String[] { nowValue, iniId });
    }

}
