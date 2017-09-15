package net.zdsoft.office.dutyinformation.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.dutyinformation.entity.OfficeDutyInformationSet;
import net.zdsoft.office.dutyinformation.dao.OfficeDutyInformationSetDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_information_set
 * @author 
 * 
 */
public class OfficeDutyInformationSetDaoImpl extends BaseDao<OfficeDutyInformationSet> implements OfficeDutyInformationSetDao{

	@Override
	public OfficeDutyInformationSet setField(ResultSet rs) throws SQLException{
		OfficeDutyInformationSet officeDutyInformationSet = new OfficeDutyInformationSet();
		officeDutyInformationSet.setId(rs.getString("id"));
		officeDutyInformationSet.setUnitId(rs.getString("unit_id"));
		officeDutyInformationSet.setCreateUserId(rs.getString("create_user_id"));
		officeDutyInformationSet.setAreaId(rs.getString("area_id"));
		officeDutyInformationSet.setCreateTime(rs.getTimestamp("create_time"));
		officeDutyInformationSet.setDutyName(rs.getString("duty_name"));
		officeDutyInformationSet.setDutyStartTime(rs.getTimestamp("duty_start_time"));
		officeDutyInformationSet.setDutyEndTime(rs.getTimestamp("duty_end_time"));
		officeDutyInformationSet.setRegistrationStartTime(rs.getTimestamp("registration_start_time"));
		officeDutyInformationSet.setRegistrationEndTime(rs.getTimestamp("registration_end_time"));
		officeDutyInformationSet.setType(rs.getString("type"));
		officeDutyInformationSet.setYear(rs.getString("year"));
		officeDutyInformationSet.setInstruction(rs.getString("instruction"));
		return officeDutyInformationSet;
	}

	@Override
	public OfficeDutyInformationSet save(OfficeDutyInformationSet officeDutyInformationSet){
		String sql = "insert into office_duty_information_set(id, unit_id, create_user_id, area_id, create_time, duty_name, duty_start_time, duty_end_time, registration_start_time, registration_end_time, type,year, instruction) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeDutyInformationSet.getId())){
			officeDutyInformationSet.setId(createId());
		}
		Object[] args = new Object[]{
			officeDutyInformationSet.getId(), officeDutyInformationSet.getUnitId(), 
			officeDutyInformationSet.getCreateUserId(), officeDutyInformationSet.getAreaId(), 
			officeDutyInformationSet.getCreateTime(), officeDutyInformationSet.getDutyName(), 
			officeDutyInformationSet.getDutyStartTime(), officeDutyInformationSet.getDutyEndTime(), 
			officeDutyInformationSet.getRegistrationStartTime(), officeDutyInformationSet.getRegistrationEndTime(), 
			officeDutyInformationSet.getType(),officeDutyInformationSet.getYear(), officeDutyInformationSet.getInstruction()
		};
		update(sql, args);
		return officeDutyInformationSet;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_duty_information_set where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeDutyInformationSet officeDutyInformationSet){
		String sql = "update office_duty_information_set set unit_id = ?, create_user_id = ?, area_id = ?, create_time = ?, duty_name = ?, duty_start_time = ?, duty_end_time = ?, registration_start_time = ?, registration_end_time = ?, type = ?,year=?, instruction = ? where id = ?";
		Object[] args = new Object[]{
			officeDutyInformationSet.getUnitId(), officeDutyInformationSet.getCreateUserId(), 
			officeDutyInformationSet.getAreaId(), officeDutyInformationSet.getCreateTime(), 
			officeDutyInformationSet.getDutyName(), officeDutyInformationSet.getDutyStartTime(), 
			officeDutyInformationSet.getDutyEndTime(), officeDutyInformationSet.getRegistrationStartTime(), 
			officeDutyInformationSet.getRegistrationEndTime(), officeDutyInformationSet.getType(), officeDutyInformationSet.getYear(),
			officeDutyInformationSet.getInstruction(), officeDutyInformationSet.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeDutyInformationSet getOfficeDutyInformationSetById(String id){
		String sql = "select * from office_duty_information_set where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeDutyInformationSet> getOfficeDutyInformationSetMapByIds(String[] ids){
		String sql = "select * from office_duty_information_set where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetList(){
		String sql = "select * from office_duty_information_set";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetPage(Pagination page){
		String sql = "select * from office_duty_information_set";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetByUnitIdList(String unitId){
		String sql = "select * from office_duty_information_set where unit_id = ? order by create_time desc";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_duty_information_set where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetsByUnitIdName(
			String unitId,String year, String dutyName, Pagination page) {
		List<Object> args=new ArrayList<Object>();
		StringBuffer sb=new StringBuffer("select * from office_duty_information_set where unit_id = ?");
		args.add(unitId);
		if(StringUtils.isNotBlank(year)){
			sb.append(" and year=?");
			args.add(year);
		}
		if(StringUtils.isNotBlank(dutyName)){
			sb.append(" and duty_name like '%"+dutyName+"%'");
		}
		sb.append(" order by duty_start_time desc");
		if(page!=null){
			return query(sb.toString(), args.toArray(), new MultiRow(), page);
		}else{
			return query(sb.toString(), args.toArray(), new MultiRow());
		}
	}

	@Override
	public boolean isExistConflict(String unitId, String dutyName, String dutyId) {
		String sql1="select count(*) from office_duty_information_set where unit_id = ? and duty_name=?";
		String sql2="select count(*) from office_duty_information_set where unit_id = ? and duty_name=? and id<>?";
		int i=0;
		if(StringUtils.isNotBlank(dutyId)){
			i=queryForInt(sql2, new String[]{unitId,dutyName,dutyId});
		}else{
			i=queryForInt(sql1, new String[]{unitId,dutyName});
		}
		if(i>0){
			return true;	
		}
		return false;
	}

	@Override
	public List<OfficeDutyInformationSet> getOfficeDutyInformationSetsByIds(
			String[] ids) {
		String sql = "select * from office_duty_information_set where id in";
		return queryForInSQL(sql, null, ids, new MultiRow());
	}
	
}
