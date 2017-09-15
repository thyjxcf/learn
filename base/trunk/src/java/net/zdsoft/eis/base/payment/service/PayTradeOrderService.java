/* 
 * @(#)PayTradeOrderService.java    Created on 2013-10-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.payment.entity.PayTradeOrder;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午05:54:52 $
 */
public interface PayTradeOrderService {
	public void addTradeOrder(PayTradeOrder tradeOrder);

	public void deleteTradeOrders(String[] tradeOrderIds);

	public PayTradeOrder getTradeOrder(String tradeOrderId);

	public Map<String, PayTradeOrder> getTradeOrderMap(String[] tradeOrderIds);

	public List<String> getOrderIds(String tradeId);
}
