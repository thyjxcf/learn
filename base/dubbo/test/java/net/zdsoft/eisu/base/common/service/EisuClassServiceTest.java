/* 
 * @(#)EisuClassServiceTest.java    Created on May 14, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.eisu.base.common.entity.EisuClass;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 14, 2011 1:42:30 PM $
 */
public class EisuClassServiceTest extends EisuBaseServiceTestCase {
    @Autowired
    private EisuClassService eisuClassService;

    public void testGetClass() {
        String classId = "ff8080812d60d782012d60dadd100008";
        EisuClass cls = eisuClassService.getClass(classId);
        assertNotNull(cls);
        assertEquals(cls.getSpecialtyId(), "ff8080812d60d782012d60dadd100000");
    }
}
