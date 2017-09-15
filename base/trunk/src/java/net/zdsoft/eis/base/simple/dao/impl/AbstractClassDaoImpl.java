/* 
 * @(#)AbstractClassDaoImpl.java    Created on May 14, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.simple.dao.AbstractClassDao;
import net.zdsoft.eis.base.simple.entity.SimpleClass;
import net.zdsoft.eis.frame.client.BaseSimpleDao;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 14, 2011 2:19:11 PM $
 */
public abstract class AbstractClassDaoImpl<E extends SimpleClass> extends
		BaseSimpleDao<E> implements AbstractClassDao<E> {
	private static final String SQL_FIND_CLASS_BY_ID = "SELECT * FROM base_class WHERE id=?";
	private static final String SQL_FIND_CLASS_BY_IDS = "SELECT * FROM base_class WHERE is_deleted = 0 AND id IN";
	private static final String SQL_FIND_CLASS_BY_IDS_WITH_DEL = "SELECT * FROM base_class WHERE id IN";
	private static final String SQL_FIND_CLASS_BY_SCHOOLID_GRADUATE = "SELECT * FROM base_class "
			+ "WHERE school_id=? AND is_graduate=? AND is_deleted = 0 ORDER BY class_code";
	private static final String SQL_FIND_CLASS_BY_SCHOOLID_ACADYEAR = "SELECT * FROM base_class "
			+ "WHERE school_id=? AND acadyear=? AND is_deleted = 0 AND is_graduate =0 ORDER BY class_code";

	public void setEntity(ResultSet rs, E cls) throws SQLException {
		cls.setId(rs.getString("id"));
		cls.setSchid(rs.getString("school_id"));
		cls.setClasscode(rs.getString("class_code"));
		cls.setClassname(rs.getString("class_name"));
		cls.setClasstype(rs.getString("class_type"));
		cls.setAcadyear(rs.getString("acadyear"));
		cls.setDatecreated(rs.getTimestamp("build_date"));
		cls.setGraduatesign(rs.getInt("is_graduate"));
		cls.setGraduatedate(rs.getTimestamp("graduate_date"));
		cls.setSchoolinglen(rs.getInt("schooling_length"));
		cls.setTeacherid(rs.getString("teacher_id"));
		cls.setStuid(rs.getString("student_id"));
		cls.setSubschoolid(rs.getString("campus_id"));
		cls.setGradeId(rs.getString("grade_id"));
		cls.setViceTeacherId(rs.getString("vice_teacher_id"));
		cls.setDisplayOrder(rs.getInt("display_order"));
		cls.setIsdeleted(rs.getBoolean("is_deleted"));
		cls.setClassnamedynamic(cls.getClassname());
		cls.setState(rs.getInt("state"));
		cls.setPartnership(rs.getString("partnership"));
		cls.setPartners(rs.getString("partners"));
		cls.setContacts(rs.getString("contacts"));
		cls.setRemark(rs.getString("remark"));
		cls.setSection(rs.getInt("section"));
		cls.setTeachPlaceId(rs.getString("teach_place_id"));
		cls.setLifeGuideTeacherId(rs.getString("life_guide_teacher_id"));
		
	}

	public E getClass(String classId) {
		return query(SQL_FIND_CLASS_BY_ID, classId, new SingleRow());
	}

	public List<E> getClasses(String schoolId, boolean isGraduate) {
		return query(SQL_FIND_CLASS_BY_SCHOOLID_GRADUATE, new Object[] {
				schoolId, Integer.valueOf(isGraduate ? 1 : 0) }, new MultiRow());
	}

	public List<E> getClassesByOpenAcadyear(String schoolId, String openAcadyear) {
		return query(SQL_FIND_CLASS_BY_SCHOOLID_ACADYEAR, new Object[] {
				schoolId, openAcadyear }, new MultiRow());
	}

	public List<E> getClasses(String[] classIds) {
		return queryForInSQL(SQL_FIND_CLASS_BY_IDS, new Object[] {}, classIds,
				new MultiRow());
	}

	public Map<String, E> getClassMap(String[] classIds) {
		return queryForInSQL(SQL_FIND_CLASS_BY_IDS, null, classIds,
				new MapRow());
	}
	
	public Map<String, E> getClassMapWithDeleted(String[] classIds) {
		return queryForInSQL(SQL_FIND_CLASS_BY_IDS_WITH_DEL, null, classIds,
				new MapRow());
	}
}
