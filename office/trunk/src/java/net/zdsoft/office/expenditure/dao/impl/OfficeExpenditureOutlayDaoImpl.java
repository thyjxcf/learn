package net.zdsoft.office.expenditure.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.expenditure.entity.OfficeExpenditureOutlay;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureReception;
import net.zdsoft.office.expenditure.dao.OfficeExpenditureOutlayDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
/**
 * office_expenditure_outlay
 * @author 
 * 
 */
public class OfficeExpenditureOutlayDaoImpl extends BaseDao<OfficeExpenditureOutlay> implements OfficeExpenditureOutlayDao{

	@Override
	public OfficeExpenditureOutlay setField(ResultSet rs) throws SQLException{
		OfficeExpenditureOutlay officeExpenditureOutlay = new OfficeExpenditureOutlay();
		officeExpenditureOutlay.setId(rs.getString("id"));
		officeExpenditureOutlay.setExpenditureId(rs.getString("expenditure_id"));
		officeExpenditureOutlay.setReason(rs.getString("reason"));
		officeExpenditureOutlay.setFeeName(rs.getString("fee_name"));
		officeExpenditureOutlay.setSum(rs.getDouble("sum"));
		officeExpenditureOutlay.setContent(rs.getString("content"));
		officeExpenditureOutlay.setCreationTime(rs.getTimestamp("creation_time"));
		return officeExpenditureOutlay;
	}

	@Override
	public OfficeExpenditureOutlay save(OfficeExpenditureOutlay officeExpenditureOutlay){
		String sql = "insert into office_expenditure_outlay(id, expenditure_id, reason, fee_name, sum, content, creation_time) values(?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeExpenditureOutlay.getId())){
			officeExpenditureOutlay.setId(createId());
		}
		Object[] args = new Object[]{
			officeExpenditureOutlay.getId(), officeExpenditureOutlay.getExpenditureId(), 
			officeExpenditureOutlay.getReason(), officeExpenditureOutlay.getFeeName(), 
			officeExpenditureOutlay.getSum(), officeExpenditureOutlay.getContent(), 
			officeExpenditureOutlay.getCreationTime()
		};
		update(sql, args);
		return officeExpenditureOutlay;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_expenditure_outlay where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeExpenditureOutlay officeExpenditureOutlay){
		String sql = "update office_expenditure_outlay set expenditure_id = ?, reason = ?, fee_name = ?, sum = ?, content = ?, creation_time = ? where id = ?";
		Object[] args = new Object[]{
			officeExpenditureOutlay.getExpenditureId(), officeExpenditureOutlay.getReason(), 
			officeExpenditureOutlay.getFeeName(), officeExpenditureOutlay.getSum(), 
			officeExpenditureOutlay.getContent(), officeExpenditureOutlay.getCreationTime(), 
			officeExpenditureOutlay.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeExpenditureOutlay getOfficeExpenditureOutlayById(String id){
		String sql = "select * from office_expenditure_outlay where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public OfficeExpenditureOutlay getOfficeExpenditureOutlayByExId(
			String expenditureId) {
		String sql = "select * from office_expenditure_outlay where expenditure_id = ?";
		return query(sql, new Object[]{expenditureId }, new SingleRow());
	}
	
	@Override
	public Map<String, OfficeExpenditureOutlay> getOfficeExpenditureOutlayByExIds(String[] officeExpenditureIds) {
		String sql = "select * from office_expenditure_outlay where expenditure_id in";
		return queryForInSQL(sql, null, officeExpenditureIds, new MapRowMapper<String, OfficeExpenditureOutlay>() {
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("expenditure_id");
			}

			@Override
			public OfficeExpenditureOutlay mapRowValue(ResultSet rs,
					int rowNum) throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public void deleteByExId(String officeExpenditureId) {
		String sql = "delete from office_expenditure_outlay where expenditure_id=?";
		update(sql, new Object[]{officeExpenditureId});
	}

}
