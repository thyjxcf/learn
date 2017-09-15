/* 
 * @(#)UnitSyncServiceImpl.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.sync.service.impl;

import com.winupon.syncdata.basedata.entity.son.MqSysOption;

import net.zdsoft.eis.base.common.entity.SysOption;
import net.zdsoft.eis.base.data.service.BaseSysOptionService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2010 10:29:49 AM $
 */
public class SysOptionSyncServiceImpl extends AbstractHandlerTemplate<SysOption, MqSysOption> {
    private BaseSysOptionService baseSysOptionService;

    public void setBaseSysOptionService(BaseSysOptionService baseSysOptionService) {
        this.baseSysOptionService = baseSysOptionService;
    }

    @Override
    public void addData(SysOption e) throws BusinessErrorException {
        baseSysOptionService.addSysOption(e);
    }

    @Override
    public void deleteData(String id, EventSourceType eventSource) throws BusinessErrorException {
        baseSysOptionService.deleteSysOption(id, eventSource);
    }

    @Override
    public void updateData(SysOption e) throws BusinessErrorException {
        baseSysOptionService.updateSysOption(e);
    }

    @Override
    public SysOption fetchOldEntity(String id) {
        return null;
    }

}
