/* 
 * @(#)PayTradeOrderDao.java    Created on 2013-10-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.payment.entity.PayTradeOrder;

public interface PayTradeOrderDao {

	public void addTradeOrder(PayTradeOrder tradeOrder);

	public void deleteTradeOrders(String[] tradeOrderIds);

	public void updateTradeOrder(PayTradeOrder tradeOrder);

	public PayTradeOrder getTradeOrder(String tradeOrderId);

	public Map<String, PayTradeOrder> getTradeOrderMap(String[] tradeOrderIds);

	public List<String> getOrderIds(String tradeId);

}