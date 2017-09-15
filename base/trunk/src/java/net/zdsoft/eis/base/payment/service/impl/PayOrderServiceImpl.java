/* 
 * @(#)PayOrderServiceImpl.java    Created on 2013-10-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.zdsoft.eis.base.payment.dao.PayOrderDao;
import net.zdsoft.eis.base.payment.entity.PayOrder;
import net.zdsoft.eis.base.payment.service.PayOrderService;

import org.springframework.stereotype.Service;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午05:39:50 $
 */
@Service
public class PayOrderServiceImpl implements PayOrderService {
	@Resource
	private PayOrderDao payOrderDao;

	@Override
	public void addOrder(PayOrder order) {
		payOrderDao.addOrder(order);
	}

	@Override
	public void deleteOrders(String[] orderIds) {
		payOrderDao.deleteOrders(orderIds);
	}

	@Override
	public void updateOrder(PayOrder order) {
		payOrderDao.updateOrder(order);
	}

	@Override
	public PayOrder getOrder(String orderId) {
		return payOrderDao.getOrder(orderId);
	}

	@Override
	public Map<String, PayOrder> getOrderMap(String[] orderIds) {
		return payOrderDao.getOrderMap(orderIds);
	}

	@Override
	public List<PayOrder> getOrders() {
		return payOrderDao.getOrders();
	}

}
