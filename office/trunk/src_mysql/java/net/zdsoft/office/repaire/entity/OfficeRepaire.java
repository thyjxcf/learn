package net.zdsoft.office.repaire.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.attachment.entity.Attachment;
/**
 * office_repaire
 * @author 
 * 
 */
public class OfficeRepaire implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	private String unitId;
	private String userId;
	private String userName;
	private String teachAreaId;
	private String type;
	private String typeName;
	private String phone;
	private String goodsName;
	private String goodsPlace;
	private Date createTime;
	private Date detailTime;
	private String remark;
	/**
	 * 1： 待报修  2：已维修 
	 */
	private String state;
	private String repaireUserId;
	private Date repaireTime;
	private String repaireRemark;
	private String feedback;
	/**
	 * 1.未反馈 2.已反馈
	 */
	private Boolean isFeedback;
	/**
	 * 0.未删除 1.已删除
	 */
	private Boolean isDeleted;
	private String classId; //教室和寝室保修时，需要维护所属班级
	private String className; //教室和寝室保修时，需要维护所属班级
	
	//辅助字段
	private String teachAreaName;//校区
	
	private String repaireTypeId;//二级类别
	private String repaireTypeName;
	
	public static final String STATE_ONE = "1";//待维修
	public static final String STATE_TWO = "2";//维修中
	public static final String STATE_THREE = "3";//已维修
	
	private List<Attachment> attachments=new ArrayList<Attachment>();
	private String uploadContentFileInput;
	
	private String equipmentType;//设备类型 DM-SBLX
	
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getRepaireTypeName() {
		return repaireTypeName;
	}
	public void setRepaireTypeName(String repaireTypeName) {
		this.repaireTypeName = repaireTypeName;
	}
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getRepaireTypeId() {
		return repaireTypeId;
	}
	public void setRepaireTypeId(String repaireTypeId) {
		this.repaireTypeId = repaireTypeId;
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
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 * 获取
	 */
	public String getUserId(){
		return this.userId;
	}
	/**
	 * 设置
	 */
	public void setTeachAreaId(String teachAreaId){
		this.teachAreaId = teachAreaId;
	}
	/**
	 * 获取
	 */
	public String getTeachAreaId(){
		return this.teachAreaId;
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
	public void setGoodsName(String goodsName){
		this.goodsName = goodsName;
	}
	/**
	 * 获取
	 */
	public String getGoodsName(){
		return this.goodsName;
	}
	/**
	 * 设置
	 */
	public void setGoodsPlace(String goodsPlace){
		this.goodsPlace = goodsPlace;
	}
	/**
	 * 获取
	 */
	public String getGoodsPlace(){
		return this.goodsPlace;
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
	public void setDetailTime(Date detailTime){
		this.detailTime = detailTime;
	}
	/**
	 * 获取
	 */
	public Date getDetailTime(){
		return this.detailTime;
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
	 * 设置1： 带报修
2：已维修
	 */
	public void setState(String state){
		this.state = state;
	}
	/**
	 * 获取1： 带报修
2：已维修
	 */
	public String getState(){
		return this.state;
	}
	/**
	 * 设置
	 */
	public void setRepaireUserId(String repaireUserId){
		this.repaireUserId = repaireUserId;
	}
	/**
	 * 获取
	 */
	public String getRepaireUserId(){
		return this.repaireUserId;
	}
	/**
	 * 设置
	 */
	public void setRepaireTime(Date repaireTime){
		this.repaireTime = repaireTime;
	}
	/**
	 * 获取
	 */
	public Date getRepaireTime(){
		return this.repaireTime;
	}
	/**
	 * 设置
	 */
	public void setRepaireRemark(String repaireRemark){
		this.repaireRemark = repaireRemark;
	}
	/**
	 * 获取
	 */
	public String getRepaireRemark(){
		return this.repaireRemark;
	}
	/**
	 * 设置
	 */
	public void setFeedback(String feedback){
		this.feedback = feedback;
	}
	/**
	 * 获取
	 */
	public String getFeedback(){
		return this.feedback;
	}
	/**
	 * 设置1.未反馈
2.已反馈
	 */
	public void setIsFeedback(Boolean isFeedback){
		this.isFeedback = isFeedback;
	}
	/**
	 * 获取1.未反馈
2.已反馈
	 */
	public Boolean getIsFeedback(){
		return this.isFeedback;
	}
	/**
	 * 设置0.未删除
1.已删除

	 */
	public void setIsDeleted(Boolean isDeleted){
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取0.未删除
1.已删除

	 */
	public Boolean getIsDeleted(){
		return this.isDeleted;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTeachAreaName() {
		return teachAreaName;
	}
	public void setTeachAreaName(String teachAreaName) {
		this.teachAreaName = teachAreaName;
	}
    public String getClassId() {
        return classId;
    }
    public void setClassId(String classId) {
        this.classId = classId;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public String getUploadContentFileInput() {
		return uploadContentFileInput;
	}
	public void setUploadContentFileInput(String uploadContentFileInput) {
		this.uploadContentFileInput = uploadContentFileInput;
	}
	
	
}