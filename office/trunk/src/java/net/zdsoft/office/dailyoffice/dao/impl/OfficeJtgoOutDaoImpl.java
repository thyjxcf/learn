package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.dailyoffice.entity.OfficeJtgoOut;
import net.zdsoft.office.dailyoffice.dao.OfficeJtgoOutDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
/**
 * office_jtgo_out
 * @author 
 * 
 */
public class OfficeJtgoOutDaoImpl extends BaseDao<OfficeJtgoOut> implements OfficeJtgoOutDao{

	@Override
	public OfficeJtgoOut setField(ResultSet rs) throws SQLException{
		OfficeJtgoOut officeJtgoOut = new OfficeJtgoOut();
		officeJtgoOut.setId(rs.getString("id"));
		officeJtgoOut.setUnitId(rs.getString("unit_id"));
		officeJtgoOut.setInvalidUser(rs.getString("invalid_user"));
		officeJtgoOut.setDays(rs.getString("days"));
		officeJtgoOut.setTripPerson(rs.getString("trip_person"));
		officeJtgoOut.setTripReason(rs.getString("trip_reason"));
		officeJtgoOut.setState(rs.getString("state"));
		officeJtgoOut.setOutType(rs.getString("out_type"));
		officeJtgoOut.setFlowId(rs.getString("flow_id"));
		officeJtgoOut.setApplyUserId(rs.getString("apply_user_id"));
		officeJtgoOut.setCreateTime(rs.getTimestamp("create_time"));
		officeJtgoOut.setIsDeleted(rs.getBoolean("is_deleted"));
		return officeJtgoOut;
	}

	@Override
	public OfficeJtgoOut save(OfficeJtgoOut officeJtgoOut){
		String sql = "insert into office_jtgo_out(id, unit_id,invalid_user, days, trip_person, trip_reason, state, out_type, flow_id, apply_user_id, create_time, is_deleted) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeJtgoOut.getId())){
			officeJtgoOut.setId(createId());
		}
		Object[] args = new Object[]{
			officeJtgoOut.getId(), officeJtgoOut.getUnitId(),officeJtgoOut.getInvalidUser(), 
			officeJtgoOut.getDays(), officeJtgoOut.getTripPerson(), 
			officeJtgoOut.getTripReason(), officeJtgoOut.getState(), 
			officeJtgoOut.getOutType(), officeJtgoOut.getFlowId(), 
			officeJtgoOut.getApplyUserId(), officeJtgoOut.getCreateTime(), 
			officeJtgoOut.getIsDeleted()
		};
		update(sql, args);
		return officeJtgoOut;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_jtgo_out where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeJtgoOut officeJtgoOut){
		String sql = "update office_jtgo_out set unit_id = ?, days = ?,invalid_user=?, trip_person = ?, trip_reason = ?, state = ?, out_type = ?, flow_id = ?, apply_user_id = ?, create_time = ?, is_deleted = ? where id = ?";
		Object[] args = new Object[]{
			officeJtgoOut.getUnitId(), officeJtgoOut.getDays(), officeJtgoOut.getInvalidUser(),
			officeJtgoOut.getTripPerson(), officeJtgoOut.getTripReason(), 
			officeJtgoOut.getState(), officeJtgoOut.getOutType(), 
			officeJtgoOut.getFlowId(), officeJtgoOut.getApplyUserId(), 
			officeJtgoOut.getCreateTime(), officeJtgoOut.getIsDeleted(), 
			officeJtgoOut.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeJtgoOut getOfficeJtgoOutById(String id){
		String sql = "select * from office_jtgo_out where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeJtgoOut> getOfficeJtgoOutMapByIds(String[] ids){
		String sql = "select * from office_jtgo_out where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeJtgoOut> getOfficeJtgoOutList(){
		String sql = "select * from office_jtgo_out";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeJtgoOut> getOfficeJtgoOutPage(Pagination page){
		String sql = "select * from office_jtgo_out";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeJtgoOut> getOfficeJtgoOutByUnitIdList(String unitId){
		String sql = "select * from office_jtgo_out where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeJtgoOut> getOfficeJtgoOutByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_jtgo_out where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeJtgoOut> getOfficeJtgoOutByUnitIdAndStates(String unitId,
			String states, Pagination page) {
		StringBuffer sb=new StringBuffer("select * from office_jtgo_out where unit_id=?");
		List<Object> args=new ArrayList<Object>();
		args.add(unitId);
		if(StringUtils.isNotBlank(states)){
			sb.append(" and state=?");
			args.add(states);
		}else{
			sb.append(" and state!=8");
		}
		sb.append(" order by state,create_time");
		if(page!=null){
			return query(sb.toString(), args.toArray(), new MultiRow(), page);
		}else{
			return query(sb.toString(), args.toArray(), new MultiRow());
		}
	}

	@Override
	public Map<String, OfficeJtgoOut> getOfficeBusinessTripMapByFlowIds(
			String[] array) {
		String sql="select * from office_jtgo_out where flow_id in";
		return queryForInSQL(sql, null, array, new MapRowMapper<String,OfficeJtgoOut>() {

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("flow_id");
			}

			@Override
			public OfficeJtgoOut mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public List<OfficeJtgoOut> doneAudit(String userId, boolean invalid,
			Pagination page) {
		List<Object> obj = new ArrayList<Object>();
		String findSql = "select distinct office_jtgo_out.* from office_jtgo_out,jbpm_hi_task where office_jtgo_out.flow_id = jbpm_hi_task.proc_inst_id ";
		StringBuffer sb = new StringBuffer();
		sb.append(findSql);
		if(invalid){
			sb.append(" and (office_jtgo_out.state=3 or office_jtgo_out.state=4)");
		}else{
			sb.append(" and office_jtgo_out.state=8");
		}
		sb.append(" and jbpm_hi_task.ASSIGNEE_ID =?");
		obj.add(userId);
		sb.append(" order by  state,create_time desc");
		if(page!=null){
			return query(sb.toString(),obj.toArray(), new MultiRow(),page);
		}else{
			return query(sb.toString(), obj.toArray(), new MultiRow());
		}
	}
}
