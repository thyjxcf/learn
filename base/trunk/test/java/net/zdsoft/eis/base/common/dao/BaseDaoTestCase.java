/* 
 * @(#)BasedataTestCase.java    Created on Jun 25, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.dao;

import net.zdsoft.eis.test.EisTestCase;

public class BaseDaoTestCase extends EisTestCase {

    protected String[] getNeededConfigLocations() {
        return new String[] { "classpath:/conf/spring/baseCommonDaoContext.xml" };
    }

}
