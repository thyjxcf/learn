package net.zdsoft.office.dailyoffice.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * office_utility_apply
 * @author 
 * 
 */
public class OfficeUtilityApply implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	private String unitId;
	/**
	 * 1：会议室
		2：机房
	 */
	private String type;
	private String roomId;
	private String applyPeriod;
	private Date applyDate;
	private String userId;
	/**
	 * 1：待审核
		2：通过
		3：未通过
	 */
	private Integer state;
	private String purpose;//备注，理由，显示的时候使用
	//镇海
	private String courseId; //对应的是eduadm_course表的id
	
	//实验室申请详情
	private String labInfoId;//office_lab_info表的id 
	
	//显示使用
	private String userName;//用户姓名
	

	public void setId(String id){
		this.id = id;
	}
	public String getId(){
		return this.id;
	}
	public void setUnitId(String unitId){
		this.unitId = unitId;
	}
	public String getUnitId(){
		return this.unitId;
	}
	/**
	 * 设置1：会议室
		2：机房
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 * 获取1：会议室
		2：机房
	 */
	public String getType(){
		return this.type;
	}
	public void setRoomId(String roomId){
		this.roomId = roomId;
	}
	public String getRoomId(){
		return this.roomId;
	}
	public void setApplyPeriod(String applyPeriod){
		this.applyPeriod = applyPeriod;
	}
	public String getApplyPeriod(){
		return this.applyPeriod;
	}
	public void setApplyDate(Date applyDate){
		this.applyDate = applyDate;
	}
	public Date getApplyDate(){
		return this.applyDate;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	public String getUserId(){
		return this.userId;
	}
	/**
	 * 设置1：待审核
		2：通过
		3：未通过
	 */
	public void setState(Integer state){
		this.state = state;
	}
	/**
	 * 获取1：待审核
		2：通过
		3：未通过
	 */
	public Integer getState(){
		return this.state;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getLabInfoId() {
		return labInfoId;
	}
	public void setLabInfoId(String labInfoId) {
		this.labInfoId = labInfoId;
	}
}