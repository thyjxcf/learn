package net.zdsoft.eis.base.data.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;


import net.zdsoft.eis.base.common.entity.SysOption;
import net.zdsoft.eis.base.common.service.impl.SysOptionServiceImpl;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.dao.BaseSysOptionDao;
import net.zdsoft.eis.base.data.service.BaseSysOptionService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.FormatException;

public class BaseSysOptionServiceImpl extends SysOptionServiceImpl implements BaseSysOptionService {
    private BaseSysOptionDao baseSysOptionDao;
    private SystemDeployService systemDeployService;

    public void setSystemDeployService(SystemDeployService systemDeployService) {
        this.systemDeployService = systemDeployService;
    }
    
    public void setBaseSysOptionDao(BaseSysOptionDao baseSysOptionDao) {
        this.baseSysOptionDao = baseSysOptionDao;
    }

    @Override
    public void addSysOption(SysOption sysOption) {
        baseSysOptionDao.insertSysOption(sysOption);
        updateSysOptionEx();
    }

    @Override
    public void updateSysOption(SysOption sysOption) {
        baseSysOptionDao.updateSysOption(sysOption);
        updateSysOptionEx();
    }

    @Override
    public void deleteSysOption(String sysOptionId, EventSourceType eventSource) {
        baseSysOptionDao.deleteSysOption(sysOptionId, eventSource);
        updateSysOptionEx();
    }

    public void saveSysOptions(SysOption[] sysOptions) throws FormatException {
        String nowValue;
        for (int i = 0; i < sysOptions.length; i++) {
            nowValue = sysOptions[i].getNowValue();
            if (StringUtils.isNotBlank(nowValue)) {
                if (BasedataConstants.SYSTEMINI_VALUE_MAXLENGTH < net.zdsoft.keel.util.StringUtils
                        .getRealLength(nowValue)) {
                    throw new FormatException(sysOptions[i].getName() + "值长度超过"
                            + BasedataConstants.SYSTEMINI_VALUE_MAXLENGTH, "baseOptionArray[" + i
                            + "].nowValue");
                }
            }

        }
        baseSysOptionDao.batchUpdateSysOption(sysOptions);

        updateSysOptionEx();
    }

    private void updateSysOptionEx() {
        clearCache();
        systemDeployService.initPassportClient();
    }
    
    public List<SysOption> getSysOptions() {
        return baseSysOptionDao.getSysOptions();
    }

}
