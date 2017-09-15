/* 
 * @(#)TeachPlaceService.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.entity.TeachPlace;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 8:11:35 PM $
 */
public interface TeachPlaceService {

    /**
     * 根据id取教学场地
     * 
     * @param teachPlaceId
     * @return
     */
    public TeachPlace getTeachPlace(String teachPlaceId);

    /**
     * 根据单位id取教学场地
     * 
     * @param unitId
     * @return
     */
    public List<TeachPlace> getTeachPlacesByUnitId(String unitId);

    /**
     * 根据教学区id取教学场地
     * 
     * @param areaId
     * @return
     */
    public List<TeachPlace> getTeachPlacesByAreaId(String areaId);

    /**
     * 取教学场地Map
     * 
     * @param teachPlaceIds
     * @return
     */
    public Map<String, TeachPlace> getTeachPlaceMap(String[] teachPlaceIds);
    
    /**
     * 根据名称或者编号查询
     * @param unitId
     * @param name
     * @param code
     * @param placeType TODO
     * @return
     */
    public List<TeachPlace> getTeachPlacesByFaintness(String unitId,
			String name, String code, String placeType);
   
    /**
     * 根据场地类型查找
     * @param unitId
     * @param type
     * @return
     */
    public List<TeachPlace> getTeachPlaceByTypeInArea(String areaId,String type);
    
    /**
     * 获取本单所以的机房id，名称  type:3
     * @param unitId
     * @return
     */
    public Map<String,String> getTeachPlaceByType(String unitId,String type);
    
    /**
     * 获取本单位对应类型的所以场地
     * @param unitId
     * @param type
     * @return
     */
    public List<TeachPlace> getTeachPlaceByUnitIdAndType(String unitId,String type);
    /**
     * 根据name获得教学场地的map
     * @param unitId
     * @param placeNames
     * @return
     */
    public Map<String, TeachPlace> getTeachPlaceMapByName(String unitId, String[] placeNames);
    
    /**
     * 根据楼Id 获取场地信息
     * @param unitId
     * @param teachBuildingId
     * @return
     */
    public List<TeachPlace> getTeachPlaceByUnitIdAndTeachBuildingId(String unitId, String teachBuildingId);
    
    /**
     * 根据uintID和controllerID查询场室
     * @param unitID
     * @param controllerID
     * @return
     */
    public TeachPlace getTeachPlaceByControllerID(String unitID,String controllerID);
}
