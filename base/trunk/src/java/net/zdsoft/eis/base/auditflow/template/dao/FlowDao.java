/* 
 * @(#)FlowDao.java    Created on 2012-12-11
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.template.dao;

import java.util.List;

import net.zdsoft.eis.base.auditflow.template.entity.Flow;

/**
 * 流程模板
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-12-11 下午02:22:13 $
 */
public interface FlowDao {
	public Flow getFlow(int businessType);
	
	/**
	 * 根据业务主键取流程信息
	 * 
	 * @param template
	 * @return
	 */
	public Flow getMatchedFlow(Flow template);
	
	public List<Flow> getFlows(Flow template);
}
