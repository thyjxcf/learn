/* 
 * @(#)Specialty.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.entity;

import java.util.Date;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 4:28:44 PM $
 */
public class Specialty extends BaseEntity {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 上级类型:学校
     */
    public static final int PARENT_SCHOOL = BaseConstant.PARENT_SCHOOL;

    /**
     * 上级类型:院系
     */
    public static final int PARENT_INSTITUTE = BaseConstant.PARENT_INSTITUTE;

    private String unitId;
    private String specialtyCode;
    private String specialtyName;
    private String englishName;
    private String shortName;
    private Date buildDate;
    private String parentId;// 院系所id(所属上级)
    private int parentType;// 上级类型：1学校、2院系
    private String specialtyTypeId;// 专业类别
    private String degree;// 学位
    private String academicQualification;// 学历
    private int schoolingLength;// 修读年限(原年制)
    private String specialtyCatalog;//专业目录版本
    //扩展字段
    private int state;// 状态
    private String supervisor;//专业负责人
    private String specialtyCategory ;//专业类型(大专业/小专业)
    private String belongSpecialty ;//所属专业id
    private Float bxCredit ;//必修学分
    private Float xxCredit ;//限选学分
    private Float rxCredit ;//任选学分
    private String parentName;// 所属院系名称
    private String supervisorName;//专业负责人名称
    private String pointName;
    
    private int parentState;

    public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getSpecialtyCategory() {
		return specialtyCategory;
	}

	public void setSpecialtyCategory(String specialtyCategory) {
		this.specialtyCategory = specialtyCategory;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getBelongSpecialty() {
		return belongSpecialty;
	}

	public void setBelongSpecialty(String belongSpecialty) {
		this.belongSpecialty = belongSpecialty;
	}

	public Float getBxCredit() {
		return bxCredit;
	}

	public void setBxCredit(Float bxCredit) {
		this.bxCredit = bxCredit;
	}

	public Float getXxCredit() {
		return xxCredit;
	}

	public void setXxCredit(Float xxCredit) {
		this.xxCredit = xxCredit;
	}

	public Float getRxCredit() {
		return rxCredit;
	}

	public void setRxCredit(Float rxCredit) {
		this.rxCredit = rxCredit;
	}

	public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getSpecialtyCode() {
        return specialtyCode;
    }

    public void setSpecialtyCode(String specialtyCode) {
        this.specialtyCode = specialtyCode;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

    public String getSpecialtyTypeId() {
        return specialtyTypeId;
    }

    public void setSpecialtyTypeId(String specialtyTypeId) {
        this.specialtyTypeId = specialtyTypeId;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getAcademicQualification() {
        return academicQualification;
    }

    public void setAcademicQualification(String academicQualification) {
        this.academicQualification = academicQualification;
    }

    public int getSchoolingLength() {
        return schoolingLength;
    }

    public void setSchoolingLength(int schoolingLength) {
        this.schoolingLength = schoolingLength;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getParentType() {
        return parentType;
    }

    public void setParentType(int parentType) {
        this.parentType = parentType;
    }

	public int getParentState() {
		return parentState;
	}

	public void setParentState(int parentState) {
		this.parentState = parentState;
	}

	public String getSpecialtyCatalog() {
		return specialtyCatalog;
	}

	public void setSpecialtyCatalog(String specialtyCatalog) {
		this.specialtyCatalog = specialtyCatalog;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	
}
