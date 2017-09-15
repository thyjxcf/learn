/* 
 * @(#)TeachResServiceImpl.java    Created on May 20, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.dao.TeachResDao;
import net.zdsoft.eisu.base.common.entity.TeachRes;
import net.zdsoft.eisu.base.common.service.TeachResService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 20, 2011 10:34:24 AM $
 */
public class TeachResServiceImpl implements TeachResService {
    private TeachResDao teachResDao;

    public void setTeachResDao(TeachResDao teachResDao) {
        this.teachResDao = teachResDao;
    }

    public TeachRes getTeachRes(String teachResId) {
        return teachResDao.getTeachRes(teachResId);
    }

    public List<TeachRes> getTeachReses() {
        return teachResDao.getTeachReses();
    }

    public Map<String, TeachRes> getTeachResMap() {
        return teachResDao.getTeachResMap();
    }

    public Map<String, TeachRes> getTeachResMap(String[] teachResIds) {
        return teachResDao.getTeachResMap(teachResIds);
    }

}
