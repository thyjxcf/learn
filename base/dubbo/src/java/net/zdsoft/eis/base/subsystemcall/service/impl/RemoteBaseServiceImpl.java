package net.zdsoft.eis.base.subsystemcall.service.impl;

import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.service.SemesterService;
import net.zdsoft.eis.base.subsystemcall.service.BaseDataSubsystemService;
import net.zdsoft.remote.service.RemoteBaseService;


/**
 * base远程调用接口
 * @author weixh
 * @since 2016-3-22 上午11:17:01
 */
public class RemoteBaseServiceImpl implements RemoteBaseService {
	private SemesterService semesterService;
	private BaseDataSubsystemService baseDataSubsystemService;
	
	public CurrentSemester getCurrentSemester() {
		return semesterService.getCurrentSemester();
	}
	
	public void updateTeaEmail(String teacherId, String email){
		baseDataSubsystemService.updateEmail(teacherId, email);
	}

	public void setSemesterService(SemesterService semesterService) {
		this.semesterService = semesterService;
	}

	public void setBaseDataSubsystemService(
			BaseDataSubsystemService baseDataSubsystemService) {
		this.baseDataSubsystemService = baseDataSubsystemService;
	}

}
