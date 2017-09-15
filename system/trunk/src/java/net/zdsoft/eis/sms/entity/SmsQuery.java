package net.zdsoft.eis.sms.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author jiangf
 * @version 创建时间：2011-8-10 上午10:18:10
 */

public class SmsQuery extends BaseEntity {

	private static final long serialVersionUID = -9001688311578642080L;
	private String sendDate;
	private String content;
	private String smsType;
	private Integer sendHour;
	private Integer sendMinutes;
	private String dep;
	private String userName;
	private String status;
	private String mobile;
	private String receiveName;
	
	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
}
