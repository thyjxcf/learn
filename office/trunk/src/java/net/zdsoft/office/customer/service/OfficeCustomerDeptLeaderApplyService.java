package net.zdsoft.office.customer.service;

import net.zdsoft.office.customer.entity.OfficeCustomerApply;

public interface OfficeCustomerDeptLeaderApplyService {
	public OfficeCustomerApply save(OfficeCustomerApply officeCustomerApply);
	
	public Integer update(OfficeCustomerApply officeCustomerApply);
	
	public OfficeCustomerApply putOffApply(OfficeCustomerApply officeCustomerApply);
}
