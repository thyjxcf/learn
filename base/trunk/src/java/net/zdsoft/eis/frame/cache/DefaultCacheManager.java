/* 
 * @(#)DefaultCacheManagerService.java    Created on Jun 23, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.frame.cache;

import java.util.List;

import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.keelcnet.config.ContainerManager;
import net.zdsoft.keelcnet.entity.EntityObject;
import net.zdsoft.leadin.cache.AbstractCacheManager;
import net.zdsoft.leadin.cache.CacheCall;
import net.zdsoft.leadin.cache.CacheManager;
import net.zdsoft.leadin.cache.CacheUtils;
import net.zdsoft.leadin.cache.WrappedCache;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 23, 2010 3:13:39 PM $
 */
public class DefaultCacheManager extends AbstractCacheManager implements CacheManager, CacheCall {

    public String fetchCacheIndexKey() {
        return "eis_" + CacheUtils.extractCacheDataName(this.getClass()) + "_index_key";
    }

    /**
     * 取缓存对象惟一标识key（即ID）
     * 
     * @return
     */
    protected String fetchCacheEntityKey() {
        return fetchCacheEntityKey(this.getClass());
    }

    /**
     * 取缓存对象惟一标识key
     * 
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    protected String fetchCacheEntityKey(Class clazz) {
        return "eis_" + CacheUtils.extractCacheDataName(clazz) + "_id_";
    }

    /**
     * 抽取id
     * 
     * @param entity
     * @return
     */
    protected String extractId(Object entity) {
        String id = null;
        if (entity instanceof BaseEntity) {
            id = ((BaseEntity) entity).getId();
        } else {
            id = String.valueOf(((EntityObject) entity).getId());
        }
        return id;
    }

    /**
     * 将对象放入cache中
     * 
     * @param entity
     */
    protected void putEntityInCache(Object entity) {
        if (entity == null)
            return;

        String id = extractId(entity);
        if (StringUtils.isBlank(id))
            return;

        String key = fetchCacheEntityKey() + id;
        wrappedCache.put(key, entity, this);
    }

    /**
     * 将entity移出cache
     * 
     * @param id
     */
    protected void removeEntityFromCache(String id) {
        if (StringUtils.isBlank(id))
            return;
        String key = fetchCacheEntityKey() + id;
        wrappedCache.remove(key, this);
    }

    /**
     * 从缓存中取单个对对象，缓存的是对象
     * 
     * @param param
     * @return
     */
    protected <T> T getEntityFromCache(CacheEntityParam<T> param) {
        String key = param.fetchKey();
        if(wrappedCache == null)
        wrappedCache  = (WrappedCache) ContainerManager.getComponent("wrappedCache");
        Object o = wrappedCache.get(key);
        T entity = null;
        if (o == null) {
            entity = param.fetchObject();
            if (entity == null) {
                return null;
            }
            wrappedCache.put(key, entity, this);
        } else {
            @SuppressWarnings("unchecked")
            T e = (T) o;
            entity = e;
        }
        return entity;
    }
    
    /**
     * 从缓存中取单个对对象，缓存的是对象
     * 
     * @param param
     * @param times 本地缓存时间(秒)
     *
     * @return
     */
    protected <T> T getEntityFromCache(CacheEntityParam<T> param,int times) {
        String key = param.fetchKey();
        Object o = wrappedCache.get(key,times);
        T entity = null;
        if (o == null) {
            entity = param.fetchObject();
            if (entity == null) {
                return null;
            }
            wrappedCache.put(key, entity, this);
        } else {
            @SuppressWarnings("unchecked")
            T e = (T) o;
            entity = e;
        }
        return entity;
    }    

    /**
     * 从缓存中取列表数据，缓存的是对象
     * 
     * @param param
     * @return
     */
    protected <T> List<T> getEntitiesFromCache(CacheEntitiesParam<T> param) {
        String key = param.fetchKey();
        Object o = wrappedCache.get(key);
        List<T> entities;
        if (o == null) {
            entities = param.fetchObjects();

            wrappedCache.put(key, entities, this);
        } else {
            @SuppressWarnings("unchecked")
            List<T> es = (List<T>) o; 
            entities = es;
        }
        return entities;
    }
    

    
    // ====================覆盖父类的方法 begin============================
    /**
     * 对数据库中的数据是否使用缓存
     * 
     * @return
     */
    public boolean isUseCache() {
        String start = System.getProperty("eis.db.cache.start");
        if (StringUtils.isBlank(start)) {
            start = "true";
        }
        return BooleanUtils.toBoolean(NumberUtils.toInt(start, 0)) || BooleanUtils.toBoolean(start);
    }
    // ====================覆盖父类的方法 end============================
}
