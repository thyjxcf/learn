package net.zdsoft.office.dutyinformation.dao.impl;

import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.dutyinformation.entity.OfficeDutyApply;
import net.zdsoft.office.dutyinformation.dao.OfficeDutyApplyDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_apply
 * @author 
 * 
 */
public class OfficeDutyApplyDaoImpl extends BaseDao<OfficeDutyApply> implements OfficeDutyApplyDao{

	@Override
	public OfficeDutyApply setField(ResultSet rs) throws SQLException{
		OfficeDutyApply officeDutyApply = new OfficeDutyApply();
		officeDutyApply.setId(rs.getString("id"));
		officeDutyApply.setUnitId(rs.getString("unit_id"));
		officeDutyApply.setDutyInformationId(rs.getString("duty_information_id"));
		officeDutyApply.setUserId(rs.getString("user_id"));
		officeDutyApply.setApplyDate(rs.getTimestamp("apply_date"));
		officeDutyApply.setType(rs.getString("type"));
		return officeDutyApply;
	}

	@Override
	public OfficeDutyApply save(OfficeDutyApply officeDutyApply){
		String sql = "insert into office_duty_apply(id, unit_id, duty_information_id, user_id, apply_date, type) values(?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeDutyApply.getId())){
			officeDutyApply.setId(createId());
		}
		Object[] args = new Object[]{
			officeDutyApply.getId(), officeDutyApply.getUnitId(), 
			officeDutyApply.getDutyInformationId(), officeDutyApply.getUserId(), 
			officeDutyApply.getApplyDate(), officeDutyApply.getType()
		};
		update(sql, args);
		return officeDutyApply;
	}

	@Override
	public void batchSaveApplyInfo(List<OfficeDutyApply> officeDutyApplies) {
		String sql="insert into office_duty_apply(id, unit_id, duty_information_id, user_id, apply_date, type) values(?,?,?,?,?,?)";
		List<Object[]> objects=new ArrayList<Object[]>();
		for (OfficeDutyApply officeDutyApply : officeDutyApplies) {
			if(StringUtils.isBlank(officeDutyApply.getId())){
				officeDutyApply.setId(getGUID());
			}
			Object[] object=new Object[]{officeDutyApply.getId(),officeDutyApply.getUnitId(),officeDutyApply.getDutyInformationId(),
					officeDutyApply.getUserId(),officeDutyApply.getApplyDate(),officeDutyApply.getType()};
			objects.add(object);
		}
		int[] argTypes=new int[]{Types.CHAR,Types.CHAR,Types.CHAR,
				Types.CHAR,Types.DATE,Types.VARCHAR};
		batchUpdate(sql, objects, argTypes);
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_duty_apply where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeDutyApply officeDutyApply){
		String sql = "update office_duty_apply set unit_id = ?, duty_information_id = ?, user_id = ?, apply_date = ?, type = ? where id = ?";
		Object[] args = new Object[]{
			officeDutyApply.getUnitId(), officeDutyApply.getDutyInformationId(), 
			officeDutyApply.getUserId(), officeDutyApply.getApplyDate(), 
			officeDutyApply.getType(), officeDutyApply.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeDutyApply getOfficeDutyApplyById(String id){
		String sql = "select * from office_duty_apply where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeDutyApply> getOfficeDutyApplyMapByIds(String[] ids){
		String sql = "select * from office_duty_apply where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeDutyApply> getOfficeDutyApplyList(){
		String sql = "select * from office_duty_apply";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeDutyApply> getOfficeDutyApplyPage(Pagination page){
		String sql = "select * from office_duty_apply";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeDutyApply> getOfficeDutyApplyByUnitIdList(String unitId){
		String sql = "select * from office_duty_apply where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeDutyApply> getOfficeDutyApplyByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_duty_apply where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeDutyApply> getOfficeDutyAppliesMap(String unitId,
			String dutyId) {
		String sql = "select * from office_duty_apply where unit_id = ? and duty_information_id=?";
		return query(sql, new String[]{unitId,dutyId}, new MultiRow());
	}

	@Override
	public boolean isApplyByOthers(String unitId,String dutyId, String type, String userId,
			Date date) {
		String sql="select count(*) from office_duty_apply where unit_id = ? and duty_information_id=? and user_id<>? and apply_date=? and type=?";
		int i=0;
		i=queryForInt(sql, new Object[]{unitId,dutyId,userId,date,type});
		
		if(i>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean isApplyAdmin(String unitId, String dutyId, String userId,
			Date date) {
		String sql2="select count(*) from office_duty_apply where unit_id = ? and duty_information_id<>? and  user_id=? and apply_date=?";
		int j=0;
		j=queryForInt(sql2, new Object[]{unitId,dutyId,userId,date});
		if(j>0){
			return true;
		}
		return false;
	}

	@Override
	public void deleteApplyInfo(String unitId, String dutyId, String[] userId) {
		List<Object> args=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer("delete from office_duty_apply where unit_id=? and duty_information_id=?");
		args.add(unitId);
		args.add(dutyId);
		if(ArrayUtils.isNotEmpty(userId)){
			sql.append(" and user_id in");
		}
		if(ArrayUtils.isNotEmpty(userId)){
			updateForInSQL(sql.toString(), args.toArray(), userId);
		}else{
			update(sql.toString(), args.toArray());
		}
	}

	@Override
	public List<OfficeDutyApply> getOfficeDutyAppliesByUnitIdAndUserId(
			String unitId, Date applyDate, String userId) {
		List<Object> args=new ArrayList<Object>();
		StringBuffer sb=new StringBuffer("select * from office_duty_apply where unit_id = ? and apply_date=?");
		args.add(unitId);
		args.add(applyDate);
		if(StringUtils.isNotBlank(userId)){
			sb.append("  and user_id=?");
			args.add(userId);
		}
		return query(sb.toString(), args.toArray(), new MultiRow());
	}
	
}
