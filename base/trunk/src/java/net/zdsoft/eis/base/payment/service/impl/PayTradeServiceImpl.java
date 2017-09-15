/* 
 * @(#)PayTradeServiceImpl.java    Created on 2013-10-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.zdsoft.eis.base.payment.dao.PayTradeDao;
import net.zdsoft.eis.base.payment.entity.PayTrade;
import net.zdsoft.eis.base.payment.service.PayTradeService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午05:50:17 $
 */
@Service
public class PayTradeServiceImpl implements PayTradeService {
	@Resource
	private PayTradeDao payTradeDao;

	@Override
	public void trade(PayTrade payTrade) {
		payTradeDao.addTrade(payTrade);
	}

	@Override
	public void deleteTrades(String[] tradeIds) {
		payTradeDao.deleteTrades(tradeIds);
	}

	@Override
	public PayTrade getTrade(String tradeId) {
		return payTradeDao.getTrade(tradeId);
	}

	@Override
	public PayTrade getTradeByNo(String tradeNo) {
		return payTradeDao.getTradeByNo(tradeNo);
	}

	@Override
	public Map<String, PayTrade> getTradeMap(String[] tradeIds) {
		return payTradeDao.getTradeMap(tradeIds);
	}

	@Override
	public List<PayTrade> getTrades() {
		return payTradeDao.getTrades();
	}
}
