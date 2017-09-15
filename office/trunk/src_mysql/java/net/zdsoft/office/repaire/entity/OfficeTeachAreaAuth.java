package net.zdsoft.office.repaire.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_teach_area_auth
 * @author 
 * 
 */
public class OfficeTeachAreaAuth implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String teachAreaId;
	/**
	 * 
	 */
	private String userId;
	/**
	 * 
	 */
	private String state;
	/**
	 * 
	 */
	private Date createTime;

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
	public void setTeachAreaId(String teachAreaId){
		this.teachAreaId = teachAreaId;
	}
	/**
	 * 获取
	 */
	public String getTeachAreaId(){
		return this.teachAreaId;
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
	/**
	 * 设置
	 */
	public void setState(String state){
		this.state = state;
	}
	/**
	 * 获取
	 */
	public String getState(){
		return this.state;
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
}