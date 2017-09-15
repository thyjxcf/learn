package net.zdsoft.office.goodmanage.dao;

import java.util.*;

import net.zdsoft.office.goodmanage.entity.OfficeGoodsDistribute;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods_distribute
 * @author 
 * 
 */
public interface OfficeGoodsDistributeDao{

	/**
	 * 新增office_goods_distribute
	 * @param officeGoodsDistribute
	 * @return
	 */
	public OfficeGoodsDistribute save(OfficeGoodsDistribute officeGoodsDistribute);

	public void batchInsertGoodsDistribute(List<OfficeGoodsDistribute> goodsDistributeList);
	
	/**
	 * 根据ids数组删除office_goods_distribute
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_goods_distribute
	 * @param officeGoodsDistribute
	 * @return
	 */
	public Integer update(OfficeGoodsDistribute officeGoodsDistribute);

	/**
	 * 根据id获取office_goods_distribute
	 * @param id
	 * @return
	 */
	public OfficeGoodsDistribute getOfficeGoodsDistributeById(String id);

	/**
	 * 根据ids数组查询office_goods_distributemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeGoodsDistribute> getOfficeGoodsDistributeMapByIds(String[] ids);

	/**
	 * 获取office_goods_distribute列表
	 * @return
	 */
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributeList();

	/**
	 * 分页获取office_goods_distribute列表
	 * @param page
	 * @return
	 */
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributePage(Pagination page);

	/**
	 * 根据unitId获取office_goods_distribute列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributeByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_goods_distribute获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributeByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributeListByConditions(String unitId, String[] goodsTypes, 
			String goodsName, String[] receiverIds, Pagination page);
	
	public List<OfficeGoodsDistribute> getGoodsDistributeByType(String unitId, String[] typeIds);
	
}