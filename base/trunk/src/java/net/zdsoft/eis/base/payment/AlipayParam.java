package net.zdsoft.eis.base.payment;

import java.io.Serializable;
import java.util.Date;

import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.keelcnet.config.ContainerManager;

import org.apache.struts2.ServletActionContext;

/**
 * 支付宝参数
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-11-4 上午09:48:51 $
 */
public class AlipayParam implements Serializable {

	private static final long serialVersionUID = 7673556940958580386L;

	/**
	 * 支付宝消息验证地址
	 */

	/* 固定值 */
	public static final String PAY_GETEWAY = "https://mapi.alipay.com/gateway.do";// 支付网关
	public static final String HTTPS_VERIFY_URL = PAY_GETEWAY + "?service=notify_verify";
	public static final String SERVICE = "create_direct_pay_by_user";// 接口名称
	public static final String SIGN_TYPE = "MD5";// 签名方式
	public static final String INPUT_CHARSET = "utf-8";// 编码
	private String paymentType = "1";// 支付方式(人民币)
	private String notifyUrl;// 通知接收URL
	private String returnUrl;// 支付完成后跳转返回的网址URL
	private String urlNamespace;// 通知和跳转的url中的namespace

	/* 配置参数 */
	private static String partner;// 支付宝合作伙伴id (账户内提取)
	private static String key;// 密钥
	private String sellerEmail;// 卖家支付宝帐号
	private String subjectPrefix;//商品名称前缀

	/* 动态值 */
	private String subject;// 商品名称
	private String body;// 商品描述
	private String outTradeNo;// 外部交易号(唯一)
	private String totalFee;// 商品总价
	private Date dealEndTime;

	private String defaultBank = "CMB";// 招行
	private String creditCardPay = "Y";// 信用卡支付
	private String creditCardDefaultDisplay = "Y";// 默认显示信用卡
	private String payMethod = "directPay";// bankPay(网银);creditCard(信用卡)directPay(支付宝直接支付);

	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * 支付宝付款成功后的通知地址
	 * 
	 * @return
	 */
	public String getNotifyUrl() {
		notifyUrl = ServletUtils.getWebsiteRoot(ServletActionContext.getRequest()) + urlNamespace
				+ "/alipayNotify.action";
		return notifyUrl;
	}

	/**
	 * 支付宝付款成功后的跳转地址
	 * 
	 * @return
	 */
	public String getReturnUrl() {
		returnUrl = ServletUtils.getWebsiteRoot(ServletActionContext.getRequest()) + urlNamespace
				+ "/alipayReturn.action";
		return returnUrl;
	}

	/**
	 * 支付宝合作伙伴id(账户内提取)
	 * 
	 * @return
	 */
	public static String getPartner() {
		partner = getParam("SYSTEM.ALIPAY.PARTNER");
		return partner;
	}

	/**
	 * 支付宝密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getKey() {
		key = getParam("SYSTEM.ALIPAY.KEY");
		if (Validators.isBlank(key)) {
			throw new RuntimeException("支付宝密钥不能为空！！！");
		}
		return key;
	}

	/**
	 * 卖家支付宝帐号
	 * 
	 * @return
	 */
	public String getSellerEmail() {
		sellerEmail = getParam("SYSTEM.ALIPAY.SELLER.EMAIL");
		return sellerEmail;
	}
	
	public String getSubjectPrefix() {
		subjectPrefix = getParam("SYSTEM.ALIPAY.SUBJECT.PREFIX");
		return subjectPrefix;
	}

	/**
	 * 得到支付宝接口参数
	 * 
	 * @param key
	 * @return 如果不存在，返回null
	 */
	private static String getParam(String key) {
		String value = "";
		if (Validators.isBlank(value)) {
			SystemIniService systemIniService = (SystemIniService) ContainerManager
					.getComponent("systemIniService");
			value = systemIniService.getValue(key);
		}
		return value;
	}

	/**
	 * 支付宝商品名称
	 * 
	 * @return
	 */
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public Date getDealEndTime() {
		return dealEndTime;
	}

	public void setDealEndTime(Date dealEndTime) {
		this.dealEndTime = dealEndTime;
	}

	public String getDefaultBank() {
		return defaultBank;
	}

	public void setDefaultBank(String defaultBank) {
		this.defaultBank = defaultBank;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getCreditCardPay() {
		return creditCardPay;
	}

	public String getCreditCardDefaultDisplay() {
		return creditCardDefaultDisplay;
	}

	public void setUrlNamespace(String urlNamespace) {
		this.urlNamespace = urlNamespace;
	}

}
