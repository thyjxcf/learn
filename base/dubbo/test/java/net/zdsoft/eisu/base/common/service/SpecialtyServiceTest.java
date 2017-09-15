/* 
 * @(#)SpecialtyServiceTest.java    Created on Jun 3, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.eisu.base.common.entity.Specialty;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 3, 2011 4:30:54 PM $
 */
public class SpecialtyServiceTest extends EisuBaseServiceTestCase {
    @Autowired
    private SpecialtyService specialtyService;

    public void testGetAllSpecialtysByParent() {
        String parentId = "A3885D86AE6C0B01E040007F01000600";
        int parentType = Specialty.PARENT_INSTITUTE;
        List<Specialty> list = specialtyService.getAllSpecialtysByParent(parentId, parentType);
        for (Specialty specialty : list) {
            System.out.println(specialty.getId());
            System.out.println(specialty.getSpecialtyName());
        }
    }
}
