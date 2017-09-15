/**
 * 
 */
package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.data.dao.BaseSystemIniDao;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author yanb
 * 
 */
public class BaseSystemIniDaoImpl extends BaseDao<SystemIni> implements BaseSystemIniDao {

    private static final String SQL_UPDATE_NOWVALUE = "UPDATE sys_option SET nowvalue=? WHERE iniid=?";
    private static final String SQL_FIND_SYSTEMINIS = "SELECT * FROM sys_option ORDER BY orderid";

    @Override
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

    public void updateNowValue(String iniId, String nowValue) {
        update(SQL_UPDATE_NOWVALUE, new String[] { nowValue, iniId });
    }

    public List<SystemIni> getAllSystemInis() {
        return (List<SystemIni>) query(SQL_FIND_SYSTEMINIS, new MultiRow());
    }
}
