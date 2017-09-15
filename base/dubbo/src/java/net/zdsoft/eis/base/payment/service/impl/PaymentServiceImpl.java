/* 
 * @(#)PaymentServiceImpl.java    Created on 2013-10-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.zdsoft.eis.base.payment.StatusEnum;
import net.zdsoft.eis.base.payment.dao.PayOrderDao;
import net.zdsoft.eis.base.payment.dao.PayTradeDao;
import net.zdsoft.eis.base.payment.dao.PayTradeOrderDao;
import net.zdsoft.eis.base.payment.entity.PayOrder;
import net.zdsoft.eis.base.payment.entity.PayTrade;
import net.zdsoft.eis.base.payment.entity.PayTradeOrder;
import net.zdsoft.eis.base.payment.service.PaymentService;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.keel.util.Validators;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午06:05:33 $
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	@Resource
	private PayTradeDao payTradeDao;
	@Resource
	private PayOrderDao payOrderDao;
	@Resource
	private PayTradeOrderDao payTradeOrderDao;

	@Override
	public PayTrade addOrderAndTrade(PayOrder order) {
		payOrderDao.addOrder(order);

		String tradeId = UUIDUtils.newId();

		// 生成支付宝的外部交易订单 */
		PayTrade trade = new PayTrade();
		trade.setId(tradeId);
		trade.setSubject(order.getSubject());
		trade.setBody(order.getBody());
		trade.setBuyerId(order.getBuyerId());
		trade.setOrderTime(order.getCreationTime());// 下单时间
		trade.setStatus(StatusEnum.UN_PAY.getValue());// 状态为未结算

		BigDecimal bg = new BigDecimal(order.getPrice() * order.getQuantity());
		double totalFee = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		trade.setTotalFee(totalFee);// 订单总金额

		// 订单号惟一性保证
		while (true) {
			try {
				String outTradeNo = getOrderNum();// 与支付宝外部交易号(唯一)
				trade.setTradeNo(outTradeNo);
				payTradeDao.addTrade(trade);
			} catch (DuplicateKeyException e) {
				continue;
			}
			break;
		}

		// 生成内部订单和支付宝外部交易号间的对应关系（订单和商品关联）
		PayTradeOrder tradeOrder = new PayTradeOrder();
		tradeOrder.setOrderType(order.getOrderType());
		tradeOrder.setOrderId(order.getId());
		tradeOrder.setTradeId(tradeId);
		payTradeOrderDao.addTradeOrder(tradeOrder);

		// 修改订单状态为未付款
		payOrderDao.updateOrderStatus(new String[] { order.getId() }, StatusEnum.UN_PAY.getValue());

		return trade;
	}

	/**
	 * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
	 * 
	 * @return 以yyyyMMddHHmmssSSS为格式的当前系统时间
	 */
	private static String getOrderNum() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return df.format(date);
	}

	@Override
	public void updateOrderAndTrade(String tradeId) {
		// 更新外部订单的状态及所属的商品订单的状态和付款时间
		Date now = new Date();// 取当前时间

		// 更改状态为已结算
		payTradeDao.updateTrade(tradeId, StatusEnum.PAYED.getValue(), now);

		List<String> orderIdLists = payTradeOrderDao.getOrderIds(tradeId);
		String[] orderIds = orderIdLists.toArray(new String[0]);
		if (!Validators.isEmpty(orderIds)) {
			payOrderDao.updateOrderStatus(orderIds, StatusEnum.PAYED.getValue(), now);
		}
	}

	@Override
	public boolean existsOrders(String tradeId) {
		List<String> orderIdLists = payTradeOrderDao.getOrderIds(tradeId);
		if (orderIdLists.size() == 0) {
			return false;
		} else {
			String[] orderIds = orderIdLists.toArray(new String[0]);
			Map<String, PayOrder> orderMap = payOrderDao.getOrderMap(orderIds);
			if (orderMap.size() == orderIds.length) {
				return true;
			} else {
				return false;
			}
		}

	}
}
