package net.zdsoft.office.teacherAttendance.dao.impl;


import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.dao.OfficeAttendanceSetDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceSet;
/**
 * office_attendance_set(考勤制度设置)
 * @author 
 * 
 */
public class OfficeAttendanceSetDaoImpl extends BaseDao<OfficeAttendanceSet> implements OfficeAttendanceSetDao{

	@Override
	public OfficeAttendanceSet setField(ResultSet rs) throws SQLException{
		OfficeAttendanceSet officeAttendanceSet = new OfficeAttendanceSet();
		officeAttendanceSet.setId(rs.getString("id"));
		officeAttendanceSet.setName(rs.getString("name"));
		officeAttendanceSet.setUnitId(rs.getString("unit_id"));
		officeAttendanceSet.setStartTime(rs.getString("start_time"));
		officeAttendanceSet.setPmTime(rs.getString("pm_time"));
		officeAttendanceSet.setEndTime(rs.getString("end_time"));
		officeAttendanceSet.setIsElastic(rs.getBoolean("is_elastic"));
		officeAttendanceSet.setElasticRange(rs.getString("elastic_range"));
		officeAttendanceSet.setEndElasticRange(rs.getString("end_elastic_range"));
		officeAttendanceSet.setStartRange(rs.getString("start_range"));
		officeAttendanceSet.setCreationTime(rs.getTimestamp("create_time"));
		officeAttendanceSet.setModifyTime(rs.getTimestamp("modify_time"));
		return officeAttendanceSet;
	}

	@Override
	public OfficeAttendanceSet save(OfficeAttendanceSet officeAttendanceSet){
		String sql = "insert into office_attendance_set(id, name,unit_id, start_time, pm_time, end_time, is_elastic, elastic_range, end_elastic_range, start_range, create_time, modify_time) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeAttendanceSet.getId())){
			officeAttendanceSet.setId(createId());
		}
		Object[] args = new Object[]{
			officeAttendanceSet.getId(), officeAttendanceSet.getName(), officeAttendanceSet.getUnitId(), 
			officeAttendanceSet.getStartTime(), officeAttendanceSet.getPmTime(), 
			officeAttendanceSet.getEndTime(), officeAttendanceSet.getIsElastic(), 
			officeAttendanceSet.getElasticRange(), officeAttendanceSet.getEndElasticRange(), officeAttendanceSet.getStartRange(), 
			officeAttendanceSet.getCreationTime(), 
			officeAttendanceSet.getModifyTime()
		};
		update(sql, args);
		return officeAttendanceSet;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_attendance_set where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeAttendanceSet officeAttendanceSet){
		String sql = "update office_attendance_set set name=?, unit_id = ?, start_time = ?, pm_time = ?, end_time = ?, is_elastic = ?, elastic_range = ?, end_elastic_range=?, start_range = ?, create_time = ?, modify_time = ? where id = ?";
		Object[] args = new Object[]{
				officeAttendanceSet.getName(),
			officeAttendanceSet.getUnitId(), officeAttendanceSet.getStartTime(), 
			officeAttendanceSet.getPmTime(), officeAttendanceSet.getEndTime(), 
			officeAttendanceSet.getIsElastic(), officeAttendanceSet.getElasticRange(), officeAttendanceSet.getEndElasticRange(),
			officeAttendanceSet.getStartRange(), 
			officeAttendanceSet.getCreationTime(), officeAttendanceSet.getModifyTime(), 
			officeAttendanceSet.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeAttendanceSet getOfficeAttendanceSetById(String id){
		String sql = "select * from office_attendance_set where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public List<OfficeAttendanceSet> getOfficeAttendanceSetByUnitId(String unitId) {
		// TODO Auto-generated method stub
		String sql = "select * from office_attendance_set where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public Map<String, OfficeAttendanceSet> getOfficeAttendanceSetMapByUnitId(
			String unitId) {
		// TODO Auto-generated method stub
		String sql = "select * from office_attendance_set where unit_id = ?";
		return queryForMap(sql,new Object[]{unitId},new MapRowMapper<String,OfficeAttendanceSet>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				// TODO Auto-generated method stub
				return rs.getString("id");
			}

			@Override
			public OfficeAttendanceSet mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				// TODO Auto-generated method stub
				OfficeAttendanceSet  set = setField(rs);
				return set;
			}

		
			
		});
	}
}