/* 
 * @(#)FlowAuditDaoImpl.java    Created on 2012-11-2
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.auditflow.manager.dao.FlowAuditDao;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-2 下午05:12:58 $
 */
public class FlowAuditDaoImpl extends BaseDao<FlowAudit> implements FlowAuditDao {
	private static final String SQL_INSERT_FLOWAUDIT = "INSERT INTO base_flow_audit(id,apply_id,apply_unit_id,"
			+ "business_type,opinion,status,audit_user_id,audit_user_name,audit_unit_id,audit_date,"
			+ "audit_order,audit_unit_level,arrange_id,role_id,creation_time) " + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_FLOWAUDIT_BY_APPLYIDS = "DELETE FROM base_flow_audit WHERE apply_id IN ";

	private static final String SQL_DELETE_FLOWAUDIT_BY_IDS = "DELETE FROM base_flow_audit WHERE id IN ";
	
	private static final String SQL_UPDATE_FLOWAUDIT_BY_ID = "UPDATE base_flow_audit "
			+ "SET opinion=?,audit_user_id=?,audit_user_name=?,audit_date=?,status=? WHERE id=? ";

	private static final String SQL_FIND_FLOWAUDIT_BY_ID = "SELECT * FROM base_flow_audit WHERE id=?";

	private static final String SQL_FIND_FLOWAUDIT_BY_IDS = "SELECT * FROM base_flow_audit WHERE id IN";

	private static final String SQL_FIND_FLOWAUDIT_BY_APPLYIDS = "SELECT * FROM base_flow_audit WHERE apply_id IN";
	
	private static final String SQL_FIND_FLOWAUDITS_BY_APPLYID = "SELECT * FROM base_flow_audit "
			+ "WHERE apply_id=? order by audit_date ";

	private static final String SQL_FIND_FLOWAUDITS_BY_APPLYID_STATE = "SELECT * FROM base_flow_audit "
			+ "WHERE status=? and apply_id IN";

	@Override
	public FlowAudit setField(ResultSet rs) throws SQLException {
		FlowAudit flowAudit = new FlowAudit();
		flowAudit.setId(rs.getString("id"));
		flowAudit.setArrangeId(rs.getString("arrange_id"));
		flowAudit.setRoleId(rs.getString("role_id"));
		flowAudit.setApplyId(rs.getString("apply_id"));
		flowAudit.setApplyUnitId(rs.getString("apply_unit_id"));
		flowAudit.setBusinessType(rs.getInt("business_type"));
		flowAudit.setOpinion(rs.getString("opinion"));
		flowAudit.setStatus(rs.getInt("status"));
		flowAudit.setAuditUserId(rs.getString("audit_user_id"));
		flowAudit.setAuditUsername(rs.getString("audit_user_name"));
		flowAudit.setAuditUnitId(rs.getString("audit_unit_id"));
		flowAudit.setAuditDate(rs.getTimestamp("audit_date"));
		flowAudit.setAuditOrder(rs.getInt("audit_order"));
		flowAudit.setAuditUnitLevel(rs.getInt("audit_unit_level"));
		flowAudit.setCreationTime(rs.getTimestamp("creation_time"));
		return flowAudit;
	}

	@Override
	public void addFlowAudits(List<FlowAudit> flowAudits) {
		List<Object[]> list = new ArrayList<Object[]>();
		for (FlowAudit flowAudit : flowAudits) {
			flowAudit.setId(createId());
			flowAudit.setCreationTime(new Date());

			Object[] objs = new Object[] { flowAudit.getId(), flowAudit.getApplyId(),
					flowAudit.getApplyUnitId(), flowAudit.getBusinessType(),
					flowAudit.getOpinion(), flowAudit.getStatus(), flowAudit.getAuditUserId(),
					flowAudit.getAuditUsername(), flowAudit.getAuditUnitId(),
					flowAudit.getAuditDate(), flowAudit.getAuditOrder(),
					flowAudit.getAuditUnitLevel(), flowAudit.getArrangeId(), flowAudit.getRoleId(),flowAudit.getCreationTime() };
			list.add(objs);
		}

		batchUpdate(SQL_INSERT_FLOWAUDIT, list, new int[] { Types.CHAR, Types.CHAR, Types.CHAR,
				Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.CHAR, Types.VARCHAR, Types.CHAR,
				Types.TIMESTAMP, Types.INTEGER, Types.INTEGER,Types.CHAR, Types.CHAR, Types.TIMESTAMP });
	}

	@Override
	public void deleteFlowAudits(String... applyIds) {
		updateForInSQL(SQL_DELETE_FLOWAUDIT_BY_APPLYIDS, null, applyIds);
	}
	
	@Override
	public void deleteFlowAuditsByIds(String... ids) {
		updateForInSQL(SQL_DELETE_FLOWAUDIT_BY_IDS, null, ids);
	}

	public void updateStatus(List<FlowAudit> audits) {
		List<Object[]> list = new ArrayList<Object[]>();
		for (FlowAudit flowAudit : audits) {
			Object[] objs = new Object[] { null, null, null, null, flowAudit.getStatus(),
					flowAudit.getId() };
			list.add(objs);
		}

		batchUpdate(SQL_UPDATE_FLOWAUDIT_BY_ID, list, new int[] { Types.VARCHAR, Types.CHAR,
				Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER, Types.CHAR });
	}

	public void updateFlowAudit(FlowAudit audit) {
		update(SQL_UPDATE_FLOWAUDIT_BY_ID,
				new Object[] { audit.getOpinion(), audit.getAuditUserId(),
						audit.getAuditUsername(), audit.getAuditDate(), audit.getStatus(),
						audit.getId() }, new int[] { Types.VARCHAR, Types.CHAR, Types.VARCHAR,
						Types.TIMESTAMP, Types.INTEGER, Types.CHAR });
	}

	@Override
	public FlowAudit getFlowAudit(String flowAuditId) {
		return query(SQL_FIND_FLOWAUDIT_BY_ID, flowAuditId, new SingleRow());
	}
	
	@Override
	public List<FlowAudit> getFlowAuditList(String[] ids) {
		return queryForInSQL(SQL_FIND_FLOWAUDIT_BY_IDS, null, ids,
				new MultiRow(), "order by audit_date");
	}

	@Override
	public List<FlowAudit> getFlowAuditListByAppIds(String[] applyIds) {
		return queryForInSQL(SQL_FIND_FLOWAUDIT_BY_APPLYIDS, null, applyIds,
				new MultiRow(), "order by audit_date");
	}

	@Override
	public List<FlowAudit> getFlowAudits(String[] applyIds, int state) {
		return queryForInSQL(SQL_FIND_FLOWAUDITS_BY_APPLYID_STATE,
				new Object[] { state }, applyIds, new MultiRow());
	}
	
	public List<FlowAudit> getFlowAudits(String applyId) {
		return query(SQL_FIND_FLOWAUDITS_BY_APPLYID, applyId, new MultiRow());
	}

	@Override
	public List<FlowAudit> getFlowAudits(String auditUnitId, int businessType, int status, int operateType,
			String applyUnitId, Pagination page) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.*");
		sqlBuffer.append(" FROM base_flow_audit a, base_flow_apply b");
		sqlBuffer.append(" where a.apply_id = b.id");
		sqlBuffer.append(" and a.audit_unit_id=? AND a.business_type=? AND a.status=?");
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(auditUnitId);
		argsList.add(Integer.valueOf(businessType));
		argsList.add(Integer.valueOf(status));
		if (operateType != 0){
			sqlBuffer.append(" and b.operate_type = ?"); 
			argsList.add(Integer.valueOf(operateType));
		}
		
		if (StringUtils.isNotBlank(applyUnitId)){
			sqlBuffer.append(" and b.apply_unit_id = ?");
			argsList.add(applyUnitId);
		}
		sqlBuffer.append(" order by a.audit_date");
		return query(sqlBuffer.toString(), argsList.toArray(), new MultiRow(), page);
	}
	
	/**
	 * 获取申请单位信息
	 * @param auditUnitId
	 * @param businessType
	 * @param status
	 * @param operateType
	 * @return
	 */
	public Map<String, Unit> getApplyUnits(String auditUnitId, int businessType, int status, int operateType){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select c.id, c.unit_name");
		sqlBuffer.append(" from base_flow_audit a, base_flow_apply b, base_unit c ");
		sqlBuffer.append(" where a.apply_id = b.id and b.apply_unit_id = c.id "); 
		sqlBuffer.append(" and a.audit_unit_id = ? and a.business_type = ? and a.status = ?");
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(auditUnitId);
		argsList.add(Integer.valueOf(businessType));
		argsList.add(Integer.valueOf(status));
		if (operateType != 0){
			sqlBuffer.append(" and b.operate_type = ?");
			argsList.add(Integer.valueOf(operateType));
		}
		return queryForMap(sqlBuffer.toString(), argsList.toArray(), new MapRowMapper<String, Unit>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("id");
			}

			@Override
			public Unit mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				Unit unit = new Unit();
				unit.setId(rs.getString("id"));
				unit.setName(rs.getString("unit_name"));
				return unit;
			}
		});
	}
	
	@Override
	public List<FlowAudit> getFlowAudits(String arrangeId, String roleId,
			int businessType, int status, int operateType, String applyUnitId,
			Pagination page) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT a.*");
		sqlBuffer.append(" FROM base_flow_audit a, base_flow_apply b");
		sqlBuffer.append(" where a.apply_id = b.id");
		sqlBuffer
				.append(" and a.arrange_id=? AND a.role_id=? AND a.business_type=? AND a.status=?");
		List<Object> argsList = new ArrayList<Object>();
		argsList.add(arrangeId);
		argsList.add(roleId);
		argsList.add(Integer.valueOf(businessType));
		argsList.add(Integer.valueOf(status));
		if (operateType != 0) {
			sqlBuffer.append(" and b.operate_type = ?");
			argsList.add(Integer.valueOf(operateType));
		}

		if (StringUtils.isNotBlank(applyUnitId)) {
			sqlBuffer.append(" and b.apply_unit_id = ?");
			argsList.add(applyUnitId);
		}
		sqlBuffer.append(" order by a.audit_date desc");
		return query(sqlBuffer.toString(), argsList.toArray(), new MultiRow(),
				page);
	}


}
