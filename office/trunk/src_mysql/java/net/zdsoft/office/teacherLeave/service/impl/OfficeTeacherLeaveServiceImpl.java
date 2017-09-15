package net.zdsoft.office.teacherLeave.service.impl;

	@Override
	public List<OfficeTeacherLeave> getOfficeTeacherLeaveByUnitIdList(String unitId){
		return officeTeacherLeaveDao.getOfficeTeacherLeaveByUnitIdList(unitId);
	}

	@Override
	public List<OfficeTeacherLeave> getOfficeTeacherLeaveByUnitIdPage(String unitId, Pagination page){
		return officeTeacherLeaveDao.getOfficeTeacherLeaveByUnitIdPage(unitId, page);
	}