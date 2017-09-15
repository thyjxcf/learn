/* 
 * @(#)UnitServiceTest.java    Created on Jun 25, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.constant.BaseConstant;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 25, 2010 3:38:02 PM $
 */
public class UnitServiceTest extends BaseServiceTestCase {
    @Autowired
    private UnitService unitService;

    public void testGetTopEdu() {
        Unit u = unitService.getTopEdu();
        assertNotNull(u);
        assertEquals(u.getName(), "浙江教育局");
    }

    public void testGetUnit() {
        Unit u = unitService.getUnit("FF8080811FAB713D011FAB8B13520000");
        assertNotNull(u);
        assertEquals(u.getName(), "浙江教育局");
    }

    public void testGetUnderlingUnits() {                
        List<Unit> list = unitService.getUnderlingUnits(BaseConstant.ZERO_GUID);
        assertEquals(list.size(), 1);
    }

}
