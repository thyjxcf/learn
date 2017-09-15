/* 
 * @(#)PaymentService.java    Created on 2013-10-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment.service;

import net.zdsoft.eis.base.payment.entity.PayOrder;
import net.zdsoft.eis.base.payment.entity.PayTrade;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午06:05:18 $
 */
public interface PaymentService {
	/**
	 * 增加订单和交易信息
	 * 
	 * @param order
	 * @return
	 */
	public PayTrade addOrderAndTrade(PayOrder order);

	public void updateOrderAndTrade(String tradeId);

	/**
	 * 交易下的订单是否都存在
	 * 
	 * @param tradeId
	 * @return
	 */
	public boolean existsOrders(String tradeId);
}
