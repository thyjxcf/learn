/* 
 * @(#)EisuStudentDao.java    Created on May 16, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.simple.dao.AbstractStudentDao;
import net.zdsoft.eisu.base.common.entity.EisuStudent;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 16, 2011 3:00:54 PM $
 */
public interface EisuStudentDao extends AbstractStudentDao<EisuStudent> {
    /**
     * 根据专业id取学生信息
     * 
     * @param specialtyId
     * @return
     */
    public List<EisuStudent> getStudentsBySpecialtyId(String specialtyId);

    /**
     * 根据专业方向id取学生信息
     * 
     * @param specialtyId
     * @param specialtyPointId
     * @return
     */
    public List<EisuStudent> getStudentsBySpecialtyPointId(String specialtyId,
            String specialtyPointId);

    /**
     * 根据专业id和是否新生取学生信息
     * 
     * @param specialtyIds
     * @param isContainFreshman 是否包含新生
     * @param studentName 左匹配
     * @param studentCode 左匹配
     * @return
     */
    public List<EisuStudent> getStudentsBySpecialtyId(String[] specialtyIds,
            boolean isContainFreshman, String studentName, String studentCode);
    
    /**
     * 根据ids修改政治面貌
     * @param background
     * @param ids
     */
    public void updateBackGroundByIds(String background, String[] ids);
}
