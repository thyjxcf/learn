package net.zdsoft.office.teacherLeave.service;

	/**
	 * 根据UnitId获取office_teacher_leave列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeTeacherLeave> getOfficeTeacherLeaveByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_teacher_leave
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeTeacherLeave> getOfficeTeacherLeaveByUnitIdPage(String unitId, Pagination page);