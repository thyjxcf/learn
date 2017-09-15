package net.zdsoft.office.boardroom.entity;

import java.io.Serializable;
/**
 * office_apply_ex_xj
 * @author 
 * 
 */
public class OfficeApplyExXj implements Serializable{
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
	private String applyId;
	/**
	 * 
	 */
	private String detailsId;

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
	public void setApplyId(String applyId){
		this.applyId = applyId;
	}
	/**
	 * 获取
	 */
	public String getApplyId(){
		return this.applyId;
	}
	/**
	 * 设置
	 */
	public void setDetailsId(String detailsId){
		this.detailsId = detailsId;
	}
	/**
	 * 获取
	 */
	public String getDetailsId(){
		return this.detailsId;
	}
}