package net.zdsoft.office.asset.dao.impl;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.ecs.xhtml.object;

import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
import net.zdsoft.office.asset.dao.OfficeAssetApplyDao;
import net.zdsoft.office.asset.entity.OfficeAssetApply;
/**
 * office_asset_apply
 * @author 
 * 
 */
public class OfficeAssetApplyDaoImpl extends BaseDao<OfficeAssetApply> implements OfficeAssetApplyDao{
	private static final String SQL_INSERT = "insert into office_asset_apply(id, unit_id, category_id, asset_name, asset_number, unit_price, is_pass_apply, purchase_price, " +
			"purchase_userid1, purchase_userid2, purchase_audit_userid, creation_time, apply_code, asset_format,purchase_total_price,purchase_opinion,purchase_state,asset_unit,purchase_date,is_sync_to_goods) " +
			"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_UPDATE = "update office_asset_apply set unit_id = ?, category_id = ?, asset_name = ?, asset_number = ?, unit_price = ?, is_pass_apply = ?, purchase_price = ?, purchase_userid1 = ?, " +
			"purchase_userid2 = ?, purchase_audit_userid = ?, creation_time = ?, apply_code=?, asset_format=?,purchase_total_price=?,purchase_opinion=?, purchase_state=?, asset_unit=?, purchase_date=?, is_sync_to_goods=? " +
			"where id = ?";
	
	private static final String SQL_FIND_BY_ID = "select * from office_asset_apply where id = ?";
	
	
	@Override
	public OfficeAssetApply setField(ResultSet rs) throws SQLException{
		OfficeAssetApply officeAssetApply = new OfficeAssetApply();
		officeAssetApply.setId(rs.getString("id"));
		officeAssetApply.setUnitId(rs.getString("unit_id"));
		officeAssetApply.setCategoryId(rs.getString("category_id"));
		officeAssetApply.setAssetName(rs.getString("asset_name"));
		officeAssetApply.setAssetNumber(rs.getInt("asset_number"));
		officeAssetApply.setUnitPrice(rs.getObject("unit_price")==null?null:rs.getDouble("unit_price"));
		officeAssetApply.setIsPassApply(rs.getBoolean("is_pass_apply"));
		officeAssetApply.setPurchasePrice(rs.getObject("purchase_price")==null?null:rs.getDouble("purchase_price"));
		officeAssetApply.setPurchaseUserid1(rs.getString("purchase_userid1"));
		officeAssetApply.setPurchaseUserid2(rs.getString("purchase_userid2"));
		officeAssetApply.setPurchaseAuditUserid(rs.getString("purchase_audit_userid"));
		officeAssetApply.setCreationTime(rs.getTimestamp("creation_time"));
		officeAssetApply.setApplyCode(rs.getString("apply_code"));
		officeAssetApply.setAssetFormat(rs.getString("asset_format"));
		officeAssetApply.setPurchaseTotalPrice(rs.getObject("purchase_total_price")==null?null:rs.getDouble("purchase_total_price"));
		officeAssetApply.setPurchaseOpinion(rs.getString("purchase_opinion"));
		officeAssetApply.setPurchaseState(String.valueOf(rs.getInt("purchase_state")));
		officeAssetApply.setAssetUnit(rs.getString("asset_unit"));
		officeAssetApply.setPurchaseDate(rs.getTimestamp("purchase_date"));
		if(officeAssetApply.getUnitPrice()!=null && officeAssetApply.getAssetNumber()!=null){
			BigDecimal ss = new BigDecimal(officeAssetApply.getAssetNumber()*officeAssetApply.getUnitPrice());
			ss.setScale(2, RoundingMode.HALF_UP);
			officeAssetApply.setTotalUnitPrice(ss.doubleValue());
		}
		officeAssetApply.setIsSyncToGoods(rs.getBoolean("is_sync_to_goods"));
		return officeAssetApply;
	}

	@Override
	public OfficeAssetApply save(OfficeAssetApply officeAssetApply){
		if (StringUtils.isBlank(officeAssetApply.getId())){
			officeAssetApply.setId(createId());
		}
		officeAssetApply.setCreationTime(new Date());
		officeAssetApply.setIsSyncToGoods(false);
		Object[] args = new Object[]{
			officeAssetApply.getId(), officeAssetApply.getUnitId(), 
			officeAssetApply.getCategoryId(), 
			officeAssetApply.getAssetName(), officeAssetApply.getAssetNumber(), 
			officeAssetApply.getUnitPrice(), officeAssetApply.getIsPassApply(), 
			officeAssetApply.getPurchasePrice(), officeAssetApply.getPurchaseUserid1(), 
			officeAssetApply.getPurchaseUserid2(), officeAssetApply.getPurchaseAuditUserid(), 
			officeAssetApply.getCreationTime(), officeAssetApply.getApplyCode(), 
			officeAssetApply.getAssetFormat(),officeAssetApply.getPurchaseTotalPrice(),
			officeAssetApply.getPurchaseOpinion(), officeAssetApply.getPurchaseState()==null?null:Integer.valueOf(officeAssetApply.getPurchaseState()),
			officeAssetApply.getAssetUnit(), officeAssetApply.getPurchaseDate(),
			officeAssetApply.getIsSyncToGoods()
		};
		update(SQL_INSERT, args);
		return officeAssetApply;
	}

	@Override
	public Integer update(OfficeAssetApply officeAssetApply){
		
		Object[] args = new Object[]{
			officeAssetApply.getUnitId(), 
			officeAssetApply.getCategoryId(), officeAssetApply.getAssetName(), 
			officeAssetApply.getAssetNumber(), officeAssetApply.getUnitPrice(), 
			officeAssetApply.getIsPassApply(), officeAssetApply.getPurchasePrice(), 
			officeAssetApply.getPurchaseUserid1(), officeAssetApply.getPurchaseUserid2(), 
			officeAssetApply.getPurchaseAuditUserid(), officeAssetApply.getCreationTime(), 
			officeAssetApply.getApplyCode(), officeAssetApply.getAssetFormat(), 
			officeAssetApply.getPurchaseTotalPrice(),officeAssetApply.getPurchaseOpinion(),
			officeAssetApply.getPurchaseState()==null?null:Integer.valueOf(officeAssetApply.getPurchaseState()),
			officeAssetApply.getAssetUnit(), officeAssetApply.getPurchaseDate(),
			officeAssetApply.getIsSyncToGoods(), officeAssetApply.getId()
		};
		return update(SQL_UPDATE, args);
	}

	@Override
	public OfficeAssetApply getOfficeAssetApplyById(String id){
		return query(SQL_FIND_BY_ID, new Object[]{id }, new SingleRow());
	}
	
	public List<OfficeAssetApply> getAssetApplyList(OfficeAssetApply ent, Pagination page){
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select a.*, sfap.id as apply_id,sfap.apply_user_id as apply_user_id, sfap.apply_username as apply_username,sfap.audit_date as audit_date,sfap.apply_date as apply_date,sfap.business_id as business_id, sfap.status as status from office_asset_apply a ");
		sql.append("inner join base_flow_apply sfap on a.id = sfap.business_id where a.unit_id=? ");
		args.add(ent.getUnitId());
		
		if(StringUtils.isNotBlank(ent.getApplyStatus())){
			sql.append(" and sfap.status=?");
			args.add(Integer.valueOf(ent.getApplyStatus()));
		}
		if(StringUtils.isNotBlank(ent.getApplyUserId())){
			sql.append(" and sfap.apply_user_id=?");
			args.add(ent.getApplyUserId());
		}
		
		sql.append(" order by sfap.audit_date desc");
		if(page != null){
				return query(sql.toString(),args.toArray(),new MultiRowMapper<OfficeAssetApply>(){
					public OfficeAssetApply mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						OfficeAssetApply ent = setField(rs);
						ent.setApplyId(rs.getString("apply_id"));
						ent.setApplyDate(rs.getDate("apply_date"));
						ent.setApplyUserId(rs.getString("apply_user_id"));
						ent.setApplyStatus(rs.getString("status"));
						return ent;
					}
				},page);
		}else{
			return query(sql.toString(),args.toArray(),new MultiRowMapper<OfficeAssetApply>(){
				public OfficeAssetApply mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeAssetApply ent = setField(rs);
					ent.setApplyId(rs.getString("apply_id"));
					ent.setApplyDate(rs.getDate("apply_date"));
					ent.setApplyUserId(rs.getString("apply_user_id"));
					ent.setApplyStatus(rs.getString("status"));
					return ent;
				}
			});
		}
	}
	
	@Override
	public List<OfficeAssetApply> getAssetApplyQueryList(String unitId,
			String state, String name, String deptId, Pagination page) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select a.*, sfap.id as apply_id,sfap.apply_user_id as apply_user_id, sfap.apply_username as apply_username,sfap.audit_date as audit_date,sfap.apply_date as apply_date,sfap.business_id as business_id, sfap.status as status from office_asset_apply a ");
		sql.append("inner join base_flow_apply sfap on a.id = sfap.business_id where a.unit_id=? ");
		args.add(unitId);
		
		if(StringUtils.isNotBlank(state)){
			sql.append(" and sfap.status=?");
			args.add(Integer.valueOf(state));
		}
		if(StringUtils.isNotBlank(name)){
			sql.append(" and sfap.apply_user_id in(select id from base_user where base_user.unit_id=? and real_name like '%"+name+"%')");
			args.add(unitId);
		}
		if(StringUtils.isNotBlank(deptId)){
			sql.append(" and sfap.apply_user_id in(select base_user.id from base_user,base_teacher where base_user.owner_id=base_teacher.id and base_teacher.dept_id=?)");
			args.add(deptId);
		}
		
		sql.append(" order by sfap.audit_date desc");
		if(page != null){
				return query(sql.toString(),args.toArray(),new MultiRowMapper<OfficeAssetApply>(){
					public OfficeAssetApply mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						OfficeAssetApply ent = setField(rs);
						ent.setApplyId(rs.getString("apply_id"));
						ent.setApplyDate(rs.getDate("apply_date"));
						ent.setApplyUserId(rs.getString("apply_user_id"));
						ent.setApplyStatus(rs.getString("status"));
						return ent;
					}
				},page);
		}else{
			return query(sql.toString(),args.toArray(),new MultiRowMapper<OfficeAssetApply>(){
				public OfficeAssetApply mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeAssetApply ent = setField(rs);
					ent.setApplyId(rs.getString("apply_id"));
					ent.setApplyDate(rs.getDate("apply_date"));
					ent.setApplyUserId(rs.getString("apply_user_id"));
					ent.setApplyStatus(rs.getString("status"));
					return ent;
				}
			});
		}
	}

	public List<OfficeAssetApply> getAssetData(OfficeAssetApply ent, Date queryBeginDate, Date queryEndDate, Pagination page){
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select a.*, sfap.id as apply_id, sfap.apply_user_id as apply_user_id, sfap.status as status from office_asset_apply a ");
		sql.append("inner join base_flow_apply sfap on a.id = sfap.business_id where a.unit_id=? ");
		args.add(ent.getUnitId());
		
		if(StringUtils.isNotBlank(ent.getPurchaseState())){
			sql.append(" and a.purchase_state=?");
			args.add(Integer.valueOf(ent.getPurchaseState()));
		}
		if(StringUtils.isNotBlank(ent.getCategoryId())){
			sql.append(" and a.category_id=?");
			args.add(ent.getCategoryId());
		}
		if(queryBeginDate != null){
			sql.append(" and to_char(a.purchase_date,'yyyy-mm-dd') >= ? ");
			args.add(DateUtils.date2String(queryBeginDate, "yyyy-MM-dd"));
		}
		
		if(queryEndDate != null){
			sql.append(" and to_char(a.purchase_date,'yyyy-mm-dd') <= ? ");
			args.add(DateUtils.date2String(queryEndDate, "yyyy-MM-dd"));
		}
		
		sql.append(" order by a.purchase_date desc");
		if(page != null){
				return query(sql.toString(),args.toArray(),new MultiRowMapper<OfficeAssetApply>(){
					public OfficeAssetApply mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						OfficeAssetApply ent = setField(rs);
						ent.setApplyUserId(rs.getString("apply_user_id"));
						return ent;
					}
				},page);
		}else{
			return query(sql.toString(),args.toArray(),new MultiRowMapper<OfficeAssetApply>(){
				public OfficeAssetApply mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeAssetApply ent = setField(rs);
					ent.setApplyUserId(rs.getString("apply_user_id"));
					return ent;
				}
			});
		}
	}
	
	public List<OfficeAssetApply> getAssetApplyAuditList(OfficeAssetApply ent, String roleId, int operateType, String[] arrangeIds, int businessType, Pagination page){
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select a.*, b.* from office_asset_apply a ");
		sql.append("inner join (select sfap.id as apply_id,sfap.apply_user_id as apply_user_id, sfap.apply_username as apply_username,sfap.apply_date as apply_date,sfad.id as audit_id, sfad.status as auditStatus, sfad.opinion as opinion,sfap.business_id as business_id, sfad.arrange_id as arrange_id from base_flow_apply sfap inner join base_flow_audit sfad on sfap.id=sfad.apply_id ");
		if(StringUtils.isNotBlank(roleId)){
			sql.append(" and sfad.role_id=?");
			args.add(roleId);
		}
		if(StringUtils.isNotBlank(ent.getAuditStatus())){
			if(ent.getAuditStatus().equals(String.valueOf(FlowAudit.STATUS_AUDIT_PASS))){
				sql.append(" and sfad.status in "+SQLUtils.toSQLInString(new String[]{String.valueOf(FlowAudit.STATUS_AUDIT_PASS), String.valueOf(FlowAudit.STATUS_AUDIT_FINISH)}));
			}else{
				sql.append(" and sfad.status=?");
				args.add(ent.getAuditStatus());
			}
		}else{
			sql.append(" and sfad.status<>0");
		}
		sql.append(" and sfad.business_type=? and sfap.operate_type=?");
		args.add(businessType);
		args.add(operateType);
		sql.append(" and sfad.apply_unit_id=?) b on a.id = b.business_id");
		args.add(ent.getUnitId());
		
		sql.append(" and b.arrange_id IN");
		return queryForInSQL(sql.toString(),args.toArray(), arrangeIds, new MultiRowMapper<OfficeAssetApply>(){
			public OfficeAssetApply mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OfficeAssetApply ent = setField(rs);
				ent.setApplyId(rs.getString("apply_id"));
				ent.setAuditId(rs.getString("audit_id"));
				ent.setAuditStatus(String.valueOf(rs.getInt("auditStatus")));
				ent.setApplyUserId(rs.getString("apply_user_id"));
				ent.setApplyDate(rs.getDate("apply_date"));
				ent.setOpinion(rs.getString("opinion"));
				return ent;
			}
		}," order by b.apply_date desc", page);
	}
	
	public List<OfficeAssetApply> getOfficeAssetApplyPurchaseList(String unitId, String applyUserId, String state, boolean isAudit, Pagination page){
		StringBuffer sql = new StringBuffer("select * from office_asset_apply where unit_id=? and is_pass_apply=1 ");
		List<Object> objs = new ArrayList<Object>();
		objs.add(unitId);
		if(StringUtils.isNotBlank(applyUserId)){
			sql.append(" and purchase_userid2 = ?");
			objs.add(applyUserId);
		}
		if(StringUtils.isNotBlank(state)){
			sql.append(" and purchase_state = ?");
			objs.add(Integer.valueOf(state));
		}else{
			if(isAudit){
				sql.append(" and purchase_state <> "+FlowAudit.STATUS_PREPARING);
			}
		}
		sql.append(" order by creation_time desc");
		return query(sql.toString(), objs.toArray(), new MultiRow(), page);
	}
}
