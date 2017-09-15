package net.zdsoft.eis.base.subsystemcall.entity;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.frame.client.BaseEntity;

public class EduadmCourseDto extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String unitId;
	private String acadyear;
	private String semster;
	/**
	 * 校区
	 */
	private String areaId;
	/**
	 * 承担教研组
	 */
	private String groupId;
	/**
	 * 班级id
	 */
	private String classId;
	/**
	 * 修习类别
	 */
	private String studyType;
	/**
	 * 课程名称
	 */
	private String subjectName;
	/**
	 * 课程id
	 */
	private String subjectId;
	/**
	 * 课程类型id
	 */
	private String subjTypeId;
	/**
	 *学生对象类型
	 */
	private String stuObjType;
	/**
	 * 是否上机
	 */
	private boolean computerClass;
	/**
	 * 考核方式
	 */
	private String examMode;
	/**
	 * 分制类型
	 */
	private String scoreTypeId;
	/**
	 * 周课时
	 */
	private Float weekPeriod;
	/**
	 * 简介
	 */
	private String subjComment;
	/**
	 * 学分
	 */
	private Float credit;
	
	/**
	 * 考试方式
	 */
	private String examWay;
	
	/**
	 * 考核类别
	 */
	private String examType;
	
//	除数据库以外的额外字段
	private String isNew;//课程是新开
	
	public Map<String, List<EduadmCourseDto>> courseMap;
	
	private Map<String, Boolean> examTypeMap;
	
	/**
	 * 班级名称
	 */
	
	private String className;
	
	/**
	 * 班级人数
	 */
	
	private int stuCount;
	
	/**
	 * 总学分
	 */
	
	private Float sumScore; 
	/**
	 * 教师id
	 */
	private String teacherId;
	/**
	 * 任课老师姓名
	 * @return
	 */
	private String teacherName;
	/**
	 * 任课老师安排 主键id
	 */
	private String courseTeacherId;
	private boolean isUpdate;//true不能修改
	private String classAcadyear;//班级学年 排序
	private String teacherCode;//教师工号 排序
	private Attachment teachingPlan;//授课计划附件
	private int indexNumber;
	private String courseNo;//课程编号
	private String subjectSpecial;//备注（特色及优势）
	private String subjectPlan;//教学内容及安排
	private String recruitNum; //报名人数
	private String courseNameClassName;//课程名(班级名)
	
	public String getClassAcadyear() {
		return classAcadyear;
	}
	public void setClassAcadyear(String classAcadyear) {
		this.classAcadyear = classAcadyear;
	}
	public String getTeacherCode() {
		return teacherCode;
	}
	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}
	public boolean isUpdate() {
		return isUpdate;
	}
	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getAcadyear() {
		return acadyear;
	}
	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}
	public String getSemster() {
		return semster;
	}
	public void setSemster(String semster) {
		this.semster = semster;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getStudyType() {
		return studyType;
	}
	public void setStudyType(String studyType) {
		this.studyType = studyType;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjTypeId() {
		return subjTypeId;
	}
	public void setSubjTypeId(String subjTypeId) {
		this.subjTypeId = subjTypeId;
	}
	public String getStuObjType() {
		return stuObjType;
	}
	public void setStuObjType(String stuObjType) {
		this.stuObjType = stuObjType;
	}
	public boolean isComputerClass() {
		return computerClass;
	}
	public void setComputerClass(boolean computerClass) {
		this.computerClass = computerClass;
	}
	public String getExamMode() {
		return examMode;
	}
	public void setExamMode(String examMode) {
		this.examMode = examMode;
	}
	public String getScoreTypeId() {
		return scoreTypeId;
	}
	public void setScoreTypeId(String scoreTypeId) {
		this.scoreTypeId = scoreTypeId;
	}
	public Float getWeekPeriod() {
		return weekPeriod;
	}
	public void setWeekPeriod(Float weekPeriod) {
		this.weekPeriod = weekPeriod;
	}
	public String getSubjComment() {
		return subjComment;
	}
	public void setSubjComment(String subjComment) {
		this.subjComment = subjComment;
	}
	public Map<String, List<EduadmCourseDto>> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<String, List<EduadmCourseDto>> courseMap) {
		this.courseMap = courseMap;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getStuCount() {
		return stuCount;
	}
	public void setStuCount(int stuCount) {
		this.stuCount = stuCount;
	}
	public Float getSumScore() {
		return sumScore;
	}
	public void setSumScore(Float sumScore) {
		this.sumScore = sumScore;
	}
	public Float getCredit() {
		return credit;
	}
	public void setCredit(Float credit) {
		this.credit = credit;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getCourseTeacherId() {
		return courseTeacherId;
	}
	public void setCourseTeacherId(String courseTeacherId) {
		this.courseTeacherId = courseTeacherId;
	}
	public String getExamWay() {
		return examWay;
	}
	public void setExamWay(String examWay) {
		this.examWay = examWay;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public Map<String, Boolean> getExamTypeMap() {
		return examTypeMap;
	}
	public void setExamTypeMap(Map<String, Boolean> examTypeMap) {
		this.examTypeMap = examTypeMap;
	}
	public Attachment getTeachingPlan() {
		return teachingPlan;
	}
	public void setTeachingPlan(Attachment teachingPlan) {
		this.teachingPlan = teachingPlan;
	}
	/**
	 * 获取课程编号
	 * @return 课程编号
	 */
	public String getCourseNo() {
	    return courseNo;
	}
	/**
	 * 设置课程编号
	 * @param courseNo 课程编号
	 */
	public void setCourseNo(String courseNo) {
	    this.courseNo = courseNo;
	}
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
	public String getSubjectSpecial() {
		return subjectSpecial;
	}
	public void setSubjectSpecial(String subjectSpecial) {
		this.subjectSpecial = subjectSpecial;
	}
	public String getSubjectPlan() {
		return subjectPlan;
	}
	public void setSubjectPlan(String subjectPlan) {
		this.subjectPlan = subjectPlan;
	}
	public String getRecruitNum() {
		return recruitNum;
	}
	public void setRecruitNum(String recruitNum) {
		this.recruitNum = recruitNum;
	}
	public String getCourseNameClassName() {
		return courseNameClassName;
	}
	public void setCourseNameClassName(String courseNameClassName) {
		this.courseNameClassName = courseNameClassName;
	}
}
