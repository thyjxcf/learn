package net.zdsoft.eis.base.data.sync.service.impl;

import com.winupon.syncdata.basedata.entity.son.MqSchool;

import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.data.service.BaseSchoolService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

public class SchoolSyncServiceImpl extends
		AbstractHandlerTemplate<BaseSchool, MqSchool> {

	private BaseSchoolService baseSchoolService;

	public void setBaseSchoolService(BaseSchoolService baseSchoolService) {
		this.baseSchoolService = baseSchoolService;
	}

	@Override
	public void addData(BaseSchool e) throws BusinessErrorException {
		baseSchoolService.addSchool(e);

	}

	@Override
	public void deleteData(String id, EventSourceType eventSource) throws BusinessErrorException {
		baseSchoolService.deleteSchool(id, eventSource);

	}

	@Override
	public void updateData(BaseSchool e) throws BusinessErrorException {
		baseSchoolService.updateSchool(e);

	}

	@Override
	public BaseSchool fetchOldEntity(String id) {
		return baseSchoolService.getBaseSchool(id);
		
	}

}
