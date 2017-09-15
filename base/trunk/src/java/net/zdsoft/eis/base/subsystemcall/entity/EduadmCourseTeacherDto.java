package net.zdsoft.eis.base.subsystemcall.entity; 

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @description: 学期课程教师安排
 * @date: 2014-4-11
 */
public class EduadmCourseTeacherDto extends BaseEntity{

	private static final long serialVersionUID = -8551393654327896L;
	
	/**
	 * 课程id
	 */
	private String courseId;
	/**
	 * 教师id
	 */
	private String teacherId;
	/**
	 * 教师类型
	 */
	private int teacherType;
	
//	除了数据库以外的字段
	/**
	 * 教师姓名
	 */
	private String teacherName;
	private String courseName;
	private String classId;
	private String className;
	private double weekPeriod;//教师周课时
	private int classStuNum;//班级学生人数
	private String studyType;
	private int indexNumber;
	private String isNew; //是否新开
	
	private String teacherIds;//次要教师ids,以,隔开
	private String teacherNames;//次要教师names,以,隔开
	
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	public int getIndexNumber() {
		return indexNumber;
	}
	public void setIndexNumber(int indexNumber) {
		this.indexNumber = indexNumber;
	}
	public String getStudyType() {
		return studyType;
	}
	public void setStudyType(String studyType) {
		this.studyType = studyType;
	}
	public double getWeekPeriod() {
		return weekPeriod;
	}
	public void setWeekPeriod(double weekPeriod) {
		this.weekPeriod = weekPeriod;
	}
	public int getClassStuNum() {
		return classStuNum;
	}
	public void setClassStuNum(int classStuNum) {
		this.classStuNum = classStuNum;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public int getTeacherType() {
		return teacherType;
	}
	public void setTeacherType(int teacherType) {
		this.teacherType = teacherType;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherIds() {
		return teacherIds;
	}
	public void setTeacherIds(String teacherIds) {
		this.teacherIds = teacherIds;
	}
	public String getTeacherNames() {
		return teacherNames;
	}
	public void setTeacherNames(String teacherNames) {
		this.teacherNames = teacherNames;
	}
	
	

}
