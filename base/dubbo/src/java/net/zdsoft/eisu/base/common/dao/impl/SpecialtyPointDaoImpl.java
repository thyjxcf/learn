/* 
 * @(#)SpecialtyPointDaoImpl.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eisu.base.common.dao.SpecialtyPointDao;
import net.zdsoft.eisu.base.common.entity.SpecialtyPoint;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 7:35:54 PM $
 */
public class SpecialtyPointDaoImpl extends BaseDao<SpecialtyPoint> implements SpecialtyPointDao {

    private static final String SQL_FIND_SPECIALTYPOINT_BY_ID = "SELECT * FROM base_specialty_point WHERE id=?";
    private static final String SQL_FIND_SPECIALTYPOINTS_BY_IDS = "SELECT * FROM base_specialty_point WHERE is_deleted = 0 AND id IN";
    private static final String SQL_FIND_SPECIALTYPOINTS_BY_UNITID = "SELECT * FROM base_specialty_point WHERE is_deleted = 0 AND unit_id=? and state=0 ORDER BY specialty_id";
    private static final String SQL_FIND_SPECIALTYPOINTS_BY_UNITID_SHOWALL = "SELECT * FROM base_specialty_point WHERE is_deleted = 0 AND unit_id=? ORDER BY specialty_id";
    private static final String SQL_FIND_SPECIALTYPOINTS_BY_SPECIALTYID = "SELECT * FROM base_specialty_point WHERE is_deleted = 0 AND specialty_id=?";
    private static final String SQL_FIND_SPECIALTYPOINTS_BY_SPECIALTYID_NOTSHOW = "SELECT * FROM base_specialty_point WHERE is_deleted = 0 and state=0 AND specialty_id=?";
    
    
    private static final String SQL_UPDATE_STATE_WX = "update base_specialty_point set state=1 where specialty_id in";

    public SpecialtyPoint setField(ResultSet rs) throws SQLException {
        SpecialtyPoint specialtyPoint = new SpecialtyPoint();
        specialtyPoint.setId(rs.getString("id"));
        specialtyPoint.setUnitId(rs.getString("unit_id"));
        specialtyPoint.setSpecialtyId(rs.getString("specialty_id"));
        specialtyPoint.setPointName(rs.getString("point_name"));
        specialtyPoint.setState(rs.getInt("state"));
        specialtyPoint.setPointCode(rs.getString("point_code"));
        return specialtyPoint;
    }

    public SpecialtyPoint getPoint(String specialtyPointId) {
        return query(SQL_FIND_SPECIALTYPOINT_BY_ID, specialtyPointId, new SingleRow());
    }

    public Map<String, SpecialtyPoint> getPointMap(String[] specialtyPointIds) {
        return queryForInSQL(SQL_FIND_SPECIALTYPOINTS_BY_IDS, null, specialtyPointIds, new MapRow());
    }

    public List<SpecialtyPoint> getPointsByUnitId(String unitId) {
        return query(SQL_FIND_SPECIALTYPOINTS_BY_UNITID, unitId, new MultiRow());
    }
    
    public List<SpecialtyPoint> getPointsByUnitId(String unitId,boolean isShow) {
    	if(isShow){
    		return query(SQL_FIND_SPECIALTYPOINTS_BY_UNITID_SHOWALL, unitId, new MultiRow());
    	}else{
    		return query(SQL_FIND_SPECIALTYPOINTS_BY_UNITID, unitId, new MultiRow());
    	}
    }

    public List<SpecialtyPoint> getPointsBySpecialtyId(String specialtyId) {
        return query(SQL_FIND_SPECIALTYPOINTS_BY_SPECIALTYID_NOTSHOW, specialtyId, new MultiRow());
    }
    public List<SpecialtyPoint> getPointsBySpecialtyId(String specialtyId,boolean isShow) {
    	if(isShow){
    		return query(SQL_FIND_SPECIALTYPOINTS_BY_SPECIALTYID, specialtyId, new MultiRow());
    	}else{
    		return query(SQL_FIND_SPECIALTYPOINTS_BY_SPECIALTYID_NOTSHOW, specialtyId, new MultiRow());
    	}
    }

	@Override
	public void updatePointState(String[] specIds) {
		updateForInSQL(SQL_UPDATE_STATE_WX, null, specIds);
	}
}
