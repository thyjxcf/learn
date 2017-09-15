/* 
 * @(#)PushMessageBean.java    Created on 2009-8-6
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.office.bulletin.service;

import java.util.List;

import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.office.bulletin.entity.OfficeBulletin;
import net.zdsoft.office.bulletin.entity.PushMessageTargetItem;


/**
 * 消息推送到其他网站的类
 * 
 * @author zhuhf
 * @version $Revision: 1.0 $, $Date: 2009-8-6 下午02:08:56 $
 */
public interface PushMessageService {

    /**
     * 初始化
     * 
     * @param url
     */
    public void initialize(String url);

    /**
     * 将信息推送到目标栏目
     * 
     * @param targetItemId
     * @param officeMsgSending
     * @param user
     */
    void pushMessage(String targetItemId, OfficeBulletin officeMsgSending, LoginInfo loginInfo,String currentUrl);

    /**
     * 获取推送目标栏目
     * 
     * @return
     */
    List<PushMessageTargetItem> getAvailableTargetItems();


}
