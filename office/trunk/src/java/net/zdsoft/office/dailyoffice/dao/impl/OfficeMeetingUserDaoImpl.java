package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.dailyoffice.entity.OfficeMeetingUser;
import net.zdsoft.office.dailyoffice.dao.OfficeMeetingUserDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_meeting_user
 * @author 
 * 
 */
public class OfficeMeetingUserDaoImpl extends BaseDao<OfficeMeetingUser> implements OfficeMeetingUserDao{

	@Override
	public OfficeMeetingUser setField(ResultSet rs) throws SQLException{
		OfficeMeetingUser officeMeetingUser = new OfficeMeetingUser();
		officeMeetingUser.setId(rs.getString("id"));
		officeMeetingUser.setMeetingApplyId(rs.getString("meeting_apply_id"));
		officeMeetingUser.setUserId(rs.getString("user_id"));
		return officeMeetingUser;
	}

	@Override
	public OfficeMeetingUser save(OfficeMeetingUser officeMeetingUser){
		String sql = "insert into office_meeting_user(id, meeting_apply_id, user_id) values(?,?,?)";
		if (StringUtils.isBlank(officeMeetingUser.getId())){
			officeMeetingUser.setId(createId());
		}
		Object[] args = new Object[]{
			officeMeetingUser.getId(), officeMeetingUser.getMeetingApplyId(), 
			officeMeetingUser.getUserId()
		};
		update(sql, args);
		return officeMeetingUser;
	}
	
	@Override
	public void batchsave(List<OfficeMeetingUser> meetingUserList){
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
	    for (int i = 0; i < meetingUserList.size(); i++) {
	    	OfficeMeetingUser officeMeetingUser = meetingUserList.get(i);
	    	if (StringUtils.isBlank(officeMeetingUser.getId())){
	    		officeMeetingUser.setId(createId());
			}
	        listOfArgs.add(new Object[] {
	        	officeMeetingUser.getId(), officeMeetingUser.getMeetingApplyId(), 
	        	officeMeetingUser.getUserId()
	        });
	    }
	    int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.CHAR };
	    String sql = "insert into office_meeting_user(id, meeting_apply_id, user_id) values(?,?,?)";
	    batchUpdate(sql, listOfArgs, argTypes);
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_meeting_user where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeMeetingUser officeMeetingUser){
		String sql = "update office_meeting_user set meeting_apply_id = ?, user_id = ? where id = ?";
		Object[] args = new Object[]{
			officeMeetingUser.getMeetingApplyId(), officeMeetingUser.getUserId(), 
			officeMeetingUser.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeMeetingUser getOfficeMeetingUserById(String id){
		String sql = "select * from office_meeting_user where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeMeetingUser> getOfficeMeetingUserMapByIds(String[] ids){
		String sql = "select * from office_meeting_user where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeMeetingUser> getOfficeMeetingUserList(){
		String sql = "select * from office_meeting_user";
		return query(sql, new MultiRow());
	}
	
	@Override
	public List<OfficeMeetingUser> getOfficeMeetingUserListByApplyId(String applyId){
		String sql = "select * from office_meeting_user where meeting_apply_id = ?";
		return query(sql, applyId, new MultiRow());
	}

	@Override
	public List<OfficeMeetingUser> getOfficeMeetingUserPage(Pagination page){
		String sql = "select * from office_meeting_user";
		return query(sql, new MultiRow(), page);
	}
}
	