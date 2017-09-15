package net.zdsoft.office.asset.entity;

import java.io.Serializable;
/**
 * 采购意见信息表
 * @author 
 * 
 */
public class OfficeAssetPurchaseOpinion implements Serializable{
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
	private String content;
	/**
	 * 1：通过的意见，2：不通过的意见
	 */
	private String type;

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
	public void setContent(String content){
		this.content = content;
	}
	/**
	 * 获取
	 */
	public String getContent(){
		return this.content;
	}
	/**
	 * 设置1：通过的意见，2：不通过的意见
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 * 获取1：通过的意见，2：不通过的意见
	 */
	public String getType(){
		return this.type;
	}
}