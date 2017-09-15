/* 
 * @(#)FlowStep.java    Created on 2012-12-11
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.template.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-12-11 下午02:51:51 $
 */
public class FlowStep extends BaseEntity {
	private static final long serialVersionUID = -7650033829396287480L;

	/**
	 * 当前学校及上级单位-出
	 */
	public static final int IOTYPE_OUT = 0;

	/**
	 * 目标学校及上级单位-入
	 */
	public static final int IOTYPE_IN = 1;

	/**
	 * 模板id
	 */
	private String flowId;

	/**
	 * 步骤值
	 */
	private int stepValue;

	/**
	 * 单位级别
	 */
	private int regionLevel;

	/**
	 * 单位类型
	 */
	private int auditUnitType;

	/**
	 * 转出转入类型
	 */
	private int ioType;
	
	private String roleCode;//角色code
	
	private String arrangeParam;//获取取值范围的参数
	
	private String arrangeMethod;//获取取值范围的方法


	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public int getStepValue() {
		return stepValue;
	}

	public void setStepValue(int stepValue) {
		this.stepValue = stepValue;
	}

	public int getRegionLevel() {
		return regionLevel;
	}

	public void setRegionLevel(int regionLevel) {
		this.regionLevel = regionLevel;
	}

	public int getAuditUnitType() {
		return auditUnitType;
	}

	public void setAuditUnitType(int auditUnitType) {
		this.auditUnitType = auditUnitType;
	}

	public int getIoType() {
		return ioType;
	}

	public void setIoType(int ioType) {
		this.ioType = ioType;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getArrangeParam() {
		return arrangeParam;
	}

	public void setArrangeParam(String arrangeParam) {
		this.arrangeParam = arrangeParam;
	}

	public String getArrangeMethod() {
		return arrangeMethod;
	}

	public void setArrangeMethod(String arrangeMethod) {
		this.arrangeMethod = arrangeMethod;
	}
}
