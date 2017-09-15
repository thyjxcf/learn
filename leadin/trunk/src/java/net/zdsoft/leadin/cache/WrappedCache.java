package net.zdsoft.leadin.cache;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alisoft.xplatform.asf.cache.IMemcachedCache;

public class WrappedCache {

    private static Logger log = LoggerFactory.getLogger(WrappedCache.class);
    private IMemcachedCache memCache;

    public void setMemCache(IMemcachedCache memCache) {
        this.memCache = memCache;
    }
    
    public IMemcachedCache getMemCache() {
        return memCache;
    }


    /**
     * 清除缓存
     * 
     * @param key
     */
    private void remove(String key) {
        remove(key, (CacheCall) null);
    }

    /**
     * 清除缓存
     * 
     * @param key
     */
    public void remove(String key, CacheCall call) {
        log.debug("清除缓存，key[" + key + "]");
        memCache.remove(key);
        if (null != call) {
            String mainKey = call.fetchCacheIndexKey();
            removeKeyFromCache(mainKey, key);
        }
    }

    /**
     * 增加到缓存中
     * 
     * @param key
     * @param value
     * @param 回調用接口
     * @return
     */
    public boolean add(String key, Object value, CacheCall call) {
        if (value == null)
            return false;
        log.debug("增加缓存，key[" + key + "]");
        boolean sign = memCache.add(key, value);
        if (null != call && sign) {
            String indexKey = call.fetchCacheIndexKey();
            putKeyInCache(indexKey, key);
        }
        return sign;
    }
    
    /**
     * 在缓存中递增
     * 
     * @param key
     * @param value
     * @return
     */
    public long incr(String key, long value) {
        log.debug("在缓存中递增，key[" + key + "]");
        long sign = memCache.incr(key, value);
        return sign;
    }
    
    /**
     * 增加到缓存中
     * 
     * @param key
     * @param value
     * @return
     */
    private Object put(String key, Object value) {
        return put(key, value, (CacheCall) null);
    }

    /**
     * 增加到缓存中
     * 
     * @param key
     * @param value
     * @param 回調用接口
     * @return
     */
    public Object put(String key, Object value, CacheCall call) {
        if (value == null)
            return null;
        log.debug("设置缓存，key[" + key + "]");
        Object obj = memCache.put(key, value);
        if (null != call) {
            String indexKey = call.fetchCacheIndexKey();
            putKeyInCache(indexKey, key);
        }
        return obj;
    }

    /**
     * 將key緩存起來
     * 
     * @param indexKey
     * @param key
     */
    private void putKeyInCache(String indexKey, String key) {
        Set<String> ids = getKeysFromCache(indexKey);
        ids.add(key);
        put(indexKey, ids);
    }

    /**
     * 將key去除
     * 
     * @param indexKey
     * @param key
     */
    private void removeKeyFromCache(String indexKey, String key) {
        Set<String> ids = getKeysFromCache(indexKey);
        ids.remove(key);
        put(indexKey, ids);
    }

    /**
     * 根据mainKey取所有key
     * 
     * @param indexKey
     * @return
     */
    @SuppressWarnings("unchecked")
    private Set<String> getKeysFromCache(String indexKey) {
        Object o = get(indexKey);
        if (o != null) {
            return (Set<String>) o;
        } else {
            return new HashSet<String>();
        }
    }

    /**
     * 清空某類缓存
     */
    public void clearCache(CacheCall call) {
        String indexKey = call.fetchCacheIndexKey();
        Set<String> ids = getKeysFromCache(indexKey);
        for (String id : ids) {
            remove(id);
        }
        remove(indexKey);
    }

    /**
     * 增加到缓存中
     * 
     * @param key
     * @param value
     * @param 回調用接口
     * @return
     */
    public Object put(String key, Object value, int time, CacheCall call) {
        if (value == null)
            return null;
        log.debug("设置缓存，key[" + key + "]");
        Object obj = memCache.put(key, value, time);
        if (null != call) {
            String indexKey = call.fetchCacheIndexKey();
            putKeyInCache(indexKey, key);
        }
        return obj;
    }

    /**
     * 增加到缓存中
     * 
     * @param key
     * @param value
     * @param time 有效期
     * @return
     */
    @SuppressWarnings("unused")
    private Object put(String key, Object value, Date time) {
        if (value == null)
            return null;
        log.debug("设置缓存，key[" + key + "]");
        return memCache.put(key, value, time);
    }

    /**
     * 从缓存中取出数据
     * 
     * @param key
     * @return
     */
    public Object get(String key) {
        log.debug("读取缓存，key[" + key + "]");
        Object o = memCache.get(key);
        return o;
    }

    /**
     * 从缓存中取出数据
     * 
     * @param key
     * @param time 本地缓存失效时间（秒）
     * @return
     */
    public Object get(String key, int time) {
        log.debug("读取缓存，key[" + key + "]");
        Object o = memCache.get(key, time);
        return o;
    }

    /**
     * 获取多个keys对应的key&value Entrys
     * 
     * @param keys
     * @return
     */
    public Map<String, Object> getMultiMap(String[] keys) {
        log.debug("读取多个缓存，key.size[" + keys.length + "]");
        Map<String, Object> map = (Map<String, Object>) memCache.getMulti(keys);
        if (map == null)
            return null;
        for (Object o : map.values()) {
            if (o != null) {
                map.remove(o);
            }
        }
        return map;
    }

    /**
     * 获取多个keys对应的值
     * 
     * @param keys
     * @return
     */
    protected Object[] getMultiArray(String[] keys) {
        log.debug("读取多个缓存，key.size[" + keys.length + "]");
        Object[] os = memCache.getMultiArray(keys);
        if (os == null)
            return null;
        for (Object o : os) {
            if (o != null) {
                ArrayUtils.removeElement(os, o);
            }
        }
        return os;
    }

    /**
     * 这个接口返回的Key如果采用fast模式，
     * 那么返回的key可能已经被清除或者失效，但是在内存中还有痕迹，如果是非fast模式，那么就会精确返回，但是效率较低
     * 
     * @param 是否需要去交验key是否存在
     * @return
     */
    protected Set<String> keySet(boolean fast) {
        return memCache.keySet(fast);
    }

    /**
     * 清除所有缓存，暂不用，由于与其它系统共用缓存时，会将别的系统的缓存一起清除
     */
    @SuppressWarnings("unused")
    @Deprecated
    private void clear() {
        memCache.clear();
    }

    /**
     * 是否包含key
     * 
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        try {
            return memCache.containsKey(key);
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
    }
}
