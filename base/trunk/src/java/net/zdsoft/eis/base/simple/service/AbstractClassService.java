/* 
 * @(#)AbstractClassService.java    Created on May 14, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.simple.dao.AbstractClassDao;
import net.zdsoft.eis.base.simple.entity.SimpleClass;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 14, 2011 2:07:19 PM $
 */
public interface AbstractClassService<E extends SimpleClass> {

    public AbstractClassDao<E> getClassDao();

    // ======================单个对象=======================
    /**
     * 根据班级GUID取得班级的信息
     * 
     * @param classId 班级GUID
     * @return
     */
    public E getClass(String classId);

    // ======================列表=======================
    /**
     * 根据班级id获取班级列表
     */
    public List<E> getClasses(String[] classIds);
    
    /**
     * 根据学校ID得到未毕业的班级列表
     * 
     * @param schoolId 学校ID
     * @return List
     */
    public List<E> getClasses(String schoolId);

    /**
     * 根据学校ID和开设学年得到班级列表
     * 
     * @param schoolId
     * @param openAcadyear
     * @return
     */
    public List<E> getClassesByOpenAcadyear(String schoolId, String openAcadyear);

    // ======================Map=======================
    /**
     * 取班级Map key=classId
     */
    public Map<String, E> getClassMap(String[] classIds);
    
    public Map<String, E> getClassMapWithDeleted(String[] classIds);
}
