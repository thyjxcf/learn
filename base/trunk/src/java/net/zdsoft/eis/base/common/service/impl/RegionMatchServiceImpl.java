/* 
 * @(#)RegionMatchServiceImpl.java    Created on Sep 16, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service.impl;

import java.util.List;

import net.zdsoft.eis.base.common.dao.RegionMatchDao;
import net.zdsoft.eis.base.common.service.RegionMatchService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 16, 2010 4:02:27 PM $
 */
public class RegionMatchServiceImpl implements RegionMatchService {
    private RegionMatchDao regionMatchDao;

    public void setRegionMatchDao(RegionMatchDao regionMatchDao) {
        this.regionMatchDao = regionMatchDao;
    }

    public List<String> getRegionMatchByCode(String regionCode) {
        return regionMatchDao.getRegionMatchByCode(regionCode);
    }

}
