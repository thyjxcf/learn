/* 
 * @(#)DefaultCacheManagerService.java    Created on Jun 23, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.cache;

import java.util.List;
import java.util.Map;

//import org.apache.commons.collections.map.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 23, 2010 3:13:39 PM $
 */
public abstract class AbstractCacheManager implements CacheManager {
    private static final Logger log = LoggerFactory.getLogger(AbstractCacheManager.class);

    protected WrappedCache wrappedCache;
    
    public void setWrappedCache(WrappedCache wrappedCache) {
        this.wrappedCache = wrappedCache;
    }
    public AbstractCacheManager() {
        CacheHelper.register(this);
    }    

    /**
     * 是否不使用缓存
     * 
     * @return
     */
    public boolean isUseCache() {
        return true;
    }    

    /**
     * 是否不使用缓存
     * 
     * @return
     */
    private boolean isNotUseCache() {
        return !(isUseCache());
    }
    
    
    public void clearCache() {
        if (isNotUseCache())
            return;
        
        wrappedCache.clearCache(this);
    }

    public void initCache() {
        if (isNotUseCache())
            return;
        
        log.info(this.getClass() + ": default init cache method");
    }

    /**
     * 取缓存，在业务使用过程中不要使用单独取缓存的方法。只提供在get缓存时发现不存在，则放入，以方便管理缓存（如进行清除）
     * @param key
     * @return
     */
    protected Object getFromCache(String key) {
        if (isNotUseCache())
            return null;
        
        return wrappedCache.get(key);
    }
    
    /**
     * 取缓存
     * @param key
     * @param time
     * @return
     */
    public Object getFromCache(String key, int time){
        if (isNotUseCache())
            return null;
        
        return wrappedCache.get(key, time);
    }
    
    /**
     * 取缓存
     * @param keys
     * @return
     */
    protected Map<String, Object> getMultiMapFromCache(String[] keys) {
        if (isNotUseCache())
            return null;
        
        return wrappedCache.getMultiMap(keys);
    }
    
    /**
     * 放入缓存
     * 
     * @param <T>
     * @param key
     * @param value
     */
    protected <T> void putInCache(String key, T value) {
        if (isNotUseCache())
            return;
        
        wrappedCache.put(key, value, this);
    }
    
    /**
     * 放入缓存
     * 
     * @param <T>
     * @param key
     * @param value
     */
    protected <T> void putInCache(String key, T value, int timeout) {
        if (isNotUseCache())
            return;
        
        wrappedCache.put(key, value, timeout, this);
    }

    protected <T> void removeFromCache(String key) {        
        wrappedCache.remove(key, this);
    }

    /**
     * 从缓存中取单个对对象，缓存的是对象
     * 
     * @param param
     * @return
     */    
    public <T> T getObjectFromCache(CacheObjectParam<T> param) {
        String key = param.fetchKey();
        Object o = getFromCache(key);
        T object = null;
        if (o == null) {
            object = param.fetchObject();
            if (object == null) {
                return null;
            }
            putInCache(key, object);
        } else {
            @SuppressWarnings("unchecked")
            T t = (T) o;
            object = t;
        }
        return object;
    }

    /**
     * 从缓存中取单个对对象，缓存的是对象列表
     * 
     * @param param
     * @return
     */
    public <T> List<T> getObjectsFromCache(CacheObjectsParam<T> param) {
        String key = param.fetchKey();
        Object o = getFromCache(key);
        List<T> objects;
        if (o == null) {
            objects = param.fetchObjects();
            putInCache(key, objects);
        } else {
            @SuppressWarnings("unchecked")
            List<T> list = (List<T>) o;
            objects = list;
        }
        return objects;
    }
    
    /**
     * 从缓存中取单个对对象，缓存的是对象列表
     * 
     * @param param
     * @return
     */
    public <K, V> Map<K, V> getObjectMapFromCache(CacheObjectMapParam<K, V> param) {
        String key = param.fetchKey();
        Object o = getFromCache(key);
        Map<K, V> objects;
        if (o == null) {
            objects = param.fetchObjects();
            putInCache(key, objects);
        } else {
            @SuppressWarnings("unchecked")
            Map<K, V> map = (Map<K, V>) o;
            objects = map;
        }
        return objects;
    }
    
//    /**
//     * 从缓存中取出值，并累加后再放入缓存中
//     * 在集群环境下会重复
//     * @param param
//     * @param step 步长
//     * @return
//     */
//    @Deprecated
//    protected synchronized int incrementInCacheNoCluster(CacheObjectParam<Integer> param, int step) {
//        String key = param.fetchKey();
//        Object o = wrappedCache.get(key);
//        int seq = 0;
//        if (o == null) {
//            Integer object = param.fetchObject();
//            if (object == null) {
//                seq = 0;
//            }else{
//                seq = object.intValue();
//            }
//        } else {
//            seq = (Integer) o;
//        }
//
//        wrappedCache.put(key, seq + step, this);
//        return seq;
//    }

//    /**
//     * 二级缓存，从缓存中取出Map，将twokey的值累加后放入Map,再将Map放入缓存中
//     * 在集群环境下会重复
//     * 
//     * @param param
//     * @param step 步长
//     * @return
//     */
//    @Deprecated
//    protected synchronized int incrementInCacheNoCluster(TwoCacheObjectParam<Integer> param, int step) {
//        LRUMap map = null;
//
//        final String key = param.fetchKey();
//        Object o1 = wrappedCache.get(key);
//        if (o1 == null) {
//            map = new LRUMap(1000);
//        } else {
//            map = (LRUMap) o1;
//        }
//
//        String twokey = param.fetchTwoKey();
//        Object o = map.get(twokey);
//        int seq = 0;
//        if (o == null) {
//            Integer object = param.fetchObject();
//            if (object == null) {
//                seq = 1;
//            }else{
//                seq = object.intValue();
//            }
//        } else {
//            seq = (Integer) o;
//        }
//        map.put(twokey, seq + step);
//        wrappedCache.put(key, map, this);
//        return seq;
//    }
    
    private static final long INIT_INCR_RETURN = -1;// 初始增量返回值
    /**
     * 从缓存中取出值，并累加后再放入缓存中 在集群环境下也不会重复
     * 
     * @param param
     * @param step 步长
     * @return
     */
    protected int incrementInCache(CacheObjectParam<Integer> param, int step) {
        String key = param.fetchKey();

        long seq = INIT_INCR_RETURN;
        while (seq == INIT_INCR_RETURN) {
            try {
                seq = wrappedCache.incr(key, step);
                if (seq == INIT_INCR_RETURN) {
                    synchronized (this) {
                        int init = 0;
                        Integer object = param.fetchObject();
                        if (object == null) {
                            init = 0;
                        } else {
                            init = object.intValue();
                        }
                        wrappedCache.add(key, String.valueOf(init), this);// 必须以string形式保存，否则初始值置不进去
                    }
                }
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
        return Long.valueOf(seq).intValue();
    }

}
