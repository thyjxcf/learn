/* 
 * @(#)SysOptionServiceImpl.java    Created on Dec 9, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.common.dao.SysOptionDao;
import net.zdsoft.eis.base.common.entity.SysOption;
import net.zdsoft.eis.base.common.service.SysOptionService;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2009 5:17:36 PM $
 */
public class SysOptionServiceImpl extends DefaultCacheManager implements SysOptionService {
    private SysOptionDao sysOptionDao;

    public void setSysOptionDao(SysOptionDao sysOptionDao) {
        this.sysOptionDao = sysOptionDao;
    }

    // -------------------缓存信息 begin------------------------
    public SysOption getSysOption(final String optionCode) {
        return getEntityFromCache(new CacheEntityParam<SysOption>() {
            public SysOption fetchObject() {
                return sysOptionDao.getSysOption(optionCode);
            }

            public String fetchKey() {
                return BaseCacheConstants.EIS_OPTION_CODE + optionCode;
            }
        });
    }

    // -------------------缓存信息 end--------------------------

    public boolean getBooleanValue(String optionCode) {
        String value = getValue(optionCode);
        return BooleanUtils.toBoolean(NumberUtils.toInt(value, 0)) || BooleanUtils.toBoolean(value);
    }

    public String getValue(String optionCode) {
        SysOption option = getSysOption(optionCode);
        if (null == option) {
            return null;
        } else {
            String value = option.getNowValue();
            return value;
        }
    }

}
