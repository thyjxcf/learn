/* 
 * @(#)RegionMatchService.java    Created on Sep 16, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service;

import java.util.List;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 16, 2010 4:02:01 PM $
 */
public interface RegionMatchService {
    /**
     * 根据行政区划代码，取出行政区划匹配信息
     * 
     * @param regionCode
     * @return
     */
    List<String> getRegionMatchByCode(String regionCode);
}
