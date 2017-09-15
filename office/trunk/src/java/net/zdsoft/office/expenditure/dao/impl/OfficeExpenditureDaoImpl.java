package net.zdsoft.office.expenditure.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.expenditure.dao.OfficeExpenditureDao;
import net.zdsoft.office.expenditure.entity.OfficeExpenditure;

import org.apache.commons.lang3.StringUtils;
/**
 * office_expenditure（财务开支）
 * @author 
 * 
 */
public class OfficeExpenditureDaoImpl extends BaseDao<OfficeExpenditure> implements OfficeExpenditureDao{

	@Override
	public OfficeExpenditure setField(ResultSet rs) throws SQLException{
		OfficeExpenditure officeExpenditure = new OfficeExpenditure();
		officeExpenditure.setId(rs.getString("id"));
		officeExpenditure.setUnitId(rs.getString("unit_id"));
		officeExpenditure.setApplyUserId(rs.getString("apply_user_id"));
		officeExpenditure.setApplyDate(rs.getTimestamp("apply_date"));
		officeExpenditure.setType(rs.getString("type"));
		officeExpenditure.setState(rs.getString("state"));
		officeExpenditure.setFlowId(rs.getString("flow_id"));
		officeExpenditure.setIsdeleted(rs.getBoolean("is_deleted"));
		officeExpenditure.setCreationTime(rs.getTimestamp("creation_time"));
		officeExpenditure.setCreateTime(rs.getTimestamp("modify_time"));
		return officeExpenditure;
	}

	@Override
	public OfficeExpenditure save(OfficeExpenditure officeExpenditure){
		String sql = "insert into office_expenditure(id, unit_id, apply_user_id, apply_date, type, state, flow_id, is_deleted, creation_time, modify_time) values(?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeExpenditure.getId())){
			officeExpenditure.setId(createId());
		}
		Object[] args = new Object[]{
			officeExpenditure.getId(), officeExpenditure.getUnitId(), 
			officeExpenditure.getApplyUserId(), officeExpenditure.getApplyDate(), 
			officeExpenditure.getType(), officeExpenditure.getState(), 
			officeExpenditure.getFlowId(), 
			officeExpenditure.getIsdeleted(), officeExpenditure.getCreationTime(), 
			officeExpenditure.getModifyTime()
		};
		update(sql, args);
		return officeExpenditure;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_expenditure where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeExpenditure officeExpenditure){
		String sql = "update office_expenditure set unit_id = ?, apply_user_id = ?, apply_date = ?, type = ?, state = ?, flow_id = ?, is_deleted = ?, creation_time = ?, modify_time = ? where id = ?";
		Object[] args = new Object[]{
			officeExpenditure.getUnitId(), officeExpenditure.getApplyUserId(), 
			officeExpenditure.getApplyDate(), officeExpenditure.getType(), 
			officeExpenditure.getState(), officeExpenditure.getFlowId(), 
			officeExpenditure.getIsdeleted(), 
			officeExpenditure.getCreationTime(), officeExpenditure.getModifyTime(), 
			officeExpenditure.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeExpenditure getOfficeExpenditureById(String id){
		String sql = "select * from office_expenditure where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public List<OfficeExpenditure> getOfficeExpenditures(String unitId,
			String applyUserId, String type, String state, Pagination page) {
		StringBuffer sb=new StringBuffer("select * from office_expenditure where unit_id = ? ");
		List<Object> args=new ArrayList<Object>();
		args.add(unitId);
		if(StringUtils.isNotBlank(applyUserId)){
			sb.append(" and apply_user_id=?");
			args.add(applyUserId);
		}
		if(StringUtils.isNotBlank(type)){
			sb.append(" and type=?");
			args.add(type);
		}
		if(StringUtils.isNotBlank(state)){
			sb.append(" and state=?");
			args.add(state);
		}
		sb.append(" order by state,creation_time desc");
		if(page!=null){
			return query(sb.toString(), args.toArray(), new MultiRow(), page);
		}else{
			return query(sb.toString(), args.toArray(), new MultiRow());
		}
	}
	
	@Override
	public List<OfficeExpenditure> getOfficeExpendituresByUserIds(String unitId,
			String[] applyUserIds, String type, String state, Pagination page) {
		StringBuffer sb=new StringBuffer("select * from office_expenditure where unit_id = ? ");
		List<Object> args=new ArrayList<Object>();
		args.add(unitId);
		if(StringUtils.isNotBlank(type)){
			sb.append(" and type=?");
			args.add(type);
		}
		if(StringUtils.isNotBlank(state)){
			sb.append(" and state=?");
			args.add(state);
		}
		if(applyUserIds==null){//搜所有
			sb.append(" order by modify_time desc");
			if(page!=null){
				return query(sb.toString(), args.toArray(), new MultiRow(), page);
			}else{
				return query(sb.toString(), args.toArray(), new MultiRow());
			}
		}else{
			sb.append(" and apply_user_id in ");
			if(page!=null){
				return queryForInSQL(sb.toString(), args.toArray(), applyUserIds, new MultiRow(), "  order by  modify_time desc", page);
			}else{
				return queryForInSQL(sb.toString(), args.toArray(), applyUserIds, new MultiRow(), "  order by  modify_time desc");
			}
		}
	}

	@Override
	public Map<String, OfficeExpenditure> getOfficeEMap(String[] flowIds) {
		String sql = "select * from office_expenditure where flow_id in";
		return queryForInSQL(sql,null, flowIds, new MapRowMapper<String, OfficeExpenditure>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("flow_id");
			}

			@Override
			public OfficeExpenditure mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public List<OfficeExpenditure> HaveDoAudit(String userId,Pagination page) {
		page.setUseCursor(true);
		List<Object> obj = new ArrayList<Object>();
		String findSql = "select distinct office_expenditure.* from office_expenditure,jbpm_hi_task task where  office_expenditure.flow_id = task.proc_inst_id ";
		StringBuffer sb = new StringBuffer();
		sb.append(findSql);
		sb.append(" and office_expenditure.state >=3");
		sb.append(" and task.ASSIGNEE_ID =?");
		obj.add(userId);
		sb.append(" order by  office_expenditure.creation_time desc,office_expenditure.state");
		return query(sb.toString(),obj.toArray(), new MultiRow(),page);
	}

	@Override
	public List<OfficeExpenditure> getOfficeExpendituresByUnitId(String unitId) {
		String sql = "select * from office_expenditure where unit_id = ?";
		return query(sql, new Object[]{unitId}, new MultiRow());
	}

}
	