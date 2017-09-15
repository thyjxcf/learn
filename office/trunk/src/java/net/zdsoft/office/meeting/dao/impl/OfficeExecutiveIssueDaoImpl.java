package net.zdsoft.office.meeting.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
import net.zdsoft.office.meeting.dao.OfficeExecutiveIssueDao;
import net.zdsoft.office.meeting.entity.OfficeExecutiveIssue;

import org.apache.commons.lang.StringUtils;
/**
 * office_executive_issue
 * @author 
 * 
 */
public class OfficeExecutiveIssueDaoImpl extends BaseDao<OfficeExecutiveIssue> implements OfficeExecutiveIssueDao{

	@Override
	public OfficeExecutiveIssue setField(ResultSet rs) throws SQLException{
		OfficeExecutiveIssue officeExecutiveIssue = new OfficeExecutiveIssue();
		officeExecutiveIssue.setId(rs.getString("id"));
		officeExecutiveIssue.setName(rs.getString("name"));
		officeExecutiveIssue.setRemark(rs.getString("remark"));
		officeExecutiveIssue.setMeetingId(rs.getString("meeting_id"));
		officeExecutiveIssue.setState(rs.getInt("state"));
		officeExecutiveIssue.setUnitId(rs.getString("unit_id"));
		officeExecutiveIssue.setDeptId(rs.getString("dept_id"));
		officeExecutiveIssue.setCreateUserId(rs.getString("create_user_id"));
		officeExecutiveIssue.setCreateTime(rs.getTimestamp("create_time"));
		officeExecutiveIssue.setSerialNumber(rs.getInt("serial_number"));
		officeExecutiveIssue.setAuditRemark(rs.getString("audit_remark"));
		return officeExecutiveIssue;
	}

	@Override
	public OfficeExecutiveIssue save(OfficeExecutiveIssue officeExecutiveIssue){
		String sql = "insert into office_executive_issue(id, name, remark, meeting_id, state, unit_id, dept_id, create_user_id, create_time, serial_number, audit_remark) values(?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeExecutiveIssue.getId())){
			officeExecutiveIssue.setId(createId());
		}
		Object[] args = new Object[]{
			officeExecutiveIssue.getId(), officeExecutiveIssue.getName(), 
			officeExecutiveIssue.getRemark(), officeExecutiveIssue.getMeetingId(), 
			officeExecutiveIssue.getState(), officeExecutiveIssue.getUnitId(), 
			officeExecutiveIssue.getDeptId(), officeExecutiveIssue.getCreateUserId(), 
			officeExecutiveIssue.getCreateTime(), officeExecutiveIssue.getSerialNumber(),
			officeExecutiveIssue.getAuditRemark()
		};
		update(sql, args);
		return officeExecutiveIssue;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_executive_issue where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeExecutiveIssue officeExecutiveIssue){
		String sql = "update office_executive_issue set name = ?, remark = ?, meeting_id = ?, state = ?, unit_id = ?, dept_id = ?, create_user_id = ?, create_time = ?, serial_number = ?, audit_remark = ? where id = ?";
		Object[] args = new Object[]{
			officeExecutiveIssue.getName(), officeExecutiveIssue.getRemark(), 
			officeExecutiveIssue.getMeetingId(), officeExecutiveIssue.getState(), 
			officeExecutiveIssue.getUnitId(), officeExecutiveIssue.getDeptId(), 
			officeExecutiveIssue.getCreateUserId(), officeExecutiveIssue.getCreateTime(), 
			officeExecutiveIssue.getSerialNumber(), officeExecutiveIssue.getAuditRemark(),
			officeExecutiveIssue.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeExecutiveIssue getOfficeExecutiveIssueById(String id){
		String sql = "select * from office_executive_issue where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeExecutiveIssue> getOfficeExecutiveIssueMapByIds(String[] ids){
		String sql = "select * from office_executive_issue where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssueList(String meetId, Pagination page){
		String sql = "select * from office_executive_issue where meeting_id = ? order by serial_number ";
		if(page == null){
			return query(sql, new Object[]{meetId }, new MultiRow());
		}
		else{
			return query(sql, new Object[]{meetId }, new MultiRow(), page);
		}
	}

	@Override
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssuePage(Pagination page){
		String sql = "select * from office_executive_issue";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssueByUnitIdList(String unitId){
		String sql = "select * from office_executive_issue where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssueByConditions(String unitId, String userId, String deptId, String queryName, String[] queryStates, Pagination page){
		String sql = "select oei.* from office_executive_issue oei where oei.unit_id = ? ";
		List<Object> args = new ArrayList<Object>();
		args.add(unitId);
		if(StringUtils.isNotBlank(queryName)){
			sql += " and oei.name like ?";
			args.add("%"+queryName+"%");
		}
		if(queryStates != null && queryStates.length > 0){
			sql += " and oei.state in " + SQLUtils.toSQLInString(queryStates);
		}
		if(StringUtils.isNotBlank(userId)){
			sql += " and (oei.create_user_id = ?";
			args.add(userId);
			
			if(StringUtils.isNotBlank(deptId)){
				sql += " or (oei.state<>1 and oei.id in (select oeia.issue_id from office_executive_issue_attend oeia where oeia.object_id = ? or oeia.object_id = ?))";
				args.add(userId);
				args.add(deptId);
			}
			sql += ")";
				
		}
		sql += " order by oei.create_time desc";
		return query(sql, args.toArray(), new MultiRow(), page);
	}
	
	@Override
	public void submitIssue(String id){
		String sql = "update office_executive_issue set state = 2 where id = ?";
		update(sql, new Object[]{id});
	}
	
	@Override
	public void removeIssue(String issueId) {
		String sql = "update office_executive_issue set meeting_id = '', serial_number =null where id = ? ";
		update(sql, new Object[]{issueId});
	}
	
	@Override
	public void saveIssueAudit(OfficeExecutiveIssue officeExecutiveIssue){
		String sql = "update office_executive_issue set meeting_id = ?, state = ?, audit_remark = ? where id = ?";
		Object[] args = new Object[]{
			officeExecutiveIssue.getMeetingId(), officeExecutiveIssue.getState(), 
			officeExecutiveIssue.getAuditRemark(), officeExecutiveIssue.getId()
		};
		update(sql, args);
	}
	
	@Override
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssueByMeetIds(
			String[] meetIds) {
		String sql = "select * from office_executive_issue where meeting_id in ";
		return queryForInSQL(sql, null, meetIds, new MultiRow());
	}
	
	@Override
	public List<OfficeExecutiveIssue> getOfficeExecutiveIssues(String unitId, Pagination page){
		String sql = "select * from office_executive_issue where unit_id = ? and state=3 and meeting_id is null order by create_time desc ";
		return query(sql, new Object[]{unitId}, new MultiRow(), page);
	}
	
	@Override
	public void addToMeet(String meetId, String[] ids, Integer firstNumber) {
		String sql = "update office_executive_issue set meeting_id = ? , serial_number = ? where id = ? ";
		List<Object[]> objs = new ArrayList<Object[]>();
		for(int i=0;i<ids.length;i++){
			Object[] args = new Object[]{meetId,firstNumber+i,ids[i]};
			objs.add(args);
		}
		batchUpdate(sql, objs, new int[] {Types.CHAR, Types.INTEGER, Types.CHAR});
	}
	
	@Override
	public List<String> getIdsByMeetId(String meetId) {
		String sql = " select id from office_executive_issue where meeting_id = ? ";
		return query(sql, new Object[]{meetId}, new MultiRowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("id");
			}
		});
	}
	
	@Override
	public void sortIssue(List<OfficeExecutiveIssue> issueList) {
		String sql = "update office_executive_issue set serial_number = ? where id = ? ";
		List<Object[]> args = new ArrayList<Object[]>();
		for(OfficeExecutiveIssue issue : issueList){
			if(StringUtils.isNotBlank(issue.getId())){
				Object[] arg = new Object[]{
					issue.getSerialNumber(), issue.getId()
				};
				args.add(arg);
			}
		}
		batchUpdate(sql, args, new int[] {Types.INTEGER, Types.CHAR});
	}
}