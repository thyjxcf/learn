package net.zdsoft.eis.base.auditflow.manager.entity;

import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * 审核步骤结果
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-2 上午11:17:50 $
 */
public class FlowAudit extends BaseEntity {

	private static final long serialVersionUID = -2477740010876438323L;

	/**
	 * 状态： 未提交(初始状态)
	 */
	public static final int STATUS_PREPARING = FlowApply.STATUS_PREPARING;

	/**
	 * 状态：待审核
	 */
	public static final int STATUS_CHECKING = FlowApply.STATUS_IN_AUDIT;

	/**
	 * 状态： 审核通过
	 */
	public static final int STATUS_AUDIT_PASS = FlowApply.STATUS_AUDIT_PASS;

	/**
	 * 状态： 审核未通过
	 */
	public static final int STATUS_AUDIT_REJECT = FlowApply.STATUS_AUDIT_REJECT;

	/**
	 * 状态： 审核完成
	 */
	public static final int STATUS_AUDIT_FINISH = FlowApply.STATUS_AUDIT_FINISH;

	/**
	 * 业务步骤中最后一步的序号
	 */
	public static final int STEP_FINAL = -1;
	
	private String arrangeId;//范围id
	private String roleId;//角色id
	private String applyId;// 申请id
	private String applyUnitId;// 申请单位
	private int businessType;// 业务类型
	private String opinion;// 意见
	private int status;// 状态

	private String auditUserId;// 审核用户
	private String auditUsername;// 审核用户姓名
	private String auditUnitId;// 审核单位
	private Date auditDate;// 审核日期
	private int auditOrder;// 审核顺序
	private int auditUnitLevel;// 审核单位级别

	// --------------------附加----------------------
	private String auditUnitName;// 审核单位名称
	private int auditUnitClass;
	private String auditUnitLevelName;// 审核单位级别
	private String roleName;// 角色名称
	public String getArrangeId() {
		return arrangeId;
	}

	public void setArrangeId(String arrangeId) {
		this.arrangeId = arrangeId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
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

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}

	public String getAuditUnitId() {
		return auditUnitId;
	}

	public void setAuditUnitId(String auditUnitId) {
		this.auditUnitId = auditUnitId;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public int getAuditOrder() {
		return auditOrder;
	}

	public void setAuditOrder(int auditOrder) {
		this.auditOrder = auditOrder;
	}

	public int getAuditUnitLevel() {
		return auditUnitLevel;
	}

	public void setAuditUnitLevel(int auditUnitLevel) {
		this.auditUnitLevel = auditUnitLevel;
	}

	public String getAuditUsername() {
		return auditUsername;
	}

	public void setAuditUsername(String auditUsername) {
		this.auditUsername = auditUsername;
	}

	public String getAuditUnitName() {
		return auditUnitName;
	}

	public void setAuditUnitName(String auditUnitName) {
		this.auditUnitName = auditUnitName;
	}

	public String getAuditUnitLevelName() {
		return auditUnitLevelName;
	}

	public void setAuditUnitLevelName(String auditUnitLevelName) {
		this.auditUnitLevelName = auditUnitLevelName;
	}

	public int getAuditUnitClass() {
		return auditUnitClass;
	}

	public void setAuditUnitClass(int auditUnitClass) {
		this.auditUnitClass = auditUnitClass;
	}


	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
