package net.zdsoft.eisu.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;
/**
 * @todo 楼层信息
 * @author wanghb
 * @date 2015-5-5
 */

public class TeachBuilding extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String unitId;
	
	private String teachAreaId;  // 所属教学区
	
	private String buildingName;  
	
	private int floorCount;  //楼层数
	
	private String buildingType;   //楼层类型
	
	/**
	 * 0 正常，1 删除
	 */
	private Boolean isDeleted;

	//辅助字段
	private String teachAreaName; 
	private String buildingTypeStr;  
	
	public String getTeachAreaName() {
		return teachAreaName;
	}

	public void setTeachAreaName(String teachAreaName) {
		this.teachAreaName = teachAreaName;
	}

	public String getBuildingTypeStr() {
		return buildingTypeStr;
	}

	public void setBuildingTypeStr(String buildingTypeStr) {
		this.buildingTypeStr = buildingTypeStr;
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
	 public void setBuildingName(String buildingName){
	 	this.buildingName = buildingName;
	 }
	 
	 /**
	  * 获取
	  */
	 public String getBuildingName(){
	 	return this.buildingName;
	 }
	/**
	 * 设置
	 */
	 public void setFloorCount(Integer floorCount){
	 	this.floorCount = floorCount;
	 }
	 
	 /**
	  * 获取
	  */
	 public Integer getFloorCount(){
	 	return this.floorCount;
	 }
	/**
	 * 设置
	 */
	 public void setBuildingType(String buildingType){
	 	this.buildingType = buildingType;
	 }
	 
	 /**
	  * 获取
	  */
	 public String getBuildingType(){
	 	return this.buildingType;
	 }
	/**
	 * 设置0 正常，1 删除
	 */
	 public void setIsDeleted(Boolean isDeleted){
	 	this.isDeleted = isDeleted;
	 }
	 
	 /**
	  * 获取0 正常，1 删除
	  */
	 public Boolean getIsDeleted(){
	 	return this.isDeleted;
	 }

}
