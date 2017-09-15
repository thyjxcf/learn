package net.zdsoft.office.seal.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.seal.entity.OfficeSeal;
import net.zdsoft.office.seal.dao.OfficeSealDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_seal
 * @author 
 * 
 */
public class OfficeSealDaoImpl extends BaseDao<OfficeSeal> implements OfficeSealDao{

	@Override
	public OfficeSeal setField(ResultSet rs) throws SQLException{
		OfficeSeal officeSeal = new OfficeSeal();
		officeSeal.setId(rs.getString("id"));
		officeSeal.setAcadyear(rs.getString("acadyear"));
		officeSeal.setSemester(rs.getInt("semester"));
		officeSeal.setCreateUserId(rs.getString("create_user_id"));
		officeSeal.setCreateTime(rs.getTimestamp("create_time"));
		officeSeal.setUnitId(rs.getString("unit_id"));
		officeSeal.setApplyOpinion(rs.getString("apply_opinion"));
		officeSeal.setAuditOpinion(rs.getString("audit_opinion"));
		officeSeal.setAuditUserId(rs.getString("audit_user_id"));
		officeSeal.setSealType(rs.getString("seal_type"));
		officeSeal.setState(rs.getString("state"));
		officeSeal.setManageUserId(rs.getString("manage_user_id"));
		officeSeal.setDeptId(rs.getString("dept_id"));
		officeSeal.setUseSeal(rs.getString("use_seal"));
		return officeSeal;
	}

	@Override
	public OfficeSeal save(OfficeSeal officeSeal){
		String sql = "insert into office_seal(id, acadyear, semester, create_user_id, create_time, unit_id, apply_opinion, audit_opinion, audit_user_id, seal_type, state, manage_user_id, use_seal,dept_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeSeal.getId())){
			officeSeal.setId(createId());
		}
		Object[] args = new Object[]{
			officeSeal.getId(), officeSeal.getAcadyear(), 
			officeSeal.getSemester(), officeSeal.getCreateUserId(), 
			officeSeal.getCreateTime(), officeSeal.getUnitId(), 
			officeSeal.getApplyOpinion(), officeSeal.getAuditOpinion(), 
			officeSeal.getAuditUserId(), officeSeal.getSealType(), 
			officeSeal.getState(), officeSeal.getManageUserId(), 
			officeSeal.getUseSeal(),officeSeal.getDeptId()
		};
		update(sql, args);
		return officeSeal;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_seal where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeSeal officeSeal){
		String sql = "update office_seal set acadyear = ?, semester = ?, create_user_id = ?, create_time = ?, unit_id = ?, apply_opinion = ?, audit_opinion = ?, audit_user_id = ?, seal_type = ?, state = ?, manage_user_id = ?, use_seal = ?,dept_id=? where id = ?";
		Object[] args = new Object[]{
			officeSeal.getAcadyear(), officeSeal.getSemester(), 
			officeSeal.getCreateUserId(), officeSeal.getCreateTime(), 
			officeSeal.getUnitId(), officeSeal.getApplyOpinion(), 
			officeSeal.getAuditOpinion(), officeSeal.getAuditUserId(), 
			officeSeal.getSealType(), officeSeal.getState(), 
			officeSeal.getManageUserId(), officeSeal.getUseSeal(), 
			officeSeal.getDeptId(),officeSeal.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeSeal getOfficeSealById(String id){
		String sql = "select * from office_seal where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeSeal> getOfficeSealMapByIds(String[] ids){
		String sql = "select * from office_seal where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeSeal> getOfficeSealList(){
		String sql = "select * from office_seal";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeSeal> getOfficeSealPage(Pagination page){
		String sql = "select * from office_seal";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeSeal> getOfficeSealByUnitIdList(String unitId){
		String sql = "select * from office_seal where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeSeal> getOfficeSealByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_seal where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeSeal> getOfficeSealByOthers(String years,
			String semesters, String userId, String unitId,Pagination page) {
		String sql="select * from office_seal where unit_id=? ";
		StringBuffer sb=new StringBuffer(sql);
		List<Object> args=new ArrayList<Object>();
		args.add(unitId);
		if(StringUtils.isNotBlank(userId)){
			sb.append(" and create_user_id=?");
			args.add(userId);
		}
		if(StringUtils.isNotBlank(years)){
			sb.append(" and acadyear=?");
			args.add(years);
		}
		if(StringUtils.isNotBlank(semesters)){
			sb.append(" and semester=?");
			args.add(semesters);
		}
		sb.append(" order by case when state=1 then 1 when state=2 then 2 end, create_time desc");
		if(page!=null){
			return query(sb.toString(), args.toArray(), new MultiRow(), page);
		}else return query(sb.toString(), args.toArray(), new MultiRow());
	}

	@Override
	public List<OfficeSeal> getOfficeSealManageByOthers(String unitId,
			String deptId, String sealType, Pagination page) {
		String sql="select * from office_seal where unit_id=? and state!='1' ";
		StringBuffer sb=new StringBuffer(sql);
		List<Object> args=new ArrayList<Object>();
		args.add(unitId);
		if(StringUtils.isNotBlank(deptId)){
			sb.append(" and dept_id=?");
			args.add(deptId);
		}
		if(StringUtils.isNotBlank(sealType)){
			sb.append(" and seal_type=?");
			args.add(sealType);
		}
		sb.append(" order by case when state=2 then 1 when manage_user_id!=null then 2  end, create_time desc");
		if(page!=null){
			return query(sb.toString(), args.toArray(), new MultiRow(), page);
		}else return query(sb.toString(), args.toArray(), new MultiRow());
	}

	@Override
	public List<OfficeSeal> getOfficeSealByUnitIdTypeId(String unitId,
			String[] typeIds) {
		String sql="select * from office_seal where unit_id=? and seal_type in";
		return queryForInSQL(sql, new String[]{unitId}, typeIds, new MultiRow());
	}
	
}
