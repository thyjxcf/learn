/* 
 * @(#)UpdateSyncAdvice.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.sync.advice;

import com.winupon.syncdata.basedata.property.MqEventType;

/**
 * 更新数据
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2010 5:31:37 PM $
 */
public class UpdateSyncAdvice extends AddSyncAdvice {

    @Override
    public MqEventType getEventType() {
        return MqEventType.U;
    }

}
