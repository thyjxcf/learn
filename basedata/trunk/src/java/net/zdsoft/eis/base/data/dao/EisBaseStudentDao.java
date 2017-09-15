package net.zdsoft.eis.base.data.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.entity.EisBaseStudent;
import net.zdsoft.eis.base.data.entity.StudentImport;
import net.zdsoft.eis.base.simple.dao.AbstractStudentDao;


/**
 * 
 * @author weixh
 * @since 2016-3-1 上午10:17:47
 */
public interface EisBaseStudentDao extends AbstractStudentDao<EisBaseStudent>{
	/**
	 * 新增
	 * @param student
	 */
	public void addStudent(EisBaseStudent student);
	
	/**
	 * 更新学生
	 * @param student
	 */
	public void updateStudent(EisBaseStudent student);
	
	/**
	 * 根据学生id数组软删学生
	 * @param ids
	 */
	public void deleteStudents(String[] ids);
	
	/**
	 * 批量插入学生，导入用
	 * @param stuList
	 * @return
	 */
	public int batchInsertStudent(List<StudentImport> stuList);
	
	/**
	 * 根据学生id获得学生信息
	 * @param stuId
	 * @return
	 */
	public EisBaseStudent getStudentByStuId(String stuId);

	/**
	 * 根据班级id获取班级学生
	 * @param clsId
	 * @return
	 */
	public List<EisBaseStudent> getStudentsByClassId(String clsId); 
	/**
	 * 根据 学校id 或 学生名称进行模糊查询
	 * @param schoolId
	 * @param studentName
	 * @return
	 */
	public List<EisBaseStudent> getStudentBySchoolIdName(String schoolId , String studentName);
}
