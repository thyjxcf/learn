package net.zdsoft.office.teacherAttendance.service.impl;


import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.teacherAttendance.dao.OfficeAttendancePlaceDao;
import net.zdsoft.office.teacherAttendance.entity.OfficeAttendancePlace;
import net.zdsoft.office.teacherAttendance.service.OfficeAttendancePlaceService;
/**
 * 办公地点
 * @author 
 * 
 */
public class OfficeAttendancePlaceServiceImpl implements OfficeAttendancePlaceService{
	private OfficeAttendancePlaceDao officeAttendancePlaceDao;

	@Override
	public OfficeAttendancePlace save(OfficeAttendancePlace officeAttendancePlace){
		return officeAttendancePlaceDao.save(officeAttendancePlace);
	}

	@Override
	public Integer delete(String[] ids){
		return officeAttendancePlaceDao.delete(ids);
	}

	@Override
	public Integer update(OfficeAttendancePlace officeAttendancePlace){
		return officeAttendancePlaceDao.update(officeAttendancePlace);
	}

	public List<OfficeAttendancePlace> getListByIds(String[] ids){
		return officeAttendancePlaceDao.getListByIds(ids);
	}
	
	@Override
	public OfficeAttendancePlace getOfficeAttendancePlaceById(String id){
		return officeAttendancePlaceDao.getOfficeAttendancePlaceById(id);
	}
	
	public List<OfficeAttendancePlace> getListByName(String unitId, String name,String ignoreId){
		return officeAttendancePlaceDao.getListByName(unitId, name, ignoreId);
	}

	@Override
	public List<OfficeAttendancePlace> listOfficeAttendancePlaceByUnitId(
			String unitId) {
		// TODO Auto-generated method stub
		return officeAttendancePlaceDao.listOfficeAttendancePlaceByUnitId(unitId);
	}

	public void setOfficeAttendancePlaceDao(
			OfficeAttendancePlaceDao officeAttendancePlaceDao) {
		this.officeAttendancePlaceDao = officeAttendancePlaceDao;
	}

	@Override
	public Map<String, OfficeAttendancePlace> getOfficeAttendancePlaceMapByUnitId(
			String unitId) {
		// TODO Auto-generated method stub
		return officeAttendancePlaceDao.getOfficeAttendancePlaceMapByUnitId(unitId);
	}

	@Override
	public List<OfficeAttendancePlace> listOfficeAttendancePlaceIds(String[] ids) {
		// TODO Auto-generated method stub
		return officeAttendancePlaceDao.listOfficeAttendancePlaceIds(ids);
	}
	
}