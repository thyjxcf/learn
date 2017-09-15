/* 
 * @(#)ProductParamDao.java    Created on Jun 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.dao;

import java.util.Map;

/**
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 13, 2011 4:41:34 PM $
 */
public interface ProductParamDao {

    /**
     * 根据参数code取参数value
     * 
     * @param paramCode
     * @return
     */
    public String getProductParamValue(String paramCode);

    /**
     * 取产品参数
     * 
     * @return key=code,value=value
     */
    public Map<String, String> getProductParamCodeValueMap();
}
