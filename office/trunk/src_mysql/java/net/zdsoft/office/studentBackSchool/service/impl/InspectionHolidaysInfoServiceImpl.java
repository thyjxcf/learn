package net.zdsoft.office.studentBackSchool.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.studentBackSchool.dao.InspectionHolidaysInfoDao;
import net.zdsoft.office.studentBackSchool.entity.InspectionHolidaysInfo;
import net.zdsoft.office.studentBackSchool.service.InspectionHolidaysInfoService;

public class InspectionHolidaysInfoServiceImpl implements InspectionHolidaysInfoService{

	private InspectionHolidaysInfoDao inspectionHolidaysInfoDao;
	
	
	public void setInspectionHolidaysInfoDao(
			InspectionHolidaysInfoDao inspectionHolidaysInfoDao) {
		this.inspectionHolidaysInfoDao = inspectionHolidaysInfoDao;
	}

	@Override
	public void updateInspectionHolidaysInfo(InspectionHolidaysInfo holidayInfo) {
		holidayInfo.setModifyTime(new Date());
		if(StringUtils.isNotBlank(holidayInfo.getId())){
			inspectionHolidaysInfoDao.updateInspectionHolidaysInfo(holidayInfo);
		}else{
			holidayInfo.setCreationTime(holidayInfo.getModifyTime());
			inspectionHolidaysInfoDao.insertInspectionHolidaysInfo(holidayInfo);
		}
		
	}

	@Override
	public List<InspectionHolidaysInfo> findInspectionHolidaysInfobyUnitId(
			String unitId, Pagination page) {
		return inspectionHolidaysInfoDao.findInspectionHolidaysInfobyUnitId(unitId, page);
	}

	@Override
	public InspectionHolidaysInfo findInspectionHolidaysInfobyId(String id) {
		return inspectionHolidaysInfoDao.findInspectionHolidaysInfobyId(id);
	}

	@Override
	public void deleteInspectionHolidaysInfobyId(String id) {
		inspectionHolidaysInfoDao.deleteInspectionHolidaysInfobyId(id);
	}

}
