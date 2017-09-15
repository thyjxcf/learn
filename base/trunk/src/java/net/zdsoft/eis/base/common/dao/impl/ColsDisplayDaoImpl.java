package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.common.dao.ColsDisplayDao;
import net.zdsoft.eis.base.common.entity.ColsDisplay;
import net.zdsoft.eis.frame.client.BaseDao;

public class ColsDisplayDaoImpl extends BaseDao<ColsDisplay> implements ColsDisplayDao {

    private static final String SQL_FIND_COLSDISPLAY_BY_UNITID_COLSTYPE = "SELECT * FROM stusys_cols_display "
            + "WHERE unit_id=? AND cols_type=? AND cols_use <> -1";
    
    private static final String SQL_FIND_COLSDISPLAY_BY_UNITID_COLSTYPES = "SELECT * FROM stusys_cols_display "
            + "WHERE unit_id=? AND cols_use<>-1 AND cols_type in";

    private static final String SQL_FIND_COLSDISPLAY_BY_UNITID_COLSTYPE_COLSUSE = "SELECT * FROM stusys_cols_display "
            + "WHERE unit_id=? AND cols_type=? AND cols_use=? order by cols_order";
    
    private static final String SQL_FIND_COLSDISPLAY_BY_UNITID_COLSTYPE_COLSUSES = "SELECT * FROM stusys_cols_display "
            + "WHERE unit_id=? AND cols_use=? AND cols_type in";

    public ColsDisplay setField(ResultSet rs) throws SQLException {
        ColsDisplay colsDisplay = new ColsDisplay();
        colsDisplay.setId(rs.getString("id"));
        colsDisplay.setUnitId(rs.getString("unit_id"));
        colsDisplay.setColsType(rs.getString("cols_type"));
        colsDisplay.setColsName(rs.getString("cols_name"));
        colsDisplay.setColsCode(rs.getString("cols_code"));
        colsDisplay.setColsOrder(rs.getInt("cols_order"));
        colsDisplay.setColsConstraint(rs.getInt("cols_constraint"));
        colsDisplay.setColsUse(rs.getInt("cols_use"));
        colsDisplay.setColsKind(rs.getString("cols_kind"));
        colsDisplay.setColsMcode(rs.getString("cols_mcode"));
        return colsDisplay;
    }

    public List<ColsDisplay> getColsDisplays(String unitId, String type) {
        return query(SQL_FIND_COLSDISPLAY_BY_UNITID_COLSTYPE, new Object[] { unitId, type },
                new MultiRow());
    }
    
    @Override
    public List<ColsDisplay> getColsDisplays(String unitId, String[] type) {
    	return queryForInSQL(SQL_FIND_COLSDISPLAY_BY_UNITID_COLSTYPES, new Object[] {unitId}, type,  new MultiRow());
    }

    public List<ColsDisplay> getColsDisplays(String unitId, String type, Integer colsUse) {
        return query(SQL_FIND_COLSDISPLAY_BY_UNITID_COLSTYPE_COLSUSE, new Object[] { unitId, type,
                colsUse }, new MultiRow());

    }
    
    @Override
    public List<ColsDisplay> getColsDisplays(String unitId, String[] type,
    		Integer colsUse) {
    	 return queryForInSQL(SQL_FIND_COLSDISPLAY_BY_UNITID_COLSTYPE_COLSUSES, new Object[] { unitId,
                 colsUse },type, new MultiRow(), " order by cols_order");
    }

}
