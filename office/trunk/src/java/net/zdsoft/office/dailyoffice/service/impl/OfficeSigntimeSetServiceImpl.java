package net.zdsoft.office.dailyoffice.service.impl;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeSigntimeSetDao;
import net.zdsoft.office.dailyoffice.entity.OfficeSigntimeSet;
import net.zdsoft.office.dailyoffice.service.OfficeSigntimeSetService;
/**
 * office_signtime_set
 * @author 
 * 
 */
public class OfficeSigntimeSetServiceImpl implements OfficeSigntimeSetService{
	private OfficeSigntimeSetDao officeSigntimeSetDao;

	@Override
	public OfficeSigntimeSet save(OfficeSigntimeSet officeSigntimeSet){
		return officeSigntimeSetDao.save(officeSigntimeSet);
	}

	@Override
	public Integer delete(String[] ids){
		return officeSigntimeSetDao.delete(ids);
	}

	@Override
	public Integer update(OfficeSigntimeSet officeSigntimeSet){
		return officeSigntimeSetDao.update(officeSigntimeSet);
	}

	@Override
	public OfficeSigntimeSet getOfficeSigntimeSetById(String id){
		return officeSigntimeSetDao.getOfficeSigntimeSetById(id);
	}

	@Override
	public Map<String, OfficeSigntimeSet> getOfficeSigntimeSetMapByIds(String[] ids){
		return officeSigntimeSetDao.getOfficeSigntimeSetMapByIds(ids);
	}

	@Override
	public List<OfficeSigntimeSet> getOfficeSigntimeSetList(){
		return officeSigntimeSetDao.getOfficeSigntimeSetList();
	}

	@Override
	public List<OfficeSigntimeSet> getOfficeSigntimeSetPage(Pagination page){
		return officeSigntimeSetDao.getOfficeSigntimeSetPage(page);
	}

	@Override
	public List<OfficeSigntimeSet> getOfficeSigntimeSetByUnitIdList(String unitId){
		return officeSigntimeSetDao.getOfficeSigntimeSetByUnitIdList(unitId);
	}

	@Override
	public List<OfficeSigntimeSet> getOfficeSigntimeSetByUnitIdPage(String unitId, Pagination page){
		return officeSigntimeSetDao.getOfficeSigntimeSetByUnitIdPage(unitId, page);
	}

	@Override
	public OfficeSigntimeSet getOfficeSigntimeSetByUnitIdPage(String unitId,
			String time) {
		return officeSigntimeSetDao.getOfficeSigntimeSetByUnitIdPage(unitId, time);
	}
	
	public void setOfficeSigntimeSetDao(OfficeSigntimeSetDao officeSigntimeSetDao){
		this.officeSigntimeSetDao = officeSigntimeSetDao;
	}

}
