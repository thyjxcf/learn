package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.ColsDisplay;
import net.zdsoft.eis.base.data.dao.BaseColsDisplayDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.leadin.util.UUIDGenerator;

import org.apache.commons.lang.StringUtils;

/**
 * @author yanb
 * 
 */
public class BaseColsDisplayDaoImpl extends BaseDao<ColsDisplay> implements BaseColsDisplayDao {
    private static final String SQL_INSERT_COLSDISPLAY = "INSERT INTO stusys_cols_display(id,unit_id,cols_type,"
            + "cols_name,cols_code,cols_order,cols_constraint,cols_use,cols_kind,cols_mcode) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?)";

    private static final String SQL_DELETE_COLSDISPLAY_BY_IDS = "DELETE stusys_cols_display WHERE id IN ";

    private static final String SQL_UPDATE_COLSDISPLAY = "UPDATE stusys_cols_display SET unit_id=?,cols_type=?,"
            + "cols_name=?,cols_code=?,cols_order=?,cols_constraint=?,cols_use=?,cols_kind=?,cols_mcode=? WHERE id=?";

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

    /**
     * 拼装的插入参数数组
     * 
     * @author "yangk" Jul 5, 2010 8:45:44 PM
     * @param colsDisplay
     * @return
     */
    private Object[] getInsertArgs(ColsDisplay colsDisplay) {
        return new Object[] {
                StringUtils.isBlank(colsDisplay.getId()) ? UUIDGenerator.getUUID() : colsDisplay
                        .getId(), colsDisplay.getUnitId(), colsDisplay.getColsType(),
                colsDisplay.getColsName(), colsDisplay.getColsCode(), colsDisplay.getColsOrder(),
                colsDisplay.getColsConstraint(), colsDisplay.getColsUse(),
                colsDisplay.getColsKind(), colsDisplay.getColsMcode() };
    }

    /**
     * 拼装的插入参数类型数组
     * 
     * @author "yangk" Jul 5, 2010 8:46:33 PM
     * @return
     */
    private int[] getInsertArgTypes() {
        return new int[] { Types.CHAR, Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR };
    }

    /**
     * 
     * 拼装的更新参数数组
     * 
     * @author "yangk" Jul 5, 2010 8:48:03 PM
     * @param colsDisplay
     * @return
     */
    private Object[] getUpdateArgs(ColsDisplay colsDisplay) {
        return new Object[] { colsDisplay.getUnitId(), colsDisplay.getColsType(),
                colsDisplay.getColsName(), colsDisplay.getColsCode(), colsDisplay.getColsOrder(),
                colsDisplay.getColsConstraint(), colsDisplay.getColsUse(),
                colsDisplay.getColsKind(), colsDisplay.getColsMcode(), colsDisplay.getId() };
    }

    /**
     * 拼装的更新参数类型数组
     * 
     * @author "yangk" Jul 5, 2010 8:48:46 PM
     * @return
     */
    private int[] getUpdateArgTypes() {
        return new int[] { Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
                Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.CHAR };
    }

    public void insertColsDisplay(ColsDisplay colsDisplay) {
        update(SQL_INSERT_COLSDISPLAY, getInsertArgs(colsDisplay), getInsertArgTypes());
    }

    public void batchInsertColsDisplay(List<ColsDisplay> colsDisplayList) {
        List<Object[]> listOfArgs = new ArrayList<Object[]>();
        for (ColsDisplay colsDisplay : colsDisplayList) {
            listOfArgs.add(getInsertArgs(colsDisplay));
        }
        batchUpdate(SQL_INSERT_COLSDISPLAY, listOfArgs, getInsertArgTypes());
    }

    public void deleteColsDisplay(String[] colsDisplayIds) {
        updateForInSQL(SQL_DELETE_COLSDISPLAY_BY_IDS, null, colsDisplayIds);
    }

    public void updateColsDisplay(ColsDisplay colsDisplay) {
        update(SQL_UPDATE_COLSDISPLAY, getUpdateArgs(colsDisplay), getUpdateArgTypes());
    }

    public void batchUpdateColsDisplay(List<ColsDisplay> colsDisplayList) {
        List<Object[]> listOfArgs = new ArrayList<Object[]>();
        for (ColsDisplay colsDisplay : colsDisplayList) {
            listOfArgs.add(getUpdateArgs(colsDisplay));
        }
        batchUpdate(SQL_UPDATE_COLSDISPLAY, listOfArgs, getUpdateArgTypes());
    }

}
