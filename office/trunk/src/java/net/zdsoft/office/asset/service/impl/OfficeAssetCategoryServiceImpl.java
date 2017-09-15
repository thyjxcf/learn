package net.zdsoft.office.asset.service.impl;


import java.util.*;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.asset.dao.OfficeAssetCategoryDao;
import net.zdsoft.office.asset.entity.OfficeAssetCategory;
import net.zdsoft.office.asset.service.OfficeAssetCategoryService;
/**
 * office_asset_category
 * @author 
 * 
 */
public class OfficeAssetCategoryServiceImpl implements OfficeAssetCategoryService{
	private OfficeAssetCategoryDao officeAssetCategoryDao;

	@Override
	public OfficeAssetCategory save(OfficeAssetCategory officeAssetCategory){
		return officeAssetCategoryDao.save(officeAssetCategory);
	}

	@Override
	public Integer delete(String[] ids){
		return officeAssetCategoryDao.delete(ids);
	}

	@Override
	public Integer update(OfficeAssetCategory officeAssetCategory){
		return officeAssetCategoryDao.update(officeAssetCategory);
	}

	@Override
	public OfficeAssetCategory getOfficeAssetCategoryById(String id){
		return officeAssetCategoryDao.getOfficeAssetCategoryById(id);
	}
	
	public List<OfficeAssetCategory> getOfficeAssetCategoryList(String unitId){
		return officeAssetCategoryDao.getOfficeAssetCategoryList(unitId);
	}
	
	public List<OfficeAssetCategory> getOfficeAssetCategoryList(String unitId, String assetName, String id){
		return officeAssetCategoryDao.getOfficeAssetCategoryList(unitId, assetName, id);
	}
	
	public Map<String, OfficeAssetCategory> getOfficeAssetCategoryMap(String unitId){
		return officeAssetCategoryDao.getOfficeAssetCategoryMap(unitId);
	}
	
	public List<OfficeAssetCategory> getOfficeAssetCategoryListByLeaderId(String unitId, String leaderId){
		return officeAssetCategoryDao.getOfficeAssetCategoryListByLeaderId(unitId, leaderId);
	}

	public List<OfficeAssetCategory> getOfficeAssetCategoryListByDeptLeaderId(String unitId, String deptLeaderId){
		return officeAssetCategoryDao.getOfficeAssetCategoryListByDeptLeaderId(unitId, deptLeaderId);
	}
	
	public void setOfficeAssetCategoryDao(OfficeAssetCategoryDao officeAssetCategoryDao){
		this.officeAssetCategoryDao = officeAssetCategoryDao;
	}
}