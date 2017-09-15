/* 
 * @(#)SpecialtyPointService.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.entity.SpecialtyPoint;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 7:36:11 PM $
 */
public interface SpecialtyPointService {

    /**
     * 根据id取专业方向
     * 
     * @param specialtyPointId
     * @return
     */
    public SpecialtyPoint getPoint(String specialtyPointId);

    /**
     * 根据单位id取专业方向
     * 
     * @param unitId
     * @return
     */
    public List<SpecialtyPoint> getPointsByUnitId(String unitId);
    
    /**
     * 根据单位id取专业方向
     * @param isShow true显示全部false显示有效
     * @param unitId
     * @return
     */
    public List<SpecialtyPoint> getPointsByUnitId(String unitId, boolean isShow);

    /**
     * 根据专业id取专业方向，包括无方向
     * 
     * @param specialtyId
     * @return
     */
    public List<SpecialtyPoint> getPointsBySpecialtyId(String specialtyId);
    /**
     * 根据专业id取专业方向，包括无方向(新需求 有效无效)
     * @param isShow true显示全部false显示有效
     * @param specialtyId
     * @return
     */
    public List<SpecialtyPoint> getPointsBySpecialtyId(String specialtyId,boolean isShow);

    /**
     * 根据专业id取专业方向，不包括无方向
     * 
     * @param specialtyId
     * @return
     */
    public List<SpecialtyPoint> getPointsBySpecialtyIdWithoutNone(String specialtyId);
    /**
     * 根据专业id取专业方向，不包括无方向(新需求 有效无效)
     * @param isShow true显示全部false显示有效
     * @param specialtyId
     * @return
     */
    public List<SpecialtyPoint> getPointsBySpecialtyIdWithoutNone(String specialtyId,boolean isShow);

    /**
     * 取专业Map
     * 
     * @param specialtyPointIds
     * @return
     */
    public Map<String, SpecialtyPoint> getPointMap(String[] specialtyPointIds);
}
