/* 
 * @(#)UnitDaoTest.java    Created on Sep 16, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.eis.base.common.dao.BaseDaoTestCase;
import net.zdsoft.eis.base.common.dao.UnitDao;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 16, 2010 11:08:02 AM $
 */
public class UnitDaoTest extends BaseDaoTestCase {
    @Autowired
    private UnitDao unitDao;

    public void testGetUnits() {
        System.out.println(unitDao.getUnits().size()); 
    }
}
