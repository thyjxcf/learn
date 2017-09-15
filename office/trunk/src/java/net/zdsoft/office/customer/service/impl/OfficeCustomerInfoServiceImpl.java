package net.zdsoft.office.customer.service.impl;

	@Override
	public List<OfficeCustomerInfo> getOfficeCustomerInfoByUnitIdList(String unitId){
		return officeCustomerInfoDao.getOfficeCustomerInfoByUnitIdList(unitId);
	}

	@Override
	public List<OfficeCustomerInfo> getOfficeCustomerInfoByUnitIdPage(String unitId, Pagination page){
		return officeCustomerInfoDao.getOfficeCustomerInfoByUnitIdPage(unitId, page);
	}