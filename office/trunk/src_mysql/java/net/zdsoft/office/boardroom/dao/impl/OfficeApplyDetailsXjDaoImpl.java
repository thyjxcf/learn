package net.zdsoft.office.boardroom.dao.impl;

import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.boardroom.entity.OfficeApplyDetailsXj;
import net.zdsoft.office.boardroom.dao.OfficeApplyDetailsXjDao;
import net.zdsoft.office.util.Constants;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_apply_details_xj
 * @author 
 * 
 */
public class OfficeApplyDetailsXjDaoImpl extends BaseDao<OfficeApplyDetailsXj> implements OfficeApplyDetailsXjDao{

	private static final String SQL_INSERT = "insert into office_apply_details_xj(id, unit_id, room_id, apply_period, apply_date, dept_id, state) values(?,?,?,?,?,?,?)";
	@Override
	public OfficeApplyDetailsXj setField(ResultSet rs) throws SQLException{
		OfficeApplyDetailsXj officeApplyDetailsXj = new OfficeApplyDetailsXj();
		officeApplyDetailsXj.setId(rs.getString("id"));
		officeApplyDetailsXj.setUnitId(rs.getString("unit_id"));
		officeApplyDetailsXj.setRoomId(rs.getString("room_id"));
		officeApplyDetailsXj.setApplyPeriod(rs.getString("apply_period"));
		officeApplyDetailsXj.setApplyDate(rs.getTimestamp("apply_date"));
		officeApplyDetailsXj.setDeptId(rs.getString("dept_id"));
		officeApplyDetailsXj.setState(rs.getString("state"));
		return officeApplyDetailsXj;
	}

	@Override
	public OfficeApplyDetailsXj save(OfficeApplyDetailsXj officeApplyDetailsXj){
		String sql = "insert into office_apply_details_xj(id, unit_id, room_id, apply_period, apply_date, dept_id, state) values(?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeApplyDetailsXj.getId())){
			officeApplyDetailsXj.setId(createId());
		}
		Object[] args = new Object[]{
			officeApplyDetailsXj.getId(), officeApplyDetailsXj.getUnitId(), 
			officeApplyDetailsXj.getRoomId(), officeApplyDetailsXj.getApplyPeriod(), 
			officeApplyDetailsXj.getApplyDate(), officeApplyDetailsXj.getDeptId(), 
			officeApplyDetailsXj.getState()
		};
		update(sql, args);
		return officeApplyDetailsXj;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_apply_details_xj where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeApplyDetailsXj officeApplyDetailsXj){
		String sql = "update office_apply_details_xj set unit_id = ?, room_id = ?, apply_period = ?, apply_date = ?, dept_id = ?, state = ? where id = ?";
		Object[] args = new Object[]{
			officeApplyDetailsXj.getUnitId(), officeApplyDetailsXj.getRoomId(), 
			officeApplyDetailsXj.getApplyPeriod(), officeApplyDetailsXj.getApplyDate(), 
			officeApplyDetailsXj.getDeptId(), officeApplyDetailsXj.getState(), 
			officeApplyDetailsXj.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeApplyDetailsXj getOfficeApplyDetailsXjById(String id){
		String sql = "select * from office_apply_details_xj where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeApplyDetailsXj> getOfficeApplyDetailsXjMapByIds(String[] ids){
		String sql = "select * from office_apply_details_xj where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeApplyDetailsXj> getOfficeApplyDetailsXjList(){
		String sql = "select * from office_apply_details_xj";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeApplyDetailsXj> getOfficeApplyDetailsXjPage(Pagination page){
		String sql = "select * from office_apply_details_xj";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeApplyDetailsXj> getOfficeApplyDetailsXjByUnitIdList(String unitId){
		String sql = "select * from office_apply_details_xj where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeApplyDetailsXj> getOfficeApplyDetailsXjByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_apply_details_xj where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeApplyDetailsXj> getOfficeApplyDetailsXjByRoomId(
			String roomId, Date applyStartDate, Date applyEndDate,
			String unitId) {
		String sql = "select * from office_apply_details_xj where room_id = ? and " +
				" ?<=apply_date and apply_date<=?"+
				" and unit_id = ? and (( state = ? ) or state != ? ) ";
		
		return query(sql,new Object[]{roomId, applyStartDate,applyEndDate, unitId, Constants.APPLY_STATE_NOPASS, Constants.APPLY_STATE_NOPASS}, new MultiRow());
	}

	@Override
	public boolean isApplyByOthers(String period, Date applyDate,
			String roomId, String deptId) {
		String sql = "select count(1) from office_apply_details_xj where room_id = ? and apply_period = ? " +
				" and apply_date=? and dept_id != ?  and state != ?";
		int i = queryForInt(sql, new Object[]{roomId, period,applyDate, deptId, Constants.APPLY_STATE_NOPASS});
		if(i > 0){
			return true;
		}
		return false;
	}

	@Override
	public void batchDelete(List<OfficeApplyDetailsXj> officeApplyDetailsXjs) {
		String sql = "delete from office_apply_details_xj where unit_id=? and room_id = ? and apply_period = ? and apply_date = ? ";
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (OfficeApplyDetailsXj officeApplyDetailsXj:officeApplyDetailsXjs) {
			Object[] args = new Object[]{
					officeApplyDetailsXj.getUnitId(),officeApplyDetailsXj.getRoomId(),
					officeApplyDetailsXj.getApplyPeriod(),officeApplyDetailsXj.getApplyDate()
			};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] {Types.CHAR,Types.CHAR, Types.VARCHAR,Types.DATE};
		batchUpdate(sql, listOfArgs, argTypes);
	}

	@Override
	public void addofficeApplyDetailsXjs(
			List<OfficeApplyDetailsXj> officeApplyDetailsXjs) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (OfficeApplyDetailsXj officeApplyDetailsXj:officeApplyDetailsXjs) {
			if (StringUtils.isBlank(officeApplyDetailsXj.getId()))
				officeApplyDetailsXj.setId(getGUID());
			Object[] args = new Object[]{
					officeApplyDetailsXj.getId(), officeApplyDetailsXj.getUnitId(), 
					officeApplyDetailsXj.getRoomId(), officeApplyDetailsXj.getApplyPeriod(), 
					officeApplyDetailsXj.getApplyDate(), officeApplyDetailsXj.getDeptId(), 
					officeApplyDetailsXj.getState()
			};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { 
				Types.CHAR, Types.CHAR, Types.CHAR,
				Types.VARCHAR, Types.DATE,
				Types.CHAR, Types.CHAR
				};
		batchUpdate(SQL_INSERT, listOfArgs, argTypes);
		
	}

	@Override
	public void updateStateByIds(String[] ids, String state) {
		String sql1="update office_apply_details_xj set state =? where id in";
		String sql2="update office_apply_details_xj set state =? where id in";
		if(StringUtils.isNotBlank(state)&&StringUtils.equals("3", state)){
			updateForInSQL(sql1, new String[]{state}, ids);
		}
		if(StringUtils.isNotBlank(state)&&StringUtils.equals("4", state)){
			updateForInSQL(sql2, new String[]{state}, ids);
		}
	}

	@Override
	public boolean getApplyByApplyStartDate(String unitId, String roomId,
			Date applyEndDate) {
		String sql = "select count(1) from office_apply_details_xj where unit_id=? and room_id = ?  " +
				" and apply_date>=? ";
		int i = queryForInt(sql, new Object[]{unitId,roomId, applyEndDate});
		if(i > 0){
			return true;
		}
		return false;
	}
	
}
