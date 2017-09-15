/* 
 * @(#)Institute.java    Created on May 13, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.entity;

import java.util.Date;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 13, 2011 5:18:42 PM $
 */
public class Institute extends BaseEntity {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4609267351798405392L;

    /**
     * 院系类别:院
     */
    public static final int INSTITUTE_KIND_INSTITUTE = 1;

    /**
     * 院系类别:系
     */
    public static final int INSTITUTE_KIND_DEPARTMENT = 2;

    /**
     * 上级类型:学校
     */
    public static final int PARENT_SCHOOL = BaseConstant.PARENT_SCHOOL;

    /**
     * 上级类型:院
     */
    public static final int PARENT_INSTITUTE = BaseConstant.PARENT_INSTITUTE;

    private String unitId;
    private String instituteCode;//院系所号
    private String instituteName;//院系所名称
    private String englishName;//院系所英文名称
    private String shortName;//院系所简称
    private Date buildDate;//建立年月
    private String civilMasterId;// 行政负责人
    private String partyMasterId;// 党务负责人
    private int instituteKind;// 院系办别
    private int state;//是否有效
    private String openType;//类别码
    private String parentId;// 所属上级
    private int parentType;// 上级类型：1学校、2院
    
    private String parentName;

    public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getInstituteCode() {
        return instituteCode;
    }

    public void setInstituteCode(String instituteCode) {
        this.instituteCode = instituteCode;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
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

    public String getCivilMasterId() {
        return civilMasterId;
    }

    public void setCivilMasterId(String civilMasterId) {
        this.civilMasterId = civilMasterId;
    }

    public String getPartyMasterId() {
        return partyMasterId;
    }

    public void setPartyMasterId(String partyMasterId) {
        this.partyMasterId = partyMasterId;
    }

    public int getInstituteKind() {
        return instituteKind;
    }

    public void setInstituteKind(int instituteKind) {
        this.instituteKind = instituteKind;
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

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
