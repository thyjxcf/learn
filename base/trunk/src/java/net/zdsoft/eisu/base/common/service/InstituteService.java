/* 
 * @(#)InstituteService.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.entity.Institute;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 7:30:07 PM $
 */
public interface InstituteService {

    /**
     * 根据id取院系
     * 
     * @param instituteId
     * @return
     */
    public Institute getInstitute(String instituteId);
    /**
     * 根据学生Id获取院系
     * @param studentId
     * @return
     * @author zhangkc
     * @date 2013年10月25日 上午10:10:32
     */
    public Institute getInstituteByStudentId(String studentId);
    
    /**
     * 根据单位id取院系
     * 
     * @param unitId
     * @return
     */
    public List<Institute> getInstitutesByUnitId(String unitId);

    /**
     * 根据上级和上级类型取直属院系
     * 
     * @param parentId
     * @param parentType
     * @return
     */
    public List<Institute> getInstitutesByParent(String parentId, int parentType);

    /**
     * 根据上级和上级类型取直属下属的所有院系
     * 
     * @param parentId
     * @param parentType
     * @param isContainSelf 如果是院系时是否包括自身
     * @return
     */
    public List<Institute> getAllInstitutesByParent(String parentId, int parentType,
            boolean isContainSelf);

    /**
     * 根据上级和上级类型取直属下属的所有院系的id
     * 
     * @param parentId
     * @param parentType
     * @param isContainSelf 如果是院系时是否包括自身
     * @return
     */
    public String[] getAllInstituteIdsByParent(String parentId, int parentType,
            boolean isContainSelf);

    /**
     * 根据上级和上级类型取院系
     * 
     * @param parentId
     * @param parentType
     * @param instituteKind 如果为0，则忽略此参数
     * @return
     */
    public List<Institute> getInstitutesByParent(String parentId, int parentType, int instituteKind);
    
    /**
     * 根据上级和上级类型取院系(适应新需求 有效无效)
     * @param parentId
     * @param parentType
     * @param isShow 是否显示全部(true全部 false过滤掉无效的)
     * @param instituteKind 如果为0，则忽略此参数
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
     * 根据上级和上级类型取直属下属的所有院系的id
     * 
     * @param parentId
     * @param parentType
     * @param isContainSelf 如果是院系时是否包括自身
     * @return
     */
    public String[] getAllInstituteIdsByParent(String[] parentId, int parentType,
            boolean isContainSelf);
    /**
     * 根据上级和上级类型取直属下属的所有院系
     * 
     * @param parentId
     * @param parentType
     * @param isContainSelf 如果是院系时是否包括自身
     * @return
     */
    public List<Institute> getAllInstitutesByParent(String[] parentId, int parentType,
            boolean isContainSelf);
    /**
     * 根据上级和上级类型取直属院系
     * 
     * @param parentId
     * @param parentType
     * @return
     */
    public List<Institute> getInstitutesByParent(String[] parentId, int parentType);
    /**
     * 取院系
     * 
     * @param instituteIds
     * @param state 0有效 1无效  null全部
     * @return
     */
    public List<Institute> getInstituteList(String[] instituteIds,Integer state);
}
