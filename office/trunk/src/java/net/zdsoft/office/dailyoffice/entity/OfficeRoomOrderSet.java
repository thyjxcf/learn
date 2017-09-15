package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
/**
 * office_room_order_set
 * @author 
 * 
 */
public class OfficeRoomOrderSet implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 微代码DM-CDLX
	 */
	private String thisId;
	/**
	 * 1：节次，2：时间段
	 */
	private String useType;
	/**
	 * 0：不审核，1：审核
	 */
	private String needAudit;
	/**
	 * 
	 */
	private String unitId;

	private String name;
	private String isSelected;
	/**
	 * 设置
	 */
	private String isUse;
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
	 * 设置微代码DM-CDLX
	 */
	public void setThisId(String thisId){
		this.thisId = thisId;
	}
	/**
	 * 获取微代码DM-CDLX
	 */
	public String getThisId(){
		return this.thisId;
	}
	/**
	 * 设置1：节次，2：时间段
	 */
	public void setUseType(String useType){
		this.useType = useType;
	}
	/**
	 * 获取1：节次，2：时间段
	 */
	public String getUseType(){
		return this.useType;
	}
	/**
	 * 设置0：不审核，1：审核
	 */
	public void setNeedAudit(String needAudit){
		this.needAudit = needAudit;
	}
	/**
	 * 获取0：不审核，1：审核
	 */
	public String getNeedAudit(){
		return this.needAudit;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsUse() {
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	public String getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
	
}