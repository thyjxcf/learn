package net.zdsoft.eis.base.auditflow.template.service;

import java.util.List;
/**
 * 异动审核步骤设定管理接口
 * 
 * @author yinp
 */
public interface JwAuditFlowManageService {
	
	/**
	 * 获得系统教育局中的最高级别
	 * 
	 * @return 最高级别号 (2省教育局,3市教育局,4区县教育局)
	 */
	public int getRegionLevel();
	
	/**
	 * 获得系统易动情况列表
	 * @param nowRegionLevel TODO
	 * 
	 * @return 情况列表
	 */
	public List getAuditTypeList(Integer nowRegionLevel);
	
	/**
	 * 获得默认审核列表
	 * 
	 * @param regionLevel 系统最高单位级别
	 * @param nowRegionLevel TODO
	 * @param businessType 异动类别，-255
	 * @param auditType 异动方式
	 * @param section 学段
	 * @param schConfirm TODO
	 * @return 审核列表
	 */
	public List getAuditFlowList(String regionLevel,Integer nowRegionLevel,String businessType,String auditType, String section, boolean schConfirm);
	
	/**
	 * 清楚数据
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
	 * 获得所有易动类型
	 * 
	 * @return 所有易动类型
	 */
//	public List getFlowTypeList();
	
	/**
	 * 根据实际异动情况生成审核列表
	 * 
	 * @param regionLevel 系统最高单位级别
	 * @param nowRegionLevel TODO
	 * @param auditType 异动方式
	 * @param schConfirm TODO
	 * @return 审核列表
	 */
	public List getNonentityNoteData(String regionLevel,Integer nowRegionLevel, String auditType, boolean schConfirm);
	
	/**
	 * 判断流程是否已经在数据库里面存在
	 * 
	 * @param regionLevel 系统最高单位级别
	 * @param auditType 异动方式
	 * 
	 * @return true 存在 false 不存在
	 */
	public boolean getAuditFlowList(String businessType,String auditType,String section);
}
