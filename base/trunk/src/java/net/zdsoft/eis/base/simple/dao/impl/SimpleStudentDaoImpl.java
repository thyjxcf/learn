/* 
 * @(#)SimpleStudentDaoImpl.java    Created on May 28, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.simple.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.simple.dao.SimpleStudentDao;
import net.zdsoft.eis.base.simple.entity.SimpleStudent;
import net.zdsoft.keel.dao.MapRowMapper;
import net.zdsoft.keel.dao.MultiRowMapper;

public class SimpleStudentDaoImpl extends AbstractStudentDaoImpl<SimpleStudent> implements
        SimpleStudentDao {

    @Override
    public SimpleStudent setField(ResultSet rs) throws SQLException {
        SimpleStudent student = new SimpleStudent();
        setEntity(rs, student);
        return student;
    }
    
    public SimpleStudent setFieldEx(ResultSet rs) throws SQLException {
        SimpleStudent stu = new SimpleStudent();
		stu.setId(rs.getString("id"));
		stu.setSpellName(rs.getString("SPELL_NAME"));
		return stu;
    }
    public Map<String ,SimpleStudent> getStudentdexByStudentIds(String[] studentIds) {
        return queryForInSQL("select * from base_student_ex where id in " ,null, studentIds,
                new MapRowMapper<String ,SimpleStudent>(){

					@Override
					public String mapRowKey(ResultSet rs, int rowNum)
							throws SQLException {
						// TODO Auto-generated method stub
						return rs.getString("id");
					}

					@Override
					public SimpleStudent mapRowValue(ResultSet rs, int rowNum)
							throws SQLException {
						// TODO Auto-generated method stub
						SimpleStudent student = setFieldEx(rs);
						return student;
					}
				
        	
        
        });
    }
}
