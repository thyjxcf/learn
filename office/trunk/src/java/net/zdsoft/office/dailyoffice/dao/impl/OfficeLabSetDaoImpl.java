package net.zdsoft.office.dailyoffice.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.dailyoffice.entity.OfficeLabSet;
import net.zdsoft.office.dailyoffice.dao.OfficeLabSetDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;
/**
 * office_lab_set
 * @author 
 * 
 */
public class OfficeLabSetDaoImpl extends BaseDao<OfficeLabSet> implements OfficeLabSetDao{

	@Override
	public OfficeLabSet setField(ResultSet rs) throws SQLException{
		OfficeLabSet officeLabSet = new OfficeLabSet();
		officeLabSet.setId(rs.getString("id"));
		officeLabSet.setUnitId(rs.getString("unit_id"));
		officeLabSet.setName(rs.getString("name"));
		officeLabSet.setCourseBook(rs.getString("course_book"));
		officeLabSet.setApparatus(rs.getString("apparatus"));
		officeLabSet.setReagent(rs.getString("reagent"));
		officeLabSet.setSubject(rs.getString("subject"));
		officeLabSet.setCreateTime(rs.getTimestamp("create_time"));
		officeLabSet.setGrade(rs.getString("grade"));
		return officeLabSet;
	}

	@Override
	public OfficeLabSet save(OfficeLabSet officeLabSet){
		String sql = "insert into office_lab_set(id, unit_id, name, course_book, apparatus, reagent, subject, create_time, grade) values(?,?,?,?,?,?,?,?,?)";
		if (StringUtils.isBlank(officeLabSet.getId())){
			officeLabSet.setId(createId());
		}
		Object[] args = new Object[]{
			officeLabSet.getId(), officeLabSet.getUnitId(), 
			officeLabSet.getName(), officeLabSet.getCourseBook(), 
			officeLabSet.getApparatus(), officeLabSet.getReagent(), 
			officeLabSet.getSubject(), officeLabSet.getCreateTime(),
			officeLabSet.getGrade()
		};
		update(sql, args);
		return officeLabSet;
	}

	@Override
	public Integer delete(String[] ids){
		String sql = "delete from office_lab_set where id in";
		return updateForInSQL(sql, null, ids);
	}

	@Override
	public Integer update(OfficeLabSet officeLabSet){
		String sql = "update office_lab_set set unit_id = ?, name = ?, course_book = ?, apparatus = ?, reagent = ?, subject = ?, create_time = ?, grade = ? where id = ?";
		Object[] args = new Object[]{
			officeLabSet.getUnitId(), officeLabSet.getName(), 
			officeLabSet.getCourseBook(), officeLabSet.getApparatus(), 
			officeLabSet.getReagent(), officeLabSet.getSubject(), 
			officeLabSet.getCreateTime(), officeLabSet.getGrade(),
			officeLabSet.getId()
		};
		return update(sql, args);
	}

	@Override
	public OfficeLabSet getOfficeLabSetById(String id){
		String sql = "select * from office_lab_set where id = ?";
		return query(sql, new Object[]{id }, new SingleRow());
	}

	@Override
	public Map<String, OfficeLabSet> getOfficeLabSetMapByIds(String[] ids){
		String sql = "select * from office_lab_set where id in";
		return queryForInSQL(sql, null, ids, new MapRow());
	}

	@Override
	public List<OfficeLabSet> getOfficeLabSetList(){
		String sql = "select * from office_lab_set";
		return query(sql, new MultiRow());
	}

	@Override
	public List<OfficeLabSet> getOfficeLabSetPage(Pagination page){
		String sql = "select * from office_lab_set";
		return query(sql, new MultiRow(), page);
	}

	@Override
	public List<OfficeLabSet> getOfficeLabSetByUnitIdList(String unitId){
		String sql = "select * from office_lab_set where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow());
	}

	@Override
	public List<OfficeLabSet> getOfficeLabSetByUnitIdPage(String unitId, Pagination page){
		String sql = "select * from office_lab_set where unit_id = ?";
		return query(sql, new Object[]{unitId }, new MultiRow(), page);
	}
	
	@Override
	public List<OfficeLabSet> getOfficeLabSetBySubjectPage(String unitId, String subject, String grade, Pagination page){
		String sql = "select * from office_lab_set where unit_id = ?";
		List<Object> obj = new ArrayList<Object>();
		obj.add(unitId);
		if(StringUtils.isNotBlank(subject)){
			sql += " and subject = ?";
			obj.add(subject);
		}
		if(StringUtils.isNotBlank(grade)){
			sql += " and grade = ?";
			obj.add(grade);
		}
		sql += " order by subject, nlssort(name,'NLS_SORT=SCHINESE_PINYIN_M')";
		if(page != null){
			return query(sql, obj.toArray(), new MultiRow(), page);
		}else{
			return query(sql, obj.toArray(), new MultiRow());
		}
	}
	
	@Override
	public Map<String, OfficeLabSet> getOfficeLabSetMapByConditions(String unitId, String searchName, String searchSubject, String searchGrade){
		List<Object> args = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from office_lab_set where unit_id = ?");
		args.add(unitId);
		if(StringUtils.isNotBlank(searchName)){
			sql.append(" and name like '%"+searchName+"%'");
		}
		if(StringUtils.isNotBlank(searchSubject)){
			sql.append(" and subject = ?");
			args.add(searchSubject);
		}
		if(StringUtils.isNotBlank(searchGrade)){
			sql.append(" and grade = ?");
			args.add(searchGrade);
		}
		return queryForMap(sql.toString(), args.toArray(), new MapRowMapper<String, OfficeLabSet>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("id");
			}
			
			@Override
			public OfficeLabSet mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
		});
	}
}
