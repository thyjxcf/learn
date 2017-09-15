package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.attachment.entity.Attachment;
/**
 * office_work_report
 * @author 
 * 
 */
public class OfficeWorkReport implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 微代码DM-HBLX
	 */
	private String reportType;
	/**
	 * 
	 */
	private Date beginTime;
	/**
	 * 
	 */
	private Date endTime;
	/**
	 * 
	 */
	private String receiveUserId;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private String state;
	/**
	 * 
	 */
	private String createUserId;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String deptId;
	/**
	 * 
	 */
	private String readUserId;
	
	private String createUserName;//冗余字段，查询使用
	private String userName;
	
	private String receiveUserNameStr;
	private String userPhoto;//用户头像路径
	private Integer readNum;//已读人数
	private Integer sendNum;//发送人数
	private String readUserNameStr;//已读人姓名
	private String unreadUserNameStr;//未读人姓名
	private String read;//0未读，1已读
	private String time;//收到日期
	private String count;//当日收到的条数
	private List<Attachment> attachments;//附件
	/**
	 * 设置
	 */
	public void setId(String id){
		this.id = id;
	}
	public String getReadUserId() {
		return readUserId;
	}
	public void setReadUserId(String readUserId) {
		this.readUserId = readUserId;
	}
	/**
	 * 获取
	 */
	public String getId(){
		return this.id;
	}
	/**
	 * 设置微代码DM-HBLX
	 */
	public void setReportType(String reportType){
		this.reportType = reportType;
	}
	/**
	 * 获取微代码DM-HBLX
	 */
	public String getReportType(){
		return this.reportType;
	}
	/**
	 * 设置
	 */
	public void setBeginTime(Date beginTime){
		this.beginTime = beginTime;
	}
	/**
	 * 获取
	 */
	public Date getBeginTime(){
		return this.beginTime;
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
	public void setReceiveUserId(String receiveUserId){
		this.receiveUserId = receiveUserId;
	}
	/**
	 * 获取
	 */
	public String getReceiveUserId(){
		return this.receiveUserId;
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
	public void setState(String state){
		this.state = state;
	}
	/**
	 * 获取
	 */
	public String getState(){
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
	public void setDeptId(String deptId){
		this.deptId = deptId;
	}
	/**
	 * 获取
	 */
	public String getDeptId(){
		return this.deptId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReceiveUserNameStr() {
		return receiveUserNameStr;
	}
	public Integer getReadNum() {
		return readNum;
	}
	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
	}
	public Integer getSendNum() {
		return sendNum;
	}
	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}
	public void setReceiveUserNameStr(String receiveUserNameStr) {
		this.receiveUserNameStr = receiveUserNameStr;
	}
	public String getReadUserNameStr() {
		return readUserNameStr;
	}
	public void setReadUserNameStr(String readUserNameStr) {
		this.readUserNameStr = readUserNameStr;
	}
	public String getUnreadUserNameStr() {
		return unreadUserNameStr;
	}
	public void setUnreadUserNameStr(String unreadUserNameStr) {
		this.unreadUserNameStr = unreadUserNameStr;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getUserPhoto() {
		return userPhoto;
	}
	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	
}