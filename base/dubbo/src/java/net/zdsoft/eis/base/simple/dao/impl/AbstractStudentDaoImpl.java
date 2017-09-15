/* 
 * @(#)AbstractStudentDaoImpl.java    Created on May 16, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.simple.dao.AbstractStudentDao;
import net.zdsoft.eis.base.simple.entity.SimpleStudent;
import net.zdsoft.eis.frame.client.BaseSimpleDao;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.QueryConditionUtils;
import net.zdsoft.leadin.util.QueryConditionUtils.QueryCondition;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 16, 2011 11:40:24 AM $
 */
public abstract class AbstractStudentDaoImpl<E extends SimpleStudent> extends BaseSimpleDao<E>
        implements AbstractStudentDao<E> {
    private static final String FIND_STU_PREFIX = "SELECT * FROM base_student ";
    
    // ========================= 按id或名称查询=============================
    private final String SQL_FIND_STUDENT_BY_ID = getFindPrefix() + " WHERE id=? ";
    private final String SQL_FIND_STUDENT_BY_IDS = getFindPrefix() + " WHERE is_deleted = 0 AND id in ";
    private final String SQL_FIND_STUDENT_BY_IDS_WITHDELETED = getFindPrefix() + " WHERE id in ";
    
    // ========================= 按号码查询================================
    // 学号
    private final String SQL_FIND_STUS_BY_SCHID_STUCODES = getFindPrefix()
            + "WHERE school_id=? AND is_deleted = 0 AND student_code IN ";
    
    private final String SQL_FIND_STUS_BY_SCHID_NAME_STUCODE = getFindPrefix()
            + "WHERE school_id=? AND is_deleted = 0 AND class_id != '00000000000000000000000000000000' AND student_name like ? AND student_code like ? ";
    
    private final String SQL_FIND_STUS_BY_SCHID_NAME_NO_CLASS_ID = getFindPrefix()
            + "WHERE school_id=? AND is_deleted = 0 AND is_leave_school=0 AND class_id != '00000000000000000000000000000000' AND student_name like ?  order by class_id";
    
    private final String SQL_FIND_STUS_BY_SCHID_NAME_CLASS_ID = getFindPrefix()
            + "WHERE school_id=? AND is_deleted = 0 AND is_leave_school=0 AND class_id =? AND student_name like ? ";
    
    private final String SQL_FIND_STUS_BY_SCHID_NAME_CLSIDS = getFindPrefix()
            + "WHERE school_id=? AND is_deleted = 0 AND is_leave_school=0 AND student_name like ? AND class_id in"; 
    
    // 学号或学籍号
    private final String SQL_FIND_STU_BY_UNITIVECODE_OR_STUCODE = getFindPrefix() 
            + "WHERE school_id = ? AND (unitive_code = ? or student_code=? ) AND is_deleted = 0";
    
    // 学籍号
    private final String SQL_FIND_STU_BY_SCHID_AND_UNITIVECODE = getFindPrefix()
            + "WHERE school_id = ? AND unitive_code = ? AND is_deleted = 0";
    private final String SQL_FIND_STU_BY_UNITIVECOD = getFindPrefix()
            + "WHERE unitive_code=? AND is_deleted = 0";
    private final String SQL_FIND_STU_PWD_BY_UNITIVECOD = getFindPrefix()// password
            + "WHERE unitive_code=? AND is_deleted = 0";
    private final String SQL_FIND_STU_IN_UNITIVECODE = getFindPrefix()
            + "WHERE is_deleted = 0 AND unitive_code IN";
    
    private static final String SQL_FIND_STU_BY_SCHID_STUNAME_UNITIVECODE = "SELECT id FROM base_student "
            + " WHERE school_id=? AND student_name like ? AND unitive_code like ? AND is_leave_school<>9 AND is_deleted = 0 ";
    
    // 姓名
    private final String SQL_FIND_STU_BY_SCHID_NAME = getFindPrefix()
            + "WHERE school_id=? AND student_name like ? AND is_deleted=0 ";
    
    // 身份证号
    private final String SQL_FIND_STUS_BY_IDENTITYCARD = getFindPrefix()
            + "WHERE is_deleted = 0 AND identity_card IN ";
    
    // ===========================按班级查询================================
    private final String SQL_FIND_STUDENT_BY_CALSSID = getFindPrefix()
            + "WHERE class_id = ? AND is_deleted = 0 ORDER BY student_code";
    private final String SQL_FIND_STUDENT_BY_CLASSID_LEAVESIGN = getFindPrefix()
            + "WHERE class_id = ? AND is_leave_school=0 AND is_deleted = 0 ORDER BY to_number(translate(student_code,'0123456789' || student_code,'0123456789'))";
    private final String SQL_FIND_STU_BY_STURECRUITMENT = getFindPrefix()
            + "WHERE class_id = ? AND is_leave_school = 0 AND source <> '2' AND is_deleted = 0 ORDER BY class_inner_code";
    private final String SQL_FIND_STU_BY_GRADUATE = getFindPrefix()
            + "WHERE class_id = ? AND (now_state = '41' OR now_state = '42') AND is_deleted = 0";
    private final String SQL_FIND_STUDENT_BY_CLASSID_ORDER_NAME = getFindPrefix()
    		+ "WHERE class_id = ? AND is_leave_school=0 AND is_deleted = 0 ORDER BY f_trans_pinyin_capital(student_name)";
    private final String SQL_FILTER_STUDENT = "SELECT id FROM base_student WHERE class_id = ? and id in";
    
    // in classId
    private final String SQL_FIND_STUS_BY_CLSIDS = getFindPrefix()
            + "WHERE is_deleted = 0 AND class_id IN ";
    private final String SQL_FIND_STUS_BY_CLASSIDS = getFindPrefix()
            + "WHERE is_leave_school=0 AND is_deleted = 0 AND class_id IN";
    
    // ===========================按学校查询================================
    private final String SQL_FIND_INSCHOOL_STUS_BY_SCHID = getFindPrefix()
            + "WHERE school_id=? AND is_leave_school<>9 AND now_state not IN ('41','42') AND is_deleted = 0 "
            + "ORDER BY class_id,unitive_code ";
    private final String SQL_FIND_BY_CLSID = getFindPrefix()
    		+" where is_deleted='0' and class_id=? and (is_leave_school=0 or (now_state in('41','42') and is_leave_school<>'9'))";
    
    // ===========================前缀=====================================
    
    // ===========================数值查询==================================
    // 学区人数
    private static final String SQL_IS_EXISTS_STU_INSCHDISTRICT = "SELECT COUNT(*) FROM base_student "
            + "WHERE school_district=? AND is_deleted = 0";
    
    // 班级人数
    private static final String SQL_FIND_STUNUM_BY_CLSID = "SELECT count(*) FROM base_student "
            + "WHERE class_id =? AND is_leave_school=0 AND is_deleted = 0";
    private static final String SQL_FIND_STUNUM_GROUP_BY_CLSID = "SELECT class_id, count(*) as stucount FROM base_student "
            + "WHERE is_leave_school = 0 AND is_deleted = 0 AND class_id IN ";
    private static final String SQL_FIND_STUSNUM_IN_CLS = "SELECT COUNT(*) FROM base_student "
            + "WHERE is_leave_school = 0 AND is_deleted = 0 AND class_id IN";
    
    private static final String SQL_FIND_STUINFO_BY_UNIONID_PREFIX = "SELECT si.* FROM base_student si,base_unit b "
            + "WHERE si.is_leave_school<>9 AND si.is_deleted = 0 AND b.is_deleted=0 AND si.class_id != '00000000000000000000000000000000' AND b.union_code like (? || '%') AND si.school_id=b.id ";
    
    private static final String SQL_FIND_STUINFO_ex_BY_CLASSID="SELECT  * FROM base_student_ex where id in ";
    @Override
    public String getFindPrefix() {
        return FIND_STU_PREFIX;
    }
    
    @Override
    public void setEntity(ResultSet rs, E student) throws SQLException {
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
    }
    
    public E getStudent(String studentId) {
        return query(SQL_FIND_STUDENT_BY_ID, new Object[] { studentId }, new SingleRow());
    }
    
    public E getStudentBy2Code(String schoolId, String code) {
        return query(SQL_FIND_STU_BY_UNITIVECODE_OR_STUCODE, new Object[] { schoolId, code, code },
                new SingleRow());
    }
    
    public E getStudentByUnitivecode(String unitivecode) {
        return query(SQL_FIND_STU_BY_UNITIVECOD, unitivecode, new SingleRow());
    }
    
    public E getStudentPwdByUnitivecode(String unitivecode) {
        return query(SQL_FIND_STU_PWD_BY_UNITIVECOD, unitivecode, new SingleRowMapper<E>() {
            public E mapRow(ResultSet rs) throws SQLException {
                E student = setField(rs);
                student.setPassword(rs.getString("password"));
                return student;
            }
        });
    }
    
    public E getStudentByUnitivecode(String schoolId, String unitiveCode) {
        return query(SQL_FIND_STU_BY_SCHID_AND_UNITIVECODE, new Object[] { schoolId, unitiveCode },
                new SingleRow());
    }
    
    public List<E> getStudentsByIds(String studentIds[]) {
        return queryForInSQL(SQL_FIND_STUDENT_BY_IDS, null, studentIds, new MultiRow(),
                " ORDER BY school_id, class_id, unitive_code");
    }
    
    public List<E> getStudentsByIdsWithDeleted(String[] studentIds){
        return queryForInSQL(SQL_FIND_STUDENT_BY_IDS_WITHDELETED, null, studentIds, new MultiRow(),
        		" ORDER BY school_id, class_id, unitive_code");
    }
    
    public List<E> getStudents(String schoolId, String[] studentCodes) {
        return queryForInSQL(SQL_FIND_STUS_BY_SCHID_STUCODES, new Object[] { schoolId },
                studentCodes, new MultiRow());
    }
    
    public List<E> getStudentsByIdentityCard(String[] identityCard) {
        return queryForInSQL(SQL_FIND_STUS_BY_IDENTITYCARD, null, identityCard, new MultiRow());
    }
    
    public List<E> getStudentsByUnitiveCodes(String[] unitiveCodes) {
        return queryForInSQL(SQL_FIND_STU_IN_UNITIVECODE, null, unitiveCodes, new MultiRow());
    
    }
    
    public List<E> getStudents(String classId) {
        return query(SQL_FIND_STUDENT_BY_CLASSID_LEAVESIGN, new Object[] { classId },
                new MultiRow());
    }

    public List<E> getStudentsForAbnormalflow(String classId) {
        return query(SQL_FIND_STU_BY_STURECRUITMENT, new Object[] { classId }, new MultiRow());
    }
    
    public List<E> getAllStudents(String classId) {
        String sql = SQL_FIND_STUDENT_BY_CALSSID;
        return query(sql, new Object[] { classId }, new MultiRow());
    }
    
    public List<E> getStudents(String[] classids) {
        return queryForInSQL(SQL_FIND_STUS_BY_CLASSIDS, null, classids, new MultiRow(),
                " ORDER BY class_id,unitive_code");
    }
    
    public List<E> getStudentsForGraduated(String classId) {
        return query(SQL_FIND_STU_BY_GRADUATE, classId, new MultiRow());
    }
    
    public List<E> getAllStudents(String[] classIds) {
        return queryForInSQL(SQL_FIND_STUS_BY_CLSIDS, null, classIds, new MultiRow(),
                " ORDER BY class_id,unitive_code");
    }
    
    public List<E> getStudentsByFaintness(String schoolId, String studentName) {
        return query(SQL_FIND_STU_BY_SCHID_NAME, new Object[] { schoolId, studentName + "%" },
                new MultiRow());
    }
    
    public List<String> getStudentsByFaintness(String schoolId, String studentName,
            String unitiveCode) {
        return query(SQL_FIND_STU_BY_SCHID_STUNAME_UNITIVECODE, new Object[] { schoolId,
                studentName + "%", unitiveCode + "%" }, new MultiRowMapper<String>() {
    
            public String mapRow(ResultSet rs, int arg1) throws SQLException {
                return rs.getString("id");
            }
        });
    }
    
    public String getIdentityTypeCard(String stuId,String type){
    	String sql = "SELECT identity_card FROM base_student WHERE is_deleted = 0 AND is_leave_school = 0 AND identitycard_type=? and identity_card=?";
    	String result;
    	try{
    		result = queryForString(sql, new Object[] {type,stuId});
    	}catch(Exception e){
    		result = "";
    	}
    	return result;
    }
    
    public List<E> getStudentsOrderByName(String classId){
    	return query(SQL_FIND_STUDENT_BY_CLASSID_ORDER_NAME,new Object[]{classId},new MultiRow());
    }
    
    public List<String> filterStudentIdsByClassId(String classId, String[] studentIds){
    	return queryForInSQL(SQL_FILTER_STUDENT, new Object[] {classId}, studentIds, new MultiRowMapper<String>(){

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("id");
			}});
    }

    public List<E> getStudentsByFaintnessStudentCode(String schoolId, String studentName,
            String studentCode) {
        if (null == studentName) {
            studentName = "";
        }
        if (null == studentCode) {
            studentCode = "";
        }
        return query(SQL_FIND_STUS_BY_SCHID_NAME_STUCODE, new Object[] { schoolId,
                studentName + "%", studentCode + "%" }, new MultiRow());
    }
    
    @Override
	public List<E> getStudentsByStudentNameClassId(String schoolId,
			String studentName, String classId, Pagination page) {
    	if(StringUtils.isNotBlank(classId)){
    		if(page!=null){
    			return query(SQL_FIND_STUS_BY_SCHID_NAME_CLASS_ID, new Object[]{schoolId,classId,"%"+studentName+"%"}, new MultiRow(),page);
    		}else{
    			return query(SQL_FIND_STUS_BY_SCHID_NAME_CLASS_ID, new Object[]{schoolId,classId,"%"+studentName+"%"}, new MultiRow());
    		}
    	}else{
    		if(page!=null){
    			return query(SQL_FIND_STUS_BY_SCHID_NAME_NO_CLASS_ID, new Object[]{schoolId,"%"+studentName+"%"}, new MultiRow(),page);
    		}else{
    			return query(SQL_FIND_STUS_BY_SCHID_NAME_NO_CLASS_ID, new Object[]{schoolId,"%"+studentName+"%"}, new MultiRow());
    		}
    	}
	}
    
    public List<E> getStudentsByNameClsIds(String schId, String studentName, String[] clsIds, Pagination page){
    	if(ArrayUtils.isNotEmpty(clsIds)){
    		if (page == null) {
				return queryForInSQL(SQL_FIND_STUS_BY_SCHID_NAME_CLSIDS,
						new Object[] { schId, "%" + studentName + "%" },
						clsIds, new MultiRow());
			} else {
				return queryForInSQL(SQL_FIND_STUS_BY_SCHID_NAME_CLSIDS,
						new Object[] { schId, "%" + studentName + "%" },
						clsIds, new MultiRow(), " ORDER BY class_id,student_code", page);
			}
    	}
    	return getStudentsByStudentNameClassId(schId, studentName, null, page);
    }
    
    public List<E> queryStudentsFaintness(String unitviecode, String stuname, String unionid) {
        if (StringUtils.isEmpty(unitviecode) && StringUtils.isEmpty(stuname)) {
            return new ArrayList<E>();
        }
        String sql = SQL_FIND_STUINFO_BY_UNIONID_PREFIX;
        if (!StringUtils.isEmpty(stuname))
            sql += " AND si.student_name like '" + stuname + "%'";
        if (!StringUtils.isEmpty(unitviecode))
            sql += " AND si.unitive_code = '" + unitviecode + "'";
    
        Object[] objs = new Object[] { unionid };
        return query(sql, objs, new MultiRow());
    
    }
    
    public List<E> getStudentsByField(String field, String value) {
        StringBuffer sqlBuf = new StringBuffer();
        sqlBuf.append(getFindPrefix() + " WHERE ");
        value = StringEscapeUtils.escapeSql(value);
        sqlBuf.append(field + " like '" + value + "%'");
        sqlBuf.append(" AND is_leave_school = 0 AND is_deleted=0 ");
    
        return query(sqlBuf.toString(), new MultiRow());
    }
    
    public List<E> getStudentsByAnyConditions(List<QueryCondition> list) {
        String sql = getFindPrefix() + " WHERE is_leave_school<>9 AND is_deleted = 0 ";
        StringBuffer sqlBuf = new StringBuffer();
        sqlBuf.append(QueryConditionUtils.getSqlWhere(list));
        return query(sql + sqlBuf.toString(), new MultiRow());
    }
    
    public int getStudentCount(String classId) {
        return queryForInt(SQL_FIND_STUNUM_BY_CLSID, new Object[] { classId });
    }
    
    public int getStudentCount(String[] classIds) {
        return queryForInSQL(SQL_FIND_STUSNUM_IN_CLS, null, classIds,
                new MultiRowMapper<Integer>() {
    
                    public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
                        return rs.getInt(1);
                    }
    
                }).get(0);
    }
    
    public boolean isExistsStuByDistrict(String schdistriId) {
        int num = queryForInt(SQL_IS_EXISTS_STU_INSCHDISTRICT, new Object[] { schdistriId });
        if (num == 0)
            return false;
        else
            return true;
    }
    
    public Map<String, Integer> getStudentCountMap(String[] classIds) {
        return queryForInSQL(SQL_FIND_STUNUM_GROUP_BY_CLSID, null, classIds,
                new MapRowMapper<String, Integer>() {
    
                    public String mapRowKey(ResultSet rs, int arg1) throws SQLException {
                        return rs.getString("class_id");
                    }
    
                    public Integer mapRowValue(ResultSet rs, int arg1) throws SQLException {
                        return rs.getInt("stucount");
                    }
    
                }, " group by class_id");
    }
    
    public Map<String, Integer> getStudentSexCountMap(String[] classIds){
    	String sql = "SELECT class_id, sex, count(*) as stucount FROM base_student "
                + "WHERE is_leave_school = 0 AND is_deleted = 0 AND class_id IN ";
    	return queryForInSQL(sql, null, classIds, new MapRowMapper<String, Integer>(){
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("class_id") + rs.getString("sex");
			}

			public Integer mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getInt("stucount");
			}
			
    	}, " group by class_id,sex");
    }
    
    @Override
    public Map<String, Integer> getUnderSchoolNumUseTypeMap(String unitId,
    		String unionCode) {
    	String sql = "select u.unit_use_type, s.sex, count(1) as stucount from base_unit u, base_student s where u.id = s.school_id"+
    						" and u.union_code like ? and u.unit_state = 1 and s.is_leave_school = 0 and s.is_deleted = 0"+
    						" and u.is_deleted = 0 group by u.unit_use_type,s.sex";
    	return queryForMap(sql, new Object[]{unionCode+"%"}, new MapRowMapper<String, Integer>(){

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("unit_use_type")+ "," + rs.getString("sex");
			}

			@Override
			public Integer mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getInt("stucount");
			}});
    }
    
    @Override
    public Map<String, Integer> getUnderSchoolNumXSLBMap(String unitId,
    		String unionCode) {
    	String sql = "select s.student_type, count(1) as stucount from base_unit u, base_student s where u.id = s.school_id"+
				" and u.union_code like ? and u.unit_state = 1 and s.is_leave_school = 0 and s.is_deleted = 0"+
				" and u.is_deleted = 0 group by s.student_type";
		return queryForMap(sql, new Object[]{unionCode+"%"}, new MapRowMapper<String, Integer>(){
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("student_type");
			}
			
			@Override
			public Integer mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getInt("stucount");
			}});
    }
    public Map<String,Integer> getUnderSchoolNumGradeCodeMap(String unionCode){
//    	String sql = "select g.grade_code, count(1) as stucount from base_unit u, base_student s,base_grade g where " +
//    						"u.id = s.school_id and g.school_id = u.id and u.union_code like ? and u.unit_state = 1 " +
//    						"and s.is_leave_school = 0 and s.is_deleted = 0 and u.is_deleted = 0 group by g.grade_code";
    	String sql = "select g.grade_code, count(1) as stucount from base_student s, base_unit u, base_grade g,base_class c "+
    						"where  s.school_id = u.id and s.school_id = g.school_id and s.class_id = c.id and c.grade_id = g.id "+
    						"and u.union_code like ? and u.unit_state = 1 and s.is_leave_school = 0 " +
    						"and s.is_deleted = 0 and u.is_deleted = 0 group by g.grade_code";
    	
		return queryForMap(sql, new Object[]{unionCode+"%"}, new MapRowMapper<String, Integer>(){
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("grade_code");
			}
			@Override
			public Integer mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getInt("stucount");
			}});
    }
    @Override
    public Map<String, Integer> getUnderSchoolNumHkMap(String unitId,
    		String unionCode) {
    	String sql = "select u.unit_use_type, s.register_type, count(1) as stucount from base_unit u, base_student s where u.id = s.school_id"+
				" and u.union_code like ? and u.unit_state = 1 and s.is_leave_school = 0 and s.is_deleted = 0 and s.register_type is not null"+
				" and u.is_deleted = 0 group by u.unit_use_type,s.register_type";
		return queryForMap(sql, new Object[]{unionCode+"%"}, new MapRowMapper<String, Integer>(){
		
		@Override
		public String mapRowKey(ResultSet rs, int rowNum)
				throws SQLException {
			return rs.getString("unit_use_type")+ "," + rs.getString("register_type");
		}
		
		@Override
		public Integer mapRowValue(ResultSet rs, int rowNum)
				throws SQLException {
			return rs.getInt("stucount");
		}});
    }
    
    // =====================返回Map=============================
    
    public Map<String, E> getStudentMap(String[] studentIds) {
        return queryForInSQL(SQL_FIND_STUDENT_BY_IDS, null, studentIds, new MapRow());
    }
    
    
    
    public Map<String, E> getStudentMapWithDeleted(String[] studentIds) {
        return queryForInSQL(SQL_FIND_STUDENT_BY_IDS_WITHDELETED, null, studentIds, new MapRow());
    }
    
    public Map<String, E> getStudentMapByUnitiveCodes(String[] unitiveCodes) {
        return queryForInSQL(SQL_FIND_STU_IN_UNITIVECODE, null, unitiveCodes,
                new MapRowMapper<String, E>() {
    
                    public String mapRowKey(ResultSet rs, int arg1) throws SQLException {
                        return rs.getString("unitive_code");
                    }
    
                    public E mapRowValue(ResultSet rs, int arg1) throws SQLException {
                        return setField(rs);
                    }
    
                });
    }
    
    public Map<String, E> getStudentMapBySchoolId(String schoolId) {
        return queryForMap(SQL_FIND_INSCHOOL_STUS_BY_SCHID, new Object[] { schoolId }, new MapRow());
    }

	@Override
	public List<E> getStudentsForState(String classId) {
		return query(SQL_FIND_BY_CLSID, classId, new MultiRow());
	}

	@Override
	public Map<String, Integer> getSchIdStuNumMapByKinClass(String artScienceType,
			String gradeCode, String curAcadyear){
		if(gradeCode.length()<2 ) return null;
		int endyear = Integer.parseInt(curAcadyear.substring(5));
		int startyear = endyear - Integer.parseInt(gradeCode.substring(1,2));
		String startyearStr = startyear+"-"+(startyear+1);
		StringBuffer stringBuf = new StringBuffer("select bs.school_id as key,count(1) as value from base_student bs,base_class bc ");
		stringBuf.append(" where bs.class_id=bc.id and bc.art_science_type=? and bc.section=? and bc.acadyear = ?  group by bs.school_id ");
		 return queryForMap(stringBuf.toString(), new Object[]{artScienceType,gradeCode.substring(0,1),startyearStr}, new MapRowMapper<String, Integer>(){
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("key");
			}
			@Override
			public Integer mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getInt("value");
			}});
	}

	public Map<String, Integer> getBackgrouStuNumMapByClaIds(String[] claIds){
		String sql="select class_id,count(*)as numb from base_student bs where (bs.background not in('01','02','03') or bs.background is null) and bs.class_id in ";
		return queryForInSQL(sql, null, claIds, new MapRowMapper<String, Integer>(){
			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("class_id");
			}
			@Override
			public Integer mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getInt("numb");
			}}, "group by bs.class_id");
	}
}
