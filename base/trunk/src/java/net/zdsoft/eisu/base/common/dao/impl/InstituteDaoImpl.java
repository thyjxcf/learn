/* 
 * @(#)InstituteDaoImpl.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eisu.base.common.dao.InstituteDao;
import net.zdsoft.eisu.base.common.entity.Institute;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 5:56:51 PM $
 */
public class InstituteDaoImpl extends BaseDao<Institute> implements InstituteDao {

    private static final String SQL_FIND_INSTITUTE_BY_ID = "SELECT * FROM base_institute WHERE id=? ";
    private static final String SQL_FIND_INSTITUTES_BY_IDS = "SELECT * FROM base_institute WHERE is_deleted = 0 AND id IN";
    private static final String SQL_FIND_INSTITUTES_BY_UNITID = "SELECT * FROM base_institute WHERE is_deleted = 0 and state=0 AND unit_id=?";
    private static final String SQL_FIND_INSTITUTES_BY_PARENT = "SELECT * FROM base_institute WHERE is_deleted = 0 AND parent_id=? "
            + "AND parent_type=? ORDER BY institute_code";
    private static final String SQL_FIND_INSTITUTES_BY_PARENT_NOTSHOW = "SELECT * FROM base_institute WHERE is_deleted = 0 AND parent_id=? "
    		+ "AND parent_type=? and state=0 ORDER BY institute_code";
    private static final String SQL_FIND_INSTITUTES_BY_PARENTIDS_NOTSHOW = "SELECT * FROM base_institute WHERE is_deleted = 0"
    	+ " and state=0 AND parent_type =? AND parent_id in ";
    private static final String SQL_FIND_INSTITUTES_BY_PARENT_KIND = "SELECT * FROM base_institute WHERE is_deleted = 0 AND parent_id=? "
            + "AND parent_type=? AND institute_kind=? ORDER BY institute_code";
    private static final String SQL_FIND_INSTITUTES_BY_PARENT_KIND_NOTSHOW = "SELECT * FROM base_institute WHERE is_deleted = 0 AND parent_id=? "
    		+ "AND parent_type=? AND institute_kind=? and state=0 ORDER BY institute_code";


    public Institute setField(ResultSet rs) throws SQLException {
        Institute institute = new Institute();
        institute.setId(rs.getString("id"));
        institute.setUnitId(rs.getString("unit_id"));
        institute.setInstituteCode(rs.getString("institute_code"));
        institute.setInstituteName(rs.getString("institute_name"));
        institute.setEnglishName(rs.getString("english_name"));
        institute.setShortName(rs.getString("short_name"));
        institute.setBuildDate(rs.getTimestamp("build_date"));
        institute.setCivilMasterId(rs.getString("civil_master_id"));
        institute.setPartyMasterId(rs.getString("party_master_id"));
        institute.setInstituteKind(rs.getInt("institute_kind"));
        institute.setParentId(rs.getString("parent_id"));
        institute.setParentType(rs.getInt("parent_type"));
        institute.setState(rs.getInt("state"));
        return institute;
    }

    public Institute getInstitute(String instituteId) {
        return query(SQL_FIND_INSTITUTE_BY_ID, instituteId, new SingleRow());
    }

    public Map<String, Institute> getInstituteMap(String[] instituteIds) {
        return queryForInSQL(SQL_FIND_INSTITUTES_BY_IDS, null, instituteIds, new MapRow());
    }

    public List<Institute> getInstitutesByUnitId(String unitId) {
        return query(SQL_FIND_INSTITUTES_BY_UNITID, unitId, new MultiRow());
    }

    public List<Institute> getInstitutesByParent(String parentId, int parentType) {
        return query(SQL_FIND_INSTITUTES_BY_PARENT_NOTSHOW, new Object[] { parentId,
                Integer.valueOf(parentType) }, new int[] { Types.CHAR, Types.INTEGER },
                new MultiRow());
    }
    
    public List<Institute> getInstitutesByParent(String parentId, int parentType,boolean isShow) {
    	if(isShow){
	    	return query(SQL_FIND_INSTITUTES_BY_PARENT, new Object[] { parentId,
	    			Integer.valueOf(parentType) }, new int[] { Types.CHAR, Types.INTEGER },
	    			new MultiRow());
    	}else{
    		return query(SQL_FIND_INSTITUTES_BY_PARENT_NOTSHOW, new Object[] { parentId,
	    			Integer.valueOf(parentType) }, new int[] { Types.CHAR, Types.INTEGER },
	    			new MultiRow());
    	}
    }

    public List<Institute> getInstitutesByParent(String parentId, int parentType, int instituteKind) {
        return query(SQL_FIND_INSTITUTES_BY_PARENT_KIND_NOTSHOW, new Object[] { parentId,
                Integer.valueOf(parentType), Integer.valueOf(instituteKind) }, new int[] {
                Types.CHAR, Types.INTEGER, Types.INTEGER }, new MultiRow());
    }
    public List<Institute> getInstitutesByParent(String parentId, int parentType, int instituteKind,boolean isShow) {
    	if(isShow){
	    	return query(SQL_FIND_INSTITUTES_BY_PARENT_KIND, new Object[] { parentId,
	    			Integer.valueOf(parentType), Integer.valueOf(instituteKind) }, new int[] {
	    			Types.CHAR, Types.INTEGER, Types.INTEGER }, new MultiRow());
    	}else{
    		return query(SQL_FIND_INSTITUTES_BY_PARENT_KIND_NOTSHOW, new Object[] { parentId,
	    			Integer.valueOf(parentType), Integer.valueOf(instituteKind) }, new int[] {
	    			Types.CHAR, Types.INTEGER, Types.INTEGER }, new MultiRow());
    	}
    }

	@Override
	public Map<String, Institute> getMapByUnitId(String unitId) {
		return queryForMap(SQL_FIND_INSTITUTES_BY_UNITID, new Object[]{unitId}, new MapRow());
	}

	@Override
	public List<Institute> getInstitutesByParent(String[] parentId,
			int parentType) {
		return queryForInSQL(SQL_FIND_INSTITUTES_BY_PARENTIDS_NOTSHOW, new Object[] {Integer.valueOf(parentType) }, parentId, new MultiRow());
	}

	@Override
	public List<Institute> getInstituteList(String[] instituteIds, Integer state) {
		StringBuffer sb = new StringBuffer("select * from base_institute where 1=1");
		if(state != null){
			sb.append(" and state="+state);
		}
		sb.append(" and id in");
		return queryForInSQL(sb.toString(), null, instituteIds, new MultiRow());
	}
}
