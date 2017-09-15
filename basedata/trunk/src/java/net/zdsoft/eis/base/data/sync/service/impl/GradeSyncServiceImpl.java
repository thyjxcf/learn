/* 
 * @(#)UnitSyncServiceImpl.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.sync.service.impl;

import com.winupon.syncdata.basedata.entity.son.MqGrade;

import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.data.service.BaseGradeService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2010 10:29:49 AM $
 */
public class GradeSyncServiceImpl extends AbstractHandlerTemplate<Grade, MqGrade> {
    private BaseGradeService baseGradeService;


    public void setBaseGradeService(BaseGradeService baseGradeService) {
        this.baseGradeService = baseGradeService;
    }

    @Override
    public void addData(Grade e) throws BusinessErrorException {
        baseGradeService.addGrade(e);
    }

    @Override
    public void deleteData(String id, EventSourceType eventSource) throws BusinessErrorException {
        baseGradeService.deleteGrade(id, eventSource);

    }

    @Override
    public void updateData(Grade e) throws BusinessErrorException {
        baseGradeService.updateGrade(e);

    }

	@Override
	public Grade fetchOldEntity(String id) {
		return baseGradeService.getBaseGrade(id);
	}

}
