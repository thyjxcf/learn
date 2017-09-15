package net.zdsoft.office.dutyweekly.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_duty_weekly
 * @author 
 * 
 */
public class OfficeDutyWeekly implements Serializable{
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
	private String createUserId;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Integer week;
	private String weekStr;
	private Integer state;
	/**
	 * 
	 */
	private Date weekStartTime;
	/**
	 * 
	 */
	private Date weekEndTime;
	/**
	 * 
	 */
	private String dutyClass;
	private String dutyClassName;
	private String dutyGrade;
	/**
	 * 
	 */
	private String dutyTeacher;
	
	private String dutyTeacherNames;
	
	private boolean canEdit;
	
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
	public void setCreateUserId(String createUserId){
		this.createUserId = createUserId;
	}
	/**
	 * 获取
	 */
	public String getCreateUserId(){
		return this.createUserId;
	}
	/**
	 * 设置
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 * 获取
	 */
	public Date getCreateTime(){
		return this.createTime;
	}
	/**
	 * 设置
	 */
	public void setWeek(Integer week){
		this.week = week;
	}
	/**
	 * 获取
	 */
	public Integer getWeek(){
		return this.week;
	}
	/**
	 * 设置
	 */
	public void setWeekStartTime(Date weekStartTime){
		this.weekStartTime = weekStartTime;
	}
	/**
	 * 获取
	 */
	public Date getWeekStartTime(){
		return this.weekStartTime;
	}
	/**
	 * 设置
	 */
	public void setWeekEndTime(Date weekEndTime){
		this.weekEndTime = weekEndTime;
	}
	/**
	 * 获取
	 */
	public Date getWeekEndTime(){
		return this.weekEndTime;
	}
	/**
	 * 设置
	 */
	public void setDutyClass(String dutyClass){
		this.dutyClass = dutyClass;
	}
	/**
	 * 获取
	 */
	public String getDutyClass(){
		return this.dutyClass;
	}
	/**
	 * 设置
	 */
	public void setDutyTeacher(String dutyTeacher){
		this.dutyTeacher = dutyTeacher;
	}
	/**
	 * 获取
	 */
	public String getDutyTeacher(){
		return this.dutyTeacher;
	}
	public String getWeekStr() {
		return weekStr;
	}
	public void setWeekStr(String weekStr) {
		this.weekStr = weekStr;
	}
	public String getDutyTeacherNames() {
		return dutyTeacherNames;
	}
	public void setDutyTeacherNames(String dutyTeacherNames) {
		this.dutyTeacherNames = dutyTeacherNames;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
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
	public String getDutyGrade() {
		return dutyGrade;
	}
	public void setDutyGrade(String dutyGrade) {
		this.dutyGrade = dutyGrade;
	}
	public String getDutyClassName() {
		return dutyClassName;
	}
	public void setDutyClassName(String dutyClassName) {
		this.dutyClassName = dutyClassName;
	}
}