package net.zdsoft.office.customer.service.impl;

	@Override
	public List<OfficeCustomerApply> getOfficeCustomerApplyByUnitIdList(String unitId){
		return officeCustomerApplyDao.getOfficeCustomerApplyByUnitIdList(unitId);
	}
	public List<OfficeCustomerApply> getMyCustomerByUnitIdPage(String unitId,
	@Override
	public List<OfficeCustomerApply> getOfficeCustomerApplyByUnitIdPage(String unitId,String userId,SearchCustomer searchCustomer,Pagination page){
		List<OfficeCustomerApply> applyList=officeCustomerApplyDao.getOfficeCustomerApplyByUnitIdPage(unitId,userId,searchCustomer, page);
	}