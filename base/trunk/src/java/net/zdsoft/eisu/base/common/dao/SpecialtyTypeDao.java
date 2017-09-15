/* 
 * @(#)SpecialtyTypeDao.java    Created on May 31, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.entity.SpecialtyType;

/**
 * 专业类别
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 31, 2011 11:29:50 AM $
 */
public interface SpecialtyTypeDao {

    /**
     * 根据专业类别id取专业类别
     * 
     * @param specialtyTypeId
     * @return
     */
    public SpecialtyType getSpecialtyType(String specialtyTypeId);

    /**
     * 根据单位id取专业类别
     * 
     * @param unitId
     * @return
     */
    public List<SpecialtyType> getSpecialtyTypes(String unitId);

    /**
     * 取专业类别Map
     * 
     * @param specialtyTypeIds
     * @return
     */
    public Map<String, SpecialtyType> getSpecialtyTypeMap(String[] specialtyTypeIds);

}
