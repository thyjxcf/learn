/* 
 * @(#)BasedataTestCase.java    Created on Jun 25, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.base.data.dao;

import net.zdsoft.eis.test.EisTestCase;

public class BaseDataDaoTestCase extends EisTestCase {

    protected String[] getNeededConfigLocations() {
        return new String[] { "classpath:/conf/spring/baseDataDaoContext.xml" };
    }

}
