package net.zdsoft.office.goodmanage.service;

import java.util.*;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsType;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods_type
 * @author 
 * 
 */
public interface OfficeGoodsTypeService{

	/**
	 * 新增office_goods_type
	 * @param officeGoodsType
	 * @return
	 */
	public OfficeGoodsType save(OfficeGoodsType officeGoodsType);

	/**
	 * 根据ids数组删除office_goods_type数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_goods_type
	 * @param officeGoodsType
	 * @return
	 */
	public Integer update(OfficeGoodsType officeGoodsType);

	/**
	 * 根据id获取office_goods_type
	 * @param id
	 * @return
	 */
	public OfficeGoodsType getOfficeGoodsTypeById(String id);

	/**
	 * 根据ids数组查询office_goods_typemap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeGoodsType> getOfficeGoodsTypeMapByIds(String[] ids);

	/**
	 * 获取office_goods_type列表
	 * @return
	 */
	public List<OfficeGoodsType> getOfficeGoodsTypeList();

	/**
	 * 分页获取office_goods_type列表
	 * @param page
	 * @return
	 */
	public List<OfficeGoodsType> getOfficeGoodsTypePage(Pagination page);

	/**
	 * 根据UnitId获取office_goods_type列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeGoodsType> getOfficeGoodsTypeByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_goods_type
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeGoodsType> getOfficeGoodsTypeByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据unitId获取map,key为type_id
	 * @param unitId
	 * @return
	 */
	public Map<String, OfficeGoodsType> getOfficeGoodsTypeMapByUnitId(String unitId);
	
	public OfficeGoodsType getOfficeGoodsTypeByTypeId(String unitId, String typeId);
	
	public List<OfficeGoodsType> getOfficeGoodsTypeByTypeId(String unitId, String[] typeIds);
}