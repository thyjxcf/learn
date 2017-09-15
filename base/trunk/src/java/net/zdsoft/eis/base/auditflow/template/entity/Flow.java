/* 
 * @(#)Flow.java    Created on 2012-12-11
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.template.entity;

import java.util.List;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-12-11 下午02:28:00 $
 */
public class Flow extends BaseEntity {
	private static final long serialVersionUID = -2028165470023431424L;

	public static final int NOT_EXISTS = -1;// 不存在
	public static final int DEFAULT_TYPE = -255;
	
	public static final int STEP_ONE_TIME = 1;//一次性生成全部步骤

	public static final int STEP_BY_STEP = 2;//根据上一步生成下一步骤

	/**
	 * 流程类型
	 */
	private int type;
	
	private int stepType;

	/**
	 * 学段
	 */
	private int section = NOT_EXISTS;

	/**
	 * 目标单位级别
	 */
	private int targetRegionLevel = NOT_EXISTS;

	/**
	 * 源单位级别
	 */
	private int sourceRegionLevel;
	
	/**
	 * 默认类型
	 */
	private List<Integer> defaultTypes;

	public Flow() {
		super();
	}

	public Flow(int type, int sourceRegionLevel) {
		super();
		this.type = type;
		this.sourceRegionLevel = sourceRegionLevel;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public int getTargetRegionLevel() {
		return targetRegionLevel;
	}

	public void setTargetRegionLevel(int targetRegionLevel) {
		this.targetRegionLevel = targetRegionLevel;
	}

	public int getSourceRegionLevel() {
		return sourceRegionLevel;
	}

	public void setSourceRegionLevel(int sourceRegionLevel) {
		this.sourceRegionLevel = sourceRegionLevel;
	}

	public List<Integer> getDefaultTypes() {
		return defaultTypes;
	}

	public void setDefaultTypes(List<Integer> defaultTypes) {
		this.defaultTypes = defaultTypes;
	}

	public int getStepType() {
		return stepType;
	}

	public void setStepType(int stepType) {
		this.stepType = stepType;
	}

	
}
