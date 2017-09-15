package net.zdsoft.office.repaire.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.repaire.dao.OfficeRepaireTypeDao;
import net.zdsoft.office.repaire.entity.OfficeRepaireType;

import org.apache.commons.lang.StringUtils;
/**
 * office_repaire_type
 * @author 
 * 
 */
public class OfficeRepaireTypeDaoImpl extends BaseDao<OfficeRepaireType> implements OfficeRepaireTypeDao{

	@Override
	public OfficeRepaireType setField(ResultSet rs) throws SQLException{
		OfficeRepaireType officeRepaireType = new OfficeRepaireType();
		officeRepaireType.setId(rs.getString("id"));
		officeRepaireType.setThisId(rs.getString("this_id"));
		officeRepaireType.setTypeName(rs.getString("type_name"));
		officeRepaireType.setUserIds(rs.getString("user_ids"));
		officeRepaireType.setIsDeleted(rs.getBoolean("is_deleted"));
		officeRepaireType.setUnitId(rs.getString("unit_id"));
		return officeRepaireType;
	}

	@Override
	public OfficeRepaireType save(OfficeRepaireType officeRepaireType){
		String sql = "insert into office_repaire_type(id, this_id, type_name, user_ids, is_deleted, unit_id) values(?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeRepaireType.getId())){
			officeRepaireType.setId(createId());
		}
		Object[] args = new Object[]{
			officeRepaireType.getId(), officeRepaireType.getThisId(), 
			officeRepaireType.getTypeName(), officeRepaireType.getUserIds(),
			officeRepaireType.getIsDeleted(), officeRepaireType.getUnitId()
		};
		update(sql, args);
		return officeRepaireType;
	}

	@Override
	public Integer delete(String id){
		String sql = "update office_repaire_type set is_deleted = 1 where id = ? ";
		return update(sql, id);
	}

	@Override
	public Integer update(OfficeRepaireType officeRepaireType){
		String sql = "update office_repaire_type set this_id = ?, type_name = ?, user_ids = ? where id = ?";
		Object[] args = new Object[]{
			officeRepaireType.getThisId(), officeRepaireType.getTypeName(),
			officeRepaireType.getUserIds(), officeRepaireType.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeRepaireType getOfficeRepaireTypeById(String id){
		String sql = "select * from office_repaire_type where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeRepaireType> getOfficeRepaireTypeMapByIds(String[] ids){
		String sql = "select * from office_repaire_type where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}
	
	@Override
	public Map<String, String> getOfficeRepaireTypeMapByUnitId(String unitId) {
		String sql = "select id, type_name from office_repaire_type where unit_id = ? ";
		return queryForMap(sql, new Object[]{unitId}, new MapRowMapper<String, String>(){
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("id");
			}
			
			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("type_name");
			}
		});
	}

	@Override
	public List<OfficeRepaireType> getOfficeRepaireTypeList(){
		String sql = "select * from office_repaire_type";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeRepaireType> getOfficeRepaireTypePage(Pagination page){
		String sql = "select * from office_repaire_type";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeRepaireType> getOfficeRepaireTypeByUnitIdList(String unitId, String thisId){
		String sql = "select * from office_repaire_type where unit_id = ? and this_id = ? and is_deleted = 0 ";
		return query(sql, new Object[]{unitId, thisId }, new MultiRow());
	}
	@Override
	public List<OfficeRepaireType> getOfficeRepaireTypeByUnitIdList(
			String unitId) {
		String sql = "select * from office_repaire_type where unit_id = ? and is_deleted = 0 ";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}
	@Override
	public List<OfficeRepaireType> getOfficeRepaireTypeByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_repaire_type where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
}