/* 
 * @(#)ContainerManager.java    Created on 2006-12-11
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keelcnet/config/ContainerManager.java,v 1.3 2006/12/21 08:29:14 liangxiao Exp $
 */
package net.zdsoft.keelcnet.config;

import org.springframework.web.context.ContextLoader;

public class ContainerManager {
    public static Object getComponent(String name) {
    	return ContextLoader.getCurrentWebApplicationContext().getBean(name);
    }

}
