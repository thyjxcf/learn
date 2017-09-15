/* 
 * @(#)SimpleCacheManager1.java    Created on Jun 29, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.cache;


public interface SimpleCacheManager extends CacheManager {
    /**
     * 取缓存
     * 
     * @param <T>
     * @param param
     * @return
     */
    public <T> T getObjectFromCache(CacheObjectParam<T> param);
    
    /**
     * 一般用到的缓存
     * 
     * @param key
     * @param o
     */
    public abstract void put(String key, Object o);

    /**
     * 一般用到的缓存
     * 
     * @param key
     * @param o
     * @param timeout
     */
    public abstract void put(String key, Object o, int timeout);

    /**
     * 删除单个一般用缓存
     * 
     * @param key
     */
    public abstract void remove(String key);

}
