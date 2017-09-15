package net.zdsoft.office.meeting.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeWorkMeetingAttendDao;
import net.zdsoft.office.meeting.entity.OfficeWorkMeetingAttend;

import org.apache.commons.lang3.StringUtils;
/**
 * office_work_meeting_attend
 * @author 
 * 
 */
public class OfficeWorkMeetingAttendDaoImpl extends BaseDao<OfficeWorkMeetingAttend> implements OfficeWorkMeetingAttendDao{
	private static final String SQL_INSERT = "insert into office_work_meeting_attend(id, meeting_id, type, object_id) values(?,?,?,?)";
	@Override
	public OfficeWorkMeetingAttend setField(ResultSet rs) throws SQLException{
		OfficeWorkMeetingAttend officeWorkMeetingAttend = new OfficeWorkMeetingAttend();
		officeWorkMeetingAttend.setId(rs.getString("id"));
		officeWorkMeetingAttend.setMeetingId(rs.getString("meeting_id"));
		officeWorkMeetingAttend.setType(rs.getInt("type"));
		officeWorkMeetingAttend.setObjectId(rs.getString("object_id"));
		return officeWorkMeetingAttend;
	}

	@Override
	public OfficeWorkMeetingAttend save(OfficeWorkMeetingAttend officeWorkMeetingAttend){
		String sql = "insert into office_work_meeting_attend(id, meeting_id, type, object_id) values(?,?,?,?)";
		if (StringUtils.isBlank(officeWorkMeetingAttend.getId())){
			officeWorkMeetingAttend.setId(createId());
		}
		Object[] args = new Object[]{
			officeWorkMeetingAttend.getId(), officeWorkMeetingAttend.getMeetingId(), 
			officeWorkMeetingAttend.getType(), officeWorkMeetingAttend.getObjectId()
		};
		update(sql, args);
		return officeWorkMeetingAttend;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_work_meeting_attend where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeWorkMeetingAttend officeWorkMeetingAttend){
		String sql = "update office_work_meeting_attend set meeting_id = ?, type = ?, object_id = ? where id = ?";
		Object[] args = new Object[]{
			officeWorkMeetingAttend.getMeetingId(), officeWorkMeetingAttend.getType(), 
			officeWorkMeetingAttend.getObjectId(), officeWorkMeetingAttend.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeWorkMeetingAttend getOfficeWorkMeetingAttendById(String id){
		String sql = "select * from office_work_meeting_attend where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeWorkMeetingAttend> getOfficeWorkMeetingAttendMapByIds(String[] ids){
		String sql = "select * from office_work_meeting_attend where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeWorkMeetingAttend> getOfficeWorkMeetingAttendList(){
		String sql = "select * from office_work_meeting_attend";
		return query(sql, new MultiRow());
	}
	@Override
	public List<OfficeWorkMeetingAttend> getOfficeWorkMeetingAttendList(
			String[] meetingIds) {
		String sql = "select * from office_work_meeting_attend where meeting_id in ";
		return queryForInSQL(sql, null, meetingIds, new MultiRow());
	}
	@Override
	public void batchSave(List<OfficeWorkMeetingAttend> meetingsAttendsList) {
		List<Object[]> args = new ArrayList<Object[]>();
		for (OfficeWorkMeetingAttend meetingsAttends: meetingsAttendsList) {
			if (StringUtils.isBlank(meetingsAttends.getId())){
				meetingsAttends.setId(createId());
			}
			Object[] arg = new Object[]{
					meetingsAttends.getId(), meetingsAttends.getMeetingId(), 
					meetingsAttends.getType(), meetingsAttends.getObjectId()
				};
			args.add(arg);
		}
		batchUpdate(SQL_INSERT, args, new int[]{Types.CHAR,Types.CHAR,Types.INTEGER,Types.CHAR});
	}
	
@Override
	public void deleteByMeetingId(String meetingId, int type) {
		String sql = "delete from office_work_meeting_attend where meeting_id = ? ";
		if(type != 0){
			sql += " and type = ? ";
			update(sql, new Object[]{meetingId,type});
		}else{
			update(sql, new Object[]{meetingId});
		}	
	}
	@Override
	public List<OfficeWorkMeetingAttend> getMeetingsAttendsListByMeetingIds(
			String[] meetingIds) {
		String sql = "select * from office_work_meeting_attend where meeting_id in ";
		return queryForInSQL(sql, null, meetingIds, new MultiRow());
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String> getMeetingsIds(String userId, String deptId,
			String[] types) {
		StringBuffer str = new StringBuffer("select distinct meeting_id from office_work_meeting_attend ");
		List<Object> objs = new ArrayList<Object>();
		if(StringUtils.isNotBlank(deptId)){
			str.append(" where (object_id = ? or object_id = ? ) ");
			objs.add(userId);
			objs.add(deptId);
		}else{
			str.append(" where object_id = ? ");
			objs.add(userId);
		}
		str.append(" and type in ");
		
		return queryForInSQL(str.toString(), objs.toArray(new Object[0]), types, new MultiRowMapper() {
			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("meeting_id");
			}
		});
	}
	@Override
	public List<OfficeWorkMeetingAttend> getOfficeWorkMeetingAttendPage(Pagination page){
		String sql = "select * from office_work_meeting_attend";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeWorkMeetingAttend> getOfficeWorkMeetingAttendByMeetingIds(
			String[] ids) {
		String sql="select * from office_work_meeting_attend where meeting_id in ";
		return queryForInSQL(sql, null, ids, new MultiRow());
	}

	@Override
	public Map<String, OfficeWorkMeetingAttend> getattendMap(String[] ids) {
		String sql="select * from office_work_meeting_attend where meeting_id in ";
		return queryForInSQL(sql, null, ids, new MapRowMapper() {

			@Override
			public Object mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("meeting_id");
			}

			@Override
			public Object mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
			
		});
	}
}
