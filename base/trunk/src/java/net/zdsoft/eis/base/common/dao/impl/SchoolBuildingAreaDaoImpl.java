package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.zdsoft.eis.base.common.dao.SchoolBuildingAreaDao;
import net.zdsoft.eis.base.common.entity.SchoolBuildingArea;
import net.zdsoft.eis.frame.client.BaseDao;

import org.apache.commons.lang.StringUtils;

/**
 * 校舍面积 
 * @author 
 * 
 */
public class SchoolBuildingAreaDaoImpl extends BaseDao<SchoolBuildingArea> implements SchoolBuildingAreaDao{
	@Override
	public SchoolBuildingArea setField(ResultSet rs) throws SQLException{
		SchoolBuildingArea schoolBuildingArea = new SchoolBuildingArea();
		schoolBuildingArea.setId(rs.getString("id"));
		schoolBuildingArea.setSchoolId(rs.getString("school_id"));
		schoolBuildingArea.setSchoolBuildingId(rs.getString("school_building_id"));
		schoolBuildingArea.setAreaType(rs.getString("area_type"));
		schoolBuildingArea.setArea(rs.getDouble("area"));
		schoolBuildingArea.setLendArea(rs.getDouble("lend_area"));
		return schoolBuildingArea;
	}
	
	public SchoolBuildingArea save(SchoolBuildingArea schoolBuildingArea){
		String sql = "insert into base_school_building_area(id, school_id, school_building_id, area_type, area, lend_area) values(?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(schoolBuildingArea.getId())){
			schoolBuildingArea.setId(createId());
		}
		Object[] args = new Object[]{
			schoolBuildingArea.getId(), schoolBuildingArea.getSchoolId(), 
			schoolBuildingArea.getSchoolBuildingId(), schoolBuildingArea.getAreaType(), 
			schoolBuildingArea.getArea(), schoolBuildingArea.getLendArea()
		};
		update(sql, args);
		return schoolBuildingArea;
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from base_school_building_area where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer update(SchoolBuildingArea schoolBuildingArea){
		String sql = "update base_school_building_area set school_id = ?, school_building_id = ?, area_type = ?, area = ?,lend_area = ? where id = ?";
		Object[] args = new Object[]{
			schoolBuildingArea.getSchoolId(), schoolBuildingArea.getSchoolBuildingId(), 
			schoolBuildingArea.getAreaType(), schoolBuildingArea.getArea(),  schoolBuildingArea.getLendArea(),
			schoolBuildingArea.getId()
		};
		return update(sql, args);
	}

	@Override
	public SchoolBuildingArea getSchoolBuildingAreaById(String id){
		String sql = "select * from base_school_building_area where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
}