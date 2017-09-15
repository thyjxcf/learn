/* 
 * @(#)PayTradeService.java    Created on 2013-10-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.payment.entity.PayTrade;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午05:49:38 $
 */
public interface PayTradeService {
	public void trade(PayTrade payTrade);

	public void deleteTrades(String[] tradeIds);

	public PayTrade getTrade(String tradeId);

	public PayTrade getTradeByNo(String tradeNo);

	public Map<String, PayTrade> getTradeMap(String[] tradeIds);

	public List<PayTrade> getTrades();
}
