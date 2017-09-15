/* 
 * @(#)FlowStepDao.java    Created on 2012-12-11
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.template.dao;

import java.util.List;

import net.zdsoft.eis.base.auditflow.template.entity.FlowStep;

/**
 * 流程步骤
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-12-11 下午02:50:29 $
 */
public interface FlowStepDao {
	/**
	 * 根据流程id取步骤信息
	 * 
	 * @param flowId
	 * @return
	 */
	public List<FlowStep> getSteps(String flowId);
}
