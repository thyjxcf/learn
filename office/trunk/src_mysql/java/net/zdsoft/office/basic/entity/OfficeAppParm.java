package net.zdsoft.office.basic.entity;

import net.zdsoft.eis.frame.client.BaseEntity;
/**
 * office_app_parm(APP权限设置)
 * @author 
 * 
 */
public class OfficeAppParm extends BaseEntity{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private Boolean isUsing;
	/**
	 * 
	 */
	private String type;
	
	//辅助字段
	private String moduleName;//app模块名称
	

	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
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
	public void setIsUsing(Boolean isUsing){
		this.isUsing = isUsing;
	}
	/**
	 * 获取
	 */
	public Boolean getIsUsing(){
		return this.isUsing;
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
}