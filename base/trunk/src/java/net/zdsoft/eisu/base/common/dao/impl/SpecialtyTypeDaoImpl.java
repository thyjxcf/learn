/* 
 * @(#)SpecialtyTypeDaoImpl.java    Created on May 31, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eisu.base.common.dao.SpecialtyTypeDao;
import net.zdsoft.eisu.base.common.entity.SpecialtyType;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 31, 2011 11:27:25 AM $
 */
public class SpecialtyTypeDaoImpl extends BaseDao<SpecialtyType> implements SpecialtyTypeDao {
    private static final String SQL_FIND_SPECIALTYTYPE_BY_ID = "SELECT * FROM base_specialty_type WHERE id=?";

    private static final String SQL_FIND_SPECIALTYTYPES_BY_IDS = "SELECT * FROM base_specialty_type WHERE is_deleted = 0 AND id IN";

    private static final String SQL_FIND_SPECIALTYTYPES_BY_UNITID = "SELECT * FROM base_specialty_type WHERE is_deleted = 0 AND unit_id=?";

    @Override
    public SpecialtyType setField(ResultSet rs) throws SQLException {
        SpecialtyType specialtyType = new SpecialtyType();
        specialtyType.setId(rs.getString("id"));
        specialtyType.setUnitId(rs.getString("unit_id"));
        specialtyType.setTypeCode(rs.getString("type_code"));
        specialtyType.setTypeName(rs.getString("type_name"));
        return specialtyType;
    }

    public SpecialtyType getSpecialtyType(String specialtyTypeId) {
        return query(SQL_FIND_SPECIALTYTYPE_BY_ID, specialtyTypeId, new SingleRow());
    }

    public Map<String, SpecialtyType> getSpecialtyTypeMap(String[] specialtyTypeIds) {
        return queryForInSQL(SQL_FIND_SPECIALTYTYPES_BY_IDS, null, specialtyTypeIds, new MapRow());
    }

    public List<SpecialtyType> getSpecialtyTypes(String unitId) {
        return query(SQL_FIND_SPECIALTYTYPES_BY_UNITID, unitId, new MultiRow());
    }
}
