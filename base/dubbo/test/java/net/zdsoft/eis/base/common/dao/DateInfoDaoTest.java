/* 
 * @(#)DateInfoDaoTest.java    Created on Jun 7, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.dao;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import net.zdsoft.eis.base.common.entity.DateInfo;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 7, 2011 3:58:12 PM $
 */
public class DateInfoDaoTest extends BaseDaoTestCase {
    @Autowired
    private DateInfoDao dateInfoDao;

    public void testGetDateInfo() {
        String unitId = "402880932FE71E28012FE75D7A170000";
        Date infoDate = new Date();
        DateInfo di = dateInfoDao.getDateInfo(unitId, infoDate);
        System.out.println(di.getWeek());
        System.out.println(di.getWeekday());
    }

}
