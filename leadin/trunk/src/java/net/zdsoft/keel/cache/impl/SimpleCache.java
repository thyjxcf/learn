/* 
 * @(#)SimpleCache.java    Created on 2008-10-23
 * Copyright (c) 2008 ZDSoft Networks, Inc. All rights reserved.
 * $Id: SimpleCache.java,v 1.1 2008/10/23 07:46:19 huangwj Exp $
 */
package net.zdsoft.keel.cache.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import net.zdsoft.keel.cache.Cache;

/**
 * 默认的简单缓存实现类.
 * 
 * @param <K>
 *            缓存键的类型
 * @param <V>
 *            缓存值的类型
 * 
 * @author huangwj
 * @version $Revision: 1.1 $, $Date: 2008/10/23 07:46:19 $
 */
@SuppressWarnings("unused")
public class SimpleCache<K, V> implements Cache<K, V> {

    private Map<K, V> cacheMap = new ConcurrentHashMap<K, V>();

    private int moduleSize = 10;
    @SuppressWarnings("unchecked")
    private Map<Integer, Map> cacheModuleMap = new ConcurrentHashMap<Integer, Map>();

    public V get(K key) {
        // TODO Auto-generated method stub
        return null;
    }

    public V put(K key, V value) {
        if (value == null) {
            throw new NullPointerException();
        }

        int hashCode = Math.abs(key.hashCode());

        return null;
    }

    public V put(K key, V value, Date expiry) {
        // TODO Auto-generated method stub
        return null;
    }

    public V put(K key, V value, long timeout, TimeUnit unit) {
        // TODO Auto-generated method stub
        return null;
    }

    public V remove(K key) {
        // TODO Auto-generated method stub
        return null;
    }

    public Set<K> keySet() {
        // TODO Auto-generated method stub
        return null;
    }

    public Collection<V> values() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean containsKey(Object key) {
        // TODO Auto-generated method stub
        return false;
    }

    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void clear() {
        // TODO Auto-generated method stub

    }

    public void destroy() {
        // TODO Auto-generated method stub

    }

}
