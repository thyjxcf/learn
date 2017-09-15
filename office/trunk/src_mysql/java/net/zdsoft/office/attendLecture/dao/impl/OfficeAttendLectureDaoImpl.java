package net.zdsoft.office.attendLecture.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.SQLUtils;
import net.zdsoft.office.attendLecture.dao.OfficeAttendLectureDao;
import net.zdsoft.office.attendLecture.entity.OfficeAttendLecture;
/**
 * office_attend_lecture(听课信息表)
 * @author 
 * 
 */
public class OfficeAttendLectureDaoImpl extends BaseDao<OfficeAttendLecture> implements OfficeAttendLectureDao{

	@Override
	public OfficeAttendLecture setField(ResultSet rs) throws SQLException{
		OfficeAttendLecture officeAttendLecture = new OfficeAttendLecture();
		officeAttendLecture.setId(rs.getString("id"));
		officeAttendLecture.setUnitId(rs.getString("unit_id"));
		officeAttendLecture.setFlowId(rs.getString("flow_id"));
		officeAttendLecture.setApplyUserId(rs.getString("apply_user_id"));
		officeAttendLecture.setState(rs.getString("state"));
		officeAttendLecture.setAttendDate(rs.getTimestamp("attend_date"));
		officeAttendLecture.setAttendPeriod(rs.getString("attend_period"));
		officeAttendLecture.setAttendPeriodNum(rs.getString("attend_period_num"));
		officeAttendLecture.setGradeId(rs.getString("grade_id"));
		officeAttendLecture.setClassId(rs.getString("class_id"));
		officeAttendLecture.setSubjectName(rs.getString("subject_name"));
		officeAttendLecture.setTeacherName(rs.getString("teacher_name"));
		officeAttendLecture.setProjectName(rs.getString("project_name"));
		officeAttendLecture.setProjectContent(rs.getString("project_content"));
		officeAttendLecture.setProjectOpinion(rs.getString("project_opinion"));
		officeAttendLecture.setCreateTime(rs.getTimestamp("create_time"));
		officeAttendLecture.setIsdeleted(rs.getBoolean("is_deleted"));
		return officeAttendLecture;
	}

	@Override
	public OfficeAttendLecture save(OfficeAttendLecture officeAttendLecture){
		String sql = "insert into office_attend_lecture(id, unit_id, flow_id, apply_user_id, state, attend_date, attend_period, attend_period_num, grade_id, class_id, subject_name, teacher_name, project_name, project_content, project_opinion, create_time, is_deleted) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeAttendLecture.getId())){
			officeAttendLecture.setId(createId());
		}
		Object[] args = new Object[]{
			officeAttendLecture.getId(), officeAttendLecture.getUnitId(), 
			officeAttendLecture.getFlowId(), officeAttendLecture.getApplyUserId(), 
			officeAttendLecture.getState(), officeAttendLecture.getAttendDate(), 
			officeAttendLecture.getAttendPeriod(), officeAttendLecture.getAttendPeriodNum(), 
			officeAttendLecture.getGradeId(), officeAttendLecture.getClassId(), 
			officeAttendLecture.getSubjectName(), officeAttendLecture.getTeacherName(), 
			officeAttendLecture.getProjectName(), officeAttendLecture.getProjectContent(), 
			officeAttendLecture.getProjectOpinion(), new Date(), 
			officeAttendLecture.getIsdeleted()
		};
		update(sql, args);
		return officeAttendLecture;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_attend_lecture where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeAttendLecture officeAttendLecture){
		String sql = "update office_attend_lecture set unit_id = ?, flow_id = ?, apply_user_id = ?, state = ?, attend_date = ?, attend_period = ?, attend_period_num = ?, grade_id = ?, class_id = ?, subject_name = ?, teacher_name = ?, project_name = ?, project_content = ?, project_opinion = ?, is_deleted = ? where id = ?";
		Object[] args = new Object[]{
			officeAttendLecture.getUnitId(), officeAttendLecture.getFlowId(), 
			officeAttendLecture.getApplyUserId(), officeAttendLecture.getState(), 
			officeAttendLecture.getAttendDate(), officeAttendLecture.getAttendPeriod(), 
			officeAttendLecture.getAttendPeriodNum(), officeAttendLecture.getGradeId(), 
			officeAttendLecture.getClassId(), officeAttendLecture.getSubjectName(), 
			officeAttendLecture.getTeacherName(), officeAttendLecture.getProjectName(), 
			officeAttendLecture.getProjectContent(), officeAttendLecture.getProjectOpinion(), 
			officeAttendLecture.getIsdeleted(), 
			officeAttendLecture.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeAttendLecture getOfficeAttendLectureById(String id){
		String sql = "select * from office_attend_lecture where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public List<OfficeAttendLecture> getOfficeAttendLectureList(String unitId, String userId, String state,
			Date startTime, Date endTime, Pagination page) {
		StringBuffer sbf = new StringBuffer("select * from office_attend_lecture where 1=1");
		List<Object> args = new ArrayList<Object>();
		if(StringUtils.isNotBlank(unitId)){
			sbf.append(" and unit_id=?");
			args.add(unitId);
		}
		if(StringUtils.isNotBlank(userId)){
			sbf.append(" and apply_user_id=?");
			args.add(userId);
		}
		if(StringUtils.isNotBlank(state)){
			sbf.append(" and state=?");
			args.add(state);
		}
		if(startTime!=null){
			sbf.append(" and attend_date>=?");
			args.add(startTime);
		}
		if(endTime!=null){
			sbf.append(" and attend_date<=?");
			args.add(endTime);
		}
		if(page!=null){
			return query(sbf.toString(), args.toArray(), new MultiRow(),page);
		}else{
			return query(sbf.toString(), args.toArray(), new MultiRow());
		}
	}

	@Override
	public Map<String, OfficeAttendLecture> getAttendLectureMap(String[] flowIds,String unitId,Date startTime,
			Date endTime, String applyUserName) {
		StringBuffer sbf = new StringBuffer("select oalt.* from office_attend_lecture oalt,base_user busr where oalt.apply_user_id=busr.id and oalt.is_deleted=0");
		List<Object> args = new ArrayList<Object>();
		if(StringUtils.isNotBlank(unitId)){
			sbf.append(" and oalt.unit_id=?");
			args.add(unitId);
		}
		if(StringUtils.isNotBlank(applyUserName)){
			sbf.append(" and busr.real_name like ?");
			args.add("%"+applyUserName+"%");
		}
		if(startTime!=null){
			sbf.append(" and oalt.attend_date>=?");
			args.add(startTime);
		}
		if(endTime!=null){
			sbf.append(" and oalt.attend_date<=?");
			args.add(endTime);
		}
		sbf.append(" and oalt.flow_id in");
		return queryForInSQL(sbf.toString(), args.toArray(), flowIds, new MapRowMapper<String, OfficeAttendLecture>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("flow_id");
			}

			@Override
			public OfficeAttendLecture mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}

	@Override
	public List<OfficeAttendLecture> getAuditedList(String userId, String[] state,String unitId,Date startTime,
			Date endTime, String applyUserName, Pagination page) {
		page.setUseCursor(true);
		StringBuffer sbf = new StringBuffer("select distinct oalt.* from office_attend_lecture oalt,jbpm_hi_task task,base_user busr where oalt.apply_user_id=busr.id and oalt.flow_id = task.proc_inst_id and oalt.is_deleted=0 ");
		List<Object> args = new ArrayList<Object>();
		if(StringUtils.isNotBlank(unitId)){
			sbf.append(" and oalt.unit_id=?");
			args.add(unitId);
		}
		if(StringUtils.isNotBlank(applyUserName)){
			sbf.append(" and busr.real_name like ?");
			args.add("%"+applyUserName+"%");
		}
		if(startTime!=null){
			sbf.append(" and oalt.attend_date>=?");
			args.add(startTime);
		}
		if(endTime!=null){
			sbf.append(" and oalt.attend_date<=?");
			args.add(endTime);
		}
		sbf.append(" and task.ASSIGNEE_ID =?");
		args.add(userId);
		if(state != null){
			sbf.append(" and oalt.state in").append(
					SQLUtils.toSQLInString(state));
		}
		sbf.append(" order by oalt.state, oalt.create_time desc");
		return query(sbf.toString(), args.toArray(), new MultiRow(), page);
	}

	@Override
	public List<OfficeAttendLecture> getOfficeCountList(String unitId,
			String[] userIds, Date startTime, Date endTime, String applyUserName) {
		StringBuilder sql=new StringBuilder("select apply_user_id,count(a.id) sum from office_attend_lecture a ");
		List<Object> args=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(applyUserName)){
			sql.append(" ,base_user b where a.apply_user_id=b.id and real_name like ? and a.unit_id =? and state='3' ");
			args.add("%"+applyUserName.trim()+"%");
			args.add(unitId);
		}else{
			sql.append(" where unit_id=? and state='3' ");
			args.add(unitId);
		}
		if(startTime!=null){
			sql.append(" and attend_date>= ? ");
			args.add(startTime);	
		}
		if(endTime!=null){
			sql.append("and attend_date<= ?");
			args.add(endTime);				
		}
		if(ArrayUtils.isNotEmpty(userIds)){
			sql.append(" and apply_user_id in");
			return queryForInSQL(sql.toString(), args.toArray(), userIds, new MultiRowMapper<OfficeAttendLecture>(){
				@Override
				public OfficeAttendLecture mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					OfficeAttendLecture office=new OfficeAttendLecture();
					office.setApplyUserId(rs.getString("apply_user_id"));
					office.setLectureNum(rs.getInt("sum"));
					return office;
				}
			},"group by apply_user_id");
		}
		sql.append(" group by apply_user_id");
		return query(sql.toString(), args.toArray(), new MultiRowMapper<OfficeAttendLecture>(){
			@Override
			public OfficeAttendLecture mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OfficeAttendLecture office=new OfficeAttendLecture();
				office.setApplyUserId(rs.getString("apply_user_id"));
				office.setLectureNum(rs.getInt("sum"));
				return office;
			}
		});
	}
	@Override
	public List<OfficeAttendLecture> getOfficeCountInfo(String unitId,
			Date startTime,Date endTime,String[] userIds,String applyUserName,Pagination page){
		StringBuilder sql=new StringBuilder("select a.* from office_attend_lecture a");
		List<Object> args=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(applyUserName)){
			sql.append(" ,base_user b where a.apply_user_id=b.id and real_name like ? and a.unit_id =? and state='3' ");
			args.add("%"+applyUserName.trim()+"%");
			args.add(unitId);
		}else{
			sql.append(" where unit_id=? and state='3' ");
			args.add(unitId);
		}
		if(startTime!=null){
			sql.append(" and attend_date>= ? ");
			args.add(startTime);	
		}
		if(endTime!=null){
			sql.append("and attend_date<= ?");
			args.add(endTime);				
		}
		if(ArrayUtils.isNotEmpty(userIds)){
			sql.append(" and apply_user_id in");
			if(page==null){
				return queryForInSQL(sql.toString(),args.toArray(), userIds, new MultiRow());
			}
			return queryForInSQL(sql.toString(),args.toArray(), userIds, new MultiRow()," ",page);
		}
		if(page==null){
			return query(sql.toString(),args.toArray(),new MultiRow());
		}
		return query(sql.toString(),args.toArray(),new MultiRow(),page);
	}
}