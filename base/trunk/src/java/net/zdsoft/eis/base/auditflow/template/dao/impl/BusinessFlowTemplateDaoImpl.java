package net.zdsoft.eis.base.auditflow.template.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.auditflow.template.dao.BusinessFlowTemplateDao;
import net.zdsoft.eis.base.auditflow.template.entity.BusinessFlowTemplate;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * 业务流程模板DAO Hibernate实现类
 * 
 * @author sherlockyao
 */
public class BusinessFlowTemplateDaoImpl extends BaseDao<BusinessFlowTemplate> implements
		BusinessFlowTemplateDao {
	@Override
	public BusinessFlowTemplate setField(ResultSet rs) throws SQLException {
		BusinessFlowTemplate flowTemplate = new BusinessFlowTemplate();
		flowTemplate.setId(rs.getString("id"));
		flowTemplate.setBusinessType(rs.getInt("business_type"));
		flowTemplate.setSection(rs.getInt("section"));
		flowTemplate.setSourceRegionLevel(rs.getInt("source_region_level"));
		flowTemplate.setTargetRegionLevel(rs.getInt("target_region_level"));
		return flowTemplate;
	}

	public List<BusinessFlowTemplate> getMatchedTemplates(
			BusinessFlowTemplate template) {
		StringBuffer hql = new StringBuffer("SELECT * FROM base_flow_template");
		hql.append(" WHERE ");
		List<Object> values = new ArrayList<Object>();
		if (null != template.getBusinessType()) {
			hql.append(" business_type = ?");
			values.add(template.getBusinessType());
		}
		if (null != template.getSourceRegionLevel()) {
			hql.append(" and source_region_level = ? ");
			values.add(template.getSourceRegionLevel());
		}
		if (null != template.getSection()) {
			hql.append(" and section = ? ");
			values.add(template.getSection());
		}
		if (null == template.getTargetRegionLevel()) {
			hql.append(" and target_region_level IS NULL ");
		} else {
			hql.append(" and target_region_level = ? ");
			values.add(template.getTargetRegionLevel());
		}
		Object[] args = values.toArray();
		return query(hql.toString(),args,new MultiRow());
	}

}
