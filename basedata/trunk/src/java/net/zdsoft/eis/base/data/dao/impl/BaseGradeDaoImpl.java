package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.data.dao.BaseGradeDao;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;

/**
 * @author yanb
 * 
 */
public class BaseGradeDaoImpl extends BaseDao<Grade> implements BaseGradeDao {

	private static final String SQL_INSERT_GRADE = "INSERT INTO base_grade(id,grade_name,grade_code,school_id,"
			+ "teacher_id,am_lesson_count,pm_lesson_count,night_lesson_count,open_acadyear,schooling_length,section,"
			+ "is_graduate,display_order,creation_time,modify_time,is_deleted,event_source) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_GRADE = "UPDATE base_grade SET is_deleted = 1,modify_time = ?,event_source=? WHERE id=?";

	private static final String SQL_DELETE_GRADE_BY_SCHID_SECTION = "UPDATE base_grade SET is_deleted = 1,"
			+ "modify_time = ?,event_source=? WHERE school_id = ? AND section = ?";

	private static final String SQL_UPDATE_GRADE = "UPDATE base_grade SET grade_name=?,grade_code=?,school_id=?,"
			+ "teacher_id=?,am_lesson_count=?,pm_lesson_count=?,night_lesson_count=?,open_acadyear=?,schooling_length=?,section=?,"
			+ "is_graduate=?,display_order=?,modify_time=?,is_deleted=?,event_source=? WHERE id=?";

	private static final String SQL_UPDATE_LESSON = "UPDATE base_grade SET am_lesson_count = ?,"
			+ " pm_lesson_count = ?, night_lesson_count = ?, modify_time=? , event_source=? "
			+ "WHERE school_id = ? AND is_deleted = 0 AND is_graduate = 0 ";

	private static final String SQL_FIND_SECTIONS_BY_SCHID = "SELECT distinct section FROM base_grade "
			+ "WHERE is_deleted = 0 AND is_graduate = 0 AND school_id = ?";

	private static final String SQL_FIND_DISPLAY_ORDER_BY_SCHID = "SELECT max(display_order) FROM base_grade WHERE school_id=? AND is_deleted=0";

	private static final String SQL_FIND_GRADE_BY_SCHID_ACADYEAR_GRADUATE = "select * FROM base_grade "
			+ "WHERE (is_graduate =0 OR is_graduate = null)"
			+ " AND is_deleted = 0 AND school_id=? AND (cast(substr(?,6,9) AS integer) - cast(substr(open_acadyear,1,4) AS integer))"
			+ " > schooling_length";
	private static final String SQL_FIND_GRADE_BY_ID = "SELECT * FROM base_grade WHERE id = ?";

	private static final String SQL_FIND_GRADE_BY_SCHID = "SELECT * FROM base_grade "
			+ "WHERE school_id = ? AND is_deleted = 0 ORDER BY section, open_acadyear desc";
	private static final String SQL_FIND_GRADE_BY_SCHID_SECTION = "SELECT * FROM base_grade "
			+ "WHERE school_id = ? AND section = ? AND is_deleted = 0 AND is_graduate = 0 "
			+ "ORDER BY section, open_acadyear desc";

	private static final String SQL_FIND_GRADE_BY_SCHID_SECTION_ACADYEAR = "SELECT * FROM base_grade "
			+ "WHERE school_id = ?  AND is_deleted = 0 AND is_graduate = 0  AND open_acadyear=? AND section in ";

	public Grade setField(ResultSet rs) throws SQLException {
		Grade grade = new Grade();
		grade.setId(rs.getString("id"));
		grade.setGradename(rs.getString("grade_name"));
		grade.setGradeCode(rs.getString("grade_code"));
		grade.setSchid(rs.getString("school_id"));
		grade.setTeacherId(rs.getString("teacher_id"));
		grade.setAmLessonCount(rs.getInt("am_lesson_count"));
		grade.setPmLessonCount(rs.getInt("pm_lesson_count"));
		grade.setNightLessonCount(rs.getInt("night_lesson_count"));
		grade.setAcadyear(rs.getString("open_acadyear"));
		grade.setSchoolinglen(rs.getInt("schooling_length"));
		grade.setSection(rs.getInt("section"));
		grade.setIsGraduated(rs.getInt("is_graduate"));
		grade.setDisplayOrder(rs.getInt("display_order"));
		grade.setCreationTime(rs.getTimestamp("creation_time"));
		grade.setModifyTime(rs.getTimestamp("modify_time"));
		return grade;
	}

	public void insertGrade(Grade grade) {
		if (StringUtils.isBlank(grade.getId()))
			grade.setId(createId());
		grade.setIsdeleted(false);
		grade.setCreationTime(new Date());
		grade.setModifyTime(new Date());
		if (grade.getSection() == 9)
			grade.setSchoolinglen(3);
		update(SQL_INSERT_GRADE,
				new Object[] { grade.getId(), grade.getGradename(),
						grade.getGradeCode(), grade.getSchid(),
						grade.getTeacherId(), grade.getAmLessonCount(),
						grade.getPmLessonCount(), grade.getNightLessonCount(),
						grade.getAcadyear(), grade.getSchoolinglen(),
						grade.getSection(), grade.getIsGraduated(),
						grade.getDisplayOrder(), grade.getCreationTime(),
						grade.getModifyTime(), grade.getIsdeleted(),
						grade.getEventSourceValue() }, new int[] { Types.CHAR,
						Types.VARCHAR, Types.CHAR, Types.CHAR, Types.CHAR,
						Types.INTEGER, Types.INTEGER, Types.INTEGER,
						Types.CHAR, Types.INTEGER, Types.INTEGER,
						Types.INTEGER, Types.INTEGER, Types.TIMESTAMP,
						Types.TIMESTAMP, Types.INTEGER, Types.INTEGER });
	}

	public void updateGrade(Grade grade) {
		grade.setModifyTime(new Date());
		grade.setIsdeleted(false);
		if (grade.getSection() == 9)
			grade.setSchoolinglen(3);
		update(SQL_UPDATE_GRADE,
				new Object[] { grade.getGradename(), grade.getGradeCode(),
						grade.getSchid(), grade.getTeacherId(),
						grade.getAmLessonCount(), grade.getPmLessonCount(),
						grade.getNightLessonCount(), grade.getAcadyear(),
						grade.getSchoolinglen(), grade.getSection(),
						grade.getIsGraduated(), grade.getDisplayOrder(),
						grade.getModifyTime(), grade.getIsdeleted(),
						grade.getEventSourceValue(), grade.getId() },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
						Types.VARCHAR, Types.INTEGER, Types.INTEGER,
						Types.INTEGER, Types.VARCHAR, Types.INTEGER,
						Types.INTEGER, Types.INTEGER, Types.INTEGER,
						Types.TIMESTAMP, Types.INTEGER, Types.INTEGER,
						Types.VARCHAR });
	}

	public void deleteGrade(String gradeId, EventSourceType sourceType) {
		update(SQL_DELETE_GRADE,
				new Object[] { new Date(), sourceType.getValue(), gradeId });
	}

	public void deleteGrade(String schoolId, int section) {
		update(SQL_DELETE_GRADE_BY_SCHID_SECTION, new Object[] { new Date(),
				EventSourceType.LOCAL.getValue(), schoolId, section });
	}

	public int updateGrade(String schId, int amNumber, int pmNumber,
			int nightNumber) {
		return update(SQL_UPDATE_LESSON, new Object[] { amNumber, pmNumber,
				nightNumber, new Date(), EventSourceType.LOCAL.getValue(),
				schId });
	}

	public int getMaxOrder(String schoolId) {
		return queryForInt(SQL_FIND_DISPLAY_ORDER_BY_SCHID,
				new Object[] { schoolId });
	}

	public List<Integer> getSections(String schoolId) {
		return query(SQL_FIND_SECTIONS_BY_SCHID, new Object[] { schoolId },
				new MultiRowMapper<Integer>() {
					public Integer mapRow(ResultSet rs, int numRow)
							throws SQLException {
						return rs.getInt("section");
					}
				});
	}

	public List<Grade> getOverSchoolinglenGrades(String schoolId,
			String curAcadyear) {
		return query(SQL_FIND_GRADE_BY_SCHID_ACADYEAR_GRADUATE, new Object[] {
				schoolId, curAcadyear }, new MultiRow());
	}

	public Grade getGrade(String gradeId) {
		return query(SQL_FIND_GRADE_BY_ID, gradeId, new SingleRow());
	}

	public Map<String, Grade> getGradeMap(String schId) {
		return queryForMap(SQL_FIND_GRADE_BY_SCHID, new String[] { schId },
				new MapRowMapper<String, Grade>() {

					public String mapRowKey(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getString("school_id")
								+ rs.getString("open_acadyear")
								+ String.valueOf(rs.getInt("section"))
								+ String.valueOf(rs.getInt("schooling_length"));
					}

					public Grade mapRowValue(ResultSet rs, int arg1)
							throws SQLException {
						return setField(rs);
					}
				});
	}

	public List<Grade> getBaseGrades(String schoolId, int section) {
		return query(SQL_FIND_GRADE_BY_SCHID_SECTION, new Object[] { schoolId,
				section }, new MultiRow());
	}

	public List<Grade> getBaseGradesBySchidSectionAcadyear(String schoolId,
			String curAcadyear, Integer[] section) {
		return queryForInSQL(SQL_FIND_GRADE_BY_SCHID_SECTION_ACADYEAR,
				new Object[] { schoolId, curAcadyear }, section,
				new MultiRow(), "ORDER BY section ");
	}

	public List<Grade> getBaseGradesWithGraduated(String schoolId, int section) {
		String sql = "SELECT * FROM base_grade WHERE school_id = ? AND section = ? AND is_deleted = 0 ORDER BY section, open_acadyear desc";
		return query(sql, new Object[] { schoolId, section }, new MultiRow());
	}

	public List<Grade> getBaseGradesByTeacherId(String schoolId,
			String teacherId) {
		String sql = "SELECT * FROM base_grade WHERE school_id = ? AND  teacher_id= ?";
		return query(sql, new Object[] { schoolId, teacherId }, new MultiRow());
	}

	@Override
	public List<Grade> getGrades(String schoolId, int section) {
		String sql = "SELECT * FROM base_grade WHERE school_id = ? AND section = ? AND is_graduate = 0 ORDER BY section, open_acadyear desc";
		return query(sql, new Object[] { schoolId, section }, new MultiRow());
	}

}
