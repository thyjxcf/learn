package net.zdsoft.eis.base.common.service.impl;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.common.dao.SystemIniDao;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;

public class SystemIniServiceImpl extends DefaultCacheManager implements SystemIniService {
    protected SystemIniDao systemIniDao;
    private SystemDeployService systemDeployService;

    public void setSystemIniDao(SystemIniDao systemIniDao) {
        this.systemIniDao = systemIniDao;
    }
    
    public void setSystemDeployService(SystemDeployService systemDeployService) {
		this.systemDeployService = systemDeployService;
	}

    // ====================以上是set==============================

    // -------------------缓存信息 begin------------------------

	public String getValue(String optionId) {
        SystemIni ini = getSystemIni(optionId);
        if (null == ini) {
            return null;
        } else {
            String value = ini.getNowValue();
            if (StringUtils.isEmpty(value)) {
                value = ini.getDefaultValue();
            }
            return value;
        }
    }

    public boolean getBooleanValue(String optionId) {
        String value = getValue(optionId);
        return BooleanUtils.toBoolean(NumberUtils.toInt(value, 0)) || BooleanUtils.toBoolean(value);
    }

    /**
     * 检索系统设置参数
     * 
     * @param optionId 设置项的id
     * @return 单个的系统配置
     */
    public SystemIni getSystemIni(final String optionId) {
        return getEntityFromCache(new CacheEntityParam<SystemIni>() {

            public SystemIni fetchObject() {
                return systemIniDao.getSystemIni(optionId);
            }

            public String fetchKey() {
                return BaseCacheConstants.EIS_SYSTEMOPTION_OPTIONID + optionId;
            }
        }, 1);
    }

    // -------------------缓存信息 end--------------------------

    public List<SystemIni> getSystemIniByViewable(Integer visible) {
        return systemIniDao.getSystemInis(visible);
    }
    
    public void updateNowValue(String iniId, String nowValue){
    	systemIniDao.updateNowValue(iniId, nowValue);
		removeFromCache(BaseCacheConstants.EIS_SYSTEMOPTION_OPTIONID+iniId);
		
		if(SystemIni.SYSTEM_PASSPORT_SWITCH.equals(iniId)){
		    systemDeployService.initPassportClient();
		}
    }

}
