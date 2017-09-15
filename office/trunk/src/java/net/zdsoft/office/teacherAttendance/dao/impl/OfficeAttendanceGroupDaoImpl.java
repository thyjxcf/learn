package net.zdsoft.office.teacherAttendance.dao.impl;


import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.dao.OfficeAttendanceGroupDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceGroup;
/**
 * 考勤组
 * @author 
 * 
 */
public class OfficeAttendanceGroupDaoImpl extends BaseDao<OfficeAttendanceGroup> implements OfficeAttendanceGroupDao{

	@Override
	public OfficeAttendanceGroup setField(ResultSet rs) throws SQLException{
		OfficeAttendanceGroup officeAttendanceGroup = new OfficeAttendanceGroup();
		officeAttendanceGroup.setId(rs.getString("id"));
		officeAttendanceGroup.setUnitId(rs.getString("unit_id"));
		officeAttendanceGroup.setName(rs.getString("name"));
		officeAttendanceGroup.setAttSetId(rs.getString("att_set_id"));
		officeAttendanceGroup.setPlaceIds(rs.getString("place_ids"));
		officeAttendanceGroup.setCreationTime(rs.getTimestamp("create_time"));
		officeAttendanceGroup.setModifyTime(rs.getTimestamp("modify_time"));
		return officeAttendanceGroup;
	}

	@Override
	public OfficeAttendanceGroup save(OfficeAttendanceGroup officeAttendanceGroup){
		String sql = "insert into office_attendance_group(id, unit_id, name, att_set_id, place_ids, create_time, modify_time) values(?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeAttendanceGroup.getId())){
			officeAttendanceGroup.setId(createId());
		}
		Object[] args = new Object[]{
			officeAttendanceGroup.getId(), officeAttendanceGroup.getUnitId(), 
			officeAttendanceGroup.getName(), officeAttendanceGroup.getAttSetId(), officeAttendanceGroup.getPlaceIds(),
			officeAttendanceGroup.getCreationTime(), officeAttendanceGroup.getModifyTime()
		};
		update(sql, args);
		return officeAttendanceGroup;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_attendance_group where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeAttendanceGroup officeAttendanceGroup){
		String sql = "update office_attendance_group set unit_id = ?, name = ?, att_set_id = ?, place_ids = ?, create_time = ?, modify_time = ? where id = ?";
		Object[] args = new Object[]{
			officeAttendanceGroup.getUnitId(), officeAttendanceGroup.getName(), 
			officeAttendanceGroup.getAttSetId(), officeAttendanceGroup.getPlaceIds(), officeAttendanceGroup.getCreationTime(), 
			officeAttendanceGroup.getModifyTime(), officeAttendanceGroup.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeAttendanceGroup getOfficeAttendanceGroupById(String id){
		String sql = "select * from office_attendance_group where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public OfficeAttendanceGroup getOfficeAttendanceGroupByName(String name ,String id) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer("select * from office_attendance_group where name = ?");
		List<Object> args = new ArrayList<Object>();
		args.add(name);
		if(StringUtils.isNotEmpty(id)){
			sb.append(" and id != ? ");
			args.add(id);
		}
		
		return query(sb.toString(), args.toArray(), new SingleRow());
	}
	
	public List<OfficeAttendanceGroup> getListByPlaceId(String placeId){
		String sql = "select * from office_attendance_group where place_ids like ?";
		return query(sql, "%"+placeId+"%", new MultiRow());
	}

	@Override
	public List<OfficeAttendanceGroup> listOfficeAttendanceGroupByUnitId(
			String unitId) {
		// TODO Auto-generated method stub
		String sql = "select * from office_attendance_group where (name!='' or name is not null) and unit_id= ? ";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public OfficeAttendanceGroup getOfficeNotAddAttendanceGroup() {
		// TODO Auto-generated method stub
		String sql = "select * from office_attendance_group where name ='' or name is  null";
		return query(sql, new SingleRow());
	}

}