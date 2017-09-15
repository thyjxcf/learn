package net.zdsoft.office.meeting.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeWorkMeetingConfirmDao;
import net.zdsoft.office.meeting.entity.OfficeWorkMeetingConfirm;

import org.apache.commons.lang3.StringUtils;
/**
 * office_work_meeting_confirm
 * @author 
 * 
 */
public class OfficeWorkMeetingConfirmDaoImpl extends BaseDao<OfficeWorkMeetingConfirm> implements OfficeWorkMeetingConfirmDao{

	@Override
	public OfficeWorkMeetingConfirm setField(ResultSet rs) throws SQLException{
		OfficeWorkMeetingConfirm officeWorkMeetingConfirm = new OfficeWorkMeetingConfirm();
		officeWorkMeetingConfirm.setId(rs.getString("id"));
		officeWorkMeetingConfirm.setMeetingId(rs.getString("meeting_id"));
		officeWorkMeetingConfirm.setAttendUserId(rs.getString("attend_user_id"));
		officeWorkMeetingConfirm.setTransferUserId(rs.getString("transfer_user_id"));
		officeWorkMeetingConfirm.setAttendType(rs.getInt("attend_type"));
		officeWorkMeetingConfirm.setCreateTime(rs.getTimestamp("create_time"));
		officeWorkMeetingConfirm.setRemark(rs.getString("remark"));
		return officeWorkMeetingConfirm;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getTransferMapByMeetingsId(String meetingId) {
		String sql = "select * from office_work_meeting_confirm where meeting_id = ? and transfer_user_id is not null ";
		return queryForMap(sql, new Object[]{meetingId}, new MapRowMapper() {
			@Override
			public String mapRowValue(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("attend_user_id");
			}
			
			@Override
			public String mapRowKey(ResultSet rs, int arg1) throws SQLException {
				return  rs.getString("transfer_user_id");
			}
		});
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, OfficeWorkMeetingConfirm> getMeetingConfirmMapByMeetingId(
			String meetingId) {
		String sql = "select * from office_work_meeting_confirm where meeting_id = ? ";
		return queryForMap(sql, new Object[]{meetingId}, new MapRowMapper() {
			@Override
			public OfficeWorkMeetingConfirm mapRowValue(ResultSet rs, int arg1) throws SQLException {
				return setField(rs);
			}
			
			@Override
			public String mapRowKey(ResultSet rs, int arg1) throws SQLException {
				return  rs.getString("attend_user_id");
			}
		});
	
	}
	
	@Override
	public boolean isTransfered(String meetingId, String transferUserId) {
		String sql = "select count(1) from office_work_meeting_confirm where meeting_id = ? and transfer_user_id = ? ";
		int i = queryForInt(sql,new Object[]{meetingId,transferUserId});
		if(i>0){
			return true;
		}
		return false;
	}
	@Override
	public boolean isExist(String meetingId,
			String attentUserId) {
		String sql = "select count(1) from office_work_meeting_confirm where meeting_id = ? and attend_user_id = ? ";
		int i = queryForInt(sql,new Object[]{meetingId,attentUserId});
		if(i>0){
			return true;
		}
		return false;
	}
	@Override
	public String getConfirmId(String meetingId, String attendUserId) {
		String sql = "select * from office_work_meeting_confirm where meeting_id = ? and attend_user_id = ? ";
		return query(sql, new Object[]{meetingId,attendUserId},new SingleRow()).getId();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, OfficeWorkMeetingConfirm> getMeetingsConfirmMapByMeetingsId(
			String meetingId) {
		String sql = "select * from office_work_meeting_confirm where meeting_id = ? ";
		return queryForMap(sql, new Object[]{meetingId}, new MapRowMapper<String,OfficeWorkMeetingConfirm>() {
			@Override
			public OfficeWorkMeetingConfirm mapRowValue(ResultSet rs, int arg1) throws SQLException {
				return setField(rs);
			}
			
			@Override
			public String mapRowKey(ResultSet rs, int arg1) throws SQLException {
				return  rs.getString("attend_user_id");
			}
		});
	}
	@Override
	public OfficeWorkMeetingConfirm save(OfficeWorkMeetingConfirm officeWorkMeetingConfirm){
		String sql = "insert into office_work_meeting_confirm(id, meeting_id, attend_user_id, transfer_user_id, attend_type, create_time, remark) values(?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeWorkMeetingConfirm.getId())){
			officeWorkMeetingConfirm.setId(createId());
		}
		Object[] args = new Object[]{
			officeWorkMeetingConfirm.getId(), officeWorkMeetingConfirm.getMeetingId(), 
			officeWorkMeetingConfirm.getAttendUserId(), officeWorkMeetingConfirm.getTransferUserId(), 
			officeWorkMeetingConfirm.getAttendType(), officeWorkMeetingConfirm.getCreateTime(), 
			officeWorkMeetingConfirm.getRemark()
		};
		update(sql, args);
		return officeWorkMeetingConfirm;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_work_meeting_confirm where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeWorkMeetingConfirm officeWorkMeetingConfirm){
		String sql = "update office_work_meeting_confirm set meeting_id = ?, attend_user_id = ?, transfer_user_id = ?, attend_type = ?, create_time = ?, remark = ? where id = ?";
		Object[] args = new Object[]{
			officeWorkMeetingConfirm.getMeetingId(), officeWorkMeetingConfirm.getAttendUserId(), 
			officeWorkMeetingConfirm.getTransferUserId(), officeWorkMeetingConfirm.getAttendType(), 
			officeWorkMeetingConfirm.getCreateTime(), officeWorkMeetingConfirm.getRemark(), 
			officeWorkMeetingConfirm.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeWorkMeetingConfirm getOfficeWorkMeetingConfirmById(String id){
		String sql = "select * from office_work_meeting_confirm where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeWorkMeetingConfirm> getOfficeWorkMeetingConfirmMapByIds(String[] ids){
		String sql = "select * from office_work_meeting_confirm where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeWorkMeetingConfirm> getOfficeWorkMeetingConfirmList(){
		String sql = "select * from office_work_meeting_confirm";
		return query(sql, new MultiRow());
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String> getMeetingsIdByUserId(String userId) {
		String sql = "select distinct meeting_id from office_work_meeting_confirm where attend_user_id = ? or transfer_user_id = ? ";
		return query(sql, new Object[]{userId,userId}, new MultiRowMapper() {
			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("meeting_id");
			}
		});
	}
	@Override
	public List<OfficeWorkMeetingConfirm> getOfficeWorkMeetingConfirmPage(Pagination page){
		String sql = "select * from office_work_meeting_confirm";
		return query(sql, new MultiRow(), page);
	}
}
	