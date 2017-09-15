/* 
 * @(#)FlowTemplateServiceImpl.java    Created on 2012-12-11
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.template.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.auditflow.template.dao.FlowDao;
import net.zdsoft.eis.base.auditflow.template.dao.FlowStepDao;
import net.zdsoft.eis.base.auditflow.template.entity.Flow;
import net.zdsoft.eis.base.auditflow.template.entity.FlowStep;
import net.zdsoft.eis.base.auditflow.template.service.FlowTemplateService;
import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-12-11 下午05:03:14 $
 */
public class FlowTemplateServiceImpl extends DefaultCacheManager implements
		FlowTemplateService {
	private FlowDao flowDao;
	private FlowStepDao flowStepDao;

	public void setFlowDao(FlowDao flowDao) {
		this.flowDao = flowDao;
	}

	public void setFlowStepDao(FlowStepDao flowStepDao) {
		this.flowStepDao = flowStepDao;
	}

	@Override
	public Flow getFlow(int businessType) {
		return flowDao.getFlow(businessType);
	}

	@Override
	public List<FlowStep> getSteps(Flow template) {
		Flow flow = flowDao.getMatchedFlow(template);
		
		// 如果为空，则取默认模板
		if (flow == null && template.getDefaultTypes() != null) {
			Flow defaultTemplate = new Flow(template.getType(), template.getSourceRegionLevel());
			defaultTemplate.setSection(template.getSection());
			defaultTemplate.setTargetRegionLevel(template.getTargetRegionLevel());
				
			for (int type : template.getDefaultTypes()) {				
				defaultTemplate.setType(type);
				flow = flowDao.getMatchedFlow(defaultTemplate);
				if(flow != null){
					break;
				}
			}
		}

		if (flow == null) {
			return Collections.<FlowStep> emptyList();
		} else {
			return flowStepDao.getSteps(flow.getId());
		}
	}
	
	@Override
	public List<FlowStep> getSteps(int businessType) {
		Flow flow = flowDao.getFlow(businessType);
		if (flow == null) {
			return Collections.<FlowStep> emptyList();
		} else {
			return flowStepDao.getSteps(flow.getId());
		}
	}

	@Override
	public List<Flow> getFlows(Flow template) {
		return flowDao.getFlows(template);
	}
	
	@Override
	public List<FlowStep> getSuitedSteps(final int businessType) {
		return getEntitiesFromCache(new CacheEntitiesParam<FlowStep>() {
			public String fetchKey() {
				return BaseCacheConstants.EIS_STEP_BY_BUSINESS_TYPE
						+ businessType;
			}
			public List<FlowStep> fetchObjects() {
				Flow flow = flowDao.getFlow(businessType);
				List<FlowStep> tmpSteps = null;
				if (flow == null) {
					tmpSteps = Collections.<FlowStep> emptyList();
				} else {
					tmpSteps = flowStepDao.getSteps(flow.getId());
				}
				Collections.sort(tmpSteps, BUSINESS_TEMPLATE_STEP_ORDER);
				// 避免相同的角色进行2次重复的审核
				FlowStep preStep = new FlowStep();
				int repeatNum = 0;
				List<FlowStep> list = new ArrayList<FlowStep>();
				for (FlowStep tmpStep : tmpSteps) {
					if (tmpStep.getRoleCode().equals(preStep.getRoleCode())) {
						repeatNum++;
					} else {
						tmpStep.setStepValue(tmpStep.getStepValue() - repeatNum);
						list.add(tmpStep);
						preStep = tmpStep;
					}
				}
				List<FlowStep> resultList = new ArrayList<FlowStep>();
				Set<String> roleIdSet = new HashSet<String>();
				for (FlowStep step : list) {
					if (roleIdSet.contains(step.getRoleCode())) {
						for (int k = resultList.size() - 1; k >= 0; k--) {
							if (step.getRoleCode().equals(
									resultList.get(k).getRoleCode())) {
								resultList.remove(k);
							}
						}
					}
					roleIdSet.add(step.getRoleCode());
					resultList.add(step);
				}
				// fix final step's order
				preStep.setStepValue(FlowAudit.STEP_FINAL);
				return resultList;
			}
		});
	}

	/**
	 * 业务模板步骤排序比较器<br>
	 * 按照 0,1,2...,-1 的顺序排列
	 */
	private static final Comparator<FlowStep> BUSINESS_TEMPLATE_STEP_ORDER = new Comparator<FlowStep>() {
		// 对步骤排序,按照 0,1,2...,-1 的顺序排列
		public int compare(FlowStep step1, FlowStep step2) {
			int value1 = step1.getStepValue();
			int value2 = step2.getStepValue();
			if (FlowAudit.STEP_FINAL == value1) {
				value1 = Integer.MAX_VALUE;
			}
			if (FlowAudit.STEP_FINAL == value2) {
				value2 = Integer.MAX_VALUE;
			}
			return value1 - value2;
		}
	};
}
