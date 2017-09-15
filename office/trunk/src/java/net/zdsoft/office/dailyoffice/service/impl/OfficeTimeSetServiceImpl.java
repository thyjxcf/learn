package net.zdsoft.office.dailyoffice.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeTimeSetDao;
import net.zdsoft.office.dailyoffice.entity.OfficeTimeSet;
import net.zdsoft.office.dailyoffice.service.OfficeTimeSetService;
/**
 * office_time_set
 * @author 
 * 
 */
public class OfficeTimeSetServiceImpl implements OfficeTimeSetService{
	private OfficeTimeSetDao officeTimeSetDao;

	@Override
	public OfficeTimeSet save(OfficeTimeSet officeTimeSet){
		return officeTimeSetDao.save(officeTimeSet);
	}

	@Override
	public Integer delete(String[] ids){
		return officeTimeSetDao.delete(ids);
	}

	@Override
	public Integer update(OfficeTimeSet officeTimeSet){
		return officeTimeSetDao.update(officeTimeSet);
	}

	@Override
	public OfficeTimeSet getOfficeTimeSetById(String id){
		return officeTimeSetDao.getOfficeTimeSetById(id);
	}

	@Override
	public Map<String, OfficeTimeSet> getOfficeTimeSetMapByIds(String[] ids){
		return officeTimeSetDao.getOfficeTimeSetMapByIds(ids);
	}

	@Override
	public List<OfficeTimeSet> getOfficeTimeSetList(){
		return officeTimeSetDao.getOfficeTimeSetList();
	}

	@Override
	public List<OfficeTimeSet> getOfficeTimeSetPage(Pagination page){
		return officeTimeSetDao.getOfficeTimeSetPage(page);
	}

	@Override
	public OfficeTimeSet getOfficeTimeSetByUnitId(String unitId){
		return officeTimeSetDao.getOfficeTimeSetByUnitId(unitId);
	}

	@Override
	public List<OfficeTimeSet> getOfficeTimeSetByUnitIdPage(String unitId, Pagination page){
		return officeTimeSetDao.getOfficeTimeSetByUnitIdPage(unitId, page);
	}

	public void setOfficeTimeSetDao(OfficeTimeSetDao officeTimeSetDao){
		this.officeTimeSetDao = officeTimeSetDao;
	}
}