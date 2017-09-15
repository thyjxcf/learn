package net.zdsoft.office.customer.service;

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
	public List<OfficeCustomerInfo> getOfficeCustomerInfoByUnitIdPage(String unitId, Pagination page);