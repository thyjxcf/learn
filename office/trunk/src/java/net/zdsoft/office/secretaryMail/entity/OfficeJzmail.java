package net.zdsoft.office.secretaryMail.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.attachment.entity.Attachment;
/**
 * office_jzmail
 * @author 
 * 
 */
public class OfficeJzmail implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String unitId;
	private String unitName;
	/**
	 * 
	 */
	private String createUserId;
	private String createUserName;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private String phone;
	/**
	 * 
	 */
	private String mail;
	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private Boolean isDeleted;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Integer state;
	/**
	 * 
	 */
	private String dealUserId;
	private String dealUserName;
	private String parth;
	/**
	 * 
	 */
	private Date dealTime;
	
	private boolean display;
	
	private boolean anonymous;
	
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
	public void setPhone(String phone){
		this.phone = phone;
	}
	/**
	 * 获取
	 */
	public String getPhone(){
		return this.phone;
	}
	/**
	 * 设置
	 */
	public void setMail(String mail){
		this.mail = mail;
	}
	/**
	 * 获取
	 */
	public String getMail(){
		return this.mail;
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
	public void setIsDeleted(Boolean isDeleted){
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取
	 */
	public Boolean getIsDeleted(){
		return this.isDeleted;
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
	public void setDealUserId(String dealUserId){
		this.dealUserId = dealUserId;
	}
	/**
	 * 获取
	 */
	public String getDealUserId(){
		return this.dealUserId;
	}
	/**
	 * 设置
	 */
	public void setDealTime(Date dealTime){
		this.dealTime = dealTime;
	}
	/**
	 * 获取
	 */
	public Date getDealTime(){
		return this.dealTime;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getDealUserName() {
		return dealUserName;
	}
	public void setDealUserName(String dealUserName) {
		this.dealUserName = dealUserName;
	}
	public boolean isDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public String getParth() {
		return parth;
	}
	public void setParth(String parth) {
		this.parth = parth;
	}
	public boolean isAnonymous() {
		return anonymous;
	}
	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}
}