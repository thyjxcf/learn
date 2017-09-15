package net.zdsoft.office.goodmanage.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_goods_req
 * @author 
 * 
 */
public class OfficeGoodsReq implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String goodsId;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String reqDeptId;
	/**
	 * 
	 */
	private String reqUserId;
	/**
	 * 申请说明
	 */
	private String remark;
	/**
	 * 审核意见
	 */
	private String advice;
	/**
	 * 
	 */
	private Integer state;
	/**
	 * 
	 */
	private Integer amount;
	/**
	 * 
	 */
	private Date creationTime;
	/**
	 * 
	 */
	private Date passTime;
	/**
	 * 
	 */
	private String getUserid;
	
	private OfficeGoods reqGoods;
	private String reqUserName;
	private String reqDeptName;
	private String stateStr;

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
	public void setReqDeptId(String reqDeptId){
		this.reqDeptId = reqDeptId;
	}
	/**
	 * 获取
	 */
	public String getReqDeptId(){
		return this.reqDeptId;
	}
	/**
	 * 设置
	 */
	public void setReqUserId(String reqUserId){
		this.reqUserId = reqUserId;
	}
	/**
	 * 获取
	 */
	public String getReqUserId(){
		return this.reqUserId;
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
	public void setAdvice(String advice){
		this.advice = advice;
	}
	/**
	 * 获取
	 */
	public String getAdvice(){
		return this.advice;
	}
	/**
	 * 设置
	 */
	public void setState(Integer state){
		this.state = state;
	}
	/**
	 * 获取
	 */
	public Integer getState(){
		return this.state;
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
	public void setPassTime(Date passTime){
		this.passTime = passTime;
	}
	/**
	 * 获取
	 */
	public Date getPassTime(){
		return this.passTime;
	}
	/**
	 * 设置
	 */
	public void setGetUserid(String getUserid){
		this.getUserid = getUserid;
	}
	/**
	 * 获取
	 */
	public String getGetUserid(){
		return this.getUserid;
	}
	public OfficeGoods getReqGoods() {
		return reqGoods;
	}
	public void setReqGoods(OfficeGoods reqGoods) {
		this.reqGoods = reqGoods;
	}
	public String getReqUserName() {
		return reqUserName;
	}
	public void setReqUserName(String reqUserName) {
		this.reqUserName = reqUserName;
	}
	public String getReqDeptName() {
		return reqDeptName;
	}
	public void setReqDeptName(String reqDeptName) {
		this.reqDeptName = reqDeptName;
	}
	public String getStateStr() {
		return stateStr;
	}
	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}
	
}