package net.zdsoft.office.schedule.service.impl;

import java.util.*;

import net.zdsoft.office.schedule.entity.OfficeCalendarDayInfo;
import net.zdsoft.office.schedule.service.OfficeCalendarDayInfoService;
import net.zdsoft.office.schedule.dao.OfficeCalendarDayInfoDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_calendar_day_info
 * @author 
 * 
 */
public class OfficeCalendarDayInfoServiceImpl implements OfficeCalendarDayInfoService{
	private OfficeCalendarDayInfoDao officeCalendarDayInfoDao;

	@Override
	public OfficeCalendarDayInfo save(OfficeCalendarDayInfo officeCalendarDayInfo){
		return officeCalendarDayInfoDao.save(officeCalendarDayInfo);
	}

	@Override
	public Integer delete(String[] ids){
		return officeCalendarDayInfoDao.delete(ids);
	}

	@Override
	public Integer update(OfficeCalendarDayInfo officeCalendarDayInfo){
		return officeCalendarDayInfoDao.update(officeCalendarDayInfo);
	}

	@Override
	public OfficeCalendarDayInfo getOfficeCalendarDayInfoById(String id){
		return officeCalendarDayInfoDao.getOfficeCalendarDayInfoById(id);
	}

	@Override
	public Map<String, OfficeCalendarDayInfo> getOfficeCalendarDayInfoMapByIds(String[] ids){
		return officeCalendarDayInfoDao.getOfficeCalendarDayInfoMapByIds(ids);
	}

	@Override
	public List<OfficeCalendarDayInfo> getOfficeCalendarDayInfoList(){
		return officeCalendarDayInfoDao.getOfficeCalendarDayInfoList();
	}

	@Override
	public List<OfficeCalendarDayInfo> getOfficeCalendarDayInfoPage(Pagination page){
		return officeCalendarDayInfoDao.getOfficeCalendarDayInfoPage(page);
	}

	@Override
	public List<OfficeCalendarDayInfo> getOfficeCalendarDayInfoByUnitIdList(String unitId){
		return officeCalendarDayInfoDao.getOfficeCalendarDayInfoByUnitIdList(unitId);
	}

	@Override
	public List<OfficeCalendarDayInfo> getOfficeCalendarDayInfoByUnitIdPage(String unitId, Pagination page){
		return officeCalendarDayInfoDao.getOfficeCalendarDayInfoByUnitIdPage(unitId, page);
	}
	
	@Override
	public Map<String, OfficeCalendarDayInfo> getCalendarDayInfoMapByUnitId(
			Date beginDate, Date endDate, String unitId) {
		return officeCalendarDayInfoDao.getCalendarDayInfoMapByUnitId( beginDate, endDate, unitId);
	}
	
	public void setOfficeCalendarDayInfoDao(OfficeCalendarDayInfoDao officeCalendarDayInfoDao){
		this.officeCalendarDayInfoDao = officeCalendarDayInfoDao;
	}
}
