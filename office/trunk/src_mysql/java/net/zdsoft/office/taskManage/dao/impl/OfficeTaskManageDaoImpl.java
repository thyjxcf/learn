package net.zdsoft.office.taskManage.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import net.zdsoft.office.taskManage.entity.OfficeTaskManage;
import net.zdsoft.office.taskManage.dao.OfficeTaskManageDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
/**
 * office_task_manage
 * @author 
 * 
 */
public class OfficeTaskManageDaoImpl extends BaseDao<OfficeTaskManage> implements OfficeTaskManageDao{

	@Override
	public OfficeTaskManage setField(ResultSet rs) throws SQLException{
		OfficeTaskManage officeTaskManage = new OfficeTaskManage();
		officeTaskManage.setId(rs.getString("id"));
		officeTaskManage.setUnitId(rs.getString("unit_id"));
		officeTaskManage.setTaskName(rs.getString("task_name"));
		officeTaskManage.setDealUserId(rs.getString("deal_user_id"));
		officeTaskManage.setCompleteTime(rs.getTimestamp("complete_time"));
		officeTaskManage.setHasAttach(rs.getInt("has_attach"));
		officeTaskManage.setRemark(rs.getString("remark"));
		officeTaskManage.setState(rs.getInt("state"));
		officeTaskManage.setCreateUserId(rs.getString("create_user_id"));
		officeTaskManage.setCreateTime(rs.getTimestamp("create_time"));
		officeTaskManage.setFirstRemindTime(rs.getTimestamp("first_remind_time"));
		officeTaskManage.setSecondRemindTime(rs.getTimestamp("second_remind_time"));
		officeTaskManage.setRemindNumber(rs.getInt("remind_number"));
		officeTaskManage.setActualFinishTime(rs.getTimestamp("actual_finish_time"));
		officeTaskManage.setHasSubmitAttach(rs.getInt("has_submit_attach"));
		officeTaskManage.setFinishRemark(rs.getString("finish_remark"));
		officeTaskManage.setIsDeleted(rs.getBoolean("is_deleted"));
		return officeTaskManage;
	}

	@Override
	public OfficeTaskManage save(OfficeTaskManage officeTaskManage){
		String sql = "insert into office_task_manage(id, unit_id, task_name, deal_user_id, complete_time, has_attach, remark, state, create_user_id, create_time, first_remind_time, second_remind_time, remind_number, actual_finish_time, has_submit_attach, finish_remark, is_deleted) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeTaskManage.getId())){
			officeTaskManage.setId(createId());
		}
		officeTaskManage.setIsDeleted(false);
		Object[] args = new Object[]{
			officeTaskManage.getId(), officeTaskManage.getUnitId(), 
			officeTaskManage.getTaskName(), officeTaskManage.getDealUserId(), 
			officeTaskManage.getCompleteTime(), officeTaskManage.getHasAttach(), 
			officeTaskManage.getRemark(), officeTaskManage.getState(), 
			officeTaskManage.getCreateUserId(), officeTaskManage.getCreateTime(), 
			officeTaskManage.getFirstRemindTime(), officeTaskManage.getSecondRemindTime(), 
			officeTaskManage.getRemindNumber(), officeTaskManage.getActualFinishTime(), 
			officeTaskManage.getHasSubmitAttach(), officeTaskManage.getFinishRemark(), 
			officeTaskManage.getIsDeleted()
		};
		update(sql, args);
		return officeTaskManage;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "update office_task_manage set is_deleted = 1 where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeTaskManage officeTaskManage){
		String sql = "update office_task_manage set unit_id = ?, task_name = ?, deal_user_id = ?, complete_time = ?, has_attach = ?, remark = ?, state = ?, create_user_id = ?, create_time = ?, first_remind_time = ?, second_remind_time = ?, remind_number = ?, actual_finish_time = ?, has_submit_attach = ?, finish_remark = ?, is_deleted = ? where id = ?";
		officeTaskManage.setIsDeleted(false);
		Object[] args = new Object[]{
			officeTaskManage.getUnitId(), officeTaskManage.getTaskName(), 
			officeTaskManage.getDealUserId(), officeTaskManage.getCompleteTime(), 
			officeTaskManage.getHasAttach(), officeTaskManage.getRemark(), 
			officeTaskManage.getState(), officeTaskManage.getCreateUserId(), 
			officeTaskManage.getCreateTime(), officeTaskManage.getFirstRemindTime(), 
			officeTaskManage.getSecondRemindTime(), officeTaskManage.getRemindNumber(), 
			officeTaskManage.getActualFinishTime(), officeTaskManage.getHasSubmitAttach(), 
			officeTaskManage.getFinishRemark(), officeTaskManage.getIsDeleted(), 
			officeTaskManage.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeTaskManage getOfficeTaskManageById(String id){
		String sql = "select * from office_task_manage where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeTaskManage> getOfficeTaskManageMapByIds(String[] ids){
		String sql = "select * from office_task_manage where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeTaskManage> getOfficeTaskManageList(){
		String sql = "select * from office_task_manage";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeTaskManage> getOfficeTaskManagePage(Pagination page){
		String sql = "select * from office_task_manage";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeTaskManage> getOfficeTaskManageByUnitIdList(String unitId){
		String sql = "select * from office_task_manage where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeTaskManage> getOfficeTaskManageByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_task_manage where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeTaskManage> getOfficeTaskManageListByCondition(String unitId, String dealUserId, String[] states, String queryBeginDate, String queryEndDate, Pagination page){
		//TODO
		StringBuffer sql = new StringBuffer("select * from office_task_manage where is_deleted = 0");
		List<Object> args = new ArrayList<Object>();
		if(StringUtils.isNotBlank(unitId)){
			sql.append(" and unit_id = ?");
			args.add(unitId);
		}
		if(StringUtils.isNotBlank(dealUserId)){
			sql.append(" and deal_user_id = ?");
			args.add(dealUserId);
		}
		if(ArrayUtils.isNotEmpty(states)){
			sql.append(" and state in " + SQLUtils.toSQLInString(states));
		}
		if(StringUtils.isNotBlank(queryBeginDate)){
			sql.append(" and str_to_date(date_format(complete_time,'%Y-%m-%d'),'%Y-%m-%d')>=str_to_date(?,'%Y-%m-%d')");
			args.add(queryBeginDate);
		}
		if(StringUtils.isNotBlank(queryEndDate)){
			sql.append(" and str_to_date(date_format(complete_time,'%Y-%m-%d'),'%Y-%m-%d')<=str_to_date(?,'%Y-%m-%d')");
			args.add(queryEndDate);
		}
		sql.append(" order by complete_time desc");
		if(page != null){
			return query(sql.toString(), args.toArray(), new MultiRow(), page);
		} else {
			return query(sql.toString(), args.toArray(), new MultiRow());
		}
	}
}
