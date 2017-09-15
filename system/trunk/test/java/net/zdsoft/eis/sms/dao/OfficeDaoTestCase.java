/* 
 * @(#)SmsDaoTestCase.java    Created on Jun 29, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.sms.dao;

import net.zdsoft.eis.test.EisTestCase;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 29, 2010 3:22:48 PM $
 */
public class OfficeDaoTestCase extends EisTestCase {

    protected String[] getNeededConfigLocations() {
        return new String[] { "classpath:/conf/spring/eisOfficeContext.xml" };
    }

}
