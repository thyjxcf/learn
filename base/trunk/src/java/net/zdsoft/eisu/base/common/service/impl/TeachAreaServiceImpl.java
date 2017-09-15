/* 
 * @(#)TeachAreaServiceImpl.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.dao.TeachAreaDao;
import net.zdsoft.eisu.base.common.entity.TeachArea;
import net.zdsoft.eisu.base.common.service.TeachAreaService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 7:54:58 PM $
 */
public class TeachAreaServiceImpl implements TeachAreaService {
    private TeachAreaDao teachAreaDao;

    public void setTeachAreaDao(TeachAreaDao teachAreaDao) {
        this.teachAreaDao = teachAreaDao;
    }

    public TeachArea getTeachArea(String teachAreaId) {
        return teachAreaDao.getTeachArea(teachAreaId);
    }

    public Map<String, TeachArea> getTeachAreaMap(String[] teachAreaIds) {
        return teachAreaDao.getTeachAreaMap(teachAreaIds);
    }

    public List<TeachArea> getTeachAreas(String unitId) {
        return teachAreaDao.getTeachAreas(unitId);
    }

    public Map<String, TeachArea> getTeachAreaMap(String unitId) {
        return teachAreaDao.getTeachAreaMap(unitId);
    }
}
