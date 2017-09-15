package net.zdsoft.eis.base.data.entity;


import java.io.Serializable;
/**
 * stusys_section_time_set
 * @author 
 * 
 */
public class StusysSectionTimeSet implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String acadyear;
	/**
	 * 
	 */
	private Integer semester;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private Integer sectionNumber;
	/**
	 * 
	 */
	private String beginTime;
	/**
	 * 
	 */
	private String endTime;
	/**
	 * 
	 */
	private String userId;

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
	public void setAcadyear(String acadyear){
		this.acadyear = acadyear;
	}
	/**
	 * 获取
	 */
	public String getAcadyear(){
		return this.acadyear;
	}
	/**
	 * 设置
	 */
	public void setSemester(Integer semester){
		this.semester = semester;
	}
	/**
	 * 获取
	 */
	public Integer getSemester(){
		return this.semester;
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
	public void setSectionNumber(Integer sectionNumber){
		this.sectionNumber = sectionNumber;
	}
	/**
	 * 获取
	 */
	public Integer getSectionNumber(){
		return this.sectionNumber;
	}
	/**
	 * 设置
	 */
	public void setBeginTime(String beginTime){
		this.beginTime = beginTime;
	}
	/**
	 * 获取
	 */
	public String getBeginTime(){
		return this.beginTime;
	}
	/**
	 * 设置
	 */
	public void setEndTime(String endTime){
		this.endTime = endTime;
	}
	/**
	 * 获取
	 */
	public String getEndTime(){
		return this.endTime;
	}
	/**
	 * 设置
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 * 获取
	 */
	public String getUserId(){
		return this.userId;
	}
}