/* 
 * @(#)FlowStep.java    Created on 2012-11-19
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.template.entity;

/**
 * 流程发起者
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-19 下午05:00:27 $
 */
public class FlowInitiator {
	private String applyId;// 申请id
	private String applyUnitId;// 申请单位id
	private String organizeUnitId;// 组织单位id
	private String cooperateUnitId;// 合作方
	private int section = Flow.NOT_EXISTS;// -1表示不分学段

	private String applyUserId;//申请人id
	private String businessId;

	public FlowInitiator(String applyId, String applyUnitId) {
		super();
		this.applyId = applyId;
		this.applyUnitId = applyUnitId;
	}
	
	public FlowInitiator(String applyId, String applyUnitId, String organizeUnitId) {
		this(applyId, applyUnitId);
		this.organizeUnitId = organizeUnitId;
	}
	
	public FlowInitiator(String applyId, String applyUnitId,String applyUserId,String businessId) {
		this(applyId, applyUnitId);
		this.applyUserId =applyUserId;
		this.businessId =businessId;
	}


	public String getCooperateUnitId() {
		return cooperateUnitId;
	}

	public void setCooperateUnitId(String cooperateUnitId) {
		this.cooperateUnitId = cooperateUnitId;
	}

	public String getApplyId() {
		return applyId;
	}

	public String getApplyUnitId() {
		return applyUnitId;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public String getOrganizeUnitId() {
		return organizeUnitId;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public String getBusinessId() {
		return businessId;
	}
}
