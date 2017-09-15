/* 
 * @(#)TeachPlaceDaoImpl.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eisu.base.common.dao.TeachPlaceDao;
import net.zdsoft.eisu.base.common.entity.TeachPlace;
import net.zdsoft.keel.dao.MapRowMapper;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 8:06:50 PM $
 */
public class TeachPlaceDaoImpl extends BaseDao<TeachPlace> implements
		TeachPlaceDao {

	private static final String SQL_FIND_TEACHPLACE_BY_ID = "SELECT * FROM base_teach_place WHERE id=?";
	private static final String SQL_FIND_TEACHPLACES_BY_IDS = "SELECT * FROM base_teach_place WHERE is_deleted = 0 AND id IN";
	private static final String SQL_FIND_TEACHPLACES_BY_UNITID = "SELECT * FROM base_teach_place WHERE is_deleted = 0 AND unit_id=? ORDER BY place_code";
	private static final String SQL_FIND_TEACHPLACES_BY_AREAID = "SELECT * FROM base_teach_place WHERE is_deleted = 0 AND teach_area_id=? ORDER BY place_code";
	//private static final String SQL_FIND_TEACH_PLACE_BY_UNITID_NAME_CODE = "SELECT * FROM base_teach_place "
	//		+ "WHERE is_deleted = 0 AND unit_id=? and place_name like ? and place_code like ?";

	
	public TeachPlace setField(ResultSet rs) throws SQLException {
		TeachPlace teachPlace = new TeachPlace();
		teachPlace.setId(rs.getString("id"));
		teachPlace.setUnitId(rs.getString("unit_id"));
		teachPlace.setPlaceCode(rs.getString("place_code"));
		teachPlace.setPlaceName(rs.getString("place_name"));
		teachPlace.setTeachAreaId(rs.getString("teach_area_id"));
		teachPlace.setPlaceNum(rs.getInt("place_num"));

		teachPlace.setTeachBuildingId(rs.getString("teach_building_id"));
		teachPlace.setFloorNumber(rs.getObject("floor_number")==null?null:rs.getInt("floor_number"));
		teachPlace.setFloorDisplayOrder(rs.getInt("floor_display_order"));
		teachPlace.setMachineCode(rs.getString("machine_code"));
		teachPlace.setControllerID(rs.getString("controllerID"));
		teachPlace.setDoorNO(rs.getInt("doorNO"));
		
		teachPlace.setPlaceType(rs.getString("place_type"));

		return teachPlace;
	}

	public TeachPlace getTeachPlace(String teachPlaceId) {
		return query(SQL_FIND_TEACHPLACE_BY_ID, teachPlaceId, new SingleRow());
	}

	public Map<String, TeachPlace> getTeachPlaceMap(String[] teachPlaceIds) {
		return queryForInSQL(SQL_FIND_TEACHPLACES_BY_IDS, null, teachPlaceIds,
				new MapRow());
	}

	public List<TeachPlace> getTeachPlacesByUnitId(String unitId) {
		return query(SQL_FIND_TEACHPLACES_BY_UNITID, unitId, new MultiRow());
	}

	public List<TeachPlace> getTeachPlacesByAreaId(String areaId) {
		return query(SQL_FIND_TEACHPLACES_BY_AREAID, areaId, new MultiRow());
	}

	public List<TeachPlace> getTeachPlacesByFaintness(String unitId,
			String name, String code, String placeType) {
		StringBuffer sql=new StringBuffer("SELECT * FROM base_teach_place WHERE is_deleted = 0 AND unit_id=? ");
		if(StringUtils.isNotBlank(name)) {
			sql.append(" and place_name like "+ name + "%");
		}
		if (StringUtils.isNotBlank(code)) {
			sql.append(" and place_code like "+code + "%");
		}
//		String sql = SQL_FIND_TEACH_PLACE_BY_UNITID_NAME_CODE;
		if(StringUtils.isNotBlank(placeType)){
			String[] types = placeType.split(",");
			for (int i = 0; i < types.length; i++) {
				if(i==0){
					sql.append(" and (");
				}
				sql.append(" place_type like '%"+types[i]+"%' or");
			}
			sql.delete(sql.lastIndexOf("or"), sql.length());
			sql.append(")");
		}
		return query(sql.toString(), new Object[] {unitId}, new MultiRow());
	}

	@Override
	public List<TeachPlace> getTeachPlaceByTypeInArea(String areaId, String type) {
		if(StringUtils.isBlank(type)){
			return getTeachPlacesByAreaId(areaId);
		}else{
			String sql = "SELECT * FROM base_teach_place WHERE is_deleted = 0 AND teach_area_id = ? and place_type = ? ";
			return query(sql, new Object[]{areaId,type}, new MultiRow());
		}
	}

	@Override
	public Map<String, TeachPlace> getTeachPlaceMapByName(String unitId,
			String[] placeNames) {
			String sql = "SELECT * FROM base_teach_place WHERE is_deleted = 0 AND unit_id=? and place_name in";
			return queryForInSQL(sql, new Object[] {unitId}, placeNames, new MapRowMapper<String, TeachPlace>() {
				@Override
				public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("place_name");
				}

				@Override
				public TeachPlace mapRowValue(ResultSet rs, int rowNum) throws SQLException {
					return setField(rs);
				}
			});
	}
	public Map<String,String> getTeachPlacesByTypes(String unitId, String placeType) {
		StringBuffer sql=new StringBuffer("SELECT * FROM base_teach_place WHERE is_deleted = 0 AND unit_id=? ");
		if(StringUtils.isNotBlank(placeType)){
			String[] types = placeType.split(",");
			for (int i = 0; i < types.length; i++) {
				if(i==0){
					sql.append(" and (");
				}
				sql.append(" place_type like '%"+types[i]+"%' or");
			}
			sql.delete(sql.lastIndexOf("or"), sql.length());
			sql.append(")");
		}
		return queryForMap(sql.toString(), new Object[] {unitId}, new MapRowMapper<String,String>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("id");
			}

			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("place_name");
			}
			
		});
	}
	
	@Override
	public List<TeachPlace> getTeachPlaceByUnitIdAndType(String unitId,
			String type) {
		StringBuffer sql=new StringBuffer("SELECT * FROM base_teach_place WHERE is_deleted = 0 AND unit_id=? ");
		if(StringUtils.isNotBlank(type)){
			String[] types = type.split(",");
			for (int i = 0; i < types.length; i++) {
				if(i==0){
					sql.append(" and (");
				}
				sql.append(" place_type like '%"+types[i]+"%' or");
			}
			sql.delete(sql.lastIndexOf("or"), sql.length());
			sql.append(")");
		}
		return query(sql.toString(), unitId, new MultiRow());
	}

	@Override
	public List<TeachPlace> getTeachPlaceByUnitIdAndTeachBuildingId(
			String unitId, String teachBuildingId) {
		String sql = "SELECT * FROM base_teach_place WHERE is_deleted = 0 AND unit_id=? AND teach_building_id=? ORDER BY floor_number, floor_display_order";
		return query(sql, new Object[]{unitId, teachBuildingId}, new MultiRow());
	}
	
	@Override
    public TeachPlace getTeachPlaceByControllerID(String unitID,
            String controllerID) {
        String sql = "SELECT * FROM base_teach_place WHERE is_deleted = 0 AND unit_id=? and controllerID = ?";
        return query(sql, new Object[] {unitID,controllerID}, new SingleRow());
    }
}
