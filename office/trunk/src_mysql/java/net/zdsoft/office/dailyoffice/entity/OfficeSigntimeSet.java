package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
/**
 * office_signtime_set
 * @author 
 * 
 */
public class OfficeSigntimeSet implements Serializable{
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
	private String startTime;
	/**
	 * 
	 */
	private String endTime;

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
	public void setStartTime(String startTime){
		this.startTime = startTime;
	}
	/**
	 * 获取
	 */
	public String getStartTime(){
		return this.startTime;
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
}