package net.zdsoft.office.meeting.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.meeting.dao.OfficeExecutiveMeetDao;
import net.zdsoft.office.meeting.entity.OfficeExecutiveMeet;

import org.apache.commons.lang.StringUtils;
/**
 * office_executive_meet
 * @author 
 * 
 */
public class OfficeExecutiveMeetDaoImpl extends BaseDao<OfficeExecutiveMeet> implements OfficeExecutiveMeetDao{

	@Override
	public OfficeExecutiveMeet setField(ResultSet rs) throws SQLException{
		OfficeExecutiveMeet officeExecutiveMeet = new OfficeExecutiveMeet();
		officeExecutiveMeet.setId(rs.getString("id"));
		officeExecutiveMeet.setName(rs.getString("name"));
		officeExecutiveMeet.setMeetDate(rs.getTimestamp("meet_date"));
		officeExecutiveMeet.setPlace(rs.getString("place"));
		officeExecutiveMeet.setState(rs.getInt("state"));
		officeExecutiveMeet.setCreateUserId(rs.getString("create_user_id"));
		officeExecutiveMeet.setCreateTime(rs.getTimestamp("create_time"));
		officeExecutiveMeet.setUnitId(rs.getString("unit_id"));
		return officeExecutiveMeet;
	}

	@Override
	public OfficeExecutiveMeet save(OfficeExecutiveMeet officeExecutiveMeet){
		String sql = "insert into office_executive_meet(id, name, meet_date, place, state, create_user_id, create_time, unit_id) values(?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeExecutiveMeet.getId())){
			officeExecutiveMeet.setId(createId());
		}
		Object[] args = new Object[]{
			officeExecutiveMeet.getId(), officeExecutiveMeet.getName(), 
			officeExecutiveMeet.getMeetDate(), officeExecutiveMeet.getPlace(), 
			officeExecutiveMeet.getState(), officeExecutiveMeet.getCreateUserId(), 
			officeExecutiveMeet.getCreateTime(), officeExecutiveMeet.getUnitId()
		};
		update(sql, args);
		return officeExecutiveMeet;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_executive_meet where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeExecutiveMeet officeExecutiveMeet){
		String sql = "update office_executive_meet set name = ?, meet_date = ?, place = ?, state = ? where id = ?";
		Object[] args = new Object[]{
			officeExecutiveMeet.getName(), officeExecutiveMeet.getMeetDate(), 
			officeExecutiveMeet.getPlace(), officeExecutiveMeet.getState(), 
			officeExecutiveMeet.getId()
		};
		return update(sql, args);
	}
	
	@Override
	public void publishMeet(String id) {
		String sql = "update office_executive_meet set state = 1 where id = ?";
		update(sql, new Object[]{id});
	}

	@Override
	public OfficeExecutiveMeet getOfficeExecutiveMeetById(String id){
		String sql = "select * from office_executive_meet where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeExecutiveMeet> getOfficeExecutiveMeetMapByIds(String[] ids){
		String sql = "select * from office_executive_meet where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetList(){
		String sql = "select * from office_executive_meet";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetPage(Pagination page){
		String sql = "select * from office_executive_meet";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetByUnitIdList(String unitId){
		String sql = "select * from office_executive_meet where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetByUnitIdPage(String unitId, String queryName, String startTime, String endTime, Integer state, Pagination page){
		StringBuffer sql = new StringBuffer("select * from office_executive_meet where unit_id = ?");
		List<Object> args = new ArrayList<Object>();
		args.add(unitId);
		if(StringUtils.isNotBlank(queryName)){
			sql.append(" and name like ?");
			args.add("%"+queryName+"%");
		}
		if(StringUtils.isNotBlank(startTime)){
			sql.append("and to_date(to_char(meet_date,'yyyy-MM-dd'),'yyyy-MM-dd')>=to_date(?,'yyyy-MM-dd')");
			args.add(startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append("and to_date(to_char(meet_date,'yyyy-MM-dd'),'yyyy-MM-dd')<=to_date(?,'yyyy-MM-dd')");
			args.add(endTime);
		}
		if(state!=null){
			sql.append(" and state = ?");
			args.add(state);
		}
		sql.append(" order by state asc, meet_date desc");
		return query(sql.toString(), args.toArray(), new MultiRow(), page);
	}
	
	@Override
	public List<OfficeExecutiveMeet> getOfficeExecutiveMeetOverduePage(
			String unitId, String queryName, String startTime, String endTime,
			Pagination page) {
		StringBuffer sql = new StringBuffer("select * from office_executive_meet where state = 1 and meet_date < sysdate and unit_id = ?");
		List<Object> args = new ArrayList<Object>();
		args.add(unitId);
		if(StringUtils.isNotBlank(queryName)){
			sql.append(" and name like ?");
			args.add("%"+queryName+"%");
		}
		if(StringUtils.isNotBlank(startTime)){
			sql.append("and to_date(to_char(meet_date,'yyyy-MM-dd'),'yyyy-MM-dd')>=to_date(?,'yyyy-MM-dd')");
			args.add(startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append("and to_date(to_char(meet_date,'yyyy-MM-dd'),'yyyy-MM-dd')<=to_date(?,'yyyy-MM-dd')");
			args.add(endTime);
		}
		sql.append(" order by meet_date desc");
		return query(sql.toString(), args.toArray(), new MultiRow(), page);
	}
	
}