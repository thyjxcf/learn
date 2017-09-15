/* 
 * @(#)BusinessAdd.java    Created on 2012-12-7
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.service;

import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;

public interface BusinessAdd<E extends ApplyBusiness> {
	/**
	 * 增加业务记录
	 * 
	 * @param e
	 */
	public void addBusiness(E e);
}
