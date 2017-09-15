/* 
 * @(#)EisuGradeService.java    Created on May 18, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eisu.base.common.entity.EisuGrade;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 18, 2011 11:25:09 AM $
 */
public interface EisuGradeService {
    /**
     * 根据id取年级
     * 
     * @param gradeId
     * @return
     */
    public EisuGrade getGrade(String gradeId);

    /**
     * 根据学校和开设学年id取年级
     * @param schoolId
     * @param openAcadyear
     * @return
     */
    public EisuGrade getGrade(String schoolId, String openAcadyear);

    /**
     * 取所有年级
     * 
     * @param schoolId
     * 
     * @return
     */
    public List<EisuGrade> getGrades(String schoolId);
    
    /**
     * 取在用的年级
     * @param schoolId
     * @return
     */
    public List<EisuGrade> getActiveGrades(String schoolId);
    
    /**
     * 取所有年级 key:
     * @param schoolId
     * @return
     */
    public Map<String,EisuGrade> getGradesMap(String schoolId);
}
