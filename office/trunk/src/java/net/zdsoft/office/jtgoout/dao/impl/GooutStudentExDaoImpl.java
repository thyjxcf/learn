package net.zdsoft.office.jtgoout.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.jtgoout.entity.GooutStudentEx;
import net.zdsoft.office.jtgoout.dao.GooutStudentExDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goout_student
 * @author 
 * 
 */
public class GooutStudentExDaoImpl extends BaseDao<GooutStudentEx> implements GooutStudentExDao{

	@Override
	public GooutStudentEx setField(ResultSet rs) throws SQLException{
		GooutStudentEx gooutStudentEx = new GooutStudentEx();
		gooutStudentEx.setId(rs.getString("id"));
		gooutStudentEx.setUnitId(rs.getString("unit_id"));
		gooutStudentEx.setJtgooutId(rs.getString("jtgoout_id"));
		gooutStudentEx.setOrganize(rs.getString("organize"));
		gooutStudentEx.setActivityNumber(rs.getInt("activity_number"));
		gooutStudentEx.setPlace(rs.getString("place"));
		gooutStudentEx.setContent(rs.getString("content"));
		gooutStudentEx.setVehicle(rs.getString("vehicle"));
		gooutStudentEx.setIsDrivinglicence(rs.getBoolean("is_drivinglicence"));
		gooutStudentEx.setIsOrganization(rs.getBoolean("is_organization"));
		gooutStudentEx.setTraveUnit(rs.getString("trave_unit"));
		gooutStudentEx.setTraveLinkPerson(rs.getString("trave_link_person"));
		gooutStudentEx.setTraveLinkPhone(rs.getString("trave_link_phone"));
		gooutStudentEx.setIsInsurance(rs.getBoolean("is_insurance"));
		gooutStudentEx.setActivityLeaderId(rs.getString("activity_leader_id"));
		gooutStudentEx.setActivityLeaderPhone(rs.getString("activity_leader_phone"));
		gooutStudentEx.setLeadGroupId(rs.getString("lead_group_id"));
		gooutStudentEx.setLeadGroupPhone(rs.getString("lead_group_phone"));
		gooutStudentEx.setOtherTeacherId(rs.getString("other_teacher_id"));
		gooutStudentEx.setActivityIdeal(rs.getBoolean("activity_ideal"));
		gooutStudentEx.setSaftIdeal(rs.getBoolean("saft_ideal"));
		return gooutStudentEx;
	}

	@Override
	public GooutStudentEx save(GooutStudentEx gooutStudentEx){
		String sql = "insert into office_goout_student(id, unit_id, jtgoout_id, organize, activity_number, place, content, vehicle, is_drivinglicence, is_organization, trave_unit, trave_link_person, trave_link_phone, is_insurance, activity_leader_id, lead_group_id, other_teacher_id, activity_ideal, saft_ideal,activity_leader_phone,lead_group_phone) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(gooutStudentEx.getId())){
			gooutStudentEx.setId(createId());
		}
		Object[] args = new Object[]{
			gooutStudentEx.getId(), gooutStudentEx.getUnitId(), 
			gooutStudentEx.getJtgooutId(), gooutStudentEx.getOrganize(), 
			gooutStudentEx.getActivityNumber(), gooutStudentEx.getPlace(), 
			gooutStudentEx.getContent(), gooutStudentEx.getVehicle(), 
			gooutStudentEx.getIsDrivinglicence(), gooutStudentEx.getIsOrganization(), 
			gooutStudentEx.getTraveUnit(), gooutStudentEx.getTraveLinkPerson(), 
			gooutStudentEx.getTraveLinkPhone(), gooutStudentEx.getIsInsurance(), 
			gooutStudentEx.getActivityLeaderId(), gooutStudentEx.getLeadGroupId(), 
			gooutStudentEx.getOtherTeacherId(), gooutStudentEx.getActivityIdeal(), 
			gooutStudentEx.getSaftIdeal(),gooutStudentEx.getActivityLeaderPhone(),
			gooutStudentEx.getLeadGroupPhone()
		};
		update(sql, args);
		return gooutStudentEx;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_goout_student where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public void deleteByJtGoOutId(String jtGooutId) {
		String sql ="delete from office_goout_student where jtgoout_id=?";
		update(sql, jtGooutId);
	}

	@Override
	public Integer update(GooutStudentEx gooutStudentEx){
		String sql = "update office_goout_student set unit_id = ?, jtgoout_id = ?, organize = ?, activity_number = ?, place = ?, content = ?, vehicle = ?, is_drivinglicence = ?, is_organization = ?, trave_unit = ?, trave_link_person = ?, trave_link_phone = ?, is_insurance = ?, activity_leader_id = ?, lead_group_id = ?, other_teacher_id = ?, activity_ideal = ?, saft_ideal = ?,activity_leader_phone=?,lead_group_phone=? where id = ?";
		Object[] args = new Object[]{
			gooutStudentEx.getUnitId(), gooutStudentEx.getJtgooutId(), 
			gooutStudentEx.getOrganize(), gooutStudentEx.getActivityNumber(), 
			gooutStudentEx.getPlace(), gooutStudentEx.getContent(), 
			gooutStudentEx.getVehicle(), gooutStudentEx.getIsDrivinglicence(), 
			gooutStudentEx.getIsOrganization(), gooutStudentEx.getTraveUnit(), 
			gooutStudentEx.getTraveLinkPerson(), gooutStudentEx.getTraveLinkPhone(), 
			gooutStudentEx.getIsInsurance(), gooutStudentEx.getActivityLeaderId(), 
			gooutStudentEx.getLeadGroupId(), gooutStudentEx.getOtherTeacherId(), 
			gooutStudentEx.getActivityIdeal(), gooutStudentEx.getSaftIdeal(),
			gooutStudentEx.getActivityLeaderPhone(),gooutStudentEx.getLeadGroupPhone(),
			gooutStudentEx.getId()
		};
		return update(sql, args);
	}

	@Override
	public GooutStudentEx getGooutStudentExById(String id){
		String sql = "select * from office_goout_student where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, GooutStudentEx> getGooutStudentExMapByIds(String[] ids){
		String sql = "select * from office_goout_student where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<GooutStudentEx> getGooutStudentExListByjtId(String[] jtGoOutIds) {
		String sql="select * from office_goout_student where jtgoout_id in";
		return queryForInSQL(sql, null, jtGoOutIds, new MultiRow());
	}
	
	@Override
	public List<GooutStudentEx> getGooutStudentExList(){
		String sql = "select * from office_goout_student";
		return query(sql, new MultiRow());
	}

	@Override
	public List<GooutStudentEx> getGooutStudentExPage(Pagination page){
		String sql = "select * from office_goout_student";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<GooutStudentEx> getGooutStudentExByUnitIdList(String unitId){
		String sql = "select * from office_goout_student where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<GooutStudentEx> getGooutStudentExByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_goout_student where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public GooutStudentEx getGooutStudentExByJtGoOutId(String jtGoOutId) {
		String sql="select * from office_goout_student where jtgoout_id=?";
		return query(sql, new Object[]{jtGoOutId}, new SingleRow());
	}

	@Override
	public List<GooutStudentEx> getGooutStudentExByUnitIdJtids(String unitId,
			String[] jtIds) {
		// TODO Auto-generated method stub
		String sql = "select * from office_goout_student where unit_id = ? and jtgoout_id in ";
		return queryForInSQL(sql,new Object[]{unitId},jtIds,new MultiRow());
	}
}
