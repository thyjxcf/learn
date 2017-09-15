package net.zdsoft.office.expenditure.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.office.expenditure.dao.OfficeExpenditureKjOpinionDao;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureKjOpinion;

import org.apache.commons.lang3.StringUtils;
/**
 * office_expenditure_kj_opinion
 * @author 
 * 
 */
public class OfficeExpenditureKjOpinionDaoImpl extends BaseDao<OfficeExpenditureKjOpinion> implements OfficeExpenditureKjOpinionDao{

	@Override
	public OfficeExpenditureKjOpinion setField(ResultSet rs) throws SQLException{
		OfficeExpenditureKjOpinion officeExpenditureKjOpinion = new OfficeExpenditureKjOpinion();
		officeExpenditureKjOpinion.setId(rs.getString("id"));
		officeExpenditureKjOpinion.setTaskId(rs.getString("task_id"));
		officeExpenditureKjOpinion.setPlan(rs.getInt("is_plan"));
		officeExpenditureKjOpinion.setAgreeState(rs.getString("is_agree_state"));
		officeExpenditureKjOpinion.setFeeName(rs.getString("fee_name"));
		officeExpenditureKjOpinion.setSurplus(rs.getDouble("surplus"));
		officeExpenditureKjOpinion.setSurplusRate(rs.getDouble("surplus_rate"));
		officeExpenditureKjOpinion.setPayType(rs.getString("pay_type"));
		officeExpenditureKjOpinion.setCreationTime(rs.getTimestamp("creation_time"));
		return officeExpenditureKjOpinion;
	}

	@Override
	public OfficeExpenditureKjOpinion save(OfficeExpenditureKjOpinion officeExpenditureKjOpinion){
		String sql = "insert into office_expenditure_kj_opinion(id, task_id, is_plan, is_agree_state, fee_name, surplus, surplus_rate, pay_type, creation_time) values(?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeExpenditureKjOpinion.getId())){
			officeExpenditureKjOpinion.setId(createId());
		}
		Object[] args = new Object[]{
			officeExpenditureKjOpinion.getId(), officeExpenditureKjOpinion.getTaskId(), 
			officeExpenditureKjOpinion.getPlan(), officeExpenditureKjOpinion.getAgreeState(), 
			officeExpenditureKjOpinion.getFeeName(), officeExpenditureKjOpinion.getSurplus(), 
			officeExpenditureKjOpinion.getSurplusRate(), officeExpenditureKjOpinion.getPayType(), 
			officeExpenditureKjOpinion.getCreationTime()
		};
		update(sql, args);
		return officeExpenditureKjOpinion;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_expenditure_kj_opinion where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeExpenditureKjOpinion officeExpenditureKjOpinion){
		String sql = "update office_expenditure_kj_opinion set task_id = ?, is_plan = ?, is_agree_state = ?, fee_name = ?, surplus = ?, surplus_rate = ?, pay_type = ?, creation_time = ? where id = ?";
		Object[] args = new Object[]{
			officeExpenditureKjOpinion.getTaskId(), officeExpenditureKjOpinion.getPlan(), 
			officeExpenditureKjOpinion.getAgreeState(), officeExpenditureKjOpinion.getFeeName(), 
			officeExpenditureKjOpinion.getSurplus(), officeExpenditureKjOpinion.getSurplusRate(), 
			officeExpenditureKjOpinion.getPayType(), officeExpenditureKjOpinion.getCreationTime(), 
			officeExpenditureKjOpinion.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeExpenditureKjOpinion getOfficeExpenditureKjOpinionById(String id){
		String sql = "select * from office_expenditure_kj_opinion where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public OfficeExpenditureKjOpinion getOfficeExpenditureKjOpinionByTaskId(String taskId) {
		String sql = "select * from office_expenditure_kj_opinion where task_id = ?";
		return query(sql, new Object[]{taskId}, new SingleRow());
	}
	
	public List<OfficeExpenditureKjOpinion> getOfficeExpenditureKjOpinionByTaskIds(String...taskId) {
		String sql = "select * from office_expenditure_kj_opinion where task_id in";
		return queryForInSQL(sql, null, taskId, new MultiRow());
	}

}
