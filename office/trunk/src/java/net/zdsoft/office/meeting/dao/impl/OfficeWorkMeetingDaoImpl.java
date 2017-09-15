package net.zdsoft.office.meeting.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeWorkMeetingDao;
import net.zdsoft.office.meeting.entity.MeetingsInfoCondition;
import net.zdsoft.office.meeting.entity.OfficeWorkMeeting;

import org.apache.commons.lang3.StringUtils;
/**
 * office_work_meeting
 * @author 
 * 
 */
public class OfficeWorkMeetingDaoImpl extends BaseDao<OfficeWorkMeeting> implements OfficeWorkMeetingDao{

	@Override
	public OfficeWorkMeeting setField(ResultSet rs) throws SQLException{
		OfficeWorkMeeting officeWorkMeeting = new OfficeWorkMeeting();
		officeWorkMeeting.setId(rs.getString("id"));
		officeWorkMeeting.setUnitId(rs.getString("unit_id"));
		officeWorkMeeting.setName(rs.getString("name"));
		officeWorkMeeting.setMeetingDate(rs.getTimestamp("meeting_date"));
		officeWorkMeeting.setDays(rs.getFloat("days"));
		officeWorkMeeting.setPlace(rs.getString("place"));
		officeWorkMeeting.setOtherPersons(rs.getString("other_persons"));
		officeWorkMeeting.setForecastNumber(rs.getInt("forecast_number"));
		officeWorkMeeting.setRemark(rs.getString("remark"));
		officeWorkMeeting.setMinutesPeople(rs.getString("minutes_people"));
		officeWorkMeeting.setState(rs.getInt("state"));
		officeWorkMeeting.setIsPublish(rs.getBoolean("is_publish"));
		officeWorkMeeting.setIsDeleted(rs.getBoolean("is_deleted"));
		officeWorkMeeting.setType(rs.getString("type"));
		officeWorkMeeting.setAuditIdea(rs.getString("audit_idea"));
		officeWorkMeeting.setCreateUserId(rs.getString("create_user_id"));
		officeWorkMeeting.setCreateTime(rs.getTimestamp("create_time"));
		officeWorkMeeting.setAuditUserId(rs.getString("audit_user_id"));
		officeWorkMeeting.setAuditTime(rs.getTimestamp("audit_time"));
		return officeWorkMeeting;
	}

	@Override
	public OfficeWorkMeeting save(OfficeWorkMeeting officeWorkMeeting){
		String sql = "insert into office_work_meeting(id, unit_id, name, meeting_date, days, place, other_persons, forecast_number, remark, minutes_people, state, is_publish, is_deleted, type, audit_idea, create_user_id, create_time, audit_user_id, audit_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeWorkMeeting.getId())){
			officeWorkMeeting.setId(createId());
		}
		Object[] args = new Object[]{
			officeWorkMeeting.getId(), officeWorkMeeting.getUnitId(), 
			officeWorkMeeting.getName(), officeWorkMeeting.getMeetingDate(), 
			officeWorkMeeting.getDays(), officeWorkMeeting.getPlace(), 
			officeWorkMeeting.getOtherPersons(), officeWorkMeeting.getForecastNumber(), 
			officeWorkMeeting.getRemark(), officeWorkMeeting.getMinutesPeople(), 
			officeWorkMeeting.getState(), officeWorkMeeting.getIsPublish(), 
			officeWorkMeeting.getIsDeleted(), officeWorkMeeting.getType(), 
			officeWorkMeeting.getAuditIdea(), officeWorkMeeting.getCreateUserId(), 
			officeWorkMeeting.getCreateTime(), officeWorkMeeting.getAuditUserId(), 
			officeWorkMeeting.getAuditTime()
		};
		update(sql, args);
		return officeWorkMeeting;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "update office_work_meeting set is_deleted = 1 where id in";
		return updateForInSQL(sql, null, ids);
	}
	@Override
	public void submitMeeting(String id) {
		String sql = "update office_work_meeting set state = 2 where id = ?";
		update(sql, new Object[]{id});
	}
	
	@Override
	public void publishMeeting(String id) {
		String sql = "update office_work_meeting set is_publish = 1 where id = ?";
		update(sql, new Object[]{id});
	}
	@Override
	public void updateAudit(OfficeWorkMeeting officeWorkMeeting) {
		String sql = "update office_work_meeting set is_publish = 0, state = ?, type = ?, audit_idea = ?,  audit_user_id = ?, audit_time = ? where id = ?";
		Object[] args = new Object[]{
				officeWorkMeeting.getState(), 
				officeWorkMeeting.getType(), officeWorkMeeting.getAuditIdea(), 
				officeWorkMeeting.getAuditUserId(), officeWorkMeeting.getAuditTime(), 
				officeWorkMeeting.getId()
			};
			update(sql, args);
	}
	@Override
	public Integer update(OfficeWorkMeeting officeWorkMeeting){
		String sql = "update office_work_meeting set unit_id = ?, name = ?, meeting_date = ?, days = ?, place = ?, other_persons = ?, forecast_number = ?, remark = ?, minutes_people = ?, state = ?, is_publish = ?, is_deleted = ?, type = ?, audit_idea = ?, create_user_id = ?, create_time = ?, audit_user_id = ?, audit_time = ? where id = ?";
		Object[] args = new Object[]{
			officeWorkMeeting.getUnitId(), officeWorkMeeting.getName(), 
			officeWorkMeeting.getMeetingDate(), officeWorkMeeting.getDays(), 
			officeWorkMeeting.getPlace(), officeWorkMeeting.getOtherPersons(), 
			officeWorkMeeting.getForecastNumber(), officeWorkMeeting.getRemark(), 
			officeWorkMeeting.getMinutesPeople(), officeWorkMeeting.getState(), 
			officeWorkMeeting.getIsPublish(), officeWorkMeeting.getIsDeleted(), 
			officeWorkMeeting.getType(), officeWorkMeeting.getAuditIdea(), 
			officeWorkMeeting.getCreateUserId(), officeWorkMeeting.getCreateTime(), 
			officeWorkMeeting.getAuditUserId(), officeWorkMeeting.getAuditTime(), 
			officeWorkMeeting.getId()
		};
		return update(sql, args);
	}
	
	public void updateEdit(OfficeWorkMeeting officeWorkMeeting){
		String sql = "update office_work_meeting set unit_id = ?, name = ?, meeting_date = ?, days = ?, place = ?, other_persons = ?, forecast_number = ?, remark = ?, minutes_people = ?, state = ?, is_publish = ?, is_deleted = ? where id = ?";
		Object[] args = new Object[]{
			officeWorkMeeting.getUnitId(), officeWorkMeeting.getName(), 
			officeWorkMeeting.getMeetingDate(), officeWorkMeeting.getDays(), 
			officeWorkMeeting.getPlace(), officeWorkMeeting.getOtherPersons(), 
			officeWorkMeeting.getForecastNumber(), officeWorkMeeting.getRemark(), 
			officeWorkMeeting.getMinutesPeople(), officeWorkMeeting.getState(), 
			officeWorkMeeting.getIsPublish(), officeWorkMeeting.getIsDeleted(), 
			officeWorkMeeting.getId()
		};
		update(sql, args);
	}
	
	@Override
	public OfficeWorkMeeting getOfficeWorkMeetingById(String id){
		String sql = "select * from office_work_meeting where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeWorkMeeting> getOfficeWorkMeetingMapByIds(String[] ids){
		String sql = "select * from office_work_meeting where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeWorkMeeting> getOfficeWorkMeetingList(){
		String sql = "select * from office_work_meeting";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeWorkMeeting> getOfficeWorkMeetingPage(Pagination page){
		String sql = "select * from office_work_meeting";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeWorkMeeting> getOfficeWorkMeetingByUnitIdList(String unitId){
		String sql = "select * from office_work_meeting where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeWorkMeeting> getOfficeWorkMeetingByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_work_meeting where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeWorkMeeting> getMeetingInfoPage(
			MeetingsInfoCondition mic, Pagination page) {

		StringBuffer sql = new StringBuffer("select * from office_work_meeting  where  is_deleted = 0 and unit_id = ?");
		List<Object> args = new ArrayList<Object>();
		args.add(mic.getUnitId());
		if(StringUtils.isNotBlank(mic.getUserId())){
			sql.append("and create_user_id = ? ");
			args.add(mic.getUserId());
		}
		if(StringUtils.isNotBlank(mic.getAuditState())){
			if(mic.getAuditState().equals("-1")){
				sql.append(" and state > 1 ");
			}else{
				if(!mic.getAuditState().equals("0")){
					sql.append(" and state = ? ");
					args.add(mic.getAuditState());
				}
			}
		}
		if(StringUtils.isNotBlank(mic.getMeetingName())){
			sql.append(" and name like ? ");
			args.add("%"+mic.getMeetingName()+"%");
		}
		if(mic.getStartDate() != null){
			sql.append(" and meeting_date >= ? ");
			args.add(mic.getStartDate());
		}
		if(mic.getEndDate() != null){
			sql.append(" and meeting_date <= ? ");
			args.add(mic.getEndDate());
		}
		sql.append(" order by state, is_publish, meeting_date desc ");
		return query(sql.toString(),args.toArray(new Object[0]),new MultiRow(), page);
	}

	@Override
	public List<OfficeWorkMeeting> getOfficeWorkMeetingListBySearchParams(
			String unitId, String meetName, Date startTime, Date endTime,
			Pagination page) {
		String sql="select * from office_work_meeting where is_deleted = 0 and is_publish = 1 and state = 3 and unit_id = ?";
		List<Object> argList=new ArrayList<Object>();
		argList.add(unitId);
		if(StringUtils.isNotBlank(meetName)){
			meetName="%"+meetName+"%";
			sql+=" and name like ? ";
			argList.add(meetName);
		}
		if(startTime!=null){
			sql+=" and meeting_date >= ? ";
			argList.add(startTime);
		}
		if(endTime!=null){
			sql+=" and meeting_date <= ? ";
			argList.add(endTime);
		}
		sql+=" order by meeting_date desc";
		return query(sql, argList.toArray(new Object[0]), new MultiRow(), page);
	}

	
	@Override
	public List<OfficeWorkMeeting> getMeetingsInfoList(String unitId,
			String isEnd, String meetingName, Date startTime, Date endTime,
			String[] ids, Pagination page) {
		String s = "select * from office_work_meeting where is_deleted = 0 and is_publish = 1 and unit_id = ? ";
		StringBuffer sql = new StringBuffer(s);
		List<Object> arr = new ArrayList<Object>();
		arr.add(unitId);
		if(StringUtils.isNotBlank(isEnd)){
			if(StringUtils.equals(isEnd, OfficeWorkMeeting.HAS_END)){
				sql.append(" and to_char(meeting_date,'yyyy-MM-dd HH24:mi') < to_char(sysdate,'yyyy-MM-dd HH24:mi')");
			}else if(StringUtils.equals(isEnd, OfficeWorkMeeting.NOT_END)){
				sql.append(" and to_char(meeting_date,'yyyy-MM-dd HH24:mi') >= to_char(sysdate,'yyyy-MM-dd HH24:mi')");
			}
		}
		if(startTime != null){
			sql.append(" and meeting_date >= ? ");
			arr.add(startTime);
		}
		if(endTime != null){
			sql.append(" and meeting_date <= ? ");
			arr.add(endTime);
		}
		if(StringUtils.isNotBlank(meetingName)){
			sql.append(" and name like ? ");
			arr.add("%"+meetingName+"%");
		}
		sql.append(" and id in ");
		return queryForInSQL(sql.toString(), arr.toArray(), ids, new MultiRow(), " order by meeting_date desc ",page);
	}

	@Override
	public List<OfficeWorkMeeting> getOfficeWorkMeetingManageListByParams(
			String unitId, String userId, String meetName, Pagination page) {
		StringBuilder sql=new StringBuilder("select * from office_work_meeting where is_deleted = 0 and is_publish = 1 and unit_id = ? and minutes_people = ? ");
		List<Object> argList=new ArrayList<Object>();
		argList.add(unitId);
		argList.add(userId);
		if(StringUtils.isNotBlank(meetName)){
			sql.append(" and name like ? ");
			argList.add("%"+meetName+"%");
		}
		sql.append("order by meeting_date desc ");
		return query(sql.toString(), argList.toArray(new Object[0]), new MultiRow(),page);
	}
	
}