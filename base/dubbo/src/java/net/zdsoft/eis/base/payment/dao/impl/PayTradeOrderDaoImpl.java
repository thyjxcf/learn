/* 
 * @(#)PayTradeOrderDaoImpl.java    Created on 2013-10-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import net.zdsoft.eis.base.payment.dao.PayTradeOrderDao;
import net.zdsoft.eis.base.payment.entity.PayTradeOrder;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午05:54:35 $
 */
@Repository
public class PayTradeOrderDaoImpl extends BaseDao<PayTradeOrder> implements PayTradeOrderDao {

	private static final String SQL_INSERT_PAYTRADEORDER = "INSERT INTO sys_pay_trade_order(id,trade_id,order_id,"
			+ "order_type) " + "VALUES(?,?,?,?)";

	private static final String SQL_DELETE_PAYTRADEORDER_BY_IDS = "DELETE FROM sys_pay_trade_order WHERE id IN ";

	private static final String SQL_UPDATE_PAYTRADEORDER = "UPDATE sys_pay_trade_order SET trade_id=?,order_id=?,"
			+ "order_type=? WHERE id=?";

	private static final String SQL_FIND_PAYTRADEORDER_BY_ID = "SELECT * FROM sys_pay_trade_order WHERE id=?";

	private static final String SQL_FIND_PAYTRADEORDERS_BY_IDS = "SELECT * FROM sys_pay_trade_order WHERE id IN";

	private static final String SQL_FIND_PAYTRADEORDERS = "SELECT order_id FROM sys_pay_trade_order WHERE trade_id=?";

	public PayTradeOrder setField(ResultSet rs) throws SQLException {
		PayTradeOrder payTradeOrder = new PayTradeOrder();
		payTradeOrder.setId(rs.getString("id"));
		payTradeOrder.setTradeId(rs.getString("trade_id"));
		payTradeOrder.setOrderId(rs.getString("order_id"));
		payTradeOrder.setOrderType(rs.getInt("order_type"));
		return payTradeOrder;
	}

	@Override
	public void addTradeOrder(PayTradeOrder tradeOrder) {
		if (StringUtils.isEmpty(tradeOrder.getId())) {
			tradeOrder.setId(createId());
		}
		if (null == tradeOrder.getCreationTime()) {
			tradeOrder.setCreationTime(new Date());
		}

		update(SQL_INSERT_PAYTRADEORDER,
				new Object[] { tradeOrder.getId(), tradeOrder.getTradeId(),
						tradeOrder.getOrderId(), tradeOrder.getOrderType() }, new int[] {
						Types.CHAR, Types.CHAR, Types.CHAR, Types.INTEGER });
	}

	@Override
	public void deleteTradeOrders(String[] tradeOrderIds) {
		updateForInSQL(SQL_DELETE_PAYTRADEORDER_BY_IDS, null, tradeOrderIds);
	}

	@Override
	public void updateTradeOrder(PayTradeOrder tradeOrder) {
		update(SQL_UPDATE_PAYTRADEORDER,
				new Object[] { tradeOrder.getTradeId(), tradeOrder.getOrderId(),
						tradeOrder.getOrderType(), tradeOrder.getId() }, new int[] {
						Types.CHAR, Types.CHAR, Types.INTEGER, Types.CHAR });
	}

	@Override
	public PayTradeOrder getTradeOrder(String tradeOrderId) {
		return query(SQL_FIND_PAYTRADEORDER_BY_ID, tradeOrderId, new SingleRow());
	}

	@Override
	public Map<String, PayTradeOrder> getTradeOrderMap(String[] tradeOrderIds) {
		return queryForInSQL(SQL_FIND_PAYTRADEORDERS_BY_IDS, null, tradeOrderIds, new MapRow());
	}

	@Override
	public List<String> getOrderIds(String tradeId) {
		return query(SQL_FIND_PAYTRADEORDERS, tradeId, new MultiRowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("order_id");
			}
		});
	}

}
