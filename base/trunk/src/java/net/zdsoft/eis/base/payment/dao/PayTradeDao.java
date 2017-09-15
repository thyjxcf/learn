/* 
 * @(#)PayTradeDao.java    Created on 2013-10-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.payment.entity.PayTrade;

public interface PayTradeDao {

	public void addTrade(PayTrade trade);

	public void deleteTrades(String[] tradeIds);

	public void updateTrade(String tradeId, int status, Date paymentTime);

	public PayTrade getTrade(String tradeId);

	public PayTrade getTradeByNo(String tradeNo);

	public Map<String, PayTrade> getTradeMap(String[] tradeIds);

	public List<PayTrade> getTrades();

}