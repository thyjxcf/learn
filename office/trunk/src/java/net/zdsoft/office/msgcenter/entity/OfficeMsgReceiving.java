package net.zdsoft.office.msgcenter.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 信息接受信息表
 * @author 
 * 
 */
public class OfficeMsgReceiving implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 消息id
	 */
	private String messageId;
	/**
	 * 发送用户id
	 */
	private String sendUserId;
	/**
	 * 接受用户id
	 */
	private String receiveUserId;
	/**
	 * 发送用户名字
	 */
	private String sendUsername;
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 1 学生 /2 教师 /3 家长
	 */
	private Integer receiverType;
	/**
	 * 1 留言/ 2 通知
	 */
	private Integer msgType;
	/**
	 * 是否阅读
	 */
	private Integer isRead;
	/**
	 * 是否删除
	 */
	private Boolean isDeleted;
	/**
	 * 1 草稿箱/2 发件箱/3 收件箱/4废件箱/5 自定义文件夹
	 */
	private Integer state;
	/**
	 * 紧急程度
	 */
	private Integer isEmergency;
	/**
	 * 发送时间
	 */
	private Date sendTime;
	/**
	 * 阅读时间
	 */
	private Date readTime;
	/**
	 * 
	 */
	private Boolean isDownloadAttachment;
	/**
	 * 
	 */
	private Boolean isSmsReceived;
	/**
	 * 
	 */
	private String replyMsgId;
	
	/**
	 * 是否包含附件
	 */
	private Integer hasAttached;
	
	/**
	 * 是否加星
	 */
	private Integer hasStar;
	
	private Integer needTodo;//是否待办
	
	private Boolean isWithdraw;//是否撤回
	
	private Integer countNum = 0;//会话总数
	
	private Boolean isSendInfo = false;//收件人会话，是否不是收件人
	
	private String dateStr;//日期格式（星期一	 01-01）
	
	private String photoUrl;//头像地址

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
	 * 设置消息id
	 */
	public void setMessageId(String messageId){
		this.messageId = messageId;
	}
	/**
	 * 获取消息id
	 */
	public String getMessageId(){
		return this.messageId;
	}
	/**
	 * 设置发送用户id
	 */
	public void setSendUserId(String sendUserId){
		this.sendUserId = sendUserId;
	}
	/**
	 * 获取发送用户id
	 */
	public String getSendUserId(){
		return this.sendUserId;
	}
	/**
	 * 设置接受用户id
	 */
	public void setReceiveUserId(String receiveUserId){
		this.receiveUserId = receiveUserId;
	}
	/**
	 * 获取接受用户id
	 */
	public String getReceiveUserId(){
		return this.receiveUserId;
	}
	/**
	 * 设置发送用户名字
	 */
	public void setSendUsername(String sendUsername){
		this.sendUsername = sendUsername;
	}
	/**
	 * 获取发送用户名字
	 */
	public String getSendUsername(){
		return this.sendUsername;
	}
	/**
	 * 设置消息标题
	 */
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 * 获取消息标题
	 */
	public String getTitle(){
		return this.title;
	}
	/**
	 * 设置1 学生 /2 教师 /3 家长
	 */
	public void setReceiverType(Integer receiverType){
		this.receiverType = receiverType;
	}
	/**
	 * 获取1 学生 /2 教师 /3 家长
	 */
	public Integer getReceiverType(){
		return this.receiverType;
	}
	/**
	 * 设置1 留言/ 2 通知
	 */
	public void setMsgType(Integer msgType){
		this.msgType = msgType;
	}
	/**
	 * 获取1 留言/ 2 通知
	 */
	public Integer getMsgType(){
		return this.msgType;
	}
	/**
	 * 设置是否阅读
	 */
	public void setIsRead(Integer isRead){
		this.isRead = isRead;
	}
	/**
	 * 获取是否阅读
	 */
	public Integer getIsRead(){
		return this.isRead;
	}
	/**
	 * 设置是否删除
	 */
	public void setIsDeleted(Boolean isDeleted){
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取是否删除
	 */
	public Boolean getIsDeleted(){
		return this.isDeleted;
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
	 * 设置紧急程度
	 */
	public void setIsEmergency(Integer isEmergency){
		this.isEmergency = isEmergency;
	}
	/**
	 * 获取紧急程度
	 */
	public Integer getIsEmergency(){
		return this.isEmergency;
	}
	/**
	 * 设置发送时间
	 */
	public void setSendTime(Date sendTime){
		this.sendTime = sendTime;
	}
	/**
	 * 获取发送时间
	 */
	public Date getSendTime(){
		return this.sendTime;
	}
	/**
	 * 设置阅读时间
	 */
	public void setReadTime(Date readTime){
		this.readTime = readTime;
	}
	/**
	 * 获取阅读时间
	 */
	public Date getReadTime(){
		return this.readTime;
	}
	/**
	 * 设置
	 */
	public void setIsDownloadAttachment(Boolean isDownloadAttachment){
		this.isDownloadAttachment = isDownloadAttachment;
	}
	/**
	 * 获取
	 */
	public Boolean getIsDownloadAttachment(){
		return this.isDownloadAttachment;
	}
	/**
	 * 设置
	 */
	public void setIsSmsReceived(Boolean isSmsReceived){
		this.isSmsReceived = isSmsReceived;
	}
	/**
	 * 获取
	 */
	public Boolean getIsSmsReceived(){
		return this.isSmsReceived;
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
	public Integer getHasAttached() {
		return hasAttached;
	}
	public void setHasAttached(Integer hasAttached) {
		this.hasAttached = hasAttached;
	}
	public Integer getHasStar() {
		return hasStar;
	}
	public void setHasStar(Integer hasStar) {
		this.hasStar = hasStar;
	}
	public Integer getNeedTodo() {
		return needTodo;
	}
	public void setNeedTodo(Integer needTodo) {
		this.needTodo = needTodo;
	}
	public Integer getCountNum() {
		return countNum;
	}
	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}
	public Boolean getIsSendInfo() {
		return isSendInfo;
	}
	public void setIsSendInfo(Boolean isSendInfo) {
		this.isSendInfo = isSendInfo;
	}
	public Boolean getIsWithdraw() {
		return isWithdraw;
	}
	public void setIsWithdraw(Boolean isWithdraw) {
		this.isWithdraw = isWithdraw;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
}