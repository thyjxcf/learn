package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeUtilityApplyDao;
import net.zdsoft.office.dailyoffice.entity.OfficeUtilityApply;
import net.zdsoft.office.util.Constants;

import org.apache.commons.lang.StringUtils;
/**
 * office_utility_apply
 * @author 
 * 
 */
public class OfficeUtilityApplyDaoImpl extends BaseDao<OfficeUtilityApply> implements OfficeUtilityApplyDao{

	private static final String SQL_INSERT = "insert into office_utility_apply(id, unit_id, type, room_id, apply_period, apply_date, user_id, state, purpose, course_id, lab_info_id) values(?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE_STATE = "update office_utility_apply set state = ? where id in ";
	
	@Override
	public OfficeUtilityApply setField(ResultSet rs) throws SQLException{
		OfficeUtilityApply officeUtilityApply = new OfficeUtilityApply();
		officeUtilityApply.setId(rs.getString("id"));
		officeUtilityApply.setUnitId(rs.getString("unit_id"));
		officeUtilityApply.setType(rs.getString("type"));
		officeUtilityApply.setRoomId(rs.getString("room_id"));
		officeUtilityApply.setApplyPeriod(rs.getString("apply_period"));
		officeUtilityApply.setApplyDate(rs.getTimestamp("apply_date"));
		officeUtilityApply.setUserId(rs.getString("user_id"));
		officeUtilityApply.setState(rs.getInt("state"));
		officeUtilityApply.setPurpose(rs.getString("purpose"));
		officeUtilityApply.setCourseId(rs.getString("course_id"));
		officeUtilityApply.setLabInfoId(rs.getString("lab_info_id"));
		return officeUtilityApply;
	}

	@Override
	public void save(OfficeUtilityApply officeUtilityApply){
		if (StringUtils.isBlank(officeUtilityApply.getId())){
			officeUtilityApply.setId(createId());
		}
		Object[] args = new Object[]{
			officeUtilityApply.getId(), officeUtilityApply.getUnitId(), 
			officeUtilityApply.getType(), officeUtilityApply.getRoomId(), 
			officeUtilityApply.getApplyPeriod(), officeUtilityApply.getApplyDate(), 
			officeUtilityApply.getUserId(), officeUtilityApply.getState(),
			officeUtilityApply.getPurpose(), officeUtilityApply.getCourseId(),
			officeUtilityApply.getLabInfoId()
		};
		update(SQL_INSERT, args);
	}
	
	public void insertOfficeUtilityApplies(
			List<OfficeUtilityApply> officeUtilityApplies) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (OfficeUtilityApply officeUtilityApply:officeUtilityApplies) {
			if (StringUtils.isBlank(officeUtilityApply.getId()))
				officeUtilityApply.setId(getGUID());
			Object[] args = new Object[]{
					officeUtilityApply.getId(), officeUtilityApply.getUnitId(), 
					officeUtilityApply.getType(), officeUtilityApply.getRoomId(), 
					officeUtilityApply.getApplyPeriod(), officeUtilityApply.getApplyDate(), 
					officeUtilityApply.getUserId(), officeUtilityApply.getState(),
					officeUtilityApply.getPurpose(), officeUtilityApply.getCourseId(),
					officeUtilityApply.getLabInfoId()
			};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { 
				Types.CHAR, Types.CHAR, Types.VARCHAR,
				Types.CHAR, Types.VARCHAR, Types.DATE,
				Types.CHAR, Types.INTEGER, Types.VARCHAR,
				Types.CHAR,	Types.CHAR
				};
		batchUpdate(SQL_INSERT, listOfArgs, argTypes);
	}
	
	@Override
	public void updateStateByIds(String[] ids, Integer state) {
		updateForInSQL(SQL_UPDATE_STATE, new Object[]{state}, ids);
	}
	
	@Override
	public boolean isApplyByOthers(String roomId, String period, String type,
			String userId, Date date) {
		String sql = "select count(1) from office_utility_apply where room_id = ? and apply_period = ? " +
				" and type = ? and user_id != ? and apply_date = ? and state != ?";
		int i = queryForInt(sql, new Object[]{roomId, period, type, userId, date, Constants.APPLY_STATE_NOPASS});
		if(i > 0){
			return true;
		}
		return false;
	}
	
	@Override
	public void batchDelete(List<OfficeUtilityApply> officeUtilityApplies) {
		String sql = "delete from office_utility_apply where type = ? and room_id = ? and apply_period = ? and apply_date = ? ";
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (OfficeUtilityApply officeUtilityApply:officeUtilityApplies) {
			Object[] args = new Object[]{
					officeUtilityApply.getType(),officeUtilityApply.getRoomId(),
					officeUtilityApply.getApplyPeriod(),officeUtilityApply.getApplyDate()
			};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] {Types.VARCHAR, Types.CHAR, Types.VARCHAR,Types.DATE};
		batchUpdate(sql, listOfArgs, argTypes);
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_utility_apply where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeUtilityApply officeUtilityApply){
		String sql = "update office_utility_apply set unit_id = ?, type = ?, room_id = ?, apply_period = ?, apply_date = ?, user_id = ?, state = ?, purpose = ?, course_id = ?, lab_info_id = ? where id = ?";
		Object[] args = new Object[]{
			officeUtilityApply.getUnitId(), officeUtilityApply.getType(), 
			officeUtilityApply.getRoomId(), officeUtilityApply.getApplyPeriod(), 
			officeUtilityApply.getApplyDate(), officeUtilityApply.getUserId(), 
			officeUtilityApply.getState(), officeUtilityApply.getPurpose(),
			officeUtilityApply.getCourseId(), officeUtilityApply.getId(),
			officeUtilityApply.getLabInfoId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeUtilityApply getOfficeUtilityApplyById(String id){
		String sql = "select * from office_utility_apply where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeUtilityApply> getOfficeUtilityApplyMapByIds(String[] ids){
		String sql = "select * from office_utility_apply where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}
	
	@Override
	public List<OfficeUtilityApply> getOfficeUtilityApplyListByIds(String[] ids) {
		String sql = "select * from office_utility_apply where id in";
		return queryForInSQL(sql, null, ids, new MultiRow());
	}

	@Override
	public List<OfficeUtilityApply> getOfficeUtilityApplyList(String roomType,
			Date applyDate, String unitId, String userId){
		String sql = "select * from office_utility_apply where type = ? and apply_date = ? and unit_id = ? and ((user_id = ? and state = ? ) or state != ? ) ";
		
		return query(sql,new Object[]{roomType, applyDate, unitId, userId, Constants.APPLY_STATE_NOPASS, Constants.APPLY_STATE_NOPASS}, new MultiRow());
	}

	@Override
	public List<OfficeUtilityApply> getOfficeUtilityApplyPage(Pagination page){
		String sql = "select * from office_utility_apply";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeUtilityApply> getOfficeUtilityApplyByUnitIdList(String unitId){
		String sql = "select * from office_utility_apply where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeUtilityApply> getOfficeUtilityApplyByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_utility_apply where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeUtilityApply> getOfficeUtilityApply(String unitId,
			String type, String state, String courseId) {
		String sql = "select * from office_utility_apply where unit_id = ? and type = ? and state = ? and course_id = ?";
		return query(sql, new Object[]{unitId, type, state, courseId}, new MultiRow());
	}
	
	@Override
	public List<OfficeUtilityApply> getOfficeUtilityApply(String unitId,
			String type, String state, Date startApplyDate, Date endApplyDate, String studyType) {
		String sql = "select * from office_utility_apply oua, eduadm_course ec where oua.course_id = ec.id and oua.unit_id = ? and oua.type = ? and oua.state = ? and oua.apply_date >= ? and oua.apply_date <= ?";
		if(StringUtils.isNotBlank(studyType)) {
			sql += " and study_type = '" + studyType + "'";
		}
		return query(sql, new Object[] {unitId, type, state, startApplyDate, endApplyDate}, new MultiRow());
	}
}