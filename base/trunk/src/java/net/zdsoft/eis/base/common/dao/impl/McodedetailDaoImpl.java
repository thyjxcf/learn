package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.McodedetailDao;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.constant.enumeration.McodeFieldType;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;

public class McodedetailDaoImpl extends BaseDao<Mcodedetail> implements McodedetailDao {

    private static final String SQL_FIND_BY_MCODE = "SELECT * FROM base_mcode_detail WHERE mcode_id=? ORDER BY display_order,this_id ASC";

    private static final String SQL_FIND_BY_MCODE_THISID = "SELECT * FROM base_mcode_detail WHERE mcode_id=? AND this_id=?";

    private static final String SQL_FIND_BY_MCODEID_USING = "SELECT * FROM base_mcode_detail WHERE mcode_id=? AND is_using = 1 ORDER BY display_order";

    private static final String SQL_FIND_BY_MCODEID_THISID_USING = "SELECT * FROM base_mcode_detail WHERE mcode_id=? AND this_id like ? AND is_using = 1";

    public Mcodedetail setField(ResultSet rs) throws SQLException {
        Mcodedetail mcodedetail = new Mcodedetail();
        mcodedetail.setId(rs.getString("id"));
        mcodedetail.setMcodeId(rs.getString("mcode_id"));
        mcodedetail.setThisId(rs.getString("this_id"));
        mcodedetail.setContent(rs.getString("mcode_content"));
        mcodedetail.setIsUsing(rs.getInt("is_using"));
        mcodedetail.setType(rs.getInt("mcode_type"));
        mcodedetail.setOrderId(rs.getInt("display_order"));
        mcodedetail.setFieldType(McodeFieldType.fromCode(rs.getString("field_type")));
        mcodedetail.setFieldValue(rs.getString("field_value"));
        mcodedetail.setParentThisId(rs.getString("parent_this_id"));
        return mcodedetail;
    }

    public List<Mcodedetail> getMcodeDetails(String mcodeId) {
        return query(SQL_FIND_BY_MCODEID_USING, mcodeId, new MultiRow());
    }
    
    @Override
	public List<Mcodedetail> getMcodeDetails(String mcodeId,
			McodeFieldType type, String value) {
    	return query("SELECT * FROM base_mcode_detail WHERE mcode_id=? and field_type=? and field_value = ? AND is_using = 1 ORDER BY display_order", 
    			new String[]{mcodeId, type.getCode(), value}, new MultiRow());
	}

	@Override
	public Mcodedetail getMcodeDetail(String mcodeId, String thisId,
			McodeFieldType type, String value) {
		 if (thisId !=null ) {
			 thisId = thisId.trim();
		 }
        return (Mcodedetail) query("SELECT * FROM base_mcode_detail WHERE mcode_id=? AND this_id=? and field_type=? and field_value = ?", 
        			new String[] { mcodeId, thisId, type.getCode(), value }, new SingleRow());
	}

	public List<Mcodedetail> getMcodeDetails(String mcodeId, String name) {
    	StringBuffer sql = new StringBuffer("SELECT * FROM base_mcode_detail WHERE mcode_id=? AND is_using = 1 ");
    	sql.append("and mcode_content like '%"+name+"%' ORDER BY display_order");
        return query(sql.toString(), mcodeId, new MultiRow());
    }

    public List<Mcodedetail> getMcodeDetailFaintness(String mcodeId, String thisId) {
        if (thisId !=null ) thisId = thisId.trim();
        return query(SQL_FIND_BY_MCODEID_THISID_USING, new String[] { mcodeId, thisId + "%" },
                new MultiRow());
    }

    public Mcodedetail getMcodeDetail(String mcodeId, String thisId) {
        if (thisId !=null ) thisId = thisId.trim();
        Mcodedetail m = (Mcodedetail) query(SQL_FIND_BY_MCODE_THISID, new String[] { mcodeId,
                thisId }, new SingleRow());
        return m;
    }

    public List<Mcodedetail> getAllMcodeDetails(String mcodeId) {
        return query(SQL_FIND_BY_MCODE, new String[] { mcodeId }, new MultiRow());
    }

    public Map<String, Mcodedetail> getMcodeDetailMap(String mcodeId) {
        return queryForMap(SQL_FIND_BY_MCODE, new String[] { mcodeId }, new MapRowMapper<String, Mcodedetail>() {

            public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("this_id");
            }

            public Mcodedetail mapRowValue(ResultSet rs, int rowNum) throws SQLException {
                return setField(rs);
            }
        });
    }

	@Override
	public List<Mcodedetail> getAllMcodeDetailsByPage(String mcodeId,
			Pagination page) {
		return query(SQL_FIND_BY_MCODE, new String[] { mcodeId }, new MultiRow(),page);
	}

}
