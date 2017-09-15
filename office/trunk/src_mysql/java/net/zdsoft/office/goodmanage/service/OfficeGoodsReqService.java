package net.zdsoft.office.goodmanage.service;

import java.util.*;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsReq;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods_req
 * @author 
 * 
 */
public interface OfficeGoodsReqService{

	/**
	 * 新增office_goods_req
	 * @param officeGoodsReq
	 * @return
	 */
	public OfficeGoodsReq save(OfficeGoodsReq officeGoodsReq);
	
	/**
	 * 提交数据并推送到审核中间表，微办公手机端使用
	 * @param officeGoodsReq
	 * @return
	 */
	public OfficeGoodsReq submitSave(OfficeGoodsReq officeGoodsReq);
	
	/**
	 * 审核通过或者不通过时推送到审核中间表，微办公手机端使用
	 * @param officeGoodsReq
	 * @return
	 */
	public Integer auditSave(OfficeGoodsReq officeGoodsReq);

	/**
	 * 根据ids数组删除office_goods_req数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 根据物品ID删除
	 * @param goodsIds
	 * @return
	 */
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
	 * 根据UnitId获取office_goods_req列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeGoodsReq> getOfficeGoodsReqByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_goods_req
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeGoodsReq> getOfficeGoodsReqByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据条件获取list
	 * @param unitId
	 * @param goodsTypes 物品类别
	 * @param goodsName
	 * @param applyType 审核状态
	 * @param applyUserId 申请人
	 * @param beginTime 申请时间
	 * @param endTime 申请时间
	 * @param page
	 * @return
	 */
	public List<OfficeGoodsReq> getOfficeGoodsReqByConditions(String unitId, String[] goodsTypes, String goodsName,
			String applyType, String applyUserId, Date beginTime, Date endTime, Pagination page);
}