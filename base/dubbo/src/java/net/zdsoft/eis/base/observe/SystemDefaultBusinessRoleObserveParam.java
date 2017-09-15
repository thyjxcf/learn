/* 
 * @(#)SystemDefaultBusinessRoleObserveParam.java    Created on Jan 5, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.observe;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Grade;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jan 5, 2010 2:13:08 PM $
 */
public class SystemDefaultBusinessRoleObserveParam {
    public static final int SRC_CLASS = 1;
    public static final int SRC_GRADE = 2;

    private int src;
    private BasicClass oldClass;
    private BasicClass newClass;
    private Grade oldGrade;
    private Grade newGrade;

    public SystemDefaultBusinessRoleObserveParam(BasicClass oldClass, BasicClass newClass) {
        this.src = SRC_CLASS;
        this.oldClass = oldClass;
        this.newClass = newClass;
    }

    public SystemDefaultBusinessRoleObserveParam(Grade oldGrade, Grade newGrade) {
        this.src = SRC_GRADE;
        this.oldGrade = oldGrade;
        this.newGrade = newGrade;
    }

    public int getSrc() {
        return src;
    }

    public BasicClass getOldClass() {
        return oldClass;
    }

    public BasicClass getNewClass() {
        return newClass;
    }

    public Grade getOldGrade() {
        return oldGrade;
    }

    public Grade getNewGrade() {
        return newGrade;
    }

}
