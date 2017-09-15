/* 
 * @(#)SimpleCacheManager.java    Created on Jun 29, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.cache;

import java.util.List;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 29, 2010 2:03:41 PM $
 */
public class SimpleCacheManagerImpl extends AbstractCacheManager implements SimpleCacheManager {
    private static final String COMMON_CACHE_ID_KEY = "eis_common_id_";

    public String fetchCacheIndexKey() {
        return "eis_common_index_key";
    }

    /**
     * 一般用到的缓存
     * 
     * @param key
     * @param o
     */
    public void put(String key, Object o) {
        putInCache(COMMON_CACHE_ID_KEY + key, o);
    }

    /**
     * 一般用到的缓存
     * 
     * @param key
     * @param o
     * @param timeout
     */
    public void put(String key, Object o, int timeout) {
        putInCache(COMMON_CACHE_ID_KEY + key, o, timeout);
    }

    /**
     * 删除单个一般用缓存
     * 
     * @param key
     */
    public void remove(String key) {
        removeFromCache(COMMON_CACHE_ID_KEY + key);
    }

    @Override
    public <T> T getObjectFromCache(final CacheObjectParam<T> param) {
        return super.getObjectFromCache(new CacheObjectParam<T>() {

            @Override
            public T fetchObject() {
                return param.fetchObject();
            }

            @Override
            public String fetchKey() {
                return COMMON_CACHE_ID_KEY + param.fetchKey();
            }
        });
    }

    @Override
    public <T> List<T> getObjectsFromCache(final CacheObjectsParam<T> param) {
        return super.getObjectsFromCache(new CacheObjectsParam<T>() {

            @Override
            public List<T> fetchObjects() {
                return param.fetchObjects();
            }

            @Override
            public String fetchKey() {
                return COMMON_CACHE_ID_KEY + param.fetchKey();
            }
        });
    }
}
