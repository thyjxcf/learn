/* 
 * @(#)CacheHelper.java    Created on Jun 23, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.cache;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 缓存辅助类
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 23, 2010 7:45:11 PM $
 */
public class CacheHelper {
    private SimpleCacheManager simpleCacheManager;

    public void setSimpleCacheManager(SimpleCacheManager simpleCacheManager) {
        this.simpleCacheManager = simpleCacheManager;
    }

    /**
     * 缓存类對象
     */
    private static ConcurrentMap<String, CacheManager> cacheObjectMap = new ConcurrentHashMap<String, CacheManager>();

    /**
     * 将简单缓存放到map中
     */
    public void init() {
        cacheObjectMap.put(simpleCacheManager.fetchCacheIndexKey(), simpleCacheManager);
    }

    /**
     * 注册缓存类
     * 
     * @param service
     */
    public static void register(CacheManager service) {
        cacheObjectMap.put(service.fetchCacheIndexKey(), service);

    }

    /**
     * 初始化缓存
     */
    public static void initCache() {
        Collection<CacheManager> cacheObjects = cacheObjectMap.values();
        for (CacheManager service : cacheObjects) {
            service.initCache();
        }
    }

    /**
     * 清除缓存
     */
    public static void clearCache() {
        Collection<CacheManager> cacheObjects = cacheObjectMap.values();
        for (CacheManager service : cacheObjects) {
            service.clearCache();
        }
    }

    public static ConcurrentMap<String, CacheManager> getCacheObjectMap() {
        return cacheObjectMap;
    }

}
