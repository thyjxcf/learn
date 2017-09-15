/*
 * Class:   ShowStep.java
 * Author:  Sophie Dong
 * Copyright (c) 2006 winupon Networks, Inc. All rights reserved.
 * 
 * Last modified & Comments:
 * <BR>$ShowStep.java$
 * <BR>Revision 1.00  2008-3-17  上午09:42:42  dongxx
 * <BR>first version
 * <BR>
 */
package net.zdsoft.eis.base.auditflow.template.dto;

import net.zdsoft.eis.base.common.entity.Unit;

/**
 * 包含用于显示流程步骤用的信息
 * 
 * @author dongxx
 * @version 1.0
 */
public class ShowStep {

	/**
	 * 顺序
	 */
	private int order;

	/**
	 * 单位类别名称
	 */
	private String levelname;

	/**
	 * 单位名称
	 */
	private String unitname;

	/**
	 * 审核结果
	 */
	private int result;

	public static String getLevelShowName(int level) {
		if (Unit.UNIT_REGION_COUNTRY == level) {
			return "国家";
		} else if (Unit.UNIT_REGION_PROVINCE == level) {
			return "省教育厅";
		} else if (Unit.UNIT_REGION_CITY == level) {
			return "市教育局";
		} else if (Unit.UNIT_REGION_COUNTY == level) {
			return "区教育局";
		} else if (Unit.UNIT_REGION_LEVEL == level) {
			return "乡镇教育局";
		}
		return null;
	}

	public String getLevelname() {
		return levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

}
