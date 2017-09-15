/* 
 * @(#)AbstractClassServiceImpl.java    Created on May 14, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.simple.entity.SimpleClass;
import net.zdsoft.eis.base.simple.service.AbstractClassService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 14, 2011 2:17:38 PM $
 */
public abstract class AbstractClassServiceImpl<E extends SimpleClass> implements
        AbstractClassService<E> {

    public E getClass(String classId) {
        return getClassDao().getClass(classId);
    }
    
    public List<E> getClasses(String[] classIds) {
        return getClassDao().getClasses(classIds);
    }
    
    public Map<String, E> getClassMap(String[] classIds) {
        return getClassDao().getClassMap(classIds);
    }

    public Map<String, E> getClassMapWithDeleted(String[] classIds) {
    	return getClassDao().getClassMapWithDeleted(classIds);
    }
    
    public List<E> getClasses(String schoolId) {
        return getClassDao().getClasses(schoolId, false);
    }
    
    public List<E> getClassesByOpenAcadyear(String schoolId, String openAcadyear){
        return getClassDao().getClassesByOpenAcadyear(schoolId, openAcadyear);
    }
}
