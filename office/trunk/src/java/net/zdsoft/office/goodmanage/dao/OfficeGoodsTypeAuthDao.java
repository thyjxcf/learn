package net.zdsoft.office.goodmanage.dao;

import java.util.*;

import net.zdsoft.office.goodmanage.entity.OfficeGoodsTypeAuth;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods_type_auth
 * @author 
 * 
 */
public interface OfficeGoodsTypeAuthDao{

	/**
	 * 新增office_goods_type_auth
	 * @param officeGoodsTypeAuth
	 * @return
	 */
	public OfficeGoodsTypeAuth save(OfficeGoodsTypeAuth officeGoodsTypeAuth);

	/**
	 * 根据ids数组删除office_goods_type_auth
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_goods_type_auth
	 * @param officeGoodsTypeAuth
	 * @return
	 */
	public Integer update(OfficeGoodsTypeAuth officeGoodsTypeAuth);

	/**
	 * 根据id获取office_goods_type_auth
	 * @param id
	 * @return
	 */
	public OfficeGoodsTypeAuth getOfficeGoodsTypeAuthById(String id);

	/**
	 * 根据ids数组查询office_goods_type_authmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthMapByIds(String[] ids);

	/**
	 * 获取office_goods_type_auth列表
	 * @return
	 */
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthList();

	/**
	 * 分页获取office_goods_type_auth列表
	 * @param page
	 * @return
	 */
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthPage(Pagination page);

	/**
	 * 根据unitId获取office_goods_type_auth列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByUnitIdList(String unitId);

	/**
	 * 根据unitId分页office_goods_type_auth获取
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByConditions(String unitId, String searchName, Pagination page);
	
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByUserId(String unitId, String userId);
	
	public void batchUpdate(List<OfficeGoodsTypeAuth> authlist);

	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthList(String[] userIds);
}