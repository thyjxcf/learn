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
import net.zdsoft.office.meeting.dao.OfficeExecutiveMeetAttendDao;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMeetAttend;

import org.apache.commons.lang.StringUtils;
/**
 * office_executive_meet_attend
 * @author 
 * 
 */
public class OfficeExecutiveMeetAttendDaoImpl extends BaseDao<OfficeExecutiveMeetAttend> implements OfficeExecutiveMeetAttendDao{

	@Override
	public OfficeExecutiveMeetAttend setField(ResultSet rs) throws SQLException{
		OfficeExecutiveMeetAttend officeExecutiveMeetAttend = new OfficeExecutiveMeetAttend();
		officeExecutiveMeetAttend.setId(rs.getString("id"));
		officeExecutiveMeetAttend.setMeetingId(rs.getString("meeting_id"));
		officeExecutiveMeetAttend.setType(rs.getInt("type"));
		officeExecutiveMeetAttend.setObjectId(rs.getString("object_id"));
		officeExecutiveMeetAttend.setUnitId(rs.getString("unit_id"));
		return officeExecutiveMeetAttend;
	}

	@Override
	public OfficeExecutiveMeetAttend save(OfficeExecutiveMeetAttend officeExecutiveMeetAttend){
		String sql = "insert into office_executive_meet_attend(id, meeting_id, type, object_id, unit_id) values(?,?,?,?,?)";
		if (StringUtils.isBlank(officeExecutiveMeetAttend.getId())){
			officeExecutiveMeetAttend.setId(createId());
		}
		Object[] args = new Object[]{
			officeExecutiveMeetAttend.getId(), officeExecutiveMeetAttend.getMeetingId(), 
			officeExecutiveMeetAttend.getType(), officeExecutiveMeetAttend.getObjectId(),
			officeExecutiveMeetAttend.getUnitId()
		};
		update(sql, args);
		return officeExecutiveMeetAttend;
	}
	
	@Override
	public void batchSave(List<OfficeExecutiveMeetAttend> list) {
		String sql = "insert into office_executive_meet_attend(id, meeting_id, type, object_id, unit_id) values(?,?,?,?,?)";
		List<Object[]> objs = new ArrayList<Object[]>();
		for(OfficeExecutiveMeetAttend officeExecutiveMeetAttend:list){
			if (StringUtils.isBlank(officeExecutiveMeetAttend.getId())){
				officeExecutiveMeetAttend.setId(createId());
			}
			Object[] args = new Object[]{
				officeExecutiveMeetAttend.getId(), officeExecutiveMeetAttend.getMeetingId(), 
				officeExecutiveMeetAttend.getType(), officeExecutiveMeetAttend.getObjectId(),
				officeExecutiveMeetAttend.getUnitId()
			};
			objs.add(args);
		}
		batchUpdate(sql, objs, new int[] {Types.CHAR, Types.CHAR, Types.INTEGER, Types.CHAR, Types.CHAR});
	}
	
	@Override
	public void deleteByMeetId(String meetId) {
		String sql = "delete from office_executive_meet_attend where meeting_id = ? ";
		update(sql, new Object[]{meetId});
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_executive_meet_attend where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeExecutiveMeetAttend officeExecutiveMeetAttend){
		String sql = "update office_executive_meet_attend set meeting_id = ?, type = ?, object_id = ? where id = ?";
		Object[] args = new Object[]{
			officeExecutiveMeetAttend.getMeetingId(), officeExecutiveMeetAttend.getType(), 
			officeExecutiveMeetAttend.getObjectId(), officeExecutiveMeetAttend.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeExecutiveMeetAttend getOfficeExecutiveMeetAttendById(String id){
		String sql = "select * from office_executive_meet_attend where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeExecutiveMeetAttend> getOfficeExecutiveMeetAttendMapByIds(String[] ids){
		String sql = "select * from office_executive_meet_attend where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeExecutiveMeetAttend> getOfficeExecutiveMeetAttendList(String unitId, String[] meetIds){
		String sql = "select * from office_executive_meet_attend where unit_id = ? and meeting_id in ";
		return queryForInSQL(sql, new Object[]{unitId}, meetIds, new MultiRow());
	}

	@Override
	public List<OfficeExecutiveMeetAttend> getOfficeExecutiveMeetAttendPage(Pagination page){
		String sql = "select * from office_executive_meet_attend";
		return query(sql, new MultiRow(), page);
	}
	
	@Override
	public List<String> getMeetIds(String unitId, String[] objIds) {
		String sql = "select meeting_id from office_executive_meet_attend where unit_id = ? and object_id in ";
		return queryForInSQL(sql, new Object[]{unitId}, objIds, new MultiRowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("meeting_id");
			}
		});
	}
}