package net.zdsoft.eis.base.subsystemcall.service;

import java.util.List;

public interface NewElectiveSubsystemService {
	
	public List<String> getClassByUidSemesterStuId(String unitId,
			String acadyear, String semester, String stuid);
	
	public List<String> getStusByClassId(String classid);
}
