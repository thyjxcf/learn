/* 
 * @(#)InstituteDao.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.entity.Institute;

/**
 * 院、系结构
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 7:25:03 PM $
 */
public interface InstituteDao {

    /**
     * 根据id取院系
     * 
     * @param instituteId
     * @return
     */
    public Institute getInstitute(String instituteId);

    /**
     * 根据单位id取院系
     * 
     * @param unitId
     * @return
     */
    public List<Institute> getInstitutesByUnitId(String unitId);

    /**
     * 根据上级和上级类型取院系
     * 
     * @param parentId
     * @param parentType
     * @return
     */
    public List<Institute> getInstitutesByParent(String parentId, int parentType);
    /**
     * 根据上级和上级类型取院系(适应新需求 有效无效)
     * @param isShow是否显示全部(true全部 false过滤掉无效的)
     * @param parentId
     * @param parentType
     * @return
     */
    public List<Institute> getInstitutesByParent(String parentId, int parentType,boolean isShow);

    /**
     * 根据上级和上级类型取院系
     * 
     * @param parentId
     * @param parentType
     * @param instituteKind
     * @return
     */
    public List<Institute> getInstitutesByParent(String parentId, int parentType, int instituteKind);
    /**
     * 根据上级和上级类型取院系(适应新需求 有效无效)
     * @param isShow是否显示全部(true全部 false过滤掉无效的)
     * @param parentId
     * @param parentType
     * @param instituteKind
     * @return
     */
    public List<Institute> getInstitutesByParent(String parentId, int parentType, int instituteKind,boolean isShow);

    /**
     * 取院系Map
     * 
     * @param instituteIds
     * @return
     */
    public Map<String, Institute> getInstituteMap(String[] instituteIds);
    /**
     * 取院系map
     * @param unitId
     * @return
     */
    public Map<String, Institute> getMapByUnitId(String unitId);
    /**
     * 根据上级和上级类型取院系
     * 
     * @param parentId
     * @param parentType
     * @return
     */
    public List<Institute> getInstitutesByParent(String[] parentId, int parentType);
    /**
     * 取有效院系
     * 
     * @param instituteIds
     * @param state 0有效 1无效  null全部
     * @return
     */
    public List<Institute> getInstituteList(String[] instituteIds,Integer state);

}
