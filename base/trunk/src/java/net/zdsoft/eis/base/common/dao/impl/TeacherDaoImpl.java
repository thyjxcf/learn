package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.TeacherDao;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.util.Pagination;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class TeacherDaoImpl extends BaseDao<Teacher> implements TeacherDao {
	private static final String FIND_TEA_PREFIX = "SELECT * FROM base_teacher ";

	private static final String SQL_FIND_TEACHER_BY_ID = FIND_TEA_PREFIX
			+ " WHERE id=?";
	private static final String SQL_FIND_TEACHER_CONTAIN_DELETE_BY_ID = FIND_TEA_PREFIX
			+ " WHERE id=?";
	private static final String SQL_FIND_TEACHERS_BY_IDS = FIND_TEA_PREFIX
			+ " WHERE is_deleted = 0 AND id IN";
	private static final String SQL_FIND_TEACHERS_BY_IDS_WITH_DELETED = FIND_TEA_PREFIX
			+ " WHERE id IN";
	private static final String SQL_FIND_TEACHERS_ALL_BY_IDS = FIND_TEA_PREFIX
			+ " WHERE id IN";
	private static final String SQL_FIND_TEACHERS_BY_POLITY = FIND_TEA_PREFIX
			+ " WHERE is_deleted = 0 AND UNIT_ID = ? AND INCUMBENCY_SIGN <> ? AND polity IN";

	private static final String SQL_FIND_TEACHER_BY_UNITID = FIND_TEA_PREFIX
			+ " WHERE unit_id=? AND is_deleted = 0 and (is_leave_school is null or is_leave_school=0) ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";
	
	private static final String SQL_FIND_TEACHER_BY_SEARCHNAME = FIND_TEA_PREFIX
			+ " WHERE unit_id=? AND is_deleted = 0 and (is_leave_school is null or is_leave_school=0) ";

	private static final String SQL_FIND_TEACHER_BY_WEAVE_UNIT_ID = FIND_TEA_PREFIX
			+ " WHERE weave_unit_id=? AND is_deleted = 0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	private static final String SQL_FIND_TEACHERS_BY_DEPT = FIND_TEA_PREFIX
			+ " WHERE dept_id =? AND is_deleted = 0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	private static final String SQL_FIND_TEACHERS_BY_DEPTS = FIND_TEA_PREFIX
			+ " WHERE is_deleted = 0  AND  dept_id in";

	private static final String SQL_FIND_TEACHERS_BY_DEPT_EXCEPTION = FIND_TEA_PREFIX
			+ " WHERE dept_id <> ? AND is_deleted = 0 ORDER BY display_order";

	private static final String SQL_FIND_CNT_BY_DEPTIDS = "SELECT dept_id,count(id) FROM base_teacher where is_deleted=0 AND dept_id IN";

	private static final String SQL_FIND_TEACHER_BY_UNITID_NAME = FIND_TEA_PREFIX
			+ "WHERE unit_id=? AND teacher_name like ? AND is_deleted = 0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	private static final String SQL_FIND_TEACHER_BY_UNITID_NAME_CODE = FIND_TEA_PREFIX
			+ "WHERE unit_id=? AND teacher_name like ? AND teacher_code like ? AND is_deleted = 0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	private static final String SQL_FIND_TEACHER_BY_DUTY_CODE = FIND_TEA_PREFIX
			+ " where id in (select teacher_id from base_teacher_duty where duty_code =? and is_deleted=0) and is_deleted=0 and unit_id IN";

	private static final String SQL_FIND_TEACHER_BY_DUTY_SUCJECT = FIND_TEA_PREFIX
			+ " where id in (select teacher_id from base_teacher_subject where subject_code =? and is_deleted=0) and is_deleted=0 and unit_id IN";

	private static final String SQL_FIND_TEACHER_BY_UNITIDS = FIND_TEA_PREFIX
			+ "where teacher_name like ? and is_deleted=0 and unit_id IN";

	private static final String SQL_FIND_TEACHINGTEACHER_BY_UNITIDS = FIND_TEA_PREFIX
			+ "where id in (select teacher_id from base_teacher_subject where is_deleted=0) and is_deleted=0 and unit_id IN";

	private static final String SQL_FIND_TEACHER_BY_DEPT_NAME = FIND_TEA_PREFIX
			+ " where  unit_id=? and dept_id=? and teacher_name like ?  and is_deleted = 0 ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))";

	private static final String SQL_FIND_TEACHER_BY_IDENTITYCARD_IDS = "SELECT * from base_teacher"
			+ " where is_deleted = 0 and identity_card in ";
	private static final String SQL_FIND_TEACHER_BY_INSTITUTEID = FIND_TEA_PREFIX
			+ " where unit_id=? and institute_id=? and is_deleted=0 ORDER BY display_order";

	public Teacher setField(ResultSet rs) throws SQLException {
		Teacher teacher = new Teacher();
		teacher.setId(rs.getString("id"));
		teacher.setDeptid(rs.getString("dept_id"));
		teacher.setUnitid(rs.getString("unit_id"));
		teacher.setTchId(rs.getString("teacher_code"));
		if (rs.getInt("is_deleted") == 0) {
			teacher.setName(rs.getString("teacher_name"));
		} else {
			teacher.setName(rs.getString("teacher_name") + "(已删除)");
		}
		teacher.setPname(rs.getString("old_name"));
		teacher.setSex(String.valueOf(rs.getInt("sex")));
		teacher.setBirthday(rs.getTimestamp("birthday"));
		teacher.setPerNative(rs.getString("native_place"));
		teacher.setNation(rs.getString("nation"));
		teacher.setPolity(rs.getString("polity"));
		teacher.setPolityJoin(rs.getTimestamp("polity_join"));
		teacher.setStulive(rs.getString("academic_qualification"));
		teacher.setFstime(rs.getTimestamp("graduate_time"));
		teacher.setFsschool(rs.getString("graduate_school"));
		teacher.setMajor(rs.getString("major"));
		teacher.setWorkdate(rs.getTimestamp("work_date"));
		teacher.setEusing(rs.getString("incumbency_sign"));
		teacher.setTitle(rs.getString("title"));
		teacher.setIdcard(rs.getString("identity_card"));
		teacher.setPersonTel(rs.getString("mobile_phone"));
		teacher.setOfficeTel(rs.getString("office_tel"));
		teacher.setRegistertype(rs.getInt("register_type"));
		teacher.setRegister(rs.getString("register_place"));
		teacher.setEmail(rs.getString("email"));
		teacher.setRemark(rs.getString("remark"));
		teacher.setCardNumber(rs.getString("card_number"));
		teacher.setHomepage(rs.getString("homepage"));
		teacher.setLinkPhone(rs.getString("link_phone"));
		teacher.setLinkAddress(rs.getString("link_address"));
		teacher.setPostalcode(rs.getString("postalcode"));
		teacher.setRegionCode(rs.getString("region_code"));
		teacher.setCreationTime(rs.getTimestamp("creation_time"));
		teacher.setModifyTime(rs.getTimestamp("modify_time"));
		teacher.setDisplayOrder(rs.getInt("display_order"));
		teacher.setChargeNumber(rs.getString("charge_number"));
		teacher.setChargeNumberType(rs.getInt("charge_number_type"));
		teacher.setDirId(rs.getString("dir_id"));
		teacher.setFilePath(rs.getString("file_path"));
		teacher.setReturnedChinese(rs.getString("returned_chinese"));
		teacher.setCountry(rs.getString("country"));
		teacher.setWeaveType(rs.getString("weave_type"));
		teacher.setMultiTitle(rs.getString("multi_title"));
		teacher.setTeachStatus(rs.getString("teach_status"));
		teacher.setInstituteId(rs.getString("institute_id"));
		teacher.setWorkTeachDate(rs.getTimestamp("work_teach_date"));
		teacher.setOldAcademicQualification(rs
				.getString("old_academic_qualification"));
		teacher.setSpecTechnicalDuty(rs.getString("spec_technical_duty"));
		teacher.setSpecTechnicalDutyDate(rs
				.getTimestamp("spec_technical_duty_date"));
		teacher.setHomeAddress(rs.getString("home_address"));
		teacher.setGeneralCard(rs.getString("general_card"));
		teacher.setIdentityType(rs.getString("identity_type"));
		teacher.setInPreparation(rs.getString("in_preparation"));
		teacher.setJob(rs.getString("job"));
		teacher.setWeaveUnitId(rs.getString("weave_unit_id"));
		teacher.setCertificationType(rs.getString("certification_type"));
		teacher.setPutonghuaGrade(rs.getString("putonghua_grade"));
		teacher.setLearningPeriod(rs.getString("learning_period"));
		teacher.setNormalGraduated(rs.getString("normal_graduated"));
		teacher.setFreeNormal(rs.getString("free_normal"));
		teacher.setSpecialTeacher(rs.getString("special_teacher"));
		teacher.setHighestDegree(rs.getString("highest_degree"));
		teacher.setHighestDegreeInstitutions(rs
				.getString("highest_degree_institutions"));
		teacher.setHighestDiploma(rs.getString("highest_diploma"));
		teacher.setHighestDiplomaInstitutions(rs
				.getString("highest_diploma_institutions"));
		teacher.setLaborContractSituation(rs
				.getString("labor_contract_situation"));
		teacher.setFiveInsurancePayments(rs
				.getString("five_insurance_payments"));
		teacher.setCertificationCode(rs.getString("certification_code"));
		teacher.setOneYearTraining(rs.getString("one_year_training"));
		teacher.setMainCourse(rs.getString("main_course"));
		teacher.setSpecialEduTraining(rs.getString("special_edu_training"));
		teacher.setPreschoolSpecialty(rs.getString("preschool_specialty"));
		teacher.setCourseSituation(rs.getString("course_situation"));
		teacher.setCourseSubjectCategory(rs
				.getString("course_subject_category"));
		teacher.setIsDoubleCertification(rs
				.getString("is_double_certification"));
		teacher.setOtherCertification(rs.getString("other_certification"));
		teacher.setOtherCertificationLevel(rs
				.getString("other_certification_level"));
		teacher.setEnterpriseWorkTime(rs.getString("enterprise_work_time"));
		teacher.setTutorType(rs.getString("tutor_type"));
		teacher.setSubjectAreas(rs.getString("subject_areas"));
		teacher.setExpertType(rs.getString("expert_type"));
		teacher.setResearchAwards(rs.getString("research_awards"));
		teacher.setOverseasDegree(rs.getString("overseas_degree"));
		teacher.setOverseasTraining(rs.getString("overseas_training"));
		teacher.setAcademicStructure(rs.getString("academic_structure"));
		teacher.setInfoPin(rs.getString("info_pin"));
		teacher.setDuty(rs.getString("duty"));
		teacher.setSubschoolId(rs.getString("subschool_id"));
		teacher.setEducationWorkDate(rs.getTimestamp("education_work_date"));
		teacher.setBackboneTeacherLevel(rs.getString("backbone_teacher_level"));
		teacher.setJobGroup(rs.getString("job_group"));
		teacher.setIsHaveCountCertification(rs
				.getString("is_have_count_certification"));
		teacher.setCountCertificationCode(rs
				.getString("count_certification_code"));
		teacher.setIsPreeEduGraduate(rs.getString("is_pree_edu_graduate"));
		teacher.setFileNumber(rs.getString("file_number"));
		teacher.setHidePhone(rs.getInt("hide_phone"));
		
		teacher.setMobileCornet(rs.getString("mobile_cornet"));
		return teacher;
	}

	@Override
	public List<Teacher> getTeacherListByPolity(String unitId, String[] polities) {
		return queryForInSQL(
				SQL_FIND_TEACHERS_BY_POLITY,
				new Object[] { unitId, "21" },
				polities,
				new MultiRow(),
				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
	}

	public Teacher getTeacher(String teacherId) {
		return query(SQL_FIND_TEACHER_BY_ID, new String[] { teacherId },
				new SingleRow());
	}

	public Teacher getTeacherContainDelete(String teacherId) {
		return query(SQL_FIND_TEACHER_CONTAIN_DELETE_BY_ID,
				new String[] { teacherId }, new SingleRow());
	}

	public List<Teacher> getTeachers(String[] tchIds) {
		return queryForInSQL(
				SQL_FIND_TEACHERS_BY_IDS,
				null,
				tchIds,
				new MultiRow(),
				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");

	}

	@Override
	public List<Teacher> getTeachersWithDeleted(String[] teacherIds) {
		return queryForInSQL(SQL_FIND_TEACHERS_BY_IDS_WITH_DELETED, null,
				teacherIds, new MultiRow());
	}

	public List<Teacher> getTeachersAll(String[] tchIds) {
		return queryForInSQL(
				SQL_FIND_TEACHERS_ALL_BY_IDS,
				null,
				tchIds,
				new MultiRow(),
				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");

	}

	public List<Teacher> getTeachers(String unitId) {

		return query(SQL_FIND_TEACHER_BY_UNITID, unitId, new MultiRow());
	}

	public List<Teacher> getTeachersByDeptId(String deptId) {
		return query(SQL_FIND_TEACHERS_BY_DEPT, new String[] { deptId },
				new MultiRow());
	}

	public List<Teacher> getExceptionTeachersByDeptId(String deptId) {
		return query(SQL_FIND_TEACHERS_BY_DEPT_EXCEPTION,
				new String[] { deptId }, new MultiRow());
	}

	public List<Teacher> getTeachersByFaintness(String unitId, String name) {
		return query(SQL_FIND_TEACHER_BY_UNITID_NAME, new String[] { unitId,
				name + "%" }, new MultiRow());
	}

	public List<Teacher> getTeachersByFaintness(String unitId,
			String teacherName, String teacherCode) {
		if (null == teacherName) {
			teacherName = "";
		}
		if (null == teacherCode) {
			teacherCode = "";
		}
		return query(SQL_FIND_TEACHER_BY_UNITID_NAME_CODE, new Object[] {
				unitId, teacherName + "%", teacherCode + "%" }, new MultiRow());
	}

	public Map<String, Integer> getTeacherCount(String[] deptIds) {
		Map<String, Integer> rtnMap = queryForInSQL(SQL_FIND_CNT_BY_DEPTIDS,
				null, deptIds, new MapRowMapper<String, Integer>() {

					public String mapRowKey(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getString(1);
					}

					public Integer mapRowValue(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getInt(2);
					}
				}, " Group by dept_id");
		return rtnMap;
	}
	
	@Override
	public Map<String, Integer> getUnionSectionSexCount(String unitId,
			String unionid) {
    	String sql = "select u.unit_use_type, s.sex, count(1) as teacount from base_unit u, base_teacher s where u.id = s.unit_id"+
				" and u.union_code like ? and u.unit_state = 1 and (s.is_leave_school is null or s.is_leave_school=0) and s.is_deleted = 0 and s.sex is not null"+
				" and u.is_deleted = 0 group by u.unit_use_type,s.sex";
		return queryForMap(sql, new Object[]{unionid+"%"}, new MapRowMapper<String, Integer>(){
		
		@Override
		public String mapRowKey(ResultSet rs, int rowNum)
				throws SQLException {
			return rs.getString("unit_use_type")+ "," + rs.getString("sex");
		}
		
		@Override
		public Integer mapRowValue(ResultSet rs, int rowNum)
				throws SQLException {
			return rs.getInt("teacount");
		}});
    }
	
	public Map<String, Teacher> getTeacherMap(String unitId) {
		return queryForMap(SQL_FIND_TEACHER_BY_UNITID, new String[] { unitId },
				new MapRow());
	}

	public Map<String, Teacher> getTeacherMap(String[] teacherIds) {
		return queryForInSQL(
				SQL_FIND_TEACHERS_BY_IDS,
				null,
				teacherIds,
				new MapRow(),
				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
	}

	@Override
	public Map<String, Teacher> getTeacherWithDeletedMap(String[] teacherIds) {
		return queryForInSQL(SQL_FIND_TEACHERS_BY_IDS_WITH_DELETED, null,
				teacherIds, new MapRow());
	}

	@Override
	public List<Teacher> getTeacherByInstituteId(String unitId,
			String instituteId) {
		return query(SQL_FIND_TEACHER_BY_INSTITUTEID, new Object[] { unitId,
				instituteId }, new MultiRow());
	}

	public List<Teacher> getTeachersOrder(String[] tchIds) {
		return queryForInSQL(SQL_FIND_TEACHERS_BY_IDS, null, tchIds,
				new MultiRow(), "order by f_trans_pinyin_capital(teacher_name)");
	}

	@Override
	public boolean isExistPrincipan(String teacherId, String deptId) {
		String sql = "select count(id) FROM base_teacher WHERE dept_id =? and id = ? AND is_deleted = 0 ";
		int isExists = queryForInt(sql.toString(), new Object[] { deptId,
				teacherId });
		return isExists == 0 ? false : true;
	}

	@Override
	public List<Teacher> getTeachers(String unitId, Pagination page) {
		if (page != null) {
			return query(SQL_FIND_TEACHER_BY_UNITID, unitId, new MultiRow(),
					page);
		} else {
			return query(SQL_FIND_TEACHER_BY_UNITID, unitId, new MultiRow());
		}
	}
	
	@Override
	public List<Teacher> getTeachers(String unitId, String deptId,
			String searchName, Pagination page) {
		StringBuffer sbf = new StringBuffer(SQL_FIND_TEACHER_BY_SEARCHNAME);
		List<Object> objects = new ArrayList<Object>();
		objects.add(unitId);
		if(StringUtils.isNotBlank(deptId)){
			sbf.append(" and dept_id = ? ");
			objects.add(deptId);
		}
		if(StringUtils.isNotBlank(searchName)){
			sbf.append(" and teacher_name like ? ");
			objects.add("%"+searchName+"%");
		}
		sbf.append(" and base_teacher.id in(select base_user.owner_id from base_user,base_teacher where base_user.user_state=1 and base_user.owner_id=base_teacher.id and base_user.is_deleted = 0)");
		sbf.append(" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
		if(page!=null)
		return query(sbf.toString(), objects.toArray(new String[0]), new MultiRow(),
				page);
		return query(sbf.toString(), objects.toArray(new String[0]), new MultiRow());
	}
	
	@Override
	public List<Teacher> getTeachersByName(String unitId, String unionCode, String searchName, Pagination page) {
		StringBuffer sbf = new StringBuffer("SELECT t.* FROM base_teacher t, base_unit u WHERE t.unit_id=u.id and t.is_deleted = 0 and (t.is_leave_school is null or t.is_leave_school=0) ");
		List<Object> objects = new ArrayList<Object>();
		if(StringUtils.isNotBlank(unitId)){
			sbf.append(" and t.unit_id = ? ");
			objects.add(unitId);
		}
		if(StringUtils.isNotBlank(unionCode)){
			sbf.append(" and u.union_code like ? ");
			objects.add(unionCode + "%");
		}
		if(StringUtils.isNotBlank(searchName)){
			//sbf.append(" and t.teacher_name = ? ");
			//objects.add(searchName);
			sbf.append(" and teacher_name like ? ");
			objects.add("%"+searchName+"%");
		}
		sbf.append(" and t.id in(select base_user.owner_id from base_user,base_teacher where base_user.user_state=1 and base_user.owner_id=base_teacher.id and base_user.is_deleted = 0)");
		sbf.append(" ORDER BY to_number(translate(t.display_order,'0123456789' || t.display_order,'0123456789'))");
		if(page != null){
			return query(sbf.toString(), objects.toArray(new String[0]), new MultiRow(),page);
		}
		else{
			return query(sbf.toString(), objects.toArray(new String[0]), new MultiRow());
		}
	}
	
	@Override
	public List<Teacher> getTeachersByIdAndName(String[] teacherIds, String teacherName, Pagination page){
		StringBuffer sbf = new StringBuffer("SELECT * FROM base_teacher WHERE is_deleted = 0 and (is_leave_school is null or is_leave_school=0) ");
		List<Object> objects = new ArrayList<Object>();
		if(StringUtils.isNotBlank(teacherName)){
			sbf.append(" and teacher_name like ? ");
			objects.add("%" + teacherName + "%");
		}
		sbf.append(" and id in ");
		if(page != null){
			return queryForInSQL(sbf.toString(), objects.toArray(new String[0]), teacherIds, new MultiRow(), " ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))", page);
		}
		else{
			return queryForInSQL(sbf.toString(), objects.toArray(new String[0]), teacherIds, new MultiRow(), " ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
		}
	}

	@Override
	public List<Teacher> getTeachersByOtherName(String unitId,String unionCode, String searchName, String type,
			String runschtype, String unitName, String deptName,String dutyName, Pagination page) {//TODO
		StringBuffer sbf = new StringBuffer("SELECT t.* FROM base_teacher t,(select unit.*,school.school_type as sch_type, school.run_school_type as run_sch_type from base_unit unit left join base_school school on unit.id=school.id) s " +
				"WHERE t.unit_id=s.id and t.is_deleted = 0 and s.is_deleted = 0 and (t.is_leave_school is null or t.is_leave_school=0) ");
		List<Object> objects = new ArrayList<Object>();
		if(StringUtils.isNotBlank(unitId)){
			sbf.append(" and t.unit_id = ? ");
			objects.add(unitId);
		}
		if(StringUtils.isNotBlank(unionCode)){
			sbf.append(" and s.union_code like ? ");
			objects.add(unionCode + "%");
		}
		if(StringUtils.isNotBlank(searchName)){
			sbf.append(" and t.teacher_name like ? ");
			objects.add("%"+searchName+"%");
		}
		if(StringUtils.isNotBlank(type)){
			sbf.append(" and s.sch_type=? ");
			objects.add(type);
		}
		if(StringUtils.isNotBlank(runschtype)){
			sbf.append(" and s.run_sch_type=? ");
			objects.add(runschtype);
		}
		if(StringUtils.isNotBlank(unitName)){
			sbf.append(" and s.unit_name like ? ");
			objects.add("%"+unitName+"%");
		}
		if(StringUtils.isNotBlank(deptName)&&StringUtils.isNotBlank(unitId)){
			sbf.append(" and t.dept_id in(select id from base_dept where base_dept.unit_id=? and base_dept.dept_name like ? )");
			objects.add(unitId);
			objects.add("%"+deptName+"%");
		}
		if(StringUtils.isNotBlank(deptName)){
			sbf.append(" and t.dept_id in(select id from base_dept where base_dept.dept_name like ? )");
			objects.add("%"+deptName+"%");
		}
		if(StringUtils.isNotBlank(dutyName)){
			sbf.append(" and t.id in(select teacher_id from base_teacher_duty where duty_code in(select this_id from base_mcode_detail where mcode_content like ? and mcode_id in('DM-JYJZW','DM-XXZW')))");
			objects.add("%"+dutyName+"%");
		}
		sbf.append(" and t.id in(select base_user.owner_id from base_user,base_teacher where base_user.user_state=1 and base_user.owner_id=base_teacher.id and base_user.is_deleted = 0)");
		sbf.append(" ORDER BY t.unit_id,t.dept_id desc");
		if(page != null){
			return query(sbf.toString(), objects.toArray(new String[0]), new MultiRow(),page);
		}
		else{
			return query(sbf.toString(), objects.toArray(new String[0]), new MultiRow());
		}
	}

	@Override
	public List<Teacher> getTeachersByDeptId(String deptId, Pagination page) {
		if (page != null) {
			return query(SQL_FIND_TEACHERS_BY_DEPT, new String[] { deptId },
					new MultiRow(), page);
		} else {
			return query(SQL_FIND_TEACHERS_BY_DEPT, new String[] { deptId },
					new MultiRow());
		}

	}

	@Override
	public Map<String, Teacher> getTeacherMapByUnitIdNameCodes(String unitid,
			String[] nameCodes) {
		String sql = "select * from base_teacher  t where t.is_deleted =0 and t.unit_id =? and t.teacher_name || t.teacher_code in";
		return this.queryForInSQL(sql, new Object[] { unitid }, nameCodes,
				new MapRowMapper<String, Teacher>() {

					@Override
					public String mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("teacher_name")
								+ rs.getString("teacher_code");
					}

					@Override
					public Teacher mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return setField(rs);
					}

				});
	}

	@Override
	public List<Teacher> getTeachersByUserState(String unitId, Pagination page) {
		String sql = "select * from base_teacher where unit_id =? "
				+ "and EXISTS (select 1 from base_user where unit_id =? AND is_deleted = 0 "
				+ "and owner_id=base_teacher.id and  user_state =1)";
		if (page != null) {
			return this.query(sql, new Object[] { unitId, unitId },
					new MultiRow(), page);
		} else {
			return this.query(sql, new Object[] { unitId, unitId },
					new MultiRow());
		}
	}

	@Override
	public List<Teacher> getTeachersByDeptIdOrUserState(String deptId,
			Pagination page) {
		String sql = "select * from base_teacher where dept_id =? "
				+ "and EXISTS (select 1 from base_user where is_deleted = 0 "
				+ "and owner_id=base_teacher.id and  user_state =1)";
		if (page != null) {
			return this.query(sql, new Object[] { deptId }, new MultiRow(),
					page);
		} else {
			return this.query(sql, new Object[] { deptId }, new MultiRow());
		}
	}

	@Override
	public List<Teacher> getTeachersByWeaveUnit(String weaveUnitId) {
		String sql = SQL_FIND_TEACHER_BY_WEAVE_UNIT_ID;
		return query(sql, new Object[] { weaveUnitId }, new MultiRow());
	}

	public List<Teacher> findTeachersByDutyCode(String[] unitIds,
			String dutyCode) {
		return queryForInSQL(SQL_FIND_TEACHER_BY_DUTY_CODE,
				new Object[] { dutyCode }, unitIds, new MultiRow());
	}

	public List<Teacher> findTeachersBySubject(String[] unitIds,
			String subjectCode) {
		return queryForInSQL(SQL_FIND_TEACHER_BY_DUTY_SUCJECT,
				new Object[] { subjectCode }, unitIds, new MultiRow());
	}

	public List<Teacher> findTeachersByUnitIds(String[] unitIds,
			String searchName) {
		return queryForInSQL(SQL_FIND_TEACHER_BY_UNITIDS, new Object[] { "%"
				+ searchName.trim() + "%" }, unitIds, new MultiRow());
	}

	public List<Teacher> findTeacheringTeachersByUnitIds(String[] unitIds) {
		return queryForInSQL(SQL_FIND_TEACHINGTEACHER_BY_UNITIDS, null,
				unitIds, new MultiRow());
	}

	@Override
	public List<Teacher> getTeachersByDeptIdName(String unitId, String deptId,
			String name) {
		name = StringEscapeUtils.escapeSql(name);
		return query(SQL_FIND_TEACHER_BY_DEPT_NAME, new Object[] { unitId,
				deptId, name + "%" }, new MultiRow());
	}

	public List<Teacher> getTeacherMapByIdentityCards(String[] identitycards) {
		return queryForInSQL(
				SQL_FIND_TEACHER_BY_IDENTITYCARD_IDS,
				new Object[] {},
				identitycards,
				new MultiRow(),
				" ORDER BY to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
	}

	@Override
	public List<Teacher> getTeachersByDeptIds(String[] deptIds) {
		return queryForInSQL(
				SQL_FIND_TEACHERS_BY_DEPTS,
				null,
				deptIds,
				new MultiRow(),
				" ORDER BY dept_id,to_number(translate(display_order,'0123456789' || display_order,'0123456789'))");
	}

	public List<Teacher> getTeachersByBirthday(String unitId,String beginDate, String endDate) {
		String sql = "select * from base_teacher where unit_id=? AND is_deleted = 0 and (is_leave_school is null or is_leave_school=0) and to_char(birthday,'mmdd') between ? and ? order by to_char(birthday,'mmdd') ";
		return query(sql, new Object[] { unitId,beginDate, endDate}, new MultiRow());
	}

    @Override
    public List<Teacher> getTeachersWithModifyTime(String unitId, String dataModifyTime, Pagination page) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        try {
            Date date = sdf.parse(dataModifyTime);
            String sql = "select * from base_teacher where unit_id = ? and modify_time > ?";
            return query(sql, new Object[] {unitId, date}, new MultiRow(), page);
        }
        catch (ParseException e) {
        }
        return new ArrayList<Teacher>();
    }

    @Override
    public List<Teacher> getTeachersWithModifyTime(String dataModifyTime, Pagination page) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        try {
            Date date = sdf.parse(dataModifyTime);
            String sql = "select * from base_teacher where modify_time > ?";
            return query(sql, new Object[] {date}, new MultiRow(), page);
        }
        catch (ParseException e) {
        }
        return new ArrayList<Teacher>();
    }

	@Override
	public Map<String, Teacher> getTeacherByIdentityCards(String[] identitycards) {
		return this.queryForInSQL(SQL_FIND_TEACHER_BY_IDENTITYCARD_IDS, null, identitycards,
			new MapRowMapper<String, Teacher>() {

				@Override
				public String mapRowKey(ResultSet rs, int rowNum)
						throws SQLException {
					return rs.getString("identity_card");
				}

				@Override
				public Teacher mapRowValue(ResultSet rs, int rowNum)
						throws SQLException {
					return setField(rs);
				}

			});
	}
}
