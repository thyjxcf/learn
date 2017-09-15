/* 
 * @(#)SpecialtyService.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.entity.Specialty;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 4:54:58 PM $
 */
public interface SpecialtyService {

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
     * 根据上级id获取直属专业
     * 
     * @param parentId
     * @param parentType
     * @return
     */
    public List<Specialty> getSpecialtysByParent(String parentId, int parentType);
    /**
     * 根据上级id获取直属专业(新需求有效无效)
     * @param isShow true显示全部 false只显示有效
     * @param parentId
     * @param parentType
     * @return
     */
    public List<Specialty> getSpecialtysByParent(String parentId, int parentType,boolean isShow);

    /**
     * 根据上级id获取直属专业id
     * 
     * @param parentId
     * @param parentType
     * @return
     */
    public String[] getSpecialtyIdsByParent(String parentId, int parentType);
    
    /**
     * 根据上级id取所有（直属和下属）的专业
     * 
     * @param instituteId
     * @return
     */
    public List<Specialty> getAllSpecialtysByParent(String parentId, int parentType);
    /**
     * 根据上级id取所有（直属和下属）的专业(新需求有效无效)
     * @param isShow true显示全部 false只显示有效
     * @param instituteId
     * @return
     */
    public List<Specialty> getAllSpecialtysByParent(String parentId, int parentType,boolean isShow);


    /**
     * 根据上级id取所有（直属和下属）的专业id
     * 
     * @param instituteId
     * @return
     */
    public String[] getAllSpecialtyIdsByParent(String parentId, int parentType);
    /**
     * 根据大专业id求小专业
     * @param specId
     * @param type
     * @return
     */
    public List<Specialty> getSpecialtysByParentSpec(String specId);
    /**
     * 专业Map
     * 
     * @param specialtyIds
     * @return
     */
    public Map<String, Specialty> getSpecialtyMap(String[] specialtyIds);
    /**
     * 根据上级id取所有（直属和下属）的专业id
     * 
     * @param instituteId
     * @return
     */
    public String[] getAllSpecialtyIdsByParent(String[] parentId, int parentType);
}
