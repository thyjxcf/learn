/* 
 * @(#)CacheHelperAction.java    Created on Jun 30, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.alisoft.xplatform.asf.cache.memcached.MemcacheStats;
import com.alisoft.xplatform.asf.cache.memcached.MemcachedCache;
import com.alisoft.xplatform.asf.cache.memcached.MemcachedResponse;

import net.zdsoft.leadin.client.LeadinAction;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 30, 2010 1:30:25 PM $
 */
public class CacheHelperAction extends LeadinAction {
    private static final long serialVersionUID = 1L;

    private WrappedCache wrappedCache;

    /**
     * 缓存对象信息
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Jun 30, 2010 1:59:21 PM $
     */
    public class CacheObject {
        private String dataName;// 缓存对象类型
        private String indexKey;// 缓存主键
        private String status;// 缓存状态
        private boolean useCache;// 是否启用缓存

        public CacheObject(String dataName, String indexKey, String status, boolean useCache) {
            this.dataName = dataName;
            this.indexKey = indexKey;
            this.status = status;
            this.useCache = useCache;
        }

        public String getDataName() {
            return dataName;
        }

        public String getIndexKey() {
            return indexKey;
        }

        public String getStatus() {
            return status;
        }

        public boolean isUseCache() {
            return useCache;
        }        
    }

    /**
     * 缓存总体信息
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Jun 30, 2010 1:59:29 PM $
     */
    public class CacheStat {
        private MemcachedResponse response;// 响应时间
        private MemcacheStats[] stats;// 状态
        @SuppressWarnings("unchecked")
        private Map items;// Items的存储情况

        public MemcachedResponse getResponse() {
            return response;
        }

        public void setResponse(MemcachedResponse response) {
            this.response = response;
        }

        public MemcacheStats[] getStats() {
            return stats;
        }

        public void setStats(MemcacheStats[] stats) {
            this.stats = stats;
        }

        @SuppressWarnings("unchecked")
        public Map getItems() {
            return items;
        }

        @SuppressWarnings("unchecked")
        public void setItems(Map items) {
            this.items = items;
        }

    }

    private List<CacheObject> cacheObjects = new ArrayList<CacheObject>();
    private CacheStat cacheStat;

    /**
     * 获得cache情况
     */
    public void cacheStat() {
        // 缓存对象信息
        Map<String, CacheManager> cacheObectMap = CacheHelper.getCacheObjectMap();
        Set<String> keys = cacheObectMap.keySet();
        for (String indexKey : keys) {
            CacheManager cacheManager = cacheObectMap.get(indexKey);
            String dataName = CacheUtils.extractCacheDataName(cacheManager.getClass());
            String status = String.valueOf(wrappedCache.containsKey(indexKey));
            boolean useCache = cacheManager.isUseCache();
            CacheObject obj = new CacheObject(dataName, indexKey, status, useCache);
            cacheObjects.add(obj);
        }

        // 缓存总体信息
        IMemcachedCache memCache = wrappedCache.getMemCache();
        cacheStat = new CacheStat();
        cacheStat.setItems(memCache.statsItems());
        cacheStat.setResponse(memCache.statCacheResponse());
        cacheStat.setStats(memCache.stats());
    }

    public String execute() throws Exception {
        cacheStat();
        return SUCCESS;
    }

    /**
     * 初始化缓存
     * 
     * @return
     * @throws Exception
     */
    public boolean initCache(String indexKey) {
        try {
            // 缓存对象信息
            Map<String, CacheManager> cacheObectMap = CacheHelper.getCacheObjectMap();

            if (StringUtils.isNotBlank(indexKey)) {
                if ("eis".equals(indexKey)) {
                    // eis所有缓存
                    CacheHelper.initCache();
                } else {
                    // 某类缓存
                    CacheManager cacheManager = cacheObectMap.get(indexKey);
                    cacheManager.initCache();
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
        return true;
    }

    /**
     * 清除缓存
     * 
     * @return
     * @throws Exception
     */
    public boolean clearCache(String indexKey) {
        try {
            // 缓存对象信息
            Map<String, CacheManager> cacheObectMap = CacheHelper.getCacheObjectMap();

            // 全部缓存
            if (StringUtils.isBlank(indexKey)) {
                wrappedCache.getMemCache().clear();
            } else {
                if ("eis".equals(indexKey)) {
                    CacheHelper.clearCache();
                } else {
                    // 某类缓存
                    CacheManager cacheManager = cacheObectMap.get(indexKey);
                    cacheManager.clearCache();
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
        return true;
    }

    /**
     * 测试缓存
     */
    private static final Log log1 = LogFactory.getLog(CacheHelperAction.class);
    public void testCache(){
        final IMemcachedCache memCache = wrappedCache.getMemCache();
        @SuppressWarnings("unused")
        final MemcachedCache cache = (MemcachedCache)memCache;
        final String key = "eis1.incr7";
        
        Runnable b = new Runnable() {
            @Override
            public void run() {
                long value = -1;
                while (value == -1) {
                    try{
                        value = memCache.incr(key, 2);                        
                        if (value == -1) {
//                            cache.getCacheClient(key).setPrimitiveAsString(true);
                            boolean sign = memCache.add(key, String.valueOf(10));
                            if (sign) {
                                value = Integer.parseInt((String) memCache.get(key));
                                System.out.println("!!!!!!!!!!!!!!777777=" + value);
                                
//                                cache.getCacheClient(key).setPrimitiveAsString(true);
//                                memCache.put(key, 20);
////                                value = (Integer) memCache.get(key);
//                                value = Integer.parseInt((String) memCache.get(key));
//                                System.out.println("!!!!!!!!!!!!!!888888=" + value);
                                
//                                memCache.storeCounter(key, 30);
//                                if (memCache.get(key) != null) {
//                                    value = (Integer) memCache.get(key);
//                                    System.out.println("!!!!!!!!!!!!!!999999=" + value);
//                                }
                                
                                value = memCache.incr(key, 2);
                                System.out.println("!!!!!!!!!!!!!!!!!!!!=" + value);
                            } 
                        }            
                    }catch (Exception e) {
                        System.out.println(e);
                    }
                }                    
          
                System.out.println(Thread.currentThread().getName() + "====" + value);
                log1.error(Thread.currentThread().getName() + "====" + value); 
            }
        };

        for (int i = 0; i < 1; i++) {
            new Thread(b, "t" + (i + 1)).start();
        }
    }
    
    public List<CacheObject> getCacheObjects() {
        return cacheObjects;
    }

    public void setWrappedCache(WrappedCache wrappedCache) {
        this.wrappedCache = wrappedCache;
    }

    public CacheStat getCacheStat() {
        return cacheStat;
    }

}
