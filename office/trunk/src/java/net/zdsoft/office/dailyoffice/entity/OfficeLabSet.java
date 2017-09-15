package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_lab_set
 * @author 
 * 
 */
public class OfficeLabSet implements Serializable{
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
	 * 实验名称
	 */
	private String name;
	/**
	 * 教材页面
	 */
	private String courseBook;
	/**
	 * 所需仪器
	 */
	private String apparatus;
	/**
	 * 所需药品
	 */
	private String reagent;
	/**
	 * 学科
	 */
	private String subject;
	/**
	 * 
	 */
	private Date createTime;
	
	/**
	 * 年级
	 */
	private String grade;

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
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 获取
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * 设置
	 */
	public void setCourseBook(String courseBook){
		this.courseBook = courseBook;
	}
	/**
	 * 获取
	 */
	public String getCourseBook(){
		return this.courseBook;
	}
	/**
	 * 设置
	 */
	public void setApparatus(String apparatus){
		this.apparatus = apparatus;
	}
	/**
	 * 获取
	 */
	public String getApparatus(){
		return this.apparatus;
	}
	/**
	 * 设置
	 */
	public void setReagent(String reagent){
		this.reagent = reagent;
	}
	/**
	 * 获取
	 */
	public String getReagent(){
		return this.reagent;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
}