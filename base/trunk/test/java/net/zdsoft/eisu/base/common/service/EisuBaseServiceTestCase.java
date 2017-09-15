/* 
 * @(#)EIsuBaseServiceTestCase.java    Created on May 14, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service;

import net.zdsoft.eis.test.EisTestCase;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 14, 2011 1:44:15 PM $
 */
public class EisuBaseServiceTestCase extends EisTestCase {

    @Override
    protected String[] getNeededConfigLocations() {
        return new String[] { "classpath:/conf/spring/baseCommonDaoContext.xml",
                "classpath:/conf/spring/baseCommonServiceContext.xml",
                "classpath:/conf/spring/eisuBaseContext.xml" };
    }

}
