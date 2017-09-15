package net.zdsoft.office.repaire.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_type_auth
 * @author 
 * 
 */
public class OfficeTypeAuth implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private String userId;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String state;
	private String userName;
	private String unitId;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
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
	public void setType(String type){
		this.type = type;
	}
	/**
	 * 获取
	 */
	public String getType(){
		return this.type;
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
	public void setState(String state){
		this.state = state;
	}
	/**
	 * 获取
	 */
	public String getState(){
		return this.state;
	}
    public String getUnitId() {
        return unitId;
    }
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}