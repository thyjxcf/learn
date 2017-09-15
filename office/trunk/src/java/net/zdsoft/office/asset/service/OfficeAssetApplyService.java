package net.zdsoft.office.asset.service;


import java.util.*;

import net.zdsoft.eis.base.auditflow.manager.entity.FlowAudit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.office.asset.entity.OfficeAssetApply;
/**
 * office_asset_apply
 * @author 
 * 
 */
public interface OfficeAssetApplyService{

	/**
	 * 新增office_asset_apply
	 * @param officeAssetApply
	 * @return
	 */
	public OfficeAssetApply save(OfficeAssetApply officeAssetApply, UploadFile uploadFile);
	
	/**
	 * 重新提交
	 * @param ent
	 */
	public void saveSubmitFlowApply(OfficeAssetApply ent, UploadFile uploadFile);

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
	 * @param roleCode
	 * @param user
	 * @param page
	 * @return
	 */
	public List<OfficeAssetApply> getAssetAuditList(OfficeAssetApply ent, String roleCode, User user, Pagination page);
	
	/**
	 * 请购审核
	 * @param audit
	 * @param businessId
	 * @param auditIds
	 */
	public void saveOfficeAssetApplyAudit(FlowAudit audit, String businessId,String applyid,String userId,String... auditIds);
	
	/**
	 * 查询审核通过的请购记录
	 * @param unitId
	 * @param applyUserId TODO
	 * @param state TODO
	 * @param isAudit TODO
	 * @param page TODO
	 * @return
	 */
	public List<OfficeAssetApply> getOfficeAssetApplyPurchaseList(String unitId, String applyUserId, String state, boolean isAudit, Pagination page);
	
	/**
	 * 采购单维护
	 * @param ent
	 * @return TODO
	 */
	public String savePurchaseInfo(OfficeAssetApply ent);
	
}