/* 
 * @(#)SpecialtyDaoImpl.java    Created on May 13, 2011
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
import net.zdsoft.eisu.base.common.dao.SpecialtyDao;
import net.zdsoft.eisu.base.common.entity.Specialty;
import net.zdsoft.keel.dao.MultiRowMapper;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 4:32:45 PM $
 */
public class SpecialtyDaoImpl extends BaseDao<Specialty> implements SpecialtyDao {
    private static final String SQL_FIND_SPECIALTY_BY_ID = "SELECT * FROM base_specialty WHERE id=? ";
    private static final String SQL_FIND_SPECIALTYS_BY_IDS = "SELECT * FROM base_specialty WHERE is_deleted = 0 AND id IN";
    private static final String SQL_FIND_SPECIALTYS_BY_UNITID = "SELECT * FROM base_specialty WHERE is_deleted = 0 "
            + "AND unit_id=? ORDER BY specialty_code";
    private static final String SQL_FIND_SPECIALTYS_BY_UNITID_NOTSHOW = "SELECT * FROM base_specialty WHERE is_deleted = 0 "
    		+ "AND unit_id=? and state=0 ORDER BY specialty_code";
    private static final String SQL_FIND_SPECIALTYS_BY_PARENT = "SELECT * FROM base_specialty WHERE is_deleted = 0 "
            + "AND parent_id=? AND parent_type=? ORDER BY specialty_code";
    private static final String SQL_FIND_SPECIALTYS_BY_PARENT_NOTSHOW = "SELECT * FROM base_specialty WHERE is_deleted = 0 "
    		+ "AND parent_id=? AND parent_type=? and state=0 ORDER BY specialty_code";

    private static final String SQL_FIND_SPECIALTYS_BY_PARENTIDS = "SELECT * FROM base_specialty WHERE is_deleted = 0 "
            + " AND parent_type=? AND parent_id IN";
    private static final String SQL_FIND_SPECIALTYS_BY_PARENTIDS_NOTSHOW = "SELECT * FROM base_specialty WHERE is_deleted = 0 "
    		+ " AND parent_type=? AND state=0 AND parent_id IN";
    
    
    private static final String SQL_UPDATE_STATE_WX = "update base_specialty set state=1 where parent_id in";
    private static final String SQL_UPDATE_STATE_WX_BYSPECID = "update base_specialty set state=1 where id in";

    public Specialty setField(ResultSet rs) throws SQLException {
        Specialty specialty = new Specialty();
        specialty.setId(rs.getString("id"));
        specialty.setUnitId(rs.getString("unit_id"));
        specialty.setSpecialtyCode(rs.getString("specialty_code"));
        specialty.setSpecialtyName(rs.getString("specialty_name"));
        specialty.setEnglishName(rs.getString("english_name"));
        specialty.setShortName(rs.getString("short_name"));
        specialty.setBuildDate(rs.getTimestamp("build_date"));
        specialty.setParentId(rs.getString("parent_id"));
        specialty.setParentType(rs.getInt("parent_type"));
        specialty.setSpecialtyTypeId(rs.getString("specialty_type_id"));
        specialty.setDegree(rs.getString("degree"));
        specialty.setAcademicQualification(rs.getString("academic_qualification"));
        specialty.setSchoolingLength(rs.getInt("schooling_length"));
        
        specialty.setBelongSpecialty(rs.getString("belong_specialty"));
        specialty.setSupervisor(rs.getString("supervisor"));
        specialty.setBxCredit(rs.getFloat("bx_Credit"));
        specialty.setXxCredit(rs.getFloat("xx_Credit"));
        specialty.setRxCredit(rs.getFloat("rx_Credit"));
        specialty.setState(rs.getInt("state"));
        specialty.setSpecialtyCategory(rs.getString("specialty_category"));
        specialty.setState(rs.getInt("state"));
        
        specialty.setSpecialtyCatalog(rs.getString("specialty_catalog"));
        return specialty;
    }

    public Specialty getSpecialty(String specialtyId) {
        return query(SQL_FIND_SPECIALTY_BY_ID, specialtyId, new SingleRow());
    }

    public Map<String, Specialty> getSpecialtyMap(String[] specialtyIds) {
        return queryForInSQL(SQL_FIND_SPECIALTYS_BY_IDS, null, specialtyIds, new MapRow());
    }

    public List<Specialty> getSpecialtysByUnitId(String unitId) {
        return query(SQL_FIND_SPECIALTYS_BY_UNITID_NOTSHOW, unitId, new MultiRow());
    }
    public List<Specialty> getSpecialtysByUnitId(String unitId,boolean isShow) {
    	if(isShow){
    		return query(SQL_FIND_SPECIALTYS_BY_UNITID, unitId, new MultiRow());
    	}else{
    		return query(SQL_FIND_SPECIALTYS_BY_UNITID_NOTSHOW, unitId, new MultiRow());
    	}
    }

    public List<Specialty> getSpecialtysByParent(String parentId, int parentType) {
        return query(SQL_FIND_SPECIALTYS_BY_PARENT_NOTSHOW, new Object[] { parentId,
                Integer.valueOf(parentType) }, new int[] { Types.CHAR, Types.INTEGER },
                new MultiRow());
    }
    public List<Specialty> getSpecialtysByParent(String parentId, int parentType,boolean isShow) {
    	if(isShow){
	    	return query(SQL_FIND_SPECIALTYS_BY_PARENT, new Object[] { parentId,
	    			Integer.valueOf(parentType) }, new int[] { Types.CHAR, Types.INTEGER },
	    			new MultiRow());
    	}else{
    		return query(SQL_FIND_SPECIALTYS_BY_PARENT_NOTSHOW, new Object[] { parentId,
    				Integer.valueOf(parentType) }, new int[] { Types.CHAR, Types.INTEGER },
    				new MultiRow());
    	}
    }

    public List<Specialty> getSpecialtysByParent(String[] parentIds, int parentType) {
        return queryForInSQL(SQL_FIND_SPECIALTYS_BY_PARENTIDS_NOTSHOW, new Object[] { Integer
                .valueOf(parentType) }, parentIds, new MultiRow(), "ORDER BY specialty_code");
    }
    public List<Specialty> getSpecialtysByParent(String[] parentIds, int parentType,boolean isShow) {
    	if(isShow){
	    	return queryForInSQL(SQL_FIND_SPECIALTYS_BY_PARENTIDS, new Object[] { Integer
	    			.valueOf(parentType) }, parentIds, new MultiRow(), "ORDER BY specialty_code");
    	}else{
    		return queryForInSQL(SQL_FIND_SPECIALTYS_BY_PARENTIDS_NOTSHOW, new Object[] { Integer
    				.valueOf(parentType) }, parentIds, new MultiRow(), "ORDER BY specialty_code");
    	}
    }

    public List<String> getSpecialtyIdsByParent(String[] parentIds, int parentType) {
        return queryForInSQL(SQL_FIND_SPECIALTYS_BY_PARENTIDS_NOTSHOW, new Object[] { Integer
                .valueOf(parentType) }, parentIds, new MultiRowMapper<String>() {

            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("id");
            }
        }, "ORDER BY specialty_code");
    }

	@Override
	public void updateSpecState(String[] iniIds) {
		updateForInSQL(SQL_UPDATE_STATE_WX, null, iniIds);
	}

	@Override
	public List<Specialty> getSpecialtysByParentSpec(String specId) {
		String sql = "select * from base_specialty where belong_specialty=? and specialty_category=2";
		return query(sql,new Object[]{specId},new MultiRow());
	}

	@Override
	public void updateSpecStateBySpec(String[] specId) {
		updateForInSQL(SQL_UPDATE_STATE_WX_BYSPECID, null, specId);
	}

	@Override
	public List<Specialty> getSpecialtysByParentSpec(String[] specIds) {
		String sql = "select * from base_specialty where specialty_category=2 and belong_specialty in";
		return queryForInSQL(sql,null,specIds,new MultiRow());
	}

	@Override
	public Map<String, Specialty> getSpecialtymapByUnitId(String unitId,
			boolean isShow) {
		if(isShow){
    		return queryForMap(SQL_FIND_SPECIALTYS_BY_UNITID, new String[]{unitId}, new MapRow());
    	}else{
    		return queryForMap(SQL_FIND_SPECIALTYS_BY_UNITID_NOTSHOW, new String[]{unitId}, new MapRow());
    	}
	}
}
