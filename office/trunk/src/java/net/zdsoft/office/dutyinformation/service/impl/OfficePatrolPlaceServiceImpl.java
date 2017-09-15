package net.zdsoft.office.dutyinformation.service.impl;

import java.util.*;

import net.zdsoft.office.dutyinformation.entity.OfficePatrolPlace;
import net.zdsoft.office.dutyinformation.service.OfficePatrolPlaceService;
import net.zdsoft.office.dutyinformation.dao.OfficePatrolPlaceDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_patrol_place
 * @author 
 * 
 */
public class OfficePatrolPlaceServiceImpl implements OfficePatrolPlaceService{
	private OfficePatrolPlaceDao officePatrolPlaceDao;

	@Override
	public OfficePatrolPlace save(OfficePatrolPlace officePatrolPlace){
		return officePatrolPlaceDao.save(officePatrolPlace);
	}

	@Override
	public Integer delete(String[] ids){
		return officePatrolPlaceDao.delete(ids);
	}

	@Override
	public Integer update(OfficePatrolPlace officePatrolPlace){
		return officePatrolPlaceDao.update(officePatrolPlace);
	}

	@Override
	public OfficePatrolPlace getOfficePatrolPlaceById(String id){
		return officePatrolPlaceDao.getOfficePatrolPlaceById(id);
	}

	@Override
	public Map<String, OfficePatrolPlace> getOfficePatrolPlaceMapByIds(String[] ids){
		return officePatrolPlaceDao.getOfficePatrolPlaceMapByIds(ids);
	}

	@Override
	public List<OfficePatrolPlace> getOfficePatrolPlaceList(){
		return officePatrolPlaceDao.getOfficePatrolPlaceList();
	}

	@Override
	public List<OfficePatrolPlace> getOfficePatrolPlacePage(Pagination page){
		return officePatrolPlaceDao.getOfficePatrolPlacePage(page);
	}

	@Override
	public Map<String, OfficePatrolPlace> getOfficePMap(String[] ids) {
		return officePatrolPlaceDao.getOfficePMap(ids);
	}

	@Override
	public List<OfficePatrolPlace> getOfficePatrolPlaceByUnitIdList(String unitId){
		return officePatrolPlaceDao.getOfficePatrolPlaceByUnitIdList(unitId);
	}

	@Override
	public List<OfficePatrolPlace> getOfficePatrolPlaceByUnitIdPage(String unitId, Pagination page){
		return officePatrolPlaceDao.getOfficePatrolPlaceByUnitIdPage(unitId, page);
	}

	public void setOfficePatrolPlaceDao(OfficePatrolPlaceDao officePatrolPlaceDao){
		this.officePatrolPlaceDao = officePatrolPlaceDao;
	}
}
