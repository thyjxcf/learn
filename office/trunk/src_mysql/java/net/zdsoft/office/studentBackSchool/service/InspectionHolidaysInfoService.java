package net.zdsoft.office.studentBackSchool.service;

import java.util.List;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentBackSchool.entity.InspectionHolidaysInfo;

public interface InspectionHolidaysInfoService {
	
	/**
	 * 新增修改节假日
	 * @param holidayInfo
	 */
	public void updateInspectionHolidaysInfo(InspectionHolidaysInfo holidayInfo);
	
	/**
	 * 根据单位id查询节假日
	 * @param unitId
	 * @return
	 */
	public List<InspectionHolidaysInfo> findInspectionHolidaysInfobyUnitId(String unitId,Pagination page);
	/**
	 * 根据id查询节假日
	 * @param unitId
	 * @return
	 */
	public InspectionHolidaysInfo findInspectionHolidaysInfobyId(String id);
	/**
	 * 根据id删除节假日
	 * @param unitId
	 * @return
	 */
	public void deleteInspectionHolidaysInfobyId(String id);

}
