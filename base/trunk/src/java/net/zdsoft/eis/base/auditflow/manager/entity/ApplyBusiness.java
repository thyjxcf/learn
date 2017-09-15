/* 
' * @(#)FlowBusiness.java    Created on 2012-11-2
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.form.Field;

/**
 * 申请的业务对象
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-2 下午05:36:30 $
 */
public class ApplyBusiness extends Attachment {

	private static final long serialVersionUID = -1430549826064673071L;

	private String businessId;// 业务主表id
	private String unitId;// 单位id
	private int flowTypeValue;// 流程类型值
	private String organizeUnitId;// 组织单位id

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public int getFlowTypeValue() {
		return flowTypeValue;
	}

	public void setFlowTypeValue(int flowTypeValue) {
		this.flowTypeValue = flowTypeValue;
	}
	
	public String getOrganizeUnitId() {
		return organizeUnitId;
	}

	public void setOrganizeUnitId(String organizeUnitId) {
		this.organizeUnitId = organizeUnitId;
	}

	/**
	 * 是否是主人：默认false
	 * 
	 * @return
	 */
	public boolean isOwner() {
		return false;
	}

	/**
	 * 附属信息对应的主信息id，主要是学生id、教师id等
	 * 
	 * @return
	 */
	public String getOwnerId() {
		return null;
	}

	/**
	 * 取主信息id的字段名
	 * 
	 * @return
	 */
	public String getOwnerIdName() {
		return null;
	}

	private List<Field> fieldValues;// 字段值

	public void setFieldValues(List<Field> fields) {
		this.fieldValues = fields;
	}

	public List<Field> getFieldValues() {
		return fieldValues;
	}

	/**
	 * 字段修改内容
	 * 
	 * @return
	 */
	public List<Field> getFieldModifications() {
		List<Field> modifications = new ArrayList<Field>();
		if (fieldValues != null) {
			for (Field field : fieldValues) {
				Field child = field.getChildField();

				if (!field.getOldValue().equals(field.getValue())
						|| (child != null && !child.getOldValue().equals(child.getValue()))) {
					if (child != null && !child.getOldValue().equals(child.getValue())) {
						if (StringUtils.isNotBlank(child.getWrappedOldValue())) {
							field.setWrappedOldValue(field.getWrappedOldValue() + "("
									+ child.getWrappedOldValue() + ")");
						}
						if (StringUtils.isNotBlank(child.getWrappedValue())) {
							field.setWrappedValue(field.getWrappedValue() + "("
									+ child.getWrappedValue() + ")");
						}
					}
					modifications.add(field);
				}
			}
		}
		return modifications;
	}

	/**
	 * 列表值
	 * 
	 * @return
	 */
	public List<Field> getListFieldValues() {
		List<Field> list = new ArrayList<Field>();
		if (fieldValues != null) {
			for (Field field : fieldValues) {
				if (field.isListShow()) {
					list.add(field);
				}
			}
		}
		return list;
	}

	/**
	 * 业务名称
	 * 
	 * @return
	 */
	public String getBusinessName() {
		return null;
	}

}
