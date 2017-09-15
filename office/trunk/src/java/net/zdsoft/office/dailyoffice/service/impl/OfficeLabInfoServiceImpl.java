package net.zdsoft.office.dailyoffice.service.impl;

import java.util.*;

import net.zdsoft.office.dailyoffice.entity.OfficeLabInfo;
import net.zdsoft.office.dailyoffice.service.OfficeLabInfoService;
import net.zdsoft.office.dailyoffice.dao.OfficeLabInfoDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_lab_info
 * @author 
 * 
 */
public class OfficeLabInfoServiceImpl implements OfficeLabInfoService{
	private OfficeLabInfoDao officeLabInfoDao;

	@Override
	public OfficeLabInfo save(OfficeLabInfo officeLabInfo){
		return officeLabInfoDao.save(officeLabInfo);
	}

	@Override
	public Integer delete(String[] ids){
		return officeLabInfoDao.delete(ids);
	}

	@Override
	public Integer update(OfficeLabInfo officeLabInfo){
		return officeLabInfoDao.update(officeLabInfo);
	}

	@Override
	public OfficeLabInfo getOfficeLabInfoById(String id){
		return officeLabInfoDao.getOfficeLabInfoById(id);
	}

	@Override
	public Map<String, OfficeLabInfo> getOfficeLabInfoMapByIds(String[] ids){
		return officeLabInfoDao.getOfficeLabInfoMapByIds(ids);
	}

	@Override
	public List<OfficeLabInfo> getOfficeLabInfoList(){
		return officeLabInfoDao.getOfficeLabInfoList();
	}

	@Override
	public List<OfficeLabInfo> getOfficeLabInfoPage(Pagination page){
		return officeLabInfoDao.getOfficeLabInfoPage(page);
	}

	@Override
	public List<OfficeLabInfo> getOfficeLabInfoByUnitIdList(String unitId){
		return officeLabInfoDao.getOfficeLabInfoByUnitIdList(unitId);
	}

	@Override
	public List<OfficeLabInfo> getOfficeLabInfoByUnitIdPage(String unitId, Pagination page){
		return officeLabInfoDao.getOfficeLabInfoByUnitIdPage(unitId, page);
	}
	
	@Override
	public Map<String, OfficeLabInfo> getOfficeLabInfoMapByConditions(String unitId, String searchLabMode, String[] labSetIds){
		return officeLabInfoDao.getOfficeLabInfoMapByConditions(unitId, searchLabMode, labSetIds);
	}

	public void setOfficeLabInfoDao(OfficeLabInfoDao officeLabInfoDao){
		this.officeLabInfoDao = officeLabInfoDao;
	}
}
	