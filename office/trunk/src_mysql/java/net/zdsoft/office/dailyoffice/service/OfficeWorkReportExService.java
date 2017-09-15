package net.zdsoft.office.dailyoffice.service;

	/**
	 * 根据UnitId获取office_work_report_ex列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeWorkReportEx> getOfficeWorkReportExByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_work_report_ex
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeWorkReportEx> getOfficeWorkReportExByUnitIdPage(String unitId, Pagination page);