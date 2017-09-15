package net.zdsoft.office.teacherAttendance.dao.impl;


import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.dao.OfficeAttendancePlaceDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendancePlace;
/**
 * 办公地点
 * @author 
 * 
 */
public class OfficeAttendancePlaceDaoImpl extends BaseDao<OfficeAttendancePlace> implements OfficeAttendancePlaceDao{

	@Override
	public OfficeAttendancePlace setField(ResultSet rs) throws SQLException{
		OfficeAttendancePlace officeAttendancePlace = new OfficeAttendancePlace();
		officeAttendancePlace.setId(rs.getString("id"));
		officeAttendancePlace.setUnitId(rs.getString("unit_id"));
		officeAttendancePlace.setName(rs.getString("name"));
		officeAttendancePlace.setMapName(rs.getString("map_name"));
		officeAttendancePlace.setAddress(rs.getString("address"));
		officeAttendancePlace.setLatitude(rs.getString("latitude"));
		officeAttendancePlace.setLongitude(rs.getString("longitude"));
		officeAttendancePlace.setRange(rs.getInt("range"));
		officeAttendancePlace.setCreationTime(rs.getTimestamp("create_time"));
		officeAttendancePlace.setModifyTime(rs.getTimestamp("modify_time"));
		return officeAttendancePlace;
	}

	@Override
	public OfficeAttendancePlace save(OfficeAttendancePlace officeAttendancePlace){
		String sql = "insert into office_attendance_place(id, unit_id, name, map_name, address, latitude, longitude, range, create_time, modify_time) values(?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeAttendancePlace.getId())){
			officeAttendancePlace.setId(createId());
		}
		Object[] args = new Object[]{
			officeAttendancePlace.getId(), officeAttendancePlace.getUnitId(), 
			officeAttendancePlace.getName(), officeAttendancePlace.getMapName(), officeAttendancePlace.getAddress(),
			officeAttendancePlace.getLatitude(), officeAttendancePlace.getLongitude(), 
			officeAttendancePlace.getRange(), officeAttendancePlace.getCreationTime(), 
			officeAttendancePlace.getModifyTime()
		};
		update(sql, args);
		return officeAttendancePlace;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_attendance_place where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeAttendancePlace officeAttendancePlace){
		String sql = "update office_attendance_place set unit_id = ?, name = ?, map_name = ?, address = ?, latitude = ?, longitude = ?, range = ?, create_time = ?, modify_time = ? where id = ?";
		Object[] args = new Object[]{
			officeAttendancePlace.getUnitId(), officeAttendancePlace.getName(), 
			officeAttendancePlace.getMapName(), officeAttendancePlace.getAddress(), officeAttendancePlace.getLatitude(), 
			officeAttendancePlace.getLongitude(), officeAttendancePlace.getRange(), 
			officeAttendancePlace.getCreationTime(), officeAttendancePlace.getModifyTime(), 
			officeAttendancePlace.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeAttendancePlace getOfficeAttendancePlaceById(String id){
		String sql = "select * from office_attendance_place where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	@Override
	public List<OfficeAttendancePlace> getListByIds(String[] ids){
		String sql = "select * from office_attendance_place where id in ";
		return queryForInSQL(sql, null, ids, new MultiRow());
	}

	@Override
	public List<OfficeAttendancePlace> listOfficeAttendancePlaceByUnitId(
			String unitId) {
		// TODO Auto-generated method stub
		String sql = "select * from office_attendance_place where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}
	
	@Override
	public List<OfficeAttendancePlace> getListByName(String unitId, String name, String ignoreId){
		String sql = "select * from office_attendance_place where unit_id = ? and name=? ";
		if(StringUtils.isNotBlank(ignoreId)){
			sql+=" and id <> '" + ignoreId + "'";
		}
		
		return query(sql, new Object[]{unitId, name }, new MultiRow());
	}

	@Override
	public Map<String, OfficeAttendancePlace> getOfficeAttendancePlaceMapByUnitId(
			String unitId) {
		// TODO Auto-generated method stub
		String sql = "select * from office_attendance_place where unit_id = ?";
		Map<String, OfficeAttendancePlace> map = queryForMap(sql,new Object[]{unitId},new MapRowMapper<String,OfficeAttendancePlace>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				// TODO Auto-generated method stub
				return rs.getString("id");
			}

			@Override
			public OfficeAttendancePlace mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				// TODO Auto-generated method stub
				OfficeAttendancePlace place = setField(rs);
				return place;
			}
			
		});
		return map;
	}

	@Override
	public List<OfficeAttendancePlace> listOfficeAttendancePlaceIds(String[] ids) {
		// TODO Auto-generated method stub
		String sql = "select * from office_attendance_place where id in ";
		return queryForInSQL(sql,null,ids,new MultiRow());
	}
}