/* 
 * @(#)SessionManager.java    Created on 2006-7-14
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/member/src/net/zdsoft/member/client/SessionManager.java,v 1.4 2006/09/05 01:16:49 liangxiao Exp $
 */
package net.zdsoft.eis.frame.passport;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.zdsoft.eis.base.data.entity.CountOnlineTime;
import net.zdsoft.eis.base.data.service.CountOnlineTimeService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.passport.exception.PassportException;
import net.zdsoft.passport.service.client.PassportClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionManager extends BaseAction implements HttpSessionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(SessionManager.class);

    private static Map<String, HttpSession> ticket2SessionMap = new ConcurrentHashMap<String, HttpSession>();

	public SessionManager() {
    }

    public void sessionCreated(HttpSessionEvent sessionEvent) {
   	
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    	//获取session失效时间
    	if(2 == getLoginInfo().getUser().getOwnerType()){
    		CountOnlineTimeService countOnlineTimeService = (CountOnlineTimeService)ContainerManager.getComponent("countOnlineTimeService");
        	CountOnlineTime countOnlineTime = countOnlineTimeService.getCountOnlineTimeBySessId(sessionEvent.getSession().getId());
        	if(null!=countOnlineTime){
        	long a = countOnlineTime.getLoginTime().getTime();
            long b = new Date().getTime();
        	int c = (int)((b - a) / 1000);
        	countOnlineTime.setLogoutTime(new Date());
        	countOnlineTime.setOnlineTime(c);
        	countOnlineTimeService.update(countOnlineTime);
    	}    	
    }
    	
    	
        String ticket = (String) sessionEvent.getSession().getAttribute(
                "ticket");
        if (ticket != null) {
            try {
                // 清除ticket
                removeTicketInMap(ticket);
                // 通知中心
                PassportClient.getInstance().invalidate(ticket);
            }
            catch (PassportException e) {
                logger.error("Could not invalidate ticket", e);
            }
        }
    }
    
    public static int getCountSession(){
    	return ticket2SessionMap.size();
    }

    public static void putTicketInMap(String ticket, HttpSession session) {
        if (ticket != null) {
            ticket2SessionMap.put(ticket, session);
        }
    }

    public static HttpSession removeTicketInMap(String ticket) {
        if (ticket == null) {
            return null;
        }
        return (HttpSession) ticket2SessionMap.remove(ticket);
    }


    

}
