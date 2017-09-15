package net.zdsoft.office.schedule.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.schedule.dao.OfficeCalendarAuthDao;
import net.zdsoft.office.schedule.entity.OfficeCalendarAuth;

import org.apache.commons.lang3.StringUtils;
/**
 * office_calendar_auth
 * @author 
 * 
 */
public class OfficeCalendarAuthDaoImpl extends BaseDao<OfficeCalendarAuth> implements OfficeCalendarAuthDao{

	@Override
	public OfficeCalendarAuth setField(ResultSet rs) throws SQLException{
		OfficeCalendarAuth officeCalendarAuth = new OfficeCalendarAuth();
		officeCalendarAuth.setId(rs.getString("id"));
		officeCalendarAuth.setUnitId(rs.getString("unit_id"));
		officeCalendarAuth.setAuthType(rs.getString("auth_type"));
		officeCalendarAuth.setObjectId(rs.getString("object_id"));
		officeCalendarAuth.setLeaderId(rs.getString("leader_id"));
		return officeCalendarAuth;
	}

	@Override
	public OfficeCalendarAuth save(OfficeCalendarAuth officeCalendarAuth){
		String sql = "insert into office_calendar_auth(id, unit_id, auth_type, object_id, leader_id) values(?,?,?,?,?)";
		if (StringUtils.isBlank(officeCalendarAuth.getId())){
			officeCalendarAuth.setId(createId());
		}
		Object[] args = new Object[]{
			officeCalendarAuth.getId(), officeCalendarAuth.getUnitId(), 
			officeCalendarAuth.getAuthType(), officeCalendarAuth.getObjectId(), 
			officeCalendarAuth.getLeaderId()
		};
		update(sql, args);
		return officeCalendarAuth;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_calendar_auth where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeCalendarAuth officeCalendarAuth){
		String sql = "update office_calendar_auth set unit_id = ?, auth_type = ?, object_id = ?, leader_id = ? where id = ?";
		Object[] args = new Object[]{
			officeCalendarAuth.getUnitId(), officeCalendarAuth.getAuthType(), 
			officeCalendarAuth.getObjectId(), officeCalendarAuth.getLeaderId(), 
			officeCalendarAuth.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeCalendarAuth getOfficeCalendarAuthById(String id){
		String sql = "select * from office_calendar_auth where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeCalendarAuth> getOfficeCalendarAuthMapByIds(String[] ids){
		String sql = "select * from office_calendar_auth where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeCalendarAuth> getOfficeCalendarAuthList(){
		String sql = "select * from office_calendar_auth";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeCalendarAuth> getOfficeCalendarAuthPage(Pagination page){
		String sql = "select * from office_calendar_auth";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeCalendarAuth> getOfficeCalendarAuthByUnitIdList(String unitId){
		String sql = "select * from office_calendar_auth where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}
	@Override
	public OfficeCalendarAuth getOfficeCalendarAuthByUnitIdPage(String unitId,
			String objectId, String authType) {
		String sql = "select * from office_calendar_auth where unit_id = ? and auth_type = ? and object_id = ?";
		return query(sql, new Object[]{unitId,authType,objectId }, new SingleRow());
	}
	public boolean checkHasAuth(String unitId, String type, String objectId, String userId){
		String sql = "select count(1) from office_calendar_auth where unit_id=? and auth_type=? and object_id=? and leader_id like ?";
		int count = queryForInt(sql, new Object[]{unitId, type, objectId, "%"+userId+"%"});
		if(count > 0){
			return true;
		}
		return false;
	}

	@Override
	public List<OfficeCalendarAuth> getOfficeCalendarAuthByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_calendar_auth where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	@Override
	public Map<String, OfficeCalendarAuth> getOfficeAuthMap(String unitId) {
		String sql="select * from office_calendar_auth where unit_id = ?";
		return queryForMap(sql,new Object[]{unitId},new MapRowMapper<String, OfficeCalendarAuth>() {

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("object_id");
			}

			@Override
			public OfficeCalendarAuth mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				OfficeCalendarAuth off=setField(rs);
				return off;
			}
			
		});
	}
}
