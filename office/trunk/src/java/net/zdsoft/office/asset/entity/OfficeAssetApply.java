package net.zdsoft.office.asset.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.attachment.entity.Attachment;
import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
/**
 * office_asset_apply
 * @author 
 * 
 */
public class OfficeAssetApply extends ApplyBusiness{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String unitId;
	
	/**
	 * 请购单编号
	 */
	private String applyCode;
	
	/**
	 * 类别
	 */
	private String categoryId;
	/**
	 * 物品名称
	 */
	private String assetName;
	
	/**
	 * 规格
	 */
	private String assetFormat;
	/**
	 * 数量
	 */
	private Integer assetNumber;
	
	/**
	 * 单位
	 */
	private String assetUnit;
	/**
	 * 请购单价
	 */
	private Double unitPrice;
	
	/**
	 * 是否申请通过
	 */
	private Boolean isPassApply;
	/**
	 * 采购单价
	 */
	private Double purchasePrice;
	/**
	 * 采购总价
	 */
	private Double purchaseTotalPrice;
	/**
	 * 采购人甲
	 */
	private String purchaseUserid1;
	/**
	 * 采购人乙
	 */
	private String purchaseUserid2;
	/**
	 * 采购批准人
	 */
	private String purchaseAuditUserid;
	
	/**
	 * 采购审核状态
	 */
	private String purchaseState;
	
	/**
	 * 采购审核意见
	 */
	private String purchaseOpinion;
	
	/**
	 * 采购时间
	 */
	private Date purchaseDate;
	
	/**
	 * 是否已经同步到物品管理
	 */
	private boolean isSyncToGoods;
	
	//辅助字段
	private String categoryName;//类别名称
	private Double totalUnitPrice;//申请总价
	private String purchaseUserName1;//采购人甲
	private String purchaseUserName2;//采购人乙
	private String purchaseAuditUserName;//采购批准人
	private String deptName;//申请人所在部门
	private boolean isOverMaxNum;//是否超过所申请的最大金额
	
	private String applyUserId;
	private String applyUserName;
	private String applyId;
	private String applyStatus;
	private Date applyDate;// 申请日期
	private String reason;// 原因
	
	private String auditId;
	private String auditStatus;//审核状态
	private String opinion;//审核意见   
	
	private String deptState;
	private String deptOpinion;
	private String assetLeaderState;
	private String assetLeaderOpinion;
	private String schoolmasterState;
	private String schoolmasterOpinion;
	private String meetingleaderState;
	private String meetingleaderOpinion;
	private String priceStr;
	private String totalPriceStr;
	private List<Attachment> attachments=new ArrayList<Attachment>();
	
	
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getAssetUnit() {
		return assetUnit;
	}
	public void setAssetUnit(String assetUnit) {
		this.assetUnit = assetUnit;
	}
	public boolean getIsOverMaxNum() {
		return isOverMaxNum;
	}
	public void setIsOverMaxNum(boolean isOverMaxNum) {
		this.isOverMaxNum = isOverMaxNum;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getPurchaseState() {
		return purchaseState;
	}
	public void setPurchaseState(String purchaseState) {
		this.purchaseState = purchaseState;
	}
	public String getPurchaseOpinion() {
		return purchaseOpinion;
	}
	public void setPurchaseOpinion(String purchaseOpinion) {
		this.purchaseOpinion = purchaseOpinion;
	}
	public String getPurchaseAuditUserName() {
		return purchaseAuditUserName;
	}
	public void setPurchaseAuditUserName(String purchaseAuditUserName) {
		this.purchaseAuditUserName = purchaseAuditUserName;
	}
	public String getPurchaseUserName1() {
		return purchaseUserName1;
	}
	public void setPurchaseUserName1(String purchaseUserName1) {
		this.purchaseUserName1 = purchaseUserName1;
	}
	public String getPurchaseUserName2() {
		return purchaseUserName2;
	}
	public void setPurchaseUserName2(String purchaseUserName2) {
		this.purchaseUserName2 = purchaseUserName2;
	}
	
	public Double getPurchaseTotalPrice() {
		return purchaseTotalPrice;
	}
	public void setPurchaseTotalPrice(Double purchaseTotalPrice) {
		this.purchaseTotalPrice = purchaseTotalPrice;
	}
	public String getDeptState() {
		return deptState;
	}
	public void setDeptState(String deptState) {
		this.deptState = deptState;
	}
	public String getAssetLeaderState() {
		return assetLeaderState;
	}
	public void setAssetLeaderState(String assetLeaderState) {
		this.assetLeaderState = assetLeaderState;
	}
	public String getSchoolmasterState() {
		return schoolmasterState;
	}
	public void setSchoolmasterState(String schoolmasterState) {
		this.schoolmasterState = schoolmasterState;
	}
	public String getMeetingleaderState() {
		return meetingleaderState;
	}
	public void setMeetingleaderState(String meetingleaderState) {
		this.meetingleaderState = meetingleaderState;
	}
	public String getDeptOpinion() {
		return deptOpinion;
	}
	public void setDeptOpinion(String deptOpinion) {
		this.deptOpinion = deptOpinion;
	}
	public String getAssetLeaderOpinion() {
		return assetLeaderOpinion;
	}
	public void setAssetLeaderOpinion(String assetLeaderOpinion) {
		this.assetLeaderOpinion = assetLeaderOpinion;
	}
	public String getSchoolmasterOpinion() {
		return schoolmasterOpinion;
	}
	public void setSchoolmasterOpinion(String schoolmasterOpinion) {
		this.schoolmasterOpinion = schoolmasterOpinion;
	}
	public String getMeetingleaderOpinion() {
		return meetingleaderOpinion;
	}
	public void setMeetingleaderOpinion(String meetingleaderOpinion) {
		this.meetingleaderOpinion = meetingleaderOpinion;
	}
	public Double getTotalUnitPrice() {
		return totalUnitPrice;
	}
	public void setTotalUnitPrice(Double totalUnitPrice) {
		this.totalUnitPrice = totalUnitPrice;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	public void setApplyUserId(String applyUserId){
		this.applyUserId = applyUserId;
	}
	/**
	 * 获取
	 */
	public String getApplyUserId(){
		return this.applyUserId;
	}
	/**
	 * 设置
	 */
	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}
	/**
	 * 获取
	 */
	public String getCategoryId(){
		return this.categoryId;
	}
	/**
	 * 设置
	 */
	public void setAssetName(String assetName){
		this.assetName = assetName;
	}
	/**
	 * 获取
	 */
	public String getAssetName(){
		return this.assetName;
	}
	/**
	 * 设置
	 */
	public void setAssetNumber(Integer assetNumber){
		this.assetNumber = assetNumber;
	}
	/**
	 * 获取
	 */
	public Integer getAssetNumber(){
		return this.assetNumber;
	}
	/**
	 * 设置
	 */
	public void setUnitPrice(Double unitPrice){
		this.unitPrice = unitPrice;
	}
	/**
	 * 获取
	 */
	public Double getUnitPrice(){
		return this.unitPrice;
	}
	/**
	 * 设置
	 */
	public void setIsPassApply(Boolean isPassApply){
		this.isPassApply = isPassApply;
	}
	/**
	 * 获取
	 */
	public Boolean getIsPassApply(){
		return this.isPassApply;
	}
	/**
	 * 设置
	 */
	public void setPurchasePrice(Double purchasePrice){
		this.purchasePrice = purchasePrice;
	}
	/**
	 * 获取
	 */
	public Double getPurchasePrice(){
		return this.purchasePrice;
	}
	/**
	 * 设置
	 */
	public void setPurchaseUserid1(String purchaseUserid1){
		this.purchaseUserid1 = purchaseUserid1;
	}
	/**
	 * 获取
	 */
	public String getPurchaseUserid1(){
		return this.purchaseUserid1;
	}
	/**
	 * 设置
	 */
	public void setPurchaseUserid2(String purchaseUserid2){
		this.purchaseUserid2 = purchaseUserid2;
	}
	/**
	 * 获取
	 */
	public String getPurchaseUserid2(){
		return this.purchaseUserid2;
	}
	/**
	 * 设置
	 */
	public void setPurchaseAuditUserid(String purchaseAuditUserid){
		this.purchaseAuditUserid = purchaseAuditUserid;
	}
	/**
	 * 获取
	 */
	public String getPurchaseAuditUserid(){
		return this.purchaseAuditUserid;
	}
	public String getApplyCode() {
		return applyCode;
	}
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	public String getAssetFormat() {
		return assetFormat;
	}
	public void setAssetFormat(String assetFormat) {
		this.assetFormat = assetFormat;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getPriceStr() {
		return priceStr;
	}
	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}
	public String getTotalPriceStr() {
		return totalPriceStr;
	}
	public void setTotalPriceStr(String totalPriceStr) {
		this.totalPriceStr = totalPriceStr;
	}
	public boolean getIsSyncToGoods() {
		return isSyncToGoods;
	}
	public void setIsSyncToGoods(boolean isSyncToGoods) {
		this.isSyncToGoods = isSyncToGoods;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	
}