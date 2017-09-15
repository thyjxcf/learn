/* 
 * @(#)EisuGradeDaoImpl.java    Created on May 18, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eisu.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eisu.base.common.dao.EisuGradeDao;
import net.zdsoft.eisu.base.common.entity.EisuGrade;
import net.zdsoft.keel.dao.MapRowMapper;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 18, 2011 11:15:10 AM $
 */
public class EisuGradeDaoImpl extends BaseDao<EisuGrade> implements EisuGradeDao {
    private static final String SQL_FIND_GRADE_BY_ID = "SELECT * FROM base_grade WHERE id = ?";

    private static final String SQL_FIND_GRADE_BY_SCHOOLID_ACADYEAR = "SELECT * FROM base_grade WHERE school_id=? AND open_acadyear=? AND is_deleted = 0";

    private static final String SQL_FIND_GRADES_BY_SCHOOLID = "SELECT * FROM base_grade "
            + "WHERE school_id=? AND is_deleted = 0 ORDER BY open_acadyear asc";
    
    private static final String SQL_FIND_ACTIVE_GRADES_BY_SCHOOLID = "SELECT * FROM base_grade "
        + "WHERE school_id=? AND is_deleted = 0 and open_acadyear IN (select acadyear from base_class where is_graduate=0 AND is_deleted = 0) ORDER BY open_acadyear asc";

    @Override
    public EisuGrade setField(ResultSet rs) throws SQLException {
        EisuGrade grade = new EisuGrade();
        grade.setId(rs.getString("id"));
        grade.setGradename(rs.getString("grade_name"));
        grade.setAcadyear(rs.getString("open_acadyear"));
        return grade;
    }

    public EisuGrade getGrade(String gradeId) {
        return query(SQL_FIND_GRADE_BY_ID, gradeId, new SingleRow());
    }

    public EisuGrade getGrade(String schoolId, String openAcadyear) {
        return query(SQL_FIND_GRADE_BY_SCHOOLID_ACADYEAR, new Object[] { schoolId, openAcadyear },
                new SingleRow());
    }

    public List<EisuGrade> getGrades(String schoolId) {
        return query(SQL_FIND_GRADES_BY_SCHOOLID, schoolId, new MultiRow());
    }
    
    public List<EisuGrade> getActiveGrades(String schoolId) {
        return query(SQL_FIND_ACTIVE_GRADES_BY_SCHOOLID, schoolId, new MultiRow());
    }

	@Override
	public Map<String, EisuGrade> getGradesMap(String schoolId) {
		return queryForMap(SQL_FIND_GRADES_BY_SCHOOLID, new String[]{schoolId}, new MapRowMapper<String,EisuGrade>() {

			@Override
			public String mapRowKey(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("open_acadyear");
			}

			@Override
			public EisuGrade mapRowValue(ResultSet rs, int rowNum)
					throws SQLException {
				return setField(rs);
			}
			
		});
	}
}
