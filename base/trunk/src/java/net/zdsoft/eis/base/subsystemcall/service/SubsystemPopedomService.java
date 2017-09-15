/* 
 * @(#)SubsystemPopedomService.java    Created on Dec 30, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.subsystemcall.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.BasicClass;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 30, 2009 4:16:59 PM $
 */
public interface SubsystemPopedomService {
    /**
     * 判断数据权限是否启用
     * 
     * @return 数据权限启用则返回true;不启用则返回false
     */
    public boolean isPopedomUsed();
    
    /**
     * 根据教师id，取得有用权限的id，此id可能是单位、学院、专业、部门、班级等
     * @param teacherId
     * @return
     */
    public List<String> getPopedomInstituteIds(String teacherId);
    
    public List<String> getPopedomClassIds(String teacherId);
    
    public List<String> getPopedomSpecialtyIds(String teacherId);
    
    public List<String> getPopedomSpecialtyPointIds(String teacherId);
    
    public List<String> getPopedomAcadyear(String teacherId, String unitId);

    /**
     * 取学校下的权限班级列表
     * @param schoolId
     * @param classState
     * @param graduateAcadyear
     * @param userId
     * @return
     */
    public List<BasicClass> getPopedomClassesBySch(String schoolId, int classState,
            String graduateAcadyear, String userId);

    /**
     * 取分校下的权限班级列表
     * @param campusId
     * @param classState
     * @param graduateAcadyear
     * @param userId
     * @return
     */
    public List<BasicClass> getPopedomClassesByCampus(String campusId, int classState,
            String graduateAcadyear, String userId);
}
