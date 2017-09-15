/* 
 * @(#)TeachPlaceRes.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 8:13:46 PM $
 */
public class TeachPlaceRes extends BaseEntity {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 8060979571648276190L;

    private String unitId;
    private String teachPlaceId;
    private String teachResId;

    // =====================辅助字段==========================
    private String teachResName;// 教学资源名称

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getTeachPlaceId() {
        return teachPlaceId;
    }

    public void setTeachPlaceId(String teachPlaceId) {
        this.teachPlaceId = teachPlaceId;
    }

    public String getTeachResId() {
        return teachResId;
    }

    public void setTeachResId(String teachResId) {
        this.teachResId = teachResId;
    }

    public String getTeachResName() {
        return teachResName;
    }

    public void setTeachResName(String teachResName) {
        this.teachResName = teachResName;
    }

}
