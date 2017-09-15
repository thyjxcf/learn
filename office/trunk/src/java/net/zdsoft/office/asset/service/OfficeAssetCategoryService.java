package net.zdsoft.office.asset.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.office.asset.entity.OfficeAssetCategory;
/**
 * office_asset_category
 * @author 
 * 
 */
public interface OfficeAssetCategoryService{

	/**
	 * 新增office_asset_category
	 * @param officeAssetCategory
	 * @return
	 */
	public OfficeAssetCategory save(OfficeAssetCategory officeAssetCategory);

	/**
	 * 根据ids数组删除office_asset_category数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_asset_category
	 * @param officeAssetCategory
	 * @return
	 */
	public Integer update(OfficeAssetCategory officeAssetCategory);

	/**
	 * 根据id获取office_asset_category
	 * @param id
	 * @return
	 */
	public OfficeAssetCategory getOfficeAssetCategoryById(String id);
	
	/**
	 * 获取list
	 * @param unitId
	 * @return
	 */
	public List<OfficeAssetCategory> getOfficeAssetCategoryList(String unitId);
	
	/**
	 * 查询list 
	 * @param unitId
	 * @param assetName
	 * @param id 若有值  则查询不等于此id的数据
	 * @return
	 */
	public List<OfficeAssetCategory> getOfficeAssetCategoryList(String unitId, String assetName, String id);
	
	/**
	 * 查询map  包括已经删除的
	 * @param unitId
	 * @return
	 */
	public Map<String, OfficeAssetCategory> getOfficeAssetCategoryMap(String unitId);
	
	/**
	 * 获取leaderId所负责的资产类别list
	 * @param unitId
	 * @param leaderId
	 * @return
	 */
	public List<OfficeAssetCategory> getOfficeAssetCategoryListByLeaderId(String unitId, String leaderId);
/**
	 * 获取deptLeaderId所负责的资产类别list
	 * @param unitId
	 * @param deptLeaderId
	 * @return
	 */
	public List<OfficeAssetCategory> getOfficeAssetCategoryListByDeptLeaderId(String unitId, String deptLeaderId);}