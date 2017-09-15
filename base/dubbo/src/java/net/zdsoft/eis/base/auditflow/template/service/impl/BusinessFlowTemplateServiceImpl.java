package net.zdsoft.eis.base.auditflow.template.service.impl;

import java.util.Collections;
import java.util.List;

import net.zdsoft.eis.base.auditflow.template.dao.BusinessFlowTemplateDao;
import net.zdsoft.eis.base.auditflow.template.dao.FlowStepDao;
import net.zdsoft.eis.base.auditflow.template.entity.BusinessFlowTemplate;
import net.zdsoft.eis.base.auditflow.template.entity.FlowStep;
import net.zdsoft.eis.base.auditflow.template.service.BusinessFlowTemplateService;

public class BusinessFlowTemplateServiceImpl implements
		BusinessFlowTemplateService {

	// DAOs
	private BusinessFlowTemplateDao businessFlowTemplateDao;

	private FlowStepDao flowStepDao;

	// Sets
	public void setBusinessFlowTemplateDao(
			BusinessFlowTemplateDao businessFlowTemplateDao) {
		this.businessFlowTemplateDao = businessFlowTemplateDao;
	}

	public void setFlowStepDao(FlowStepDao flowStepDao) {
		this.flowStepDao = flowStepDao;
	}

	public List<FlowStep> getFlowSteps(BusinessFlowTemplate template) {
		if (null == template.getBusinessType() || null == template.getSection()
				|| null == template.getSourceRegionLevel()) {
			throw new IllegalArgumentException(
					"not enough info in BusinessFlowTemplate");
		}
		List<BusinessFlowTemplate> templates = businessFlowTemplateDao
				.getMatchedTemplates(template);
		if (templates.isEmpty()) {
			return Collections.<FlowStep> emptyList();
		}
		return flowStepDao.getSteps(templates.get(0).getId());
	}

}
