package net.zdsoft.office.dutyweekly.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_weekly_apply
 * @author 
 * 
 */
public class OfficeWeeklyApply implements Serializable{
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
	private String dutyWeeklyId;
	private String week;
	/**
	 * 
	 */
	private String dutyProjectId;
	private String dutyRemarkId;//备注Id
	private Date dutyDate;
	/**
	 * 
	 */
	private String classId;
	/**
	 * 
	 */
	private Integer score;

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
	public void setDutyWeeklyId(String dutyWeeklyId){
		this.dutyWeeklyId = dutyWeeklyId;
	}
	/**
	 * 获取
	 */
	public String getDutyWeeklyId(){
		return this.dutyWeeklyId;
	}
	/**
	 * 设置
	 */
	public void setDutyProjectId(String dutyProjectId){
		this.dutyProjectId = dutyProjectId;
	}
	/**
	 * 获取
	 */
	public String getDutyProjectId(){
		return this.dutyProjectId;
	}
	/**
	 * 设置
	 */
	public void setClassId(String classId){
		this.classId = classId;
	}
	/**
	 * 获取
	 */
	public String getClassId(){
		return this.classId;
	}
	/**
	 * 设置
	 */
	public void setScore(Integer score){
		this.score = score;
	}
	/**
	 * 获取
	 */
	public Integer getScore(){
		return this.score;
	}
	public Date getDutyDate() {
		return dutyDate;
	}
	public void setDutyDate(Date dutyDate) {
		this.dutyDate = dutyDate;
	}
	public String getDutyRemarkId() {
		return dutyRemarkId;
	}
	public void setDutyRemarkId(String dutyRemarkId) {
		this.dutyRemarkId = dutyRemarkId;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	
}