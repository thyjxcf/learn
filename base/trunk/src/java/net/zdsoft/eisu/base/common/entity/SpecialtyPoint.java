/* 
 * @(#)SpecialtyPoint.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.entity;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 7:35:17 PM $
 */
public class SpecialtyPoint extends BaseEntity {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 2286071219857062458L;

    public static final String SPECIALTY_POINT_NONE_NAME = "无方向";
    public static final String SPECIALTY_POINT_NONE_ID = BaseConstant.ZERO_GUID;

    private String unitId;//单位id
    private String specialtyId;//专业id
    private String pointName;//专业方向名称
    private String pointCode;//专业方向代码
    private int state;//状态
    private int parentState;
    
    

    public String getPointCode() {
		return pointCode;
	}

	public void setPointCode(String pointCode) {
		this.pointCode = pointCode;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(String specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

	public int getParentState() {
		return parentState;
	}

	public void setParentState(int parentState) {
		this.parentState = parentState;
	}

}
