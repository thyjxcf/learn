package net.zdsoft.office.expense.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
import net.zdsoft.office.expense.dao.OfficeExpenseDao;
import net.zdsoft.office.expense.entity.OfficeExpense;
import net.zdsoft.office.util.Constants;

import org.apache.commons.lang.StringUtils;
/**
 * office_expense
 * @author 
 * 
 */
public class OfficeExpenseDaoImpl extends BaseDao<OfficeExpense> implements OfficeExpenseDao{

	@Override
	public OfficeExpense setField(ResultSet rs) throws SQLException{
		OfficeExpense officeExpense = new OfficeExpense();
		officeExpense.setId(rs.getString("id"));
		officeExpense.setExpenseMoney(rs.getFloat("expense_money"));
		officeExpense.setExpenseType(rs.getString("expense_type"));
		officeExpense.setDetail(rs.getString("detail"));
		officeExpense.setState(rs.getString("state"));
		officeExpense.setFlowId(rs.getString("flow_id"));
		officeExpense.setUnitId(rs.getString("unit_id"));
		officeExpense.setApplyUserId(rs.getString("apply_user_id"));
		officeExpense.setCreateTime(rs.getTimestamp("create_time"));
		officeExpense.setIsDeleted(rs.getBoolean("is_deleted"));
		return officeExpense;
	}

	@Override
	public OfficeExpense save(OfficeExpense officeExpense){
		String sql = "insert into office_expense(id, expense_money, expense_type, detail, state, flow_id, unit_id, apply_user_id, create_time, is_deleted) values(?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeExpense.getId())){
			officeExpense.setId(createId());
		}
		officeExpense.setCreateTime(new Date());
		officeExpense.setIsDeleted(false);
		Object[] args = new Object[]{
			officeExpense.getId(), officeExpense.getExpenseMoney(), 
			officeExpense.getExpenseType(), officeExpense.getDetail(), 
			officeExpense.getState(), officeExpense.getFlowId(), 
			officeExpense.getUnitId(), officeExpense.getApplyUserId(), 
			officeExpense.getCreateTime(), officeExpense.getIsDeleted()
		};
		update(sql, args);
		return officeExpense;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "update office_expense set is_deleted=1 where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeExpense officeExpense){
		String sql = "update office_expense set expense_money = ?, expense_type = ?, detail = ?, state = ?, flow_id = ?, unit_id = ?, apply_user_id = ?, create_time = ?, is_deleted = ? where id = ?";
		officeExpense.setCreateTime(new Date());
		officeExpense.setIsDeleted(false);
		Object[] args = new Object[]{
			officeExpense.getExpenseMoney(), officeExpense.getExpenseType(), 
			officeExpense.getDetail(), officeExpense.getState(), 
			officeExpense.getFlowId(), officeExpense.getUnitId(), 
			officeExpense.getApplyUserId(), officeExpense.getCreateTime(), 
			officeExpense.getIsDeleted(), officeExpense.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeExpense getOfficeExpenseById(String id){
		String sql = "select * from office_expense where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeExpense> getOfficeExpenseMapByIds(String[] ids){
		String sql = "select * from office_expense where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeExpense> getOfficeExpenseList(){
		String sql = "select * from office_expense";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeExpense> getOfficeExpensePage(Pagination page){
		String sql = "select * from office_expense";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeExpense> getOfficeExpenseByUnitIdList(String unitId){
		String sql = "select * from office_expense where is_deleted=0 and unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeExpense> getOfficeExpenseByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_expense where is_deleted=0 and unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeExpense> getOfficeExpenseListByCondition(String unitId, String searchType, String applyUserId, Pagination page){
		List<Object> argsList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from office_expense where is_deleted = 0 and unit_id = ? ");
		argsList.add(unitId);
		if(StringUtils.isNotBlank(searchType) && Integer.parseInt(searchType)!=Constants.LEAVE_APPLY_ALL){
			sql.append(" and state = ?");
			argsList.add(Integer.parseInt(searchType));
		}
		if(StringUtils.isNotBlank(applyUserId)){
			sql.append(" and apply_user_id = ?");
			argsList.add(applyUserId);
		}
		sql.append(" order by state, create_time desc, apply_user_id asc");
		if(page != null)
			return query(sql.toString(), argsList.toArray(), new MultiRow(), page);
		else
			return query(sql.toString(), argsList.toArray(), new MultiRow());
	}
	
	@Override
	public List<OfficeExpense> getOfficeExpenseByIds(String[] ids){
		String sql = "select * from office_expense where id in";
		return queryForInSQL(sql, null, ids, new MultiRow());
	}
	
	@Override
	public List<OfficeExpense> getAuditedList(String userId, String[] state, Pagination page){
		page.setUseCursor(true);
		List<Object> obj = new ArrayList<Object>();
		String sql = "select distinct expense.* from office_expense expense,jbpm_hi_task task where expense.is_deleted=0 and expense.flow_id = task.proc_inst_id ";
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		sb.append(" and task.ASSIGNEE_ID =?");
		obj.add(userId);
		if(state != null){
			sb.append(" and expense.state in").append(
					SQLUtils.toSQLInString(state));
		}
		sb.append(" order by expense.state, expense.create_time desc");
		return query(sb.toString(), obj.toArray(), new MultiRow(), page);
	}
	
	@Override
	public Map<String, OfficeExpense> getOfficeExpenseMapByFlowIds(String[] flowIds){
		String sql = "select * from office_expense where is_deleted=0 and flow_id in";
		return queryForInSQL(sql, null, flowIds, new MapRowMapper<String, OfficeExpense>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("flow_id");
			}

			@Override
			public OfficeExpense mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public List<OfficeExpense> getOfficeExpensesByUnitIdAndOthers(
			String unitId, String state, Date startTime, Date Endtime,
			String applyUserName, Pagination page) {
		StringBuffer sb=new StringBuffer("select * from office_expense where is_deleted=0 and unit_id=?");
		List<Object> args=new ArrayList<Object>();
		args.add(unitId);
		if(org.apache.commons.lang3.StringUtils.isNotBlank(state)){
			sb.append(" and state=?");
			args.add(state);
		}
		if(startTime!=null){
			sb.append(" and create_time>=?");
			args.add(startTime);
		}
		if(Endtime!=null){
			sb.append(" and create_time<=?");
			args.add(Endtime);
		}
		if(org.apache.commons.lang3.StringUtils.isNotBlank(applyUserName)){
			sb.append(" and exists(select 1 from base_user where base_user.id=office_expense.apply_user_id and base_user.real_name like '%"+applyUserName+"%')");
		}
		if(page!=null){
			return query(sb.toString(), args.toArray(), new MultiRow(), page);
		}else{
			return query(sb.toString(), args.toArray(), new MultiRow());
		}
	}
	
}
