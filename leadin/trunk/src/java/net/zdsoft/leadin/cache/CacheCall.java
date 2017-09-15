/* 
 * @(#)CacheCallService.java    Created on Jun 22, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.cache;

import java.util.List;
import java.util.Map;

/**
 * 核心的回调接口
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 22, 2010 8:09:47 PM $
 */
public interface CacheCall {
    /**
     * 取缓存类的主key，即key的key
     */
    public String fetchCacheIndexKey();

    /**
     * 缓存key
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Jun 22, 2010 10:14:49 PM $
     */
    public interface CacheKey {
        /**
         * key
         * 
         * @return
         */
        public String fetchKey();
    }

    /**
     * 缓存对象参数
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Jun 22, 2010 10:14:49 PM $
     */
    public interface CacheObjectParam<V> extends CacheKey {
        /**
         * 對象
         * 
         * @return
         */
        public V fetchObject();
    }

    /**
     * 二级缓存对象
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Jun 22, 2010 10:14:49 PM $
     */
    public interface TwoCacheObjectParam<V> extends CacheObjectParam<V> {
        /**
         * 二级key
         * @return
         */
        public String fetchTwoKey();
    }
    
    /**
     * 缓存对象列表参数
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Jun 22, 2010 10:14:49 PM $
     */
    public interface CacheObjectsParam<V> extends CacheKey {

        /**
         * 對象
         * 
         * @return
         */
        public List<V> fetchObjects();
    }

    /**
     * 缓存对象列表参数
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Jun 22, 2010 10:14:49 PM $
     */
    public interface CacheObjectMapParam<K, V> extends CacheKey {

        /**
         * 對象
         * 
         * @return
         */
        public Map<K, V> fetchObjects();
    }
}
