package net.zdsoft.office.dailyoffice.service.impl;

	@Override
	public List<OfficeLog> getOfficeLogByUnitIdList(String unitId){
		return officeLogDao.getOfficeLogByUnitIdList(unitId);
	}

	@Override
	public List<OfficeLog> getOfficeLogByUnitIdPage(String unitId, Pagination page){
		return officeLogDao.getOfficeLogByUnitIdPage(unitId, page);
	}
