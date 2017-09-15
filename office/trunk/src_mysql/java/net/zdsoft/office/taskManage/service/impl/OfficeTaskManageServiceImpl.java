package net.zdsoft.office.taskManage.service.impl;

import java.util.*;

import net.zdsoft.office.taskManage.entity.OfficeTaskManage;
import net.zdsoft.office.taskManage.service.OfficeTaskManageService;
import net.zdsoft.office.taskManage.dao.OfficeTaskManageDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_task_manage
 * @author 
 * 
 */
public class OfficeTaskManageServiceImpl implements OfficeTaskManageService{
	private OfficeTaskManageDao officeTaskManageDao;

	@Override
	public OfficeTaskManage save(OfficeTaskManage officeTaskManage){
		return officeTaskManageDao.save(officeTaskManage);
	}

	@Override
	public Integer delete(String[] ids){
		return officeTaskManageDao.delete(ids);
	}

	@Override
	public Integer update(OfficeTaskManage officeTaskManage){
		return officeTaskManageDao.update(officeTaskManage);
	}

	@Override
	public OfficeTaskManage getOfficeTaskManageById(String id){
		return officeTaskManageDao.getOfficeTaskManageById(id);
	}

	@Override
	public Map<String, OfficeTaskManage> getOfficeTaskManageMapByIds(String[] ids){
		return officeTaskManageDao.getOfficeTaskManageMapByIds(ids);
	}

	@Override
	public List<OfficeTaskManage> getOfficeTaskManageList(){
		return officeTaskManageDao.getOfficeTaskManageList();
	}

	@Override
	public List<OfficeTaskManage> getOfficeTaskManagePage(Pagination page){
		return officeTaskManageDao.getOfficeTaskManagePage(page);
	}

	@Override
	public List<OfficeTaskManage> getOfficeTaskManageByUnitIdList(String unitId){
		return officeTaskManageDao.getOfficeTaskManageByUnitIdList(unitId);
	}

	@Override
	public List<OfficeTaskManage> getOfficeTaskManageByUnitIdPage(String unitId, Pagination page){
		return officeTaskManageDao.getOfficeTaskManageByUnitIdPage(unitId, page);
	}
	
	@Override
	public List<OfficeTaskManage> getOfficeTaskManageListByCondition(String unitId, String dealUserId, String[] states, String queryBeginDate, String queryEndDate, Pagination page){
		return officeTaskManageDao.getOfficeTaskManageListByCondition(unitId, dealUserId, states, queryBeginDate, queryEndDate, page);
	}

	public void setOfficeTaskManageDao(OfficeTaskManageDao officeTaskManageDao){
		this.officeTaskManageDao = officeTaskManageDao;
	}
}
