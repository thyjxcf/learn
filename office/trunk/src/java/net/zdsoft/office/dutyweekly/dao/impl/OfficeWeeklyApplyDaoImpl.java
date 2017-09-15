package net.zdsoft.office.dutyweekly.dao.impl;

import java.sql.*;
import java.util.*;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.dutyinformation.entity.OfficeDutyApply;
import net.zdsoft.office.dutyweekly.entity.OfficeWeeklyApply;
import net.zdsoft.office.dutyweekly.dao.OfficeWeeklyApplyDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
/**
 * office_weekly_apply
 * @author 
 * 
 */
public class OfficeWeeklyApplyDaoImpl extends BaseDao<OfficeWeeklyApply> implements OfficeWeeklyApplyDao{

	@Override
	public OfficeWeeklyApply setField(ResultSet rs) throws SQLException{
		OfficeWeeklyApply officeWeeklyApply = new OfficeWeeklyApply();
		officeWeeklyApply.setId(rs.getString("id"));
		officeWeeklyApply.setUnitId(rs.getString("unit_id"));
		officeWeeklyApply.setDutyWeeklyId(rs.getString("duty_weekly_id"));
		officeWeeklyApply.setDutyProjectId(rs.getString("duty_project_id"));
		officeWeeklyApply.setDutyRemarkId(rs.getString("duty_remark_id"));
		officeWeeklyApply.setClassId(rs.getString("class_id"));
		officeWeeklyApply.setScore(rs.getInt("score"));
		officeWeeklyApply.setDutyDate(rs.getTimestamp("duty_date"));
		return officeWeeklyApply;
	}

	@Override
	public OfficeWeeklyApply save(OfficeWeeklyApply officeWeeklyApply){
		String sql = "insert into office_weekly_apply(id, unit_id, duty_weekly_id, duty_project_id,duty_remark_id, class_id, score,duty_date) values(?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeWeeklyApply.getId())){
			officeWeeklyApply.setId(createId());
		}
		Object[] args = new Object[]{
			officeWeeklyApply.getId(), officeWeeklyApply.getUnitId(), 
			officeWeeklyApply.getDutyWeeklyId(), officeWeeklyApply.getDutyProjectId(),officeWeeklyApply.getDutyRemarkId(), 
			officeWeeklyApply.getClassId(), officeWeeklyApply.getScore(),officeWeeklyApply.getDutyDate()
		};
		update(sql, args);
		return officeWeeklyApply;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_weekly_apply where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeWeeklyApply officeWeeklyApply){
		String sql = "update office_weekly_apply set unit_id = ?, duty_weekly_id = ?, duty_project_id = ?,duty_remark_id=?, class_id = ?, score = ?,duty_date=? where id = ?";
		Object[] args = new Object[]{
			officeWeeklyApply.getUnitId(), officeWeeklyApply.getDutyWeeklyId(), 
			officeWeeklyApply.getDutyProjectId(),officeWeeklyApply.getDutyRemarkId(), officeWeeklyApply.getClassId(), 
			officeWeeklyApply.getScore(),officeWeeklyApply.getDutyDate(), officeWeeklyApply.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeWeeklyApply getOfficeWeeklyApplyById(String id){
		String sql = "select * from office_weekly_apply where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeWeeklyApply> getOfficeWeeklyApplyMapByIds(String[] ids){
		String sql = "select * from office_weekly_apply where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeWeeklyApply> getOfficeWeeklyApplyList(){
		String sql = "select * from office_weekly_apply";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeWeeklyApply> getOfficeWeeklyApplyPage(Pagination page){
		String sql = "select * from office_weekly_apply";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeWeeklyApply> getOfficeWeeklyApplyByUnitIdList(String unitId){
		String sql = "select * from office_weekly_apply where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeWeeklyApply> getOfficeWeeklyApplyByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_weekly_apply where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public List<OfficeWeeklyApply> getOfficeWeeklyAppliesByUnitId(
			String unitId, String[] projectId) {
		String sql="select * from office_weekly_apply where unit_id = ? and duty_project_id in";
		return queryForInSQL(sql, new String[]{unitId}, projectId, new MultiRow());
	}

	@Override
	public void deleteRecordes(String unitId, String dutyWeeklyId, Date dutyDate) {
		String sql="delete from office_weekly_apply where unit_id = ? and duty_weekly_id=? and duty_date=?";
		update(sql, new Object[]{unitId,dutyWeeklyId,dutyDate});
	}

	@Override
	public void batchSave(List<OfficeWeeklyApply> officeWeeklyApplys) {
		String sql="insert into office_weekly_apply(id, unit_id, duty_weekly_id, duty_project_id,duty_remark_id, class_id, score,duty_date) values(?,?,?,?,?,?,?,?)";
		List<Object[]> objects=new ArrayList<Object[]>();
		for (OfficeWeeklyApply officeWeeklyApply : officeWeeklyApplys) {
			if(StringUtils.isBlank(officeWeeklyApply.getId())){
				officeWeeklyApply.setId(getGUID());
			}
			Object[] object=new Object[]{officeWeeklyApply.getId(),officeWeeklyApply.getUnitId(),officeWeeklyApply.getDutyWeeklyId(),officeWeeklyApply.getDutyProjectId(),officeWeeklyApply.getDutyRemarkId()
					,officeWeeklyApply.getClassId(),officeWeeklyApply.getScore(),officeWeeklyApply.getDutyDate()};
			objects.add(object);
		}
		int[] argTypes=new int[]{Types.CHAR,Types.CHAR,Types.CHAR,Types.CHAR,Types.CHAR,
				Types.CHAR,Types.INTEGER,Types.DATE};
		batchUpdate(sql, objects, argTypes);
	}

	@Override
	public Map<String, OfficeWeeklyApply> getOfficeWeeklyApplyMapByUnitIdAndWeeklyId(
			String unitId, String weeklyId, Date dutyDate) {
		String sql="select * from office_weekly_apply where unit_id = ? and duty_weekly_id=? and duty_date=?";
		return queryForMap(sql, new Object[]{unitId,weeklyId,dutyDate}, new MapRowMapper<String, OfficeWeeklyApply>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("duty_project_id")+"_"+rs.getString("class_id");
			}

			@Override
			public OfficeWeeklyApply mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
			
		});
	}

	@Override
	public Map<String, OfficeWeeklyApply> getOfficeWMapCount(String unitId, String weeklyId,Date[] dutyTime) {
		//select  duty_project_id,class_id, sum(score) sumscore from office_weekly_apply where unit_id=? and duty_weekly_id=? group by duty_project_id,class_id order by duty_project_id
		List<Object> args=new ArrayList<Object>();
		StringBuffer sb=new StringBuffer("select  duty_project_id,class_id, sum(score) sumscore from office_weekly_apply where unit_id=? ");
		args.add(unitId);
		if(StringUtils.isNotBlank(weeklyId)){
			sb.append(" and duty_weekly_id=?");
			args.add(weeklyId);
		}
		if(ArrayUtils.isEmpty(dutyTime)){
			sb.append(" group by duty_project_id,class_id order by duty_project_id");
			return queryForMap(sb.toString(), args.toArray(), new MapRowMapper<String, OfficeWeeklyApply>(){
				
				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("duty_project_id")+"_"+rs.getString("class_id");
				}
				
				@Override
				public OfficeWeeklyApply mapRowValue(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeWeeklyApply officeWeeklyApply = new OfficeWeeklyApply();
					officeWeeklyApply.setDutyProjectId(rs.getString("duty_project_id"));
					officeWeeklyApply.setDutyRemarkId(rs.getString("class_id"));
					officeWeeklyApply.setScore(rs.getInt("sumscore"));
					return officeWeeklyApply;
				}
				
			});
		}else{
			sb.append(" and duty_date in");
			return queryForInSQL(sb.toString(), args.toArray(), dutyTime, new MapRowMapper<String,OfficeWeeklyApply>() {

				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("duty_project_id")+"_"+rs.getString("class_id");
				}

				@Override
				public OfficeWeeklyApply mapRowValue(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeWeeklyApply officeWeeklyApply = new OfficeWeeklyApply();
					officeWeeklyApply.setDutyProjectId(rs.getString("duty_project_id"));
					officeWeeklyApply.setDutyRemarkId(rs.getString("class_id"));
					officeWeeklyApply.setScore(rs.getInt("sumscore"));
					return officeWeeklyApply;
				}
			}, "group by duty_project_id,class_id order by duty_project_id");
		}
	}

	@Override
	public Map<String, OfficeWeeklyApply> getOfficeCountMapByUnitIdAndWeeklyId(
			String unitId, String weeklyId, Date dutyDate) {
		String sql="select  duty_project_id,class_id, sum(score) sumscore from office_weekly_apply where unit_id=? and duty_weekly_id=? and duty_date=? group by duty_project_id,class_id order by duty_project_id";
		return queryForMap(sql, new Object[]{unitId,weeklyId,dutyDate}, new MapRowMapper<String, OfficeWeeklyApply>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("duty_project_id")+"_"+rs.getString("class_id");
			}

			@Override
			public OfficeWeeklyApply mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				OfficeWeeklyApply officeWeeklyApply = new OfficeWeeklyApply();
				officeWeeklyApply.setDutyProjectId(rs.getString("duty_project_id"));
				officeWeeklyApply.setDutyRemarkId(rs.getString("class_id"));
				officeWeeklyApply.setScore(rs.getInt("sumscore"));
				return officeWeeklyApply;
			}
			
		});
	}

	@Override
	public List<OfficeWeeklyApply> getOfficeWMap(String unitId, String acadayear,
			String smster, String week, Date startTime, Date endTime,
			Date[] dutyTime) {
		List<Object> args=new ArrayList<Object>();
		StringBuffer sb=new StringBuffer("select * from office_weekly_apply where unit_id=?");
		args.add(unitId);
		sb.append(" and exists(select 1 from office_duty_weekly where office_duty_weekly.id=office_weekly_apply.duty_weekly_id");
		if(StringUtils.isNotBlank(acadayear)){
			sb.append(" and acadyear=?");
			args.add(acadayear);
		}
		if(StringUtils.isNotBlank(smster)){
			sb.append(" and semester=?");
			args.add(smster);
		}
		if(StringUtils.isNotBlank(week)){
			sb.append(" and week=?");
			args.add(week);
		}
		if(startTime!=null){
			sb.append(" and week_start_time=?");
			args.add(startTime);
		}
		if(endTime!=null){
			sb.append(" and week_end_time=?");
			args.add(endTime);
		}
		sb.append(")");
		if(ArrayUtils.isEmpty(dutyTime)){
			return query(sb.toString(), args.toArray(), new MultiRow());
		}else{
			return null;
		}
	}

	@Override
	public List<OfficeWeeklyApply> getOfficeWeeklyApplies(String unitId,
			String weeklyId) {
		String sql="select * from office_weekly_apply where unit_id=? and duty_weekly_id=?";
		return query(sql, new Object[]{unitId,weeklyId}, new MultiRow());
	}
	
}
