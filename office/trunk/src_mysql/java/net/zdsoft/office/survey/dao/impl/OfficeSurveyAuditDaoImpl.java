package net.zdsoft.office.survey.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.survey.entity.OfficeSurveyAudit;
import net.zdsoft.office.survey.dao.OfficeSurveyAuditDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_survey_audit
 * @author 
 * 
 */
public class OfficeSurveyAuditDaoImpl extends BaseDao<OfficeSurveyAudit> implements OfficeSurveyAuditDao{

	@Override
	public OfficeSurveyAudit setField(ResultSet rs) throws SQLException{
		OfficeSurveyAudit officeSurveyAudit = new OfficeSurveyAudit();
		officeSurveyAudit.setId(rs.getString("id"));
		officeSurveyAudit.setUnitId(rs.getString("unit_id"));
		officeSurveyAudit.setApplyId(rs.getString("apply_id"));
		officeSurveyAudit.setState(rs.getInt("state"));
		officeSurveyAudit.setAuditUserId(rs.getString("audit_user_id"));
		officeSurveyAudit.setOpinion(rs.getString("opinion"));
		officeSurveyAudit.setAuditDate(rs.getTimestamp("audit_date"));
		return officeSurveyAudit;
	}

	@Override
	public OfficeSurveyAudit save(OfficeSurveyAudit officeSurveyAudit){
		String sql = "insert into office_survey_audit(id, unit_id, apply_id, state, audit_user_id, opinion, audit_date) values(?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeSurveyAudit.getId())){
			officeSurveyAudit.setId(createId());
		}
		Object[] args = new Object[]{
			officeSurveyAudit.getId(), officeSurveyAudit.getUnitId(), 
			officeSurveyAudit.getApplyId(), officeSurveyAudit.getState(), 
			officeSurveyAudit.getAuditUserId(), officeSurveyAudit.getOpinion(), 
			officeSurveyAudit.getAuditDate()
		};
		update(sql, args);
		return officeSurveyAudit;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_survey_audit where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeSurveyAudit officeSurveyAudit){
		String sql = "update office_survey_audit set unit_id = ?, apply_id = ?, state = ?, audit_user_id = ?, opinion = ?, audit_date = ? where id = ?";
		Object[] args = new Object[]{
			officeSurveyAudit.getUnitId(), officeSurveyAudit.getApplyId(), 
			officeSurveyAudit.getState(), officeSurveyAudit.getAuditUserId(), 
			officeSurveyAudit.getOpinion(), officeSurveyAudit.getAuditDate(), 
			officeSurveyAudit.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeSurveyAudit getOfficeSurveyAuditById(String id){
		String sql = "select * from office_survey_audit where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeSurveyAudit> getOfficeSurveyAuditMapByIds(String[] ids){
		String sql = "select * from office_survey_audit where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeSurveyAudit> getOfficeSurveyAuditList(){
		String sql = "select * from office_survey_audit";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeSurveyAudit> getOfficeSurveyAuditPage(Pagination page){
		String sql = "select * from office_survey_audit";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeSurveyAudit> getOfficeSurveyAuditByUnitIdList(String unitId){
		String sql = "select * from office_survey_audit where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeSurveyAudit> getOfficeSurveyAuditByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_survey_audit where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public Map<String, OfficeSurveyAudit> getOfficeSurveyAuditByUnitIdMap(String unitId){
		String sql = "select * from office_survey_audit where unit_id = ?";
		return queryForMap(sql, new Object[]{unitId }, new MapRow());
	}

	@Override
	public OfficeSurveyAudit getOfficeSurveyAuditByUnitId(String unitId,
			String applyId) {
		String sql = "select * from office_survey_audit where unit_id = ? and apply_id=?";
		return query(sql, new Object[]{unitId,applyId }, new SingleRow());
	}
}
