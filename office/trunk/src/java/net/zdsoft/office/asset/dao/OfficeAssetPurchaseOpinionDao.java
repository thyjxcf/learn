package net.zdsoft.office.asset.dao;

import java.util.*;

import net.zdsoft.office.asset.entity.OfficeAssetPurchaseOpinion;
import net.zdsoft.keel.util.Pagination;
/**
 * 采购意见信息表
 * @author 
 * 
 */
public interface OfficeAssetPurchaseOpinionDao{

	/**
	 * 新增采购意见信息表
	 * @param officeAssetPurchaseOpinion
	 * @return
	 */
	public OfficeAssetPurchaseOpinion save(OfficeAssetPurchaseOpinion officeAssetPurchaseOpinion);

	/**
	 * 根据ids数组删除采购意见信息表
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新采购意见信息表
	 * @param officeAssetPurchaseOpinion
	 * @return
	 */
	public Integer update(OfficeAssetPurchaseOpinion officeAssetPurchaseOpinion);

	/**
	 * 根据id获取采购意见信息表
	 * @param id
	 * @return
	 */
	public OfficeAssetPurchaseOpinion getOfficeAssetPurchaseOpinionById(String id);

	/**
	 * 根据ids数组查询采购意见信息表map
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionMapByIds(String[] ids);

	/**
	 * 获取采购意见信息表列表
	 * @return
	 */
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionList();

	/**
	 * 分页获取采购意见信息表列表
	 * @param page
	 * @return
	 */
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionPage(Pagination page);

	/**
	 * 根据unitId获取采购意见信息表列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionByUnitIdList(String unitId);

	/**
	 * 根据unitId分页采购意见信息表获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据unitId、type、content获取意见信息列表
	 * @param unitId
	 * @param type
	 * @param content
	 * @return
	 */
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionList(String unitId, String type, String content);
	
	/**
	 * 根据unitId、type获取意见信息
	 * @param unitId
	 * @param type
	 * @return
	 */
	public List<OfficeAssetPurchaseOpinion> getOfficeAssetPurchaseOpinionListByType(String unitId, String type);
}