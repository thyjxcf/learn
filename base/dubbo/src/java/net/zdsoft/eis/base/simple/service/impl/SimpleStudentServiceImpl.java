/* 
 * @(#)SimpleStudentServiceImpl.java    Created on May 28, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.service.impl;

import java.util.Map;

import net.zdsoft.eis.base.simple.dao.SimpleStudentDao;
import net.zdsoft.eis.base.simple.entity.SimpleStudent;
import net.zdsoft.eis.base.simple.service.SimpleStudentService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 28, 2011 4:07:28 PM $
 */
public class SimpleStudentServiceImpl extends AbstractStudentServiceImpl<SimpleStudent> implements
        SimpleStudentService {
    protected SimpleStudentDao simpleStudentDao;

    public void setSimpleStudentDao(SimpleStudentDao simpleStudentDao) {
        this.simpleStudentDao = simpleStudentDao;
    }

    @Override
    public SimpleStudentDao getStudentDao() {
        return simpleStudentDao;
    }

	@Override
	public Map<String, SimpleStudent> getStudentdexByStudentIds(
			String[] studentIds) {
		// TODO Auto-generated method stub
		return simpleStudentDao.getStudentdexByStudentIds(studentIds);
	}

}
