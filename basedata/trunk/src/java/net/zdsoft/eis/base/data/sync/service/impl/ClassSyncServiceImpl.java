/* 
 * @(#)UnitSyncServiceImpl.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.sync.service.impl;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.data.service.BaseClassService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

import com.winupon.syncdata.basedata.entity.son.MqClass;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2010 10:29:49 AM $
 */
public class ClassSyncServiceImpl extends AbstractHandlerTemplate<BasicClass, MqClass> {
    private BaseClassService baseClassService;

    public void setBaseClassService(BaseClassService baseClassService) {
        this.baseClassService = baseClassService;
    }

    @Override
    public void addData(BasicClass e) throws BusinessErrorException {
        // 置默认值
        e.setSubschoolid(null);
        baseClassService.addClass(e);
    }

    @Override
    public void deleteData(String id, EventSourceType eventSource) throws BusinessErrorException {
        baseClassService.deleteClass(id, eventSource);

    }

    @Override
    public void updateData(BasicClass e) throws BusinessErrorException {
        baseClassService.updateClass(e);

    }

	@Override
	public BasicClass fetchOldEntity(String id) {
		return baseClassService.getClass(id);
	}

}
