/* 
 * @(#)AbstractClassDao.java    Created on May 14, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.simple.entity.SimpleClass;

public interface AbstractClassDao<E extends SimpleClass> {

    public E getClass(String classId);

    // ======================列表=======================

    /**
     * 根据学校ID和是否毕结业得到班级列表
     * 
     * @param schoolId 学校ID
     * @param isGraduate 是否毕结业
     * @return List
     */
    public List<E> getClasses(String schoolId, boolean isGraduate);
    
    /**
     * 根据学校ID和开设学年得到班级列表
     * 
     * @param schoolId
     * @param openAcadyear
     * @return
     */
    public List<E> getClassesByOpenAcadyear(String schoolId, String openAcadyear);
    
    /**
     * 
     * classIds
     * @param classIds
     * @return
     */
    public List<E> getClasses(String[] classIds);

    // ======================Map=======================
    /**
     * 取班级Map key=classId
     */
    public Map<String, E> getClassMap(String[] classIds);

	public Map<String, E> getClassMapWithDeleted(String[] classIds);
}
