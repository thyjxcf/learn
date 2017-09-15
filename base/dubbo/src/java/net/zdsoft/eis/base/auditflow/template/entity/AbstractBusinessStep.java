package net.zdsoft.eis.base.auditflow.template.entity;

import java.util.Date;

import net.zdsoft.eis.frame.entity.HibernateEntity;

/**
 * 审核步骤抽象对象
 * 
 * @author sherlockyao
 */
public abstract class AbstractBusinessStep extends HibernateEntity {

	private static final long serialVersionUID = -3796285975170215306L;

	/**
	 * 审核单位级别
	 */
	private Integer auditUnitLevel;

	/**
	 * 审核顺序号
	 */
	private Integer auditOrder;

	/**
	 * 审核单位
	 */
	private String operateUnit;

	/**
	 * 审核结果
	 */
	private Integer state;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 审核时间
	 */
	private Date auditDate;

	/**
	 * 申请单位id
	 */
	private String applyUnitId;

	/**
	 * 审核意见
	 */
	private String opinion;

	/**
	 * 申请id
	 */
	private String applyId;

	/**
	 * 操作人
	 */
	private String operateUser;
	

	/**
	 * 备份信息,把所有可备份数据都存入备份对象中
	 * 
	 * @param backupStep
	 *            备份对象
	 */
	public void backupInfo(AbstractBusinessStep backupStep) {
		backupStep.setApplyId(applyId);
		backupStep.setApplyUnitId(applyUnitId);
		backupStep.setAuditDate(auditDate);
		backupStep.setAuditOrder(auditOrder);
		backupStep.setAuditUnitLevel(auditUnitLevel);
		backupStep.setBusinessType(businessType);
		backupStep.setId(id);
		backupStep.setOperateUnit(operateUnit);
		backupStep.setOperateUser(operateUser);
		backupStep.setOpinion(opinion);
		backupStep.setState(state);
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getApplyUnitId() {
		return applyUnitId;
	}

	public void setApplyUnitId(String applyUnitId) {
		this.applyUnitId = applyUnitId;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Integer getAuditOrder() {
		return auditOrder;
	}

	public void setAuditOrder(Integer auditOrder) {
		this.auditOrder = auditOrder;
	}

	public Integer getAuditUnitLevel() {
		return auditUnitLevel;
	}

	public void setAuditUnitLevel(Integer auditUnitLevel) {
		this.auditUnitLevel = auditUnitLevel;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getOperateUnit() {
		return operateUnit;
	}

	public void setOperateUnit(String operateUnit) {
		this.operateUnit = operateUnit;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	
}
