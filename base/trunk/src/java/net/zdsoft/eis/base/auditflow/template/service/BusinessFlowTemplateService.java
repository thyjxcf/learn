package net.zdsoft.eis.base.auditflow.template.service;

import java.util.List;

import net.zdsoft.eis.base.auditflow.template.entity.BusinessFlowTemplate;
import net.zdsoft.eis.base.auditflow.template.entity.FlowStep;

/**
 * 业务流程模板相关方法接口
 * 
 * @author sherlockyao
 */
public interface BusinessFlowTemplateService {

	/**
	 * 返回业务模板信息所对应的业务流程步骤列表.<br>
	 * 所提供的业务模板信息必须包括以下信息:
	 * <ul>
	 * <li>业务类型</li>
	 * <li>学段</li>
	 * <li>源单位级别</li>
	 * <li>目标单位级别(如果需要的话)</li>
	 * </ul>
	 * 
	 * @param template
	 *            业务模板对象<code>BusinessFlowTemplate</code>
	 * @return 所对应的业务流程步骤列表
	 */
	public List<FlowStep> getFlowSteps(BusinessFlowTemplate template);

}
