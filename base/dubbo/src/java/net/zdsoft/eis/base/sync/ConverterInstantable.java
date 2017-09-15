/* 
 * @(#)ConverterInstantable.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.sync;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2010 1:37:24 PM $
 */
public interface ConverterInstantable<T> {
    /**
     * 得到实例
     * 
     * @return
     */
    public T getInstance();
}
