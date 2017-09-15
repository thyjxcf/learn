/* 
 * @(#)TeachPlaceServiceImpl.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.dao.TeachPlaceDao;
import net.zdsoft.eisu.base.common.entity.TeachPlace;
import net.zdsoft.eisu.base.common.service.TeachPlaceService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 8:11:55 PM $
 */
public class TeachPlaceServiceImpl implements TeachPlaceService {
	private TeachPlaceDao teachPlaceDao;

	public void setTeachPlaceDao(TeachPlaceDao teachPlaceDao) {
		this.teachPlaceDao = teachPlaceDao;
	}

	public TeachPlace getTeachPlace(String teachPlaceId) {
		return teachPlaceDao.getTeachPlace(teachPlaceId);
	}

	public Map<String, TeachPlace> getTeachPlaceMap(String[] teachPlaceIds) {
		return teachPlaceDao.getTeachPlaceMap(teachPlaceIds);
	}

	public List<TeachPlace> getTeachPlacesByAreaId(String areaId) {
		return teachPlaceDao.getTeachPlacesByAreaId(areaId);
	}

	public List<TeachPlace> getTeachPlacesByUnitId(String unitId) {
		return teachPlaceDao.getTeachPlacesByUnitId(unitId);
	}

	@Override
	public List<TeachPlace> getTeachPlacesByFaintness(String unitId,
			String name, String code, String placeType) {
		return teachPlaceDao.getTeachPlacesByFaintness(unitId, name, code,
				placeType);
	}

	@Override
	public List<TeachPlace> getTeachPlaceByTypeInArea(String areaId, String type) {
		return teachPlaceDao.getTeachPlaceByTypeInArea(areaId, type);
	}

	@Override
	public Map<String, String> getTeachPlaceByType(String unitId, String type) {
//		Map<String, String> m = new HashMap<String, String>();
//		List<TeachPlace> plist = teachPlaceDao.getTeachPlacesByFaintness(
//				unitId, "", "", type);
//		for (TeachPlace ent : plist) {
//			m.put(ent.getId(), ent.getPlaceName());
//		}
		
		return teachPlaceDao.getTeachPlacesByTypes(unitId, type);
	}
	
	@Override
	public List<TeachPlace> getTeachPlaceByUnitIdAndType(String unitId,
			String type) {
		return teachPlaceDao.getTeachPlaceByUnitIdAndType(unitId, type);
	}

	@Override
	public Map<String, TeachPlace> getTeachPlaceMapByName(String unitId,
			String[] placeNames) {
		return teachPlaceDao.getTeachPlaceMapByName(unitId, placeNames);
	}

	@Override
	public List<TeachPlace> getTeachPlaceByUnitIdAndTeachBuildingId(
			String unitId, String teachBuildingId) {
		return teachPlaceDao.getTeachPlaceByUnitIdAndTeachBuildingId(unitId,teachBuildingId);
	}
	
	@Override
    public TeachPlace getTeachPlaceByControllerID(String unitID,
            String controllerID) {
        return teachPlaceDao.getTeachPlaceByControllerID(unitID, controllerID);
    }
}
