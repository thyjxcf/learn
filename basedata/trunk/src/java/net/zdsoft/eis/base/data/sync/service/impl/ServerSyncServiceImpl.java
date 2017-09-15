package net.zdsoft.eis.base.data.sync.service.impl;

import com.winupon.syncdata.basedata.entity.son.MqServer;

import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.data.service.BaseServerService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

public class ServerSyncServiceImpl extends AbstractHandlerTemplate<Server, MqServer> {
private BaseServerService baseServerService;


	public void setBaseServerService(BaseServerService baseServerService) {
	this.baseServerService = baseServerService;
}

	@Override
	public void addData(Server e) throws BusinessErrorException {
		baseServerService.addServer(e);
		
	}

	@Override
	public void deleteData(String id, EventSourceType eventSource) throws BusinessErrorException {
		baseServerService.deleteServer(new String[]{id}, eventSource);
		
	}

	@Override
	public Server fetchOldEntity(String id) {
		return null;
	}

	@Override
	public void updateData(Server e) throws BusinessErrorException {
		baseServerService.updateServer(e);
		
	}

}
