package net.zdsoft.office.goodmanage.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.goodmanage.entity.OfficeGoods;
/**
 * office_goods
 * @author 
 * 
 */
public interface OfficeGoodsService{

	/**
	 * 新增office_goods
	 * @param officeGoods
	 * @return
	 */
	public OfficeGoods save(OfficeGoods officeGoods);

	/**
	 * 根据ids数组删除office_goods数据
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
	 * 根据UnitId获取office_goods列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeGoods> getOfficeGoodsByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_goods
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeGoods> getOfficeGoodsByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据条件获取列表
	 * @param unitId
	 * @param goodsTypes
	 * @param goodsName
	 * @param isApply 是否用于物品申请，是则取出库存为0的物品
	 * @param page
	 * @return
	 */
	public List<OfficeGoods> getOfficeGoodsByConditions(String unitId, String[] goodsTypes, String goodsName, Boolean isApply, Pagination page);
	
	/**
	 * 根据物品类别ID，来获取物品list
	 * @param unitId
	 * @param typeIds
	 * @return
	 */
	public List<OfficeGoods> getGoodsByType(String unitId, String[] typeIds);
	
	/**
	 * 根据物品单位ID，来获取物品list
	 * @param unitId
	 * @param goodsUnitIds
	 * @return
	 */
	public List<OfficeGoods> getGoodsByGoodsUnit(String unitId, String[] goodsUnitIds);
	
	/**
	 * 发送消息
	 * @param user	发送人
	 * @param unit	发送单位
	 * @param officeGoods	物品
	 * ----------下面两个字段审核的时候用
	 * @param state 审核状态 通过、不通过
	 * @param userIds 审核通过或者不通过的情况下传发送到的用户
	 * @param goodsReqId 相关的申请信息ID
	 */
	public void sendMsg(User user,Unit unit, OfficeGoods officeGoods, Integer state, String[] userIds, String goodsReqId);
	
}