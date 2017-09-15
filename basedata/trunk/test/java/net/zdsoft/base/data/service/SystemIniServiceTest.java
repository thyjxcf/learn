/* 
 * @(#)SystemIniServiceTest.java    Created on Aug 19, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.base.data.service;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.data.service.BaseSystemIniService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 19, 2010 1:56:53 PM $
 */
public class SystemIniServiceTest extends BaseDataServiceTestCase {
    @Autowired
    private BaseSystemIniService baseSystemIniService;
    
    public void testGetSystemIni(){
        SystemIni ini = null;
        for (int i = 0; i < 5; i++) {
            ini = baseSystemIniService.getSystemIni("SYSTEM.CLASSNAME");
            System.out.println(ini.getNowValue());
        }
        
        baseSystemIniService.saveMod("SYSTEM.CLASSNAME","2");
        
        for (int i = 0; i < 5; i++) {
            ini = baseSystemIniService.getSystemIni("SYSTEM.CLASSNAME");
            System.out.println(ini.getNowValue());
        }
    }
    
}
