package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.common.entity.BaseCourse;
import net.zdsoft.eis.base.common.dao.BaseCourseDao;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.SingleRowMapper;

public class BaseCourseDaoImpl extends BaseDao<BaseCourse> implements BaseCourseDao {
	private static final String GET_BASECOURSE_LIST = "select * from eduadm_subject where unit_id=? and is_using = 1 and is_deleted = 0";
	
	private static final String GET_BASECOURSE_BY_ID = "select * from eduadm_subject where subject_code = ? and is_using = 1 and is_deleted = 0";
	
	private static final String GET_BASECOURSE_BY_IDS = "select * from eduadm_subject where id IN";
	
	public BaseCourse setField(ResultSet rs) throws SQLException {
		BaseCourse course = new BaseCourse();
		course.setId(rs.getString("id"));
		course.setSubjectCode(rs.getString("subject_code"));
		course.setSubjectName(rs.getString("subject_name"));
		course.setIsUsing(rs.getInt("is_using"));
		course.setUnitId(rs.getString("unit_id"));
		return course;
	}
	
	public List<BaseCourse> getBaseCoureList(String unitid){
		return query(GET_BASECOURSE_LIST, new Object[]{unitid},new MultiRow());
	}
	
	public BaseCourse getBaseCourseByCode(String code){
		return query(GET_BASECOURSE_BY_ID, new Object[]{code}, new SingleRowMapper<BaseCourse>() {
			public BaseCourse mapRow(ResultSet rs) throws SQLException {
				return setField(rs);
			}
			
		});
	}
	
	public List<BaseCourse> getBaseCoursesByIds(String[] ids){
		return queryForInSQL(GET_BASECOURSE_BY_IDS, null, ids, new MultiRow());
	}
}
