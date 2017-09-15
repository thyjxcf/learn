/* 
 * @(#)SessionManager.java    Created on 2006-7-14
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/member/src/net/zdsoft/member/client/SessionManager.java,v 1.4 2006/09/05 01:16:49 liangxiao Exp $
 */
package net.zdsoft.eis.frame.passport;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.passport.exception.PassportException;
import net.zdsoft.passport.service.client.PassportClient;

public class SessionManager implements HttpSessionListener {

    private static Logger logger = LoggerFactory.getLogger(SessionManager.class);

    private static Map<String, HttpSession> ticket2SessionMap = new ConcurrentHashMap<String, HttpSession>();

	public SessionManager() {
    }

    public void sessionCreated(HttpSessionEvent sessionEvent) {
   	
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    	//获取session失效时间
//    	CountOnlineTimeService countOnlineTimeService = (CountOnlineTimeService)ContainerManager.getComponent("countOnlineTimeService");
//    	CountOnlineTime countOnlineTime = countOnlineTimeService.getCountOnlineTimeBySessId(sessionEvent.getSession().getId());
//    	if(null!=countOnlineTime){
//    	long a = countOnlineTime.getLoginTime().getTime();
//        long b = new Date().getTime();
//    	int c = (int)((b - a) / 1000);
//    	countOnlineTime.setLogoutTime(new Date());
//    	countOnlineTime.setOnlineTime(c);
//    	countOnlineTimeService.update(countOnlineTime);
//    	}
    	
    	
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
