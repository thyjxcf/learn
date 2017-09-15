package net.zdsoft.office.dutyinformation.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.dutyinformation.entity.OfficePatrolPlace;
import net.zdsoft.office.dutyinformation.dao.OfficePatrolPlaceDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_patrol_place
 * @author 
 * 
 */
public class OfficePatrolPlaceDaoImpl extends BaseDao<OfficePatrolPlace> implements OfficePatrolPlaceDao{

	@Override
	public OfficePatrolPlace setField(ResultSet rs) throws SQLException{
		OfficePatrolPlace officePatrolPlace = new OfficePatrolPlace();
		officePatrolPlace.setId(rs.getString("id"));
		officePatrolPlace.setUnitId(rs.getString("unit_id"));
		officePatrolPlace.setPlaceName(rs.getString("place_name"));
		return officePatrolPlace;
	}

	@Override
	public OfficePatrolPlace save(OfficePatrolPlace officePatrolPlace){
		String sql = "insert into office_patrol_place(id, unit_id, place_name) values(?,?,?)";
		if (StringUtils.isBlank(officePatrolPlace.getId())){
			officePatrolPlace.setId(createId());
		}
		Object[] args = new Object[]{
			officePatrolPlace.getId(), officePatrolPlace.getUnitId(), 
			officePatrolPlace.getPlaceName()
		};
		update(sql, args);
		return officePatrolPlace;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_patrol_place where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficePatrolPlace officePatrolPlace){
		String sql = "update office_patrol_place set unit_id = ?, place_name = ? where id = ?";
		Object[] args = new Object[]{
			officePatrolPlace.getUnitId(), officePatrolPlace.getPlaceName(), 
			officePatrolPlace.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficePatrolPlace getOfficePatrolPlaceById(String id){
		String sql = "select * from office_patrol_place where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficePatrolPlace> getOfficePatrolPlaceMapByIds(String[] ids){
		String sql = "select * from office_patrol_place where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficePatrolPlace> getOfficePatrolPlaceList(){
		String sql = "select * from office_patrol_place";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficePatrolPlace> getOfficePatrolPlacePage(Pagination page){
		String sql = "select * from office_patrol_place";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficePatrolPlace> getOfficePatrolPlaceByUnitIdList(String unitId){
		String sql = "select * from office_patrol_place where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficePatrolPlace> getOfficePatrolPlaceByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_patrol_place where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public Map<String, OfficePatrolPlace> getOfficePMap(String[] ids) {
		String sql = "select * from office_patrol_place where id in";
		return queryForInSQL(sql, null, ids, new MapRow(), null);
	}
}
