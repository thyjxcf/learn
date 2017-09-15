/* 
 * @(#)DutyService.java    Created on 2007-8-14
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header$
 */
package net.zdsoft.eis.base.data.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.entity.Duty;

public interface DutyService {
    /**
     * 保存或更新
     * 
     * @param dutys
     */
    public void saveDuty(Duty... dutys);

    /**
     * 删除
     * 
     * @param unitIds
     */
    public void deleteUnitDuty(String... unitIds);

    /**
     * 删除
     * 
     * @param dutyIds
     */
    public void deleteDuty(String... dutyIds);

    /**
     * 根据unitId获得职称列表
     * 
     * @param unitId
     * @return
     */
    public List<Duty> getDuties(String unitId);

    /**
     * 根据unitId获得职称Map
     * 
     * @param unitId
     * @return
     */
    public Map<String, Duty> getDutyMap(String unitId);
}
