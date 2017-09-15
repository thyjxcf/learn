package net.zdsoft.office.dutyinformation.entity;

import java.io.Serializable;
/**
 * office_patrol_place
 * @author 
 * 
 */
public class OfficePatrolPlace implements Serializable{
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
	private String placeName;

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
	public void setPlaceName(String placeName){
		this.placeName = placeName;
	}
	/**
	 * 获取
	 */
	public String getPlaceName(){
		return this.placeName;
	}
}