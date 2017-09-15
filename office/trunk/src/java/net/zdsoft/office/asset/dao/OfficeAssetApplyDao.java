package net.zdsoft.office.asset.dao;

import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.asset.entity.OfficeAssetApply;
/**
 * office_asset_apply
 * @author 
 * 
 */
public interface OfficeAssetApplyDao{

	/**
	 * 新增office_asset_apply
	 * @param officeAssetApply
	 * @return
	 */
	public OfficeAssetApply save(OfficeAssetApply officeAssetApply);

	/**
	 * 更新office_asset_apply
	 * @param officeAssetApply
	 * @return
	 */
	public Integer update(OfficeAssetApply officeAssetApply);

	/**
	 * 根据id获取office_asset_apply
	 * @param id
	 * @return
	 */
	public OfficeAssetApply getOfficeAssetApplyById(String id);
	
	/**
	 * 根据条件获取applylist
	 * @param ent
	 * @param page
	 * @return
	 */
	public List<OfficeAssetApply> getAssetApplyList(OfficeAssetApply ent, Pagination page);
	
	/**
	 * 根据条件查询所有的资产
	 * @param unitId
	 * @param state
	 * @param name
	 * @param deptId
	 * @param page
	 * @return
	 */
	public List<OfficeAssetApply> getAssetApplyQueryList(String unitId,String state,String name,String deptId,Pagination page);
	
	/**
	 * 通过查询条件（类别、采购时间）获取通过审核的资产信息汇总
	 * @param ent
	 * @param queryBeginDate
	 * @param queryEndDate
	 * @param page
	 * @return
	 */
	public List<OfficeAssetApply> getAssetData(OfficeAssetApply ent, Date queryBeginDate, Date queryEndDate, Pagination page);
	
	/**
	 * 根据条件获取auditlist
	 * @param ent
	 * @param roleId
	 * @param operateType
	 * @param arrangeIds
	 * @param businessType
	 * @param page
	 * @return
	 */
	public List<OfficeAssetApply> getAssetApplyAuditList(OfficeAssetApply ent, String roleId, int operateType, String[] arrangeIds, int businessType, Pagination page);
	
	/**
	 * 查询采购记录
	 * @param unitId
	 * @param applyUserId TODO
	 * @param state TODO
	 * @param isAudit TODO
	 * @param page TODO
	 * @return
	 */
	public List<OfficeAssetApply> getOfficeAssetApplyPurchaseList(String unitId, String applyUserId, String state, boolean isAudit, Pagination page);
}