/* 
 * @(#)DeleteSyncAdvice.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.sync.advice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.winupon.syncdata.basedata.property.MqEventType;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * 删除数据
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2010 5:31:37 PM $
 */
public class DeleteSyncAdvice extends AbstractSyncAdvice {

    @Override
    public List<BaseEntity> getEntities(Object[] args) {
        List<BaseEntity> entities = new ArrayList<BaseEntity>();
        Object obj = args[0];
        if (obj instanceof String) {
            String id = (String) obj;
            add(id, entities);
        } else if (obj instanceof String[]) {
            String[] arr = (String[]) obj;
            for (String id : arr) {
                add(id, entities);
            }
        } else if (obj instanceof Collection) {
            @SuppressWarnings("unchecked")
            Collection<String> c = (Collection<String>) obj;
            for (String id : c) {
                add(id, entities);
            }
        }
        return entities;
    }

    private void add(String id, List<BaseEntity> entities) {
        BaseEntity e = new BaseEntity();
        e.setId(id);
        entities.add(e);
    }

    @Override
    public MqEventType getEventType() {
        return MqEventType.D;
    }

}
