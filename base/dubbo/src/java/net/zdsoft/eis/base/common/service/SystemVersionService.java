package net.zdsoft.eis.base.common.service;

import net.zdsoft.eis.base.common.entity.SystemPatch;
import net.zdsoft.eis.base.common.entity.SystemVersion;

public interface SystemVersionService {
    /**
     * 取系统版本信息
     * 
     * @return
     */
    public SystemVersion getSystemVersion();

    /**
     * 
     * @param unitClass
     * @return
     */
    public int updateCurProduct(String unitClass);
    

    /**
     * 获取子系统版本信息
     * 
     * @param subSystem
     * @return
     */
    public SystemPatch getSubSystemPatch(Integer subSystem);
}
