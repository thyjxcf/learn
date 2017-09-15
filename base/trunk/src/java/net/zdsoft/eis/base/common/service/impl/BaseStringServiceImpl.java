package net.zdsoft.eis.base.common.service.impl;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.common.dao.BaseStringDao;
import net.zdsoft.eis.base.common.service.BaseStringService;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;

public class BaseStringServiceImpl extends DefaultCacheManager implements BaseStringService {

    private BaseStringDao baseStringDao;

    @Override
    public String getValue(final String code) {
        return getEntityFromCache(new CacheEntityParam<String>() {
            @Override
            public String fetchKey() {
                return BaseCacheConstants.EIS_STRING_CODE + code;
            }

            @Override
            public String fetchObject() {
                return baseStringDao.getValue(code);
            }
        });
    }
    
    @Override
    public String getValue(String code, String defaultValue) {
        String s = getValue(code);
        if(s == null) {
            return defaultValue;
        }
        return s;
    }

    public void setBaseStringDao(BaseStringDao baseStringDao) {
        this.baseStringDao = baseStringDao;
    }

}
