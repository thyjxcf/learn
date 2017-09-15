package net.zdsoft.office.msgcenter.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.attachment.entity.Attachment;
/**
 * office_msg_sending
 * @author 
 * 
 */
public class OfficeMsgSending implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String title;
	/**
	 * 1一般 /2紧急 
	 */
	private Integer isEmergency;
	/**
	 * 
	 */
	private String content;
	/**
	 *  消息来源，微代码DM-MSGTYPE,默认为1消息
	 */
	private Integer msgType;
	/**
	 * 
	 */
	private Boolean isNeedsms;
	/**
	 * 
	 */
	private String smsContent;
	/**
	 * 1 草稿箱/2 发件箱/3 收件箱/4废件箱/5 自定义文件夹
	 */
	private Integer state;
	/**
	 * 
	 */
	private String createUserId;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date sendTime;
	/**
	 * 0 否/ 1是
	 */
	private Integer isRead;
	/**
	 * 
	 */
	private String replyMsgId;
	/**
	 * 
	 */
	private Integer hasAttached;
	
	private Boolean isWithdraw;//是否撤回
	
	private String simpleContent;
	
	
	private Boolean timing;//是否定时发送
	private String smsTime;//短信发送时间，精确到分
	
	private String userIds;//选择的用户ids
	private String userNames;//选择的用户姓名
	private String detailNames;//选择的用户姓名（包含部门单位）
	private String detailIds;
	
	private String readStr;//已读人员信息
	
	private String sendUserName;//发件人姓名
	private String sendUserNameSimple;//发件人姓名，手机端显示使用，不带单位部门
	private String[] mainSendNames;//主送人员
	private String receivingName;//
	private String photoUrl;
	
	private Integer unReadNum;//未读人数
	private Integer hasReadNum;//已读人数
	private Integer receiveNum;//总人数，是否显示更多使用
	
	private boolean titleLink;//消息推送
	
	private String dateStr;//日期格式（星期一	 01-01）
	
	private String deptIds;//部门ids
	private String unitIds;//单位ids
	
	private List<Attachment> attachments = new ArrayList<Attachment>();
	
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
	public void setIsEmergency(Integer isEmergency){
		this.isEmergency = isEmergency;
	}
	public Integer getIsEmergency(){
		return this.isEmergency;
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
	public void setMsgType(Integer msgType){
		this.msgType = msgType;
	}
	public Integer getMsgType(){
		return this.msgType;
	}
	/**
	 * 设置
	 */
	public void setIsNeedsms(Boolean isNeedsms){
		this.isNeedsms = isNeedsms;
	}
	/**
	 * 获取
	 */
	public Boolean getIsNeedsms(){
		return this.isNeedsms;
	}
	/**
	 * 设置
	 */
	public void setSmsContent(String smsContent){
		this.smsContent = smsContent;
	}
	/**
	 * 获取
	 */
	public String getSmsContent(){
		return this.smsContent;
	}
	/**
	 * 设置1 草稿箱/2 发件箱/3 收件箱/4废件箱/5 自定义文件夹
	 */
	public void setState(Integer state){
		this.state = state;
	}
	/**
	 * 获取1 草稿箱/2 发件箱/3 收件箱/4废件箱/5 自定义文件夹
	 */
	public Integer getState(){
		return this.state;
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
	public void setSendTime(Date sendTime){
		this.sendTime = sendTime;
	}
	/**
	 * 获取
	 */
	public Date getSendTime(){
		return this.sendTime;
	}
	/**
	 * 设置0 否/ 1是
	 */
	public void setIsRead(Integer isRead){
		this.isRead = isRead;
	}
	/**
	 * 获取0 否/ 1是
	 */
	public Integer getIsRead(){
		return this.isRead;
	}
	/**
	 * 设置
	 */
	public void setReplyMsgId(String replyMsgId){
		this.replyMsgId = replyMsgId;
	}
	/**
	 * 获取
	 */
	public String getReplyMsgId(){
		return this.replyMsgId;
	}
	/**
	 * 设置
	 */
	public void setHasAttached(Integer hasAttached){
		this.hasAttached = hasAttached;
	}
	/**
	 * 获取
	 */
	public Integer getHasAttached(){
		return this.hasAttached;
	}
	
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public String getDetailNames() {
		return detailNames;
	}
	public void setDetailNames(String detailNames) {
		this.detailNames = detailNames;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
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
	public String getReadStr() {
		return readStr;
	}
	public void setReadStr(String readStr) {
		this.readStr = readStr;
	}
	public String getSendUserName() {
		return sendUserName;
	}
	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	public Integer getUnReadNum() {
		return unReadNum;
	}
	public void setUnReadNum(Integer unReadNum) {
		this.unReadNum = unReadNum;
	}
	public Integer getHasReadNum() {
		return hasReadNum;
	}
	public void setHasReadNum(Integer hasReadNum) {
		this.hasReadNum = hasReadNum;
	}
	public String[] getMainSendNames() {
		return mainSendNames;
	}
	public void setMainSendNames(String[] mainSendNames) {
		this.mainSendNames = mainSendNames;
	}
	public Boolean getIsWithdraw() {
		return isWithdraw;
	}
	public void setIsWithdraw(Boolean isWithdraw) {
		this.isWithdraw = isWithdraw;
	}
	public Integer getReceiveNum() {
		return receiveNum;
	}
	public void setReceiveNum(Integer receiveNum) {
		this.receiveNum = receiveNum;
	}
	public String getSimpleContent() {
		return simpleContent;
	}
	public void setSimpleContent(String simpleContent) {
		this.simpleContent = simpleContent;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public String getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	public String getUnitIds() {
		return unitIds;
	}
	public void setUnitIds(String unitIds) {
		this.unitIds = unitIds;
	}
	public String getSendUserNameSimple() {
		return sendUserNameSimple;
	}
	public void setSendUserNameSimple(String sendUserNameSimple) {
		this.sendUserNameSimple = sendUserNameSimple;
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
	public String getDetailIds() {
		return detailIds;
	}
	public void setDetailIds(String detailIds) {
		this.detailIds = detailIds;
	}
	public String getReceivingName() {
		return receivingName;
	}
	public void setReceivingName(String receivingName) {
		this.receivingName = receivingName;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
}