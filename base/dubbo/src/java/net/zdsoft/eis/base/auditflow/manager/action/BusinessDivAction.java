package net.zdsoft.eis.base.auditflow.manager.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.service.ApplyBusinessService;
import net.zdsoft.eis.base.auditflow.manager.service.FlowApplyService;
import net.zdsoft.eis.base.form.Field;
import net.zdsoft.eis.base.form.FieldService;
import net.zdsoft.eis.frame.action.BaseDivAction;

public abstract class BusinessDivAction extends BaseDivAction {
	private static final long serialVersionUID = 7957066430670713296L;

	private List<ApplyBusiness> objects = new ArrayList<ApplyBusiness>();
	protected int businessType;
	private String ownerId;

	private FlowApplyService flowApplyService;
	private FieldService fieldService;

	public String execute() {
		objects = flowApplyService.getBusinesses(getApplyBusinessService(), businessType, ownerId);

		return SUCCESS;
	}

	/**
	 * 业务列表字段
	 * 
	 * @return
	 */
	public List<Field> getListFieldHeads() {
		return fieldService.getListFieldHeads(businessType);
	}

	public List<ApplyBusiness> getObjects() {
		return objects;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public void setFlowApplyService(FlowApplyService flowApplyService) {
		this.flowApplyService = flowApplyService;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
	public void setFieldService(FieldService fieldService) {
		this.fieldService = fieldService;
	}

	
	public abstract ApplyBusinessService<ApplyBusiness> getApplyBusinessService();
}
