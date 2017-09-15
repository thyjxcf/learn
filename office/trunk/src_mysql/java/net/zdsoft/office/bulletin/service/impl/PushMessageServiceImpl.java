/* 
 * @(#)PushMessageBeanImpl.java    Created on 2009-8-7
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.office.bulletin.service.impl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.SysOptionService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.office.bulletin.entity.OfficeBulletin;
import net.zdsoft.office.bulletin.entity.PushMessageTargetItem;
import net.zdsoft.office.bulletin.service.PushMessageService;
import net.zdsoft.office.util.DotNetDESUtils;
import net.zdsoft.office.util.WebsitePushService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.xfire.util.Base64;
import org.springframework.remoting.jaxrpc.JaxRpcPortProxyFactoryBean;

/**
 * 处理将信息推送到网站的类
 * 
 * @author zhuhf
 * @version $Revision: 1.0 $, $Date: 2009-8-7 上午10:01:47 $
 */
public class PushMessageServiceImpl implements PushMessageService {

    private static final Log LOG = LogFactory.getLog(PushMessageServiceImpl.class);

    private final String SERVER_CODE = "Dispatch";
    private final char TARGET_ITEM_SEPARATE = '@';
    private final String TARGET_ITEM_INNER_SEPARATE = ",";

    private static final int PUSH_MESSAGE_THREAD_POOL_SIZE = 100;

    private ExecutorService executorService;
    private WebsitePushService websitePushService;
    private DeptService deptService;
    private SystemIniService systemIniService;
    
    @Override
    public void initialize(String url) {
        LOG.info("Begin registe web site push message service...");
        try {
            JaxRpcPortProxyFactoryBean jaxRpcPortProxy = new JaxRpcPortProxyFactoryBean();
            jaxRpcPortProxy.setWsdlDocumentUrl(new URL(url));
            jaxRpcPortProxy.setServiceInterface(WebsitePushService.class);
            jaxRpcPortProxy.setPortName("CommonServiceSoap");
            jaxRpcPortProxy.setServiceName("CommonService");
            jaxRpcPortProxy.setNamespaceUri("http://www.winupon.com/WebService");
            jaxRpcPortProxy.afterPropertiesSet();
            this.websitePushService = (WebsitePushService) jaxRpcPortProxy.getObject();

            String temporaryTicket = getTemporaryTicket();
            websitePushService.RegisterService(SERVER_CODE, temporaryTicket);
            websitePushService.RemoveTicket(temporaryTicket);
        }
        catch (Exception e) {
        	e.printStackTrace();
            websitePushService = null;
            LOG.error("Failed to registe web site push message service", e);
        }
        LOG.info("End registe web site push message service");
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(PUSH_MESSAGE_THREAD_POOL_SIZE);
        }
    }

    @Override
    public List<PushMessageTargetItem> getAvailableTargetItems() {
        String temporaryTicket = getTemporaryTicket();
        List<PushMessageTargetItem> items = Collections.emptyList();
        try {
            items = disassembleMixture(websitePushService.GetServiceClassList(SERVER_CODE, temporaryTicket));
            websitePushService.RemoveTicket(temporaryTicket);
        }
        catch (Exception e) {
            LOG.error("Failed to getAvailableTargetItems", e);
        }
        return items;
    }

    @Override
    public void pushMessage(final String targetItemId, final OfficeBulletin message, final LoginInfo loginInfo, final String currentUrl) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                doPushMessage(websitePushService, targetItemId, message, loginInfo, currentUrl);
            }
        });
    }

    private String getTemporaryTicket() {
    	String registCode = systemIniService.getValue("REGIST_CODE");
        return rebuildTicket(websitePushService.GetTicket(registCode));
    }

    private String rebuildTicket(String originTicket) {
        StringBuilder builder = new StringBuilder();
        builder.append(new String(DotNetDESUtils.desDecryptAndBase64(originTicket)));
        String registCode = systemIniService.getValue("REGIST_CODE");
        builder.append(",").append(registCode).append("@").append(DateUtils.date2StringBySecond(new Date()));
        return DotNetDESUtils.desEncryptAndBase64(builder.toString());
    }

    private List<PushMessageTargetItem> disassembleMixture(String pushTargetList) {
        List<PushMessageTargetItem> items = new ArrayList<PushMessageTargetItem>();
        try {
            String[] mixItems = StringUtils.split(pushTargetList, TARGET_ITEM_SEPARATE);
            for (String mixItem : mixItems) {
                int index = mixItem.indexOf(TARGET_ITEM_INNER_SEPARATE);
                items.add(new PushMessageTargetItem(mixItem.substring(index + 1), mixItem.substring(0, index)));
            }
        }
        catch (Exception e) {
            // return empty list
        }
        return items;
    }

    private String getPushMessageTitle(OfficeBulletin message) {
        if (message.isTitleLink()) {
            StringBuilder builder = new StringBuilder("<a href=\"");// 网站那里需要提供TITLE为链接
            builder.append(message.getContent()).append("\" target=\"_blank\" >");
            builder.append(message.getTitle()).append("</a>");
            return builder.toString();
        }
        return message.getTitle();
    }

    private String getPushMessageContent(OfficeBulletin message, String currentUrl) {
    	String actionString="/common/open/office/netDownLoad.action";
    	String url=currentUrl.lastIndexOf("/")<7 ? StringUtils.EMPTY:currentUrl.substring(currentUrl.lastIndexOf("/",currentUrl.length()));
    	String xxString= message.getContent().replaceAll("src=\""+url+"/common/downloadFile.action", "src=\""+currentUrl+actionString);
    	String xxxString=xxString.replaceAll("src=\""+url+"/static", "<img src=\""+currentUrl+"/static");
    	String xxxxString=xxxString.replaceAll("href=\""+url+"/common/downloadFile.action", "href=\""+currentUrl+"/common/open/office/netDownLoad.action");
    	StringBuilder content = new StringBuilder(xxxxString);
         //appendAttachmentInformation(content, message, currentUrl);
        return Base64.encode(content.toString().getBytes());
    }  
    
    private void doPushMessage(WebsitePushService websitePushService, String targetItemId, OfficeBulletin message,
            LoginInfo loginInfo, String currentUrl) {
        String temporaryTicket = getTemporaryTicket();
        User user = loginInfo.getUser();
        Dept dept=deptService.getDept(user.getDeptid());
        websitePushService.PostArticleForOffice(SERVER_CODE, temporaryTicket, getPushMessageTitle(message),
                getPushMessageContent(message, currentUrl), "", user.getName(), 0, DateUtils.date2StringBySecond(new Date()),
                Integer.parseInt(targetItemId), user.getPassword(), user.getRealname(), 1, loginInfo.getUnitName(),
                dept.getDeptname(), message.isTitleLink() ? 1 : 0);
        websitePushService.RemoveTicket(temporaryTicket);
    }

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

}
