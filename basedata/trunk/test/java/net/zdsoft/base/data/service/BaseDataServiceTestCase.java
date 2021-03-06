/* 
 * @(#)BaseServiceTestCase.java    Created on Jun 25, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.base.data.service;

import net.zdsoft.eis.test.EisTestCase;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 25, 2010 1:57:29 PM $
 */
public class BaseDataServiceTestCase extends EisTestCase {

    @Override
    protected String[] getNeededConfigLocations() {
        return new String[] { "classpath:/conf/spring/baseCommonDaoContext.xml",
                "classpath:/conf/spring/baseCommonServiceContext.xml",
                "classpath:/conf/spring/baseDataDaoContext.xml",
                "classpath:/conf/spring/baseDataServiceContext.xml",
                "classpath:/conf/spring/baseDataSyncContext.xml" };
    }

}
