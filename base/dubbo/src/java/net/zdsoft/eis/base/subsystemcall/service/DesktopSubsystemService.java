package net.zdsoft.eis.base.subsystemcall.service;

import net.zdsoft.eis.base.subsystemcall.entity.DesktopAppDto;

/**
 * @author chens  2016-7-8下午2:15:54
 */
public interface DesktopSubsystemService {

	public DesktopAppDto getDesktopApp(String unitId, int unitClass, String code);
	
}
