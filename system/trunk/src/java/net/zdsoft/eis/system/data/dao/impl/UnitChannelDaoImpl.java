package net.zdsoft.eis.system.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.system.data.dao.UnitChannelDao;
import net.zdsoft.eis.system.data.entity.UnitChannel;

/**
 * @author yanb
 * 
 */
public class UnitChannelDaoImpl extends BaseDao<UnitChannel> implements UnitChannelDao {
    private static final String SQL_INSERT_UNITCHANNELINI = "INSERT INTO sys_unit_channel(id,unit_id,mark,"
            + "name,url,code,order_id,display,unit_class,display_level) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?)";

    private static final String SQL_UPDATE_UNITCHANNELINI = "UPDATE sys_unit_channel SET unit_id=?,mark=?,"
            + "name=?,url=?,code=?,order_id=?,display=?,unit_class=?,display_level=? WHERE id=?";

    private static final String SQL_DELETE_UNITCHANNELINI_BY_UNITID = "DELETE sys_unit_channel WHERE unit_id =?";

    private static final String SQL_FIND_UNITCHANNELINI_BY_UNITID_UNITCLASS = "SELECT * FROM sys_unit_channel "
            + "WHERE mark <>-1 AND unit_id = ? AND (unit_class = 0 or unit_class = ?) order by order_id";

    private static final String SQL_FIND_UNITCHANNELINI_BY_UNITID_DISPLAY_UNITCLASS_DISPLAYLEVEL = "SELECT * FROM sys_unit_channel "
            + "WHERE mark <>-1 AND unit_id = ? AND display=1 AND (unit_class = 0 or unit_class = ?) AND display_level = ? order by order_id";

    @Override
    public UnitChannel setField(ResultSet rs) throws SQLException {
        UnitChannel unitChannel = new UnitChannel();
        unitChannel.setId(rs.getString("id"));
        unitChannel.setUnitid(rs.getString("unit_id"));
        unitChannel.setMark(rs.getInt("mark"));
        unitChannel.setName(rs.getString("name"));
        unitChannel.setUrl(rs.getString("url"));
        unitChannel.setCode(rs.getString("code"));
        unitChannel.setOrderid(rs.getInt("order_id"));
        unitChannel.setDisplay(rs.getInt("display"));
        unitChannel.setUnitClass(rs.getInt("unit_class"));
        unitChannel.setDisplayLevel(rs.getInt("display_level"));
        return unitChannel;
    }

    public void insertUnitChannel(UnitChannel unitChannel) {
        unitChannel.setId(getGUID());
        update(SQL_INSERT_UNITCHANNELINI,
                new Object[] { unitChannel.getId(), unitChannel.getUnitid(), unitChannel.getMark(),
                        unitChannel.getName(), unitChannel.getUrl(), unitChannel.getCode(),
                        unitChannel.getOrderid(), unitChannel.getDisplay(),
                        unitChannel.getUnitClass(), unitChannel.getDisplayLevel() }, new int[] {
                        Types.CHAR, Types.CHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER });
    }

    public void updateUnitChannelIni(UnitChannel unitChannel) {
        update(SQL_UPDATE_UNITCHANNELINI, new Object[] { unitChannel.getUnitid(),
                unitChannel.getMark(), unitChannel.getName(), unitChannel.getUrl(),
                unitChannel.getCode(), unitChannel.getOrderid(), unitChannel.getDisplay(),
                unitChannel.getUnitClass(), unitChannel.getDisplayLevel(), unitChannel.getId() },
                new int[] { Types.CHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.CHAR });
    }

    public void deleteUnitChannel(String unitId) {
        update(SQL_DELETE_UNITCHANNELINI_BY_UNITID, unitId);
    }

    public List<UnitChannel> getUnitChannels(String unitId, int unitClass) {
        return query(SQL_FIND_UNITCHANNELINI_BY_UNITID_UNITCLASS, new Object[] { unitId,
                Integer.valueOf(unitClass) }, new MultiRow());
    }

    public List<UnitChannel> getUnitChannels(String unitId, int unitClass, int displayLevel) {
        return query(SQL_FIND_UNITCHANNELINI_BY_UNITID_DISPLAY_UNITCLASS_DISPLAYLEVEL,
                new Object[] { unitId, Integer.valueOf(unitClass), Integer.valueOf(displayLevel) },
                new MultiRow());
    }

}
