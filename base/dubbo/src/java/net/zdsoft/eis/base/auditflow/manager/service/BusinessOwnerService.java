/* 
 * @(#)BusinessOwnerService.java    Created on 2012-11-26
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.auditflow.manager.entity.BusinessOwner;

/**
 * 申请业务所有者
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-26 上午09:47:55 $
 */
public interface BusinessOwnerService {

	/**
	 * 取列标题
	 * 
	 * @return
	 */
	public List<String> getFieldHeads();
	
	public String getMacroPage(); 

	public BusinessOwner getOwner(String ownerId);

	public Map<String, BusinessOwner> getOwnerMap(String[] ownerIds);
}
