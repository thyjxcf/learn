/* 
 * @(#)FlowTemplateService.java    Created on 2012-12-11
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.template.service;

import java.util.List;

import net.zdsoft.eis.base.auditflow.template.entity.Flow;
import net.zdsoft.eis.base.auditflow.template.entity.FlowStep;
import net.zdsoft.leadin.cache.CacheManager;

/**
 * 流程模板
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-12-11 下午05:01:06 $
 */
public interface FlowTemplateService extends CacheManager{
	/**
	 * 获取模板
	 * 
	 * @param businessType
	 * @return
	 */
	public Flow getFlow(int businessType);

	public List<Flow> getFlows(Flow template);
	/**
	 * 根据模板（如果取不到，则取默认模板）取流程步骤信息
	 * 
	 * @param template
	 * @return
	 */
	public List<FlowStep> getSteps(Flow template);

	public List<FlowStep> getSteps(int businessType);

	public List<FlowStep> getSuitedSteps(final int businessType);
}
