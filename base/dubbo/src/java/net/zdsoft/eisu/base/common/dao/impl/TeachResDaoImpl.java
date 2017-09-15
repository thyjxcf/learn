/* 
 * @(#)TeachResDaoImpl.java    Created on May 20, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eisu.base.common.dao.TeachResDao;
import net.zdsoft.eisu.base.common.entity.TeachRes;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 20, 2011 10:29:17 AM $
 */
public class TeachResDaoImpl extends BaseDao<TeachRes> implements TeachResDao {
    private static final String SQL_FIND_TEACHRES_BY_ID = "SELECT * FROM base_teach_res WHERE id=?";
    private static final String SQL_FIND_TEACHRESS_BY_IDS = "SELECT * FROM base_teach_res WHERE is_deleted = 0 AND id IN";
    private static final String SQL_FIND_TEACHRESS = "SELECT * FROM base_teach_res WHERE is_deleted = 0";

    public TeachRes setField(ResultSet rs) throws SQLException {
        TeachRes teachRes = new TeachRes();
        teachRes.setId(rs.getString("id"));
        teachRes.setResName(rs.getString("res_name"));
        return teachRes;
    }

    public TeachRes getTeachRes(String teachResId) {
        return query(SQL_FIND_TEACHRES_BY_ID, teachResId, new SingleRow());
    }

    public List<TeachRes> getTeachReses() {
        return query(SQL_FIND_TEACHRESS, null, null, new MultiRow());
    }

    public Map<String, TeachRes> getTeachResMap(String[] teachResIds) {
        return queryForInSQL(SQL_FIND_TEACHRESS_BY_IDS, null, teachResIds, new MapRow());
    }

    public Map<String, TeachRes> getTeachResMap() {
        return queryForMap(SQL_FIND_TEACHRESS, new MapRow());
    }
}
