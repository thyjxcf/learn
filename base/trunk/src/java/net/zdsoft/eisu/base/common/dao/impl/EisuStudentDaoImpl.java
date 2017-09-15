/* 
 * @(#)EisuStudentDaoImpl.java    Created on May 16, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.simple.dao.impl.AbstractStudentDaoImpl;
import net.zdsoft.eisu.base.common.dao.EisuStudentDao;
import net.zdsoft.eisu.base.common.entity.EisuStudent;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 16, 2011 3:01:40 PM $
 */
public class EisuStudentDaoImpl extends AbstractStudentDaoImpl<EisuStudent>
		implements EisuStudentDao {

	private final String SQL_FIND_STUDENT_BY_SPECIALTYID = getFindPrefix()
			+ "WHERE spec_id = ? AND is_deleted = 0 ORDER BY student_code";

	private final String SQL_FIND_STUDENT_BY_SPECIALTYPOINTID = getFindPrefix()
			+ "WHERE spec_id = ? AND specpoint_id = ? AND is_deleted = 0 ORDER BY student_code";

	private static final String SQL_UPDATE_BACKGROUND_BY_IDS = "UPDATE base_student "
			+ " set background=? where id IN";

	@Override
	public EisuStudent setField(ResultSet rs) throws SQLException {
		EisuStudent student = new EisuStudent();
		setEntity(rs, student);
		student.setSpecId(rs.getString("spec_id"));
		student.setSpecpointId(rs.getString("specpoint_id"));
		student.setIsdeleted(rs.getBoolean("is_deleted"));
		return student;
	}

	public List<EisuStudent> getStudentsBySpecialtyId(String specialtyId) {
		return query(SQL_FIND_STUDENT_BY_SPECIALTYID, specialtyId,
				new MultiRow());
	}

	public List<EisuStudent> getStudentsBySpecialtyPointId(String specialtyId,
			String specialtyPointId) {
		return query(SQL_FIND_STUDENT_BY_SPECIALTYPOINTID, new Object[] {
				specialtyId, specialtyPointId }, new MultiRow());
	}

	public List<EisuStudent> getStudentsBySpecialtyId(String[] specialtyIds,
			boolean isContainFreshman, String studentName, String studentCode) {
		if (null == studentName) {
			studentName = "";
		}
		if (null == studentCode) {
			studentCode = "";
		}

		String sql = getFindPrefix() + "WHERE is_deleted = 0 ";
		if (!isContainFreshman) {
			sql += " AND class_id <> '" + BaseConstant.ZERO_GUID + "'";
		}

		sql += " AND student_name like ? AND student_code like ? AND spec_id IN ";

		return queryForInSQL(sql, new Object[] { studentName + "%",
				studentCode + "%" }, specialtyIds, new MultiRow(),
				" ORDER BY student_code");
	}

	public void updateBackGroundByIds(String background, String[] ids) {
		updateForInSQL(SQL_UPDATE_BACKGROUND_BY_IDS,
				new Object[] { background }, ids);
	}
}
