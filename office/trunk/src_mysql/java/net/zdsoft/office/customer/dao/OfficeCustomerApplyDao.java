package net.zdsoft.office.customer.dao;

import java.util.*;

import net.zdsoft.office.customer.entity.OfficeCustomerApply;
import net.zdsoft.office.customer.entity.SearchCustomer;
import net.zdsoft.keel.util.Pagination;

/**
 * office_customer_apply 
 * @author 
 * 
 */
public interface OfficeCustomerApplyDao {
	public List<OfficeCustomerApply> getOfficeCustomerApplyForNosubmit(String[] ids,String unitId,String userId, Pagination page);
	/**
	 * 新增office_customer_apply
	 * @param officeCustomerApply
	 * @return"
	 */
	public OfficeCustomerApply save(OfficeCustomerApply officeCustomerApply);
	
	/**
	 * 根据ids数组删除office_customer_apply
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	
	
	public Integer deletePutOffCustomer(String unitId,Date nowDate);
	/**
	 * 更新office_customer_apply
	 * @param officeCustomerApply
	 * @return
	 */
	public Integer update(OfficeCustomerApply officeCustomerApply);
	
	public Integer updateFollowerId(OfficeCustomerApply officeCustomerApply,Date time,boolean flag);

	public Integer updateIsDelete(boolean flag,String id);
	/**
	 * 根据id获取office_customer_apply;
	 * @param id);
	 * @return
	 */
	public OfficeCustomerApply getOfficeCustomerApplyById(String id);
		
	/**
	 * 根据ids数组查询office_customer_applymap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeCustomerApply> getOfficeCustomerApplyMapByIds(String[] ids);
	
	/**
	 * 获取office_customer_apply列表
	 * @return
	 */
	public List<OfficeCustomerApply> getOfficeCustomerApplyList();
		
	/**
	 * 分页获取office_customer_apply列表
	 * @param page
	 * @return
	 */
	public List<OfficeCustomerApply> getOfficeCustomerApplyPage(Pagination page);
	

	/**
	 * 根据unitId获取office_customer_apply列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeCustomerApply> getOfficeCustomerApplyByUnitIdList(String unitId);

	
	public List<OfficeCustomerApply> getAllList(SearchCustomer searchCustomer,String[] ids,String unitId,String userId,boolean cilent, Pagination page);
	/**
	 * 根据unitId分页office_customer_apply获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeCustomerApply> getOfficeCustomerApplyByUnitIdPage(String unitId,String userId,SearchCustomer searchCustomer, Pagination page);
	
	/**
	 * 获取审核list
	 * @param ent
	 * @param queryStatus
	 * @param roleId
	 * @param operateType
	 * @param arrangeIds
	 * @param customer TODO
	 * @param applyTypes TODO
	 * @param page
	 * @return
	 */
	public List<OfficeCustomerApply> getCustomerApplyAuditList(OfficeCustomerApply ent, String queryStatus, String roleId, int operateType, String[] arrangeIds, SearchCustomer customer, String[] applyTypes, Pagination page);
	
	public List<OfficeCustomerApply> getMyCustomerByUnitIdPage(String unitId,
			String userId,SearchCustomer searchCustomer,String[] applyIds,Pagination page);
}
