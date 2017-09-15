/**
 * 
 */
package net.zdsoft.eis.base.auditflow.manager.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.auditflow.manager.service.ApplyBusinessService;
import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * 申请信息
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2012-11-2 上午11:18:00 $
 */
public class FlowApply extends BaseEntity {

	private static final long serialVersionUID = 6265167567542421354L;

	public FlowApply() {
		super();
	}

	public FlowApply(ApplyBusinessService<ApplyBusiness> applyBusinessService) {
		super();

		this.applyBusinessService = applyBusinessService;
		this.business = applyBusinessService.getEntity();
	}

	/**
	 * 不区分操作类型
	 */
	public static final int OPERATE_TYPE_NO = 0;

	/**
	 * 操作类型：增加
	 */
	public static final int OPERATE_TYPE_ADD = 1;

	/**
	 * 操作类型：修改
	 */
	public static final int OPERATE_TYPE_MODIFY = 2;

	/**
	 * 操作类型：删除
	 */
	public static final int OPERATE_TYPE_DELETE = 3;

	/**
	 * 状态： 未提交（准备中）
	 */
	public static final int STATUS_PREPARING = 0;

	/**
	 * 状态： 已提交，审核中
	 */
	public static final int STATUS_IN_AUDIT = 1;

	/**
	 * 状态： 审核通过
	 */
	public static final int STATUS_AUDIT_PASS = 2;

	/**
	 * 状态： 审核未通过
	 */
	public static final int STATUS_AUDIT_REJECT = 3;

	/**
	 * 状态： 审核完成
	 */
	public static final int STATUS_AUDIT_FINISH = 4;

	/**
	 * 增删改申请操作类型map列表
	 */
	public static final Map<Integer, String> OPERATE_TYPE_MAP = new HashMap<Integer, String>();
	public static final List<String[]> OPERATE_TYPE_LIST = new ArrayList<String[]>();
	static {
		OPERATE_TYPE_MAP.put(Integer.valueOf(OPERATE_TYPE_ADD), "增加");
		OPERATE_TYPE_MAP.put(Integer.valueOf(OPERATE_TYPE_MODIFY), "修改");
		OPERATE_TYPE_MAP.put(Integer.valueOf(OPERATE_TYPE_DELETE), "删除");

		OPERATE_TYPE_LIST.add(new String[] { String.valueOf(OPERATE_TYPE_ADD), "增加" });
		OPERATE_TYPE_LIST.add(new String[] { String.valueOf(OPERATE_TYPE_MODIFY), "修改" });
		OPERATE_TYPE_LIST.add(new String[] { String.valueOf(OPERATE_TYPE_DELETE), "删除" });
	}

	private String businessId;
	private int businessType;// 申请对象类型（业务类型，一般对应某张表）
	private int operateType;// 操作类型

	private String reason;// 原因
	private int status;// 状态

	private String applyUserId;// 申请用户id
	private String applyUsername;// 申请用户姓名
	private String applyArrangeId;// 申请范围id
	private String applyUnitId;// 申请单位
	private Date applyDate;// 申请日期

	private Date auditDate;// 最后审核的日期

	private ApplyBusinessService<ApplyBusiness> applyBusinessService;
	private ApplyBusiness business = new ApplyBusiness();// 申请变更的业务对象
	private BusinessOwner owner = new BusinessOwner();
	private List<FlowAudit> audits;

	// -------------------附加--------------------
	private String applyUnitName;// 申请单位名称
	private String applyArrangeName;//申请范围名称

	public ApplyBusiness getBusiness() {
		return business;
	}

	public void setBusiness(ApplyBusiness business) {
		this.business = business;
	}

	public BusinessOwner getOwner() {
		return owner;
	}

	public void setOwner(BusinessOwner owner) {
		this.owner = owner;
	}

	public void setBusinessId(String businessId) {
		business.setBusinessId(businessId);
	}

	public String getBusinessId() {
		return business.getBusinessId();
	}

	public List<FlowAudit> getAudits() {
		return audits;
	}

	public void setAudits(List<FlowAudit> audits) {
		this.audits = audits;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getApplyUnitId() {
		return applyUnitId;
	}

	public void setApplyUnitId(String applyUnitId) {
		this.applyUnitId = applyUnitId;
	}
	
		public String getApplyArrangeId() {
		return applyArrangeId;
	}

	public void setApplyArrangeId(String applyArrangeId) {
		this.applyArrangeId = applyArrangeId;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyUsername() {
		return applyUsername;
	}

	public void setApplyUsername(String applyUsername) {
		this.applyUsername = applyUsername;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getApplyUnitName() {
		return applyUnitName;
	}

	public void setApplyUnitName(String applyUnitName) {
		this.applyUnitName = applyUnitName;
	}

	public String getApplyArrangeName() {
		return applyArrangeName;
	}

	public void setApplyArrangeName(String applyArrangeName) {
		this.applyArrangeName = applyArrangeName;
	}

	public void setApplyBusinessService(ApplyBusinessService<ApplyBusiness> applyBusinessService) {
		this.applyBusinessService = applyBusinessService;
	}

	public ApplyBusinessService<ApplyBusiness> getApplyBusinessService() {
		return applyBusinessService;
	}

}
