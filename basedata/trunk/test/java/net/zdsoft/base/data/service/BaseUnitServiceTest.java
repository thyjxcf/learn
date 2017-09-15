/* 
 * @(#)BaseUnitServiceTest.java    Created on Dec 23, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.base.data.service;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.service.BaseUnitService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 23, 2010 10:17:59 AM $
 */
public class BaseUnitServiceTest extends BaseDataServiceTestCase {
    @Autowired
    private BaseUnitService baseUnitService;

    public void testUpdateUnitWithUser() {
        System.out.println("===========");
        BaseUnit unit = baseUnitService.getBaseUnit("402880932CCB377E012CCB3F8E7A0000");
        unit.setName("杭州市教育局1");
        try {
            baseUnitService.updateUnitWithUser(unit, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setComplete();
    }
}
