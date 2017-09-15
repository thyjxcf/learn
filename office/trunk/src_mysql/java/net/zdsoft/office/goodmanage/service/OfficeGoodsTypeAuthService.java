package net.zdsoft.office.goodmanage.service;

import java.util.*;
import net.zdsoft.office.goodmanage.entity.OfficeGoodsTypeAuth;
import net.zdsoft.keel.util.Pagination;
/**
 * office_goods_type_auth
 * @author 
 * 
 */
public interface OfficeGoodsTypeAuthService{

	/**
	 * 新增office_goods_type_auth
	 * @param officeGoodsTypeAuth
	 * @return
	 */
	public OfficeGoodsTypeAuth save(OfficeGoodsTypeAuth officeGoodsTypeAuth);

	/**
	 * 根据ids数组删除office_goods_type_auth数据
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
	 * 根据UnitId获取office_goods_type_auth列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_goods_type_auth
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByUnitIdPage(String unitId, Pagination page);
	
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByConditions(String unitId, String searchName, Pagination page);
	
	public List<OfficeGoodsTypeAuth> getOfficeGoodsTypeAuthByUserId(String unitId, String userId);
	
	/**
	 * 根据要删除的typeId更新权限表，若有用户有权限的类型全被删除，则删除此用户权限
	 * @param unitId
	 * @param delTypeIds
	 */
	public void updateByDelTypeIds(String unitId, String[] delTypeIds);
	
	/**
	 * 批量更新
	 * @param authlist
	 */
	public void batchUpdate(List<OfficeGoodsTypeAuth> authlist);

	public Map<String,List<OfficeGoodsTypeAuth>> getOfficeGoodsTypeAuthMapByUserIds(String[] userIds);
}