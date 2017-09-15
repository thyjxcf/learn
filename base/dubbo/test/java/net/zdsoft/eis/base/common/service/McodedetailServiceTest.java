/* 
 * @(#)McodedetailServiceTest.java    Created on Jun 25, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.eis.base.common.entity.Mcodedetail;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 25, 2010 1:59:23 PM $
 */
public class McodedetailServiceTest extends BaseServiceTestCase {
    @Autowired
    private McodedetailService mcodedetailService;

    public void testGetMcodeDetail() {
        Mcodedetail m = mcodedetailService.getMcodeDetail("DM-XB", "2");
        assertNotNull(m);
        assertEquals(m.getContent(), "女");
    }

    public void testGetMcodeDetails() {
        List<Mcodedetail> list = mcodedetailService.getMcodeDetails("DM-XB");
        assertEquals(list.size(), 4);
    }

}
