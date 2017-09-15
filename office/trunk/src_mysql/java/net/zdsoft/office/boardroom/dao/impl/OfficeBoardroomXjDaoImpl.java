package net.zdsoft.office.boardroom.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.boardroom.entity.OfficeBoardroomXj;
import net.zdsoft.office.boardroom.dao.OfficeBoardroomXjDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MapRow;
import net.zdsoft.keel.util.Pagination;
/**
 * office_boardroom_xj
 * @author 
 * 
 */
public class OfficeBoardroomXjDaoImpl extends BaseDao<OfficeBoardroomXj> implements OfficeBoardroomXjDao{

	private static final String SQL_FIND_DEPTS_BY_UNITID = "SELECT * FROM office_boardroom_xj where unit_id = ?";
	@Override
	public OfficeBoardroomXj setField(ResultSet rs) throws SQLException{
		OfficeBoardroomXj officeBoardroomXj = new OfficeBoardroomXj();
		officeBoardroomXj.setId(rs.getString("id"));
		officeBoardroomXj.setUnitId(rs.getString("unit_id"));
		officeBoardroomXj.setName(rs.getString("name"));
		officeBoardroomXj.setNeedAudit(rs.getString("need_audit"));
		officeBoardroomXj.setStartTime(rs.getString("start_time"));
		officeBoardroomXj.setEndTime(rs.getString("end_time"));
		officeBoardroomXj.setTimeInterval(rs.getString("time_interval"));
		officeBoardroomXj.setNoonStartTime(rs.getString("noon_start_time"));
		officeBoardroomXj.setNoonEndTime(rs.getString("noon_end_time"));
		officeBoardroomXj.setMaxNumber(rs.getInt("max_number"));
		officeBoardroomXj.setAddress(rs.getString("address"));
		officeBoardroomXj.setRostrum(rs.getInt("rostrum"));
		officeBoardroomXj.setConferenceSeats(rs.getInt("conference_seats"));
		officeBoardroomXj.setTableType(rs.getInt("table_type"));
		officeBoardroomXj.setAttendNumber(rs.getInt("attend_number"));
		officeBoardroomXj.setIsProjector(rs.getBoolean("is_projector"));
		officeBoardroomXj.setRemark(rs.getString("remark"));
		officeBoardroomXj.setCreateTime(rs.getTimestamp("create_time"));
		return officeBoardroomXj;
	}

	@Override
	public OfficeBoardroomXj save(OfficeBoardroomXj officeBoardroomXj){
		String sql = "insert into office_boardroom_xj(id, unit_id, name, need_audit, start_time, end_time, time_interval, noon_start_time, noon_end_time, max_number, address, rostrum, conference_seats, table_type, attend_number, is_projector, remark, create_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeBoardroomXj.getId())){
			officeBoardroomXj.setId(createId());
		}
		Object[] args = new Object[]{
			officeBoardroomXj.getId(), officeBoardroomXj.getUnitId(), 
			officeBoardroomXj.getName(), officeBoardroomXj.getNeedAudit(), 
			officeBoardroomXj.getStartTime(), officeBoardroomXj.getEndTime(), 
			officeBoardroomXj.getTimeInterval(), officeBoardroomXj.getNoonStartTime(), 
			officeBoardroomXj.getNoonEndTime(), officeBoardroomXj.getMaxNumber(), 
			officeBoardroomXj.getAddress(), officeBoardroomXj.getRostrum(), 
			officeBoardroomXj.getConferenceSeats(), officeBoardroomXj.getTableType(), 
			officeBoardroomXj.getAttendNumber(), officeBoardroomXj.getIsProjector(), 
			officeBoardroomXj.getRemark(), officeBoardroomXj.getCreateTime()
		};
		update(sql, args);
		return officeBoardroomXj;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_boardroom_xj where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeBoardroomXj officeBoardroomXj){
		String sql = "update office_boardroom_xj set unit_id = ?, name = ?, need_audit = ?, start_time = ?, end_time = ?, time_interval = ?, noon_start_time = ?, noon_end_time = ?, max_number = ?, address = ?, rostrum = ?, conference_seats = ?, table_type = ?, attend_number = ?, is_projector = ?, remark = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			officeBoardroomXj.getUnitId(), officeBoardroomXj.getName(), 
			officeBoardroomXj.getNeedAudit(), officeBoardroomXj.getStartTime(), 
			officeBoardroomXj.getEndTime(), officeBoardroomXj.getTimeInterval(), 
			officeBoardroomXj.getNoonStartTime(), officeBoardroomXj.getNoonEndTime(), 
			officeBoardroomXj.getMaxNumber(), officeBoardroomXj.getAddress(), 
			officeBoardroomXj.getRostrum(), officeBoardroomXj.getConferenceSeats(), 
			officeBoardroomXj.getTableType(), officeBoardroomXj.getAttendNumber(), 
			officeBoardroomXj.getIsProjector(), officeBoardroomXj.getRemark(), 
			officeBoardroomXj.getCreateTime(), officeBoardroomXj.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeBoardroomXj getOfficeBoardroomXjById(String id){
		String sql = "select * from office_boardroom_xj where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeBoardroomXj> getOfficeBoardroomXjMapByIds(String[] ids){
		String sql = "select * from office_boardroom_xj where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeBoardroomXj> getOfficeBoardroomXjList(){
		String sql = "select * from office_boardroom_xj";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeBoardroomXj> getOfficeBoardroomXjPage(Pagination page){
		String sql = "select * from office_boardroom_xj";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeBoardroomXj> getOfficeBoardroomXjByUnitIdList(String unitId){
		String sql = "select * from office_boardroom_xj where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeBoardroomXj> getOfficeBoardroomXjByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_boardroom_xj where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public boolean isExistConflict(String unitId, String name,String id) {
		String sql = "select count(1) from office_boardroom_xj where unit_id=? and name=?";
		
		String sql1 = "select count(1) from office_boardroom_xj where id != ? and unit_id=? and name=?";
		int i = 0;
		if(StringUtils.isNotBlank(id)){
			i = queryForInt(sql1, new Object[] {id,unitId,name});
		}else{
			i = queryForInt(sql, new Object[] {unitId,name});
		}
		if(i > 0){
			return true;
		}
		return false;
	}

	@Override
	public Map<String, OfficeBoardroomXj> getOfficeBoardroomMapByUnitId(
			String unitId) {
		return queryForMap(SQL_FIND_DEPTS_BY_UNITID, new String[] { unitId }, new MapRow());
	}
	
}
