package net.zdsoft.eis.base.common.dao;

import net.zdsoft.eis.base.common.entity.SystemPatch;

public interface SystemPatchDao {


	/**
	 * 根据子系统标识获取版本信息
	 * 
	 * @param subSystem
	 * @return
	 */
	public SystemPatch getPathBySubSystem(Integer subSystem);
}
