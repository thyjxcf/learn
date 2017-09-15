package net.zdsoft.office.repaire.entity;

import java.io.Serializable;
/**
 * office_repaire_type
 * @author 
 * 
 */
public class OfficeRepaireType implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String thisId;
	/**
	 * 
	 */
	private String typeName;
	/**
	 * 
	 */
	private String unitId;
	
	private boolean isDeleted;//是否已删除
	private String userIds;//用户ids
	private String userNames;//用户names
	

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
	public void setThisId(String thisId){
		this.thisId = thisId;
	}
	/**
	 * 获取
	 */
	public String getThisId(){
		return this.thisId;
	}
	/**
	 * 设置
	 */
	public void setTypeName(String typeName){
		this.typeName = typeName;
	}
	/**
	 * 获取
	 */
	public String getTypeName(){
		return this.typeName;
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
	public boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	
}