package net.zdsoft.office.goodmanage.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_goods_change_log
 * @author 
 * 
 */
public class OfficeGoodsChangeLog implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String addUserId;
	/**
	 * 
	 */
	private String goodsId;
	/**
	 * 
	 */
	private String reason;
	/**
	 * 
	 */
	private Integer amount;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private Date creationTime;

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
	public void setGoodsId(String goodsId){
		this.goodsId = goodsId;
	}
	/**
	 * 获取
	 */
	public String getGoodsId(){
		return this.goodsId;
	}
	/**
	 * 设置
	 */
	public void setReason(String reason){
		this.reason = reason;
	}
	/**
	 * 获取
	 */
	public String getReason(){
		return this.reason;
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
	public void setCreationTime(Date creationTime){
		this.creationTime = creationTime;
	}
	/**
	 * 获取
	 */
	public Date getCreationTime(){
		return this.creationTime;
	}
}