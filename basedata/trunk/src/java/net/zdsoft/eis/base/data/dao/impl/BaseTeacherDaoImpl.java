package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.dao.BaseTeacherDao;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;

import org.apache.commons.lang.StringUtils;

/**
 * @author yanb
 * 
 */
public class BaseTeacherDaoImpl extends BaseDao<BaseTeacher> implements
		BaseTeacherDao {
	private static final String SQL_INSERT_TEACHER = "INSERT INTO base_teacher(id,dept_id,"
			+ "unit_id,teacher_code,teacher_name,old_name,sex,birthday,native_place,"
			+ "nation,polity,polity_join,academic_qualification,graduate_time,graduate_school,major,"
			+ "work_date,incumbency_sign,title,identity_card,mobile_phone,office_tel,register_type,register_place,"
			+ "email,remark,card_number,homepage,link_phone,link_address,postalcode,region_code,"
			+ "creation_time,modify_time,is_deleted,display_order,charge_number,charge_number_type,event_source,dir_id," 
			+ "file_path,returned_chinese,country,weave_type, multi_title,teach_status,institute_id," +
			" work_teach_date, old_academic_qualification, spec_technical_duty, spec_technical_duty_date, home_address,general_card,other_job_level,job_level,job,hide_phone,weave_unit_id,in_preparation,mobile_cornet ," +
			"file_number ,duty_date ,duty_ex_unit ,duty_ex,duty_ex_date , duty_level ,other_duty_level)"+
			//			"title_code, title_date, title_dept, spec_job_level, manage_job_level, " +
//			"app_date, admin_post, app_year, admin_post_level, teach_subject, " +
//			"degree, first_stulive, highest_stulive, highest_stulive_sch, highest_stulive_major," +
//			"highest_stulive_date, teacher_level, certificate_name, approval_dept, certificate_level, " +
//			"approval_date, certificate_approval_dept, certificate_approval_date, contract_start_date, contract_end_date, " +
//			"first_contract_start, first_contract_end, second_contract_start, second_contract_end, third_contract_start, " +
//			"third_contract_end, fourth_contract_start, fourth_contract_end, QQ)"
			" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
			" ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ,? ,?)"; 
//			+ "?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)";

	private static final String SQL_DELETE_TEACHER_BY_IDS = "UPDATE base_teacher SET is_deleted = 1,event_source=?,modify_time=? WHERE id IN ";

	private static final String SQL_UPDATE_TEACHER = "UPDATE base_teacher SET dept_id=?,"
			+ "unit_id=?,teacher_code=?,teacher_name=?,old_name=?,sex=?,birthday=?,native_place=?,"
			+ "nation=?,polity=?,polity_join=?,academic_qualification=?,graduate_time=?,graduate_school=?,major=?,"
			+ "work_date=?,incumbency_sign=?,title=?,identity_card=?,mobile_phone=?,office_tel=?,register_type=?,"
			+ "register_place=?,email=?,remark=?,card_number=?,homepage=?,link_phone=?,link_address=?,"
			+ "postalcode=?,region_code=?,modify_time=?,is_deleted=?,display_order=?,"
			+ "charge_number=?,charge_number_type=?,event_source=?,returned_chinese=?,country=?,weave_type=?,multi_title=?,teach_status=?,institute_id=?," 
			+ " work_teach_date=?, old_academic_qualification=?, spec_technical_duty=?, spec_technical_duty_date=?, home_address=?, general_card=?" 
			+ ",other_job_level=?,job_level=?,job=?,hide_phone = ?,mobile_cornet=? , "
			+ "file_number = ? ,duty_date =?  ,duty_ex_unit =? ,duty_ex = ?,duty_ex_date=?  ,duty_level = ? ,other_duty_level  = ?"
//			"title_code = ?, title_date = ?, title_dept = ?, spec_job_level = ?, manage_job_level = ?, " +
//			"app_date = ?, admin_post = ?, app_year = ?, admin_post_level = ?, teach_subject = ?, " +
//			"degree = ?, first_stulive = ?, highest_stulive = ?, highest_stulive_sch = ?, highest_stulive_major = ?," +
//			"highest_stulive_date = ?, teacher_level = ?, certificate_name = ?, approval_dept = ?, certificate_level = ?, " +
//			"approval_date = ?, certificate_approval_dept = ?, certificate_approval_date = ?, contract_start_date = ?, contract_end_date = ?, " +
//			"first_contract_start = ?, first_contract_end = ?, second_contract_start = ?, second_contract_end = ?, third_contract_start = ?, " +
//			"third_contract_end = ?, fourth_contract_start = ?, fourth_contract_end = ?, QQ = ? " +
			+ "  WHERE id=?";

	private static final String SQL_UPDATE_PHOTO = "UPDATE base_teacher SET "
            + "dir_id=?,file_path=?,modify_time=?,event_source=? WHERE id=?";
	
	private static final String SQL_IS_ID_EXISTS_BY_IDENTITYCARD = "SELECT count(id) FROM base_teacher WHERE identity_card=? AND is_deleted = 0";
	private static final String SQL_FIND_TEACHER_BY_IDENTITYCARD = "SELECT count(id) FROM base_teacher WHERE id <>? AND identity_card=? AND is_deleted = 0";

	private static final String SQL_IS_ID_EXISTS_BY_CARDNUMBER = "SELECT count(id) FROM base_teacher WHERE card_number=? AND is_deleted = 0";
	private static final String SQL_FIND_TEACHER_BY_CARDNUMBER = "SELECT id FROM base_teacher WHERE id=? AND card_number=? AND is_deleted = 0";

	private static final String SQL_FIND_TEACHER_BY_UNIT_CODE = "SELECT * FROM base_teacher WHERE is_deleted = 0 AND unit_id = ? AND teacher_code=?";

	private static final String SQL_FIND_TEACHERCODE_BY_UNITID = "SELECT teacher_code FROM base_teacher WHERE is_deleted = 0 AND unit_id = ?";

	private static final String SQL_FIND_TEACHER_BY_TEA_NAME_CARD = "SELECT * FROM base_teacher "
			+ "WHERE unit_id=?";

	private static final String SQL_IS_EXISTS_CARDNUM = "select count(*) from base_student where card_number = ?";

	private static final String SQL_FIND_TEACHER_BY_ID = "SELECT * FROM base_teacher WHERE is_deleted = 0 AND id=?";

	private static final String SQL_FIND_TEACHER_BY_UNITID = "SELECT * FROM base_teacher WHERE unit_id=? AND is_deleted = 0 ORDER BY display_order,teacher_code";
	private static final String SQL_FIND_TEACHERS_BY_DEPT = "SELECT * FROM base_teacher WHERE dept_id =? AND is_deleted = 0 ORDER BY display_order,teacher_code";

	private static final String SQL_FIND_TEACHERS_BY_IDS = "SELECT * FROM base_teacher WHERE is_deleted = 0 AND id IN";

	private static final String SQL_FIND_TEACHER_BY_IDENTITYCARD_IDS = "SELECT * from base_teacher"
			+ " where is_deleted = 0 and identity_card in ";

	private static final String SQL_FIND_TEACHER_BY_CODES = "SELECT * from base_teacher where unit_id = ? and is_deleted = 0 and teacher_code in ";

	private static final String SQL_FIND_TEACHERS_BY_UNITIDS = "SELECT * FROM base_teacher WHERE is_deleted = 0 AND unit_id IN ";
	
	public BaseTeacher setField(ResultSet rs) throws SQLException {
		BaseTeacher teacher = new BaseTeacher();
		teacher.setId(rs.getString("id"));
		teacher.setDeptid(rs.getString("dept_id"));
		teacher.setUnitid(rs.getString("unit_id"));
		teacher.setTchId(rs.getString("teacher_code"));
		teacher.setName(rs.getString("teacher_name"));
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
		
		
		//14-12-30 add by like 镇海
		teacher.setWorkTeachDate(rs.getTimestamp("work_teach_date"));
	    teacher.setOldAcademicQualification(rs.getString("old_academic_qualification"));
	    teacher.setSpecTechnicalDuty(rs.getString("spec_technical_duty"));
	    teacher.setSpecTechnicalDutyDate(rs.getTimestamp("spec_technical_duty_date"));
	    teacher.setHomeAddress(rs.getString("home_address"));
		teacher.setGeneralCard(rs.getString("general_card"));
		//2013-1-14
//		/*teacher.setTitleCode(rs.getString("title_code"));
//		teacher.setTitleDate(rs.getDate("title_date"));
//		teacher.setTitleDept(rs.getString("title_dept"));
//		teacher.setSpecJobLevel(rs.getString("spec_job_level"));
//		teacher.setManageJobLevel(rs.getString("manage_job_level"));
//		teacher.setAppDate(rs.getDate("app_date"));
//		teacher.setAdminPost(rs.getString("admin_post"));
//		if (StringUtils.isNotBlank(rs.getString("app_year"))){
//			teacher.setAppYear(rs.getInt("app_year"));
//		}
//		teacher.setAdminPostLevel(rs.getString("admin_post_level"));
//		teacher.setTeachSubject(rs.getString("teach_subject"));
//		teacher.setDegree(rs.getString("degree"));
//		teacher.setFirstStulive(rs.getString("first_stulive"));
//		teacher.setHighestStulive(rs.getString("highest_stulive"));
//		teacher.setHighestStuliveSch(rs.getString("highest_stulive_sch"));
//		teacher.setHighestStuliveMajor(rs.getString("highest_stulive_major"));
//		teacher.setHighestStuliveDate(rs.getDate("highest_stulive_date"));
//		teacher.setTeacherLevel(rs.getString("teacher_level"));
//		teacher.setCertificateName(rs.getString("certificate_name"));
//		teacher.setApprovalDept(rs.getString("approval_dept"));
//		teacher.setCertificateLevel(rs.getString("certificate_level"));
//		teacher.setApprovalDate(rs.getDate("approval_date"));
//		teacher.setCertificateApprovalDept(rs.getString("certificate_approval_dept"));
//		teacher.setCertificateApprovalDate(rs.getDate("certificate_approval_date"));
//		teacher.setContractStartDate(rs.getDate("contract_start_date"));
//		teacher.setContractEndDate(rs.getDate("contract_end_date"));
//		teacher.setFirstContractStart(rs.getDate("first_contract_start"));
//		teacher.setFirstContractEnd(rs.getDate("first_contract_end"));
//		teacher.setSecondContractStart(rs.getDate("second_contract_start"));
//		teacher.setSecondContractEnd(rs.getDate("second_contract_end"));
//		teacher.setThirdContractStart(rs.getDate("Third_contract_start"));
//		teacher.setThirdContractEnd(rs.getDate("Third_contract_end"));
//		teacher.setFourthContractStart(rs.getDate("Fourth_contract_start"));
//		teacher.setFourthContractEnd(rs.getDate("Fourth_contract_end"));
//		teacher.setQQ(rs.getString("QQ"));*/
		///教职工 工作岗位
		teacher.setJob(rs.getString("job"));
		teacher.setJobLevel(rs.getString("job_level"));
		teacher.setOtherJobLevel(rs.getString("other_job_level"));
		teacher.setHidePhone(rs.getInt("hide_phone"));
		
		//手机短号
		teacher.setMobileCornet(rs.getString("mobile_cornet"));
		teacher.setFileNumber(rs.getString("file_number"));
		teacher.setDutyDate(rs.getTimestamp("duty_date"));
		teacher.setDutyEx(rs.getString("duty_ex"));
		teacher.setDutyExUnit(rs.getString("duty_ex_unit"));
		teacher.setDutyExDate(rs.getTimestamp("duty_ex_date"));
		teacher.setDutyLevel(rs.getString("duty_level"));
		teacher.setOtherDutyLevel(rs.getString("other_duty_level"));
		return teacher;
	}

	public void insertTeacher(BaseTeacher t) {
		if (StringUtils.isBlank(t.getId()))
			t.setId(getGUID());
		t.setCreationTime(new Date());
		t.setModifyTime(new Date());
		t.setIsdeleted(false);
		Object[] objs = new Object[] { t.getId(), t.getDeptid(), t.getUnitid(),
				t.getTchId(), t.getName(), t.getPname(), StringUtils.isBlank(t.getSex()) ? 1 : Integer.valueOf(t.getSex()),
				t.getBirthday(), t.getPerNative(), t.getNation(),
				t.getPolity(), t.getPolityJoin(), t.getStulive(),
				t.getFstime(), t.getFsschool(), t.getMajor(), t.getWorkdate(), 
				t.getEusing(), t.getTitle(), t.getIdcard(), t.getPersonTel(),
				t.getOfficeTel(), t.getRegistertype(), t.getRegister(),
				t.getEmail(), t.getRemark(), t.getCardNumber(),
				t.getHomepage(), t.getLinkPhone(), t.getLinkAddress(),
				t.getPostalcode(), t.getRegionCode(), t.getCreationTime(),
				t.getModifyTime(), t.getIsdeleted()?1:0, t.getDisplayOrder(),
				t.getChargeNumber(), t.getChargeNumberType(),
				t.getEventSourceValue(),t.getDirId(),t.getFilePath(),
				t.getReturnedChinese(),t.getCountry(),
				t.getWeaveType(),t.getMultiTitle(), t.getTeachStatus(),t.getInstituteId(),
				//weaveUnitId inPreparation
	    		t.getWorkTeachDate(), t.getOldAcademicQualification(), t.getSpecTechnicalDuty(),
	    		t.getSpecTechnicalDutyDate(), t.getHomeAddress(), t.getGeneralCard(),
	    		t.getOtherJobLevel(), t.getJobLevel(), t.getJob(),t.getHidePhone(),
	    		t.getUnitid(),"1",t.getMobileCornet(),
	    		t.getFileNumber(),t.getDutyDate(),t.getDutyExUnit(),t.getDutyEx(),t.getDutyExDate(),t.getDutyLevel(),t.getOtherDutyLevel()
//				t.getTitleCode(), t.getTitleDate(), t.getTitleDept(), t.getSpecJobLevel(), t.getManageJobLevel(),
//				t.getAppDate(), t.getAdminPost(), t.getAppYear(), t.getAdminPostLevel(), t.getTeachSubject(),
//				t.getDegree(), t.getFirstStulive(), t.getHighestStulive(), t.getHighestStuliveSch(), t.getHighestStuliveMajor(),
//				t.getHighestStuliveDate(), t.getTeacherLevel(), t.getCertificateName(), t.getApprovalDept(), t.getCertificateLevel(),
//				t.getApprovalDate(), t.getCertificateApprovalDept(), t.getCertificateApprovalDate(), t.getContractStartDate(), t.getContractEndDate(),
//				t.getFirstContractStart(), t.getFirstContractEnd(), t.getSecondContractStart(), t.getSecondContractEnd(), t.getThirdContractStart(),
//				t.getThirdContractEnd(), t.getFourthContractStart(), t.getFourthContractEnd(), t.getQQ()
				};

		update(SQL_INSERT_TEACHER, objs);
	}

	public void insertTeachers(List<BaseTeacher> teacheList) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (int i = 0; i < teacheList.size(); i++) {
			BaseTeacher t = teacheList.get(i);
			if (StringUtils.isBlank(t.getId()))
				t.setId(getGUID());
			t.setCreationTime(new Date());
			t.setModifyTime(new Date());
			t.setIsdeleted(false);
			Object[] objs = new Object[] { t.getId(), t.getDeptid(),
					t.getUnitid(), t.getTchId(), t.getName(), t.getPname(),
					t.getSex() == null ? 1 : Integer.valueOf(t.getSex()),
					t.getBirthday(), t.getPerNative(), t.getNation(),
					t.getPolity(), t.getPolityJoin(), t.getStulive(),
					t.getFstime(), t.getFsschool(), t.getMajor(),
					t.getWorkdate(), t.getEusing(), t.getTitle(),
					t.getIdcard(), t.getPersonTel(), t.getOfficeTel(),
					t.getRegistertype(), t.getRegister(), t.getEmail(),
					t.getRemark(), t.getCardNumber(), t.getHomepage(),
					t.getLinkPhone(), t.getLinkAddress(), t.getPostalcode(),
					t.getRegionCode(), t.getCreationTime(), t.getModifyTime(),
					t.getIsdeleted() ? 1 : 0, t.getDisplayOrder(),
					t.getChargeNumber(), t.getChargeNumberType(),
					t.getEventSourceValue(),t.getDirId(),t.getFilePath(),
					t.getReturnedChinese(),t.getCountry(),
					t.getWeaveType(), t.getMultiTitle(), t.getTeachStatus(),
					t.getWorkTeachDate(), t.getOldAcademicQualification(), t.getSpecTechnicalDuty(),
		    		t.getSpecTechnicalDutyDate(), t.getHomeAddress(), t.getGeneralCard(),
		    		t.getOtherJobLevel(), t.getJobLevel(), t.getJob(),t.getHidePhone(), t.getMobileCornet()
//					,
//					t.getTitleCode(), t.getTitleDate(), t.getTitleDept(), t.getSpecJobLevel(), t.getManageJobLevel(),
//					t.getAppDate(), t.getAdminPost(), t.getAppYear(), t.getAdminPostLevel(), t.getTeachSubject(),
//					t.getDegree(), t.getFirstStulive(), t.getHighestStulive(), t.getHighestStuliveSch(), t.getHighestStuliveMajor(),
//					t.getHighestStuliveDate(), t.getTeacherLevel(), t.getCertificateName(), t.getApprovalDept(), t.getCertificateLevel(),
//					t.getApprovalDate(), t.getCertificateApprovalDept(), t.getCertificateApprovalDate(), t.getContractStartDate(), t.getContractEndDate(),
//					t.getFirstContractStart(), t.getFirstContractEnd(), t.getSecondContractStart(), t.getSecondContractEnd(), t.getThirdContractStart(),
//					t.getThirdContractEnd(), t.getFourthContractStart(), t.getFourthContractEnd(), t.getQQ()
					};
			listOfArgs.add(objs);
		}
		int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.CHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
				Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR, Types.CHAR,
				Types.TIMESTAMP, Types.CHAR, Types.TIMESTAMP, Types.VARCHAR,
				Types.CHAR, Types.TIMESTAMP, Types.CHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.CHAR,
				Types.CHAR, Types.TIMESTAMP, Types.TIMESTAMP, Types.INTEGER,
				Types.INTEGER, Types.INTEGER, Types.INTEGER,
				Types.CHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.CHAR,
				Types.TIMESTAMP, Types.CHAR,Types.CHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,
				Types.CHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR
//				,
//				Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//				Types.DATE, Types.VARCHAR, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR,
//				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//				Types.DATE, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//				Types.DATE, Types.VARCHAR, Types.DATE, Types.DATE, Types.DATE,
//				Types.DATE, Types.DATE, Types.DATE,Types.DATE, Types.DATE,
//				Types.DATE, Types.DATE, Types.DATE, Types.VARCHAR
				};
		batchUpdate(SQL_INSERT_TEACHER, listOfArgs, argTypes);
	}

	public void updateTeacher(BaseTeacher t) {
		t.setModifyTime(new Date());
		t.setIsdeleted(false);
		Object[] objs = new Object[] { t.getDeptid(), t.getUnitid(),
				t.getTchId(), t.getName(), t.getPname(),
				t.getSex() == null ? 1 : Integer.valueOf(t.getSex()),
				t.getBirthday(), t.getPerNative(), t.getNation(),
				t.getPolity(), t.getPolityJoin(), t.getStulive(),
				t.getFstime(), t.getFsschool(), t.getMajor(), t.getWorkdate(),
				t.getEusing(), t.getTitle(), t.getIdcard(), t.getPersonTel(),
				t.getOfficeTel(), t.getRegistertype(), t.getRegister(),
				t.getEmail(), t.getRemark(), t.getCardNumber(),
				t.getHomepage(), t.getLinkPhone(), t.getLinkAddress(),
				t.getPostalcode(), t.getRegionCode(), t.getModifyTime(),
				t.getIsdeleted() ? 1 : 0, t.getDisplayOrder(),
				t.getChargeNumber(), t.getChargeNumberType(),
				t.getEventSourceValue(), 
				t.getReturnedChinese(), t.getCountry(), t.getWeaveType(), 
				t.getMultiTitle(), t.getTeachStatus(),t.getInstituteId(),
				t.getWorkTeachDate(), t.getOldAcademicQualification(), t.getSpecTechnicalDuty(),
	    		t.getSpecTechnicalDutyDate(), t.getHomeAddress(), t.getGeneralCard(),
	    		t.getOtherJobLevel(), t.getJobLevel(), t.getJob(),t.getHidePhone(),t.getMobileCornet(),
//				, t.getTitleCode(), t.getTitleDate(), t.getTitleDept(), t.getSpecJobLevel(), t.getManageJobLevel(),
//				t.getAppDate(), t.getAdminPost(), t.getAppYear(), t.getAdminPostLevel(), t.getTeachSubject(),
//				t.getDegree(), t.getFirstStulive(), t.getHighestStulive(), t.getHighestStuliveSch(), t.getHighestStuliveMajor(),
//				t.getHighestStuliveDate(), t.getTeacherLevel(), t.getCertificateName(), t.getApprovalDept(), t.getCertificateLevel(),
//				t.getApprovalDate(), t.getCertificateApprovalDept(), t.getCertificateApprovalDate(), t.getContractStartDate(), t.getContractEndDate(),
//				t.getFirstContractStart(), t.getFirstContractEnd(), t.getSecondContractStart(), t.getSecondContractEnd(), t.getThirdContractStart(),
//				t.getThirdContractEnd(), t.getFourthContractStart(), t.getFourthContractEnd(), t.getQQ()
	    		t.getFileNumber(),t.getDutyDate(),t.getDutyExUnit(),t.getDutyEx(),t.getDutyExDate(),t.getDutyLevel(),t.getOtherDutyLevel()
				,t.getId() };

		update(SQL_UPDATE_TEACHER, objs);

	}

	public void updatePhoto(BaseTeacher t) {
        t.setModifyTime(new Date());
        update(SQL_UPDATE_PHOTO, new Object[] { t.getDirId(), t.getFilePath(), t.getModifyTime(),
                t.getEventSourceValue(), t.getId() });
    }
	
	public void updateTeachers(List<BaseTeacher> teacheList) {
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (int i = 0; i < teacheList.size(); i++) {
			BaseTeacher t = teacheList.get(i);
			t.setModifyTime(new Date());
			t.setIsdeleted(false);
			Object[] objs = new Object[] { t.getDeptid(), t.getUnitid(),
					t.getTchId(), t.getName(), t.getPname(),
					t.getSex() == null ? 1 : Integer.valueOf(t.getSex()),
					t.getBirthday(), t.getPerNative(), t.getNation(),
					t.getPolity(), t.getPolityJoin(), t.getStulive(),
					t.getFstime(), t.getFsschool(), t.getMajor(),
					t.getWorkdate(), t.getEusing(), t.getTitle(),
					t.getIdcard(), t.getPersonTel(), t.getOfficeTel(),
					t.getRegistertype(), t.getRegister(), t.getEmail(),
					t.getRemark(), t.getCardNumber(), t.getHomepage(),
					t.getLinkPhone(), t.getLinkAddress(), t.getPostalcode(),
					t.getRegionCode(), t.getModifyTime(),
					t.getIsdeleted() ? 1 : 0, t.getDisplayOrder(),
					t.getChargeNumber(), t.getChargeNumberType(),
					t.getEventSourceValue(),t.getReturnedChinese(),
					t.getCountry(),t.getWeaveType(), t.getMultiTitle(), t.getTeachStatus(),t.getInstituteId(),
					t.getWorkTeachDate(), t.getOldAcademicQualification(), t.getSpecTechnicalDuty(),
		    		t.getSpecTechnicalDutyDate(), t.getHomeAddress(), t.getGeneralCard()
		    		
		    		,t.getOtherJobLevel(), t.getJobLevel(), t.getJob(),t.getHidePhone(),t.getMobileCornet()
//					, t.getTitleCode(), t.getTitleDate(), t.getTitleDept(), t.getSpecJobLevel(), t.getManageJobLevel(),
//					t.getAppDate(), t.getAdminPost(), t.getAppYear(), t.getAdminPostLevel(), t.getTeachSubject(),
//					t.getDegree(), t.getFirstStulive(), t.getHighestStulive(), t.getHighestStuliveSch(), t.getHighestStuliveMajor(),
//					t.getHighestStuliveDate(), t.getTeacherLevel(), t.getCertificateName(), t.getApprovalDept(), t.getCertificateLevel(),
//					t.getApprovalDate(), t.getCertificateApprovalDept(), t.getCertificateApprovalDate(), t.getContractStartDate(), t.getContractEndDate(),
//					t.getFirstContractStart(), t.getFirstContractEnd(), t.getSecondContractStart(), t.getSecondContractEnd(), t.getThirdContractStart(),
//					t.getThirdContractEnd(), t.getFourthContractStart(), t.getFourthContractEnd(), t.getQQ()
//					
					,t.getId() };
			listOfArgs.add(objs);
		}
		int[] argTypes = new int[] { Types.CHAR, Types.CHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP,
				Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.TIMESTAMP,
				Types.CHAR, Types.TIMESTAMP, Types.VARCHAR, Types.CHAR,
				Types.TIMESTAMP, Types.CHAR, Types.CHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.CHAR, Types.CHAR,
				Types.TIMESTAMP,Types.INTEGER, Types.INTEGER,
				Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.CHAR,Types.CHAR, 
				Types.TIMESTAMP, Types.CHAR,Types.CHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR
//				, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//				Types.DATE, Types.VARCHAR, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR,
//				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//				Types.DATE, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//				Types.DATE, Types.VARCHAR, Types.DATE, Types.DATE, Types.DATE,
//				Types.DATE, Types.DATE, Types.DATE,Types.DATE, Types.DATE,
//				Types.DATE, Types.DATE, Types.DATE, Types.VARCHAR
				,Types.CHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR
				, Types.CHAR};
		batchUpdate(SQL_UPDATE_TEACHER, listOfArgs, argTypes);
	}

	// 教职工列表排序
	public void updateAsOrderByTeacher(String[] teacherids, String[] orderids) {
		String sqll = "UPDATE base_teacher SET display_order = ?,event_source=? WHERE id = ?";
		String sql2 = "UPDATE base_user SET display_order =?,event_source=? WHERE owner_id = ?";
		List<Object[]> listOfArgs = new ArrayList<Object[]>();
		for (int i = 0; i < teacherids.length; i++) {
			listOfArgs.add(new Object[] { orderids[i],
					EventSourceType.LOCAL.getValue(), teacherids[i] });
		}
		int[] argTypes = new int[] { Types.CHAR, Types.INTEGER, Types.CHAR };
		batchUpdate(sqll, listOfArgs, argTypes);
		batchUpdate(sql2, listOfArgs, argTypes);
	}

	public void deleteTeacher(String[] teacherIds, EventSourceType eventSource) {
		updateForInSQL(SQL_DELETE_TEACHER_BY_IDS, new Object[] {
				eventSource.getValue(), new Date() }, teacherIds);
	}

	public boolean isExistsIdCard(String id, String idCard) {
		int count;
		if (StringUtils.isEmpty(id)) {
			count = queryForInt(SQL_IS_ID_EXISTS_BY_IDENTITYCARD,
					new String[] { idCard });
		} else {
			count = queryForInt(SQL_FIND_TEACHER_BY_IDENTITYCARD,
					new String[] { id, idCard });
		}
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isExistsCardNumber(String id, String cardNumber) {
		int count;
		if (StringUtils.isEmpty(id)) {
			count = queryForInt(SQL_IS_ID_EXISTS_BY_CARDNUMBER,
					new String[] { cardNumber });
		} else {
			List<String> ids = query(SQL_FIND_TEACHER_BY_CARDNUMBER,
					new String[] { id, cardNumber },
					new MultiRowMapper<String>() {
						public String mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getString(1);
						}
					});
			if (ids.contains(id)) {
				count = ids.size() - 1;
			} else {
				count = ids.size();
			}
		}
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isExistsCardNumberInStu(String cardNumber) throws Exception {
		int count = queryForInt(SQL_IS_EXISTS_CARDNUM,
				new String[] { cardNumber });
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isExistsTeacherCode(String unitId, String code) {
		BaseTeacher t = query(SQL_FIND_TEACHER_BY_UNIT_CODE, new String[] {
				unitId, code }, new SingleRow());
		if (t == null) {
			return false;
		} else {
			return true;
		}
	}

	public List<String> getTeacherCodes(String unitId) {
		List<String> tchCodes = query(SQL_FIND_TEACHERCODE_BY_UNITID,
				new String[] { unitId }, new MultiRowMapper<String>() {
					public String mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString(1);
					}
				});
		return tchCodes;
	}

	public List<BaseTeacher> getTeachersFaintness(String unitId, String code,
			String name, String cardNumber, String deptId) {
		Object[] objs = null;
		String sql = SQL_FIND_TEACHER_BY_TEA_NAME_CARD;
		if (StringUtils.isNotBlank(code)) {
			sql += " AND teacher_code='" + code + "'";
		}
		if (StringUtils.isNotBlank(name)) {
			sql += " AND teacher_name like '" + name + "%' escape '\\' ";
		}
		if (StringUtils.isNotBlank(cardNumber)) {
			sql += " AND card_number='" + cardNumber + "'";
		}
		if(StringUtils.isNotBlank(deptId)){
			sql += " AND dept_id='" + deptId + "'";
		}
		
		sql += " AND is_deleted = 0";
		objs = new Object[] { unitId };
		return query(sql, objs, new MultiRow());
	}

	public BaseTeacher getBaseTeacher(String teacherId) {
		return query(SQL_FIND_TEACHER_BY_ID, new String[] { teacherId },
				new SingleRow());
	}

	public List<BaseTeacher> getBaseTeachers(String unitId) {
		return query(SQL_FIND_TEACHER_BY_UNITID, unitId, new MultiRow());
	}

	public List<BaseTeacher> getBaseTeachersByDeptId(String deptId) {
		return query(SQL_FIND_TEACHERS_BY_DEPT, new String[] { deptId },
				new MultiRow());
	}

	public List<BaseTeacher> getBaseTeachers(String[] teacherIds) {
		return queryForInSQL(SQL_FIND_TEACHERS_BY_IDS, null, teacherIds,
				new MultiRow());
	}

	public List<BaseTeacher> getTeacherMapByIdentityCards(String[] identitycards) {
		return queryForInSQL(SQL_FIND_TEACHER_BY_IDENTITYCARD_IDS,
				new Object[] {}, identitycards, new MultiRow());
	}

	public Map<String, BaseTeacher> getTeacherMap(String unitId, String[] codes) {
		return queryForInSQL(SQL_FIND_TEACHER_BY_CODES,
				new Object[] { unitId }, codes,
				new MapRowMapper<String, BaseTeacher>() {
					@Override
					public String mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("unit_id") + "_"
								+ rs.getString("teacher_code");
					}

					@Override
					public BaseTeacher mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return setField(rs);
					}
				});
	}
	
	/**
	 * 获取教职工信息
	 * @param unitId
	 * @param code
	 * @return
	 */
	public BaseTeacher getTeacherByCodeAndUnit(String unitId, String code){
		String sql = "select * from base_teacher where unit_id = ? and teacher_code = ?";
		return query(sql, new Object[]{unitId, code}, new SingleRow());
	}
	
	public List<BaseTeacher> getTeachers(String[] unitIds){
		return queryForInSQL(SQL_FIND_TEACHERS_BY_UNITIDS, null, unitIds,
				new MultiRow());
	}
}
