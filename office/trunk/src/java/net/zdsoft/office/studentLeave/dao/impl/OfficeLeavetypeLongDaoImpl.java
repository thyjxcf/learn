package net.zdsoft.office.studentLeave.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.dao.OfficeLeavetypeLongDao;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeLong;

import org.apache.commons.lang3.StringUtils;
/**
 * office_leavetype_long
 * @author 
 * 
 */
public class OfficeLeavetypeLongDaoImpl extends BaseDao<OfficeLeavetypeLong> implements OfficeLeavetypeLongDao{

	@Override
	public OfficeLeavetypeLong setField(ResultSet rs) throws SQLException{
		OfficeLeavetypeLong officeLeavetypeLong = new OfficeLeavetypeLong();
		officeLeavetypeLong.setId(rs.getString("id"));
		officeLeavetypeLong.setLeaveId(rs.getString("leave_id"));
		officeLeavetypeLong.setStartTime(rs.getTimestamp("start_time"));
		officeLeavetypeLong.setEndTime(rs.getTimestamp("end_time"));
		officeLeavetypeLong.setDays(rs.getInt("days"));
		officeLeavetypeLong.setHasBed(rs.getString("has_bed"));
		officeLeavetypeLong.setAddress(rs.getString("address"));
		officeLeavetypeLong.setMateName(rs.getString("mate_name"));
		officeLeavetypeLong.setMateGx(rs.getString("mate_gx"));
		officeLeavetypeLong.setCreationTime(rs.getTimestamp("creation_time"));
		return officeLeavetypeLong;
	}
	@Override
	public void deleteByLeaveId(String leaveId) {
		String sql = "delete from office_leavetype_long where leave_id = ?";
		update(sql, new Object[]{leaveId});
	}
	@Override
	public OfficeLeavetypeLong save(OfficeLeavetypeLong officeLeavetypeLong){
		String sql = "insert into office_leavetype_long(id, leave_id, start_time, end_time, days, has_bed, address, mate_name, mate_gx, creation_time) values(?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeLeavetypeLong.getId())){
			officeLeavetypeLong.setId(createId());
		}
		Object[] args = new Object[]{
			officeLeavetypeLong.getId(), officeLeavetypeLong.getLeaveId(), 
			officeLeavetypeLong.getStartTime(), officeLeavetypeLong.getEndTime(), 
			officeLeavetypeLong.getDays(), officeLeavetypeLong.getHasBed(), 
			officeLeavetypeLong.getAddress(), officeLeavetypeLong.getMateName(), 
			officeLeavetypeLong.getMateGx(), officeLeavetypeLong.getCreationTime()
		};
		update(sql, args);
		return officeLeavetypeLong;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_leavetype_long where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public List<OfficeLeavetypeLong> isExistTime(String id, Date startTime,
			Date endTime) {
		String sql = "select * from office_leavetype_long where " +
				"(start_time >= ? AND start_time <= ?) " +
				"OR (start_time <= ? AND end_time >= ?) " +
				"OR (end_time >= ? AND end_time <= ?)";
		if(StringUtils.isNotBlank(id)){
			sql = sql + " and id <> ? ";
			return query(sql, new Object[]{startTime,endTime,startTime,endTime,startTime,endTime,id},new MultiRow());
		}else{
			return query(sql, new Object[]{startTime,endTime,startTime,endTime,startTime,endTime},new MultiRow());
		}
	}
	
	@Override
	public Integer update(OfficeLeavetypeLong officeLeavetypeLong){
		String sql = "update office_leavetype_long set leave_id = ?, start_time = ?, end_time = ?, days = ?, has_bed = ?, address = ?, mate_name = ?, mate_gx = ?, creation_time = ? where id = ?";
		Object[] args = new Object[]{
			officeLeavetypeLong.getLeaveId(), officeLeavetypeLong.getStartTime(), 
			officeLeavetypeLong.getEndTime(), officeLeavetypeLong.getDays(), 
			officeLeavetypeLong.getHasBed(), officeLeavetypeLong.getAddress(), 
			officeLeavetypeLong.getMateName(), officeLeavetypeLong.getMateGx(), 
			officeLeavetypeLong.getCreationTime(), officeLeavetypeLong.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeLeavetypeLong getOfficeLeavetypeLongById(String id){
		String sql = "select * from office_leavetype_long where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeLeavetypeLong> getOfficeLeavetypeLongMapByIds(String[] ids){
		String sql = "select * from office_leavetype_long where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}
	@Override
	public Map<String, OfficeLeavetypeLong> getMapByLeaveIds(
			String[] leaveIds) {
		String sql = "select * from office_leavetype_long where leave_id in";
		return queryForInSQL(sql, null, leaveIds, new MapRowMapper<String, OfficeLeavetypeLong>(){
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("leave_id");
			}
			@Override
			public OfficeLeavetypeLong mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}
	@Override
	public List<OfficeLeavetypeLong> getOfficeLeavetypeLongList(){
		String sql = "select * from office_leavetype_long";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeLeavetypeLong> getOfficeLeavetypeLongPage(Pagination page){
		String sql = "select * from office_leavetype_long";
		return query(sql, new MultiRow(), page);
	}
	@Override
	public OfficeLeavetypeLong findByLeaveId(
			String leaveId) {
		String sql = "select * from office_leavetype_long where leave_id = ?";
		return query(sql, new Object[]{leaveId }, new SingleRow());
	}
	@Override
	public List<OfficeLeavetypeLong> findByLeaveIds(String[] leaveIds) {
		String sql = "select * from office_leavetype_long where leave_id in";
		return queryForInSQL(sql,null,leaveIds, new MultiRow());
	}
	@Override
	public List<OfficeLeavetypeLong> findByLeaveTimeAndLeaveIds(Date date,
			String[] leaveIds) {
		String sql = "select * from office_leavetype_long where start_time <= ? and end_time >= ? and leave_id in";
		return queryForInSQL(sql,new Object[]{date,date},leaveIds, new MultiRow());
	}
}
