package net.zdsoft.eis.base.common.service.impl;

import net.zdsoft.eis.base.common.dao.StudentDao;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.simple.service.impl.AbstractStudentServiceImpl;

public class StudentServiceImpl extends AbstractStudentServiceImpl<Student> implements StudentService {
    protected StudentDao studentDao;

    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

}
