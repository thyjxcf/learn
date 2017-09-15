/* 
 * @(#)BaseAction.java    Created on Mar 5, 2007
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44CVSROOT/exam/src/net/zdsoft/exam/client/BaseAction.java,v 1.6 2007/03/15 09:53:06 linqz Exp $
 */
package net.zdsoft.eis.frame.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.CurrentSemester;
import net.zdsoft.eis.base.common.service.SchoolSemesterService;
import net.zdsoft.eis.base.common.service.SemesterService;

public class BaseSemesterAction extends BaseAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -6789871992255951303L;

    protected SemesterService semesterService;
    protected SchoolSemesterService schoolSemesterService;

    protected String acadyear;
    protected String semester;

    /**
     * 学年列表
     */
    public List<String> getAcadyearList() {
        return semesterService.getAcadyears();
    }

    public CurrentSemester getCurrentSemester() {
        return semesterService.getCurrentSemester();
    }

    /**
     * @return the curAcadyear
     */
    public String getAcadyear() {
        if (StringUtils.isEmpty(acadyear)) {
            CurrentSemester sem = getCurrentSemester();
            if (sem != null)
                acadyear = getCurrentSemester().getAcadyear();
            else
                acadyear = "";
        }
        return acadyear;
    }

    /**
     * @return the curSemester
     */
    public String getSemester() {
        if (StringUtils.isEmpty(semester)) {
            CurrentSemester sem = getCurrentSemester();
            if (sem != null)
                semester = getCurrentSemester().getSemester();
            else
                semester = "";
        }
        return semester;
    }

    public void setAcadyear(String acadyear) {
        this.acadyear = acadyear;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setSemesterService(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    public void setSchoolSemesterService(SchoolSemesterService schoolSemesterService) {
        this.schoolSemesterService = schoolSemesterService;
    }

}
