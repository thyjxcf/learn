/* 
 * @(#)SpecialtyType.java    Created on May 31, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * 专业类别
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 31, 2011 11:26:28 AM $
 */
public class SpecialtyType extends BaseEntity {
    private static final long serialVersionUID = -4721487421379223541L;
    private String unitId;
    private String typeCode;
    private String typeName;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
