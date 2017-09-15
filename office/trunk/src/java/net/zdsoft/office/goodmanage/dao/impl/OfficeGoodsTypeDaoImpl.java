package net.zdsoft.office.goodmanage.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.goodmanage.entity.OfficeGoodsType;
import net.zdsoft.office.goodmanage.dao.OfficeGoodsTypeDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods_type
 * @author 
 * 
 */
public class OfficeGoodsTypeDaoImpl extends BaseDao<OfficeGoodsType> implements OfficeGoodsTypeDao{

	@Override
	public OfficeGoodsType setField(ResultSet rs) throws SQLException{
		OfficeGoodsType officeGoodsType = new OfficeGoodsType();
		officeGoodsType.setId(rs.getString("id"));
		officeGoodsType.setUnitId(rs.getString("unit_id"));
		officeGoodsType.setTypeId(rs.getString("type_id"));
		officeGoodsType.setTypeName(rs.getString("type_name"));
		return officeGoodsType;
	}

	@Override
	public OfficeGoodsType save(OfficeGoodsType officeGoodsType){
		String sql = "insert into office_goods_type(id, unit_id, type_id, type_name) values(?,?,?,?)";
		if (StringUtils.isBlank(officeGoodsType.getId())){
			officeGoodsType.setId(createId());
		}
		Object[] args = new Object[]{
			officeGoodsType.getId(), officeGoodsType.getUnitId(), 
			officeGoodsType.getTypeId(), officeGoodsType.getTypeName()
		};
		update(sql, args);
		return officeGoodsType;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_goods_type where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeGoodsType officeGoodsType){
		String sql = "update office_goods_type set unit_id = ?, type_id = ?, type_name = ? where id = ?";
		Object[] args = new Object[]{
			officeGoodsType.getUnitId(), officeGoodsType.getTypeId(), 
			officeGoodsType.getTypeName(), officeGoodsType.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeGoodsType getOfficeGoodsTypeById(String id){
		String sql = "select * from office_goods_type where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeGoodsType> getOfficeGoodsTypeMapByIds(String[] ids){
		String sql = "select * from office_goods_type where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeGoodsType> getOfficeGoodsTypeList(){
		String sql = "select * from office_goods_type";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeGoodsType> getOfficeGoodsTypePage(Pagination page){
		String sql = "select * from office_goods_type";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeGoodsType> getOfficeGoodsTypeByUnitIdList(String unitId){
		String sql = "select * from office_goods_type where unit_id = ? order by type_id";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeGoodsType> getOfficeGoodsTypeByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_goods_type where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, OfficeGoodsType> getOfficeGoodsTypeMapByUnitId(String unitId){
		String sql = "select * from office_goods_type where unit_id = ?";
		return queryForMap(sql, new Object[]{unitId}, new MapRowMapper(){
			public Object mapRowKey(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("type_id");
			}

			public Object mapRowValue(ResultSet rs, int arg1) throws SQLException {
				return setField(rs);
			}
		});
	}
	
	@Override
	public List<OfficeGoodsType> getOfficeGoodsTypeByTypeId(String unitId, String typeId){
		String sql = "select * from office_goods_type where unit_id = ? and type_id = ? order by type_id";
		return query(sql, new Object[]{unitId, typeId}, new MultiRow());
	}
	
	@Override
	public List<OfficeGoodsType> getOfficeGoodsTypeByTypeId(String unitId, String[] typeIds){
		String sql = "select * from office_goods_type where unit_id = ? and type_id in";
		return queryForInSQL(sql, new Object[]{unitId}, typeIds, new MultiRow(), " order by type_id");
	}
}
	