package net.zdsoft.office.goodmanage.service;

import java.util.*;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsDistribute;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods_distribute
 * @author 
 * 
 */
public interface OfficeGoodsDistributeService{

	/**
	 * 新增office_goods_distribute
	 * @param officeGoodsDistribute
	 * @return
	 */
	public OfficeGoodsDistribute save(OfficeGoodsDistribute officeGoodsDistribute);

	public void batchInsertGoodsDistribute(List<OfficeGoodsDistribute> goodsDistributeList);
	
	/**
	 * 根据ids数组删除office_goods_distribute数据
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
	 * 根据UnitId获取office_goods_distribute列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributeByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_goods_distribute
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributeByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据条件获取物品分发信息（非审核模式）
	 * @param unitId
	 * @param goodsTypes 物品类别
	 * @param goodsName 物品名称
	 * @param receiverIds 使用人ID
	 * @param page
	 * @return
	 */
	public List<OfficeGoodsDistribute> getOfficeGoodsDistributeListByConditions(String unitId, String[] goodsTypes, 
			String goodsName, String[] receiverIds, Pagination page);
	
	public List<OfficeGoodsDistribute> getGoodsDistributeByType(String unitId, String[] typeIds);
}