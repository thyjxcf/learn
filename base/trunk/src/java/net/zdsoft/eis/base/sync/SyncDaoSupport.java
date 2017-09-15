/* 
 * @(#)SyncDaoSupport.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.sync;

import com.winupon.syncdata.basedata.entity.MqBaseData;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseEntity;

public abstract class SyncDaoSupport<E extends BaseEntity, M extends MqBaseData> extends BaseDao<E> {
    private SyncObjectConvertable<E, M> converter;// 转换类

    public void setConverter(SyncObjectConvertable<E, M> converter) {
        this.converter = converter;
    }

    public SyncObjectConvertable<E, M> getConverter() {
        return converter;
    }

}
