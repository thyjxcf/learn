package net.zdsoft.office.customer.service;

	/**
	 * 根据UnitId获取office_customer_apply列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeCustomerApply> getOfficeCustomerApplyByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_customer_apply
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeCustomerApply> getOfficeCustomerApplyByUnitIdPage(String unitId,String userId,SearchCustomer searchCustomer, Pagination page);