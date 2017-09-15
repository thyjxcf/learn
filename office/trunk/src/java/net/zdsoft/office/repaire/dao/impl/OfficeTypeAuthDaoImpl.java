package net.zdsoft.office.repaire.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.repaire.dao.OfficeTypeAuthDao;
import net.zdsoft.office.repaire.entity.OfficeTypeAuth;

import org.apache.commons.lang.StringUtils;
/**
 * office_type_auth
 * @author 
 * 
 */
public class OfficeTypeAuthDaoImpl extends BaseDao<OfficeTypeAuth> implements OfficeTypeAuthDao{

	@Override
	public OfficeTypeAuth setField(ResultSet rs) throws SQLException{
		OfficeTypeAuth officeTypeAuth = new OfficeTypeAuth();
		officeTypeAuth.setId(rs.getString("id"));
		officeTypeAuth.setType(rs.getString("type"));
		officeTypeAuth.setUserId(rs.getString("user_id"));
		officeTypeAuth.setCreateTime(rs.getTimestamp("create_time"));
		officeTypeAuth.setState(rs.getString("state"));
		officeTypeAuth.setUnitId(rs.getString("unit_id"));
		return officeTypeAuth;
	}

	@Override
	public OfficeTypeAuth save(OfficeTypeAuth officeTypeAuth){
		String sql = "insert into office_type_auth(id, type, user_id, create_time, state, unit_id) values(?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeTypeAuth.getId())){
			officeTypeAuth.setId(createId());
		}
		Object[] args = new Object[]{
			officeTypeAuth.getId(), officeTypeAuth.getType(), 
			officeTypeAuth.getUserId(), officeTypeAuth.getCreateTime(), 
			officeTypeAuth.getState(), officeTypeAuth.getUnitId()
		};
		update(sql, args);
		return officeTypeAuth;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_type_auth where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeTypeAuth officeTypeAuth){
		String sql = "update office_type_auth set type = ?, user_id = ?, create_time = ?, state = ? where id = ?";
		Object[] args = new Object[]{
			officeTypeAuth.getType(), officeTypeAuth.getUserId(), 
			officeTypeAuth.getCreateTime(), officeTypeAuth.getState(), 
			officeTypeAuth.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeTypeAuth getOfficeTypeAuthById(String id){
		String sql = "select * from office_type_auth where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeTypeAuth> getOfficeTypeAuthMapByIds(String[] ids){
		String sql = "select * from office_type_auth where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeTypeAuth> getOfficeTypeAuthList(String unitId){
		String sql = "select * from office_type_auth where unit_id = ?";
		return query(sql, new Object[] {unitId}, new MultiRow());
	}

	@Override
	public List<OfficeTypeAuth> getOfficeTypeAuthPage(String unitId, Pagination page){
		String sql = "select * from office_type_auth where unit_id = ? ";
		return query(sql, new Object[]{unitId}, new MultiRow(), page);
	}
	@Override
	public List<OfficeTypeAuth> getUserPage(String unitId, String state, Pagination page) {
		String sql = "select distinct user_id  from office_type_auth where unit_id = ? and state = ? ";
		return query(sql, new Object[]{unitId, state}, new MultiRowMapper<OfficeTypeAuth>(){

			@Override
			public OfficeTypeAuth mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OfficeTypeAuth officeTypeAuth = new OfficeTypeAuth();
				officeTypeAuth.setUserId(rs.getString("user_id"));
				return officeTypeAuth;
			}
			
		});
	}
	
	@Override
	public List<OfficeTypeAuth> getOfficeTypeAuthList(String state,
			String userId) {
		String sql = "select * from office_type_auth where state = ? and user_id = ?";
		return query(sql, new Object[]{state,userId},new MultiRow());
	}
	@Override
	public void deleteByUserIds(String[] userIds) {
		String sql = "delete from office_type_auth where state = 1 and user_id in";
		updateForInSQL(sql, null, userIds);
	}
	@Override
	public void saveBach(List<OfficeTypeAuth> typelist) {
		// TODO Auto-generated method stub
		String sql = "insert into office_type_auth(id, type, user_id, create_time, state, unit_id) values(?,?,?,?,?,?)";
		List<Object[]> args = new ArrayList<Object[]>();
		for(OfficeTypeAuth phi : typelist){
			if(phi == null){
				continue;
			}
			if (StringUtils.isBlank(phi.getId())){
				phi.setId(createId());
			}
			Object[] arg = new Object[]{
					phi.getId(),phi.getType(), phi.getUserId(), 
					phi.getCreateTime(), phi.getState(), phi.getUnitId()
				};
			args.add(arg);
		}
		int[] argsType = new int[]{
				Types.CHAR,Types.CHAR,Types.CHAR,
				Types.DATE,Types.CHAR,Types.CHAR
			};
		batchUpdate(sql, args, argsType);
	}
	
	@Override
	public List<OfficeTypeAuth> getOfficeTypeAuthList(String unitId, String state, String typeId){
		String sql = "select * from office_type_auth where unit_id = ? and state = ? and type = ?";
		return query(sql, new Object[]{unitId, state, typeId}, new MultiRow());
	}
}