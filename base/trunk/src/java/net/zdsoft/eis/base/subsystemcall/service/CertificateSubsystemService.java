package net.zdsoft.eis.base.subsystemcall.service;

import java.util.Map;


public interface CertificateSubsystemService {

	public Map<String,Integer> getCertMap(String[] stuIds,String specId,String unitId,String classId);
}
