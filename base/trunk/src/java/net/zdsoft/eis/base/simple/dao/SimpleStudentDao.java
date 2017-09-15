/* 
 * @(#)SimpleStudentDao1.java    Created on May 28, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.dao;

import java.util.Map;

import net.zdsoft.eis.base.simple.entity.SimpleStudent;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 28, 2011 3:59:37 PM $
 */
public interface SimpleStudentDao extends AbstractStudentDao<SimpleStudent> {
	 public Map<String ,SimpleStudent> getStudentdexByStudentIds(String[] studentIds) ;
}
