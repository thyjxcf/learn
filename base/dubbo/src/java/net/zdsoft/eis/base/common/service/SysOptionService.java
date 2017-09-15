/* 
 * @(#)SysOptionService.java    Created on Dec 9, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.service;

import net.zdsoft.eis.base.common.entity.SysOption;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2009 5:17:23 PM $
 */
public interface SysOptionService {
	/**
	 * 取参数的nowvalue，如果为空，则取dvalue
	 * @param optionCode
	 * @return
	 */
	public String getValue(String optionCode);

	/**
	 * 取参数的nowvalue，如果为空，则取dvalue
	 * @param optionCode
	 * @return
	 */
	public boolean getBooleanValue(String optionCode);

	/**
	 * 运营参数
	 * @param optionCode
	 * @return
	 */
	public SysOption getSysOption(String optionCode);
}
