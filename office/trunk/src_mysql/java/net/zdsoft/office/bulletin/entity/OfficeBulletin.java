package net.zdsoft.office.bulletin.entity;


import java.io.Serializable;
import java.util.Date;
/**
 * office_bulletin
 * @author 
 * 
 */
public class OfficeBulletin implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;
	private String unitId;
	private String title;
	private String content;
	private Date createTime;
	private Date endTime;
	private Date publishTime;
	private String createUserId;
	private String auditUserId;
	private Date auditTime;
	private String idea;
	/**
	 * 1：是，0：否
	 */
	private Boolean isTop;
	private Date placeTopTime;
	/**
	 * 1：未提交，2：审核中，3：通过，4：未通过
	 */
	private String state;
	/**
	 * 1:通知，2：行事历，3：公告，4：文件
	 */
	private String type;
	/**
	 * 1：是，0：否
	 */
	private Boolean isAll;
	private Integer orderId;
	private String scope;//所属类型
	private String endType;//截止时间类型，分类四种：永久、一年、半年、一月四种
	private String areaId;//校区id,32个0为全校，默认为32个0
	private String modifyUserId;//最后一次修改人员
	private Integer isDeleted;//是否删除
	
	private boolean needSms;//是否需要发短信
	private String smsContent;//短信内容
	
	private Boolean timing;//是否定时发送
	private String smsTime;//短信发送时间，精确到分
	
	private String releaseScope;//发布范围
	
	//移动端用
	private String first;//当天最新
	private String last;//当天最后
	public static final String SCOPE_ALL_UNIT = "1"; //所有单位
	public static final String SCOPE_SELF_UNIT = "2";//本单位 
	public static final String SCOPE_SELF_AND_SON_UNIT = "3";//本单位及直属单位
	
	private boolean titleLink;//消息推送
	
	public boolean isNeedSms() {
		return needSms;
	}
	public void setNeedSms(boolean needSms) {
		this.needSms = needSms;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public String getReleaseScope() {
		return releaseScope;
	}
	public void setReleaseScope(String releaseScope) {
		this.releaseScope = releaseScope;
	}
	public static final String STATE_ALL = "0";
	public static final String STATE_UNSUBMIT = "1";
	public static final String STATE_AUDIT = "2";
	public static final String STATE_PASS = "3";
	public static final String STATE_UNPASS = "4";
	public static final String STATE_AUDITALL = "5";//审核是显示的全部数据
	
	public static final String END_TYPE_PERMANENT = "1";//永久
	public static final String END_TYPE_ONEYEAR = "2";//一年
	public static final String END_TYPE_HALFYEAR = "3";//半年
	public static final String END_TYPE_ONEMONTH = "4";//一月
	
	//辅助字段
	private String areaName;//校区名称
	private String createUserName;
	private String publishUserName;
	private Boolean isNew = false;//是否今天创建的,默认为false
	private Boolean isRead = false;//是否已读
	
	private String deptName;//发布人所属部门
	private String clickNum;//点击量
	private String weekDay;//星期几
	
	public String getPublishUserName() {
		return publishUserName;
	}
	public void setPublishUserName(String publishUserName) {
		this.publishUserName = publishUserName;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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
	 * 设置
	 */
	public void setAuditUserId(String auditUserId){
		this.auditUserId = auditUserId;
	}
	/**
	 * 获取
	 */
	public String getAuditUserId(){
		return this.auditUserId;
	}
	/**
	 * 设置
	 */
	public void setAuditTime(Date auditTime){
		this.auditTime = auditTime;
	}
	/**
	 * 获取
	 */
	public Date getAuditTime(){
		return this.auditTime;
	}
	public String getIdea() {
		return idea;
	}
	public void setIdea(String idea) {
		this.idea = idea;
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
	 * 设置1：未提交，2：审核中，3：通过，4：未通过
	 */
	public void setState(String state){
		this.state = state;
	}
	/**
	 * 获取1：未提交，2：审核中，3：通过，4：未通过
	 */
	public String getState(){
		return this.state;
	}
	/**
	 * 设置1:通知，2：公告，3：行事历，4：文件
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 * 获取1:通知，2：公告，3：行事历，4：文件
	 */
	public String getType(){
		return this.type;
	}
	/**
	 * 设置1：是，0：否
	 */
	public void setIsAll(Boolean isAll){
		this.isAll = isAll;
	}
	/**
	 * 获取1：是，0：否
	 */
	public Boolean getIsAll(){
		return this.isAll;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getEndType() {
		return endType;
	}
	public void setEndType(String endType) {
		this.endType = endType;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getModifyUserId() {
		return modifyUserId;
	}
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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
	public String getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}
	/**
	 * @return the titleLink
	 */
	public boolean isTitleLink() {
		return titleLink;
	}
	/**
	 * @param titleLink the titleLink to set
	 */
	public void setTitleLink(boolean titleLink) {
		this.titleLink = titleLink;
	}
	/**
	 * @return the timing
	 */
	public Boolean getTiming() {
		return timing;
	}
	/**
	 * @param timing the timing to set
	 */
	public void setTiming(Boolean timing) {
		this.timing = timing;
	}
	/**
	 * @return the smsTime
	 */
	public String getSmsTime() {
		return smsTime;
	}
	/**
	 * @param smsTime the smsTime to set
	 */
	public void setSmsTime(String smsTime) {
		this.smsTime = smsTime;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	
}