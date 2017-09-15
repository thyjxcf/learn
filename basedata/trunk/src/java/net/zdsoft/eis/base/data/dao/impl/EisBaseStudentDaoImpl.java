package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.data.dao.EisBaseStudentDao;
import net.zdsoft.eis.base.data.entity.EisBaseStudent;
import net.zdsoft.eis.base.data.entity.StudentImport;
import net.zdsoft.eis.base.simple.dao.impl.AbstractStudentDaoImpl;
import net.zdsoft.keel.util.DateUtils;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author weixh
 * @since 2016-3-1 上午10:19:13
 */
public class EisBaseStudentDaoImpl extends AbstractStudentDaoImpl<EisBaseStudent> implements EisBaseStudentDao {
	private static final String SQL_INSERT_BASESTUDENT = "INSERT INTO base_student(id,school_id,class_id,"
			+ "student_name,old_name,student_code,unitive_code,identitycard_type,identity_card,sex,birthday,"
			+ "mobile_phone,is_leave_school,enroll_year,class_inner_code,economy_state,card_number,is_singleton,"
			+ "stayin,boorish,is_local_source,source,study_mode,native_place,homepage,email,link_phone,"
			+ "link_address,postalcode,region_code,creation_time,modify_time,is_deleted,event_source,is_freshman,password,now_state,school_district,"
			+ "meetexam_code,source_place,is_specialid,dir_id,file_path,health,nation,background,polity_join_date,home_address,"
			+ "to_school_date,dorm,dorm_tel,spec_id,specpoint_id,enter_score,bank_card_no,old_school_name,old_school_date) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String SQL_DELETE_BASESTUDENT_BY_IDS = "UPDATE base_student SET is_deleted = 1 WHERE id IN ";

	private static final String SQL_UPDATE_BASESTUDENT = "UPDATE base_student SET school_id=?,class_id=?,"
			+ "student_name=?,old_name=?,student_code=?,unitive_code=?,identitycard_type=?,identity_card=?,sex=?,birthday=?,"
			+ "mobile_phone=?,is_leave_school=?,enroll_year=?,class_inner_code=?,economy_state=?,card_number=?,is_singleton=?,"
			+ "stayin=?,boorish=?,is_local_source=?,source=?,study_mode=?,native_place=?,homepage=?,email=?,link_phone=?,link_address=?,postalcode=?,region_code=?,modify_time=?,is_deleted=?,event_source=?,is_freshman=?,password=?,now_state=?,school_district=?,meetexam_code=?,source_place=?,is_specialid=?,dir_id=?,file_path=?,health=?,nation=?,background=?,polity_join_date=?,home_address=?,to_school_date=?,dorm=?,dorm_tel=?,spec_id=?,specpoint_id=?,enter_score=?,bank_card_no=?,old_school_name=?,old_school_date=? WHERE id=?";
	
	//==================FIND===================
	private static final String SQL_FIND_BASESTUDENT_BY_ID = "SELECT * FROM base_student WHERE id=?";
	
	public EisBaseStudent setField(ResultSet rs) throws SQLException {
		EisBaseStudent student = new EisBaseStudent();
		student.setId(rs.getString("id"));
        student.setSchid(rs.getString("school_id"));
        student.setClassid(rs.getString("class_id"));
        student.setStuname(rs.getString("student_name"));
        student.setStucode(rs.getString("student_code"));
        student.setUnitivecode(rs.getString("unitive_code"));
        student.setIdentitycardType(rs.getString("identitycard_type"));
        student.setIdentitycard(rs.getString("identity_card"));
        student.setSex(rs.getInt("sex"));
        student.setNation(rs.getString("nation"));
        student.setBirthday(rs.getTimestamp("birthday"));
        student.setLeavesign(rs.getInt("is_leave_school"));
        student.setClassorderid(rs.getString("class_inner_code"));
        student.setPassword(rs.getString("password"));
        student.setBankCardNo(rs.getString("BANK_CARD_NO"));
        student.setBackground(rs.getString("background"));
        student.setLinkPhone(rs.getString("link_phone"));
        student.setHomeAddress(rs.getString("home_address"));
        student.setNativePlace(rs.getString("native_place"));
        student.setPolityJoinDate(rs.getTimestamp("polity_join_date"));
        student.setDirId(rs.getString("dir_id"));
        student.setFilePath(rs.getString("file_path"));
		
		student.setSpecId(rs.getString("spec_id"));
		student.setSpecpointId(rs.getString("specpoint_id"));
		student.setRecruitMode(rs.getInt("recruit_mode"));
		student.setLearnMode(rs.getInt("learn_mode"));
		student.setCooperateType(rs.getInt("cooperate_type"));
		student.setCooperateSchCode(rs.getString("cooperate_school_code"));
		student.setRegisterPlace(rs.getString("register_place"));
		student.setRegisterType(rs.getInt("register_type"));
		student.setIsLowAllowance(rs.getInt("is_low_allowce"));
		student.setIsEnjoyState(rs.getInt("is_enjoy_state_grants"));
		student.setGrantStandard(rs.getDouble("grant_standard"));
		student.setStudentType(rs.getString("student_type"));
		student.setSourceType(rs.getInt("source_type"));
		student.setTeachPointsName(rs.getString("teaching_points_name"));
		student.setStudentCategory(rs.getString("student_category"));
		student.setCompatriots(rs.getInt("compatriots"));
		student.setGraduateSchool(rs.getString("graduate_school"));
		student.setRemark(rs.getString("remark"));
		student.setRegisterCode(rs.getString("register_code"));
		student.setSpellName(rs.getString("spell_name"));
		student.setHomePlace(rs.getString("home_place"));
		student.setLastDegree(rs.getString("last_degree"));
		student.setRegisterAddress(rs.getString("register_address"));
		student.setBloodType(rs.getString("blood_type"));
		student.setExamCode(rs.getString("exam_code"));
		student.setPhotoNo(rs.getString("photo_no"));
		student.setBenefitMoney(rs.getDouble("benefit_money"));
		student.setStudentMode(rs.getString("student_mode"));
		student.setRewardSpecId(rs.getString("reward_spec_id"));
		student.setSchLengthType(rs.getString("sch_length_type"));
		student.setPlanMode(rs.getString("plan_mode"));
		student.setIsFamilySide(rs.getInt("is_family_side"));
		student.setStudentQq(rs.getString("student_qq"));
		student.setOneCardNumber(rs.getString("one_card_number"));
		student.setSeatNumber(rs.getString("seat_number"));
		student.setEnglishName(rs.getString("english_name"));
		student.setToschooltype(rs.getString("toschooltype"));
		student.setCountry(rs.getString("country"));
		student.setSchoolinglen(rs.getString("schoolinglen"));
		student.setMarriage(rs.getString("marriage"));
		student.setNativeType(rs.getString("native_type"));
		student.setLocalPoliceStation(rs.getString("local_police_station"));
		student.setNowaddress(rs.getString("nowaddress"));
		student.setHomepostalcode(rs.getString("home_postalcode"));
		student.setSource(rs.getString("source"));
		student.setCooperateForm(rs.getString("cooperate_form"));
		student.setTrainStation(rs.getString("train_station"));
		student.setOldStuCode(rs.getString("old_student_code"));
		student.setFamilymobile(rs.getString("family_mobile"));
		student.setIsRereading(rs.getString("is_rereading"));
		student.setRegularClass(rs.getString("regular_class"));
		student.setAge(rs.getInt("age"));
		student.setDegreePlace(rs.getString("degree_place"));
		student.setDisabilityType(rs.getString("disability_type"));
		student.setIsBoarding(rs.getString("is_boarding"));
		student.setIsMigration(rs.getString("is_migration"));
		student.setIsFallEnrollment(rs.getString("is_fall_enrollment"));
		student.setIsPreedu(rs.getString("is_preedu"));
		student.setAccountAttribution(rs.getString("account_attribution"));
		
		student.setFingerprint(rs.getString("fingerprint"));
		student.setFingerprint2(rs.getString("fingerprint2"));
		student.setOldSchcode(rs.getString("old_schcode"));
		student.setCompulsoryedu(rs.getString("compulsoryedu"));
		student.setComputerLevel(rs.getString("computer_level"));
		student.setEnglishLevel(rs.getString("english_level"));
		student.setFamilyOrigin(rs.getString("family_origin"));
		student.setFlowingPeople(rs.getInt("flowing_people"));
		student.setFormerClassLeader(rs.getString("former_class_leader"));
		student.setFormerClassTeacher(rs.getString("former_class_teacher"));
		student.setIsLocalSchoolEnrollment(rs.getString("is_local_school_enrollment"));
		student.setPartyDate(rs.getDate("party_date"));
		student.setPartyIntroducer(rs.getString("party_introducer"));
		student.setPolityDate(rs.getDate("polity_date"));
		student.setPolityIntroducer(rs.getString("polity_introducer"));
		student.setRegisterInfo(rs.getInt("register_info"));
		student.setReligion(rs.getString("religion"));
		student.setRxArtResult(rs.getDouble("rx_art_result"));
		student.setRxEnglishResult(rs.getDouble("rx_english_result"));
		student.setRxIntegratedResult(rs.getDouble("rx_integrated_result"));
		student.setRxMathResult(rs.getDouble("rx_math_result"));
		student.setRxResult(rs.getDouble("rx_result"));
		student.setStrong(rs.getString("strong"));
		student.setWlClassIntention(rs.getString("wl_class_intention"));
		student.setZkArtResult(rs.getDouble("zk_art_result"));
		student.setZkCode(rs.getString("zk_code"));
		student.setZkComputerResult(rs.getDouble("zk_computer_result"));
		student.setZkEnglishResult(rs.getDouble("zk_english_result"));
		student.setZkExperimentResult(rs.getDouble("zk_experiment_result"));
		student.setZkExtro(rs.getString("zk_extro"));
		student.setZkIntegratedResult(rs.getDouble("zk_integrated_result"));
		student.setZkMathResult(rs.getDouble("zk_math_result"));
		student.setZkPeResult(rs.getDouble("zk_pe_result"));
		student.setZkResult(rs.getDouble("zk_result"));
		student.setZkStrong(rs.getString("zk_strong"));
		student.setDistance(rs.getString("distance"));
		student.setTrafficWay(rs.getString("traffic_way"));
		student.setIsNeedBus(rs.getInt("is_need_bus"));
		student.setIsGovernmentBear(rs.getInt("is_government_bear"));
		student.setIsNeedAssistance(rs.getInt("is_need_assistance"));
		student.setIsOrphan(rs.getInt("is_orphan"));
		student.setIsEnjoyAssistance(rs.getInt("is_enjoy_assistance"));
		student.setIsMartyrChild(rs.getInt("is_martyr_child"));
		student.setIdentitycardValid(rs.getString("identitycard_valid"));
		student.setUrbanRegisterType(rs.getString("urban_register_type"));
		student.setSocialRecruitmentType(rs.getString("social_recruitment_type"));
		student.setMobilePhone(rs.getString("mobile_phone"));
		return student;
	}

	@Override
	public void addStudent(EisBaseStudent student) {
		if (StringUtils.isBlank(student.getId()))
			student.setId(createId());
		//为了保证身份证入库到表中都是大写字母
		if(StringUtils.isNotBlank(student.getIdentitycardType())){
			if (StringUtils.equals(student.getIdentitycardType(), "1")) {
				student.setIdentitycard(StringUtils.upperCase(student.getIdentitycard()));
			} 
		}
		
		student.setCreationTime(new Date());
		student.setModifyTime(new Date());

		update(SQL_INSERT_BASESTUDENT, new Object[] { student.getId(),
				student.getSchid(), student.getClassid(),
				student.getStuname(), student.getOldName(),
				student.getStucode(), student.getUnitivecode(),
				student.getIdentitycardType(), student.getIdentitycard(), student.getSex(),
				student.getBirthday(), student.getMobilePhone(),
				student.getLeavesign(), student.getEnrollYear(),
				student.getClassorderid(), student.getEconomyState(),
				student.getCardNumber(), student.getIsSingleton(),
				student.getStayin(), student.getBoorish(),
				student.getIsLocalSource(),
				student.getSource(),
				student.getStudyMode(), student.getNativePlace(),
				student.getHomepage(), student.getEmail(),
				student.getLinkPhone(), student.getLinkAddress(),
				student.getPostalcode(), student.getRegionCode(),
				student.getCreationTime(), student.getModifyTime(),
				student.getIsdeleted(), student.getEventSourceValue(),
				student.getIsFreshman(), student.getPassword(),
				student.getNowState(), student.getSchoolDistrict(),
				student.getMeetexamCode(), student.getSourcePlace(),
				student.getIsSpecialid(), student.getDirId(),
				student.getFilePath(), student.getHealth(),
				student.getNation(), student.getBackground(),
				student.getPolityJoinDate(), student.getHomeAddress(),
				student.getToSchoolDate(), student.getDorm(),
				student.getDormTel(), student.getSpecId(),
				student.getSpecpointId(), student.getEnterScore(),
				student.getBankCardNo(),student.getOldSchoolName(),student.getOldSchoolDate()});
	}

	@Override
	public void updateStudent(EisBaseStudent student) {
		student.setModifyTime(new Date());
		if(StringUtils.isNotBlank(student.getIdentitycardType())){
			if (StringUtils.equals(student.getIdentitycardType(), "1")) {
				student.setIdentitycard(StringUtils.upperCase(student.getIdentitycard()));
			}
		}
		Object[] objs = new Object[] { 
				student.getSchid(),student.getClassid(), student.getStuname(),
				student.getOldName(), student.getStucode(),student.getUnitivecode(), 
				student.getIdentitycardType(), student.getIdentitycard(),student.getSex(), 
				student.getBirthday(),student.getMobilePhone(), student.getLeavesign(),
				student.getEnrollYear(), student.getClassorderid(),student.getEconomyState(), 
				student.getCardNumber(),student.getIsSingleton(), student.getStayin(),
				student.getBoorish(), student.getIsLocalSource(),student.getSource(),
				student.getStudyMode(), student.getNativePlace(),student.getHomepage(), 
				student.getEmail(),student.getLinkPhone(), student.getLinkAddress(),
				student.getPostalcode(), student.getRegionCode(),student.getModifyTime(), 
				student.getIsdeleted(),student.getEventSourceValue(), student.getIsFreshman(),
				student.getPassword(), student.getNowState(),student.getSchoolDistrict(), 
				student.getMeetexamCode(),student.getSourcePlace(), student.getIsSpecialid(),
				student.getDirId(), student.getFilePath(),student.getHealth(), 
				student.getNation(),student.getBackground(), student.getPolityJoinDate(),
				student.getHomeAddress(), student.getToSchoolDate(),student.getDorm(), 
				student.getDormTel(),student.getSpecId(), student.getSpecpointId(),
				student.getEnterScore(),student.getBankCardNo(), student.getOldSchoolName(),
				student.getOldSchoolDate(), student.getId() };
		
		update(SQL_UPDATE_BASESTUDENT, objs);
	}

	@Override
	public void deleteStudents(String[] ids) {
		updateForInSQL(SQL_DELETE_BASESTUDENT_BY_IDS, null, ids);
	}
	
	@Override
	public EisBaseStudent getStudentByStuId(String stuId) {
		return query(SQL_FIND_BASESTUDENT_BY_ID, stuId, new SingleRow());
	}

	@Override
	public List<EisBaseStudent> getStudentsByClassId(String clsId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int batchInsertStudent(final List<StudentImport> stuList){
		String SQL_INSERT_STUDENT = "INSERT INTO base_student"
				+ "(id, school_id,class_id,student_name,old_name,student_code,unitive_code,identitycard_type,identity_card,sex,"
				+ "birthday,is_local_source,mobile_phone,now_state,is_leave_school,is_freshman,"
				+ "enroll_year,class_inner_code,economy_state,card_number,meetexam_code,is_singleton,"
				+ "stayin,boorish,study_mode,native_place,homepage,email,link_phone,link_address,postalcode,"
				+ "source,source_place,region_code,creation_time,modify_time,is_deleted,state_code)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (StudentImport stu : stuList) {
		    Date now = new Date();
			listOfArgs.add(new Object[] {
					stu.getStuid(),
					stu.getSchid(),
					stu.getClassid(),
					stu.getStuname(),
					stu.getOldname(),
					stu.getStucode(),
					stu.getUnitivecode(),stu.getIdentitycardType(),
					stu.getIdentitycard(),
					NumberUtils.toInt(stu.getSex()),
					DateUtils.string2Date(stu.getBirthday()),

					NumberUtils.toInt(stu.getIslocalsource()),
					stu.getMobilephone(),
					stu.getFlowtype(), // now_state
					Integer.valueOf(stu.getLeavesign()), // is_leave_school
					9, // is_freshman
					stu.getEnrollyear(),
					stu.getClassorderid(), // class_inner_code
					stu.getEconomystate(),
					stu.getCardnumber(), // card_number
					null, // meetexam_code

					NumberUtils.toInt(stu.getSingleton()),
					NumberUtils.toInt(stu.getStayin()),
					NumberUtils.toInt(stu.getBoorish()),
					NumberUtils.toInt(stu.getBoarder()), stu.getNativeplace(),
					stu.getHomepage(), stu.getEmail(), stu.getLinkphone(),
					stu.getLinkaddress(), stu.getPostalcode(),

					stu.getSource(),// source
					stu.getSourceplace(), stu.getRegioncode(),
					now, // creation_time
					now, // modify_time
					0, // is_deleted
					stu.getStateCode()
					});//38
		}
		int[] argTypes = { Types.CHAR, Types.CHAR, Types.CHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.VARCHAR, 
				Types.INTEGER, Types.TIMESTAMP, Types.INTEGER, Types.VARCHAR,
				Types.CHAR, Types.INTEGER, Types.INTEGER, Types.CHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER,
				Types.CHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.CHAR, Types.CHAR, Types.CHAR,
				Types.CHAR, Types.TIMESTAMP, Types.TIMESTAMP, Types.INTEGER ,Types.VARCHAR};

		int[] rtn = batchUpdate(SQL_INSERT_STUDENT, listOfArgs, argTypes);
		int count = 0;
		for (int i : rtn) {
			if (i > 0)
				count += i;
		}
		return count;
	}

	@Override
	public List<EisBaseStudent>  getStudentBySchoolIdName(String schoolid,
			String studentName) {
		// TODO Auto-generated method stub
		   StringBuffer sql = new StringBuffer(" SELECT s.* FROM base_student s, base_class c  where c.id = s.class_id ");
	        List<Object> args = new ArrayList<Object>();
	        if(StringUtils.isNotEmpty(schoolid)){
	            sql.append("  and s.school_id = ?");
	            args.add(schoolid);
	        }

	
	        if(StringUtils.isNotEmpty(studentName)){
	            sql.append(" and s.student_name like  ? ");
	            args.add("%"+studentName+"%");
	        }
	        sql.append( " AND s.is_deleted = 0  AND s.is_leave_school=0   order by c.section , c.acadyear desc  , substr(c.class_name,1,length(c.class_name)-1) ");
	        return query(sql.toString() , args.toArray() , new MultiRow());

		
	}

}
