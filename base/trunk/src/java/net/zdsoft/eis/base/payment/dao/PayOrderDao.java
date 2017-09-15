/* 
 * @(#)PayOrderDao.java    Created on 2013-10-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.payment.entity.PayOrder;

public interface PayOrderDao {

	public void addOrder(PayOrder order);

	public void deleteOrders(String[] orderIds);

	public void updateOrder(PayOrder order);

	/**
	 * 批量修改订单状态
	 * 
	 * @param orderIds
	 * @param status
	 * @return
	 */
	public int updateOrderStatus(String[] orderIds, int status);

	/**
	 * 批量修改订单状态和支付时间
	 * 
	 * @param orderIds
	 * @param status
	 * @return
	 */
	public int updateOrderStatus(String[] orderIds, int status, Date paymentTime);

	public PayOrder getOrder(String orderId);

	public Map<String, PayOrder> getOrderMap(String[] orderIds);

	public List<PayOrder> getOrders();

}