package net.zdsoft.office.bulletin.entity;


import java.io.Serializable;
import java.util.Date;
/**
 * office_bulletin_tl
 * @author 
 * 
 */
@SuppressWarnings("serial")
public class OfficeBulletinTl implements Serializable{

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date endTime;
	/**
	 * 
	 */
	private Date publishTime;
	/**
	 * 
	 */
	private String createUserId;
	/**
	 * 1：是，0：否
	 */
	private Boolean isTop;
	/**
	 * 
	 */
	private Date placeTopTime;
	/**
	 * 1：未发布，3:已发布
	 */
	private String state;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private Integer orderId;
	/**
	 * 
	 */
	private String endType;
	/**
	 * 
	 */
	private String modifyUserId;
	/**
	 * 
	 */
	private Integer isDeleted;
	
	private String weekDay;
	private Boolean isNew = false;//是否今天创建的,默认为false
	private Boolean isRead = false;//是否已读
	private String deptName;//发布人所属部门
	private String clickNum;//点击量
	private String createUserName;
	private String unitName;//发布单位名称
	
	private String unitIds;//单位ids
	private String unitNames;//用户ids
	
	private Boolean isNeedsms;//是否发短信
	private String smsContent;//短信正文
	private Boolean timing;//是否定时发送
	private String smsTime;//短信发送时间，精确到分

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
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 * 获取
	 */
	public String getTitle(){
		return this.title;
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
	 * 设置
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 * 获取
	 */
	public Date getCreateTime(){
		return this.createTime;
	}
	/**
	 * 设置
	 */
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	/**
	 * 获取
	 */
	public Date getEndTime(){
		return this.endTime;
	}
	/**
	 * 设置
	 */
	public void setPublishTime(Date publishTime){
		this.publishTime = publishTime;
	}
	/**
	 * 获取
	 */
	public Date getPublishTime(){
		return this.publishTime;
	}
	/**
	 * 设置
	 */
	public void setCreateUserId(String createUserId){
		this.createUserId = createUserId;
	}
	/**
	 * 获取
	 */
	public String getCreateUserId(){
		return this.createUserId;
	}
	/**
	 * 设置1：是，0：否
	 */
	public void setIsTop(Boolean isTop){
		this.isTop = isTop;
	}
	/**
	 * 获取1：是，0：否
	 */
	public Boolean getIsTop(){
		return this.isTop;
	}
	/**
	 * 设置
	 */
	public void setPlaceTopTime(Date placeTopTime){
		this.placeTopTime = placeTopTime;
	}
	/**
	 * 获取
	 */
	public Date getPlaceTopTime(){
		return this.placeTopTime;
	}
	/**
	 * 设置1：未发布，3:已发布
	 */
	public void setState(String state){
		this.state = state;
	}
	/**
	 * 获取1：未发布，3:已发布
	 */
	public String getState(){
		return this.state;
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
	public void setOrderId(Integer orderId){
		this.orderId = orderId;
	}
	/**
	 * 获取
	 */
	public Integer getOrderId(){
		return this.orderId;
	}
	/**
	 * 设置
	 */
	public void setEndType(String endType){
		this.endType = endType;
	}
	/**
	 * 获取
	 */
	public String getEndType(){
		return this.endType;
	}
	/**
	 * 设置
	 */
	public void setModifyUserId(String modifyUserId){
		this.modifyUserId = modifyUserId;
	}
	/**
	 * 获取
	 */
	public String getModifyUserId(){
		return this.modifyUserId;
	}
	/**
	 * 设置
	 */
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取
	 */
	public Integer getIsDeleted(){
		return this.isDeleted;
	}
	
	public String getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}
	public Boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getClickNum() {
		return clickNum;
	}
	public void setClickNum(String clickNum) {
		this.clickNum = clickNum;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getUnitIds() {
		return unitIds;
	}
	public void setUnitIds(String unitIds) {
		this.unitIds = unitIds;
	}
	public String getUnitNames() {
		return unitNames;
	}
	public void setUnitNames(String unitNames) {
		this.unitNames = unitNames;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Boolean getIsNeedsms() {
		return isNeedsms;
	}
	public void setIsNeedsms(Boolean isNeedsms) {
		this.isNeedsms = isNeedsms;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public Boolean getTiming() {
		return timing;
	}
	public void setTiming(Boolean timing) {
		this.timing = timing;
	}
	public String getSmsTime() {
		return smsTime;
	}
	public void setSmsTime(String smsTime) {
		this.smsTime = smsTime;
	}
	
}