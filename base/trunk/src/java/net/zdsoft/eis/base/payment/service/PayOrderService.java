/* 
 * @(#)PayOrderService.java    Created on 2013-10-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.payment.entity.PayOrder;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午05:43:21 $
 */
public interface PayOrderService {
	public void addOrder(PayOrder order);

	public void deleteOrders(String[] orderIds);

	public void updateOrder(PayOrder order);

	public PayOrder getOrder(String orderId);

	public Map<String, PayOrder> getOrderMap(String[] orderIds);

	public List<PayOrder> getOrders();
}
