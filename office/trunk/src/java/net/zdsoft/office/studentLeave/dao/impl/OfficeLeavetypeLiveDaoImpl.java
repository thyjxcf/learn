package net.zdsoft.office.studentLeave.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.dao.OfficeLeavetypeLiveDao;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeLive;

import org.apache.commons.lang3.StringUtils;
/**
 * office_leavetype_live
 * @author 
 * 
 */
public class OfficeLeavetypeLiveDaoImpl extends BaseDao<OfficeLeavetypeLive> implements OfficeLeavetypeLiveDao{

	@Override
	public OfficeLeavetypeLive setField(ResultSet rs) throws SQLException{
		OfficeLeavetypeLive officeLeavetypeLive = new OfficeLeavetypeLive();
		officeLeavetypeLive.setId(rs.getString("id"));
		officeLeavetypeLive.setLeaveId(rs.getString("leave_id"));
		officeLeavetypeLive.setApplyType(rs.getString("apply_type"));
		officeLeavetypeLive.setStartTime(rs.getTimestamp("start_time"));
		officeLeavetypeLive.setEndTime(rs.getTimestamp("end_time"));
		officeLeavetypeLive.setDays(rs.getInt("days"));
		officeLeavetypeLive.setCreationTime(rs.getTimestamp("creation_time"));
		officeLeavetypeLive.setRemark(rs.getString("remark"));
		return officeLeavetypeLive;
	}
	@Override
	public void deleteByLeaveId(String leaveId) {
		String sql = "delete from office_leavetype_live where leave_id = ?";
		update(sql, new Object[]{leaveId});
	}
	@Override
	public OfficeLeavetypeLive save(OfficeLeavetypeLive officeLeavetypeLive){
		String sql = "insert into office_leavetype_live(id, leave_id, apply_type, start_time, end_time, days, creation_time, remark) values(?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeLeavetypeLive.getId())){
			officeLeavetypeLive.setId(createId());
		}
		Object[] args = new Object[]{
			officeLeavetypeLive.getId(), officeLeavetypeLive.getLeaveId(), 
			officeLeavetypeLive.getApplyType(), officeLeavetypeLive.getStartTime(), 
			officeLeavetypeLive.getEndTime(), officeLeavetypeLive.getDays(), 
			officeLeavetypeLive.getCreationTime(), officeLeavetypeLive.getRemark()
		};
		update(sql, args);
		return officeLeavetypeLive;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_leavetype_live where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeLeavetypeLive officeLeavetypeLive){
		String sql = "update office_leavetype_live set leave_id = ?, apply_type = ?, start_time = ?, end_time = ?, days = ?, creation_time = ?, remark = ? where id = ?";
		Object[] args = new Object[]{
			officeLeavetypeLive.getLeaveId(), officeLeavetypeLive.getApplyType(), 
			officeLeavetypeLive.getStartTime(), officeLeavetypeLive.getEndTime(), 
			officeLeavetypeLive.getDays(), officeLeavetypeLive.getCreationTime(), 
			officeLeavetypeLive.getRemark(), officeLeavetypeLive.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeLeavetypeLive getOfficeLeavetypeLiveById(String id){
		String sql = "select * from office_leavetype_live where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeLeavetypeLive> getOfficeLeavetypeLiveMapByIds(String[] ids){
		String sql = "select * from office_leavetype_live where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}
	@Override
	public Map<String, OfficeLeavetypeLive> getMapByLeaveIds(
			String[] leaveIds) {
		String sql = "select * from office_leavetype_live where leave_id in";
		return queryForInSQL(sql, null, leaveIds, new MapRowMapper<String, OfficeLeavetypeLive>(){
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("leave_id");
			}
			@Override
			public OfficeLeavetypeLive mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}
	
	@Override
	public List<OfficeLeavetypeLive> isExistTime(String id, Date startTime,
			Date endTime) {
		String sql = "select * from office_leavetype_live where " +
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
	public List<OfficeLeavetypeLive> getOfficeLeavetypeLiveList(){
		String sql = "select * from office_leavetype_live";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeLeavetypeLive> getOfficeLeavetypeLivePage(Pagination page){
		String sql = "select * from office_leavetype_live";
		return query(sql, new MultiRow(), page);
	}
	
	@Override
	public OfficeLeavetypeLive findByLeaveId(
			String leaveId) {
		String sql = "select * from office_leavetype_live where leave_id = ?";
		return query(sql, new Object[]{leaveId }, new SingleRow());
	}
	@Override
	public List<OfficeLeavetypeLive> findByLeaveIds(String[] leaveIds) {
		String sql = "select * from office_leavetype_live where leave_id in";
		return queryForInSQL(sql,null,leaveIds, new MultiRow());
	}
	@Override
	public List<OfficeLeavetypeLive> findByLeaveTimeAndAppleyTypeAdnLeaveIds(
			Date date, String applyType, String[] leaveIds) {
		if(StringUtils.isBlank(applyType)){
			String sql = "select * from office_leavetype_live where start_time <= ? and end_time >= ? and leave_id in";
			return queryForInSQL(sql,new Object[]{date,date},leaveIds, new MultiRow());
		}else{
			String sql = "select * from office_leavetype_live where start_time <= ? and end_time >= ? and apply_type=? and leave_id in";
			return queryForInSQL(sql,new Object[]{date,date,applyType},leaveIds, new MultiRow());
		}
	}
	
}

	