/* 
 * @(#)PayOrderDaoImpl.java    Created on 2013-10-30
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

import net.zdsoft.eis.base.payment.dao.PayOrderDao;
import net.zdsoft.eis.base.payment.entity.PayOrder;
import net.zdsoft.eis.frame.client.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午05:39:23 $
 */
@Repository
public class PayOrderDaoImpl extends BaseDao<PayOrder> implements PayOrderDao {

	private static final String SQL_INSERT_PAYORDER = "INSERT INTO sys_pay_order(id,subject,body,"
			+ "buyer_id,ware_id,ware_type,price,quantity,creation_time,payment_time,"
			+ "status,remark) " + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_PAYORDER_BY_IDS = "DELETE FROM sys_pay_order WHERE id IN ";

	private static final String SQL_UPDATE_PAYORDER = "UPDATE sys_pay_order SET subject=?,body=?,"
			+ "buyer_id=?,ware_id=?,ware_type=?,price=?,quantity=?,creation_time=?,payment_time=?,"
			+ "status=?,remark=? WHERE id=?";

	/**
	 * 修改记录:订单状态
	 */
	private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE sys_pay_order SET status=? WHERE id IN ";
	private static final String SQL_UPDATE_ORDER_STATUS_PAYMENTTIME = "UPDATE sys_pay_order SET status=?,payment_time=? WHERE id IN ";

	private static final String SQL_FIND_PAYORDER_BY_ID = "SELECT * FROM sys_pay_order WHERE id=?";

	private static final String SQL_FIND_PAYORDERS_BY_IDS = "SELECT * FROM sys_pay_order WHERE id IN";

	private static final String SQL_FIND_PAYORDERS = "SELECT * FROM sys_pay_order ";

	@Override
	public PayOrder setField(ResultSet rs) throws SQLException {
		PayOrder order = new PayOrder();
		order.setId(rs.getString("id"));
		order.setSubject(rs.getString("subject"));
		order.setBody(rs.getString("body"));
		order.setBuyerId(rs.getString("buyer_id"));
		order.setWareId(rs.getString("ware_id"));
		order.setWareType(rs.getString("ware_type"));
		order.setPrice(rs.getDouble("price"));
		order.setQuantity(rs.getInt("quantity"));
		order.setCreationTime(rs.getTimestamp("creation_time"));
		order.setPaymentTime(rs.getTimestamp("payment_time"));
		order.setStatus(rs.getInt("status"));
		order.setRemark(rs.getString("remark"));
		return order;
	}

	@Override
	public void addOrder(PayOrder order) {
		if (StringUtils.isEmpty(order.getId())) {
			order.setId(createId());
		}
		if (null == order.getCreationTime()) {
			order.setCreationTime(new Date());
		}
		update(SQL_INSERT_PAYORDER,
				new Object[] { order.getId(), order.getSubject(), order.getBody(),
						order.getBuyerId(), order.getWareId(), order.getWareType(),
						order.getPrice(), order.getQuantity(), order.getCreationTime(),
						order.getPaymentTime(), order.getStatus(), order.getRemark() }, new int[] {
						Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.CHAR,
						Types.VARCHAR, Types.DOUBLE, Types.INTEGER, Types.DATE, Types.DATE,
						Types.INTEGER, Types.VARCHAR });
	}

	@Override
	public void deleteOrders(String[] orderIds) {
		updateForInSQL(SQL_DELETE_PAYORDER_BY_IDS, null, orderIds);
	}

	@Override
	public void updateOrder(PayOrder order) {
		update(SQL_UPDATE_PAYORDER,
				new Object[] { order.getSubject(), order.getBody(), order.getBuyerId(),
						order.getWareId(), order.getWareType(), order.getPrice(),
						order.getQuantity(), order.getCreationTime(), order.getPaymentTime(),
						order.getStatus(), order.getRemark(), order.getId() }, new int[] {
						Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.CHAR, Types.VARCHAR,
						Types.DOUBLE, Types.INTEGER, Types.DATE, Types.DATE, Types.INTEGER,
						Types.VARCHAR, Types.CHAR });
	}

	@Override
	public int updateOrderStatus(String[] orderIds, int status) {
		return updateForInSQL(SQL_UPDATE_ORDER_STATUS, new Object[] { Integer.valueOf(status) },
				orderIds);
	}

	@Override
	public int updateOrderStatus(String[] orderIds, int status, Date paymentTime) {
		return updateForInSQL(SQL_UPDATE_ORDER_STATUS_PAYMENTTIME,
				new Object[] { Integer.valueOf(status), paymentTime }, orderIds);
	}

	@Override
	public PayOrder getOrder(String orderId) {
		return query(SQL_FIND_PAYORDER_BY_ID, orderId, new SingleRow());
	}

	@Override
	public Map<String, PayOrder> getOrderMap(String[] orderIds) {
		return queryForInSQL(SQL_FIND_PAYORDERS_BY_IDS, null, orderIds, new MapRow());
	}

	@Override
	public List<PayOrder> getOrders() {
		return query(SQL_FIND_PAYORDERS, null, null, new MultiRow());
	}

}
