package net.zdsoft.office.schedule.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_calendar_semester
 * @author 
 * 
 */
public class OfficeCalendarSemester implements Serializable{
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
	private int semester;
	/**
	 * 
	 */
	private Date beginDate;
	/**
	 * 
	 */
	private Date endDate;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private int calyear;

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
	public void setSemester(int semester){
		this.semester = semester;
	}
	/**
	 * 获取
	 */
	public int getSemester(){
		return this.semester;
	}
	/**
	 * 设置
	 */
	public void setBeginDate(Date beginDate){
		this.beginDate = beginDate;
	}
	/**
	 * 获取
	 */
	public Date getBeginDate(){
		return this.beginDate;
	}
	/**
	 * 设置
	 */
	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}
	/**
	 * 获取
	 */
	public Date getEndDate(){
		return this.endDate;
	}
	/**
	 * 设置
	 */
	public void setContent(String content){
		this.content = content;
	}
	/**
	 * 获取
	 */
	public String getContent(){
		return this.content;
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
	public void setCalyear(int calyear){
		this.calyear = calyear;
	}
	/**
	 * 获取
	 */
	public int getCalyear(){
		return this.calyear;
	}
}