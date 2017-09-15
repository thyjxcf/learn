package net.zdsoft.office.goodmanage.entity;

import java.io.Serializable;
/**
 * office_goods_type
 * @author 
 * 
 */
public class OfficeGoodsType implements Serializable{
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
	private String typeId;
	/**
	 * 
	 */
	private String typeName;
	
	private Boolean isExist;

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
	public void setTypeId(String typeId){
		this.typeId = typeId;
	}
	/**
	 * 获取
	 */
	public String getTypeId(){
		return this.typeId;
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
	public Boolean getIsExist() {
		return isExist;
	}
	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}
	
}