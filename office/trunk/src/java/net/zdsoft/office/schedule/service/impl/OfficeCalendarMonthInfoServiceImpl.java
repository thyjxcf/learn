package net.zdsoft.office.schedule.service.impl;

import java.util.*;

import net.zdsoft.office.schedule.entity.OfficeCalendarMonthInfo;
import net.zdsoft.office.schedule.service.OfficeCalendarMonthInfoService;
import net.zdsoft.office.schedule.dao.OfficeCalendarMonthInfoDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_calendar_month_info
 * @author 
 * 
 */
public class OfficeCalendarMonthInfoServiceImpl implements OfficeCalendarMonthInfoService{
	private OfficeCalendarMonthInfoDao officeCalendarMonthInfoDao;

	@Override
	public OfficeCalendarMonthInfo save(OfficeCalendarMonthInfo officeCalendarMonthInfo){
		return officeCalendarMonthInfoDao.save(officeCalendarMonthInfo);
	}

	@Override
	public Integer delete(String[] ids){
		return officeCalendarMonthInfoDao.delete(ids);
	}

	@Override
	public Integer update(OfficeCalendarMonthInfo officeCalendarMonthInfo){
		return officeCalendarMonthInfoDao.update(officeCalendarMonthInfo);
	}

	@Override
	public OfficeCalendarMonthInfo getOfficeCalendarMonthInfoById(String id){
		return officeCalendarMonthInfoDao.getOfficeCalendarMonthInfoById(id);
	}

	@Override
	public Map<String, OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoMapByIds(String[] ids){
		return officeCalendarMonthInfoDao.getOfficeCalendarMonthInfoMapByIds(ids);
	}

	@Override
	public List<OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoList(){
		return officeCalendarMonthInfoDao.getOfficeCalendarMonthInfoList();
	}

	@Override
	public List<OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoPage(Pagination page){
		return officeCalendarMonthInfoDao.getOfficeCalendarMonthInfoPage(page);
	}

	@Override
	public List<OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoByUnitIdList(String unitId){
		return officeCalendarMonthInfoDao.getOfficeCalendarMonthInfoByUnitIdList(unitId);
	}

	@Override
	public List<OfficeCalendarMonthInfo> getOfficeCalendarMonthInfoByUnitIdPage(String unitId, Pagination page){
		return officeCalendarMonthInfoDao.getOfficeCalendarMonthInfoByUnitIdPage(unitId, page);
	}
	@Override
	public Map<String, OfficeCalendarMonthInfo> getCalendarMonthInfoMapByUnitId(
			String unitId) {
		return officeCalendarMonthInfoDao.getCalendarMonthInfoMapByUnitId(unitId);
	}
	public void setOfficeCalendarMonthInfoDao(OfficeCalendarMonthInfoDao officeCalendarMonthInfoDao){
		this.officeCalendarMonthInfoDao = officeCalendarMonthInfoDao;
	}
}
