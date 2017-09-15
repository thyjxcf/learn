package net.zdsoft.office.dailyoffice.service.impl;

	@Override
	public List<OfficeWorkReportEx> getOfficeWorkReportExByUnitIdList(String unitId){
		return officeWorkReportExDao.getOfficeWorkReportExByUnitIdList(unitId);
	}

	@Override
	public List<OfficeWorkReportEx> getOfficeWorkReportExByUnitIdPage(String unitId, Pagination page){
		return officeWorkReportExDao.getOfficeWorkReportExByUnitIdPage(unitId, page);
	}
