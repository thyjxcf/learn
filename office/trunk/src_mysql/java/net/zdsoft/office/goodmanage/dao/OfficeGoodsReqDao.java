package net.zdsoft.office.goodmanage.dao;

import java.util.*;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsReq;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods_req
 * @author 
 * 
 */
public interface OfficeGoodsReqDao{

	/**
	 * 新增office_goods_req
	 * @param officeGoodsReq
	 * @return
	 */
	public OfficeGoodsReq save(OfficeGoodsReq officeGoodsReq);

	/**
	 * 根据ids数组删除office_goods_req
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	public Integer deleteByGoodsIds(String[] goodsIds);
	
	/**
	 * 更新office_goods_req
	 * @param officeGoodsReq
	 * @return
	 */
	public Integer update(OfficeGoodsReq officeGoodsReq);

	/**
	 * 根据id获取office_goods_req
	 * @param id
	 * @return
	 */
	public OfficeGoodsReq getOfficeGoodsReqById(String id);

	/**
	 * 根据ids数组查询office_goods_reqmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeGoodsReq> getOfficeGoodsReqMapByIds(String[] ids);

	/**
	 * 获取office_goods_req列表
	 * @return
	 */
	public List<OfficeGoodsReq> getOfficeGoodsReqList();

	/**
	 * 分页获取office_goods_req列表
	 * @param page
	 * @return
	 */
	public List<OfficeGoodsReq> getOfficeGoodsReqPage(Pagination page);

	/**
	 * 根据unitId获取office_goods_req列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeGoodsReq> getOfficeGoodsReqByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_goods_req获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeGoodsReq> getOfficeGoodsReqByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeGoodsReq> getOfficeGoodsReqByConditions(String unitId, String[] goodsIds,
			String applyType, String applyUserId, Date beginTime, Date endTime, Pagination page);
}