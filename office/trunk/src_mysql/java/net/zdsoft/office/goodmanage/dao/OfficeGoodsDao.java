package net.zdsoft.office.goodmanage.dao;

import java.util.*;

import net.zdsoft.office.goodmanage.entity.OfficeGoods;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods
 * @author 
 * 
 */
public interface OfficeGoodsDao{

	/**
	 * 新增office_goods
	 * @param officeGoods
	 * @return
	 */
	public OfficeGoods save(OfficeGoods officeGoods);

	/**
	 * 根据ids数组删除office_goods
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_goods
	 * @param officeGoods
	 * @return
	 */
	public Integer update(OfficeGoods officeGoods);

	/**
	 * 根据id获取office_goods
	 * @param id
	 * @return
	 */
	public OfficeGoods getOfficeGoodsById(String id);

	/**
	 * 根据ids数组查询office_goodsmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeGoods> getOfficeGoodsMapByIds(String[] ids);

	/**
	 * 获取office_goods列表
	 * @return
	 */
	public List<OfficeGoods> getOfficeGoodsList();

	/**
	 * 分页获取office_goods列表
	 * @param page
	 * @return
	 */
	public List<OfficeGoods> getOfficeGoodsPage(Pagination page);

	/**
	 * 根据unitId获取office_goods列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeGoods> getOfficeGoodsByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_goods获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeGoods> getOfficeGoodsByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeGoods> getOfficeGoodsByConditions(String unitId, String[] goodsTypes, String goodsName, Boolean isApply, Pagination page);
	
	public List<OfficeGoods> getGoodsByType(String unitId, String[] typeIds);
	
	public List<OfficeGoods> getGoodsByGoodsUnit(String unitId, String[] goodsUnitIds);
}