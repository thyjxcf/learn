/* 
 * @(#)PayTradeDaoImpl.java    Created on 2013-10-30
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

import net.zdsoft.eis.base.payment.dao.PayTradeDao;
import net.zdsoft.eis.base.payment.entity.PayTrade;
import net.zdsoft.eis.frame.client.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午05:49:09 $
 */
@Repository
public class PayTradeDaoImpl extends BaseDao<PayTrade> implements PayTradeDao {

	private static final String SQL_INSERT_PAYTRADE = "INSERT INTO sys_pay_trade(id,trade_no,subject,body,buyer_id,"
			+ "total_fee,order_time,payment_time,status) " + "VALUES(?,?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_PAYTRADE_BY_IDS = "DELETE FROM sys_pay_trade WHERE id IN ";

	private static final String SQL_UPDATE_PAYTRADE = "UPDATE sys_pay_trade SET status=?,payment_time=? WHERE id=?";

	private static final String SQL_FIND_PAYTRADE_BY_ID = "SELECT * FROM sys_pay_trade WHERE id=?";

	private static final String SQL_FIND_PAYTRADE_BY_NO = "SELECT * FROM sys_pay_trade WHERE trade_no=?";

	private static final String SQL_FIND_PAYTRADES_BY_IDS = "SELECT * FROM sys_pay_trade WHERE id IN";

	private static final String SQL_FIND_PAYTRADES = "SELECT * FROM sys_pay_trade ";

	public PayTrade setField(ResultSet rs) throws SQLException {
		PayTrade payTrade = new PayTrade();
		payTrade.setId(rs.getString("id"));
		payTrade.setTradeNo(rs.getString("trade_no"));
		payTrade.setSubject(rs.getString("subject"));
		payTrade.setBody(rs.getString("body"));
		payTrade.setBuyerId(rs.getString("buyer_id"));
		payTrade.setTotalFee(rs.getDouble("total_fee"));
		payTrade.setOrderTime(rs.getTimestamp("order_time"));
		payTrade.setPaymentTime(rs.getTimestamp("payment_time"));
		payTrade.setStatus(rs.getInt("status"));
		return payTrade;
	}

	@Override
	public void addTrade(PayTrade trade) {
		if (StringUtils.isEmpty(trade.getId())) {
			trade.setId(createId());
		}
		if (null == trade.getCreationTime()) {
			trade.setCreationTime(new Date());
		}
		update(SQL_INSERT_PAYTRADE,
				new Object[] { trade.getId(), trade.getTradeNo(), trade.getSubject(),
						trade.getBody(), trade.getBuyerId(), trade.getTotalFee(),
						trade.getOrderTime(), trade.getPaymentTime(), trade.getStatus() },
				new int[] { Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.CHAR,
						Types.DOUBLE, Types.DATE, Types.DATE, Types.INTEGER });
	}

	@Override
	public void deleteTrades(String[] tradeIds) {
		updateForInSQL(SQL_DELETE_PAYTRADE_BY_IDS, null, tradeIds);
	}

	@Override
	public void updateTrade(String tradeId, int status, Date paymentTime) {
		update(SQL_UPDATE_PAYTRADE, new Object[] { Integer.valueOf(status), paymentTime, tradeId },
				new int[] { Types.INTEGER, Types.DATE, Types.CHAR });
	}

	@Override
	public PayTrade getTrade(String tradeId) {
		return query(SQL_FIND_PAYTRADE_BY_ID, tradeId, new SingleRow());
	}

	@Override
	public PayTrade getTradeByNo(String tradeNo) {
		return query(SQL_FIND_PAYTRADE_BY_NO, tradeNo, new SingleRow());
	}

	@Override
	public Map<String, PayTrade> getTradeMap(String[] tradeIds) {
		return queryForInSQL(SQL_FIND_PAYTRADES_BY_IDS, null, tradeIds, new MapRow());
	}

	@Override
	public List<PayTrade> getTrades() {
		return query(SQL_FIND_PAYTRADES, null, null, new MultiRow());
	}

}
