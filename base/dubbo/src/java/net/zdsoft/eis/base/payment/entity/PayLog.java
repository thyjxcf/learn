package net.zdsoft.eis.base.payment.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * 支付宝回调日志
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午04:22:47 $
 */
public class PayLog extends BaseEntity {

	private static final long serialVersionUID = 8015169948769491417L;
	
	/**
	 * 支付宝的外部交易号
	 */
	private String tradeNo;

	/**
	 * 日志内容
	 */
	private String logContent;

	/**
	 * @return 支付宝的外部交易号
	 */
	public String getTradeNo() {
		return tradeNo;
	}

	/**
	 * @param 支付宝的外部交易号
	 */
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	/**
	 * @return 日志内容
	 */
	public String getLogContent() {
		return logContent;
	}

	/**
	 * @param 日志内容
	 */
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}


}
