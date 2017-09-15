package net.zdsoft.office.dutyweekly.entity;

import java.io.Serializable;
/**
 * office_duty_project
 * @author 
 * 
 */
public class OfficeDutyProject implements Serializable{
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
	 * 
	 */
	private String projectName;
	
	private String year;//学年
	private Integer semester;

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
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}
	/**
	 * 获取
	 */
	public String getProjectName(){
		return this.projectName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Integer getSemester() {
		return semester;
	}
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
}