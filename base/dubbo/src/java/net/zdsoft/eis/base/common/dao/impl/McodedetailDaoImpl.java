package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.basedata.remote.service.McodeDetailRemoteService;
import net.zdsoft.eis.base.common.dao.McodedetailDao;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.constant.enumeration.McodeFieldType;
import net.zdsoft.eis.base.util.EntityUtils;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;

public class McodedetailDaoImpl extends BaseDao<Mcodedetail> implements McodedetailDao {
	
	@Autowired
	private McodeDetailRemoteService mcodeDetailRemoteService;

    private static final String SQL_FIND_BY_MCODE = "SELECT * FROM base_mcode_detail WHERE mcode_id=? ORDER BY display_order,this_id ASC";

//    private static final String SQL_FIND_BY_MCODE_THISID = "SELECT * FROM base_mcode_detail WHERE mcode_id=? AND this_id=?";
//
//    private static final String SQL_FIND_BY_MCODEID_USING = "SELECT * FROM base_mcode_detail WHERE mcode_id=? AND is_using = 1 ORDER BY display_order";
//
//    private static final String SQL_FIND_BY_MCODEID_THISID_USING = "SELECT * FROM base_mcode_detail WHERE mcode_id=? AND this_id like ? AND is_using = 1";

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
    	return Mcodedetail.dt(mcodeDetailRemoteService.findByMcodeIds(mcodeId));
//        return query(SQL_FIND_BY_MCODEID_USING, mcodeId, new MultiRow());
    }
    
    @Override
	public List<Mcodedetail> getMcodeDetails(String mcodeId,
			McodeFieldType type, String value) {
    	//TODO
    	return query("SELECT * FROM base_mcode_detail WHERE mcode_id=? and field_type=? and field_value = ? AND is_using = 1 ORDER BY display_order", 
    			new String[]{mcodeId, type.getCode(), value}, new MultiRow());
	}

	@Override
	public Mcodedetail getMcodeDetail(String mcodeId, String thisId,
			McodeFieldType type, String value) {
		//TODO
		 if (thisId !=null ) {
			 thisId = thisId.trim();
		 }
        return (Mcodedetail) query("SELECT * FROM base_mcode_detail WHERE mcode_id=? AND this_id=? and field_type=? and field_value = ?", 
        			new String[] { mcodeId, thisId, type.getCode(), value }, new SingleRow());
	}

	public List<Mcodedetail> getMcodeDetails(String mcodeId, String name) {
		return Mcodedetail.dt(mcodeDetailRemoteService.findByMcodeContentLike(mcodeId, "%" + name + "%"));
//    	StringBuffer sql = new StringBuffer("SELECT * FROM base_mcode_detail WHERE mcode_id=? AND is_using = 1 ");
//    	sql.append("and mcode_content like '%"+name+"%' ORDER BY display_order");
//        return query(sql.toString(), mcodeId, new MultiRow());
    }

    public List<Mcodedetail> getMcodeDetailFaintness(String mcodeId, String thisId) {
    	List<Mcodedetail> mds = Mcodedetail.dt(mcodeDetailRemoteService.findByMcodeIds(mcodeId));
    	List<Mcodedetail> list = new ArrayList<Mcodedetail>();
    	for(Mcodedetail md : mds){
    		if(StringUtils.startsWith(md.getThisId(), thisId)){
    			list.add(md);
    		}
    	}
    	return list;
//        if (thisId !=null ) thisId = thisId.trim();
//        return query(SQL_FIND_BY_MCODEID_THISID_USING, new String[] { mcodeId, thisId + "%" },
//                new MultiRow());
    }

    public Mcodedetail getMcodeDetail(String mcodeId, String thisId) {
    	return Mcodedetail.dc(mcodeDetailRemoteService.findByMcodeAndThisId(mcodeId, thisId));
//        if (thisId !=null ) thisId = thisId.trim();
//        Mcodedetail m = (Mcodedetail) query(SQL_FIND_BY_MCODE_THISID, new String[] { mcodeId,
//                thisId }, new SingleRow());
//        return m;
    }

    public List<Mcodedetail> getAllMcodeDetails(String mcodeId) {
    	return Mcodedetail.dt(mcodeDetailRemoteService.findAllByMcodeIds(mcodeId));
//        return query(SQL_FIND_BY_MCODE, new String[] { mcodeId }, new MultiRow());
    }

    public Map<String, Mcodedetail> getMcodeDetailMap(String mcodeId) {
    	List<Mcodedetail> mds = getAllMcodeDetails(mcodeId);
    	return EntityUtils.getMap(mds, "thisId");
//        return queryForMap(SQL_FIND_BY_MCODE, new String[] { mcodeId }, new MapRowMapper<String, Mcodedetail>() {
//
//            public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
//                return rs.getString("this_id");
//            }
//
//            public Mcodedetail mapRowValue(ResultSet rs, int rowNum) throws SQLException {
//                return setField(rs);
//            }
//        });
    }

	@Override
	public List<Mcodedetail> getAllMcodeDetailsByPage(String mcodeId,
			Pagination page) {
		//TODO
		return query(SQL_FIND_BY_MCODE, new String[] { mcodeId }, new MultiRow(),page);
	}

}
