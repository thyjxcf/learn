/* 
 * @(#)EisuStudentService.java    Created on May 16, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.service;

import java.util.List;

import net.zdsoft.eis.base.simple.service.AbstractStudentService;
import net.zdsoft.eisu.base.common.entity.EisuStudent;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 16, 2011 3:05:18 PM $
 */
public interface EisuStudentService extends AbstractStudentService<EisuStudent> {
    /**
     * 根据专业id取学生信息
     * 
     * @param specialtyId
     * @return
     */
    public List<EisuStudent> getStudentsBySpecialtyId(String specialtyId);

    /**
     * 根据专业id和专业方向id取学生信息
     * 
     * @param specialtyId
     * @param specialtyPointId
     * @return
     */
    public List<EisuStudent> getStudentsBySpecialtyPointId(String specialtyId,
            String specialtyPointId);

    /**
     * 取院系下的学生
     * 
     * @param schoolId
     * @param instituteId
     * @param isAll true 所有下属，false 直接下属
     * @param isContainFreshman 是否包含新生
     * @param studentName 左匹配
     * @param studentCode 左匹配
     * @return
     */
    public List<EisuStudent> getStudentsByFaintnessInstituteId(String schoolId, String instituteId,
            boolean isAll, boolean isContainFreshman, String studentName, String studentCode);
    
    /**
     * 根据ids修改政治面貌
     * @param background
     * @param ids
     */
    public void updateBackGroundByIds(String background, String[] ids);

}
