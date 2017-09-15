/* 
 * @(#)Cache.java    Created on 2008-10-21
 * Copyright (c) 2008 ZDSoft Networks, Inc. All rights reserved.
 * $Id: Cache.java,v 1.1 2008/10/23 07:46:19 huangwj Exp $
 */
package net.zdsoft.keel.cache;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 数据缓存接口.
 * 
 * @param <K>
 *            缓存键的类型
 * @param <V>
 *            缓存值的类型
 * 
 * @author huangwj
 * @version $Revision: 1.1 $, $Date: 2008/10/23 07:46:19 $
 */
public interface Cache<K, V> {

    /**
     * 根据指定的缓存键来获取缓存值.
     * 
     * @param key
     *            缓存键
     * @return 缓存值
     */
    V get(K key);

    /**
     * 将数据放入缓存.
     * 
     * @param key
     *            缓存键
     * @param value
     *            缓存值
     * @return 缓存值
     */
    V put(K key, V value);

    /**
     * 将数据放入缓存, 并设置过期的时间点.
     * 
     * @param key
     *            缓存键
     * @param value
     *            缓存值
     * @param expiry
     *            缓存过期的时间点
     * @return 缓存值
     */
    V put(K key, V value, Date expiry);

    /**
     * 将数据放入缓存, 并设置超时时间.
     * 
     * @param key
     *            缓存键
     * @param value
     *            缓存值
     * @param timeout
     *            超时时间
     * @param unit
     *            时间单位
     * @return 缓存值
     */
    V put(K key, V value, long timeout, TimeUnit unit);

    /**
     * 根据缓存键来删除缓存值.
     * 
     * @param key
     *            缓存键
     * @return 被删除的缓存值
     */
    V remove(K key);

    /**
     * 获取所有缓存键的集合.
     * 
     * @return 所有缓存键的集合
     */
    Set<K> keySet();

    /**
     * 获取所有缓存值集合.
     * 
     * @return 所有缓存值的集合
     */
    Collection<V> values();

    /**
     * 判断某个缓存键是否存在.
     * 
     * @param key
     *            缓存键
     * @return 如果存在对应的缓存值则返回 <code>true</code>, 否则返回 <code>false</code>.
     */
    boolean containsKey(Object key);

    /**
     * 获取缓存的个数.
     * 
     * @return 缓存个数.
     */
    int size();

    /**
     * 清除所有缓存.
     */
    void clear();

    /**
     * 清理数据缓存.
     */
    void destroy();

}
