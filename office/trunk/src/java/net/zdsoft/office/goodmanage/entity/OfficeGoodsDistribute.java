package net.zdsoft.office.goodmanage.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_goods_distribute
 * @author 
 * 
 */
public class OfficeGoodsDistribute implements Serializable{
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
	private Float price;
	/**
	 * 
	 */
	private String goodsUnit;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private Date purchaseTime;
	/**
	 * 
	 */
	private String goodsRemark;
	/**
	 * 
	 */
	private Integer amount;
	/**
	 * 
	 */
	private String receiverId;
	/**
	 * 
	 */
	private String distributeRemark;
	
	private Date distributeTime;
	
	//辅助字段
	private String receiverName;
	private String receiverDeptName;
	private String typeName;

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
	public void setGoodsUnit(String goodsUnit){
		this.goodsUnit = goodsUnit;
	}
	/**
	 * 获取
	 */
	public String getGoodsUnit(){
		return this.goodsUnit;
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
	public void setPurchaseTime(Date purchaseTime){
		this.purchaseTime = purchaseTime;
	}
	/**
	 * 获取
	 */
	public Date getPurchaseTime(){
		return this.purchaseTime;
	}
	/**
	 * 设置
	 */
	public void setGoodsRemark(String goodsRemark){
		this.goodsRemark = goodsRemark;
	}
	/**
	 * 获取
	 */
	public String getGoodsRemark(){
		return this.goodsRemark;
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
	public void setReceiverId(String receiverId){
		this.receiverId = receiverId;
	}
	/**
	 * 获取
	 */
	public String getReceiverId(){
		return this.receiverId;
	}
	/**
	 * 设置
	 */
	public void setDistributeRemark(String distributeRemark){
		this.distributeRemark = distributeRemark;
	}
	/**
	 * 获取
	 */
	public String getDistributeRemark(){
		return this.distributeRemark;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverDeptName() {
		return receiverDeptName;
	}
	public void setReceiverDeptName(String receiverDeptName) {
		this.receiverDeptName = receiverDeptName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Date getDistributeTime() {
		return distributeTime;
	}
	public void setDistributeTime(Date distributeTime) {
		this.distributeTime = distributeTime;
	}
}