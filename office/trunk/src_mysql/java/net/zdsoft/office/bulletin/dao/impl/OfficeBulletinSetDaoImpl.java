package net.zdsoft.office.bulletin.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.bulletin.entity.OfficeBulletinSet;
import net.zdsoft.office.bulletin.dao.OfficeBulletinSetDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.keel.util.Pagination;
/**
 * office_bulletin_set
 * @author 
 * 
 */
public class OfficeBulletinSetDaoImpl extends BaseDao<OfficeBulletinSet> implements OfficeBulletinSetDao{

	@Override
	public OfficeBulletinSet setField(ResultSet rs) throws SQLException{
		OfficeBulletinSet officeBulletinSet = new OfficeBulletinSet();
		officeBulletinSet.setId(rs.getString("id"));
		officeBulletinSet.setUnitId(rs.getString("unit_id"));
		officeBulletinSet.setNeedAudit(rs.getString("need_audit"));
		officeBulletinSet.setAuditId(rs.getString("audit_id"));
		officeBulletinSet.setRoleCode(rs.getString("role_code"));
		return officeBulletinSet;
	}

	@Override
	public OfficeBulletinSet save(OfficeBulletinSet officeBulletinSet){
		String sql = "insert into office_bulletin_set(id, unit_id, need_audit, audit_id,role_code) values(?,?,?,?,?)";
		if (StringUtils.isBlank(officeBulletinSet.getId())){
			officeBulletinSet.setId(createId());
		}
		Object[] args = new Object[]{
			officeBulletinSet.getId(), officeBulletinSet.getUnitId(), 
			officeBulletinSet.getNeedAudit(), officeBulletinSet.getAuditId(),
			officeBulletinSet.getRoleCode()
		};
		update(sql, args);
		return officeBulletinSet;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_bulletin_set where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeBulletinSet officeBulletinSet){
		String sql = "update office_bulletin_set set unit_id = ?, need_audit = ?, audit_id = ? ,role_code=? where id = ?";
		Object[] args = new Object[]{
			officeBulletinSet.getUnitId(), officeBulletinSet.getNeedAudit(), 
			officeBulletinSet.getAuditId(),officeBulletinSet.getRoleCode(),
			officeBulletinSet.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeBulletinSet getOfficeBulletinSetById(String id){
		String sql = "select * from office_bulletin_set where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeBulletinSet> getOfficeBulletinSetMapByIds(String[] ids){
		String sql = "select * from office_bulletin_set where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeBulletinSet> getOfficeBulletinSetList(){
		String sql = "select * from office_bulletin_set";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeBulletinSet> getOfficeBulletinSetPage(Pagination page){
		String sql = "select * from office_bulletin_set";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeBulletinSet> getOfficeBulletinSetByUnitIdList(String unitId,String roleCode){
		String sql = "select * from office_bulletin_set where unit_id = ? and role_code=?";
		return query(sql, new Object[]{unitId,roleCode }, new MultiRow());
	}

	@Override
	public List<OfficeBulletinSet> getOfficeBulletinSetByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_bulletin_set where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeBulletinSet> getOfficeBulletinSetByUnitIdUserIdList(
			String unitId, String userId,String roleCode) {
		String sql = "select * from office_bulletin_set where unit_id = ? and role_code=? and audit_id like '%"+userId+"%'";
		return query(sql, new Object[]{unitId,roleCode }, new MultiRow());
	}
}
