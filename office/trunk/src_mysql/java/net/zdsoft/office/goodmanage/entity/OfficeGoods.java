package net.zdsoft.office.goodmanage.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_goods
 * @author 
 * 
 */
public class OfficeGoods implements Serializable{
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
	private String addUserId;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String model;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private Integer amount;
	/**
	 * 
	 */
	private Float price;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private Integer reqTag;
	/**
	 * 
	 */
	private Date creationTime;
	/**
	 * 
	 */
	private Boolean isReturned;
	
	private String goodsUnit;//物品单位
	
	private Date purchaseDate;//购买时间
	
	private String typeName;
	private String isReturnedStr;

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
	public void setAddUserId(String addUserId){
		this.addUserId = addUserId;
	}
	/**
	 * 获取
	 */
	public String getAddUserId(){
		return this.addUserId;
	}
	/**
	 * 设置
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 获取
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * 设置
	 */
	public void setModel(String model){
		this.model = model;
	}
	/**
	 * 获取
	 */
	public String getModel(){
		return this.model;
	}
	/**
	 * 设置
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/**
	 * 获取
	 */
	public String getRemark(){
		return this.remark;
	}
	/**
	 * 设置
	 */
	public void setAmount(Integer amount){
		this.amount = amount;
	}
	/**
	 * 获取
	 */
	public Integer getAmount(){
		return this.amount;
	}
	/**
	 * 设置
	 */
	public void setPrice(Float price){
		this.price = price;
	}
	/**
	 * 获取
	 */
	public Float getPrice(){
		return this.price;
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
	public void setReqTag(Integer reqTag){
		this.reqTag = reqTag;
	}
	/**
	 * 获取
	 */
	public Integer getReqTag(){
		return this.reqTag;
	}
	/**
	 * 设置
	 */
	public void setCreationTime(Date creationTime){
		this.creationTime = creationTime;
	}
	/**
	 * 获取
	 */
	public Date getCreationTime(){
		return this.creationTime;
	}
	/**
	 * 设置
	 */
	public void setIsReturned(Boolean isReturned){
		this.isReturned = isReturned;
	}
	/**
	 * 获取
	 */
	public Boolean getIsReturned(){
		return this.isReturned;
	}
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getIsReturnedStr() {
		return isReturnedStr;
	}
	public void setIsReturnedStr(String isReturnedStr) {
		this.isReturnedStr = isReturnedStr;
	}
	public String getGoodsUnit() {
		return goodsUnit;
	}
	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
}