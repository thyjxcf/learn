package net.zdsoft.office.convertflow.entity;

import java.util.Date;
/**
 * office_convert_flow_task
 * @author 
 * 
 */
public class OfficeConvertFlowTask {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * office_convert_flow表id
	 */
	private String convertFlowId;
	/**
	 * 审核人id、物品id、角色id等
	 */
	private String auditParm;
	/**
	 * 审核状态
	 */
	private Integer status;
	/**
	 * 业务参数（可以为taskId等）
	 */
	private String parm;
	/**
	 * 
	 */
	private Date createTime;

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
	public void setConvertFlowId(String convertFlowId){
		this.convertFlowId = convertFlowId;
	}
	/**
	 * 获取
	 */
	public String getConvertFlowId(){
		return this.convertFlowId;
	}
	/**
	 * 设置
	 */
	public void setAuditParm(String auditParm){
		this.auditParm = auditParm;
	}
	/**
	 * 获取
	 */
	public String getAuditParm(){
		return this.auditParm;
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
	public void setParm(String parm){
		this.parm = parm;
	}
	/**
	 * 获取
	 */
	public String getParm(){
		return this.parm;
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
}