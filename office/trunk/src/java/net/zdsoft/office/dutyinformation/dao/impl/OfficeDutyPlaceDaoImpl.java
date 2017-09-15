package net.zdsoft.office.dutyinformation.dao.impl;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.dutyinformation.entity.OfficeDutyPlace;
import net.zdsoft.office.dutyinformation.dao.OfficeDutyPlaceDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_place
 * @author 
 * 
 */
public class OfficeDutyPlaceDaoImpl extends BaseDao<OfficeDutyPlace> implements OfficeDutyPlaceDao{

	@Override
	public OfficeDutyPlace setField(ResultSet rs) throws SQLException{
		OfficeDutyPlace officeDutyPlace = new OfficeDutyPlace();
		officeDutyPlace.setId(rs.getString("id"));
		officeDutyPlace.setUnitId(rs.getString("unit_id"));
		officeDutyPlace.setDutyApplyId(rs.getString("duty_apply_id"));
		officeDutyPlace.setPatrolPlaceId(rs.getString("patrol_place_id"));
		officeDutyPlace.setDutyTime(rs.getTimestamp("duty_time"));
		officeDutyPlace.setDutyUserId(rs.getString("duty_user_id"));
		officeDutyPlace.setPatrolContent(rs.getString("patrol_content"));
		officeDutyPlace.setPatrolTime(rs.getString("patrol_time"));
		officeDutyPlace.setCreateTime(rs.getTimestamp("create_time"));
		return officeDutyPlace;
	}

	@Override
	public OfficeDutyPlace save(OfficeDutyPlace officeDutyPlace){
		String sql = "insert into office_duty_place(id, unit_id, duty_apply_id, patrol_place_id, duty_time, duty_user_id, patrol_content, patrol_time, create_time) values(?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeDutyPlace.getId())){
			officeDutyPlace.setId(createId());
		}
		Object[] args = new Object[]{
			officeDutyPlace.getId(), officeDutyPlace.getUnitId(), 
			officeDutyPlace.getDutyApplyId(), officeDutyPlace.getPatrolPlaceId(), 
			officeDutyPlace.getDutyTime(), officeDutyPlace.getDutyUserId(), 
			officeDutyPlace.getPatrolContent(), officeDutyPlace.getPatrolTime(), 
			officeDutyPlace.getCreateTime()
		};
		update(sql, args);
		return officeDutyPlace;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_duty_place where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeDutyPlace officeDutyPlace){
		String sql = "update office_duty_place set unit_id = ?, duty_apply_id = ?, patrol_place_id = ?, duty_time = ?, duty_user_id = ?, patrol_content = ?, patrol_time = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			officeDutyPlace.getUnitId(), officeDutyPlace.getDutyApplyId(), 
			officeDutyPlace.getPatrolPlaceId(), officeDutyPlace.getDutyTime(), 
			officeDutyPlace.getDutyUserId(), officeDutyPlace.getPatrolContent(), 
			officeDutyPlace.getPatrolTime(), officeDutyPlace.getCreateTime(), 
			officeDutyPlace.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeDutyPlace getOfficeDutyPlaceById(String id){
		String sql = "select * from office_duty_place where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeDutyPlace> getOfficeDutyPlaceMapByIds(String[] ids){
		String sql = "select * from office_duty_place where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeDutyPlace> getOfficeDutyPlaceList(){
		String sql = "select * from office_duty_place";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeDutyPlace> getOfficeDutyPlacePage(Pagination page){
		String sql = "select * from office_duty_place";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeDutyPlace> getOfficeDutyPlaceByUnitIdList(String unitId){
		String sql = "select * from office_duty_place where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeDutyPlace> getOfficeDutyPlaceByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_duty_place where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public void batchSave(List<OfficeDutyPlace> officeDutyPlaces) {
		String sql = "insert into office_duty_place(id, unit_id, duty_apply_id, patrol_place_id, duty_time, duty_user_id, patrol_content, patrol_time, create_time) values(?,?,?,?,?,?,?,?,?)";
		List<Object[]> args=new ArrayList<Object[]>();
		for (OfficeDutyPlace officeDutyPlace : officeDutyPlaces) {
			if(StringUtils.isBlank(officeDutyPlace.getId())){
				officeDutyPlace.setId(getGUID());
			}
			Object[] obs=new Object[]{officeDutyPlace.getId(),officeDutyPlace.getUnitId(),officeDutyPlace.getDutyApplyId(),
					officeDutyPlace.getPatrolPlaceId(),officeDutyPlace.getDutyTime(),officeDutyPlace.getDutyUserId(),officeDutyPlace.getPatrolContent(),
					officeDutyPlace.getPatrolTime(),officeDutyPlace.getCreateTime()};
			args.add(obs);
		}
		int[] intTypes=new int[]{Types.CHAR,Types.CHAR,Types.CHAR,Types.CHAR,
				Types.DATE,Types.CHAR,Types.VARCHAR,Types.VARCHAR,Types.DATE};
		batchUpdate(sql, args, intTypes);
	}

	@Override
	public void batchUpdate(List<OfficeDutyPlace> officeDutyPlaces) {
		String sql = "update office_duty_place set  patrol_content = ?, patrol_time = ? where id = ?";
		List<Object[]> args=new ArrayList<Object[]>();
		for (OfficeDutyPlace officeDutyPlace : officeDutyPlaces) {
			Object[] obs=new Object[]{officeDutyPlace.getPatrolContent(),
					officeDutyPlace.getPatrolTime(),officeDutyPlace.getId()};
			args.add(obs);
		}
		int[] intTypes=new int[]{Types.VARCHAR,Types.VARCHAR,Types.CHAR};
		batchUpdate(sql, args, intTypes);
	}

	@Override
	public List<OfficeDutyPlace> getOfficeDutyPlacesByUnitIdAndUserId(
			String unitId, String userId, String dutyApplyId,Date dutyTime) {
		String sql="select * from office_duty_place where unit_id = ? and duty_apply_id=? and duty_time=? and duty_user_id=?";
		return query(sql, new Object[]{unitId,dutyApplyId,dutyTime,userId}, new MultiRow());
	}

	@Override
	public Map<String,OfficeDutyPlace> getOfficeDutyPlacesByUnitIdAndOthers(
			String unitId, String setId, Date startTime, Date endTime) {
		List<Object> args=new ArrayList<Object>();
		StringBuffer sb=new StringBuffer("select * from office_duty_place where unit_id = ?");
		args.add(unitId);
		if(StringUtils.isNotBlank(setId)){
			sb.append(" and duty_apply_id=?");
			args.add(setId);
		}
		if(startTime!=null){
			sb.append(" and to_date(to_char(duty_time,'yyyy-MM-dd'),'yyyy-MM-dd')>=?");
			args.add(startTime);
		}
		if(endTime!=null){
			sb.append(" and to_date(to_char(duty_time,'yyyy-MM-dd'),'yyyy-MM-dd')<=?");
			args.add(endTime);
		}
		return queryForMap(sb.toString(), args.toArray(), new MapRowMapper<String, OfficeDutyPlace>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				Date dutyTime=rs.getTimestamp("duty_time");
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");
				String dutyTimeStr=sdf.format(dutyTime);
				return dutyTimeStr+"_"+rs.getString("patrol_place_id")+"_"+rs.getString("duty_user_id");
			}

			@Override
			public OfficeDutyPlace mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public List<OfficeDutyPlace> getOfficeDMap(String unitId,
			String setId) {
		List<Object> args=new ArrayList<Object>();
		StringBuffer sb=new StringBuffer("select * from office_duty_place where unit_id = ?");
		args.add(unitId);
		if(StringUtils.isNotBlank(setId)){
			sb.append(" and duty_apply_id=?");
			args.add(setId);
		}
		return query(sb.toString(), args.toArray(), new MultiRow());
	}
	
}
