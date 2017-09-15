package net.zdsoft.eis.system.frame.service.impl;

import net.zdsoft.eis.system.frame.dao.ModuleLogDao;
import net.zdsoft.eis.system.frame.entity.ModuleLog;
import net.zdsoft.eis.system.frame.service.ModuleLogService;

public class ModuleLogServiceImpl implements ModuleLogService {
    private ModuleLogDao moduleLogDao;

    public void setModuleLogDao(ModuleLogDao moduleLogDao) {
        this.moduleLogDao = moduleLogDao;
    }

    public void insertModuleLog(ModuleLog log) {
        moduleLogDao.insertModuleLog(log);

    }
}
