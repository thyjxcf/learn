package net.zdsoft.office.dutyweekly.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.dutyweekly.entity.OfficeDutyProject;
import net.zdsoft.office.dutyweekly.dao.OfficeDutyProjectDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_project
 * @author 
 * 
 */
public class OfficeDutyProjectDaoImpl extends BaseDao<OfficeDutyProject> implements OfficeDutyProjectDao{

	@Override
	public OfficeDutyProject setField(ResultSet rs) throws SQLException{
		OfficeDutyProject officeDutyProject = new OfficeDutyProject();
		officeDutyProject.setId(rs.getString("id"));
		officeDutyProject.setUnitId(rs.getString("unit_id"));
		officeDutyProject.setProjectName(rs.getString("project_name"));
		officeDutyProject.setYear(rs.getString("acadyear"));
		officeDutyProject.setSemester(rs.getInt("semester"));
		return officeDutyProject;
	}

	@Override
	public OfficeDutyProject save(OfficeDutyProject officeDutyProject){
		String sql = "insert into office_duty_project(id, unit_id, project_name,acadyear,semester) values(?,?,?,?,?)";
		if (StringUtils.isBlank(officeDutyProject.getId())){
			officeDutyProject.setId(createId());
		}
		Object[] args = new Object[]{
			officeDutyProject.getId(), officeDutyProject.getUnitId(), 
			officeDutyProject.getProjectName(),officeDutyProject.getYear(),
			officeDutyProject.getSemester()
		};
		update(sql, args);
		return officeDutyProject;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_duty_project where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeDutyProject officeDutyProject){
		String sql = "update office_duty_project set unit_id = ?, project_name = ?,acadyear=?,semester=? where id = ?";
		Object[] args = new Object[]{
			officeDutyProject.getUnitId(), officeDutyProject.getProjectName(),
			officeDutyProject.getYear(),officeDutyProject.getSemester(),
			officeDutyProject.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeDutyProject getOfficeDutyProjectById(String id){
		String sql = "select * from office_duty_project where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeDutyProject> getOfficeDutyProjectMapByIds(String[] ids){
		String sql = "select * from office_duty_project where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeDutyProject> getOfficeDutyProjectList(){
		String sql = "select * from office_duty_project";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeDutyProject> getOfficeDutyProjectPage(Pagination page){
		String sql = "select * from office_duty_project";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeDutyProject> getOfficeDutyProjectByUnitIdList(String unitId){
		String sql = "select * from office_duty_project where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeDutyProject> getOfficeDutyProjectByUnitIdListss(
			String unitId, String years, String semesters) {
		String sql = "select * from office_duty_project where unit_id = ? and acadyear=? and semester=?";
		return query(sql, new Object[]{unitId,years,semesters}, new MultiRow());
	}

	@Override
	public List<OfficeDutyProject> getOfficeDutyProjectByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_duty_project where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
}
