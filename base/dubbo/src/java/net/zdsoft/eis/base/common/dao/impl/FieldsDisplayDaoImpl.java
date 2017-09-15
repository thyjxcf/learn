package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.basedata.remote.service.FieldsDisplayRemoteService;
import net.zdsoft.eis.base.common.dao.FieldsDisplayDao;
import net.zdsoft.eis.base.common.entity.FieldsDisplay;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * 
 *@author weixh
 *@since 2013-3-7
 */
public class FieldsDisplayDaoImpl extends BaseDao<FieldsDisplay> implements
		FieldsDisplayDao {
	
	@Autowired
	private FieldsDisplayRemoteService displayRemoteService;
	
	private static final String SQL_FIND_COLSDISPLAY_BY_UNITID_COLSTYPE = "SELECT * FROM base_fields_display "
            + "WHERE unit_id=? AND cols_type=? AND cols_use<>-1 order by parent_id,cols_order";

    private static final String SQL_FIND_COLSDISPLAY_BY_UNITID_COLSTYPE_COLSUSE = "SELECT * FROM base_fields_display "
            + "WHERE unit_id=? AND cols_type=? AND cols_use=? order by parent_id desc,cols_order";
    
    private static final String SQL_FIND_COLSDISPLAY_BY_PARENTID_COLSUSE = "SELECT * FROM base_fields_display "
            + "WHERE parent_id=? AND cols_use=? order by cols_order";
	
	public FieldsDisplay setField(ResultSet rs) throws SQLException {
	    FieldsDisplay fieldsDisplay = new FieldsDisplay();
	    fieldsDisplay.setId(rs.getString("id"));
	    fieldsDisplay.setUnitId(rs.getString("unit_id"));
	    fieldsDisplay.setColsType(rs.getString("cols_type"));
	    fieldsDisplay.setParentId(rs.getString("parent_id"));
	    fieldsDisplay.setIsExistsSubitem(rs.getInt("is_exists_subitem"));
	    fieldsDisplay.setColsName(rs.getString("cols_name"));
	    fieldsDisplay.setColsCode(rs.getString("cols_code"));
	    fieldsDisplay.setColsKind(rs.getString("cols_kind"));
	    fieldsDisplay.setColsMcode(rs.getString("cols_mcode"));
	    fieldsDisplay.setColsOrder(rs.getInt("cols_order"));
	    fieldsDisplay.setColsUse(rs.getInt("cols_use"));
	    return fieldsDisplay;
	}
	
	public List<FieldsDisplay> getColsDisplays(String unitId, String type) {
        return FieldsDisplay.dt(displayRemoteService.findByColsDisplays(unitId,type));
		
		/*return query(SQL_FIND_COLSDISPLAY_BY_UNITID_COLSTYPE, new Object[] { unitId, type },
                new MultiRow());*/
    }

    public List<FieldsDisplay> getColsDisplays(String unitId, String type, Integer colsUse) {
       return FieldsDisplay.dt(displayRemoteService.findByColsDisplays(unitId,type,colsUse));
    	
    		   
     //SELECT * FROM base_fields_display WHERE unit_id=? AND cols_type=? AND cols_use=? order by parent_id desc,cols_order
    	/*return query(SQL_FIND_COLSDISPLAY_BY_UNITID_COLSTYPE_COLSUSE, new Object[] { unitId, type,
                colsUse }, new MultiRow());*/
    }
    
    public List<FieldsDisplay> getColsDisplays(String parentId, Integer colsUse){
    	return FieldsDisplay.dt(displayRemoteService.findByColsDisplays(parentId,colsUse));
    	
    	/*return query(SQL_FIND_COLSDISPLAY_BY_PARENTID_COLSUSE, new Object[] { parentId, colsUse },
                new MultiRow());*/
    }
}
