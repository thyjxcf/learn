/* 
 * @(#)AddSyncAdvice.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.sync.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.winupon.syncdata.basedata.property.MqEventType;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * 增加数据
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2010 5:25:31 PM $
 */
public class AddSyncAdvice extends AbstractSyncAdvice {

    public List<BaseEntity> getEntities(Object[] args) {
        List<BaseEntity> entities = new ArrayList<BaseEntity>();
        int argLen = args.length;
        if (argLen == 0)
            return entities;

        Object obj = args[0];
        if (obj instanceof BaseEntity) {
            BaseEntity e = (BaseEntity) obj;
            add(e, entities);
        } else if (obj instanceof BaseEntity[]) {
            BaseEntity[] arr = (BaseEntity[]) obj;
            for (BaseEntity e : arr) {
                add(e, entities);
            }
        } else if (obj instanceof Collection) {
            @SuppressWarnings("unchecked")
            Collection c = (Collection) obj;
            for (Object o : c) {
                Object[] objs = Arrays.copyOf(args, argLen);
                objs[0] = o;
                getEntities(objs);// 递归调用
            }
        }
        return entities;
    }

    private void add(BaseEntity e, List<BaseEntity> entities) {
        entities.add(e);
    }

    @Override
    public MqEventType getEventType() {
        return MqEventType.I;
    }

}
