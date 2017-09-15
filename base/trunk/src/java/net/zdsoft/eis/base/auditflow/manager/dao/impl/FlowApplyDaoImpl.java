/* 
 * @(#)FlowApplyDaoImpl.java    Created on 2012-11-2
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.auditflow.manager.dao.FlowApplyDao;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-2 下午04:40:33 $
 */
public class FlowApplyDaoImpl extends BaseDao<FlowApply> implements FlowApplyDao {

	private static final String SQL_INSERT_FLOWAPPLY = "INSERT INTO base_flow_apply(id,business_id,business_type,"
			+ "operate_type,reason,status,apply_user_id,"
			+ "apply_username,apply_arrange_id,apply_arrange_name,apply_unit_id,apply_date,audit_date,creation_time) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_FLOWAPPLY_BY_IDS = "DELETE FROM base_flow_apply WHERE id IN ";

	private static final String SQL_UPDATE_FLOWAPPLY = "UPDATE base_flow_apply SET reason=?,status=? WHERE id=?";

	private static final String SQL_UPDATE_STATUS_BY_IDS = "UPDATE base_flow_apply SET status=?,audit_date=? WHERE id IN ";

	private static final String SQL_FIND_FLOWAPPLY_BY_ID = "SELECT * FROM base_flow_apply WHERE id=?";

	private static final String SQL_FIND_FLOWAPPLY_BY_BUSINESSIDS = "SELECT * FROM base_flow_apply WHERE business_id IN";
	private static final String SQL_FIND_FLOWAPPLY_BY_BUSINESSID_STATUS = "SELECT * FROM base_flow_apply WHERE business_id=? AND status <>"
			+ FlowApply.STATUS_AUDIT_PASS;

	private static final String SQL_FIND_FLOWAPPLYS_BY_IDS = "SELECT * FROM base_flow_apply WHERE id IN";

	private static final String SQL_FIND_FLOWAPPLYS_BY_UNITID_BUSINESSTYPE_STATUS_UNIT_ID = "SELECT * FROM base_flow_apply "
			+ "WHERE apply_unit_id=? AND business_type=? AND status=? order by apply_date";

private static final String SQL_FIND_FLOWAPPLYS_BY_UNITID_BUSINESSTYPE_STATUS_ARRANGE_ID = "SELECT * FROM base_flow_apply "
			+ "WHERE apply_arrange_id=? AND business_type=? AND status=? order by apply_date desc";

	@Override
	public FlowApply setField(ResultSet rs) throws SQLException {
		FlowApply flowApply = new FlowApply();
		flowApply.setId(rs.getString("id"));
		flowApply.setBusinessId(rs.getString("business_id"));
		flowApply.setBusinessType(rs.getInt("business_type"));
		flowApply.setOperateType(rs.getInt("operate_type"));
		flowApply.setReason(rs.getString("reason"));
		flowApply.setStatus(rs.getInt("status"));
		flowApply.setApplyUserId(rs.getString("apply_user_id"));
		flowApply.setApplyUsername(rs.getString("apply_username"));
		flowApply.setApplyArrangeId(rs.getString("apply_arrange_id"));
		flowApply.setApplyArrangeName(rs.getString("apply_arrange_name"));
		flowApply.setApplyUnitId(rs.getString("apply_unit_id"));
		flowApply.setApplyDate(rs.getTimestamp("apply_date"));
		flowApply.setAuditDate(rs.getTimestamp("audit_date"));
		flowApply.setCreationTime(rs.getTimestamp("creation_time"));
		return flowApply;
	}

	@Override
	public void addFlowApply(FlowApply flowApply) {
		flowApply.setCreationTime(new Date());
		update(SQL_INSERT_FLOWAPPLY, new Object[] { flowApply.getId(), flowApply.getBusinessId(),
				flowApply.getBusinessType(), flowApply.getOperateType(), flowApply.getReason(),
				flowApply.getStatus(), flowApply.getApplyUserId(), flowApply.getApplyUsername(),
				flowApply.getApplyArrangeId(),flowApply.getApplyArrangeName(),
				flowApply.getApplyUnitId(), flowApply.getApplyDate(), flowApply.getAuditDate(),
				flowApply.getCreationTime() }, new int[] { Types.CHAR, Types.CHAR, Types.INTEGER,
				Types.INTEGER, Types.VARCHAR, Types.INTEGER,Types.CHAR,
						Types.VARCHAR,  Types.CHAR, Types.VARCHAR, Types.CHAR,
				Types.TIMESTAMP, Types.TIMESTAMP, Types.TIMESTAMP });
	}

	@Override
	public void deleteFlowApply(String... flowApplyIds) {
		updateForInSQL(SQL_DELETE_FLOWAPPLY_BY_IDS, null, flowApplyIds);
	}

	@Override
	public void updateFlowApply(FlowApply flowApply) {
		update(SQL_UPDATE_FLOWAPPLY, new Object[] { flowApply.getReason(), flowApply.getStatus(),
				flowApply.getId() }, new int[] { Types.VARCHAR, Types.INTEGER, Types.CHAR });
	}

	@Override
	public void updateStatus(int status, String... applyIds) {
		updateForInSQL(SQL_UPDATE_STATUS_BY_IDS,
				new Object[] { Integer.valueOf(status), new Date() }, applyIds);
	}

	@Override
	public FlowApply getFlowApply(String flowApplyId) {
		return query(SQL_FIND_FLOWAPPLY_BY_ID, flowApplyId, new SingleRow());
	}

	@Override
	public FlowApply getFlowApplyInFlow(String businessId) {
		return query(SQL_FIND_FLOWAPPLY_BY_BUSINESSID_STATUS, businessId, new SingleRow());
	}
	
	@Override
	public List<FlowApply> getFlowApplysByBusinessIds(String[] businessIds) {
		return queryForInSQL(SQL_FIND_FLOWAPPLY_BY_BUSINESSIDS,null, businessIds,
				new MultiRow());
	}

	@Override
	public List<FlowApply> getFlowApplys(String[] flowApplyIds) {
		return queryForInSQL(SQL_FIND_FLOWAPPLYS_BY_IDS, null, flowApplyIds, new MultiRow());
	}

	public String[] getBusinessIds(String[] flowApplyIds) {
		List<String> ids = queryForInSQL(SQL_FIND_FLOWAPPLYS_BY_IDS, null, flowApplyIds,
				new MultiRowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("business_id");
					}
				});
		return ids.toArray(new String[0]);
	}

	@Override
	public List<FlowApply> getFlowApplys(String applyUnitId, int businessType, int status,
			String businessId, Pagination page) {
		return query(SQL_FIND_FLOWAPPLYS_BY_UNITID_BUSINESSTYPE_STATUS_UNIT_ID, new Object[] { applyUnitId,
				Integer.valueOf(businessType), Integer.valueOf(status) }, new MultiRow(), page);
	}
	
	@Override
	public List<FlowApply> getFlowApplys(String applyArrangeId,
			int businessType, int status, Pagination page) {
		return query(SQL_FIND_FLOWAPPLYS_BY_UNITID_BUSINESSTYPE_STATUS_ARRANGE_ID,
				new Object[] { applyArrangeId, Integer.valueOf(businessType),
						Integer.valueOf(status) }, new MultiRow(), page);
	}
}