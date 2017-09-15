/* 
 * @(#)Affair.java    Created on Dec 29, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.affair;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 29, 2010 4:13:13 PM $
 */
public class Affair extends BaseEntity {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -2729512913895402773L;

    /**
     * 系统
     */
    public static final int AFFAIR_SOURCE_SYSTEM = 1;

    /**
     * 业务自定义
     */
    public static final int AFFAIR_SOURCE_CUSTOM = 2;

    /**
     * 没有权限时过滤掉
     */
    public static final int SHOW_TYPE_FILTER = 0;

    /**
     * 展现出来，当用户点击时如果没有权限，则提示用户
     */
    public static final int SHOW_TYPE_PROMPT = 1;

    private String identifier; // 标识符
    private String title; // 标题
    private String content;// 内容
    private String receiverId;// 接收单位
    private String senderId;// 发送单位
    private boolean complete;// 是否完成
    private int affairSource;// 来源：1系统，2业务自定义
    private int moduleId;
    private String operationHeading;
    private int subsystemId;
    private int showType;// 展现类型：0：没有权限时过滤掉; 1：展现出来，当用户点击时如果没有权限，则提示用户
    private String beginDate;
    private String endDate;
    
    private String url;

    public Affair() {
        super();
    }

    public Affair(String identifier, String receiverId, boolean complete) {
        super();
        this.identifier = identifier;
        this.receiverId = receiverId;
        this.complete = complete;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

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

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public int getAffairSource() {
        return affairSource;
    }

    public void setAffairSource(int affairSource) {
        this.affairSource = affairSource;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getOperationHeading() {
        return operationHeading;
    }

    public void setOperationHeading(String operationHeading) {
        this.operationHeading = operationHeading;
    }

    public int getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(int subsystemId) {
        this.subsystemId = subsystemId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
