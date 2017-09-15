/* 
 * @(#)SyncObjectConverter.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.sync;

import java.util.Date;

import com.winupon.syncdata.basedata.entity.MqBaseData;

import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.leadin.util.Assert;

/**
 * 转换工具类
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2010 11:25:20 AM $
 */
public class SyncObjectConverter {
    public static <E extends BaseEntity, M extends MqBaseData> void toEntity(M m, E e,
            SyncObjectConvertable<E, M> converter) {
        Assert.notNull(m);
        Assert.notNull(e);

        e.setEventSource(EventSourceType.OTHER);
        e.setModifyTime(new Date());
        converter.toEntity(m, e);
    }

    public static <E extends BaseEntity, M extends MqBaseData> void toMq(E e, M m,
            SyncObjectConvertable<E, M> converter) {
        Assert.notNull(e);
        Assert.notNull(m);

        converter.toMq(e, m);
    }
}
