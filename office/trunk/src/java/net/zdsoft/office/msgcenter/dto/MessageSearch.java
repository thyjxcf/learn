/* 
 * @(#)MessageSearch.java    Created on 2009-3-10
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.office.msgcenter.dto;

import java.io.Serializable;

// 这个类主要用来存放信息检索的一些属性
public class MessageSearch implements Serializable {

    private String searchTitle;
	private String searchSender;
	private String searchBeginTime;
	private String searchEndTime;
	private int messageType;
	private String readType;
	private String userId;
	private String sendDeptId;
	private String unitId;
	private String[] sendUserIds;
	private String searchTitleORSender;
	
    private static final long serialVersionUID = -5376494078458668104L;

    private String messageId;
    /**
     * 消息接受者的Id
     * 
     */
    private String receiveUserId;
    /**
     * 消息发送者Id
     * 
     */
    private String sendUserid;
    /**
     * 1:留言 2:通知
     * 
     */
    private Integer msgType;
    /**
     * 是否已读 0,表示未读;1，表示已读
     */
    private Integer isRead;
    /**
     * 消息状态 1 草稿箱、2 发件箱、3 收件箱、4废件箱、 5 自定义文件夹
     */
    private Integer state;
    /**
     * 页面上传入的关键字
     */
    private String keyword;
    
    private String receiveDeptId;
    private String receiveUnitId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getSendUserid() {
        return sendUserid;
    }

    public void setSendUserid(String sendUserid) {
        this.sendUserid = sendUserid;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	public String getSearchSender() {
		return searchSender;
	}

	public void setSearchSender(String searchSender) {
		this.searchSender = searchSender;
	}

	public String getSearchBeginTime() {
		return searchBeginTime;
	}

	public void setSearchBeginTime(String searchBeginTime) {
		this.searchBeginTime = searchBeginTime;
	}

	public String getSearchEndTime() {
		return searchEndTime;
	}

	public void setSearchEndTime(String searchEndTime) {
		this.searchEndTime = searchEndTime;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public String getReadType() {
		return readType;
	}

	public void setReadType(String readType) {
		this.readType = readType;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSendDeptId() {
		return sendDeptId;
	}

	public void setSendDeptId(String sendDeptId) {
		this.sendDeptId = sendDeptId;
	}

	public String getUserId() {
		return userId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String[] getSendUserIds() {
		return sendUserIds;
	}

	public void setSendUserIds(String[] sendUserIds) {
		this.sendUserIds = sendUserIds;
	}

	public String getSearchTitleORSender() {
		return searchTitleORSender;
	}

	public void setSearchTitleORSender(String searchTitleORSender) {
		this.searchTitleORSender = searchTitleORSender;
	}

	public String getReceiveDeptId() {
		return receiveDeptId;
	}

	public void setReceiveDeptId(String receiveDeptId) {
		this.receiveDeptId = receiveDeptId;
	}

	public String getReceiveUnitId() {
		return receiveUnitId;
	}

	public void setReceiveUnitId(String receiveUnitId) {
		this.receiveUnitId = receiveUnitId;
	}


}
