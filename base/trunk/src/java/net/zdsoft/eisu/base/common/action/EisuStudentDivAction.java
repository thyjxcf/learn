/* 
 * @(#)EisuStudentDivAction.java    Created on Jun 17, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.action.StudentDivAction;
import net.zdsoft.eis.base.simple.entity.SimpleStudent;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eisu.base.common.entity.EisuStudent;
import net.zdsoft.eisu.base.common.service.EisuStudentService;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 17, 2011 4:58:24 PM $
 */
public class EisuStudentDivAction extends StudentDivAction {
    private static final long serialVersionUID = 283754047373403653L;

    private EisuStudentService eisuStudentService;

    /**
     * 是否需要权限控制，所有下属权限
     */
    private boolean needAllPopedom = false;

    /**
     * 是否需要权限控制，直接下属权限
     */
    private boolean needDirectPopedom = false;

    /**
     * 左匹配姓名 学号查询学生信息
     * 
     * @return
     */
    public String getStudentsByFaintnessInstituteId() {
        studentList = new ArrayList<SimpleStudent>();
        if (StringUtils.isEmpty(queryObjectName) && StringUtils.isEmpty(queryObjectCode)) {
            studentList = new ArrayList<SimpleStudent>();
        } else {
            LoginInfo loginInfo = getLoginInfo();

            boolean isAll = false;
            if (needAllPopedom) {
                isAll = true;
            } else if (needDirectPopedom) {
                isAll = false;
            }

            List<EisuStudent> students = eisuStudentService.getStudentsByFaintnessInstituteId(
                    loginInfo.getUnitID(), loginInfo.getUser().getInstituteId(), isAll, false,
                    queryObjectName, queryObjectCode);
            for (EisuStudent stu : students) {
                studentList.add(stu);
            }
            disposeObjects(studentList);

        }
        return SUCCESS;
    }

    public void setEisuStudentService(EisuStudentService eisuStudentService) {
        this.eisuStudentService = eisuStudentService;
    }

    public void setNeedAllPopedom(boolean needAllPopedom) {
        this.needAllPopedom = needAllPopedom;
    }

    public void setNeedDirectPopedom(boolean needDirectPopedom) {
        this.needDirectPopedom = needDirectPopedom;
    }

    public boolean isNeedAllPopedom() {
        return needAllPopedom;
    }

    public boolean isNeedDirectPopedom() {
        return needDirectPopedom;
    }

}
