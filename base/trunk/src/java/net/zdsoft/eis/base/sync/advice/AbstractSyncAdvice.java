/* 
 * @(#)AbstractSyncAdvice.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.sync.advice;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;

import com.winupon.syncdata.basedata.entity.MqBaseData;
import com.winupon.syncdata.basedata.property.MqEventType;

import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.sync.SyncDaoSupport;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;
import net.zdsoft.eis.base.sync.SyncObjectConverter;
import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * 同步通知
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2010 3:45:22 PM $
 */
public abstract class AbstractSyncAdvice extends SyncAspectSupport implements AfterReturningAdvice {
    private static final Logger log = LoggerFactory.getLogger(AbstractSyncAdvice.class);

    private SystemDeployService systemDeployService;

    public void setSystemDeployService(SystemDeployService systemDeployService) {
        this.systemDeployService = systemDeployService;
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target)
            throws Throwable {
        boolean opened = systemDeployService.isOpenMq();
        if (opened == false) {
            return;
        }

        // mq entity
        if (!(target instanceof SyncDaoSupport)) {
            return;
        }

        @SuppressWarnings("unchecked")
        Class<MqBaseData> entityClass = (Class<MqBaseData>) ((ParameterizedType) target.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];

        log.debug("组织 {} 消息", entityClass.getSimpleName());

        // 转换器
        @SuppressWarnings("unchecked")
        SyncDaoSupport<BaseEntity, MqBaseData> syncDao = (SyncDaoSupport<BaseEntity, MqBaseData>) target;
        SyncObjectConvertable<BaseEntity, MqBaseData> converter = syncDao.getConverter();

        // 组织数据
        List<MqBaseData> baseDataList = new ArrayList<MqBaseData>();
        List<BaseEntity> entities = getEntities(args);
        for (BaseEntity e : entities) {
            if (!(e.isSendMq()))
                continue;// 如果是从MQ接收的数据，则不再发送

            MqBaseData m = entityClass.newInstance();
            SyncObjectConverter.toMq(e, m, converter); // 转换数据
            baseDataList.add(m);
        }

        SyncInfo syncInfo = currentSyncInfo();
        if (null == syncInfo) {
            log.error("current thread's syncInfo is null");
        } else {
            log.debug("add message data");
            syncInfo.add(new SpecialDatas(baseDataList, getEventType()));
        }

    }

    /**
     * 获得entity
     * 
     * @param args
     * @return
     */
    public abstract List<BaseEntity> getEntities(Object[] args);

    /**
     * 获得类型
     * 
     * @return
     */
    public abstract MqEventType getEventType();

}
