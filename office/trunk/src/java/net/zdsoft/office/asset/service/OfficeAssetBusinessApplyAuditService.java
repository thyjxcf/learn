package net.zdsoft.office.asset.service;

import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.service.ApplyBusinessService;

public interface OfficeAssetBusinessApplyAuditService {
	public ApplyBusinessService<ApplyBusiness> getApplyBusinessService(int businessType);
}
