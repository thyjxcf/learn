package net.zdsoft.office.goodmanage.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.goodmanage.dao.OfficeGoodsTypeAuthDao;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsTypeAuth;
/**
 * office_goods_type_auth
 * @author 
 * 
 */
public class OfficeGoodsTypeAuthDaoImpl extends BaseDao<OfficeGoodsTypeAuth> implements OfficeGoodsTypeAuthDao{

	@Override
	public OfficeGoodsTypeAuth setField(ResultSet rs) throws SQLException{
		OfficeGoodsTypeAuth officeGoodsTypeAuth = new OfficeGoodsTypeAuth();
		officeGoodsTypeAuth.setId(rs.getString("id"));
		officeGoodsTypeAuth.setUnitId(rs.getString("unit_id"));
		officeGoodsTypeAuth.setUserId(rs.getString("user_id"));
		officeGoodsTypeAuth.setTypeId(rs.getString("type_id"));
		officeGoodsTypeAuth.setCreationTime(rs.getTimestamp("creation_time"));
		return officeGoodsTypeAuth;
	}

	@Override
	public OfficeGoodsTypeAuth save(OfficeGoodsTypeAuth officeGoodsTypeAuth){
		String sql = "insert into office_goods_type_auth(id, unit_id, user_id, type_id, creation_time) values(?,?,?,?,?)";
		if (StringUtils.isBlank(officeGoodsTypeAuth.getId())){
			officeGoodsTypeAuth.setId(createId());
		}
		Object[] args = new Object[]{
			officeGoodsTypeAuth.getId(), officeGoodsTypeAuth.getUnitId(), 
			officeGoodsTypeAuth.getUserId(), officeGoodsTypeAuth.getTypeId(), 
			officeGoodsTypeAuth.getCreationTime()
		};
		update(sql, args);
		return officeGoodsTypeAuth;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_goods_type_auth where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeGoodsTypeAuth officeGoodsTypeAuth){
		String sql = "update office_goods_type_auth set unit_id = ?, user_id = ?, type_id = ?, creation_time = ? where id = ?";
		Object[] args = new Object[]{
			officeGoodsTypeAuth.getUnitId(), officeGoodsTypeAuth.getUserId(), 
			officeGoodsTypeAuth.getTypeId(), officeGoodsTypeAuth.getCreationTime(), 
			officeGoodsTypeAuth.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeGoodsTypeAuth getOfficeGoodsTypeAuthById(String id){
		String sql = "select * from office_goods_type_auth where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthMapByIds(String[] ids){
		String sql = "select * from office_goods_type_auth where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthList(){
		String sql = "select * from office_goods_type_auth";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthPage(Pagination page){
		String sql = "select * from office_goods_type_auth";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByUnitIdList(String unitId){
		String sql = "select * from office_goods_type_auth where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_goods_type_auth where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByConditions(String unitId, String searchName, Pagination page){
		List<Object> args = new ArrayList<Object>();
		String sql = "select * from office_goods_type_auth where unit_id = ?";
		args.add(unitId);
		if(StringUtils.isNotBlank(searchName)){
			sql += " and user_id in (select us.id from base_user us where us.username = ? or us.real_name = ?)";
			args.add(searchName);
			args.add(searchName);
		}
		sql += " order by creation_time desc";
		if(page != null)
			return query(sql, args.toArray(), new MultiRow(), page);
		else
			return query(sql, args.toArray(), new MultiRow());
	}
	
	@Override
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByUserId(String unitId, String userId){
		String sql = "select * from office_goods_type_auth where unit_id = ? and user_id = ?";
		return query(sql, new Object[]{unitId, userId}, new MultiRow());
	}
	
	@Override
	public void batchUpdate(List<OfficeGoodsTypeAuth> authlist){
		List<Object[]> objList = new ArrayList<Object[]>();
		for(OfficeGoodsTypeAuth auth : authlist){
			Object[] obj = new Object[]{
				auth.getUnitId(), auth.getUserId(), 
				auth.getTypeId(), auth.getCreationTime(), 
				auth.getId() };
			objList.add(obj);
		}
		String sql = "update office_goods_type_auth set unit_id = ?, user_id = ?, type_id = ?, creation_time = ? where id = ?";
		batchUpdate(sql, objList, new int[] { Types.CHAR, Types.CHAR,
				Types.VARCHAR, Types.DATE, Types.CHAR });
	}

	@Override
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthList(String[] userIds) {
		String sql = "select * from office_goods_type_auth where user_id in";
		return queryForInSQL(sql, null, userIds, new MultiRow());
	}
}
	