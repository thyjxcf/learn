/* 
 * @(#)CacheUtils.java    Created on Jun 30, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.cache;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 30, 2010 1:46:56 PM $
 */
public class CacheUtils {
	/**
	 * 抽取缓存数据种类名称
	 * 因为base和基础service是继承关系，带base会造成缓存的key不一样。所以要去掉base关键字
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String extractCacheDataName(Class clazz) {
		return clazz.getSimpleName().replace("ServiceImpl", "").toLowerCase()
				.replace("base", "");
	}
}
