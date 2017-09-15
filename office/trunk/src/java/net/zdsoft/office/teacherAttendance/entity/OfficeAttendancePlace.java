package net.zdsoft.office.teacherAttendance.entity;


import java.io.Serializable;
import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;
/**
 * 办公地点
 * @author 
 * 
 */
public class OfficeAttendancePlace extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String unitId;
	/**
	 * 办公地点名称(自定义)
	 */
	private String name;
	/**
	 * 办公地点名称（高德返回的名称）
	 */
	private String mapName;
	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 纬度
	 */
	private String latitude;
	/**
	 * 范围（米）
	 */
	private Integer range;
	

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
	public void setMapName(String mapName){
		this.mapName = mapName;
	}
	/**
	 * 获取
	 */
	public String getMapName(){
		return this.mapName;
	}
	/**
	 * 设置
	 */
	public void setLatitude(String latitude){
		this.latitude = latitude;
	}
	/**
	 * 获取
	 */
	public String getLatitude(){
		return this.latitude;
	}
	/**
	 * 设置
	 */
	public void setLongitude(String longitude){
		this.longitude = longitude;
	}
	/**
	 * 获取
	 */
	public String getLongitude(){
		return this.longitude;
	}
	/**
	 * 设置
	 */
	public void setRange(Integer range){
		this.range = range;
	}
	/**
	 * 获取
	 */
	public Integer getRange(){
		return this.range;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}