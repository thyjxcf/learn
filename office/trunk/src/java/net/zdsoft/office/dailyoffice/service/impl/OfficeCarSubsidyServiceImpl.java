package net.zdsoft.office.dailyoffice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.dailyoffice.dao.OfficeCarSubsidyDao;
import net.zdsoft.office.dailyoffice.entity.OfficeCarSubsidy;
import net.zdsoft.office.dailyoffice.service.OfficeCarSubsidyService;

import org.apache.commons.lang.ArrayUtils;
/**
 * office_car_subsidy
 * @author 
 * 
 */
public class OfficeCarSubsidyServiceImpl implements OfficeCarSubsidyService{
	private OfficeCarSubsidyDao officeCarSubsidyDao;

	@Override
	public OfficeCarSubsidy save(OfficeCarSubsidy officeCarSubsidy){
		return officeCarSubsidyDao.save(officeCarSubsidy);
	}

	@Override
	public Integer delete(String[] ids){
		return officeCarSubsidyDao.delete(ids);
	}

	@Override
	public Integer update(OfficeCarSubsidy officeCarSubsidy){
		return officeCarSubsidyDao.update(officeCarSubsidy);
	}

	@Override
	public OfficeCarSubsidy getOfficeCarSubsidyById(String id){
		return officeCarSubsidyDao.getOfficeCarSubsidyById(id);
	}

	@Override
	public Map<String, OfficeCarSubsidy> getOfficeCarSubsidyMapByIds(String[] ids){
		return officeCarSubsidyDao.getOfficeCarSubsidyMapByIds(ids);
	}

	@Override
	public List<OfficeCarSubsidy> getOfficeCarSubsidyList(){
		return officeCarSubsidyDao.getOfficeCarSubsidyList();
	}

	@Override
	public List<OfficeCarSubsidy> getOfficeCarSubsidyPage(Pagination page){
		return officeCarSubsidyDao.getOfficeCarSubsidyPage(page);
	}

	public void setOfficeCarSubsidyDao(OfficeCarSubsidyDao officeCarSubsidyDao){
		this.officeCarSubsidyDao = officeCarSubsidyDao;
	}
	
	@Override
	public List<OfficeCarSubsidy> getOfficeCarSubsidyList(String unitId) {
		return officeCarSubsidyDao.getOfficeCarSubsidyList(unitId);
	}
	@Override
	public void save(String unitId, String[] subsidyIds, String[] scopeNames,
			String[] subsidys) {
		List<OfficeCarSubsidy> updateList = new ArrayList<OfficeCarSubsidy>();
		List<OfficeCarSubsidy> insertList = new ArrayList<OfficeCarSubsidy>();
		int i = 0;
		if(ArrayUtils.isNotEmpty(subsidyIds)){
			for(String id:subsidyIds){
				OfficeCarSubsidy ocd = new OfficeCarSubsidy();
				ocd.setId(id);
				ocd.setScope(scopeNames[i]);
				ocd.setSubsidy(Float.parseFloat(subsidys[i]));
				updateList.add(ocd);
				i++;
			}
			officeCarSubsidyDao.batchUpdate(updateList);
		}
		if(scopeNames.length>i){
			for(;i<scopeNames.length;i++){
				OfficeCarSubsidy ocd = new OfficeCarSubsidy();
				ocd.setUnitId(unitId);
				ocd.setScope(scopeNames[i]);
				ocd.setSubsidy(Float.parseFloat(subsidys[i]));
				ocd.setIsDetele(false);
				insertList.add(ocd);
			}
			officeCarSubsidyDao.batchSave(insertList);
		}
	}
}