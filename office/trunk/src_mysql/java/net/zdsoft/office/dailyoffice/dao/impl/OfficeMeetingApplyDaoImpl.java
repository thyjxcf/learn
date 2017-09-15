package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
import net.zdsoft.office.dailyoffice.dao.OfficeMeetingApplyDao;
import net.zdsoft.office.dailyoffice.entity.OfficeMeetingApply;

import org.apache.commons.lang.StringUtils;
/**
 * office_meeting_apply
 * @author 
 * 
 */
public class OfficeMeetingApplyDaoImpl extends BaseDao<OfficeMeetingApply> implements OfficeMeetingApplyDao{

	@Override
	public OfficeMeetingApply setField(ResultSet rs) throws SQLException{
		OfficeMeetingApply officeMeetingApply = new OfficeMeetingApply();
		officeMeetingApply.setId(rs.getString("id"));
		officeMeetingApply.setUnitId(rs.getString("unit_id"));
		officeMeetingApply.setApplyUserId(rs.getString("apply_user_id"));
		officeMeetingApply.setMeetingTheme(rs.getString("meeting_theme"));
		officeMeetingApply.setHostUserId(rs.getString("host_user_id"));
		officeMeetingApply.setStartTime(rs.getTimestamp("start_time"));
		officeMeetingApply.setEndTime(rs.getTimestamp("end_time"));
		officeMeetingApply.setMeetingPlace(rs.getString("meeting_place"));
		officeMeetingApply.setMeetingRoom(rs.getString("meeting_room"));
		officeMeetingApply.setDeptIds(rs.getString("dept_ids"));
		officeMeetingApply.setParticipants(rs.getString("participants"));
		officeMeetingApply.setMeetingContent(rs.getString("meeting_content"));
		officeMeetingApply.setFee(rs.getInt("fee"));
		officeMeetingApply.setState(rs.getInt("state"));
		officeMeetingApply.setIsSendNotice(rs.getBoolean("is_send_notice"));
		officeMeetingApply.setIsSendSms(rs.getBoolean("is_send_sms"));
		officeMeetingApply.setApplyDate(rs.getTimestamp("apply_date"));
		officeMeetingApply.setAuditDate(rs.getTimestamp("audit_date"));
		officeMeetingApply.setCreationTime(rs.getTimestamp("creation_time"));
		return officeMeetingApply;
	}

	@Override
	public OfficeMeetingApply save(OfficeMeetingApply officeMeetingApply){
		String sql = "insert into office_meeting_apply(id, unit_id, apply_user_id, meeting_theme, host_user_id, start_time, end_time, meeting_place, meeting_room, dept_ids, participants, meeting_content, fee, state, is_send_notice, is_send_sms, apply_date, audit_date, creation_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeMeetingApply.getId())){
			officeMeetingApply.setId(createId());
		}
		officeMeetingApply.setCreationTime(getCreateDate());
		Object[] args = new Object[]{
			officeMeetingApply.getId(), officeMeetingApply.getUnitId(), 
			officeMeetingApply.getApplyUserId(), officeMeetingApply.getMeetingTheme(), 
			officeMeetingApply.getHostUserId(), officeMeetingApply.getStartTime(), 
			officeMeetingApply.getEndTime(), officeMeetingApply.getMeetingPlace(), 
			officeMeetingApply.getMeetingRoom(), officeMeetingApply.getDeptIds(), 
			officeMeetingApply.getParticipants(), officeMeetingApply.getMeetingContent(), 
			officeMeetingApply.getFee(), officeMeetingApply.getState(), 
			officeMeetingApply.getIsSendNotice(), officeMeetingApply.getIsSendSms(), 
			officeMeetingApply.getApplyDate(), officeMeetingApply.getAuditDate(), 
			officeMeetingApply.getCreationTime()
		};
		update(sql, args);
		return officeMeetingApply;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_meeting_apply where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeMeetingApply officeMeetingApply){
		String sql = "update office_meeting_apply set unit_id = ?, apply_user_id = ?, meeting_theme = ?, host_user_id = ?, start_time = ?, end_time = ?, meeting_place = ?, meeting_room = ?, dept_ids = ?, participants = ?, meeting_content = ?, fee = ?, state = ?, is_send_notice = ?, is_send_sms = ?, apply_date = ?, audit_date = ?, creation_time = ? where id = ?";
		Object[] args = new Object[]{
			officeMeetingApply.getUnitId(), officeMeetingApply.getApplyUserId(), 
			officeMeetingApply.getMeetingTheme(), officeMeetingApply.getHostUserId(), 
			officeMeetingApply.getStartTime(), officeMeetingApply.getEndTime(), 
			officeMeetingApply.getMeetingPlace(), officeMeetingApply.getMeetingRoom(), 
			officeMeetingApply.getDeptIds(), officeMeetingApply.getParticipants(), 
			officeMeetingApply.getMeetingContent(), officeMeetingApply.getFee(), 
			officeMeetingApply.getState(), officeMeetingApply.getIsSendNotice(), 
			officeMeetingApply.getIsSendSms(), officeMeetingApply.getApplyDate(), 
			officeMeetingApply.getAuditDate(), officeMeetingApply.getCreationTime(), 
			officeMeetingApply.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeMeetingApply getOfficeMeetingApplyById(String id){
		String sql = "select * from office_meeting_apply where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeMeetingApply> getOfficeMeetingApplyMapByIds(String[] ids){
		String sql = "select * from office_meeting_apply where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeMeetingApply> getOfficeMeetingApplyList(){
		String sql = "select * from office_meeting_apply";
		return query(sql, new MultiRow());
	}
	
	@Override
	public List<OfficeMeetingApply> getOfficeMeetingApplyList(String unitId, String applyUserId, 
			String queryName, String[] queryState, String queryBeginDate, String queryEndDate, Pagination page){
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select ap.* from office_meeting_apply ap join office_meeting_audit au on ap.id=au.apply_id where ap.unit_id = ?");
		args.add(unitId);
		if(StringUtils.isNotBlank(applyUserId)){
			sql.append(" and ap.apply_user_id = ?");
			args.add(applyUserId);
		}
		if(StringUtils.isNotBlank(queryName)){
			sql.append(" and ap.meeting_theme like '%"+queryName+"%'");
		}
		if(queryState != null){
			sql.append(" and au.state in "+SQLUtils.toSQLInString(queryState));
		}
		if(StringUtils.isNotBlank(queryBeginDate)){
			sql.append(" and str_to_date(?, '%Y-%m-%d') <= str_to_date(date_format(ap.start_time, '%Y-%m-%d'), '%Y-%m-%d')");
			args.add(queryBeginDate);
		}
		if(StringUtils.isNotBlank(queryEndDate)){
			sql.append(" and str_to_date(?, '%Y-%m-%d') >= str_to_date(date_format(ap.end_time, '%Y-%m-%d'), '%Y-%m-%d')");
			args.add(queryEndDate);
		}
		sql.append(" order by ap.audit_date desc");
		if(page != null){
			return query(sql.toString(),args.toArray(), new MultiRow(), page);
		}
		else{
			return query(sql.toString(),args.toArray(), new MultiRow());
		}
	}

	@Override
	public List<OfficeMeetingApply> getOfficeMeetingApplyPage(Pagination page){
		String sql = "select * from office_meeting_apply";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeMeetingApply> getOfficeMeetingApplyByUnitIdList(String unitId){
		String sql = "select * from office_meeting_apply where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeMeetingApply> getOfficeMeetingApplyByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_meeting_apply where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
}
