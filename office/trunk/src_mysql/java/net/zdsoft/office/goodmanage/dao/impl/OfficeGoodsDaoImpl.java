package net.zdsoft.office.goodmanage.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.goodmanage.entity.OfficeGoods;
import net.zdsoft.office.goodmanage.dao.OfficeGoodsDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
/**
 * office_goods
 * @author 
 * 
 */
public class OfficeGoodsDaoImpl extends BaseDao<OfficeGoods> implements OfficeGoodsDao{

	@Override
	public OfficeGoods setField(ResultSet rs) throws SQLException{
		OfficeGoods officeGoods = new OfficeGoods();
		officeGoods.setId(rs.getString("id"));
		officeGoods.setUnitId(rs.getString("unit_id"));
		officeGoods.setAddUserId(rs.getString("add_user_id"));
		officeGoods.setName(rs.getString("name"));
		officeGoods.setModel(rs.getString("model"));
		officeGoods.setRemark(rs.getString("remark"));
		officeGoods.setAmount(rs.getInt("amount"));
		officeGoods.setPrice(rs.getFloat("price"));
		officeGoods.setType(rs.getString("type"));
		officeGoods.setReqTag(rs.getInt("req_tag"));
		officeGoods.setCreationTime(rs.getTimestamp("creation_time"));
		officeGoods.setIsReturned(rs.getBoolean("is_returned"));
		officeGoods.setGoodsUnit(rs.getString("goods_unit"));
		officeGoods.setPurchaseDate(rs.getTimestamp("purchase_date"));
		return officeGoods;
	}

	@Override
	public OfficeGoods save(OfficeGoods officeGoods){
		String sql = "insert into office_goods(id, unit_id, add_user_id, name, model, remark, amount, price, type, req_tag, creation_time, is_returned, goods_unit, purchase_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeGoods.getId())){
			officeGoods.setId(createId());
		}
		Object[] args = new Object[]{
			officeGoods.getId(), officeGoods.getUnitId(), 
			officeGoods.getAddUserId(), officeGoods.getName(), 
			officeGoods.getModel(), officeGoods.getRemark(), 
			officeGoods.getAmount(), officeGoods.getPrice(), 
			officeGoods.getType(), officeGoods.getReqTag(), 
			officeGoods.getCreationTime(), officeGoods.getIsReturned(),
			officeGoods.getGoodsUnit(), officeGoods.getPurchaseDate()
		};
		update(sql, args);
		return officeGoods;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_goods where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeGoods officeGoods){
		String sql = "update office_goods set unit_id = ?, add_user_id = ?, name = ?, model = ?, remark = ?, amount = ?, price = ?, type = ?, req_tag = ?, creation_time = ?, is_returned = ?, goods_unit = ?, purchase_date = ? where id = ?";
		Object[] args = new Object[]{
			officeGoods.getUnitId(), officeGoods.getAddUserId(), 
			officeGoods.getName(), officeGoods.getModel(), 
			officeGoods.getRemark(), officeGoods.getAmount(), 
			officeGoods.getPrice(), officeGoods.getType(), 
			officeGoods.getReqTag(), officeGoods.getCreationTime(), 
			officeGoods.getIsReturned(), officeGoods.getGoodsUnit(),
			officeGoods.getPurchaseDate(), officeGoods.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeGoods getOfficeGoodsById(String id){
		String sql = "select * from office_goods where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeGoods> getOfficeGoodsMapByIds(String[] ids){
		String sql = "select * from office_goods where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeGoods> getOfficeGoodsList(){
		String sql = "select * from office_goods";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeGoods> getOfficeGoodsPage(Pagination page){
		String sql = "select * from office_goods";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeGoods> getOfficeGoodsByUnitIdList(String unitId){
		String sql = "select * from office_goods where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeGoods> getOfficeGoodsByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_goods where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeGoods> getOfficeGoodsByConditions(String unitId, String[] goodsTypes, String goodsName, Boolean isApply, Pagination page){
		List<Object> argsList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from office_goods where unit_id = ? ");
		argsList.add(unitId);
		if(goodsTypes != null){
			sql.append(" and type in ").append(
					SQLUtils.toSQLInString(goodsTypes));
		}
		if(StringUtils.isNotBlank(goodsName)){
			sql.append(" and name like '%" + goodsName + "%'");
		}
		if(isApply){
			sql.append(" and amount > 0");
		}
		sql.append(" order by creation_time desc, name asc");
		if(page != null)
			return query(sql.toString(), argsList.toArray(), new MultiRow(), page);
		else
			return query(sql.toString(), argsList.toArray(), new MultiRow());
	}
	
	@Override
	public List<OfficeGoods> getGoodsByType(String unitId, String[] typeIds){
		String sql = "select * from office_goods where unit_id = ? and type in";
		return queryForInSQL(sql, new Object[]{unitId}, typeIds, new MultiRow());
	}
	
	@Override
	public List<OfficeGoods> getGoodsByGoodsUnit(String unitId, String[] goodsUnitIds){
		String sql = "select * from office_goods where unit_id = ? and goods_unit_id in";
		return queryForInSQL(sql, new Object[]{unitId}, goodsUnitIds, new MultiRow());
	}
}
