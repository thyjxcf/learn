/* 
 * @(#)ExamCourseSchedule.java    Created on Jul 25, 2007
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 */
package net.zdsoft.eis.base.subsystemcall.entity;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.entity.Timetable;

/**
 * 课程表Entity
 */
public class CourseScheduleDto extends Timetable {

	private static final long serialVersionUID = 7955321249471721472L;
	
	public static final int SUBJECT_TYPE_EXOT = 1; // 理论课
	public static final int SUBJECT_TYPE_FUL = 2; // 实践课
	public static final int WEEK_TYPE_NORMAL = 3; //正常
	public static final int WEEK_TYPE_ODD = 1; //单周
	public static final int WEEK_TYPE_EVEN = 2; //双周
	public static final int CLASS_TYPE_TEACH = 2; //教学班
	public static final int CLASS_TYPE_NORMAL = 1; //正常行政班

	//数据库外，附加字段
	private String courseName; // 课程名称
	private String teacherName; // 教师姓名
	private String teacherIds; //教师id 多个教师的话id已"-"间隔组成字符串
	private String className; // 班级名称
	private String placeName;
	
	private String kindName;//单双周名字(正常／单周／双周)
	
	private Grade grade; // 年级
	private String pageId;//subjectId,teacherId,kind组合
	private String subjectTypeName;
	private String shortSubjectName;
	private List<Map<String, String>> subjectClassList; // 课程及其班级列表
	
	private int myCourse; //0 不是登陆教师自己的课程  1是登陆教师自己的课程
	
	private boolean isHaveReplace;//是否有代课记录
	private String auditStatus;
	
	private String modiyType;//修改类型
	
	private String teacherNames;//辅助教师
	private List<String> teacherExIds;//辅助教师id list
	private String teacherExIdStr;//辅助教师id str
	
	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public boolean isHaveReplace() {
		return isHaveReplace;
	}

	public void setHaveReplace(boolean isHaveReplace) {
		this.isHaveReplace = isHaveReplace;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public List<Map<String, String>> getSubjectClassList() {
		return subjectClassList;
	}

	public void setSubjectClassList(List<Map<String, String>> subjectClassList) {
		this.subjectClassList = subjectClassList;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getSubjectTypeName() {
		return subjectTypeName;
	}

	public void setSubjectTypeName(String subjectTypeName) {
		this.subjectTypeName = subjectTypeName;
	}

	public String getShortSubjectName() {
		return shortSubjectName;
	}

	public void setShortSubjectName(String shortSubjectName) {
		this.shortSubjectName = shortSubjectName;
	}
	
	public String getTeacherIds() {
		return teacherIds;
	}

	public void setTeacherIds(String teacherIds) {
		this.teacherIds = teacherIds;
	}

	/**
	 * 获取myCourse
	 * @return myCourse
	 */
	public int getMyCourse() {
	    return myCourse;
	}

	/**
	 * 设置myCourse
	 * @param myCourse myCourse
	 */
	public void setMyCourse(int myCourse) {
	    this.myCourse = myCourse;
	}

	/**
	 * 获取modiyType
	 * @return modiyType
	 */
	public String getModiyType() {
	    return modiyType;
	}

	/**
	 * 设置modiyType
	 * @param modiyType modiyType
	 */
	public void setModiyType(String modiyType) {
	    this.modiyType = modiyType;
	}

	/**
	 * 获取teacherNames
	 * @return teacherNames
	 */
	public String getTeacherNames() {
	    return teacherNames;
	}

	/**
	 * 设置teacherNames
	 * @param teacherNames teacherNames
	 */
	public void setTeacherNames(String teacherNames) {
	    this.teacherNames = teacherNames;
	}

	/**
	 * 获取teacherExIds
	 * @return teacherExIds
	 */
	public List<String> getTeacherExIds() {
	    return teacherExIds;
	}

	/**
	 * 设置teacherExIds
	 * @param teacherExIds teacherExIds
	 */
	public void setTeacherExIds(List<String> teacherExIds) {
	    this.teacherExIds = teacherExIds;
	}

	/**
	 * 获取teacherExIdStr
	 * @return teacherExIdStr
	 */
	public String getTeacherExIdStr() {
	    return teacherExIdStr;
	}

	/**
	 * 设置teacherExIdStr
	 * @param teacherExIdStr teacherExIdStr
	 */
	public void setTeacherExIdStr(String teacherExIdStr) {
	    this.teacherExIdStr = teacherExIdStr;
	}
	
	
}