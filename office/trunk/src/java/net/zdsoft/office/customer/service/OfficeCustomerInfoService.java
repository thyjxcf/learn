package net.zdsoft.office.customer.service;import java.util.*;import net.zdsoft.office.customer.entity.OfficeCustomerInfo;import net.zdsoft.office.customer.entity.SearchCustomer;import net.zdsoft.keel.util.Pagination;/** * office_customer_info  * @author  *  */public interface OfficeCustomerInfoService {	public Integer saveAddTime(Date addTime);	public Integer getSameCustomerName(OfficeCustomerInfo officeCustomerInfo);			public List<OfficeCustomerInfo> getCustomerLibraryByUnitIdPage(SearchCustomer searchCustomer,String unitId, Pagination page);	/**	 * 新增office_customer_info	 * @param officeCustomerInfo	 * @return	 */	public OfficeCustomerInfo save(OfficeCustomerInfo officeCustomerInfo);		/**	 * 根据ids数组删除office_customer_info数据	 * @param ids	 * @return	 */	public Integer delete(String[] ids);		/**	 * 更新office_customer_info	 * @param officeCustomerInfo	 * @return	 */	public Integer update(OfficeCustomerInfo officeCustomerInfo);		/**	 * 更新客户表客户状态	 * @param id	 * @param state	 * @param addTime TODO	 */	public void updateState(String id, int state, Date addTime);		/**	 * 根据id获取office_customer_info	 * @param id	 * @return	 */	public OfficeCustomerInfo getOfficeCustomerInfoById(String id);		/**	 * 根据ids数组查询office_customer_infomap	 * @param ids	 * @return	 */	public Map<String, OfficeCustomerInfo> getOfficeCustomerInfoMapByIds(String[] ids);		/**	 * 获取office_customer_info列表	 * @return	 */	public List<OfficeCustomerInfo> getOfficeCustomerInfoList();		/**	 * 分页获取office_customer_info列表	 * @param page	 * @return	 */	public List<OfficeCustomerInfo> getOfficeCustomerInfoPage(Pagination page);	

	/**
	 * 根据UnitId获取office_customer_info列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeCustomerInfo> getOfficeCustomerInfoByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_customer_info
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeCustomerInfo> getOfficeCustomerInfoByUnitIdPage(String unitId, Pagination page);}