package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
/**
 * office_lab_info
 * @author 
 * 
 */
public class OfficeLabInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * office_lab_set表ID
	 */
	private String labSetId;
	/**
	 * 上课班级
	 */
	private String className;
	/**
	 * 学生人数
	 */
	private Integer studentNum;
	/**
	 * 任课教师
	 */
	private String teacherId;
	/**
	 * 实验形式：1-教师演示实验，2-学生操作实验
	 */
	private String labMode;
	/**
	 * 学科
	 */
	private String subject;
	
	//辅助字段
	private String teacherName;//任课教师
	
	/**
	 * 设置
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 * 获取
	 */
	public String getId(){
		return this.id;
	}
	/**
	 * 设置
	 */
	public void setUnitId(String unitId){
		this.unitId = unitId;
	}
	/**
	 * 获取
	 */
	public String getUnitId(){
		return this.unitId;
	}
	/**
	 * 设置
	 */
	public void setLabSetId(String labSetId){
		this.labSetId = labSetId;
	}
	/**
	 * 获取
	 */
	public String getLabSetId(){
		return this.labSetId;
	}
	/**
	 * 设置
	 */
	public void setClassName(String className){
		this.className = className;
	}
	/**
	 * 获取
	 */
	public String getClassName(){
		return this.className;
	}
	/**
	 * 设置
	 */
	public void setStudentNum(Integer studentNum){
		this.studentNum = studentNum;
	}
	/**
	 * 获取
	 */
	public Integer getStudentNum(){
		return this.studentNum;
	}
	/**
	 * 设置
	 */
	public void setTeacherId(String teacherId){
		this.teacherId = teacherId;
	}
	/**
	 * 获取
	 */
	public String getTeacherId(){
		return this.teacherId;
	}
	/**
	 * 设置
	 */
	public void setLabMode(String labMode){
		this.labMode = labMode;
	}
	/**
	 * 获取
	 */
	public String getLabMode(){
		return this.labMode;
	}
	/**
	 * 设置
	 */
	public void setSubject(String subject){
		this.subject = subject;
	}
	/**
	 * 获取
	 */
	public String getSubject(){
		return this.subject;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
}