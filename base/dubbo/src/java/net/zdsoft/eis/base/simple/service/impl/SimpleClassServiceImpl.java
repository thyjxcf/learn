/* 
 * @(#)SimpleClassServiceImpl.java    Created on May 28, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.service.impl;

import net.zdsoft.eis.base.simple.dao.SimpleClassDao;
import net.zdsoft.eis.base.simple.entity.SimpleClass;
import net.zdsoft.eis.base.simple.service.SimpleClassService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 28, 2011 5:26:21 PM $
 */
public class SimpleClassServiceImpl extends AbstractClassServiceImpl<SimpleClass> implements
        SimpleClassService {
    private SimpleClassDao simpleClassDao;

    public void setSimpleClassDao(SimpleClassDao simpleClassDao) {
        this.simpleClassDao = simpleClassDao;
    }

    @Override
    public SimpleClassDao getClassDao() {
        return simpleClassDao;
    }

}
