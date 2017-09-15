package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.convertflow.entity.OfficeConvertFlow;
import net.zdsoft.office.dailyoffice.dao.OfficeGoOutDao;
import net.zdsoft.office.dailyoffice.entity.OfficeGoOut;
import net.zdsoft.office.teacherLeave.entity.OfficeTeacherLeave;
import net.zdsoft.office.util.Constants;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
/**
 * office_go_out
 * @author 
 * 
 */
public class OfficeGoOutDaoImpl extends BaseDao<OfficeGoOut> implements OfficeGoOutDao{

	@Override
	public OfficeGoOut setField(ResultSet rs) throws SQLException{
		OfficeGoOut officeGoOut = new OfficeGoOut();
		officeGoOut.setId(rs.getString("id"));
		officeGoOut.setBeginTime(rs.getTimestamp("begin_time"));
		officeGoOut.setEndTime(rs.getTimestamp("end_time"));
		officeGoOut.setHours(rs.getDouble("hours"));
		officeGoOut.setTripReason(rs.getString("trip_reason"));
		officeGoOut.setState(rs.getString("state"));
		officeGoOut.setFlowId(rs.getString("flow_id"));
		officeGoOut.setUnitId(rs.getString("unit_id"));
		officeGoOut.setApplyUserId(rs.getString("apply_user_id"));
		officeGoOut.setOutType(rs.getString("out_type"));
		officeGoOut.setCreateTime(rs.getTimestamp("create_time"));
		officeGoOut.setIsDeleted(rs.getBoolean("is_deleted"));
		officeGoOut.setInvalidUser(rs.getString("invalid_user"));
		return officeGoOut;
	}

	@Override
	public OfficeGoOut save(OfficeGoOut officeGoOut){
		String sql = "insert into office_go_out(id, begin_time, end_time, hours,out_type, trip_reason, state, flow_id, unit_id, apply_user_id, create_time, is_deleted,invalid_user) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeGoOut.getId())){
			officeGoOut.setId(createId());
		}
		Object[] args = new Object[]{
			officeGoOut.getId(), officeGoOut.getBeginTime(), 
			officeGoOut.getEndTime(), officeGoOut.getHours(),officeGoOut.getOutType(), 
			officeGoOut.getTripReason(), officeGoOut.getState(), 
			officeGoOut.getFlowId(), officeGoOut.getUnitId(), 
			officeGoOut.getApplyUserId(), officeGoOut.getCreateTime(), 
			officeGoOut.getIsDeleted(),officeGoOut.getInvalidUser()
		};
		update(sql, args);
		return officeGoOut;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_go_out where id in";
		return updateForInSQL(sql, null, ids);
	}
	@Override
	public List<OfficeGoOut> getOfficeGoOutByIds(String[] ids) {
		String sql = "select * from office_go_out where id in";
		return queryForInSQL(sql, null, ids, new MultiRow());
	}

	@Override
	public Integer update(OfficeGoOut officeGoOut){
		String sql = "update office_go_out set begin_time = ?, end_time = ?, hours = ?, out_type=?,trip_reason = ?, state = ?, flow_id = ?, unit_id = ?, apply_user_id = ?, create_time = ?, is_deleted = ?,invalid_user = ? where id = ?";
		Object[] args = new Object[]{
			officeGoOut.getBeginTime(), officeGoOut.getEndTime(), 
			officeGoOut.getHours(),officeGoOut.getOutType(), officeGoOut.getTripReason(), 
			officeGoOut.getState(), officeGoOut.getFlowId(), 
			officeGoOut.getUnitId(), officeGoOut.getApplyUserId(), 
			officeGoOut.getCreateTime(), officeGoOut.getIsDeleted(), 
			officeGoOut.getInvalidUser(),officeGoOut.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeGoOut getOfficeGoOutById(String id){
		String sql = "select * from office_go_out where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeGoOut> getOfficeGoOutMapByIds(String[] ids){
		String sql = "select * from office_go_out where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeGoOut> getOfficeGoOutList(){
		String sql = "select * from office_go_out";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeGoOut> getOfficeGoOutPage(Pagination page){
		String sql = "select * from office_go_out";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeGoOut> getOfficeGoOutByUnitIdList(String unitId){
		String sql = "select * from office_go_out where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeGoOut> getOfficeGoOutByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_go_out where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeGoOut> getOfficeGoOutByUnitIdUserIdPage(String unitId,
			String userId, String states, Pagination page) {
		List<Object> argsList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from office_go_out where unit_id = ? ");
		argsList.add(unitId);
		if(StringUtils.isNotBlank(states) && Integer.parseInt(states)!=Constants.LEAVE_APPLY_ALL){
				sql.append(" and state = ?");
				argsList.add(Integer.parseInt(states));
		}else{
			sql.append(" and state != 8");
		}
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and apply_user_id = ?");
			argsList.add(userId);
		}
		sql.append(" order by state,create_time desc");
		if(page != null)
			return query(sql.toString(), argsList.toArray(), new MultiRow(), page);
		else
			return query(sql.toString(), argsList.toArray(), new MultiRow());
	}

	@Override
	public boolean isExistConflict(String id, String applyUserId,
			Date beginTime, Date endTime) {
		String sql = "select count(1) from office_go_out where state !=4 and state !=8 and apply_user_id = ? and ((begin_time <= ? and end_time >= ?) or (begin_time <= ? and end_time >= ?) or (begin_time >= ? and end_time <= ?))";
		
		String sql1 = "select count(1) from office_go_out where state !=4 and state !=8 and apply_user_id = ? and id != ? and ((begin_time <= ? and end_time >= ?) or (begin_time <= ? and end_time >= ?) or (begin_time >= ? and end_time <= ?))";
		int i = 0;
		if(StringUtils.isNotBlank(id)){
			i = queryForInt(sql1, new Object[] {applyUserId, id, beginTime, beginTime, endTime, endTime, beginTime, endTime});
		}else{
			i = queryForInt(sql, new Object[] {applyUserId, beginTime, beginTime, endTime, endTime, beginTime, endTime});
		}
		if(i > 0){
			return true;
		}
		return false;
	}
	@Override
	public Map<String, OfficeGoOut> getOfficeBusinessTripMapByFlowIds(
			String[] array) {
		String sql = "select * from office_go_out where flow_id in";
		return queryForInSQL(sql, null, array, new MapRowMapper<String, OfficeGoOut>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("flow_id");
			}

			@Override
			public OfficeGoOut mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public List<OfficeGoOut> HaveDoAudit(String userId,boolean invalid, Pagination page) {
		page.setUseCursor(true);
		List<Object> obj = new ArrayList<Object>();
		String findSql = "select distinct leave.* from office_go_out leave,jbpm_hi_task task where  leave.flow_id = task.proc_inst_id ";
		StringBuffer sb = new StringBuffer();
		sb.append(findSql);
		if(invalid){
			sb.append(" and (leave.state=3 or leave.state=4)");
		}else{
			sb.append(" and leave.state=8");
		}
		sb.append(" and task.ASSIGNEE_ID =?");
		obj.add(userId);
		sb.append(" order by  state,create_time desc");
		return query(sb.toString(),obj.toArray(), new MultiRow(),page);
	}
	@Override
	public List<OfficeGoOut> getStatistics(String unitId,Date startTime,Date endTime,String[] userIds){
		StringBuilder sql=new StringBuilder("select count(id) outNum, sum(hours) sumHours, out_type as out_type,apply_user_id as apply_user_id from office_go_out where unit_id=? and state='3' ");
		List<Object> obj=new ArrayList<Object>();
		obj.add(unitId);
		if(startTime!=null){
			sql.append(" and begin_time >= ? ");
			obj.add(startTime);
		}
		if(endTime!=null){
			sql.append(" and begin_time < ? ");
			obj.add(endTime);
		}
		if(ArrayUtils.isNotEmpty(userIds)){
			sql.append(" and apply_user_id in  ");
			return queryForInSQL(sql.toString(), obj.toArray(), userIds, new MultiRowMapper<OfficeGoOut>() {
				@Override
				public OfficeGoOut mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeGoOut officeGoOut=new OfficeGoOut();
					officeGoOut.setSumHours(rs.getDouble("sumHours"));
					officeGoOut.setOutNum(rs.getInt("outNum"));
					officeGoOut.setApplyUserId(rs.getString("apply_user_id"));
					officeGoOut.setOutType(rs.getString("out_type"));
					return officeGoOut;
				}
			}," group by  apply_user_id,out_type");
		}else{
			sql.append(" group by  apply_user_id,out_type");
			return query(sql.toString(), obj.toArray(),  new MultiRowMapper<OfficeGoOut>(){
				@Override
				public OfficeGoOut mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeGoOut officeGoOut=new OfficeGoOut();
					officeGoOut.setSumHours(rs.getDouble("sumHours"));
					officeGoOut.setOutNum(rs.getInt("outNum"));
					officeGoOut.setApplyUserId(rs.getString("apply_user_id"));
					officeGoOut.setOutType(rs.getString("out_type"));
					return officeGoOut;
				}
			});
		}
	}
}
