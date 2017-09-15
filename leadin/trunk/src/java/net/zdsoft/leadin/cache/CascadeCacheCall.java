/* 
 * @(#)CacheCallService.java    Created on Jun 22, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.cache;

/**
 * 级联的回调接口
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 22, 2010 8:09:47 PM $
 */
public interface CascadeCacheCall extends EntityCacheCall {
    /**
     * 取單個對象
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Jun 24, 2010 9:46:36 PM $
     */
    public interface CacheEntity<V> {
        /**
         * 单个对象
         * 
         * @param id
         * @return
         */
        public V fetchObject(String id);
    }

    /**
     * 缓存实体ID参数
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Jun 23, 2010 11:03:09 AM $
     */
    public interface CacheEntityIdParam<V> extends CacheEntityParam<V>, CacheEntity<V> {

    }

    /**
     * 缓存实体ID列表参数
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Jun 24, 2010 9:40:45 PM $
     */
    public interface CacheEntitiesIdParam<V> extends CacheEntitiesParam<V>, CacheEntity<V> {

    }
}
