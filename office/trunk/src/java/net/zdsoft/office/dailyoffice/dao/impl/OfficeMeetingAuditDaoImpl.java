package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeMeetingAuditDao;
import net.zdsoft.office.dailyoffice.entity.OfficeMeetingAudit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_meeting_audit
 * @author 
 * 
 */
public class OfficeMeetingAuditDaoImpl extends BaseDao<OfficeMeetingAudit> implements OfficeMeetingAuditDao{

	@Override
	public OfficeMeetingAudit setField(ResultSet rs) throws SQLException{
		OfficeMeetingAudit officeMeetingAudit = new OfficeMeetingAudit();
		officeMeetingAudit.setId(rs.getString("id"));
		officeMeetingAudit.setUnitId(rs.getString("unit_id"));
		officeMeetingAudit.setApplyId(rs.getString("apply_id"));
		officeMeetingAudit.setState(rs.getString("state"));
		officeMeetingAudit.setAuditUserId(rs.getString("audit_user_id"));
		officeMeetingAudit.setOpinion(rs.getString("opinion"));
		officeMeetingAudit.setAuditDate(rs.getTimestamp("audit_date"));
		return officeMeetingAudit;
	}

	@Override
	public OfficeMeetingAudit save(OfficeMeetingAudit officeMeetingAudit){
		String sql = "insert into office_meeting_audit(id, unit_id, apply_id, state, audit_user_id, opinion, audit_date) values(?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeMeetingAudit.getId())){
			officeMeetingAudit.setId(createId());
		}
		Object[] args = new Object[]{
			officeMeetingAudit.getId(), officeMeetingAudit.getUnitId(), 
			officeMeetingAudit.getApplyId(), officeMeetingAudit.getState(), 
			officeMeetingAudit.getAuditUserId(), officeMeetingAudit.getOpinion(), 
			officeMeetingAudit.getAuditDate()
		};
		update(sql, args);
		return officeMeetingAudit;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_meeting_audit where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeMeetingAudit officeMeetingAudit){
		String sql = "update office_meeting_audit set unit_id = ?, apply_id = ?, state = ?, audit_user_id = ?, opinion = ?, audit_date = ? where id = ?";
		Object[] args = new Object[]{
			officeMeetingAudit.getUnitId(), officeMeetingAudit.getApplyId(), 
			officeMeetingAudit.getState(), officeMeetingAudit.getAuditUserId(), 
			officeMeetingAudit.getOpinion(), officeMeetingAudit.getAuditDate(), 
			officeMeetingAudit.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeMeetingAudit getOfficeMeetingAuditById(String id){
		String sql = "select * from office_meeting_audit where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeMeetingAudit> getOfficeMeetingAuditMapByIds(String[] ids){
		String sql = "select * from office_meeting_audit where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeMeetingAudit> getOfficeMeetingAuditList(){
		String sql = "select * from office_meeting_audit";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeMeetingAudit> getOfficeMeetingAuditPage(Pagination page){
		String sql = "select * from office_meeting_audit";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeMeetingAudit> getOfficeMeetingAuditByUnitIdList(String unitId){
		String sql = "select * from office_meeting_audit where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeMeetingAudit> getOfficeMeetingAuditByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_meeting_audit where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeMeetingAudit> getOfficeMeetingAuditByApplyId(String applyId){
		String sql = "select * from office_meeting_audit where apply_id = ?";
		return query(sql, applyId, new MultiRow());
	}
	
	@Override
	public Map<String, List<OfficeMeetingAudit>> getOfficeMeetingAuditMapByApplyIds(String[] applyIds){
		String sql = "select * from office_meeting_audit where apply_id in";
		List<OfficeMeetingAudit> list = queryForInSQL(sql, null, applyIds, new MultiRow());
		Map<String, List<OfficeMeetingAudit>> meetingAuditMap = new HashMap<String, List<OfficeMeetingAudit>>();
		List<OfficeMeetingAudit> meetingAuditList = null;
		
		if (CollectionUtils.isNotEmpty(list)) {
			for (OfficeMeetingAudit officeMeetingAudit : list) {
				
				if (meetingAuditMap.containsKey(officeMeetingAudit.getApplyId())) {
					meetingAuditList = meetingAuditMap.get(officeMeetingAudit.getApplyId());
				} else {
					meetingAuditList = new ArrayList<OfficeMeetingAudit>();
				}
				meetingAuditList.add(officeMeetingAudit);
				meetingAuditMap.put(officeMeetingAudit.getApplyId(), meetingAuditList);
			}
		}
		return meetingAuditMap;
	}
}
	