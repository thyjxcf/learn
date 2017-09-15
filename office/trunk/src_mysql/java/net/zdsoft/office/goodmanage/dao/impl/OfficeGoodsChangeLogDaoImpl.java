package net.zdsoft.office.goodmanage.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.goodmanage.entity.OfficeGoodsChangeLog;
import net.zdsoft.office.goodmanage.dao.OfficeGoodsChangeLogDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods_change_log
 * @author 
 * 
 */
public class OfficeGoodsChangeLogDaoImpl extends BaseDao<OfficeGoodsChangeLog> implements OfficeGoodsChangeLogDao{

	@Override
	public OfficeGoodsChangeLog setField(ResultSet rs) throws SQLException{
		OfficeGoodsChangeLog officeGoodsChangeLog = new OfficeGoodsChangeLog();
		officeGoodsChangeLog.setId(rs.getString("id"));
		officeGoodsChangeLog.setAddUserId(rs.getString("add_user_id"));
		officeGoodsChangeLog.setGoodsId(rs.getString("goods_id"));
		officeGoodsChangeLog.setReason(rs.getString("reason"));
		officeGoodsChangeLog.setAmount(rs.getInt("amount"));
		officeGoodsChangeLog.setRemark(rs.getString("remark"));
		officeGoodsChangeLog.setCreationTime(rs.getTimestamp("creation_time"));
		return officeGoodsChangeLog;
	}

	@Override
	public OfficeGoodsChangeLog save(OfficeGoodsChangeLog officeGoodsChangeLog){
		String sql = "insert into office_goods_change_log(id, add_user_id, goods_id, reason, amount, remark, creation_time) values(?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeGoodsChangeLog.getId())){
			officeGoodsChangeLog.setId(createId());
		}
		Object[] args = new Object[]{
			officeGoodsChangeLog.getId(), officeGoodsChangeLog.getAddUserId(), 
			officeGoodsChangeLog.getGoodsId(), officeGoodsChangeLog.getReason(), 
			officeGoodsChangeLog.getAmount(), officeGoodsChangeLog.getRemark(), 
			officeGoodsChangeLog.getCreationTime()
		};
		update(sql, args);
		return officeGoodsChangeLog;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_goods_change_log where id in";
		return updateForInSQL(sql, null, ids);
	}
	
	@Override
	public Integer deleteByGoodsIds(String[] goodsIds){
		String sql = "delete from office_goods_change_log where goods_id in";
		return updateForInSQL(sql, null, goodsIds);
	}

	@Override
	public Integer update(OfficeGoodsChangeLog officeGoodsChangeLog){
		String sql = "update office_goods_change_log set add_user_id = ?, goods_id = ?, reason = ?, amount = ?, remark = ?, creation_time = ? where id = ?";
		Object[] args = new Object[]{
			officeGoodsChangeLog.getAddUserId(), officeGoodsChangeLog.getGoodsId(), 
			officeGoodsChangeLog.getReason(), officeGoodsChangeLog.getAmount(), 
			officeGoodsChangeLog.getRemark(), officeGoodsChangeLog.getCreationTime(), 
			officeGoodsChangeLog.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeGoodsChangeLog getOfficeGoodsChangeLogById(String id){
		String sql = "select * from office_goods_change_log where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeGoodsChangeLog> getOfficeGoodsChangeLogMapByIds(String[] ids){
		String sql = "select * from office_goods_change_log where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeGoodsChangeLog> getOfficeGoodsChangeLogList(){
		String sql = "select * from office_goods_change_log";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeGoodsChangeLog> getOfficeGoodsChangeLogPage(Pagination page){
		String sql = "select * from office_goods_change_log";
		return query(sql, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeGoodsChangeLog> getOfficeGoodsChangeLogByGoodsId(String goodsId, Pagination page){
		String sql = "select * from office_goods_change_log where goods_id = ? order by creation_time desc";
		if(page != null)
			return query(sql, new Object[]{goodsId}, new MultiRow(), page);
		else
			return query(sql, new Object[]{goodsId}, new MultiRow());
	}
}
	