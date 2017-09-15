/* 
 * @(#)SyncAspectSupport.java    Created on Dec 22, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.sync.advice;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.winupon.amqp.rabbit.basedata.gateway.MqBaseDataGateway;
import com.winupon.syncdata.basedata.entity.MqBaseData;
import com.winupon.syncdata.basedata.property.MqEventType;

/**
 * 数据同步
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 22, 2010 2:29:01 PM $
 */
public class SyncAspectSupport {

    private static final ThreadLocal<SyncInfo> syncInfoHolder = new ThreadLocal<SyncInfo>();

    public static SyncInfo currentSyncInfo() {
        return syncInfoHolder.get();
    }

    public class SyncInfo {
        private MqBaseDataGateway mqBaseDataGateway;

        public SyncInfo(MqBaseDataGateway mqBaseDataGateway) {
            this.mqBaseDataGateway = mqBaseDataGateway;
        }

        /**
         * 此事务产生的所有消息数据
         */
        private List<SpecialDatas> transactionDatas = new ArrayList<SpecialDatas>();

        public void add(SpecialDatas datas) {
            transactionDatas.add(datas);
        }

        public List<SpecialDatas> getTransactionDatas() {
            return transactionDatas;
        }

        public void bindToThread() {
            syncInfoHolder.set(this);
            TransactionSynchronizationManager.registerSynchronization(new MessageSynchronization(
                    this));
        }

        public void cleanup() {
            syncInfoHolder.set(null);
        }

        public MqBaseDataGateway getMqBaseDataGateway() {
            return mqBaseDataGateway;
        }

    }

    /**
     * 某一类消息数据
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Dec 22, 2010 3:15:33 PM $
     */
    public static class SpecialDatas {
        private List<MqBaseData> mqDatas = new ArrayList<MqBaseData>();
        private MqEventType eventType;

        public SpecialDatas(List<MqBaseData> mqDatas, MqEventType eventType) {
            super();
            this.mqDatas = mqDatas;
            this.eventType = eventType;
        }

        public MqEventType getEventType() {
            return eventType;
        }

        public void setEventType(MqEventType eventType) {
            this.eventType = eventType;
        }

        public void setMqDatas(List<MqBaseData> mqDatas) {
            this.mqDatas = mqDatas;
        }

        public List<MqBaseData> getMqDatas() {
            return mqDatas;
        }

    }

    /**
     * 在事务提交后发送消息
     * 
     * @author zhaosf
     * @version $Revision: 1.0 $, $Date: Dec 23, 2010 3:01:24 PM $
     */
    private static class MessageSynchronization extends TransactionSynchronizationAdapter {
        private static final Logger log = LoggerFactory.getLogger(MessageSynchronization.class);

        private final SyncInfo syncInfo;

        private MessageSynchronization(SyncInfo syncInfo) {
            this.syncInfo = syncInfo;
        }

        @Override
        public void afterCommit() {
            try {
                // 事务提交后一起发送数据
                log.debug("send message");
                List<SpecialDatas> transactionDatas = syncInfo.getTransactionDatas();
                for (SpecialDatas datas : transactionDatas) {
                    syncInfo.getMqBaseDataGateway().sendBatchBaseData(datas.getMqDatas(),
                            datas.getEventType());
                }
            } catch (Exception e) {
                log.error("send message error: {}", e.getMessage());
            } finally {
                syncInfo.cleanup();
            }
        }

    }
}
