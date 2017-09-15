package net.zdsoft.office.bulletin.entity;

import java.io.Serializable;
/**
 * office_bulletin_read
 * @author 
 * 
 */
public class OfficeBulletinRead implements Serializable{
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
	private String bulletinId;
	/**
	 * 
	 */
	private String bulletinType;
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
	public void setBulletinId(String bulletinId){
		this.bulletinId = bulletinId;
	}
	/**
	 * 获取
	 */
	public String getBulletinId(){
		return this.bulletinId;
	}
	/**
	 * 设置
	 */
	public void setBulletinType(String bulletinType){
		this.bulletinType = bulletinType;
	}
	/**
	 * 获取
	 */
	public String getBulletinType(){
		return this.bulletinType;
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