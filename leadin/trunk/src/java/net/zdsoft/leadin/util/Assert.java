/* 
 * @(#)Assert.java    Created on Aug 4, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 4, 2010 5:11:55 PM $
 */
public abstract class Assert {
    /**
     * 不能为null和空串
     * 
     * @param str
     * @throws IllegalArgumentException if the str is
     *             <code>null or empty string</code>
     */
    public static void notEmpty(String str) {
        notEmpty(str,
                "[Assertion failed] - this argument is required; it must not be null and empty string");
    }

    /**
     * 不能为null和空串
     * 
     * @param str
     * @param message 消息
     */
    public static void notEmpty(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 不能为null
     * 
     * @param object
     * @param message
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 不能为null
     * 
     * @param object
     */
    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }
}
