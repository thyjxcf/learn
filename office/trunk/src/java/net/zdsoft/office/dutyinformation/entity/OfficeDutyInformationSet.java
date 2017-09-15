package net.zdsoft.office.dutyinformation.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
/**
 * office_duty_information_set
 * @author 
 * 
 */
public class OfficeDutyInformationSet implements Serializable{
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
	private String createUserId;
	/**
	 * 
	 */
	private String areaId;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String dutyName;
	/**
	 * 
	 */
	private Date dutyStartTime;
	/**
	 * 
	 */
	private Date dutyEndTime;
	/**
	 * 
	 */
	private Date registrationStartTime;
	/**
	 * 
	 */
	private Date registrationEndTime;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private String instruction;
	
	private String userIds;
	private String userNames;
	
	private String year;
	
	private boolean canEdit;//是否可以报名
	
	private boolean onlyView;//只能查看
	
	private Set<String> userSet=new HashSet<String>();
	
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
	public void setAreaId(String areaId){
		this.areaId = areaId;
	}
	/**
	 * 获取
	 */
	public String getAreaId(){
		return this.areaId;
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
	public void setDutyName(String dutyName){
		this.dutyName = dutyName;
	}
	/**
	 * 获取
	 */
	public String getDutyName(){
		return this.dutyName;
	}
	/**
	 * 设置
	 */
	public void setDutyStartTime(Date dutyStartTime){
		this.dutyStartTime = dutyStartTime;
	}
	/**
	 * 获取
	 */
	public Date getDutyStartTime(){
		return this.dutyStartTime;
	}
	/**
	 * 设置
	 */
	public void setDutyEndTime(Date dutyEndTime){
		this.dutyEndTime = dutyEndTime;
	}
	/**
	 * 获取
	 */
	public Date getDutyEndTime(){
		return this.dutyEndTime;
	}
	/**
	 * 设置
	 */
	public void setRegistrationStartTime(Date registrationStartTime){
		this.registrationStartTime = registrationStartTime;
	}
	/**
	 * 获取
	 */
	public Date getRegistrationStartTime(){
		return this.registrationStartTime;
	}
	/**
	 * 设置
	 */
	public void setRegistrationEndTime(Date registrationEndTime){
		this.registrationEndTime = registrationEndTime;
	}
	/**
	 * 获取
	 */
	public Date getRegistrationEndTime(){
		return this.registrationEndTime;
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
	public void setInstruction(String instruction){
		this.instruction = instruction;
	}
	/**
	 * 获取
	 */
	public String getInstruction(){
		return this.instruction;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
	public Set<String> getUserSet() {
		return userSet;
	}
	public void setUserSet(Set<String> userSet) {
		this.userSet = userSet;
	}
	public boolean isOnlyView() {
		return onlyView;
	}
	public void setOnlyView(boolean onlyView) {
		this.onlyView = onlyView;
	}
}