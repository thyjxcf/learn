package net.zdsoft.office.meeting.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeDeptLeaderDao;
import net.zdsoft.office.meeting.entity.OfficeDeptLeader;

import org.apache.commons.lang3.StringUtils;
/**
 * office_dept_leader
 * @author 
 * 
 */
public class OfficeDeptLeaderDaoImpl extends BaseDao<OfficeDeptLeader> implements OfficeDeptLeaderDao{

	@Override
	public OfficeDeptLeader setField(ResultSet rs) throws SQLException{
		OfficeDeptLeader officeDeptLeader = new OfficeDeptLeader();
		officeDeptLeader.setId(rs.getString("id"));
		officeDeptLeader.setUnitId(rs.getString("unit_id"));
		officeDeptLeader.setDeptId(rs.getString("dept_id"));
		officeDeptLeader.setUserId(rs.getString("user_id"));
		return officeDeptLeader;
	}

	@Override
	public OfficeDeptLeader save(OfficeDeptLeader officeDeptLeader){
		String sql = "insert into office_dept_leader(id, unit_id, dept_id, user_id) values(?,?,?,?)";
		if (StringUtils.isBlank(officeDeptLeader.getId())){
			officeDeptLeader.setId(createId());
		}
		Object[] args = new Object[]{
			officeDeptLeader.getId(), officeDeptLeader.getUnitId(), 
			officeDeptLeader.getDeptId(), officeDeptLeader.getUserId()
		};
		update(sql, args);
		return officeDeptLeader;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_dept_leader where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeDeptLeader officeDeptLeader){
		String sql = "update office_dept_leader set unit_id = ?, dept_id = ?, user_id = ? where id = ?";
		Object[] args = new Object[]{
			officeDeptLeader.getUnitId(), officeDeptLeader.getDeptId(), 
			officeDeptLeader.getUserId(), officeDeptLeader.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeDeptLeader getOfficeDeptLeaderById(String id){
		String sql = "select * from office_dept_leader where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeDeptLeader> getOfficeDeptLeaderMapByIds(String[] ids){
		String sql = "select * from office_dept_leader where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeDeptLeader> getOfficeDeptLeaderList(){
		String sql = "select * from office_dept_leader";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeDeptLeader> getOfficeDeptLeaderPage(Pagination page){
		String sql = "select * from office_dept_leader";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeDeptLeader> getOfficeDeptLeaderByUnitIdList(String unitId){
		String sql = "select * from office_dept_leader where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeDeptLeader> getOfficeDeptLeaderByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_dept_leader where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public Map<String, OfficeDeptLeader> getOfficeDeptMap() {
		String sql="select * from office_dept_leader";
		return queryForMap(sql, new MapRowMapper<String, OfficeDeptLeader>() {

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("dept_id");
			}

			@Override
			public OfficeDeptLeader mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				OfficeDeptLeader off=setField(rs);
				return off;
			}
			
		});
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, String> getLeaderMap(String[] deptIds) {
		String sql = "select * from office_dept_leader where dept_id in ";
		return queryForInSQL(sql,null, deptIds, new MapRowMapper() {
			public String mapRowValue(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("user_id");
			}
			
			public String mapRowKey(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("dept_id");
			}
		});
	}
	@Override
	public OfficeDeptLeader getOfficeDeptLeaderByUnitIdList(String unitId,
			String userId, String deptId) {
		String sql = "select * from office_dept_leader where unit_id = ? and dept_id = ? and user_id = ?";
		return query(sql, new Object[]{unitId,deptId,userId }, new SingleRow());
	}

}
