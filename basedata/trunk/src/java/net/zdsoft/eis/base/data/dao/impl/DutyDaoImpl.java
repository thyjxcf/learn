package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.dao.DutyDao;
import net.zdsoft.eis.base.data.entity.Duty;
import net.zdsoft.eis.frame.client.BaseDao;

public class DutyDaoImpl extends BaseDao<Duty> implements DutyDao {

    private static final String SQL_INSERT_DUTY = "INSERT INTO stusys_duty(id,unit_id,this_id,"
            + "content,type,updatestamp,is_deleted) " + "VALUES(?,?,?,?,?,?,?)";

    private static final String SQL_UPDATE_DUTY = "UPDATE stusys_duty SET unit_id=?,this_id=?,"
            + "content=?,type=?,updatestamp=?,is_deleted=? WHERE id=?";

    private static final String SQL_DELETE_DUTY_BY_IDS = "UPDATE stusys_duty SET is_deleted = '1',updatestamp=? WHERE id IN ";

    private static final String SQL_DELETE_DUTY_BY_UNITIDS = "UPDATE stusys_duty SET is_deleted = '1',updatestamp=? WHERE unit_id IN ";

    private static final String SQL_FIND_DUTY_BY_UNITID = "SELECT * FROM stusys_duty WHERE unit_id=? AND is_deleted = '0' ORDER BY this_id";

    public Duty setField(ResultSet rs) throws SQLException {
        Duty duty = new Duty();
        duty.setId(rs.getString("id"));
        duty.setUnitId(rs.getString("unit_id"));
        duty.setThisId(rs.getString("this_id"));
        duty.setContent(rs.getString("content"));
        duty.setType(rs.getInt("type"));
        duty.setUpdatestamp(rs.getInt("updatestamp"));
        duty.setIsDeleted(rs.getString("is_deleted"));
        return duty;
    }

    public void insertDuty(Duty duty) {
        duty.setId(getGUID());
        duty.setIsDeleted("0");
        duty.setUpdatestamp(getUpdatestamp());
        update(SQL_INSERT_DUTY, new Object[] { duty.getId(), duty.getUnitId(), duty.getThisId(),
                duty.getContent(), duty.getType(), duty.getUpdatestamp(), duty.getIsDeleted() },
                new int[] { Types.CHAR, Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
                        Types.INTEGER, Types.CHAR });
    }

    public void updateDuty(Duty duty) {
        duty.setIsDeleted("0");
        duty.setUpdatestamp(getUpdatestamp());
        update(SQL_UPDATE_DUTY, new Object[] { duty.getUnitId(), duty.getThisId(),
                duty.getContent(), duty.getType(), duty.getUpdatestamp(), duty.getIsDeleted(),
                duty.getId() }, new int[] { Types.CHAR, Types.VARCHAR, Types.VARCHAR,
                Types.INTEGER, Types.INTEGER, Types.CHAR, Types.CHAR });
    }

    public void deleteDuty(String... dutyId) {
        updateForInSQL(SQL_DELETE_DUTY_BY_IDS, new Object[] { getUpdatestamp() }, dutyId);
    }

    public void deleteUnitDuty(String... unitIds) {
        updateForInSQL(SQL_DELETE_DUTY_BY_UNITIDS, new Object[] { getUpdatestamp() }, unitIds);
    }

    public List<Duty> getDuties(String unitId) {
        return query(SQL_FIND_DUTY_BY_UNITID, unitId, new MultiRow());
    }

    public Map<String, Duty> getDutyMap(String unitId) {
        return queryForMap(SQL_FIND_DUTY_BY_UNITID, new Object[] { unitId }, new MapRow());
    }

}
