package net.zdsoft.eis.sms.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class MsgDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String content; // 短信内容
	private String smsType; // 短信类型 公文：SmsConstant.MSG_ARCHIVE
	private boolean isTiming = false; // 是否定时发送，默认否
	private String sendDate; // 发送日期，格式:20090604
	private Integer sendHour; // 发送小时，格式：1至24小时
	private Integer sendMinutes; // 发送分钟，格式：1至60分钟
	private String dep; // 发送者所在部门名称
	private String depId; // 发送者所在部门id
	private String userName; // 发送者用户名，注意：是登录名
	private String userId;// 发送者用户id,默认用户ZDConstant.DEFAULT_USER_ID
	private String unitId;// 发送者单位id
	private String unitName;// 发送者单位名称

	private String sendTime; // 组装后的时间信息，用于定时发送

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public boolean isTiming() {
		return isTiming;
	}

	public void setTiming(boolean isTiming) {
		this.isTiming = isTiming;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getSendHour() {
		return sendHour;
	}

	public void setSendHour(Integer sendHour) {
		this.sendHour = sendHour;
	}

	public Integer getSendMinutes() {
		return sendMinutes;
	}

	public void setSendMinutes(Integer sendMinutes) {
		this.sendMinutes = sendMinutes;
	}

	public String getDep() {
		return dep;
	}

	public void setDep(String dep) {
		this.dep = dep;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	// 组装成yyyymmddhhmmss格式，用于短信的定时发送要求
	public String getSendTime() {

		StringBuffer sb = new StringBuffer("");
		String hour = leftPadZero(String.valueOf(getSendHour()), 2);
		String minute = leftPadZero(String.valueOf(getSendMinutes()), 2);
		sb.append(getSendDate()).append(hour).append(minute).append("00");
		sendTime = sb.toString();

		return sendTime;
	}

	/**
	 * 在字符串左边填充0直到指定长度.
	 * 
	 * @param str
	 *            原始字符串
	 * @param len
	 *            总的长度
	 */
	public static String leftPadZero(String str, int len) {
		if (StringUtils.isBlank(str))
			return str;

		while (str.length() < len) {
			str = "0" + str;
		}
		return str;
	}

}
