package net.zdsoft.eis.base.data.sync.service.impl;

import com.winupon.syncdata.basedata.entity.son.MqWare;

import net.zdsoft.eis.base.common.entity.Ware;
import net.zdsoft.eis.base.data.service.BaseWareService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

public class WareSyncServiceImpl extends AbstractHandlerTemplate<Ware, MqWare> {
	private BaseWareService baseWareService;

	public void setBaseWareService(BaseWareService baseWareService) {
		this.baseWareService = baseWareService;
	}

	@Override
	public void addData(Ware e) throws BusinessErrorException {
		baseWareService.addWare(e);

	}

	@Override
	public void deleteData(String id, EventSourceType eventSource) throws BusinessErrorException {
		baseWareService.deleteWare(new String[]{id}, eventSource);
	}

	@Override
	public Ware fetchOldEntity(String id) {
		return null;
	}

	@Override
	public void updateData(Ware e) throws BusinessErrorException {
		baseWareService.updateWare(e);

	}

}
