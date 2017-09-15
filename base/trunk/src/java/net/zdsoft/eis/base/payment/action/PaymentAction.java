/* 
 * @(#)PaymentAction.java    Created on 2013-10-30
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.payment.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.zdsoft.eis.base.payment.AlipayParam;
import net.zdsoft.eis.base.payment.PaymentResultEnum;
import net.zdsoft.eis.base.payment.StatusEnum;
import net.zdsoft.eis.base.payment.PaymentValidateException;
import net.zdsoft.eis.base.payment.entity.PayLog;
import net.zdsoft.eis.base.payment.entity.PayOrder;
import net.zdsoft.eis.base.payment.entity.PayTrade;
import net.zdsoft.eis.base.payment.entity.PayWare;
import net.zdsoft.eis.base.payment.service.PayLogService;
import net.zdsoft.eis.base.payment.service.PayTradeService;
import net.zdsoft.eis.base.payment.service.PaymentService;
import net.zdsoft.eis.base.payment.util.AlipayNotify;
import net.zdsoft.eis.base.payment.util.AlipaySubmit;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.keel.util.Validators;


/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-10-30 下午04:11:53 $
 */
public abstract class PaymentAction extends BaseAction {
	private static final long serialVersionUID = -2464372688531607600L;

	@Resource
	private PaymentService paymentService;

	@Resource
	private PayTradeService payTradeService;

	@Resource
	private PayLogService payLogService;

	private PayOrder order;
	private PayTrade trade;
	private int result;
	private String resultStr;// 结果字符串
	private String formText;

	public abstract String getLoginUserId();

	public abstract PayWare getWare(String wareId, String wareType);

	public abstract void validateWare(PayWare ware) throws PaymentValidateException;

	public abstract String getUrlNamespace();

	/**
	 * 立即购买 传递参数：buyerId、wareId、wareType、quantity、price
	 */
	public String buyNow() {
		Date now = new Date();

		// 获取商品
		PayWare ware = getWare(order.getWareId(), order.getWareType());

		try {
			if (ware == null) {
				throw new PaymentValidateException(PaymentResultEnum.WARE_OUT_SALE);
			}

			// 验证商品有效性
			validateWare(ware);

			int wareQuantity = order.getQuantity();
			if (wareQuantity > 99 || wareQuantity <= 0) {// 限制上品的最大购买数量为1~99件
				throw new PaymentValidateException(PaymentResultEnum.WARE_OUT_COUNT);
			}

		} catch (PaymentValidateException e) {
			if (e.getResult() == null) {
				resultStr = e.getMessage();
			} else {
				result = e.getResult().getValue();
				resultStr = e.getResult().getNameValue();
			}
			trade = new PayTrade();
			trade.setTradeNo("");
			trade.setTotalFee(0);
			trade.setOrderTime(now);
			return INPUT;
		}

		if (order.getPrice() == 0.0) {
			order.setPrice(ware.getPrice());
		}
		order.setSubject(ware.getSubject());
		order.setBody(ware.getBody());
		order.setId(UUIDUtils.newId());
		order.setCreationTime(now);
		order.setPaymentTime(null);
		order.setRemark(null);
		order.setStatus(StatusEnum.UN_ORDER.getValue());
		trade = paymentService.addOrderAndTrade(order);

		return SUCCESS;

	}

	/**
	 * 订单付款
	 * 
	 * @throws Exception
	 */
	public String checkOutUnpayOrder() throws Exception {
		Date now = new Date();// 获取系统当前时间

		try {
			trade = payTradeService.getTrade(trade.getId());
			if (trade == null) {
				throw new PaymentValidateException(PaymentResultEnum.ORDER_INVALID);
			}

			if (trade.getStatus() == StatusEnum.PAYED.getValue()) {
				throw new PaymentValidateException(PaymentResultEnum.ORDER_PAYED);
			}

			if (!(paymentService.existsOrders(trade.getId()))) {
				throw new PaymentValidateException(PaymentResultEnum.ORDER_DELETEDY);
			}

		} catch (PaymentValidateException e) {
			if (e.getResult() == null) {
				resultStr = e.getMessage();
			} else {
				result = e.getResult().getValue();
				resultStr = e.getResult().getNameValue();
			}
			if (trade == null) {
				trade = new PayTrade();
				trade.setTradeNo("");
				trade.setTotalFee(0);
				trade.setOrderTime(now);
			}
			return INPUT;
		}

		return SUCCESS;
	}

	public String payForAlipay() throws Exception {
		String userId = getLoginUserId();

		trade = payTradeService.getTrade(trade.getId());

		try {
			if (trade == null) {
				throw new PaymentValidateException(PaymentResultEnum.ORDER_INVALID);
			}

			if (!userId.equals(trade.getBuyerId())) {
				throw new PaymentValidateException(PaymentResultEnum.NO_SELF_LOGIN);// 不是本人登录，不能支付
			}

			// 总金额须大于0
			double totalFee = trade.getTotalFee();
			if (totalFee <= 0) {
				throw new PaymentValidateException(PaymentResultEnum.WARE_TOTAL_FEE);
			}
		} catch (PaymentValidateException e) {
			if (e.getResult() == null) {
				resultStr = e.getMessage();
			} else {
				result = e.getResult().getValue();
				resultStr = e.getResult().getNameValue();
			}
			if (trade == null) {
				trade = new PayTrade();
				trade.setTradeNo("");
				trade.setTotalFee(0);
				trade.setOrderTime(new Date());
			}
			return INPUT;
		}

		AlipayParam alipayParam = new AlipayParam();
		alipayParam.setUrlNamespace(getUrlNamespace());
		alipayParam.setSubject(trade.getSubject());
		alipayParam.setBody(trade.getBody());
		alipayParam.setOutTradeNo(trade.getTradeNo());
		alipayParam.setTotalFee(Double.toString(trade.getTotalFee()));

		// 建立请求
		String formText = AlipaySubmit.buildRequest(alipayParam, "get", "确认");
		log.debug(formText);

		return SUCCESS;
	}

	public String alipayReturn() throws Exception {
		PaymentResultEnum returnEnum = null;

		HttpServletRequest request = getRequest();
		String outTradeNo = request.getParameter("out_trade_no");
		Map<String, String> params = getParams(request);

		// 记录支付宝回调的结果日志
		PayLog log = new PayLog();
		log.setId(UUIDUtils.newId());
		log.setTradeNo(outTradeNo);
		log.setLogContent(params.toString());
		log.setCreationTime(new Date());
		payLogService.addLog(log);

		// 数据完整性校验
		try {
			AlipayNotify.verify(params);
		} catch (PaymentValidateException e) {
			returnEnum = e.getResult();
		}

		String trade_status = request.getParameter("trade_status"); // 交易状态
		boolean statusFinish = trade_status.equals("TRADE_FINISHED")
				|| trade_status.equals("TRADE_SUCCESS");
		if (statusFinish) {
			// 查询订单是否有效
			trade = payTradeService.getTradeByNo(outTradeNo);
			if (trade != null) {
				String totalFee = request.getParameter("total_fee");
				if (StatusEnum.PAYED.getValue() != trade.getStatus()) {
					double totalAmount = Double.parseDouble(totalFee);
					if (totalAmount == trade.getTotalFee()) {
						String tradeNo = request.getParameter("trade_no");
						tradeNo = Validators.isBlank(tradeNo) ? "" : tradeNo;
						doWithPaySuccess(trade.getId());
						// 付款成功
						returnEnum = PaymentResultEnum.ORDER_PAY_SUCCESS;
					} else {
						// 订单金额不匹配，前后不一致。
						returnEnum = PaymentResultEnum.ORDER_AMOUNT_UNMATCH;
					}
				} else {
					// 订单已结算
					returnEnum = PaymentResultEnum.ORDER_PAYED;
				}
			} else {
				// 无效的订单
				returnEnum = PaymentResultEnum.ORDER_INVALID;
			}
		} else {
			// 支付宝返回交易状态为未处理或者处理未成功。
			returnEnum = PaymentResultEnum.ALIPAY_TRADE_TIMEOUT;
		}

		result = returnEnum.getValue();
		resultStr = returnEnum.getNameValue();

		return SUCCESS;

	}

	public String alipayNotify() throws Exception {
		alipayReturn();

		if (result == PaymentResultEnum.ORDER_PAY_SUCCESS.getValue()
				|| result == PaymentResultEnum.ORDER_PAYED.getValue()) {
			ServletUtils.print(getResponse(), "success");
		} else {
			ServletUtils.print(getResponse(), "fail");
		}
		return NONE;

	}

	// 支付成功后需要处理的事情
	private void doWithPaySuccess(String tradeId) {
		// 更新外部订单的状态和付款时间及所属的商品订单的状态和付款时间
		paymentService.updateOrderAndTrade(tradeId);
	}

	private Map<String, String> getParams(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		// 获得POST 过来参数设置到新的PARAMS中
		@SuppressWarnings("unchecked")
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; ++i) {
				valueStr += (i == values.length - 1) ? values[i] : values[i] + ",";
			}
			params.put(name, valueStr);
		}
		return params;
	}

	public PayOrder getOrder() {
		return order;
	}

	public void setOrder(PayOrder order) {
		this.order = order;
	}

	public PayTrade getTrade() {
		return trade;
	}

	public void setTrade(PayTrade trade) {
		this.trade = trade;
	}

	public int getResult() {
		return result;
	}

	public String getResultStr() {
		return resultStr;
	}

	public String getFormText() {
		return formText;
	}

}
