package net.zdsoft.eis.sms.constant;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class SmsConstant {

	public static final String SMS_USE_MODEL = "SMS.USE.MODEL";// 单位短信启用模式 1
																// 顶级单位统一管理 2
																// 各个单位分别各自管理

	public static final String SMS_USE_MODEL_TOP = "1";// 单位短信启用模式 1 顶级单位统一管理 2
														// 各个单位分别各自管理

	public static final String SMS_USE_MODEL_SELF = "2";// 单位短信启用模式 1 顶级单位统一管理 2
														// 各个单位分别各自管理

	public static final String SMSSERVER_CONNECT_ERROR_MSG = "通讯服务器连接失败，请检查相关短信服务器配置信息！";

	public static final String SMSSERVER_CONNECT_SUCCESS_MSG = "通讯服务器连接成功！";
	
	public static final String STATISTIC_UNIT_SMS = "STATISTIC_UNIT_SMS";

	// 批次发送状态(0:已发送至网关，等结果 1：成功 2：失败)
	public static String BATCH_STATUS_WAIT = "0";
	public static String BATCH_STATUS_WAIT_DESC = "等待发送";

	public static String BATCH_STATUS_SUCCESS = "1";
	public static String BATCH_STATUS_SUCCESS_DESC = "发送成功";

	public static String BATCH_STATUS_FAIL = "2";
	public static String BATCH_STATUS_FAIL_DESC = "发送失败";

	// 短信发送状态
	public static String SMS_STATUS_WAIT = "0"; // 已发送至网关、等待结果
	public static String SMS_STATUS_SUCCESS = "1"; // 发送成功
	public static String SMS_STATUS_FAIL = "2"; // 发送失败

	// 短信业务收费类型
	public static int SMS_BUSINESS_PAY = 1; // 按条计费短信
	public static int SMS_BUSINESS_ETOH = 2; // 家校互联短信

	// 短信网关信息描述
	public static String SMS_RESULT_WAIT = "未响应";
	public static String SMS_RESULT_SUCCESS = "成功";
	public static String SMS_RESULT_FAIL = "失败";
	public static String SMS_RESULT_OTHER = "其它"; // 一般是相同内容重复发送，导致失败
	public static String SMS_RESULT_LESS_BALANCE = "余额不足";
	
	public static final String SMS_CONFIG_FILE = "conclient.properties";
	// ----通信服务属性名称
	public static final String SMS_SERVER = "server";
	public static final String SMS_PORT = "port";
	public static final String SMS_LOCALNAME = "localName";
	public static final String SMS_LOCALPWD = "localPwd";
	

	public static Map<Integer, String> smsResultMsgMap = new HashMap<Integer, String>();
	static {
		smsResultMsgMap.put(0, SMS_RESULT_WAIT);
		smsResultMsgMap.put(1, SMS_RESULT_SUCCESS);
		smsResultMsgMap.put(2, SMS_RESULT_FAIL);
		smsResultMsgMap.put(3, SMS_RESULT_OTHER);
		smsResultMsgMap.put(10, SMS_RESULT_LESS_BALANCE);
	}

	// 手机结果信息描述
	public static String SMS_REPORT_CODE_WAIT = "0";
	public static String SMS_REPORT_CODE_SUCCESS = "1";
	public static String SMS_REPORT_CODE_FAIL = "2";

	public static String SMS_REPORT_WAIT = "未响应";
	public static String SMS_REPORT_SUCCESS = "成功";
	public static String SMS_REPORT_FAIL = "失败";
	public static String SMS_REPORT_OTHER = "其它"; // 其它不正常的

	public static Map<String, String> smsReportMsgMap = new HashMap<String, String>();
	static {
		smsReportMsgMap.put(SMS_REPORT_CODE_WAIT, SMS_REPORT_WAIT);
		smsReportMsgMap.put(SMS_REPORT_CODE_SUCCESS, SMS_REPORT_SUCCESS);
		smsReportMsgMap.put(SMS_REPORT_CODE_FAIL, SMS_REPORT_FAIL);
	}

	// 信息类型
	public static final int MSG_ALL = 0; // 全部
	public static final int MSG_NOTE = 1; // 留言
	public static final int MSG_NOTICE = 2; // 通知
	// public static final int MSG_COMMENT = 3; //评语
	// public static final int MSG_HOMEWORK = 4; //家庭作业
	// public static final int MSG_ACHIEVEMENT = 5; //成绩
	public static final int MSG_SMS = 6; // 自由短信
	public static final int MSG_ARCHIVE = 7; // 公文
	
	public static final List<String> getSmsTypeList() {
		List<String> smsTypeList = new LinkedList<String>();
		smsTypeList.add(String.valueOf(MSG_NOTE));
		smsTypeList.add(String.valueOf(MSG_NOTICE));
		smsTypeList.add(String.valueOf(MSG_SMS));
		return smsTypeList;
	}

	public static final Map<String, String> getSmsTypeMap() {
		Map<String, String> smsTypeMap = new HashMap<String, String>();
		smsTypeMap.put(String.valueOf(MSG_NOTE), "留言");
		smsTypeMap.put(String.valueOf(MSG_NOTICE), "通知");
		smsTypeMap.put(String.valueOf(MSG_SMS), "自由短信");
		return smsTypeMap;
	}

	/**
	 * 短信使用配置标志符：短信使用
	 */
	public static final String CONFIG_SIGN_SMS = "sms";
	/**
	 * 短信使用配置标志符：公文发送
	 */
	public static final String CONFIG_SIGN_ARCHIVE_SEND = "archivesend";
	/**
	 * 短信使用配置标志符：通知发送
	 */
	public static final String CONFIG_SIGN_NOTICE_SEND = "noticesend";
	/**
	 * 短信使用配置标志符：短信客户clientId
	 */
	public static final String CONFIG_SIGN_CLIENTID = "clientid";

	/**
	 * 短信类型的微代码标识符mcodeid
	 */
	// public static final String SMSTYPE = "DM-SMSTYPE";
	/**
	 * 系统默认的不能修改的短信类型：公文
	 */
	public static final String SMSTYPE_ARCHIVE = "01";
	/**
	 * 系统默认的不能修改的短信类型：通知
	 */
	public static final String SMSTYPE_NOTICE = "02";

	/**
	 * 系统默认的不能修改的短信类型：办公消息
	 */
	public static final String SMSTYPE_MESSAGE = "03";

	/**
	 * 系统默认的不能修改的短信类型：公告
	 */
	public static final String SMSTYPE_BULLETIN = "11";
	/**
	 * 系统默认的不能修改的短信类型：生日祝福
	 */
	// public static final String SMSTYPE_BIRTHDAY = "12";
	/**
	 * 系统默认的不能修改的短信类型：节假日祝福
	 */
	// public static final String SMSTYPE_FESTIVE = "13";
	/**
	 * 系统默认的不能修改的短信类型：数据通
	 */
	// public static final String SMSTYPE_COMMSTAT = "09";
	/**
	 * 短信类型：普通
	 */
	// public static final String SMSTYPE_GENERAL = "10";
	/**
	 * 短信类型：其它
	 */
	// public static final String SMSTYPE_OTHER = "99";
	/**
	 * 短信扩展号码：公文发送同步发送出去的短信
	 */
	public static final String SPNUM_ARCHIVE_SEND = "100";
	/**
	 * 短信扩展号码：办公系统中发送出去的短信
	 */
	public static final String SPNUM_OFFICE_GENERAL = "200";

	/**
	 * 短消息发送时间间隔，单位为分钟
	 */
	// public static int SMS_SEND_INTERVAL = 5;
	//
	// public static final String TASK_FLAG_TASK = "0"; //
	// 短信任务表中flag字段：已经提交发送的短信任务
	// public static final String TASK_FLAG_BATCH = "1"; // 短信任务表中flag字段：草稿箱中的短信
	// public static final String TASK_FLAG_DELETED = "2";//
	// 短信任务表中flag字段：该条记录(包括草稿和任务)已删除，位于垃圾箱中
	//
	// public static final String TASK_ISDRAFT_FALSE = "0";//
	// 短信任务表中isdraft字段：不是草稿箱中短信
	// public static final String TASK_ISDRAFT_TRUE = "1"; //
	// 短信任务表中isdraft字段：是草稿箱中短信
	//
	// public static final String TASK_CIRCLE_APPOINTDAY =
	// "06";//短信任务表中circletype字段，定时发送会用到的周期类型：指定日
	/**
	 * 特殊的零值单位GUID：用来表示单位短信使用配置初始化数据使用的单位GUID。
	 */
	// public static final String SPECIAL_UNITGUID =
	// "00000000-0000-0000-0000-000000000000";
	/**
	 * 短信内容中允许的最大字数：不包括任何中文字符
	 */
	public static int MSG_WORDNUM_EN = 120;
	/**
	 * 短信内容中允许的最大字数：若包括中文字符
	 */
	public static int MSG_WORDNUM_CH = 70;
	/**
	 * 短信内容中允许的最大字数：需要进行分页时每页减少的字符数
	 */
	public static int MSG_WORDNUM_PAGE = 5;

	/**
	 * 数据库中能够容纳的短信内容长度
	 */
	public static int MSG_LIMIT_LEN = 400;

	/**
	 * 个人用，不公开
	 */
	// public static final String SMS_PERSON = "0";
	/**
	 * 公用
	 */
	// public static final String SMS_PUBLIC = "1";
	/**
	 * 是否定时发送短信
	 */
	// public static final String IS_FIXED_SMS = "1";
	// 短信接收对象类型：1：用户 2：联系人 4：直接输入手机号
	// public static final String SMS_RECEIVER_TYPE_USER = "1";
	// public static final String SMS_RECEIVER_TYPE_CONTACT_ADDRESS = "2";
	// public static final String MESSAGE_RECEIVER_TYPE_MOBILE = "4";
	// 短信发送或保存标识
	// public static final String SMS_SEND_FLAG = "send";
	// public static final String SMS_SAVE_FLAG = "save";

}
