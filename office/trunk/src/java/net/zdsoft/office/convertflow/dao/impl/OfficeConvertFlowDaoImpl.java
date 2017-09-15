package net.zdsoft.office.convertflow.dao.impl;

import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
import net.zdsoft.office.convertflow.constant.ConvertFlowConstants;
import net.zdsoft.office.convertflow.dao.OfficeConvertFlowDao;
import net.zdsoft.office.convertflow.entity.OfficeConvertFlow;
import net.zdsoft.office.util.Constants;
/**
 * office_convert_flow
 * @author 
 * 
 */
public class OfficeConvertFlowDaoImpl extends BaseDao<OfficeConvertFlow> implements OfficeConvertFlowDao{
	private static final String INSERT_SQL = "insert into office_convert_flow(id, unit_id, business_id, type, apply_user_id, status, create_time, modify_time) values(?,?,?,?,?,?,?,?)";
	private static final String UPDATE_SQL = "update office_convert_flow set status = ? where business_id = ?";
	private static final String QUERY_SQL = "select * from office_convert_flow where business_id = ?";
	private static final String QUERY_AUDIT_LIST_SQL =  "select a.*, b.status as state, b.parm as parm from office_convert_flow a, office_convert_flow_task b where a.id = b.convert_flow_id ";
	private static final String QUERY_APPLY_LIST_SQL = "select * from office_convert_flow where status!=8";
	
	@Override
	public OfficeConvertFlow setField(ResultSet rs) throws SQLException{
		OfficeConvertFlow officeConvertFlow = new OfficeConvertFlow();
		officeConvertFlow.setId(rs.getString("id"));
		officeConvertFlow.setUnitId(rs.getString("unit_id"));
		officeConvertFlow.setBusinessId(rs.getString("business_id"));
		officeConvertFlow.setType(rs.getInt("type"));
		officeConvertFlow.setApplyUserId(rs.getString("apply_user_id"));
		officeConvertFlow.setStatus(rs.getInt("status"));
		officeConvertFlow.setCreateTime(rs.getTimestamp("create_time"));
		officeConvertFlow.setModifyTime(rs.getTimestamp("modify_time"));
		return officeConvertFlow;
	}

	@Override
	public OfficeConvertFlow save(OfficeConvertFlow ent){
			if (StringUtils.isBlank(ent.getId())){
				ent.setId(createId());
			}
			if(ent.getCreateTime() == null){
				ent.setCreateTime(new Date());
			}
			
			update(INSERT_SQL, new Object[]{
					ent.getId(), ent.getUnitId(), 
					ent.getBusinessId(), ent.getType(), 
					ent.getApplyUserId(), ent.getStatus(), 
					ent.getCreateTime(), ent.getModifyTime()
			});
			return ent;
	}

	@Override
	public void update(Integer status, String businessId){
		update(UPDATE_SQL, new Object[]{status, businessId});
	}
	
	public OfficeConvertFlow getObjByBusinessId(String businessId){
		return query(QUERY_SQL, businessId, new SingleRow());
	}
	
	public List<OfficeConvertFlow> getAuditList(String unitId, String[] applyUserIds, String userId, String otherAuditParm, String dataType, Integer type, Pagination page){
		List<Object> objs = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(QUERY_AUDIT_LIST_SQL);
		if(ConvertFlowConstants.OFFICE_ALL == type){
			if(StringUtils.isNotBlank(otherAuditParm) && StringUtils.isNotBlank(userId)){//物品权限
				String[] goodIds = otherAuditParm.split(",");
				sql.append("  and ((a.unit_id = ?  and b.audit_parm in "+SQLUtils.toSQLInString(goodIds)+") or b.audit_parm like ?) ");
				objs.add(unitId);
				objs.add("%"+userId+"%");
			}else{
				if(StringUtils.isNotBlank(userId)){
					//当前步骤有可能会有多个审核人都可以处理
					sql.append(" and b.audit_parm like ? ");
					objs.add("%"+userId+"%");
				}
			}
		}else if(ConvertFlowConstants.OFFICE_GOODS == type){
			if(StringUtils.isNotBlank(otherAuditParm)){
				String[] goodIds = otherAuditParm.split(",");
				sql.append(" and (a.unit_id = ?  and b.audit_parm in "+SQLUtils.toSQLInString(goodIds)+ ")");
				objs.add(unitId);
			}
		}else{
			if(StringUtils.isNotBlank(userId)){
				//当前步骤有可能会有多个审核人都可以处理
				sql.append(" and b.audit_parm like ? ");
				objs.add("%"+userId+"%");
			}
		}
		
		if(StringUtils.isNotBlank(dataType)){//审核步骤状态
			if(ConvertFlowConstants.OFFICE_AUDIT_DATATYPE_2 == Integer.valueOf(dataType)){
				sql.append(" and b.status in ("+Constants.APPLY_STATE_PASS+","+Constants.APPLY_STATE_NOPASS+")");
			}else{
				sql.append(" and b.status = " + Constants.APPLY_STATE_NEED_AUDIT);
			}
		}
		
		
		if(type != null && ConvertFlowConstants.OFFICE_ALL != type){
			sql.append(" and a.type = ? ");
			objs.add(type);
		}
		
		if(applyUserIds!=null && applyUserIds.length>0){//申请人姓名模糊搜索
			sql.append(" and a.apply_user_id IN ");
			if(page == null){
				return queryForInSQL(sql.toString(), objs.toArray(), applyUserIds, new MultiRowMapper<OfficeConvertFlow>(){
					@Override
					public OfficeConvertFlow mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						OfficeConvertFlow ent = setField(rs);
						ent.setState(rs.getInt("state"));
						ent.setParm(rs.getString("parm"));
						return ent;
					}
				}, "order by a.create_time desc");
			}
			return queryForInSQL(sql.toString(), objs.toArray(), applyUserIds, new MultiRowMapper<OfficeConvertFlow>(){
				@Override
				public OfficeConvertFlow mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeConvertFlow ent = setField(rs);
					ent.setState(rs.getInt("state"));
					ent.setParm(rs.getString("parm"));
					return ent;
				}
			}, " order by a.create_time desc", page);
		}else{
			sql.append(" order by a.create_time desc");
			
			if(page == null){
				return query(sql.toString(), objs.toArray(), new MultiRowMapper<OfficeConvertFlow>(){
					@Override
					public OfficeConvertFlow mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						OfficeConvertFlow ent = setField(rs);
						ent.setState(rs.getInt("state"));
						ent.setParm(rs.getString("parm"));
						return ent;
					}
				});
			}
			
			return query(sql.toString(), objs.toArray(), new MultiRowMapper<OfficeConvertFlow>(){
				@Override
				public OfficeConvertFlow mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeConvertFlow ent = setField(rs);
					ent.setState(rs.getInt("state"));
					ent.setParm(rs.getString("parm"));
					return ent;
				}
			}, page);
		}
	}
	
	public List<OfficeConvertFlow> getHaveDoAuditList(String unitId, String[] applyUserIds, String userId, String otherAuditParm, String dataType, Integer type, Pagination page){
		List<Object> objs = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from office_convert_flow a where  a.status!=8 and exists(select 1 from office_convert_flow_task b where a.id = b.convert_flow_id and b.status in ("+Constants.APPLY_STATE_PASS+","+Constants.APPLY_STATE_NOPASS+") ");
		if(ConvertFlowConstants.OFFICE_ALL == type){
			if(StringUtils.isNotBlank(otherAuditParm) && StringUtils.isNotBlank(userId)){//物品权限
				String[] goodIds = otherAuditParm.split(",");
				sql.append("  and ((a.unit_id = ?  and b.audit_parm in "+SQLUtils.toSQLInString(goodIds)+") or b.audit_parm like ?) ");
				objs.add(unitId);
				objs.add("%"+userId+"%");
			}else{
				if(StringUtils.isNotBlank(userId)){
					//当前步骤有可能会有多个审核人都可以处理
					sql.append(" and b.audit_parm like ? ");
					objs.add("%"+userId+"%");
				}
			}
		}else if(ConvertFlowConstants.OFFICE_GOODS == type){
			if(StringUtils.isNotBlank(otherAuditParm)){
				String[] goodIds = otherAuditParm.split(",");
				sql.append(" and (a.unit_id = ?  and b.audit_parm in "+SQLUtils.toSQLInString(goodIds)+ ")");
				objs.add(unitId);
			}
		}else{
			if(StringUtils.isNotBlank(userId)){
				//当前步骤有可能会有多个审核人都可以处理
				sql.append(" and b.audit_parm like ? ");
				objs.add("%"+userId+"%");
			}
		}
		sql.append(")");
		
		if(type != null && ConvertFlowConstants.OFFICE_ALL != type){
			sql.append(" and a.type = ? ");
			objs.add(type);
		}
		
		
		if(applyUserIds != null && applyUserIds.length>0){
			sql.append(" and a.apply_user_id IN ");
			if(page == null){
				return queryForInSQL(sql.toString(), objs.toArray(), applyUserIds, new MultiRow(), " order by a.create_time desc");
			}
			
			return queryForInSQL(sql.toString(), objs.toArray(), applyUserIds, new MultiRow(), " order by a.create_time desc", page);
		}else{
			sql.append(" order by a.create_time desc");
			
			if(page == null){
				return query(sql.toString(), objs.toArray(), new MultiRow());
			}
			
			return query(sql.toString(), objs.toArray(), new MultiRow(), page);
		}
	}
	
	public List<OfficeConvertFlow> getApplyList(String applyUserId, Integer type, Pagination page){
		List<Object> objs = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(QUERY_APPLY_LIST_SQL);
		
		if(StringUtils.isNotBlank(applyUserId)){
			sql.append(" and apply_user_id = ? ");
			objs.add(applyUserId);
		}
		
		if(type != null && ConvertFlowConstants.OFFICE_ALL != type){
			sql.append(" and type = ? ");
			objs.add(type);
		}
		
		sql.append(" order by create_time desc");
		
		if(page == null){
			
			return query(sql.toString(), objs.toArray(), new MultiRowMapper<OfficeConvertFlow>(){
				@Override
				public OfficeConvertFlow mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeConvertFlow ent = setField(rs);
					return ent;
				}
			});
		}
		
		return query(sql.toString(), objs.toArray(), new MultiRowMapper<OfficeConvertFlow>(){
			@Override
			public OfficeConvertFlow mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OfficeConvertFlow ent = setField(rs);
				return ent;
			}
		}, page);
	}

	@Override
	public int deleteByBusinessId(String businessId) {
		String sql = "delete from office_convert_flow where business_id in";
		return updateForInSQL(sql, null, new String[]{businessId});
	}
	
	@Override
	public OfficeConvertFlow getOfficeConvertFlowById(String id){
		String sql = "select * from office_convert_flow where id = ?";
		return query(sql, id, new SingleRow());
	}
}