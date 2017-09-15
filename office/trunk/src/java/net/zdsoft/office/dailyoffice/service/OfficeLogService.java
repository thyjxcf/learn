package net.zdsoft.office.dailyoffice.service;

	/**
	 * 根据UnitId获取office_log列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeLog> getOfficeLogByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_log
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeLog> getOfficeLogByUnitIdPage(String unitId, Pagination page);