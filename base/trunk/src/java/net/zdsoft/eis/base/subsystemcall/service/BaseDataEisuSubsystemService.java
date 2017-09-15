package net.zdsoft.eis.base.subsystemcall.service;

import java.util.List;

import net.zdsoft.eisu.base.common.entity.EisuClass;

/**
 * 
 *@author weixh
 *@since 2014-5-5
 */
public interface BaseDataEisuSubsystemService {
	/**
     * 更新班级班主任、教室
     * @param clsList
     */
    public void updateClsTeaRoom(List<EisuClass> clsList);

}
