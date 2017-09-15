package net.zdsoft.eis.base.auditflow.template.dao;

import java.util.List;

import net.zdsoft.eis.base.auditflow.template.entity.FlowStep;

public interface JwAuditFlowManageDao {	
	/**
	 * 清除数据
	 * 
	 * @param auditType 异动方式
	 * @param section 学段
	 * @param businessType 异动类别，-255
	 */
	public void deleteAuditFlow(String auditType,String section,String businessType);
	
	/**
	 * 保存数据
	 * 
	 * @param auditType 异动方式
	 * @param section 学段
	 * @param businessType 异动类别，-255
	 * @param auditFlowList 最终审核单位列表
	 */
	public void addAuditFlow(String auditType,String section,String businessType,List auditFlowList);
	
	/**
	 * 获得所有异动类型
	 * 
	 * @return 所有异动类型
	 */
//	public List getFlowTypeList();
	
	/**
	 * 获得已经存在的审核步骤
	 * 
	 * @param businessType 异动类别，-255
	 * @param auditType 异动方式
	 * @param section 学段
	 * 
	 * @return 所有异动类型
	 */
	public List<FlowStep> getFlowTypeNoteData(String businessType,String auditType,String section);
}
