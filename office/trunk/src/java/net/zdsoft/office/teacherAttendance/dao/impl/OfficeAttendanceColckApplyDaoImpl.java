package net.zdsoft.office.teacherAttendance.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.dao.OfficeAttendanceColckApplyDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendanceColckApply;
import net.zdsoft.office.util.Constants;

import org.apache.commons.lang.StringUtils;
/**
 * 考勤补卡申请表
 * @author 
 * 
 */
public class OfficeAttendanceColckApplyDaoImpl extends BaseDao<OfficeAttendanceColckApply> implements OfficeAttendanceColckApplyDao{

	@Override
	public OfficeAttendanceColckApply setField(ResultSet rs) throws SQLException{
		OfficeAttendanceColckApply officeAttendanceColckApply = new OfficeAttendanceColckApply();
		officeAttendanceColckApply.setId(rs.getString("id"));
		officeAttendanceColckApply.setUnitId(rs.getString("unit_id"));
		officeAttendanceColckApply.setFlowId(rs.getString("flow_id"));
		officeAttendanceColckApply.setApplyUserId(rs.getString("apply_user_id"));
		officeAttendanceColckApply.setApplyStatus(rs.getInt("apply_status"));
		officeAttendanceColckApply.setAttenceDate(rs.getTimestamp("attence_date"));
		officeAttendanceColckApply.setType(rs.getString("type"));
		officeAttendanceColckApply.setReason(rs.getString("reason"));
		officeAttendanceColckApply.setIsdeleted(rs.getBoolean("is_deleted"));
		officeAttendanceColckApply.setCreationTime(rs.getTimestamp("create_time"));
		return officeAttendanceColckApply;
	}

	@Override
	public OfficeAttendanceColckApply save(OfficeAttendanceColckApply officeAttendanceColckApply){
		String sql = "insert into office_attendance_colck_apply(id, unit_id, flow_id, apply_user_id, apply_status, attence_date, type, reason, is_deleted, create_time) values(?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeAttendanceColckApply.getId())){
			officeAttendanceColckApply.setId(createId());
		}
		if(officeAttendanceColckApply.getCreationTime()==null){
			officeAttendanceColckApply.setCreationTime(new Date());
		}
		Object[] args = new Object[]{
			officeAttendanceColckApply.getId(), officeAttendanceColckApply.getUnitId(), 
			officeAttendanceColckApply.getFlowId(), officeAttendanceColckApply.getApplyUserId(), 
			officeAttendanceColckApply.getApplyStatus(), officeAttendanceColckApply.getAttenceDate(), 
			officeAttendanceColckApply.getType(), officeAttendanceColckApply.getReason(), 
			officeAttendanceColckApply.getIsdeleted(), officeAttendanceColckApply.getCreationTime()
		};
		update(sql, args, new int[]{
				Types.CHAR,Types.CHAR,
				Types.CHAR,Types.CHAR,
				Types.INTEGER, Types.DATE,
				Types.VARCHAR,Types.VARCHAR,
				Types.INTEGER,Types.TIMESTAMP,
		});
		return officeAttendanceColckApply;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_attendance_colck_apply where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeAttendanceColckApply officeAttendanceColckApply){
		String sql = "update office_attendance_colck_apply set unit_id = ?, flow_id = ?, apply_user_id = ?, apply_status = ?, attence_date = ?, type = ?, reason = ?, is_deleted = ?, create_time = ? where id = ?";
		Object[] args = new Object[]{
			officeAttendanceColckApply.getUnitId(), officeAttendanceColckApply.getFlowId(), 
			officeAttendanceColckApply.getApplyUserId(), officeAttendanceColckApply.getApplyStatus(), 
			officeAttendanceColckApply.getAttenceDate(), officeAttendanceColckApply.getType(), 
			officeAttendanceColckApply.getReason(), officeAttendanceColckApply.getIsdeleted(), 
			officeAttendanceColckApply.getCreationTime(),
			officeAttendanceColckApply.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeAttendanceColckApply getOfficeAttendanceColckApplyById(String id){
		String sql = "select * from office_attendance_colck_apply where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}
	
	public List<OfficeAttendanceColckApply> getListByDate(String userId, String startDate, String endDate){
		String sql = "select * from office_attendance_colck_apply where apply_user_id = ? and attence_date between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd')";
		return query(sql, new Object[]{userId, startDate, endDate}, new MultiRow());
	}
	
	public OfficeAttendanceColckApply getObjByDateType(String userId, String attenceDate, String type){
		String sql = "select * from office_attendance_colck_apply where apply_user_id = ? and attence_date = ? and type = ?";
		return query(sql, new Object[]{userId, DateUtils.string2Date(attenceDate, "yyyy-MM-dd"), type}, new SingleRow());
	}
	
	@Override
	public List<OfficeAttendanceColckApply> getApplyList(String userId, String unitId,int applyStatus,Pagination page){
		StringBuffer sql=new StringBuffer("select * from office_attendance_colck_apply where unit_id=? and apply_user_id=? ");
		if(page==null){
			return query(sql.toString(), new String[]{unitId,userId},new MultiRow());
		}else{
			if (applyStatus!=Constants.LEAVE_APPLY_ALL) {
				sql.append(" and apply_status="+applyStatus);
			}else{
				sql.append(" and apply_status!="+Constants.APPLY_STATE_INVALID);
			}
			sql.append(" order by apply_status, create_time desc ");
			return query(sql.toString(), new String[]{unitId,userId}, new MultiRow(),page);
		}
	}
	@Override
	public List<OfficeAttendanceColckApply> HaveDoAudit(String userId, Pagination page){
		page.setUseCursor(true);
		List<Object> obj = new ArrayList<Object>();
		String findSql = "select distinct colck.* from office_attendance_colck_apply colck,jbpm_hi_task task where  colck.flow_id = task.proc_inst_id ";
		StringBuffer sb = new StringBuffer();
		sb.append(findSql);
		sb.append(" and colck.apply_status >=3 and colck.apply_status != 8");
		sb.append(" and task.ASSIGNEE_ID =?");
		obj.add(userId);
		sb.append(" order by  colck.create_time desc,apply_status");
		return query(sb.toString(),obj.toArray(), new MultiRow(),page);
	}
	@Override
	public Map<String,OfficeAttendanceColckApply> getOfficeAttendanceClockByUnitIdMap(String unitId,
								String[] userIds,String startTimeStr,String endTimeStr){
		String sql="select * from office_attendance_colck_apply where unit_id=? and attence_date between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd')";
		if(userIds!=null && userIds.length>0){
			sql+=" and apply_user_id in";
			return queryForInSQL(sql, new Object[]{unitId,startTimeStr,endTimeStr},userIds, new MapRowMapper<String,OfficeAttendanceColckApply>(){
				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("apply_user_id")+"_"+DateUtils.date2String(rs.getTimestamp("attence_date"),"yyyy-MM-dd")+"_"+rs.getString("type");
				}
				@Override
				public OfficeAttendanceColckApply mapRowValue(ResultSet rs, int rowNum)
						throws SQLException {
					return setField(rs);
				}
				
			});
		}else{
			return queryForMap(sql, new Object[]{unitId,startTimeStr,endTimeStr} , new MapRowMapper<String,OfficeAttendanceColckApply>(){
				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("apply_user_id")+"_"+DateUtils.date2String(rs.getTimestamp("attence_date"))+"_"+rs.getString("type");
				}

				@Override
				public OfficeAttendanceColckApply mapRowValue(ResultSet rs, int rowNum)
						throws SQLException {
					return setField(rs);
				}
			});
		}
	}
	@Override
	public Map<String, OfficeAttendanceColckApply> getAttendanceColckApplyMapByFlowIds(String[] flowIds) {
		String sql = "select * from office_attendance_colck_apply where flow_id in";
		return queryForInSQL(sql, null, flowIds, new MapRowMapper<String, OfficeAttendanceColckApply>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("flow_id");
			}
			@Override
			public OfficeAttendanceColckApply mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}
}