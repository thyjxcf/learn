package net.zdsoft.eis.base.common.dao.impl;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import net.zdsoft.eis.base.common.dao.UnitIniDao;
import net.zdsoft.eis.base.common.entity.UnitIni;
import net.zdsoft.eis.frame.client.BaseDao;

public class UnitIniDaoImpl extends BaseDao<UnitIni> implements UnitIniDao {
    private static final String SQL_INSERT_UNITINI = "INSERT INTO sys_systemini_unit(id,iniid,name,"
            + "dvalue,description,nowvalue,viewable,unitid,flag,validatejs,"
            + "coercive,orderid) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SQL_UPDATE_UNITINI = "UPDATE sys_systemini_unit SET iniid=?,name=?,"
            + "dvalue=?,description=?,nowvalue=?,viewable=?,unitid=?,flag=?,validatejs=?,"
            + "coercive=?,orderid=? WHERE id=?";
    private static final String SQL_FIND_UNITINIS_BY_UNITID_INIID = "SELECT * FROM sys_systemini_unit WHERE unitid=? AND iniid =?";
    private static final String SQL_FIND_UNITINIS_BY_UNITID = "SELECT * FROM sys_systemini_unit WHERE unitid=? ";

    public UnitIni setField(ResultSet rs) throws SQLException {
        UnitIni unitIni = new UnitIni();
        unitIni.setId(rs.getLong("id"));
        unitIni.setIniid(rs.getString("iniid"));
        unitIni.setName(rs.getString("name"));
        unitIni.setDefaultValue(rs.getString("dvalue"));
        unitIni.setDescription(rs.getString("description"));
        unitIni.setNowValue(rs.getString("nowvalue"));
        unitIni.setIsVisible(rs.getInt("viewable"));
        unitIni.setUnitid(rs.getString("unitid"));
        unitIni.setFlag(rs.getString("flag"));
        unitIni.setValidatejs(rs.getString("validatejs"));
        unitIni.setCoercive(rs.getInt("coercive"));
        unitIni.setOrderid(rs.getInt("orderid"));
        return unitIni;
    }

    public void insertUnitIni(UnitIni unitIni) {
        unitIni.setId(getIncrementerKey());
        update(SQL_INSERT_UNITINI, new Object[] { unitIni.getId(), unitIni.getIniid(),
                unitIni.getName(), unitIni.getDefaultValue(), unitIni.getDescription(),
                unitIni.getNowValue(), unitIni.getIsVisible(), unitIni.getUnitid(),
                unitIni.getFlag(), unitIni.getValidatejs(), unitIni.getCoercive(),
                unitIni.getOrderid() }, new int[] { Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.CHAR,
                Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER });
    }

    public void updateUnitIni(UnitIni unitIni) {
        update(SQL_UPDATE_UNITINI, new Object[] { unitIni.getIniid(), unitIni.getName(),
                unitIni.getDefaultValue(), unitIni.getDescription(), unitIni.getNowValue(),
                unitIni.getIsVisible(), unitIni.getUnitid(), unitIni.getFlag(),
                unitIni.getValidatejs(), unitIni.getCoercive(), unitIni.getOrderid(),
                unitIni.getId() }, new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.CHAR, Types.VARCHAR,
                Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.CHAR });
    }

    public UnitIni getUnitIni(String unitId, String iniId) {
        return query(SQL_FIND_UNITINIS_BY_UNITID_INIID, new Object[] { unitId, iniId },
                new SingleRow());
    }

    public List<UnitIni> getUnitIniList(String unitId) {
        return query(SQL_FIND_UNITINIS_BY_UNITID, new Object[] { unitId}, new MultiRow());
    }

}
