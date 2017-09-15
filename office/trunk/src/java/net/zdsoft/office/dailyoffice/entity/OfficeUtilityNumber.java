package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
/**
 * office_utility_number
 * @author 
 * 
 */
public class OfficeUtilityNumber implements Serializable{
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
	private String utilityId;
	/**
	 * 
	 */
	private String numberId;

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
	public void setUtilityId(String utilityId){
		this.utilityId = utilityId;
	}
	/**
	 * 获取
	 */
	public String getUtilityId(){
		return this.utilityId;
	}
	/**
	 * 设置
	 */
	public void setNumberId(String numberId){
		this.numberId = numberId;
	}
	/**
	 * 获取
	 */
	public String getNumberId(){
		return this.numberId;
	}
}