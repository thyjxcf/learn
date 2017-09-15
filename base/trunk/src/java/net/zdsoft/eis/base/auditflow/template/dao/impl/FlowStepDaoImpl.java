/* 
 * @(#)FlowStepDaoImpl.java    Created on 2012-12-11
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.template.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.auditflow.template.dao.FlowStepDao;
import net.zdsoft.eis.base.auditflow.template.entity.FlowStep;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-12-11 下午02:51:23 $
 */
public class FlowStepDaoImpl extends BaseDao<FlowStep> implements FlowStepDao {
	private static final String SQL_FIND_STEPS_BY_FLOWID = "SELECT * FROM base_flow_step WHERE flow_id=?";

	@Override
	public FlowStep setField(ResultSet rs) throws SQLException {
		FlowStep step = new FlowStep();
		step.setId(rs.getString("id"));
		step.setFlowId(rs.getString("flow_id"));
		step.setAuditUnitType(rs.getInt("audit_unit_type"));
		step.setRegionLevel(rs.getInt("region_level"));
		step.setStepValue(rs.getInt("step"));
		step.setIoType(rs.getInt("io_type"));

		step.setRoleCode(rs.getString("role_code"));
		step.setArrangeMethod(rs.getString("arrange_method"));
		step.setArrangeParam(rs.getString("arrange_param"));

		return step;
	}

	public List<FlowStep> getSteps(String flowId) {
		return query(SQL_FIND_STEPS_BY_FLOWID, flowId, new MultiRow());
	}
}
