/* 
 * @(#)EisuClass.java    Created on May 14, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.entity;

import net.zdsoft.eis.base.simple.entity.SimpleClass;

/**
 * 班级
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 14, 2011 11:31:04 AM $
 */
public class EisuClass extends SimpleClass {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4654435923591711004L;

    // =====================以下为eisu==========================
    private String specialtyId;// 专业
    private String specialtyPointId;// 专业方向
    private String teachAreaId;// 所在教学区
    private String teachPlaceId;// 专用教学场地
    private String preGraduateSign;//是否是预毕业班级
    private String graduateAcadyear;//毕业学年
    private String firstType;//班级类别
    
    // =====================辅助字段==========================
    private String specialtyName;// 专业
    private String specialtyPointName;// 专业方向
    private String teachAreaName;// 所在教学区
    private String teachPlaceName;// 专用教学场地
    private int specialtyState;//是否专业及专业方向都有效  无效则班级不能编辑
    
    //扩展字段 年级
    private String gradeName;// 年级
    

    public String getFirstType() {
		return firstType;
	}

	public void setFirstType(String firstType) {
		this.firstType = firstType;
	}

    public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(String specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getSpecialtyPointId() {
        return specialtyPointId;
    }

    public void setSpecialtyPointId(String specialtyPointId) {
        this.specialtyPointId = specialtyPointId;
    }

    public String getTeachAreaId() {
        return teachAreaId;
    }

    public void setTeachAreaId(String teachAreaId) {
        this.teachAreaId = teachAreaId;
    }

    public String getTeachPlaceId() {
        return teachPlaceId;
    }

    public void setTeachPlaceId(String teachPlaceId) {
        this.teachPlaceId = teachPlaceId;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public String getSpecialtyPointName() {
        return specialtyPointName;
    }

    public void setSpecialtyPointName(String specialtyPointName) {
        this.specialtyPointName = specialtyPointName;
    }

    public String getTeachAreaName() {
        return teachAreaName;
    }

    public void setTeachAreaName(String teachAreaName) {
        this.teachAreaName = teachAreaName;
    }

    public String getTeachPlaceName() {
        return teachPlaceName;
    }

    public void setTeachPlaceName(String teachPlaceName) {
        this.teachPlaceName = teachPlaceName;
    }

	public String getPreGraduateSign() {
		return preGraduateSign;
	}

	public void setPreGraduateSign(String preGraduateSign) {
		this.preGraduateSign = preGraduateSign;
	}

	public String getGraduateAcadyear() {
		return graduateAcadyear;
	}

	public void setGraduateAcadyear(String graduateAcadyear) {
		this.graduateAcadyear = graduateAcadyear;
	}

	public int getSpecialtyState() {
		return specialtyState;
	}

	public void setSpecialtyState(int specialtyState) {
		this.specialtyState = specialtyState;
	}

    @Override
    /**
     * 中职不需要加上年级名称
     */
    public String getClassnamedynamic() {
        return getClassname();
    }
}
