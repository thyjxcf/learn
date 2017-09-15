/* 
 * @(#)BusinessInterceptor.java    Created on Dec 22, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.sync.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winupon.amqp.rabbit.basedata.gateway.MqBaseDataGateway;

import net.zdsoft.eis.base.deploy.SystemDeployService;

/**
 * 业务层拦截
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 22, 2010 1:59:25 PM $
 */
public class BusinessSyncInterceptor extends SyncAspectSupport implements MethodInterceptor {
    private static final Logger log = LoggerFactory.getLogger(BusinessSyncInterceptor.class);

    private SystemDeployService systemDeployService;
    private MqBaseDataGateway mqBaseDataGateway;

    public void setMqBaseDataGateway(MqBaseDataGateway mqBaseDataGateway) {
        this.mqBaseDataGateway = mqBaseDataGateway;
    }

    public void setSystemDeployService(SystemDeployService systemDeployService) {
        this.systemDeployService = systemDeployService;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
         boolean opened = systemDeployService.isOpenMq();
         if (opened == false) {
         return invocation.proceed();
         }

        SyncInfo syncInfo = currentSyncInfo();
        if (null == syncInfo) {
            log.debug("create syncInfo and bind to thread");
            syncInfo = new SyncInfo(mqBaseDataGateway);
            syncInfo.bindToThread();
        }

        log.debug("execute method");
        Object rval = invocation.proceed();

        return rval;
    }

}
