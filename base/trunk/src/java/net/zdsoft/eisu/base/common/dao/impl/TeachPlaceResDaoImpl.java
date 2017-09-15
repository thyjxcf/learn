/* 
 * @(#)TeachPlaceResDaoImpl.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eisu.base.common.dao.TeachPlaceResDao;
import net.zdsoft.eisu.base.common.entity.TeachPlaceRes;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 8:14:28 PM $
 */
public class TeachPlaceResDaoImpl extends BaseDao<TeachPlaceRes> implements TeachPlaceResDao {

    private static final String SQL_FIND_TEACHPLACERES_BY_ID = "SELECT * FROM base_teach_place_res WHERE id=?";
    private static final String SQL_FIND_TEACHPLACERESS_BY_IDS = "SELECT * FROM base_teach_place_res WHERE is_deleted = 0 AND id IN";
    private static final String SQL_FIND_TEACHPLACERESS_BY_UNITID = "SELECT * FROM base_teach_place_res WHERE is_deleted = 0 AND unit_id=? ORDER BY teach_place_id";
    private static final String SQL_FIND_TEACHPLACERESS_BY_PLACEID = "SELECT * FROM base_teach_place_res WHERE is_deleted = 0 AND teach_place_id=?";

    public TeachPlaceRes setField(ResultSet rs) throws SQLException {
        TeachPlaceRes teachPlaceRes = new TeachPlaceRes();
        teachPlaceRes.setId(rs.getString("id"));
        teachPlaceRes.setUnitId(rs.getString("unit_id"));
        teachPlaceRes.setTeachPlaceId(rs.getString("teach_place_id"));
        teachPlaceRes.setTeachResId(rs.getString("teach_res_id"));
        return teachPlaceRes;
    }

    public TeachPlaceRes getTeachPlaceRes(String teachPlaceResId) {
        return query(SQL_FIND_TEACHPLACERES_BY_ID, teachPlaceResId, new SingleRow());
    }

    public List<TeachPlaceRes> getTeachPlaceResesByUnitId(String unitId) {
        return query(SQL_FIND_TEACHPLACERESS_BY_UNITID, unitId, new MultiRow());
    }

    public List<TeachPlaceRes> getTeachPlaceResesByPlaceId(String placeId) {
        return query(SQL_FIND_TEACHPLACERESS_BY_PLACEID, placeId, new MultiRow());
    }

    public Map<String, TeachPlaceRes> getTeachPlaceResMap(String[] teachPlaceResIds) {
        return queryForInSQL(SQL_FIND_TEACHPLACERESS_BY_IDS, null, teachPlaceResIds, new MapRow());
    }
}
