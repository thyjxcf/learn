/* 
 * @(#)TeachAreaDaoImpl.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eisu.base.common.dao.TeachAreaDao;
import net.zdsoft.eisu.base.common.entity.TeachArea;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 7:46:07 PM $
 */
public class TeachAreaDaoImpl extends BaseDao<TeachArea> implements TeachAreaDao {
    private static final String SQL_FIND_TEACHAREA_BY_ID = "SELECT * FROM base_teach_area WHERE id=?";
    private static final String SQL_FIND_TEACHAREAS_BY_IDS = "SELECT * FROM base_teach_area WHERE is_deleted = 0 AND id IN";
    private static final String SQL_FIND_TEACHAREAS_BY_UNITID = "SELECT * FROM base_teach_area WHERE is_deleted = 0 AND unit_id=? ORDER BY area_code";

    public TeachArea setField(ResultSet rs) throws SQLException {
        TeachArea teachArea = new TeachArea();
        teachArea.setId(rs.getString("id"));
        teachArea.setUnitId(rs.getString("unit_id"));
        teachArea.setAreaCode(rs.getString("area_code"));
        teachArea.setAreaName(rs.getString("area_name"));
        teachArea.setAddress(rs.getString("address"));
        teachArea.setRemark(rs.getString("remark"));
        return teachArea;
    }

    public TeachArea getTeachArea(String teachAreaId) {
        return query(SQL_FIND_TEACHAREA_BY_ID, teachAreaId, new SingleRow());
    }

    public List<TeachArea> getTeachAreas(String unitId) {
        return query(SQL_FIND_TEACHAREAS_BY_UNITID, unitId, new MultiRow());
    }

    public Map<String, TeachArea> getTeachAreaMap(String unitId) {
        return queryForMap(SQL_FIND_TEACHAREAS_BY_UNITID, new Object[] { unitId }, new MapRow());
    }

    public Map<String, TeachArea> getTeachAreaMap(String[] teachAreaIds) {
        return queryForInSQL(SQL_FIND_TEACHAREAS_BY_IDS, null, teachAreaIds, new MapRow());
    }
}
