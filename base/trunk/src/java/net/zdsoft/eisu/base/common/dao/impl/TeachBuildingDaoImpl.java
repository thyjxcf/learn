package net.zdsoft.eisu.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eisu.base.common.dao.TeachBuildingDao;
import net.zdsoft.eisu.base.common.entity.TeachBuilding;

import org.apache.commons.lang.StringUtils;

/**
 * 楼层信息表 
 * @author 
 * 
 */
public class TeachBuildingDaoImpl extends BaseDao<TeachBuilding> implements TeachBuildingDao{
	@Override
	public TeachBuilding setField(ResultSet rs) throws SQLException{
		TeachBuilding baseTeachBuilding = new TeachBuilding();
		baseTeachBuilding.setId(rs.getString("id"));
		baseTeachBuilding.setUnitId(rs.getString("unit_id"));
		baseTeachBuilding.setTeachAreaId(rs.getString("teach_area_id"));
		baseTeachBuilding.setBuildingName(rs.getString("building_name"));
		baseTeachBuilding.setFloorCount(rs.getInt("floor_count"));
		baseTeachBuilding.setBuildingType(rs.getString("building_type"));
		baseTeachBuilding.setIsDeleted(rs.getBoolean("is_deleted"));
		return baseTeachBuilding;
	}

	@Override
	public TeachBuilding getBaseTeachBuildingById(String id){
		String sql = "select * from base_teach_building where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, TeachBuilding> getBaseTeachBuildingMapByIds(String[] ids){
		String sql = "select * from base_teach_building WHERE is_deleted = 0 and id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<TeachBuilding> getBaseTeachBuildingByBuildingType(
			String unitId, String areaId, String buildingType) {
		StringBuffer sql = new StringBuffer();
		List<Object> objs = new ArrayList<Object>();
		sql.append("select * from base_teach_building where is_deleted=0 and unit_id = ? ");
		objs.add(unitId);
		if(StringUtils.isNotBlank(areaId)){
			sql.append(" and teach_area_id = ? ");
			objs.add(areaId);
		}
		if(StringUtils.isNotBlank(buildingType)){
			String[] types = buildingType.split(",");
			for (int i = 0; i < types.length; i++) {
				if(i==0){
					sql.append(" and (");
				}
				sql.append(" building_type like '%"+types[i]+"%' or");
			}
			sql.delete(sql.lastIndexOf("or"), sql.length());
			sql.append(")");
		}
		sql.append(" order by teach_area_id ");
		return query(sql.toString(), objs.toArray(), new MultiRow());
	}
}
