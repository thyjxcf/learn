package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
/**
 * office_time_set
 * @author 
 * 
 */
public class OfficeTimeSet implements Serializable{
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
	 * 
	 */
	private Integer timeInterval;
	
	private String noonStartTime;//中午开始时间
	private String noonEndTime;//中午结束时间
	
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
	/**
	 * 设置
	 */
	public void setTimeInterval(Integer timeInterval){
		this.timeInterval = timeInterval;
	}
	/**
	 * 获取
	 */
	public Integer getTimeInterval(){
		return this.timeInterval;
	}
	public String getNoonStartTime() {
		return noonStartTime;
	}
	public void setNoonStartTime(String noonStartTime) {
		this.noonStartTime = noonStartTime;
	}
	public String getNoonEndTime() {
		return noonEndTime;
	}
	public void setNoonEndTime(String noonEndTime) {
		this.noonEndTime = noonEndTime;
	}
	
}