package net.zdsoft.office.meeting.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeWorkMeetingMinutesDao;
import net.zdsoft.office.meeting.entity.OfficeWorkMeetingMinutes;

import org.apache.commons.lang3.StringUtils;
/**
 * office_work_meeting_minutes
 * @author 
 * 
 */
public class OfficeWorkMeetingMinutesDaoImpl extends BaseDao<OfficeWorkMeetingMinutes> implements OfficeWorkMeetingMinutesDao{

	@Override
	public OfficeWorkMeetingMinutes setField(ResultSet rs) throws SQLException{
		OfficeWorkMeetingMinutes officeWorkMeetingMinutes = new OfficeWorkMeetingMinutes();
		officeWorkMeetingMinutes.setId(rs.getString("id"));
		officeWorkMeetingMinutes.setMeetingId(rs.getString("meeting_id"));
		officeWorkMeetingMinutes.setContent(rs.getString("content"));
		officeWorkMeetingMinutes.setCreateUserId(rs.getString("create_user_id"));
		officeWorkMeetingMinutes.setCreateTime(rs.getTimestamp("create_time"));
		return officeWorkMeetingMinutes;
	}

	@Override
	public OfficeWorkMeetingMinutes save(OfficeWorkMeetingMinutes officeWorkMeetingMinutes){
		String sql = "insert into office_work_meeting_minutes(id, meeting_id, content, create_user_id, create_time) values(?,?,?,?,?)";
		if (StringUtils.isBlank(officeWorkMeetingMinutes.getId())){
			officeWorkMeetingMinutes.setId(createId());
		}
		Object[] args = new Object[]{
			officeWorkMeetingMinutes.getId(), officeWorkMeetingMinutes.getMeetingId(), 
			officeWorkMeetingMinutes.getContent(), officeWorkMeetingMinutes.getCreateUserId(), 
			officeWorkMeetingMinutes.getCreateTime()
		};
		update(sql, args);
		return officeWorkMeetingMinutes;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_work_meeting_minutes where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeWorkMeetingMinutes officeWorkMeetingMinutes){
		String sql = "update office_work_meeting_minutes set meeting_id = ?, content = ?, create_user_id = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			officeWorkMeetingMinutes.getMeetingId(), officeWorkMeetingMinutes.getContent(), 
			officeWorkMeetingMinutes.getCreateUserId(), officeWorkMeetingMinutes.getCreateTime(), 
			officeWorkMeetingMinutes.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeWorkMeetingMinutes getOfficeWorkMeetingMinutesById(String id){
		String sql = "select * from office_work_meeting_minutes where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeWorkMeetingMinutes> getOfficeWorkMeetingMinutesMapByIds(String[] ids){
		String sql = "select * from office_work_meeting_minutes where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeWorkMeetingMinutes> getOfficeWorkMeetingMinutesList(){
		String sql = "select * from office_work_meeting_minutes";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeWorkMeetingMinutes> getOfficeWorkMeetingMinutesPage(Pagination page){
		String sql = "select * from office_work_meeting_minutes";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public OfficeWorkMeetingMinutes getOfficeWorkMeetingsByMeetId(String meetId) {
		String sql="select * from office_work_meeting_minutes where meeting_id = ? ";
		return query(sql, new Object[]{meetId},new SingleRow());
	}
}

