/* 
 * @(#)FlowDaoImpl.java    Created on 2012-12-11
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.template.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.auditflow.template.dao.FlowDao;
import net.zdsoft.eis.base.auditflow.template.entity.Flow;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-12-11 下午02:25:38 $
 */
public class FlowDaoImpl extends BaseDao<Flow> implements FlowDao {
	private static final String SQL_FIND_FLOW_BY_BUSINESSKEY = "SELECT * FROM base_flow_template WHERE business_type=? AND source_region_level=?";
	
	private static final String SQL_FIND_FLOW_BY_BUSINESS_TYPE = "SELECT * FROM base_flow_template WHERE business_type=? ";

	@Override
	public Flow setField(ResultSet rs) throws SQLException {
		Flow flow = new Flow();
		flow.setId(rs.getString("id"));
		flow.setType(rs.getInt("business_type"));
		flow.setSection(rs.getInt("section"));
		flow.setSourceRegionLevel(rs.getInt("source_region_level"));
		flow.setTargetRegionLevel(rs.getInt("target_region_level"));
		flow.setStepType(rs.getInt("step_type"));
		return flow;
	}
	
	@Override
	public Flow getFlow(int businessType) {
		return query(SQL_FIND_FLOW_BY_BUSINESS_TYPE,
				new Object[] { businessType}, new SingleRow());
	}

	public Flow getMatchedFlow(Flow template) {
		StringBuilder sql = new StringBuilder(SQL_FIND_FLOW_BY_BUSINESSKEY);
		if (Flow.NOT_EXISTS == template.getSection()) {
			sql.append(" and section IS NULL ");
		} else {
			sql.append(" and section = ").append(template.getSection());
		}
		if (Flow.NOT_EXISTS == template.getTargetRegionLevel()) {
			sql.append(" and target_region_level IS NULL ");
		} else {
			sql.append(" and target_region_level = ").append(template.getTargetRegionLevel());
		}
		return query(
				sql.toString(),
				new Object[] { Integer.valueOf(template.getType()),
						Integer.valueOf(template.getSourceRegionLevel()) }, new SingleRow());
	}

	@Override
	public List<Flow> getFlows(Flow template) {
		return query(SQL_FIND_FLOW_BY_BUSINESS_TYPE, new Object[]{template.getType()}, new MultiRow());
	}
}
