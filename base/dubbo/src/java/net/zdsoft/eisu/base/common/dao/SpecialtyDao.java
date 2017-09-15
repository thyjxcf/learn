/* 
 * @(#)SpecialtyDao.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.entity.Specialty;

/**
 * 专业
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 4:52:29 PM $
 */
public interface SpecialtyDao {

    /**
     * 根据id获取专业
     * 
     * @param specialtyId
     * @return
     */
    public Specialty getSpecialty(String specialtyId);

    /**
     * 根据单位id获取专业
     * 
     * @param unitId
     * @return
     */
    public List<Specialty> getSpecialtysByUnitId(String unitId);
    /**
     * 根据单位id获取专业(新需求有效无效)
     * @param isShow true显示全部 false只显示有效  
     * @param unitId
     * @return
     */
    public List<Specialty> getSpecialtysByUnitId(String unitId,boolean isShow);
    /**
     * 根据单位id获取专业(新需求有效无效)
     * @param isShow true显示全部 false只显示有效  
     * @param unitId
     * @return
     */
    public Map<String,Specialty> getSpecialtymapByUnitId(String unitId,boolean isShow);

    /**
     * 根据上级id获取专业
     * 
     * @param parentId
     * @param parentType
     * @return
     */
    public List<Specialty> getSpecialtysByParent(String parentId, int parentType);
    /**
     * 根据大专业id求小专业
     * @param specId
     * @param type
     * @return
     */
    public List<Specialty> getSpecialtysByParentSpec(String specId);
    /**
     * 根据大专业id数组求小专业
     * @param specId
     * @param type
     * @return
     */
    public List<Specialty> getSpecialtysByParentSpec(String[] specIds);
    
    /**
     * 根据上级id获取专业(新需求有效无效)
     * @param isShow true显示全部 false只显示有效 
     * @param parentId
     * @param parentType
     * @return
     */
    public List<Specialty> getSpecialtysByParent(String parentId, int parentType,boolean isShow);

    /**
     * 根据上级取专业
     * 
     * @param specialtyIds
     * @return
     */
    public List<Specialty> getSpecialtysByParent(String[] parentIds, int parentType);
    /**
     * 根据上级取专业(新需求有效无效)
     * @param isShow true显示全部 false只显示有效 
     * @param specialtyIds
     * @return
     */
    public List<Specialty> getSpecialtysByParent(String[] parentIds, int parentType,boolean isShow);

    /**
     * 根据上级取专业id
     * 
     * @param parentIds
     * @param parentType
     * @return
     */
    public List<String> getSpecialtyIdsByParent(String[] parentIds, int parentType);

    /**
     * 专业Map
     * 
     * @param specialtyIds
     * @return
     */
    public Map<String, Specialty> getSpecialtyMap(String[] specialtyIds);
    /**
     * 根据院系id批量更新其下专业为无效
     * @param ids
     */
    public void updateSpecState(String[] iniIds);
    /**
     * 根据专业id批量更新专业为无效
     * @param ids
     */
    public void updateSpecStateBySpec(String[] specId);

}
