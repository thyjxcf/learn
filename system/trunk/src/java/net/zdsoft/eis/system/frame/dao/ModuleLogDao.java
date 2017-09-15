package net.zdsoft.eis.system.frame.dao;

import net.zdsoft.eis.system.frame.entity.ModuleLog;

public interface ModuleLogDao {
    /**
     * 插入日志
     * @param log
     */
    public void insertModuleLog(ModuleLog log);
}
