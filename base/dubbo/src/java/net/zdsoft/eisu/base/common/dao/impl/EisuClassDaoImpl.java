/* 
 * @(#)EisuClassDaoImpl.java    Created on May 14, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.simple.dao.impl.AbstractClassDaoImpl;
import net.zdsoft.eisu.base.common.dao.EisuClassDao;
import net.zdsoft.eisu.base.common.entity.EisuClass;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 14, 2011 11:36:54 AM $
 */
public class EisuClassDaoImpl extends AbstractClassDaoImpl<EisuClass> implements
		EisuClassDao {
	private static final String SQL_UPDATE_GRADUATE_SIGN = "UPDATE base_class SET is_graduate=?,graduate_acadyear=? WHERE id=?";

	private static final String SQL_UPDATE_STATE_WX = "UPDATE base_class SET state=1 , is_deleted = 1 WHERE is_graduate=0 and specialty_point_id in";
	private static final String SQL_UPDATE_STATE_WX_BYSPECID = "UPDATE base_class SET state=1 , is_deleted = 1 WHERE is_graduate=0 and specialty_id in";

	private static final String SQL_UPDATE_PRE_GRADUATE_SIGN_BY_IDS = "UPDATE base_class SET is_pre_graduate=? WHERE id IN";

	private static final String SQL_FIND_CLASS_BY_SPECIALTYID = "SELECT * FROM base_class "
			+ "WHERE specialty_id=? AND is_deleted = 0 AND is_graduate=0 ORDER BY acadyear desc, class_code asc";

	private static final String SQL_FIND_CLASS_BY_SPECIALTYID_ACADYEAR = "SELECT * FROM base_class "
			+ "WHERE specialty_id=? AND acadyear=? AND is_deleted = 0 AND is_graduate=0 ORDER BY class_code";

	private static final String SQL_FIND_CLASS_BY_SPECIALTYID_ACADYEAR_TEACHERID = "SELECT * FROM base_class "
			+ "WHERE specialty_id=? AND acadyear=? AND teacher_id=? AND is_deleted = 0 ORDER BY class_code";

	private static final String SQL_FIND_CLASS_BY_SPECIALTYID_GRADUATE = "SELECT * FROM base_class "
			+ "WHERE specialty_id=? AND is_deleted = 0 AND is_graduate=? ORDER BY class_code";

	private static final String SQL_FIND_CLASS_BY_SPECIALTYIDS_GRADUATE = "SELECT * FROM base_class "
			+ "WHERE is_deleted = 0 AND is_graduate=? and state=0 and specialty_id in";

	private static final String SQL_FIND_CLASS_BY_SPECIALTY_POINTID_ACADYEAR = "SELECT * FROM base_class "
			+ "WHERE specialty_id=? AND specialty_point_id=? AND acadyear=? and is_deleted = 0 ORDER BY class_code";

	private static final String SQL_FIND_CLASS_BY_SPECIALTY_POINTID_ACADYEAR_GRADUATE = "SELECT * FROM base_class "
			+ "WHERE specialty_id=? AND specialty_point_id like ? AND acadyear like ? AND is_graduate=? AND is_deleted = 0 "
			+ "ORDER BY acadyear,class_code";

	private static final String SQL_FIND_PRE_GRADUATE_CLASS_BY_SPECIALTY_POINTID_ACADYEAR = "SELECT * FROM base_class "
			+ "WHERE specialty_id=? AND specialty_point_id like ? AND acadyear like ? AND is_graduate=0 AND is_pre_graduate ='1' AND is_deleted = 0 "
			+ "ORDER BY acadyear,class_code";
	private static final String SQL_FIND_CLASS_BY_SPECIALTY = "SELECT * FROM base_class "
			+ "WHERE specialty_id=? AND specialty_point_id like ? AND acadyear like ? AND is_deleted = 0 ORDER BY class_code";

	private static final String SQL_FIND_CLASS_BY_TEACHAREAID = "SELECT * FROM base_class "
			+ "WHERE teach_area_id =? AND is_deleted = 0 ORDER BY class_code";
	private static final String SQL_FIND_CLASS_BY_TEACHAREAID_ACADYEAR = "SELECT * FROM base_class "
			+ "WHERE teach_area_id =? AND acadyear=? AND is_deleted = 0 ORDER BY class_code";
	private static final String SQL_FIND_CLASS_BY_POINTID = "SELECT id FROM base_class "
			+ "WHERE is_deleted = 0 and is_graduate=0 and specialty_point_id=? and specialty_id=? ";
	private static final String SQL_FIND_CLASS_TEACHERID = "SELECT * FROM base_class where teacher_id=? AND is_deleted = 0";
	private static final String SQL_FIND_CLASS_SPECIDS_ACADYEAR = "SELECT * FROM base_class "
			+ "WHERE is_deleted = 0 and is_graduate=0 and acadyear=? and specialty_id IN";
	private static final String SQL_FIND_CLASS_By_CLASSID_AND_TEACHERID = "SELECT * FROM base_class where teacher_id=? AND is_deleted = 0 and id in ";

	private static final String SQL_FIND_GRADUTING_CLS_BY_SCHID_SPECIALTY_GRADEDYEAR = "SELECT * FROM base_class "
			+ "WHERE specialty_id=? AND specialty_point_id like ? AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) = schooling_length AND is_deleted = 0 "
			+ "ORDER BY acadyear DESC,class_code ASC";

	private static final String SQL_FIND_GRADUTING_CLS_BY_SCHID_SPECIALTY_GRADEDYEAR_OPENACADYEAR = "SELECT * FROM base_class "
			+ "WHERE specialty_id=? AND specialty_point_id like ? AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) = schooling_length  AND acadyear = ? AND is_deleted = 0"
			+ "ORDER BY acadyear DESC,class_code ASC";

	private static final String SQL_FIND_GRADUTING_CLS_BY_SCHID_GRADEDYEAR = "SELECT * FROM base_class "
			+ "WHERE school_id=? AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) = schooling_length AND is_deleted = 0 "
			+ "ORDER BY acadyear DESC,class_code ASC";

	private static final String SQL_FIND_ENROLL_CLS_BY_SCHID_ENROLLYEAR = "SELECT * FROM base_class "
			+ "WHERE school_id=? AND acadyear=? AND is_deleted = 0 "
			+ "ORDER BY acadyear DESC,class_code ASC";

	private static final String SQL_FIND_ENROLL_CLS_BY_SCHID = "SELECT * FROM base_class "
			+ "WHERE school_id=? AND is_deleted = 0 "
			+ "ORDER BY acadyear DESC,class_code ASC";

	private static final String SQL_FIND_CLASS_BY_OPENACADYEAR = "SELECT * FROM base_class "
			+ "WHERE is_deleted = 0 and is_graduate=0 and acadyear = ? ";

	@Override
	public EisuClass setField(ResultSet rs) throws SQLException {
		EisuClass cls = new EisuClass();
		setEntity(rs, cls);
		if(this.isExistColumn(rs, "specialty_id")){
			cls.setSpecialtyId(rs.getString("specialty_id"));
		}
		if(this.isExistColumn(rs, "specialty_point_id")){
			cls.setSpecialtyPointId(rs.getString("specialty_point_id"));
		}
		if(this.isExistColumn(rs, "teach_area_id")){
			cls.setTeachAreaId(rs.getString("teach_area_id"));
		}
		if(this.isExistColumn(rs, "teach_place_id")){
			cls.setTeachPlaceId(rs.getString("teach_place_id"));
		}
		if(this.isExistColumn(rs, "is_pre_graduate")){
			cls.setPreGraduateSign(rs.getString("is_pre_graduate"));
		}
		if(this.isExistColumn(rs, "graduate_acadyear")){
			cls.setGraduateAcadyear(rs.getString("graduate_acadyear"));
		}
		cls.setClassnamedynamic(cls.getClassname());
		if(this.isExistColumn(rs, "first_type")){
			cls.setFirstType(rs.getString("first_type"));
		}
		return cls;
	}
	public boolean isExistColumn(ResultSet rs, String columnName) {
	    try {
	        if (rs.findColumn(columnName) > 0 ) {
	            return true;
	        } 
	    }
	    catch (SQLException e) {
	        return false;
	    }
	    return false;
	}
	public void updateGraduateSign(String classId, String graduateAcadyear,
			int sign) {
		update(SQL_UPDATE_GRADUATE_SIGN, new Object[] { sign, graduateAcadyear,
				classId });
	}

	public void updatePreGraduateSign(int sign, String... classIds) {
		updateForInSQL(SQL_UPDATE_PRE_GRADUATE_SIGN_BY_IDS,
				new Object[] { sign }, classIds);
	}

	public List<EisuClass> getClassesBySpecialtyId(String specialtyId) {
		return query(SQL_FIND_CLASS_BY_SPECIALTYID,
				new Object[] { specialtyId }, new MultiRow());
	}

	public List<EisuClass> getClassesBySpecialtyId(String specialtyId,
			String openAcadyear) {
		return query(SQL_FIND_CLASS_BY_SPECIALTYID_ACADYEAR, new Object[] {
				specialtyId, openAcadyear }, new MultiRow());

	}

	public List<EisuClass> getClassesBySpecialtyId(String specialtyId,
			String openAcadyear, String teacherId) {
		return query(SQL_FIND_CLASS_BY_SPECIALTYID_ACADYEAR_TEACHERID,
				new Object[] { specialtyId, openAcadyear, teacherId },
				new MultiRow());

	}

	public List<EisuClass> getClassesBySpecialtyId(String specialtyId,
			boolean isGraduate) {
		return query(SQL_FIND_CLASS_BY_SPECIALTYID_GRADUATE, new Object[] {
				specialtyId, isGraduate ? 1 : 0 }, new MultiRow());
	}

	public List<EisuClass> getClassesBySpecialtyPointId(String specialtyId,
			String specialtyPointId, String openAcadyear) {
		return query(SQL_FIND_CLASS_BY_SPECIALTY_POINTID_ACADYEAR,
				new Object[] { specialtyId, specialtyPointId, openAcadyear },
				new MultiRow());
	}

	public List<EisuClass> getClassesBySpecialtyId(String specialtyId,
			String specialtyPointId, String openAcadyear, boolean isGraduate) {
		if (StringUtils.isEmpty(specialtyPointId))
			specialtyPointId = "%";

		if (StringUtils.isEmpty(openAcadyear))
			openAcadyear = "%";

		return query(SQL_FIND_CLASS_BY_SPECIALTY_POINTID_ACADYEAR_GRADUATE,
				new Object[] { specialtyId, specialtyPointId, openAcadyear,
						Integer.valueOf(isGraduate ? 1 : 0) }, new MultiRow());
	}

	public List<EisuClass> getPreGraudateClasses(String specialtyId,
			String specialtyPointId, String openAcadyear) {
		if (StringUtils.isEmpty(specialtyPointId))
			specialtyPointId = "%";

		if (StringUtils.isEmpty(openAcadyear))
			openAcadyear = "%";

		return query(SQL_FIND_PRE_GRADUATE_CLASS_BY_SPECIALTY_POINTID_ACADYEAR,
				new Object[] { specialtyId, specialtyPointId, openAcadyear },
				new MultiRow());
	}

	public List<EisuClass> getClassesBySpecialty(String specialtyId,
			String specialtyPointId, String openAcadyear) {
		if (StringUtils.isEmpty(specialtyPointId))
			specialtyPointId = "%";

		if (StringUtils.isEmpty(openAcadyear))
			openAcadyear = "%";

		return query(SQL_FIND_CLASS_BY_SPECIALTY, new Object[] { specialtyId,
				specialtyPointId, openAcadyear }, new MultiRow());
	}

	@Override
	public List<EisuClass> getClassesByTeachAreaId(String teachAreaId) {
		if (StringUtils.isBlank(teachAreaId)) {
			return query(
					"SELECT * FROM base_class "
							+ "WHERE teach_area_id is null AND is_deleted = 0 ORDER BY class_code",
					new MultiRow());
		}
		return query(SQL_FIND_CLASS_BY_TEACHAREAID,
				new Object[] { teachAreaId }, new MultiRow());
	}

	@Override
	public void updateClassState(String[] pointIds) {
		updateForInSQL(SQL_UPDATE_STATE_WX, null, pointIds);
	}

	@Override
	public List<String> getClassIdsBySpecIds(String[] specIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id from base_class where is_deleted = 0 and is_graduate=0 ");
		if (null != specIds && specIds.length > 0) {
			sql.append("and specialty_id in");
		}
		return queryForInSQL(sql.toString(), null, specIds,
				new MultiRowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("id");
					}
				});
	}

	@Override
	public List<String> getClassIdsByPointId(String pointId, String specId) {
		return query(SQL_FIND_CLASS_BY_POINTID,
				new Object[] { pointId, specId }, new MultiRowMapper<String>() {

					@Override
					public String mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return rs.getString("id");
					}
				});

	}

	@Override
	public List<EisuClass> getClassesBySpecialtyIds(String[] specialtyId,
			boolean isGraduate) {
		return queryForInSQL(SQL_FIND_CLASS_BY_SPECIALTYIDS_GRADUATE,
				new Object[] { isGraduate ? 1 : 0 }, specialtyId,
				new MultiRow());
	}

	public List<EisuClass> getClassIdsBySpecIds(String[] specIds,
			String openAcadyear) {
		if (ArrayUtils.isEmpty(specIds)) {
			return new ArrayList<EisuClass>();
		}
		return queryForInSQL(SQL_FIND_CLASS_SPECIDS_ACADYEAR,
				new Object[] { openAcadyear }, specIds, new MultiRow());
	}

	@Override
	public void updateClassStateBySpec(String[] specIds) {
		updateForInSQL(SQL_UPDATE_STATE_WX_BYSPECID, null, specIds);
	}

	@Override
	public List<EisuClass> getClassesByTeacherId(String teacherId) {
		return query(SQL_FIND_CLASS_TEACHERID, new Object[] { teacherId },
				new MultiRow());
	}
	
	public List<EisuClass> getClassesByLifeGuide(String lifeGuideTeaId){
		return query("SELECT * FROM base_class where life_guide_teacher_id=? AND is_deleted = 0", new Object[] { lifeGuideTeaId },
				new MultiRow());
	}

	@Override
	public List<EisuClass> getClassesAllstate(String schoolId) {
		String sql = "select * from base_class where school_id=? and is_graduate=0 AND (is_deleted = 0) or (is_deleted=1 and state=1) ORDER BY acadyear DESC,class_code ASC";
		return query(sql, new Object[] { schoolId }, new MultiRow());
	}

	@Override
	public List<EisuClass> getClassByGradeId(String schoolId, String gradeId) {
		StringBuffer sql =new StringBuffer("select * from base_class where school_id=?");
		List<Object> args=new ArrayList<Object>();
		args.add(schoolId);
		if(StringUtils.isNotBlank(gradeId)){
			sql.append(" and grade_id=?");
			args.add(gradeId);
		}
		sql.append(" and is_graduate=0 AND (is_deleted = 0) or (is_deleted=1 and state=1) ORDER BY acadyear DESC,class_code ASC");
		return query(sql.toString(), args.toArray(), new MultiRow());
	}
	@Override
	public List<EisuClass> getClasses(String schoolId) {
		String sql = "select * from base_class where school_id=? AND is_graduate=0 AND is_deleted = 0 ORDER BY acadyear desc, class_code asc";
		return query(sql, new Object[] { schoolId }, new MultiRow());
	}

	@Override
	public List<EisuClass> getClassesByAreaId(String areaId) {
		String sql = "select * from base_class where teach_area_id=? AND is_graduate=0 AND is_deleted = 0 ORDER BY acadyear desc, class_code asc";
		return query(sql, new Object[] { areaId }, new MultiRow());
	}

	@Override
	public List<EisuClass> getClassesByClassIdAndTeacherId(String[] ClassIds,
			String teacherId) {
		return queryForInSQL(SQL_FIND_CLASS_By_CLASSID_AND_TEACHERID,
				new Object[] { teacherId }, ClassIds, new MultiRow());
	}

	public List<EisuClass> getGraduatingClassesBySpecialty(String specialtyId,
			String specialtyPointId, String graduateAcadyear) {
		if (StringUtils.isEmpty(specialtyPointId))
			specialtyPointId = "%";
		return query(
				SQL_FIND_GRADUTING_CLS_BY_SCHID_SPECIALTY_GRADEDYEAR,
				new Object[] { specialtyId, specialtyPointId, graduateAcadyear },
				new MultiRow());
	}

	public List<EisuClass> getGraduatingClassesBySpecialty(String specialtyId,
			String specialtyPointId, String graduateAcadyear, String acadyear) {
		if (StringUtils.isEmpty(specialtyPointId))
			specialtyPointId = "%";
		return query(
				SQL_FIND_GRADUTING_CLS_BY_SCHID_SPECIALTY_GRADEDYEAR_OPENACADYEAR,
				new Object[] { specialtyId, specialtyPointId, graduateAcadyear,
						acadyear }, new MultiRow());
	}

	@Override
	public List<EisuClass> getGraduatingClasses(String schoolId,
			String graduateAcadyear) {
		return query(SQL_FIND_GRADUTING_CLS_BY_SCHID_GRADEDYEAR, new Object[] {
				schoolId, graduateAcadyear }, new MultiRow());
	}

	public List<EisuClass> getClassByAcadyearTeaId(String unitId,
			String teachAreaId, String acadyear, String teacherId) {
		String sql = "SELECT * FROM base_class "
				+ "WHERE school_id=? AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) > 0 "
				+ "AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) < schooling_length AND is_deleted = 0 ";
		List<Object> args = new ArrayList<Object>();
		args.add(unitId);
		args.add(acadyear);
		args.add(acadyear);
		if (StringUtils.isNotBlank(teachAreaId)) {
			if (StringUtils.equals(BaseConstant.ZERO_GUID, teachAreaId)) {
				sql += " and teach_area_id is null";
			} else {
				sql += " and teach_area_id=?";
				args.add(teachAreaId);
			}
		}
		if (StringUtils.isNotBlank(teacherId)) {
			sql += " and teacher_id=?";
			args.add(teacherId);
		}
		sql += " ORDER BY acadyear DESC,class_code ASC";
		return query(sql, args.toArray(), new MultiRow());
	}

	public List<EisuClass> getClassByAcadyearTeaId(String unitId,
			String teachAreaId, String acadyear, String teacherId,
			String enrollYear) {
		if (StringUtils.isBlank(enrollYear)) {
			return getClassByAcadyearTeaId(unitId, teachAreaId, acadyear,
					teacherId);
		}
		String sql = "SELECT * FROM base_class "
				+ "WHERE school_id=? AND acadyear=? AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) > 0 "
				+ "AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) < schooling_length AND is_deleted = 0 ";
		List<Object> args = new ArrayList<Object>();
		args.add(unitId);
		args.add(enrollYear);
		args.add(acadyear);
		args.add(acadyear);
		if (StringUtils.isNotBlank(teachAreaId)) {
			if (StringUtils.equals(BaseConstant.ZERO_GUID, teachAreaId)) {
				sql += " and teach_area_id is null";
			} else {
				sql += " and teach_area_id=?";
				args.add(teachAreaId);
			}
		}
		if (StringUtils.isNotBlank(teacherId)) {
			sql += " and teacher_id=?";
			args.add(teacherId);
		}
		sql += " ORDER BY acadyear DESC,class_code ASC";
		return query(sql, args.toArray(), new MultiRow());
	}
	
	public List<EisuClass> getClassByAcadyear(String unitId,
			String teachAreaId, String acadyear, String teacherId, String enrollYear){
		String sql = "SELECT * FROM base_class "
				+ "WHERE school_id=? AND is_deleted = 0 AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) > 0 "
				+ "AND (cast(substr(?,6,9) AS integer) - cast(substr(acadyear,1,4) AS integer)) <= schooling_length ";
		List<Object> args = new ArrayList<Object>();
		args.add(unitId);
		args.add(acadyear);
		args.add(acadyear);
		if(StringUtils.isNotBlank(enrollYear)){
			sql += " and acadyear=?";
			args.add(enrollYear);
		}
		if (StringUtils.isNotBlank(teachAreaId)) {
			if (StringUtils.equals(BaseConstant.ZERO_GUID, teachAreaId)) {
				sql += " and teach_area_id is null";
			} else {
				sql += " and teach_area_id=?";
				args.add(teachAreaId);
			}
		}
		if (StringUtils.isNotBlank(teacherId)) {
			sql += " and teacher_id=?";
			args.add(teacherId);
		}
		sql += " ORDER BY acadyear DESC,class_code ASC";
		return query(sql, args.toArray(), new MultiRow());
	}

	public List<EisuClass> getClassesByTeachAreaIdAndOpenAcadyear(
			String schoolId, String teachAreaId, String openAcadyear) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM base_class WHERE school_id = ? ");
		List<Object> argList = new ArrayList<Object>();
		argList.add(schoolId);
		if  (StringUtils.isNotBlank(teachAreaId) && !BaseConstant.ZERO_GUID.equals(teachAreaId)) {
			sb.append(" AND teach_area_id =? ");
			argList.add(teachAreaId);
		} else if (BaseConstant.ZERO_GUID.equals(teachAreaId)) {
			sb.append(" AND teach_area_id is null ");
		}
		if  (StringUtils.isNotBlank(openAcadyear)) {
			sb.append(" AND acadyear=? ");
			argList.add(openAcadyear);
		}		
		sb.append("AND is_deleted = 0 AND is_graduate = 0 ORDER BY class_code ASC ");
		return query(sb.toString(), argList.toArray(new Object[0]),
				new MultiRow());
	}

	@Override
	public List<EisuClass> getEnrollClasses(String schoolId, String acadyear) {
		return query(SQL_FIND_ENROLL_CLS_BY_SCHID_ENROLLYEAR, new Object[] {
				schoolId, acadyear }, new MultiRow());
	}

	@Override
	public Map<String, EisuClass> getClassMap(String schoolId) {
		return queryForMap(SQL_FIND_ENROLL_CLS_BY_SCHID,
				new Object[] { schoolId }, new MapRow());
	}

	@Override
	public Map<String, EisuClass> getClassMapByUnitIdNameCodes(String unitid,
			String[] nameCodes) {
		String sql = "select * from  base_class t where t.is_deleted=0 and t.school_id =? and t.class_name || t.class_code in";

		return this.queryForInSQL(sql, new Object[] { unitid }, nameCodes,
				new MapRowMapper<String, EisuClass>() {

					@Override
					public String mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						String key = rs.getString("class_name")
								+ rs.getString("class_code");
						return key;
					}

					@Override
					public EisuClass mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						return setField(rs);
					}

				});
	}

	@Override
	public List<EisuClass> getClassesByTeachAreaId(String teachAreaId,
			String openAcadyear) {
		return query(SQL_FIND_CLASS_BY_TEACHAREAID_ACADYEAR, new Object[] {
				teachAreaId, openAcadyear }, new MultiRow());
	}

	@Override
	public List<EisuClass> getClassesByOpenacadyear(String openAcadyear) {
		return query(SQL_FIND_CLASS_BY_OPENACADYEAR,
				new Object[] { openAcadyear }, new MultiRow());
	}

	@Override
	public List<EisuClass> getClassesByOpenacadyear(String unitId,
			String openAcadyear, String teacherId) {
		String sql = "SELECT * FROM base_class "
				+ "WHERE is_deleted = 0 and is_graduate=0 and school_id=? ";
		List<Object> args = new ArrayList<Object>();
		args.add(unitId);
		if (StringUtils.isNotBlank(openAcadyear)) {
			sql += " and acadyear=?";
			args.add(openAcadyear);
		}
		if (StringUtils.isNotBlank(teacherId)) {
			sql += " and teacher_id=?";
			args.add(teacherId);
		}
		sql += " ORDER BY acadyear DESC,class_code ASC";
		return query(sql, args.toArray(), new MultiRow());
	}

}
