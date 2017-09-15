package net.zdsoft.office.studentLeave.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.dao.OfficeLeavetypeTemporaryDao;
import net.zdsoft.office.studentLeave.entity.OfficeLeavetypeTemporary;

import org.apache.commons.lang3.StringUtils;
/**
 * office_leavetype_temporary
 * @author 
 * 
 */
public class OfficeLeavetypeTemporaryDaoImpl extends BaseDao<OfficeLeavetypeTemporary> implements OfficeLeavetypeTemporaryDao{

	@Override
	public OfficeLeavetypeTemporary setField(ResultSet rs) throws SQLException{
		OfficeLeavetypeTemporary officeLeavetypeTemporary = new OfficeLeavetypeTemporary();
		officeLeavetypeTemporary.setId(rs.getString("id"));
		officeLeavetypeTemporary.setLeaveId(rs.getString("leave_id"));
		officeLeavetypeTemporary.setStartTime(rs.getTimestamp("start_time"));
		officeLeavetypeTemporary.setEndTime(rs.getTimestamp("end_time"));
		officeLeavetypeTemporary.setDays(rs.getInt("days"));
		officeLeavetypeTemporary.setLinkPhone(rs.getString("link_phone"));
		officeLeavetypeTemporary.setRemark(rs.getString("remark"));
		officeLeavetypeTemporary.setCreationTime(rs.getTimestamp("creation_time"));
		return officeLeavetypeTemporary;
	}
	@Override
	public void deleteByLeaveId(String leaveId) {
		String sql = "delete from office_leavetype_temporary where leave_id = ?";
		update(sql, new Object[]{leaveId});
	}
	@Override
	public OfficeLeavetypeTemporary save(OfficeLeavetypeTemporary officeLeavetypeTemporary){
		String sql = "insert into office_leavetype_temporary(id, leave_id, start_time, end_time, days, link_phone, remark, creation_time) values(?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeLeavetypeTemporary.getId())){
			officeLeavetypeTemporary.setId(createId());
		}
		Object[] args = new Object[]{
			officeLeavetypeTemporary.getId(), officeLeavetypeTemporary.getLeaveId(), 
			officeLeavetypeTemporary.getStartTime(), officeLeavetypeTemporary.getEndTime(), 
			officeLeavetypeTemporary.getDays(), officeLeavetypeTemporary.getLinkPhone(), 
			officeLeavetypeTemporary.getRemark(), officeLeavetypeTemporary.getCreationTime()
		};
		update(sql, args);
		return officeLeavetypeTemporary;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_leavetype_temporary where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeLeavetypeTemporary officeLeavetypeTemporary){
		String sql = "update office_leavetype_temporary set leave_id = ?, start_time = ?, end_time = ?, days = ?, link_phone = ?, remark = ?, creation_time = ? where id = ?";
		Object[] args = new Object[]{
			officeLeavetypeTemporary.getLeaveId(), officeLeavetypeTemporary.getStartTime(), 
			officeLeavetypeTemporary.getEndTime(), officeLeavetypeTemporary.getDays(), 
			officeLeavetypeTemporary.getLinkPhone(), officeLeavetypeTemporary.getRemark(), 
			officeLeavetypeTemporary.getCreationTime(), officeLeavetypeTemporary.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeLeavetypeTemporary getOfficeLeavetypeTemporaryById(String id){
		String sql = "select * from office_leavetype_temporary where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeLeavetypeTemporary> getOfficeLeavetypeTemporaryMapByIds(String[] ids){
		String sql = "select * from office_leavetype_temporary where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}
	@Override
	public Map<String, OfficeLeavetypeTemporary> getMapByLeaveIds(
			String[] leaveIds) {
		String sql = "select * from office_leavetype_temporary where leave_id in";
		return queryForInSQL(sql, null, leaveIds, new MapRowMapper<String, OfficeLeavetypeTemporary>(){
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("leave_id");
			}
			@Override
			public OfficeLeavetypeTemporary mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}
	@Override
	public List<OfficeLeavetypeTemporary> getOfficeLeavetypeTemporaryList(){
		String sql = "select * from office_leavetype_temporary";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeLeavetypeTemporary> getOfficeLeavetypeTemporaryPage(Pagination page){
		String sql = "select * from office_leavetype_temporary";
		return query(sql, new MultiRow(), page);
	}
	
	@Override
	public OfficeLeavetypeTemporary findByLeaveId(
			String leaveId) {
		String sql = "select * from office_leavetype_temporary where leave_id = ?";
		return query(sql, new Object[]{leaveId }, new SingleRow());
	}
	@Override
	public List<OfficeLeavetypeTemporary> findByLeaveIds(String[] leaveIds) {
		String sql = "select * from office_leavetype_temporary where leave_id in";
		return queryForInSQL(sql,null,leaveIds, new MultiRow());
	}
	@Override
	public List<OfficeLeavetypeTemporary> isExistTime(String id,
			Date startTime, Date endTime) {
		String sql = "select * from office_leavetype_temporary where " +
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
	public List<OfficeLeavetypeTemporary> findByLeaveTimeAndLeaveIds(Date date,
			String[] leaveIds) {
		String sql = "select * from office_leavetype_temporary where start_time <= ? and end_time >= ? and leave_id in";
		return queryForInSQL(sql,new Object[]{date,date},leaveIds, new MultiRow());
	}
}
