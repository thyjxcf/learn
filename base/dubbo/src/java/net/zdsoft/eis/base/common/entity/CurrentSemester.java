/* 
 * @(#)CurrentSemester.java    Created on Jul 12, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.entity;

import java.io.Serializable;

/**
 * 当前学年学期
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jul 12, 2010 3:37:00 PM $
 */
public class CurrentSemester implements Serializable {
    private static final long serialVersionUID = -8992595666457291131L;

    private String acadyear; // 学年
    private String semester; // 学期

    public String getAcadyear() {
        return acadyear;
    }

    public void setAcadyear(String acadyear) {
        this.acadyear = acadyear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

}
