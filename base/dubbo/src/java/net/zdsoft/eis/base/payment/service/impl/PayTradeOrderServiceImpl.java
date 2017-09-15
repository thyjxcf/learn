/* 
 * @(#)PayTradeOrderServiceImpl.java    Created on 2013-10-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.zdsoft.eis.base.payment.dao.PayTradeOrderDao;
import net.zdsoft.eis.base.payment.entity.PayTradeOrder;
import net.zdsoft.eis.base.payment.service.PayTradeOrderService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午05:55:02 $
 */
@Service
public class PayTradeOrderServiceImpl implements PayTradeOrderService {
	@Resource
	private PayTradeOrderDao payTradeOrderDao;

	public void addTradeOrder(PayTradeOrder tradeOrder) {
		payTradeOrderDao.addTradeOrder(tradeOrder);
	}

	@Override
	public void deleteTradeOrders(String[] tradeOrderIds) {
		payTradeOrderDao.deleteTradeOrders(tradeOrderIds);
	}

	@Override
	public PayTradeOrder getTradeOrder(String tradeOrderId) {
		return payTradeOrderDao.getTradeOrder(tradeOrderId);
	}

	@Override
	public Map<String, PayTradeOrder> getTradeOrderMap(String[] tradeOrderIds) {
		return payTradeOrderDao.getTradeOrderMap(tradeOrderIds);
	}

	@Override
	public List<String> getOrderIds(String tradeId) {
		return payTradeOrderDao.getOrderIds(tradeId);
	}

}
