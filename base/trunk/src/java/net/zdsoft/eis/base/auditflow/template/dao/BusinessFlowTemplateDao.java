package net.zdsoft.eis.base.auditflow.template.dao;

import java.util.List;

import net.zdsoft.eis.base.auditflow.template.entity.BusinessFlowTemplate;

/**
 * 业务流程模板DAO接口
 * 
 * @author sherlockyao
 */
public interface BusinessFlowTemplateDao {

	/**
	 * 根据提供的模板信息,找到和它匹配的真实模板对象.<br>
	 * 对于目标单位级别内容,如果提供的模板内该内容为<tt>null</tt>,<br>
	 * 则在匹配时也会排除那些不为<tt>null</tt>的模板.<br>
	 * 
	 * @param template
	 *            存放属性值的模板对象
	 * @return <code>BusinessFlowTemplate</code>对象<tt>List</tt>
	 */
	public List<BusinessFlowTemplate> getMatchedTemplates(
			BusinessFlowTemplate template);

}
