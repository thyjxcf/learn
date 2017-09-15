package net.zdsoft.office.customer.service;

import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.service.ApplyBusinessService;

public interface OfficeCustomerBusinessApplyAuditService {
	public ApplyBusinessService<ApplyBusiness> getApplyBusinessService(int businessType);
}
