package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
/**
 * office_car_subsidy
 * @author 
 * 
 */
public class OfficeCarSubsidy implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	private String unitId;
	/**
	 * 
	 */
	private String scope;
	/**
	 * 
	 */
	private float subsidy;
	/**
	 * 
	 */
	private boolean isDetele;

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
	public void setScope(String scope){
		this.scope = scope;
	}
	/**
	 * 获取
	 */
	public String getScope(){
		return this.scope;
	}
	/**
	 * 设置
	 */
	public void setSubsidy(float subsidy){
		this.subsidy = subsidy;
	}
	/**
	 * 获取
	 */
	public float getSubsidy(){
		return this.subsidy;
	}
	/**
	 * 设置
	 */
	public void setIsDetele(boolean isDetele){
		this.isDetele = isDetele;
	}
	/**
	 * 获取
	 */
	public boolean getIsDetele(){
		return this.isDetele;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
}