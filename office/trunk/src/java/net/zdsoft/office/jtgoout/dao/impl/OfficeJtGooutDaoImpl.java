package net.zdsoft.office.jtgoout.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
import net.zdsoft.office.jtgoout.dao.OfficeJtGooutDao;
import net.zdsoft.office.jtgoout.entity.OfficeJtGoout;

import org.apache.commons.lang.StringUtils;
/**
 * office_jt_goout
 * @author 
 * 
 */
public class OfficeJtGooutDaoImpl extends BaseDao<OfficeJtGoout> implements OfficeJtGooutDao{

	@Override
	public OfficeJtGoout setField(ResultSet rs) throws SQLException{
		OfficeJtGoout officeJtGoout = new OfficeJtGoout();
		officeJtGoout.setId(rs.getString("id"));
		officeJtGoout.setUnitId(rs.getString("unit_id"));
		officeJtGoout.setApplyUserId(rs.getString("apply_user_id"));
		officeJtGoout.setInvalidUserId(rs.getString("invalidUserId"));
		officeJtGoout.setType(rs.getString("type"));
		officeJtGoout.setFlowId(rs.getString("flow_id"));
		officeJtGoout.setStartTime(rs.getTimestamp("start_time"));
		officeJtGoout.setEndTime(rs.getTimestamp("end_time"));
		officeJtGoout.setState(rs.getString("state"));
		officeJtGoout.setCreateTime(rs.getTimestamp("create_time"));
		officeJtGoout.setIsDeleted(rs.getBoolean("is_deleted"));
		return officeJtGoout;
	}

	@Override
	public OfficeJtGoout save(OfficeJtGoout officeJtGoout){
		String sql = "insert into office_jt_goout(id, unit_id, apply_user_id, type, flow_id, start_time, end_time, state, create_time,is_deleted,invalidUserId) values(?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeJtGoout.getId())){
			officeJtGoout.setId(createId());
		}
		Object[] args = new Object[]{
			officeJtGoout.getId(), officeJtGoout.getUnitId(), 
			officeJtGoout.getApplyUserId(), officeJtGoout.getType(), 
			officeJtGoout.getFlowId(), officeJtGoout.getStartTime(), 
			officeJtGoout.getEndTime(), officeJtGoout.getState(), 
			officeJtGoout.getCreateTime(),officeJtGoout.getIsDeleted(),
			officeJtGoout.getInvalidUserId()
		};
		update(sql, args);
		return officeJtGoout;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_jt_goout where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public void deleteRevoke(String id) {
		String sql="update office_jt_goout set is_deleted=1 where id=?";
		update(sql, id);
	}

	@Override
	public Integer update(OfficeJtGoout officeJtGoout){
		String sql = "update office_jt_goout set unit_id = ?, apply_user_id = ?, type = ?, flow_id = ?, start_time = ?, end_time = ?, state = ?, create_time = ?,is_deleted = ?,invalidUserId=? where id = ?";
		Object[] args = new Object[]{
			officeJtGoout.getUnitId(), officeJtGoout.getApplyUserId(), 
			officeJtGoout.getType(), officeJtGoout.getFlowId(), 
			officeJtGoout.getStartTime(), officeJtGoout.getEndTime(), 
			officeJtGoout.getState(), officeJtGoout.getCreateTime(), 
			officeJtGoout.getIsDeleted(),officeJtGoout.getInvalidUserId(),
			officeJtGoout.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeJtGoout getOfficeJtGooutById(String id){
		String sql = "select * from office_jt_goout where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeJtGoout> getOfficeJtGooutMapByIds(String[] ids){
		String sql = "select * from office_jt_goout where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutList(){
		String sql = "select * from office_jt_goout where is_deleted =0";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutPage(Pagination page){
		String sql = "select * from office_jt_goout where is_deleted =0";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutByUnitIdList(String unitId){
		String sql = "select * from office_jt_goout where unit_id = ? and is_deleted =0";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_jt_goout where unit_id = ? and is_deleted =0";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutByUnitIdAndState(String unitId,
			String userId, String state, Pagination page) {
		List<Object> args=new ArrayList<Object>();
		StringBuffer sb=new StringBuffer("select * from office_jt_goout where unit_id = ? and apply_user_id=? and is_deleted =0");
		args.add(unitId);
		args.add(userId);
		if(StringUtils.isNotBlank(state)){
			sb.append(" and state=?");
			args.add(state);
		}else{
			sb.append(" and state != 8");
		}
		sb.append(" order by state,create_time desc");
		if(page!=null){
			return query(sb.toString(), args.toArray(), new MultiRow(), page);
		}else{
			return query(sb.toString(), args.toArray(), new MultiRow());
		}
	}

	@Override
	public Map<String, OfficeJtGoout> getOfficeJtGooutMapByFlowId(
			String[] flowId, Pagination page) {
		String sql="select * from office_jt_goout where is_deleted =0 and flow_id in";
		return queryForInSQL(sql, null, flowId, new MapRowMapper<String,OfficeJtGoout>() {

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("flow_id");
			}

			@Override
			public OfficeJtGoout mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutsByUnitIds(String[] unitIds) {
		String sql="select * from office_jt_goout where is_deleted =0 and unit_id in";
		return queryForInSQL(sql, null, unitIds, new MultiRow());
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutsByFlowIds(String[] flowIds,
			Pagination page) {
		String sql="select * from office_jt_goout where is_deleted =0 and flow_id in";
		StringBuffer sb=new StringBuffer();
		sb.append(sql);
		if(page!=null){
			return queryForInSQL(sb.toString(), null, flowIds, new MultiRow(), " order by state,create_time desc", page);
		}else{
			return queryForInSQL(sb.toString(), null, flowIds, new MultiRow(), " order by state,create_time desc");
		}
	}

	@Override
	public List<OfficeJtGoout> HaveDoneAudit(String userId, boolean invalid,
			Pagination page) {
		List<Object> obj = new ArrayList<Object>();
		String sql = "select distinct goout.* from office_jt_goout goout,jbpm_hi_task task where  goout.flow_id = task.proc_inst_id and goout.is_deleted =0";
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		if(invalid){
			sb.append(" and goout.state = 8");
		}else{
			sb.append(" and goout.state >=3 and goout.state != 8");
		}
		sb.append(" and task.ASSIGNEE_ID =?");
		obj.add(userId);
		sb.append(" order by  goout.create_time desc,goout.state");
		if(page!=null){
			return query(sb.toString(),obj.toArray(), new MultiRow(),page);
		}else{
			return query(sb.toString(), obj.toArray(), new MultiRow());
		}
	}
	
	public List<OfficeJtGoout> getListByStarttimeAndEndtime(String unitId, Date startTime,Date endTime){
		//获取日期范围内的外出记录
		StringBuffer sql = new StringBuffer("select * from office_jt_goout where state = '3' and is_deleted=0 and unit_id = ?");
		List<Object>list=new ArrayList<Object>();
		list.add(unitId);
		if(startTime!=null){
			sql.append(" and to_char(end_time,'yyyy-MM-dd') >=?");
			list.add(DateUtils.date2String(startTime, "yyyy-MM-dd"));
		}
		if(endTime!=null){
			sql.append(" and to_char(start_time,'yyyy-MM-dd')<=?");
			list.add(DateUtils.date2String(endTime, "yyyy-MM-dd"));
		}
		return query(sql.toString(), list.toArray(), new MultiRow());
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutsByUnitNameAndType(
			String[] unitIds, String type, String startTime, String endTime,
			Pagination page) {
		StringBuffer sb=new StringBuffer("select * from office_jt_goout where is_deleted =0");
		sb.append(" and (state=3 or state=4)");
		List<Object> args=new ArrayList<Object>();
		sb.append(" and unit_id in");
		sb.append(net.zdsoft.leadin.util.SQLUtils.toSQLInString(unitIds));
		if(StringUtils.isNotBlank(type)){
			sb.append(" and type=?");
			args.add(type);
		}
		if(StringUtils.isNotBlank(startTime)){
			sb.append(" and to_date(to_char(start_time,'yyyy-MM-dd'),'yyyy-MM-dd')>=to_date(?,'yyyy-MM-dd')");
			args.add(startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			sb.append(" and to_date(to_char(start_time,'yyyy-MM-dd'),'yyyy-MM-dd')<to_date(?,'yyyy-MM-dd')");
			args.add(endTime);
		}
		if(page!=null){
			return query(sb.toString(), args.toArray(), new MultiRow(), page);
		}else{
			return query(sb.toString(), args.toArray(), new MultiRow());
		}
	}

	@Override
	public List<OfficeJtGoout> getOfficeJtGooutsByUnitAndType(String unitId,
			String type, Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer("select * from office_jt_goout where is_deleted =0");
		sb.append(" and state = 3 ");
		List<Object> args=new ArrayList<Object>();
		sb.append(" and unit_id = ? ");
		args.add(unitId);
		if(StringUtils.isNotBlank(type)){
			sb.append(" and type=?");
			args.add(type);
		}
		if(startTime != null){
			sb.append(" and start_time >= ?");
			args.add(startTime);
		}
		if(endTime != null){
			sb.append(" and end_time <= ?");
			args.add(endTime);
		}
		return query(sb.toString(), args.toArray(), new MultiRow());
	}

	@Override
	public List<OfficeJtGoout> getListByUnitIdAndDate(String unitId, Date date) {
		StringBuffer sql = new StringBuffer("select * from office_jt_goout where state =3 and is_deleted=0 and unit_id = ?");
		List<Object>list=new ArrayList<Object>();
		list.add(unitId);
		if(date!=null){
			sql.append(" and to_date(to_char(end_time,'yyyy-MM-dd'),'yyyy-MM-dd')>=to_date(to_char(?,'yyyy-MM-dd'),'yyyy-MM-dd') and to_date(to_char(start_time,'yyyy-MM-dd'),'yyyy-MM-dd')<=to_date(to_char(?,'yyyy-MM-dd'),'yyyy-MM-dd')");
			list.add(date);list.add(date);
		}
		return query(sql.toString(), list.toArray(), new MultiRow());
	}

}
