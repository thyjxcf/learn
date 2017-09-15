package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.StudentGraduate;
import net.zdsoft.keel.util.Pagination;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: StudentGraduateDao.java,v 1.10 2007/01/26 09:43:17 zhanghh Exp $
 */
public interface StudentGraduateDao {

    /**
     * 根据学校ID和学生ID数组得到毕业信息列表
     * 
     * @param schoolId 学校ID
     * @param studentIds 学生IDs
     * @return List<StudentGraduate>
     */
    public List<StudentGraduate> getStudentGraduates(String schoolId, String[] studentIds);

    /**
     * 根据学校ID和学期ID得到毕业信息列表
     * 
     * @param schoolId 学校ID
     * @param acadyear 学年
     * @param semester 学期
     * @return List
     */
    public List<StudentGraduate> getStudentGraduates(String schoolId, String acadyear,
            String semester);

    /**
     * 根据学生GUID列表得到毕业生StudentGraduateSearch列表
     * 
     * @param studentIds
     * @return
     */
    public List<StudentGraduate> getStudentGraduates(String[] studentIds);

    /**
     * 根据学校ID取相应毕业类型学生
     * 
     * @param schoolId
     * @param acadyear 
     * @param semester 
     * @param byType
     * 
     * @return
     */
    public List<StudentGraduate> getStudentGraduates(String schoolId, String acadyear,
            String semester, String byType);
    
    /**
     * 根据学校ID取相应毕业类型学生
     * 
     * @param schoolId
     * @param acadyear 
     * @param semester 
     * @param byType
     * 
     * @return
     */
    public List<StudentGraduate> getStudentGraduates(String schoolId, String acadyear,
            String semester, String byType, Pagination page);

    /**
     * 根据班级ID和学期ID得到毕业信息列表
     * 
     * @param classId 班级ID
     * @return List
     */
    public List<StudentGraduate> getStudentGraduates(String classId);
    
    /**
     * 根据班级ID和学期ID得到毕业信息列表
     * 
     * @param classId 班级ID
     * @return List
     */
    public List<StudentGraduate> getStudentGraduates(String[] classIds, String studentName, String studentCode, Pagination page);

    /**
     * 判断是否存在相同的毕业证号码，schid=null表示整个数据库
     * 
     * @param schoolId 学校ID
     * @param studentId 学生ID
     * @param byNumber 毕业证号码
     * @return boolean true:存在，false:不存在
     */
    public boolean isExistsGraduateCode(String schoolId, String studentId, String byNumber);
    
    public List<String[]> getStuGradueteAndClass(String schid, String acadyear);

}
