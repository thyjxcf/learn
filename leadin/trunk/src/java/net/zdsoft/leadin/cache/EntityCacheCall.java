/* 
 * @(#)CacheCallService.java    Created on Jun 22, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.cache;


/**
 * 核心的回调接口
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 22, 2010 8:09:47 PM $
 */
public interface EntityCacheCall extends CacheCall{

    /**
     * 缓存实体参数
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Jun 22, 2010 10:14:49 PM $
     */
    public interface CacheEntityParam<V> extends CacheObjectParam<V> {
    }

    /**
     * 缓存实体列表参数
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Jun 22, 2010 10:14:49 PM $
     */
    public interface CacheEntitiesParam<V> extends CacheObjectsParam<V> {
    }

}
