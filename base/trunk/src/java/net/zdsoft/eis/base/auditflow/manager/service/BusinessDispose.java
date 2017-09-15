/* 
 * @(#)BusinessDispose.java    Created on 2012-12-7
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.service;

import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;

/**
 * 业务处理
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-12-7 上午11:05:47 $
 */
public interface BusinessDispose<E extends ApplyBusiness> {
	/**
	 * 审核完成后的业务处理
	 * 
	 * @param e
	 */
	public void saveDisposeBusiness(E e);
}
