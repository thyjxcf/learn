package net.zdsoft.eis.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class Timetable extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//timetable_teach_plan_ex.type=2
	public static final int TEACHER_EX = 2; //扩展教师存放类型
	public static final int CLASSTYPE = 4; //学生选修课
	
	private String schoolId; // 学校Id
	private String classId; // 班级Id
	private int dayOfWeek; // 星期一:0; 星期二:1;以此类推
	private int period; // 节课
	private String subjectId; // 课程Id
	private String teacherId; // 教师Id
	private String acadyear; // 学年
	private int semester; // 学期
	private int weekType;// 单双周
	private int classType;//班级类型
	private int weekOfWorktime; //开学时间的周次
	private String placeId;
	private int subjectType;
	private String subjectName;
	
	private String courseId;//学期课程安排id 冗余字段
	private String computerlabId; // 机房id
	private String timeInterval; //  时段
	private String periodInterval;//早上，上午，下午，晚上
	private int punchCard;//是否需要一卡通打卡
	
	
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getAcadyear() {
		return acadyear;
	}
	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public int getWeekType() {
		return weekType;
	}
	public void setWeekType(int weekType) {
		this.weekType = weekType;
	}
	public int getClassType() {
		return classType;
	}
	public void setClassType(int classType) {
		this.classType = classType;
	}
	public int getWeekOfWorktime() {
		return weekOfWorktime;
	}
	public void setWeekOfWorktime(int weekOfWorktime) {
		this.weekOfWorktime = weekOfWorktime;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public int getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(int subjectType) {
		this.subjectType = subjectType;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getComputerlabId() {
		return computerlabId;
	}
	public void setComputerlabId(String computerlabId) {
		this.computerlabId = computerlabId;
	}
	public String getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}
	public String getPeriodInterval() {
		return periodInterval;
	}
	public void setPeriodInterval(String periodInterval) {
		this.periodInterval = periodInterval;
	}
	public int getPunchCard() {
		return punchCard;
	}
	public void setPunchCard(int punchCard) {
		this.punchCard = punchCard;
	}
	
	
}
