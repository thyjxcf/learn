package net.zdsoft.eis.sms.core;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import net.zdsoft.background2.message.eschool.AccountInfo;
import net.zdsoft.background2.message.eschool.EtohSmsMessage;
import net.zdsoft.background2.message.eschool.PhoneInfo;
import net.zdsoft.eis.base.common.entity.SysOption;
import net.zdsoft.eis.base.common.service.SysOptionService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.dto.SendSmsMessageVO;
import net.zdsoft.eis.sms.service.SmsUseConfigService;
import net.zdsoft.eschool.smsclient.SmsClient;
import net.zdsoft.eschool.smsclient.SmsHandler;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.leadin.util.ConfigFileUtils;
import net.zdsoft.leadin.util.PWD;
import net.zdsoft.smsc.client.SmscClient;
import net.zdsoft.smsc.client.SmscException;
import net.zdsoft.smsc.client.SmscHandler;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsClientWrapper {
	private static final Logger log = LoggerFactory
			.getLogger(SmsClientWrapper.class);

	private SmsClient szxySmsClient;// 数字校园

	private SmscClient sdkSmsClient;// sdk

	private SmsHandler szxyHandler;// 数字校园回调接口，用于取得短信回执、回复信息

	private SmscHandler sdkHandler;// sdk回调接口，用于取得短信回执、回复信息

	private static String SERVER_IP; // 通信服务器主机

	private static int SERVER_PORT; // 端口

	private static String loginNamePlatform;

	private static String loginNameRegion;

	/**
	 * 充值和开通短信的客户id,由公司商务部提供
	 */
	private static String clientId;

	// 扩展业务的sp号码，如：100-999,循环增长,用于识别是哪个人发的短信,区别收短信的人回复短信用
	private static String spExtNumber = "100";

	private static String localName; // 登录名

	private static String localPwd; // 登录密码

	private boolean connected = false;

	private String connectedMessage = "";

	private SystemIniService systemIniService;// 获取短信发送使用方式

	private SysOptionService sysOptionService;

	private SmsUseConfigService smsUseConfigService;

	private String smsMode = "0";// 短信发送使用方式:1表示SDK平台,2表示数字校园模式,0不发送

	// 初始化，在第一次使用SmsClientWrapper类时，就做连接短信平台的操作
	public void init() {
		smsMode = systemIniService.getValue("SYSTEM.SMS.MODE");
		if (connected == false) {// 如果连接为空，则再次连接，否则直接使用

			// 规则：[登录用户名：eschool_web + 6位行政区划 + 3位随机数，密码与登录用户名相同]
			// localName = loginNamePlatform + loginNameRegion + getRandom();
			// log.debug(">>>>loginName:" + localName);
			try {
				log.debug("-----调试：初始化短信服务器，smsHandler[" + sdkHandler
						+ "], serverip[" + SERVER_IP + "], serverport["
						+ SERVER_PORT + "], localName[" + localName
						+ "], localName[" + localPwd + "]");
				// 判断短信通道连接方式
				if ("1".equals(smsMode)) {
					// sdk通道模式
					String filePath = ConfigFileUtils.getStoreConfigPath()
							+ File.separator + SmsConstant.SMS_CONFIG_FILE;
					Properties pro = FileUtils.readProperties(filePath);
					SERVER_IP = pro.getProperty(SmsConstant.SMS_SERVER);
					SERVER_PORT = Integer.parseInt(pro
							.getProperty(SmsConstant.SMS_PORT));
					localName = pro.getProperty(SmsConstant.SMS_LOCALNAME);
					localPwd = PWD.decode(pro
							.getProperty(SmsConstant.SMS_LOCALPWD));
					sdkSmsClient = SmscClient.newInstance(sdkHandler,
							SERVER_IP, SERVER_PORT, localName, localPwd);
				} else if ("2".equals(smsMode)) {
					// 数字校园通道模式
					SysOption sysOption = sysOptionService
							.getSysOption("SMS.PORT");
					if (StringUtils.isNotBlank(sysOption.getNowValue())) {
						SERVER_PORT = NumberUtils
								.toInt(sysOption.getNowValue());
					}
					SERVER_IP = systemIniService
							.getSystemIni("SZXY.SMS.SERVER").getNowValue();
					loginNamePlatform = systemIniService.getSystemIni(
							"SZXY.SMS.LOGINNAME.PLATFORM").getNowValue();
					loginNameRegion = systemIniService.getSystemIni(
							"SZXY.SMS.LOGINNAME.REGION").getNowValue();
					// 规则：[登录用户名：eschool_web_eis + 6位行政区划 +
					// 3位随机数，密码与登录用户名相同]
					localName = loginNamePlatform + loginNameRegion
							+ getRandom();
					localPwd = localName; // 登录密码，默认跟登录名相同

					szxySmsClient = SmsClient.newInstance(szxyHandler,
							SERVER_IP, SERVER_PORT, localName, localPwd);
					szxySmsClient.setSaveUpTimeOut(20000);
					szxySmsClient.setBlockedSaveUp(true);
				}
				connected = true;
				connectedMessage = SmsConstant.SMSSERVER_CONNECT_SUCCESS_MSG;
			} catch (RuntimeException e) {
				log.error(e.getMessage(), e);
				connected = false;
				if (e.getMessage() != null)
					connectedMessage = e.getMessage();
				else {
					connectedMessage = SmsConstant.SMSSERVER_CONNECT_ERROR_MSG;
				}
			}

		}
	}
	
	public void test(String serverIp,String serverPort,String localName,String localpwd){
		sdkSmsClient = SmscClient.newInstance(sdkHandler,
				serverIp, Integer.parseInt(serverPort), localName, localpwd);
	}
	
	public void reload(){
		this.connected=false;
		init();
	}

	/**
	 * 发送短信
	 */
	public void sendMsg(SendSmsMessageVO msg) throws Exception {
		if (szxySmsClient != null || sdkSmsClient != null) {

			List<PhoneInfo> mobiles = msg.getMobiles();
			List<AccountInfo> accounts = msg.getAccounts();
			// 对短信扩展号进行循环使用
			int spNumber = Integer.valueOf(spExtNumber).intValue();
			if (spNumber >= 999) {
				spExtNumber = "100";
			} else {
				spExtNumber = String.valueOf(spNumber + 1);
			}
			// log.error(spExtNumber);

			try {
				if (CollectionUtils.isNotEmpty(mobiles) && "1".equals(smsMode))
					sendSdkSmsMsg(msg);

				if (CollectionUtils.isNotEmpty(accounts) && "2".equals(smsMode))
					sendEtohSmsMsg(msg);
			} catch (Exception e) {
				throw e;
			}

		} else {
			throw new Exception(SmsConstant.SMSSERVER_CONNECT_ERROR_MSG);
		}
	}

	/**
	 * 通过sdk方式发送短信.
	 * 
	 * @param msg
	 */
	public void sendSdkSmsMsg(SendSmsMessageVO msg) {
		clientId = smsUseConfigService.getClientId(msg.getUnitId());
		if (msg.isTiming()) {
			log.debug("=======定时短信，定时时间：" + msg.getSendTime());
			Date date = DateUtils.string2Date(msg.getSendTime(),
					"yyyyMMddHHmmss");
			List<PhoneInfo> phones = msg.getMobiles();
			for (Iterator<PhoneInfo> ite = phones.iterator(); ite.hasNext();) {
				PhoneInfo phone = ite.next();

				// 参数说明,开通短信功能的客户id,短信扩展号,base_sms_send表短信数据对应的id,手机号码,短信内容
				sdkSmsClient.send(clientId, spExtNumber, "", phone
						.getRecordId(), phone.getPhone(), msg.getMsg(), date);

			}
		} else {
			// 立即发送的短信
			List<PhoneInfo> phones = msg.getMobiles();
			for (Iterator<PhoneInfo> ite = phones.iterator(); ite.hasNext();) {
				PhoneInfo phone = ite.next();

				// 参数说明,开通短信功能的客户id,短信扩展号,base_sms_send表短信数据对应的id,手机号码,短信内容
				sdkSmsClient.send(clientId, spExtNumber, phone.getRecordId(),
						phone.getPhone(), msg.getMsg());

			}
		}

	}

	/**
	 * 发送家校互联短信. 此接口调用的前提是接收短信的手机对应的家长已开通家校互联基本服务.
	 * 
	 * @param msg
	 */
	public void sendEtohSmsMsg(SendSmsMessageVO msg) {
		log.debug("===========开始发送家校互联短信===========");

		EtohSmsMessage emsg = new EtohSmsMessage();
		emsg.setSequence(SmsClient.createId());
		emsg.setSchoolId(msg.getUnitId()); // 发送单位id
		emsg.setFromUserId(msg.getUserName());// 发送用户登录名
		emsg.setNeedReply(true);
		if (msg.isTiming()) {
			log.debug("=======定时短信，定时时间：" + msg.getSendTime());
			emsg.setTiming(true);// 定时发送
			Date date = DateUtils.string2Date(msg.getSendTime(),
					"yyyyMMddHHmmss");
			emsg.setSendTime(date); // 定时发送时间
		} else {
			emsg.setTiming(false);// 不定时发送
		}
		emsg.setContent(msg.getMsg());

		List<AccountInfo> accounts = msg.getAccounts();
		int index = 0;
		for (AccountInfo account : accounts) {
			log.debug("=====发送accountId[" + index + "]:"
					+ account.getAccountId());
			log
					.debug("=====发送recordId[" + index + "]:"
							+ account.getRecordId());
			index++;
		}
		// 每次允许最多发送500条
		if (accounts.size() > 500) {
			emsg.setAccountList(accounts.subList(0, 500));
			szxySmsClient.EtohSmsSend(emsg);
			msg.setAccountList(accounts.subList(500, accounts.size()));
			sendEtohSmsMsg(msg);
		} else {
			emsg.setAccountList(accounts);
			szxySmsClient.EtohSmsSend(emsg);
		}
	}

	public String queryExtSpNumber(String clientId) throws SmscException {
		return sdkSmsClient.queryExtSpNumber(clientId);
	}

	public boolean isSmsUsed(String unitId) throws Exception {
		if ("1".equals(smsMode)) {
			return smsUseConfigService.isSmsUsed(unitId);
		} else {
			return true;
		}
	}

	public double queryBalance(String unitId) throws SmscException {
		clientId = smsUseConfigService.getClientId(unitId);
		return sdkSmsClient.queryBalance(clientId);
	}

	// 当销毁该类时关闭连接
	public void destroy() {
		if (szxySmsClient != null) {
			szxySmsClient.close();
		}
		if (sdkSmsClient != null) {
			sdkSmsClient.close();
		}
	}

	private String getRandom() {
		String radStr = "ABCDEFGHIJ0123456789";// 随机产生的所有字母
		StringBuffer generateRandStr = new StringBuffer();
		Random rand = new Random();
		int length = 3;// 随机数长度
		for (int i = 0; i < length; i++) {
			int randNum = rand.nextInt(20);
			generateRandStr.append(radStr.substring(randNum, randNum + 1));
		}
		return generateRandStr.toString();
	}

	public SmsHandler getSzxyHandler() {
		return szxyHandler;
	}

	public void setSzxyHandler(SmsHandler szxyHandler) {
		this.szxyHandler = szxyHandler;
	}

	public SmscHandler getSdkHandler() {
		return sdkHandler;
	}

	public void setSdkHandler(SmscHandler sdkHandler) {
		this.sdkHandler = sdkHandler;
	}

	public String getConnectedMessage() {
		return connectedMessage;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setSysOptionService(SysOptionService sysOptionService) {
		this.sysOptionService = sysOptionService;
	}

	public void setSmsUseConfigService(SmsUseConfigService smsUseConfigService) {
		this.smsUseConfigService = smsUseConfigService;
	}

}
