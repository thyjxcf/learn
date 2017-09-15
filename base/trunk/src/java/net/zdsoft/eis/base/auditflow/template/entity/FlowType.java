/* 
 * @(#)FlowType.java    Created on 2012-11-19
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.template.entity;

/**
 * 流程类型
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-19 下午05:16:59 $
 */
public class FlowType {
	/**
	 * 业务模式: 单向单个用户参与发起的业务：无合作方
	 */
	public static final int BUSINESS_MODE_SINGLE_ONE_WAY = 1;

	/**
	 * 业务模式: 双向单个用户参与发起的的业务
	 */
	public static final int BUSINESS_MODE_SINGLE_TWO_WAY = 2;

	/**
	 * 业务模式: 单向多个用户参与发起的的业务：有合作方
	 */
	public static final int BUSINESS_MODE_MULTI_ONE_WAY = 3;

	/**
	 * 业务模式: 双向多个用户参与发起的的业务
	 */
	public static final int BUSINESS_MODE_MULTI_TWO_WAY = 4;

	private boolean needAudit = true;// 是否需要审核，默认需审核
	private int businessType;// 申请对象类型（业务类型，一般对应某张表），4位
	private int businessMode = BUSINESS_MODE_SINGLE_ONE_WAY;// 业务类型：单方还是双发发起、是否有合作方，1位

	private int value;// 类型值

	public FlowType(int businessType) {
		super();
		this.businessType = businessType;
		this.value = this.businessType * 100 + (needAudit ? 0 : 1) * 10 + businessMode;
	}

	public FlowType(int businessType, int value) {
		this(businessType);
		if (value != 0) {
			this.value = value;
		}
	}

	public FlowType(int businessType, boolean needAudit, int businessMode) {
		super();
		this.businessType = businessType;
		this.needAudit = needAudit;
		this.businessMode = businessMode;
		value = businessType * 100 + (needAudit ? 0 : 1) * 10 + businessMode;
	}

	public boolean isNeedAudit() {
		return needAudit;
	}

	public int getBusinessType() {
		return businessType;
	}

	public int getBusinessMode() {
		return businessMode;
	}

	public int getValue() {
		return value;
	}

}
