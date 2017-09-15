package net.zdsoft.office.dailyoffice.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeWorkReportTlDao;
import net.zdsoft.office.dailyoffice.entity.OfficeWorkReportTl;

import org.apache.commons.lang.StringUtils;
/**
 * office_work_report_tl
 * @author 
 * 
 */
public class OfficeWorkReportTlDaoImpl extends BaseDao<OfficeWorkReportTl> implements OfficeWorkReportTlDao{

	@Override
	public OfficeWorkReportTl setField(ResultSet rs) throws SQLException{
		OfficeWorkReportTl officeWorkReportTl = new OfficeWorkReportTl();
		officeWorkReportTl.setId(rs.getString("id"));
		officeWorkReportTl.setYear(rs.getString("year"));
		officeWorkReportTl.setSemester(rs.getInt("semester"));
		officeWorkReportTl.setWeek(rs.getInt("week"));
		officeWorkReportTl.setContent(rs.getString("content"));
		officeWorkReportTl.setState(rs.getInt("state"));
		officeWorkReportTl.setUnitId(rs.getString("unit_id"));
		officeWorkReportTl.setUnitClass(rs.getInt("unit_class"));
		officeWorkReportTl.setParentUnitId(rs.getString("parent_unit_id"));
		officeWorkReportTl.setUnitOrderId(rs.getString("unit_order_id"));
		officeWorkReportTl.setTeacherOrderId(rs.getInt("teacher_order_id"));
		officeWorkReportTl.setCreateUserId(rs.getString("create_user_id"));
		officeWorkReportTl.setCreateTime(rs.getTimestamp("create_time"));
		officeWorkReportTl.setModifyUserId(rs.getString("modify_user_id"));
		officeWorkReportTl.setModifyTime(rs.getTimestamp("modify_time"));
		return officeWorkReportTl;
	}

	@Override
	public OfficeWorkReportTl save(OfficeWorkReportTl officeWorkReportTl){
		String sql = "insert into office_work_report_tl(id, year, semester, week, content, state, unit_id, unit_class, parent_unit_id, unit_order_id, teacher_order_id, create_user_id, create_time, modify_user_id, modify_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeWorkReportTl.getId())){
			officeWorkReportTl.setId(createId());
		}
		Object[] args = new Object[]{
			officeWorkReportTl.getId(), officeWorkReportTl.getYear(), 
			officeWorkReportTl.getSemester(), officeWorkReportTl.getWeek(), 
			officeWorkReportTl.getContent(), officeWorkReportTl.getState(), 
			officeWorkReportTl.getUnitId(), officeWorkReportTl.getUnitClass(), 
			officeWorkReportTl.getParentUnitId(), officeWorkReportTl.getUnitOrderId(), 
			officeWorkReportTl.getTeacherOrderId(), officeWorkReportTl.getCreateUserId(), 
			officeWorkReportTl.getCreateTime(), officeWorkReportTl.getModifyUserId(), 
			officeWorkReportTl.getModifyTime()
		};
		update(sql, args);
		return officeWorkReportTl;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_work_report_tl where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeWorkReportTl officeWorkReportTl){
		String sql = "update office_work_report_tl set year = ?, semester = ?, week = ?, content = ?, state = ?, unit_id = ?, unit_class = ?, parent_unit_id = ?, unit_order_id = ?, teacher_order_id = ?, create_user_id = ?, create_time = ?, modify_user_id = ?, modify_time = ? where id = ?";
		Object[] args = new Object[]{
			officeWorkReportTl.getYear(), officeWorkReportTl.getSemester(), 
			officeWorkReportTl.getWeek(), officeWorkReportTl.getContent(), 
			officeWorkReportTl.getState(), officeWorkReportTl.getUnitId(), 
			officeWorkReportTl.getUnitClass(), officeWorkReportTl.getParentUnitId(), 
			officeWorkReportTl.getUnitOrderId(), officeWorkReportTl.getTeacherOrderId(), 
			officeWorkReportTl.getCreateUserId(), officeWorkReportTl.getCreateTime(), 
			officeWorkReportTl.getModifyUserId(), officeWorkReportTl.getModifyTime(), 
			officeWorkReportTl.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeWorkReportTl getOfficeWorkReportTlById(String id){
		String sql = "select * from office_work_report_tl where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeWorkReportTl> getOfficeWorkReportTlMapByIds(String[] ids){
		String sql = "select * from office_work_report_tl where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeWorkReportTl> getOfficeWorkReportTlList(){
		String sql = "select * from office_work_report_tl";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeWorkReportTl> getOfficeWorkReportTlPage(Pagination page){
		String sql = "select * from office_work_report_tl";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeWorkReportTl> getOfficeWorkReportTlByUnitIdList(String unitId){
		String sql = "select * from office_work_report_tl where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeWorkReportTl> getOfficeWorkReportTlByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_work_report_tl where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}

	@Override
	public String findMaxWeek(String userId,String semester,String unitId,String states) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer("SELECT MAX(week) FROM office_work_report_tl WHERE semester=?");
		args.add(semester);
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and create_user_id = ?");
			args.add(userId);
		}
		if(StringUtils.isNotBlank(unitId)){
			sql.append(" and unit_id=?");
			args.add(unitId);
		}
		if(StringUtils.isNotBlank(states)){
			sql.append(" and state=?");
			args.add(states);
		}
		return queryForString(sql.toString(),args.toArray());
	}

	@Override
	public List<OfficeWorkReportTl> getOfficeWorkReportByUnitIdPageContent(
			String unit, Pagination page, String userId, String acadyear,
			String semester, String week, String contents, String states) {
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer("select * from office_work_report_tl where unit_id=? and create_user_id=?");
		args.add(unit);
		args.add(userId);
		if(StringUtils.isNotBlank(contents)){
			sql.append(" and content like '%"+ contents +"%'");
		}
		if(StringUtils.isNotBlank(acadyear)){
			sql.append(" and year = ?");
			args.add(acadyear);
		}
		if(StringUtils.isNotBlank(semester)){
			sql.append(" and semester = ?");
			args.add(semester);
		}
		if(StringUtils.isNotBlank(week)){
			sql.append(" and week=?");
			args.add(week);
		}
		if(StringUtils.isNotBlank(states)){
			sql.append(" and state=? ");
			args.add(states);
		}
		sql.append(" order by case when state=1 then 1 when state=2 then 2 end, year desc");
		//return queryForInSQL(sql.toString(), null, ids, new MultiRow());
		return query(sql.toString(),args.toArray(), new MultiRow(), page);
	}

	@Override
	public List<OfficeWorkReportTl> getOfficeWorkReportTlByUnitIdPageContentCreateUserName(
			Pagination page, String acadyear, String semester, String week,
			String contents, String createUserName) {
		StringBuffer sql=new StringBuffer("select * from office_work_report_tl where  1=1");
		List<Object> args = new ArrayList<Object>();
		if(StringUtils.isNotBlank(contents)){
			sql.append(" and content like '%"+ contents +"%'");
		}
		if(StringUtils.isNotBlank(acadyear)){
			sql.append(" and year = ?");
			args.add(acadyear);
		}
		if(StringUtils.isNotBlank(semester)){
			sql.append(" and semester = ?");
			args.add(semester);
		}
		if(StringUtils.isNotBlank(week)){
			sql.append(" and week=?");
			args.add(week);
		}
		if(StringUtils.isNotBlank(createUserName)){
			sql.append(" and exists(select 1 from base_user where base_user.id=office_work_report_tl.create_user_id and base_user.real_name like '%"+createUserName+"%' )");
		}
		sql.append(" and state = 2");
		sql.append(" order by unit_class,unit_order_id,teacher_order_id");
		if(page == null)
			return query(sql.toString(), args.toArray(), new MultiRow());
		
		return query(sql.toString(), args.toArray(), new MultiRow(), page);
	}

	@Override
	public List<OfficeWorkReportTl> getOfficeWorkReportTlByUnitId(
			String unitId,String userId, String acadyear, String semester, String week) {
		String sql = "select * from office_work_report_tl where unit_id = ? and create_user_id = ? and year=? and semester=? and week=?";
		return query(sql, new Object[]{unitId,userId,acadyear,semester,week }, new MultiRow());
	}
}

