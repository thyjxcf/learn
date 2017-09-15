/* 
 * @(#)TeachPlaceResService.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.entity.TeachPlaceRes;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 8:18:55 PM $
 */
public interface TeachPlaceResService {

    /**
     * 根据id取教学场地资源
     * 
     * @param teachPlaceResId
     * @return
     */
    public TeachPlaceRes getTeachPlaceRes(String teachPlaceResId);

    /**
     * 根据单位id取教学场地资源
     * 
     * @param unitId
     * @return
     */
    public List<TeachPlaceRes> getTeachPlaceResesByUnitId(String unitId);

    /**
     * 根据场地id取教学场地资源
     * 
     * @param placeId
     * @return
     */
    public List<TeachPlaceRes> getTeachPlaceResesByPlaceId(String placeId);

    /**
     * 取教学场地资源Map
     * 
     * @param teachPlaceResIds
     * @return
     */
    public Map<String, TeachPlaceRes> getTeachPlaceResMap(String[] teachPlaceResIds);
}
