package net.zdsoft.office.studentBackSchool.dao;

import java.util.List;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentBackSchool.entity.InspectionHolidaysInfo;

public interface InspectionHolidaysInfoDao {
	
	public void insertInspectionHolidaysInfo(InspectionHolidaysInfo holidayInfo);
	
	public void updateInspectionHolidaysInfo(InspectionHolidaysInfo holidayInfo);
	
	public List<InspectionHolidaysInfo> findInspectionHolidaysInfobyUnitId(String unitId,Pagination page);
	
	public InspectionHolidaysInfo findInspectionHolidaysInfobyId(String id);

	public void  deleteInspectionHolidaysInfobyId(String id);
}
