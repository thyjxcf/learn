package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.zdsoft.basedata.remote.service.ClassRemoteService;
import net.zdsoft.eis.base.common.dao.BasicClassDao;
import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.KinClass;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.simple.dao.impl.AbstractClassDaoImpl;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class BasicClassDaoImpl extends AbstractClassDaoImpl<BasicClass>
		implements BasicClassDao {

	@Autowired
	private ClassRemoteService classRemoteService; 

	
    private static final String SQL_UPDATE_GRADUATE_SIGN = "UPDATE base_class SET is_graduate=?,graduate_date=? WHERE id=?";


	private static final String SQL_FIND_CLASS_BY_IDS = "SELECT * FROM base_class WHERE is_deleted = 0 AND id IN";

	// ======================按(副)班主任teacherId查询班级=====================
	private static final String SQL_FIND_BASECLASSS_BY_TEAID = "SELECT * FROM base_class "
			+ "WHERE (teacher_id=? OR vice_teacher_id=?) AND is_graduate=0 AND is_deleted = 0 "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	private static final String SQL_FIND_BASECLASSS_BY_TEAID_CAMPID = "SELECT * FROM base_class "
			+ "WHERE campus_id=? AND (teacher_id=? OR vice_teacher_id=?) AND is_graduate=0 AND is_deleted = 0 "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	private static final String SQL_FIND_GRADUATINGCLASSS_BY_TEAID = "SELECT * FROM base_class "
			+ "WHERE (teacher_id=? OR vice_teacher_id=?) AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) = schooling_length "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	private static final String SQL_FIND_GRADUATINGCLASSS_BY_TEAID_CAMPID = "SELECT * FROM base_class "
			+ "WHERE campus_id=? AND (teacher_id=? OR vice_teacher_id=?) AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) = schooling_length "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	private static final String SQL_FIND_GRADUATDCLASSS_BY_TEAID = "SELECT * FROM base_class "
			+ "WHERE (teacher_id=? OR vice_teacher_id=?) AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) = schooling_length  AND is_graduate=1 AND is_deleted = 0 "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	private static final String SQL_FIND_GRADUATDCLASSS_BY_TEAID_CAMPID = "SELECT * FROM base_class "
			+ "WHERE campus_id=? AND (teacher_id=? OR vice_teacher_id=?) AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) = schooling_length AND is_graduate=1 AND is_deleted = 0 "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	// ======================按schoolId查询班级=====================
	private static final String SQL_FIND_CLASS_BY_SCHOOLID_PREFIX = "SELECT * FROM base_class WHERE school_id=? AND is_graduate=0 AND is_deleted = 0 ";

	private static final String SQL_FIND_CLASS_ALL_BY_SCHID = "SELECT * FROM base_class "
			+ "WHERE school_id = ? AND is_deleted = 0 ORDER BY section ASC,acadyear DESC,class_code ASC";

	private static final String SQL_FIND_CLASS_BY_SCHID_ACADYEAR = "SELECT * FROM base_class "
			+ "WHERE school_id = ? AND acadyear=? AND is_deleted = 0 ORDER BY section ASC,class_code ASC";

	private static final String SQL_FIND_CLASS_BY_SCHID_ACADYEAR_TEAID = "SELECT * FROM base_class "
			+ "WHERE school_id = ? AND grade_id=? AND teacher_id = ? AND is_deleted = 0 ORDER BY section ASC,class_code ASC";

	
	private static final String SQL_FIND_CLS_GRADUATE_BY_SCHID = "SELECT id, substr(acadyear,1,4)+schooling_length as lenth FROM base_class "
			+ "WHERE school_id =? AND is_deleted = 0";

	private static final String SQL_FIND_BASECLASSS_BY_ACADYEAR = "SELECT * FROM base_class "
			+ "WHERE school_id=? AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) >0 AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) <= schooling_length  AND is_deleted = 0 "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";
	
	private static final String SQL_FIND_BASECLASSS_BY_ACADYEAR_SECTION = "SELECT * FROM base_class "
		+ "WHERE school_id=? AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) >0 AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) <= schooling_length AND SECTION=?  AND is_deleted = 0 "
		+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	private static final String SQL_FIND_OVERSCHLEN_CLS_BY_SCHID = "SELECT * FROM base_class "
			+ "WHERE school_id=? AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) > schooling_length AND is_graduate=0 AND is_deleted = 0 "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	private static final String SQL_FIND_GRADUTING_CLS_BY_SCHID_GRADEDYEAR = "SELECT * FROM base_class "
			+ "WHERE school_id=? AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) = schooling_length AND is_deleted = 0 "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	private static final String SQL_FIND_GRADUTED_CLS_BY_SCHID_GRADEDYEAR = "SELECT * FROM base_class "
			+ "WHERE school_id=? AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) = schooling_length  AND is_graduate=1 AND is_deleted = 0  "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	// ======================按campusId查询班级=======================
	private static final String SQL_FIND_BASECLASSS_BY_SUBSCHID = "SELECT * FROM base_class "
			+ "WHERE campus_id=? AND is_deleted = 0 AND is_graduate=0 "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	private static final String SQL_FIND_GRADUTING_CLS_BY_SUBSCHID = "SELECT * FROM base_class "
			+ "WHERE campus_id=? AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) = schooling_length AND is_deleted = 0 "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	private static final String SQL_FIND_GRADUTED_CLS_BY_SUBSCHID = "SELECT * FROM base_class "
			+ "WHERE campus_id=? AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer))=schooling_length AND is_graduate=1 AND is_deleted = 0 "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	// ======================根据班级查询年级=======================
	private static final String SQL_FIND_GRADE_BY_SCHID = "SELECT DISTINCT school_id, section, acadyear,schooling_length,grade_id FROM base_class "
			+ "WHERE school_id=? AND is_graduate=0 AND is_deleted = 0 "
			+ "ORDER BY section ASC, acadyear DESC";

	private static final String SQL_FIND_GRADE_BY_SCHID_ACADYEAR = "SELECT DISTINCT school_id, section, acadyear, schooling_length,grade_id FROM base_class "
			+ "WHERE school_id=? AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) <= schooling_length and (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer))>0  AND is_deleted = 0 "
			+ "ORDER BY section ASC, acadyear DESC";

	private static final String SQL_FIND_GRADUTING_GRADE_BY_SCHID_GRADUATEYEAR = "SELECT DISTINCT school_id, section, acadyear, schooling_length,grade_id FROM base_class "
			+ "WHERE school_id=? AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) = schooling_length AND is_deleted = 0 "
			+ "ORDER BY section ASC, acadyear DESC";

	private static final String SQL_FIND_GRADE_BY_SCHID_TEACHERID = "SELECT DISTINCT school_id, section, acadyear, schooling_length,grade_id FROM base_class "
			+ "WHERE school_id=? AND (teacher_id=? or vice_teacher_id=?) AND is_graduate=0 AND is_deleted = 0 "
			+ "ORDER BY section ASC, acadyear DESC";
	private static final String SQL_FIND_NOTGRADUTING_GRADE_BY_SCHID_ACADYEAR = "SELECT DISTINCT school_id, section, acadyear, schooling_length,grade_id FROM base_class "
			+ "WHERE school_id=? AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) < schooling_length and (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer))>=0  AND is_deleted = 0 "
			+ "ORDER BY section ASC, acadyear DESC";
	
	private static final String SQL_FIND_GRADE_BY_SCHID_TEACHERID_ALL = "SELECT * FROM base_class WHERE school_id=? AND (teacher_id=? or vice_teacher_id=?) ";

	// ======================根据年级查询班级=======================
	private static final String SQL_FIND_CLASS_BY_GRADE = "SELECT * FROM base_class "
			+ "WHERE school_id=? AND section=? AND acadyear=? AND schooling_length like ? AND is_deleted = 0 AND is_graduate=0 "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	private static final String SQL_FIND_CLASS_BY_CAMPUS_GRADE = "SELECT * FROM base_class "
			+ "WHERE school_id=? AND campus_id=? AND section=? AND acadyear=? AND schooling_length=? AND is_deleted = 0 AND is_graduate=0 "
			+ " ORDER BY section ASC,acadyear DESC,class_code ASC";
	
	private static final String SQL_FIND_CLASS_BY_SCHOOLID_AND_GRADEID = "SELECT * FROM base_class "
			+ "WHERE school_id=? AND grade_id=? AND is_deleted = 0 AND is_graduate=0 "
			+ " ORDER BY section ASC,acadyear DESC,class_code ASC";
	
	private static final String SQL_FIND_CLASS_BY_GRADEID = "SELECT * FROM base_class "
	        + "WHERE grade_id=? AND is_deleted = 0 AND is_graduate=0 "
	        + " ORDER BY section ASC,acadyear DESC,class_code ASC";
	
	//毕业生校对表
	private static final String SQL_FIND_CLASS_BY_GRADEID_ALL = "SELECT * FROM base_class "
	        + "WHERE grade_id=? AND is_deleted = 0"
	        + " ORDER BY section ASC,acadyear DESC,class_code ASC";

	// ======================根据同类班级级查询班级=======================
	private static final String SQL_FIND_DISTINCT_CLASSTYPE = "SELECT distinct art_science_type FROM base_class "
			+ "WHERE school_id=? AND section=? AND acadyear=? AND is_graduate=0 AND is_deleted = 0";

	private static final String SQL_FIND_KINCLASS_BY_SCHOOLID = "SELECT DISTINCT school_id,section,acadyear, schooling_length, art_science_type FROM base_class "
			+ "WHERE school_id=? AND is_deleted = 0 AND is_graduate=0 "
			+ "ORDER BY section ASC, acadyear DESC";

	private static final String SQL_FIND_CLASS_BY_KINCLASS = "SELECT * FROM base_class "
			+ "WHERE school_id=? AND section=? AND acadyear=? AND art_science_type=? AND is_deleted = 0 "
			+ "ORDER BY class_code ASC";

	private static final String SQL_FIND_CLASS_BY_KINCLASS_SCHOOLINGLEN = "SELECT * FROM base_class "
			+ "WHERE school_id=? AND section=? AND acadyear=? AND schooling_length=? AND art_science_type=? AND is_deleted = 0 "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	private static final String SQL_FIND_CLASS_BY_CAMPUS_KINCLASS = "SELECT * FROM base_class "
			+ "WHERE school_id=?  AND campus_id=? AND section=? AND acadyear=? AND schooling_length=? AND art_science_type=? AND is_deleted = 0 "
			+ "ORDER BY section ASC,acadyear DESC,class_code ASC";

	// ======================多表关联=====================
	// ======================按年级组长teacherId查询班级=====================
	private static final String SQL_FIND_CLASS_BY_GRADE_HEADTEAID = "SELECT bc.* FROM base_class bc, base_grade bg "
			+ "WHERE bc.school_id=bg.school_id AND bc.acadyear=bg.open_acadyear AND bc.section=bg.section AND "
			+ "bc.schooling_length=bg.schooling_length AND bc.is_deleted = 0 AND bg.teacher_id = ? ";

	private static final String SQL_FIND_CLASS_BY_GRADE_HEADTEAID_OR_CLASS_TEACHERID = "SELECT bc.* FROM base_class bc, base_grade bg "
			+ "WHERE bc.school_id=bg.school_id AND bc.acadyear=bg.open_acadyear AND bc.section=bg.section AND "
			+ "bc.schooling_length=bg.schooling_length AND bc.is_deleted = 0 AND bg.is_deleted=0 AND ( bg.teacher_id = ? or bc.teacher_id=? OR bc.vice_teacher_id=?)";

	private static final String SQL_FIND_GRADENAME_BY_UNITIVECODE = "SELECT c.grade_name,si.unitive_code,a.* "
			+ "FROM base_student si,base_class a,base_grade c "
			+ "WHERE c.id=a.grade_id AND a.id=si.class_id AND a.is_deleted=0 AND si.is_deleted = 0 AND si.is_leave_school<>9 AND si.unitive_code IN";

	public BasicClass setField(ResultSet rs) throws SQLException {
		BasicClass baseClass = new BasicClass();
		setEntity(rs, baseClass);		
		baseClass.setHonor(rs.getString("honor"));		
		baseClass.setClasstype(rs.getString("class_type"));
		baseClass.setArtsciencetype(rs.getInt("art_science_type"));
		baseClass.setIsDuplexClass(rs.getBoolean("is_duplex_class"));
		return baseClass;
	}

	public Grade setFieldGrade(ResultSet rs) throws SQLException {
		Grade grade = new Grade();
		grade.setSchid(rs.getString("school_id"));
		grade.setSection(rs.getInt("section"));
		grade.setAcadyear(rs.getString("acadyear"));
		grade.setSchoolinglen(rs.getInt("schooling_length"));
		//grade.setSubschoolId(rs.getString("subschool_id"));
		grade.setId(rs.getString("grade_id"));
		return grade;
	}

	public void updateGraduateSign(String classId, int sign) {
		classRemoteService.updateGraduateSign(sign,getCurrentDate(),classId);
		
		//UPDATE base_class SET is_graduate=?,graduate_date=? WHERE id=?
//		update(SQL_UPDATE_GRADUATE_SIGN, new Object[] { sign, getCurrentDate(),
//				classId });
	}

	public List<BasicClass> getClasses(String[] classIds) {
		return BasicClass.dt(classRemoteService.findByIds(classIds));
		
		/*String postfix = " ORDER BY section ASC,acadyear DESC,class_code ASC ";
		//SELECT * FROM base_class WHERE is_deleted = 0 AND id IN
		return queryForInSQL(SQL_FIND_CLASS_BY_IDS, null, classIds,
				new MultiRow(), postfix);*/
	}

	// ======================按(副)班主任teacherId查询班级，权限使用=======================
	public List<BasicClass> getClassesByTeacherId(String teacherId) {
		return BasicClass.dt(classRemoteService.findByteacherId(teacherId));
		
		/*return query(SQL_FIND_BASECLASSS_BY_TEAID, new Object[] { teacherId,
				teacherId }, new MultiRow());*/
	}

	public List<BasicClass> getClassesByTeacherId(String campusId,
			String teacherId) {
		List<BasicClass> list = BasicClass.dt(classRemoteService.findByteacherId(teacherId));
		List<BasicClass> list1 = new ArrayList<BasicClass>();
		for(BasicClass bc : list){
			if(StringUtils.equals(campusId, bc.getCampusId())){
				list1.add(bc);
			}
		}
		return list1;
		/*return query(SQL_FIND_BASECLASSS_BY_TEAID_CAMPID, new Object[] {
				campusId, teacherId, teacherId }, new MultiRow());*/
	}

	public List<BasicClass> getGraduatingClassesByTeacherId(String teacherId,
			String graduateAcadyear) {
		return BasicClass.dt(classRemoteService.findByIdAcadyear(teacherId,graduateAcadyear));
		
		/*return query(SQL_FIND_GRADUATINGCLASSS_BY_TEAID, new Object[] {
				teacherId, teacherId, graduateAcadyear }, new MultiRow());*/
	}

	public List<BasicClass> getGraduatingClassesByTeacherId(String campusId,
			String teacherId, String graduateAcadyear) {
		List<BasicClass> list = BasicClass.dt(classRemoteService.findByIdAcadyear(teacherId,graduateAcadyear));
		List<BasicClass> list1 = new ArrayList<BasicClass>();
		for(BasicClass bc : list){
			if(StringUtils.equals(campusId, bc.getCampusId())){
				list1.add(bc);
			}
		}
		return list1;
		/*return query(SQL_FIND_GRADUATINGCLASSS_BY_TEAID_CAMPID, new Object[] {
				campusId, teacherId, teacherId, graduateAcadyear },
				new MultiRow());*/
	}

	public List<BasicClass> getGraduatedClassesByTeacherId(String teacheId,
			String graduateAcadyear) {
		return BasicClass.dt(classRemoteService.findByteacherIdAcadyear(teacheId,graduateAcadyear));
		
		/*return query(SQL_FIND_GRADUATDCLASSS_BY_TEAID, new Object[] { teacheId,
				teacheId, graduateAcadyear }, new MultiRow());*/
	}

	public List<BasicClass> getGraduatedClassesByTeacherId(String campusId,
			String teacherId, String graduateAcadyear) {
		List<BasicClass> list = BasicClass.dt(classRemoteService.findByteacherIdAcadyear(teacherId,graduateAcadyear));
		List<BasicClass> list1 = new ArrayList<BasicClass>();
		for(BasicClass bc : list){
			if(StringUtils.equals(campusId, bc.getCampusId())){
				list1.add(bc);
			}
		}
		return list1;
		
		/*return query(SQL_FIND_GRADUATDCLASSS_BY_TEAID_CAMPID, new Object[] {
				campusId, teacherId, teacherId, graduateAcadyear },
				new MultiRow());*/
	}

	// ======================按年级组长teacherId查询班级，权限使用=======================
	public List<BasicClass> getClassesByGradeTeacherId(String gradeTeacherId,
			String campusId) {
		String sql = SQL_FIND_CLASS_BY_GRADE_HEADTEAID;
		sql += " AND bc.is_graduate=0";
		StringBuffer sqlBuf = new StringBuffer();
		if (campusId != null && !"".equals(campusId)) {
			sqlBuf.append(" AND bc.campus_id ='" + campusId + "'");
		}
		sqlBuf.append(" ORDER BY bc.section ASC, bc.acadyear DESC, bc.class_code ASC");

		return query(sql + sqlBuf.toString(), gradeTeacherId, new MultiRow());
	}

	public List<BasicClass> getClassesByGradeTeacherIdOrClassTeacherId(
			String teacherId, String campusId) {
		String sql = SQL_FIND_CLASS_BY_GRADE_HEADTEAID_OR_CLASS_TEACHERID;
		sql += " AND bc.is_graduate=0";
		StringBuffer sqlBuf = new StringBuffer();
		if (campusId != null && !"".equals(campusId)) {
			sqlBuf.append(" AND bc.campus_id ='" + campusId + "'");
		}
		sqlBuf.append(" ORDER BY bc.section ASC, bc.acadyear DESC, bc.class_code ASC");

		return query(sql + sqlBuf.toString(), new Object[] { teacherId,
				teacherId, teacherId }, new MultiRow());
	}

	public List<BasicClass> getGraduatingClassesByGradeTeacherId(
			String gradeTeacherId, String campusId, String graduateAcadyear) {
		String sql = SQL_FIND_CLASS_BY_GRADE_HEADTEAID;
		StringBuffer sqlBuf = new StringBuffer();
		if (campusId != null && !"".equals(campusId)) {
			sqlBuf.append(" AND bc.campus_id ='" + campusId + "'");
		}
		if (graduateAcadyear != null && !"".equals(graduateAcadyear)) {
			sqlBuf.append(" AND (cast(substr('"
					+ graduateAcadyear
					+ "',6,9) AS integer) - cast(substr(bc.acadyear,1,4) AS integer))"
					+ " = bc.schooling_length");
		}
		sqlBuf.append(" ORDER BY bc.section ASC, bc.acadyear DESC, bc.class_code ASC");

		return query(sql + sqlBuf.toString(), gradeTeacherId, new MultiRow());
	}

	public List<BasicClass> getGraduatedClassesByGradeTeacherId(
			String gradeTeacherId, String campusId, String graduateAcadyear) {
		String sql = SQL_FIND_CLASS_BY_GRADE_HEADTEAID;
		sql += " AND bc.is_graduate =1";
		StringBuffer sqlBuf = new StringBuffer();
		if (campusId != null && !"".equals(campusId)) {
			sqlBuf.append(" AND bc.campus_id ='" + campusId + "'");
		}
		if (graduateAcadyear != null && !"".equals(graduateAcadyear)) {
			sqlBuf.append(" AND (cast(substr('"
					+ graduateAcadyear
					+ "',6,9) AS integer) - cast(substr(bc.acadyear,1,4) AS integer))"
					+ " = bc.schooling_length");
		}
		sqlBuf.append(" ORDER BY bc.section ASC, bc.acadyear DESC, bc.class_code ASC");

		return query(sql + sqlBuf.toString(), gradeTeacherId, new MultiRow());
	}

	// ======================按schoolId查询班级=======================
	public List<BasicClass> getAllClasses(String schoolId) {
		return  BasicClass.dt(classRemoteService.findByAllSchoolId(schoolId));
		//return query(SQL_FIND_CLASS_ALL_BY_SCHID, schoolId, new MultiRow());
	}

	public List<BasicClass> getClassesByOpenAcadyear(String schoolId,
			String openAcadyear) {
		return BasicClass.dt(classRemoteService.findByOpenAcadyear(schoolId,openAcadyear));
		/*return query(SQL_FIND_CLASS_BY_SCHID_ACADYEAR, new Object[] { schoolId,
				openAcadyear }, new MultiRow());*/
	}
	
	@Override
	public List<BasicClass> getClassesByGradeId(String schoolId,
			String gradeId, String teacherId) {
		return BasicClass.dt(classRemoteService.findByGradeId(schoolId,gradeId,teacherId));
		/*return query(SQL_FIND_CLASS_BY_SCHID_ACADYEAR_TEAID, new Object[] { schoolId,
				gradeId, teacherId}, new MultiRow());*/
	}

	public Map<String, String> getGraduateYearMap(String schoolId) {
		return queryForMap(SQL_FIND_CLS_GRADUATE_BY_SCHID,
				new Object[] { schoolId }, new MapRowMapper<String, String>() {

					public String mapRowKey(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getString("id");
					}

					public String mapRowValue(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getString("lenth");
					}

				});
	}

	public List<BasicClass> getClasses(String schoolId, int section,
			String enrollyear, int schoolingLen) {
		StringBuffer buf = new StringBuffer();
		if (section > -1) {
			buf.append(" AND section= " + section + "");
		}
		if (StringUtils.isNotEmpty(enrollyear)) {
			buf.append(" AND acadyear='" + enrollyear + "'");
		}
		if (schoolingLen > 0) {
			buf.append(" AND schooling_length=" + schoolingLen);
		}
		buf.append(" ORDER BY section ASC,acadyear DESC,class_code ASC");

		List<BasicClass> bcList = query(
				SQL_FIND_CLASS_BY_SCHOOLID_PREFIX + buf.toString(), schoolId,
				new MultiRow());
		return bcList;
	}

	public List<BasicClass> getClasses(String schoolId, String curAcadyear) {
		return BasicClass.dt(classRemoteService.findByIdCurAcadyear(schoolId,curAcadyear));
		
		/*return query(SQL_FIND_BASECLASSS_BY_ACADYEAR, new Object[] { schoolId,
				curAcadyear,curAcadyear }, new MultiRow());*/
	}
	
	public List<BasicClass> getClassesBy(String schoolId, String curAcadyear, int section){
		List<BasicClass> list = BasicClass.dt(classRemoteService.findByIdCurAcadyear(schoolId,curAcadyear));
		List<BasicClass> list1 = new ArrayList<BasicClass>();
		for(BasicClass bc : list){
			if(section == bc.getSection()){
				list1.add(bc);
			}
		}
		return list1;
		/*return query(SQL_FIND_BASECLASSS_BY_ACADYEAR_SECTION, new Object[] { schoolId,
				curAcadyear,curAcadyear, section }, new MultiRow());*/
	}

	public List<BasicClass> getOverSchoolinglenClasses(String schoolId,
			String curAcadyear) {
		return BasicClass.dt(classRemoteService.findByOverSchoolinglen(schoolId,curAcadyear));
		
		/*return query(SQL_FIND_OVERSCHLEN_CLS_BY_SCHID, new Object[] { schoolId,
				curAcadyear }, new MultiRow());*/
	}

	public List<BasicClass> getGraduatingClasses(String schoolId,
			String graduateAcadyear) {
		return BasicClass.dt(classRemoteService.findByGraduateyear(schoolId,graduateAcadyear));
		
		/*return query(SQL_FIND_GRADUTING_CLS_BY_SCHID_GRADEDYEAR, new Object[] {
				schoolId, graduateAcadyear }, new MultiRow());*/
	}

	public List<BasicClass> getGraduatedClasses(String schoolId,
			String graduateAcadyear) {
		List<BasicClass> list = BasicClass.dt(classRemoteService.findByGraduateyear(schoolId,graduateAcadyear));
		List<BasicClass> list1 = new ArrayList<BasicClass>();
		for(BasicClass bc : list){
			if(bc.getGraduatesign() == 1){
				list1.add(bc);
			}
		}
		return list1;
		/*return query(SQL_FIND_GRADUTED_CLS_BY_SCHID_GRADEDYEAR, new Object[] {
				schoolId, graduateAcadyear }, new MultiRow());*/
	}

	// ======================按campusId查询班级=======================

	public List<BasicClass> getClassesByCampusId(String campusId) {
		return BasicClass.dt(classRemoteService.findByCampusId(campusId));
		//return query(SQL_FIND_BASECLASSS_BY_SUBSCHID, campusId, new MultiRow());
	}

	public List<BasicClass> getGraduatingClassesByCampusId(String campusId,
			String graduateAcadyear) {
		return BasicClass.dt(classRemoteService.findByIdYear(campusId,graduateAcadyear));
		/*return query(SQL_FIND_GRADUTING_CLS_BY_SUBSCHID, new Object[] {
				campusId, graduateAcadyear }, new MultiRow());*/
	}

	public List<BasicClass> getGraduatedClassesByCampusId(String campusId,
			String graduateAcadyear) {
		List<BasicClass> list = BasicClass.dt(classRemoteService.findByIdYear(campusId,graduateAcadyear));
		List<BasicClass> list1 = new ArrayList<BasicClass>();
		for(BasicClass bc : list){
			if(bc.getGraduatesign() == 1){
				list1.add(bc);
			}
		}
		return list1;
		/*return query(SQL_FIND_GRADUTED_CLS_BY_SUBSCHID, new Object[] {
				campusId, graduateAcadyear }, new MultiRow());*/
	}

	// ======================根据班级查询年级=======================
	public List<Grade> getGrades(String schoolId) {
	
		List<BasicClass> basicClasses=BasicClass.dt(classRemoteService.findBySchoolId(schoolId));
		if (basicClasses==null) {
			return null;
		}else{
			return getListGrade(basicClasses);
		}
		
//		return query(SQL_FIND_GRADE_BY_SCHID, schoolId,
//				new MultiRowMapper<Grade>() {
//
//					public Grade mapRow(ResultSet rs, int arg1)
//							throws SQLException {
//						return setFieldGrade(rs);
//					}
//				});
	}

	public List<Grade> getGrades(String schoolId, String curAcadyear) {
		List<BasicClass> basicClasses=BasicClass.dt(classRemoteService.findBySchoolIdCurAcadyear(schoolId,curAcadyear));
		if (basicClasses==null) {
			return null;
		}else{
			return getListGrade(basicClasses);
		}
		
//		return query(SQL_FIND_GRADE_BY_SCHID_ACADYEAR, new Object[] { schoolId,
//				curAcadyear, curAcadyear }, new MultiRowMapper<Grade>() {
//
//			public Grade mapRow(ResultSet rs, int arg1) throws SQLException {
//				return setFieldGrade(rs);
//			}
//		});
	}

	public List<Grade> getGraduatingGrades(String schoolId,
			String graduateAcadyear) {
		List<BasicClass> basicClasses=BasicClass.dt(classRemoteService.findBySchoolIdGraduateAcadyear(schoolId,graduateAcadyear));
		if (basicClasses==null) {
			return null;
		}else{
			return getListGrade(basicClasses);
		}
		
//		return query(SQL_FIND_GRADUTING_GRADE_BY_SCHID_GRADUATEYEAR,
//				new Object[] { schoolId, graduateAcadyear },
//				new MultiRowMapper<Grade>() {
//
//					public Grade mapRow(ResultSet rs, int arg1)
//							throws SQLException {
//						return setFieldGrade(rs);
//					}
//				});
	}
	public List<Grade> getNotGraduatingGrades(String schoolId, String acadyear) {
		
		List<BasicClass> basicClasses=BasicClass.dt(classRemoteService.findBySchoolIdAcadyear(schoolId,acadyear));
		if (basicClasses==null) {
			return null;
		}else{
			return getListGrade(basicClasses);
		}
//		return query(SQL_FIND_NOTGRADUTING_GRADE_BY_SCHID_ACADYEAR, new Object[] { schoolId,
//				acadyear, acadyear }, new MultiRowMapper<Grade>() {
//
//			public Grade mapRow(ResultSet rs, int arg1) throws SQLException {
//				return setFieldGrade(rs);
//			}
//		});
	}

	public List<Grade> getGradesByTeacherId(String schoolId, String teacherId) {
		List<BasicClass> basicClasses=BasicClass.dt(classRemoteService.findBySchoolIdTeacherId(schoolId, teacherId));
		if (basicClasses==null) {
			return null;
		}else{
			return getListGrade(basicClasses);
		}	
//		return query(SQL_FIND_GRADE_BY_SCHID_TEACHERID, new Object[] {
//				schoolId, teacherId }, new MultiRowMapper<Grade>() {
//
//			public Grade mapRow(ResultSet rs, int arg1) throws SQLException {
//				return setFieldGrade(rs);
//			}
//		});
	}

	// ======================根据年级查询班级=======================
	public List<BasicClass> getClassesByGrade(String schoolId, int section,
			String enrollyear, int schoolingLen) {
		String sl = String.valueOf(schoolingLen);
		if(schoolingLen == 0){
			sl = "%";
		}
	return	BasicClass.dt(classRemoteService.findClassesByGrade(schoolId, section,enrollyear,sl));	
//		String sl = String.valueOf(schoolingLen);
//		if(schoolingLen == 0){
//			sl = "%";
//		}
//		return query(SQL_FIND_CLASS_BY_GRADE, new Object[] { schoolId, section,
//				enrollyear, sl}, new MultiRow());
	}

	public List<BasicClass> getClassesByGrade(String schoolId, String campusId,
			int section, String enrollyear, int schoolingLen) {
		return BasicClass.dt(classRemoteService.findClassesByGrade(schoolId,campusId, section,enrollyear,schoolingLen));	
//		return query(SQL_FIND_CLASS_BY_CAMPUS_GRADE, new Object[] { schoolId,
//				campusId, section, enrollyear, new Integer(schoolingLen) },
//				new MultiRow());
	}
	@Override
	public List<BasicClass> getClassesBySchoolIdGradeId(String schoolId,
			String gradeId) {
		return BasicClass.dt(classRemoteService.findBySchoolIdGradeId(schoolId,gradeId));	
		
//		return query(SQL_FIND_CLASS_BY_SCHOOLID_AND_GRADEID, new Object[] {
//				schoolId, gradeId}, new MultiRow());
	}
	
	// ======================根据同类班级查询班级=======================
	public List<BasicClass> getClassesByKinClass(String schoolId, int section,
			String enrollyear, int schoolingLen, String artScienceType) {
		return BasicClass.dt(classRemoteService.findClassesByKinClass(schoolId,section,enrollyear,schoolingLen,artScienceType));
		
//		Object[] objs = new Object[] { schoolId, section, enrollyear,
//				new Integer(schoolingLen), artScienceType };
//
//		return query(SQL_FIND_CLASS_BY_KINCLASS_SCHOOLINGLEN, objs,
//				new MultiRow());
	}

	//TODO  
	public List<BasicClass> getClassesByKinClass(String schoolId, int section,
			String enrollyear, String artScienceType) {
		
		return BasicClass.dt(classRemoteService.findByIdSectionYearType(schoolId,section,enrollyear,artScienceType));
		
		
//		return query(SQL_FIND_CLASS_BY_KINCLASS, new Object[] { schoolId,
//				section, enrollyear, artScienceType }, new MultiRow());
	}

	public List<BasicClass> getClassesByKinClass(String schoolId,
			String campusId, int section, String enrollyear, int schoolingLen,
			String artScienceType) {
		
		
		
		Object[] objs = new Object[] { schoolId, campusId, section, enrollyear,
				new Integer(schoolingLen), artScienceType };

		return query(SQL_FIND_CLASS_BY_CAMPUS_KINCLASS, objs, new MultiRow());
	}

	public List<String> getClassType(String schoolId, int section,
			String acadyear) {
		List<BasicClass> basicClasses= BasicClass.dt(classRemoteService.findByIdSectionYear(schoolId,section,acadyear));
		List<String> artScienceTypes=new ArrayList<String>();
		for (BasicClass basicClass : basicClasses) {
			artScienceTypes.add(String.valueOf(basicClass.getArtsciencetype()));
		}
		return artScienceTypes;
//		return query(SQL_FIND_DISTINCT_CLASSTYPE, new Object[] { schoolId,
//				section, acadyear }, new MultiRowMapper<String>() {
//
//			public String mapRow(ResultSet rs, int arg1) throws SQLException {
//				return String.valueOf(rs.getInt("art_science_type"));
//			}
//
//		});
	}

	public List<KinClass> getKinClasses(String schoolId) {
		List<BasicClass> basicClasses= BasicClass.dt(classRemoteService.findBySchoolId(schoolId));
		List<KinClass> kinClasses=new ArrayList<KinClass>();
		for (BasicClass rs : basicClasses) {
			KinClass cls = new KinClass();
			cls.setSchid(rs.getSchid());
			cls.setSection(rs.getSection());
			cls.setAcadyear(rs.getAcadyear());
			cls.setSchoolinglen(rs.getSchoolinglen());
			cls.setType(String.valueOf(rs.getArtsciencetype()));
			kinClasses.add(cls);
		}
		return kinClasses;
//		return query(SQL_FIND_KINCLASS_BY_SCHOOLID, schoolId,
//				new MultiRowMapper<KinClass>() {
//
//					public KinClass mapRow(ResultSet rs, int arg1)
//							throws SQLException {
//						KinClass cls = new KinClass();
//						cls.setSchid(rs.getString("school_id"));
//						cls.setSection(rs.getInt("section"));
//						cls.setAcadyear(rs.getString("acadyear"));
//						cls.setSchoolinglen(rs.getInt("schooling_length"));
//						cls.setType(rs.getString("art_science_type"));
//						return cls;
//					}
//				});
	}

	public Map<String, BasicClass> getClassMapByUnitivecodes(
			String[] unitiveCode) {

		return queryForInSQL(SQL_FIND_GRADENAME_BY_UNITIVECODE, null,
				unitiveCode, new MapRowMapper<String, BasicClass>() {
					public String mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("unitive_code");
					}

					public BasicClass mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						BasicClass basicClass = setField(rs);
						basicClass.setGradeName(rs.getString("grade_name"));
						return basicClass;
					}

				});
	}

	public String[] getClassidByUserid(String userid, String subschoolid) {
		String hql = "select pp.classid from stusys_role_user pu,stusys_role_perm pp,base_class bc "
				+ " where pu.userid=? and pu.roleid=pp.roleid and bc.id=pp.classid and bc.campus_id=?";
		List<String> list = query(hql, new Object[] { userid, subschoolid },
				new MultiRowMapper<String>() {

					public String mapRow(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getString("classid");
					}

				});

		String[] classid = new String[list.size()];

		Iterator<String> iterator = list.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			classid[i] = iterator.next();
		}

		return classid;
	}

    @Override
    public List<BasicClass> getClassesByGradeId(String gradeId) {
    	List<BasicClass> basicClasses= getClassesByGradeIdAll(gradeId);
    	List<BasicClass> basicClasses2=new ArrayList<BasicClass>();		
    	for (BasicClass basicClass : basicClasses) {
    		if(basicClass.getGraduatesign()==0){
    			basicClasses2.add(basicClass);
    		}
    	}
    	return basicClasses2;
    	
    //    return query(SQL_FIND_CLASS_BY_GRADEID, gradeId, new MultiRow());
    }

	@Override
	public void updateGraduateSign(String[] classIds, int sign) {
		
		
		String sql="UPDATE base_class SET is_graduate=?,graduate_date=? WHERE id in";
		updateForInSQL(sql, new Object[] { sign, getCurrentDate()},classIds);
	}

//获取毕业生校对表班级
	
	 @Override
	    public List<BasicClass> getClassesByGradeIdAll(String gradeId) {
		 return BasicClass.dt(classRemoteService.findByGradeIdSortAll(gradeId));
		 
	    //    return query(SQL_FIND_CLASS_BY_GRADEID_ALL, gradeId, new MultiRow());
	    }
	 
	 @Override
	public List<BasicClass> getClassesByGradeIds(String schoolId, String[] gradeIds) {
		if(StringUtils.isNotBlank(schoolId)){
			return	BasicClass.dt(classRemoteService.findByInGradeIds(gradeIds));
		}else{
			return  BasicClass.dt(classRemoteService.findBySchoolIdInGradeIds(schoolId,gradeIds));
		}
		 
		
		 
//		StringBuffer sql=new StringBuffer("SELECT * FROM base_class WHERE is_deleted = 0");
//		if(StringUtils.isNotBlank(schoolId)){
//			sql.append(" and school_id='"+schoolId+"'");
//		}
//		sql.append(" AND grade_id in");
//		return queryForInSQL(sql.toString(), null, gradeIds, new MultiRow(), " ORDER BY section ASC,acadyear DESC,class_code ASC");
	}

	 @Override
	public List<BasicClass> getClassesByTeacherIdAll(String schoolId, String teacherId) {
		 return	BasicClass.dt(classRemoteService.findBySchoolIdTeacherIdAll(schoolId,teacherId));
		 
		// return query(SQL_FIND_GRADE_BY_SCHID_TEACHERID_ALL, new Object[] {	 schoolId, teacherId, teacherId }, new MultiRow());
	}
	 
	 private List<Grade> getListGrade(List<BasicClass> basicClasses)  {
		    List<Grade> grades=new ArrayList<Grade>();
		    for (BasicClass rs : basicClasses) {
		    	Grade grade = new Grade();
		    	grade.setSchid(rs.getSchid());
		    	grade.setSection(rs.getSection());
		    	grade.setAcadyear(rs.getAcadyear());
		    	grade.setSchoolinglen(rs.getSchoolinglen());
		    	//grade.setSubschoolId(rs.getString("subschool_id"));
		    	grade.setId(rs.getGradeId());
		    	grades.add(grade);
			}
		    return grades;
			
		}

}
