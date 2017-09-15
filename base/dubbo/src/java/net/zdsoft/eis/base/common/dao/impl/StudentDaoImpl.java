package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.zdsoft.eis.base.common.dao.StudentDao;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.simple.dao.impl.AbstractStudentDaoImpl;

public class StudentDaoImpl extends AbstractStudentDaoImpl<Student> implements StudentDao {

    public Student setField(ResultSet rs) throws SQLException {
        Student student = new Student();
        setEntity(rs, student);
        return student;
    }

}
