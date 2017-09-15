/*
 * Class:   BusinessFlowTemplate.java
 * Author:  Sophie Dong
 * Copyright (c) 2006 winupon Networks, Inc. All rights reserved.
 * 
 * Last modified & Comments:
 * <BR>$BusinessFlowTemplate.java$
 * <BR>Revision 1.00  2008-3-5  下午03:47:19  dongxx
 * <BR>first version
 * <BR>
 */
package net.zdsoft.eis.base.auditflow.template.entity;

import net.zdsoft.eis.frame.entity.HibernateEntity;

/**
 * 控制流程模板
 * 
 * @author dongxx
 */
public class BusinessFlowTemplate extends HibernateEntity {

	private static final long serialVersionUID = 2667004593678138168L;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 学段
	 */
	private Integer section;

	/**
	 * 目标单位级别
	 */
	private Integer targetRegionLevel;

	/**
	 * 源单位级别
	 */
	private Integer sourceRegionLevel;

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public Integer getSection() {
		return section;
	}

	public void setSection(Integer section) {
		this.section = section;
	}

	public Integer getSourceRegionLevel() {
		return sourceRegionLevel;
	}

	public void setSourceRegionLevel(Integer sourceRegionLevel) {
		this.sourceRegionLevel = sourceRegionLevel;
	}

	public Integer getTargetRegionLevel() {
		return targetRegionLevel;
	}

	public void setTargetRegionLevel(Integer targetRegionLevel) {
		this.targetRegionLevel = targetRegionLevel;
	}
}
