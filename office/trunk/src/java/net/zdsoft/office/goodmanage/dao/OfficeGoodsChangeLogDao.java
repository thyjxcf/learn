package net.zdsoft.office.goodmanage.dao;

import java.util.*;

import net.zdsoft.office.goodmanage.entity.OfficeGoodsChangeLog;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods_change_log
 * @author 
 * 
 */
public interface OfficeGoodsChangeLogDao{

	/**
	 * 新增office_goods_change_log
	 * @param officeGoodsChangeLog
	 * @return
	 */
	public OfficeGoodsChangeLog save(OfficeGoodsChangeLog officeGoodsChangeLog);

	/**
	 * 根据ids数组删除office_goods_change_log
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	public Integer deleteByGoodsIds(String[] goodsIds);

	/**
	 * 更新office_goods_change_log
	 * @param officeGoodsChangeLog
	 * @return
	 */
	public Integer update(OfficeGoodsChangeLog officeGoodsChangeLog);

	/**
	 * 根据id获取office_goods_change_log
	 * @param id
	 * @return
	 */
	public OfficeGoodsChangeLog getOfficeGoodsChangeLogById(String id);

	/**
	 * 根据ids数组查询office_goods_change_logmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeGoodsChangeLog> getOfficeGoodsChangeLogMapByIds(String[] ids);

	/**
	 * 获取office_goods_change_log列表
	 * @return
	 */
	public List<OfficeGoodsChangeLog> getOfficeGoodsChangeLogList();

	/**
	 * 分页获取office_goods_change_log列表
	 * @param page
	 * @return
	 */
	public List<OfficeGoodsChangeLog> getOfficeGoodsChangeLogPage(Pagination page);
	
	public List<OfficeGoodsChangeLog> getOfficeGoodsChangeLogByGoodsId(String goodsId, Pagination page);
}