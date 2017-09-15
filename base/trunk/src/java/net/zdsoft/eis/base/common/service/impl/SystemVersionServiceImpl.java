package net.zdsoft.eis.base.common.service.impl;

import net.zdsoft.eis.base.common.dao.SystemPatchDao;
import net.zdsoft.eis.base.common.dao.SystemVersionDao;
import net.zdsoft.eis.base.common.entity.SystemPatch;
import net.zdsoft.eis.base.common.entity.SystemVersion;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.SystemVersionService;

public class SystemVersionServiceImpl implements SystemVersionService {
    
    private SystemVersionDao systemVersionDao;
    protected SystemPatchDao systemPatchDao;

    public void setSystemPatchDao(SystemPatchDao systemPatchDao) {
        this.systemPatchDao = systemPatchDao;
    }

    public void setSystemVersionDao(SystemVersionDao systemVersionDao) {
        this.systemVersionDao = systemVersionDao;
    }

    public SystemVersion getSystemVersion() {
        return systemVersionDao.getSystemVersion();
    }

    public int updateCurProduct(String unitClass) {
        if (Integer.valueOf(unitClass) == (Unit.UNIT_CLASS_EDU)) {
            return systemVersionDao.updateProduct(SystemVersion.PRODUCT_EIS_E);
        } else {
            return systemVersionDao.updateProduct(SystemVersion.PRODUCT_EIS_S);
        }
    }

    public SystemPatch getSubSystemPatch(Integer subSystem) {
        return systemPatchDao.getPathBySubSystem(subSystem);
    }
}
