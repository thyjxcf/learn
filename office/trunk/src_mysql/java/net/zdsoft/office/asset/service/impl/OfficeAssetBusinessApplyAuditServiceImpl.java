package net.zdsoft.office.asset.service.impl;

import java.util.Map;

import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.service.ApplyBusinessService;

public class OfficeAssetBusinessApplyAuditServiceImpl implements
		net.zdsoft.office.asset.service.OfficeAssetBusinessApplyAuditService {
	
	private Map<String, ApplyBusinessService<ApplyBusiness>> applyBusinessServiceMap;
	
	public void setApplyBusinessServiceMap(
			Map<String, ApplyBusinessService<ApplyBusiness>> applyBusinessServiceMap) {
		this.applyBusinessServiceMap = applyBusinessServiceMap;
	}

	public ApplyBusinessService<ApplyBusiness> getApplyBusinessService(
			int businessType) {
		return applyBusinessServiceMap.get(String.valueOf(businessType));
	}

}
