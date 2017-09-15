package net.zdsoft.eis.system.subsystemcall.service.impl;

import net.zdsoft.eis.base.subsystemcall.service.SystemSubsystemService;
import net.zdsoft.eis.remote.service.RemoteSystemAppService;
import net.zdsoft.eis.system.frame.serial.SerialManager;

public class SystemSubsystemServiceImpl implements SystemSubsystemService {

	private SerialManager  serialManager;
	private RemoteSystemAppService remoteSystemAppService;
	
    public int getUnitCountLimit() {
        return serialManager.getUnitCountLimit();
    }

    public int getUserCountLimit() {
        return serialManager.getUserCountLimit();
    }
    
    public String officeAuth(String remoteParam){
    	return remoteSystemAppService.officeAuth(remoteParam);
    }
    
    public void setRemoteSystemAppService(
			RemoteSystemAppService remoteSystemAppService) {
		this.remoteSystemAppService = remoteSystemAppService;
	}

	public void setSerialManager(SerialManager serialManager) {
		this.serialManager = serialManager;
	}

	public SerialManager getSerialManager() {
		return serialManager;
	}

}
