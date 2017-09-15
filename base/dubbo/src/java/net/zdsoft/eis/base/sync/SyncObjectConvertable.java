/* 
 * @(#)SyncObjectConvertable.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.sync;

import com.winupon.syncdata.basedata.entity.MqBaseData;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2010 10:35:55 AM $
 */
public interface SyncObjectConvertable<E extends BaseEntity, M extends MqBaseData> {
    /**
     * 将entity转换为mq对象
     * 
     * @param e
     * @param m
     */
    public void toMq(E e, M m);

    /**
     * 将mq对象转换为entity
     * 
     * @param m
     * @param e
     */
    public void toEntity(M m, E e);
}
