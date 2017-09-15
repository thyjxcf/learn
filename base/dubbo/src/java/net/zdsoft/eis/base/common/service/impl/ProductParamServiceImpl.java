/* 
 * @(#)ProductParamServiceImpl.java    Created on Jun 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service.impl;

import java.util.Map;

import net.zdsoft.eis.base.common.dao.ProductParamDao;
import net.zdsoft.eis.base.common.service.ProductParamService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 13, 2011 4:42:31 PM $
 */
public class ProductParamServiceImpl implements ProductParamService {
    private ProductParamDao productParamDao;

    public void setProductParamDao(ProductParamDao productParamDao) {
        this.productParamDao = productParamDao;
    }

    public String getProductParamValue(String paramCode) {
        return productParamDao.getProductParamValue(paramCode);
    }

    public Map<String, String> getProductParamCodeValueMap() {
        return productParamDao.getProductParamCodeValueMap();
    }

}
