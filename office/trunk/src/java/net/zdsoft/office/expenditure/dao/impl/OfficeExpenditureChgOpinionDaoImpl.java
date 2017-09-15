package net.zdsoft.office.expenditure.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.office.expenditure.dao.OfficeExpenditureChgOpinionDao;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureChgOpinion;

import org.apache.commons.lang3.StringUtils;
/**
 * office_expenditure_chg_opinion
 * @author 
 * 
 */
public class OfficeExpenditureChgOpinionDaoImpl extends BaseDao<OfficeExpenditureChgOpinion> implements OfficeExpenditureChgOpinionDao{

	@Override
	public OfficeExpenditureChgOpinion setField(ResultSet rs) throws SQLException{
		OfficeExpenditureChgOpinion officeExpenditureChgOpinion = new OfficeExpenditureChgOpinion();
		officeExpenditureChgOpinion.setId(rs.getString("id"));
		officeExpenditureChgOpinion.setTaskId(rs.getString("task_id"));
		officeExpenditureChgOpinion.setCarUnitName(rs.getString("car_unit_name"));
		officeExpenditureChgOpinion.setCarNumber(rs.getString("car_number"));
		officeExpenditureChgOpinion.setDriverName(rs.getString("driver_name"));
		officeExpenditureChgOpinion.setPayType(rs.getString("pay_type"));
		officeExpenditureChgOpinion.setFee(rs.getDouble("fee"));
		officeExpenditureChgOpinion.setCreationTime(rs.getTimestamp("creation_time"));
		return officeExpenditureChgOpinion;
	}

	@Override
	public OfficeExpenditureChgOpinion save(OfficeExpenditureChgOpinion officeExpenditureChgOpinion){
		String sql = "insert into office_expenditure_chg_opinion(id, task_id, car_unit_name, car_number, driver_name, pay_type, fee, creation_time) values(?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeExpenditureChgOpinion.getId())){
			officeExpenditureChgOpinion.setId(createId());
		}
		Object[] args = new Object[]{
			officeExpenditureChgOpinion.getId(), officeExpenditureChgOpinion.getTaskId(), 
			officeExpenditureChgOpinion.getCarUnitName(), officeExpenditureChgOpinion.getCarNumber(), 
			officeExpenditureChgOpinion.getDriverName(), officeExpenditureChgOpinion.getPayType(), 
			officeExpenditureChgOpinion.getFee(), officeExpenditureChgOpinion.getCreationTime()
		};
		update(sql, args);
		return officeExpenditureChgOpinion;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_expenditure_chg_opinion where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeExpenditureChgOpinion officeExpenditureChgOpinion){
		String sql = "update office_expenditure_chg_opinion set task_id = ?, car_unit_name = ?, car_number = ?, driver_name = ?, pay_type = ?, fee = ?, creation_time = ? where id = ?";
		Object[] args = new Object[]{
			officeExpenditureChgOpinion.getTaskId(), officeExpenditureChgOpinion.getCarUnitName(), 
			officeExpenditureChgOpinion.getCarNumber(), officeExpenditureChgOpinion.getDriverName(), 
			officeExpenditureChgOpinion.getPayType(), officeExpenditureChgOpinion.getFee(), 
			officeExpenditureChgOpinion.getCreationTime(), officeExpenditureChgOpinion.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeExpenditureChgOpinion getOfficeExpenditureChgOpinionById(String id){
		String sql = "select * from office_expenditure_chg_opinion where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public OfficeExpenditureChgOpinion getOfficeExpenditureChgOpinionByTaskId(
			String taskId) {
		String sql = "select * from office_expenditure_chg_opinion where task_id = ?";
		return query(sql, new Object[]{taskId }, new SingleRow());
	}
	
	public List<OfficeExpenditureChgOpinion> getOfficeExpenditureChgOpinionByTaskIds(
			String...taskId) {
		String sql = "select * from office_expenditure_chg_opinion where task_id in ";
		return queryForInSQL(sql, null, taskId, new MultiRow());
	}
}
