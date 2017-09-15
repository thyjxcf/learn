package net.zdsoft.eis.base.subsystemcall.entity;

import java.io.Serializable;
public class OfficeMsgSendingDto implements Serializable{
	private static final long serialVersionUID = 1L;

	private String title;
	private String content;
	private Boolean isNeedsms;
	private String smsContent;
	private String createUserId;
	private String unitId;
	private String simpleContent;
	private String userIds;//选择的用户ids
	private String sendUserName;//发件人姓名
	/**
	 * 消息来源，微代码DM-MSGTYPE,默认为1消息
	 */
	private Integer msgType;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Boolean getIsNeedsms() {
		return isNeedsms;
	}
	public void setIsNeedsms(Boolean isNeedsms) {
		this.isNeedsms = isNeedsms;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getSimpleContent() {
		return simpleContent;
	}
	public void setSimpleContent(String simpleContent) {
		this.simpleContent = simpleContent;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getSendUserName() {
		return sendUserName;
	}
	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	public Integer getMsgType() {
		return msgType;
	}
	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}
	
}