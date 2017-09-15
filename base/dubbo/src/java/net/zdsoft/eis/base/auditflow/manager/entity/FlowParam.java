/* 
 * @(#)FlowParam.java    Created on 2012-11-16
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.entity;

/**
 * 配置参数
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-16 下午03:53:06 $
 */
public class FlowParam {
	private String applyBusinessService; // 业务处理service

	public FlowParam(String applyBusinessService) {
		this.applyBusinessService = applyBusinessService;
	}

	public String getApplyBusinessService() {
		return applyBusinessService;
	}

}
