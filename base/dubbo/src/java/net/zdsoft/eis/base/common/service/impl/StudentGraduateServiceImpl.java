package net.zdsoft.eis.base.common.service.impl;

import java.util.List;

import net.zdsoft.eis.base.common.dao.StudentGraduateDao;
import net.zdsoft.eis.base.common.entity.StudentGraduate;
import net.zdsoft.eis.base.common.service.StudentGraduateService;
import net.zdsoft.keel.util.Pagination;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: StudentGraduateServiceImpl.java,v 1.15 2007/02/07 09:41:26 zhanghh Exp $
 */
public class StudentGraduateServiceImpl implements StudentGraduateService {
    private StudentGraduateDao studentGraduateDao;

    public void setStudentGraduateDao(StudentGraduateDao studentGraduateDao) {
        this.studentGraduateDao = studentGraduateDao;
    }

    // ================ 以上是set====================

    public List<StudentGraduate> getStudentGraduates(String[] studentIds) {
        return studentGraduateDao.getStudentGraduates(studentIds);
    }

    public List<StudentGraduate> getStudentGraduates(String schoolId, String[] studentIds) {
        return studentGraduateDao.getStudentGraduates(schoolId, studentIds);
    }

    public List<StudentGraduate> getStudentGraduatesBySemesterId(String schoolId,
            String acadyear, String semester) {
        return studentGraduateDao.getStudentGraduates(schoolId, acadyear, semester);
    }

    public List<String[]> getStuGradueteAndClass(String schid, String acadyear){
    	 return studentGraduateDao.getStuGradueteAndClass( schid,  acadyear);
    }
    
    public List<StudentGraduate> getStudentGraduates(String schoolId, String acadyear,
            String semester, String byType) {
        return studentGraduateDao.getStudentGraduates(schoolId, acadyear, semester, byType);
    }
    
    @Override
	public List<StudentGraduate> getStudentGraduates(String schoolId,
			String acadyear, String semester, String byType, Pagination page) {
    	 return studentGraduateDao.getStudentGraduates(schoolId, acadyear, semester, byType, page);
	}

    public boolean isExistsGraduateCode(String schoolId, String studentId, String bynumber) {
        return studentGraduateDao.isExistsGraduateCode(schoolId, studentId, bynumber);
    }

    public List<StudentGraduate> getStudentGraduates(String classId) {
        return studentGraduateDao.getStudentGraduates(classId);
    }

	@Override
	public List<StudentGraduate> getStudentGraduates(String[] classIds, String studentName, String studentCode, 
			Pagination page) {
		return studentGraduateDao.getStudentGraduates(classIds, studentName, studentCode, page);
	}

}
