/* 
 * @(#)EisuGrade.java    Created on May 18, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 18, 2011 11:22:37 AM $
 */
public class EisuGrade extends BaseEntity {
    private static final long serialVersionUID = 8754313450166001294L;

    private String schid;
    private String gradename;
    private String acadyear;// 即openAcadyear

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String gradename) {
        this.gradename = gradename;
    }

    public String getAcadyear() {
        return acadyear;
    }

    public void setAcadyear(String acadyear) {
        this.acadyear = acadyear;
    }

    public String getSchid() {
        return schid;
    }

    public void setSchid(String schid) {
        this.schid = schid;
    }

}
