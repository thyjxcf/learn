/* 
 * @(#)UnitSyncServiceImpl.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.sync.service.impl;

import com.winupon.syncdata.basedata.entity.son.MqUnit;

import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2010 10:29:49 AM $
 */
public class UnitSyncServiceImpl extends AbstractHandlerTemplate<BaseUnit, MqUnit> {
    private BaseUnitService baseUnitService;

    public void setBaseUnitService(BaseUnitService baseUnitService) {
        this.baseUnitService = baseUnitService;
    }

    @Override
    public void addData(BaseUnit e) throws BusinessErrorException {
        baseUnitService.addUnitFromMq(e);
    }

    @Override
    public void deleteData(String id, EventSourceType eventSource) throws BusinessErrorException {
        baseUnitService.deleteUnit(id, eventSource);
    }

    @Override
    public void updateData(BaseUnit e) throws BusinessErrorException {
        baseUnitService.updateUnitFromMq(e);
    }

	@Override
	public BaseUnit fetchOldEntity(String id) {
		return baseUnitService.getBaseUnit(id);
	}

}
