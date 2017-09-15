/* 
 * @(#)CacheManagerServiceImpl.java    Created on Jun 22, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.frame.cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.leadin.cache.CascadeCacheCall;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 22, 2010 8:54:50 PM $
 */
public class CascadeCacheManager extends DefaultCacheManager implements CascadeCacheCall {
    private static final Logger log = LoggerFactory.getLogger(CascadeCacheManager.class);

    /**
     * 将entity的Id放入缓存中，用于根据id级联缓存
     * 
     * @param key
     * @param id
     */    
    private void putEntityIdInCache(String key, String id) {
        Object o = wrappedCache.get(key);
        if (o != null) {
            @SuppressWarnings("unchecked")
            Set<String> ids = (Set<String>) o;
            ids.add(id);
            wrappedCache.put(key, ids, this);
        }
    }

    /**
     * 将entity的Id移出cache，同时清除entity
     * 
     * @param key
     * @param id
     */
    protected void removeEntityFromCache(String key, String id) {
        Object o = wrappedCache.get(key);
        if (o != null) {
            @SuppressWarnings("unchecked")
            Set<String> ids = (Set<String>) o;
            ids.remove(id);
            wrappedCache.put(key, ids, this);
        }
        removeEntityFromCache(id);
    }

    /**
     * 更新entity的ID所关联的缓存，主要用于根据分类查询列表有缓存的情况
     * 
     * @param srcKey
     * @param destKey
     * @param id
     */
    protected void updateEntityCache(String srcKey, String destKey, Object entity) {
        String id = extractId(entity);
        if (null == srcKey) {
            putEntityIdInCache(destKey, id);
        } else {
            if (!srcKey.equals(destKey)) {
                removeEntityFromCache(srcKey, id);
                putEntityIdInCache(destKey, id);
            }
        }
        putEntityInCache(entity);
    }

    /**
     * 从缓存中取单个对对象，缓存的是id
     * 
     * @param param
     * @return
     */
    protected <T> T getObjectFromCache(CacheEntityIdParam<T> param) {
        String key = param.fetchKey();
        Object o = wrappedCache.get(key);
        T entity = null;
        if (o == null) {
            entity = param.fetchObject();
            if (entity == null) {
                return null;
            }
            String id = extractId(entity);
            wrappedCache.put(key, id, this);
            putEntityInCache(entity);
        } else {
            String id = (String) o;
            entity = getEntityFromCache(id, param);
        }
        return entity;
    }

    private <T> T getEntityFromCache(final String id, final CacheEntity<T> param) {
        return super.getEntityFromCache(new CacheEntityParam<T>() {

            public String fetchKey() {
                return fetchCacheEntityKey() + id;
            }

            public T fetchObject() {
                return param.fetchObject(id);
            }
        });
    }

    /**
     * 从缓存中取列表数据，缓存的是id
     * 
     * @param param
     * @return
     */
    protected <T> List<T> getEntitiesFromCache(CacheEntitiesIdParam<T> param) {
        String key = param.fetchKey();
        Object o = wrappedCache.get(key);
        List<T> entities;
        Set<String> ids = null;
        if (o == null) {
            ids = new HashSet<String>();
            entities = param.fetchObjects();
            for (T entity : entities) {
                String id = extractId(entity);
                ids.add(id);
                putEntityInCache(entity);
            }
            wrappedCache.put(key, ids, this);
        } else {
            entities = new ArrayList<T>();
            @SuppressWarnings("unchecked")
            Set<String> _ids = (Set<String>) o;
            ids = _ids;
            Set<String> idKeys = new HashSet<String>();
            for (String id : ids) {
                idKeys.add(fetchCacheEntityKey() + id);
            }
            Object o1 = wrappedCache.getMultiMap(idKeys.toArray(new String[0]));
            // 如果不为空，则判断是否所有的item已经进入了缓存，如果没有，则再取出放入缓存
            if (o1 != null) {
                @SuppressWarnings("unchecked")
                Map<String, T> entityMap = (Map<String, T>) o1;
                for (String id : ids) {
                    T entity = entityMap.get(fetchCacheEntityKey() + id);
                    if (entity == null) {
                        entity = getEntityFromCache(id, param);
                    }
                    if (entity != null) {
                        entities.add(entity);
                    }
                }
            } else {
                log.debug("部分緩存丟失，出現非正常情況");
            }
        }
        return entities;
    }
}
