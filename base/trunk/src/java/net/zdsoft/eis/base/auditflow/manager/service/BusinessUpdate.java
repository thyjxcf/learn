/* 
 * @(#)BusinessUpdate.java    Created on 2012-12-7
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.service;

import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-12-7 上午11:13:38 $
 */
public interface BusinessUpdate<E extends ApplyBusiness> {

	/**
	 * 修改业务
	 * 
	 * @param e
	 */
	public void updateBusiness(E e);
}
