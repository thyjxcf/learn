package net.zdsoft.eis.base.subsystemcall.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.subsystemcall.entity.CourseScheduleDto;
import net.zdsoft.keel.util.Pagination;



public interface TimetableSubsystemService {
	/**
	 * 根据班级获取任课教师
	 * @param teacherId
	 * @return
	 */
	public String getCourseTeacherIdByClassId(String acadyear,String semester,String classId, 
			String weekOfWorktime, String dayOfWeek, String period);
	
	/**
	 * 根据班级数组获取任课教师
	 * @param acadyear
	 * @param semester
	 * @param classIds
	 * @param weekOfWorktime
	 * @param dayOfWeek
	 * @param period
	 * @return
	 */
	public Map<String, String> getCourseTeacherIdByClassIds(String acadyear,String semester,String[] classIds, 
			String weekOfWorktime, String dayOfWeek, String period);
	
	/**
	 * 获取当前学年学期该教师的任教班级
	 * @param unitId
	 * @param acadyear
	 * @param semester
	 * @param teacherId
	 * @return
	 */
	public List<String> getCourseScheduleClassIds(String unitId,String acadyear,String semester,String teacherId);
	
	/**
	 * 获取学期的教师课程
	 * @param unitId
	 * @param acadyear
	 * @param semester
	 * @return
	 */
	public List<CourseScheduleDto> getCourseScheduleListBySemester(String unitId, String acadyear, String semester);

	public List<CourseScheduleDto> findCourseSchedulesByTeacherId(
			String acadyear, int semester, String teacherId, int weekOfWorktime);
	
	/**
	 * 获取id课程信息
	 * 
	 * @param id
	 * @return
	 */
	public CourseScheduleDto getCourseScheduleById(String id);
	/**
	 * 根据条件获取课程信息
	 * 
	 * 
	 * @param timetableCourseSchedule
	 * @param isDayOfWeek是否需要带上这个参数
	 * @param page分页
	 * @return
	 */
	public List<CourseScheduleDto> findCourseSchedulesByCondition(
			CourseScheduleDto timetableCourseSchedule, boolean isDayOfWeek,
			Pagination page);
	
	public void saveCourseSchedules(CourseScheduleDto... examCourseSchedules);
	
	/**
	 * 按周、节次、星期几查询需要机房的课列表
	 * 
	 * @param unitId
	 * @param acadyear
	 * @param semester
	 * @param weekOfWorkTime
	 * @param dayOfWeeks
	 * @param periods
	 * @return
	 */
	public List<CourseScheduleDto> getComputerlabCourseSchedules(String unitId,
			String acadyear, String semester, int weekOfWorkTime,
			String[] dayOfWeeks, String[] periods);
	
	/**
	 * 按周、节次、星期几删除机房的课列表
	 * 
	 * @param unitId
	 * @param acadyear
	 * @param semester
	 * @param weeks
	 * @param dayOfWeeks
	 * @param periods
	 */
	public void deleteComputerlabCourseSchedules(String unitId,
			String acadyear, String semester, String[] weeks,
			String[] dayOfWeeks, String[] periods);
	/**
	 * 获取需要上机排课相关信息
	 * 
	 * @param unitId
	 * @param acadyear
	 * @param semester
	 * @param computerlabId
	 * @param teacherId
	 * @param weekOfWorktime
	 * @param periodIntervals(时段 早上下晚)暂时没有用到这个查询字段
	 * @return
	 */
	public List<CourseScheduleDto> getComputerlabCourseSchedules(String unitId,
			String acadyear, String semester, String computerlabId,
			String teacherId, int weekOfWorktime,String[] periodIntervals);
	public void deleteCourseSchedules(String... ids);
	
	/**
	 * 获取排课中所以老师
	 * @param unitId
	 * @param acadyear
	 * @param semester
	 * @return
	 */
	public Map<String,String> getAllTeacherIdByUnitIdOrAcSe(String unitId, String acadyear, String semester);
	/**
	 * 获取当前教师任课信息
	 * @param teacherId
	 * @return
	 */
	public Object[] getTeacherCurrentCourseSchedule(String unitId,String teacherId);
}
