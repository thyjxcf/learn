package net.zdsoft.eis.base.subsystemcall.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class EduadmTeachClassDto extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3433914813445373452L;
	
	private String unitId;

	private String acadyear;

	private String semester;

	private String clsName;//班级名称
	
	/**
	 * 教学班类型:1选修课教学班 2实习班 3高考班
	 */
	private String clsKind;//班级类型 -- 微代码 DM-JXBLX

	private Integer clsMax;//班级容量

	private String courseId;//课程id

	private String placeId;//场地id

	private String teacherId;//班主任
	
	private String specialtyId;//专业
	
	
	/**
	 *学生对象类型--- 1,男 2.女  1,2 男女不限
	 */
	private String stuObjType;
	
	/**
	 * 年级
	 */
	private String openAcadYear;
	
	
	//辅助字段
	private String teacherName;
	
	private String placeName;//场地名称
	
	private int clsStuCount; //当前班级已有人数
	
	//add by chens,实习信息采集
	private String date;//填表日期
	private int internShipNum;//实习人数
	private int skillNum;//技能+学历人数
	
	private String viceTeacherId;
	
	public String getViceTeacherId() {
		return viceTeacherId;
	}

	public void setViceTeacherId(String viceTeacherId) {
		this.viceTeacherId = viceTeacherId;
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

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getClsName() {
		return clsName;
	}

	public void setClsName(String clsName) {
		this.clsName = clsName;
	}

	public String getClsKind() {
		return clsKind;
	}

	public void setClsKind(String clsKind) {
		this.clsKind = clsKind;
	}

	public Integer getClsMax() {
		return clsMax;
	}

	public void setClsMax(Integer clsMax) {
		this.clsMax = clsMax;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public int getClsStuCount() {
		return clsStuCount;
	}

	public void setClsStuCount(int clsStuCount) {
		this.clsStuCount = clsStuCount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getInternShipNum() {
		return internShipNum;
	}

	public void setInternShipNum(int internShipNum) {
		this.internShipNum = internShipNum;
	}

	public int getSkillNum() {
		return skillNum;
	}

	public void setSkillNum(int skillNum) {
		this.skillNum = skillNum;
	}

	/**
	 * 获取specialtyId
	 * @return specialtyId
	 */
	public String getSpecialtyId() {
	    return specialtyId;
	}

	/**
	 * 设置specialtyId
	 * @param specialtyId specialtyId
	 */
	public void setSpecialtyId(String specialtyId) {
	    this.specialtyId = specialtyId;
	}

	public String getStuObjType() {
		return stuObjType;
	}

	public void setStuObjType(String stuObjType) {
		this.stuObjType = stuObjType;
	}

	public String getOpenAcadYear() {
		return openAcadYear;
	}

	public void setOpenAcadYear(String openAcadYear) {
		this.openAcadYear = openAcadYear;
	}

	
}
