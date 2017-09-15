package net.zdsoft.office.seal.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.seal.entity.OfficeSealType;
import net.zdsoft.office.seal.dao.OfficeSealTypeDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
/**
 * office_seal_type
 * @author 
 * 
 */
public class OfficeSealTypeDaoImpl extends BaseDao<OfficeSealType> implements OfficeSealTypeDao{

	@Override
	public OfficeSealType setField(ResultSet rs) throws SQLException{
		OfficeSealType officeSealType = new OfficeSealType();
		officeSealType.setId(rs.getString("id"));
		officeSealType.setUnitId(rs.getString("unit_id"));
		officeSealType.setTypeId(rs.getString("type_id"));
		officeSealType.setTypeName(rs.getString("type_name"));
		return officeSealType;
	}

	@Override
	public OfficeSealType save(OfficeSealType officeSealType){
		String sql = "insert into office_seal_type(id, unit_id, type_id, type_name) values(?,?,?,?)";
		if (StringUtils.isBlank(officeSealType.getId())){
			officeSealType.setId(createId());
		}
		Object[] args = new Object[]{
			officeSealType.getId(), officeSealType.getUnitId(), 
			officeSealType.getTypeId(), officeSealType.getTypeName()
		};
		update(sql, args);
		return officeSealType;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_seal_type where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeSealType officeSealType){
		String sql = "update office_seal_type set unit_id = ?, type_id = ?, type_name = ? where id = ?";
		Object[] args = new Object[]{
			officeSealType.getUnitId(), officeSealType.getTypeId(), 
			officeSealType.getTypeName(), officeSealType.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeSealType getOfficeSealTypeById(String id){
		String sql = "select * from office_seal_type where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeSealType> getOfficeSealTypeMapByIds(String[] ids){
		String sql = "select * from office_seal_type where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeSealType> getOfficeSealTypeList(){
		String sql = "select * from office_seal_type";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeSealType> getOfficeSealTypePage(Pagination page){
		String sql = "select * from office_seal_type";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeSealType> getOfficeSealTypeByUnitIdList(String unitId){
		String sql = "select * from office_seal_type where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeSealType> getOfficeSealTypeByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_seal_type where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public Map<String, OfficeSealType> getOfficeSealTypeMap(String[] checkId) {
		String sql="select * from office_seal_type where id in";
		return queryForInSQL(sql, null, checkId, new MapRow());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, OfficeSealType> getOfficeSealTypeByUnitIdMap(
			String unitId) {
		String sql="select * from office_seal_type where unit_id=?";
		return queryForMap(sql, new String[]{unitId}, new MapRowMapper(){

			@Override
			public Object mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("type_id");
			}

			@Override
			public Object mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
			
		});
	}
}
