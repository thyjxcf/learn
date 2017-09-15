package net.zdsoft.office.studentLeave.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentLeave.entity.OfficeStudentLeave;
/**
 * office_student_leave
 * @author 
 * 
 */
public interface OfficeStudentLeaveService{

	/**
	 * 新增office_student_leave
	 * @param officeStudentLeave
	 * @return
	 */
	public OfficeStudentLeave save(OfficeStudentLeave officeStudentLeave);

	/**
	 * 根据ids数组删除office_student_leave数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_student_leave
	 * @param officeStudentLeave
	 * @return
	 */
	public Integer update(OfficeStudentLeave officeStudentLeave);
	
	/**
	 * 批量更新回校状态
	 * @param officeStudentLeaves
	 */
	public void batchUpdateBackState(List<OfficeStudentLeave> officeStudentLeaves);

	/**
	 * 根据id获取office_student_leave
	 * @param id
	 * @return
	 */
	public OfficeStudentLeave getOfficeStudentLeaveById(String id);

	/**
	 * 根据ids数组查询office_student_leavemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeStudentLeave> getOfficeStudentLeaveMapByIds(String[] ids);

	/**
	 * 获取office_student_leave列表
	 * @return
	 */
	public List<OfficeStudentLeave> getOfficeStudentLeaveList();

	/**
	 * 分页获取office_student_leave列表
	 * @param page
	 * @return
	 */
	public List<OfficeStudentLeave> getOfficeStudentLeavePage(Pagination page);
	
	/**
	 * 根据相应的参数获取列表
	 * @param startTime
	 * @param endTime
	 * @param status
	 * @return
	 */
	public List<OfficeStudentLeave> getOfficeStudentLeavePageByParams(String createUserName,Date startTime,Date endTime,int state,Pagination page,String unitId);
	
	/**
	 * 查出state>=2的数据
	 * @param page
	 * @param unitId
	 * @return
	 */
	public List<OfficeStudentLeave> getOfficeStudentLeavesByAuditParams(Date startTime,Date endTime,int state,Pagination page,String unitId,String createUserName);

	
	/**
	 * 查出state>=3的数据  统计
	 * @param startTime
	 * @param endTime
	 * @param state
	 * @param page
	 * @param unitId
	 * @return
	 */
	public List<OfficeStudentLeave> getOfficeStudentLeavesByCountParams(Date startTime,Date endTime,int state,Pagination page,String unitId,String[] leaveIds,String remark,String classId,String gradeId);
	
	/**
	 * 获取统计信息
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param classId
	 * @return
	 */
	public Map<String, String> getSumMap(String unitId, Date startTime, Date endTime, String classId);
	/**
	 * 获取年级统计信息
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param classId
	 * @return
	 */
	public Map<String, String> getSumGradeMap(String unitId, Date startTime, Date endTime);
	/**
	 * 获取班级统计信息
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param classId
	 * @return
	 */
	public Map<String, String> getSumClassMap(String unitId,String gradeId, Date startTime, Date endTime);
	/**
	 * 获取年级次数信息
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param classId
	 * @return
	 */
	public Map<String, String> getSumGradeTimeMap(String unitId, Date startTime, Date endTime);
	/**
	 * 获取班级次数信息
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param classId
	 * @return
	 */
	public Map<String, String> getSumClassTimeMap(String unitId,String gradeId, Date startTime, Date endTime);
	/**
	 * 获取班级次数信息
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param classId
	 * @return
	 */
	public Map<String, String> getSumNameTimeMap(String unitId,String gradeId,String classId, Date startTime, Date endTime);
	
	/**
	 * 相应时间段内有请假信息的学生id
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param classId
	 * @return
	 */
	public String[] getStuIds(String unitId,Date startTime,Date endTime,String classId,String[] leaveTypeIds,String gradeId);

	
	/**
	 * 某个特定时间，查出state=3的数据
	 * @param date
	 * @param studentId
	 */
	public List<OfficeStudentLeave> findStuIsLeaveBytime(Date date, String[] studentIds);
	
	/**
	 * 某个时间段，查出state=3的数据
	 * @param date
	 * @param studentId
	 */
	public List<OfficeStudentLeave> findStuIsLeaveBytime(Date startTime,Date endTime, String[] studentIds);
	
	/**
	 * 判断时间是否交叉
	 * @param unitId
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public boolean isExistConflict(String unitId,String id,String studentId,Date startTime,Date endTime);
}