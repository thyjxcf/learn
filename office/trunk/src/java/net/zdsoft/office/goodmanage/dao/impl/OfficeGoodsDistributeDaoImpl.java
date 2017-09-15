package net.zdsoft.office.goodmanage.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.goodmanage.entity.OfficeGoodsDistribute;
import net.zdsoft.office.goodmanage.dao.OfficeGoodsDistributeDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
/**
 * office_goods_distribute
 * @author 
 * 
 */
public class OfficeGoodsDistributeDaoImpl extends BaseDao<OfficeGoodsDistribute> implements OfficeGoodsDistributeDao{

	@Override
	public OfficeGoodsDistribute setField(ResultSet rs) throws SQLException{
		OfficeGoodsDistribute officeGoodsDistribute = new OfficeGoodsDistribute();
		officeGoodsDistribute.setId(rs.getString("id"));
		officeGoodsDistribute.setUnitId(rs.getString("unit_id"));
		officeGoodsDistribute.setAddUserId(rs.getString("add_user_id"));
		officeGoodsDistribute.setName(rs.getString("name"));
		officeGoodsDistribute.setModel(rs.getString("model"));
		officeGoodsDistribute.setPrice(rs.getFloat("price"));
		officeGoodsDistribute.setGoodsUnit(rs.getString("goods_unit"));
		officeGoodsDistribute.setType(rs.getString("type"));
		officeGoodsDistribute.setPurchaseTime(rs.getTimestamp("purchase_time"));
		officeGoodsDistribute.setGoodsRemark(rs.getString("goods_remark"));
		officeGoodsDistribute.setAmount(rs.getInt("amount"));
		officeGoodsDistribute.setReceiverId(rs.getString("receiver_id"));
		officeGoodsDistribute.setDistributeRemark(rs.getString("distribute_remark"));
		officeGoodsDistribute.setDistributeTime(rs.getTimestamp("distribute_time"));
		return officeGoodsDistribute;
	}

	@Override
	public OfficeGoodsDistribute save(OfficeGoodsDistribute officeGoodsDistribute){
		String sql = "insert into office_goods_distribute(id, unit_id, add_user_id, name, model, price, goods_unit, type, purchase_time, goods_remark, amount, receiver_id, distribute_remark, distribute_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeGoodsDistribute.getId())){
			officeGoodsDistribute.setId(createId());
		}
		Object[] args = new Object[]{
			officeGoodsDistribute.getId(), officeGoodsDistribute.getUnitId(), 
			officeGoodsDistribute.getAddUserId(), officeGoodsDistribute.getName(), 
			officeGoodsDistribute.getModel(), officeGoodsDistribute.getPrice(), 
			officeGoodsDistribute.getGoodsUnit(), officeGoodsDistribute.getType(), 
			officeGoodsDistribute.getPurchaseTime(), officeGoodsDistribute.getGoodsRemark(), 
			officeGoodsDistribute.getAmount(), officeGoodsDistribute.getReceiverId(), 
			officeGoodsDistribute.getDistributeRemark(), officeGoodsDistribute.getDistributeTime()
		};
		update(sql, args);
		return officeGoodsDistribute;
	}

	@Override
	public void batchInsertGoodsDistribute(List<OfficeGoodsDistribute> goodsDistributeList){
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for(OfficeGoodsDistribute officeGoodsDistribute : goodsDistributeList){
			listOfArgs.add(new Object[] { 
				createId(), officeGoodsDistribute.getUnitId(), 
				officeGoodsDistribute.getAddUserId(), officeGoodsDistribute.getName(), 
				officeGoodsDistribute.getModel(), officeGoodsDistribute.getPrice(), 
				officeGoodsDistribute.getGoodsUnit(), officeGoodsDistribute.getType(), 
				officeGoodsDistribute.getPurchaseTime(), officeGoodsDistribute.getGoodsRemark(), 
				officeGoodsDistribute.getAmount(), officeGoodsDistribute.getReceiverId(), 
				officeGoodsDistribute.getDistributeRemark(), officeGoodsDistribute.getDistributeTime()
			});
		}
		int[] argTypes = new int[] { 
				Types.CHAR, Types.CHAR, Types.CHAR, Types.CHAR, 
				Types.CHAR, Types.FLOAT, Types.CHAR, Types.CHAR,
				Types.TIMESTAMP, Types.CHAR, Types.INTEGER, Types.CHAR,
				Types.CHAR, Types.TIMESTAMP
		};
		String sql = "insert into office_goods_distribute(id, unit_id, add_user_id, name, model, price, goods_unit, type, purchase_time, goods_remark, amount, receiver_id, distribute_remark, distribute_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		batchUpdate(sql, listOfArgs, argTypes);
	}
	
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_goods_distribute where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeGoodsDistribute officeGoodsDistribute){
		String sql = "update office_goods_distribute set unit_id = ?, add_user_id = ?, name = ?, model = ?, price = ?, goods_unit = ?, type = ?, purchase_time = ?, goods_remark = ?, amount = ?, receiver_id = ?, distribute_remark = ?, distribute_time = ? where id = ?";
		Object[] args = new Object[]{
			officeGoodsDistribute.getUnitId(), officeGoodsDistribute.getAddUserId(), 
			officeGoodsDistribute.getName(), officeGoodsDistribute.getModel(), 
			officeGoodsDistribute.getPrice(), officeGoodsDistribute.getGoodsUnit(), 
			officeGoodsDistribute.getType(), officeGoodsDistribute.getPurchaseTime(), 
			officeGoodsDistribute.getGoodsRemark(), officeGoodsDistribute.getAmount(), 
			officeGoodsDistribute.getReceiverId(), officeGoodsDistribute.getDistributeRemark(), 
			officeGoodsDistribute.getDistributeTime(), officeGoodsDistribute.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeGoodsDistribute getOfficeGoodsDistributeById(String id){
		String sql = "select * from office_goods_distribute where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeGoodsDistribute> getOfficeGoodsDistributeMapByIds(String[] ids){
		String sql = "select * from office_goods_distribute where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributeList(){
		String sql = "select * from office_goods_distribute";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributePage(Pagination page){
		String sql = "select * from office_goods_distribute";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributeByUnitIdList(String unitId){
		String sql = "select * from office_goods_distribute where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributeByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_goods_distribute where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributeListByConditions(String unitId, String[] goodsTypes, 
			String goodsName, String[] receiverIds, Pagination page){
		StringBuffer sql = new StringBuffer("select * from office_goods_distribute where 1=1 ");
		List<Object> objs = new ArrayList<Object>();
		
		if(StringUtils.isNotBlank(unitId)){
			sql.append(" and unit_id = ?");
			objs.add(unitId);
		}
		if(goodsTypes != null){
			sql.append(" and type in ").append(SQLUtils.toSQLInString(goodsTypes));
		}
		if(StringUtils.isNotBlank(goodsName)){
			sql.append(" and name like '%" + goodsName + "%'");
		}
		if(receiverIds != null){
			sql.append(" and receiver_id in ").append(SQLUtils.toSQLInString(receiverIds));
		}
		sql.append(" order by purchase_time desc, name asc");
		if(page != null){
			return query(sql.toString(), objs.toArray(), new MultiRow(), page);
		}else{
			return query(sql.toString(), objs.toArray(), new MultiRow());
		}
	}
	
	@Override
	public List<OfficeGoodsDistribute> getGoodsDistributeByType(String unitId, String[] typeIds){
		String sql = "select * from office_goods_distribute where unit_id = ? and type in";
		return queryForInSQL(sql, new Object[]{unitId}, typeIds, new MultiRow());
	}
}
