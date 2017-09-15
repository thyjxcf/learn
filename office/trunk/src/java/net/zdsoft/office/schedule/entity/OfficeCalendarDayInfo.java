package net.zdsoft.office.schedule.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_calendar_day_info
 * @author 
 * 
 */
public class OfficeCalendarDayInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private Date restDate;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private String unitId;

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
	public void setRestDate(Date restDate){
		this.restDate = restDate;
	}
	/**
	 * 获取
	 */
	public Date getRestDate(){
		return this.restDate;
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
}