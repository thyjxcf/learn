package net.zdsoft.office.customer.dao.impl;

import java.sql.*;
import java.util.*;
import java.util.Date;

import org.activiti.engine.impl.juel.Builder;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ecs.xhtml.s;

import net.zdsoft.office.asset.entity.OfficeAssetApply;
import net.zdsoft.office.customer.entity.OfficeCustomerApply;
import net.zdsoft.office.customer.entity.SearchCustomer;
import net.zdsoft.office.customer.constant.OfficeCustomerConstants;
import net.zdsoft.office.customer.dao.OfficeCustomerApplyDao;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;

/**
 * office_customer_apply 
 * @author 
 * 
 */
public class OfficeCustomerApplyDaoImpl extends BaseDao<OfficeCustomerApply> implements OfficeCustomerApplyDao{
	@Override
	public OfficeCustomerApply setField(ResultSet rs) throws SQLException{
		OfficeCustomerApply officeCustomerApply = new OfficeCustomerApply();
		officeCustomerApply.setId(rs.getString("id"));
		officeCustomerApply.setUnitId(rs.getString("unit_id"));
		officeCustomerApply.setCustomerId(rs.getString("customer_id"));
		officeCustomerApply.setFollowerId(rs.getString("follower_id"));
		officeCustomerApply.setApplyType(rs.getString("apply_type"));
		officeCustomerApply.setDeadline(rs.getTimestamp("deadline"));
		officeCustomerApply.setDelayInfo(rs.getString("delay_info"));
		officeCustomerApply.setCreateTime(rs.getTimestamp("create_time"));
		officeCustomerApply.setState(rs.getInt("state"));
		officeCustomerApply.setIsdeleted(rs.getBoolean("is_deleted"));
		officeCustomerApply.setOpenLecture(rs.getString("open_lecture"));
		officeCustomerApply.setFirstAuditTime(rs.getTimestamp("first_audit_time"));
		officeCustomerApply.setFinallyAuditTime(rs.getTimestamp("finally_audit_time"));
		return officeCustomerApply;
	}
	
	public OfficeCustomerApply save(OfficeCustomerApply officeCustomerApply){
		String sql = "insert into office_customer_apply(id, unit_id, state,customer_id, follower_id, apply_type, deadline, delay_info, create_time,is_deleted,open_lecture,first_audit_time,finally_audit_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
		if (StringUtils.isBlank(officeCustomerApply.getId())){
			officeCustomerApply.setId(createId());
		}
		Object[] args = new Object[]{
			officeCustomerApply.getId(), officeCustomerApply.getUnitId(),officeCustomerApply.getState(),
			officeCustomerApply.getCustomerId(), officeCustomerApply.getFollowerId(), 
			officeCustomerApply.getApplyType(), officeCustomerApply.getDeadline(), 
			officeCustomerApply.getDelayInfo(), officeCustomerApply.getCreateTime(),
			officeCustomerApply.getIsdeleted(),officeCustomerApply.getOpenLecture(),
			officeCustomerApply.getFirstAuditTime(),
			officeCustomerApply.getFinallyAuditTime()
		};
		update(sql, args);
		return officeCustomerApply;
	}
	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_customer_apply where id in";
		return updateForInSQL(sql, null, ids);
	}
	@Override
	public Integer deletePutOffCustomer(String unitId,Date nowDate){//) or ( apply_type='2'and deadline <? and state=9) 
		String sql="update office_customer_info set state=2 where unit_id=? and progress_state!='06' and progress_state!='07' and progress_state!='08' and id in(select customer_id from office_customer_apply where  deadline <? and state=9 and is_deleted =0)";
		String sql1="update office_customer_apply set is_deleted = ? where unit_id=? and deadline <? and (state=9 or state=7 or state=8) and customer_id in(select id from office_customer_info where progress_state!='06' and progress_state!='07' and progress_state!='08')";
		update(sql,new Object[]{unitId,nowDate});
		return update(sql1,new Object[]{true,unitId,nowDate});
	}
	@Override
	public Integer update(OfficeCustomerApply officeCustomerApply){
		StringBuilder sql = new StringBuilder("update office_customer_apply set state=? ");
		List<Object> args=new ArrayList<Object>();
		args.add(officeCustomerApply.getState());
		if(officeCustomerApply.getDeadline() != null){
			sql.append(" , deadline=? ");
			args.add(officeCustomerApply.getDeadline());
			if(officeCustomerApply.getIsdeleted()){
				sql.append(" , is_deleted=? ");
				args.add(officeCustomerApply.getIsdeleted());
			}
			if(StringUtils.isNotEmpty(officeCustomerApply.getOpenLecture())){
				sql.append(" ,open_lecture=? ");
				args.add(officeCustomerApply.getOpenLecture());
			}
		}
		if(officeCustomerApply.getFirstAuditTime()!=null){
			sql.append(" , first_audit_time=? ");
			args.add(officeCustomerApply.getFirstAuditTime());
		}
		if(officeCustomerApply.getFinallyAuditTime()!=null){
			sql.append(" , finally_audit_time=? ");
			args.add(officeCustomerApply.getFinallyAuditTime());
		}
		sql.append(" where id =? ");
		args.add(officeCustomerApply.getId());
		return update(sql.toString(), args.toArray());
	}
	@Override
	public Integer updateFollowerId(OfficeCustomerApply officeCustomerApply,Date time,boolean flag){
		StringBuilder sql =new StringBuilder("update office_customer_apply set id=? ");
		List<Object> args=new ArrayList<Object>();
		args.add(officeCustomerApply.getId());
		if(StringUtils.isNotBlank(officeCustomerApply.getFollowerId())){
			sql.append(" , follower_id =? ");
			args.add(officeCustomerApply.getFollowerId());
		}
		if(StringUtils.isNotBlank(officeCustomerApply.getOpenLecture())){
			sql.append(" , open_lecture =? ");
			args.add(officeCustomerApply.getOpenLecture());
		}
		if(time!=null){
			sql.append(", deadline=? ");
			args.add(time);
		}
		if(flag){
			sql.append(" ,is_deleted =? ");
			args.add(flag);
		}
		sql.append(" where id =? ");
		args.add(officeCustomerApply.getId());
		return update(sql.toString(), args.toArray());
	}
	@Override
	public Integer updateIsDelete(boolean flag,String id){
		String sql = "update office_customer_apply set is_deleted=? where id = ?";
		Object[] args = new Object[]{
				flag, id
		};
		return update(sql, args);
	}

	@Override//select a.*,c.opinion as from office_customer_apply a ,base_flow_apply b, base_flow_audit c where a.id=b.business_id and b.id=c.apply_id and a.id = ?
	public OfficeCustomerApply getOfficeCustomerApplyById(String id){
		String sql = "select * from office_customer_apply where id = ? and is_deleted=0 ";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	@Override
	public OfficeCustomerApply getOfficeCustomerApplyAndApdateById(String id){
		String sql = "select a.*,b.apply_date as apply_date from office_customer_apply a inner join base_flow_apply b on a.id=b.business_id where a.id = ? and is_deleted=0 ";
		return query(sql, new Object[]{id }, new SingleRowMapper<OfficeCustomerApply>(){
			@Override
			public OfficeCustomerApply mapRow(ResultSet rs) throws SQLException {
				OfficeCustomerApply apply=setField(rs);
				apply.setApplyDate(rs.getTimestamp("apply_date"));
				return apply;
			}
		});
	}

	@Override
	public Map<String, OfficeCustomerApply> getOfficeCustomerApplyMapByIds(String[] ids){
		String sql = "select * from office_customer_apply where is_deleted=0 and id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeCustomerApply> getOfficeCustomerApplyList(){
		String sql = "select * from office_customer_apply where is_deleted=0";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeCustomerApply> getOfficeCustomerApplyPage(Pagination page){
		String sql = "select * from office_customer_apply where is_deleted=0";
		return query(sql, new MultiRow(), page);
	}
	@Override
	public List<OfficeCustomerApply> getMyCustomerByUnitIdPage(String unitId,
			String userId,SearchCustomer searchCustomer,String[] applyIds,Pagination page){
		StringBuilder sql=new StringBuilder("select a.* from office_customer_apply a,office_customer_info e  where e.id=a.customer_id and a.is_deleted=0 ");
		List<Object> args=new ArrayList<Object>();
		if(StringUtils.isEmpty(searchCustomer.getDataType())){
			sql.append(" and ( a.id in ");
			sql.append(SQLUtils.toSQLInString(applyIds));
			sql.append(" or ((a.unit_id =? and a.follower_id = ? and a.state = 9 and a.apply_type != '2' and e.state=1 ) ");
			sql.append(" or (a.apply_type = '2' and a.unit_id =? and a.follower_id =? )) )");
			args.add(unitId);
			args.add(userId);
			args.add(unitId);
			args.add(userId);
		}else{
			if("1".equals(searchCustomer.getDataType())){
				sql.append(" and ( a.id in ");
				sql.append(SQLUtils.toSQLInString(applyIds));
				sql.append(" or (a.unit_id =? and a.follower_id = ? and a.state = 9 and e.state=1))");
				args.add(unitId);
				args.add(userId);
			}
			
			if("2".equals(searchCustomer.getDataType())){
				sql.append(" and (a.apply_type = '2' and a.unit_id =? and a.follower_id =?) ");
				args.add(unitId);
				args.add(userId);
			}
		}
		
		
		if(StringUtils.isNotEmpty(searchCustomer.getName())){
			sql.append(" and e.name like ?");
			args.add("%"+searchCustomer.getName().trim()+"%");
		}
		if(StringUtils.isNotEmpty(searchCustomer.getRegion())){
			sql.append(" and e.region like ?");
			args.add(searchCustomer.getRegion()+"%");
		}
		if(StringUtils.isNotEmpty(searchCustomer.getType())){
			sql.append(" and e.type = ?");
			args.add(searchCustomer.getType());
		}
		if(StringUtils.isNotEmpty(searchCustomer.getProgressState())){
			if("1".equals(searchCustomer.getDataType())){
				sql.append(" and e.progress_state = ?");
			}else{
				sql.append(" and a.state = ?");
			}
			
			args.add(searchCustomer.getProgressState());
		}
		if(ArrayUtils.isNotEmpty(searchCustomer.getFollowerIds())){
			sql.append(" and a.follower_id in ");
			return queryForInSQL(sql.toString(), args.toArray(), searchCustomer.getFollowerIds(), new MultiRow()," order by follower_id,apply_type,deadline ",page);
		}
		sql.append(" order by follower_id,apply_type,deadline");
		return query(sql.toString(), args.toArray(), new MultiRow(),page);
	}
	@Override
	public List<OfficeCustomerApply> getAllList(SearchCustomer searchCustomer,String[] ids,String unitId,String userId,boolean cilent, Pagination page){
		StringBuilder sql=new StringBuilder("select a.* from office_customer_apply a , office_customer_info b where a.customer_id=b.id ");
		List<Object> args=new ArrayList<Object>();
		sql.append(" and follower_id !=? and a.unit_id=? and a.state=9 and a.is_deleted=0 and  b.state=1 ");
		args.add(userId);
		args.add(unitId);
		if(StringUtils.isNotBlank(searchCustomer.getName())){
			sql.append(" and b.name like ? ");
			args.add("%"+searchCustomer.getName().trim()+"%");
		}
		if(StringUtils.isNotBlank(searchCustomer.getRegion())){
			sql.append(" and b.region like ?");
			args.add(searchCustomer.getRegion()+"%");
		}
		if(searchCustomer.getStartTime()!=null||searchCustomer.getEndTime()!=null){
			sql.append(" and progress_state !='06' and progress_state !='07' and progress_state !='08' ");
		}
		if(searchCustomer.getStartTime()!=null){
			sql.append(" and a.deadline >= ? ");
			args.add(searchCustomer.getStartTime());
		}
		if(searchCustomer.getEndTime()!=null){
			Calendar c=Calendar.getInstance();
			c.setTime(searchCustomer.getEndTime());
			c.add(Calendar.DATE, 1);
			sql.append(" and a.deadline < ? ");
			args.add(c.getTime());
		}
		if(StringUtils.isNotBlank(searchCustomer.getType())){
			sql.append(" and b.type = ?");
			args.add(searchCustomer.getType());
		}
		if(StringUtils.isNotEmpty(searchCustomer.getProgressState())){
			sql.append(" and b.progress_state = ?");
			args.add(searchCustomer.getProgressState());
		}
		if(ArrayUtils.isNotEmpty(searchCustomer.getFollowerIds())){
			sql.append(" and a.follower_id in ");
			sql.append(SQLUtils.toSQLInString(searchCustomer.getFollowerIds()));
		}
		if(cilent&& StringUtils.isEmpty(searchCustomer.getDeptId())){
			if(page==null){
				return query(sql.toString(), args.toArray(),new MultiRow());
			}
			return query(sql.toString(), args.toArray(),new MultiRow(),page);
		}
		sql.append(" and a.follower_id in ");
		if(page==null){
			/*return queryForInSQL(sql.toString(), args.toArray(), ids, new MultiRowMapper<OfficeCustomerApply>(){
				@Override
				public OfficeCustomerApply mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeCustomerApply customer=setField(rs);
					customer.setApplyDate(rs.getTimestamp("apply_date"));
					return customer;
				}
			});*/
			return queryForInSQL(sql.toString(), args.toArray(), ids, new MultiRow(),"");
		}
		return queryForInSQL(sql.toString(), args.toArray(), ids, new MultiRow(),"",page);
		 //and follower_id in
	}
	@Override
	public List<OfficeCustomerApply> getOfficeCustomerApplyByUnitIdList(String unitId){
		String sql = "select * from office_customer_apply where unit_id = ? and is_deleted=0";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}
	@Override
	public List<OfficeCustomerApply> getOfficeCustomerApplyForNosubmit(String[] ids,String unitId,String userId, Pagination page){
		String sql="select * from office_customer_apply where unit_id=? and follower_id=? and state=9 and is_deleted=0 and id not in";
		return queryForInSQL(sql, new Object[]{unitId, userId},ids, new MultiRow()," ",page);
	}
	@Override
	public List<OfficeCustomerApply> getOfficeCustomerApplyByUnitIdPage(String unitId,String userId,SearchCustomer searchCustomer, Pagination page){
		StringBuilder sql = new StringBuilder("select a.*,b.id as apply_id,b.apply_user_id  as apply_user_id,b.audit_date as audit_date,b.apply_date as apply_date,b.business_id as business_id,b.status as status from office_customer_apply a ");
		sql.append(" inner join   base_flow_apply b  on a.id=business_id join office_customer_info c on c.id=a.customer_id where a.unit_id = ? and b.apply_user_id=? and apply_type!='2' and a.is_deleted=0 ");

		List<Object> args=new ArrayList<Object>();
		args.add(unitId);
		args.add(userId);
		if("3".equals(searchCustomer.getDataType())){//新老客户申请
			sql.append(" and apply_type!='3'");
		}
		if("4".equals(searchCustomer.getDataType())){//转发申请
			sql.append(" and apply_type='3'");
		}
		// jion 信息表
		if(StringUtils.isNotBlank(searchCustomer.getName())){
			sql.append(" and c.name like ? ");
			args.add("%"+searchCustomer.getName().trim()+"%");
		}
		if(StringUtils.isNotEmpty(searchCustomer.getRegion())){
			sql.append(" and c.region like ?");
			args.add(searchCustomer.getRegion()+"%");
		}
		if(StringUtils.isNotEmpty(searchCustomer.getType())){
			sql.append(" and c.type = ?");
			args.add(searchCustomer.getType());
		}
		if(StringUtils.isNotEmpty(searchCustomer.getProgressState())){
			if("0".equals(searchCustomer.getProgressState())){
				sql.append(" and a.state is null");
			}else{
				sql.append(" and a.state = ?");
				args.add(searchCustomer.getProgressState());
			}
		}
		sql.append(" order by a.state desc,status");
		return query(sql.toString(), args.toArray(), new MultiRowMapper<OfficeCustomerApply>(){
			@Override
			public OfficeCustomerApply mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OfficeCustomerApply customer=setField(rs);
				customer.setApplyId(rs.getString("apply_id"));
				customer.setApplyUserId(rs.getString("apply_user_id"));
				customer.setAuditDate(rs.getTimestamp("audit_date"));
				customer.setApplyDate(rs.getTimestamp("apply_date"));
				customer.setBusinessId(rs.getString("business_id"));
				customer.setStatus(rs.getInt("status"));
				return customer;
			}
		}, page);
	}
	
  public List<OfficeCustomerApply> getCustomerApplyAuditList(OfficeCustomerApply ent,String queryStatus, String roleId,
					int operateType, String[] arrangeIds, SearchCustomer customer, String[] applyTypes, Pagination page){
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select a.*, b.* from ");
		sql.append("(select apply.* from office_customer_apply apply inner join office_customer_info info on apply.customer_id = info.id ");
		if(customer!= null){
			if(StringUtils.isNotBlank(customer.getName())){
				sql.append(" and info.name like ? ");
				args.add("%"+customer.getName()+"%");
			}
			if(StringUtils.isNotBlank(customer.getRegion())){
				sql.append(" and info.region like ? ");
				args.add(customer.getRegion()+"%");
			}
			if(StringUtils.isNotBlank(customer.getType())){
				sql.append(" and info.type = ? ");
				args.add(customer.getType());
			}
		}
		if(StringUtils.isNotBlank(queryStatus)){
			sql.append(" and apply.state = ? ");
			args.add(queryStatus);
		}else{
			sql.append(" and apply.state <>0 ");
		}
		sql.append(" ) a");
		sql.append(" inner join (select sfap.id as apply_id,sfap.apply_user_id as apply_user_id, sfap.apply_username as apply_username,sfap.apply_date as apply_date,sfad.id as audit_id, sfad.status as auditStatus, sfad.opinion as opinion,sfap.business_id as business_id, sfad.arrange_id as arrange_id from base_flow_apply sfap inner join base_flow_audit sfad on sfap.id=sfad.apply_id ");
		if(StringUtils.isNotBlank(roleId)){
			sql.append(" and sfad.role_id=?");
			args.add(roleId);
		}
		if(customer!=null){
			if(StringUtils.isNotBlank(customer.getApplyUserName())){
				sql.append(" and sfap.apply_username like ?");
				args.add("%"+customer.getApplyUserName().trim()+"%");
			}
			if(customer.getStartTime()!=null){
				sql.append(" and sfap.apply_date >= ?");
				args.add(customer.getStartTime());
			}
			if(customer.getEndTime()!=null){
				sql.append(" and sfap.apply_date <= ?");
				args.add(customer.getEndTime());
			}
		}
		/*if(StringUtils.isNotBlank(queryStatus)){
			if(queryStatus.equals(String.valueOf(FlowAudit.STATUS_AUDIT_PASS))){
				sql.append(" and sfad.status in "+SQLUtils.toSQLInString(new String[]{String.valueOf(FlowAudit.STATUS_AUDIT_PASS), String.valueOf(FlowAudit.STATUS_AUDIT_FINISH)}));
			}else{
				sql.append(" and sfad.status=?");
				args.add(queryStatus);
			}
		}else{
			sql.append(" and sfad.status<>0");
		}*/
		sql.append(" and sfap.operate_type=?");
		args.add(operateType);
		sql.append(" and sfad.apply_unit_id=?) b on a.id = b.business_id and a.is_deleted = 0");
		args.add(ent.getUnitId());
		
		sql.append(" and b.arrange_id IN " + SQLUtils.toSQLInString(arrangeIds));
		
		if(applyTypes != null && applyTypes.length>0){
			sql.append(" and a.apply_type IN " + SQLUtils.toSQLInString(applyTypes));
		}
		
		sql.append(" order by b.apply_date desc");
		
		if(page==null){
			return query(sql.toString(), args.toArray(), new MultiRowMapper<OfficeCustomerApply>(){
				@Override
				public OfficeCustomerApply mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeCustomerApply ent = setField(rs);
					ent.setApplyId(rs.getString("apply_id"));
					ent.setAuditId(rs.getString("audit_id"));
					ent.setAuditStatus(String.valueOf(rs.getInt("auditStatus")));
					ent.setApplyUserId(rs.getString("apply_user_id"));
					ent.setApplyDate(rs.getDate("apply_date"));
					ent.setOpinion(rs.getString("opinion"));
					return ent;
				}
			});
		}else{
			return query(sql.toString(), args.toArray(), new MultiRowMapper<OfficeCustomerApply>(){
				@Override
				public OfficeCustomerApply mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeCustomerApply ent = setField(rs);
					ent.setApplyId(rs.getString("apply_id"));
					ent.setAuditId(rs.getString("audit_id"));
					ent.setAuditStatus(String.valueOf(rs.getInt("auditStatus")));
					ent.setApplyUserId(rs.getString("apply_user_id"));
					ent.setApplyDate(rs.getDate("apply_date"));
					ent.setOpinion(rs.getString("opinion"));
					return ent;
				}
			},page);
		}
		
	}
	
}
