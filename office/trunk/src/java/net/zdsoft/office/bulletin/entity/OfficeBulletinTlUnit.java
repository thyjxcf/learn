package net.zdsoft.office.bulletin.entity;

import java.io.Serializable;
/**
 * office_bulletin_tl_unit
 * @author 
 * 
 */
public class OfficeBulletinTlUnit implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String bulletinId;
	/**
	 * 
	 */
	private String receiveUnitId;

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
	public void setReceiveUnitId(String receiveUnitId){
		this.receiveUnitId = receiveUnitId;
	}
	/**
	 * 获取
	 */
	public String getReceiveUnitId(){
		return this.receiveUnitId;
	}
}