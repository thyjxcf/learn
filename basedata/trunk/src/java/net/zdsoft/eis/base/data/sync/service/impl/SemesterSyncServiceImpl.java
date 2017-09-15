/* 
 * @(#)UnitSyncServiceImpl.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.sync.service.impl;

import net.zdsoft.eis.base.common.entity.Semester;
import net.zdsoft.eis.base.data.service.BaseSemesterService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

import com.winupon.syncdata.basedata.entity.son.MqSemester;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2010 10:29:49 AM $
 */
public class SemesterSyncServiceImpl extends
		AbstractHandlerTemplate<Semester, MqSemester> {
	private BaseSemesterService baseSemesterService;

	public void setBaseSemesterService(BaseSemesterService baseSemesterService) {
		this.baseSemesterService = baseSemesterService;
	}

	@Override
	public void addData(Semester e) throws BusinessErrorException {
		// 置默认值
		if (0 == e.getClasshour())
			e.setClasshour((short) 45);
		if (null == e.getRegisterdate())
			e.setRegisterdate(e.getSemesterBegin());
		baseSemesterService.addSemester(e);
	}

	@Override
	public void deleteData(String id, EventSourceType eventSource)
			throws BusinessErrorException {
		baseSemesterService.deleteSemester(new String[] { id }, eventSource);
	}

	@Override
	public void updateData(Semester e) throws BusinessErrorException {
		baseSemesterService.updateSemester(e);

	}

	@Override
	public Semester fetchOldEntity(String id) {
		return baseSemesterService.getSemester(id);
	}

}
