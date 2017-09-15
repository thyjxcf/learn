package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.GradeDao;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.frame.client.BaseDao.MultiRow;
import net.zdsoft.keel.dao.MapRowMapper;

public class GradeDaoImpl extends BaseDao<Grade> implements GradeDao {
    private static final String SQL_UPDATE_GRADUATE = "UPDATE base_grade SET is_graduate = 1 ,modify_time = ? "
            + "WHERE school_id = ? AND open_acadyear = ? AND section = ? AND schooling_length = ?";

    private static final String SQL_FIND_GRADE_BY_ID = "SELECT * FROM base_grade WHERE id = ?";
    private static final String SQL_FIND_GRADE_BY_IDS = "SELECT * FROM base_grade WHERE id IN";
    private static final String SQL_FIND_GRADE_BY_ACADEAR_SECTION_SCHID = "SELECT * FROM base_grade "
            + "WHERE school_id = ? AND open_acadyear = ? AND section = ? AND is_graduate = 0 AND is_deleted = 0";

    private static final String SQL_FIND_GRADE_BY_SCHID_SECTION = "SELECT * FROM base_grade "
            + "WHERE school_id = ? AND section = ? AND is_deleted = 0 AND is_graduate = 0 "
            + "ORDER BY section, open_acadyear desc";

    private static final String SQL_FIND_GRADE_BY_SCHID = "SELECT * FROM base_grade "
            + "WHERE school_id = ? AND is_deleted = 0 AND is_graduate = 0 ORDER BY section, open_acadyear desc";

    private static final String SQL_FIND_GRADE_BY_TEACHERID = "SELECT * FROM base_grade "
            + "WHERE teacher_id = ? AND is_deleted = 0 AND is_graduate = 0 "
            + "ORDER BY section, open_acadyear desc";
    
    private static final String SQL_FIND_GRADE_BY_SCHID_SECTION_ACADYEAR = "SELECT * FROM base_grade "
            + "WHERE school_id = ?  AND is_deleted = 0 AND is_graduate = 0  AND open_acadyear=? AND section in ";

    public Grade setField(ResultSet rs) throws SQLException {
        Grade grade = new Grade();
        grade.setId(rs.getString("id"));
        grade.setSchid(rs.getString("school_id"));
        grade.setTeacherId(rs.getString("teacher_id"));
        grade.setAmLessonCount(rs.getInt("am_lesson_count"));
        grade.setPmLessonCount(rs.getInt("pm_lesson_count"));
        grade.setNightLessonCount(rs.getInt("night_lesson_count"));
        grade.setGradename(rs.getString("grade_name"));
        grade.setGradeCode(rs.getString("grade_code"));
        grade.setAcadyear(rs.getString("open_acadyear"));
        grade.setSchoolinglen(rs.getInt("schooling_length"));
        grade.setSection(rs.getInt("section"));
        grade.setSubschoolId(rs.getString("subschool_id"));
        grade.setIsGraduated(rs.getInt("is_graduate"));
        grade.setIsdeleted(rs.getBoolean("is_deleted"));
        grade.setDisplayOrder(rs.getInt("display_order"));
        grade.setCreationTime(rs.getTimestamp("creation_time"));
        grade.setModifyTime(rs.getTimestamp("modify_time"));
        return grade;
    }

    public void updateGraduate(String schId, String acadYear, int section, int schoolingLength) {
        update(SQL_UPDATE_GRADUATE, new Object[] { new Date(), schId, acadYear, section,
                Integer.valueOf(schoolingLength) });
    }

    public Grade getGrade(String gradeId) {
        return query(SQL_FIND_GRADE_BY_ID, gradeId, new SingleRow());
    }
    
    public List<Grade> getGradesByIds(String[] gradeIds){
    	return queryForInSQL(SQL_FIND_GRADE_BY_IDS, null, gradeIds, new MultiRow());
    }

    public Grade getGrade(String schoolId, String acadyear, int section) {
        return query(SQL_FIND_GRADE_BY_ACADEAR_SECTION_SCHID, new Object[] { schoolId, acadyear,
                section }, new SingleRow());
    }

    public List<Grade> getGrades(String schoolId) {
        return query(SQL_FIND_GRADE_BY_SCHID, new Object[] { schoolId }, new MultiRow());
    }

    public List<Grade> getGrades(String schoolId, int section) {
        return query(SQL_FIND_GRADE_BY_SCHID_SECTION, new Object[] { schoolId, section },
                new MultiRow());
    }

    public List<Grade> getGradesByTeacherId(String teacherId) {
        return query(SQL_FIND_GRADE_BY_TEACHERID, new Object[] { teacherId }, new MultiRow());
    }
	@Override
	public Map<String, Grade> getGradeMapBySchid(String schId) {
		return queryForMap(SQL_FIND_GRADE_BY_SCHID, new String[] { schId },
				new MapRowMapper<String, Grade>() {

					public String mapRowKey(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getString("id");
					}

					public Grade mapRowValue(ResultSet rs, int arg1)
							throws SQLException {
						return setField(rs);
					}
				});
	}
	 public List<Grade> getBaseGradesBySchidSectionAcadyear(String schoolId, String curAcadyear, Integer[] section) {
	        return queryForInSQL(SQL_FIND_GRADE_BY_SCHID_SECTION_ACADYEAR, new Object[] { schoolId, curAcadyear }, section,
	                new MultiRow(), "ORDER BY section ");
	    }
}
