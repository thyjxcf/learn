/* 
 * @(#)CacheManagerService.java    Created on Jun 21, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.cache;

/**
 * 缓存管理类
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 21, 2010 2:23:29 PM $
 */
public interface CacheManager extends EntityCacheCall {
    /**
     * 初始化缓存
     */
    public void initCache();

    /**
     * 清空缓存
     */
    public void clearCache();
    
    /**
     * 是否使用缓存
     * 
     * @return
     */
    public boolean isUseCache();

}
