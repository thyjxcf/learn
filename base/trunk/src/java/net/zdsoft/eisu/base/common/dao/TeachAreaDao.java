/* 
 * @(#)TeachAreaDao.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.entity.TeachArea;

/**
 * 教学区
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 7:48:25 PM $
 */
public interface TeachAreaDao {

    /**
     * 根据id取教学区
     * 
     * @param teachAreaId
     * @return
     */
    public TeachArea getTeachArea(String teachAreaId);

    /**
     * 根据单位id取教学区
     * 
     * @param unitId
     * @return
     */
    public List<TeachArea> getTeachAreas(String unitId);

    /**
     * 根据单位id取教学区Map
     * 
     * @param unitId
     * @return
     */
    public Map<String, TeachArea> getTeachAreaMap(String unitId);

    /**
     * 取教学区Map
     * 
     * @param teachAreaIds
     * @return
     */
    public Map<String, TeachArea> getTeachAreaMap(String[] teachAreaIds);

}
