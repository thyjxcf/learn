/* 
 * @(#)AffairHelper.java    Created on Dec 29, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.affair;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 待办事项助手
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 29, 2010 3:28:16 PM $
 */
public class AffairHelper {
    /**
     * 缓存事项对象
     */
    private static ConcurrentMap<String, Transactable> transactObjectMap = new ConcurrentHashMap<String, Transactable>();

    /**
     * 注册待办事项类
     * 
     * @param service
     */
    public static void register(Transactable service) {
        transactObjectMap.put(service.getIdentifier(), service);
    }

    public static ConcurrentMap<String, Transactable> getTransactObjectMap() {
        return transactObjectMap;
    }

}
