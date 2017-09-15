package net.zdsoft.office.studentLeave.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.dao.OfficeLeavetypeGeneralDao;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeGeneral;

import org.apache.commons.lang3.StringUtils;
/**
 * office_leavetype_general
 * @author 
 * 
 */
public class OfficeLeavetypeGeneralDaoImpl extends BaseDao<OfficeLeavetypeGeneral> implements OfficeLeavetypeGeneralDao{

	@Override
	public OfficeLeavetypeGeneral setField(ResultSet rs) throws SQLException{
		OfficeLeavetypeGeneral officeLeavetypeGeneral = new OfficeLeavetypeGeneral();
		officeLeavetypeGeneral.setId(rs.getString("id"));
		officeLeavetypeGeneral.setLeaveId(rs.getString("leave_id"));
		officeLeavetypeGeneral.setStartTime(rs.getTimestamp("start_time"));
		officeLeavetypeGeneral.setEndTime(rs.getTimestamp("end_time"));
		officeLeavetypeGeneral.setDays(rs.getFloat("days"));
		officeLeavetypeGeneral.setRemark(rs.getString("remark"));
		officeLeavetypeGeneral.setCreationTime(rs.getTimestamp("creation_time"));
		return officeLeavetypeGeneral;
	}

	@Override
	public OfficeLeavetypeGeneral save(OfficeLeavetypeGeneral officeLeavetypeGeneral){
		String sql = "insert into office_leavetype_general(id, leave_id, start_time, end_time, days, remark, creation_time) values(?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeLeavetypeGeneral.getId())){
			officeLeavetypeGeneral.setId(createId());
		} 
		Object[] args = new Object[]{
			officeLeavetypeGeneral.getId(), officeLeavetypeGeneral.getLeaveId(), 
			officeLeavetypeGeneral.getStartTime(), officeLeavetypeGeneral.getEndTime(), 
			officeLeavetypeGeneral.getDays(), officeLeavetypeGeneral.getRemark(), 
			officeLeavetypeGeneral.getCreationTime()
		};
		update(sql, args);
		return officeLeavetypeGeneral;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_leavetype_general where id in";
		return updateForInSQL(sql, null, ids);
	}
	@Override
	public void deleteByLeaveId(String leaveId) {
		String sql = "delete from office_leavetype_general where leave_id = ?";
		update(sql, new Object[]{leaveId});
	}

	@Override
	public Integer update(OfficeLeavetypeGeneral officeLeavetypeGeneral){
		String sql = "update office_leavetype_general set leave_id = ?, start_time = ?, end_time = ?, days = ?, remark = ?, creation_time = ? where id = ?";
		Object[] args = new Object[]{
			officeLeavetypeGeneral.getLeaveId(), officeLeavetypeGeneral.getStartTime(), 
			officeLeavetypeGeneral.getEndTime(), officeLeavetypeGeneral.getDays(), 
			officeLeavetypeGeneral.getRemark(), officeLeavetypeGeneral.getCreationTime(), 
			officeLeavetypeGeneral.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeLeavetypeGeneral getOfficeLeavetypeGeneralById(String id){
		String sql = "select * from office_leavetype_general where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	@Override
	public OfficeLeavetypeGeneral getOfficeLeavetypeGeneralByLeaveId(
			String leaveId) {
		String sql = "select * from office_leavetype_general where leave_id = ?";
		return query(sql, new Object[]{leaveId }, new SingleRow());
	}
	@Override
	public List<OfficeLeavetypeGeneral> findByLeaveIds(String[] leaveIds) {
		String sql = "select * from office_leavetype_general where leave_id in";
		return queryForInSQL(sql,null,leaveIds, new MultiRow());
	}
	
	@Override
	public List<OfficeLeavetypeGeneral> findByLeaveTimeAndLeaveIds(Date date,
			String[] leaveIds) {
		String sql = "select * from office_leavetype_general where start_time <= ? and end_time >= ? and leave_id in";
		return queryForInSQL(sql,new Object[]{date,date},leaveIds, new MultiRow());
	}
	@Override
	public List<OfficeLeavetypeGeneral> isExistTime(String id, Date startTime,
			Date endTime) {
		String sql = "select * from office_leavetype_general where " +
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
	public Map<String, OfficeLeavetypeGeneral> getOfficeLeavetypeGeneralMapByIds(String[] ids){
		String sql = "select * from office_leavetype_general where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}
	@Override
	public Map<String, OfficeLeavetypeGeneral> getMapByLeaveIds(
			String[] leaveIds) {
		String sql = "select * from office_leavetype_general where leave_id in";
		return queryForInSQL(sql, null, leaveIds, new MapRowMapper<String, OfficeLeavetypeGeneral>(){
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("leave_id");
			}
			@Override
			public OfficeLeavetypeGeneral mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}
	@Override
	public List<OfficeLeavetypeGeneral> getOfficeLeavetypeGeneralList(){
		String sql = "select * from office_leavetype_general";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeLeavetypeGeneral> getOfficeLeavetypeGeneralPage(Pagination page){
		String sql = "select * from office_leavetype_general";
		return query(sql, new MultiRow(), page);
	}
}
