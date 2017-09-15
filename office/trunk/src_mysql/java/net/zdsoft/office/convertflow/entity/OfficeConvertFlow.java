package net.zdsoft.office.convertflow.entity;

import java.util.Date;

/**
 * office_convert_flow
 * @author 
 * 
 */
public class OfficeConvertFlow {
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
	 * 业务表id
	 */
	private String businessId;
	/**
	 * 业务类型---请假、物品、外出等
	 */
	private Integer type;
	/**
	 * 申请人id
	 */
	private String applyUserId;
	/**
	 * 申请状态
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date modifyTime;
	
	//辅助字段
	private Integer state;//步骤审核状态
	private String auditParm;//审核人或者其他审核参数
	private String parm;//其他参数
	
	public String getParm() {
		return parm;
	}
	public void setParm(String parm) {
		this.parm = parm;
	}
	public String getAuditParm() {
		return auditParm;
	}
	public void setAuditParm(String auditParm) {
		this.auditParm = auditParm;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
	public void setBusinessId(String businessId){
		this.businessId = businessId;
	}
	/**
	 * 获取
	 */
	public String getBusinessId(){
		return this.businessId;
	}
	/**
	 * 设置
	 */
	public void setType(Integer type){
		this.type = type;
	}
	/**
	 * 获取
	 */
	public Integer getType(){
		return this.type;
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
	public void setStatus(Integer status){
		this.status = status;
	}
	/**
	 * 获取
	 */
	public Integer getStatus(){
		return this.status;
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
	public void setModifyTime(Date modifyTime){
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取
	 */
	public Date getModifyTime(){
		return this.modifyTime;
	}
}