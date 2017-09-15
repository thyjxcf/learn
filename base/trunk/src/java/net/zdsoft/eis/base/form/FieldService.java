package net.zdsoft.eis.base.form;

import java.util.List;

import net.zdsoft.eis.base.auditflow.manager.entity.ApplyBusiness;
import net.zdsoft.eis.base.auditflow.manager.service.ApplyBusinessService;


public interface FieldService {
	/**
	 * 获取类表
	 * @param businessType
	 * @return
	 */
	public List<Field> getFiledList(int businessType);
	
	public List<Field> getListFieldHeads(int businessType);

	public List<Field> getFiledList(int businessType,
			ApplyBusinessService<ApplyBusiness> applyBusinessService);

}
