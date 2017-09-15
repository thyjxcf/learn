/* 
 * @(#)SpecialtyPointServiceImpl.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.dao.SpecialtyPointDao;
import net.zdsoft.eisu.base.common.entity.Specialty;
import net.zdsoft.eisu.base.common.entity.SpecialtyPoint;
import net.zdsoft.eisu.base.common.service.SpecialtyPointService;
import net.zdsoft.eisu.base.common.service.SpecialtyService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 7:36:22 PM $
 */
public class SpecialtyPointServiceImpl implements SpecialtyPointService {
    private SpecialtyPointDao specialtyPointDao;
    private SpecialtyService specialtyService;

    public void setSpecialtyPointDao(SpecialtyPointDao specialtyPointDao) {
        this.specialtyPointDao = specialtyPointDao;
    }

    public void setSpecialtyService(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    public SpecialtyPoint getPoint(String specialtyPointId) {
        if (SpecialtyPoint.SPECIALTY_POINT_NONE_ID.equals(specialtyPointId)) {
            return getNonePoint(null);
        }
        return specialtyPointDao.getPoint(specialtyPointId);
    }

    public Map<String, SpecialtyPoint> getPointMap(String[] specialtyPointIds) {
        Map<String, SpecialtyPoint> map = specialtyPointDao.getPointMap(specialtyPointIds);
        for (String specialtyPointId : specialtyPointIds) {
            if (SpecialtyPoint.SPECIALTY_POINT_NONE_ID.equals(specialtyPointId)) {
                map.put(specialtyPointId, getNonePoint(null));
                break;
            }
        }
        return map;
    }

    public List<SpecialtyPoint> getPointsBySpecialtyId(String specialtyId) {
        List<SpecialtyPoint> points = specialtyPointDao.getPointsBySpecialtyId(specialtyId);

        List<SpecialtyPoint> list = new ArrayList<SpecialtyPoint>();
        list.add(getNonePoint(specialtyId));
        list.addAll(points);
        return list;
    }
    public List<SpecialtyPoint> getPointsBySpecialtyId(String specialtyId,boolean isShow) {
    	List<SpecialtyPoint> points = specialtyPointDao.getPointsBySpecialtyId(specialtyId,isShow);
    	
    	List<SpecialtyPoint> list = new ArrayList<SpecialtyPoint>();
    	list.add(getNonePoint(specialtyId));
    	list.addAll(points);
    	return list;
    }

    public List<SpecialtyPoint> getPointsBySpecialtyIdWithoutNone(String specialtyId) {
        return specialtyPointDao.getPointsBySpecialtyId(specialtyId);
    }
    public List<SpecialtyPoint> getPointsBySpecialtyIdWithoutNone(String specialtyId,boolean isShow) {
    	return specialtyPointDao.getPointsBySpecialtyId(specialtyId,isShow);
    }

    public List<SpecialtyPoint> getPointsByUnitId(String unitId) {
        List<SpecialtyPoint> points = specialtyPointDao.getPointsByUnitId(unitId);

        List<SpecialtyPoint> list = new ArrayList<SpecialtyPoint>();
        list.add(getNonePoint(null));
        list.addAll(points);
        return list;
    }
    
    public List<SpecialtyPoint> getPointsByUnitId(String unitId, boolean isShow){
    	List<SpecialtyPoint> points = specialtyPointDao.getPointsByUnitId(unitId, isShow);

        List<SpecialtyPoint> list = new ArrayList<SpecialtyPoint>();
        list.add(getNonePoint(null));
        list.addAll(points);
        return list;
    }

    /**
     * 无方向
     * 
     * @param specialtyId
     * @return
     */
    private SpecialtyPoint getNonePoint(String specialtyId) {
        SpecialtyPoint sp = new SpecialtyPoint();
        sp.setId(SpecialtyPoint.SPECIALTY_POINT_NONE_ID);
        sp.setPointName(SpecialtyPoint.SPECIALTY_POINT_NONE_NAME);
        sp.setSpecialtyId(specialtyId);
        
        if (specialtyId != null) {
            Specialty spec = specialtyService.getSpecialty(specialtyId);
            if (null != spec) {
                sp.setUnitId(spec.getUnitId());
            }
        }
        return sp;
    }

}
