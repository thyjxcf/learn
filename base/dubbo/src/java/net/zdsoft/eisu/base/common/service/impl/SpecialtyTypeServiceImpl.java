/* 
 * @(#)SpecialtyTypeServiceImpl.java    Created on May 31, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.dao.SpecialtyTypeDao;
import net.zdsoft.eisu.base.common.entity.SpecialtyType;
import net.zdsoft.eisu.base.common.service.SpecialtyTypeService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 31, 2011 11:31:28 AM $
 */
public class SpecialtyTypeServiceImpl implements SpecialtyTypeService {
    private SpecialtyTypeDao specialtyTypeDao;

    public void setSpecialtyTypeDao(SpecialtyTypeDao specialtyTypeDao) {
        this.specialtyTypeDao = specialtyTypeDao;
    }

    public SpecialtyType getSpecialtyType(String specialtyTypeId) {
        return specialtyTypeDao.getSpecialtyType(specialtyTypeId);
    }

    public List<SpecialtyType> getSpecialtyTypes(String unitId) {
        return specialtyTypeDao.getSpecialtyTypes(unitId);
    }

    public Map<String, SpecialtyType> getSpecialtyTypeMap(String[] specialtyTypeIds) {
        return specialtyTypeDao.getSpecialtyTypeMap(specialtyTypeIds);
    }

}
