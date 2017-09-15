/* 
 * @(#)SimpleObject.java    Created on Jun 23, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * 简单对象
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Jun 23, 2011 2:49:22 PM $
 */
public class SimpleObject extends BaseEntity {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -1325821704544207675L;

    private String unitId; // 单位id
    private String groupId; // 组id
    private String objectName; // 对象名
    private String objectCode; // 对象代码：单位内惟一
    private String unitiveCode; // 统一编号：全局惟一
    private String fullName;//长名：如行政区划：objectName=西湖区，shortName=浙江省杭州市西湖区

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public String getUnitiveCode() {
        return unitiveCode;
    }

    public void setUnitiveCode(String unitiveCode) {
        this.unitiveCode = unitiveCode;
    }

	public String getFullName() {
		if (fullName == null) {
			fullName = objectName;
		}
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

    
}
