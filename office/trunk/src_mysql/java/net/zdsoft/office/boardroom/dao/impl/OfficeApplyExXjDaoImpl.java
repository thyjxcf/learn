package net.zdsoft.office.boardroom.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.boardroom.entity.OfficeApplyExXj;
import net.zdsoft.office.boardroom.dao.OfficeApplyExXjDao;
import net.zdsoft.office.dailyoffice.entity.OfficeUtilityNumber;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
/**
 * office_apply_ex_xj
 * @author 
 * 
 */
public class OfficeApplyExXjDaoImpl extends BaseDao<OfficeApplyExXj> implements OfficeApplyExXjDao{

	private static final String SQL_INSERT="insert into office_apply_ex_xj(id, unit_id, apply_id, details_id) values(?,?,?,?)";
	@Override
	public OfficeApplyExXj setField(ResultSet rs) throws SQLException{
		OfficeApplyExXj officeApplyExXj = new OfficeApplyExXj();
		officeApplyExXj.setId(rs.getString("id"));
		officeApplyExXj.setUnitId(rs.getString("unit_id"));
		officeApplyExXj.setApplyId(rs.getString("apply_id"));
		officeApplyExXj.setDetailsId(rs.getString("details_id"));
		return officeApplyExXj;
	}

	@Override
	public OfficeApplyExXj save(OfficeApplyExXj officeApplyExXj){
		String sql = "insert into office_apply_ex_xj(id, unit_id, apply_id, details_id) values(?,?,?,?)";
		if (StringUtils.isBlank(officeApplyExXj.getId())){
			officeApplyExXj.setId(createId());
		}
		Object[] args = new Object[]{
			officeApplyExXj.getId(), officeApplyExXj.getUnitId(), 
			officeApplyExXj.getApplyId(), officeApplyExXj.getDetailsId()
		};
		update(sql, args);
		return officeApplyExXj;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_apply_ex_xj where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeApplyExXj officeApplyExXj){
		String sql = "update office_apply_ex_xj set unit_id = ?, apply_id = ?, details_id = ? where id = ?";
		Object[] args = new Object[]{
			officeApplyExXj.getUnitId(), officeApplyExXj.getApplyId(), 
			officeApplyExXj.getDetailsId(), officeApplyExXj.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeApplyExXj getOfficeApplyExXjById(String id){
		String sql = "select * from office_apply_ex_xj where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeApplyExXj> getOfficeApplyExXjMapByIds(String[] ids){
		String sql = "select * from office_apply_ex_xj where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeApplyExXj> getOfficeApplyExXjList(){
		String sql = "select * from office_apply_ex_xj";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeApplyExXj> getOfficeApplyExXjPage(Pagination page){
		String sql = "select * from office_apply_ex_xj";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeApplyExXj> getOfficeApplyExXjByUnitIdList(String unitId){
		String sql = "select * from office_apply_ex_xj where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeApplyExXj> getOfficeApplyExXjByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_apply_ex_xj where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public void addOfficeApplyExXjs(List<OfficeApplyExXj> officeApplyExXjs) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (OfficeApplyExXj officeApplyExXj:officeApplyExXjs) {
			if (StringUtils.isBlank(officeApplyExXj.getId()))
				officeApplyExXj.setId(getGUID());
			Object[] args = new Object[]{
					officeApplyExXj.getId(), officeApplyExXj.getUnitId(), 
					officeApplyExXj.getApplyId(), officeApplyExXj.getDetailsId()
				};
			listOfArgs.add(args);
		}
		int[] argTypes = new int[] { 
				Types.CHAR, Types.CHAR, Types.CHAR,
				Types.CHAR
				};
		batchUpdate(SQL_INSERT, listOfArgs, argTypes);
		
	}

	@Override
	public List<OfficeApplyExXj> getOfficeApplyExXjByApplyId(String unitId,
			String officeBoardroomApplyId) {
		String sql="select * from office_apply_ex_xj where unit_id = ? and apply_id=?";
		return query(sql, new Object[]{unitId,officeBoardroomApplyId }, new MultiRow());
	}

	@Override
	public String[] getOfficeApplyDetailsXjByIds(String[] applyRoomIds) {
		String sql="select details_id from office_apply_ex_xj where apply_id in ";
		List<String> ids = queryForInSQL(sql, null, applyRoomIds,
				new MultiRowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getString("details_id");
					}
				});
		return ids.toArray(new String[0]);
	}
}
