package net.zdsoft.office.customer.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.auditflow.manager.entity.FlowApply;
import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.auditflow.manager.service.SchFlowApplyService;
import net.zdsoft.eis.base.auditflow.manager.service.SchFlowAuditService;
import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.office.customer.constant.OfficeCustomerConstants;
import net.zdsoft.office.customer.entity.OfficeCustomerApply;
import net.zdsoft.office.customer.entity.OfficeCustomerInfo;
import net.zdsoft.office.customer.service.OfficeCustomerApplyService;
import net.zdsoft.office.customer.service.OfficeCustomerInfoService;

public class CustomerAuditAction extends CustomerCommonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OfficeCustomerApplyService officeCustomerApplyService;
	private SchFlowAuditService schFlowAuditService;
	private OfficeCustomerInfoService officeCustomerInfoService;
	private SchFlowApplyService schFlowApplyService;
	private UserService userService;
	private DeptService deptService;

	private List<CustomRole> roleList = new ArrayList<CustomRole>();
	private List<OfficeCustomerApply> customerApplyList = new ArrayList<OfficeCustomerApply>();
	private OfficeCustomerApply customer = new OfficeCustomerApply();
	
	private String roleCode;
	private String stateQuery;
	private String applyId;
	private String auditId;
	
	private boolean isBusinessTypeTwo = false;//7003
	
	
	
	public String auditAdmin(){
		if(officeCustomerApplyService.isDeptLeader(getLoginUser().getUnitId(), getLoginUser().getUserId())){
			CustomRole role1 = customRoleService.getCustomRoleByRoleCode(getUnitId(), OfficeCustomerConstants.OFFCIE_DEPT_LEADER);
			roleList.add(role1);
		}
		
		List<CustomRole> cRoles = getCustomRoles(); 
		for(CustomRole role : cRoles){
			if(StringUtils.equals(OfficeCustomerConstants.OFFICE_CLIENT_MANAGER, role.getRoleCode())){
				roleList.add(role);
				break;
			}
		}
		return SUCCESS;
	}
	
	public String auditList(){
		
		customer.setUnitId(getLoginUser().getUnitId());
		
		customerApplyList = officeCustomerApplyService.getCustomerApplyAuditList(customer, getLoginUser().getUserId(), stateQuery, roleCode, null, null, getPage());
		
		return SUCCESS;
	}
	
	public String auditEdit(){
		customer = officeCustomerApplyService.getOfficeCustomerApplyById(customer.getId());
		FlowApply apply = schFlowApplyService.getFlowApply(applyId);
		
		User user = userService.getUser(apply.getApplyUserId());
		if(user != null){
			customer.setApplyUserName(user.getRealname());
			
			Dept dept = deptService.getDept(user.getDeptid());
			if(dept != null){
				customer.setDeptName(dept.getDeptname());
			}
		}
		
		
		OfficeCustomerInfo info = officeCustomerInfoService.getOfficeCustomerInfoById(customer.getCustomerId());
		customer.setOfficeCustomerInfo(info);
		
		FlowAudit adent = schFlowAuditService.getFlowAudit(auditId);
		customer.setAuditId(adent.getId());
		customer.setAuditStatus(String.valueOf(adent.getStatus()));
		
		if(adent.getBusinessType() == OfficeCustomerConstants.BUSINESS_TYPE_2){
			isBusinessTypeTwo = true;
		}
		
		List<FlowAudit> auditlist = schFlowAuditService.getFlowAudits(applyId);
		
		if(isBusinessTypeTwo){
			String str = "";
			if(auditlist.get(0).getStatus() == FlowAudit.STATUS_AUDIT_PASS){
				str = "【审核通过】    ";
			}else if(auditlist.get(0).getStatus() == FlowAudit.STATUS_AUDIT_REJECT){
				str = "【审核未通过】   ";
			}
			customer.setOperateOpinion(str+auditlist.get(0).getOpinion()==null?"":auditlist.get(0).getOpinion());
		}else{
			if(auditlist.size()>1){
				String str = "";
				if(auditlist.get(0).getStatus() == FlowAudit.STATUS_AUDIT_PASS){
					str = "【审核通过】 ";
				}else if(auditlist.get(0).getStatus() == FlowAudit.STATUS_AUDIT_REJECT){
					str = "【审核未通过】 ";
				}
				customer.setDeptOpinion(str + auditlist.get(0).getOpinion()==null?"":auditlist.get(0).getOpinion());
				
				String str1 = "";
				if(auditlist.get(1).getStatus() == FlowAudit.STATUS_AUDIT_PASS){
					str1 = "【审核通过】 ";
				}else if(auditlist.get(1).getStatus() == FlowAudit.STATUS_AUDIT_REJECT){
					str1 = "【审核未通过】 ";
				}
				customer.setOperateOpinion(str1 + auditlist.get(1).getOpinion()==null?"":auditlist.get(1).getOpinion());
			}
		}
		
		return SUCCESS;
	}
	
	public String saveAudit(){
		User user = getLoginInfo().getUser(); 
		FlowAudit audit = new FlowAudit();
		audit.setAuditUnitId(user.getUnitid());
		audit.setAuditUserId(user.getId());
		audit.setAuditUsername(user.getRealname());
		audit.setStatus(Integer.valueOf(customer.getAuditStatus()));
		audit.setOpinion(customer.getOpinion());
		audit.setAuditDate(new Date());
		
		try {
			officeCustomerApplyService.saveContestStususclaAudit(audit, customer, customer.getAuditId(), roleCode);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("审核异常！");
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	
	public boolean isBusinessTypeTwo() {
		return isBusinessTypeTwo;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getStateQuery() {
		return stateQuery;
	}

	public void setStateQuery(String stateQuery) {
		this.stateQuery = stateQuery;
	}

	public List<CustomRole> getRoleList() {
		return roleList;
	}

	public List<OfficeCustomerApply> getCustomerApplyList() {
		return customerApplyList;
	}

	public void setOfficeCustomerApplyService(
			OfficeCustomerApplyService officeCustomerApplyService) {
		this.officeCustomerApplyService = officeCustomerApplyService;
	}

	public OfficeCustomerApply getCustomer() {
		return customer;
	}

	public void setCustomer(OfficeCustomerApply customer) {
		this.customer = customer;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	public void setSchFlowAuditService(SchFlowAuditService schFlowAuditService) {
		this.schFlowAuditService = schFlowAuditService;
	}
	
	public void setOfficeCustomerInfoService(
			OfficeCustomerInfoService officeCustomerInfoService) {
		this.officeCustomerInfoService = officeCustomerInfoService;
	}
	
	public void setSchFlowApplyService(SchFlowApplyService schFlowApplyService) {
		this.schFlowApplyService = schFlowApplyService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
}
