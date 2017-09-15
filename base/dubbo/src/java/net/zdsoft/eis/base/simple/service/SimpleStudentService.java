/* 
 * @(#)SimpleStudentService.java    Created on May 28, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.service;

import java.util.Map;

import net.zdsoft.eis.base.simple.entity.SimpleStudent;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 28, 2011 4:06:54 PM $
 */
public interface SimpleStudentService extends AbstractStudentService<SimpleStudent> {
	 public Map<String ,SimpleStudent> getStudentdexByStudentIds(String[] studentIds);
}
