/* 
 * @(#)EmployeeApplyBusiness.java    Created on 2012-12-10
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.entity;


/**
 * 教职工申请业务
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-12-10 下午02:18:23 $
 */
public abstract class TeacherApplyBusiness extends ApplyBusiness {
	private static final long serialVersionUID = -1998183190900274094L;
	
	@Override
	public String getOwnerId() {
		return getEmployeeId();
	}

	public String getOwnerIdName() {
		return "employeeId";
	}
	
	public abstract String getEmployeeId();
}
