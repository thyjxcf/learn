package net.zdsoft.eis.base.subsystemcall.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.subsystemcall.entity.EduadmCourseDto;
import net.zdsoft.eis.base.subsystemcall.entity.EduadmCourseTeacherDto;
import net.zdsoft.eis.base.subsystemcall.entity.EduadmSubjectDto;
import net.zdsoft.eis.base.subsystemcall.entity.EduadmTeachClassDto;
import net.zdsoft.eis.base.subsystemcall.entity.EduadmTeachClassStuDto;
import net.zdsoft.keel.util.Pagination;

public interface EduadmSubsystemService {
	
	/**
	 * 判断是否是教务管理员
	 * @param unitId
	 * @param teacherId
	 * @return
	 */
	public boolean isEduadmRole(String unitId,String teacherId);
	
	/**
	 * 根据教师获取课程班级列表
	 * @param teacherId
	 * @return
	 */
	public List<String> getCourseClassIdsByTeacherId(String unitId,String acadyear,String semester,String teacherId);
	
	/**
	 * 根据场地id获取考试信息
	 * @param unitId TODO
	 * @param teachPlaceId
	 * @return
	 */
	public JSONObject getExamInfoByTeachPlaceId(String unitId, String teachPlaceId);
	
	/**
	 * 根据examSectId获取考试信息
	 * @param examId
	 * @return
	 */
	public Map<String, JSONObject> getExamInfoBySectId(String[] examSectIds);
	
	/**
	 * 根据提交人获取成绩班级列表
	 * @param teacherId
	 * @return
	 */
	public List<String> getAchiClassIdsByTeacherId(String unitId,String acadyear,String semester,String teacherId);
	
	/**
	 * 学生成绩登记表--镇海定制
	 * @param unitId 
	 * @param classId 
	 * @param studentId 
	 */
	public String getAchiStudent(String unitId, String classId, String studentId,String exportType);
	
	/**
	 * 学生奖惩
	 * @param unitId 不能为空
	 * @param acadyear
	 * @param semester
	 * @param stuIds
	 * @param isSanliandan 是否需要取三联单的奖励和处分
	 * @param isIncluded 是否只取计入学籍的奖励
	 * @return key studentId
	 */
	public Map<String,String> getstuworkMap(String unitId,String acadyear,String semester,String[] stuIds, boolean isSanliandan, boolean isIncluded);
	
	/**
	 * 根据unitId获取宿舍楼
	 * @param unitId
	 * @return
	 */
	public JSONArray getDormBuildingsByUnitId(String unitId);
	
	/**
	 * 根据宿舍楼id和房间类型获取stuwork_dorm_room列表
	 * @param buildingId
	 * @param roomType
	 * @param unitId 
	 * @return
	 */
	public JSONArray getStuworkDormRoomByBulidIdTpye(
			String buildingId, String roomType, String unitId);
	
	/**
	 * 根据学年、学期、宿舍id获取床位列表
	 * @param acadyear
	 * @param semester
	 * @param roomId
	 * @return
	 */
	public JSONArray getDormRoomBedList(String acadyear, String semester, String roomId);
	
	/**
	 * 获取课程
	 * @param unitId
	 * @param acadyear
	 * @param semester
	 * @param teacherId
	 * @param studyType
	 * @return
	 */
	public List<EduadmCourseDto> getEduadmCourse(String unitId, String acadyear, String semester, String teacherId, String studyType);
	
	/**
	 * 获取学生选修课安排
	 * @param unitId
	 * @param acadyear
	 * @param semester
	 * @param clsKind
	 * @param studentIds
	 * @param page TODO
	 * @return
	 */
	public List<EduadmTeachClassStuDto> getEduadmTeachClassStu(String unitId, String acadyear, String semester, int clsKind, String[] studentIds, Pagination page);
	
	/**
	 * 根据id获取课程
	 * @param id
	 * @return
	 */
	public EduadmCourseDto getEduadmCourseById(String id);
	
	/**
	 * 获取教学班的名字
	 * @param id
	 * @return
	 */
	public String getEduadmTeachClassName(String id);
	
	/**
	 * 根据教学班id获取学生id列表
	 * @param courseId
	 * @return
	 */
	public List<Student> getStudentByTeachClassId(String classId);
	
	
	//====================================排课子系统调用begin======================================================
	/**
	 * 获取课程
	 * @param id 主键id
	 * @return
	 */
	public EduadmSubjectDto getEduadmSubjectById(String id);
	
	/**
	 * 通过id获取
	 * 
	 * @param id
	 * @return
	 */
	public EduadmTeachClassDto getEduadmTeachClassById(String id);
	public Map<String, EduadmTeachClassDto> getTeachClassListByIds(String[] ids);
	
	/**
	 * 根据条件获取教学班list
	 * 
	 * @param unitId
	 *            必填
	 * @param acadyear
	 *            可为空
	 * @param semester
	 *            可为空
	 * @param classType
	 *            可为空
	 * @param teacherId
	 *            可为空
	 * @param studentId TODO
	 * @param page
	 *            可为空
	 * @return
	 */
	public List<EduadmTeachClassDto> getTeachClassList(String unitId,
			String acadyear, String semester, String classType,
			String teacherId, String studentId, Pagination page);
	
	/**
	 * 学期课程安排 列表
	 * 
	 * @param unitId
	 * @param acadyear
	 * @param semester
	 * @param page
	 * @return
	 */
	public List<EduadmCourseDto> getAllCourseList(String unitId, String acadyear, String semester, String studyType);
	
	public List<EduadmSubjectDto> getEduadmSubjectListByUnitId(String unitId);

	public Map<String, EduadmSubjectDto> getMapByUnitId(String unitId,
			boolean b, boolean c);

	public List<EduadmCourseDto> getCourseListBySubjectId(String unitId, String acadyear, String semester, String classId, String subjectId,
			String studyType, boolean isComputerClass, Pagination page);

	public EduadmCourseDto getCourseById(String courseId);

	public Map<String, EduadmCourseDto> getCourseMapByClassId(String unitId,
			String acadyear, String string, String classId);

	public Map<String, EduadmCourseDto> getCourseByIds(String[] array);

	public List<EduadmCourseDto> queryAllEduadmCourse(String unitId,
			String acadyear, String semester, String studyType, String examType, Pagination page);

	public Map<String, EduadmCourseDto> queryCourseMapByName(String unitId,
			String acadyear, String semester, String[] array);

	public List<String> queryCourse(String unitId, String acadyear,
			String semester);

	public Map<String, String> queryTeachers(String unitId, String acadyear,
			String semester);

	public Map<String, List<EduadmCourseTeacherDto>> getEduadmCourseTeacherMap(
			String[] array);
	
}
