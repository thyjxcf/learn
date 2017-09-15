package net.zdsoft.office.meeting.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeExecutiveIssueAttendDao;
import net.zdsoft.office.meeting.entity.OfficeExecutiveIssueAttend;

import org.apache.commons.lang.StringUtils;
/**
 * office_executive_issue_attend
 * @author 
 * 
 */
public class OfficeExecutiveIssueAttendDaoImpl extends BaseDao<OfficeExecutiveIssueAttend> implements OfficeExecutiveIssueAttendDao{

	@Override
	public OfficeExecutiveIssueAttend setField(ResultSet rs) throws SQLException{
		OfficeExecutiveIssueAttend officeExecutiveIssueAttend = new OfficeExecutiveIssueAttend();
		officeExecutiveIssueAttend.setId(rs.getString("id"));
		officeExecutiveIssueAttend.setIssueId(rs.getString("issue_id"));
		officeExecutiveIssueAttend.setType(rs.getInt("type"));
		officeExecutiveIssueAttend.setObjectId(rs.getString("object_id"));
		officeExecutiveIssueAttend.setRemark(rs.getString("remark"));
		officeExecutiveIssueAttend.setReplyInfo(rs.getString("reply_info"));
		officeExecutiveIssueAttend.setIsReplyed(rs.getBoolean("is_replyed"));
		officeExecutiveIssueAttend.setUnitId(rs.getString("unit_id"));
		return officeExecutiveIssueAttend;
	}

	@Override
	public OfficeExecutiveIssueAttend save(OfficeExecutiveIssueAttend officeExecutiveIssueAttend){
		String sql = "insert into office_executive_issue_attend(id, issue_id, type, object_id, remark, reply_info, is_replyed, unit_id) values(?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeExecutiveIssueAttend.getId())){
			officeExecutiveIssueAttend.setId(createId());
		}
		Object[] args = new Object[]{
			officeExecutiveIssueAttend.getId(), officeExecutiveIssueAttend.getIssueId(), 
			officeExecutiveIssueAttend.getType(), officeExecutiveIssueAttend.getObjectId(), 
			officeExecutiveIssueAttend.getRemark(), officeExecutiveIssueAttend.getReplyInfo(), 
			officeExecutiveIssueAttend.getIsReplyed(), officeExecutiveIssueAttend.getUnitId()
		};
		update(sql, args);
		return officeExecutiveIssueAttend;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_executive_issue_attend where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeExecutiveIssueAttend officeExecutiveIssueAttend){
		String sql = "update office_executive_issue_attend set issue_id = ?, type = ?, object_id = ?, remark = ?, reply_info = ?, is_replyed = ?, unit_id = ? where id = ?";
		Object[] args = new Object[]{
			officeExecutiveIssueAttend.getIssueId(), officeExecutiveIssueAttend.getType(), 
			officeExecutiveIssueAttend.getObjectId(), officeExecutiveIssueAttend.getRemark(), 
			officeExecutiveIssueAttend.getReplyInfo(), officeExecutiveIssueAttend.getIsReplyed(), 
			officeExecutiveIssueAttend.getUnitId(), officeExecutiveIssueAttend.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeExecutiveIssueAttend getOfficeExecutiveIssueAttendById(String id){
		String sql = "select * from office_executive_issue_attend where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeExecutiveIssueAttend> getOfficeExecutiveIssueAttendMapByIds(String[] ids){
		String sql = "select * from office_executive_issue_attend where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeExecutiveIssueAttend> getOfficeExecutiveIssueAttendList(){
		String sql = "select * from office_executive_issue_attend";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeExecutiveIssueAttend> getOfficeExecutiveIssueAttendPage(Pagination page){
		String sql = "select * from office_executive_issue_attend";
		return query(sql, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeExecutiveIssueAttend> getOfficeExecutiveIssueAttendList(String[] issueIds){
		String sql = "select * from office_executive_issue_attend where issue_id in";
		return queryForInSQL(sql, null, issueIds, new MultiRow());
	}
	
	@Override
	public void deleteByIssueIds(String[] issueIds){
		String sql = "delete from office_executive_issue_attend where issue_id in";
		updateForInSQL(sql, null, issueIds);
	}
	
	@Override
	public void batchSave(List<OfficeExecutiveIssueAttend> attendlist){
		List<Object[]> args = new ArrayList<Object[]>();
		for(OfficeExecutiveIssueAttend attend : attendlist){
			if(StringUtils.isBlank(attend.getId())){
				attend.setId(createId());
			}
			Object[] arg = new Object[]{
				attend.getId(), attend.getIssueId(),
				attend.getType(), attend.getObjectId(),
				attend.getRemark(), attend.getReplyInfo(),
				attend.getIsReplyed(), attend.getUnitId()
			};
			args.add(arg);
		}
		String sql = "insert into office_executive_issue_attend(id, issue_id, type, object_id, remark, reply_info, is_replyed, unit_id) values(?,?,?,?,?,?,?,?)";
		batchUpdate(sql, args, new int[]{Types.CHAR,Types.CHAR,Types.INTEGER,Types.CHAR,Types.CHAR,Types.CHAR,Types.BOOLEAN,Types.CHAR});
	}
	
	@Override
	public void batchUpdate(List<OfficeExecutiveIssueAttend> attendlist){
		List<Object[]> args = new ArrayList<Object[]>();
		for(OfficeExecutiveIssueAttend attend : attendlist){
			Object[] arg = new Object[]{
				attend.getIssueId(),
				attend.getType(), attend.getObjectId(),
				attend.getRemark(), attend.getReplyInfo(),
				attend.getIsReplyed(), attend.getUnitId(),
				attend.getId()
			};
			args.add(arg);
		}
		String sql = "update office_executive_issue_attend set issue_id = ?, type = ?, object_id = ?, remark = ?, reply_info = ?, is_replyed = ?, unit_id = ? where id = ?";
		batchUpdate(sql, args, new int[]{Types.CHAR,Types.INTEGER,Types.CHAR,Types.CHAR,Types.CHAR,Types.BOOLEAN,Types.CHAR,Types.CHAR});
	}
	
	public Map<String, String> getOfficeExecutiveIssueAttendMap(String[] issueIds,
			String userId, String deptId) {
		String sql = "";
		Object[] args = null;
		if(StringUtils.isNotBlank(deptId)){
			sql = "select distinct issue_id from office_executive_issue_attend where ((type = 3 and object_id = ?) or (type = 2 and object_id = ?) or (type = 1 and object_id = ?)) and issue_id in ";
			args = new Object[]{userId, deptId, deptId};
		}else{
			sql = "select distinct issue_id from office_executive_issue_attend where type = 3 and object_id = ? and issue_id in ";
			args = new Object[]{userId};
		}
		
		return queryForInSQL(sql, args, issueIds, new MapRowMapper<String, String>() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("issue_id");
			}
			@Override
			public String mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("issue_id");
			}
		});
	}
}